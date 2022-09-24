package io.github.rojae.authserver;

import io.github.rojae.authserver.common.props.JwtProps;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.login.nonsocial.NonSocialLoginService;
import io.github.rojae.authserver.oauth.login.nonsocial.ServiceLoginRequest;
import io.github.rojae.authserver.oauth.logout.LogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OAuth2Controller {

    private final NonSocialLoginService nonSocialLoginService;
    private final LogoutService logoutService;
    private final JwtProps jwtProps;

    public OAuth2Controller(NonSocialLoginService nonSocialLoginService, LogoutService logoutService, JwtProps jwtProps) {
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
    @PostMapping("/login/oauth2/nonsocial")
    public ResponseEntity<OAuth2LoginResponse> nonSocialLogin(@RequestBody ServiceLoginRequest request) {
        OAuth2LoginResponse response = nonSocialLoginService.login(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @methodName: logOut
     * @author: rojae
     * @date: 2022/09/24
     * @Description: 로그아웃 API
     */
    @GetMapping("/login/oauth2/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest){
        if(logoutService.logout(httpServletRequest.getHeader(jwtProps.jwtHeaderName)))
            return new ResponseEntity<>("logout Complete", HttpStatus.OK);
        else
            return new ResponseEntity<>("logout Failed", HttpStatus.FORBIDDEN);
    }
}
