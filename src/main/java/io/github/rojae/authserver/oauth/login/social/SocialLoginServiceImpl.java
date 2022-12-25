package io.github.rojae.authserver.oauth.login.social;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.common.props.OAuth2Props;
import io.github.rojae.authserver.domain.entity.Account;
import io.github.rojae.authserver.domain.redis.RAccount;
import io.github.rojae.authserver.domain.redis.RAccountBuilder;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.github.rojae.authserver.persistence.RAccountRepository;
import io.github.rojae.authserver.persistence.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional(readOnly = false)
public class SocialLoginServiceImpl implements SocialLoginService {

    Logger logger = Logger.getLogger(SocialLoginServiceImpl.class.getName());

    private final JwtProvider jwtProvider;
    private final OAuth2Props oAuth2Props;
    private final AccountRepository accountRepository;
    private final RAccountRepository accountRedisRepository;

    public SocialLoginServiceImpl(JwtProvider jwtProvider, OAuth2Props oAuth2Props, AccountRepository accountRepository, RAccountRepository accountRedisRepository) {
        this.jwtProvider = jwtProvider;
        this.oAuth2Props = oAuth2Props;
        this.accountRepository = accountRepository;
        this.accountRedisRepository = accountRedisRepository;
    }

    @Override
    public OAuth2LoginResponse login(String code, String reqUuid) {
        return null;
    }

    @Override
    public String publishToken(OAuth2Principal oAuth2Principal, String reqUuid) {
        logger.info("STEP 1 :: TOKEN CREATE");
        String token = this.generateToken(oAuth2Principal);

        logger.info("STEP 2 :: USER INFO SAVE DATABASE");
        this.saveDB(oAuth2Principal, token, reqUuid);

        logger.info("STEP 3 :: TOKEN INFO SAVE REDIS");
        this.saveRedis(oAuth2Principal, token, reqUuid);

        return token;
    }

    @Override
    public String generateToken(OAuth2Principal oAuth2Principal) {
        String token = jwtProvider.generateToken(oAuth2Principal);
        try {
            jwtProvider.verify(token);
        } catch (Exception e) {
            logger.info("발급할 토큰에 검증에 실패했습니다.");
            e.printStackTrace();
        }
        return token;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveDB(OAuth2Principal oAuth2Principal, String token, String reqUuid) {
        Account selectedAccount = accountRepository.findByEmailAndIsEnableAndIsAuth(oAuth2Principal.getEmail(), 'Y', 'Y');

        // 새로운 계정인 경우, 회원가입 처
        if (selectedAccount == null) {
            logger.info("최초 회원으로 회원가입 처리를 합니다");

            Account newAccount = new Account();
            newAccount.setName(oAuth2Principal.getName());
            newAccount.setEmail(oAuth2Principal.getEmail());
            newAccount.setPlatformType(oAuth2Principal.getPlatformType());
            newAccount.setProfileImage(oAuth2Principal.getProfileImage());
            newAccount.setAccessToken(token);
            newAccount.setPassword(UUID.randomUUID().toString());
            newAccount.setIsAuth('Y');
            newAccount.setEnable('Y');
            newAccount.setReqUuid(reqUuid);

            accountRepository.save(newAccount);
        }
        // 이미 저장된 계정 정보는 업데이트 처리
        else {
            logger.info("기가입된 회원으로 정보를 최신화합니다.");

            selectedAccount.setName(oAuth2Principal.getName());
            selectedAccount.setProfileImage(oAuth2Principal.getProfileImage());
            selectedAccount.setAccessToken(token);
            selectedAccount.setReqUuid(reqUuid);

        }

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveRedis(OAuth2Principal oAuth2Principal, String token, String reqUuid) {
        // 기저장된 Redis 정보는 만료처리
        Optional<RAccount> beforeTokenInfo = accountRedisRepository.findById(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()));

        beforeTokenInfo.ifPresent(
                rAccount -> {
                    // 기저장된 Redis 정보 삭제
                    logger.info("사용 가능한 기발급 토큰정보를 삭제합니다.");
                    logger.info(String.format("Id = %s | Name = %s | Token = %s", rAccount.getId(), rAccount.getName(), rAccount.getAccessToken()));
                    accountRedisRepository.delete(rAccount);
                }
        );

        // 새로운 토큰정보 Redis 저장
        logger.info("새로운 토큰정보를 저장합니다.");
        RAccount newTokenInfo = new RAccountBuilder()
                .setId(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()))
                .setName(oAuth2Principal.getName())
                .setProfileImage(oAuth2Principal.getProfileImage())
                .setAccessToken(token)
                .setReqUuid(reqUuid)
                .createRAccount();

        accountRedisRepository.save(newTokenInfo);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean unlinkDB(String token) {
        OAuth2Principal oAuth2Principal = jwtProvider.toPrincipal(token);
        Account savedAccount = accountRepository.findByEmailAndPlatformType(oAuth2Principal.getEmail(), oAuth2Principal.getPlatformType());
        savedAccount.setEnable('N');
        return true;
    }
}
