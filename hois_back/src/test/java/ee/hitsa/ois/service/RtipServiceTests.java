package ee.hitsa.ois.service;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.rtip.RtipService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RtipServiceTests {

    private static final String RTIP_SCHOOL_CODE = "G340";

    @Autowired
    private EntityManager em;
    @Autowired
    private RtipService rtipService;

    @Ignore("for developing")
    @Test
    public void syncSchool() {
        School school = em.find(School.class, Long.valueOf(971));
        Assert.assertNotNull(school);
        if(school.getRtipSchoolCode() == null) {
            school.setRtipSchoolCode(RTIP_SCHOOL_CODE);
        }
        LocalDate from = LocalDate.parse("2013-12-08");
        LocalDate to = LocalDate.parse("2017-11-21");
        rtipService.syncSchool(school, from, to);
    }

    @Ignore("for developing")
    @Test
    public void syncSchoolTeacherData() {
        School school = em.find(School.class, Long.valueOf(1));
        Assert.assertNotNull(school);
        school.setRtipSchoolCode(RTIP_SCHOOL_CODE);
        rtipService.syncSchoolTeacherData(school);
    }
}
