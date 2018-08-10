package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.domain.student.StudentHigherResult;
import ee.hitsa.ois.domain.student.StudentHigherResultModule;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.domain.student.StudentVocationalResult;
import ee.hitsa.ois.domain.student.StudentVocationalResultOmodule;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentModuleListChangeForm;
import ee.hitsa.ois.web.commandobject.student.StudentResultModuleChangeForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceSearchDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentForeignstudyDto;
import ee.hitsa.ois.web.dto.student.StudentModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalConnectedEntity;
import ee.hitsa.ois.web.dto.student.StudentVocationalModuleDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultByTimeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;

@Transactional
@Service
public class StudentService {

    private static final String STUDENT_LIST_SELECT = "s.id, person.firstname, person.lastname, person.idcode, "+
            "curriculum_version.id curriculum_version_id, curriculum_version.code curriculum_version_code, curriculum.id curriculum_id, curriculum.name_et, curriculum.name_en, " +
            "student_group.id student_group_id, student_group.code student_group_code, s.study_form_code, s.status_code, s.person_id";
    private static final String STUDENT_LIST_FROM = "from student s inner join person person on s.person_id=person.id "+
            "inner join curriculum_version curriculum_version on s.curriculum_version_id=curriculum_version.id "+
            "inner join curriculum curriculum on curriculum_version.curriculum_id=curriculum.id "+
            "inner join classifier status on s.status_code=status.code "+
            "left outer join student_group student_group on s.student_group_id=student_group.id "+
            "left outer join classifier study_form on s.study_form_code=study_form.code";
    
    private static final List<String> JOURNAL_RESULT_ENTRY_TYPES = EnumUtil.toNameList(JournalEntryType.SISSEKANNE_L,
            JournalEntryType.SISSEKANNE_O, JournalEntryType.SISSEKANNE_R, JournalEntryType.SISSEKANNE_H);

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;

    /**
     * Search students
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSearchDto> search(HoisUserDetails user, StudentSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_LIST_FROM).sort(pageable);

        if (user.isStudent()) {
            // student can search active students only
            qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents", StudentStatus.STUDENT_STATUS_ACTIVE);
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

        return JpaQueryUtil.pagingResult(qb, STUDENT_LIST_SELECT, em, pageable).map(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
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
            return dto;
        });
    }

    public Student save(HoisUserDetails user, Student student, StudentForm form) {
        Person p = EntityUtil.bindToEntity(form.getPerson(), student.getPerson(), classifierRepository);
        PersonUtil.conditionalClean(p);
        EntityUtil.save(p, em);
        
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

        if (!UserUtil.isSchoolAdmin(user, student.getSchool())) {
            return student;
        }

        EntityUtil.bindToEntity(form, student, classifierRepository, "person");
        student.setEmail(form.getSchoolEmail());
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

        Page<StudentAbsenceDto> data = JpaQueryUtil.pagingResult(qb, "sa.id, sa.is_accepted, sa.is_rejected, sa.valid_from, sa.valid_thru, sa.cause, sa.version", em, pageable).map(r -> {
            StudentAbsenceDto dto = new StudentAbsenceDto();
            dto.setId(resultAsLong(r, 0));
            dto.setIsAccepted(resultAsBoolean(r, 1));
            dto.setIsRejected(resultAsBoolean(r, 2));
            dto.setValidFrom(resultAsLocalDate(r, 3));
            dto.setValidThru(resultAsLocalDate(r, 4));
            dto.setCause(resultAsString(r, 5));
            dto.setVersion(resultAsLong(r, 6));
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
    public Page<StudentApplicationDto> applications(Long studentId, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from application a join classifier type on type.code = a.type_code "+
                "join classifier status on status.code = a.status_code").sort(pageable);

        qb.requiredCriteria("a.student_id = :studentId", "studentId", studentId);
        return JpaQueryUtil.pagingResult(qb, "a.id, a.type_code, a.inserted, a.status_code, case when a.status_code = 'AVALDUS_STAATUS_KINNITATUD' then a.changed else null end, a.submitted, a.reject_reason", em, pageable).map(r -> {
            StudentApplicationDto dto = new StudentApplicationDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setInserted(resultAsLocalDateTime(r, 2));
            dto.setStatus(resultAsString(r, 3));
            dto.setConfirmDate(resultAsLocalDateTime(r, 4));
            dto.setSubmitted(resultAsLocalDateTime(r, 5));
            dto.setRejectReason(resultAsString(r, 6));
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
    public Page<StudentDirectiveDto> directives(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d join classifier type on type.code = d.type_code").sort(pageable);

        String showCanceled = "";
        if(!UserUtil.isSchoolAdmin(user, student.getSchool())) {
            // don't show these directives which are cancelled for given student
            showCanceled = " and ds.canceled = false";
        }
        qb.requiredCriteria(String.format("d.id in (select ds.directive_id from directive_student ds where ds.student_id = :studentId%s)", showCanceled), "studentId", EntityUtil.getId(student));

        qb.requiredCriteria("d.type_code <> :directiveType", "directiveType", DirectiveType.KASKKIRI_TYHIST);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);

        return JpaQueryUtil.pagingResult(qb, "d.id, d.headline, d.type_code, d.directive_nr, d.confirm_date, d.inserted_by", em, pageable).map(r -> {
            StudentDirectiveDto dto = new StudentDirectiveDto();
            dto.setId(resultAsLong(r, 0));
            dto.setHeadline(resultAsString(r, 1));
            dto.setType(resultAsString(r, 2));
            dto.setDirectiveNr(resultAsString(r, 3));
            dto.setConfirmDate(resultAsLocalDate(r, 4));
            dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 5)));
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
                "join classifier programme on ds.abroad_programme_code = programme.code " +
                "left join classifier ehis_school on ds.ehis_school_code = ehis_school.code " +
                "left join study_period sp on ds.study_period_start_id = sp.id " +
                "left join study_period ep on ds.study_period_end_id = ep.id").sort(pageable);
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.filter("ds.canceled = false");

        return JpaQueryUtil.pagingResult(qb, "case when ds.is_abroad then abroad_school else ehis_school.name_et end, ds.country_code, " +
                "case when ds.is_period then sp.start_date else ds.start_date end, case when ds.is_period then ep.end_date else ds.end_date end, " +
                "ds.abroad_purpose_code, ds.abroad_programme_code", em, pageable).map(r -> {
            StudentForeignstudyDto dto = new StudentForeignstudyDto();
            dto.setSchool(resultAsString(r, 0));
            dto.setCountry(resultAsString(r, 1));
            dto.setStartDate(resultAsLocalDate(r, 2));
            dto.setEndDate(resultAsLocalDate(r, 3));
            dto.setAbroadPurpose(resultAsString(r, 4));
            dto.setAbroadProgramme(resultAsString(r, 5));
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
                + "inner join curriculum_version_hmodule cvh on cvh.id = cvhms.curriculum_version_hmodule_id");
        qb.requiredCriteria("cvh.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em).getResultList();
        return StreamUtil.toMappedList(r ->
            new AutocompleteResult(resultAsLong(r, 0),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 1), resultAsDecimal(r, 4)),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 2), resultAsDecimal(r, 4))), data);
    }

    /**
     * Student data for student view form, main data tab
     *
     * @param user
     * @param student
     * @return
     */
    public StudentViewDto getStudentView(HoisUserDetails user, Student student) {
        StudentViewDto dto = StudentViewDto.of(student);
        // rights for editing student data, adding representative and displaying sensitive fields
        dto.setUserCanEditStudent(Boolean.valueOf(UserUtil.canEditStudent(user, student)));
        dto.setUserCanAddRepresentative(Boolean.valueOf(UserUtil.canAddStudentRepresentative(user, student)));
        dto.setUserIsSchoolAdmin(Boolean.valueOf(UserUtil.isSchoolAdmin(user, student.getSchool())));
        dto.setUserIsStudentGroupTeacher(Boolean.valueOf(UserUtil.isStudentGroupTeacher(user, student)));
        if(!(Boolean.TRUE.equals(dto.getUserIsSchoolAdmin()) || UserUtil.isSame(user, student) || UserUtil.isStudentRepresentative(user, student))) {
            dto.setSpecialNeed(null);
            dto.setIsRepresentativeMandatory(null);
        }

        if (Boolean.TRUE.equals(dto.getIsVocational())) {
            StudentCurriculumCompletion completion = getStudentCurriculumCompletion(student);
            if (completion != null) {
                dto.setCredits(completion.getCredits());
                dto.setKkh(completion.getAverageMark());
            } else {
                dto.setCredits(BigDecimal.ZERO);
                dto.setKkh(BigDecimal.ZERO);
            }
        }
        dto.setOccupationCertificates(occupationCertificates(student));
        
        OisFile logo = student.getPhoto();
        if(logo != null) {
            dto.setPhoto(EntityUtil.bindToDto(logo, new OisFileCommand()));
        }
        return dto;
    }
    
    public StudentCurriculumCompletion getStudentCurriculumCompletion(Student student) {
        List<StudentCurriculumCompletion> result = em.createQuery("select scc"
                + " from StudentCurriculumCompletion scc where scc.student = ?1", 
                StudentCurriculumCompletion.class)
                .setParameter(1, student)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    public Collection<StudentVocationalResultByTimeDto> vocationalResultsByTimeResults(Student student) {
        String journalResults = 
                "select false as module, coalesce(je.entry_date,jes.grade_inserted) as kp, j.id, j.name_et, " + 
                    "(select string_agg(tp.firstname||' '||tp.lastname,', ') " + 
                        "from journal_teacher jt " + 
                        "join teacher t on t.id = jt.teacher_id " + 
                        "join person tp on tp.id = T.person_id " + 
                    "where jt.journal_id=j.id) as opetaja, " + 
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
                   "jes.grade_code, sy.year_code, sy.start_date, " +
                   "false as informal, false as formal " +
                "from journal_student js " +
                "join journal j on j.id = js.journal_id " +
                "join journal_entry je on je.journal_id = js.journal_id " +
                "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id=js.id " + 
                "join student ss on ss.id=js.student_id " +
                "join study_year sy on sy.id = j.study_year_id " +
                "where js.student_id =:studentId and je.entry_type_code in (:entryTypeCodes)";
        
        String protocolResults = 
                "select true as module, ps.grade_date,pp.id, " + 
                    "cm.name_et||' - '||mcl.name_et ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "tp.firstname ||' '||tp.lastname as opetaja, null," +
                    "cm.name_et||' - '||mcl.name_et ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et) ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end ," +
                    "null, null, ps.grade_code, sy.year_code, sy.start_date, false as informal, false as formal " + 
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
                    "null, null, aai.grade_code, sy.year_code, sy.start_date, true as informal, false as formal " + 
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
                "    cm.name_et || ' - ' || mcl.name_et || case when cv.id != ss.curriculum_version_id then ' (' || cv.code || ')' else '' end, " + 
                "    cm.name_en || ' - ' || mcl.name_en || case when cv.id != ss.curriculum_version_id then ' (' || cv.code || ')' else '' end, " + 
                "    aaf.name_et || ' - ' || a_s.name_et as foreign_theme, " + 
                "    aaf.name_en || ' - ' || a_s.name_en as foreign_theme_en, " + 
                "    aaf.grade_code, sy.year_code, sy.start_date, false as informal, true as formal " +
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
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (" + journalResults + " union all " + protocolResults + " union all " + informalApelResults + 
                " union all " + formalApelResults + ") xx where grade_code is not null");
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("entryTypeCodes", JOURNAL_RESULT_ENTRY_TYPES);
        
        qb.sort("kp desc, my_theme");
        
        List<?> rows = qb.select("*",em).getResultList();
        
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
            dto.setGrade(resultAsString(r, 10));
            dto.setTeachers(resultAsString(r, 4));
            dto.setStudyYear(resultAsString(r, 11));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 12));
            dto.setIsInformal(resultAsBoolean(r, 13));
            dto.setIsFormal(resultAsBoolean(r, 14));
            dto.setIsApel(Boolean.valueOf(dto.getIsInformal().booleanValue() || dto.getIsFormal().booleanValue()));
            
            result.add(dto);
        }
        return result;
    }

    public StudentVocationalResultDto vocationalResults(Student student) {
        StudentVocationalResultDto dto = new StudentVocationalResultDto();
        dto.setResults(studentVocationalResults(student));
        dto.setCurriculumModules(StreamUtil.toMappedList(StudentVocationalModuleDto::of,
                student.getCurriculumVersion().getOccupationModules()));

        Set<Long> curriculaModuleIds = StreamUtil.toMappedSet(StudentVocationalModuleDto::getId, dto.getCurriculumModules());
        Set<Long> extraCurriculaModuleIds = StreamUtil.toMappedSet(it -> it.getModule().getId(), 
                StreamUtil.toFilteredList(it -> it.getModule().getId() != null, dto.getResults()));
        extraCurriculaModuleIds.removeIf(curriculaModuleIds::contains);
        dto.setExtraCurriculaModules(StreamUtil.toMappedList(StudentVocationalModuleDto::of, curriculumVersionOccupationModuleRepository.findAll(extraCurriculaModuleIds)));
        return dto;
    }
    
    public List<StudentVocationalResultModuleThemeDto> studentVocationalResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        result.addAll(vocationalResultsThemeResults(student));
        result.addAll(vocationalResultsModuleResults(student));
        result.addAll(otherSchoolExtraCurriculaModuleResults(student));
        result.sort(StreamUtil.comparingWithNullsLast(StudentVocationalResultModuleThemeDto::getDate).reversed());
        return result;
    }
    
    private List<StudentVocationalResultModuleThemeDto> vocationalResultsModuleResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        
        String from = "from student_vocational_result svr"
                + " join curriculum_version_omodule cvo on cvo.id = svr.curriculum_version_omodule_id"
                + " join curriculum_version cv on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm on cm.id = cvo.curriculum_module_id"
                + " join classifier mcl on mcl.code = cm.module_code"
                + " left join student_vocational_result_omodule svro on svr.id = svro.student_vocational_result_id"
                + " left join study_year sy on sy.id = svr.study_year_id"
                + " left join apel_application_record aar on svr.apel_application_record_id = aar.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.sort("svr.grade_date");
        
        List<?> rows = qb.select(
                "distinct coalesce(svro.curriculum_version_omodule_id, svr.curriculum_version_omodule_id),"
                + "cv.code, cm.name_et as module_name_et, mcl.name_et classifer_name_et,cm.name_en as module_name_en, mcl.name_en as classifer_name_en, "
                + "cm.credits, svr.apel_application_record_id, aar.is_formal_learning, svr.grade_code, svr.grade_date, "
                + "svr.teachers, sy.year_code, sy.start_date",
                em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setModule(new AutocompleteResult(resultAsLong(r, 0),
                    CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                    CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))));
            dto.setCredits(resultAsDecimal(r, 6));
            
            Long apelApplicationRecordId = resultAsLong(r, 7);
            dto.setIsApelTransfer(apelApplicationRecordId != null ? Boolean.TRUE : Boolean.FALSE);
            dto.setIsFormalLearning(resultAsBoolean(r, 8));
            dto.setGrade(resultAsString(r, 9));
            dto.setDate(resultAsLocalDate(r, 10));
            dto.getTeachers().add(new AutocompleteResult(null, resultAsString(r, 11), resultAsString(r, 11)));
            dto.setStudyYear(resultAsString(r, 12));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 13));
            result.add(dto);
        }
        return result;
    }
    
    private List<StudentVocationalResultModuleThemeDto> otherSchoolExtraCurriculaModuleResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr "
                + "join curriculum_version_omodule cvo on cvo.id = any(svr.arr_modules) "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "left join apel_school a_s on svr.apel_school_id = a_s.id "
                + "left join study_year sy on sy.id = svr.study_year_id");
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("svr.curriculum_version_omodule_id is null");
        qb.sort("svr.grade_date");
        qb.groupBy("svr.arr_modules, svr.module_name_et, svr.module_name_en, svr.credits, a_s.id, "
                + "svr.grade_code, svr.grade_date, svr.teachers, sy.year_code, sy.start_date");
        
        List<?> rows = qb.select("svr.module_name_et, svr.module_name_en, string_agg(cm.name_et || ' - ' || mcl.name_et, ', ') as replaced_modules_et, " + 
                "string_agg(cm.name_en || ' - ' || mcl.name_en, ', ') as replaced_modules_en, svr.credits, a_s.id as school_id, " + 
                "a_s.name_et, a_s.name_en, svr.grade_code, svr.grade_date, svr.teachers, sy.year_code, sy.start_date", em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setModule(new AutocompleteResult(null, 
                    otherSchoolExtraCurriculumModuleName(resultAsString(r, 0), resultAsString(r, 2), resultAsString(r, 6)),
                    otherSchoolExtraCurriculumModuleName(resultAsString(r, 1), resultAsString(r, 3), resultAsString(r, 7))));
            dto.setCredits(resultAsDecimal(r, 4));
            dto.setGrade(resultAsString(r, 8));
            dto.setIsOtherSchool(Boolean.TRUE);
            result.add(dto);
       }
       return result;
    }
    
    private static String otherSchoolExtraCurriculumModuleName(String moduleName, String replacedModules, String school) {
        if (moduleName != null && replacedModules != null && school != null) {
            return moduleName + " -> (" + replacedModules + ") - " + school; 
        }
        return null;
    }

    private Collection<StudentVocationalResultModuleThemeDto> vocationalResultsThemeResults(Student student) {
        String journalResults = "select distinct on (cvot.id, teacher_id) cvot.id cvot_id, cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, jes.grade_code grade_code, jes.grade_inserted grade_inserted, "
                + "tp.id teacher_id, tp.firstname teacher_firstname, tp.lastname teacher_lastname,"
                + "cvo.id cvo_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, cm.credits cm_credits,"
                + "sy.year_code sy_year_code, sy.start_date sy_start_date, false is_apel_transfer "
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
                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id=js.id "
                + "left join journal_teacher jt on jt.journal_id = js.journal_id "
                + "left join teacher t on t.id = jt.teacher_id "
                + "left join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and "
                + "je.entry_type_code = :entryTypeCode "
                + "order by cvot.id desc, teacher_id, jes.grade_inserted desc)";
        
        String apelResults = "select cvo.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, "
                + "cvot.id cvot_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, aai.grade_code grade_code, aa.confirmed grade_inserted, null as teacher_id, "
                + "null teacher_firstname, null teacher_lastname, sy.year_code sy_year_code, sy.start_date sy_start_date, true is_apel_transfer "
                + "from apel_application aa join apel_application_record aar on aa.id = aar.apel_application_id "
                + "join apel_application_informal_subject_or_module aai on aar.id = aai.apel_application_record_id "
                + "join curriculum_version_omodule_theme cvot on aai.curriculum_version_omodule_theme_id = cvot.id "
                + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
                + "join curriculum_version cv on cvo.curriculum_version_id = cv.id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) "
                + "where aa.student_id = :studentId and aa.status_code = :applicationStatus and aai.transfer = true";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from (" + journalResults + " as journal_results "
                + "union all " + apelResults + " order by grade_inserted desc");

        qb.parameter("entryTypeCode", JournalEntryType.SISSEKANNE_L.name());
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("applicationStatus", ApelApplicationStatus.VOTA_STAATUS_C.name());

        List<?> rows = qb.select("cvo_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en,"
                + "cvot_id, cvot_name_et, cvot_credits, grade_code, grade_inserted, teacher_id, teacher_firstname, teacher_lastname, "
                + "sy_year_code, sy_start_date, is_apel_transfer",
                em).getResultList();

        Map<Long, StudentVocationalResultModuleThemeDto> result = new HashMap<>();
        for (Object r : rows) {
            Long themeId = resultAsLong(r, 6);
            StudentVocationalResultModuleThemeDto dto = result.get(themeId);
            if (dto != null) {
                String teacherName = PersonUtil.fullname(resultAsString(r, 12), resultAsString(r, 13));
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));
            } else {
                dto = new StudentVocationalResultModuleThemeDto();
                dto.setModule(new AutocompleteResult(resultAsLong(r, 0),
                        CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                        CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))));

                dto.setTheme(new AutocompleteResult(themeId, resultAsString(r, 7), resultAsString(r, 7)));
                dto.setCredits(resultAsDecimal(r, 8));
                dto.setGrade(resultAsString(r, 9));
                dto.setDate(resultAsLocalDate(r, 10));
                String teacherName = PersonUtil.fullname(resultAsString(r, 12), resultAsString(r, 13));
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 11), teacherName, teacherName));
                dto.setStudyYear(resultAsString(r, 14));
                dto.setStudyYearStartDate(resultAsLocalDate(r, 15));
                dto.setIsApelTransfer(resultAsBoolean(r, 16));
                result.put(themeId, dto);
            }
        }
        return result.values();
    }

    private List<StudentOccupationCertificateDto> occupationCertificates(Student student) {
        List<StudentOccupationCertificate> data = em.createQuery(
                "select soc from StudentOccupationCertificate soc where soc.student.id = ?1 order by soc.issueDate desc", StudentOccupationCertificate.class)
            .setParameter(1, EntityUtil.getId(student))
            .getResultList();
        return StreamUtil.toMappedList(StudentOccupationCertificateDto::new, data);
    }
    
    public List<StudentModuleResultDto> vocationalChangeableModules(Long studentId) {
        Query q = em.createNativeQuery("select svr.id, coalesce(svrm.curriculum_version_omodule_id, svr.curriculum_version_omodule_id), "
                + "svr.module_name_et, svr.module_name_en, svr.credits, svr.grade, svr.grade_date from student_vocational_result svr "
                + "left join student_vocational_result_omodule svrm on svrm.student_vocational_result_id = svr.id "
                + "where student_id = ?1");
        q.setParameter(1, studentId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(r -> new StudentModuleResultDto(resultAsLong(r, 0), resultAsLong(r, 1),
                resultAsString(r, 2), resultAsString(r, 3), resultAsDecimal(r, 4), resultAsString(r, 5), resultAsLocalDate(r, 6)), data);
    }
    
    public List<AutocompleteResult> vocationalCurriculumModulesForSelection(Long curriculumVersionId) {
        Query q = em.createNativeQuery("select cvo.id, cm.name_et, cm.name_en from curriculum_version_omodule cvo " + 
                "inner join curriculum_module cm on cvo.curriculum_module_id = cm.id " + 
                "where cvo.curriculum_version_id = ?1");
        q.setParameter(1, curriculumVersionId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), data);
    }
    
    public void changeVocationalCurriculumVersionModules(Student student, StudentModuleListChangeForm form) {
        StudentVocationalResult result = em.getReference(StudentVocationalResult.class, form.getModules().get(0).getId());
        if (!student.equals(result.getStudent())) {
            throw new AssertionFailedException("Student mismatch");
        }
        
        List<Long> vocationalResultIds = form.getModules().stream().map(m -> m.getId()).collect(Collectors.toList());
        List<StudentVocationalResultOmodule> data = em.createQuery(
                "select svro from StudentVocationalResultOmodule svro where svro.studentVocationalResult.id in (?1)",
                StudentVocationalResultOmodule.class).setParameter(1, vocationalResultIds).getResultList();
        
        Map<Long, StudentVocationalResultOmodule> vocationalResultModules = StreamUtil.toMap(d -> d.getStudentVocationalResult().getId(), data);

        for (StudentResultModuleChangeForm vocationalModule : form.getModules()) {
            if (vocationalModule.getCurriculumVersionModuleId() != null &&  !vocationalModule.getCurriculumVersionModuleId().equals(vocationalModule.getOldCurriculumVersionModuleId())) {
                StudentVocationalResultOmodule module = vocationalResultModules.get(vocationalModule.getId());
                if (module != null) {
                    module.setCurriculumVersionOmodule(em.getReference(CurriculumVersionOccupationModule.class, vocationalModule.getCurriculumVersionModuleId()));
                    em.merge(module);
                } else {
                    module = EntityUtil.bindToEntity(form, new StudentVocationalResultOmodule());
                    module.setStudentVocationalResult(em.getReference(StudentVocationalResult.class, vocationalModule.getId()));
                    module.setCurriculumVersionOmodule(em.getReference(CurriculumVersionOccupationModule.class, vocationalModule.getCurriculumVersionModuleId()));
                    em.persist(module);
                }
            }
        }
    }
    
    public List<StudentModuleResultDto> higherChangeableModules(Long studentId) {
        Query q = em.createNativeQuery("select shr.id, coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id), "
                + "shr.subject_name_et, shr.subject_name_en, shr.credits, shr.grade, shr.grade_date from student_higher_result shr "
                + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id "
                + "where student_id = ?1");
        q.setParameter(1, studentId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(r -> new StudentModuleResultDto(resultAsLong(r, 0), resultAsLong(r, 1),
                resultAsString(r, 2), resultAsString(r, 3), resultAsDecimal(r, 4), resultAsString(r, 5), resultAsLocalDate(r, 6)), data);
    }
    
    public List<AutocompleteResult> higherCurriculumModulesForSelection(Long curriculumVersionId) {
        Query q = em.createNativeQuery("select cvh.id, cvh.name_et, cvh.name_en from curriculum_version_hmodule cvh " + 
                "where cvh.curriculum_version_id = ?1");
        q.setParameter(1, curriculumVersionId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), data);
    }
    
    public void changeHigherCurriculumVersionModules(Student student, StudentModuleListChangeForm form) {
        StudentHigherResult result = em.getReference(StudentHigherResult.class, form.getModules().get(0).getId());
        if (!student.equals(result.getStudent())) {
            throw new AssertionFailedException("Student mismatch");
        }
        
        List<Long> higherResultIds = form.getModules().stream().map(m -> m.getId()).collect(Collectors.toList());
        List<StudentHigherResultModule> data = em.createQuery(
                "select shrm from StudentHigherResultModule shrm where shrm.studentHigherResult.id in (?1)",
                StudentHigherResultModule.class).setParameter(1, higherResultIds).getResultList();
        
        Map<Long, StudentHigherResultModule> higherResultModules = StreamUtil.toMap(d -> d.getStudentHigherResult().getId(), data);

        for (StudentResultModuleChangeForm higherModule : form.getModules()) {
            if (higherModule.getCurriculumVersionModuleId() != null && !higherModule.getCurriculumVersionModuleId().equals(higherModule.getOldCurriculumVersionModuleId())) {
                StudentHigherResultModule module = higherResultModules.get(higherModule.getId());
                if (module != null) {
                    module.setCurriculumVersionHmodule(em.getReference(CurriculumVersionHigherModule.class, higherModule.getCurriculumVersionModuleId()));
                    em.merge(module);
                } else {
                    module = EntityUtil.bindToEntity(form, new StudentHigherResultModule());
                    module.setStudentHigherResult(em.getReference(StudentHigherResult.class, higherModule.getId()));
                    module.setCurriculumVersionHmodule(em.getReference(CurriculumVersionHigherModule.class, higherModule.getCurriculumVersionModuleId()));
                    em.persist(module);
                }
            }
        }
    }
    
    public List<StudentVocationalConnectedEntity> vocationalConnectedEntities(Long studentId) {
        Query q = em.createNativeQuery("select jj.id, 'journal' as type, jj.name_et as name_et, jj.name_et as name_en, sy.end_date, sy.year_code, null as protocol_nr from journal jj "
                + "inner join journal_student js on jj.id=js.journal_id " 
                + "inner join study_year sy on jj.study_year_id = sy.id " 
                + "where js.student_id = ?1 " 
                + "union " 
                + "select ps.protocol_id, 'protocol', cm.name_et, cm.name_et, sy.end_date, sy.year_code, p.protocol_nr from protocol_student ps "
                + "inner join protocol p on ps.protocol_id = p.id "
                + "inner join protocol_vdata pvd on ps.protocol_id = pvd.protocol_id "  
                + "inner join curriculum_version_omodule cvo on pvd.curriculum_version_omodule_id = cvo.id " 
                + "inner join curriculum_module cm on cvo.curriculum_module_id = cm.id " 
                + "inner join study_year sy on pvd.study_year_id = sy.id " 
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
}
