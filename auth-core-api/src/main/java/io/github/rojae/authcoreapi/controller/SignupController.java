package io.github.rojae.authcoreapi.controller;

import io.github.rojae.authcoreapi.common.enums.ApiCode;
import io.github.rojae.authcoreapi.dto.ApiBase;
import io.github.rojae.authcoreapi.dto.SignupRequest;
import io.github.rojae.authcoreapi.dto.SignupResponse;
import io.github.rojae.authcoreapi.service.SignupService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/api/v1/signup/newuser")
    @ApiOperation(value = "사용자 회원가입", notes = "회원가입 저장 API")
    public ResponseEntity<ApiBase<SignupResponse>> signupRequest(@Valid @RequestBody SignupRequest request){
        if(signupService.isDuplicate(request))
            return ResponseEntity.ok(new ApiBase<>(ApiCode.SIGNUP_DUPLICATE, new SignupResponse()));
        else if(signupService.signup(request))
            return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, new SignupResponse(request.getEmail(), request.getNickname(), request.getPlatformType())));
        else
            return ResponseEntity.ok(new ApiBase<>(ApiCode.SIGNUP_FAILED, new SignupResponse()));
    }
}
