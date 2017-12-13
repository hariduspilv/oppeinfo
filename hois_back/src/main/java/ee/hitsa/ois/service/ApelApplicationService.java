package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
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
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApelApplicationUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
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

@Transactional
@Service
public class ApelApplicationService {

    @Autowired
    private EntityManager em;
    
    @Autowired
    private ApelSchoolService apelSchoolService;

    private static final String RPM_FROM = "from apel_application aa"
            + " inner join student s on aa.student_id = s.id"
            + " inner join person p on s.person_id=p.id"
            + " inner join curriculum_version curv on s.curriculum_version_id=curv.id"
            + " inner join curriculum cur on curv.curriculum_id=cur.id"
            + " inner join classifier c on aa.status_code=c.code";
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(RPM_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.optionalCriteria("aa.student_id = :studentId", "studentId", criteria.getStudent());
        if (user.isStudent()) {
            qb.requiredCriteria("aa.student_id = :studentId", "studentId", user.getStudentId());
        }
        qb.optionalCriteria("aa.status_code in (:status)", "status", criteria.getStatus());
        
        qb.optionalCriteria("aa.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("aa.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("aa.confirmed >= :confirmedFrom", "confirmedFrom", criteria.getConfirmedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("aa.confirmed <= :confirmedThru", "confirmedThru", criteria.getConfirmedThru(), DateUtils::lastMomentOfDay);
        
        return JpaQueryUtil.pagingResult(qb, RPM_SELECT, em, pageable).map(r -> {
            ApelApplicationSearchDto dto = new ApelApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStatus(resultAsString(r, 1));
            dto.setInserted(resultAsLocalDate(r, 8));
            dto.setConfirmed(resultAsLocalDate(r, 9));
            
            String studentName = PersonUtil.fullname(resultAsString(r, 3), resultAsString(r, 4));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 2), studentName, studentName));
            
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 7)));
            
            return dto;
        });
    }
    
    /**Get student APEL application
     * 
     * @param application
     * @return
     */
    public ApelApplicationDto get(ApelApplication application) {
        return ApelApplicationDto.of(application);
    }
    
    
    /**Create new student APEL application
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

    /**Store student APEL application
     * 
     * @param user
     * @param application
     * @param applicationForm
     * @return
     */
    public ApelApplication save(HoisUserDetails user, ApelApplication application, ApelApplicationForm applicationForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(applicationForm, application, "records", "files");
        updateRecords(user, application, applicationForm);
        return EntityUtil.save(application, em);
    }

    /**Delete student APEL application
     * 
     * @param user
     * @param application
     */
    public void delete(HoisUserDetails user, ApelApplication application) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(application, em);
    }

    /**Create new APEL application's file
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

    /**Delete APEL application's file
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

    /**Create new APEL application's record
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

    /**Update APEL application's record's data
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
    
    /**Delete APEL application's record
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
    
    private void validateInformalSubjectOrModule(ApelApplicationRecord record, ApelApplicationInformalSubjectOrModule subjectOrModule) {
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
            form.setApelSchool(form.getNewApelSchool() != null ? apelSchoolService.create(user, form.getNewApelSchool()).getId() : form.getApelSchool());
            subjectOrModule.setApelApplicationRecord(record);
            updateFormalSubjectOrModule(subjectOrModule, form);
            validateFormalSubjectOrModule(record, subjectOrModule);
            return subjectOrModule;
        }, (form, subjectOrModule) -> {
            EntityUtil.bindToEntity(form, subjectOrModule, "apelApplicationRecord", "apelSchool", "subject", "curriculumVersionHmodule", 
                    "curriculumVersionOmodule", "type", "grade", "assessment");
            updateFormalSubjectOrModule(subjectOrModule, form);
            validateFormalSubjectOrModule(record, subjectOrModule);
        });
    }
    
    private void updateFormalSubjectOrModule(ApelApplicationFormalSubjectOrModule subjectOrModule, ApelApplicationFormalSubjectOrModuleForm form) {
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
    
    private void validateFormalSubjectOrModule(ApelApplicationRecord record, ApelApplicationFormalSubjectOrModule subjectOrModule) {
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
    
    private void validateFormalReplacedSubjectOrModule(ApelApplicationRecord record, ApelApplicationFormalReplacedSubjectOrModule subjectOrModule) {
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
    
    /**Create new APEL application's comment
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
    /**Update APEL application's comment
     * 
     * @param commentForm
     * @param comment
     * @return
     */
    public ApelApplicationComment updateComment(ApelApplicationCommentForm commentForm, ApelApplicationComment comment) {
        ApelApplicationComment updatedComment = EntityUtil.bindToEntity(commentForm, comment);
        return EntityUtil.save(updatedComment, em);
    }
    
    /**Delete APEL application's comment
     * 
     * @param user
     * @param comment
     */
    public void deleteComment(HoisUserDetails user, ApelApplicationComment comment) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(comment, em);
        comment.getApelApplication().getComments().remove(comment);
    }

    /**Set APEL application's status to 'Submitted'
     * 
     * @param application
     * @return
     */
    public ApelApplication submit(ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_E);
        return EntityUtil.save(application, em);
    }

    /**Set APEL application's status to 'Being confirmed'
     * 
     * @param application
     * @return
     */
    public ApelApplication sendToConfirm(ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_Y);
        return EntityUtil.save(application, em);
    }

    /**Set APEL application's status back to 'Drafting'
     * 
     * @param application
     * @return
     */
    public ApelApplication sendBackToCreation(ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_K);
        return EntityUtil.save(application, em);
    }
    
    /**Set APEL application's status to 'Confirmed'
     * 
     * @param application
     * @return
     */
    public ApelApplication confirm(HoisUserDetails user, ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_C);
        application.setConfirmedBy(user.getUsername());
        application.setConfirmed(LocalDateTime.now());
        return EntityUtil.save(application, em);
    }
    
    /**Set APEL application's status back to 'Submitted'
     * 
     * @param application
     * @return
     */
    public ApelApplication sendBack(ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_E);
        return EntityUtil.save(application, em);
    }
    
    
    /**Set APEL application's status back to 'Rejected' and add comment explaining the rejection
     * 
     * @param application
     * @param commentForm
     * @return
     */
    public ApelApplication reject(ApelApplication application, ApelApplicationCommentForm commentForm) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_L);
        createComment(application, commentForm);
        return EntityUtil.save(application, em);
    }
    
    /** Remove APEL application confirmation, set application's status back to 'Being confirmed'
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
    
}
