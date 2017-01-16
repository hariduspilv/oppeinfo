package ee.hitsa.ois.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithVersionedEntity {
    String value();
    /**
     * Take version value from {@link org.springframework.web.bind.annotation.PathVariable} with given name
     */
    String versionPathVariable() default "";
    /**
     * Take version value from {@link org.springframework.web.bind.annotation.RequestParam} with given name
     */
    String versionRequestParam() default "";
    /**
     * Take version value from {@link @org.springframework.web.bind.annotation.RequestBody} annotated parameter. Parameter should implement {@link Versioned} interface
     */
    boolean versionRequestBody() default false;
}
