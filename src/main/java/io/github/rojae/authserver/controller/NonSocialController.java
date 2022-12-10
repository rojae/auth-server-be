package io.github.rojae.authserver.controller;

import io.github.rojae.authserver.common.props.JwtProps;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.login.nonsocial.NonSocialLoginService;
import io.github.rojae.authserver.dto.ServiceLoginRequest;
import io.github.rojae.authserver.oauth.logout.LogoutService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
public class NonSocialController {
    private final NonSocialLoginService nonSocialLoginService;
    private final LogoutService logoutService;
    private final JwtProps jwtProps;

    public NonSocialController(NonSocialLoginService nonSocialLoginService, LogoutService logoutService, JwtProps jwtProps) {
        this.nonSocialLoginService = nonSocialLoginService;
        this.logoutService = logoutService;
        this.jwtProps = jwtProps;
    }

    /**
     * @method : nonSocialLogin
     * @description : NOT Social Login, Service's Account Login
     * @author: rojae
     * @date : 2022/08/13
     **/
    @PostMapping("/login/oauth2/nonsocial/login")
    public ResponseEntity<OAuth2LoginResponse> nonSocialLogin(@RequestBody ServiceLoginRequest request) {
        OAuth2LoginResponse response = nonSocialLoginService.login(request.getEmail(), request.getPassword(),
            UUID.randomUUID().toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @methodName: logOut
     * @author: rojae
     * @date: 2022/09/24
     * @Description: 로그아웃 API
     */
    @GetMapping("/login/oauth2/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(value = JwtProps.AUTHORIZATION_HEADER)
            @NotBlank(message = "Authorization can not be empty")
            String token)
    {
        if (logoutService.logout(token))
            return new ResponseEntity<>("logout Complete", HttpStatus.OK);
        else
            return new ResponseEntity<>("logout Failed", HttpStatus.FORBIDDEN);
    }
}
