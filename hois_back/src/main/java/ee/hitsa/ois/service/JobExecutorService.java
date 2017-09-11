package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.enums.JobStatus;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.repository.JobRepository;
import ee.hitsa.ois.service.ehis.EhisDirectiveStudentService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;

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
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;
    @Autowired
    private EntityManager em;
    @Autowired
    private JobRepository jobRepository;

    @Scheduled(cron = "${hois.jobs.directive.cron}")
    public void directiveJob() {
        handleJobs(job -> {
            directiveConfirmService.updateStudentStatus(job);
        }, JobType.JOB_AKAD_KATK, JobType.JOB_AKAD_MINEK, JobType.JOB_AKAD_TULEK, JobType.JOB_VALIS_MINEK, JobType.JOB_VALIS_TULEK);
    }

    @Scheduled(cron = "${hois.jobs.ehis.cron}")
    public void ehisJob() {
        handleJobs(job -> {
            ehisDirectiveStudentService.updateStudents(EntityUtil.getId(job.getDirective()));
        }, JobType.JOB_EHIS);
    }

    private void handleJobs(Consumer<Job> handler, JobType... types) {
        for(Job job : findExecutableJobs(types)) {
            try {
                handler.accept(job);
                jobDone(job);
            } catch(Throwable t) {
                jobFailed(job, t);
            }
        }
    }

    private List<Job> findExecutableJobs(JobType... types) {
        List<String> typeNames = EnumUtil.toNameList(types);
        return jobRepository.executableJobs(JobStatus.JOB_STATUS_VALMIS.name(), typeNames, LocalDateTime.now());
    }

    private void jobFailed(Job job, Throwable t) {
        log.error("Error while executing job with id {} :", job.getId(), t);

        setJobStatus(job, JobStatus.JOB_STATUS_VIGA);
        jobRepository.save(job);
    }

    private void jobDone(Job job) {
        setJobStatus(job, JobStatus.JOB_STATUS_TAIDETUD);
        jobRepository.save(job);
    }

    private void setJobStatus(Job job, JobStatus status) {
        job.setStatus(em.getReference(Classifier.class, status.name()));
    }
}
