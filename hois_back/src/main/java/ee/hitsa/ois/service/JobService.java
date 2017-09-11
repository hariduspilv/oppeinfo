package ee.hitsa.ois.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JobStatus;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;

/**
 * Job handling service.
 */
@Transactional
@Service
public class JobService {

    private static final List<String> AKAD_JOB_TYPES = EnumUtil.toNameList(JobType.JOB_AKAD_MINEK, JobType.JOB_AKAD_TULEK);
    private static final List<String> AKADK_JOB_TYPES = EnumUtil.toNameList(JobType.JOB_AKAD_KATK);
    private static final List<String> VALIS_JOB_TYPES = EnumUtil.toNameList(JobType.JOB_VALIS_MINEK, JobType.JOB_VALIS_TULEK);

    @Autowired
    private EntityManager em;

    public void directiveConfirmed(Directive directive) {
        submitEhisSend(directive);

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        switch(directiveType) {
        case KASKKIRI_TYHIST:
            Directive canceled = directive.getCanceledDirective();
            DirectiveType canceledType = DirectiveType.valueOf(EntityUtil.getCode(canceled.getType()));

            if(DirectiveType.KASKKIRI_AKAD.equals(canceledType)) {
                cancelJobs(AKAD_JOB_TYPES, directive);
            } else if(DirectiveType.KASKKIRI_AKAD.equals(canceledType)) {
                cancelJobs(AKADK_JOB_TYPES, directive);
            } else if(DirectiveType.KASKKIRI_VALIS.equals(canceledType)) {
                cancelJobs(VALIS_JOB_TYPES, directive);
            }
            break;
        case KASKKIRI_AKAD:
            for(DirectiveStudent ds : directive.getStudents()) {
                submitAkadJob(ds);
            }
            break;
        case KASKKIRI_AKADK:
            cancelJobs(EnumUtil.toNameList(JobType.JOB_AKAD_TULEK), directive);
            for(DirectiveStudent ds : directive.getStudents()) {
                submitAkadkJob(ds);
            }
            break;
        case KASKKIRI_VALIS:
            for(DirectiveStudent ds : directive.getStudents()) {
                submitValisJob(ds);
            }
            break;
        default:
            // do nothing
        }
    }

    private void submitAkadJob(DirectiveStudent ds) {
        Directive directive = ds.getDirective();

        Job job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodStart(ds).atStartOfDay());
        submitJob(JobType.JOB_AKAD_MINEK, job);

        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodEnd(ds).atStartOfDay().plusDays(1));
        submitJob(JobType.JOB_AKAD_TULEK, job);
    }

    private void submitAkadkJob(DirectiveStudent ds) {
        Directive directive = ds.getDirective();

        Job job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(ds.getStartDate().atStartOfDay());
        submitJob(JobType.JOB_AKAD_KATK, job);
    }

    private void submitValisJob(DirectiveStudent ds) {
        Directive directive = ds.getDirective();

        Job job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodStart(ds).atStartOfDay());
        submitJob(JobType.JOB_VALIS_MINEK, job);

        job = new Job();
        job.setSchool(directive.getSchool());
        job.setDirective(directive);
        job.setStudent(ds.getStudent());
        job.setJobTime(DateUtils.periodEnd(ds).atStartOfDay().plusDays(1));
        submitJob(JobType.JOB_VALIS_TULEK, job);
    }

    private void submitEhisSend(Directive directive) {
        // directive cancelling data is not sent to ehis (it's changed by hand in ehis)
        if(!ClassifierUtil.equals(DirectiveType.KASKKIRI_TYHIST, directive.getType())) {
            Job ehis = new Job();
            ehis.setSchool(directive.getSchool());
            ehis.setDirective(directive);
            ehis.setJobTime(LocalDateTime.now());
            submitJob(JobType.JOB_EHIS, ehis);
        }
    }

    private void submitJob(JobType type, Job job) {
        job.setType(em.getReference(Classifier.class, type.name()));
        job.setStatus(em.getReference(Classifier.class, JobStatus.JOB_STATUS_VALMIS.name()));
        em.persist(job);
    }

    private void cancelJobs(List<String> types, Directive directive) {
        List<Long> studentIds = directive.getStudents().stream().map(r -> EntityUtil.getId(r.getStudent())).collect(Collectors.toList());
        if(!studentIds.isEmpty()) {
            Query q = em.createNativeQuery("update job set status_code = :newStatus where type_code in :types and student_id in :studentIds and status_code = :oldStatus and directive_id = :directiveId");
            q.setParameter("types", types);
            q.setParameter("newStatus", JobStatus.JOB_STATUS_TYHISTATUD.name());
            q.setParameter("oldStatus", JobStatus.JOB_STATUS_VALMIS.name());
            q.setParameter("studentIds", studentIds);
            q.setParameter("directiveId", EntityUtil.getId(directive));
            q.executeUpdate();
        }
    }
}
