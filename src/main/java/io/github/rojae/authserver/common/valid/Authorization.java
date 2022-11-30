package io.github.rojae.authserver.common.valid;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "Authorization 헤더는 빈 공간을 가질 수 없습니다.")
@Constraint(validatedBy = {AuthorizationValidator.class})
public @interface Authorization {
    String message() default "";
    Class[] groups() default {};
    Class[] payload() default {};
}