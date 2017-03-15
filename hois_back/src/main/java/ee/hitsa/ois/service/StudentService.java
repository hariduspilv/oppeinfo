package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.StudentAbsenceRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class StudentService {

    private static final String STUDENT_LIST_SELECT = "s.id, person.firstname, person.lastname, person.idcode, "+
            "curriculum_version.id curriculum_version_id, curriculum_version.code curriculum_version_code, curriculum.name_et, curriculum.name_en, " +
            "student_group.id student_group_id, student_group.code student_group_code, s.study_form_code, s.status_code";
    private static final String STUDENT_LIST_FROM = "from student s inner join person person on s.person_id=person.id "+
            "inner join curriculum_version curriculum_version on s.curriculum_version_id=curriculum_version.id "+
            "inner join curriculum curriculum on curriculum_version.curriculum_id=curriculum.id "+
            "inner join classifier status on s.status_code=status.code "+
            "left outer join student_group student_group on s.student_group_id=student_group.id "+
            "left outer join classifier study_form on s.study_form_code=study_form.code";

    @Autowired
    private EntityManager em;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentAbsenceRepository studentAbsenceRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    public Page<StudentSearchDto> search(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_LIST_FROM, pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        if(StringUtils.hasText(criteria.getName()))  {
            qb.requiredCriteria("(upper(person.firstname) like :name or upper(person.lastname) like :name)", "name", "%"+criteria.getName().toUpperCase()+"%");
        }
        qb.optionalCriteria("s.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalContains("student_group.code", "code", criteria.getStudentGroup());
        qb.optionalCriteria("s.study_form_code in (:studyForm)", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.status_code in (:status)", "status", criteria.getStatus());

        return JpaQueryUtil.pagingResult(qb.select(STUDENT_LIST_SELECT, em), pageable, () -> qb.count(em)).map(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStudyForm(resultAsString(r, 2));
            String curriculumVersionCode = resultAsString(r, 5);
            dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 6)),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 7))));
            // TODO studentgroup as AutocompleteResult
            dto.setStudentGroup(resultAsString(r, 9));
            dto.setStudyForm(resultAsString(r, 10));
            dto.setStatus(resultAsString(r, 11));
            return dto;
        });
    }

    public Student save(HoisUserDetails user, Student student, StudentForm form) {
        Person p = student.getPerson();
        EntityUtil.bindToEntity(form.getPerson(), p, classifierRepository);
        personRepository.save(p);

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
        student.setStudentHistory(current);
        return studentRepository.save(student);
    }

    public Page<StudentAbsenceDto> absences(Long studentId, Pageable pageable) {
        return studentAbsenceRepository.findAllByStudent_id(studentId, pageable).map(StudentAbsenceDto::of);
    }

    public StudentAbsence create(Student student, StudentAbsenceForm form) {
        StudentAbsence absence = new StudentAbsence();
        absence.setStudent(student);
        absence.setIsAccepted(Boolean.FALSE);
        return save(absence, form);
    }

    public StudentAbsence save(StudentAbsence absence, StudentAbsenceForm form) {
        EntityUtil.bindToEntity(form, absence);
        return studentAbsenceRepository.save(absence);
    }

    public void delete(StudentAbsence absence) {
        EntityUtil.deleteEntity(studentAbsenceRepository, absence);
    }

    public Page<StudentApplicationDto> applications(Long studentId, Pageable pageable) {
        return applicationRepository.findAllByStudent_id(studentId, pageable).map(StudentApplicationDto::of);
    }

    public Page<StudentDirectiveDto> directives(Long studentId, Pageable pageable) {
        return directiveRepository.findAllByStudent_id(studentId, pageable).map(StudentDirectiveDto::of);
    }

    public List<AutocompleteResult> subjects(Student student) {
        //TODO single query
        List<AutocompleteResult> subjects = new ArrayList<>();
        student.getCurriculumVersion().getModules().forEach(
                m -> m.getSubjects().forEach(s -> subjects.add(AutocompleteResult.of(s.getSubject()))));
        return subjects;
    }
}
