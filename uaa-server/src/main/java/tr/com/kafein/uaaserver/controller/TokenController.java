package tr.com.kafein.uaaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;
import tr.com.kafein.uaaserver.dto.LoginRequestDto;
import tr.com.kafein.uaaserver.service.AuthenticationService;

@RestController
@RequestMapping
public class TokenController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/auth/login")
    public AccessTokenDto getToken(@RequestBody LoginRequestDto request) {
        return authenticationService.getToken(request.username, request.password);
    }
}
