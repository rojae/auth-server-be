package io.github.rojae.authunionapi.api.coreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreApiLoginRequest {
    private String email;
    private String password;
    private String platformType;
}
