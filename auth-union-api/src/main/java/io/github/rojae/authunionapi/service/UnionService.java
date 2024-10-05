package io.github.rojae.authunionapi.service;

import io.github.rojae.authunionapi.api.CoreApiClient;
import io.github.rojae.authunionapi.api.OAuth2ApiClient;
import io.github.rojae.authunionapi.api.SmtpApiClient;
import io.github.rojae.authunionapi.api.SocialApiClient;
import io.github.rojae.authunionapi.aspect.LogExecutionTime;
import kr.github.rojae.data.common.ApiBase;
import kr.github.rojae.data.common.ApiCode;
import kr.github.rojae.data.enums.MailType;
import kr.github.rojae.data.enums.PlatformType;
import kr.github.rojae.data.io.core.CoreApiCheckExistUserRequest;
import kr.github.rojae.data.io.core.CoreApiLoginHistoryRequest;
import kr.github.rojae.data.io.core.CoreApiLoginRequest;
import kr.github.rojae.data.io.core.CoreApiLoginResponse;
import kr.github.rojae.data.io.core.CoreApiProfileInfoRequest;
import kr.github.rojae.data.io.core.CoreApiSignupRequest;
import kr.github.rojae.data.io.core.CoreApiSignupResponse;
import kr.github.rojae.data.io.oauth2.OAuth2TokenPublishRequest;
import kr.github.rojae.data.io.overmind.CheckExistUserRequest;
import kr.github.rojae.data.io.overmind.ClientInfoResponse;
import kr.github.rojae.data.io.overmind.LoginResponse;
import kr.github.rojae.data.io.overmind.NonSocialLoginRequest;
import kr.github.rojae.data.io.overmind.ProfileInfoResponse;
import kr.github.rojae.data.io.overmind.SignupRequest;
import kr.github.rojae.data.io.overmind.SignupResponse;
import kr.github.rojae.data.io.smtp.MailRequestDto;
import kr.github.rojae.data.io.social.SocialApiClientInfoRequest;
import kr.github.rojae.data.io.social.SocialApiClientInfoResponse;
import kr.github.rojae.data.io.social.SocialApiLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnionService {

    private final CoreApiClient coreApiClient;
    private final OAuth2ApiClient oAuth2ApiClient;
    private final SocialApiClient socialApiClient;
    private final SmtpApiClient smtpApiClient;

    @LogExecutionTime
    public Mono<ApiBase<SignupResponse>> signup(SignupRequest request) {
        Mono<ApiBase<CoreApiSignupResponse>> coreResponse = coreApiClient.signup(new CoreApiSignupRequest(
                request.getName(), request.getEmail(), request.getPassword(), request.getNickname(), request.getPlatformType(), request.getProfileImage(),
                request.getBirthDate(), request.getGender(), request.getMobileTel1(), request.getMobileTel2(), request.getMobileTel3(),
                request.getAgreePersonalInfo(), request.getAgreeAdult(), request.getAgreeRecvMail(), request.getAgreeRecvSms()
        ));


        return coreResponse.flatMap(r -> {
            // 가입완료 메일 전송
            smtpApiClient.sendWelcomeMail(new MailRequestDto(r.getData().getEmail(), MailType.WELCOME.name())).subscribe();

            SignupResponse signupResponse = new SignupResponse(r.getData().getEmail(), r.getData().getName(), r.getData().getPlatformType());
            return Mono.just(new ApiBase<SignupResponse>().setResponse(r.getCode(), r.getReason(), signupResponse));
        });
    }

    @LogExecutionTime
    public Mono<ApiBase<ClientInfoResponse>> clientInfo(String platformType) {
        Mono<ApiBase<SocialApiClientInfoResponse>> socialResponse = socialApiClient.clientInfo(new SocialApiClientInfoRequest(platformType));
        return socialResponse.flatMap(r -> {
            ClientInfoResponse clientInfoResponse = new ClientInfoResponse(r.getData().getUri(), r.getData().getClientId(), r.getData().getRedirectUri(), r.getData().getResponseType(), r.getData().getTotal());
            return Mono.just(new ApiBase<ClientInfoResponse>().setResponse(r.getCode(), r.getReason(), clientInfoResponse));
        });
    }

    @LogExecutionTime
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

    @LogExecutionTime
    public Mono<ApiBase<LoginResponse>> kakaoLogin(String code) {
        // 1. Social API를 통한 로그인/가입 처리
        Mono<ApiBase<SocialApiLoginResponse>> socialResponse = socialApiClient.login(code);

        return socialResponse.flatMap(r -> {
            if (!ApiCode.ofCode(r.getCode()).equals(ApiCode.OK)) {
                return Mono.just(new ApiBase<>(ApiCode.LOGIN_ACCOUNT_INVALID));
            } else {
                return coreApiClient.isExist(new CoreApiCheckExistUserRequest(r.getData().getEmail(), PlatformType.KAKAO.name())).flatMap(o -> {
                    // 2. Core API를 통한, 존재하지 않는 회원의 경우 회원가입 처리
                    if (ApiCode.ofCode(o.getCode()) != ApiCode.OK){
                        coreApiClient.signup(new CoreApiSignupRequest(r.getData().getEmail(), UUID.randomUUID().toString(), r.getData().getName(), PlatformType.KAKAO.name(), r.getData().getProfileImage())).subscribe();

                        // 가입완료 메일 전송
                        smtpApiClient.sendWelcomeMail(new MailRequestDto(r.getData().getEmail(), MailType.WELCOME.name())).subscribe();
                    }

                    // 3. OAuth2 API를 통한, 토큰 발급 처리
                    return oAuth2ApiClient.tokenPublish(new OAuth2TokenPublishRequest(r.getData().getName(), PlatformType.KAKAO.name(), r.getData().getEmail(), UUID.randomUUID().toString()))
                        .flatMap(oo -> Mono.just(new ApiBase<>(ApiCode.OK, new LoginResponse(r.getData().getName(), PlatformType.KAKAO.name(), r.getData().getEmail(), r.getData().getProfileImage(), oo.getData().getHeaderValue()))));
                });
            }
        });
    }

    @LogExecutionTime
    public void loginHistory(LoginResponse loginResponse){
        // 소셜 로그인에 성공했다면, 히스토리 저장
        coreApiClient.loginHistory(new CoreApiLoginHistoryRequest(loginResponse.getEmail(), loginResponse.getPlatformType())).subscribe();
    }

    @LogExecutionTime
    public Mono<ApiBase<ProfileInfoResponse>> myProfile(String token) {
        // 1. oauth2 api를 통한 사용자 데이터 가져오기 -> (이메일, 플랫폼)
        return oAuth2ApiClient.getDetail(token)
                .flatMap(d -> {
                    // 2. core api를 통한, 프로필 데이터 가져오기
                    if(ApiCode.OK == ApiCode.ofCode(d.getCode())) {
                        return coreApiClient.profile(new CoreApiProfileInfoRequest(d.getData().getEmail(), d.getData().getPlatformType()))
                                .flatMap(dd -> Mono.just(new ApiBase<>(ApiCode.ofCode(dd.getCode()), new ProfileInfoResponse(dd.getData().getName(), dd.getData().getEmail(), dd.getData().getPlatformType(), dd.getData().getProfileImage(), dd.getData().isAuth(), dd.getData().isEnable(), dd.getData().getLastLoginDate()))));
                    }
                    else {
                        return Mono.just(new ApiBase<>(ApiCode.ofCode(d.getCode()), new ProfileInfoResponse()));
                    }
                });
    }

    @LogExecutionTime
    public Mono<ApiBase<Object>> isExistUser(CheckExistUserRequest request) {
        return coreApiClient.isExist(new CoreApiCheckExistUserRequest(request.getEmail(), request.getPlatformType()));
    }
}
