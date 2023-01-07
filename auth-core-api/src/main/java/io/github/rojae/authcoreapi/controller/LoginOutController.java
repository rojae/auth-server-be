package io.github.rojae.authcoreapi.controller;

import io.github.rojae.authcoreapi.common.enums.ApiCode;
import io.github.rojae.authcoreapi.dto.ApiBase;
import io.github.rojae.authcoreapi.dto.LoginRequest;
import io.github.rojae.authcoreapi.dto.LoginResponse;
import io.github.rojae.authcoreapi.dto.SignupResponse;
import io.github.rojae.authcoreapi.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginOutController {

    private final LoginService loginService;

    @PostMapping("/api/v1/login/oauth2")
    @ApiOperation(value = "사용자 계정 조회 (로그인)", notes = "email, password, platformType을 통해서 조회합니다.")
    public ResponseEntity<ApiBase<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = loginService.login(request);
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, response));
    }

    @GetMapping("/api/v1/login/oauth2/logout")
    @ApiOperation(value = "사용자 로그아웃 (로그아웃)", notes = "로그아웃 시간을 갱신하며, 후속처리를 진행합니다.")
    public String logout(){
        return "logout ok";
    }

}
