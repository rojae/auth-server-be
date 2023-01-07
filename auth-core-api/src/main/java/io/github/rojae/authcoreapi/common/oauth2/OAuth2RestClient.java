package io.github.rojae.authcoreapi.common.oauth2;

import io.github.rojae.authcoreapi.common.props.OAuth2Props;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class OAuth2RestClient {

    private final OAuth2Props OAuth2Props;

    public OAuth2RestClient(OAuth2Props OAuth2Props) {
        this.OAuth2Props = OAuth2Props;
    }

    public SignatureAlgorithm algorithm() {
        return SignatureAlgorithm.HS256;
    }

}
