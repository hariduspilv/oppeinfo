package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchStudentsCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupStudentDto;

@Transactional
@Service
public class StudentGroupService {

    private static final List<String> STUDENT_STATUS_ACTIVE = Arrays.asList(StudentStatus.OPPURSTAATUS_O.name(), StudentStatus.OPPURSTAATUS_A.name(), StudentStatus.OPPURSTAATUS_V.name());
    private static final String STUDENT_GROUP_LIST_SELECT =
            "sg.id, sg.code, sg.study_form_code, sg.course, curriculum.id as curriculum_id, "+
            "curriculum.name_et, curriculum.name_en, "+
            "(select count(*) from student s where s.student_group_id=sg.id and s.status_code in (:studentStatus))";
    private static final String STUDENT_GROUP_LIST_FROM =
            "from student_group sg inner join curriculum curriculum on sg.curriculum_id=curriculum.id "+
            "inner join classifier study_form on sg.study_form_code=study_form.code";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    public Page<StudentGroupSearchDto> search(Long schoolId, StudentGroupSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_GROUP_LIST_FROM, pageable);

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalContains("sg.code", "code", criteria.getCode());
        qb.optionalCriteria("curriculum.id = :curriculum", "curriculum", criteria.getCurriculum());
        qb.optionalCriteria("curriculum.id in (:curriculums)", "curriculums", criteria.getCurriculums());
        qb.optionalCriteria("sg.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("sg.study_form_code in (:studyForm)", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("sg.teacher_id = :teacherId", "teacherId", criteria.getTeacher());
        qb.optionalCriteria("sg.teacher_id in (:teacherIds)", "teacherIds", criteria.getTeachers());
        qb.optionalCriteria("sg.teacher_id in (select t.id from teacher t where t.person_id = :teacherPerson and t.school_id = :schoolId)", "teacherPerson", criteria.getTeacherPerson());

        return JpaQueryUtil.pagingResult(qb.select(STUDENT_GROUP_LIST_SELECT, em, Collections.singletonMap("studentStatus", STUDENT_STATUS_ACTIVE)), pageable, () -> qb.count(em)).map(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setStudyForm(resultAsString(r, 2));
            dto.setCourse(resultAsInteger(r, 3));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 6)));
            dto.setStudentCount(resultAsLong(r, 7));
            return dto;
        });
    }

    public StudentGroup create(HoisUserDetails user, StudentGroupForm form) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return save(user, studentGroup, form);
    }

    public StudentGroup save(HoisUserDetails user, StudentGroup studentGroup, StudentGroupForm form) {
        EntityUtil.bindToEntity(form, studentGroup, classifierRepository, "students");

        // curriculum is required and must be from same school
        Long curriculumId = form.getCurriculum().getId();
        if(curriculumId == null) {
            throw new IllegalArgumentException();
        }
        Curriculum curriculum = curriculumRepository.getOne(curriculumId);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        studentGroup.setCurriculum(curriculum);

        // curriculum version is optional but must be from same curriculum
        CurriculumVersion curriculumVersion = EntityUtil.getOptionalOne(CurriculumVersion.class, form.getCurriculumVersion(), em);
        if(curriculumVersion != null && !curriculumId.equals(EntityUtil.getId(curriculumVersion.getCurriculum()))) {
            throw new AssertionFailedException("Curriculum mismatch");
        }
        studentGroup.setCurriculumVersion(curriculumVersion);

        // teacher is optional but must be from same school
        Long teacherId = form.getTeacher() != null ? form.getTeacher().getId() : null;
        Teacher teacher = EntityUtil.getOptionalOne(Teacher.class, teacherId, em);
        if(teacher != null) {
            UserUtil.assertSameSchool(user, teacher.getSchool());
        }
        studentGroup.setTeacher(teacher);

        studentGroup = studentGroupRepository.save(studentGroup);
        // update student list in group
        Set<Long> studentIds = new HashSet<>(form.getStudents() != null ? form.getStudents() : Collections.emptyList());
        List<Student> added = new ArrayList<>();
        for(Long studentId : studentIds) {
            Student student = studentRepository.getOne(studentId);
            if(!studentGroup.getId().equals(EntityUtil.getNullableId(student.getStudentGroup()))) {
                student.setStudentGroup(studentGroup);
                studentService.saveWithHistory(student);
                added.add(student);
            }
        }
        // update student group for these students which were removed
        List<Student> oldStudents = studentGroup.getStudents();
        if(oldStudents != null) {
            oldStudents.addAll(added);
            List<Student> removed = new ArrayList<>();
            for(Student student : oldStudents) {
                if(!studentIds.contains(EntityUtil.getId(student))) {
                    student.setStudentGroup(null);
                    studentService.saveWithHistory(student);
                    removed.add(student);
                }
            }
            oldStudents.removeAll(removed);
        } else {
            studentGroup.setStudents(added);
        }
        Integer places = studentGroup.getPlaces();
        if(places != null && places.intValue() < studentGroup.getStudents().size()) {
            throw new ValidationFailedException("studentGroup.too-many-students");
        }
        return studentGroup;
    }

    public void delete(StudentGroup studentGroup) {
        EntityUtil.deleteEntity(studentGroupRepository, studentGroup);
    }

    public List<StudentGroupStudentDto> searchStudents(Long schoolId, StudentGroupSearchStudentsCommand criteria) {
        return studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            Long studentGroupId = criteria.getId();
            if(studentGroupId != null) {
                // existing group, fetch only these students which are not in this group
                Path<?> studentGroup = root.get("studentGroup").get("id");
                filters.add(cb.or(cb.notEqual(studentGroup, studentGroupId), cb.isNull(studentGroup)));
            }
            filters.add(cb.equal(root.get("curriculumVersion").get("curriculum").get("id"), criteria.getCurriculum().getId()));
            if(criteria.getCurriculumVersion() != null) {
                filters.add(cb.equal(root.get("curriculumVersion").get("id"), criteria.getCurriculumVersion()));
            }
            filters.add(cb.equal(root.get("language").get("code"), criteria.getLanguage()));
            filters.add(cb.equal(root.get("studyForm").get("code"), criteria.getStudyForm()));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().map(StudentGroupStudentDto::of).collect(Collectors.toList());
    }

    public Map<String, ?> curriculumData(Curriculum curriculum) {
        Map<String, Object> data = new HashMap<>();
        data.put("languages", curriculum.getStudyLanguages().stream().map(r -> EntityUtil.getCode(r.getStudyLang())).collect(Collectors.toList()));
        List<String> studyForms;
        if(CurriculumUtil.isHigher(curriculum.getOrigStudyLevel())) {
            studyForms = classifierRepository.findAllCodesByMainClassCode(MainClassCode.OPPEVORM.name());
        } else {
            studyForms = curriculum.getStudyForms().stream().map(r -> EntityUtil.getCode(r.getStudyForm())).collect(Collectors.toList());
        }
        data.put("studyForms", studyForms);
        data.put("origStudyLevel", EntityUtil.getCode(curriculum.getOrigStudyLevel()));
        data.put("specialities", findSpecialities(curriculum));
        data.put("isVocational", Boolean.valueOf(CurriculumUtil.isVocational(curriculum.getOrigStudyLevel())));
        return data;
    }

    @SuppressWarnings("unchecked")
    private List<String> findSpecialities(Curriculum curriculum) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from curriculum_occupation_speciality s inner join curriculum_occupation co on s.curriculum_occupation_id = co.id");
        qb.requiredCriteria("co.curriculum_id = :curriculumId", "curriculumId", EntityUtil.getId(curriculum));

        List<?> data = qb.select("s.speciality_code", em).getResultList();
        return (List<String>)data;
    }
}
