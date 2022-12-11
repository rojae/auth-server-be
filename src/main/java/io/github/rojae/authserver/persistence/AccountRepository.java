package io.github.rojae.authserver.persistence;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmailAndIsEnable(String email, char isEnable);
    Account findByEmailAndPlatformType(String email, PlatformType platformType);
    boolean existsByEmail(String email);
}
