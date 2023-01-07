package io.github.rojae.authunionapi.api;

import io.github.rojae.authunionapi.api.coreapi.dto.CoreApiSignupResponse;
import io.github.rojae.authunionapi.api.oauth2api.dto.OAuth2TokenPublishRequest;
import io.github.rojae.authunionapi.api.oauth2api.dto.OAuth2TokenPublishResponse;
import io.github.rojae.authunionapi.common.exception.CoreApiException;
import io.github.rojae.authunionapi.common.exception.OAuth2ApiException;
import io.github.rojae.authunionapi.common.props.UrlProps;
import io.github.rojae.authunionapi.dto.ApiBase;
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
                .uri(urlProps.oauth2ApiTokenPublishUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<OAuth2TokenPublishResponse>>() {})
                .onErrorResume(s -> Mono.error(new OAuth2ApiException(String.format("%s (%s)", "토큰 발급에 실패했습니다.", s.getMessage()), urlProps.oauth2ApiTokenPublishUrl)));
    }

    public Mono<Void> tokenDrop(OAuth2TokenPublishRequest request){
        return webClient.post()
                .uri(urlProps.oauth2ApiTokenDropUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Void>() {})
                .onErrorResume(s -> Mono.error(new OAuth2ApiException(String.format("%s (%s)", "토큰 제거에 실패했습니다.", s.getMessage()), urlProps.oauth2ApiTokenDropUrl)));
    }

}
