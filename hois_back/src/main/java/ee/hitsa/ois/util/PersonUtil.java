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
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentType;
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
    
    public static String fullname(Student student) {
        if (student.getPerson() == null) return null;
        if (!StudentType.OPPUR_K.name().equals(EntityUtil.getNullableCode(student.getType()))) {
            return fullname(student.getPerson());
        }
        return fullname(student.getPerson()) + " (KY)";
    }

    public static String fullnameAndIdcode(String firstname, String lastname, String idcode) {
        return fullnameAndIdcode(fullname(firstname, lastname), idcode);
    }
    
    public static String fullnameAndIdcodeOptionalGuest(String firstname, String lastname, String idcode, String type) {
        return fullnameAndIdcodeOptionalGuest(fullname(firstname, lastname), idcode, type);
    }
    
    public static String fullnameOptionalGuest(String firstname, String lastname, String type) {
        if (!StudentType.OPPUR_K.name().equals(type)) {
            return fullname(firstname, lastname);
        }
        return fullname(firstname, lastname) + " (KY)";
    }
    
    public static String fullnameOptionalGuest(String fullname, String type) {
        if (!StudentType.OPPUR_K.name().equals(type)) {
            return fullname;
        }
        return fullname + " (KY)";
    }

    public static String fullnameAndIdcode(String fullname, String idcode) {
        if(!StringUtils.hasText(idcode)) {
            return fullname;
        }
        // if format of this string is changed, adjust also IDCODE_PATTERN in this file
        return fullname + " (" + idcode + ")";
    }
    
    public static String fullnameAndIdcodeOptionalGuest(String fullname, String idcode, String type) {
        if(StringUtils.hasText(idcode) && StudentType.OPPUR_K.name().equals(type)) {
            return fullname + " (" + idcode + " KY)";
        } else if (StringUtils.hasText(idcode)) {
            return  fullname + " (" + idcode + ")";
        } else if (StudentType.OPPUR_K.name().equals(type)) {
            return  fullname + " (KY)";
        }
        return fullname;
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
    
//    /**
//     * TODO Create Tests for this one.
//     * 
//     * @param args
//     */
//    public static void main(String[] args) {
//        Arrays.asList("O’CONNEŽ-ŠUSLIK TESTNUMBER MARY ÄNN", "ALEXANDER VON HUMBOLDT", "VOLVO", "TEST VONVON", "TEST 8NAME",
//                "CARL ABCÄÖÜÕ-ŽŠ", "PEGGY SUGGY", "PILVI VALIKOVILVI", "DAR'YA MURUMÄGI", "KADRI KOVVALAG", "PAUL KULD",
//                "KALLE VALIKOVILLE", "KRIS LUTSVVK", "IRA ŽEN-ŠUN", "WILLEM VORR", "LIISA CASK", "MIRRY METSSAR",
//                "EVELLIINA-MARGARIITA KUUSEOKS-KUUSEPUU", "MART RAUDSEPP-LIIK", "RENEK METSASOO", "KAIDO MONROVIA",
//                "KARIN VALIKOVIRIN", "ALEKSANDERA LIANNYI", "INGVER VALIKOVIVER").forEach(fullname -> {
//                    System.out.println(String.format("Before: %s\nAfter REGEX: %s\n", fullname, initCapName(fullname)));
//                });
//    }
    
    /**
     * WorldUtils.capitalizeFully does not work as postgresql initcap.
     * Java Regex cannot user \L to make characters into lower case. It should be done via toLowerCase manually.
     * 
     * How it works:
     * It finds any letter or digit and then takes as group letters (which can be transformed to lowercase) or digits (stops on any other symbol as "-+*'" etc)
     * 
     * @param fullname
     */
    public static String initCapName(String fullname) {
        // To make it ignore some words regex should be this way:
        // ((?<=[\\p{L}])([\\p{Lu}]*)|(?<=\\s)(%s)(?=\\s)) - where %s should be similar to String.join("|", INITCAP_EXCLUDING_WORDS)
        // and similar to this list (any iterable):
        // private static final String[] INITCAP_EXCLUDING_WORDS = new String[] {"VON"};
        Matcher matcher = Pattern.compile("(?<=[\\p{L}\\d])([\\p{Lu}\\d]*)").matcher(fullname);
        StringBuilder builder = new StringBuilder();
        
        int last = 0;
        while (matcher.find()) {
            builder.append(fullname.substring(last, matcher.start()));
            builder.append(matcher.group(0).toLowerCase());
            last = matcher.end();
        }
        builder.append(fullname.substring(last));
        return builder.toString();
    }
    
}
