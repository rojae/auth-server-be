package io.github.rojae.authunionapi.api.oauth2api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2TokenPublishResponse {

    private String headerName;
    private String headerValue;
}
