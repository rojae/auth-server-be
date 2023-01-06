package io.github.rojae.oauth2api.controller;

import io.github.rojae.oauth2api.common.enums.ApiCode;
import io.github.rojae.oauth2api.common.props.JwtProps;
import io.github.rojae.oauth2api.dto.ApiBase;
import io.github.rojae.oauth2api.dto.OAuth2Principal;
import io.github.rojae.oauth2api.dto.TokenPublishRequest;
import io.github.rojae.oauth2api.dto.TokenPublishResponse;
import io.github.rojae.oauth2api.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final JwtProps jwtProps;
    private final OAuth2Service oAuth2Service;

    @PostMapping("/api/v1/oauth2/publish")
    public ResponseEntity<ApiBase<TokenPublishResponse>> tokenPublish(@Valid @RequestBody TokenPublishRequest request){
        String token = oAuth2Service.publishToken(new OAuth2Principal(request.getEmail(), request.getPlatformType(), request.getName(), request.getReqUuid()));
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, new TokenPublishResponse(jwtProps.jwtHeaderName, token)));
    }

    @PostMapping("/api/v1/oauth2/dropping")
    public ResponseEntity<ApiBase<Object>> tokenDropping(@RequestHeader(value = JwtProps.AUTHORIZATION_HEADER)
                                                 @NotBlank(message = "Authorization can not be empty")
                                                 String token){
        oAuth2Service.deleteToken(token);
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK));
    }

}
