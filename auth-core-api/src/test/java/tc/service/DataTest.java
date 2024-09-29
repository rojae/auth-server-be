package tc.service;

import io.github.rojae.authcoreapi.AuthCoreApiApplication;
import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import io.github.rojae.authcoreapi.service.CheckService;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.ObjectCreationException;
import org.jeasy.random.randomizers.misc.EnumRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import randomizer.EmailRandomizer;
import randomizer.PlatformTypeRandomizer;
import tc.mariadb.MariadbContainer;

import static org.jeasy.random.TypePredicates.named;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)      // FOR TC
@SpringBootTest(classes = AuthCoreApiApplication.class)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {DataTest.Initializer.class})
public class DataTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    // @ClassRule: junit4, @Container: junit5
    @Container
    public static MariaDBContainer<MariadbContainer> mariadbContainer = MariadbContainer.getInstance();

    @Autowired
    private CheckService checkService;

    private EasyRandom easyRandom;

    private final String email = "test@email.com";
    private final String email2 = "test2@email.com";

    @BeforeEach
    public void setUp() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .ignoreRandomizationErrors(true);
        easyRandom = new EasyRandom(parameters);
    }


    @Test
    @DisplayName("사용자 중복체크 - init.sql's database insert test")
    public void test() {
        // KAKAO
        PlatformType platformType = PlatformType.NONSOCIAL;

        // Request Data 생성
        CheckExistUserRequest request = easyRandom.nextObject(CheckExistUserRequest.class);
        request.setEmail(email);
        request.setPlatformType(platformType.name());

        // 테스트 대상 메소드 호출
        boolean isExist = checkService.isExistUser(request);
        assertTrue(isExist);
    }

    @Test
    @DisplayName("사용자 중복체크 - init.sql's database insert test")
    public void test2() {
        // KAKAO
        PlatformType platformType = PlatformType.NONSOCIAL;

        // Request Data 생성
        CheckExistUserRequest request = easyRandom.nextObject(CheckExistUserRequest.class);
        request.setEmail(email2);
        request.setPlatformType(platformType.name());

        // 테스트 대상 메소드 호출
        boolean isExist = checkService.isExistUser(request);
        assertTrue(isExist);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mariadbContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mariadbContainer.getUsername(),
                    "spring.datasource.password=" + mariadbContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
