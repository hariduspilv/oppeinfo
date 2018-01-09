package ee.hitsa.ois.validation;

import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.validation.ApplicationValidation.Akad;
import ee.hitsa.ois.validation.ApplicationValidation.Akadk;
import ee.hitsa.ois.validation.ApplicationValidation.Eksmat;
import ee.hitsa.ois.validation.ApplicationValidation.Finm;
import ee.hitsa.ois.validation.ApplicationValidation.Okava;
import ee.hitsa.ois.validation.ApplicationValidation.Ovorm;
import ee.hitsa.ois.validation.ApplicationValidation.Valis;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ApplicationTests {

    @Autowired
    private Validator validator;

    @Test
    public void validate() {
        Application application = new Application();
        validator.validate(application, Akad.class);
        validator.validate(application, Akadk.class);
        validator.validate(application, Eksmat.class);
        validator.validate(application, Finm.class);
        validator.validate(application, Okava.class);
        validator.validate(application, Ovorm.class);
        validator.validate(application, Valis.class);
    }
}
