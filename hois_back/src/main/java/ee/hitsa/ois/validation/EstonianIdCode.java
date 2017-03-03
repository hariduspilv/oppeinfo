package ee.hitsa.ois.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {EstonianIdCodeValidator.class, EstonianIdCodeValidator.PersonValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EstonianIdCode {
    String message() default "InvalidEstonianIdCode";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
