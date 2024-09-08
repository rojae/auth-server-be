package io.github.rojae.authunionapi.controller;

import io.github.rojae.authunionapi.api.CoreApiClient;
import kr.github.rojae.data.common.ApiBase;
import kr.github.rojae.data.io.overmind.CheckDuplicateEmail;
import kr.github.rojae.data.io.overmind.CheckDuplicateNickname;
import kr.github.rojae.data.io.core.CoreApiCheckDuplicateEmail;
import kr.github.rojae.data.io.core.CoreApiCheckDuplicateNickname;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import reactor.core.publisher.Mono;

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
