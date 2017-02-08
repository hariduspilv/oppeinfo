package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.Period;

import ee.hitsa.ois.domain.Person;

public abstract class PersonUtil {

    private static final int ADULT_YEARS = 18;

    public static boolean isAdult(Person person) {
        return Period.between(person.getBirthdate(), LocalDate.now()).getYears() >= ADULT_YEARS;
    }
}
