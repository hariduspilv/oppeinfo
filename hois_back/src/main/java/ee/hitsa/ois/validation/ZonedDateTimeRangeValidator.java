package ee.hitsa.ois.validation;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.ZonedDateTime;

public class ZonedDateTimeRangeValidator implements ConstraintValidator<ZonedDateTimeRange, Object> {

    private ZonedDateTimeRange constraint;

    @Override
    public void initialize(ZonedDateTimeRange constraintAnnotation) {
        this.constraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(value);
        ZonedDateTime from = (ZonedDateTime) reader.getPropertyValue(constraint.from());
        if(from == null) {
            return true;
        }
        ZonedDateTime thru = (ZonedDateTime)reader.getPropertyValue(constraint.thru());
        return thru == null || (!thru.isBefore(from) && (constraint.allowPast() || !from.isBefore(ZonedDateTime.now())));
    }
}
