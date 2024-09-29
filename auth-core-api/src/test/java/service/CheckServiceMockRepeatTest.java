package service;

import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.domain.Account;
import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import io.github.rojae.authcoreapi.persistence.AccountRepository;
import io.github.rojae.authcoreapi.service.CheckService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.AccountFixtureFactory;
import utils.RequestRandomFixtureFactory;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CheckServiceMockRepeatTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CheckService checkService;

    private final String email = "test@email.com";

    private static final int TOTAL_REQUESTS = 100; // 총 요청 수
    private static final int THREAD_COUNT = 10; // 동시 실행 스레드 수

    @RepeatedTest(value = 1000, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("isExist TPS Test - 1")
    public void isExistUser1() {
        CheckExistUserRequest request = RequestRandomFixtureFactory.createCheckExistUserRequest(); // 랜덤 요청 생성
        boolean existUser = checkService.isExistUser(request); // 존재 여부 확인
        Assertions.assertFalse(existUser); // false여야 하는지 확인
    }

    @RepeatedTest(value = 1000, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("isExist TPS Test - 2")
    public void isExistUser2() {
        // Mock Account 생성
        Account mockedAccount1 = AccountFixtureFactory.create(email, PlatformType.KAKAO);
        mockedAccount1.setEmail(email);
        mockedAccount1.setPlatformType(PlatformType.KAKAO);

        Account mockedAccount2 = AccountFixtureFactory.create(email, PlatformType.NONSOCIAL);
        mockedAccount2.setEmail(email);
        mockedAccount2.setPlatformType(PlatformType.NONSOCIAL);

        // Mock behavior for both accounts
        doReturn(mockedAccount1)
                .when(accountRepository)
                .findByEmailAndPlatformType(eq(email), eq(PlatformType.KAKAO));

        doReturn(mockedAccount2)
                .when(accountRepository)
                .findByEmailAndPlatformType(eq(email), eq(PlatformType.NONSOCIAL));

        // Repeat Test
        for (int i = 0; i < 1000; i++) {
            PlatformType platformType = (i % 2 == 0) ? PlatformType.KAKAO : PlatformType.NONSOCIAL;
            CheckExistUserRequest request = new CheckExistUserRequest(email, platformType.name()); // 랜덤 요청 생성
            boolean existUser = checkService.isExistUser(request); // 존재 여부 확인
            Assertions.assertTrue(existUser); // true여야 하는지 확인
        }
    }

}
