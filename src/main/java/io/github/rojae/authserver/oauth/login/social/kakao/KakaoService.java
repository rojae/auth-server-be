package io.github.rojae.authserver.oauth.login.social.kakao;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.common.exception.InvalidKakaoTokenException;
import io.github.rojae.authserver.common.exception.InvalidTokenException;
import io.github.rojae.authserver.common.http.HttpHeader;
import io.github.rojae.authserver.common.http.RestProvider;
import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.common.props.OAuth2Props;
import io.github.rojae.authserver.domain.redis.RAccount;
import io.github.rojae.authserver.domain.redis.RKakaoTokenInfo;
import io.github.rojae.authserver.domain.redis.RKakaoUserInfo;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.OAuth2LoginResponseBuilder;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.github.rojae.authserver.oauth.login.social.SocialLoginServiceImpl;
import io.github.rojae.authserver.persistence.RAccountRepository;
import io.github.rojae.authserver.persistence.AccountRepository;
import io.github.rojae.authserver.persistence.RKakaoTokenInfoRepository;
import io.github.rojae.authserver.persistence.RKakaoUserInfoRepository;
import io.github.rojae.authserver.util.TimeUtils;
import java.util.Map;
import java.util.Optional;
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
    private final RAccountRepository rAccountRepository;
    private final RKakaoTokenInfoRepository rKakaoTokenInfoRepository;
    private final RKakaoUserInfoRepository rKakaoUserInfoRepository;

    public KakaoService(JwtProvider jwtProvider, OAuth2Props oAuth2Props, AccountRepository accountRepository, RAccountRepository rAccountRepository, RestProvider restProvider, JwtProvider jwtProvider1, OAuth2Props oAuth2Props1,
        RAccountRepository rAccountRepository1,
        RKakaoTokenInfoRepository rKakaoTokenInfoRepository,
        RKakaoUserInfoRepository rKakaoUserInfoRepository) {
        super(jwtProvider, oAuth2Props, accountRepository, rAccountRepository);
        this.restProvider = restProvider;
        this.jwtProvider = jwtProvider1;
        this.oAuth2Props = oAuth2Props1;
        this.rAccountRepository = rAccountRepository1;
        this.rKakaoTokenInfoRepository = rKakaoTokenInfoRepository;
        this.rKakaoUserInfoRepository = rKakaoUserInfoRepository;
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
    public OAuth2LoginResponse login(String code, String reqUuid) {
        logger.info(String.format("START KAKAO LOGIN :: CODE => %s", code));

        // STEP 1 : KAKAO TOKEN
        ResponseEntity<KakaoTokenWrapper> tokenResponse = this.getKakaoToken(code);
        logger.info(String.format("STEP 1 : KAKAO TOKEN => %s", tokenResponse.getBody()));
        // REDIS SAVE :: KAKAO TOKEN INFO
        rKakaoTokenInfoRepository.save(new RKakaoTokenInfo(reqUuid, tokenResponse.getBody().getToken_type(), tokenResponse.getBody().getAccess_token(), tokenResponse.getBody().getRefresh_token(), tokenResponse.getBody().getScope()));
        
        // STEP 2 : KAKAO USER INFO
        ResponseEntity<KakaoUserInfoWrapper> userInfoResponse =
                this.getKakaoUserInfo(Objects.requireNonNull(tokenResponse.getBody()).getAccess_token());
        logger.info(String.format("STEP 2 : KAKAO USER INFO => %s", userInfoResponse.getBody()));
        // REDIS SAVE :: KAKAO USER INFO
        rKakaoUserInfoRepository.save(new RKakaoUserInfo(reqUuid, userInfoResponse.getBody().getId(), userInfoResponse.getBody().connected_at, userInfoResponse.getBody().getProperties(), userInfoResponse.getBody().kakao_account ));

        // STEP 3 : Generate JWT Token including User's Info
        logger.info("STEP 3 : KAKAO JWT Login, Signup");

        // Create User's Principal Data (토큰에 저장될 데이터)
        OAuth2Principal oAuth2Principal = this.principal(Objects.requireNonNull(userInfoResponse.getBody()));

        // Generate JWT Token, Save To DB, Save To Redis
        String jwtToken = this.publishToken(oAuth2Principal,reqUuid);

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

    public ResponseEntity<Map> unLink(String token) throws Exception {
        OAuth2Principal oAuth2Principal = jwtProvider.toPrincipal(token);

        Optional<RAccount> currentTokenInfo = rAccountRepository.findById(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()));
        if(currentTokenInfo.isEmpty())
            throw new InvalidTokenException();

        Optional<RKakaoTokenInfo> kakaoTokenInfo = rKakaoTokenInfoRepository.findById(currentTokenInfo.get().getReqUuid());
        if(kakaoTokenInfo.isEmpty())
            throw new InvalidKakaoTokenException();

        HttpHeader headers = new HttpHeader(MediaType.APPLICATION_FORM_URLENCODED);
        headers.addHeader("Authorization", String.format("Bearer %s", kakaoTokenInfo.get().getAccess_token()));
        headers.addHeader("charset", "utf8");

        ResponseEntity<Map> response = restProvider.send(HttpMethod.POST, oAuth2Props.kakaoUnlinkUri, headers, Map.class);
        logger.info(String.format("KAKAO UNLINK :: RESPONSE => %s", response));

        return response;
    }

}
