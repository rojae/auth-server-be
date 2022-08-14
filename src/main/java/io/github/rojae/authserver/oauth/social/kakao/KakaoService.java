package io.github.rojae.authserver.oauth.social.kakao;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.common.http.HttpHeader;
import io.github.rojae.authserver.common.http.RestProvider;
import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.common.props.OAuth2Props;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.OAuth2LoginResponseBuilder;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.github.rojae.authserver.oauth.social.SocialLoginServiceImpl;
import io.github.rojae.authserver.persistence.AccountRedisRepository;
import io.github.rojae.authserver.persistence.AccountRepository;
import io.github.rojae.authserver.util.TimeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class KakaoService extends SocialLoginServiceImpl {

    Logger logger = Logger.getLogger(KakaoService.class.getName());

    private final RestProvider restProvider;
    private final JwtProvider jwtProvider;
    private final OAuth2Props oAuth2Props;

    public KakaoService(JwtProvider jwtProvider, OAuth2Props oAuth2Props, AccountRepository accountRepository, AccountRedisRepository accountRedisRepository, RestProvider restProvider, JwtProvider jwtProvider1, OAuth2Props oAuth2Props1) {
        super(jwtProvider, oAuth2Props, accountRepository, accountRedisRepository);
        this.restProvider = restProvider;
        this.jwtProvider = jwtProvider1;
        this.oAuth2Props = oAuth2Props1;
    }

    public OAuth2Principal principal(KakaoUserInfoWrapper userInfoResponse) {
        OAuth2Principal oAuth2Principal = new OAuth2Principal();
        oAuth2Principal.setName(userInfoResponse.kakao_account.profile.nickname);
        oAuth2Principal.setEmail(userInfoResponse.kakao_account.email);
        oAuth2Principal.setPlatformType(PlatformType.KAKAO);
        oAuth2Principal.setProfileImage(userInfoResponse.kakao_account.profile.profile_image_url);  // 640 X 640
        return oAuth2Principal;
    }

    public OAuth2LoginResponse response(KakaoUserInfoWrapper userInfoResponse, String token) {
        return new OAuth2LoginResponseBuilder()
                .setName(userInfoResponse.kakao_account.profile.nickname)
                .setEmail(userInfoResponse.kakao_account.email)
                .setPlatformType(PlatformType.KAKAO)
                .setProfileImage(userInfoResponse.kakao_account.profile.profile_image_url)       // 640 X 640
                .setToken(token)
                .setExpireTime(TimeUtils.dateFomat(jwtProvider.getExpiration(token), "yyyy.MM.dd HH:mm:ss"))
                .createOAuth2LoginResponse();
    }

    @Override
    public OAuth2LoginResponse login(String code) {
        logger.info(String.format("START KAKAO LOGIN :: CODE => %s", code));

        // STEP 1 : KAKAO TOKEN
        ResponseEntity<KakaoTokenWrapper> tokenResponse = this.getKakaoToken(code);
        logger.info(String.format("STEP 1 : KAKAO TOKEN => %s", tokenResponse.getBody()));

        // STEP 2 : KAKAO USER INFO
        ResponseEntity<KakaoUserInfoWrapper> userInfoResponse =
                this.getKakaoUserInfo(Objects.requireNonNull(tokenResponse.getBody()).getAccess_token());
        logger.info(String.format("STEP 2 : KAKAO USER INFO => %s", userInfoResponse.getBody()));

        // STEP 3 : Generate JWT Token including User's Info
        logger.info("STEP 3 : KAKAO JWT Login, Signup");

        // Create User's Principal Data (토큰에 저장될 데이터)
        OAuth2Principal oAuth2Principal = this.principal(Objects.requireNonNull(userInfoResponse.getBody()));

        // Generate JWT Token, Save To DB, Save To Redis
        String jwtToken = this.publishToken(oAuth2Principal);

        // Set Response Object
        OAuth2LoginResponse oAuth2LoginResponse = this.response(userInfoResponse.getBody(), jwtToken);

        logger.info(String.format("END KAKAO LOGIN :: CODE => %s", code));
        return oAuth2LoginResponse;

    }

    public ResponseEntity<KakaoTokenWrapper> getKakaoToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("client_id", Collections.singletonList(oAuth2Props.kakaoClientId));
        params.put("client_secret", Collections.singletonList(oAuth2Props.kakaoClientSecret));
        params.put("redirect_uri", Collections.singletonList(oAuth2Props.kakaoRedirectUri));
        params.put("grant_type", Collections.singletonList(oAuth2Props.kakaoAuthGrantType));
        params.put("code", Collections.singletonList(code));

        return restProvider.send(HttpMethod.POST, oAuth2Props.kakaoTokenUri, params
                , new HttpHeader(MediaType.APPLICATION_FORM_URLENCODED), KakaoTokenWrapper.class);
    }

    public ResponseEntity<KakaoUserInfoWrapper> getKakaoUserInfo(String accessToken) {
        HttpHeader headers = new HttpHeader(MediaType.APPLICATION_FORM_URLENCODED);
        headers.addHeader("Authorization", String.format("Bearer %s", accessToken));
        headers.addHeader("charset", "utf8");

        return restProvider.send(HttpMethod.POST, oAuth2Props.kakaoUserInfoUri, headers, KakaoUserInfoWrapper.class);
    }

}
