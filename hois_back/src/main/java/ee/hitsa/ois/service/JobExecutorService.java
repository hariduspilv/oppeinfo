package ee.hitsa.ois.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ee.hitsa.ois.service.ehis.EhisStudentService;
import ee.hitsa.ois.service.kutseregister.KutseregisterService;
import ee.hitsa.ois.service.poll.PollService;
import ee.hitsa.ois.service.rr.PopulationRegisterService;
import ee.hitsa.ois.service.rtip.RtipService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EnumUtil;

/**
 * job execution service
 *
 * All transaction logic is outside of this service to avoid marking transaction as rollback only in case of unhandled exception.
 * 
 * NB! 
 * cannot use references to other tables from job in this service directly (job.getStudent().getSomething... will result in no session exception
 * use student = em.getReference(Student.class, EntityUtil.getId(job.getStudent()) instead
 * 
 */

@ConditionalOnExpression("${hois.jobs.enabled:false}")
@Service
public class JobExecutorService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//    private static final Authentication AUTHENTICATION_JOB = new UsernamePasswordAuthenticationToken("Automatic job", null,
//            Collections.singletonList((GrantedAuthority)(() -> "ROLE_JOB")));
    private static final Authentication AUTHENTICATION_MSG = new UsernamePasswordAuthenticationToken("Automaatne sõnum", null,
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
    @Autowired
    private StudentRepresentativeService studentRepresentativeService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PopulationRegisterService rrService;
    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private PollService pollService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EhisStudentService ehisStudentService;
    @Autowired
    private TimetableEventService timetableEventService;
    
    @Value("${hois.jobs.supportService.message.days}")
    private Integer supportServiceMessageDays;

    @Value("${hois.jobs.message.representative.days}")
    private Integer representativeMessageDays;

    /**
     * Handler for practice contract ended jobs
     */
    @Scheduled(cron = "${hois.jobs.contract.cron}")
    public void contractJob() {
        handleJobs(job -> {
            contractService.endContract(job.getContract(), false);
        }, AUTHENTICATION_MSG, JobType.JOB_PRAKTIKALEPING_KEHTETU);
        
        withAuthentication(() -> {
            teacherService.updateTeacherContractEnd();
        }, AUTHENTICATION_MSG);
    }

    /**
     * Handler for student status change jobs caused by directives
     * Sends support services for ended directives.
     */
    @Scheduled(cron = "${hois.jobs.directive.cron}")
    public void directiveJob() {
        handleJobs(job -> {
            directiveConfirmService.updateStudentStatus(job);
            if(ClassifierUtil.oneOf(job.getType(), JobType.JOB_AKAD_KATK, JobType.JOB_AKAD_MINEK)) {
                jobService.submitEhisSend(job.getDirective(), job.getStudent());
            }
        }, AUTHENTICATION_MSG, JobType.JOB_AKAD_KATK, JobType.JOB_AKAD_MINEK, JobType.JOB_AKAD_TULEK, JobType.JOB_VALIS_MINEK, JobType.JOB_VALIS_TULEK);

        handleJobs(job -> {
            directiveConfirmService.sendAcademicLeaveEndingMessage(job);
        }, AUTHENTICATION_MSG, JobType.JOB_AKAD_LOPP_TEADE);

        withAuthentication(() -> {
            ehisDirectiveStudentService.sendEndedSupportServices();
        }, AUTHENTICATION_MSG);
    }

    /**
     * Handler for EHIS jobs
     */
    @Scheduled(cron = "${hois.jobs.ehis.cron}")
    public void ehisJob() {
        handleJobs(job -> {
            ehisDirectiveStudentService.updateStudents(job);
        }, AUTHENTICATION_MSG, JobType.JOB_EHIS);
    }
    
    /**
     * Handler for EHIS languages job
     */
    @Scheduled(cron = "${hois.jobs.ehis.languages.cron}")
    public void ehisLanguagesJob() {
        handleJobs(job -> {
            ehisStudentService.sendStudentLanguages(job);
        }, AUTHENTICATION_MSG, JobType.JOB_KEEL_EHIS);
    }

    /**
     * Handler for EKIS jobs
     */
    // @Scheduled(cron = "${hois.jobs.ekis.cron}")
    public void ekisJob() {
        handleJobs(job -> {
            // XXX do nothing for now
        }, AUTHENTICATION_MSG, JobType.JOB_EKIS);
    }

    /**
     * Automatic task to refresh data from RTIP
     */
    @Scheduled(cron = "${hois.jobs.rtip.cron}")
    public void syncRtip() {
        withAuthentication(() -> {
            LocalDate to = LocalDate.now();
            LocalDate from = to.minusDays(1);
            for(School school : rtipService.rtipSchools()) {
                rtipService.syncSchool(school, from, to);
            }
        }, AUTHENTICATION_MSG);
    }

    /**
     * Automatic task to refresh data from Kutseregister
     */
    @Scheduled(cron = "${hois.jobs.kutseregister.cron}")
    public void syncKutseregister() {
        withAuthentication(() -> {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            kutseregisterService.muutunudKutsestandardid(yesterday);
        }, AUTHENTICATION_MSG);
    }

    /**
     * Automatic task to refresh data from Rahvastikuregister
     */
    @Scheduled(cron = "${hois.jobs.rr.cron}")
    public void syncPopulationRegister() {
        withAuthentication(() -> {
            rrService.updateAcitveUsers();
        }, AUTHENTICATION_MSG);
    }

    /**
     * Automatic task to send automatic messages
     */
    @Scheduled(cron = "${hois.jobs.message.cron}")
    public void sendMessages() {
        withAuthentication(() -> {
            studentRepresentativeService.sendRepresentativeEndingMessages(representativeMessageDays);
        }, AUTHENTICATION_MSG);
    }
    
    /**
     * Automatic task to send email containing Poll
     */
    @Scheduled(cron = "${hois.jobs.email.supervisor}")
    public void sendPollReminder() {
        withAuthentication(() -> {
            pollService.sendPracticeJournalSupervisorEmails();
            pollService.sendEmails();
        }, AUTHENTICATION_MSG);
    }
    
    /**
     * Automatic task to change poll status to finished
     * A poll is finished, when its valid thru is in the past
     */
    @Scheduled(cron = "${hois.jobs.poll.end.cron}")
    public void checkPollValidThru() {
        withAuthentication(() -> {
            pollService.checkPollValidThru();
        }, AUTHENTICATION_MSG);
    }
    
    /**
     * Automatic task to change guest student status to ended
     * Guest student is ended when its directive student end_date or
     * student study_end is in the past
     */
    @Scheduled(cron = "${hois.jobs.guest.student.end.cron}")
    public void endGuestStudent() {
        withAuthentication(() -> {
            studentService.endGuestStudent();
        }, AUTHENTICATION_MSG);
    }
    
    /**
     * Automatic task to update students meal support according to requirements
     */
    @Scheduled(cron = "${hois.jobs.meal.support.cron}")
    public void updateStudentMealSupport() {
        withAuthentication(() -> {
            studentService.enableStudentMealSupport();
            studentService.disableStudentMealSupport();
        }, AUTHENTICATION_MSG);
    }
    
    /**
     * Automatic task to check TUGI directive and send a notification message.
     */
    @Scheduled(cron = "${hois.jobs.supportService.message.cron}")
    public void sendSupportServiceMessages() {
        withAuthentication(() -> {
            directiveService.sendSupportServiceMessages(supportServiceMessageDays);
        }, AUTHENTICATION_MSG);
    }

    /**
     * Automatic task to send teacher schedule for the next 2 weeks.
     */
    @Scheduled(cron = "${hois.jobs.timetable.teacherSchedule.cron}")
    public void sendTeacherSchedule() {
        withAuthentication(() -> {
            timetableEventService.sendTeacherWeekSchedules();
        }, AUTHENTICATION_MSG);
    }

    /**
     * Job execution wrapper.
     * If actual handler returns without exception, job is marked as done, otherwise failed (and exception is logged).
     *
     * @param handler
     * @param authentication
     * @param types
     */
    private void handleJobs(Consumer<Job> handler, Authentication authentication, JobType... types) {
        log.info("Executing jobs with types: {}", EnumUtil.toNameList(types));
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
        }, authentication);
    }

    private static void withAuthentication(Runnable handler, Authentication authentication) {
        // set authentication to get audit log fields filled
        Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            handler.run();
        } finally {
            SecurityContextHolder.getContext().setAuthentication(oldAuthentication);
        }
    }
}
