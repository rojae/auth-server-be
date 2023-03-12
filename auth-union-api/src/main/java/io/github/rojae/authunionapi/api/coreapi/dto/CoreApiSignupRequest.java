package io.github.rojae.authunionapi.api.coreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreApiSignupRequest {
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String platformType;
    private String profileImage;

    ////////// CUSTOM INFO ///////////
    private String birthDate;
    private String gender;      // M, F
    private String mobileTel1;
    private String mobileTel2;
    private String mobileTel3;

    // 필수 약관
    private String agreePersonalInfo;   // agreePersonalInfo (Y,N)
    private String agreeAdult;   // agreePersonalInfo (Y,N)

    // 선택 약관
    private String agreeRecvMail;   // Marketing Mail (Y,N)
    private String agreeRecvSms;   // Marketing SMS (Y,N)

    public CoreApiSignupRequest(String email, String password, String name, String platformType, String profileImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.platformType = platformType;
        this.profileImage = profileImage;
        // add
        this.nickname = name;
    }
}