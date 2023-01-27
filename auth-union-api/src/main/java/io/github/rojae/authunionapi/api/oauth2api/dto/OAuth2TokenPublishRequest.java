package io.github.rojae.authunionapi.api.oauth2api.dto;

import io.github.rojae.authunionapi.common.valid.PlatformTypeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2TokenPublishRequest {

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
