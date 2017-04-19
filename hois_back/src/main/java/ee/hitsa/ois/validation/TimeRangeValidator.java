package ee.hitsa.ois.validation;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

public class TimeRangeValidator implements ConstraintValidator<TimeRange, Object> {

    private TimeRange constraint;

    @Override
    public void initialize(TimeRange constraintAnnotation) {
        constraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(value);
        LocalTime from = (LocalTime)reader.getPropertyValue(constraint.from());
        if(from == null) {
            return true;
        }
        LocalTime thru = (LocalTime)reader.getPropertyValue(constraint.thru());
        return thru == null || !thru.isBefore(from);
    }
}
