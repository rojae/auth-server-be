package io.github.rojae.socialapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AuthSocialApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthSocialApiApplication.class, args);
    }

}
