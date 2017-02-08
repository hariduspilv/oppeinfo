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
        LocalDate from = read(value, constraint.from());
        if(from == null) {
            return true;
        }
        LocalDate thru = read(value, constraint.thru());
        return thru == null || !thru.isBefore(from);
    }

    private static LocalDate read(Object bean, String propertyName) {
        PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        return (LocalDate)reader.getPropertyValue(propertyName);
    }
}
