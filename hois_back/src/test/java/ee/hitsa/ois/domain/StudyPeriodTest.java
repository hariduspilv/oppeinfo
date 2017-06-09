package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudyPeriodTest {

    @Test
    public void getWeekNrs() {
        StudyYear sy = new StudyYear();
        sy.setStartDate(LocalDate.of(2016, 9, 1));
        sy.setEndDate(LocalDate.of(2017, 5, 30));

        StudyPeriod sp = new StudyPeriod();
        StudyPeriod sp2 = new StudyPeriod();
        StudyPeriod sp3 = new StudyPeriod();

        sy.setStartDate(LocalDate.of(2017, 6, 1));
        sy.setEndDate(LocalDate.of(2017, 7, 3));

        // 2 weeks - 1, 2
        sp.setStudyYear(sy);
        sp.setStartDate(sy.getStartDate());
        sp.setEndDate(LocalDate.of(2017, 6, 6));

        // 1 week - 3
        sp2.setStudyYear(sy);
        sp2.setStartDate(LocalDate.of(2017, 6, 10));
        sp2.setEndDate(LocalDate.of(2017, 6, 17));

        // 2 weeks - 6, 7
        sp3.setStudyYear(sy);
        sp3.setStartDate(LocalDate.of(2017, 6, 27));
        sp3.setEndDate(sy.getEndDate());

        sy.setStudyPeriods(new HashSet<>(Arrays.asList(sp, sp2, sp3)));
        
        Assert.assertEquals(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)), sp.getWeekNrs());
        Assert.assertEquals(Arrays.asList(Integer.valueOf(3)), sp2.getWeekNrs());
        Assert.assertEquals(Arrays.asList(Integer.valueOf(5), Integer.valueOf(6)), sp3.getWeekNrs());

    }
}
