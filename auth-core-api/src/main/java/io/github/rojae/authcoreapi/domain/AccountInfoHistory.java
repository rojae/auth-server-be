package io.github.rojae.authcoreapi.domain;

import io.github.rojae.authcoreapi.common.enums.PlatformType;

import javax.persistence.*;

@Table(name = "TBL_ACCOUNT_INFO_HISTORY")
@Entity
public class AccountInfoHistory extends TimeCreatedEntity{

    public AccountInfoHistory() {
    }

    public AccountInfoHistory(Long accountId, String nickname, String password, String email, char isAuth, PlatformType platformType, String profileImage, char isEnable, String reqUuid) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.isAuth = isAuth;
        this.platformType = platformType;
        this.profileImage = profileImage;
        this.isEnable = isEnable;
        this.reqUuid = reqUuid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private Long seq;

    @Column(name = "accountId", nullable = false)
    private Long accountId;

    @Column(name = "nickname", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8") //CHARACTER SET UTF8"
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
    private String email;

    @Column(name = "isAuth", nullable = false, columnDefinition = "CHAR(1)", length = 1)
    private char isAuth;

    @Column(name = "platformType", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @Column(name = "profileImage", nullable = true)
    private String profileImage;

    @Column(name = "isEnable", nullable = false)
    private char isEnable;

    @Column(name = "reqUuid", nullable = false)
    private String reqUuid;

}

