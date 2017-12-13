package ee.hitsa.ois.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.service.ehis.EhisDirectiveStudentService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JobServiceTests {

    @Autowired
    private EntityManager em;
    @Autowired
    private JobService jobService;
    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;

    @Test
    public void findExecutableJobs() {
        jobService.findExecutableJobs(JobType.JOB_EKIS);
    }

    @Ignore("for developing") @Test
    public void updateStundents() {
        ehisDirectiveStudentService.updateStudents(em.getReference(Job.class, Long.valueOf(737)));
    }
}
