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
}
