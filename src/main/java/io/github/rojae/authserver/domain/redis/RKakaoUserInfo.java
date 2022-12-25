package io.github.rojae.authserver.domain.redis;

import io.github.rojae.authserver.oauth.login.social.kakao.KakaoUserInfoWrapper.KakaoAccount;
import io.github.rojae.authserver.oauth.login.social.kakao.KakaoUserInfoWrapper.Properties;
import java.util.Date;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "RKakaoUserInfo")
public class RKakaoUserInfo {

  private String reqUuid;
  private long id;
  private Date connected_at;
  private Properties properties;
  private KakaoAccount kakao_account;

  public RKakaoUserInfo(String reqUuid, long id, Date connected_at, Properties properties,
      KakaoAccount kakao_account) {
    this.reqUuid = reqUuid;
    this.id = id;
    this.connected_at = connected_at;
    this.properties = properties;
    this.kakao_account = kakao_account;
  }

  public String getReqUuid() {
    return reqUuid;
  }

  public long getId() {
    return id;
  }

  public Date getConnected_at() {
    return connected_at;
  }

  public Properties getProperties() {
    return properties;
  }

  public KakaoAccount getKakao_account() {
    return kakao_account;
  }
}
