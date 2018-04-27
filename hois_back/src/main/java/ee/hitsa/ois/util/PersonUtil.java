package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;

/**
 * Utility functions for person
 */
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
        return fullnameAndIdcode(fullname(firstname, lastname), idcode);
    }

    public static String fullnameAndIdcode(String fullname, String idcode) {
        if(!StringUtils.hasText(idcode)) {
            return fullname;
        }
        // if format of this string is changed, adjust also IDCODE_PATTERN in this file
        return fullname + " (" + idcode + ")";
    }

    public static String fullnameAndIdcode(Person person) {
        return fullnameAndIdcode(fullname(person), person.getIdcode());
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

    /**
     * Returns estonian idcode-like value (see regex IDCODE_PATTERN in this file) from string.
     * @param fullnameAndIdcode
     * @return null if fullnameAndIdcode is null or match is not found
     */
    public static String idcodeFromFullnameAndIdcode(String fullnameAndIdcode) {
        if(fullnameAndIdcode != null) {
            Matcher m = IDCODE_PATTERN.matcher(fullnameAndIdcode);
            if(m.find()) {
                String idcode = m.group();
                // strip parenthesis
                return idcode.substring(1, idcode.length() - 1);
            }
        }
        return null;
    }

    public static final Comparator<Person> SORT = Comparator.comparing(Person::getLastname, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Comparator.comparing(Person::getFirstname, String.CASE_INSENSITIVE_ORDER));

    /**
     * Returns list of person names sorted by name
     * @param stream
     * @return
     */
    public static List<String> sorted(Stream<Person> stream) {
        return stream.sorted(PersonUtil.SORT).map(PersonUtil::fullname).collect(Collectors.toList());
    }
    
    /**
     * Removes some values if necessary
     * @param person
     */
    public static void conditionalClean(Person person) {
        if (!ClassifierUtil.isEstonia(person.getResidenceCountry())) {
            person.setAddressAds(null);
            person.setAddressAdsOid(null);
        }
    }
}
