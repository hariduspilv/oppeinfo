package ee.hitsa.ois.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.service.curriculum.StateCurriculumCopyService;

public class StateCurriculumCopyServiceTests {

    @Test
    public void calculateStudyPeriod() {
        int result = StateCurriculumCopyService.calculateStudyPeriod(BigDecimal.valueOf(100));
        Assert.assertTrue(20 == result);
    }

    @Test
    public void calculateStudyPeriodFraction() {
        int result = StateCurriculumCopyService.calculateStudyPeriod(BigDecimal.valueOf(109));
        Assert.assertTrue(22 == result);
    }
}
