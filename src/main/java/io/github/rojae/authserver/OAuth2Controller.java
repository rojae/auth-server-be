package io.github.rojae.authserver;

import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.nonsocial.NonSocialLoginService;
import io.github.rojae.authserver.oauth.nonsocial.ServiceLoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    private final NonSocialLoginService nonSocialLoginService;

    public OAuth2Controller(NonSocialLoginService nonSocialLoginService) {
        this.nonSocialLoginService = nonSocialLoginService;
    }

    /**
     * @method : nonSocialLogin
     * @description : NOT Social Login, Service's Account Login
     * @author: rojae
     * @date : 2022/08/13
     **/
    @PostMapping("/login/oauth2/nonsocial")
    public ResponseEntity<OAuth2LoginResponse> nonSocialLogin(@RequestBody ServiceLoginRequest request) {
        OAuth2LoginResponse response = nonSocialLoginService.login(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
