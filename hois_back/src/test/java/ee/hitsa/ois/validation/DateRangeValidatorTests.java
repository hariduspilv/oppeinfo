package ee.hitsa.ois.validation;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.web.commandobject.GeneralMessageForm;

public class DateRangeValidatorTests {

    private DateRangeValidator validator = new DateRangeValidator();

    @Test
    public void test() {
        DateRange[] ranges = GeneralMessageForm.class.getAnnotationsByType(DateRange.class);
        Assert.assertTrue("Missing DateRange annotation", ranges.length > 0);
        validator.initialize(ranges[0]);
        GeneralMessageForm form = new GeneralMessageForm();
        Assert.assertTrue("Both mssing date values does not validate", validator.isValid(form, null));
        form.setValidFrom(LocalDate.now());
        Assert.assertTrue("Missing thru value does not validate", validator.isValid(form, null));
        form.setValidFrom(null);
        form.setValidThru(LocalDate.now());
        Assert.assertTrue("Missing from value does not validate", validator.isValid(form, null));
        form.setValidFrom(form.getValidThru());
        Assert.assertTrue("Equal from and thru values does not validate", validator.isValid(form, null));
        form.setValidThru(form.getValidThru().plusDays(1));
        Assert.assertTrue("Thru after from does not validate", validator.isValid(form, null));

        form.setValidFrom(form.getValidThru().plusDays(1));
        Assert.assertFalse("Thru before from does validate", validator.isValid(form, null));
    }
}
