package io.github.rojae.socialapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String name;
    private String platformType;
    private String email;
    private String profileImage;
    private String token;
}
