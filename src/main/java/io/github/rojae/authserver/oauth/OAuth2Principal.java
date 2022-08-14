package io.github.rojae.authserver.oauth;

import io.github.rojae.authserver.common.enums.PlatformType;

public class OAuth2Principal{
    private String name;
    private PlatformType platformType;
    private String email;
    private String profileImage;

    public OAuth2Principal() {
    }

    public OAuth2Principal(String name, PlatformType platformType, String email, String profileImage) {
        this.name = name;
        this.platformType = platformType;
        this.email = email;
        this.profileImage = profileImage;
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
}
