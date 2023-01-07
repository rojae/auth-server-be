package io.github.rojae.socialapi.controller;

import io.github.rojae.socialapi.dto.*;
import io.github.rojae.socialapi.enums.ApiCode;
import io.github.rojae.socialapi.service.ClientInfoService;
import io.github.rojae.socialapi.service.KakaoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class SocialController {

    private final ClientInfoService clientInfoService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "소셜 요청 정보 API", notes = "소셜 요청 정보 API")
    @GetMapping("/api/v1/social/client-info")
    public ResponseEntity<ApiBase<ClientInfoResponse>> clientInfo(@RequestParam(value = "platformType") ClientInfoRequest request){
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, clientInfoService.getClientInfo(request)));
    }

    ///// KAKAO /////
    @ApiOperation(value = "카카오 로그인 처리 API", notes = "카카오 로그인 처리 API")
    @GetMapping("/api/v1/social/kakao-callback")
    public ResponseEntity<ApiBase<LoginResponse>> kakaoLogin(@RequestParam(value = "code") @NotBlank(message = "code cannot be empty value") String code){
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, kakaoService.login(code)));
    }


    ///// NAVER /////
    // TODO :: Not implement, naver social login
}
