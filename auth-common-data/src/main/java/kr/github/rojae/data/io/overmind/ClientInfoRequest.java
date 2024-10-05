package kr.github.rojae.data.io.overmind;

import io.swagger.annotations.ApiParam;
import kr.github.rojae.data.validator.PlatformTypeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoRequest {

    @ApiParam(value = "플랫폼 종류", required = true, example = "KAKAO")
    @NotBlank(message = "유효한 platformType이(가) 아닙니다.")
    @PlatformTypeValid
    private String platformType;
}
