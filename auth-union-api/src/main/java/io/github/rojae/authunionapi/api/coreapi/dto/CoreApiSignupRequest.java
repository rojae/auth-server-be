package io.github.rojae.authunionapi.api.coreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreApiSignupRequest {
    private String email;
    private String password;
    private String name;
    private String platformType;
    private String profileImage;
}