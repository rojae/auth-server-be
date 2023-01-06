package io.github.rojae.authcoreapi.controller;

import io.github.rojae.authcoreapi.common.enums.ApiCode;
import io.github.rojae.authcoreapi.dto.ApiBase;
import io.github.rojae.authcoreapi.dto.LoginRequest;
import io.github.rojae.authcoreapi.dto.LoginResponse;
import io.github.rojae.authcoreapi.dto.SignupResponse;
import io.github.rojae.authcoreapi.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginOutController {

    private final LoginService loginService;

    @PostMapping("/login/oauth2")
    public ResponseEntity<ApiBase<LoginResponse>> login(@RequestBody LoginRequest request){
        LoginResponse response = loginService.login(request);
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, response));
    }

    @GetMapping("/login/oauth2/logout")
    public String logout(){
        return "logout ok";
    }

}
