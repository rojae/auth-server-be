package io.github.rojae.authserver.oauth.logout;

import io.github.rojae.authserver.common.jwt.JwtProvider;
import io.github.rojae.authserver.domain.redis.RAccount;
import io.github.rojae.authserver.oauth.OAuth2Principal;
import io.github.rojae.authserver.persistence.RAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogoutServiceImpl implements LogoutService {
    Logger logger = LoggerFactory.getLogger(LogoutServiceImpl.class.getName());

    private final RAccountRepository accountRedisRepository;
    private final JwtProvider jwtProvider;

    public LogoutServiceImpl(RAccountRepository accountRedisRepository, JwtProvider jwtProvider) {
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
                logger.debug(String.format("Logout Complete :: %s", token));
                return true;
            }
            else{
                logger.debug(String.format("Token is not existed :: %s", token));
            }

        } catch (Exception e) {
            logger.debug(String.format("Parsing Failed :: %s", token));
        }

        return false;
    }

}
