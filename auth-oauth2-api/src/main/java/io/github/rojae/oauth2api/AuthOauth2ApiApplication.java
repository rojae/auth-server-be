package io.github.rojae.oauth2api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AuthOauth2ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthOauth2ApiApplication.class, args);
    }

}
