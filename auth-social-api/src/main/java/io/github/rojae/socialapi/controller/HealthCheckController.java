package io.github.rojae.socialapi.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @ApiOperation(value = "서버 상태 확인 API", notes = "서버 상태 확인 API")
    @GetMapping("/")
    public String healthCheck(){
        return "ok";
    }

}
