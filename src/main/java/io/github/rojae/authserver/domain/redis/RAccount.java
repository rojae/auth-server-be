package io.github.rojae.authserver.domain.redis;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.common.props.JwtProps;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import org.springframework.data.annotation.Id;
import java.io.Serializable;

@RedisHash(value = "RAccount")
public class RAccount implements Serializable {

    public RAccount(String id, String name, String profileImage, String accessToken, String reqUuid) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.accessToken = accessToken;
        this.reqUuid = reqUuid;
    }

    @Id
    private String id;

    private String name;

    private String profileImage;

    private String accessToken;
    private String reqUuid;

    public static String idFormat(PlatformType platformType, String email) {
        return String.format("%s:%s",platformType.name(),email);
    }

    @TimeToLive
    public long getTimeToLive() {
        return JwtProps.jwtExpireDurationHour * 3600;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getReqUuid() {
        return reqUuid;
    }
}
