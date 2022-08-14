package io.github.rojae.authserver.persistence;

import io.github.rojae.authserver.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
    boolean existsByEmail(String email);
}
