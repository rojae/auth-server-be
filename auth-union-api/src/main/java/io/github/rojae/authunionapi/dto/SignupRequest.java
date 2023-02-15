package io.github.rojae.authunionapi.dto;

import io.github.rojae.authunionapi.common.valid.PlatformTypeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "email이(가) 유효하지 않습니다")
    private String email;

    @NotBlank(message = "password이(가) 유효하지 않습니다")
    private String password;

    @NotBlank(message = "name이(가) 유효하지 않습니다")
    private String name;

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

    private String agreeRecvMail;   // Marketing Mail
}
