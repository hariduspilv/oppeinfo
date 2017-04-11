package ee.hitsa.ois.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ee.hitsa.ois.util.Period;

public class PeriodRangeValidator implements ConstraintValidator<PeriodRange, Period> {

    @Override
    public void initialize(PeriodRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(Period value, ConstraintValidatorContext context) {
        Boolean isPeriod = value.getIsPeriod();
        if(isPeriod == null) {
            return false;
        }
        if(Boolean.TRUE.equals(isPeriod)) {
            if(value.getStudyPeriodStart() == null || value.getStudyPeriodEnd() == null) {
                return false;
            }
        } else {
            if(value.getStartDate() == null || value.getEndDate() == null) {
                return false;
            }
        }

        return true;
    }
}
