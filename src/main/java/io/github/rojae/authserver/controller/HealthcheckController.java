package io.github.rojae.authserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    /**
     * @method : aliveCheck
     * @description : Alive Check API of Load Balancer
     * @author: rojae
     * @date : 2022/08/14
     **/
    @GetMapping("/")
    public ResponseEntity<String> aliveCheck(){
        return new ResponseEntity<>("Alive Check OK", HttpStatus.OK);
    }

}
