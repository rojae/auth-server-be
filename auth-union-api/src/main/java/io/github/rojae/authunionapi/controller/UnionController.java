package io.github.rojae.authunionapi.controller;

import io.github.rojae.authunionapi.common.props.JwtProps;
import io.github.rojae.authunionapi.service.UnionService;
import kr.github.rojae.data.common.ApiBase;
import kr.github.rojae.data.io.overmind.CheckExistUserRequest;
import kr.github.rojae.data.io.overmind.ClientInfoResponse;
import kr.github.rojae.data.io.overmind.LoginResponse;
import kr.github.rojae.data.io.overmind.NonSocialLoginRequest;
import kr.github.rojae.data.io.overmind.ProfileInfoResponse;
import kr.github.rojae.data.io.overmind.SignupRequest;
import kr.github.rojae.data.io.overmind.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UnionController {

    private final UnionService unionService;

    @GetMapping("/api/v1/check/exist-user")
    public Mono<ResponseEntity<ApiBase<Object>>> isExistUser(@Valid @RequestBody CheckExistUserRequest request){
        return unionService.isExistUser(request).map(ResponseEntity::ok);
    }

    @PostMapping("/api/v1/auth/signup/newuser")
    public Mono<ResponseEntity<ApiBase<SignupResponse>>> signup(@Valid @RequestBody SignupRequest request){
        return unionService.signup(request).map(ResponseEntity::ok);
    }

    @GetMapping("/api/v1/auth/client-info")
    public Mono<ResponseEntity<ApiBase<ClientInfoResponse>>> clientInfo(@Valid @RequestParam(value = "platformType") String platformType){
        return unionService.clientInfo(platformType).map(ResponseEntity::ok);
    }

    @PostMapping("/api/v1/auth/login/nonsocial")
    public Mono<ResponseEntity<ApiBase<LoginResponse>>> nonSocialLogin(@Valid @RequestBody NonSocialLoginRequest request){
        return unionService.nonSocialLogin(request).map(ResponseEntity::ok);
    }

    @GetMapping("/api/v1/auth/login/social/kakao")
    public Mono<ResponseEntity<ApiBase<LoginResponse>>> kakaoLogin(@Valid @RequestParam(value = "code") @NotBlank(message = "code cannot be empty value") String code) {
        return unionService.kakaoLogin(code).map(ResponseEntity::ok)
                .doOnNext(r -> unionService.loginHistory(Objects.requireNonNull(r.getBody()).getData()));
    }

    @GetMapping("/api/v1/my-profile")
    public Mono<ResponseEntity<ApiBase<ProfileInfoResponse>>> myProfile(@RequestHeader(value = JwtProps.AUTHORIZATION_HEADER)
                                                                            @NotBlank(message = "Authorization can not be empty")
                                                                            String token){
        return unionService.myProfile(token).map(ResponseEntity::ok);
    }
}
