package randomizer;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

// 이메일 형식 Randomizer
public class EmailRandomizer implements Randomizer<String> {
    private final Random random = new Random();

    @Override
    public String getRandomValue() {
        return "user" + random.nextInt(1000) + "@example.com";  // 이메일 형식으로 랜덤 값 생성
    }
}