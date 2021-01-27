package ee.AD.demo;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobExecutorService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    @Autowired
    private RestService service;

    @Scheduled(cron = "${hois.jobs.immat.cron}")
    public void createUsers() {
        log.info("STARTING JOB createUsers");
        service.createUsers();
    }
    
    @Scheduled(cron = "${hois.jobs.exmat.cron}")
    public void disableUsers() {
        log.info("STARTING JOB disableUsers");
        service.disableUsers();
    }
}

