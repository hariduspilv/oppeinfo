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
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.web.commandobject.StudentSearchCommand;

@Transactional
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Page<Student> search(StudentSearchCommand criteria, Pageable pageable) {
        return studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("person").get("firstname"), cb, criteria.getName(), name::add);
            propertyContains(() -> root.get("person").get("lastname"), cb, criteria.getName(), name::add);
            if(!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }
            if(criteria.getCurriculumVersionId() != null) {
                filters.add(cb.equal(root.get("curriculumVersion").get("id"), criteria.getCurriculumVersionId()));
            }
            propertyContains(() -> root.get("studentGroup").get("code"), cb, criteria.getStudentGroupCode(), filters::add);
            if(StringUtils.hasText(criteria.getStudyFormCode())) {
                filters.add(cb.equal(root.get("studyForm").get("code"), criteria.getStudyFormCode()));
            }
            if(StringUtils.hasText(criteria.getStatusCode())) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatusCode()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable);
    }
}
