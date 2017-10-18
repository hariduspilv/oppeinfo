package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.service.ehis.EhisDirectiveStudentService;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.util.EntityUtil;

/**
 * job execution service
 *
 * All transaction logic is outside of this service to avoid marking transaction as rollback only in case of unhandled exception.
 */
@ConditionalOnExpression("${hois.jobs.enabled:false}")
@Service
public class JobExecutorService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ContractService contractService;
    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;
    @Autowired
    private EkisService ekisService;
    @Autowired
    private JobService jobService;

    @Scheduled(cron = "${hois.jobs.contract.cron}")
    public void contractJob() {
        handleJobs(job -> {
            contractService.endContract(job);
        }, JobType.JOB_PRAKTIKALEPING_KEHTETU);
    }

    @Scheduled(cron = "${hois.jobs.directive.cron}")
    public void directiveJob() {
        handleJobs(job -> {
            directiveConfirmService.updateStudentStatus(job);
            ehisDirectiveStudentService.updateStudentStatus(job);
        }, JobType.JOB_AKAD_KATK, JobType.JOB_AKAD_MINEK, JobType.JOB_AKAD_TULEK, JobType.JOB_VALIS_MINEK, JobType.JOB_VALIS_TULEK);
    }

    @Scheduled(cron = "${hois.jobs.ehis.cron}")
    public void ehisJob() {
        handleJobs(job -> {
            ehisDirectiveStudentService.updateStudents(EntityUtil.getId(job.getDirective()));
        }, JobType.JOB_EHIS);
    }

    @Scheduled(cron = "${hois.jobs.ekis.cron}")
    public void ekisJob() {
        handleJobs(job -> {
            if(job.getDirective() != null) {
                ekisService.registerDirective(job.getDirective());
            } else if(job.getContract() != null) {
                ekisService.registerPracticeContract(job.getContract());
            }
        }, JobType.JOB_EKIS);
    }

    private void handleJobs(Consumer<Job> handler, JobType... types) {
        for(Job job : jobService.findExecutableJobs(types)) {
            try {
                handler.accept(job);
                jobService.jobDone(job);
            } catch(Throwable t) {
                log.error("Error while executing job with id {} :", job.getId(), t);
                jobService.jobFailed(job);
            }
        }
    }
}
