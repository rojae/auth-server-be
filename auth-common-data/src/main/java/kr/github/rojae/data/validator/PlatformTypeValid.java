package kr.github.rojae.data.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlatformTypeValidator.class)
public @interface PlatformTypeValid {
    String message() default "정의된 platformType이 아닙니다";
    Class[] groups() default {};
    Class[] payload() default {};
}