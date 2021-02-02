package tr.com.kafein.uaaserver.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;
import tr.com.kafein.uaaserver.util.Constants;
import tr.com.kafein.uaaserver.util.DateUtil;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;

import java.util.Date;

import static tr.com.kafein.uaaserver.util.Constants.UNAUTHORIZED_MSG;

@Service
public class AuthenticationService {

    private final CustomAuthenticationManager customAuthenticationManager;

    public AuthenticationService(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    public AccessTokenDto getToken(String userName, String password) {
        Authentication authentication = customAuthenticationManager.authenticate(userName, password);

        if (authentication.getPrincipal() != null) {
            Date expireDate = new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME);
            String token = Jwts.builder()
                    .setSubject(userName)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)
                    .compact();

            AccessTokenDto tokenDto = new AccessTokenDto();
            tokenDto.setToken(token);
            tokenDto.setExpireDate(DateUtil.toString(expireDate));
            return tokenDto;
        } else {
            throw new UnauthorizedException(UNAUTHORIZED_MSG);
        }
    }
}
