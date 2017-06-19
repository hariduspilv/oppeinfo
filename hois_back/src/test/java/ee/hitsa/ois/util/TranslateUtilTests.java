package ee.hitsa.ois.util;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.enums.Language;

public class TranslateUtilTests {

    @Test
    public void translate() {
        Assert.assertEquals("Õppekava nimetus", TranslateUtil.translate("report.curriculum.name", Language.ET));
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingBundle() {
        TranslateUtil.translate("report.curriculum.name", Language.RU);
    }
}
