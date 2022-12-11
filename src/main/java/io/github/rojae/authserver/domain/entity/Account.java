package io.github.rojae.authserver.domain.entity;


import io.github.rojae.authserver.common.enums.PlatformType;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Table(name = "TBL_ACCOUNT")
@Entity
@DynamicUpdate
public class Account extends TimeEntity {

    public Account() {
    }

    public Account(Long id, String name, String password, String email, char isAuth, PlatformType platformType, String profileImage, String accessToken) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isAuth = isAuth;
        this.platformType = platformType;
        this.profileImage = profileImage;
        this.accessToken = accessToken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)") //CHARACTER SET UTF8"
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "isAuth", nullable = false, columnDefinition = "CHAR(1)", length = 1) //  DEFAULT 'N'
    private char isAuth = 'N';

    @Column(name = "platformType", nullable = false, columnDefinition = "VARCHAR(10)") // DEFAULT 'NONSOCIAL'
    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @Column(name = "profileImage", nullable = true)
    private String profileImage;

    @Column(name = "accessToken", nullable = true, length = 1024)
    private String accessToken;

    @Column(name = "isEnable", nullable = false)
    private char isEnable;

    @Column(name = "reqUuid", nullable = false)
    private String reqUuid;

    // 패스워드 암호화 기법
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(char isAuth) {
        this.isAuth = isAuth;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getReqUuid() {
        return reqUuid;
    }

    public void setReqUuid(String reqUuid) {
        this.reqUuid = reqUuid;
    }

    public char getEnable() {
        return isEnable;
    }

    public void setEnable(char enable) {
        isEnable = enable;
    }
}
