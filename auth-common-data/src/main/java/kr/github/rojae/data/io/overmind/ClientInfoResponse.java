package kr.github.rojae.data.io.overmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoResponse {
    private String uri;
    private String clientId;
    private String redirectUri;
    private String responseType;
    private String total;
}