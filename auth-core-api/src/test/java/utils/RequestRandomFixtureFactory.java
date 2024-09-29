package utils;

import io.github.rojae.authcoreapi.dto.CheckExistUserRequest;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.ObjectCreationException;
import org.junit.jupiter.api.DisplayName;
import randomizer.EmailRandomizer;
import randomizer.PlatformTypeRandomizer;

import javax.transaction.SystemException;

public class RequestRandomFixtureFactory {

    @DisplayName("create UserRequest 객체 생성")
    static public CheckExistUserRequest createCheckExistUserRequest(){
        // EasyRandomParameters로 특정 필드 커스터마이즈
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(FieldPredicates.named("email"), new EmailRandomizer())
                .randomize(FieldPredicates.named("platformType"), new PlatformTypeRandomizer());

        // EasyRandom 객체 생성
        EasyRandom easyRandom = new EasyRandom(parameters);

        try {
            return easyRandom.nextObject(CheckExistUserRequest.class);
        }
        catch (ObjectCreationException e) {
            e.printStackTrace(); // 에러의 원인과 스택 트레이스를 확인
        }
        throw new RuntimeException();
    }

}
