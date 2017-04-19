package ee.hitsa.ois.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = TimeRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TimeRanges.class)
public @interface TimeRange {
    String from() default "startTime";
    String thru() default "endTime";
    boolean allowPast() default true;

    String message() default "InvalidTimeRange";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
