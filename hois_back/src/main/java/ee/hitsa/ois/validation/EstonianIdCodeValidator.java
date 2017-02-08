package ee.hitsa.ois.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EstonianIdCodeValidator implements ConstraintValidator<EstonianIdCode, String> {

    private static final Pattern PATTERN = Pattern.compile("^([1-6])(\\d{2})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d)$");
    private static final int[] WEIGHT1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    private static final int[] WEIGHT2 = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
    private static final Map<String, String> CENTURY = new HashMap<>();

    static {
        CENTURY.put("1", "18");
        CENTURY.put("2", "18");
        CENTURY.put("3", "19");
        CENTURY.put("4", "19");
        CENTURY.put("5", "20");
        CENTURY.put("6", "20");
    }

    @Override
    public void initialize(EstonianIdCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return true;
        }
        Matcher m = PATTERN.matcher(value);
        if(m.matches()) {
            // verify checksum
            int sum = 0;
            for(int pos = 0; pos < 10; pos++) {
                sum += (WEIGHT1[pos] * Character.digit(value.charAt(pos), 10));
            }
            sum %= 11;
            int checksum = Integer.parseInt(m.group(5));
            if(sum != 10) {
                if(sum != checksum) {
                    return false;
                }
            }else{
                sum = 0;
                for(int pos = 0; pos < 10; pos++) {
                    sum += (WEIGHT2[pos] * Character.digit(value.charAt(pos), 10));
                }
                sum = (sum % 11) % 10;
                if(sum != checksum) {
                    return false;
                }
            }
            // verify birthdate
            String birthDate = String.format("%s%s-%s-%s", CENTURY.get(m.group(1)), m.group(2), m.group(3), m.group(4));
            try {
                LocalDate.parse(birthDate);
                return true;
            } catch(@SuppressWarnings("unused") DateTimeParseException e) {
                return false;
            }
        }
        return false;
    }
}
