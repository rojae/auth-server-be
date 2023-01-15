package io.github.rojae.authcoreapi.controller;

import io.github.rojae.authcoreapi.common.enums.ApiCode;
import io.github.rojae.authcoreapi.dto.*;
import io.github.rojae.authcoreapi.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/api/v1/my-profile")
    @ApiOperation(value = "내 정보 조회", notes = "내 정보 조회 API")
    public ResponseEntity<ApiBase<ProfileInfoResponse>> myProfile(@Valid ProfileInfoRequest request) {
        ProfileInfoResponse profileInfoResponse = profileService.profileInfo(request);
        if (profileInfoResponse == null)
            return ResponseEntity.ok(new ApiBase<>(ApiCode.NOTFOUND_PROFILE, new ProfileInfoResponse()));
        else
            return ResponseEntity.ok(new ApiBase<>(ApiCode.OK, profileInfoResponse));
    }

}