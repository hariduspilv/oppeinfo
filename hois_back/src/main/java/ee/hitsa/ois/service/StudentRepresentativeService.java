package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_E;
import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_K;
import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_T;
import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.message.StudentRepresentativeApplicationAccepted;
import ee.hitsa.ois.message.StudentRepresentativeApplicationCreated;
import ee.hitsa.ois.message.StudentRepresentativeApplicationRejectedMessage;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudentRepresentativeApplicationRepository;
import ee.hitsa.ois.repository.StudentRepresentativeRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
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
    private AutomaticMessageService automaticMessageService;
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

    public StudentRepresentative create(Student student, StudentRepresentativeForm form) {
        StudentRepresentative representative = new StudentRepresentative();
        representative.setStudent(student);
        representative = save(representative, form);

        representativeCreatedMessage(representative);
        return representative;
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
            p.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(p.getIdcode()));
            p.setSex(classifierRepository.getOne(EstonianIdCodeValidator.sexFromIdcode(p.getIdcode())));
        }
        p = personRepository.save(p);
        if(representative.getPerson() != p) {
            representative.setPerson(p);
        }

        return studentRepresentativeRepository.save(representative);
    }

    public void delete(StudentRepresentative representative) {
        // TODO if there is StudentRepresentativeApplication, change it's status too?
        EntityUtil.deleteEntity(studentRepresentativeRepository, representative);
    }

    public Page<StudentRepresentativeApplicationDto> searchApplications(Long schoolId, StudentRepresentativeApplicationSearchCommand criteria, Pageable pageable) {
        return studentRepresentativeApplicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("student").get("school").get("id"), schoolId));
            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("person").get("idcode"), criteria.getIdcode()));
            }

            if(!StringUtils.isEmpty(criteria.getName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("person").get("firstname"), cb, criteria.getName(), name::add);
                propertyContains(() -> root.get("person").get("lastname"), cb, criteria.getName(), name::add);
                name.add(cb.like(cb.concat(cb.upper(root.get("person").get("firstname")), cb.concat(" ", cb.upper(root.get("person").get("lastname")))), JpaQueryUtil.toContains(criteria.getName())));
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }

            if(StringUtils.hasText(criteria.getStatus())) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatus()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(StudentRepresentativeApplicationDto::of);
    }

    public void acceptApplication(StudentRepresentativeApplication application) {
        assertApplicationIsRequested(application);

        if(StudentUtil.isAdult(application.getStudent())) {
            throw new ValidationFailedException("representative.application.adult");
        }

        // create representative for student
        StudentRepresentative representative = EntityUtil.bindToEntity(application, new StudentRepresentative());
        representative.setIsStudentVisible(Boolean.TRUE);
        representative = studentRepresentativeRepository.save(representative);
        Person person = representative.getPerson();
        School school = representative.getStudent().getSchool();
        Long schoolId = EntityUtil.getId(school);
        // if there is no user for given person with role of parent/representative for school of student, create it
        if(!person.getUsers().stream().anyMatch(u -> schoolId.equals(EntityUtil.getNullableId(u.getSchool())) && ClassifierUtil.equals(Role.ROLL_L, u.getRole()))) {
            userService.createUser(person, Role.ROLL_L, school);
        }
        application.setStatus(classifierRepository.getOne(AVALDUS_ESINDAJA_STAATUS_K.name()));
        studentRepresentativeApplicationRepository.save(application);

        representativeCreatedMessage(representative);
    }

    public void declineApplication(StudentRepresentativeApplication application, StudentRepresentativeApplicationDeclineForm form) {
        assertApplicationIsRequested(application);

        EntityUtil.bindToEntity(form, application);
        application.setStatus(classifierRepository.getOne(AVALDUS_ESINDAJA_STAATUS_T.name()));
        application = studentRepresentativeApplicationRepository.save(application);

        // send message to representative candidate about rejected application
        StudentRepresentativeApplicationRejectedMessage data = new StudentRepresentativeApplicationRejectedMessage(application);
        automaticMessageService.sendMessageToPerson(MessageType.TEATE_LIIK_OP_TL,
                application.getStudent().getSchool(), application.getPerson(), data);
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
            // FIXME status of student?
            return cb.equal(root.get("person").get("idcode"), form.getStudentIdcode());
        });
        AssertionFailedException.throwIf(students.isEmpty(), "Student representative application: student not found");

        if(StudentUtil.isAdult(students.get(0))) {
            throw new ValidationFailedException("representative.application.adult");
        }

        // update person data
        person.setPhone(form.getPhone());
        person.setEmail(form.getEmail());
        person = personRepository.save(person);

        // create application for each student
        applications(person, students, form);

        // send message to school administrative workers about new application
        Set<School> schools = StreamUtil.toMappedSet(Student::getSchool, students);
        StudentRepresentativeApplicationCreated data = new StudentRepresentativeApplicationCreated();
        for(School school : schools) {
            automaticMessageService.sendMessageToSchoolAdmins(MessageType.TEATE_LIIK_AV_OPPURI_ANDMED, school, data);
        }
    }

    private void applications(Person person, List<Student> students, StudentRepresentativeApplicationForm form) {
        Classifier status = classifierRepository.getOne(AVALDUS_ESINDAJA_STAATUS_E.name());
        Classifier relation = classifierRepository.getOne(form.getRelation());

        for(Student student : students) {
            StudentRepresentativeApplication application = new StudentRepresentativeApplication();
            application.setStudent(student);
            application.setPerson(person);
            application.setStatus(status);
            application.setRelation(relation);
            studentRepresentativeApplicationRepository.save(application);
        }
    }

    private void representativeCreatedMessage(StudentRepresentative representative) {
        // send message to new representative
        Student student = representative.getStudent();
        StudentRepresentativeApplicationAccepted data = new StudentRepresentativeApplicationAccepted(representative);
        automaticMessageService.sendMessageToPerson(MessageType.TEATE_LIIK_OP_ESINDAJA, student.getSchool(), student.getPerson(), data);
    }

    private static void assertApplicationIsRequested(StudentRepresentativeApplication application) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(AVALDUS_ESINDAJA_STAATUS_E, application.getStatus()), "Invalid student representative application status");
    }
}
