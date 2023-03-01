package io.github.rojae.authcoreapi.service;

import io.github.rojae.authcoreapi.common.aspect.LogExecutionTime;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.common.exception.LoginAccountInvalidException;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.dto.LoginRequest;
import io.github.rojae.authcoreapi.dto.LoginResponse;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    @LogExecutionTime
    public LoginResponse login(LoginRequest request) {
        Account selectedAccount = accountRepository.findByEmailAndIsEnableAndIsAuthAndPlatformType(request.getEmail(), 'Y', 'Y', PlatformType.valueOf(request.getPlatformType()));

        if (selectedAccount != null && passwordEncoder.matches(request.getPassword(), selectedAccount.getPassword())) {
            log.debug(String.format("SUCCESS LOGIN :: %s %s", request.getEmail(), request.getPlatformType()));
            selectedAccount.setLastLoginDate(LocalDateTime.now());
            return new LoginResponse(selectedAccount.getEmail(), "닉네임", selectedAccount.getPlatformType().name(), selectedAccount.getProfileImage());
        }
        else{
            throw new LoginAccountInvalidException();
        }
    }
}
