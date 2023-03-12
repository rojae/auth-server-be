package io.github.rojae.authunionapi.api;

import io.github.rojae.authunionapi.api.smtpapi.dto.MailRequestDto;
import io.github.rojae.authunionapi.common.exception.SmtpApiException;
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
public class SmtpApiClient {
    private final WebClient webClient;
    private final UrlProps urlProps;

    public Mono<ApiBase<Object>> sendWelcomeMail(MailRequestDto request){
        return webClient.post()
                .uri(urlProps.smtpApi + urlProps.welcomeMailUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiBase<Object>>() {})
                .onErrorResume(s -> Mono.error(new SmtpApiException(String.format("%s (%s)", "가입완료 메일 발송에 실패했습니다.", s.getMessage()), urlProps.welcomeMailUrl)));
    }
}
