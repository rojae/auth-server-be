package io.github.rojae.authunionapi.api.oauth2api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2ProfileInfoResponse {
    private String email;
    private String platformType;
    private String name;
    private String reqUuid;
}
