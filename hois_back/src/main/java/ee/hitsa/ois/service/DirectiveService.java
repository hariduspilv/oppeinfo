package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.*;
import static ee.hitsa.ois.enums.StudentStatus.*;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.SaisApplication;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.SaisApplicationStatus;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.DirectiveCoordinatorRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveDataCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveStudentSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudent;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveDto;
import ee.hitsa.ois.web.dto.directive.DirectiveSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveViewStudentDto;

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
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_EKSMAT, Arrays.asList(OPPURSTAATUS_O.name(), OPPURSTAATUS_V.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_ENNIST, Arrays.asList(OPPURSTAATUS_K.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_FINM, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_LOPET, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKAVA, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKOORM, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OVORM, Arrays.asList(OPPURSTAATUS_O.name()));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_VALIS, Arrays.asList(OPPURSTAATUS_O.name()));
    }

    // application statuses which can added to directive
    private static final List<String> APPLICATION_STATUS_FOR_DIRECTIVE = Arrays.asList(ApplicationStatus.AVALDUS_STAATUS_ESIT.name(), ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name());

    @Autowired
    private EntityManager em;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SaisApplicationRepository saisApplicationRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;

    public Page<DirectiveSearchDto> search(Long schoolId, DirectiveSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(DIRECTIVE_LIST_FROM, pageable);

        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalCriteria("d.type_code in (:type)", "type", criteria.getType());
        qb.optionalContains("d.headline", "headline", criteria.getHeadline());
        qb.optionalContains("d.directive_nr", "directiveNr", criteria.getDirectiveNr());
        qb.optionalCriteria("d.confirm_date >= :confirmDateFrom", "confirmDateFrom", criteria.getConfirmDateFrom());
        qb.optionalCriteria("d.confirm_date <= :confirmDateThru", "confirmDateThru", criteria.getConfirmDateThru());
        qb.optionalCriteria("d.status_code in (:status)", "status", criteria.getStatus());

        qb.optionalCriteria("d.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("d.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("d.id in (select ds.directive_id from directive_student ds inner join student_group sg on ds.student_group_id=sg.id where upper(sg.code) like :studentGroup)", "studentGroup", criteria.getStudentGroup(), SearchUtil::toContains);

        return JpaQueryUtil.pagingResult(qb, DIRECTIVE_LIST_SELECT, em, pageable).map(r -> {
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
        directive.setSchool(em.getReference(School.class, user.getSchoolId()));
        directive.setStatus(em.getReference(Classifier.class, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name()));
        return save(directive, form);
    }

    public Directive save(Directive directive, DirectiveForm form) {
        assertModifyable(directive);

        EntityUtil.bindToEntity(form, directive, classifierRepository, "students");

        EntityUtil.setEntityFromRepository(form, directive, directiveCoordinatorRepository, "directiveCoordinator");
        DirectiveCoordinator coordinator = directive.getDirectiveCoordinator();
        assertSameSchool(directive, coordinator != null ? coordinator.getSchool() : null);

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(KASKKIRI_TYHIST.equals(directiveType) && directive.getId() == null) {
            // canceled directive can added only during directive create, check for same school
            EntityUtil.setEntityFromRepository(form, directive, directiveRepository, "canceledDirective");
            Directive canceledDirective = directive.getCanceledDirective();
            if(canceledDirective == null) {
                throw new AssertionFailedException("Canceled directive is missing");
            }
            assertSameSchool(directive, canceledDirective.getSchool());
            // check that there is no cancel directive already
            if(Boolean.TRUE.equals(directiveRepository.existsByCanceledDirectiveId(canceledDirective.getId()))) {
                throw new ValidationFailedException("directive.duplicatecancel");
            }
        }

        if(form.getStudents() != null) {
            List<DirectiveStudent> students = directive.getStudents();
            if(students == null) {
                directive.setStudents(students = new ArrayList<>());
            }
            Map<Long, DirectiveStudent> studentMapping = StreamUtil.toMap(DirectiveStudent::getId, students);
            for(DirectiveFormStudent formStudent : form.getStudents()) {
                Long directiveStudentId = formStudent.getId();
                DirectiveStudent directiveStudent = directiveStudentId != null ? studentMapping.remove(directiveStudentId) : null;
                if(KASKKIRI_TYHIST.equals(directiveType)) {
                    continue;
                }
                if(directiveStudent == null) {
                    directiveStudent = new DirectiveStudent();
                    directiveStudent.setDirective(directive);

                    Long studentId = formStudent.getStudent();
                    setStudent(studentId, directiveStudent);
                    setApplication(studentId, formStudent.getApplication(), directiveStudent);
                    if(KASKKIRI_IMMATV.equals(directiveType)) {
                        SaisApplication sais = em.getReference(SaisApplication.class, formStudent.getSaisApplication());
                        assertSameSchool(directive, sais.getSaisAdmission().getCurriculumVersion().getCurriculum().getSchool());
                        directiveStudent.setSaisApplication(sais);
                    }
                    students.add(directiveStudent);
                }

                if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_IMMATV.equals(directiveType)) {
                    // directive type can add new persons (and later students) to the system
                    setPerson(formStudent, directiveStudent);
                }

                EntityUtil.bindToEntity(formStudent, directiveStudent, classifierRepository, "application", "directive", "person", "student");

                EntityUtil.setEntityFromRepository(formStudent, directiveStudent, studentGroupRepository, "studentGroup");
                EntityUtil.setEntityFromRepository(formStudent, directiveStudent, curriculumVersionRepository, "curriculumVersion");
                EntityUtil.setEntityFromRepository(formStudent, directiveStudent, studyPeriodRepository, "studyPeriodStart", "studyPeriodEnd");

                switch(directiveType) {
                case KASKKIRI_AKAD:
                    adjustPeriod(directiveStudent);
                    break;
                case KASKKIRI_LOPET:
                    // TODO copy from student data
                    directiveStudent.setIsCumLaude(Boolean.FALSE);
                    // student.setCurriculumGrade(curriculumGrade);
                    directiveStudent.setCurriculumVersion(directiveStudent.getStudent().getCurriculumVersion());
                    break;
                case KASKKIRI_VALIS:
                    adjustPeriod(directiveStudent);
                    break;
                default:
                    break;
                }

                assertSameSchool(directive, directiveStudent.getStudentGroup() != null ? directiveStudent.getStudentGroup().getSchool() : null);
                assertSameSchool(directive, directiveStudent.getCurriculumVersion() != null ? directiveStudent.getCurriculumVersion().getCurriculum().getSchool() : null);
                assertSameSchool(directive, directiveStudent.getStudyPeriodStart() != null ? directiveStudent.getStudyPeriodStart().getStudyYear().getSchool() : null);
                assertSameSchool(directive, directiveStudent.getStudyPeriodEnd() != null ? directiveStudent.getStudyPeriodEnd().getStudyYear().getSchool() : null);
            }
            // remove possible existing directive students not included in update command
            students.removeAll(studentMapping.values());
            studentMapping.values().forEach(this::studentRemovedFromDirective);
        }
        return directiveRepository.save(directive);
    }

    public void delete(Directive directive) {
        assertModifyable(directive);
        // update possible applications as free for directives
        directive.getStudents().forEach(this::studentRemovedFromDirective);
        EntityUtil.deleteEntity(directiveRepository, directive);
    }

    /**
     * Fetch initial data for given directive type and for selected students
     *
     * @param user
     * @param cmd
     * @return
     */
    public DirectiveDto directivedata(HoisUserDetails user, DirectiveDataCommand cmd) {
        if(KASKKIRI_TYHIST.name().equals(cmd.getType())) {
            Directive canceled = directiveRepository.getOne(cmd.getCanceledDirective());
            assertSameSchool(canceled, em.getReference(School.class, user.getSchoolId()));
            DirectiveDto.DirectiveCancelDto dto = directiveInitialValues(new DirectiveDto.DirectiveCancelDto(), user, cmd);
            dto.setCanceledDirectiveType(EntityUtil.getCode(canceled.getType()));
            dto.setCanceledStudents(canceled.getStudents().stream().map(DirectiveViewStudentDto::of).collect(Collectors.toList()));
            // TODO fetch list of students which cannot canceled
            // dto.setChangedStudents()
            return dto;
        }

        DirectiveDto dto = directiveInitialValues(new DirectiveDto(), user, cmd);
        dto.setStudents(loadStudents(user.getSchoolId(), cmd));
        return dto;
    }

    private <T extends DirectiveDto> T directiveInitialValues(T dto, HoisUserDetails user, DirectiveDataCommand cmd) {
        EntityUtil.bindToDto(cmd, dto, "students");
        dto.setStatus(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name());
        dto.setInserted(LocalDateTime.now());
        dto.setInsertedBy(user.getUsername());
        // directive type as default headline
        Classifier type = classifierRepository.findOne(cmd.getType());
        dto.setHeadline(type != null ? type.getNameEt() : null);
        return dto;
    }

    public List<DirectiveStudentDto> loadStudents(Long schoolId, DirectiveDataCommand cmd) {
        if(isSais(cmd.getType())) {
            // if type is immat (vastuvõtt), use sais application for filling student data
            return saisLoadStudents(schoolId, cmd);
        }

        List<Long> studentIds = cmd.getStudents();
        if(studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyList();
        }

        DirectiveType directiveType = DirectiveType.valueOf(cmd.getType());
        if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_IMMATV.equals(directiveType)) {
            return Collections.emptyList();
        }

        Map<Long, Application> applications = Collections.emptyMap();
        Optional<ApplicationType> applicationType = applicationType(directiveType);
        if(applicationType.isPresent()) {
            applications = applicationRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.equal(root.get("student").get("school").get("id"), schoolId));
                filters.add(root.get("student").get("id").in(studentIds));
                // matching application type
                filters.add(cb.equal(root.get("type").get("code"), applicationType.get().name()));
                filters.add(root.get("status").get("code").in(APPLICATION_STATUS_FOR_DIRECTIVE));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }).stream().collect(Collectors.toMap(r -> EntityUtil.getId(r.getStudent()), r -> r, (o, n) -> o));
        }
        List<Student> students = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(root.get("id").in(studentIds));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        // for each student, create DirectiveStudentDto either from application or from student
        return createDirectiveStudents(directiveType, applications, students);
    }

    private static List<DirectiveStudentDto> createDirectiveStudents(DirectiveType directiveType, Map<Long, Application> applications, List<Student> students) {
        return students.stream().map(student -> {
            Application application = applications.get(student.getId());
            if(application != null) {
                return DirectiveStudentDto.of(application, directiveType);
            }
            return DirectiveStudentDto.of(student, directiveType);
        }).collect(Collectors.toList());
    }

    public List<DirectiveStudentSearchDto> searchStudents(Long schoolId, DirectiveStudentSearchCommand criteria) {
        if(isSais(criteria.getType())) {
            // if type is immat (vastuvõtt), then there is no persion selection
            return Collections.emptyList();
        }

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from student s inner join person person on s.person_id = person.id "+
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id inner join curriculum c on cv.curriculum_id = c.id "+
                "left outer join student_group sg on s.student_group_id = sg.id", new Sort("person.lastname", "person.firstname"));

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("person.firstname", "firstname", criteria.getFirstname());
        qb.optionalContains("person.lastname", "lastname", criteria.getLastname());
        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("s.id not in (select ds.student_id from directive_student ds where ds.directive_id = :directiveId)", "directiveId", criteria.getDirective());

        DirectiveType directiveType = DirectiveType.valueOf(criteria.getType());
        if(DirectiveType.ONLY_FROM_APPLICATION.contains(directiveType)) {
            criteria.setApplication(Boolean.TRUE);
        }
        Optional<ApplicationType> applicationType = applicationType(directiveType);
        if(applicationType.isPresent() && criteria.getApplication() != null) {
            boolean isApplication = Boolean.TRUE.equals(criteria.getApplication());
            String applicationSql = "select a.id from application a where a.student_id = s.id and a.type_code = :applicationType and a.status_code in (:applicationStatus)";
            qb.requiredCriteria(String.format(isApplication ? "exists (%s)" : "not exists (%s)", applicationSql), "applicationType", applicationType.get().name());
            qb.parameter("applicationStatus", APPLICATION_STATUS_FOR_DIRECTIVE);
        }

        // student has no unconfirmed directive of same type
        qb.requiredCriteria("not exists(select ds2.id from directive_student ds2 inner join directive d2 on ds2.directive_id = d2.id where ds2.student_id = s.id and d2.type_code = :directiveType and d2.status_code in (:directiveStatus))", "directiveType", directiveType.name());
        qb.parameter("directiveStatus", Arrays.asList(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name(), DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL.name()));

        // student has given status
        List<String> allowedStudentStatus = STUDENT_STATUS_FOR_DIRECTIVE_TYPE.get(directiveType);
        if(allowedStudentStatus != null && !allowedStudentStatus.isEmpty()) {
            qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", allowedStudentStatus);
        }

        // directive type specific filters
        switch(directiveType) {
        case KASKKIRI_AKAD:
            // nominal study end not passed
            qb.requiredCriteria("s.nominal_study_end > :now", "now", LocalDate.now());
            break;
        case KASKKIRI_OKOORM:
            qb.filter("c.is_higher = true");
            break;
        default:
            break;
        }

        List<?> data = qb.select("s.id, person.firstname, person.lastname, person.idcode, c.id as curriculum_id, c.name_et, c.name_en, sg.code", em).setMaxResults(STUDENTS_MAX).getResultList();
        return data.stream().map(r -> {
            DirectiveStudentSearchDto dto = new DirectiveStudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            if(DirectiveType.KASKKIRI_LOPET.equals(directiveType)) {
                dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 6)));
                dto.setStudentGroup(resultAsString(r, 7));
            }
            return dto;
        }).collect(Collectors.toList());
    }

    private List<DirectiveStudentDto> saisLoadStudents(Long schoolId, DirectiveDataCommand cmd) {
        List<DirectiveStudentDto> students = saisApplicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("saisAdmission").get("curriculumVersion").get("curriculum").get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("status").get("code"), SaisApplicationStatus.SAIS_AVALDUSESTAATUS_T.name()));
            if(cmd.getCurriculumVersion() != null && !cmd.getCurriculumVersion().isEmpty()) {
                filters.add(root.get("saisAdmission").get("curriculumVersion").get("id").in(cmd.getCurriculumVersion()));
            }
            if(cmd.getStudyLevel() != null && !cmd.getStudyLevel().isEmpty()) {
                filters.add(root.get("saisAdmission").get("studyLevel").get("code").in(cmd.getStudyLevel()));
            }

            // not on directive
            Subquery<Long> directiveQuery = query.subquery(Long.class);
            Root<DirectiveStudent> directiveRoot = directiveQuery.from(DirectiveStudent.class);
            directiveQuery = directiveQuery.select(directiveRoot.get("id")).where(cb.equal(directiveRoot.get("saisApplication").get("id"), root.get("id")));
            filters.add(cb.not(cb.exists(directiveQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, new PageRequest(0, STUDENTS_MAX, new Sort("lastname", "firstname"))).map(DirectiveStudentDto::of).getContent();

        // TODO autofill studentGroup
        return students;
    }

    public Page<DirectiveCoordinatorDto> search(Long schoolId, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_coordinator dc", pageable);
        qb.requiredCriteria("dc.school_id = :schoolId", "schoolId", schoolId);

        return JpaQueryUtil.pagingResult(qb, "dc.id, dc.name, dc.idcode, dc.version, dc.is_directive, dc.is_certificate, dc.is_certificate_default", em, pageable).map(r -> {
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
        coordinator.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(coordinator, form);
    }

    public DirectiveCoordinator save(DirectiveCoordinator coordinator, DirectiveCoordinatorForm form) {
        EntityUtil.bindToEntity(form, coordinator);
        return directiveCoordinatorRepository.save(coordinator);
    }

    public void delete(DirectiveCoordinator coordinator) {
        EntityUtil.deleteEntity(directiveCoordinatorRepository, coordinator);
    }

    private void setApplication(Long studentId, Long applicationId, DirectiveStudent student) {
        if(applicationId != null) {
            // verify application to be linked
            String directiveType = EntityUtil.getCode(student.getDirective().getType());
            Application app = em.getReference(Application.class, applicationId);
            ApplicationType applicationType = ApplicationType.valueOf(EntityUtil.getCode(app.getType()));
            // should be from same student and of matching type
            if(!EntityUtil.getId(app.getStudent()).equals(studentId) || !applicationType.directiveType().name().equals(directiveType)) {
                throw new AssertionFailedException("Student and/or directive type mismatch");
            }
            student.setApplication(app);

            if(!ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name().equals(EntityUtil.getCode(app.getType()))) {
                // student with application is included in directive
                // update application status to AVALDUS_STAATUS_KINNITAM
                app.setStatus(em.getReference(Classifier.class, ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name()));
                applicationRepository.save(app);
            }
        }
    }

    private void setPerson(DirectiveFormStudent formStudent, DirectiveStudent student) {
        String idcode = formStudent.getIdcode();
        if(StringUtils.hasText(idcode)) {
            // add new person if person idcode is not known
            Person person = personRepository.findByIdcode(idcode);
            // FIXME should update existing person?
            if(person == null) {
                person = new Person();
                person.setIdcode(idcode);
                person.setFirstname(formStudent.getFirstname());
                person.setLastname(formStudent.getLastname());
                person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
                person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(idcode)));
                SaisApplication sais = student.getSaisApplication();
                if(sais != null) {
                    // can copy additional fields from sais application
                    person.setForeignIdcode(sais.getForeignIdcode());
                    person.setAddress(sais.getAddress());
                    person.setPhone(sais.getPhone());
                    person.setEmail(sais.getEmail());
                    person.setCitizenship(sais.getCitizenship());
                    person.setResidenceCountry(sais.getResidenceCountry());
                }
                person = personRepository.save(person);
            }
            student.setPerson(person);
        }
    }

    private void setStudent(Long studentId, DirectiveStudent student) {
        if(studentId != null) {
            Student s = em.getReference(Student.class, studentId);
            // verify student to be linked
            Long schoolId = EntityUtil.getId(student.getDirective().getSchool());
            if(schoolId == null || !schoolId.equals(EntityUtil.getId(s.getSchool()))) {
                // not from same school
                throw new AssertionFailedException("School mismatch");
            }
            String directiveType = EntityUtil.getCode(student.getDirective().getType());
            List<String> allowedStudentStatus = STUDENT_STATUS_FOR_DIRECTIVE_TYPE.get(DirectiveType.valueOf(directiveType));
            if(allowedStudentStatus != null && !allowedStudentStatus.contains(EntityUtil.getCode(s.getStatus()))) {
                // wrong status of student for given directive type
                throw new AssertionFailedException("Student status for given directive mismatch");
            }
            student.setStudent(s);
        }
    }

    private void studentRemovedFromDirective(DirectiveStudent student) {
        Application app = student.getApplication();
        if(app != null && !ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name().equals(EntityUtil.getCode(app.getType()))) {
            // student with application is removed from directive
            // update application status to AVALDUS_STAATUS_YLEVAAT
            app.setStatus(em.getReference(Classifier.class, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name()));
            applicationRepository.save(app);
        }
    }

    private static void adjustPeriod(DirectiveStudent student) {
        if(Boolean.TRUE.equals(student.getIsPeriod())) {
            student.setStartDate(null);
            student.setEndDate(null);
        } else {
            student.setStudyPeriodStart(null);
            student.setStudyPeriodEnd(null);
        }
    }

    private static boolean isSais(String directiveType) {
        return DirectiveType.KASKKIRI_IMMATV.name().equals(directiveType);
    }

    private static void assertModifyable(Directive directive) {
        // assert directive state 'KOOSTAMISEL'
        if(!DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name().equals(EntityUtil.getNullableCode(directive.getStatus()))) {
            throw new AssertionFailedException("Directive status mismatch");
        }
    }

    private static Optional<ApplicationType> applicationType(DirectiveType type) {
        return Stream.of(ApplicationType.values()).filter(r -> type.equals(r.directiveType())).findFirst();
    }

    private static void assertSameSchool(Directive directive, School school) {
        if(school != null && !EntityUtil.getId(directive.getSchool()).equals(EntityUtil.getId(school))) {
            throw new AssertionFailedException("School mismatch");
        }
    }
}
