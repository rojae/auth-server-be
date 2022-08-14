package io.github.rojae.authserver.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final OAuth2Principal oAuth2Principal;

    public AuthenticationToken(OAuth2Principal oAuth2Principal) {
        super(Collections.singletonList(new SimpleGrantedAuthority("USER")));
        this.oAuth2Principal = oAuth2Principal;
    }

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, OAuth2Principal oAuth2Principal) {
        super(Collections.singletonList(new SimpleGrantedAuthority("USER")));
        this.oAuth2Principal = oAuth2Principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return oAuth2Principal;
    }

    public OAuth2Principal getoAuth2Principal() {
        return oAuth2Principal;
    }

    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "oAuth2Principal=" + oAuth2Principal +
                '}';
    }
}