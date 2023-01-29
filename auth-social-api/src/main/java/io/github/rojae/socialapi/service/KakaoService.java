package io.github.rojae.socialapi.service;

import io.github.rojae.socialapi.aspect.LogExecutionTime;
import io.github.rojae.socialapi.dto.LoginResponse;
import io.github.rojae.socialapi.enums.PlatformType;
import io.github.rojae.socialapi.social.kakao.KakaoApiClient;
import io.github.rojae.socialapi.social.kakao.dto.KakaoTokenWrapper;
import io.github.rojae.socialapi.social.kakao.dto.KakaoUserInfoWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoApiClient kakaoApiClient;

    @LogExecutionTime
    public LoginResponse login(String code){
        KakaoTokenWrapper tokenWrapper = kakaoApiClient.getToken(code).getBody();
        KakaoUserInfoWrapper userInfoWrapper = kakaoApiClient.getKakaoUserInfo(Objects.requireNonNull(tokenWrapper).getAccess_token()).getBody();
        return toResponse(Objects.requireNonNull(userInfoWrapper));
    }


    public LoginResponse toResponse(KakaoUserInfoWrapper userInfoResponse) {
        LoginResponse oAuth2Principal = new LoginResponse();
        oAuth2Principal.setName(userInfoResponse.kakao_account.profile.nickname);
        oAuth2Principal.setEmail(userInfoResponse.kakao_account.email);
        oAuth2Principal.setPlatformType(PlatformType.KAKAO.name());
        oAuth2Principal.setProfileImage(userInfoResponse.kakao_account.profile.profile_image_url);  // 640 X 640
        return oAuth2Principal;
    }
}
