package io.github.rojae.authserver.controller;

import io.github.rojae.authserver.common.props.JwtProps;
import io.github.rojae.authserver.common.props.OAuth2Props;
import io.github.rojae.authserver.dto.KakaoClientInfoResponse;
import io.github.rojae.authserver.oauth.login.social.kakao.KakaoService;
import io.github.rojae.authserver.oauth.logout.LogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoController {

    private final KakaoService kakaoService;
    private final LogoutService logoutService;
    private final JwtProps jwtProps;
    private final OAuth2Props oAuth2Props;

    public KakaoController(KakaoService kakaoService, LogoutService logoutService, JwtProps jwtProps, OAuth2Props oAuth2Props) {
        this.kakaoService = kakaoService;
        this.logoutService = logoutService;
        this.jwtProps = jwtProps;
        this.oAuth2Props = oAuth2Props;
    }

    @GetMapping("/login/oauth2/social/kakao-info")
    public ResponseEntity<KakaoClientInfoResponse> kakaoClientInfo() {
        return new ResponseEntity<>(new KakaoClientInfoResponse(oAuth2Props.kakaoClientId, oAuth2Props.kakaoRedirectUri, oAuth2Props.responseType), HttpStatus.OK);
    }
}
