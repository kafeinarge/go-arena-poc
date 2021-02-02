package tr.com.kafein.uaaserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.com.kafein.uaaserver.dto.AccessTokenDto;
import tr.com.kafein.uaaserver.dto.LoginRequestDto;
import tr.com.kafein.uaaserver.service.AuthenticationService;

@RestController
public class TokenController {
    private final AuthenticationService authenticationService;

    public TokenController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/auth/login")
    public AccessTokenDto getToken(@RequestBody LoginRequestDto request) {
        return authenticationService.getToken(request.getUsername(), request.getPassword());
    }
}
