package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ee.hitsa.ois.domain.application.ApplicationPlannedSubject;
import ee.hitsa.ois.domain.application.ApplicationPlannedSubjectEquivalent;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;
import ee.hitsa.ois.web.dto.timetable.DateRangeDto;
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
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.ApelSubjectType;
import ee.hitsa.ois.enums.CommitteeType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.NominalDurationExtension;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.enums.VocationalGradeType;
import ee.hitsa.ois.message.ApelApplicationCreated;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApelApplicationUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
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
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleResult;

@Transactional
@Service
public class ApelApplicationService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ApelSchoolService apelSchoolService;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private Validator validator;

    private static final String RPM_FROM = "from apel_application aa"
            + " join student s on aa.student_id = s.id"
            + " left join student_group sg on s.student_group_id = sg.id"
            + " join person p on s.person_id = p.id"
            + " join curriculum_version curv on s.curriculum_version_id = curv.id"
            + " join curriculum cur on curv.curriculum_id = cur.id"
            + " join classifier c on aa.status_code = c.code";
    private static final String RPM_SELECT = "aa.id as application_id, aa.status_code as application_status, s.id as student_id,"
            + " p.firstname as student_firstname, p.lastname as student_lastname, cur.id as curriculum_id, cur.name_et as curriculum_name_et,"
            + " cur.name_en as curriculum_name_en, aa.inserted, aa.confirmed, sg.code";

    private static final String OUTCOME_RESULT_ADDINFO = "VÕTA";
    
    /**
     * Search student APEL applications
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<ApelApplicationSearchDto> search(HoisUserDetails user, ApelApplicationSearchCommand criteria,
            Pageable pageable) {
        boolean hasApelViewPerm = UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_VOTA);
        boolean hasApelCommitteeViewPerm = UserUtil.hasPermission(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_VOTAKOM);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(RPM_FROM);
        qb.requiredCriteria("aa.school_id = :schoolId", "schoolId", user.getSchoolId());

        if (user.isStudent()) {
            qb.requiredCriteria("aa.student_id = :studentId", "studentId", user.getStudentId());
        } else {
            String allowedApplications = "";
            if (hasApelViewPerm && user.isLeadingTeacher()) {
                allowedApplications += "cur.id in (:userCurriculumIds)";
                qb.parameter("userCurriculumIds", user.getCurriculumIds());
                if (hasApelCommitteeViewPerm) {
                    allowedApplications += " or ";
                }
            }
            if (hasApelCommitteeViewPerm && (!user.isSchoolAdmin() || !hasApelViewPerm)) {
                allowedApplications += "exists (select 1 from committee co"
                        + " join committee_member cm on co.id = cm.committee_id"
                        + " where co.id = aa.committee_id and cm.person_id = :personId)";
                qb.parameter("personId", user.getPersonId());
            }
            if (!allowedApplications.isEmpty()) {
                qb.filter("(" + allowedApplications + ")");
            }

            if (!hasApelViewPerm) {
                qb.requiredCriteria("aa.status_code = :status", "status", ApelApplicationStatus.VOTA_STAATUS_V);
            }
        }

        qb.optionalCriteria("aa.student_id = :studentId", "studentId", criteria.getStudent());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname",
                "concat(p.firstname, ' ', p.lastname, ' (', p.idcode, ')')"), "name", criteria.getName());
        qb.optionalCriteria("aa.status_code in (:status)", "status", criteria.getStatus());

        qb.optionalCriteria("aa.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("aa.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(),
                DateUtils::lastMomentOfDay);

        qb.optionalCriteria("aa.confirmed >= :confirmedFrom", "confirmedFrom", criteria.getConfirmedFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("aa.confirmed <= :confirmedThru", "confirmedThru", criteria.getConfirmedThru(),
                DateUtils::lastMomentOfDay);

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
            dto.setStudentGroup(resultAsString(r, 10));
            String studentName = PersonUtil.fullname(resultAsString(r, 3), resultAsString(r, 4));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 2), studentName, studentName));
            Long curriculumId = resultAsLong(r, 5);
            dto.setCurriculum(new AutocompleteResult(curriculumId, resultAsString(r, 6), resultAsString(r, 7)));
            dto.setCanEdit(Boolean.valueOf(ApelApplicationUtil.canEdit(user, dto.getStatus(), curriculumId)));
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

        List<AbroadStudiesHolder> finishedAbroadStudies = finishedAbroadStudies(application.getStudent());
        dto.setAbroadStudyPeriods(StreamUtil.nullSafeList(finishedAbroadStudies).stream()
                .filter(StreamUtil.distinctByKey(as -> as.getApplicationId()))
                .map(as -> as.getPeriod()).collect(Collectors.toList()));
        dto.setHasAbroadStudies(Boolean.valueOf(!finishedAbroadStudies.isEmpty()));
        boolean hasPlannedSubjectsToTransfer = !notTransferredPlannedSubjectIds(finishedAbroadStudies).isEmpty();
        dto.setHasPlannedSubjectsToTransfer(Boolean.valueOf(hasPlannedSubjectsToTransfer));
        dto.setCanExtendNominalDuration(Boolean.valueOf(canExtendNominalDuration(application)
            && ApelApplicationUtil.hasRightsToExtendNominalDuration(user, application)));
        dto.setOldNominalStudyEnd(application.getOldNominalStudyEnd() != null ? application.getOldNominalStudyEnd()
                : application.getStudent().getNominalStudyEnd());
        dto.setHasMultipleNominalDurationExtensions(Boolean.valueOf(hasMultipleNominalDurationExtensions(application)));

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

        Student student = em.getReference(Student.class, applicationForm.getStudent().getId());
        application.setStudent(student);
        application.setIsVocational(
                Boolean.valueOf(CurriculumUtil.isVocational(student.getCurriculumVersion().getCurriculum())));
        application.setSchool(em.getReference(School.class, user.getSchoolId()));
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_K);

        return save(user, application, applicationForm);
    }

    /**
     * Saves which subjects/modules are transferred and application committee
     *
     * @param application
     * @param applicationForm
     * @return
     */
    public ApelApplication save(HoisUserDetails user, ApelApplication application, ApelApplicationForm applicationForm) {
        return save(user, application, applicationForm, false);
    }

    private ApelApplication save(HoisUserDetails user, ApelApplication application,
            ApelApplicationForm applicationForm, boolean validate) {
        if (validate) validateSubmittedApplication(application, true);
        EntityUtil.bindToEntity(applicationForm, application, "nominalType", "newNominalStudyEnd", "oldNominalStudyEnd",
                "isEhisSent", "records", "committee", "decision", "files");

        if (ApelApplicationUtil.hasRightsToExtendNominalDuration(user, application)) {
            setNominalStudyExtension(application, applicationForm);
        }

        for (ApelApplicationRecordForm recordForm : applicationForm.getRecords()) {
            for (ApelApplicationFormalSubjectOrModuleForm formalForm : recordForm.getFormalSubjectsOrModules()) {
                ApelApplicationFormalSubjectOrModule subjectOrModule = EntityUtil
                        .getOptionalOne(ApelApplicationFormalSubjectOrModule.class, formalForm.getId(), em);
                subjectOrModule.setTransfer(formalForm.getTransfer());
            }
            for (ApelApplicationInformalSubjectOrModuleForm informalForm : recordForm.getInformalSubjectsOrModules()) {
                ApelApplicationInformalSubjectOrModule subjectOrModule = EntityUtil
                        .getOptionalOne(ApelApplicationInformalSubjectOrModule.class, informalForm.getId(), em);
                subjectOrModule.setTransfer(informalForm.getTransfer());
            }
        }

        application.setCommittee(EntityUtil.getOptionalOne(Committee.class, applicationForm.getCommitteeId(), em));
        return EntityUtil.save(application, em);
    }

    private void setNominalStudyExtension(ApelApplication application, ApelApplicationForm applicationForm) {
        // nominal study extension can be set if all the conditions are currently met
        // if it's already set then it can be changed, but if it's saved as 'no extending' then null everything
        if (!canExtendNominalDuration(application)) {
            if (application.getNominalType() == null || NominalDurationExtension.NOM_PIKEND_0.name()
                    .equals(applicationForm.getNominalType())) {
                applicationForm.setNominalType(null);
            }
        }

        application.setNominalType(EntityUtil.getOptionalOne(applicationForm.getNominalType(), em));
        if (application.getNominalType() != null) {
            application.setNewNominalStudyEnd(applicationForm.getNewNominalStudyEnd());
            if (application.getIsEhisSent() == null) application.setIsEhisSent(Boolean.FALSE);
        } else {
            application.setNewNominalStudyEnd(null);
            application.setIsEhisSent(null);
        }
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

    /**
     * Create new APEL application's record
     * 
     * @param user
     * @param application
     * @param recordForm
     * @return
     */
    public ApelApplicationRecord createRecord(HoisUserDetails user, ApelApplication application,
            ApelApplicationRecordForm recordForm) {
        ApelApplicationRecord record = new ApelApplicationRecord();
        record.setApelApplication(application);
        return updateRecord(user, recordForm, record, application.getIsVocational());
    }

    /**
     * Update APEL application's record's data
     * 
     * @param user
     * @param recordForm
     * @param record
     * @return
     */
    public ApelApplicationRecord updateRecord(HoisUserDetails user, ApelApplicationRecordForm recordForm,
            ApelApplicationRecord record, Boolean isVocational) {
        ApelApplicationRecord updatedRecord = EntityUtil.bindToEntity(recordForm, record, "apelApplication", 
                "informalExperiences", "informalSubjectsOrModules", "formalSubjectsOrModules", "formalReplacedSubjectsOrModules");
        updateInformalExperiences(recordForm, updatedRecord);
        updateInformalSubjectsOrModules(recordForm, updatedRecord);
        updateFormalSubjectsOrModules(user, recordForm, updatedRecord, isVocational);
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
        EntityUtil.bindEntityCollection(record.getInformalExperiences(), ApelApplicationInformalExperience::getId,
                recordForm.getInformalExperiences(), ApelApplicationInformalExperienceForm::getId, form -> {
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
        EntityUtil.bindEntityCollection(record.getInformalSubjectsOrModules(),
                ApelApplicationInformalSubjectOrModule::getId, recordForm.getInformalSubjectsOrModules(),
                ApelApplicationInformalSubjectOrModuleForm::getId, form -> {
            ApelApplicationInformalSubjectOrModule subjectOrModule = EntityUtil.bindToEntity(form, 
                    new ApelApplicationInformalSubjectOrModule(), "apelApplicationRecord", "subject", "curriculumVersionHmodule",
                    "curriculumVersionOmodule", "curriculumVersionOmoduleTheme", "outcomes");
            subjectOrModule.setApelApplicationRecord(record);
            updateInformalSubjectOrModule(form, subjectOrModule);
            return subjectOrModule;
        }, (form, subjectOrModule) -> {
            EntityUtil.bindToEntity(form, subjectOrModule, "subject", "curriculumVersionHmodule",
                    "curriculumVersionOmodule", "curriculumVersionOmoduleTheme", "outcomes");
            updateInformalSubjectOrModule(form, subjectOrModule);
        });
    }

    private void updateInformalSubjectOrModule(
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
    }

    private void updateInformalSubjectsOrModulesOutcomes(ApelApplicationInformalSubjectOrModuleForm subjectOrModuleForm,
            ApelApplicationInformalSubjectOrModule subjectOrModule) {
        EntityUtil.bindEntityCollection(subjectOrModule.getOutcomes(),
                ApelApplicationInformalSubjectOrModuleOutcomes::getId, subjectOrModuleForm.getOutcomes(),
                ApelApplicationInformalSubjectOrModuleOutcomesForm::getId, form -> {
            ApelApplicationInformalSubjectOrModuleOutcomes subjectOrModuleOutcomes = EntityUtil.bindToEntity(form, 
                    new ApelApplicationInformalSubjectOrModuleOutcomes(), "apelApplicationInformalSubjectOrModule", "curriculumModuleOutcomes");
            subjectOrModuleOutcomes.setApelApplicationInformalSubjectOrModule(subjectOrModule);
            subjectOrModuleOutcomes.setCurriculumModuleOutcomes(em.getReference(CurriculumModuleOutcome.class, form.getCurriculumModuleOutcomes().getId()));
            return subjectOrModuleOutcomes;
        }, null);
    }

    private void updateFormalSubjectsOrModules(HoisUserDetails user, ApelApplicationRecordForm recordForm,
            ApelApplicationRecord record, Boolean isVocational) {
        EntityUtil.bindEntityCollection(record.getFormalSubjectsOrModules(),
                ApelApplicationFormalSubjectOrModule::getId, recordForm.getFormalSubjectsOrModules(),
                ApelApplicationFormalSubjectOrModuleForm::getId, form -> {
            ApelApplicationFormalSubjectOrModule subjectOrModule = EntityUtil.bindToEntity(form, 
                    new ApelApplicationFormalSubjectOrModule(), "apelApplicationRecord", "apelSchool", "subject", "curriculumVersionHmodule", 
                    "curriculumVersionOmodule", "type", "grade", "assessment", "isOptional");
            subjectOrModule.setApelApplicationRecord(record);
            updateFormalSubjectOrModule(user, subjectOrModule, form, isVocational);
            validateFormalSubjectOrModule(subjectOrModule, isVocational);
            return subjectOrModule;
        }, (form, subjectOrModule) -> {
            EntityUtil.bindToEntity(form, subjectOrModule, "apelApplicationRecord", "apelSchool", "subject", "curriculumVersionHmodule", 
                    "curriculumVersionOmodule", "type", "grade", "assessment", "isOptional");
            updateFormalSubjectOrModule(user, subjectOrModule, form, isVocational);
            validateFormalSubjectOrModule(subjectOrModule, isVocational);
        });
    }
    
    private void updateFormalSubjectOrModule(HoisUserDetails user, ApelApplicationFormalSubjectOrModule subjectOrModule,
            ApelApplicationFormalSubjectOrModuleForm form, Boolean isVocational) {
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
        subjectOrModule.setIsMySchool(form.getIsMySchool() != null ? form.getIsMySchool() : null);
        if (Boolean.FALSE.equals(isVocational)) {
            subjectOrModule.setIsOptional(form.getIsOptional() != null ? form.getIsOptional() : Boolean.TRUE);
        }
    }
    
    private void validateFormalSubjectOrModule(ApelApplicationFormalSubjectOrModule subjectOrModule, Boolean isVocational) {
        ApelSubjectType type = ApelSubjectType.valueOf(EntityUtil.getCode(subjectOrModule.getType()));
        Set<ConstraintViolation<ApelApplicationFormalSubjectOrModule>> errors = validator.validate(subjectOrModule,
                type.getValidationGroup(isVocational, subjectOrModule.getIsMySchool()));

        if (!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
        }
    }
    
    private void updateFormalReplacedSubjectsOrModules(ApelApplicationRecordForm recordForm, ApelApplicationRecord record) {
        EntityUtil.bindEntityCollection(record.getFormalReplacedSubjectsOrModules(),
                ApelApplicationFormalReplacedSubjectOrModule::getId, recordForm.getFormalReplacedSubjectsOrModules(),
                ApelApplicationFormalReplacedSubjectOrModuleForm::getId, form -> {
                    ApelApplicationFormalReplacedSubjectOrModule replacedSubjectOrModule = EntityUtil.bindToEntity(form,
                            new ApelApplicationFormalReplacedSubjectOrModule(), "apelApplicationRecord", "subject",
                            "curriculumVersionOmodule", "curriculumVersionOmoduleTheme");
            replacedSubjectOrModule.setApelApplicationRecord(record);
            replacedSubjectOrModule.setSubject(form.getSubject() != null 
                    ? em.getReference(Subject.class, form.getSubject()) : null);
            replacedSubjectOrModule.setCurriculumVersionOmodule(form.getCurriculumVersionOmodule() != null 
                    ? em.getReference(CurriculumVersionOccupationModule.class, form.getCurriculumVersionOmodule()) : null);
            replacedSubjectOrModule.setCurriculumVersionOmoduleTheme(form.getCurriculumVersionOmoduleTheme() != null 
                    ? em.getReference(CurriculumVersionOccupationModuleTheme.class, form.getCurriculumVersionOmoduleTheme()) : null);
            return replacedSubjectOrModule;
        }, (form, replacedSubjectOrModule) -> {
            EntityUtil.bindToEntity(form, replacedSubjectOrModule, "apelApplicationRecord", "subject",
                    "curriculumVersionOmodule", "curriculumVersionOmoduleTheme");
            replacedSubjectOrModule.setSubject(form.getSubject() != null 
                    ? em.getReference(Subject.class, form.getSubject()) : null);
            replacedSubjectOrModule.setCurriculumVersionOmodule(form.getCurriculumVersionOmodule() != null 
                    ? em.getReference(CurriculumVersionOccupationModule.class, form.getCurriculumVersionOmodule()) : null);
            replacedSubjectOrModule.setCurriculumVersionOmoduleTheme(form.getCurriculumVersionOmoduleTheme() != null 
                    ? em.getReference(CurriculumVersionOccupationModuleTheme.class, form.getCurriculumVersionOmoduleTheme()) : null);
        });
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
        validateSubmittedApplication(application, false);
        if (application.getRecords().isEmpty()) {
            throw new ValidationFailedException("apel.error.atLeastOneFormalOrInformalLearning");
        }

        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_E);
        application = EntityUtil.save(application, em);
        ApelApplicationCreated data = new ApelApplicationCreated(application);
        automaticMessageService.sendMessageToStudentAndSchoolAdmins(MessageType.TEATE_LIIK_VOTA, application.getStudent(), data);
        return application;
    }

    private void validateSubmittedApplication(ApelApplication application, boolean strictValidation) {
        boolean vocational = Boolean.TRUE.equals(application.getIsVocational());
        Set<Long> recordsWithErrors = new HashSet<>();
        Map<Long, List<String>> recordErrors = new HashMap<>();

        for (ApelApplicationRecord record : application.getRecords()) {
            // formal subject or modules need to be validated because they can be transferred from abroad studies applications
            validateFormalSubjectsOrModules(record, recordsWithErrors, recordErrors, vocational);

            if (strictValidation && !recordsWithErrors.contains(record.getId())) {
                validateTransferredCredits(record, recordsWithErrors, recordErrors, vocational);
            }
        }

        if(!recordsWithErrors.isEmpty()) {
            Map<Object, Object> params = new HashMap<>();
            params.put("recordsWithErrors", recordsWithErrors);
            params.put("recordErrors", recordErrors);
            throw new ValidationFailedException("apel.error.formalRecordsHaveErrors", params);
        }
    }

    private void validateFormalSubjectsOrModules(ApelApplicationRecord record, Set<Long> recordsWithErrors,
            Map<Long, List<String>> recordErrors, boolean vocational) {
        for (ApelApplicationFormalSubjectOrModule subjectOrModule : record.getFormalSubjectsOrModules()) {
            ApelSubjectType type = ApelSubjectType.valueOf(EntityUtil.getCode(subjectOrModule.getType()));
            Set<ConstraintViolation<ApelApplicationFormalSubjectOrModule>> errors = validator.validate(subjectOrModule,
                    type.getValidationGroup(Boolean.valueOf(vocational), subjectOrModule.getIsMySchool()));

            if (!errors.isEmpty()) {
                recordsWithErrors.add(record.getId());
                addRecordError(record.getId(), "apel.error.incompleteData", recordErrors);
                break;
            }
        }
    }

    private static void validateTransferredCredits(ApelApplicationRecord record, Set<Long> recordsWithErrors,
            Map<Long, List<String>> recordErrors, boolean vocational) {
        BigDecimal replacedCredits;
        if (vocational) {
            List<BigDecimal> moduleCredits = record.getFormalReplacedSubjectsOrModules().stream()
                    .filter(m -> m.getCurriculumVersionOmoduleTheme() == null)
                    .map(m -> m.getCurriculumVersionOmodule().getCurriculumModule().getCredits())
                    .collect(Collectors.toList());
            List<BigDecimal> themeCredits = record.getFormalReplacedSubjectsOrModules().stream()
                    .filter(m -> m.getCurriculumVersionOmoduleTheme() != null)
                    .map(m -> m.getCurriculumVersionOmoduleTheme().getCredits())
                    .collect(Collectors.toList());
            replacedCredits = StreamUtil.sumBigDecimals(s -> s, Stream.concat(moduleCredits.stream(),
                    themeCredits.stream()));
        } else {
            if (allTransferredSubjectsInFreeChoiceModules(record)) {
                return;
            }
            replacedCredits = StreamUtil.sumBigDecimals(s -> s.getSubject().getCredits(),
                    record.getFormalReplacedSubjectsOrModules());
        }
        BigDecimal transferredCredits = StreamUtil.sumBigDecimals(ApelApplicationFormalSubjectOrModule::getCredits,
                record.getFormalSubjectsOrModules());

        if (replacedCredits.compareTo(transferredCredits) > 0) {
            recordsWithErrors.add(record.getId());
            String error = vocational ? "apel.error.thereMustBeMoreTransferableCreditsThanSubstitutableCreditsVocational"
                    : "apel.error.thereMustBeMoreTransferableCreditsThanSubstitutableCreditsHigher";
            addRecordError(record.getId(), error, recordErrors);
        }
    }

    private static void addRecordError(Long recordId, String error, Map<Long, List<String>> recordErrors) {
        if (!recordErrors.containsKey(recordId)) {
            recordErrors.put(recordId, new ArrayList<>());
        }
        recordErrors.get(recordId).add(error);
    }



    private static boolean allTransferredSubjectsInFreeChoiceModules(ApelApplicationRecord record) {
        return record.getFormalSubjectsOrModules().stream().allMatch(s -> s.getCurriculumVersionHmodule() != null &&
                HigherModuleType.KORGMOODUL_V.name().equals(EntityUtil.getCode(s.getCurriculumVersionHmodule().getType())));
    }


    /**
     * Set APEL application's status to 'Being confirmed'
     * if application is reviewed add decision and duplicate it to comments
     * 
     * @param application
     * @return
     */
    public ApelApplication sendToConfirm(HoisUserDetails user, ApelApplication application, ApelApplicationForm applicationForm) {
        if (application.getRecords().isEmpty()) {
            throw new ValidationFailedException("apel.error.atLeastOneFormalOrInformalLearning");
        }

        save(user, application,  applicationForm, true);
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
    public ApelApplication sendToCommittee(HoisUserDetails user, ApelApplication application, ApelApplicationForm applicationForm) {
        if (application.getRecords().isEmpty()) {
            throw new ValidationFailedException("apel.error.atLeastOneFormalOrInformalLearning");
        }
        save(user, application,  applicationForm, true);
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
        if (Boolean.TRUE.equals(application.getIsVocational())) {
            checkThatModulesAreTransferredOnlyOnce(application);
        } else {
            checkThatSubjectsAreReplacedOnlyOnce(application);
        }

        if (Boolean.FALSE.equals(application.getIsEhisSent())) {
            Student student = application.getStudent();
            application.setOldNominalStudyEnd(student.getNominalStudyEnd());
            student.setNominalStudyEnd(application.getNewNominalStudyEnd());
        }
        addStudentCurriculumOutcomeResults(user, application);
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_C);
        application.setConfirmedBy(user.getUsername());
        application.setConfirmed(LocalDateTime.now());
        return EntityUtil.save(application, em);
    }

    private void addStudentCurriculumOutcomeResults(HoisUserDetails user, ApelApplication application) {
        List<CurriculumModuleOutcome> apelApplicationOutcomes = apelApplicationOutcomes(application.getId());
        if (!apelApplicationOutcomes.isEmpty()) {
            Student student = application.getStudent();
            Map<Long, StudentCurriculumModuleOutcomesResult> existingResults = existingStudentCurriculumOutcomeResults(student.getId());
            for (CurriculumModuleOutcome outcome : apelApplicationOutcomes) {
                StudentCurriculumModuleOutcomesResult currentResult = existingResults.get(outcome.getId());
                String currentResultGrade = currentResult != null ? EntityUtil.getNullableCode(currentResult.getGrade()) : null;
                if (currentResult == null) {
                    StudentCurriculumModuleOutcomesResult result = new StudentCurriculumModuleOutcomesResult();
                    result.setStudent(student);
                    result.setCurriculumModuleOutcomes(outcome);
                    setOutcomeResultGrade(user, application, result);
                    EntityUtil.save(result, em);
                } else if (!OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE.contains(currentResultGrade)) {
                    if (currentResultGrade != null) {
                        currentResult.addToHistory();
                    }
                    setOutcomeResultGrade(user, application, currentResult);
                    EntityUtil.save(currentResult, em);
                }
            }
        }
    }

    private void setOutcomeResultGrade(HoisUserDetails user, ApelApplication application,
            StudentCurriculumModuleOutcomesResult result) {
        result.setGrade(em.getReference(Classifier.class, OccupationalGrade.KUTSEHINDAMINE_A.name()));
        result.setGradeDate(LocalDate.now());
        result.setGradeInserted(LocalDateTime.now());
        result.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(user.getUsername()));
        result.setAddInfo(OUTCOME_RESULT_ADDINFO);
        result.setApelApplication(application);
    }

    private List<CurriculumModuleOutcome> apelApplicationOutcomes(Long applicationId) {
        return em.createQuery("select aaismo.curriculumModuleOutcomes from ApelApplicationInformalSubjectOrModuleOutcomes aaismo" +
                " where aaismo.apelApplicationInformalSubjectOrModule.apelApplicationRecord.apelApplication.id = :applicationId",
                CurriculumModuleOutcome.class)
                .setParameter("applicationId", applicationId)
                .getResultList();
    }

    private Map<Long, StudentCurriculumModuleOutcomesResult> existingStudentCurriculumOutcomeResults(Long studentId) {
        List<StudentCurriculumModuleOutcomesResult> results = em.createQuery("select scmor from"
                + " StudentCurriculumModuleOutcomesResult scmor where scmor.student.id = :studentId",
                StudentCurriculumModuleOutcomesResult.class)
                .setParameter("studentId", studentId)
                .getResultList();
        return StreamUtil.toMap(r -> EntityUtil.getId(r.getCurriculumModuleOutcomes()), r -> r, results);
    }

    private void checkThatSubjectsDoNotAlreadyHavePositiveGrade(Long studentId, List<Long> replacedIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_higher_result shr");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", studentId);
        qb.requiredCriteria("shr.grade_code in (:positiveGrades)", "positiveGrades", Stream.of(HigherAssessment.values())
                .filter(HigherAssessment::getIsPositive).map(HigherAssessment::name).collect(Collectors.toList()));
        List<?> data = qb.select("shr.subject_id", em).getResultList();
        Set<Long> subjectsWithPositiveResultsIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        if (listsHaveCommonIds(replacedIds, subjectsWithPositiveResultsIds)) {
            throw new ValidationFailedException("apel.error.subjectHasPositiveGrade");
        }
    }

    private void checkThatModulesDoNotAlreadyHavePositiveGrade(Long studentId, List<Long> replacedIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr");
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", studentId);
        qb.requiredCriteria("svr.grade_code in (:positiveGrades)", "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        List<?> data = qb.select("svr.curriculum_version_omodule_id", em).getResultList();
        Set<Long> previouslyTransferredIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        if (listsHaveCommonIds(replacedIds, previouslyTransferredIds)) {
            throw new ValidationFailedException("apel.error.moduleHasPositiveGrade");
        }
    }

    private static boolean listsHaveCommonIds(List<Long> transferredIds, Set<Long> previouslyTransferredIds) {
        return !Collections.disjoint(transferredIds, previouslyTransferredIds);
    }

    private void checkThatModulesAreTransferredOnlyOnce(ApelApplication application) {
        Set<Map<Long, Long>> replacedModuleAndThemeIdsMaps = new HashSet<>();
        Set<Long> transferedFormalModules = new HashSet<>();
        
        for (ApelApplicationRecord record : application.getRecords()) {
            record.getInformalSubjectsOrModules().forEach(informalModule -> {
                addReplacedModuleAndThemeIdsMap(informalModule, replacedModuleAndThemeIdsMaps);
            });

            boolean transferedFormalLearning = recordIncludesTransferedFromalLearning(record);
            if (transferedFormalLearning) {
                record.getFormalReplacedSubjectsOrModules().forEach(formalReplacedModule -> {
                    addReplacedModuleAndThemeIdsMap(formalReplacedModule, replacedModuleAndThemeIdsMaps);
                });
            }
            
            record.getFormalSubjectsOrModules().forEach(formalModule -> {
                addTransferedModule(formalModule, transferedFormalModules);
            });
        }
        if (!replacedModuleAndThemeIdsMaps.isEmpty()) {
            List<Long> replacedModuleIds = StreamUtil.toMappedList(m -> m.keySet().toArray(new Long[0])[0],
                    replacedModuleAndThemeIdsMaps);
            checkThatModulesDoNotAlreadyHavePositiveGrade(EntityUtil.getId(application.getStudent()),
                    replacedModuleIds);
        }
    }

    private void checkThatSubjectsAreReplacedOnlyOnce(ApelApplication application) {
        Set<Long> replacedSubjectIds = new HashSet<>();
        Set<Long> transferedFormalSubjects = new HashSet<>();
        
        for (ApelApplicationRecord record : application.getRecords()) {
            record.getInformalSubjectsOrModules().forEach(informalSubject -> {
                addReplacedSubjectId(informalSubject, replacedSubjectIds);
            });

            boolean transferedFormalLearning = recordIncludesTransferedFromalLearning(record);
            if (transferedFormalLearning) {
                record.getFormalReplacedSubjectsOrModules().forEach(formalReplacedSubject -> {
                    addReplacedSubjectId(formalReplacedSubject, replacedSubjectIds);
                });
            }
            
            record.getFormalSubjectsOrModules().forEach(formalSubject -> {
                addTransferedSubject(formalSubject, transferedFormalSubjects);
            });
        }
        if (!replacedSubjectIds.isEmpty()) {
            checkThatSubjectsDoNotAlreadyHavePositiveGrade(EntityUtil.getId(application.getStudent()),
                    new ArrayList<>(replacedSubjectIds));
        }
    }

    private static boolean recordIncludesTransferedFromalLearning(ApelApplicationRecord record) {
        boolean includes = false;
        for (int i = 0; i < record.getFormalSubjectsOrModules().size(); i++) {
            if (Boolean.TRUE.equals(record.getFormalSubjectsOrModules().get(i).getTransfer())) {
                includes = true;
                break;
            }
        }
        
        return includes;
    }

    private static void addReplacedModuleAndThemeIdsMap(ApelApplicationInformalSubjectOrModule informalModule,
            Set<Map<Long, Long>> replacedModuleAndThemeIdsMaps) {
        if (Boolean.TRUE.equals(informalModule.getTransfer())) {
            if (Boolean.TRUE.equals(informalModule.getTransfer())
                    && informalModule.getCurriculumVersionOmodule() != null
                    && informalModule.getCurriculumVersionOmoduleTheme() == null) {
                addReplacedModuleMap(replacedModuleAndThemeIdsMaps,
                        EntityUtil.getId(informalModule.getCurriculumVersionOmodule()), null);
            } else if (Boolean.TRUE.equals(informalModule.getTransfer())
                    && informalModule.getCurriculumVersionOmodule() != null
                    && informalModule.getCurriculumVersionOmoduleTheme() != null) {
                addReplacedModuleMap(replacedModuleAndThemeIdsMaps,
                        EntityUtil.getId(informalModule.getCurriculumVersionOmodule()),
                        EntityUtil.getId(informalModule.getCurriculumVersionOmoduleTheme()));
            }
        }
    }

    private static void addReplacedModuleMap(Set<Map<Long, Long>> replacedModuleIds, Long moduleId,
            Long themeId) {
        Map<Long, Long> fullModule = new HashMap<>();
        Map<Long, Long> replacement = new HashMap<>();
        fullModule.put(moduleId, null);
        replacement.put(moduleId, themeId);

        if (themeId == null && (isModuleOrThemeFromModuleAlreadyReplaced(moduleId, replacedModuleIds)
                || !replacedModuleIds.add(replacement))) {
            throw new ValidationFailedException("apel.error.moduleReplacedMoreThanOnce");
        } else if (themeId != null
                && (replacedModuleIds.contains(fullModule) || !replacedModuleIds.add(replacement))) {
            throw new ValidationFailedException("apel.error.moduleReplacedMoreThanOnce");
        }
    }

    private static void addTransferedModule(ApelApplicationFormalSubjectOrModule transferedModule,
            Set<Long> transferedModuleIds) {
        if (Boolean.TRUE.equals(transferedModule.getTransfer())
                && transferedModule.getCurriculumVersionOmodule() != null
                && !transferedModuleIds.add(EntityUtil.getId(transferedModule.getCurriculumVersionOmodule()))) {
            throw new ValidationFailedException("apel.error.moduleTransferredMoreThanOnce");
        }
    }

    private static void addReplacedModuleAndThemeIdsMap(
            ApelApplicationFormalReplacedSubjectOrModule formalReplacedModule,
            Set<Map<Long, Long>> replacedModuleAndThemeIdsMaps) {
        Long moduleId = EntityUtil.getId(formalReplacedModule.getCurriculumVersionOmodule());
        Long themeId = EntityUtil.getNullableId(formalReplacedModule.getCurriculumVersionOmoduleTheme());
        addReplacedModuleMap(replacedModuleAndThemeIdsMaps, moduleId, themeId);
    }

    private static boolean isModuleOrThemeFromModuleAlreadyReplaced(Long moduleId,
            Set<Map<Long, Long>> replacedModuleAndThemeIdsMaps) {
        return replacedModuleAndThemeIdsMaps.stream().anyMatch(e -> e.containsKey(moduleId));
    }

    private static void addReplacedSubjectId(ApelApplicationInformalSubjectOrModule informalSubject,
            Set<Long> replacedSubjectIds) {
        if (Boolean.TRUE.equals(informalSubject.getTransfer()) && informalSubject.getSubject() != null
                && !replacedSubjectIds.add(EntityUtil.getId(informalSubject.getSubject()))) {
            throw new ValidationFailedException("apel.error.subjectReplacedMoreThanOnce");
        }
    }

    private static void addReplacedSubjectId(ApelApplicationFormalReplacedSubjectOrModule formalReplacedSubject,
            Set<Long> replacedSubjectIds) {
        if (!replacedSubjectIds.add(EntityUtil.getId(formalReplacedSubject.getSubject()))) {
            throw new ValidationFailedException("apel.error.subjectReplacedMoreThanOnce");
        }
    }
    
    private static void addTransferedSubject(ApelApplicationFormalSubjectOrModule formalSubject,
            Set<Long> transferedSubjectIds) {
        if (Boolean.TRUE.equals(formalSubject.getTransfer()) && formalSubject.getSubject() != null
                && !transferedSubjectIds.add(EntityUtil.getId(formalSubject.getSubject()))) {
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
    public ApelApplication removeConfirmation(HoisUserDetails user, ApelApplication application) {
        setApplicationStatus(application, ApelApplicationStatus.VOTA_STAATUS_Y);
        if (Boolean.FALSE.equals(application.getIsEhisSent())) {
            application.getStudent().setNominalStudyEnd(application.getOldNominalStudyEnd());
            application.setOldNominalStudyEnd(null);
        }
        removeStudentCurriculumOutcomeResults(user, application);
        application.setConfirmedBy(null);
        application.setConfirmed(null);
        return EntityUtil.save(application, em);
    }

    private void removeStudentCurriculumOutcomeResults(HoisUserDetails user, ApelApplication application) {
        EntityUtil.setUsername(user.getUsername(), em);
        Iterator<StudentCurriculumModuleOutcomesResult> resultsInterator = application.getOutcomeResults().iterator();
        while (resultsInterator.hasNext()) {
            StudentCurriculumModuleOutcomesResult result = resultsInterator.next();
            resultsInterator.remove();
            application.getOutcomeResults().remove(result);
            if (result.getHistory().size() > 0) {
                result.removeGrade();
            } else {
                EntityUtil.deleteEntity(result, em);
            }
        }
    }

    public CurriculumVersionHigherModuleDto subjectModule(Long curriculumVersionId, Long subjectId) {
        List<CurriculumVersionHigherModule> modules = em.createQuery(
                "select cvhs.module from CurriculumVersionHigherModuleSubject cvhs "
                        + "where cvhs.module.curriculumVersion.id = ?1 and cvhs.subject.id = ?2",
                CurriculumVersionHigherModule.class)
                .setParameter(1, curriculumVersionId)
                .setParameter(2, subjectId)
                .setMaxResults(1).getResultList();

        return !modules.isEmpty() ? CurriculumVersionHigherModuleDto.of(modules.get(0)) : null;
    }

    public List<CurriculumVersionHigherModuleResult> studentModules(Student student) {
        // final thesis and final exam modules are not allowed
        JpaNativeQueryBuilder qb = studentResultHigherService.studentModulesQueryBuilder(student);
        qb.requiredCriteria("cvhm.type_code not in (:typeCode)", "typeCode", HigherModuleType.FINAL_MODULES);

        List<?> data = qb.select("cvhm.id, cvhm.name_et, cvhm.name_en, cvhm.type_code", em).getResultList();
        return StreamUtil.toMappedList(r -> new CurriculumVersionHigherModuleResult(resultAsLong(r, 0),
                resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3)), data);
    }

    public AutocompleteResult studentFreeChoiceModule(Student student) {
        JpaNativeQueryBuilder qb = studentResultHigherService.studentModulesQueryBuilder(student);
        qb.requiredCriteria("cvhm.type_code = :typeCode", "typeCode", HigherModuleType.KORGMOODUL_V);

        List<?> data = qb.select("cvhm.id, cvhm.name_et, cvhm.name_en", em).getResultList();
        List<AutocompleteResult> freeChoiceModules = StreamUtil.toMappedList(
                r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), data);

        return !freeChoiceModules.isEmpty() ? freeChoiceModules.get(0) : null;
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

    private List<AbroadStudiesHolder> finishedAbroadStudies(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join application a on a.id = ds.application_id"
                + " left join application_planned_subject aps on aps.application_id = a.id"
                + " left join apel_application_record aar on aar.application_planned_subject_id = aps.id"
                + " left join study_period sp_start on sp_start.id = ds.study_period_start_id"
                + " left join study_period sp_end on sp_end.id = ds.study_period_end_id"
                + " left join (directive_student ds_katk join directive d_katk on d_katk.id = ds_katk.directive_id"
                    + " and d_katk.type_code = :katkDirectiveType and d_katk.status_code = :directiveStatus)"
                    + " on ds_katk.directive_student_id = ds.id and ds_katk.canceled = false");
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", student.getId());
        qb.requiredCriteria("coalesce(ds_katk.start_date, sp_end.end_date, ds.end_date) < :today",
                "today", LocalDate.now());
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS);
        qb.requiredCriteria("d.status_code = :directiveStatus and ds.canceled = false", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.filter("a.apel_school_id is not null");
        qb.parameter("katkDirectiveType", DirectiveType.KASKKIRI_VALISKATK.name());
        qb.parameter("apelStatus", ApelApplicationStatus.VOTA_STAATUS_L.name());

        List<?> data = qb.select("a.id a_id, aps.id aps_id, coalesce(sp_start.start_date, ds.start_date) start_date,"
                + " coalesce(ds_katk.start_date, sp_end.end_date, ds.end_date) end_date,"
                + " exists (select aar2.id from apel_application_record aar2"
                + " join apel_application aa2 on aa2.id = aar2.apel_application_id"
                + " where aar2.application_planned_subject_id = aps.id and aa2.status_code != :apelStatus) as transferred", em)
                .getResultList();
        List<AbroadStudiesHolder> plannedSubjects = StreamUtil.toMappedList(r -> new AbroadStudiesHolder(resultAsLong(r, 0),
                resultAsLong(r, 1), resultAsLocalDate(r, 2), resultAsLocalDate(r, 3), resultAsBoolean(r, 4)), data);
        return plannedSubjects;
    }

    private boolean canExtendNominalDuration(ApelApplication apelApplication) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_application aa"
                + " join apel_application_record aar on aar.apel_application_id = aa.id"
                + " join apel_application_formal_subject_or_module fsm on fsm.apel_application_record_id = aar.id"
                + " join apel_school a_s on a_s.id = fsm.apel_school_id");
        qb.requiredCriteria("aa.id = :applicationId", "applicationId", apelApplication.getId());
        qb.requiredCriteria("a_s.country_code != :countryCode", "countryCode", ClassifierUtil.COUNTRY_ESTONIA);
        qb.filter("exists (select 1 from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join application a on a.id = ds.application_id"
                + " left join study_period sp_start on sp_start.id = ds.study_period_start_id"
                + " left join study_period sp_end on sp_end.id = ds.study_period_end_id"
                + " left join (directive_student ds_katk join directive d_katk on d_katk.id = ds_katk.directive_id"
                    + " and d_katk.type_code = :katkDirectiveType and d_katk.status_code = :directiveStatus)"
                    + " on ds_katk.directive_student_id = ds.id and ds_katk.canceled = false"
                + " where ds.student_id = aa.student_id and d.type_code = :directiveType and d.status_code = :directiveStatus"
                + " and coalesce(ds_katk.start_date, sp_end.end_date, ds.end_date) < :today"
                + " and coalesce(ds_katk.start_date, sp_end.end_date, ds.end_date) >= fsm.grade_date"
                + " and coalesce(sp_start.start_date, ds.start_date) <= fsm.grade_date)");
        qb.parameter("directiveType", DirectiveType.KASKKIRI_VALIS.name());
        qb.parameter("katkDirectiveType", DirectiveType.KASKKIRI_VALISKATK.name());
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("today", LocalDate.now());

        return !qb.select("fsm.id", em).setMaxResults(1).getResultList().isEmpty();
    }

    private boolean hasMultipleNominalDurationExtensions(ApelApplication apelApplication) {
        List<?> data = em.createNativeQuery("select aa.id from apel_application aa"
                + " where aa.student_id = :studentId and aa.id != :applicationId and aa.nominal_type_code is not null"
                + " and aa.nominal_type_code != :nominalType and aa.status_code != :applicationStatus")
                .setParameter("studentId", EntityUtil.getId(apelApplication.getStudent()))
                .setParameter("applicationId", apelApplication.getId())
                .setParameter("nominalType", NominalDurationExtension.NOM_PIKEND_0.name())
                .setParameter("applicationStatus", ApelApplicationStatus.VOTA_STAATUS_L.name())
                .setMaxResults(1).getResultList();
        return !data.isEmpty();
    }

    public void addAbroadStudiesApplicationSubjects(ApelApplication apelApplication) {
        List<AbroadStudiesHolder> abroadStudies = finishedAbroadStudies(apelApplication.getStudent());
        Set<Long> plannedSubjectIds = notTransferredPlannedSubjectIds(abroadStudies);

        if (!plannedSubjectIds.isEmpty()) {
            List<ApplicationPlannedSubject> plannedSubjects = getApplicationPlannedSubjects(plannedSubjectIds);
            List<ApplicationPlannedSubjectEquivalent> equivalents = getApplicationPlannedSubjectEquivalents(plannedSubjectIds);
            Map<Long, List<ApplicationPlannedSubjectEquivalent>> equivalentsByPs = StreamUtil.nullSafeList(equivalents)
                    .stream().collect(Collectors.groupingBy(e -> EntityUtil.getId(e.getApplicationPlannedSubject()),
                            Collectors.mapping(e -> e, Collectors.toList())));

            for (ApplicationPlannedSubject plannedSubject : plannedSubjects) {
                List<ApplicationPlannedSubjectEquivalent> psEquivalents = equivalentsByPs.get(plannedSubject.getId());
                addAbroadStudiesRecord(apelApplication, plannedSubject, psEquivalents);
            }
            EntityUtil.save(apelApplication, em);
        }
    }

    private static Set<Long> notTransferredPlannedSubjectIds(List<AbroadStudiesHolder> abroadStudies) {
        return StreamUtil.nullSafeList(abroadStudies).stream()
                .filter(as -> as.getPlannedSubjectId() != null && Boolean.FALSE.equals(as.getAlreadyTransferred()))
                .map(as -> as.getPlannedSubjectId()).collect(Collectors.toSet());
    }

    private List<ApplicationPlannedSubject> getApplicationPlannedSubjects(Set<Long> plannedSubjectIds) {
        return em.createQuery("select aps from ApplicationPlannedSubject aps"
                + " where aps.id in (:plannedSubjectIds)", ApplicationPlannedSubject.class)
                    .setParameter("plannedSubjectIds", plannedSubjectIds)
                    .getResultList();
    }

    private List<ApplicationPlannedSubjectEquivalent> getApplicationPlannedSubjectEquivalents(Set<Long> plannedSubjectIds) {
        return em.createQuery("select apse from ApplicationPlannedSubjectEquivalent apse"
                + " where apse.applicationPlannedSubject.id in (:plannedSubjectIds)", ApplicationPlannedSubjectEquivalent.class)
                    .setParameter("plannedSubjectIds", plannedSubjectIds)
                    .getResultList();
    }

    private void addAbroadStudiesRecord(ApelApplication apelApplication, ApplicationPlannedSubject plannedSubject,
            List<ApplicationPlannedSubjectEquivalent> plannedSubjectEquivalents) {
        ApelApplicationRecord record = new ApelApplicationRecord();
        record.setApelApplication(apelApplication);
        record.setIsFormalLearning(Boolean.TRUE);
        record.setApplicationPlannedSubject(plannedSubject);
        apelApplication.getRecords().add(record);

        addPlannedSubjectToRecord(apelApplication, record, plannedSubject);
        if (plannedSubjectEquivalents != null) {
            addPlannedSubjectEquivalents(plannedSubjectEquivalents, record);
        }
    }

    private void addPlannedSubjectToRecord(ApelApplication apelApplication, ApelApplicationRecord record,
            ApplicationPlannedSubject plannedSubject) {
        ApelApplicationFormalSubjectOrModule subjectOrModule = new ApelApplicationFormalSubjectOrModule();
        subjectOrModule.setApelApplicationRecord(record);
        subjectOrModule.setIsMySchool(Boolean.FALSE);
        subjectOrModule.setType(em.getReference(Classifier.class, ApelSubjectType.VOTA_AINE_LIIK_M.name()));
        subjectOrModule.setApelSchool(plannedSubject.getApplication().getApelSchool());
        subjectOrModule.setIsOptional(Boolean.FALSE);
        subjectOrModule.setNameEt(plannedSubject.getName());
        subjectOrModule.setAssessment(em.getReference(Classifier.class,
                Boolean.TRUE.equals(apelApplication.getIsVocational()) ? VocationalGradeType.KUTSEHINDAMISVIIS_M.name()
                        : SubjectAssessment.HINDAMISVIIS_A.name()));
        record.getFormalSubjectsOrModules().add(subjectOrModule);
    }

    private static void addPlannedSubjectEquivalents(List<ApplicationPlannedSubjectEquivalent> plannedSubjectEquivalents,
            ApelApplicationRecord record) {
        for (ApplicationPlannedSubjectEquivalent equivalent : plannedSubjectEquivalents) {
            ApelApplicationFormalReplacedSubjectOrModule replacedSubjectOrModule = new ApelApplicationFormalReplacedSubjectOrModule();
            replacedSubjectOrModule.setApelApplicationRecord(record);
            replacedSubjectOrModule.setSubject(equivalent.getSubject());
            replacedSubjectOrModule.setCurriculumVersionOmodule(equivalent.getCurriculumVersionOmodule());
            replacedSubjectOrModule.setCurriculumVersionOmoduleTheme(equivalent.getCurriculumVersionOmoduleTheme());
            record.getFormalReplacedSubjectsOrModules().add(replacedSubjectOrModule);
        }
    }

    public static class AbroadStudiesHolder {
        private final Long applicationId;
        private final Long plannedSubjectId;
        private final DateRangeDto period;
        private final Boolean alreadyTransferred;

        public AbroadStudiesHolder(Long applicationId, Long plannedSubjectId, LocalDate start, LocalDate end,
                Boolean alreadyTransferred) {
            this.applicationId = applicationId;
            this.plannedSubjectId = plannedSubjectId;
            this.period = new DateRangeDto(start, end);
            this.alreadyTransferred = alreadyTransferred;
        }

        public Long getApplicationId() {
            return applicationId;
        }

        public Long getPlannedSubjectId() {
            return plannedSubjectId;
        }

        public DateRangeDto getPeriod() {
            return period;
        }

        public Boolean getAlreadyTransferred() {
            return alreadyTransferred;
        }
    }
}
