package randomizer;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

// Enum Randomizer
class EnumRandomizer<T extends Enum<T>> implements Randomizer<T> {
    private final T[] enumConstants;
    private final Random random = new Random();

    public EnumRandomizer(Class<T> enumClass) {
        this.enumConstants = enumClass.getEnumConstants();
    }

    @Override
    public T getRandomValue() {
        int index = random.nextInt(enumConstants.length);
        return enumConstants[index];  // Enum 값 중 랜덤하게 반환
    }
}