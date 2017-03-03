package ee.hitsa.ois.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private DateRange constraint;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.constraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(value);
        LocalDate from = (LocalDate)reader.getPropertyValue(constraint.from());
        if(from == null) {
            return true;
        }
        LocalDate thru = (LocalDate)reader.getPropertyValue(constraint.thru());
        return thru == null || !thru.isBefore(from);
    }
}
