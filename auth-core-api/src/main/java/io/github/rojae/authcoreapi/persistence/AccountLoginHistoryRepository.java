package io.github.rojae.authcoreapi.persistence;

import io.github.rojae.authcoreapi.domain.AccountLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLoginHistoryRepository extends JpaRepository<AccountLoginHistory, Long> {
}
