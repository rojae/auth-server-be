package randomizer;

import io.github.rojae.authcoreapi.common.enums.PlatformType;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.misc.EnumRandomizer;

// 이메일 형식 Randomizer
public class PlatformTypeRandomizer implements Randomizer<String> {

    @Override
    public String getRandomValue() {
        EnumRandomizer<PlatformType> randomizer = new EnumRandomizer<>(PlatformType.class);
        return randomizer.getRandomValue().name();
    }
}