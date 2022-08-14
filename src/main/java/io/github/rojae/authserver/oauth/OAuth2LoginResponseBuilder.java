package io.github.rojae.authserver.oauth;

import io.github.rojae.authserver.common.enums.PlatformType;

public class OAuth2LoginResponseBuilder {
    private String name;
    private PlatformType platformType;
    private String email;
    private String profileImage;
    private String token;
    private String expireTime;

    public OAuth2LoginResponseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public OAuth2LoginResponseBuilder setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
        return this;
    }

    public OAuth2LoginResponseBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public OAuth2LoginResponseBuilder setProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public OAuth2LoginResponseBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public OAuth2LoginResponseBuilder setExpireTime(String expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public OAuth2LoginResponse createOAuth2LoginResponse() {
        return new OAuth2LoginResponse(name, platformType, email, profileImage, token, expireTime);
    }
}