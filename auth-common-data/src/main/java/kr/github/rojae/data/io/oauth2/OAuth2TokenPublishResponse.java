package kr.github.rojae.data.io.oauth2;

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
