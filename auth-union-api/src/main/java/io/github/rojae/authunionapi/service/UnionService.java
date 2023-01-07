package io.github.rojae.authunionapi.service;

import io.github.rojae.authunionapi.api.CoreApiClient;
import io.github.rojae.authunionapi.api.OAuth2ApiClient;
import io.github.rojae.authunionapi.api.SocialApiClient;
import io.github.rojae.authunionapi.api.coreapi.dto.*;
import io.github.rojae.authunionapi.api.oauth2api.dto.OAuth2TokenPublishRequest;
import io.github.rojae.authunionapi.api.oauth2api.dto.OAuth2TokenPublishResponse;
import io.github.rojae.authunionapi.api.socialapi.dto.SocialApiClientInfoRequest;
import io.github.rojae.authunionapi.api.socialapi.dto.SocialApiClientInfoResponse;
import io.github.rojae.authunionapi.api.socialapi.dto.SocialApiLoginResponse;
import io.github.rojae.authunionapi.common.enums.ApiCode;
import io.github.rojae.authunionapi.common.enums.PlatformType;
import io.github.rojae.authunionapi.common.valid.PlatformTypeValid;
import io.github.rojae.authunionapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnionService {

    private final CoreApiClient coreApiClient;
    private final OAuth2ApiClient oAuth2ApiClient;
    private final SocialApiClient socialApiClient;

    public Mono<ApiBase<SignupResponse>> signup(SignupRequest request) {
        Mono<ApiBase<CoreApiSignupResponse>> coreResponse = coreApiClient.signup(new CoreApiSignupRequest(request.getEmail(), request.getPassword(), request.getName(), request.getPlatformType(), request.getProfileImage()));
        return coreResponse.flatMap(r -> {
            SignupResponse signupResponse = new SignupResponse(r.getData().getEmail(), r.getData().getName(), r.getData().getPlatformType());
            return Mono.just(new ApiBase<SignupResponse>().setResponse(r.getCode(), r.getReason(), signupResponse));
        });
    }

    public Mono<ApiBase<ClientInfoResponse>> clientInfo(ClientInfoRequest request) {
        Mono<ApiBase<SocialApiClientInfoResponse>> socialResponse = socialApiClient.clientInfo(new SocialApiClientInfoRequest(request.getPlatformType()));
        return socialResponse.flatMap(r -> {
            ClientInfoResponse clientInfoResponse = new ClientInfoResponse(r.getData().getUri(), r.getData().getClientId(), r.getData().getRedirectUri(), r.getData().getResponseType(), r.getData().getTotal());
            return Mono.just(new ApiBase<ClientInfoResponse>().setResponse(r.getCode(), r.getReason(), clientInfoResponse));
        });
    }

    public Mono<ApiBase<LoginResponse>> nonSocialLogin(NonSocialLoginRequest request) {
        // 1. CORE API를 통한 RDB 조회
        Mono<ApiBase<CoreApiLoginResponse>> coreResponse = coreApiClient.login(new CoreApiLoginRequest(request.getEmail(), request.getPassword(), PlatformType.NONSOCIAL.name()));

        return coreResponse.flatMap(r -> {
            if (!ApiCode.ofCode(r.getCode()).equals(ApiCode.OK)) {
                return Mono.just(new ApiBase<>(ApiCode.LOGIN_ACCOUNT_INVALID));
            } else {
                // 2. OAuth2 API를 통한 토큰 발급
                return oAuth2ApiClient.tokenPublish(new OAuth2TokenPublishRequest(r.getData().getName(), r.getData().getPlatformType(), r.getData().getEmail(), UUID.randomUUID().toString()))
                        .flatMap(o -> Mono.just(new ApiBase<>(ApiCode.OK, new LoginResponse(r.getData().getName(), r.getData().getPlatformType(), r.getData().getEmail(), r.getData().getProfileImage(), o.getData().getHeaderValue()))));
            }
        });
    }

    public Mono<ApiBase<LoginResponse>> kakaoLogin(String code) {
        // 1. Social API를 통한 로그인/가입 처리
        Mono<ApiBase<SocialApiLoginResponse>> socialResponse = socialApiClient.login(code);

        return socialResponse.flatMap(r -> {
            if (!ApiCode.ofCode(r.getCode()).equals(ApiCode.OK)) {
                return Mono.just(new ApiBase<>(ApiCode.LOGIN_ACCOUNT_INVALID));
            } else {
                return coreApiClient.isExist(new CoreApiCheckExistUserRequest(r.getData().getEmail(), PlatformType.KAKAO.name())).flatMap(o -> {
                    // 2. Core API를 통한, 존재하지 않는 회원의 경우 회원가입 처리
                    if (ApiCode.ofCode(o.getCode()) != ApiCode.OK)
                        coreApiClient.signup(new CoreApiSignupRequest(r.getData().getEmail(), UUID.randomUUID().toString(), r.getData().getName(), PlatformType.KAKAO.name(), r.getData().getProfileImage())).subscribe();

                    // 3. OAuth2 API를 통한, 토큰 발급 처리
                    return oAuth2ApiClient.tokenPublish(new OAuth2TokenPublishRequest(r.getData().getName(), PlatformType.KAKAO.name(), r.getData().getEmail(), UUID.randomUUID().toString()))
                        .flatMap(oo -> Mono.just(new ApiBase<>(ApiCode.OK, new LoginResponse(r.getData().getName(), PlatformType.KAKAO.name(), r.getData().getEmail(), r.getData().getProfileImage(), oo.getData().getHeaderValue()))));
                });
            }
        });
    }

}
