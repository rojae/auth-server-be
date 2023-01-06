package io.github.rojae.authcoreapi.common.oauth2;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collections;

public class AuthenticationToken extends AbstractAuthenticationToken {

    public AuthenticationToken() {
        super(Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}