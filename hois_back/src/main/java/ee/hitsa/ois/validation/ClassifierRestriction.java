package ee.hitsa.ois.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ee.hitsa.ois.enums.MainClassCode;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassifierRestriction {
    MainClassCode[] value();
    boolean useClassifierValue() default false;
}
