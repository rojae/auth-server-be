package io.github.rojae.oauth2api.domain;

import io.github.rojae.oauth2api.common.enums.PlatformType;
import io.github.rojae.oauth2api.common.props.JwtProps;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash(value = "RAccount")
public class RAccount implements Serializable {

    public RAccount(String id, String name, String accessToken, String reqUuid) {
        this.id = id;
        this.name = name;
        this.accessToken = accessToken;
        this.reqUuid = reqUuid;
    }

    @Id
    private String id;

    private String name;

    private String accessToken;
    private String reqUuid;

    public static String idFormat(String platformType, String email) {
        return String.format("%s:%s", platformType, email);
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

    public String getAccessToken() {
        return accessToken;
    }

    public String getReqUuid() {
        return reqUuid;
    }
}
