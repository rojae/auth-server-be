package io.github.rojae.authunionapi.api;

import io.github.rojae.authunionapi.common.exception.OAuth2ApiException;
import io.github.rojae.authunionapi.common.props.JwtProps;
import io.github.rojae.authunionapi.common.props.UrlProps;
import kr.github.rojae.data.common.ApiBase;
import kr.github.rojae.data.io.oauth2.OAuth2ProfileInfoResponse;
import kr.github.rojae.data.io.oauth2.OAuth2TokenPublishRequest;
import kr.github.rojae.data.io.oauth2.OAuth2TokenPublishResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2ApiClient {

    private final WebClient webClient;
    private final UrlProps urlProps;

    public Mono<ApiBase<OAuth2TokenPublishResponse>> tokenPublish(OAuth2TokenPublishRequest request){
        return webClient.post()
                .uri(urlProps.oauth2Api + urlProps.oauth2ApiTokenPublishUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<OAuth2TokenPublishResponse>>() {})
                .onErrorResume(s -> Mono.error(new OAuth2ApiException(String.format("%s (%s)", "토큰 발급에 실패했습니다.", s.getMessage()), urlProps.oauth2ApiTokenPublishUrl)));
    }

    public Mono<Void> tokenDrop(OAuth2TokenPublishRequest request){
        return webClient.post()
                .uri(urlProps.oauth2Api + urlProps.oauth2ApiTokenDropUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Void>() {})
                .onErrorResume(s -> Mono.error(new OAuth2ApiException(String.format("%s (%s)", "토큰 제거에 실패했습니다.", s.getMessage()), urlProps.oauth2ApiTokenDropUrl)));
    }

    public Mono<ApiBase<OAuth2ProfileInfoResponse>> getDetail(String token){
        return webClient.get()
                .uri(urlProps.oauth2Api + urlProps.oauth2ApiTokenDetailUrl)
                .header(JwtProps.AUTHORIZATION_HEADER, token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<OAuth2ProfileInfoResponse>>() {})
                .onErrorResume(s -> Mono.error(new OAuth2ApiException(String.format("%s (%s)", "토큰 정보 상세 조회에 실패했습니다.", s.getMessage()), urlProps.oauth2ApiTokenDetailUrl)));
    }

}
