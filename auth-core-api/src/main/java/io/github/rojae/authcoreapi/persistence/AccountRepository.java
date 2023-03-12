package io.github.rojae.authcoreapi.persistence;

import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findTopByAccountId(Long accountId);
    Account findByEmailAndIsEnableAndIsAuthAndPlatformType(String email, char isEnable, char isAuth, PlatformType platformType);
    Account findByEmailAndPlatformType(String email, PlatformType platformType);
    Account findByEmailAndPlatformTypeAndIsEnableAndIsAuth(String email, PlatformType platformType, char isEnable, char isAuth);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    boolean existsByEmailAndIsEnable(String email, char isEnable);

}
