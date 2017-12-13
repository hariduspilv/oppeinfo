package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.service.ehis.EhisDirectiveStudentService;
import ee.hitsa.ois.service.kutseregister.KutseregisterService;
import ee.hitsa.ois.service.rtip.RtipService;
import ee.hitsa.ois.util.ClassifierUtil;
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
    private static final Authentication JOB_AUTHENTICATION = new UsernamePasswordAuthenticationToken("Automatic job", null,
            Collections.singletonList((GrantedAuthority)(() -> "ROLE_JOB")));

    @Autowired
    private ContractService contractService;
    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;
    @Autowired
    private JobService jobService;
    @Autowired
    private KutseregisterService kutseregisterService;
    @Autowired
    private RtipService rtipService;

    /**
     * Handler for practice contract ended jobs
     */
    @Scheduled(cron = "${hois.jobs.contract.cron}")
    public void contractJob() {
        handleJobs(job -> {
            contractService.endContract(EntityUtil.getId(job.getContract()));
        }, JobType.JOB_PRAKTIKALEPING_KEHTETU);
    }

    /**
     * Handler for student status change jobs caused by directives
     */
    @Scheduled(cron = "${hois.jobs.directive.cron}")
    public void directiveJob() {
        handleJobs(job -> {
            directiveConfirmService.updateStudentStatus(job);
            if(ClassifierUtil.oneOf(job.getType(), JobType.JOB_AKAD_KATK, JobType.JOB_AKAD_MINEK)) {
                jobService.submitEhisSend(job.getDirective(), job.getStudent());
            }
        }, JobType.JOB_AKAD_KATK, JobType.JOB_AKAD_MINEK, JobType.JOB_AKAD_TULEK, JobType.JOB_VALIS_MINEK, JobType.JOB_VALIS_TULEK);

        handleJobs(job -> {
            directiveConfirmService.sendAcademicLeaveEndingMessage(job);
        }, JobType.JOB_AKAD_LOPP_TEADE);
    }

    /**
     * Handler for EHIS jobs
     */
    @Scheduled(cron = "${hois.jobs.ehis.cron}")
    public void ehisJob() {
        handleJobs(job -> {
            ehisDirectiveStudentService.updateStudents(job);
        }, JobType.JOB_EHIS);
    }

    /**
     * Handler for EKIS jobs
     */
    // @Scheduled(cron = "${hois.jobs.ekis.cron}")
    public void ekisJob() {
        handleJobs(job -> {
            // XXX do nothing for now
        }, JobType.JOB_EKIS);
    }

    /**
     * Automatic task to refresh data from RTIP
     */
    @Scheduled(cron = "${hois.jobs.rtip.cron}")
    public void syncRtip() {
        withAuthentication(() -> {
            for(School school : rtipService.rtipSchools()) {
                rtipService.syncSchool(school, null, null);
            }
        });
    }

    /**
     * Automatic task to refresh data from Kutseregister
     */
    @Scheduled(cron = "${hois.jobs.kutseregister.cron}")
    public void syncKutseregister() {
        withAuthentication(() -> {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            kutseregisterService.muutunudKutsestandardid(yesterday);
        });
    }

    /**
     * Job execution wrapper.
     * If actual handler returns without exception, job is marked as done, otherwise failed (and exception is logged).
     *
     * @param handler
     * @param types
     */
    private void handleJobs(Consumer<Job> handler, JobType... types) {
        withAuthentication(() -> {
            for(Job job : jobService.findExecutableJobs(types)) {
                try {
                    handler.accept(job);
                    jobService.jobDone(job);
                } catch(Throwable t) {
                    log.error("Error while executing job with id {} :", job.getId(), t);
                    jobService.jobFailed(job);
                }
            }
        });
    }

    private static void withAuthentication(Runnable handler) {
        // set authentication to get audit log fields filled
        Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(JOB_AUTHENTICATION);

        try {
            handler.run();
        } finally {
            SecurityContextHolder.getContext().setAuthentication(oldAuthentication);
        }
    }
}
