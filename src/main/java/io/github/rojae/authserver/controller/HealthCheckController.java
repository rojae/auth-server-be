package io.github.rojae.authserver.controller;

import io.github.rojae.authserver.common.enums.ApiCode;
import io.github.rojae.authserver.dto.ApiBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    /**
     * @method : aliveCheck
     * @description : Alive Check API of Load Balancer
     * @author: rojae
     * @date : 2022/08/14
     **/
    @GetMapping("/")
    public ResponseEntity<ApiBase<Object>> aliveCheck(){
        ApiBase<Object> response = new ApiBase<>(ApiCode.OK, "Alive Check Ok");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
