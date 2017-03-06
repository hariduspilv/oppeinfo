package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.*;
import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudentRepresentativeApplicationRepository;
import ee.hitsa.ois.repository.StudentRepresentativeRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationDeclineForm;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationForm;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeForm;
import ee.hitsa.ois.web.dto.student.StudentRepresentativeApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentRepresentativeDto;

@Transactional
@Service
public class StudentRepresentativeService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentRepresentativeRepository studentRepresentativeRepository;
    @Autowired
    private StudentRepresentativeApplicationRepository studentRepresentativeApplicationRepository;
    @Autowired
    private UserService userService;

    public Page<StudentRepresentativeDto> search(HoisUserDetails user, Long studentId, Pageable pageable) {
        return studentRepresentativeRepository.findAllByStudent_id(studentId, pageable).map(r -> StudentRepresentativeDto.of(r, user));
    }

    public StudentRepresentative save(StudentRepresentative representative, StudentRepresentativeForm form) {
        Person p;
        StudentRepresentativeForm.StudentRepresentativePersonForm personForm = form.getPerson();
        if(representative.getId() != null) {
            p = representative.getPerson();

            representative.setIsStudentVisible(form.getIsStudentVisible());
        } else {
            // new representative
            // do we have given person already in database?
            p = personRepository.findByIdcode(personForm.getIdcode());
            EntityUtil.bindToEntity(form, representative, classifierRepository);
        }
        if(p != null) {
            // there already was person, just update contact data
            p.setEmail(personForm.getEmail());
            p.setPhone(personForm.getPhone());
        } else {
            // add new person
            p = EntityUtil.bindToEntity(personForm, new Person());
        }
        p = personRepository.save(p);
        if(representative.getPerson() != p) {
            representative.setPerson(p);
        }
        return studentRepresentativeRepository.save(representative);
    }

    public void delete(StudentRepresentative representative) {
        // TODO if there is StudentRepresentativeApplication, change it's status too?
        studentRepresentativeRepository.delete(representative);
    }

    public Page<StudentRepresentativeApplicationDto> searchApplications(Long schoolId, StudentRepresentativeApplicationSearchCommand criteria, Pageable pageable) {
        return studentRepresentativeApplicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("student").get("school").get("id"), schoolId));
            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("person").get("idcode"), criteria.getIdcode()));
            }
            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("person").get("firstname"), cb, criteria.getName(), name::add);
            propertyContains(() -> root.get("person").get("lastname"), cb, criteria.getName(), name::add);
            if(!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }
            if(StringUtils.hasText(criteria.getStatus())) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatus()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(StudentRepresentativeApplicationDto::of);
    }

    public void acceptApplication(StudentRepresentativeApplication application) {
        assertApplicationIsRequested(application);

        // create representative for student
        StudentRepresentative representative = EntityUtil.bindToEntity(application, new StudentRepresentative());
        representative.setIsStudentVisible(Boolean.TRUE);
        representative = studentRepresentativeRepository.save(representative);
        Person person = representative.getPerson();
        School school = representative.getStudent().getSchool();
        Long schoolId = EntityUtil.getId(school);
        // if there is no user for given person with role of parent/representative for school of student, create it
        if(!person.getUsers().stream().anyMatch(u -> schoolId.equals(EntityUtil.getNullableId(u.getSchool())) && Role.ROLL_L.name().equals(EntityUtil.getCode(u.getRole())))) {
            userService.createUser(person, Role.ROLL_L, school);
        }
        application.setStatus(classifierRepository.getOne(AVALDUS_ESINDAJA_STAATUS_K.name()));
        studentRepresentativeApplicationRepository.save(application);
    }

    public void declineApplication(StudentRepresentativeApplication application, StudentRepresentativeApplicationDeclineForm form) {
        assertApplicationIsRequested(application);

        EntityUtil.bindToEntity(form, application);
        application.setStatus(classifierRepository.getOne(AVALDUS_ESINDAJA_STAATUS_T.name()));
        studentRepresentativeApplicationRepository.save(application);
    }

    public void createApplication(HoisUserDetails user, StudentRepresentativeApplicationForm form) {
        // find person submitting application
        Person person = personRepository.findByUserId(user.getUserId());
        if(person == null) {
            // TODO create new person with data from user (idcode, lastname, firstname?)
            throw new EntityNotFoundException();
        }

        // find all students with given studentIdcode
        List<Student> students = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("person").get("idcode"), form.getStudentIdcode()));
            // FIXME status of student?

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        if(students.isEmpty()) {
            throw new ValidationFailedException(null, "invalid-student-representative-application-student-not-found");
        }

        // update person data
        person.setPhone(form.getPhone());
        person.setEmail(form.getEmail());
        person = personRepository.save(person);

        // create application for each student
        applications(person, students, form);
    }

    private void applications(Person person, List<Student> students, StudentRepresentativeApplicationForm form) {
        Classifier status = classifierRepository.getOne(AVALDUS_ESINDAJA_STAATUS_E.name());
        Classifier relation = classifierRepository.getOne(form.getRelation());

        students.forEach(student -> {
            StudentRepresentativeApplication application = new StudentRepresentativeApplication();
            application.setStudent(student);
            application.setPerson(person);
            application.setStatus(status);
            application.setRelation(relation);
            studentRepresentativeApplicationRepository.save(application);
        });
    }

    private static void assertApplicationIsRequested(StudentRepresentativeApplication application) {
        if(!AVALDUS_ESINDAJA_STAATUS_E.name().equals(EntityUtil.getCode(application.getStatus()))) {
            throw new ValidationFailedException(null, "invalid-student-representative-application-status");
        }
    }
}
