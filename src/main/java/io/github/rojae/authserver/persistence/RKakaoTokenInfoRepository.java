package io.github.rojae.authserver.persistence;

import io.github.rojae.authserver.domain.redis.RKakaoTokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface RKakaoTokenInfoRepository extends CrudRepository<RKakaoTokenInfo, String> {

}
