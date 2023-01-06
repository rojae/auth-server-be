package io.github.rojae.oauth2api.repository;

import io.github.rojae.oauth2api.domain.RAccount;
import org.springframework.data.repository.CrudRepository;

public interface RAccountRepository extends CrudRepository<RAccount, String> {
}