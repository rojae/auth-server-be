package kr.github.rojae.data.io.overmind;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NonSocialLoginRequest {
    @ApiParam(value = "이메일", required = true, example = "rojae@test.com")
    @NotBlank(message = "email이(가) 유효하지 않습니다")
    private String email;

    @ApiParam(value = "패스워드!", required = true, example = "password01!")
    @NotBlank(message = "password이(가) 유효하지 않습니다")
    private String password;
}