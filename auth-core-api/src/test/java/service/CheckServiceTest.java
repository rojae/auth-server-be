package service;

import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import io.github.rojae.authcoreapi.service.CheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import utils.AccountFixtureFactory;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private CheckService checkService;

    private EasyRandom easyRandom;

    private final String email = "test@email.com";

    @BeforeEach
    public void setUp() {
        // @ExtendWith(MockitoExtension.class)을 사용하면,
        // 아래 MockitoAnnotations.openMocks(this)가 필요 없습니다. (충돌이 일어나서, Mocking에 실패함)
        // MockitoAnnotations.openMocks(this);
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .ignoreRandomizationErrors(true);
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    @DisplayName("사용자 중복체크 (카카오) - 1개의 카카오계정은 1개의 이메일을 가집니다 (중복 불가능)")
    public void testIsExistUserForKakao() {
        // KAKAO
        PlatformType platformType = PlatformType.KAKAO;

        // Request Data 생성
        CheckExistUserRequest request = easyRandom.nextObject(CheckExistUserRequest.class);
        request.setEmail(email);
        request.setPlatformType(platformType.name());

        // Mock Account 생성
        Account mockedAccount = AccountFixtureFactory.create(email, platformType);
        mockedAccount.setEmail(email);
        mockedAccount.setPlatformType(PlatformType.KAKAO);

        // AccountRepository의 findByEmailAndPlatformType 메소드가 호출될 때 MockedAccount를 반환하도록 설정
        doReturn(mockedAccount)
                .when(accountRepository)
                .findByEmailAndPlatformType(eq(email), eq(PlatformType.KAKAO));

        // 테스트 대상 메소드 호출
        boolean isExist = checkService.isExistUser(request);

        // 검증: repository 메소드 호출 확인 및 결과 검증
        assertTrue(isExist);

        // 마지막 로그인 날짜가 설정되었는지 확인
        assertNotNull(mockedAccount.getLastLoginDate());

        // 검증: accountRepository 메소드 호출 확인
        verify(accountRepository).findByEmailAndPlatformType(eq(request.getEmail()), eq(PlatformType.KAKAO));
    }

    @Test
    @DisplayName("사용자 중복체크 (서비스) - 1개의 서비스계정은 1개의 이메일을 가집니다 (중복 불가능)")
    public void testIsExistUserForNonsocial() {
        // NONSOCIAL
        PlatformType platformType = PlatformType.NONSOCIAL;

        // Request Data 생성
        CheckExistUserRequest request = easyRandom.nextObject(CheckExistUserRequest.class);
        request.setEmail(email);
        request.setPlatformType(platformType.name());

        // Mock Account 생성
        Account mockedAccount = AccountFixtureFactory.create(email, platformType);
        mockedAccount.setEmail(email);
        mockedAccount.setPlatformType(PlatformType.NONSOCIAL);

        // AccountRepository의 findByEmailAndPlatformType 메소드가 호출될 때 MockedAccount를 반환하도록 설정
        doReturn(mockedAccount)
                .when(accountRepository)
                .findByEmailAndPlatformType(eq(email), eq(PlatformType.NONSOCIAL));

        // 테스트 대상 메소드 호출
        boolean isExist = checkService.isExistUser(request);

        // 검증: repository 메소드 호출 확인 및 결과 검증
        assertTrue(isExist);

        // 마지막 로그인 날짜가 설정되었는지 확인
        assertNotNull(mockedAccount.getLastLoginDate());

        // 검증: accountRepository 메소드 호출 확인
        verify(accountRepository).findByEmailAndPlatformType(eq(request.getEmail()), eq(PlatformType.NONSOCIAL));
    }



}
