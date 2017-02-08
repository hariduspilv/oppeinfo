package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.domain.StudentAbsence;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.StudentAbsenceRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.StudentForm;
import ee.hitsa.ois.web.commandobject.StudentSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class StudentService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentAbsenceRepository studentAbsenceRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Page<StudentSearchDto> search(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        return studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("person").get("idcode"), criteria.getIdcode()));
            }
            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("person").get("firstname"), cb, criteria.getName(), name::add);
            propertyContains(() -> root.get("person").get("lastname"), cb, criteria.getName(), name::add);
            if(!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }
            if(!CollectionUtils.isEmpty(criteria.getCurriculumVersion())) {
                filters.add(root.get("curriculumVersion").get("id").in(criteria.getCurriculumVersion()));
            }
            propertyContains(() -> root.get("studentGroup").get("code"), cb, criteria.getStudentGroup(), filters::add);
            if(!CollectionUtils.isEmpty(criteria.getStudyForm())) {
                filters.add(root.get("studyForm").get("code").in(criteria.getStudyForm()));
            }
            if(!CollectionUtils.isEmpty(criteria.getStatus())) {
                filters.add(root.get("status").get("code").in(criteria.getStatus()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(StudentSearchDto::of);
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
        return studentRepository.save(student);
    }

    public Page<StudentAbsenceDto> absences(Long studentId, Pageable pageable) {
        return studentAbsenceRepository.findAllByStudent_id(studentId, pageable).map(StudentAbsenceDto::of);
    }

    public StudentAbsence save(StudentAbsence absence) {
        return studentAbsenceRepository.save(absence);
    }

    public void delete(StudentAbsence absence) {
        studentAbsenceRepository.delete(absence);
    }
}
