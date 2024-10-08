package kr.github.rojae.data.io.oauth2;

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
