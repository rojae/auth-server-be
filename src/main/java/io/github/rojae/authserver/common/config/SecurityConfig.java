package io.github.rojae.authserver.common.config;

import io.github.rojae.authserver.common.filter.JwtAuthenticationFilter;
import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.common.props.JwtProps;
import io.github.rojae.authserver.common.props.SecurityProps;
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
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final JwtProps jwtProps;
    private final SecurityProps securityProps;

    public SecurityConfig(JwtProvider jwtProvider, JwtProps jwtProps, SecurityProps securityProps) {
        this.jwtProvider = jwtProvider;
        this.jwtProps = jwtProps;
        this.securityProps = securityProps;
    }

    @Override
    public void configure(WebSecurity web) {
        String[] staticResources = {
        };

        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers(securityProps.IgnoreMatchers)
                .mvcMatchers(staticResources);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers(
//                        securityProps.IgnoreMatchers
//                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, jwtProps), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(jwtProvider, jwtProps))
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//              .logoutSuccessHandler(logoutSuccessHandler)
                .and()
//                .authenticationProvider(new CustomTokenAuthenticationProvider())
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

