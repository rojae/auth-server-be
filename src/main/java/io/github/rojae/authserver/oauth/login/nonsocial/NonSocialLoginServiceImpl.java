package io.github.rojae.authserver.oauth.login.nonsocial;

import io.github.rojae.authserver.common.enums.PlatformType;
import io.github.rojae.authserver.common.exception.LoginAccountInvalidException;
import io.github.rojae.authserver.common.exception.SignupDuplicateException;
import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.common.props.OAuth2Props;
import io.github.rojae.authserver.domain.entity.Account;
import io.github.rojae.authserver.domain.redis.RAccount;
import io.github.rojae.authserver.domain.redis.RAccountBuilder;
import io.github.rojae.authserver.dto.ServiceSignupRequest;
import io.github.rojae.authserver.oauth.OAuth2LoginResponse;
import io.github.rojae.authserver.oauth.OAuth2LoginResponseBuilder;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.github.rojae.authserver.persistence.RAccountRepository;
import io.github.rojae.authserver.persistence.AccountRepository;
import io.github.rojae.authserver.util.TimeUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional(readOnly = false)
public class NonSocialLoginServiceImpl implements NonSocialLoginService {

    Logger logger = Logger.getLogger(NonSocialLoginServiceImpl.class.getName());

    private final JwtProvider jwtProvider;
    private final OAuth2Props oAuth2Props;
    private final AccountRepository accountRepository;
    private final RAccountRepository accountRedisRepository;
    private final PasswordEncoder passwordEncoder;

    public NonSocialLoginServiceImpl(JwtProvider jwtProvider, OAuth2Props oAuth2Props, AccountRepository accountRepository, RAccountRepository accountRedisRepository, PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.oAuth2Props = oAuth2Props;
        this.accountRepository = accountRepository;
        this.accountRedisRepository = accountRedisRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = false)
    public Account signup(ServiceSignupRequest request) {
        if(accountRepository.existsByEmailAndIsEnable(request.getEmail(), 'Y')){
            throw new SignupDuplicateException();
        }

        Account newAccount = new Account(request.getName(), passwordEncoder.encode(request.getPassword()), request.getEmail(), request.getPlatformType(), request.getProfileImage(), "EMPTY");
        accountRepository.save(newAccount);

        return newAccount;
    }

    @Override
    public OAuth2LoginResponse login(String email, String password, String reqUuid) {
        Account selectedAccount = accountRepository.findByEmailAndIsEnable(email, 'Y');

        if(selectedAccount == null){
            throw new LoginAccountInvalidException();
        }
        else if (passwordEncoder.matches(password, selectedAccount.getPassword())) {
            logger.info(String.format("SUCCESS LOGIN :: %s", email));

            OAuth2Principal oAuth2Principal = new OAuth2Principal();
            oAuth2Principal.setName(selectedAccount.getName());
            oAuth2Principal.setEmail(selectedAccount.getEmail());
            oAuth2Principal.setProfileImage(selectedAccount.getProfileImage());
            oAuth2Principal.setPlatformType(selectedAccount.getPlatformType());

            String token = this.publishToken(oAuth2Principal, reqUuid);

            return new OAuth2LoginResponseBuilder()
                    .setName(selectedAccount.getName())
                    .setEmail(selectedAccount.getEmail())
                    .setPlatformType(PlatformType.NONSOCIAL)
                    .setProfileImage(selectedAccount.getProfileImage())
                    .setToken(token)
                    .setExpireTime(TimeUtils.dateFomat(jwtProvider.getExpiration(token), "yyyy.MM.dd HH:mm:ss"))
                    .createOAuth2LoginResponse();
        } else{
            throw new UsernameNotFoundException(email);
        }
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
        Account selectedAccount = accountRepository.findByEmailAndIsEnable(oAuth2Principal.getEmail(), 'Y');

        // 이미 저장된 계정 정보는 업데이트 처리
        logger.info("기가입된 회원으로 정보를 최신화합니다.");
        selectedAccount.setAccessToken(token);
        selectedAccount.setReqUuid(reqUuid);

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
