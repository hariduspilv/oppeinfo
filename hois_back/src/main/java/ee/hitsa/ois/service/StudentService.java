package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceSearchDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalModuleDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultDto;

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
    private ProtocolService protocolService;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;

    public Page<StudentSearchDto> search(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_LIST_FROM).sort(pageable);

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

    public StudentAbsenceSearchDto absences(HoisUserDetails user, Student student, Pageable pageable) {
        Long studentId = EntityUtil.getId(student);
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from student_absence sa").sort(pageable);
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
            dto.setUserCanEdit(Boolean.valueOf(UserUtil.canEditStudentAbsence(user, absence)));
            return dto;
        });

        // fetch student-related data
        qb = new JpaQueryUtil.NativeQueryBuilder("from student s inner join person p on s.person_id = p.id left outer join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        Object studentData = qb.select("p.firstname, p.lastname, sg.code", em).getSingleResult();
        String studentName = PersonUtil.fullname(resultAsString(studentData, 0), resultAsString(studentData, 1));
        String studentGroup = resultAsString(studentData, 2);
        boolean canAddAbsence = UserUtil.canAddStudentAbsence(user, student);

        return new StudentAbsenceSearchDto(data.getContent(), pageable, data.getTotalElements(), studentName, studentGroup, canAddAbsence);
    }

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

    public StudentAbsence save(StudentAbsence absence, StudentAbsenceForm form) {
        EntityUtil.bindToEntity(form, absence);
        return EntityUtil.save(absence, em);
    }

    public void delete(StudentAbsence absence) {
        EntityUtil.deleteEntity(absence, em);
    }

    public Page<StudentApplicationDto> applications(Long studentId, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from application a join classifier type on type.code = a.type_code").sort(pageable);

        qb.requiredCriteria("a.student_id = :studentId", "studentId", studentId);
        return JpaQueryUtil.pagingResult(qb, "a.id, a.type_code, a.inserted, a.status_code, a.changed, a.submitted, a.reject_reason", em, pageable).map(r -> {
            StudentApplicationDto dto = new StudentApplicationDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setInserted(resultAsLocalDateTime(r, 2));
            dto.setStatus(resultAsString(r, 3));
            if(ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name().equals(dto.getStatus())) {
                dto.setConfirmDate(resultAsLocalDateTime(r, 4));
            }
            dto.setSubmitted(resultAsLocalDateTime(r, 5));
            dto.setRejectReason(resultAsString(r, 6));
            return dto;
        });
    }

    public Page<StudentDirectiveDto> directives(HoisUserDetails user, Student student, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive d").sort(pageable);

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

    public List<AutocompleteResult> subjects(Student student) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from subject s "
                + "inner join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = s.id "
                + "inner join curriculum_version_hmodule cvh on cvh.id = cvhms.curriculum_version_hmodule_id");
        qb.requiredCriteria("cvh.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em).getResultList();
        return StreamUtil.toMappedList(r ->
            new AutocompleteResult(resultAsLong(r, 0),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 1), resultAsDecimal(r, 4)),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 2), resultAsDecimal(r, 4))), data);
    }

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
            dto.setCredits(protocolService.vocationalTotalCreditsOnCurrentCurriculum(student));
            dto.setKkh(protocolService.vocationalWeightedAverageGrade(student));
        }
        return dto;
    }

    public StudentVocationalResultDto vocationalResults(Student student) {
        StudentVocationalResultDto dto = new StudentVocationalResultDto();
        dto.setResults(protocolService.vocationalResults(student));
        dto.setCurriculumModules(StreamUtil.toMappedList(StudentVocationalModuleDto::of,
                student.getCurriculumVersion().getOccupationModules()));

        Set<Long> curriculaModuleIds = StreamUtil.toMappedSet(StudentVocationalModuleDto::getId, dto.getCurriculumModules());
        Set<Long> extraCurriculaModuleIds = StreamUtil.toMappedSet(it -> it.getModule().getId(), dto.getResults());
        extraCurriculaModuleIds.removeIf(curriculaModuleIds::contains);
        dto.setExtraCurriculaModules(StreamUtil.toMappedList(StudentVocationalModuleDto::of, curriculumVersionOccupationModuleRepository.findAll(extraCurriculaModuleIds)));
        return dto;
    }

}
