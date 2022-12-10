package io.github.rojae.authserver.persistence;

import io.github.rojae.authserver.domain.redis.RKakaoUserInfo;
import org.springframework.data.repository.CrudRepository;

public interface RKakaoUserInfoRepository extends CrudRepository<RKakaoUserInfo, String> {

}
