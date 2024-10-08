package io.github.rojae.authunionapi.api;

import io.github.rojae.authunionapi.common.exception.SocialApiException;
import io.github.rojae.authunionapi.common.props.UrlProps;
import kr.github.rojae.data.common.ApiBase;
import kr.github.rojae.data.io.social.SocialApiClientInfoRequest;
import kr.github.rojae.data.io.social.SocialApiClientInfoResponse;
import kr.github.rojae.data.io.social.SocialApiLoginResponse;
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
public class SocialApiClient {

    private final WebClient webClient;
    private final UrlProps urlProps;

    public Mono<ApiBase<SocialApiClientInfoResponse>> clientInfo(SocialApiClientInfoRequest request) {
        var url = UriComponentsBuilder.fromHttpUrl(urlProps.socialApi + urlProps.socialApiClientInfoUrl)
                .queryParam("platformType", request.getPlatformType())
                .buildAndExpand().toString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<SocialApiClientInfoResponse>>() {})
                .onErrorResume(s -> Mono.error(new SocialApiException(String.format("%s (%s)", "소셜 클라이언트 정보를 가져오지 못했습니다.", s.getMessage()), url)));
    }

    public Mono<ApiBase<SocialApiLoginResponse>> login(String code) {
        var url = UriComponentsBuilder.fromHttpUrl(urlProps.socialApi + urlProps.socialApiLoginUrl)
                .queryParam("code", code)
                .buildAndExpand().toString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<SocialApiLoginResponse>>() {})
                .onErrorResume(s -> Mono.error(new SocialApiException(String.format("%s (%s)", "소셜 로그인을 위한 통신에 실패했습니다.", s.getMessage()), url)));
    }

}
