package ee.hitsa.ois.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

public class PeriodRangeValidator implements ConstraintValidator<PeriodRange, Object> {

    @Override
    public void initialize(PeriodRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Boolean isPeriod = (Boolean)reader.getPropertyValue("isPeriod");
        if(isPeriod == null) {
            return false;
        }
        if(Boolean.TRUE.equals(isPeriod)) {
            if(reader.getPropertyValue("studyPeriodStart") == null || reader.getPropertyValue("studyPeriodEnd") == null) {
                return false;
            }
        } else {
            if(reader.getPropertyValue("startDate") == null || reader.getPropertyValue("endDate") == null) {
                return false;
            }
        }

        return true;
    }
}
