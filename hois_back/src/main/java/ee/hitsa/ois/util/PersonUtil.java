package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.Period;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;

public abstract class PersonUtil {

    private static final int ADULT_YEARS = 18;

    public static boolean isAdult(Person person) {
        LocalDate birthdate = person.getBirthdate();
        if (birthdate == null) {
            birthdate = EstonianIdCodeValidator.birthdateFromIdcode(person.getIdcode());
        }
        // if information about birth date is missing then person is considered as an adult
        return birthdate == null ? true : Period.between(birthdate, LocalDate.now()).getYears() >= ADULT_YEARS;
    }

    public static String fullname(String firstname, String lastname) {
        // firstname is optional
        return firstname == null ? lastname : (firstname + " " + lastname);
    }

    public static String fullnameAndIdcode(String firstname, String lastname, String idcode) {
        return fullname(firstname, lastname) + " (" + idcode + ")";
    }
}
