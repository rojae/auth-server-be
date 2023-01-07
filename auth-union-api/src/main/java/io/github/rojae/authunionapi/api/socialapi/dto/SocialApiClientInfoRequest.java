package io.github.rojae.authunionapi.api.socialapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialApiClientInfoRequest {
    private String platformType;
}
