package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.*;
import static ee.hitsa.ois.enums.StudentStatus.*;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.DirectiveCoordinatorRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
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

    // required student status for given directive type
    private static final Map<DirectiveType, List<String>> STUDENT_STATUS_FOR_DIRECTIVE_TYPE = new HashMap<>();
    static {
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_AKAD, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_AKADK, Arrays.asList(OPPURSTAATUS_A.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKAVA, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_FINM, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OVORM, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_VALIS, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_EKSMAT, Arrays.asList(OPPURSTAATUS_O.name(), OPPURSTAATUS_A.name(), OPPURSTAATUS_V.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_LOPET, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_ENNIST, Arrays.asList(OPPURSTAATUS_K.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(RIIGIKEEL, Arrays.asList(OPPURSTAATUS_O.name()));
    }

    @Autowired
    private EntityManager em;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolRepository schoolRepository;
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
            qb.requiredCriteria("d.inserted >= :insertedFrom", "insertedFrom", DateUtils.firstMomentOfDay(criteria.getInsertedFrom()));
        }
        if(criteria.getInsertedThru() != null) {
            qb.requiredCriteria("d.inserted <= :insertedThru", "insertedThru", DateUtils.lastMomentOfDay(criteria.getInsertedThru()));
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
            dto.setInserted(resultAsLocalDate(r, 5));
            dto.setConfirmDate(resultAsLocalDate(r, 6));
            return dto;
        });
    }

    public Directive create(HoisUserDetails user, DirectiveForm form) {
        Directive directive = new Directive();
        directive.setSchool(schoolRepository.getOne(user.getSchoolId()));
        directive.setStatus(classifierRepository.getOne(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name()));
        return save(directive, form);
    }

    public Directive save(Directive directive, DirectiveForm form) {
        assertModifyable(directive);

        EntityUtil.bindToEntity(form, directive, classifierRepository, "students");

        DirectiveCoordinator coordinator = form.getDirectiveCoordinator() != null ? directiveCoordinatorRepository.getOne(form.getDirectiveCoordinator()) : null;
        if(coordinator != null && !EntityUtil.getId(directive.getSchool()).equals(EntityUtil.getId(coordinator.getSchool()))) {
            // coordinator is not from same school
            throw new IllegalArgumentException();
        }
        directive.setDirectiveCoordinator(coordinator);

        if(form.getStudents() != null) {
            List<DirectiveStudent> students = directive.getStudents();
            if(students == null) {
                directive.setStudents(students = new ArrayList<>());
            }
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
                if(StringUtils.hasText(idcode) && KASKKIRI_IMMAT.name().equals(EntityUtil.getCode(directive.getType()))) {
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
        qb.optionalCriteria("s.id not in (select ds.student_id from directive_student ds where ds.directive_id = :directiveId)", "directiveId", criteria.getDirective());

        DirectiveType directiveType = DirectiveType.valueOf(criteria.getType());
        Optional<ApplicationType> applicationType = Stream.of(ApplicationType.values()).filter(r -> directiveType.equals(r.directiveType())).findFirst();
        if(applicationType.isPresent()) {
            String applicationSql = "select a.id from application a where a.student_id = s.id and a.type_code = :applicationType and a.status_code in (:applicationStatus)";
            qb.requiredCriteria(String.format(Boolean.TRUE.equals(criteria.getApplication()) ? "exists (%s)" : "not exists (%s)", applicationSql), "applicationType", applicationType.get().name());
            qb.parameter("applicationStatus", Arrays.asList(ApplicationStatus.AVALDUS_STAATUS_ESIT.name(), ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name()));
        }

        // student has no unconfirmed directive of same type
        qb.requiredCriteria("not exists(select ds2.id from directive_student ds2 inner join directive d2 on ds2.directive_id = d2.id where ds2.student_id = s.id and d2.type_code = :directiveType and d2.status_code in (:directiveStatus))", "directiveType", directiveType.name());
        qb.parameter("directiveStatus", Arrays.asList(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name(), DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL.name()));

        List<String> allowedStudentStatus = STUDENT_STATUS_FOR_DIRECTIVE_TYPE.get(directiveType);
        if(allowedStudentStatus != null && !allowedStudentStatus.isEmpty()) {
            qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", allowedStudentStatus);
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

    public DirectiveCoordinator create(HoisUserDetails user, DirectiveCoordinatorForm form) {
        DirectiveCoordinator coordinator = new DirectiveCoordinator();
        coordinator.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return save(coordinator, form);
    }

    public DirectiveCoordinator save(DirectiveCoordinator coordinator, DirectiveCoordinatorForm form) {
        EntityUtil.bindToEntity(form, coordinator);
        return directiveCoordinatorRepository.save(coordinator);
    }

    public void delete(DirectiveCoordinator coordinator) {
        EntityUtil.deleteEntity(directiveCoordinatorRepository, coordinator);
    }

    private static void assertModifyable(Directive directive) {
        // assert directive state 'KOOSTAMISEL'
        if(!DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name().equals(EntityUtil.getNullableCode(directive.getStatus()))) {
            throw new IllegalArgumentException();
        }
    }
}
