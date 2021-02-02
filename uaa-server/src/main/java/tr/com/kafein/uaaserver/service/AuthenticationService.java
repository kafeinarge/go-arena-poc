package tr.com.kafein.uaaserver.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.accessor.UserServiceAccessor;
import tr.com.kafein.uaaserver.dto.UserDto;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;
import tr.com.kafein.uaaserver.util.Constants;
import tr.com.kafein.uaaserver.util.DateUtil;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthenticationService {

    @Autowired
    private UserServiceAccessor userServiceAccessor;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AccessTokenDto getToken(String username, String password) {
        UserDto userFromDatabase = userServiceAccessor.findByUsername(username);

        if (userFromDatabase == null) {
            throw new UnauthorizedException(username);
        }

        if (!encoder.matches(password, userFromDatabase.password)) {
            throw new UnauthorizedException("Hatalı Giriş");
        }


        Date expireDate = new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME);
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)
                .compact();

        AccessTokenDto tokenDto = new AccessTokenDto();
        tokenDto.token = token;
        tokenDto.expireDate = DateUtil.toString(expireDate);
        tokenDto.userId = userFromDatabase.id;
        return tokenDto;

    }
}
