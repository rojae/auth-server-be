package io.github.rojae.authunionapi.api;

import io.github.rojae.authunionapi.api.coreapi.dto.*;
import io.github.rojae.authunionapi.common.props.UrlProps;
import io.github.rojae.authunionapi.dto.ApiBase;
import io.github.rojae.authunionapi.common.exception.CoreApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CoreApiClient {

    private final WebClient webClient;
    private final UrlProps urlProps;

    public Mono<ApiBase<CoreApiSignupResponse>> signup(CoreApiSignupRequest request){
        return webClient.post()
                .uri(urlProps.coreApi + urlProps.coreApiSignupUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<CoreApiSignupResponse>>() {})
                .onErrorResume(s -> Mono.error(new CoreApiException(String.format("%s (%s)", "회원가입을 위한 통신에 실패했습니다.", s.getMessage()), urlProps.coreApiSignupUrl)));
    }

    public Mono<ApiBase<CoreApiLoginResponse>> login(CoreApiLoginRequest request){
        return webClient.post()
                .uri(urlProps.coreApi + urlProps.coreApiLoginUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<CoreApiLoginResponse>>() {})
                .onErrorResume(s -> Mono.error(new CoreApiException(String.format("%s (%s)", "로그인을 위한 통신에 실패했습니다.", s.getMessage()), urlProps.coreApiLoginUrl)));
    }

    public Mono<ApiBase<Object>> isExist(CoreApiCheckExistUserRequest request){
        return webClient.post()
                .uri(urlProps.coreApi + urlProps.coreApiExistCheckUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<Object>>() {})
                .onErrorResume(s -> Mono.error(new CoreApiException(String.format("%s (%s)", "유저 조회에 실패했습니다.", s.getMessage()), urlProps.coreApiExistCheckUrl)));
    }

    public Mono<ApiBase<CoreApiProfileInfoResponse>> profile(CoreApiProfileInfoRequest request){
        var url = UriComponentsBuilder.fromHttpUrl(urlProps.coreApi + urlProps.coreApiProfileUrl)
                .queryParam("email", request.getEmail())
                .queryParam("platformType", request.getPlatformType())
                .buildAndExpand().toString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<CoreApiProfileInfoResponse>>() {})
                .onErrorResume(s -> Mono.error(new CoreApiException(String.format("%s (%s)", "유저 프로필 조회에 실패했습니다.", s.getMessage()), urlProps.coreApiProfileUrl)));
    }

}
