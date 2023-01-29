package io.github.rojae.authunionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AuthUnionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthUnionApiApplication.class, args);
    }

}
