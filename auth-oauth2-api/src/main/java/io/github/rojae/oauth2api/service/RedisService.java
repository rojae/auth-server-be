package io.github.rojae.oauth2api.service;

import io.github.rojae.oauth2api.domain.RAccount;
import io.github.rojae.oauth2api.domain.RAccountBuilder;
import io.github.rojae.oauth2api.dto.OAuth2Principal;
import io.github.rojae.oauth2api.repository.RAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final JwtService jwtService;
    private final RAccountRepository accountRedisRepository;

    public String generateToken(OAuth2Principal oAuth2Principal) {
        String token = jwtService.generateToken(oAuth2Principal);
        try {
            jwtService.verify(token);
        } catch (Exception e) {
            log.debug("발급할 토큰에 검증에 실패했습니다.");
            e.printStackTrace();
        }
        return token;
    }

    @Transactional(readOnly = false)
    public boolean saveRedis(OAuth2Principal oAuth2Principal, String token) {
        // 기저장된 Redis 정보는 만료처리
        Optional<RAccount> beforeTokenInfo = accountRedisRepository.findById(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()));

        beforeTokenInfo.ifPresent(
                rAccount -> {
                    // 기저장된 Redis 정보 삭제
                    log.debug("사용 가능한 기발급 토큰정보를 삭제합니다.");
                    log.debug(String.format("Id = %s | Name = %s | Token = %s", rAccount.getId(), rAccount.getName(), rAccount.getAccessToken()));
                    accountRedisRepository.delete(rAccount);
                }
        );

        // 새로운 토큰정보 Redis 저장
        log.debug("새로운 토큰정보를 저장합니다.");
        RAccount newTokenInfo = new RAccountBuilder()
                .setId(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()))
                .setName(oAuth2Principal.getName())
                .setAccessToken(token)
                .setReqUuid(oAuth2Principal.getReqUuid())
                .createRAccount();

        accountRedisRepository.save(newTokenInfo);
        return true;
    }

    @Transactional(readOnly = false)
    public void deleteRedis(String token){
        OAuth2Principal oAuth2Principal = jwtService.toPrincipal(token);

        Optional<RAccount> tokenInfo = accountRedisRepository.findById(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()));

        tokenInfo.ifPresent(
                rAccount -> {
                    // 기저장된 Redis 정보 삭제
                    log.debug("사용 가능한 기발급 토큰정보를 삭제합니다.");
                    log.debug(String.format("Id = %s | Name = %s | Token = %s", rAccount.getId(), rAccount.getName(), rAccount.getAccessToken()));
                    accountRedisRepository.delete(rAccount);
                }
        );
    }

    @Transactional(readOnly = true)
    public OAuth2Principal getDetail(String token) {
        OAuth2Principal oAuth2Principal = jwtService.toPrincipal(token);
        Optional<RAccount> tokenInfo = accountRedisRepository.findById(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()));
        if(tokenInfo.isPresent())
            return oAuth2Principal;
        else
            return null;
    }
}
