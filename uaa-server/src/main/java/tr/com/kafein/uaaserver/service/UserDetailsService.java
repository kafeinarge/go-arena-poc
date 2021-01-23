package tr.com.kafein.uaaserver.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.accessor.UserServiceAccessor;
import tr.com.kafein.uaaserver.dto.SecurityUser;
import tr.com.kafein.uaaserver.dto.UserDto;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserServiceAccessor userServiceAccessor;

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = StringUtils.trim(login);

        UserDto userFromDatabase = userServiceAccessor.findByUsername(login);
        return Optional.ofNullable(userFromDatabase)
                .map(user -> {
                    SecurityUser securityUser = new SecurityUser(lowercaseLogin, userFromDatabase.password);
                    securityUser.setId(userFromDatabase.id);
                    securityUser.setName(userFromDatabase.name);
                    return securityUser;
                })
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }
}
