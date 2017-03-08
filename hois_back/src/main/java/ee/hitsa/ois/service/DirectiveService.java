package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.DirectiveCoordinatorRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveDataCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveStudentSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudent;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentSearchDto;

@Transactional
@Service
public class DirectiveService {
    private static final String DIRECTIVE_LIST_SELECT =
            "d.id, d.headline, d.directive_nr, d.type_code, d.status_code, d.inserted, d.confirm_date";
    private static final String DIRECTIVE_LIST_FROM =
            "from directive d inner join classifier type on d.type_code=type.code inner join classifier status on d.status_code=status.code";
    // maximum number of students returned by search for one directive
    private static final int STUDENTS_MAX = 100;

    @Autowired
    private EntityManager em;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Page<DirectiveSearchDto> search(Long schoolId, DirectiveSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(DIRECTIVE_LIST_FROM, pageable);

        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalCriteria("d.type_code in (:type)", "type", criteria.getType());
        qb.optionalContains("d.headline", "headline", criteria.getHeadline());
        qb.optionalContains("d.directive_nr", "directiveNr", criteria.getDirectiveNr());
        qb.optionalCriteria("d.confirm_date >= :confirmDateFrom", "confirmDateFrom", criteria.getConfirmDateFrom());
        qb.optionalCriteria("d.confirm_date <= :confirmDateThru", "confirmDateThru", criteria.getConfirmDateThru());
        qb.optionalCriteria("d.status_code in (:status)", "status", criteria.getStatus());
        if(criteria.getInsertedFrom() != null) {
            qb.requiredCriteria("d.inserted >= :insertedFrom", "insertedFrom", LocalDateTime.of(criteria.getInsertedFrom(), LocalTime.MIN));
        }
        if(criteria.getInsertedThru() != null) {
            qb.requiredCriteria("d.inserted <= :insertedThru", "insertedThru", LocalDateTime.of(criteria.getInsertedThru(), LocalTime.MAX));
        }
        if(StringUtils.hasText(criteria.getStudentGroup())) {
            qb.requiredCriteria("d.id in (select ds.directive_id from directive_student ds inner join student_group sg on ds.student_group_id=sg.id where upper(sg.code) like :studentGroup)", "studentGroup", "%"+criteria.getStudentGroup().toUpperCase()+"%");
        }

        return JpaQueryUtil.pagingResult(qb.select(DIRECTIVE_LIST_SELECT, em), pageable, () -> qb.count(em)).map(r -> {
            DirectiveSearchDto dto = new DirectiveSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setHeadline(resultAsString(r, 1));
            dto.setDirectiveNr(resultAsString(r, 2));
            dto.setType(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setCreated(resultAsLocalDate(r, 5));
            dto.setConfirmDate(resultAsLocalDate(r, 6));
            return dto;
        });
    }

    public Directive save(Directive directive, DirectiveForm form) {
        if(directive.getId() == null) {
        } else {
            assertModifyable(directive);
        }
        EntityUtil.bindToEntity(form, directive, "students");
        if(form.getStudents() != null) {
            List<DirectiveStudent> students = directive.getStudents();
            Map<Long, DirectiveStudent> studentMapping = students.stream().collect(Collectors.toMap(DirectiveStudent::getId, ds -> ds));
            for(DirectiveFormStudent formStudent : form.getStudents()) {
                DirectiveStudent student = studentMapping.remove(formStudent.getId());
                if(student == null) {
                    student = new DirectiveStudent();
                    student.setDirective(directive);
                    students.add(student);
                }
                EntityUtil.bindToEntity(formStudent, student);

                String idcode = formStudent.getIdcode();
                if(StringUtils.hasText(idcode) && DirectiveType.KASKKIRI_IMMAT.name().equals(EntityUtil.getCode(directive.getType()))) {
                    // add new person if person idcode is not known
                    Person person = personRepository.findByIdcode(idcode);
                    // FIXME should update existing person?
                    if(person == null) {
                        person = new Person();
                        person.setIdcode(idcode);
                        person.setFirstname(formStudent.getFirstname());
                        person.setLastname(formStudent.getLastname());
                        person = personRepository.save(person);
                    }
                    student.setPerson(person);
                }
            }
            // remove possible existing directive students not included in update command
            students.removeAll(studentMapping.values());
        }
        return directiveRepository.save(directive);
    }

    public void delete(Directive directive) {
        assertModifyable(directive);
        EntityUtil.deleteEntity(directiveRepository, directive);
    }

    public List<DirectiveStudentDto> loadStudents(Long schoolId, DirectiveDataCommand cmd) {
        List<Long> studentIds = cmd.getStudents();
        if(studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, Application> applications = applicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(root.get("student").get("id").in(studentIds));
            // TODO application type

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().collect(Collectors.toMap(r -> EntityUtil.getId(r.getStudent()), r -> r));
        List<Student> students = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(root.get("student").get("id").in(studentIds));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        // for each student, create DirectiveStudentDto either from application or from student
        return students.stream().map(student -> {
            Application application = applications.get(student.getId());
            if(application != null) {
                return DirectiveStudentDto.of(application);
            }
            return DirectiveStudentDto.of(student);
        }).collect(Collectors.toList());
    }

    public List<DirectiveStudentSearchDto> searchStudents(Long schoolId, DirectiveStudentSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from student s inner join person person on s.person_id = person.id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("person.firstname", "firstname", criteria.getFirstname());
        qb.optionalContains("person.lastname", "lastname", criteria.getLastname());
        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        if(Boolean.TRUE.equals(criteria.getApplication())) {
            // TODO directive
        }

        List<?> data = qb.select("s.id, person.firstname, person.lastname, person.idcode", em).setMaxResults(STUDENTS_MAX).getResultList();
        return data.stream().map(r -> {
            DirectiveStudentSearchDto dto = new DirectiveStudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            return dto;
        }).collect(Collectors.toList());
    }

    public Page<DirectiveCoordinatorDto> search(Long schoolId, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_coordinator dc");
        qb.requiredCriteria("dc.school_id = :schoolId", "schoolId", schoolId);

        return JpaQueryUtil.pagingResult(qb.select("dc.id, dc.name, dc.idcode, dc.version, dc.is_directive, dc.is_certificate, dc.is_certificate_default", em), pageable, () -> qb.count(em)).map(r -> {
            DirectiveCoordinatorDto dto = new DirectiveCoordinatorDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setIdcode(resultAsString(r, 2));
            dto.setVersion(resultAsLong(r, 3));
            dto.setIsDirective(resultAsBoolean(r, 4));
            dto.setIsCertificate(resultAsBoolean(r, 5));
            dto.setIsCertificateDefault(resultAsBoolean(r, 6));
            return dto;
        });
    }

    public DirectiveCoordinator save(DirectiveCoordinator coordinator) {
        return directiveCoordinatorRepository.save(coordinator);
    }

    public void delete(DirectiveCoordinator coordinator) {
        directiveCoordinatorRepository.delete(coordinator);
    }

    private static void assertModifyable(Directive directive) {
        // assert directive state 'KOOSTAMISEL'
        if(!DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name().equals(EntityUtil.getNullableCode(directive.getStatus()))) {
            throw new IllegalArgumentException();
        }
    }
}
