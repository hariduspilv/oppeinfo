package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.web.dto.GradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.domain.student.StudentLanguages;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.domain.student.StudentSpecialNeed;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApelApplicationUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.StudentCommand;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentAddInfoForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentLanguageCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSpecialitySearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;
import ee.hitsa.ois.web.dto.SpecialityAutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hitsa.ois.web.dto.StudentSupportServiceDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceSearchDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentDormitoryHistoryDto;
import ee.hitsa.ois.web.dto.student.StudentForeignstudyDto;
import ee.hitsa.ois.web.dto.student.StudentPracticeContractDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSpecialitySearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalConnectedEntity;
import ee.hitsa.ois.web.dto.student.StudentVocationalModuleDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultByTimeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalStudyProgrammeDto;

@Transactional
@Service
public class StudentService {

    private static final String STUDENT_LIST_SELECT = "s.id, person.firstname, person.lastname, person.idcode, "+
            "curriculum_version.id curriculum_version_id, curriculum_version.code curriculum_version_code, curriculum.id curriculum_id, curriculum.name_et, curriculum.name_en, " +
            "student_group.id student_group_id, student_group.code student_group_code, s.study_form_code, " +
            "s.status_code, s.person_id, s.type_code, s.reg_nr";
    private static final String STUDENT_LIST_FROM = "from student s inner join person person on s.person_id=person.id "+
            "left join curriculum_version curriculum_version on s.curriculum_version_id=curriculum_version.id "+
            "left join curriculum curriculum on curriculum_version.curriculum_id=curriculum.id "+
            "inner join classifier status on s.status_code=status.code "+
            "left outer join student_group student_group on s.student_group_id=student_group.id "+
            "left outer join classifier study_form on s.study_form_code=study_form.code ";
    
    private static final List<String> JOURNAL_RESULT_ENTRY_TYPES = EnumUtil.toNameList(JournalEntryType.SISSEKANNE_L,
            JournalEntryType.SISSEKANNE_R, JournalEntryType.SISSEKANNE_H);

    private static final BigDecimal DISTINCTIVE_GRADES_CRITERIA = BigDecimal.valueOf(0.5);

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;
    @Autowired
    private StudentRemarkService studentRemarkService;
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private JobService jobService;
    @Autowired
    private StudyYearService studyYearService;

    /**
     * Search students
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSearchDto> search(HoisUserDetails user, StudentSearchCommand criteria, Pageable pageable) {
        StringBuilder select = new StringBuilder(STUDENT_LIST_SELECT);
        StringBuilder from = new StringBuilder(STUDENT_LIST_FROM);
        final boolean isJournalUsed = !CollectionUtils.isEmpty(criteria.getJournalId());
        final boolean isSubjectUsed = !CollectionUtils.isEmpty(criteria.getSubjectId());
        if (isJournalUsed) {
            select.append(", j.id as journal_id, j.name_et as journal_name");
            from.append("left join journal_student js on js.student_id = s.id ");
            from.append("left join journal j on j.id = js.journal_id ");
        }
        if (isSubjectUsed) {
            select.append(", sj.id as subject_id, sj.name_et as subject_name_et, sj.name_en as subject_name_en");
            Long studyPeriodId = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
            from.append("left join declaration d on d.student_id = s.id " + (studyPeriodId != null ? "and d.study_period_id = " + studyPeriodId.longValue() : ""));
            from.append("left join declaration_subject ds on ds.declaration_id = d.id ");
            from.append("left join subject_study_period ssp on ssp.id = ds.subject_study_period_id ");
            from.append("left join subject sj on sj.id = ssp.subject_id ");
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);

        if (user.isLeadingTeacher()) {
            qb.optionalCriteria("curriculum.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        // student cannot search by idcode
        if(!user.isStudent()) {
            qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        }
        qb.optionalContains(Arrays.asList("person.firstname", "person.lastname", "person.firstname || ' ' || person.lastname"), "name", criteria.getName());

        qb.optionalCriteria("curriculum.id in (:curriculum)", "curriculum", criteria.getCurriculum());
        qb.optionalCriteria("s.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalContains("student_group.code", "code", criteria.getStudentGroup());
        qb.optionalCriteria("s.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroupId());
        qb.optionalCriteria("s.study_form_code in (:studyForm)", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("j.id in (:journalIds)", "journalIds", criteria.getJournalId());
        qb.optionalCriteria("sj.id in (:subjectIds)", "subjectIds", criteria.getSubjectId());

        if (Boolean.TRUE.equals(criteria.getHigher())) {
            qb.filter("curriculum.is_higher = true");
        }
        if (Boolean.FALSE.equals(criteria.getHigher())) {
            qb.filter("curriculum.is_higher = false");
        }
        if (Long.valueOf(1).equals(criteria.getStudentType())) {
            qb.requiredCriteria("s.type_code = :typeCode", "typeCode", StudentType.OPPUR_K.name());
        } else if (Long.valueOf(2).equals(criteria.getStudentType())) {
            qb.requiredCriteria("s.type_code = :typeCode", "typeCode", StudentType.OPPUR_E.name());
        }
        if (user.isTeacher() && Boolean.TRUE.equals(criteria.getShowMyStudentGroups())) {
            qb.requiredCriteria("student_group.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }

        return JpaQueryUtil.pagingResult(qb, select.toString(), em, pageable).map(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 14)));
            dto.setIdcode(user.isStudent() ? null : resultAsString(r, 3));
            String curriculumVersionCode = resultAsString(r, 5);
            dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 7)),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 8))));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 9), resultAsString(r, 10), resultAsString(r, 10)));
            dto.setStudyForm(resultAsString(r, 11));
            dto.setStatus(resultAsString(r, 12));
            dto.setPersonId(user.isStudent() ? null : resultAsLong(r, 13));
            dto.setRegNr(resultAsLong(r, 15));
            if (isJournalUsed) {
                dto.setJournal(new AutocompleteResult(resultAsLong(r, 16), resultAsString(r, 17), resultAsString(r, 17)));
                if (isSubjectUsed) {
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 18), resultAsString(r, 19), resultAsString(r, 20)));
                }
            } else if (isSubjectUsed) {
                dto.setSubject(new AutocompleteResult(resultAsLong(r, 16), resultAsString(r, 17), resultAsString(r, 18)));
            }
            return dto;
        });
    }
    

    /**
     * 
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSpecialitySearchDto> search(HoisUserDetails user, StudentSpecialitySearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " + 
                "join person p on p.id = s.person_id " + 
                "join curriculum_version cv on cv.id = s.curriculum_version_id " + 
                "left join student_group sg on sg.id = s.student_group_id " + 
                "left join curriculum_speciality cs on cs.id = s.curriculum_speciality_id ").sort("fullname");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalCriteria("cv.id = :cvId", "cvId", criteria.getCurriculumVersion());

        if (user.isLeadingTeacher()) {
            qb.optionalCriteria("cv.curriculum_id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }
        if (Boolean.TRUE.equals(criteria.getWoSpeciality())) {
            qb.filter("cs.id is null");
        }

        return JpaQueryUtil.pagingResult(qb, "s.id as student, p.firstname || ' ' || p.lastname as fullname, p.idcode,"
                + " cv.id as cv, cs.id as spec, cs.name_et, cs.name_en,"
                + " sg.id as sg, sg.code",
                em, pageable).map(r -> {
            StudentSpecialitySearchDto dto = new StudentSpecialitySearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setIdcode(resultAsString(r, 2));
            dto.setGroup(new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 8)));
            dto.setSpeciality(new SpecialityAutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 6)));
            return dto;
        });
    }

    public Student save(HoisUserDetails user, Student student, StudentForm form) {
        Person p = EntityUtil.bindToEntity(form.getPerson(), student.getPerson(), classifierRepository);
        PersonUtil.conditionalClean(p);
        EntityUtil.save(p, em);
        
        if (!user.isStudent() || StudentUtil.canStudentEditPhoto(student)) {
            OisFile photo = student.getPhoto();
            if (Boolean.TRUE.equals(form.getDeleteCurrentPhoto())) {
                if (photo != null) {
                    removePhotoFromStudentHistory(student, photo);
                    student.setPhoto(null);
                    em.remove(photo);
                }
            } else if (form.getPhoto() != null) {
                if (photo == null) {
                    photo = new OisFile();
                }
                EntityUtil.bindToEntity(form.getPhoto(), photo);
                photo = EntityUtil.save(photo, em);
                student.setPhoto(photo);
            }
        }

        if (!(UserUtil.isSchoolAdmin(user, student.getSchool()) || UserUtil.isLeadingTeacher(user, student)
                || UserUtil.isStudentGroupTeacher(user, student))) {
            // allow student to change other contact field
            student.setOtherContact(form.getOtherContact());
            return saveWithHistory(student);
        }

        EntityUtil.bindToEntity(form, student, classifierRepository, "person", "curriculumSpeciality", "specialNeeds", "specialNeed", "studentLanguages");
        student.setCurriculumSpeciality(form.getCurriculumSpeciality() != null
                ? em.getReference(CurriculumSpeciality.class, form.getCurriculumSpeciality().getId()) : null);
        student.setEmail(form.getSchoolEmail());
        student.setLanguage(form.getStudyLanguage() != null ? 
                classifierRepository.getOne(form.getStudyLanguage())
                : null);
        EntityUtil.bindEntityCollection(student.getSpecialNeeds(), specialNeed -> specialNeed.getSpecialNeed().getCode(),
            form.getSpecialNeeds(), specialNeed -> specialNeed, formSpecialNeed -> {
                StudentSpecialNeed specialNeed = new StudentSpecialNeed();
                specialNeed.setStudent(student);
                specialNeed.setSpecialNeed(em.getReference(Classifier.class, formSpecialNeed));
                return specialNeed;
            });
        AtomicBoolean updateRequired = new AtomicBoolean(false);
        EntityUtil.bindEntityCollection(student.getStudentLanguages(), language -> EntityUtil.getId(language),
                form.getStudentLanguages(), lang -> lang.getId()
                , studentLanguage -> {
                    StudentLanguages studentLanguages = new StudentLanguages();
                    studentLanguages.setStudent(student);
                    studentLanguages.setForeignLangType(em.getReference(Classifier.class, studentLanguage.getForeignLangTypeCode()));
                    studentLanguages.setForeignLang(em.getReference(Classifier.class, studentLanguage.getForeignLangCode()));
                    updateRequired.set(true);
                    return studentLanguages;
                },(langDto, lang) -> {
                    if (langDto.getForeignLangCode() != null && !langDto.getForeignLangCode().equals(EntityUtil.getCode(lang.getForeignLang()))) {
                        updateRequired.set(true);
                    }
                    lang.setForeignLang(em.getReference(Classifier.class, langDto.getForeignLangCode()));
                },lang -> {
                    EntityUtil.deleteEntity(lang, em);
                    updateRequired.set(true);
                });
        if (updateRequired.get()) jobService.studentLanguageUpdated(student);
        LocalDate studyStart = student.getStudyStart();
        LocalDate nominalStudyEnd = student.getNominalStudyEnd();
        if (studyStart != null && nominalStudyEnd != null && nominalStudyEnd.isBefore(studyStart)) {
            throw new ValidationFailedException("student.error.nominalStudyEndIsBeforeStudyStart");
        }
        
        return saveWithHistory(student);
    }
    
    public void removePhotoFromStudentHistory(Student student, OisFile photo) {
        List<?> data = em
                .createNativeQuery("select sh.student_id from student_history sh where sh.student_id = ?1 and sh.ois_file_id = ?2")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, EntityUtil.getId(photo))
                .getResultList();

        if(!data.isEmpty()) {
            List<Long> studentHistoryIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
            em.createNativeQuery("update student_history set ois_file_id = null where student_id in (?1)")
                .setParameter(1, studentHistoryIds)
                .executeUpdate();
        }
    }

    public Student saveWithHistory(Student student) {
        // student version handling: update current version validity end
        StudentHistory old = student.getStudentHistory();
        LocalDateTime now = LocalDateTime.now();
        if(old != null) {
            old.setValidThru(now);
        }
        // and create new version
        StudentHistory current = EntityUtil.bindToEntity(student, new StudentHistory());
        current.setStudent(student);
        current.setValidFrom(now);
        current.setPrevStudentHistory(old);
        student.setStudentHistory(current);
        return EntityUtil.save(student, em);
    }

    /**
     * Absences of student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public StudentAbsenceSearchDto absences(HoisUserDetails user, Student student, Pageable pageable) {
        Long studentId = EntityUtil.getId(student);
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_absence sa").sort(pageable);
        qb.requiredCriteria("sa.student_id = :studentId", "studentId", studentId);

        // absence object for checking rights. So far only student is used
        StudentAbsence absence = new StudentAbsence();
        absence.setStudent(student);

        Page<StudentAbsenceDto> data = JpaQueryUtil.pagingResult(qb, "sa.id, sa.is_accepted, sa.is_rejected, sa.valid_from, sa.valid_thru, sa.cause, sa.version, sa.reject_reason", em, pageable).map(r -> {
            StudentAbsenceDto dto = new StudentAbsenceDto();
            dto.setId(resultAsLong(r, 0));
            dto.setIsAccepted(resultAsBoolean(r, 1));
            dto.setIsRejected(resultAsBoolean(r, 2));
            dto.setValidFrom(resultAsLocalDate(r, 3));
            dto.setValidThru(resultAsLocalDate(r, 4));
            dto.setCause(resultAsString(r, 5));
            dto.setVersion(resultAsLong(r, 6));
            dto.setRejectReason(resultAsString(r, 7));
            absence.setIsAccepted(dto.getIsAccepted());
            absence.setIsRejected(dto.getIsRejected() != null ? dto.getIsRejected() : Boolean.FALSE);
            dto.setUserCanEdit(Boolean.valueOf(StudentAbsenceUtil.canEdit(user, absence)));
            return dto;
        });

        // fetch student-related data
        qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id left outer join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        Object studentData = qb.select("p.firstname, p.lastname, sg.code", em).getSingleResult();
        String studentName = PersonUtil.fullname(resultAsString(studentData, 0), resultAsString(studentData, 1));
        String studentGroup = resultAsString(studentData, 2);
        boolean canAddAbsence = StudentAbsenceUtil.canCreate(user, student);

        return new StudentAbsenceSearchDto(data.getContent(), pageable, data.getTotalElements(), studentName, studentGroup, canAddAbsence);
    }

    /**
     * Create new absence of student.
     * If absence is created by representative, send message about it to school admins.
     *
     * @param user
     * @param student
     * @param form
     * @return
     */
    public StudentAbsence create(HoisUserDetails user, Student student, StudentAbsenceForm form) {
        StudentAbsence absence = new StudentAbsence();
        absence.setStudent(student);
        absence.setIsAccepted(Boolean.FALSE);
        absence.setIsRejected(Boolean.FALSE);
        absence = save(absence, form);
        if(user.isRepresentative()) {
            // send message to school admins, if absence is created by parent/representative
            automaticMessageService.sendMessageToSchoolAdmins(MessageType.TEATE_LIIK_OP_PT, student.getSchool(), new StudentAbsenceCreated(absence));
        }
        return absence;
    }

    /**
     * Update absence of student
     *
     * @param absence
     * @param form
     * @return
     */
    public StudentAbsence save(StudentAbsence absence, StudentAbsenceForm form) {
        EntityUtil.bindToEntity(form, absence);
        return EntityUtil.save(absence, em);
    }

    /**
     * Delete absence of student
     *
     * @param user
     * @param absence
     */
    public void delete(HoisUserDetails user, StudentAbsence absence) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(absence, em);
    }

    /**
     * Applications of student
     *
     * @param studentId
     * @param pageable
     * @return
     */
    public Page<StudentApplicationDto> applications(Long studentId, Pageable pageable, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from application a join classifier type on type.code = a.type_code "+
                "join classifier status on status.code = a.status_code").sort(pageable);
        qb.filter(":personId = :personId");
        qb.parameter("personId", user.getPersonId());
        qb.requiredCriteria("a.student_id = :studentId", "studentId", studentId);
        return JpaQueryUtil.pagingResult(qb, "a.id, a.type_code, a.inserted, a.status_code, case when a.status_code = 'AVALDUS_STAATUS_KINNITATUD' then a.changed else null end, "
                + "a.submitted, a.reject_reason, exists(select 1 from committee_member cm where cm.committee_id = a.committee_id and cm.person_id = :personId)", em, pageable).map(r -> {
            StudentApplicationDto dto = new StudentApplicationDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setInserted(resultAsLocalDateTime(r, 2));
            dto.setStatus(resultAsString(r, 3));
            dto.setConfirmDate(resultAsLocalDateTime(r, 4));
            dto.setSubmitted(resultAsLocalDateTime(r, 5));
            dto.setRejectReason(resultAsString(r, 6));
            dto.setIsConnectedByCommittee(resultAsBoolean(r, 7));
            return dto;
        });
    }

    /**
     * Directives related to student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public Page<StudentDirectiveDto> directives(HoisUserDetails user, Student student, Pageable pageable, DirectiveType type) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d join classifier type on type.code = d.type_code "
                + "left join directive_student ds on ds.directive_id = d.id "
                + "left join study_period sp_start on sp_start.id = ds.study_period_start_id "
                + "left join study_period sp_end on sp_end.id = ds.study_period_end_id "
                + "left join classifier reason on reason.code = ds.reason_code "
                 + (DirectiveType.KASKKIRI_AKAD.equals(type) ? 
                         "left join application a on a.directive_id = d.id and a.student_id = ds.student_id and a.type_code = '" + ApplicationType.AVALDUS_LIIK_AKADK.name()
                         + "' left join (select ds2.application_id, ds2.student_id, ds2.start_date "
                         + "from directive_student ds2 "
                         + "join directive d2 on d2.id = ds2.directive_id and d2.type_code = '" + DirectiveType.KASKKIRI_AKADK.name() + "' "
                         + "where ds2.canceled != true and d2.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "') "
                         + "akadK on akadK.student_id = ds.student_id and akadK.application_id = a.id"
                         : "")).sort(pageable);

        String showCanceled = "";
        if(!UserUtil.isSchoolAdmin(user, student.getSchool())) {
            // don't show these directives which are cancelled for given student
            showCanceled = " and ds.canceled = false";
        }
        qb.requiredCriteria(String.format("ds.student_id = :studentId%s", showCanceled), "studentId", EntityUtil.getId(student));

        qb.requiredCriteria("d.type_code <> :cancelType", "cancelType", DirectiveType.KASKKIRI_TYHIST);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        if (type != null) qb.requiredCriteria("d.type_code = :directiveType", "directiveType", type);

        return JpaQueryUtil.pagingResult(qb, "d.id, d.headline, d.type_code, d.directive_nr, d.confirm_date, d.inserted_by, ds.reason_code, coalesce(sp_start.start_date, ds.start_date) as startDate, "
        + (DirectiveType.KASKKIRI_AKAD.equals(type) ? "coalesce(akadK.start_date, sp_end.end_date, ds.end_date) as endDate" : "ds.end_date"), em, pageable).map(r -> {
            StudentDirectiveDto dto = new StudentDirectiveDto();
            dto.setId(resultAsLong(r, 0));
            dto.setHeadline(resultAsString(r, 1));
            dto.setType(resultAsString(r, 2));
            dto.setDirectiveNr(resultAsString(r, 3));
            dto.setConfirmDate(resultAsLocalDate(r, 4));
            dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 5)));
            dto.setReason(resultAsString(r, 6));
            dto.setStartDate(resultAsLocalDate(r, 7));
            dto.setEndDate(resultAsLocalDate(r, 8));
            return dto;
        });
    }
    
    /**
     * Practice contracts related to student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public Page<StudentPracticeContractDto> practiceContracts(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from contract c"
                + " join enterprise e on c.enterprise_id = e.id"
                + " join teacher t on c.teacher_id = t.id"
                + " join person p on t.person_id = p.id"
                + " join classifier status on c.status_code = status.code").sort(pageable);
        qb.requiredCriteria("c.student_id = :studentId", "studentId", EntityUtil.getId(student));

        return JpaQueryUtil.pagingResult(qb,
                "c.id, c.contract_nr, c.start_date, c.end_date, e.name, e.contact_person_name, p.firstname, p.lastname, c.confirm_date, c.status_code",
                em, pageable).map(r -> {
            StudentPracticeContractDto dto = new StudentPracticeContractDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStartDate(resultAsLocalDate(r, 2));
            dto.setEndDate(resultAsLocalDate(r, 3));
            dto.setEnterprise(resultAsString(r, 4));
            dto.setSupervisor(resultAsString(r, 5));
            dto.setSchoolSupervisor(PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7)));
            dto.setContractDate(resultAsLocalDate(r, 8));
            dto.setStatus(resultAsString(r, 9));
            return dto;
        });
    }

    /**
     * Foreign studies related to student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public Page<StudentForeignstudyDto> foreignstudies(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds join directive d on ds.directive_id = d.id " +
                "join classifier country on ds.country_code = country.code " +
                "join classifier purpose on ds.abroad_purpose_code = purpose.code " +
                "left join classifier programme on ds.abroad_programme_code = programme.code " +
                "left join (select ds1.start_date, ds1.directive_student_id, ds1.student_id from directive_student ds1 " +
                    "join directive d1 on ds1.directive_id = d1.id " +
                    "where d1.type_code = '" + DirectiveType.KASKKIRI_VALISKATK.name() + "'" + 
                    "and d1.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "') KATK " +
                    "on KATK.directive_student_id = ds.id " +
                "left join classifier ehis_school on ds.ehis_school_code = ehis_school.code " +
                "left join study_period sp on ds.study_period_start_id = sp.id " +
                "left join study_period ep on ds.study_period_end_id = ep.id " +
                "left join apel_school aps on aps.id = ds.apel_school_id").sort(pageable);
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.filter("ds.canceled = false");

        return JpaQueryUtil.pagingResult(qb, "coalesce(aps.name_et, ehis_school.name_et, ds.abroad_school) as schoolEt, " +
                "coalesce(aps.name_en, ehis_school.name_en, ds.abroad_school) as schoolEn, ds.country_code, " +
                "coalesce(sp.start_date, ds.start_date) as foreignStart, coalesce(case when KATK.start_date is not null then KATK.start_date - interval '1 day' else null end, ep.end_date, ds.end_date) as foreignEnd, " +
                "ds.abroad_purpose_code, ds.abroad_programme_code", em, pageable).map(r -> {
            StudentForeignstudyDto dto = new StudentForeignstudyDto();
            dto.setSchool(new AutocompleteResult(null, resultAsString(r, 0), resultAsString(r, 1)));
            dto.setCountry(resultAsString(r, 2));
            dto.setStartDate(resultAsLocalDate(r, 3));
            dto.setEndDate(resultAsLocalDate(r, 4));
            dto.setAbroadPurpose(resultAsString(r, 5));
            dto.setAbroadProgramme(resultAsString(r, 6));
            return dto;
        });
    }

    /**
     * Subjects related to student
     *
     * @param student
     * @return
     */
    public List<AutocompleteResult> subjects(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s "
                + "inner join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = s.id "
                + "inner join curriculum_version_hmodule cvh on cvh.id = cvhms.curriculum_version_hmodule_id").groupBy("s.id");
        qb.requiredCriteria("cvh.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em).getResultList();
        return StreamUtil.toMappedList(r ->
            new AutocompleteResult(resultAsLong(r, 0),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 1), resultAsDecimal(r, 4)),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 2), resultAsDecimal(r, 4))), data);
    }
    
    /**
     * Modules and themes related to student
     *
     * @param student
     * @return
     */
    public List<AutocompleteResult> modulesAndThemes(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_omodule cvo "
                + "join curriculum_version cv on cvo.curriculum_version_id = cv.id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier c on c.code = cm.module_code "
                + "left join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id");
        qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        List<?> data = qb.select("cvo.id, cv.code, cm.name_et as moduleEt, c.name_et as codeEt, cm.name_en as moduleEn, c.name_en as codeEn, cvot.name_et as themeEt", em).getResultList();
        return StreamUtil.toMappedList(r ->
            new AutocompleteResult(resultAsLong(r, 0),
                CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))), data);
    }

    /**
     * Student data for student view form, main data tab
     *
     * @param user
     * @param student
     * @return
     */
    public StudentViewDto getStudentView(HoisUserDetails user, Student student, StudentCommand criteria) {
        StudentViewDto dto = StudentViewDto.of(student);
        if (dto.getIsVocational() == null) {
            boolean higher = isHigher(student);
            if (higher) {
                dto.setIsVocational(Boolean.FALSE);
            } else {
                dto.setIsVocational(Boolean.TRUE);
            }
        }
        // rights for editing student data, adding representative and displaying sensitive fields
        dto.setUserCanEditStudent(Boolean.valueOf(UserUtil.canEditStudent(user, student)));
        dto.setUserCanAddRepresentative(Boolean.valueOf(UserUtil.canAddStudentRepresentative(user, student)));
        dto.setUserIsSchoolAdmin(Boolean.valueOf(UserUtil.isSchoolAdmin(user, student.getSchool())));
        dto.setUserIsStudentGroupTeacher(Boolean.valueOf(UserUtil.isStudentGroupTeacher(user, student)));
        dto.setUserIsLeadingTeacher(Boolean.valueOf(UserUtil.isLeadingTeacher(user, student)));
        dto.setUserCanViewStudentSpecificData(Boolean.valueOf(UserUtil.canViewStudentSpecificData(user, student)));
        dto.setUserCanUpdateRR(Boolean.valueOf(UserUtil.canUpdateStudentRR(user, student)));
        dto.setUserCanRequestPhotoBoxPhoto(Boolean.valueOf(UserUtil.canRequestStudentFotoBoxPhoto(user, student)));
        dto.setUserCanViewStudentSupportServices(Boolean.valueOf(UserUtil.canViewStudentSupportServices(user, student)));
        dto.setUserCanViewStudentAddInfo(Boolean.valueOf(UserUtil.canViewStudentAddInfo(user, student)));
        dto.setUserCanViewPrivateStudentSupportServices(Boolean.valueOf(UserUtil.canViewPrivateStudentSupportServices(user, student)));
        dto.setStudentCanAddPhoto(Boolean.valueOf(StudentUtil.canStudentEditPhoto(student)));
        if(!(Boolean.TRUE.equals(dto.getUserIsSchoolAdmin()) || UserUtil.isStudent(user, student) || UserUtil.isStudentRepresentative(user, student))) {
            dto.setSpecialNeed(null);
            dto.setIsRepresentativeMandatory(null);
        }
        StudentCurriculumCompletion completion = null;
        if (Boolean.TRUE.equals(dto.getIsVocational())) {
            completion = getStudentCurriculumCompletion(student);
            if (completion != null && student.getCurriculumVersion() != null) {
                dto.setCredits(completion.getCredits());
                dto.setKkh(completion.getAverageMark());
                dto.setIsCurriculumFulfilled(completion.isCurriculumFulfilled());
            } else {
                dto.setCredits(BigDecimal.ZERO);
                dto.setKkh(BigDecimal.ZERO);
                dto.setIsCurriculumFulfilled(Boolean.FALSE);
            }
            dto.setHasRemarksPastSevenDays(studentRemarkService.studentHasRemarksPastSevenDays(student));
            if (student.getStudentGroup() != null && student.getStudentGroup().getSpeciality() != null) {
                dto.setSpeciality(student.getStudentGroup().getSpeciality().getCode());   
            }
        } else {
            dto.setStudyCompany(null);
            if (student.getCurriculumSpeciality() != null) {
                dto.setCurriculumIncludesSpecialities(Boolean.TRUE);
                dto.setCurriculumSpeciality(AutocompleteResult.of(student.getCurriculumSpeciality()));
            } else {
                dto.setCurriculumIncludesSpecialities(Boolean.valueOf(getCurriculumVersionIncludesSpecialities(student)));
            }
        }
        dto.setOccupationCertificates(occupationCertificates(student));
        dto.setDormitoryHistory(dormitoryHistory(student));
        dto.setStudentLanguages(studentLanguages(student));

        OisFile logo = student.getPhoto();
        if (logo != null) {
            dto.setPhoto(EntityUtil.bindToDto(logo, new OisFileCommand()));
        }
        setStudentIndividualCurriculum(dto);
        setStudentBoardingSchool(dto);
        BigDecimal curriculumCredits = BigDecimal.ZERO;
        if (criteria != null) {
            if (Boolean.TRUE.equals(criteria.getWithHTM())) {
                CurriculumVersion version = student.getCurriculumVersion();
                if (version != null) {
                    Curriculum curriculum = version.getCurriculum();
                    //HTM code
                    String merCode = curriculum.getMerCode();
                    String versionCode = version.getCode();
                    String curriculumNameEt = (merCode != null ? merCode + " " : "") + curriculum.getNameEt();
                    String curriculumNameEn = (merCode != null ? merCode + " " : "") + curriculum.getNameEn();
                    dto.setCurriculumVersion(new AutocompleteResult(EntityUtil.getId(version), versionCode, versionCode));
                    dto.setCurriculumObject(new AutocompleteResult(EntityUtil.getId(curriculum), curriculumNameEt, curriculumNameEn));
                    dto.setStudyLevel(EntityUtil.getNullableCode(curriculum.getOrigStudyLevel()));
                    curriculumCredits = curriculum.getCredits();
                }
            }
        }
        if (completion != null) {
            dto.setFulfillmentPercentage(StudentResultHigherService.getCurriculumFulfillmentPercentage(curriculumCredits,
                    curriculumCredits.add(completion.getStudyBacklog())));
        }
        dto.setApelApplicationCredits(getStudentApelCredits(student));
        return dto;
    }
    
    private List<StudentLanguageCommand> studentLanguages(Student student) {
        List<?> data = em.createNativeQuery("select sl.foreign_lang_code, sl.foreign_lang_type_code, sl.id as language_id from student s "
                + "join student_languages sl on sl.student_id = s.id "
                + "where s.id = ?1 "
                + "order by sl.foreign_lang_type_code")
                .setParameter(1, EntityUtil.getId(student))
                .getResultList();

        List<StudentLanguageCommand> studentLanguages = new ArrayList<>();
        for (Object row : data) {
            StudentLanguageCommand dto = new StudentLanguageCommand();
            dto.setForeignLangCode(resultAsString(row, 0));
            dto.setForeignLangTypeCode(resultAsString(row, 1));
            dto.setId(resultAsLong(row, 2));
            studentLanguages.add(dto);
        }
        return studentLanguages;
    }


    public BigDecimal getStudentApelCredits(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_application_formal_subject_or_module aafsm " +
                "join apel_application_record aar1 on aafsm.apel_application_record_id = aar1.id " +
                "join apel_application aa1 on aar1.apel_application_id = aa1.id");
        qb.requiredCriteria("aa1.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("aa1.status_code = :statusCode", "statusCode", ApelApplicationStatus.VOTA_STAATUS_C);
        qb.requiredCriteria("aafsm.transfer = :transferStatus", "transferStatus", Boolean.TRUE);

        String formalCredits = qb.querySql("aa1.id, aafsm.id aafsm_id, aafsm.credits", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from apel_application_informal_subject_or_module aaism " +
                "join apel_application_record aar2 on aaism.apel_application_record_id = aar2.id " +
                "join apel_application aa2 on aar2.apel_application_id = aa2.id " +
                "left join subject s on s.id = aaism.subject_id " +
                "left join curriculum_version_omodule cvo on aaism.curriculum_version_omodule_id = cvo.id " +
                "left join curriculum_module cm on cvo.curriculum_module_id = cm.id " +
                "left join curriculum_version_omodule_theme cvot on aaism.curriculum_version_omodule_theme_id = cvot.id");
        qb.requiredCriteria("aa2.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("aa2.status_code = :statusCode", "statusCode", ApelApplicationStatus.VOTA_STAATUS_C);
        qb.requiredCriteria("aaism.transfer = :transferStatus", "transferStatus", Boolean.TRUE);

        String informalCredits = qb.querySql("aa2.id, aaism.id aaism_id, coalesce(s.credits, cvot.credits, cm.credits) credits", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + formalCredits + " union all " + informalCredits + ") apel_credits");
        List<?> data = qb.select("coalesce(sum(credits), 0)", em, parameters).getResultList();
        return !data.isEmpty() ? resultAsDecimal(data.get(0), 0) : BigDecimal.ZERO;
    }

    public StudentCurriculumCompletion getStudentCurriculumCompletion(Student student) {
        List<StudentCurriculumCompletion> result = em.createQuery("select scc"
                + " from StudentCurriculumCompletion scc where scc.student.id = ?1",
                StudentCurriculumCompletion.class)
                .setParameter(1, student.getId())
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    private boolean getCurriculumVersionIncludesSpecialities(Student student) {
        List<?> data = em.createNativeQuery("select 1 from curriculum_version_speciality cvs"
                + " join curriculum_version cv on cv.id = cvs.curriculum_version_id"
                + " join student s on s.curriculum_version_id = cv.id"
                + " where s.id = ?1")
                .setParameter(1, student.getId())
                .getResultList();
        return !data.isEmpty() && data.size() > 1;
    }

    private void setStudentIndividualCurriculum(StudentViewDto studentDto) {
        List<?> result = em.createNativeQuery("select ds.start_date, coalesce(ds_lop.start_date, ds.end_date)"
                + " from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " left join (directive_student ds_lop join directive d_lop on d_lop.id = ds_lop.directive_id "
                + " and d_lop.type_code = :lopDirectiveType and d_lop.status_code = :directiveStatus) "
                + " on ds_lop.directive_student_id = ds.id and ds_lop.canceled = false"
                + " where ds.student_id = :studentId and d.type_code = :directiveType"
                + " and d.status_code = :directiveStatus and ds.canceled = false"
                + " and ds.start_date <= :today and coalesce(ds_lop.start_date, ds.end_date) >= :today"
                + " union all"
                + " select ds2.start_date, coalesce(ds_lop2.start_date, ds2.end_date)"
                + " from directive_student ds2"
                + " join directive d2 on d2.id = ds2.directive_id"
                + " join application a on a.id = ds2.application_id"
                + " join application_support_service ass on ass.application_id = a.id"
                + " left join (directive_student ds_lop2 join directive d_lop2 on d_lop2.id = ds_lop2.directive_id"
                + " and d_lop2.type_code = :lopDirectiveType2 and d_lop2.status_code = :directiveStatus)"
                + " on ds_lop2.directive_student_id = ds2.id and ds_lop2.canceled = false"
                + " where ds2.student_id = :studentId and d2.type_code = :directiveType2 and d2.status_code = :directiveStatus"
                + " and ds2.canceled = false and ass.support_service_code = :supportServiceCode" 
                + " and ds2.start_date <= :today and coalesce(ds_lop2.start_date, ds2.end_date) >= :today")
                .setParameter("studentId", studentDto.getId())
                .setParameter("directiveType", DirectiveType.KASKKIRI_INDOK.name())
                .setParameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter("lopDirectiveType", DirectiveType.KASKKIRI_INDOKLOP.name())
                .setParameter("directiveType2", DirectiveType.KASKKIRI_TUGI.name())
                .setParameter("supportServiceCode", SupportServiceType.TUGITEENUS_1.name())
                .setParameter("lopDirectiveType2", DirectiveType.KASKKIRI_TUGILOPP.name())
                .setParameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();
        if (!result.isEmpty()) {
            studentDto.setIndividualCurriculum(Boolean.TRUE);
            studentDto.setIndividualCurriculumStart(resultAsLocalDate(result.get(0), 0));
            studentDto.setIndividualCurriculumEnd(resultAsLocalDate(result.get(0), 1));
        }
    }

    private void setStudentBoardingSchool(StudentViewDto studentDto) {
        List<?> result = em.createNativeQuery("select d.id, b.id b_id, b.code b_code, r.code r_code, d.valid_from,"
                + " d.valid_thru, d.add_info from dormitory d"
                + " join room r on r.id = d.room_id"
                + " join building b on b.id = r.building_id"
                + " where d.student_id = :studentId and d.valid_from <= :today and d.valid_thru >= :today"
                + " order by d.valid_thru desc, d.valid_from desc")
                .setParameter("studentId", studentDto.getId())
                .setParameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();
        if (!result.isEmpty()) {
            Object r = result.get(0);
            studentDto.setBoardingSchool(new RoomAutocompleteResult(resultAsLong(r, 0), resultAsLong(r, 1),
                    resultAsString(r, 2), resultAsString(r, 3)));
            studentDto.setBoardingSchoolValidFrom(resultAsLocalDate(r, 4));
            studentDto.setBoardingSchoolValidThru(resultAsLocalDate(r, 5));
            studentDto.setBoardingSchoolAddInfo(resultAsString(r, 6));
        }
    }

    public Collection<StudentVocationalResultByTimeDto> vocationalResultsByTimeResults(Student student, String sort) {
        String journalResults = 
                "select false as module, coalesce(je.entry_date,jes.grade_inserted) as kp, j.id, j.name_et, " + 
                    "(select string_agg(tp.firstname||' '||tp.lastname,', ') " + 
                        "from journal_teacher jt " + 
                        "join teacher t on t.id = jt.teacher_id " + 
                        "join person tp on tp.id = t.person_id " + 
                    "where jt.journal_id=j.id) as teacher, " + 
                    "je.entry_type_code, " +
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(cm.name_et||' - '||mcl.name_et),',')||')'" +
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl ON mcl.code = cm.module_code " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id and cv.id=ss.curriculum_version_id) as my_theme, " + 
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et)),',')||')'" +
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl on mcl.code = cm.module_code " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id and cv.id=ss.curriculum_version_id) as my_theme_en, " +
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(cm.name_et||' - '||mcl.name_et)||'('||cv.code||')',',')||')'" +
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl on mcl.code = cm.module_code " + 
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id and cv.id!=ss.curriculum_version_id) as foreign_theme, " +
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et)),',')||')'" + 
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl on mcl.code = cm.module_code " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                   "where jot.journal_id = js.journal_id and cv.id!=ss.curriculum_version_id) as foreign_theme_en, " +
                   "jes.grade_code, jes.grading_schema_row_id, jes.verbal_grade, sy.year_code, sy.start_date, " +
                   "false as informal, false as formal, false as practice, " +
                   "(select string_agg(name_et, ', ') from (select distinct(cm.name_et), cv.id = ss.curriculum_version_id as is_student_cv from journal_omodule_theme jot " +
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id " +
                    "order by cv.id = ss.curriculum_version_id desc, cm.name_et) modules) as modules_et " +
                "from journal_student js " +
                "join journal j on j.id = js.journal_id " +
                "join journal_entry je on je.journal_id = js.journal_id " +
                "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id=js.id " + 
                "join student ss on ss.id=js.student_id " +
                "join study_year sy on sy.id = j.study_year_id " +
                "where js.student_id =:studentId and je.entry_type_code in (:entryTypeCodes)";
        
        String practiceJournalResults = "select false as module, pj.grade_inserted, pj.id, null, tp.firstname||' '||tp.lastname as teacher, null, " + 
                    "cvot.name_et || ' (' || cm.name_et || ' - ' || mcl.name_et || ')' as my_theme, " +
                    "cvot.name_et || ' (' || coalesce(cm.name_en,cm.name_et) || ' - ' || coalesce(cm.name_en,cm.name_et) || ')' as my_theme_en, " +
                    "null, null, pj.grade_code, pj.grading_schema_row_id, null as verbal_grade, sy.year_code, sy.start_date, " +
                    "false as informal, false as formal, true as practice, cm.name_et as modules_et " +
                "from practice_journal pj " +
                "join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id " +
                "join curriculum_version_omodule_theme cvot on cvot.id = pjms.curriculum_version_omodule_theme_id " +
                "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                "join classifier mcl on mcl.code = cm.module_code " +
                "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                "join curriculum c on c.id = cv.curriculum_id " +
                "join study_year sy on sy.id = pj.study_year_id " +
                "join teacher t on t.id = pj.teacher_id " +
                "join person tp on tp.id = t.person_id " +
                "where pj.student_id =:studentId";
        
        String protocolResults = 
                "select true as module, ps.grade_date,pp.id, " + 
                    "cm.name_et||' - '||mcl.name_et ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "tp.firstname ||' '||tp.lastname as teacher, null," +
                    "cm.name_et||' - '||mcl.name_et ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et) ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "null, null, ps.grade_code, ps.grading_schema_row_id, null as verbal_grade, sy.year_code, sy.start_date, " +
                    "false as informal, false as formal, false as practice, cm.name_et as modules_et " +
                "from protocol pp " +
                "join protocol_vdata pv on pp.id=pv.protocol_id " +
                "join protocol_student ps on pp.id=ps.protocol_id " +
                "join curriculum_version_omodule cvo on pv.curriculum_version_omodule_id=cvo.id " +
                "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                "join classifier mcl ON mcl.code = cm.module_code " + 
                "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                "join student ss on ss.id=ps.student_id " +
                "left join teacher t on t .id = pv.teacher_id " +
                "left join person tp on tp.id = t.person_id " +
                "join study_year sy on pv.study_year_id=sy.id " +
          "where ps.student_id=:studentId";
        
        String informalApelResults = 
                "select case when aai.curriculum_version_omodule_theme_id is not null then false else true end as module, aa.confirmed, aa.id, null, null, null, " + 
                    "case when aai.curriculum_version_omodule_theme_id is not null then cvot.name_et || ' (' || cm.name_et || ' - ' || mcl.name_et || ')' " + 
                        "else cm.name_et || ' - ' || mcl.name_et end as my_theme, " +
                    "case when aai.curriculum_version_omodule_theme_id is not null then cvot.name_et || ' (' || cm.name_en || ' - ' || mcl.name_en || ')' " +
                        "else cm.name_en || ' - ' || mcl.name_en end as my_theme_en, " +
                    "null, null, aai.grade_code, null as grading_schema_row_id, null as verbal_grade, sy.year_code, sy.start_date, " +
                    "true as informal, false as formal, false as practice, cm.name_et as modules_et " +
                "from apel_application aa " + 
                "join apel_application_record aar on aa.id=aar.apel_application_id " + 
                "join apel_application_informal_subject_or_module aai on aar.id=aai.apel_application_record_id " + 
                "join curriculum_version_omodule cvo on aai.curriculum_version_omodule_id=cvo.id " + 
                "left join curriculum_version_omodule_theme cvot on aai.curriculum_version_omodule_theme_id=cvot.id " + 
                "join curriculum_module cm on cvo.curriculum_module_id=cm.id " + 
                "join classifier mcl on mcl.code = cm.module_code " + 
                "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) " + 
            "where aa.student_id=:studentId and aa.status_code='VOTA_STAATUS_C' and aai.transfer = true";
        
        String formalApelResults =  "select true as module, aaf.grade_date, aa.id, null, aaf.teachers, null, " + 
                    "cm.name_et || ' - ' || mcl.name_et || case when cv.id != ss.curriculum_version_id then ' (' || cv.code || ')' else '' end, " +
                    "cm.name_en || ' - ' || mcl.name_en || case when cv.id != ss.curriculum_version_id then ' (' || cv.code || ')' else '' end, " +
                    "aaf.name_et || ' - ' || a_s.name_et as foreign_theme, " +
                    "aaf.name_en || ' - ' || a_s.name_en as foreign_theme_en, " +
                    "aaf.grade_code, null as grading_schema_row_id, null as verbal_grade, sy.year_code, sy.start_date, " +
                    "false as informal, true as formal, false as practice, cm.name_et as modules_et " +
                "from apel_application aa " + 
                "join apel_application_record aar on aa.id=aar.apel_application_id " + 
                "join apel_application_formal_subject_or_module aaf on aar.id=aaf.apel_application_record_id " + 
                "left join apel_school a_s on aaf.apel_school_id = a_s.id " + 
                "left join curriculum_version_omodule cvo on aaf.curriculum_version_omodule_id=cvo.id " + 
                "left join curriculum_module cm on cvo.curriculum_module_id=cm.id " + 
                "left join classifier mcl on mcl.code = cm.module_code " + 
                "left join curriculum_version cv on cvo.curriculum_version_id = cv.id " + 
                "left join student ss on aa.student_id = ss.id " + 
                "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) " + 
            "where aa.student_id=:studentId and aa.status_code='VOTA_STAATUS_C' and aaf.transfer = true";

        String outcomeResults = "select false as module, scmor.grade_date, scmor.curriculum_module_outcomes_id, null, " +
                    "p.firstname || ' ' || p.lastname as teachers, '" + JournalEntryType.SISSEKANNE_O.name() + "', " +
                    "cmo.outcome_et || ' (' || cm.name_et || ' - ' || mcl.name_et || ')' as my_theme, " +
                    "cmo.outcome_en || ' (' || coalesce(cm.name_en,cm.name_et) || ' - ' || coalesce(cm.name_en,cm.name_et) || ')' as my_theme_en, " +
                    "null, null, scmor.grade_code, scmor.grading_schema_row_id, null as verbal_grade, sy.year_code, sy.start_date, " +
                    "false as informal, false as formal, false as practice, cm.name_et as modules_et " +
                "from student_curriculum_module_outcomes_result scmor " +
                "join curriculum_module_outcomes cmo on cmo.id = scmor.curriculum_module_outcomes_id " +
                "join curriculum_module cm on cm.id = cmo.curriculum_module_id " +
                "join classifier mcl on mcl.code = cm.module_code " +
                "join student s on s.id = scmor.student_id " +
                "left join teacher t on t.id = scmor.grade_inserted_teacher_id " +
                "left join person p on p.id = t.person_id " +
                "join study_year sy on sy.id = get_study_year(cast(scmor.grade_date as date), cast(s.school_id as int)) " +
                "where scmor.student_id = :studentId";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (" + journalResults + " union all " + practiceJournalResults + " union all " + protocolResults + 
                " union all " + informalApelResults + " union all " + formalApelResults + " union all " + outcomeResults +
                ") xx where grade_code is not null");
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("entryTypeCodes", JOURNAL_RESULT_ENTRY_TYPES);

        qb.sort(sort);
        List<?> rows = qb.select("*", em).getResultList();
        
        List<StudentVocationalResultByTimeDto> result = new ArrayList<>();
        for (Object r : rows) {
            Boolean isModule = resultAsBoolean(r, 0);
            
            StudentVocationalResultByTimeDto dto = new StudentVocationalResultByTimeDto();
            if (!isModule.booleanValue()) {
                dto.setJournalName(resultAsString(r, 3));
                dto.setEntryType(resultAsString(r, 5));
            }
            dto.setIsModule(isModule);
            
            boolean studentCurriculumResult = resultAsString(r, 6) != null ? true : false;
            dto.setName(studentCurriculumResult ? new AutocompleteResult(null, resultAsString(r, 6), resultAsString(r, 7))
                            : new AutocompleteResult(null, resultAsString(r, 8), resultAsString(r, 9)));

            dto.setDate(resultAsLocalDate(r, 1));
            dto.setGrade(new GradeDto(resultAsString(r, 10), resultAsLong(r, 11)));
            dto.setVerbalGrade(resultAsString(r, 12));
            dto.setTeachers(resultAsString(r, 4));
            dto.setStudyYear(resultAsString(r, 13));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 14));
            dto.setIsInformal(resultAsBoolean(r, 15));
            dto.setIsFormal(resultAsBoolean(r, 16));
            dto.setIsPractice(resultAsBoolean(r, 17));
            dto.setIsApel(Boolean.valueOf(dto.getIsInformal().booleanValue() || dto.getIsFormal().booleanValue()));
            
            result.add(dto);
        }
        return result;
    }

    public StudentVocationalResultDto vocationalResults(Student student) {
        StudentVocationalResultDto dto = new StudentVocationalResultDto();
        List<StudentVocationalResultModuleThemeDto> results = studentVocationalResults(student, false, true);
        dto.setResults(results);
        
        Long curriculumVersionId = EntityUtil.getNullableId(student.getCurriculumVersion());
        String specialityCode = student.getStudentGroup() != null && student.getStudentGroup().getSpeciality() != null
                ? EntityUtil.getCode(student.getStudentGroup().getSpeciality())
                : "";
        if (curriculumVersionId != null) {
            dto.setCurriculumModules(vocationalCurriculumModules(student, curriculumVersionId, specialityCode));
            setExtraCurriculaModules(dto, results, curriculumVersionId, specialityCode);
        }
        
        addOtherCurriculumVersionModuleThemes(dto.getResults(), dto.getCurriculumModules(),
                dto.getExtraCurriculaModules());
        return dto;
    }

    public List<StudentVocationalResultModuleThemeDto> studentVocationalResults(Student student, boolean showUncompleted,
            boolean generateNameBeforehand) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        if (student.getCurriculumVersion() != null) {
            result.addAll(vocationalResultsOutcomeResults(student));
            result.addAll(vocationalResultsThemeResults(student, generateNameBeforehand));
            result.addAll(vocationalResultsModuleResults(student, generateNameBeforehand));
            result.addAll(formalLearningReplacedModuleResults(student, generateNameBeforehand));
        }
        if (showUncompleted) addExtraModulesAndThemes(student, result);
        return result;
    }
    
    private void addExtraModulesAndThemes(Student student, List<StudentVocationalResultModuleThemeDto> result) {
        List<Long> addedThemes = result.stream().filter(p -> p.getTheme() != null).map(p -> p.getTheme().getId()).collect(Collectors.toList());
        List<Long> addedModules = result.stream().map(p -> p.getModule().getId()).collect(Collectors.toList());
        // Should have only one declaration
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j "
                + "join journal_teacher jt on j.id = jt.journal_id "
                + "join journal_student js on js.journal_id = j.id "
                + "join journal_omodule_theme jot on jot.journal_id = j.id "
                + "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join teacher t on jt.teacher_id = t.id "
                + "join person p on t.person_id = p.id");
        String checkForThemes = "cvot.id not in (:themeIds)";
        String checkForModules = "cm.id not in (:moduleIds)";
        if (addedThemes != null && !addedThemes.isEmpty() && addedModules != null && !addedModules.isEmpty()) {
            qb.optionalCriteria("(" + checkForThemes +" or " + checkForModules + ")", "themeIds", addedThemes);
            qb.parameter("moduleIds", addedModules);
        } else if (addedThemes != null && !addedThemes.isEmpty()) {
            qb.optionalCriteria(checkForThemes, "themeIds", addedThemes);
        } else if (addedModules != null && !addedModules.isEmpty()) {
            qb.optionalCriteria(checkForModules, "moduleIds", addedModules);
        }
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.groupBy("themeId, themeEt, moduleId, moduleEt, moduleEn, cm_order, cv_code, module_code, cvo_id, cm_curriculum ");
        List<?> data = qb.select("cvot.id themeId, cvot.name_et themeEt, cm.id moduleId, cm.name_et moduleEt, cm.name_en moduleEn, "
                + "cm.credits, string_agg(p.firstname||' '||p.lastname,', ') as teachers, "
                + "cm.order_nr cm_order, cv.code cv_code, cm.module_code module_code, cvo.id cvo_id, cm.curriculum_id cm_curriculum ", em).getResultList();
        for (Object r : data) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setTheme(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1)));
            dto.setCurriculumVersionModuleId(resultAsLong(r, 10));
            dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4),
                    resultAsString(r, 9), resultAsString(r, 8), resultAsShort(r, 7), resultAsDecimal(r, 5)));
            dto.setCredits(JpaQueryUtil.resultAsDecimal(r, 5));
            dto.setTeachersAsString(resultAsString(r, 6));
            dto.setCurriculum(new CurriculumResult(resultAsLong(r, 11), null, null, null));
            result.add(dto);
        }
    }

    // protocol results and apel informal learning results
    private List<StudentVocationalResultModuleThemeDto> vocationalResultsModuleResults(Student student,
            boolean generateNameBeforehand) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();

        String from = "from student_vocational_result svr"
                + " join curriculum_version_omodule cvo on cvo.id = svr.curriculum_version_omodule_id"
                + " join curriculum_version cv on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm on cm.id = cvo.curriculum_module_id"
                + " join classifier mcl on mcl.code = cm.module_code"
                + " left join study_year sy on sy.id = svr.study_year_id"
                + " left join apel_application_record aar on svr.apel_application_record_id = aar.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("svr.arr_modules is null");
        qb.sort("svr.grade_date");

        List<?> rows = qb.select("distinct cvo.id cvo_id, cm.id cm_id, cv.code, cm.name_et as module_name_et, mcl.name_et classifer_name_et, "
                + "cm.name_en as module_name_en, mcl.name_en as classifer_name_en, "
                + "cm.credits, svr.apel_application_record_id, svr.grade_code, svr.grade_date, "
                + "svr.teachers, sy.year_code, sy.start_date, cm.order_nr, cm.module_code, cm.curriculum_id, svr.grading_schema_row_id",
                em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();

            dto.setCurriculumVersionModuleId(resultAsLong(r, 0));
            if (generateNameBeforehand) {
                dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(AutocompleteResult.curriculumModuleResult(resultAsLong(r, 1), resultAsString(r, 3),
                        resultAsString(r, 5), resultAsString(r, 4), resultAsString(r, 6), resultAsString(r, 2))));
            } else {
                dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(resultAsLong(r, 1), resultAsString(r, 3), resultAsString(r, 5),
                        resultAsString(r, 15), resultAsString(r, 2), resultAsShort(r, 14), resultAsDecimal(r, 7)));
            }
            dto.setCredits(resultAsDecimal(r, 7));
            dto.setCurriculum(new CurriculumResult(resultAsLong(r, 16), null, null, null));
            
            Long apelApplicationRecordId = resultAsLong(r, 8);
            dto.setIsApelTransfer(apelApplicationRecordId != null ? Boolean.TRUE : Boolean.FALSE);
            dto.setIsFormalLearning(Boolean.FALSE);
            dto.setGrade(new GradeDto(resultAsString(r, 9), resultAsLong(r, 17)));
            dto.setDate(resultAsLocalDate(r, 10));
            dto.getTeachers().add(new AutocompleteResult(null, resultAsString(r, 11), resultAsString(r, 11)));
            dto.setStudyYear(resultAsString(r, 12));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 13));
            result.add(dto);
        }
        return result;
    }
    
    private List<StudentVocationalResultModuleThemeDto> formalLearningReplacedModuleResults(Student student,
            boolean generateNameBeforehand) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr "
                + "join curriculum_version_omodule cvo on cvo.id = any(svr.arr_modules) "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "left join apel_school a_s on svr.apel_school_id = a_s.id "
                + "left join study_year sy on sy.id = svr.study_year_id");
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.sort("svr.grade_date");
        qb.groupBy("svr.arr_modules, svr.module_name_et, svr.module_name_en, svr.credits, a_s.id, "
                + "svr.grade_code, svr.grade_date, svr.teachers, sy.year_code, sy.start_date, svr.id ");
        
        List<?> rows = qb.select("svr.module_name_et, svr.module_name_en, " +
                "string_agg(cm.name_et" + (generateNameBeforehand ? " || ' - ' || mcl.name_et" : "") + ", ', ') as replaced_modules_et, " +
                "string_agg(cm.name_en" + (generateNameBeforehand ? " || ' - ' || mcl.name_en" : "") + ", ', ') as replaced_modules_en, " +
                "svr.credits, a_s.id as school_id, " +
                "a_s.name_et, a_s.name_en, svr.grade_code, svr.grade_date, svr.teachers, sy.year_code, sy.start_date, " +
                "array_to_string(array_agg(cast(cm.id as text)), ','), svr.id svr_id, " +
                "max(cm.order_nr) as cm_order, " + 
                "min(cm.curriculum_id) as curriculum_id, svr.grading_schema_row_id", em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setCurriculumVersionModuleId(null);
            String svrEn = resultAsString(r, 1);
            String moduleEn = resultAsString(r, 3);
            if (generateNameBeforehand) {
                dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(new AutocompleteResult(null, 
                        formalLearningResultModuleName(resultAsString(r, 0), resultAsString(r, 2), resultAsString(r, 6)),
                        formalLearningResultModuleName(
                                StringUtils.isEmpty(svrEn) ? resultAsString(r, 0) : svrEn, 
                                StringUtils.isEmpty(moduleEn) ? resultAsString(r, 2) : moduleEn, resultAsString(r, 7)))));
            } else {
                dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(new AutocompleteResult(
                        Long.valueOf(resultAsLong(r, 14).longValue() * -1), // For grouping it by module id svr id should be different
                        formalLearningResultModuleName(resultAsString(r, 0), resultAsString(r, 2),
                                resultAsString(r, 6)),
                        formalLearningResultModuleName(StringUtils.isEmpty(svrEn) ? resultAsString(r, 0) : svrEn,
                                StringUtils.isEmpty(moduleEn) ? resultAsString(r, 2) : moduleEn,
                                resultAsString(r, 7)))));
            }
            dto.setCredits(resultAsDecimal(r, 4));
            dto.setGrade(new GradeDto(resultAsString(r, 8), resultAsLong(r, 17)));
            dto.setIsApelTransfer(Boolean.TRUE);
            dto.setIsFormalLearning(Boolean.TRUE);
            dto.getModule().setOrderNr(resultAsShort(r, 15));
            dto.setCurriculum(new CurriculumResult(resultAsLong(r, 16), null, null, null)); // ID for certificate sorting
            dto.setDate(resultAsLocalDate(r, 9));
            
            String replacedModulesAsString = resultAsString(r, 13);
            if(replacedModulesAsString != null) {
                List<String> replacedModules = Arrays.asList(replacedModulesAsString.split(","));
                dto.setReplacedModules(new ArrayList<>());
                for (String moduleId : replacedModules) {
                    dto.getReplacedModules().add(Long.valueOf(moduleId));
                }
            }
            result.add(dto);
       }
       return result;
    }
    
    private static String formalLearningResultModuleName(String moduleName, String replacedModules, String school) {
        if (moduleName != null && replacedModules != null) {
            String name = moduleName + " -> (" + replacedModules + ")";
            return school != null ? name + " - " + school : name;
        }
        return null;
    }

    private Collection<StudentVocationalResultModuleThemeDto> vocationalResultsThemeResults(Student student, boolean generateNameBeforehand) {
        String journalSelect = "cvo_id, cm_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en, cvot_id, cvot_name_et, cvot_credits, "
                + "grade_code, grade_inserted, teacher_id, teacher_firstname, teacher_lastname, sy_year_code, sy_start_date, "
                + "is_apel_transfer, is_formal, curriculum_version_result, cm_order, cm_module_code, cm_credits, cm_curriculum, "
                + "grading_schema_row_id ";

        String journalCurriculumResults = " select * from (select distinct on (cvot.id, teacher_id) cvo.id cvo_id, cm.id cm_id, "
                + "cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, "
                + "jes.grade_code grade_code, jes.grade_inserted grade_inserted, tp.id teacher_id, tp.firstname teacher_firstname, "
                + "tp.lastname teacher_lastname, cvot.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, "
                + "mcl.name_en mcl_name_en, cm.credits cm_credits, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "false is_apel_transfer, false is_formal, first_value(cv.id) over (partition by jot.journal_id order by case when "
                + "cvo.curriculum_version_id = :curriculumVersionId then 1 else 0 end desc, case when cm.curriculum_id = :curriculumId then 1 else 0 end desc, "
                + "cvo.curriculum_version_id) = :curriculumVersionId curriculum_version_result, "
                + "cm.order_nr cm_order, cm.module_code cm_module_code, cm.curriculum_id cm_curriculum, jes.grading_schema_row_id "
                + "from journal_student js "
                + "join journal_omodule_theme jot on jot.journal_id = js.journal_id "
                + "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " 
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                + "join journal j on j.id = js.journal_id "
                + "join study_year sy on sy.id = j.study_year_id " 
                + "join journal_entry je on je.journal_id = js.journal_id "
                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                + "left join journal_teacher jt on jt.journal_id = js.journal_id "
                + "left join teacher t on t.id = jt.teacher_id "
                + "left join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and je.entry_type_code = :entryTypeCode and jes.grade_code is not null "
                    + "and cm.id in (select cmo.curriculum_module_id from curriculum_version cv "
                    + "join curriculum_version_omodule cmo on cv.id = cmo.curriculum_version_id where cv.id = :curriculumVersionId)) x";
        
        String journalExtraCurriculumResults = " select distinct on (cvot.id, teacher_id) cvo.id cvo_id, cm.id cm_id, "
                + "cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, "
                + "jes.grade_code grade_code, jes.grade_inserted grade_inserted, tp.id teacher_id, tp.firstname teacher_firstname, "
                + "tp.lastname teacher_lastname, cvot.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, "
                + "mcl.name_en mcl_name_en, cm.credits cm_credits, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "false is_apel_transfer, false is_formal, false curriculum_version_result, "
                + "cm.order_nr cm_order, cm.module_code cm_module_code, cm.curriculum_id cm_curriculum, jes.grading_schema_row_id "
                + "from journal_student js "
                + "join journal_omodule_theme jot on jot.journal_id = js.journal_id "
                + "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id " 
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id " 
                + "join journal j on j.id = js.journal_id "
                + "join study_year sy on sy.id = j.study_year_id "
                + "join journal_entry je on je.journal_id = js.journal_id "
                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                + "left join journal_teacher jt on jt.journal_id = js.journal_id " 
                + "left join teacher t on t.id = jt.teacher_id "
                + "left join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and je.entry_type_code = :entryTypeCode and jes.grade_code is not null "
                    + "and cm.id not in (select cmo3.curriculum_module_id from curriculum_version cv3 "
                        + "join curriculum_version_omodule cmo3 on cv3.id = cmo3.curriculum_version_id where cv3.id = :curriculumVersionId) "
                    + "and (select count(*) from journal_omodule_theme jot2 join curriculum_version_omodule_theme cvot2 on "
                    + "cvot2.id = jot2.curriculum_version_omodule_theme_id join curriculum_version_omodule cvo2 on cvo2.id = cvot2.curriculum_version_omodule_id "
                    + "where jot2.journal_id = j.id and cvo2.curriculum_module_id in "
                        + "(select cmo3.curriculum_module_id from curriculum_version cv3 "
                        + "join curriculum_version_omodule cmo3 on cv3.id = cmo3.curriculum_version_id "
                        + "where cv3.id = :curriculumVersionId)) = 0";
        
        String practiceJournalResults = "select cvo.id cvo_id, cm.id cm_id, cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, "
                + "cvot.credits cvot_credits, pj.grade_code grade_code, pj.grade_inserted grade_inserted, tp.id teacher_id, tp.firstname teacher_firstname, "
                + "tp.lastname teacher_lastname, cvot.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, " 
                + "mcl.name_en mcl_name_en, cm.credits cm_credits, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "false is_apel_transfer, false is_formal, cv.id = :curriculumVersionId curriculum_version_result, "
                + "cm.order_nr cm_order, cm.module_code cm_module_code, cm.curriculum_id cm_curriculum, pj.grading_schema_row_id "
                + "from practice_journal pj "
                + "join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id "
                + "join curriculum_version_omodule_theme cvot on cvot.id = pjms.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id " 
                + "join curriculum c on c.id = cv.curriculum_id "
                + "join study_year sy on sy.id = pj.study_year_id "
                + "join teacher t on t.id = pj.teacher_id "
                + "join person tp on tp.id = t.person_id "
                + "where pj.student_id = :studentId and pj.grade_code is not null";
        
        String journalResultsOrderBy = " order by 1 desc, teacher_id, grade_inserted desc";
        
        String apelInformalResults = "select cvo.id cvo_id, cm.id cm_id, cv.code cv_code, "
                + "cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, "
                + "cvot.id cvot_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, aai.grade_code grade_code, aa.confirmed grade_inserted, null as teacher_id, "
                + "null teacher_firstname, null teacher_lastname, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "true is_apel_transfer, false is_formal, cv.id = :curriculumVersionId curriculum_version_result, "
                + "cm.order_nr cm_order, cm.module_code cm_module_code, cm.credits cm_credits, cm.curriculum_id cm_curriculum, null grading_schema_row_id "
                + "from apel_application aa join apel_application_record aar on aa.id = aar.apel_application_id "
                + "join apel_application_informal_subject_or_module aai on aar.id = aai.apel_application_record_id "
                + "join curriculum_version_omodule_theme cvot on aai.curriculum_version_omodule_theme_id = cvot.id "
                + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
                + "join curriculum_version cv on cvo.curriculum_version_id = cv.id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) "
                + "where aa.student_id = :studentId and aa.status_code = :applicationStatus and aai.transfer = true";
        
        String apelFormalResults = "select cvo.id cvo_id, cm.id cm_id, cv.code cv_code, cm.name_et cm_name_et, "
                + "mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, "
                + "cvot.id cvot_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, 'KUTSEHINDAMINE_A' grade_code, aa.confirmed grade_inserted, null as teacher_id, "
                + "null teacher_firstname, null teacher_lastname, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "true is_apel_transfer, true is_formal, cv.id = :curriculumVersionId curriculum_version_result, "
                + "cm.order_nr cm_order, cm.module_code cm_module_code, cm.credits cm_credits, cm.curriculum_id cm_curriculum, null grading_schema_row_id "
                + "from apel_application aa join apel_application_record aar on aa.id = aar.apel_application_id "
                + "join apel_application_formal_subject_or_module aaf on aar.id = aaf.apel_application_record_id "
                + "join apel_application_formal_replaced_subject_or_module aarf on aar.id = aarf.apel_application_record_id "
                + "join curriculum_version_omodule_theme cvot on aarf.curriculum_version_omodule_theme_id = cvot.id "
                + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
                + "join curriculum_version cv on cvo.curriculum_version_id = cv.id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) "
                + "where aa.student_id = :studentId and aa.status_code = :applicationStatus and aaf.transfer = true";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (select " + journalSelect + " from (" + journalCurriculumResults + " union "
                        + journalExtraCurriculumResults + " union " + practiceJournalResults + journalResultsOrderBy
                        + ") as journal_results union all " + apelInformalResults + " union all " + apelFormalResults
                        + " order by is_apel_transfer desc, grade_inserted desc) as results");

        qb.parameter("entryTypeCode", JournalEntryType.SISSEKANNE_L.name());
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));
        qb.parameter("curriculumId", EntityUtil.getId(student.getCurriculumVersion().getCurriculum()));
        qb.parameter("applicationStatus", ApelApplicationStatus.VOTA_STAATUS_C.name());

        List<?> rows = qb.select("cvo_id, cm_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en, "
                + "cvot_id, cvot_name_et, cvot_credits, grade_code, grade_inserted, "
                + "teacher_id, teacher_firstname, teacher_lastname, sy_year_code, sy_start_date, is_apel_transfer, is_formal, "
                + "curriculum_version_result, cm_order, cm_module_code, cm_credits, cm_curriculum, grading_schema_row_id",
                em).getResultList();

        Map<Long, StudentVocationalResultModuleThemeDto> result = new HashMap<>();
        for (Object r : rows) {
            Long themeId = resultAsLong(r, 7);
            StudentVocationalResultModuleThemeDto dto = result.get(themeId);
            if (dto != null) {
                String teacherName = PersonUtil.fullname(resultAsString(r, 13), resultAsString(r, 14));
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 12), teacherName, teacherName));
            } else {
                dto = new StudentVocationalResultModuleThemeDto();
                dto.setCurriculumVersionModuleId(resultAsLong(r, 0));
                if (generateNameBeforehand) {
                    dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(AutocompleteResult.curriculumModuleResult(resultAsLong(r, 1), resultAsString(r, 3),
                            resultAsString(r, 5), resultAsString(r, 4), resultAsString(r, 6), resultAsString(r, 2))));
                } else {
                    dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(resultAsLong(r, 1), resultAsString(r, 3), resultAsString(r, 5),
                            resultAsString(r, 21), resultAsString(r, 2), resultAsShort(r, 20), resultAsDecimal(r, 22)));
                }
                dto.setCurriculum(new CurriculumResult(resultAsLong(r, 23), null, null, null));

                dto.setTheme(new AutocompleteResult(themeId, resultAsString(r, 8), resultAsString(r, 8)));
                dto.setCredits(resultAsDecimal(r, 9));
                dto.setGrade(new GradeDto(resultAsString(r, 10), resultAsLong(r, 24)));
                dto.setDate(resultAsLocalDate(r, 11));
                String teacherName = PersonUtil.fullname(resultAsString(r, 13), resultAsString(r, 14));
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 12), teacherName, teacherName));
                dto.setStudyYear(resultAsString(r, 15));
                dto.setStudyYearStartDate(resultAsLocalDate(r, 16));
                dto.setIsApelTransfer(resultAsBoolean(r, 17));
                dto.setIsFormalLearning(resultAsBoolean(r, 18));
                dto.setIsCurrentCurriculumVersionResult(resultAsBoolean(r, 19));
                result.put(themeId, dto);
            }
        }
        return result.values();
    }

    private List<StudentVocationalResultModuleThemeDto> vocationalResultsOutcomeResults(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_curriculum_module_outcomes_result scmor "
                + "join curriculum_module_outcomes cmo on cmo.id = scmor.curriculum_module_outcomes_id "
                + "join curriculum_module cm on cm.id = cmo.curriculum_module_id "
                + "join curriculum c on c.id = cm.curriculum_id "
                + "left join teacher t on t.id = scmor.grade_inserted_teacher_id "
                + "left join person p on p.id = t.person_id");
        qb.requiredCriteria("scmor.student_id = :student_id", "student_id", EntityUtil.getId(student));
        qb.filter("scmor.grade_code is not null");

        List<?> data = qb.select("cm.id cm_id, cm.name_et, cm.name_en, cmo.id, cmo.outcome_et, cmo.outcome_en, "
                + "scmor.grade_code, scmor.grade_date, p.firstname, p.lastname, "
                + "c.id c_id, c.name_et c_name_et, c.name_en c_name_en, c.code c_code, cm.order_nr cm_order, "
                + "cm.module_code, cm.credits credits, scmor.grading_schema_row_id", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setModule(new StudentVocationalResultModuleThemeDto.CurriculumModuleResult(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsString(r, 2), resultAsString(r, 15), null, resultAsShort(r, 14), resultAsDecimal(r, 16)));
            dto.setOutcome(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
            dto.setGrade(new GradeDto(resultAsString(r, 6), resultAsLong(r, 17)));
            dto.setDate(resultAsLocalDate(r, 7));
            String teacherName = PersonUtil.fullname(resultAsString(r, 8), resultAsString(r, 9));
            dto.getTeachers().add(new AutocompleteResult(null, teacherName, teacherName));
            dto.setCurriculum(new CurriculumResult(resultAsLong(r, 10), resultAsString(r, 11), resultAsString(r, 12),
                    resultAsString(r, 13)));
            return dto;
        }, data);
    }

    private List<StudentVocationalModuleDto> vocationalCurriculumModules(Student student, Long curriculumVersionId,
            String specialityCode) {
        List<?> data = em.createNativeQuery("select cvo.id from curriculum_version_omodule cvo "
                + "left join curriculum_module_occupation cmo on cmo.curriculum_module_id = cvo.curriculum_module_id and cmo.occupation_code like ?1 "
                + "where cvo.curriculum_version_id = ?2 and (cmo.id is null or cmo.occupation_code = ?3)")
                .setParameter(1, "%" + MainClassCode.SPETSKUTSE.name() + "%")
                .setParameter(2, curriculumVersionId)
                .setParameter(3, specialityCode)
                .getResultList();

        Set<Long> moduleIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        List<StudentVocationalModuleDto> modules = curriculumVersionOccupationModules(moduleIds);
        Map<Long, StudentVocationalModuleDto> modulesMap = StreamUtil.toMap(m -> m.getCurriculumModule().getId(),
                modules);
        if (!modulesMap.isEmpty()) {
            Map<Long, Set<Long>> replacedThemes = curriculumModuleReplacedThemes(student, modulesMap.keySet());
            for (Long curriculumModuleId : replacedThemes.keySet()) {
                Set<Long> moduleReplacedThemes = replacedThemes.get(curriculumModuleId);
                if (moduleReplacedThemes != null) {
                    modulesMap.get(curriculumModuleId).getReplacedThemes().addAll(moduleReplacedThemes);
                }
            }
        }

        return modules;
    }

    private List<StudentVocationalModuleDto> curriculumVersionOccupationModules(Set<Long> moduleIds) {
        if (moduleIds == null || moduleIds.isEmpty()) {
            return new LinkedList<>();
        }
        List<CurriculumVersionOccupationModule> modules = em
                .createQuery("select cvo from CurriculumVersionOccupationModule cvo where cvo.id in (?1)",
                        CurriculumVersionOccupationModule.class)
                .setParameter(1, moduleIds).getResultList();

        return StreamUtil.toMappedList(StudentVocationalModuleDto::of, modules);
    }

    private Map<Long, Set<Long>> curriculumModuleReplacedThemes(Student student, Set<Long> curriculumModuleIds) {
        List<?> data = em.createNativeQuery("select aot.curriculum_module_id, aot.curriculum_version_omodule_theme_id "
                + "from application_omodule_theme aot "
                + "join application a on a.id = aot.application_id "
                + "where a.student_id = ?1 and a.type_code = ?2 and a.status_code = ?3 "
                + "and aot.curriculum_module_id in (?4) and aot.is_old = false")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, ApplicationType.AVALDUS_LIIK_RAKKAVA.name())
                .setParameter(3, ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name())
                .setParameter(4, curriculumModuleIds)
                .getResultList();

        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toSet())));
    }

    private void setExtraCurriculaModules(StudentVocationalResultDto dto,
            List<StudentVocationalResultModuleThemeDto> results, Long curriculumVersionId, String specialityCode) {
        Map<Long, Long> extraCurriculaModuleIds = extraCurriculaSpecialityModules(curriculumVersionId, specialityCode);
        Set<Long> curriculaModuleIds = StreamUtil.toMappedSet(it -> it.getCurriculumModule().getId(),
                dto.getCurriculumModules());

        if (!results.isEmpty()) {
            for (StudentVocationalResultModuleThemeDto result : results) {
                if (result.getModule().getId() != null && result.getCurriculumVersionModuleId() != null
                        && !extraCurriculaModuleIds.containsKey(result.getModule().getId())) {
                    extraCurriculaModuleIds.put(result.getModule().getId(), result.getCurriculumVersionModuleId());
                }
            }
        }
        
        extraCurriculaModuleIds.keySet().removeIf(curriculaModuleIds::contains);
        dto.getExtraCurriculaModules().addAll(StreamUtil.toMappedList(StudentVocationalModuleDto::of,
                curriculumVersionOccupationModuleRepository.findAll(extraCurriculaModuleIds.values())));
    }

    private Map<Long, Long> extraCurriculaSpecialityModules(Long curriculumVersionId, String specialityCode) {
        List<?> data = em.createNativeQuery("select distinct cm.id cm_id, cvo.id cvo_id from curriculum_version_omodule cvo "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join curriculum_module_occupation cmo on cmo.curriculum_module_id = cvo.curriculum_module_id and cmo.occupation_code like ?1 "
                + "where cvo.curriculum_version_id = ?2 and cmo.occupation_code != ?3")
                .setParameter(1, "%" + MainClassCode.SPETSKUTSE.name() + "%")
                .setParameter(2, curriculumVersionId)
                .setParameter(3, specialityCode)
                .getResultList();
        
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), data);
    }

    private void addOtherCurriculumVersionModuleThemes(List<StudentVocationalResultModuleThemeDto> results,
            List<StudentVocationalModuleDto> curriculumModules,
            List<StudentVocationalModuleDto> extraCurriculaModules) {
        List<StudentVocationalResultModuleThemeDto> themeResults = StreamUtil.toFilteredList(r -> r.getTheme() != null
                && r.getTheme().getId() != null && Boolean.FALSE.equals(r.getIsCurrentCurriculumVersionResult()),
                results);

        if (!themeResults.isEmpty()) {
            Map<Long, List<StudentVocationalResultModuleThemeDto>> themesByModules = themeResults.stream()
                    .collect(Collectors.groupingBy(t -> t.getModule().getId()));

            List<StudentVocationalModuleDto> modules = new ArrayList<>();
            modules.addAll(curriculumModules);
            modules.addAll(extraCurriculaModules);
            Map<Long, StudentVocationalModuleDto> modulesMap = StreamUtil.toMap(m -> m.getCurriculumModule().getId(),
                    modules);

            for (Long moduleId : modulesMap.keySet()) {
                StudentVocationalModuleDto module = modulesMap.get(moduleId);
                Map<Long, CurriculumVersionOccupationModuleThemeDto> moduleThemes = StreamUtil.toMap(t -> t.getId(),
                        module.getThemes());
                List<StudentVocationalResultModuleThemeDto> moduleThemeResults = themesByModules.get(moduleId);

                if (moduleThemeResults != null) {
                    for (StudentVocationalResultModuleThemeDto themeResult : moduleThemeResults) {
                        if (!moduleThemes.containsKey(themeResult.getTheme().getId())) {
                            CurriculumVersionOccupationModuleThemeDto themeDto = CurriculumVersionOccupationModuleThemeDto
                                    .forCurriculumFulfillment(em.getReference(CurriculumVersionOccupationModuleTheme.class,
                                            themeResult.getTheme().getId()));
                            themeDto.setOtherCurriculumVersionModuleTheme(Boolean.TRUE);
                            module.getOtherCurriculumVersionModuleThemes().add(themeDto);
                        }
                    }
                }
            }
        }
    }

    private List<StudentOccupationCertificateDto> occupationCertificates(Student student) {
        List<StudentOccupationCertificate> data = em.createQuery(
                "select soc from StudentOccupationCertificate soc where soc.student.id = ?1 order by soc.issueDate desc", StudentOccupationCertificate.class)
            .setParameter(1, EntityUtil.getId(student))
            .getResultList();
        return StreamUtil.toMappedList(StudentOccupationCertificateDto::new, data);
    }

    private List<StudentDormitoryHistoryDto> dormitoryHistory(Student student) {
        List<?> data = em.createNativeQuery("select sh.inserted, sh.dormitory_code from student s "
                + "join student_history sh on sh.student_id = s.id "
                + "where s.id = ?1 and sh.dormitory_code is not null order by sh.inserted desc")
                .setParameter(1, EntityUtil.getId(student))
                .getResultList();

        List<StudentDormitoryHistoryDto> dormitoryHistory = new ArrayList<>();
        for (Object row : data) {
            StudentDormitoryHistoryDto newerHistoryDto = dormitoryHistory.size() > 0
                    ? dormitoryHistory.get(dormitoryHistory.size() - 1) : null;
            StudentDormitoryHistoryDto historyDto = new StudentDormitoryHistoryDto(resultAsLocalDate(row, 0),
                    resultAsString(row, 1));
            if (newerHistoryDto != null && newerHistoryDto.getDormitory().equals(historyDto.getDormitory())) {
                newerHistoryDto.setDate(historyDto.getDate());
            } else {
                dormitoryHistory.add(historyDto);
            }
        }
        return dormitoryHistory;
    }

    public List<StudentVocationalConnectedEntity> vocationalConnectedEntities(Long studentId) {
        Query q = em.createNativeQuery("select jj.id, 'journal' as type, jj.name_et as name_et, jj.name_et as name_en, sy.end_date, sy.year_code, null as protocol_nr from journal jj "
                + "join journal_student js on jj.id=js.journal_id " 
                + "join study_year sy on jj.study_year_id = sy.id " 
                + "where js.student_id = ?1 " 
                + "union " 
                + "select pj.id, 'practice', 'Praktikapäevik', 'Practice journal', pj.grade_inserted, sy.year_code, null as protocol_nr from practice_journal pj "
                + "join study_year sy on pj.study_year_id = sy.id "
                + "where pj.student_id = ?1 "
                + "union "
                + "select ps.protocol_id, case when p.is_final = true then 'finalProtocol' else 'protocol' end, cm.name_et, "
                + "cm.name_et, sy.end_date, sy.year_code, p.protocol_nr from protocol_student ps "
                + "join protocol p on ps.protocol_id = p.id "
                + "join protocol_vdata pvd on ps.protocol_id = pvd.protocol_id "  
                + "join curriculum_version_omodule cvo on pvd.curriculum_version_omodule_id = cvo.id " 
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id " 
                + "join study_year sy on pvd.study_year_id = sy.id " 
                + "where ps.student_id = ?1 " 
                + "union " 
                + "select aa.id, 'apel', 'VÕTA avaldus', 'APEL application', aa.confirmed, null as year_code, null from apel_application aa "
                + "where aa.student_id = ?1 and aa.status_code='VOTA_STAATUS_C' " 
                + "order by 5 desc");
        q.setParameter(1, studentId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(
                r -> new StudentVocationalConnectedEntity(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsLocalDate(r, 4), resultAsString(r, 5), resultAsString(r, 6)), data);
    }

    public List<AutocompleteResult> specialities(Student student) {
        if (student.getCurriculumVersion() == null) return null;
        return StreamUtil.toMappedList(spec -> AutocompleteResult.of(spec.getCurriculumSpeciality()),
                student.getCurriculumVersion().getSpecialities());
    }

    public List<StudentSpecialitySearchDto> saveSpecialities(HoisUserDetails user, List<StudentSpecialitySearchDto> form) {
        EntityUtil.setUsername(user.getUsername(), em);
        Map<Long, StudentSpecialitySearchDto> rows = form.stream().collect(Collectors.toMap(StudentSpecialitySearchDto::getId, Function.identity()));
        rows.forEach((id, studentSpec) -> {
            Student student = em.getReference(Student.class, id);
            if (UserUtil.isSameSchool(user, student.getSchool()) && StudentUtil.isActive(student) 
                    && !Objects.equals(EntityUtil.getNullableId(student.getCurriculumSpeciality()), studentSpec.getSpeciality().getId())) {
                student.setCurriculumSpeciality(studentSpec.getSpeciality().getId() == null ? null : em.getReference(CurriculumSpeciality.class, studentSpec.getSpeciality().getId()));
                saveWithHistory(student);
            } else {
                studentSpec.getSpeciality().setId(EntityUtil.getNullableId(student.getCurriculumSpeciality()));
            }
        });
        return rows.values().stream().collect(Collectors.toList());
    }

    private Map<Long, StudentVocationalStudyProgrammeDto> studentStudyProgrammes(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select student_id, "
                + "sum(case when m.module_code = 'KUTSEMOODUL_Y' then m.credits else 0 end) as general, "
                + "sum(case when m.module_code = 'KUTSEMOODUL_P' then m.credits else 0 end) as core, "
                + "sum(case when m.module_code = 'KUTSEMOODUL_L' then m.credits else 0 end) as final, "
                + "min(m.optional_study_credits) as free_choice from "
                + "(select distinct ss.id student_id, cvo.id cvo_id, cm.id as m_id, cm.credits, "
                + "cc.optional_study_credits, cm.module_code from curriculum_version cv "
                + "join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id and cv.curriculum_id = cm.curriculum_id "
                    + "and coalesce(cm.is_additional, false) = false and cm.module_code in (?1) "
                    + "and coalesce(cm.is_additional, false) = false "
                + "join curriculum cc on cv.curriculum_id = cc.id "
                + "join student ss on cv.id = ss.curriculum_version_id "
                + "left join student_group sg on ss.student_group_id = sg.id "
                + "where ss.id in (?2) and (sg.id is null or coalesce( sg.speciality_code, 'x' )= 'x' "
                + "or coalesce( sg.speciality_code, 'x' )!= 'x' and exists(select 1 from curriculum_module_occupation cmo left "
                + "join classifier_connect ccc on cmo.occupation_code = ccc.connect_classifier_code "
                + "where cmo.curriculum_module_id = cm.id and (cmo.occupation_code = sg.speciality_code "
                + "or ccc.classifier_code = sg.speciality_code)))) as m group by student_id")
                .setParameter(1, EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_Y,
                        CurriculumModuleType.KUTSEMOODUL_P, CurriculumModuleType.KUTSEMOODUL_L))
                .setParameter(2, studentIds)
                .getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> new StudentVocationalStudyProgrammeDto(resultAsDecimal(r, 1),
                resultAsDecimal(r, 2), resultAsDecimal(r, 3), resultAsDecimal(r, 4)), data);
    }


    public Page<StudentSupportServiceDto> supportServices(Student student, Pageable pageable, boolean showPrivate) {
        HashMap<String, Object> params = new HashMap<>();
        return JpaQueryUtil.pagingResult(supportServicesQB(student, params, pageable, showPrivate),
                "id, entry_date, title, \"content\", validity_code, is_public, ois_file_id, submitter, artificial, ending_id, ehis", params, em, pageable)
                .map(r -> new StudentSupportServiceDto(r, EntityUtil.getOptionalOne(OisFile.class, resultAsLong(r, 6), em)));
    }

    public List<StudentSupportServiceDto> supportServicesList(Student student, boolean showPrivate, LocalDate from, LocalDate thru) {
        HashMap<String, Object> params = new HashMap<>();
        JpaNativeQueryBuilder qb = supportServicesQB(student, params, null, showPrivate);
        qb.optionalCriteria("entry_date >= :from", "from", from);
        qb.optionalCriteria("entry_date <= :thru", "thru", thru);
        qb.sort("entry_date desc");
        List<?> results = qb.select("id, entry_date, title, \"content\", validity_code, is_public, ois_file_id, submitter, artificial, ending_id, ehis", em, params).getResultList();
        return StreamUtil.toMappedList(r -> new StudentSupportServiceDto(r, EntityUtil.getOptionalOne(OisFile.class, resultAsLong(r, 6), em)), results);
    }
    
    private static JpaNativeQueryBuilder studentSupportServices(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_support_service sss");
        qb.requiredCriteria("sss.student_id = :studentId", "studentId", student.getId());
        return qb;
    }
    
    private static JpaNativeQueryBuilder directiveSupportServices(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "
                + "join directive d on d.id = ds.directive_id "
                + "join application a on a.id = ds.application_id " // Application should be for every ds.
                + "join application_support_service ass on ass.application_id = a.id " // If there is a decision and it is TRUE then it HAS TO have at least 1 service.
                + "join (select c.id as c_id, concat ( c.name_et, ' - (', "
                + "coalesce(string_agg( p.firstname || ' ' || p.lastname, ', ' order by p.firstname, p.lastname ), cm.member_name), ')' ) as submitter "
                + "from committee c join committee_member cm on c.id = cm.committee_id and cm.is_chairman "
                + "left join person p on p.id = cm.person_id group by c.id, cm.member_name) as sub on sub.c_id = a.committee_id "
                + "left join directive_student ds2 on ds.id = ds2.directive_student_id and ds2.canceled = false "
                + "left join directive d2 on d2.id = ds2.directive_id and d2.type_code = :lopDirectiveType and d2.status_code = :lopDirectiveStatus");
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", student.getId());
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_TUGI.name());
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.filter("ds.canceled = false");
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_TUGILOPP.name());
        qb.parameter("lopDirectiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.groupBy("d.id, ds.id, d.confirm_date, sub.submitter, d2.id, coalesce (ds2.start_date, ds.end_date)");
        return qb;
    }
    
    private static JpaNativeQueryBuilder supportServicesQB(Student student, Map<String, Object> parameters, Pageable pageable, boolean showPrivate) {
        JpaNativeQueryBuilder qb = studentSupportServices(student);
        String supportServicesQuery = qb.querySql("sss.id as id, sss.entry_date as entry_date, sss.name_et as title,"
                + "sss.\"content\" as \"content\", sss.validity_code as validity_code, sss.is_public as is_public,"
                + "sss.ois_file_id as ois_file_id, sss.changed_by as submitter, false as artificial, null as ending_id, sss.is_ehis as ehis", false);
        parameters.putAll(qb.queryParameters());

        qb = directiveSupportServices(student); 
        String directiveServicesQuery = qb.querySql("d.id as id, d.confirm_date as entry_date, null as title, "
                + "string_agg( ass.support_service_code, ';' order by ass.support_service_code) as \"content\", "
                + "case when coalesce ( ds2.start_date, ds.end_date) >= current_date then 'TUGIKEHTIV_K' else 'TUGIKEHTIV_L' end as validity_code, "
                + "false as is_public, null as ois_file_id, sub.submitter as submitter, true as artificial, d2.id as ending_id, false as ehis", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + supportServicesQuery + " union all " + directiveServicesQuery + ") as support_services");
        if (pageable != null) {
            qb.sort(pageable);
        }
        if (!showPrivate) {
            qb.filter("is_public is true");
        }
        return qb;
    }

    /**
     * Used as a job to set guest student status to finished
     * when student study_end is in the past
     */
    public void endGuestStudent() {
        List<Student> students = em.createQuery("select s from Student s "
                + "where s.type.code = ?1 "
                + "and s.status.code != ?2 "
                + "and s.studyEnd < ?3", Student.class)
                // Guest
                .setParameter(1, StudentType.OPPUR_K.name())
                .setParameter(2, StudentStatus.OPPURSTAATUS_L.name())
                .setParameter(3, LocalDate.now())
                .getResultList();
        for (Student student : students) {
            student.setStatus(EntityUtil.getOptionalOne(StudentStatus.OPPURSTAATUS_L.name(), em));
            userService.disableUser(student, LocalDate.now().minusDays(1));
            saveWithHistory(student);
        }
    }
    
    /**
     * Students should have directives
     * Use only Kylalis type students to find directive with this method
     * @param student
     * @return
     */
    public Boolean directiveStudyLevel(Student student) {
        List<?> directiveStudyLevels = em.createNativeQuery("select d.is_higher from directive d "
                + "join directive_student ds on ds.directive_id = d.id and ds.student_id = ?1 "
                + "where d.type_code = ?2")
            .setParameter(1, EntityUtil.getId(student))
            .setParameter(2, DirectiveType.KASKKIRI_KYLALIS.name())
            .setMaxResults(1).getResultList();
        return JpaQueryUtil.resultAsBoolean(directiveStudyLevels.get(0), 0);
    }

    public Set<Long> cumLaudes(Set<Long> studentIds, boolean isHigher) {
        if (isHigher) {
            return higherCumLaudes(studentIds);
        }
        return vocationalCumLaudes(studentIds);
    }

    private Set<Long> higherCumLaudes(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select scc.student_id from student_curriculum_completion scc"
                + " join protocol_student ps on ps.student_id = scc.student_id"
                + " join protocol p on p.id = ps.protocol_id"
                + " where scc.student_id in ?1 and scc.average_mark >= 4.6 and p.status_code = ?2"
				+ " and (p.is_final = true and ps.grade_code = ?3)"
                //+ " and ((p.is_final = true and p.is_final_thesis = false and ps.grade_code = ?3)"
                //+ " or exists(select 1 from protocol_student_occupation pso where pso.protocol_student_id = ps.id))"
                + " and exists(select 1 from student s join curriculum_version cv on s.curriculum_version_id = cv.id"
                + " join curriculum c on cv.curriculum_id = c.id and c.is_higher = true"
                + " where s.id = scc.student_id)")
                .setParameter(1, studentIds)
                .setParameter(2, ProtocolStatus.PROTOKOLL_STAATUS_K.name())
                .setParameter(3, HigherAssessment.KORGHINDAMINE_5.name())
                .getResultList();
        return data.stream().map(r -> resultAsLong(r, 0)).collect(Collectors.toSet());
    }

    private Set<Long> vocationalCumLaudes(Set<Long> studentIds) {
        Set<Long> cumLaudeStudents = new HashSet<>();

        List<?> data = em.createNativeQuery("select scc.student_id, exists(select 1 from protocol_student_occupation pso where pso.protocol_student_id = ps.id)"
                + " from student_curriculum_completion scc"
                + " join protocol_student ps on ps.student_id = scc.student_id"
                + " join protocol p on p.id = ps.protocol_id"
                + " where scc.student_id in ?1 and scc.average_mark >= 4.6 and p.status_code = ?2"
                + " and ((p.is_final = true and p.is_final_thesis = false and ps.grade_code = ?3)"
                + " or exists(select 1 from protocol_student_occupation pso where pso.protocol_student_id = ps.id))"
                + " and exists(select 1 from student s join curriculum_version cv on s.curriculum_version_id = cv.id"
                + " join curriculum c on cv.curriculum_id = c.id"
                + " join classifier_connect cc on c.orig_study_level_code = cc.classifier_code and cc.connect_classifier_code = ?4"
                + " where s.id = scc.student_id)")
                .setParameter(1, studentIds)
                .setParameter(2, ProtocolStatus.PROTOKOLL_STAATUS_K.name())
                .setParameter(3, OccupationalGrade.KUTSEHINDAMINE_5.name())
                .setParameter(4, FormType.LOPUBLANKETT_KK.name())
                .getResultList();

        Map<Long, Boolean> studentsMeetingCriteria = StreamUtil.toMap(r -> resultAsLong(r, 0),
                r -> resultAsBoolean(r, 1), data);
        if (!studentsMeetingCriteria.isEmpty()) {
            Set<Long> extraChecksNeeded = studentsMeetingCriteria.entrySet().stream()
                    .filter(s -> Boolean.TRUE.equals(s.getValue()))
                    .map(Map.Entry::getKey).collect(Collectors.toSet());

            cumLaudeStudents.addAll(studentsMeetingCriteria.keySet().stream()
                    .filter(r -> !extraChecksNeeded.contains(r))
                    .collect(Collectors.toSet()));
            if (!extraChecksNeeded.isEmpty()) {
                cumLaudeStudents.addAll(studentMatchingExtraChecks(extraChecksNeeded));
            }
        }
        return cumLaudeStudents;
    }

    private Set<Long> studentMatchingExtraChecks(Set<Long> studentIds) {
        Set<Long> suitableStudents = new HashSet<>();

        Set<Long> unsuitableResults = studentsWithUnsuitableResults(studentIds);
        Map<Long, StudentVocationalStudyProgrammeDto> studyProgrammes = studyProgrammesWithEarnedCredits(studentIds, true);
        for (Long studentId : studentIds) {
            if (unsuitableResults.contains(studentId)) {
                continue;
            }

            StudentVocationalStudyProgrammeDto studentStudyProgramme = studyProgrammes.get(studentId);
            if (studentStudyProgramme == null) {
                continue;
            }

            // at least 50% of general studies graded by distinctive grades
            BigDecimal studentGeneralCredits = studentStudyProgramme.getEarnedGeneralStudies();
            BigDecimal programmeGeneralCredits = studentStudyProgramme.getGeneralStudies();
            if (BigDecimal.ZERO.compareTo(programmeGeneralCredits) != 0
                    && studentGeneralCredits.divide(programmeGeneralCredits, 3, RoundingMode.DOWN)
                    .compareTo(DISTINCTIVE_GRADES_CRITERIA) < 0) {
                continue;
            }

            // at least 50% of core + free choice studies graded by distinctive grades
            BigDecimal programmeCoreAndFreeCredits = studentStudyProgramme.getCoreStudies()
                    .add(studentStudyProgramme.getFreeChoice());
            BigDecimal studentCoreAndFreeCredits = studentStudyProgramme.getEarnedCoreStudies()
                    .add(studentStudyProgramme.getEarnedFreeChoice());
            if (BigDecimal.ZERO.compareTo(programmeCoreAndFreeCredits) != 0
                    && studentCoreAndFreeCredits.divide(programmeCoreAndFreeCredits, 3, RoundingMode.DOWN)
                    .compareTo(DISTINCTIVE_GRADES_CRITERIA) < 0) {
                continue;
            }
            suitableStudents.add(studentId);
        }
        return suitableStudents;
    }

    private Set<Long> studentsWithUnsuitableResults(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select svr.student_id from student_vocational_result svr"
                + " where svr.student_id in ?1 and svr.grade_code = ?2")
                .setParameter(1, studentIds)
                .setParameter(2, OccupationalGrade.KUTSEHINDAMINE_3.name())
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    public Map<Long, StudentVocationalStudyProgrammeDto> studyProgrammesWithEarnedCredits(Set<Long> studentIds,
            boolean onlyDistinctiveGrades) {
        Map<Long, StudentVocationalStudyProgrammeDto> studyProgrammes = studentStudyProgrammes(studentIds);

        Map<Long, BigDecimal> generalStudiesCredits = studentModuleTypeCredits(studentIds,
                EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_Y), onlyDistinctiveGrades, true);
        Map<Long, BigDecimal> coreStudiesCredits = studentModuleTypeCredits(studentIds,
                EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_P), onlyDistinctiveGrades, true);
        Map<Long, BigDecimal> freeChoiceCredits = studentModuleTypeCredits(studentIds,
                EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_Y, CurriculumModuleType.KUTSEMOODUL_P,
                        CurriculumModuleType.KUTSEMOODUL_L), onlyDistinctiveGrades, false);

        for (Map.Entry<Long, StudentVocationalStudyProgrammeDto> entry : studyProgrammes.entrySet()) {
            StudentVocationalStudyProgrammeDto dto = entry.getValue();
            dto.setEarnedGeneralStudies(generalStudiesCredits.get(entry.getKey()));
            dto.setEarnedCoreStudies(coreStudiesCredits.get(entry.getKey()));
            dto.setEarnedFreeChoice(freeChoiceCredits.get(entry.getKey()));
        }
        return studyProgrammes;
    }

    private Map<Long, BigDecimal> studentModuleTypeCredits(Set<Long> studentIds, List<String> moduleTypes,
            boolean onlyDistinctiveGrades, boolean ofModuleType) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr"
                + " join curriculum_version_omodule cmm on (svr.arr_modules is null"
                + " and svr.curriculum_version_omodule_id = cmm.id or cmm.id=any(svr.arr_modules))");
        qb.requiredCriteria("svr.student_id in (:studentIds)", "studentIds", studentIds);
        List<String> grades = OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE;
        qb.requiredCriteria("svr.grade_code in (:positiveGrades)", "positiveGrades", grades);
        if (onlyDistinctiveGrades) {
            qb.requiredCriteria("svr.grade_code != :notDistinctive", "notDistinctive", OccupationalGrade.KUTSEHINDAMINE_A);
        }
        qb.filter("cmm.curriculum_module_id in (select cm.id as m_id from curriculum_version cv"
                + " join curriculum_version_omodule cvo on cv.id=cvo.curriculum_version_id"
                + " join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id"
                + " and coalesce(cm.is_additional,false)=false"
                + " and cm.module_code" + (ofModuleType ? " " : " not ") + "in (:moduleTypes)"
                + " and coalesce(cm.is_additional,false)=false"
                + " join student ss on cv.id=ss.curriculum_version_id"
                + " left join student_group sg on ss.student_group_id=sg.id"
                + " where ss.id in (:studentIds) and (sg.id is null or coalesce(sg.speciality_code,'x')='x' or"
                + " coalesce(sg.speciality_code,'x')!='x' and exists("
                + " select 1 from curriculum_module_occupation cmo"
                + " left join classifier_connect ccc on cmo.occupation_code=ccc.connect_classifier_code"
                + " where cmo.curriculum_module_id=cm.id and (cmo.occupation_code=sg.speciality_code or ccc.classifier_code=sg.speciality_code))))"
                + " group by svr.student_id");
        qb.parameter("moduleTypes", moduleTypes);

        List<?> data = qb.select("svr.student_id, sum(svr.credits)", em).getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsDecimal(r, 1), data);
    }


    public Page<ApelApplicationSearchDto> apelApplications(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_application aa "
                + "join classifier c on aa.status_code = c.code").sort(pageable);
        qb.requiredCriteria("aa.student_id = :studentId", "studentId", EntityUtil.getId(student));

        return JpaQueryUtil.pagingResult(qb,
                "aa.id, aa.inserted, aa.confirmed, aa.status_code",
                em, pageable).map(r -> {
            ApelApplicationSearchDto dto = new ApelApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setInserted(resultAsLocalDate(r, 1));
            dto.setConfirmed(resultAsLocalDate(r, 2));
            dto.setStatus(resultAsString(r, 3));
            dto.setCanView(Boolean.valueOf(ApelApplicationUtil.canView(user, em.getReference(ApelApplication.class, dto.getId()))));
            return dto;
        });
    }
    
    public boolean isHigher(Student student) {
        if (student.getCurriculumVersion() == null) {
            SchoolType schoolType = schoolService.schoolType(EntityUtil.getId(student.getSchool()));
            boolean hybrid = schoolType.isHigher() && schoolType.isVocational();
            if (hybrid) return getIsDirectiveHigher(student);
            return !schoolType.isVocational();
        }
        return CurriculumUtil.isHigher(student.getCurriculumVersion().getCurriculum());
    }
    
    public boolean isVocational(Student student) {
        return !isHigher(student);
    }
    
    public boolean getIsDirectiveHigher(Student student) {
        // should be only one directive per student
        Optional<Directive> directive = Optional.empty();
        if (StudentUtil.isGuestStudent(student)) {
            // get the most recent guest student creation directive
            directive = latestConfirmedDirective(student, DirectiveType.KASKKIRI_KYLALIS);
        } else if (StudentUtil.isExternal(student)) {
         // get the most recent external student creation directive
            directive = latestConfirmedDirective(student, DirectiveType.KASKKIRI_EKSTERN);
        }
        if (directive.isPresent()) {
            Boolean higher = directive.get().getIsHigher();
            if (higher == null) return false;
            return higher.booleanValue();
        }
        return false;
    }
    
    private Optional<Directive> latestConfirmedDirective(Student student, DirectiveType type) {
        Optional<Directive> directive = Optional.empty();
        List<Directive> directives = em.createQuery("select d from Directive d left join d.students students "
                + "where students.student.id = ?1 "
                + "and students.canceled != true "
                + "and d.type.code = ?2 "
                + "and d.status.code = ?3 "
                + "order by d.confirmDate desc", Directive.class)
        .setParameter(1, student.getId())
        .setParameter(2, type.name())
        .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
        .getResultList();
        if (directives != null && !directives.isEmpty()) {
            directive = Optional.ofNullable(directives.get(0));
        }
        return directive;
    }


    public StudentViewDto saveAddInfo(HoisUserDetails user, Student student, StudentAddInfoForm form) {
        student.setAddInfo(form.getAddInfo());
        return getStudentView(user ,student, null);
    }

}
