package ee.hitsa.ois.validation;

import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.validation.DirectiveValidation.Akad;
import ee.hitsa.ois.validation.DirectiveValidation.Akadk;
import ee.hitsa.ois.validation.DirectiveValidation.Eksmat;
import ee.hitsa.ois.validation.DirectiveValidation.Ennist;
import ee.hitsa.ois.validation.DirectiveValidation.Finm;
import ee.hitsa.ois.validation.DirectiveValidation.Immat;
import ee.hitsa.ois.validation.DirectiveValidation.Lopet;
import ee.hitsa.ois.validation.DirectiveValidation.Okava;
import ee.hitsa.ois.validation.DirectiveValidation.Okoorm;
import ee.hitsa.ois.validation.DirectiveValidation.Ovorm;
import ee.hitsa.ois.validation.DirectiveValidation.Valis;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class DirectiveStudentTests {

    @Autowired
    private Validator validator;

    @Test
    public void validate() {
        DirectiveStudent ds = new DirectiveStudent();
        validator.validate(ds, Akad.class);
        validator.validate(ds, Akadk.class);
        validator.validate(ds, Eksmat.class);
        validator.validate(ds, Ennist.class);
        validator.validate(ds, Finm.class);
        validator.validate(ds, Immat.class);
        validator.validate(ds, Lopet.class);
        validator.validate(ds, Okava.class);
        validator.validate(ds, Okoorm.class);
        validator.validate(ds, Ovorm.class);
        validator.validate(ds, Valis.class);
    }
}
