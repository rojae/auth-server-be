package io.github.rojae.oauth2api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2Principal{
    private String email;
    private String platformType;
    private String name;
    private String reqUuid;
}
