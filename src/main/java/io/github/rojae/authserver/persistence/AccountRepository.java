package io.github.rojae.authserver.persistence;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmailAndIsEnableAndIsAuth(String email, char isEnable, char isAuth);
    Account findByEmailAndPlatformType(String email, PlatformType platformType);
    Account findByEmailAndPlatformTypeAndIsEnableAndIsAuth(String email, PlatformType platformType, char isEnable, char isAuth);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIsEnable(String email, char isEnable);

}
