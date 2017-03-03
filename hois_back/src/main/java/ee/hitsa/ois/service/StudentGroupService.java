package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.StudentGroupForm;
import ee.hitsa.ois.web.commandobject.StudentGroupSearchCommand;
import ee.hitsa.ois.web.dto.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.StudentGroupStudentDto;

@Transactional
@Service
public class StudentGroupService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    @SuppressWarnings("unchecked")
    public Page<StudentGroupSearchDto> search(Long schoolId, StudentGroupSearchCommand criteria, Pageable pageable) {
        return JpaQueryUtil.query(StudentGroupSearchDto.class, StudentGroup.class, (root, query, cb) -> {
            // TODO optimize curriculum fetch. Now it's N + 1 queries
            // TODO only students with status õpib, akadeemilisel või välisõppes
            ((CriteriaQuery<StudentGroupSearchDto>)query).select(cb.construct(StudentGroupSearchDto.class, root.get("id"), root.get("code"), root.get("curriculum"), root.get("studyForm").get("code"), root.get("course"), cb.count(root.join("students").get("id"))));
            query.groupBy(root.get("id"), root.get("code"), root.get("curriculum"), root.get("studyForm"), root.get("course"));

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            propertyContains(() -> root.get("code"), cb, criteria.getCode(), filters::add);
            if(!CollectionUtils.isEmpty(criteria.getCurriculumVersion())) {
                filters.add(root.get("curriculumVersion").get("id").in(criteria.getCurriculumVersion()));
            }
            if(!CollectionUtils.isEmpty(criteria.getStudyForm())) {
                filters.add(root.get("studyForm").get("code").in(criteria.getStudyForm()));
            }
            if(criteria.getTeacher() != null && criteria.getTeacher().getId() != null) {
                filters.add(cb.equal(root.get("teacher").get("id"), criteria.getTeacher().getId()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);
    }

    public StudentGroup save(StudentGroup studentGroup, StudentGroupForm form) {
        EntityUtil.bindToEntity(form, studentGroup);
        studentGroup = studentGroupRepository.save(studentGroup);
        // update student list in group
        List<Long> studentIds = form.getStudents();
        if(studentIds != null) {
            for(Long studentId : studentIds) {
                Student student = studentRepository.getOne(studentId);
                student.setStudentGroup(studentGroup);
                studentService.saveWithHistory(student);
            }
            // update student group for these sutdents which were removed
            List<Student> oldStudents = studentGroup.getStudents();
            if(oldStudents != null) {
                for(Student student : oldStudents) {
                    if(!studentIds.contains(EntityUtil.getId(student))) {
                        student.setStudentGroup(null);
                        studentService.saveWithHistory(student);
                    }
                }
            }
        }
        return studentGroup;
    }

    public void delete(StudentGroup studentGroup) {
        studentGroupRepository.delete(studentGroup);
    }

    public List<StudentGroupStudentDto> searchStudents(Long schoolId) {
        return studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().map(StudentGroupStudentDto::of).collect(Collectors.toList());
    }
}
