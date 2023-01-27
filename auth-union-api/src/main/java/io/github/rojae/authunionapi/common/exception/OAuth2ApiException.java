package io.github.rojae.authunionapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2ApiException extends RuntimeException{
    private String message;
    private String url;
}