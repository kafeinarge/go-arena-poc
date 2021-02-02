package tr.com.kafein.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tr.com.kafein.userservice.constants.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static tr.com.kafein.userservice.constants.Constants.TOKEN_AUTHORITIES_KEY;
import static tr.com.kafein.userservice.constants.Constants.TOKEN_PREFIX;

public class ApiJWTAuthorizationFilter extends BasicAuthenticationFilter {

    public ApiJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(Constants.HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
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
