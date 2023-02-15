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

    // CUSTOM INFO //
    private String birthDate;
    private String gender;      // M, F
    private String mobileTel1;
    private String mobileTel2;
    private String mobileTel3;

    private String agreeRecvMail;   // Marketing Mail

    public CoreApiSignupRequest(String email, String password, String name, String platformType, String profileImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.platformType = platformType;
        this.profileImage = profileImage;
    }
}