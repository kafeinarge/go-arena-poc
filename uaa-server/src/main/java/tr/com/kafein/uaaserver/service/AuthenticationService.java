package tr.com.kafein.uaaserver.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.accessor.UserServiceAccessor;
import tr.com.kafein.uaaserver.dto.UserDto;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;
import tr.com.kafein.uaaserver.util.Constants;
import tr.com.kafein.uaaserver.util.DateUtil;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;

import java.util.Date;

import static tr.com.kafein.uaaserver.util.Constants.UNAUTHORIZED_MSG;

@Service
public class AuthenticationService {

    private final UserServiceAccessor userServiceAccessor;
    private final BCryptPasswordEncoder encoder;

    public AuthenticationService(UserServiceAccessor userServiceAccessor,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userServiceAccessor = userServiceAccessor;
        this.encoder = bCryptPasswordEncoder;
    }

    public AccessTokenDto getToken(String username, String password) {
        UserDto userFromDatabase = userServiceAccessor.findByUsername(username);

        if (userFromDatabase == null) {
            throw new UnauthorizedException(username);
        }

        if (!encoder.matches(password, userFromDatabase.getPassword())) {
            throw new UnauthorizedException(UNAUTHORIZED_MSG);
        }
        Date expireDate = new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME);
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET)
                .compact();

        AccessTokenDto tokenDto = new AccessTokenDto();
        tokenDto.setToken(token);
        tokenDto.setExpireDate(DateUtil.toString(expireDate));
        tokenDto.setUserId(userFromDatabase.getId());
        return tokenDto;
    }
}
