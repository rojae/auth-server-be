package io.github.rojae.authunionapi.api.socialapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialApiLoginResponse {
    private String name;
    private String platformType;
    private String email;
    private String profileImage;
    private String token;
}
