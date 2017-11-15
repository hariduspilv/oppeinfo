package ee.hitsa.ois.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.enums.JobType;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JobServiceTests {

    @Autowired
    private JobService jobService;

    @Test
    public void findExecutableJobs() {
        jobService.findExecutableJobs(JobType.JOB_EKIS);
    }
}
