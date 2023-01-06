package io.github.rojae.authcoreapi.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HealthCheckController {

    @GetMapping("/")
    @ApiOperation(value = "서버 상태 확인 API", notes = "서버 상태 확인 API")
    public String healthCheck(){
        return "ok";
    }

}
