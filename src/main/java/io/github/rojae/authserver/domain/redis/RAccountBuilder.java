package io.github.rojae.authserver.domain.redis;

public class RAccountBuilder {
    private String id;
    private String name;
    private String profileImage;
    private String accessToken;
    private String reqUuid;

    public RAccountBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public RAccountBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RAccountBuilder setProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public RAccountBuilder setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public RAccountBuilder setReqUuid(String reqUuid) {
        this.reqUuid = reqUuid;
        return this;
    }

    public RAccount createRAccount() {
        return new RAccount(id, name, profileImage, accessToken, reqUuid);
    }
}