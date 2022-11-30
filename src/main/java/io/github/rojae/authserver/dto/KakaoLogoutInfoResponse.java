package io.github.rojae.authserver.dto;

public class KakaoLogoutInfoResponse {
    private final String uri;
    private final String clientId;
    private final String logoutRedirectUri;
    private final String total;

    // ex. https://kauth.kakao.com/oauth/logout?
    // client_id=50ca5e8cf40713abcab868ed9ed3047d
    // &logout_redirect_uri=http%3A%2F%2Fkimcoder.kro.kr%3A8080%2Fhome

    public KakaoLogoutInfoResponse(String uri, String clientId, String logoutRedirectUri, String total) {
        this.uri = uri;
        this.clientId = clientId;
        this.logoutRedirectUri = logoutRedirectUri;
        this.total = total;
    }

    public String getUri() {
        return uri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getLogoutRedirectUri() {
        return logoutRedirectUri;
    }

    public String getTotal() {
        return total;
    }
}
