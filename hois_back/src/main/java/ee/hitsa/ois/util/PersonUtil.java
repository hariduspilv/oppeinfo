package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;

public abstract class PersonUtil {

    private static final Pattern IDCODE_PATTERN = Pattern.compile("\\(([1-6])(\\d{2})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d)\\)");
    public static final Long AUTOMATIC_SENDER_ID = Long.valueOf(-1L);
    public static final int ADULT_YEARS = 18;

    public static boolean isAdult(Person person) {
        LocalDate birthdate = person.getBirthdate();
        if (birthdate == null) {
            birthdate = EstonianIdCodeValidator.birthdateFromIdcode(person.getIdcode());
        }
        // if information about birth date is missing then person is considered as an adult
        return birthdate == null || Period.between(birthdate, LocalDate.now()).getYears() >= ADULT_YEARS;
    }

    public static String fullname(String firstname, String lastname) {
        // firstname is optional
        return firstname == null ? lastname : (firstname + " " + lastname);
    }

    public static String fullname(Person person) {
        return fullname(person.getFirstname(), person.getLastname());
    }

    public static String fullnameAndIdcode(String firstname, String lastname, String idcode) {
        // if format of this string is changed, adjust also IDCODE_PATTERN
        return fullname(firstname, lastname) + " (" + idcode + ")";
    }

    public static String fullnameAndIdcode(Person person) {
        return fullnameAndIdcode(person.getFirstname(), person.getLastname(), person.getIdcode());
    }

    /**
     * Strips estonian idcode-like values (see regex IDCODE_PATTERN in this file) from string.
     * Format of string should be same as fullnameAndIdcode produces fullname (idcode)
     *
     * @param fullnameAndIdcode
     * @return null if fullnameAndIdcode is null
     */
    public static String stripIdcodeFromFullnameAndIdcode(String fullnameAndIdcode) {
        return fullnameAndIdcode != null ? IDCODE_PATTERN.matcher(fullnameAndIdcode).replaceAll("").trim() : null;
    }
}
