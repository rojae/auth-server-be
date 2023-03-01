package io.github.rojae.authunionapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckExistUserRequest {
    private String email;
    private String platformType;
}