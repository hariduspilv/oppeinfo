package ee.hitsa.ois.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.util.AssertionFailedException;

public class StudyPeriodRangeValidator implements ConstraintValidator<StudyPeriodRange, Object> {

    private StudyPeriodRange constraint;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;

    @Override
    public void initialize(StudyPeriodRange constraintAnnotation) {
        this.constraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Long from = (Long)reader.getPropertyValue(constraint.from());
        Long thru = (Long)reader.getPropertyValue(constraint.thru());
        if(from == null || thru == null) {
            return true;
        }

        StudyPeriod fromPeriod = studyPeriodRepository.getOne(from);
        if (fromPeriod == null) {
            throw new AssertionFailedException("from period not found");
        }
        StudyPeriod thruPeriod = studyPeriodRepository.getOne(thru);
        if (thruPeriod == null) {
            throw new AssertionFailedException("thru period not found");
        }

        return thruPeriod.getStartDate() == null || fromPeriod.getEndDate() == null
                || fromPeriod == thruPeriod || !thruPeriod.getStartDate().isBefore(fromPeriod.getEndDate());
    }

    public void setStudyPeriodRepository(StudyPeriodRepository studyPeriodRepository) {
        this.studyPeriodRepository = studyPeriodRepository;
    }

}
