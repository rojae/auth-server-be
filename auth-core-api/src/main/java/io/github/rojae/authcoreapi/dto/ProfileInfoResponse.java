package io.github.rojae.authcoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileInfoResponse {
    private String nickname;
    private String email;
    private String platformType;
    private String profileImage;
    private boolean isAuth;
    private boolean isEnable;
    private String lastLoginDate;
}
