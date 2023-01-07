package io.github.rojae.socialapi.common.valid;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlatformTypeValidator.class)
public @interface PlatformTypeValid {
    String message() default "정의된 platformType이 아닙니다";
    Class[] groups() default {};
    Class[] payload() default {};
}