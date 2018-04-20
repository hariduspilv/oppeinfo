package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchStudentsCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentGroupDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupStudentDto;

@Transactional
@Service
public class StudentGroupService {

    private static final String STUDENT_GROUP_LIST_SELECT =
            "sg.id, sg.code, sg.study_form_code, sg.course, curriculum.id as curriculum_id, "+
            "curriculum.name_et, curriculum.name_en, sg.valid_from, sg.valid_thru, "+
            "(select count(*) from student s where s.student_group_id=sg.id and s.status_code in (:studentStatus)), p.firstname, p.lastname";
    private static final String STUDENT_GROUP_LIST_FROM =
            "from student_group sg " +
            "join curriculum curriculum on sg.curriculum_id=curriculum.id "+
            "join classifier study_form on sg.study_form_code=study_form.code " +
            "left join teacher t on sg.teacher_id=t.id " +
            "left join person p on t.person_id=p.id";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudentService studentService;

    /**
     * Search student groups
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentGroupSearchDto> search(Long schoolId, StudentGroupSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_GROUP_LIST_FROM).sort(pageable);

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalContains("sg.code", "code", criteria.getCode());
        qb.optionalCriteria("curriculum.id = :curriculum", "curriculum", criteria.getCurriculum());
        qb.optionalCriteria("curriculum.id in (:curriculums)", "curriculums", criteria.getCurriculums());
        qb.optionalCriteria("sg.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("sg.study_form_code in (:studyForm)", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("sg.teacher_id = :teacherId", "teacherId", criteria.getTeacher());
        qb.optionalCriteria("sg.teacher_id in (:teacherIds)", "teacherIds", criteria.getTeachers());
        qb.optionalCriteria("sg.valid_from >= :validFrom", "validFrom", criteria.getValidFrom());
        qb.optionalCriteria("sg.valid_thru <= :validThru", "validThru", criteria.getValidThru());

        return JpaQueryUtil.pagingResult(qb.select(STUDENT_GROUP_LIST_SELECT, em, Collections.singletonMap("studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE)), pageable, () -> qb.count(em)).map(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setStudyForm(resultAsString(r, 2));
            dto.setCourse(resultAsInteger(r, 3));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 6)));
            dto.setValidFrom(resultAsLocalDate(r, 7));
            dto.setValidThru(resultAsLocalDate(r, 8));
            dto.setStudentCount(resultAsLong(r, 9));
            dto.setTeacher(PersonUtil.fullname(resultAsString(r, 10), resultAsString(r, 11)));
            return dto;
        });
    }

    /**
     * Get student group record
     *
     * @param user
     * @param studentGroup
     * @return
     */
    public StudentGroupDto get(HoisUserDetails user, StudentGroup studentGroup) {
        return StudentGroupDto.of(user, studentGroup);
    }

    /**
     * Add new student group
     *
     * @param user
     * @param form
     * @return
     */
    public Long create(HoisUserDetails user, StudentGroupForm form) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setSchool(em.getReference(School.class, user.getSchoolId()));
        return saveInternal(user, studentGroup, form).getId();
    }

    /**
     * Update student group
     *
     * @param user
     * @param studentGroup
     * @param form
     * @return
     */
    public StudentGroupDto save(HoisUserDetails user, StudentGroup studentGroup, StudentGroupForm form) {
        studentGroup = saveInternal(user, studentGroup, form);
        em.flush();

        return get(user, studentGroup);
    }

    private StudentGroup saveInternal(HoisUserDetails user, StudentGroup studentGroup, StudentGroupForm form) {
        EntityUtil.bindToEntity(form, studentGroup, classifierRepository, "students");

        // curriculum is required and must be from same school
        Long curriculumId = form.getCurriculum().getId();
        Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        studentGroup.setCurriculum(curriculum);

        // curriculum version is optional but must be from same curriculum
        CurriculumVersion curriculumVersion = EntityUtil.getOptionalOne(CurriculumVersion.class, form.getCurriculumVersion(), em);
        if(curriculumVersion != null && !curriculumId.equals(EntityUtil.getId(curriculumVersion.getCurriculum()))) {
            throw new AssertionFailedException("Curriculum mismatch");
        }
        studentGroup.setCurriculumVersion(curriculumVersion);

        // teacher is optional but must be from same school
        Teacher teacher = EntityUtil.getOptionalOne(Teacher.class, form.getTeacher(), em);
        if(teacher != null) {
            UserUtil.assertSameSchool(user, teacher.getSchool());
        }
        studentGroup.setTeacher(teacher);

        studentGroup = EntityUtil.save(studentGroup, em);

        // update student list in group
        Set<Long> studentIds = new HashSet<>(StreamUtil.nullSafeList(form.getStudents()));
        List<Student> added = new ArrayList<>();
        if(!studentIds.isEmpty()) {
            List<Student> students = em.createQuery("select s from Student s where s.id in (?1)", Student.class)
                    .setParameter(1, studentIds)
                    .getResultList();

            if(students.size() != studentIds.size()) {
                // some students were not found
                throw new EntityNotFoundException();
            }
            boolean canAdd = studentGroup.getValidThru() == null || !LocalDate.now().isAfter(studentGroup.getValidThru());
            Long studentGroupId = studentGroup.getId();
            for(Student student : students) {
                if(!studentGroupId.equals(EntityUtil.getNullableId(student.getStudentGroup()))) {
                    if(!canAdd) {
                        throw new ValidationFailedException("studentGroup.cannotaddstudents");
                    }
                    UserUtil.assertSameSchool(user, student.getSchool());
                    student.setStudentGroup(studentGroup);
                    studentService.saveWithHistory(student);
                    added.add(student);
                }
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
        return studentGroup;
    }

    /**
     * Delete student group
     *
     * @param user
     * @param studentGroup
     */
    public void delete(HoisUserDetails user, StudentGroup studentGroup) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(studentGroup, em);
    }

    /**
     * Search students for adding into student group
     *
     * @param schoolId
     * @param criteria
     * @return
     */
    public List<StudentGroupStudentDto> searchStudents(Long schoolId, StudentGroupSearchStudentsCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s join curriculum_version cv on s.curriculum_version_id = cv.id join curriculum c on cv.curriculum_id = c.id join person p on s.person_id = p.id").sort("p.lastname", "p.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("cv.curriculum_id = :curriculum", "curriculum", criteria.getCurriculum());
        qb.optionalCriteria("s.curriculum_version_id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("(s.student_group_id is null or s.student_group_id != :studentGroup)", "studentGroup", criteria.getId());
        qb.optionalCriteria("s.language_code = :language", "language", criteria.getLanguage());
        qb.optionalCriteria("s.study_form_code = :studyForm", "studyForm", criteria.getStudyForm());

        List<?> data = qb.select("s.id, p.firstname, p.lastname, p.idcode, cv.id as cv_id, cv.code, c.name_et, c.name_en, s.status_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupStudentDto dto = new StudentGroupStudentDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            String cvCode = resultAsString(r, 5);
            dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                    CurriculumUtil.versionName(cvCode, resultAsString(r, 6)), CurriculumUtil.versionName(cvCode, resultAsString(r, 7))));
            dto.setStatus(resultAsString(r, 8));
            return dto;
        }, data);
    }

    public Map<String, ?> curriculumData(Curriculum curriculum) {
        Map<String, Object> data = new HashMap<>();
        data.put("languages", StreamUtil.toMappedList(r -> EntityUtil.getCode(r.getStudyLang()), curriculum.getStudyLanguages()));
        List<String> studyForms;
        if(CurriculumUtil.isHigher(curriculum)) {
            // only study forms with higher flag set
            studyForms = em.createQuery("select code from Classifier where main_class_code = ?1 and higher = true", String.class)
                .setParameter(1, MainClassCode.OPPEVORM.name())
                .getResultList();
        } else {
            studyForms = StreamUtil.toMappedList(r -> EntityUtil.getCode(r.getStudyForm()), curriculum.getStudyForms());
        }
        data.put("studyForms", studyForms);
        data.put("origStudyLevel", EntityUtil.getCode(curriculum.getOrigStudyLevel()));
        data.put("specialities", findSpecialities(curriculum));
        data.put("isVocational", Boolean.valueOf(CurriculumUtil.isVocational(curriculum)));
        return data;
    }

    @SuppressWarnings("unchecked")
    private List<String> findSpecialities(Curriculum curriculum) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_occupation_speciality s inner join curriculum_occupation co on s.curriculum_occupation_id = co.id");
        qb.requiredCriteria("co.curriculum_id = :curriculumId", "curriculumId", EntityUtil.getId(curriculum));

        List<?> data = qb.select("s.speciality_code", em).getResultList();
        return (List<String>)data;
    }
}
