package io.github.rojae.authcoreapi.common.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Props {

    @Value("${oauth2.header.name}")
    public String jwtHeaderName;
    public static final String AUTHORIZATION_HEADER = "Authorization";

}
