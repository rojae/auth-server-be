package io.github.rojae.authserver.domain.redis;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "RKakaoTokenInfo")
public class RKakaoTokenInfo implements Serializable {

  @Id
  private String reqUuid;
  private String token_type;
  private String access_token;
  private String refresh_token;
  private String scope;

  public RKakaoTokenInfo(String reqUuid, String token_type, String access_token, String refresh_token,
      String scope) {
    this.reqUuid = reqUuid;
    this.token_type = token_type;
    this.access_token = access_token;
    this.refresh_token = refresh_token;
    this.scope = scope;
  }

  public String getReqUuid() {
    return reqUuid;
  }

  public String getToken_type() {
    return token_type;
  }

  public String getAccess_token() {
    return access_token;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public String getScope() {
    return scope;
  }
}
