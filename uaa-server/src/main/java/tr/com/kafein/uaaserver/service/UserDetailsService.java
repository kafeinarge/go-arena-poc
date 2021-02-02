package tr.com.kafein.uaaserver.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.kafein.uaaserver.accessor.UserServiceAccessor;
import tr.com.kafein.uaaserver.dto.SecurityUser;
import tr.com.kafein.uaaserver.dto.UserDto;

import java.util.Optional;

import static tr.com.kafein.uaaserver.util.Constants.INVALID_USERNAME_MSG;

@Slf4j
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserServiceAccessor userServiceAccessor;

    public UserDetailsService(UserServiceAccessor userServiceAccessor) {
        this.userServiceAccessor = userServiceAccessor;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String username = StringUtils.trim(login);

        UserDto userFromDatabase = userServiceAccessor.findByUsername(username);
        return Optional.ofNullable(userFromDatabase)
                .map(user -> {
                    SecurityUser securityUser = new SecurityUser(username, userFromDatabase.getPassword());
                    securityUser.setId(userFromDatabase.getId());
                    securityUser.setName(userFromDatabase.getName());
                    return securityUser;
                })
                .orElseThrow(() -> new UsernameNotFoundException(String.format(INVALID_USERNAME_MSG, username)));
    }
}
