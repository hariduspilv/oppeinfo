package ee.hitsa.ois.validation;

import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class RequiredValidator implements ConstraintValidator<Required, Object> {

    @Override
    public void initialize(Required constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // notnull
        if(value == null) {
            return false;
        }
        // size min = 1
        if(value instanceof Collection) {
            return !((Collection<?>)value).isEmpty();
        }
        if(value instanceof Map) {
            return !((Map<?, ?>)value).isEmpty();
        }
        if(value instanceof CharSequence) {
            return StringUtils.hasText((CharSequence)value);
        }
        // XXX is array validation required?
        return true;
    }
}
