package io.github.rojae.authserver.common.valid;

import io.github.rojae.authserver.common.props.JwtProps;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class AuthorizationValidator implements ConstraintValidator<Authorization, String> {

    private final JwtProps jwtProps;

    public AuthorizationValidator(JwtProps jwtProps) {
        this.jwtProps = jwtProps;
    }

    @Override
    public void initialize(Authorization constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
