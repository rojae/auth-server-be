package io.github.rojae.authunionapi.api.coreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoreApiProfileInfoResponse {
    private String name;
    private String email;
    private String platformType;
    private String profileImage;
    private boolean isAuth;
    private boolean isEnable;
    private String lastLoginDate;
}
