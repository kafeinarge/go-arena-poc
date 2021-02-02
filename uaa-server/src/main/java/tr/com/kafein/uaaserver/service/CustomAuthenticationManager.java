package tr.com.kafein.uaaserver.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.exception.UnauthorizedException;

import java.util.Collections;

import static tr.com.kafein.uaaserver.util.Constants.UNAUTHORIZED_MSG;

@Service
public class CustomAuthenticationManager {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    public CustomAuthenticationManager(UserDetailsService userDetailsService,
                                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = bCryptPasswordEncoder;
    }

    Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UnauthorizedException(username);
        }

        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new UnauthorizedException(UNAUTHORIZED_MSG);
        }

        return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    }
}
