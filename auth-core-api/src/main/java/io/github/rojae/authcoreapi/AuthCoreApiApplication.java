package io.github.rojae.authcoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuthCoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthCoreApiApplication.class, args);
    }

}
