package tr.com.kafein.uaaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomAuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }

        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Hatalı Giriş");
        }

        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }
}