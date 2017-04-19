package ee.hitsa.ois.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateTimeRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DateTimeRanges.class)
public @interface DateTimeRange {
    String from() default "validFrom";
    String thru() default "validThru";
    boolean allowPast() default true;

    String message() default "InvalidDateRange";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
