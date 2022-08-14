package io.github.rojae.authserver.oauth;

import io.github.rojae.authserver.common.enums.PlatformType;

public class OAuth2LoginResponse {

    private String name;
    private PlatformType platformType;
    private String email;
    private String profileImage;
    private String token;
    private String expireTime;

    public OAuth2LoginResponse(String name, PlatformType platformType, String email, String profileImage, String token, String expireTime) {
        this.name = name;
        this.platformType = platformType;
        this.email = email;
        this.profileImage = profileImage;
        this.token = token;
        this.expireTime = expireTime;
    }

    public OAuth2Principal toPrincipal() {
        OAuth2Principal oAuth2Principal = new OAuth2Principal();
        oAuth2Principal.setName(this.name);
        oAuth2Principal.setEmail(this.email);
        oAuth2Principal.setPlatformType(this.platformType);
        oAuth2Principal.setProfileImage(this.profileImage);
        return oAuth2Principal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
