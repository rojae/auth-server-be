package io.github.rojae.oauth2api.service;

import io.github.rojae.oauth2api.aspect.LogExecutionTime;
import io.github.rojae.oauth2api.dto.OAuth2Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2Service {

    private final RedisService redisService;

    @LogExecutionTime
    public String publishToken(OAuth2Principal oAuth2Principal) {
        log.debug("STEP 1 :: TOKEN CREATE");
        String token = redisService.generateToken(oAuth2Principal);

        log.debug("STEP 2 :: TOKEN INFO SAVE REDIS");
        redisService.saveRedis(oAuth2Principal, token);

        return token;
    }

    @LogExecutionTime
    public void deleteToken(String token){
        redisService.deleteRedis(token);
    }

    @LogExecutionTime
    public OAuth2Principal getDetail(String token) {
        return redisService.getDetail(token);
    }
}
