package ee.hitsa.ois.validation;

import org.junit.Assert;
import org.junit.Test;

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
}
