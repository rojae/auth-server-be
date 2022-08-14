package io.github.rojae.authserver.persistence;


import io.github.rojae.authserver.domain.redis.RAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountRedisRepository extends CrudRepository<RAccount, String> {
}
