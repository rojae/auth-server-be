package io.github.rojae.authunionapi.controller;

import io.github.rojae.authunionapi.api.CoreApiClient;
import io.github.rojae.authunionapi.api.coreapi.dto.CoreApiCheckDuplicateEmail;
import io.github.rojae.authunionapi.api.coreapi.dto.CoreApiCheckDuplicateNickname;
import io.github.rojae.authunionapi.dto.ApiBase;
import io.github.rojae.authunionapi.dto.CheckDuplicateEmail;
import io.github.rojae.authunionapi.dto.CheckDuplicateNickname;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CheckController {

    private final CoreApiClient coreApiClient;

    @PostMapping("/api/v1/check/duplicate/email")
    public Mono<ResponseEntity<ApiBase<Object>>> duplicateEmail(@Valid @RequestBody CheckDuplicateEmail request){
        return coreApiClient.isDuplicateEmail(new CoreApiCheckDuplicateEmail(request.getEmail())).map(ResponseEntity::ok);
    }

    @PostMapping("/api/v1/check/duplicate/nickname")
    public Mono<ResponseEntity<ApiBase<Object>>> duplicateNickname(@Valid @RequestBody CheckDuplicateNickname request){
        return coreApiClient.isDuplicateNickname(new CoreApiCheckDuplicateNickname(request.getNickname())).map(ResponseEntity::ok);
    }


}
