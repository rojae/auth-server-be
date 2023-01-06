package io.github.rojae.authcoreapi.common.config;

import io.github.rojae.authcoreapi.common.filter.JwtAuthenticationFilter;
import io.github.rojae.authcoreapi.common.oauth2.OAuth2RestClient;
import io.github.rojae.authcoreapi.common.props.OAuth2Props;
import io.github.rojae.authcoreapi.common.props.SecurityProps;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2RestClient OAuth2RestClient;
    private final OAuth2Props OAuth2Props;
    private final SecurityProps securityProps;

    public SecurityConfig(OAuth2RestClient OAuth2RestClient, OAuth2Props OAuth2Props, SecurityProps securityProps) {
        this.OAuth2RestClient = OAuth2RestClient;
        this.OAuth2Props = OAuth2Props;
        this.securityProps = securityProps;
    }

    @Override
    public void configure(WebSecurity web) {
        String[] staticResources = {
                "swagger-resources/**",
                "v2/api-docs"
        };

        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers(securityProps.IgnoreMatchers)
                .mvcMatchers(staticResources);
//        web.ignoring()
//                .anyRequest();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(OAuth2RestClient, OAuth2Props), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

