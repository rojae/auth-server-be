package io.github.rojae.authcoreapi.service;

import io.github.rojae.authcoreapi.common.aspect.LogExecutionTime;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.dto.CheckEmailRequest;
import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import io.github.rojae.authcoreapi.dto.CheckNicknameRequest;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import io.github.rojae.authcoreapi.persistence.CustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final AccountRepository accountRepository;
    private final CustomRepository customRepository;

    @Transactional(readOnly = false)
    @LogExecutionTime
    public boolean isExistUser(CheckExistUserRequest request){
        if(StringUtils.isEmpty(request.getEmail()) || PlatformType.valueOf(request.getPlatformType()) == PlatformType.UNKNOWN){
            throw new RuntimeException("파리미터가 비어있습니다");
        }
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
