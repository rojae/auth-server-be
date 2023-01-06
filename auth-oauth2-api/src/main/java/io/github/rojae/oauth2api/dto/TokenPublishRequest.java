package io.github.rojae.oauth2api.dto;

import io.github.rojae.oauth2api.common.enums.PlatformType;
import io.github.rojae.oauth2api.common.valid.PlatformTypeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenPublishRequest {

    @NotBlank(message = "유효한 name이(가) 아닙니다.")
    private String name;

    @NotBlank(message = "유효한 platformType이(가) 아닙니다.")
    @PlatformTypeValid
    private String platformType;

    @NotBlank(message = "유효한 email이(가) 아닙니다.")
    private String email;

    @NotBlank(message = "유효한 reqUuid이(가) 아닙니다.")
    private String reqUuid;
}
