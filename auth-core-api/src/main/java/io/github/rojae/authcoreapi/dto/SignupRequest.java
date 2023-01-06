package io.github.rojae.authcoreapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String platformType;
    private String profileImage;
}
