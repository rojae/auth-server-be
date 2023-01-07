package io.github.rojae.socialapi.social.kakao;

import io.github.rojae.socialapi.common.http.HttpHeader;
import io.github.rojae.socialapi.common.http.RestProvider;
import io.github.rojae.socialapi.common.props.OAuth2Props;
import io.github.rojae.socialapi.social.kakao.dto.KakaoTokenWrapper;
import io.github.rojae.socialapi.social.kakao.dto.KakaoUserInfoWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoApiClient {

    private final RestProvider restProvider;
    private final OAuth2Props oAuth2Props;

    public ResponseEntity<KakaoTokenWrapper> getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("client_id", Collections.singletonList(oAuth2Props.kakaoClientId));
        params.put("client_secret", Collections.singletonList(oAuth2Props.kakaoClientSecret));
        params.put("redirect_uri", Collections.singletonList(oAuth2Props.kakaoRedirectUri));
        params.put("grant_type", Collections.singletonList(oAuth2Props.kakaoAuthGrantType));
        params.put("code", Collections.singletonList(code));

        return restProvider.send(HttpMethod.POST, oAuth2Props.kakaoTokenUri, params,
                new HttpHeader(MediaType.APPLICATION_FORM_URLENCODED), KakaoTokenWrapper.class);
    }

    public ResponseEntity<KakaoUserInfoWrapper> getKakaoUserInfo(String accessToken) {
        HttpHeader headers = new HttpHeader(MediaType.APPLICATION_FORM_URLENCODED);
        headers.addHeader("Authorization", String.format("Bearer %s", accessToken));
        headers.addHeader("charset", "utf8");

        return restProvider.send(HttpMethod.POST, oAuth2Props.kakaoUserInfoUri, headers, KakaoUserInfoWrapper.class);
    }

    public void unLink(String kakaoAccessToken) throws Exception {
        HttpHeader headers = new HttpHeader(MediaType.APPLICATION_FORM_URLENCODED);
        headers.addHeader("Authorization", String.format("Bearer %s", kakaoAccessToken));
        headers.addHeader("charset", "utf8");

        ResponseEntity<Map> response = restProvider.send(HttpMethod.POST, oAuth2Props.kakaoUnlinkUri, headers, Map.class);
        log.debug(String.format("KAKAO UNLINK :: RESPONSE => %s", response));
    }
}
