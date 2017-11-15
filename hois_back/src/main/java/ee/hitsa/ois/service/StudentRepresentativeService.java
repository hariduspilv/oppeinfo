package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_E;
import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_K;
import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_T;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.message.StudentRepresentativeApplicationAccepted;
import ee.hitsa.ois.message.StudentRepresentativeApplicationCreated;
import ee.hitsa.ois.message.StudentRepresentativeApplicationRejectedMessage;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
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
    private EntityManager em;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserService userService;

    public Page<StudentRepresentativeDto> search(HoisUserDetails user, Long studentId, Pageable pageable) {
        JpaQueryBuilder<StudentRepresentative> qb = new JpaQueryBuilder<>(StudentRepresentative.class, "sr").sort(pageable);
        qb.requiredCriteria("sr.student.id = :studentId", "studentId", studentId);

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(r -> StudentRepresentativeDto.of(r, user));
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
            p.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(p.getIdcode())));
        }
        p = EntityUtil.save(p, em);
        if(representative.getPerson() != p) {
            representative.setPerson(p);
        }

        representative = EntityUtil.save(representative, em);
        createUser(representative);
        return representative;
    }

    public void delete(HoisUserDetails user, StudentRepresentative representative) {
        EntityUtil.setUsername(user.getUsername(), em);
        // TODO if there is StudentRepresentativeApplication, change it's status too?
        userService.deleteUser(representative);
        EntityUtil.deleteEntity(representative, em);
    }

    public Page<StudentRepresentativeApplicationDto> searchApplications(Long schoolId, StudentRepresentativeApplicationSearchCommand criteria, Pageable pageable) {
        JpaQueryBuilder<StudentRepresentativeApplication> qb = new JpaQueryBuilder<>(StudentRepresentativeApplication.class, "sra").sort(pageable);

        qb.requiredCriteria("sra.student.school.id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("sra.person.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalContains(Arrays.asList("sra.person.firstname", "sra.person.lastname", "concat(sra.person.firstname, ' ', sra.person.lastname)"), "name", criteria.getName());
        qb.optionalCriteria("sra.status.code = :status", "status", criteria.getStatus());

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(StudentRepresentativeApplicationDto::of);
    }

    public void acceptApplication(StudentRepresentativeApplication application) {
        assertApplicationIsRequested(application);
        assertRepresentativeApplicationAllowed(application.getStudent());

        // create representative for student
        StudentRepresentative representative = EntityUtil.bindToEntity(application, new StudentRepresentative());
        representative.setIsStudentVisible(Boolean.TRUE);
        representative = EntityUtil.save(representative, em);
        createUser(representative);
        setApplicationStatus(application, AVALDUS_ESINDAJA_STAATUS_K);
        EntityUtil.save(application, em);

        representativeCreatedMessage(representative);
    }

    public void declineApplication(StudentRepresentativeApplication application, StudentRepresentativeApplicationDeclineForm form) {
        assertApplicationIsRequested(application);

        EntityUtil.bindToEntity(form, application);
        setApplicationStatus(application, AVALDUS_ESINDAJA_STAATUS_T);
        application = EntityUtil.save(application, em);

        // send message to representative candidate about rejected application
        StudentRepresentativeApplicationRejectedMessage data = new StudentRepresentativeApplicationRejectedMessage(application);
        automaticMessageService.sendMessageToPerson(MessageType.TEATE_LIIK_OP_TL,
                application.getStudent().getSchool(), application.getPerson(), data);
    }

    public void createApplication(HoisUserDetails user, StudentRepresentativeApplicationForm form) {
        // find person submitting application
        // FIXME should create new person with data from user (idcode, lastname, firstname?)
        Person person = EntityUtil.withEntity(user.getUserId(), id -> personRepository.findByUserId(id), p -> p);

        // find all students with given studentIdcode
        // FIXME status of student?
        List<Student> students = em.createQuery("select s from Student s where s.person.idcode = ?1", Student.class)
                .setParameter(1, form.getStudentIdcode()).getResultList();

        AssertionFailedException.throwIf(students.isEmpty(), "Student representative application: student not found");
        assertRepresentativeApplicationAllowed(students.get(0));

        // update person data
        person.setPhone(form.getPhone());
        person.setEmail(form.getEmail());
        person = EntityUtil.save(person, em);

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
        Classifier status = em.getReference(Classifier.class, AVALDUS_ESINDAJA_STAATUS_E.name());
        Classifier relation = em.getReference(Classifier.class, form.getRelation());

        for(Student student : students) {
            StudentRepresentativeApplication application = new StudentRepresentativeApplication();
            application.setStudent(student);
            application.setPerson(person);
            application.setStatus(status);
            application.setRelation(relation);
            EntityUtil.save(application, em);
        }
    }

    private void createUser(StudentRepresentative representative) {
        // if there is no user for given person with role of parent/representative for school of student, create it
        Person person = representative.getPerson();
        Long schoolId = EntityUtil.getId(representative.getStudent().getSchool());
        Long studentId =  EntityUtil.getId(representative.getStudent());

        if(!StreamUtil.nullSafeSet(person.getUsers()).stream().anyMatch(
                u -> schoolId.equals(EntityUtil.getNullableId(u.getSchool())) && ClassifierUtil.equals(Role.ROLL_L, u.getRole()) && studentId.equals(EntityUtil.getNullableId(u.getStudent())))) {
            userService.createUser(representative);
        }
    }

    private void representativeCreatedMessage(StudentRepresentative representative) {
        // send message to new representative
        Student student = representative.getStudent();
        StudentRepresentativeApplicationAccepted data = new StudentRepresentativeApplicationAccepted(representative);
        automaticMessageService.sendMessageToPerson(MessageType.TEATE_LIIK_OP_ESINDAJA, student.getSchool(), student.getPerson(), data);
    }

    private void setApplicationStatus(StudentRepresentativeApplication application, StudentRepresentativeApplicationStatus status) {
        application.setStatus(em.getReference(Classifier.class, status.name()));
    }

    private static void assertApplicationIsRequested(StudentRepresentativeApplication application) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(AVALDUS_ESINDAJA_STAATUS_E, application.getStatus()), "Invalid student representative application status");
    }

    private static void assertRepresentativeApplicationAllowed(Student student) {
        if(StudentUtil.isAdultAndDoNotNeedRepresentative(student)) {
            throw new ValidationFailedException("student.representative.application.adult");
        }
    }
}
