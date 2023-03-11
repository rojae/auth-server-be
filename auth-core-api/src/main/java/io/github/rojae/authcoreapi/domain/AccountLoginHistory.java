package io.github.rojae.authcoreapi.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "TBL_ACCOUNT_LOGIN_HISTORY")
@Entity
public class AccountLoginHistory extends TimeCreatedEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private Long seq;

    @Column(name = "accountId", nullable = false)
    private Long accountId;

    @Column(name = "lastLoginDate", nullable = true)
    private LocalDateTime lastLoginDate;

    public AccountLoginHistory() {
    }

    public AccountLoginHistory(Long accountId, LocalDateTime lastLoginDate) {
        this.accountId = accountId;
        this.lastLoginDate = lastLoginDate;
    }
}
