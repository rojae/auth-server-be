package io.github.rojae.authcoreapi.service;

import io.github.rojae.authcoreapi.common.aspect.LogExecutionTime;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.common.utils.DateUtils;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.dto.ProfileInfoRequest;
import io.github.rojae.authcoreapi.dto.ProfileInfoResponse;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final AccountRepository accountRepository;

    @LogExecutionTime
    public ProfileInfoResponse profileInfo(ProfileInfoRequest request){
        Account selectedAccount = accountRepository.findByEmailAndPlatformType(request.getEmail(), PlatformType.valueOfName(request.getPlatformType()));

        if(selectedAccount == null) return null;

        boolean isAuth = selectedAccount.getIsAuth() == 'Y';
        boolean isEnable = selectedAccount.getEnable() == 'Y';
        String lastLoginDate = DateUtils.toString(selectedAccount.getLastLoginDate());

        return new ProfileInfoResponse(selectedAccount.getEmail(), selectedAccount.getPlatformType().name(), selectedAccount.getProfileImage(), isAuth, isEnable, lastLoginDate);
    }

}
