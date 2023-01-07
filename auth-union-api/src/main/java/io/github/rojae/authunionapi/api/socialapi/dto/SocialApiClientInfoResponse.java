package io.github.rojae.authunionapi.api.socialapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialApiClientInfoResponse {
    private String uri;
    private String clientId;
    private String redirectUri;
    private String responseType;
    private String total;
}
