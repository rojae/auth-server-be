package io.github.rojae.authcoreapi.service;

import io.github.rojae.authcoreapi.common.aspect.LogExecutionTime;
import io.github.rojae.authcoreapi.common.enums.IsAuth;
import io.github.rojae.authcoreapi.common.enums.IsEnable;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.common.exception.SignupAccountException;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.domain.AccountLoginHistory;
import io.github.rojae.authcoreapi.domain.Custom;
import io.github.rojae.authcoreapi.dto.SignupRequest;
import io.github.rojae.authcoreapi.persistence.AccountLoginHistoryRepository;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import io.github.rojae.authcoreapi.persistence.CustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {
    private final CustomRepository customRepository;
    private final AccountRepository accountRepository;
    private final AccountLoginHistoryRepository accountLoginHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    @LogExecutionTime
    public boolean signup(SignupRequest request) {
        try {
            var newAccount = new Account(request.getNickname(), passwordEncoder.encode(request.getPassword()), request.getEmail(), PlatformType.valueOf(request.getPlatformType()), request.getProfileImage(), "", IsEnable.Y.getYn(), IsAuth.Y.getYn());
            var newCustom = new Custom(newAccount, request.getName(), request.getBirthDate(), request.getGender(), request.getMobileTel1(), request.getMobileTel2(), request.getMobileTel3(),
                    request.getAgreePersonalInfo(), request.getAgreeAdult(), request.getAgreeRecvMail(), request.getAgreeRecvSms());

            accountRepository.save(newAccount);

            // not implemented :: 소셜 로그인의 경우, 개인정보와 동의여부는 따로 받아야 함
            if(PlatformType.valueOf(request.getPlatformType()) != PlatformType.KAKAO)
                customRepository.save(newCustom);

            // kakao의 경우, 가입과 동시에 로그인 (수정예정)
            // save login history
            if(PlatformType.valueOf(request.getPlatformType()) == PlatformType.KAKAO){
                AccountLoginHistory loginHistory = new AccountLoginHistory(newAccount.getAccountId(), newAccount.getLastLoginDate());
                accountLoginHistoryRepository.save(loginHistory);
            }

            return true;
        } catch (RuntimeException ex) {
            log.error(
                    "Signup Request Exception | message : {} | request : {}",
                    ex.getCause().getMessage(),
                    request.toString()
            );
            throw new SignupAccountException();
        }
    }

    @Transactional(readOnly = true)
    @LogExecutionTime
    public boolean isDuplicate(SignupRequest request) {
        return accountRepository.existsByEmailAndIsEnable(request.getEmail(), IsEnable.Y.getYn());
    }
}
