package ee.hitsa.ois.util;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.domain.Person;

public class PersonUtilTests {

    @Test
    public void testIsAdult() {
        Person person = new Person();
        person.setBirthdate(LocalDate.now().minusYears(1));
        Assert.assertFalse(PersonUtil.isAdult(person));
        person.setBirthdate(LocalDate.now().minusYears(17));
        Assert.assertFalse(PersonUtil.isAdult(person));
        person.setBirthdate(LocalDate.now().minusYears(18));
        Assert.assertTrue(PersonUtil.isAdult(person));
        person.setBirthdate(LocalDate.now().minusYears(19));
        Assert.assertTrue(PersonUtil.isAdult(person));

        //test birthdate from idcode is checked first
        person.setBirthdate(LocalDate.now().minusYears(1));
        person.setIdcode("47101010033");
        Assert.assertTrue(PersonUtil.isAdult(person));

        //test extracting birth date from idcode
        person.setBirthdate(null);
        person.setIdcode("47101010033");
        Assert.assertTrue(PersonUtil.isAdult(person));
        person.setIdcode("61701012233");
        Assert.assertFalse(PersonUtil.isAdult(person));

        //birth date not available
        person.setBirthdate(null);
        person.setIdcode(null);
        Assert.assertTrue(PersonUtil.isAdult(person));

    }
}
