package tr.com.kafein.dashboard.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.List;
import java.util.stream.Collectors;

import static tr.com.kafein.dashboard.util.Constants.SESSION_EXPIRE_MSG;
import static tr.com.kafein.dashboard.util.Constants.TOKEN_AUTHORITIES_KEY;
import static tr.com.kafein.dashboard.util.Constants.TOKEN_PREFIX;

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
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (ExpiredJwtException e) {
            ErrorDto dto = ErrorDto.builder()
                    .resultCode(HttpStatus.UNAUTHORIZED.value())
                    .result(HttpStatus.UNAUTHORIZED.name())
                    .errorMessage(SESSION_EXPIRE_MSG)
                    .build();
            res.resetBuffer();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            res.getOutputStream().print(objectMapper.writeValueAsString(dto));
            res.flushBuffer();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    @SuppressWarnings("unchecked")
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_STRING);
        if (!StringUtils.isBlank(token) && token.startsWith(TOKEN_PREFIX)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.TOKEN_SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String user = claims.getSubject();
            if (!StringUtils.isBlank(user)) {
                List<String> roles = (List<String>) claims.get(TOKEN_AUTHORITIES_KEY);
                List<GrantedAuthority> list = roles
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                return new UsernamePasswordAuthenticationToken(user, null, list);
            } else {
                return null;
            }
        }
        return null;
    }
}
