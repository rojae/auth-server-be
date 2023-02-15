package io.github.rojae.authcoreapi.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "TBL_CUSTOM")
@Entity
@DynamicUpdate
public class Custom extends TimeEntity {
    @Id
    @Column(name = "accountId", nullable = false)
    private Long accountId;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(name = "birthDate", nullable = false)
    private String birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;      // M, F

    @Column(name = "mobileTel1", nullable = false)
    private String mobileTel1;

    @Column(name = "mobileTel2", nullable = false)
    private String mobileTel2;

    @Column(name = "mobileTel3", nullable = false)
    private String mobileTel3;

    @Column(name = "agreeRecvMail", nullable = false)
    private String agreeRecvMail;   // Marketing Mail (Y,N)

    public Custom(Account account, String birthDate, String gender, String mobileTel1, String mobileTel2, String mobileTel3, String agreeRecvMail) {
        this.account = account;
        this.birthDate = birthDate;
        this.gender = gender;
        this.mobileTel1 = mobileTel1;
        this.mobileTel2 = mobileTel2;
        this.mobileTel3 = mobileTel3;
        this.agreeRecvMail = agreeRecvMail;
    }
}
