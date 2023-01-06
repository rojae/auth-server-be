package io.github.rojae.oauth2api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenPublishResponse {

    private String headerName;
    private String headerValue;
}
