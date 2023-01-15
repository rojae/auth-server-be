package io.github.rojae.authunionapi.dto;

import io.github.rojae.authunionapi.common.valid.PlatformTypeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileInfoRequest {

    @NotBlank(message = "email은 빈값을 가질 수 없습니다.")
    private String email;

    @PlatformTypeValid
    private String platformType;

}
