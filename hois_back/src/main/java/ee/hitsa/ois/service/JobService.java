package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JobStatus;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;

/**
 * Job handling service.
 */
@Transactional
@Service
public class JobService {

    private static final int ACADEMIC_LEAVE_MESSAGE_BEFORE_DAYS = 14;
    private static final List<String> AKAD_JOB_TYPES = EnumUtil.toNameList(JobType.JOB_AKAD_MINEK, JobType.JOB_AKAD_TULEK, JobType.JOB_AKAD_LOPP_TEADE);
    private static final List<String> AKADK_JOB_TYPES = EnumUtil.toNameList(JobType.JOB_AKAD_KATK);
    private static final List<String> VALIS_JOB_TYPES = EnumUtil.toNameList(JobType.JOB_VALIS_MINEK, JobType.JOB_VALIS_TULEK);

    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    @Autowired
    private EntityManager em;

    /**
     * Practice contract confirmed by EKIS.
     * If there is end date for contract, submit job to mark contract as "ended".
     *
     * @param contractId
     */
    public void contractConfirmed(Long contractId) {
        Contract contract = em.getReference(Contract.class, contractId);
        if(contract.getEndDate() != null) {
            Job ehis = new Job();
            Student student = contract.getStudent();
            ehis.setSchool(student.getSchool());
            ehis.setStudent(student);
            ehis.setContract(contract);
            ehis.setJobTime(contract.getEndDate().atStartOfDay().plusDays(1));
            submitJob(JobType.JOB_PRAKTIKALEPING_KEHTETU, ehis);
        }
    }

    /**
     * Directive confirmed by EKIS.
     * Submit jobs to send directive data to EHIS and change student status on required dates
     * If directive is cancel directive, cancel possible jobs of canceled directive.
     *
     * @param directiveId
     */
    public void directiveConfirmed(Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);
        // cancelling directive is not sent to ehis (it's changed manually in ehis)
        // akad and akadk are sent when student status changes
        // valis and lopetamine are sent manually
        // kiitus, noomi, otegevus and praktik are not sent to ehis.
        // muu is not sent to ehis
        if(!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_TYHIST, DirectiveType.KASKKIRI_AKAD, DirectiveType.KASKKIRI_AKADK, DirectiveType.KASKKIRI_LOPET,
                DirectiveType.KASKKIRI_VALIS, DirectiveType.KASKKIRI_VALISKATK, DirectiveType.KASKKIRI_KIITUS, DirectiveType.KASKKIRI_NOOMI, DirectiveType.KASKKIRI_OTEGEVUS,
                DirectiveType.KASKKIRI_PRAKTIK, DirectiveType.KASKKIRI_TUGILOPP, DirectiveType.KASKKIRI_MUU)) {
            submitEhisSend(directive, null);
        }

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        switch(directiveType) {
        case KASKKIRI_TYHIST:
            Directive canceled = directive.getCanceledDirective();
            DirectiveType canceledType = DirectiveType.valueOf(EntityUtil.getCode(canceled.getType()));

            if(DirectiveType.KASKKIRI_AKAD.equals(canceledType)) {
                cancelJobs(AKAD_JOB_TYPES, directive);
            } else if(DirectiveType.KASKKIRI_AKADK.equals(canceledType)) {
                cancelJobs(AKADK_JOB_TYPES, directive);
            } else if(DirectiveType.KASKKIRI_VALIS.equals(canceledType)) {
                cancelJobs(VALIS_JOB_TYPES, directive);
            } else if (DirectiveType.KASKKIRI_TUGI.equals(canceledType)) {
                // HITSAOIS-622 TYHIST should send data about TUGI once more.
                submitEhisSend(directive, null);
            }
            break;
        case KASKKIRI_AKAD:
            for(DirectiveStudent ds : directive.getStudents()) {
                submitAkadJob(ds);
            }
            break;
        case KASKKIRI_AKADK:
            cancelJobs(EnumUtil.toNameList(JobType.JOB_AKAD_TULEK, JobType.JOB_AKAD_LOPP_TEADE), directive);
            for(DirectiveStudent ds : directive.getStudents()) {
                submitAkadkJob(ds);
            }
            break;
        case KASKKIRI_VALIS:
            for(DirectiveStudent ds : directive.getStudents()) {
                submitValisJob(ds);
            }
            break;
        case KASKKIRI_VALISKATK:
            for(DirectiveStudent ds : directive.getStudents()) {
                if (Boolean.TRUE.equals(ds.getStudent().getCurriculumVersion().getCurriculum().getHigher())) {
                    submitEhisSend(directive, ds.getStudent());
                }
                changeValisTulekJob(ds);
            }
            break;
        default:
            // do nothing
        }
    }

    private void changeValisTulekJob(DirectiveStudent ds) {
        Long studentId = EntityUtil.getNullableId(ds.getStudent());
        Long originDirectiveId = EntityUtil.getNullableId(ds.getDirectiveStudent().getDirective());
        if (studentId != null && originDirectiveId != null) {
            LocalDate now = LocalDate.now();
            if (ds.getStartDate().isAfter(now)) {
                // Only 1 VALIS_TULEK job should be active (VALMIS) at the same time for given directive and student
                String sql = "update job set job_time = :endDate where type_code = :type and student_id = :studentId and directive_id = :directiveId and status_code = :jobStatus";
                Query q = em.createNativeQuery(sql);
                q.setParameter("type", JobType.JOB_VALIS_TULEK.name());
                q.setParameter("endDate", JpaQueryUtil.parameterAsTimestamp(ds.getStartDate()));
                q.setParameter("jobStatus", JobStatus.JOB_STATUS_VALMIS.name());
                q.setParameter("studentId", studentId);
                q.setParameter("directiveId", originDirectiveId);
                q.executeUpdate();
            } else {
                List<Job> jobs = em.createQuery("select j from Job j where j.status.code = :status and j.type.code = :type and "
                        + "j.student.id = :studentId and j.directive.id = :directiveId", Job.class)
                        .setParameter("status", JobStatus.JOB_STATUS_VALMIS.name())
                        .setParameter("type", JobType.JOB_VALIS_TULEK.name())
                        .setParameter("studentId", studentId)
                        .setParameter("directiveId", originDirectiveId)
                        .setMaxResults(1)
                        .getResultList();
                // Only 1 VALIS_TULEK job should be active (VALMIS) at the same time for given directive and student
                if (!jobs.isEmpty()) {
                    Job job = jobs.get(0);
                    setJobStatus(job, JobStatus.JOB_STATUS_TAIDETUD);
                    job.setJobTime(ds.getStartDate().atTime(LocalTime.MIN));
                    directiveConfirmService.updateStudentStatus(job);
                }
            }
        }
    }

    /**
     * Find all jobs which are ready to execute.
     *
     * @param types
     * @return list of jobs
     */
    public List<Job> findExecutableJobs(JobType... types) {
        List<String> typeNames = EnumUtil.toNameList(types);
        return em.createQuery("select j from Job j where j.status.code = ?1 and j.type.code in ?2 and j.jobTime <= ?3 order by j.inserted", Job.class)
            .setParameter(1, JobStatus.JOB_STATUS_VALMIS.name())
            .setParameter(2, typeNames)
            .setParameter(3, LocalDateTime.now())
            .getResultList();
    }

    /**
     * Mark job as failed.
     *
     * @param job
     * @return job
     */
    public Job jobFailed(Job job) {
        setJobStatus(job, JobStatus.JOB_STATUS_VIGA);
        return em.merge(job);
    }

    /**
     * Mark job as done.
     *
     * @param job
     * @return job
     */
    public Job jobDone(Job job) {
        setJobStatus(job, JobStatus.JOB_STATUS_TAIDETUD);
        return em.merge(job);
    }

    public void submitEhisSend(Directive directive, Student student) {
        if(!em.contains(directive)) {
            directive = em.merge(directive);
        }
        Job job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(student);
        // ASAP
        job.setJobTime(LocalDateTime.now());
        submitJob(JobType.JOB_EHIS, job);
    }

    private void submitAkadJob(DirectiveStudent ds) {
        Directive directive = ds.getDirective();

        // student goes to academic leave, change status O -> A
        Job job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodStart(ds).atStartOfDay());
        submitJob(JobType.JOB_AKAD_MINEK, job);

        // automatic message before academic leave ends
        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodEnd(ds).atStartOfDay().minusDays(ACADEMIC_LEAVE_MESSAGE_BEFORE_DAYS));
        submitJob(JobType.JOB_AKAD_LOPP_TEADE, job);

        // student comes back from academic leave, change status A -> O
        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodEnd(ds).atStartOfDay().plusDays(1));
        submitJob(JobType.JOB_AKAD_TULEK, job);
    }

    private void submitAkadkJob(DirectiveStudent ds) {
        Directive directive = ds.getDirective();

        // automatic message before cancel academic leave
        Job job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(ds.getStartDate().atStartOfDay().minusDays(ACADEMIC_LEAVE_MESSAGE_BEFORE_DAYS));
        submitJob(JobType.JOB_AKAD_LOPP_TEADE, job);

        // student cancels academic leave, change status A -> O
        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(ds.getStartDate().atStartOfDay());
        submitJob(JobType.JOB_AKAD_KATK, job);
    }

    private void submitValisJob(DirectiveStudent ds) {
        Directive directive = ds.getDirective();

        Job job;
        if (Boolean.TRUE.equals(ds.getStudent().getCurriculumVersion().getCurriculum().getHigher())) {
            // directive data should be sent to EHIS
            job = new Job();
            job.setSchool(directive.getSchool());
            job.setDirective(directive);
            job.setStudent(ds.getStudent());
            job.setJobTime(LocalDateTime.now());
            submitJob(JobType.JOB_EHIS, job);
        }

        // student will be foreign student, change status O -> V
        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodStart(ds).atStartOfDay());
        submitJob(JobType.JOB_VALIS_MINEK, job);

        // student is back, change status V -> O
        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodEnd(ds).atStartOfDay().plusDays(1));
        submitJob(JobType.JOB_VALIS_TULEK, job);
    }

    private void submitJob(JobType type, Job job) {
        job.setType(em.getReference(Classifier.class, type.name()));
        setJobStatus(job, JobStatus.JOB_STATUS_VALMIS);

        LocalDateTime now = LocalDateTime.now();
        if(!job.getJobTime().isAfter(now)) {
            // job start time already arrived
            switch(type) {
            case JOB_AKAD_MINEK:
            case JOB_AKAD_KATK:
            case JOB_VALIS_MINEK:
                directiveConfirmService.updateStudentStatus(job);
                if(JobType.JOB_AKAD_MINEK.equals(type) || JobType.JOB_AKAD_KATK.equals(type)) {
                    // send student status change to ehis
                    submitEhisSend(job.getDirective(), job.getStudent());
                }
                setJobStatus(job, JobStatus.JOB_STATUS_TAIDETUD);
                break;
            case JOB_AKAD_LOPP_TEADE:
                directiveConfirmService.sendAcademicLeaveEndingMessage(job);
                setJobStatus(job, JobStatus.JOB_STATUS_TAIDETUD);
                break;
            default:
                break;
            }
        }
        em.persist(job);
    }

    private void setJobStatus(Job job, JobStatus status) {
        job.setStatus(em.getReference(Classifier.class, status.name()));
    }

    private void cancelJobs(List<String> types, Directive directive) {
        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        if(!studentIds.isEmpty()) {
            boolean canceled = ClassifierUtil.equals(DirectiveType.KASKKIRI_TYHIST, directive.getType());
            String sql = "update job set status_code = :newStatus where type_code in :types and student_id in :studentIds and status_code = :oldStatus";
            if(canceled) {
                sql += " and directive_id = :directiveId";
            }
            Query q = em.createNativeQuery(sql);
            q.setParameter("types", types);
            q.setParameter("newStatus", JobStatus.JOB_STATUS_TYHISTATUD.name());
            q.setParameter("oldStatus", JobStatus.JOB_STATUS_VALMIS.name());
            q.setParameter("studentIds", studentIds);
            if(canceled) {
                q.setParameter("directiveId", EntityUtil.getId(directive.getCanceledDirective()));
            }
            q.executeUpdate();
        }
    }
}
