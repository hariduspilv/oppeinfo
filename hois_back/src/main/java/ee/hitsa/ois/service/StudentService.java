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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceSearchDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalModuleDto;
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
    public Page<StudentSearchDto> search(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_LIST_FROM).sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
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
            dto.setIdcode(resultAsString(r, 3));
            String curriculumVersionCode = resultAsString(r, 5);
            dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 7)),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 8))));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 9), resultAsString(r, 10), resultAsString(r, 10)));
            dto.setStudyForm(resultAsString(r, 11));
            dto.setStatus(resultAsString(r, 12));
            dto.setPersonId(resultAsLong(r, 13));
            return dto;
        });
    }

    public Student save(HoisUserDetails user, Student student, StudentForm form) {
        Person p = EntityUtil.bindToEntity(form.getPerson(), student.getPerson(), classifierRepository);
        EntityUtil.save(p, em);

        if(!UserUtil.isSchoolAdmin(user, student.getSchool())) {
            return student;
        }

        EntityUtil.bindToEntity(form, student, classifierRepository, "person");
        student.setEmail(form.getSchoolEmail());
        return saveWithHistory(student);
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

        Page<StudentAbsenceDto> data = JpaQueryUtil.pagingResult(qb, "sa.id, sa.is_accepted, sa.valid_from, sa.valid_thru, sa.cause, sa.version", em, pageable).map(r -> {
            StudentAbsenceDto dto = new StudentAbsenceDto();
            dto.setId(resultAsLong(r, 0));
            dto.setIsAccepted(resultAsBoolean(r, 1));
            dto.setValidFrom(resultAsLocalDate(r, 2));
            dto.setValidThru(resultAsLocalDate(r, 3));
            dto.setCause(resultAsString(r, 4));
            dto.setVersion(resultAsLong(r, 5));
            absence.setIsAccepted(dto.getIsAccepted()); // for validation
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
        if(!(Boolean.TRUE.equals(dto.getUserIsSchoolAdmin()) || UserUtil.isSame(user, student) || UserUtil.isStudentRepresentative(user, student))) {
            dto.setSpecialNeed(null);
            dto.setIsRepresentativeMandatory(null);
        }

        if (Boolean.TRUE.equals(dto.getIsVocational())) {
            dto.setCredits(vocationalTotalCreditsOnCurrentCurriculum(student));
            dto.setKkh(vocationalWeightedAverageGrade(student));
            dto.setOccupationCertificates(occupationCertificates(student));
        }
        return dto;
    }
    
    private BigDecimal vocationalTotalCreditsOnCurrentCurriculum(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (select distinct on (cm.id) cm.id, svr.credits from student_vocational_result svr " 
                        + "inner join curriculum_version_omodule cvo on cvo.id = svr.curriculum_version_omodule_id "
                        + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id " 
                        + "where svr.student_id = :studentId and " + "cvo.curriculum_version_id = :curriculumVersionId "
                        + "order by cm.id, svr.id desc) as module_results");

        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        return resultAsDecimal(qb.select("sum(credits)", em).getSingleResult(), 0);
    }

    private BigDecimal vocationalWeightedAverageGrade(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (select CAST(grade.value AS integer) as grade_value, sum(cvoyc.credits) as ekap from student_vocational_result svr "
                        + "inner join classifier grade on grade.code = grade_code "
                        + "inner join curriculum_version_omodule_year_capacity cvoyc on cvoyc.curriculum_version_omodule_id = svr.curriculum_version_omodule_id "
                        + "inner join curriculum_version_omodule cvo on cvo.id = svr.curriculum_version_omodule_id "
                        + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                        + "where svr.student_id = :studentId and cv.id = :curriculumVersionId and svr.grade_code in :positiveValueGrades "
                        + "group by cvoyc.curriculum_version_omodule_id, grade.value) as grade_ekap");

        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));
        qb.parameter("positiveValueGrades", OccupationalGrade.OCCUPATIONAL_VALUE_GRADE_POSITIVE);
//      sum(ekap) is zero in some cases
        Object result = qb.select("sum(grade_value * ekap) as grade_sum, sum(ekap) as credits_sum", em).getSingleResult();
        BigDecimal gradeSum = resultAsDecimal(result, 0);
        BigDecimal creditsSum = resultAsDecimal(result, 1);
        if(gradeSum != null && creditsSum != null && BigDecimal.ZERO.compareTo(creditsSum) != 0) {
            return gradeSum.divide(creditsSum, 3, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    public StudentVocationalResultDto vocationalResults(Student student) {
        StudentVocationalResultDto dto = new StudentVocationalResultDto();
        dto.setResults(studentVocationalResults(student));
        dto.setCurriculumModules(StreamUtil.toMappedList(StudentVocationalModuleDto::of,
                student.getCurriculumVersion().getOccupationModules()));

        Set<Long> curriculaModuleIds = StreamUtil.toMappedSet(StudentVocationalModuleDto::getId, dto.getCurriculumModules());
        Set<Long> extraCurriculaModuleIds = StreamUtil.toMappedSet(it -> it.getModule().getId(), dto.getResults());
        extraCurriculaModuleIds.removeIf(curriculaModuleIds::contains);
        dto.setExtraCurriculaModules(StreamUtil.toMappedList(StudentVocationalModuleDto::of, curriculumVersionOccupationModuleRepository.findAll(extraCurriculaModuleIds)));
        return dto;
    }
    
    public List<StudentVocationalResultModuleThemeDto> studentVocationalResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        result.addAll(vocationalResultsThemeResults(student));
        result.addAll(vocationalResultsModuleResults(student));
        result.sort(Comparator.comparing(StudentVocationalResultModuleThemeDto::getDate).reversed());
        return result;
    }
    
    private List<StudentVocationalResultModuleThemeDto> vocationalResultsModuleResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        
        String from = "from student_vocational_result svr"
                + " inner join curriculum_version_omodule cvo on cvo.id = svr.curriculum_version_omodule_id"
                + " inner join curriculum_version cv on cv.id = cvo.curriculum_version_id"
                + " inner join curriculum_module cm on cm.id = cvo.curriculum_module_id"
                + " inner join classifier mcl on mcl.code = cm.module_code"
                + " left join study_year sy on sy.id = svr.study_year_id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.sort("svr.grade_date");
        
        List<?> rows = qb.select(
                "distinct svr.curriculum_version_omodule_id, cv.code, cm.name_et as module_name_et, mcl.name_et classifer_name_et,"
                + "cm.name_en as module_name_en, mcl.name_en as classifer_name_en, cm.credits, svr.grade_code, svr.grade_date,"
                + "svr.teachers, sy.year_code, sy.start_date",
                em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setModule(new AutocompleteResult(resultAsLong(r, 0),
                    CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                    CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))));
            dto.setCredits(resultAsDecimal(r, 6));
            dto.setGrade(resultAsString(r, 7));
            dto.setDate(resultAsLocalDate(r, 8));
            dto.getTeachers().add(new AutocompleteResult(null, resultAsString(r, 9), resultAsString(r, 9)));
            dto.setStudyYear(resultAsString(r, 10));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 11));
            result.add(dto);
        }
        return result;
    }
    
    private Collection<StudentVocationalResultModuleThemeDto> vocationalResultsThemeResults(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ("
                + "select distinct on (cvot.id, journal_teacher_id) cvot.id cvot_id, cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, jes.grade_code jes_grade_code, jes.grade_inserted jes_grade_inserted, "
                + "tp.id journal_teacher_id, tp.firstname journal_teacher_firstname, tp.lastname journal_teacher_lastname,"
                + "cvo.id cvo_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, cm.credits cm_credits,"
                + "sy.year_code sy_year_code, sy.start_date sy_start_date "
                + "from journal_student js "
                + "inner join journal_omodule_theme jot on jot.journal_id = js.journal_id "
                + "inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "inner join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id "
                + "inner join journal j on j.id = js.journal_id "
                + "inner join study_year sy on sy.id = j.study_year_id "
                + "inner join journal_entry je on je.journal_id = js.journal_id "
                + "inner join journal_entry_student jes on jes.journal_entry_id = je.id "
                + "inner join journal_teacher jt on jt.journal_id = js.journal_id "
                + "inner join teacher t on t.id = jt.teacher_id "
                + "inner join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and "
                + "je.entry_type_code = :entryTypeCode "
                + "order by cvot.id desc, journal_teacher_id, jes.grade_inserted desc) as module_theme_result "
                + "order by module_theme_result.jes_grade_inserted desc");

        qb.parameter("entryTypeCode", JournalEntryType.SISSEKANNE_L.name());
        qb.parameter("studentId", EntityUtil.getId(student));

        List<?> rows = qb.select(
                "cvo_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en,"
                        + "cvot_id, cvot_name_et, cvot_credits, jes_grade_code, jes_grade_inserted, journal_teacher_id, journal_teacher_firstname, journal_teacher_lastname, sy_year_code, sy_start_date ",
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
}
