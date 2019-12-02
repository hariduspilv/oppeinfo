package ee.hitsa.ois.validation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {
    
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String selected;
    private String[] required;
    private String message;
    private String[] values;

    @Override
    public void initialize(Conditional requiredIfChecked) {
        selected = requiredIfChecked.selected();
        required = requiredIfChecked.required();
        message = requiredIfChecked.message();
        values = requiredIfChecked.values();
    }

    @Override
    public boolean isValid(Object objectToValidate, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            Object actualValue = BeanUtils.getProperty(objectToValidate, selected);
            Set<String> stringValues = new HashSet<>(Arrays.asList(values));
            if (stringValues.contains(actualValue) || (stringValues.contains("null") && actualValue == null)) {
                for (String propName : required) {
                    Object requiredValue = BeanUtils.getProperty(objectToValidate, propName);
                    boolean isFieldValid = isFieldValid(requiredValue);
                    if (!isFieldValid) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(message).addPropertyNode(propName).addConstraintViolation();
                        valid = false;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Accessor method is not available for class : {}, exception : {}", objectToValidate.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            log.error("Field or method is not present on class : {}, exception : {}", objectToValidate.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            log.error("An exception occurred while accessing class : {}, exception : {}", objectToValidate.getClass().getName(), e);
            e.printStackTrace();
            return false;
        }
        return valid;
    }
    
    /**
     * Same method as in Required validator
     */
    public boolean isFieldValid(Object value) {
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