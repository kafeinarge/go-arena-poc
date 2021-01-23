package tr.kafein.com.uaaserver.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import tr.kafein.com.uaaserver.dto.AccessTokenDto;
import tr.kafein.com.uaaserver.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

import static tr.kafein.com.uaaserver.util.Constants.*;


@Service
public class AuthenticationService {


    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    public AccessTokenDto getToken(String userName, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userName,
                password,
                new ArrayList<>());

        Authentication authentication = customAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (authentication.getPrincipal() != null) {
            ArrayList<String> authList = new ArrayList<>(authentication.getAuthorities().size());

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                authList.add(authority.getAuthority());
            }
            Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
            String token = Jwts.builder()
                    .claim(TOKEN_AUTHORITIES_KEY, authList)
                    .setSubject(userName)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                    .compact();

            AccessTokenDto tokenDto = new AccessTokenDto();
            tokenDto.token = token;
            tokenDto.expireDate = DateUtil.toString(expireDate);

            return tokenDto;

        }
        throw new BadCredentialsException("Hatalı Giriş");
    }
}
