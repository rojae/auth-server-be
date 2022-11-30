package io.github.rojae.authserver.controller;

import io.github.rojae.authserver.common.props.JwtProps;
import io.github.rojae.authserver.common.props.OAuth2Props;
import io.github.rojae.authserver.dto.KakaoClientInfoResponse;
import io.github.rojae.authserver.dto.KakaoLogoutInfoResponse;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.login.social.kakao.KakaoService;
import io.github.rojae.authserver.oauth.logout.LogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoSocialController {

    private final KakaoService kakaoService;
    private final LogoutService logoutService;
    private final JwtProps jwtProps;
    private final OAuth2Props oAuth2Props;

    public KakaoSocialController(KakaoService kakaoService, LogoutService logoutService, JwtProps jwtProps, OAuth2Props oAuth2Props) {
        this.kakaoService = kakaoService;
        this.logoutService = logoutService;
        this.jwtProps = jwtProps;
        this.oAuth2Props = oAuth2Props;
    }

    // 카카오 브라우저 로그인을 위한, 카카오 인증서버 호출 정보
    @GetMapping("/login/oauth2/social/kakao-info")
    public ResponseEntity<KakaoClientInfoResponse> kakaoClientInfo() {
        KakaoClientInfoResponse response = new KakaoClientInfoResponse(
                oAuth2Props.kakaoAuthUri,
                oAuth2Props.kakaoClientId,
                oAuth2Props.kakaoRedirectUri,
                oAuth2Props.responseType,
                String.format("%s?client_id=%s&redirect_uri=%s&response_type=%s",
                        oAuth2Props.kakaoAuthUri,
                        oAuth2Props.kakaoClientId,
                        oAuth2Props.kakaoRedirectUri,
                        oAuth2Props.responseType
                )
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 카카오 브라우저 로그인 이후, 카카오 인증서버에서 전송되는 API
    @GetMapping("/login/oauth2/social/kakao")
    public ResponseEntity<OAuth2LoginResponse> login(@RequestParam(value = "code", required = true) String code){
        OAuth2LoginResponse response = kakaoService.login(code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 카카오 브라우저 로그아웃을 위해서는, 브라우저 이동이 필요. 호출 정보 API
    @GetMapping("/login/oauth2/kakao/logout")
    public ResponseEntity<KakaoLogoutInfoResponse> logout(){
        KakaoLogoutInfoResponse response = new KakaoLogoutInfoResponse(oAuth2Props.logoutUri,
                oAuth2Props.kakaoClientId,
                jwtProps.logoutUri,
                String.format("%s?client_id=%s&logout_redirect_uri=%s",
                        oAuth2Props.logoutUri,
                        oAuth2Props.kakaoClientId,
                        jwtProps.logoutUri
                )
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
