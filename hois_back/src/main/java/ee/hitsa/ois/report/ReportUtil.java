package ee.hitsa.ois.report;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.TranslateUtil;

public abstract class ReportUtil {

    public static final String KEY_MISSING = "missing";

    public static String valueOrMissing(String value, Language lang) {
        return value != null && !value.isEmpty() ? value : TranslateUtil.translate(KEY_MISSING, lang);
    }
}
