package ee.hitsa.ois.validation;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.web.commandobject.ApplicationForm;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class StudyPeriodRangeValidatorTests {

    private StudyPeriodRangeValidator validator = new StudyPeriodRangeValidator();

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;

    @Test
    public void test() {
        StudyPeriodRange[] ranges = ApplicationForm.class.getAnnotationsByType(StudyPeriodRange.class);
        Assert.assertTrue("Missing StudyPeriodRange annotation", ranges.length > 0);
        validator.initialize(ranges[0]);
        validator.setStudyPeriodRepository(studyPeriodRepository);

        ApplicationForm form = new ApplicationForm();
        Assert.assertTrue("Both missing date values does validate", validator.isValid(form, null));

        //2017/sügis
        StudyPeriod start = studyPeriodRepository.getOne(Long.valueOf(4));
        Assert.assertNotNull(start);

        //2017/kevad
        StudyPeriod end = studyPeriodRepository.getOne(Long.valueOf(7));
        Assert.assertNotNull(end);

        form.setStudyPeriodStart(start.getId());
        Assert.assertTrue("Missing thru value does validate", validator.isValid(form, null));
        form.setStudyPeriodStart(null);
        form.setStudyPeriodEnd(end.getId());
        Assert.assertTrue("Missing from value does validate", validator.isValid(form, null));

        form.setStudyPeriodStart(form.getStudyPeriodEnd());
        Assert.assertTrue("Equal from and thru values does validate", validator.isValid(form, null));

        form.setStudyPeriodStart(start.getId());
        form.setStudyPeriodEnd(end.getId());
        Assert.assertTrue("Thru after from does validate", validator.isValid(form, null));

        form.setStudyPeriodStart(end.getId());
        form.setStudyPeriodEnd(start.getId());
        Assert.assertFalse("Thru before from does not validate", validator.isValid(form, null));


    }

}
