package io.github.rojae.authserver.oauth.logout;

import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.domain.redis.RAccount;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.github.rojae.authserver.persistence.AccountRedisRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class LogoutServiceImpl implements LogoutService {
    Logger logger = Logger.getLogger(LogoutServiceImpl.class.getName());

    private final AccountRedisRepository accountRedisRepository;
    private final JwtProvider jwtProvider;

    public LogoutServiceImpl(AccountRedisRepository accountRedisRepository, JwtProvider jwtProvider) {
        this.accountRedisRepository = accountRedisRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean logout(String token) {
        OAuth2Principal oAuth2Principal = null;
        Optional<RAccount> currentTokenInfo = Optional.empty();

        try {
            oAuth2Principal = jwtProvider.toPrincipal(token);
            currentTokenInfo = accountRedisRepository.findById(RAccount.idFormat(oAuth2Principal.getPlatformType(), oAuth2Principal.getEmail()));

            if(currentTokenInfo.isPresent() && currentTokenInfo.get().getAccessToken().equals(token)){
                accountRedisRepository.delete(currentTokenInfo.get());
                logger.info(String.format("Logout Complete :: %s", token));
                return true;
            }
            else{
                logger.info(String.format("Token is not existed :: %s", token));
            }

        } catch (Exception e) {
            logger.info(String.format("Parsing Failed :: %s", token));
        }

        return false;
    }

}
