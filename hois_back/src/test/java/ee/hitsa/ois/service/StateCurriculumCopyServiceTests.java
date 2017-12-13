package ee.hitsa.ois.service;

import org.junit.Assert;
import org.junit.Test;

import ee.hitsa.ois.service.curriculum.StateCurriculumCopyService;

public class StateCurriculumCopyServiceTests {
    
    @Test
    public void calculateStudyPeriod() {
        Integer result = StateCurriculumCopyService.calculateStudyPeriod(Long.valueOf(100));
        Assert.assertTrue(Integer.valueOf(20).equals(result));
    }
    
    @Test
    public void calculateStudyPeriodFraction() {
        Integer result = StateCurriculumCopyService.calculateStudyPeriod(Long.valueOf(109));
        Assert.assertTrue(Integer.valueOf(22).equals(result));
    }
}
