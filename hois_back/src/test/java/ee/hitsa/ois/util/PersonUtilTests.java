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

    @Test
    public void testFullname() {
        Assert.assertNotNull(PersonUtil.fullname(null, "Last"));
        Assert.assertNotNull(PersonUtil.fullname("First", "Last"));
        Person person = new Person();
        person.setFirstname(null);
        person.setLastname("Last");
        Assert.assertNotNull(PersonUtil.fullname(person));
        person.setFirstname("First");
        Assert.assertNotNull(PersonUtil.fullname(person));
    }

    @Test
    public void fullnameAndIdcode() {
        Assert.assertNotNull(PersonUtil.fullnameAndIdcode(null, "Last", "47101010033"));
        Assert.assertNotNull(PersonUtil.fullnameAndIdcode("First", "Last", "47101010033"));
        Person person = new Person();
        person.setFirstname(null);
        person.setLastname("Last");
        person.setIdcode("47101010033");
        Assert.assertNotNull(PersonUtil.fullnameAndIdcode(person));
        person.setFirstname("First");
        Assert.assertNotNull(PersonUtil.fullnameAndIdcode(person));
    }

    @Test
    public void stripIdcodeFromFullnameAndIdcode() {
        String name = "First Last";
        String noIdcode = name + " (12345678901)";
        Assert.assertEquals(noIdcode, PersonUtil.stripIdcodeFromFullnameAndIdcode(noIdcode));
        String idcode = name + " (47101010033)";
        Assert.assertEquals(name, PersonUtil.stripIdcodeFromFullnameAndIdcode(idcode));
    }
}
