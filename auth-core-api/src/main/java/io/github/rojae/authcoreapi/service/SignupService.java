package io.github.rojae.authcoreapi.service;

import io.github.rojae.authcoreapi.common.aspect.LogExecutionTime;
import io.github.rojae.authcoreapi.common.enums.IsAuth;
import io.github.rojae.authcoreapi.common.enums.IsEnable;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.common.exception.SignupAccountException;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.domain.Custom;
import io.github.rojae.authcoreapi.dto.SignupRequest;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    @LogExecutionTime
    public boolean signup(SignupRequest request) {
        try {
            var newAccount = new Account(request.getName(), passwordEncoder.encode(request.getPassword()), request.getEmail(), PlatformType.valueOf(request.getPlatformType()), request.getProfileImage(), "", IsEnable.Y.getYn(), IsAuth.Y.getYn());
            var newCustom = new Custom(newAccount, request.getBirthDate(), request.getGender(), request.getMobileTel1(), request.getMobileTel2(), request.getMobileTel3(), request.getAgreeRecvMail());

            accountRepository.save(newAccount);

            if(PlatformType.valueOf(request.getPlatformType()) != PlatformType.KAKAO)
                customRepository.save(newCustom);

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
