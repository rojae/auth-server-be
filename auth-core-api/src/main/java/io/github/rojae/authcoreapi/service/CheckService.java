package io.github.rojae.authcoreapi.service;

import io.github.rojae.authcoreapi.common.aspect.LogExecutionTime;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.dto.CheckEmailRequest;
import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import io.github.rojae.authcoreapi.dto.CheckNicknameRequest;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = false)
    @LogExecutionTime
    public boolean isExistUser(CheckExistUserRequest request){
        Account selectedAccount = accountRepository.findByEmailAndPlatformType(request.getEmail(), PlatformType.valueOfName(request.getPlatformType()));
        if(selectedAccount != null){
            selectedAccount.setLastLoginDate(LocalDateTime.now());  // 로그인 절차이기 때문에, 업데이트 처리..
        }
        return selectedAccount != null;
    }

    @Transactional(readOnly = false)
    @LogExecutionTime
    public boolean duplicateEmail(CheckEmailRequest request) {
        return accountRepository.existsByEmail(request.getEmail());
    }

    @Transactional(readOnly = false)
    @LogExecutionTime
    public boolean duplicateNickname(CheckNicknameRequest request) {
        return accountRepository.existsByNickname(request.getNickname());
    }

}
