package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationComment;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFile;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalReplacedSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalExperience;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModuleOutcomes;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.CommitteeType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.message.ApelApplicationCreated;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApelApplicationUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationCommentForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationFormalReplacedSubjectOrModuleForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationFormalSubjectOrModuleForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationInformalExperienceForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationInformalSubjectOrModuleForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationInformalSubjectOrModuleOutcomesForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationRecordForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;

@Transactional
@Service
public class ApelApplicationService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ApelSchoolService apelSchoolService;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private AutomaticMessageService automaticMessageService;

    private static final String RPM_FROM = "from apel_application aa"
            + " join student s on aa.student_id = s.id"
            + " join person p on s.person_id=p.id"
            + " join curriculum_version curv on s.curriculum_version_id=curv.id"
            + " join curriculum cur on curv.curriculum_id=cur.id"
            + " join classifier c on aa.status_code=c.code";
    private static final String RPM_SELECT = "aa.id as application_id, aa.status_code as application_status, s.id as student_id,"
            + " p.firstname as student_firstname, p.lastname as student_lastname, cur.id as curriculum_id, cur.name_et as curriculum_name_et,"
            + " cur.name_en as curriculum_name_en, aa.inserted, aa.confirmed";
    
    /**
     * Search student APEL applications
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<ApelApplicationSearchDto> search(HoisUserDetails user,
            ApelApplicationSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(RPM_FROM);
        
        qb.requiredCriteria("aa.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.optionalCriteria("aa.student_id = :studentId", "studentId", criteria.getStudent());
        if (user.isStudent()) {
            qb.requiredCriteria("aa.student_id = :studentId", "studentId", user.getStudentId());
        }
        
        qb.optionalCriteria("aa.status_code in (:status)", "status", criteria.getStatus());
        if (user.isTeacher()) {
            qb.optionalCriteria("aa.status_code = :status", "status", ApelApplicationStatus.VOTA_STAATUS_V);
            qb.filter("aa.id in (select aa2.id from apel_application aa2"
                    + " join committee co on aa2.committee_id = co.id"
                    + " join committee_member cm on co.id = cm.committee_id"
                    + " where cm.person_id = " + user.getPersonId() + ")");
        }
        
        qb.optionalCriteria("aa.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("aa.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("aa.confirmed >= :confirmedFrom", "confirmedFrom", criteria.getConfirmedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("aa.confirmed <= :confirmedThru", "confirmedThru", criteria.getConfirmedThru(), DateUtils::lastMomentOfDay);
        
        qb.optionalCriteria("aa.committee_id = :committeeId", "committeeId", criteria.getCommittee());
        
        List<?> data = qb.select("aa.id", em).getResultList();
        List<Long> applicationIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
        Map<Long, List<Long>> committeeMembers = applicationCommitteeMembers(applicationIds);
        
        qb.sort(pageable);
        return JpaQueryUtil.pagingResult(qb, RPM_SELECT, em, pageable).map(r -> {
            ApelApplicationSearchDto dto = new ApelApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStatus(resultAsString(r, 1));
            dto.setInserted(resultAsLocalDate(r, 8));
            dto.setConfirmed(resultAsLocalDate(r, 9));
            String studentName = PersonUtil.fullname(resultAsString(r, 3), resultAsString(r, 4));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 2), studentName, studentName));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 7)));
            dto.setCanEdit(Boolean.valueOf(ApelApplicationUtil.canEdit(user, dto.getStatus())));
            dto.setCanReview(Boolean.valueOf(ApelApplicationUtil.canReview(user, dto.getStatus(), committeeMembers.get(dto.getId()))));
            return dto;
        });
    }
    
    private Map<Long, List<Long>> applicationCommitteeMembers(List<Long> applications) {
        Map<Long, List<Long>> committeeMembers = new HashMap<>();
        if (!applications.isEmpty()) {
            List<?> data = em.createNativeQuery("select aa.id, cm.person_id from apel_application aa"
                    + " join committee c on aa.committee_id = c.id"
                    + " join committee_member cm on c.id = cm.committee_id"
                    + " where aa.id in (?1)")
                    .setParameter(1, applications).getResultList();
            committeeMembers = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
        }
        return committeeMembers;
    }
    
    /**
     * Get student APEL application
     * 
     * @param user
     * @param application
     * @return
     */
    public ApelApplicationDto get(HoisUserDetails user, ApelApplication application) {
        ApelApplicationDto dto = ApelApplicationDto.of(application);
        dto.setCanEdit(Boolean.valueOf(ApelApplicationUtil.canEdit(user, application)));
        dto.setCanReview(Boolean.valueOf(ApelApplicationUtil.canReview(user, application)));
        dto.setCanSendToConfirm(Boolean.valueOf(ApelApplicationUtil.canSendToConfirm(user, application)));
        dto.setCanSendToCommittee(Boolean.valueOf(ApelApplicationUtil.canSendToCommittee(user, application)));
        dto.setCanSendBackToCreation(Boolean.valueOf(ApelApplicationUtil.canSendBackToCreation(user, application)));
        dto.setCanSendBack(Boolean.valueOf(ApelApplicationUtil.canSendBack(user, application)));
        dto.setCanReject(Boolean.valueOf(ApelApplicationUtil.canReject(user, application)));
        dto.setCanChangeTransferStatus(
                Boolean.valueOf(ApelApplicationUtil.canCanChangeTransferStatus(user, application)));
        dto.setCanConfirm(Boolean.valueOf(ApelApplicationUtil.canConfirm(user, application)));
        dto.setCanRemoveConfirmation(Boolean.valueOf(ApelApplicationUtil.canRemoveConfirmation(user, application)));
        return dto;
    }
    
    
    /**
     * Create new student APEL application
     * 
     * @param user
     * @param applicationForm
     * @return
     */
    public ApelApplication create(HoisUserDetails user, ApelApplicationForm applicationForm) {
        ApelApplication application = new ApelApplication();
        EntityUtil.bindToEntity(applicationForm, application, "student", "school", "status", "records", "files");
        
        application.setStudent(EntityUtil.getOptionalOne(Student.class, applicationForm.getStudent(), em));
        application.setSchool(em.getReference(School.class, user.getSchoolId()));
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_K);
        
        return save(user, application, applicationForm);
    }

    /**
     * Store student APEL application
     * 
     * @param user
     * @param application
     * @param applicationForm
     * @return
     */
    public ApelApplication save(HoisUserDetails user, ApelApplication application, ApelApplicationForm applicationForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(applicationForm, application, "records", "files", "committee", "decision");
        updateRecords(user, application, applicationForm);
        application.setCommittee(applicationForm.getCommitteeId() != null
                ? em.getReference(Committee.class, applicationForm.getCommitteeId()) : null);
        return EntityUtil.save(application, em);
    }

    /**
     * Delete student APEL application
     * 
     * @param user
     * @param application
     */
    public void delete(HoisUserDetails user, ApelApplication application) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(application, em);
    }

    /**
     * Create new APEL application's file
     * 
     * @param application
     * @param fileForm
     * @return
     */
    public ApelApplicationFile createFile(ApelApplication application, OisFileForm fileForm) {
        ApelApplicationFile file = new ApelApplicationFile();
        EntityUtil.bindToEntity(fileForm, file, "oisFile");
        file.setApelApplication(application);
        file.setOisFile(EntityUtil.bindToEntity(fileForm.getOisFile(), new OisFile()));
        return EntityUtil.save(file, em);
    }

    /**
     * Delete APEL application's file
     * 
     * @param user
     * @param file
     */
    public void deleteFile(HoisUserDetails user, ApelApplicationFile file) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(file, em);
    }
    
    private void updateRecords(HoisUserDetails user, ApelApplication application, ApelApplicationForm applicationForm) {
        EntityUtil.bindEntityCollection(application.getRecords(), ApelApplicationRecord::getId, applicationForm.getRecords(), ApelApplicationRecordForm::getId, recordForm -> {
            ApelApplicationRecord updatedRecord = EntityUtil.bindToEntity(recordForm, new ApelApplicationRecord(), 
                    "apelApplication", "informalExperiences", "informalSubjectsOrModules", "formalSubjectsOrModules", "formalReplacedSubjectsOrModules");
            updateInformalExperiences(recordForm, updatedRecord);
            updateInformalSubjectsOrModules(recordForm, updatedRecord);
            updateFormalSubjectsOrModules(user, recordForm, updatedRecord);
            updateFormalReplacedSubjectsOrModules(recordForm, updatedRecord);
            return updatedRecord;
        }, (recordForm, updatedRecord) -> {
            EntityUtil.bindToEntity(recordForm, updatedRecord, "apelApplication", "informalExperiences", "informalSubjectsOrModules", 
                    "formalSubjectsOrModules", "formalReplacedSubjectsOrModules");
            updateInformalExperiences(recordForm, updatedRecord);
            updateInformalSubjectsOrModules(recordForm, updatedRecord);
            updateFormalSubjectsOrModules(user, recordForm, updatedRecord);
            updateFormalReplacedSubjectsOrModules(recordForm, updatedRecord);
        });
    }

    /**
     * Create new APEL application's record
     * 
     * @param user
     * @param application
     * @param recordForm
     * @return
     */
    public ApelApplicationRecord createRecord(HoisUserDetails user, ApelApplication application, ApelApplicationRecordForm recordForm) {
        ApelApplicationRecord record = new ApelApplicationRecord();
        record.setApelApplication(application);
        return updateRecord(user, recordForm, record);
    }

    /**
     * Update APEL application's record's data
     * 
     * @param user
     * @param recordForm
     * @param record
     * @return
     */
    public ApelApplicationRecord updateRecord(HoisUserDetails user, ApelApplicationRecordForm recordForm, ApelApplicationRecord record) {
        ApelApplicationRecord updatedRecord = EntityUtil.bindToEntity(recordForm, record, "apelApplication", 
                "informalExperiences", "informalSubjectsOrModules", "formalSubjectsOrModules", "formalReplacedSubjectsOrModules");
        updateInformalExperiences(recordForm, updatedRecord);
        updateInformalSubjectsOrModules(recordForm, updatedRecord);
        updateFormalSubjectsOrModules(user, recordForm, updatedRecord);
        updateFormalReplacedSubjectsOrModules(recordForm, updatedRecord);
        return EntityUtil.save(updatedRecord, em);
    }
    
    /**
     * Delete APEL application's record
     * 
     * @param user
     * @param record
     */
    public void deleteRecord(HoisUserDetails user, ApelApplicationRecord record) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(record, em);
        record.getApelApplication().getRecords().remove(record);
    }

    private void updateInformalExperiences(ApelApplicationRecordForm recordForm, ApelApplicationRecord record) {
        EntityUtil.bindEntityCollection(record.getInformalExperiences(), ApelApplicationInformalExperience::getId, recordForm.getInformalExperiences(), ApelApplicationInformalExperienceForm::getId, form -> {
            ApelApplicationInformalExperience informalExperience = EntityUtil.bindToEntity(form,
                    new ApelApplicationInformalExperience(), "apelApplicationRecord", "type");
            informalExperience.setApelApplicationRecord(record);
            informalExperience.setType(em.getReference(Classifier.class, form.getType()));
            return informalExperience;
        }, (form, informalExperience) -> {
            EntityUtil.bindToEntity(form, informalExperience, "type");
            informalExperience.setType(em.getReference(Classifier.class, form.getType()));
        });
    }

    private void updateInformalSubjectsOrModules(ApelApplicationRecordForm recordForm, ApelApplicationRecord record) {
        EntityUtil.bindEntityCollection(record.getInformalSubjectsOrModules(), ApelApplicationInformalSubjectOrModule::getId, recordForm.getInformalSubjectsOrModules(), ApelApplicationInformalSubjectOrModuleForm::getId, form -> {
            ApelApplicationInformalSubjectOrModule subjectOrModule = EntityUtil.bindToEntity(form, 
                    new ApelApplicationInformalSubjectOrModule(), "apelApplicationRecord", "subject", "curriculumVersionHmodule",
                    "curriculumVersionOmodule", "curriculumVersionOmoduleTheme", "outcomes");
            subjectOrModule.setApelApplicationRecord(record);
            subjectOrModule = updateInformalSubjectOrModule(form, subjectOrModule);
            validateInformalSubjectOrModule(record, subjectOrModule);
            return subjectOrModule;
        }, (form, subjectOrModule) -> {
            EntityUtil.bindToEntity(form, subjectOrModule, "subject", "curriculumVersionHmodule",
                    "curriculumVersionOmodule", "curriculumVersionOmoduleTheme", "outcomes");
            updateInformalSubjectsOrModulesOutcomes(form, subjectOrModule);
            validateInformalSubjectOrModule(record, subjectOrModule);
        });
    }

    private ApelApplicationInformalSubjectOrModule updateInformalSubjectOrModule(
            ApelApplicationInformalSubjectOrModuleForm form, ApelApplicationInformalSubjectOrModule subjectOrModule) {
        subjectOrModule.setSubject(form.getSubject() != null ? em.getReference(Subject.class, form.getSubject().getId()) : null);
        subjectOrModule.setCurriculumVersionHmodule(form.getCurriculumVersionHmodule() != null 
                ? em.getReference(CurriculumVersionHigherModule.class, form.getCurriculumVersionHmodule().getId()) : null);
        subjectOrModule.setCurriculumVersionOmodule(form.getCurriculumVersionOmodule() != null 
                ? em.getReference(CurriculumVersionOccupationModule.class, form.getCurriculumVersionOmodule().getId()) : null);
        subjectOrModule.setCurriculumVersionOmoduleTheme(form.getCurriculumVersionOmoduleTheme() != null 
                ? em.getReference(CurriculumVersionOccupationModuleTheme.class, form.getCurriculumVersionOmoduleTheme().getId()) : null);
        subjectOrModule.setGrade(em.getReference(Classifier.class, form.getGrade()));
        updateInformalSubjectsOrModulesOutcomes(form, subjectOrModule);
        return subjectOrModule;
    }

    private void updateInformalSubjectsOrModulesOutcomes(ApelApplicationInformalSubjectOrModuleForm subjectOrModuleForm, ApelApplicationInformalSubjectOrModule subjectOrModule) {
        EntityUtil.bindEntityCollection(subjectOrModule.getOutcomes(), ApelApplicationInformalSubjectOrModuleOutcomes::getId, subjectOrModuleForm.getOutcomes(), ApelApplicationInformalSubjectOrModuleOutcomesForm::getId, form -> {
            ApelApplicationInformalSubjectOrModuleOutcomes subjectOrModuleOutcomes = EntityUtil.bindToEntity(form, 
                    new ApelApplicationInformalSubjectOrModuleOutcomes(), "apelApplicationInformalSubjectOrModule", "curriculumModuleOutcomes");
            subjectOrModuleOutcomes.setApelApplicationInformalSubjectOrModule(subjectOrModule);
            subjectOrModuleOutcomes.setCurriculumModuleOutcomes(em.getReference(CurriculumModuleOutcome.class, form.getCurriculumModuleOutcomes().getId()));
            return subjectOrModuleOutcomes;
        }, null);
    }
    
    private static void validateInformalSubjectOrModule(ApelApplicationRecord record, ApelApplicationInformalSubjectOrModule subjectOrModule) {
        ApelApplication application = record.getApelApplication();
        
        if (application.getIsVocational().booleanValue()) {
            ApelApplicationUtil.assertInformalModule(subjectOrModule);
        } else {
            ApelApplicationUtil.assertInformalSubject(subjectOrModule);
        }
    }
    
    private void updateFormalSubjectsOrModules(HoisUserDetails user, ApelApplicationRecordForm recordForm, ApelApplicationRecord record) {
        EntityUtil.bindEntityCollection(record.getFormalSubjectsOrModules(), ApelApplicationFormalSubjectOrModule::getId, recordForm.getFormalSubjectsOrModules(), ApelApplicationFormalSubjectOrModuleForm::getId, form -> {
            ApelApplicationFormalSubjectOrModule subjectOrModule = EntityUtil.bindToEntity(form, 
                    new ApelApplicationFormalSubjectOrModule(), "apelApplicationRecord", "apelSchool", "subject", "curriculumVersionHmodule", 
                    "curriculumVersionOmodule", "type", "grade", "assessment");
            subjectOrModule.setApelApplicationRecord(record);
            updateFormalSubjectOrModule(user, subjectOrModule, form);
            validateFormalSubjectOrModule(record, subjectOrModule);
            return subjectOrModule;
        }, (form, subjectOrModule) -> {
            EntityUtil.bindToEntity(form, subjectOrModule, "apelApplicationRecord", "apelSchool", "subject", "curriculumVersionHmodule", 
                    "curriculumVersionOmodule", "type", "grade", "assessment");
            updateFormalSubjectOrModule(user, subjectOrModule, form);
            validateFormalSubjectOrModule(record, subjectOrModule);
        });
    }
    
    private void updateFormalSubjectOrModule(HoisUserDetails user, ApelApplicationFormalSubjectOrModule subjectOrModule, ApelApplicationFormalSubjectOrModuleForm form) {
        form.setApelSchool(form.getNewApelSchool() != null ? apelSchoolService.create(user, form.getNewApelSchool()).getId() : form.getApelSchool());
        subjectOrModule.setApelSchool(form.getApelSchool() != null ? em.getReference(ApelSchool.class, form.getApelSchool()): null);
        subjectOrModule.setSubject(form.getSubject() != null ? em.getReference(Subject.class, form.getSubject()) : null);
        subjectOrModule.setCurriculumVersionHmodule(form.getCurriculumVersionHmodule() != null 
                ? em.getReference(CurriculumVersionHigherModule.class, form.getCurriculumVersionHmodule()) : null);
        subjectOrModule.setCurriculumVersionOmodule(form.getCurriculumVersionOmodule() != null 
                ? em.getReference(CurriculumVersionOccupationModule.class, form.getCurriculumVersionOmodule()) : null);
        subjectOrModule.setType(em.getReference(Classifier.class, form.getType()));
        subjectOrModule.setGrade(em.getReference(Classifier.class, form.getGrade()));
        subjectOrModule.setAssessment(em.getReference(Classifier.class, form.getAssessment()));
    }
    
    private static void validateFormalSubjectOrModule(ApelApplicationRecord record, ApelApplicationFormalSubjectOrModule subjectOrModule) {
        ApelApplication application = record.getApelApplication();
        
        if (application.getIsVocational().booleanValue()) {
            ApelApplicationUtil.assertFormalModule(subjectOrModule);
        } else {
            ApelApplicationUtil.assertFormalSubject(subjectOrModule);
        }
    }
    
    private void updateFormalReplacedSubjectsOrModules(ApelApplicationRecordForm recordForm, ApelApplicationRecord record) {
        EntityUtil.bindEntityCollection(record.getFormalReplacedSubjectsOrModules(), ApelApplicationFormalReplacedSubjectOrModule::getId, recordForm.getFormalReplacedSubjectsOrModules(), ApelApplicationFormalReplacedSubjectOrModuleForm::getId, form -> {
            ApelApplicationFormalReplacedSubjectOrModule replacedSubjectOrModule = EntityUtil.bindToEntity(form,
                    new ApelApplicationFormalReplacedSubjectOrModule(), "apelApplicationRecord", "subject", "curriculumVersionOmodule");
            replacedSubjectOrModule.setApelApplicationRecord(record);
            replacedSubjectOrModule.setSubject(form.getSubject() != null 
                    ? em.getReference(Subject.class, form.getSubject()) : null);
            replacedSubjectOrModule.setCurriculumVersionOmodule(form.getCurriculumVersionOmodule() != null 
                    ? em.getReference(CurriculumVersionOccupationModule.class, form.getCurriculumVersionOmodule()) : null);
            validateFormalReplacedSubjectOrModule(record, replacedSubjectOrModule);
            return replacedSubjectOrModule;
        }, (form, replacedSubjectOrModule) -> {
            EntityUtil.bindToEntity(form, replacedSubjectOrModule, "apelApplicationRecord", "subject", "curriculumVersionOmodule");
            replacedSubjectOrModule.setSubject(form.getSubject() != null 
                    ? em.getReference(Subject.class, form.getSubject()) : null);
            replacedSubjectOrModule.setCurriculumVersionOmodule(form.getCurriculumVersionOmodule() != null 
                    ? em.getReference(CurriculumVersionOccupationModule.class, form.getCurriculumVersionOmodule()) : null);
            validateFormalReplacedSubjectOrModule(record, replacedSubjectOrModule);
        });
    }
    
    private static void validateFormalReplacedSubjectOrModule(ApelApplicationRecord record, ApelApplicationFormalReplacedSubjectOrModule subjectOrModule) {
        ApelApplication application = record.getApelApplication();
        
        if (application.getIsVocational().booleanValue()) {
            ApelApplicationUtil.assertFormalReplacedModule(subjectOrModule);
        } else {
            ApelApplicationUtil.assertFormalReplacedSubject(subjectOrModule);
        }
    }
    
    private void setApplicationStatus(ApelApplication application, ApelApplicationStatus status) {
        application.setStatus(em.getReference(Classifier.class, status.name()));
    }

    /**
     * Create new APEL application's comment
     * 
     * @param application
     * @param commentForm
     * @return
     */
    public ApelApplicationComment createComment(ApelApplication application, ApelApplicationCommentForm commentForm) {
        ApelApplicationComment comment = new ApelApplicationComment();
        comment.setApelApplication(application);
        return updateComment(commentForm, comment);
    }

    /* TODO: not used right now an might never be */
    /**
     * Update APEL application's comment
     * 
     * @param commentForm
     * @param comment
     * @return
     */
    public ApelApplicationComment updateComment(ApelApplicationCommentForm commentForm, ApelApplicationComment comment) {
        ApelApplicationComment updatedComment = EntityUtil.bindToEntity(commentForm, comment);
        return EntityUtil.save(updatedComment, em);
    }

    /**
     * Delete APEL application's comment
     * 
     * @param user
     * @param comment
     */
    public void deleteComment(HoisUserDetails user, ApelApplicationComment comment) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(comment, em);
        comment.getApelApplication().getComments().remove(comment);
    }

    /**
     * Set APEL application's status to 'Submitted'
     * 
     * @param application
     * @return
     */
    public ApelApplication submit(ApelApplication application) {
        if (application.getRecords().isEmpty()) {
            throw new ValidationFailedException("apel.error.atLeastOneFormalOrInformalLearning");
        }
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_E);
        application = EntityUtil.save(application, em);
        ApelApplicationCreated data = new ApelApplicationCreated(application);
        automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_VOTA, application.getStudent(), data);
        automaticMessageService.sendMessageToSchoolAdmins(MessageType.TEATE_LIIK_VOTA, application.getSchool(), data);
        return application;
    }

    /**
     * Set APEL application's status to 'Being confirmed'
     * if application is reviewed add decision and duplicate it to comments
     * 
     * @param application
     * @return
     */
    public ApelApplication sendToConfirm(ApelApplication application, ApelApplicationForm applicationForm) {
        if (application.getRecords().isEmpty()) {
            throw new ValidationFailedException("apel.error.atLeastOneFormalOrInformalLearning");
        }
        
        if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(EntityUtil.getCode(application.getStatus()))) {
            application.setDecision(applicationForm.getAddInfo());
            
            ApelApplicationCommentForm commentForm = new ApelApplicationCommentForm(); 
            commentForm.setAddInfo(applicationForm.getAddInfo());
            createComment(application, commentForm);
        }
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_Y);
        return EntityUtil.save(application, em);
    }
    
    /**
     * Set APEL application's status to 'Being confirmed (committee)'
     * 
     * @param application
     * @return
     */
    public ApelApplication sendToCommittee(ApelApplication application, ApelApplicationForm applicationForm) {
        if (application.getRecords().isEmpty()) {
            throw new ValidationFailedException("apel.error.atLeastOneFormalOrInformalLearning");
        }
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_V);
        application.setCommittee(em.getReference(Committee.class, applicationForm.getCommitteeId()));
        return EntityUtil.save(application, em);
    }

    /**
     * Set APEL application's status back to 'Drafting'
     * if application is reviewed add decision and duplicate it to comments
     * 
     * @param application
     * @return
     */
    public ApelApplication sendBackToCreation(ApelApplication application, ApelApplicationCommentForm commentForm) {
        if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(EntityUtil.getCode(application.getStatus()))) {
            application.setDecision(commentForm.getAddInfo());
            createComment(application, commentForm);
        }
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_K);
        return EntityUtil.save(application, em);
    }

    /**
     * Set APEL application's status to 'Confirmed'
     * 
     * @param application
     * @return
     */
    public ApelApplication confirm(HoisUserDetails user, ApelApplication application) {
        checkThatModulesOrSubjectsAreTransferredOnlyOnce(application);
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_C);
        application.setConfirmedBy(user.getUsername());
        application.setConfirmed(LocalDateTime.now());
        return EntityUtil.save(application, em);
    }

    private void checkThatSubjectsAreNotAlreadyTransferred(Long studentId, List<Long> transferredIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_higher_result shr");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", studentId);
        List<?> data = qb.select("shr.subject_id", em).getResultList();
        Set<Long> previouslyTransferredIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        if (listsHaveCommonIds(transferredIds, previouslyTransferredIds)) {
            throw new ValidationFailedException("apel.error.subjectHasBeenPreviouslyTransferred");
        }
    }

    private void checkThatModulesAreNotAlreadyTransferred(Long studentId, List<Long> transferredIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr");
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", studentId);
        List<?> data = qb.select("svr.curriculum_version_omodule_id", em).getResultList();
        Set<Long> previouslyTransferredIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        if (listsHaveCommonIds(transferredIds, previouslyTransferredIds)) {
            throw new ValidationFailedException("apel.error.moduleHasBeenPreviouslyTransferred");
        }
    }

    private static boolean listsHaveCommonIds(List<Long> transferredIds, Set<Long> previouslyTransferredIds) {
        return !Collections.disjoint(transferredIds, previouslyTransferredIds);
    }

    private void checkThatModulesOrSubjectsAreTransferredOnlyOnce(ApelApplication application) {
        Set<Long> transferredSubjectIds = new HashSet<>();
        Set<Map<Long, Long>> transferredModuleAndThemeIdsMaps = new HashSet<>();
        for(ApelApplicationRecord record : application.getRecords()) {
            record.getInformalSubjectsOrModules().forEach(informalSubjectOrModule -> {
                if (Boolean.TRUE.equals(application.getIsVocational())) {
                    addTransferredModuleAndThemeIdsMap(informalSubjectOrModule, transferredModuleAndThemeIdsMaps);
                } else {
                    addTransferredSubjectId(informalSubjectOrModule, transferredSubjectIds);
                }
            });
            record.getFormalSubjectsOrModules().forEach(formalSubjectOrModule -> {
                if (Boolean.TRUE.equals(application.getIsVocational())) {
                    addTransferredModuleAndThemeIdsMap(formalSubjectOrModule, transferredModuleAndThemeIdsMaps);
                } else {
                    addTransferredSubjectId(formalSubjectOrModule, transferredSubjectIds);
                }
            });
        }
        if (!transferredSubjectIds.isEmpty()) {
            checkThatSubjectsAreNotAlreadyTransferred(EntityUtil.getId(application.getStudent()), new ArrayList<>(transferredSubjectIds));
        } else if (!transferredModuleAndThemeIdsMaps.isEmpty()) {
            List<Long> transferredModuleIds = StreamUtil.toMappedList(m -> m.keySet().toArray(new Long[0])[0], transferredModuleAndThemeIdsMaps);
            checkThatModulesAreNotAlreadyTransferred(EntityUtil.getId(application.getStudent()), transferredModuleIds);
        }
    }

    private static void addTransferredModuleAndThemeIdsMap(ApelApplicationInformalSubjectOrModule informalModule,
            Set<Map<Long, Long>> transferredModuleAndThemeIdsMaps) {
        if (Boolean.TRUE.equals(informalModule.getTransfer())) {
            if (Boolean.TRUE.equals(informalModule.getTransfer()) && informalModule.getCurriculumVersionOmodule() != null
                    && informalModule.getCurriculumVersionOmoduleTheme() == null) {
                addTransferredModuleMap(transferredModuleAndThemeIdsMaps, EntityUtil.getId(informalModule.getCurriculumVersionOmodule()), null);
            } else if (Boolean.TRUE.equals(informalModule.getTransfer()) && informalModule.getCurriculumVersionOmodule() != null
                    && informalModule.getCurriculumVersionOmoduleTheme() != null) {
                addTransferredModuleMap(transferredModuleAndThemeIdsMaps, EntityUtil.getId(informalModule.getCurriculumVersionOmodule()),
                        EntityUtil.getId(informalModule.getCurriculumVersionOmoduleTheme()));
            }
        }
    }
    
    private static void addTransferredModuleMap(Set<Map<Long, Long>> transferredModuleIds, Long moduleId, Long themeId) {
        Map<Long, Long> fullModule = new HashMap<>();
        Map<Long, Long> transfer = new HashMap<>();
        fullModule.put(moduleId, null);
        transfer.put(moduleId, themeId);
        
        if (themeId == null && (isModuleOrThemeFromModuleAlreadyTransferred(moduleId, transferredModuleIds) || !transferredModuleIds.add(transfer))) {
            throw new ValidationFailedException("apel.error.moduleTransferredMoreThanOnce");
        } else if (themeId != null && (transferredModuleIds.contains(fullModule) || !transferredModuleIds.add(transfer))) {
            throw new ValidationFailedException("apel.error.moduleTransferredMoreThanOnce");
        }
    }

    private static void addTransferredModuleAndThemeIdsMap(ApelApplicationFormalSubjectOrModule formalModule, Set<Map<Long, Long>> transferredModuleAndThemeIdsMaps) {
        if (formalModule.getCurriculumVersionOmodule() != null) {
            Map<Long, Long> transfer = new HashMap<>();
            Long moduleId = EntityUtil.getId(formalModule.getCurriculumVersionOmodule());
            transfer.put(moduleId, null);
            
            if (Boolean.TRUE.equals(formalModule.getTransfer()) && (isModuleOrThemeFromModuleAlreadyTransferred(moduleId, transferredModuleAndThemeIdsMaps)
                    || !transferredModuleAndThemeIdsMaps.add(transfer))) {
                throw new ValidationFailedException("apel.error.moduleTransferredMoreThanOnce");
            }
        }
    }

    private static boolean isModuleOrThemeFromModuleAlreadyTransferred(Long moduleId, Set<Map<Long, Long>> transferredModuleAndThemeIdsMaps) {
        return transferredModuleAndThemeIdsMaps.stream().anyMatch(e -> e.containsKey(moduleId));
    }

    private static void addTransferredSubjectId(ApelApplicationInformalSubjectOrModule informalSubject, Set<Long> transferredSubjectIds) {
        if (Boolean.TRUE.equals(informalSubject.getTransfer()) && informalSubject.getSubject() != null && !transferredSubjectIds.add(EntityUtil.getId(informalSubject.getSubject()))) {
            throw new ValidationFailedException("apel.error.subjectTransferredMoreThanOnce");
        }
    }

    private static void addTransferredSubjectId(ApelApplicationFormalSubjectOrModule formalSubject, Set<Long> transferredSubjectIds) {
        if (Boolean.TRUE.equals(formalSubject.getTransfer()) && formalSubject.getSubject() != null && !transferredSubjectIds.add(EntityUtil.getId(formalSubject.getSubject()))) {
            throw new ValidationFailedException("apel.error.subjectTransferredMoreThanOnce");
        }
    }

    /**
     * Set APEL application's status back to 'Submitted'
     * 
     * @param application
     * @return
     */
    public ApelApplication sendBack(ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_E);
        return EntityUtil.save(application, em);
    }

    /**
     * Set APEL application's status back to 'Rejected' and add comment explaining the rejection
     * if application is reviewed add comment as decision
     * 
     * @param application
     * @param commentForm
     * @return
     */
    public ApelApplication reject(ApelApplication application, ApelApplicationCommentForm commentForm) {
        if (ApelApplicationStatus.VOTA_STAATUS_V.name().equals(EntityUtil.getCode(application.getStatus()))) {
            application.setDecision(commentForm.getAddInfo());
        }
        createComment(application, commentForm);
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_L);
        return EntityUtil.save(application, em);
    }

    /**
     * Remove APEL application confirmation, set application's status back to 'Being confirmed'
     * 
     * @param application
     * @return
     */
    public ApelApplication removeConfirmation(ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_Y);
        application.setConfirmedBy(null);
        application.setConfirmed(null);
        return EntityUtil.save(application, em);
    }

    public CurriculumVersionHigherModuleDto subjectModule(Subject subject) {
        List<CurriculumVersion> versions = curriculumVersionRepository.findAllDistinctByModules_Subjects_Subject_id(subject.getId());
        List<CurriculumVersionHigherModule> modules = new ArrayList<>(); 

        versions.forEach(v -> v.getModules().forEach(m -> modules.add(m)));

        for (CurriculumVersionHigherModule module : modules) {
            List<CurriculumVersionHigherModuleSubject> moduleSubjects = new ArrayList<>(module.getSubjects());
            for (CurriculumVersionHigherModuleSubject moduleSubject : moduleSubjects) {
                if (EntityUtil.getId(moduleSubject.getSubject()).equals(subject.getId())) {
                    return CurriculumVersionHigherModuleDto.of(module);
                }
            }
        }
        return null;
    }
    
    public List<AutocompleteResult> committeesForSelection(HoisUserDetails user, ApelApplication application) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from committee c"
                + " join committee_member cm on c.id = cm.committee_id"
                + " join person p on p.id = cm.person_id");
        
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("c.type_code = :type", "type", CommitteeType.KOMISJON_V.name());
        
        String validFilter = "(c.valid_from <= '" + JpaQueryUtil.parameterAsTimestamp(LocalDate.now())
                + "' and c.valid_thru >= '" + JpaQueryUtil.parameterAsTimestamp(LocalDate.now()) + "')";
        Long currentCommitteeId =  EntityUtil.getNullableId(application.getCommittee());
        if (currentCommitteeId != null) {
            validFilter += " or c.id = " + currentCommitteeId;
        }
        qb.filter(validFilter);
        
        qb.groupBy("c.id");
        List<?> result = qb.select("distinct c.id, c.name_et,"
                + " array_to_string(array_agg(p.firstname || ' ' || p.lastname), ', ') as members", em)
                .getResultList();
        
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            String caption = (name != null ? name : "-") + " (" + resultAsString(r, 2) + ")";
            return new AutocompleteResult(resultAsLong(r, 0), caption, caption);
        }, result);
    }
}
