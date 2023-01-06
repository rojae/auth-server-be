package io.github.rojae.authcoreapi.common.filter;

import io.github.rojae.authcoreapi.common.oauth2.AuthenticationToken;
import io.github.rojae.authcoreapi.common.oauth2.OAuth2RestClient;
import io.github.rojae.authcoreapi.common.props.OAuth2Props;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final OAuth2RestClient OAuth2RestClient;
    private final OAuth2Props OAuth2Props;

    public JwtAuthenticationFilter(OAuth2RestClient OAuth2RestClient, OAuth2Props OAuth2Props) {
        this.OAuth2RestClient = OAuth2RestClient;
        this.OAuth2Props = OAuth2Props;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
