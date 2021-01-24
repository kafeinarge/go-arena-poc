package tr.com.kafein.dashboard.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tr.com.kafein.dashboard.dto.ErrorDto;
import tr.com.kafein.dashboard.util.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ApiJWTAuthorizationFilter extends BasicAuthenticationFilter {

    public ApiJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(Constants.HEADER_STRING);
        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (ExpiredJwtException e) {
            ErrorDto dto = new ErrorDto();
            dto.setResultCode(HttpStatus.UNAUTHORIZED.value());
            dto.setResult(HttpStatus.UNAUTHORIZED.name());
            dto.setErrorMessage("Oturumunuzun süresi dolmuş, lütfen tekrar giriş yapınız");
            res.resetBuffer();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            res.getOutputStream().print(objectMapper.writeValueAsString(dto));
            res.flushBuffer();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    @SuppressWarnings("unchecked")
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.TOKEN_SECRET)
                    .parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
                    .getBody();
            String user = claims.getSubject();
            ArrayList<String> roles = (ArrayList<String>) claims.get(Constants.TOKEN_AUTHORITIES_KEY);
            ArrayList<GrantedAuthority> list = new ArrayList<>();
            if (roles != null) {
                for (String a : roles) {
                    GrantedAuthority g = new SimpleGrantedAuthority(a);
                    list.add(g);
                }
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, list);
            }
            return null;
        }
        return null;
    }
}
