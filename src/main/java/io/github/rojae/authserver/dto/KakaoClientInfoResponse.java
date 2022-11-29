package io.github.rojae.authserver.dto;

public class KakaoClientInfoResponse {
    private String clientId;
    private String redirectUri;
    private String responseType;

    public KakaoClientInfoResponse(String clientId, String redirectUri, String responseType) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getResponseType() {
        return responseType;
    }
}
