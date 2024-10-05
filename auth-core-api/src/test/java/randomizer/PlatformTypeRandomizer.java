package randomizer;

import io.github.rojae.authcoreapi.common.enums.PlatformType;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.misc.EnumRandomizer;

// PlatformType Randomizer
public class PlatformTypeRandomizer implements Randomizer<String> {

    @Override
    public String getRandomValue() {
        EnumRandomizer<PlatformType> randomizer = new EnumRandomizer<>(PlatformType.class);
        PlatformType randomValue = randomizer.getRandomValue();

        // UNKNOWN 타입은 제외합니다.
        if(PlatformType.UNKNOWN == randomValue)
            return getRandomValue();
        else
            return randomValue.name();
    }

}