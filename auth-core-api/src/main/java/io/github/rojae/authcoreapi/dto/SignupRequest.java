package io.github.rojae.authcoreapi.dto;


import io.github.rojae.authcoreapi.common.valid.PlatformTypeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupRequest {

    @NotBlank(message = "name이(가) 유효하지 않습니다")
    private String name;

    @NotBlank(message = "email이(가) 유효하지 않습니다")
    private String email;

    @NotBlank(message = "password이(가) 유효하지 않습니다")
    private String password;

    @NotBlank(message = "nickname이(가) 유효하지 않습니다")
    private String nickname;

    @NotBlank(message = "platformType이(가) 유효하지 않습니다")
    @PlatformTypeValid
    private String platformType;

    @NotNull(message = "profileImage는 null이 될 수 없습니다.")
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

}
