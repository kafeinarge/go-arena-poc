package tr.com.kafein.uaaserver.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;
import tr.com.kafein.uaaserver.util.Constants;
import tr.com.kafein.uaaserver.util.DateUtil;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;

import java.util.ArrayList;
import java.util.Date;

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
            Date expireDate = new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME);
            String token = Jwts.builder()
                    .setSubject(userName)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)
                    .compact();

            AccessTokenDto tokenDto = new AccessTokenDto();
            tokenDto.token = token;
            tokenDto.expireDate = DateUtil.toString(expireDate);
            return tokenDto;
        }
        throw new UnauthorizedException("Hatalı Giriş");
    }
}
