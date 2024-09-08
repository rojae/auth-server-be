package kr.github.rojae.data.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import kr.github.rojae.data.enums.PlatformType;

public class PlatformTypeValidator implements ConstraintValidator<PlatformTypeValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !PlatformType.valueOfName(value).equals(PlatformType.UNKNOWN);
    }
}
