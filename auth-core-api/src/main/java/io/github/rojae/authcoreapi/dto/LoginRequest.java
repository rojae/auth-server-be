package io.github.rojae.authcoreapi.dto;

import io.github.rojae.authcoreapi.common.valid.PlatformTypeValid;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @ApiParam(value = "이메일", required = true, example = "rojae@test.com")
    @NotBlank(message = "email이(가) 유효하지 않습니다")
    private String email;

    @ApiParam(value = "패스워드!", required = true, example = "password01!")
    @NotBlank(message = "password이(가) 유효하지 않습니다")
    private String password;

    @ApiParam(value = "플랫폼의 종류 (NONSOCIAL, KAKAO)", required = true, example = "NONSOCIAL")
    @PlatformTypeValid
    private String platformType;
}
