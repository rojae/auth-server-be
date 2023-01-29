package io.github.rojae.socialapi.service;

import io.github.rojae.socialapi.aspect.LogExecutionTime;
import io.github.rojae.socialapi.common.props.OAuth2Props;
import io.github.rojae.socialapi.dto.ClientInfoRequest;
import io.github.rojae.socialapi.dto.ClientInfoResponse;
import io.github.rojae.socialapi.enums.PlatformType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInfoService {

    private final OAuth2Props oAuth2Props;

    @LogExecutionTime
    public ClientInfoResponse getClientInfo(ClientInfoRequest request){
        PlatformType platformType = PlatformType.valueOfName(request.getPlatformType());

        if(platformType == PlatformType.UNKNOWN || platformType == PlatformType.NONSOCIAL){
            return new ClientInfoResponse();
        }
        else if(platformType == PlatformType.KAKAO){
            return new ClientInfoResponse(
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
        }

        return new ClientInfoResponse();
    }
}
