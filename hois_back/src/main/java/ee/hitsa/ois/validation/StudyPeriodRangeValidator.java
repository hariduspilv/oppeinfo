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

        StudyPeriod fromPeriod = getStudyPeriod(reader.getPropertyValue(constraint.from()), true);
        StudyPeriod thruPeriod = getStudyPeriod(reader.getPropertyValue(constraint.thru()), false);
        if (fromPeriod == null || thruPeriod == null) {
            return true;
        }

        return thruPeriod.getStartDate() == null || fromPeriod.getEndDate() == null
                || fromPeriod == thruPeriod || !thruPeriod.getStartDate().isBefore(fromPeriod.getEndDate());
    }

    public void setStudyPeriodRepository(StudyPeriodRepository studyPeriodRepository) {
        this.studyPeriodRepository = studyPeriodRepository;
    }

    private StudyPeriod getStudyPeriod(Object value, boolean from) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            StudyPeriod period = studyPeriodRepository.getOne((Long)value);
            if (period == null) {
                throw new AssertionFailedException(from ? "from period not found" : "thru period not found");
            }
            return period;
        }
        if (value instanceof StudyPeriod) {
            return (StudyPeriod)value;
        }
        throw new AssertionFailedException(from ? "unable to load from period" : "unable to load thru period");
    }
}
