package io.github.rojae.oauth2api.common.valid;


import io.github.rojae.oauth2api.common.enums.PlatformType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlatformTypeValidator implements ConstraintValidator<PlatformTypeValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !PlatformType.valueOfName(value).equals(PlatformType.UNKNOWN);
    }
}
