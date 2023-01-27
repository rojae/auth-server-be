package io.github.rojae.oauth2api.controller;

import io.github.rojae.oauth2api.common.enums.ApiCode;
import io.github.rojae.oauth2api.common.props.JwtProps;
import io.github.rojae.oauth2api.dto.ApiBase;
import io.github.rojae.oauth2api.dto.OAuth2Principal;
import io.github.rojae.oauth2api.dto.TokenPublishRequest;
import io.github.rojae.oauth2api.dto.TokenPublishResponse;
import io.github.rojae.oauth2api.service.OAuth2Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final JwtProps jwtProps;
    private final OAuth2Service oAuth2Service;

    @ApiOperation(value = "사용자 토큰 생성 API", notes = "사용자 토큰 생성 API")
    @PostMapping("/api/v1/oauth2/publish")
    public ResponseEntity<ApiBase<TokenPublishResponse>> tokenPublish(@Valid @RequestBody TokenPublishRequest request){
        String token = oAuth2Service.publishToken(new OAuth2Principal(request.getEmail(), request.getPlatformType(), request.getName(), request.getReqUuid()));
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, new TokenPublishResponse(jwtProps.jwtHeaderName, token)));
    }


    @ApiOperation(value = "사용자 토큰 삭제 API", notes = "사용자 토큰 삭제 API")
    @PostMapping("/api/v1/oauth2/dropping")
    public ResponseEntity<ApiBase<Object>> tokenDropping(@RequestHeader(value = JwtProps.AUTHORIZATION_HEADER)
                                                 @NotBlank(message = "Authorization can not be empty")
                                                 String token){
        oAuth2Service.deleteToken(token);
        return ResponseEntity.ok(new ApiBase<>(ApiCode.OK));
    }

    @ApiOperation(value = "사용자 데이터 추출", notes = "사용자 데이터 추출 API")
    @GetMapping("/api/v1/oauth2/detail")
    public ResponseEntity<ApiBase<OAuth2Principal>> getDetail(@RequestHeader(value = JwtProps.AUTHORIZATION_HEADER)
                                                              @NotBlank(message = "Authorization can not be empty")
                                                              String token){
        OAuth2Principal principal = oAuth2Service.getDetail(token);
        if(principal == null)
            return ResponseEntity.ok(new ApiBase<>(ApiCode.NOTFOUND_PROFILE, new OAuth2Principal()));
        else
            return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, principal));
    }

}
