package io.github.rojae.authserver.dto;

public class KakaoClientInfoResponse {
    private final String uri;
    private final String clientId;
    private final String redirectUri;
    private final String responseType;
    private final String total;

    public KakaoClientInfoResponse(String uri, String clientId, String redirectUri, String responseType, String total) {
        this.uri = uri;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
        this.total = total;
    }

    public String getUri() {
        return uri;
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

    public String getTotal() {
        return total;
    }
}
