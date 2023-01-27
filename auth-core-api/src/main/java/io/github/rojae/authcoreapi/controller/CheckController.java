package io.github.rojae.authcoreapi.controller;

import io.github.rojae.authcoreapi.common.enums.ApiCode;
import io.github.rojae.authcoreapi.dto.ApiBase;
import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import io.github.rojae.authcoreapi.service.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @PostMapping("/api/v1/check/exist-user")
    public ResponseEntity<ApiBase<Object>> checkExistUser(@Valid @RequestBody CheckExistUserRequest request){
        if(checkService.isExistUser(request))
            return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, null));
        else
            return ResponseEntity.ok(new ApiBase<>(ApiCode.AUTH_ACCOUNT_INVALID, null));
    }


}
