package ee.hitsa.ois.util;

import ee.hitsa.ois.validation.ValidationFailedException;
import ee.sk.mid.MidInputUtil;
import ee.sk.mid.exception.MidInvalidPhoneNumberException;

public abstract class AuthUtil {

    public static String validateMobileNumber(String mobileNumber) {
        if (!(mobileNumber.startsWith("+372") || mobileNumber.startsWith("372"))) {
            mobileNumber = "+372" + mobileNumber;
        } else if (mobileNumber.startsWith("372")) {
            mobileNumber = "+" + mobileNumber;
        }

        try {
            mobileNumber = MidInputUtil.getValidatedPhoneNumber(mobileNumber);
        } catch (MidInvalidPhoneNumberException e) {
            throw new ValidationFailedException("invalid phone number");
        }
        return mobileNumber;
    }
}
