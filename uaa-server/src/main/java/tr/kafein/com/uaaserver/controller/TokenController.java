package tr.kafein.com.uaaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.kafein.com.uaaserver.dto.AccessTokenDto;
import tr.kafein.com.uaaserver.service.AuthenticationService;

@RestController
@RequestMapping("/uaa")
public class TokenController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token")
    public AccessTokenDto getToken(String username, String password) {
        return authenticationService.getToken(username, password);
    }
}
