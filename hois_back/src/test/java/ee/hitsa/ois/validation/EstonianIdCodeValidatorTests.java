package ee.hitsa.ois.validation;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;

public class EstonianIdCodeValidatorTests {

    private final EstonianIdCodeValidator validator = new EstonianIdCodeValidator();

    @Test
    public void testEmpty() {
        Assert.assertTrue("Null string does not pass", validator.isValid(null, null));
        Assert.assertTrue("Empty string does not pass", validator.isValid("", null));
    }

    @Test
    public void testInvalid() {
        Assert.assertFalse("Invalid idcode passes", validator.isValid("1", null));
        Assert.assertFalse("Invalid idcode passes", validator.isValid("02345678901", null));
        Assert.assertFalse("Invalid idcode passes", validator.isValid("12345678901", null));
        Assert.assertFalse("Invalid idcode passes", validator.isValid("42311318901", null));
        Assert.assertFalse("Invalid idcode passes", validator.isValid("12310178901", null));
    }

    @Test
    public void testValid() {
        Assert.assertTrue("Valid idcode does not pass", validator.isValid("47101010033", null));
        Assert.assertTrue("Valid idcode does not pass", validator.isValid("37101010021", null));
        Assert.assertTrue("Valid idcode does not pass", validator.isValid("48908209998", null));
        Assert.assertTrue("Valid idcode does not pass", validator.isValid("36908209993", null));
    }

    @Test
    public void testObject() {
        TeacherForm.TeacherPersonForm person = new TeacherForm.TeacherPersonForm();
        EstonianIdCodeValidator.PersonValidator personValidator = new EstonianIdCodeValidator.PersonValidator();

        Assert.assertTrue("Missing idcode does not pass", personValidator.isValid(person, null));
        person.setIdcode("47101010033");
        Assert.assertFalse("Missing birthdate and missing sex does pass", personValidator.isValid(person, null));
        person.setBirthdate(LocalDate.of(1971, 01, 01));
        Assert.assertFalse("Missing sex does pass", personValidator.isValid(person, null));
        person.setSex("SEX");
        Assert.assertFalse("Wrong sex does pass", personValidator.isValid(person, null));
        person.setSex("SUGU_M");
        Assert.assertFalse("Wrong sex does pass", personValidator.isValid(person, null));
        person.setSex("SUGU_N");
        Assert.assertTrue("Collect sex does not pass", personValidator.isValid(person, null));
    }
}
