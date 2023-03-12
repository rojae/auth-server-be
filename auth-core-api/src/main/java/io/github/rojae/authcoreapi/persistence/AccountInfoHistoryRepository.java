package io.github.rojae.authcoreapi.persistence;

import io.github.rojae.authcoreapi.domain.AccountInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoHistoryRepository extends JpaRepository<AccountInfoHistory, Long> {
}
