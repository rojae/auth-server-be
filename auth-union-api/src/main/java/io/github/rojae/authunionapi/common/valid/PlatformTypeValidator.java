package io.github.rojae.authunionapi.common.valid;


import io.github.rojae.authunionapi.common.enums.PlatformType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlatformTypeValidator implements ConstraintValidator<PlatformTypeValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !PlatformType.valueOfName(value).equals(PlatformType.UNKNOWN);
    }
}
