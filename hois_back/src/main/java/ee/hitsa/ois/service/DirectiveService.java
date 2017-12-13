package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_AKAD;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_AKADK;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_EKSMAT;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_ENNIST;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_FINM;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_IMMAT;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_IMMATV;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_LOPET;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OKAVA;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OKOORM;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OVORM;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_TYHIST;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_VALIS;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_A;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_K;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_O;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_V;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.SaisApplicationStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.EntityRemoveException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveDataCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudent;
import ee.hitsa.ois.web.commandobject.directive.DirectiveSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveStudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveDto;
import ee.hitsa.ois.web.dto.directive.DirectiveDto.DirectiveCancelDto;
import ee.hitsa.ois.web.dto.directive.DirectiveSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveViewDto;
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
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_AKAD, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_AKADK, EnumUtil.toNameList(OPPURSTAATUS_A));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_EKSMAT, EnumUtil.toNameList(OPPURSTAATUS_O, OPPURSTAATUS_V));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_ENNIST, EnumUtil.toNameList(OPPURSTAATUS_K));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_FINM, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_LOPET, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKAVA, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKOORM, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OVORM, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_VALIS, EnumUtil.toNameList(OPPURSTAATUS_O));
    }

    // application statuses which can added to directive
    private static final List<String> APPLICATION_STATUS_FOR_DIRECTIVE = EnumUtil.toNameList(ApplicationStatus.AVALDUS_STAATUS_ESIT, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT);

    @Autowired
    private EkisService ekisService;
    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SaisApplicationRepository saisApplicationRepository;
    @Autowired
    private Validator validator;

    /**
     * get directive record for editing
     *
     * @param directive
     * @return
     */
    public DirectiveDto get(Directive directive) {
        if(ClassifierUtil.equals(DirectiveType.KASKKIRI_TYHIST, directive.getType())) {
            return DirectiveCancelDto.of(directive, changedStudentsForCancel(directive.getCanceledDirective()));
        }
        return DirectiveDto.of(directive);
    }

    /**
     * get directive record for viewing
     *
     * @param user
     * @param directive
     * @return
     */
    public DirectiveViewDto getForView(HoisUserDetails user, Directive directive) {
        // filter for visible students: for school admin none, for student only itself
        Set<Long> filtered = null;
        if(user.isStudent()) {
            filtered = Collections.singleton(user.getStudentId());
        } else if(user.isRepresentative()) {
            // for representative all represented students of same school
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join student_representative sr on sr.student_id=s.id");
            qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
            qb.requiredCriteria("sr.person_id = :personId", "personId", user.getPersonId());
            List<?> data = qb.select("s.id", em).getResultList();
            filtered = StreamUtil.toMappedSet(r -> Long.valueOf(((Number)r).longValue()), data);
        }
        DirectiveViewDto dto = DirectiveViewDto.of(directive, filtered, !user.isSchoolAdmin());

        if(!ClassifierUtil.equals(DirectiveType.KASKKIRI_TYHIST, directive.getType())
           && ClassifierUtil.oneOf(directive.getStatus(), DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD,  DirectiveStatus.KASKKIRI_STAATUS_TYHISTATUD)
           && UserUtil.isSchoolAdmin(user, directive.getSchool())) {
            // look for optional canceling directives
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(DIRECTIVE_LIST_FROM).sort(new Sort(Direction.DESC, "d.inserted"));
            qb.requiredCriteria("d.school_id = :schoolId", "schoolId", EntityUtil.getId(directive.getSchool()));
            qb.requiredCriteria("d.canceled_directive_id = :canceledDirectiveId", "canceledDirectiveId", directive.getId());

            List<?> data = qb.select(DIRECTIVE_LIST_SELECT, em).getResultList();
            List<DirectiveSearchDto> directives = StreamUtil.toMappedList(r -> {
                DirectiveSearchDto d = new DirectiveSearchDto();
                d.setId(resultAsLong(r, 0));
                d.setHeadline(resultAsString(r, 1));
                d.setDirectiveNr(resultAsString(r, 2));
                d.setType(resultAsString(r, 3));
                d.setStatus(resultAsString(r, 4));
                d.setInserted(resultAsLocalDate(r, 5));
                d.setConfirmDate(resultAsLocalDate(r, 6));
                return d;
            }, data);
            dto.setCancelingDirectives(directives);
        }

        boolean canCancel = UserUtil.canCancelDirective(user, directive);
        if(canCancel) {
            // verify there are cancellable students still on the directive
            if(changedStudentsForCancel(directive).size() == directive.getStudents().size()) {
                canCancel = false;
            }
        }
        dto.setUserCanCancel(Boolean.valueOf(canCancel));
        dto.setUserCanEdit(Boolean.valueOf(UserUtil.canEditDirective(user, directive)));
        return dto;
    }

    /**
     * Search directives
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<DirectiveSearchDto> search(Long schoolId, DirectiveSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(DIRECTIVE_LIST_FROM).sort(pageable);

        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalCriteria("d.type_code in (:type)", "type", criteria.getType());
        qb.optionalContains("d.headline", "headline", criteria.getHeadline());
        qb.optionalContains("d.directive_nr", "directiveNr", criteria.getDirectiveNr());
        qb.optionalCriteria("d.confirm_date >= :confirmDateFrom", "confirmDateFrom", criteria.getConfirmDateFrom());
        qb.optionalCriteria("d.confirm_date <= :confirmDateThru", "confirmDateThru", criteria.getConfirmDateThru());
        qb.optionalCriteria("d.status_code in (:status)", "status", criteria.getStatus());

        qb.optionalCriteria("d.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("d.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("d.id in (select ds.directive_id from directive_student ds inner join student_group sg on ds.student_group_id=sg.id where upper(sg.code) like :studentGroup)", "studentGroup", criteria.getStudentGroup(), JpaQueryUtil::toContains);

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

    /**
     * Create new directive
     *
     * @param user
     * @param form
     * @return
     */
    public Directive create(HoisUserDetails user, DirectiveForm form) {
        Directive directive = new Directive();
        directive.setSchool(em.getReference(School.class, user.getSchoolId()));
        directive.setStatus(em.getReference(Classifier.class, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name()));
        return save(directive, form);
    }

    /**
     * Store directive
     *
     * @param directive
     * @param form
     * @return
     */
    public Directive save(Directive directive, DirectiveForm form) {
        assertModifyable(directive);

        EntityUtil.bindToEntity(form, directive, classifierRepository, "students");

        DirectiveCoordinator coordinator = EntityUtil.getOptionalOne(DirectiveCoordinator.class, form.getDirectiveCoordinator(), em);
        assertSameSchool(directive, coordinator != null ? coordinator.getSchool() : null);
        directive.setDirectiveCoordinator(coordinator);

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(directiveType.validationGroup() != null) {
            // second validation of input: specific for given directive type
            ValidationFailedException.throwOnError(validator.validate(form, directiveType.validationGroup()));
        }

        if(KASKKIRI_TYHIST.equals(directiveType) && directive.getId() == null) {
            // canceled directive can added only during directive create, check for same school
            directive.setCanceledDirective(EntityUtil.getOptionalOne(Directive.class, form.getCanceledDirective(), em));
            Directive canceledDirective = directive.getCanceledDirective();
            if(canceledDirective == null) {
                throw new AssertionFailedException("Canceled directive is missing");
            }

            assertSameSchool(directive, canceledDirective.getSchool());
            // check that there is no cancel directive already in "entry" state
            List<Long> canceled = em.createQuery("select d.id from Directive d where d.canceledDirective.id = ?1 and d.status.code = ?2", Long.class)
                    .setParameter(1, canceledDirective.getId()).setParameter(2, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name())
                    .setMaxResults(1).getResultList();
            if(!canceled.isEmpty()) {
                throw new ValidationFailedException("directive.duplicatecancel");
            }
        }

        List<DirectiveStudent> students = directive.getStudents();
        if(students == null) {
            directive.setStudents(students = new ArrayList<>());
        }

        if(KASKKIRI_TYHIST.equals(directiveType)) {
            // cancel directive maps data based on student id
            Map<Long, DirectiveStudent> studentMapping = StreamUtil.toMap(ds -> EntityUtil.getId(ds.getStudent()), students);
            Set<Long> originalStudentIds = StreamUtil.toMappedSet(ds -> EntityUtil.getId(ds.getStudent()), directive.getCanceledDirective().getStudents());
            for(Long studentId : StreamUtil.nullSafeList(form.getSelectedStudents())) {
                DirectiveStudent directiveStudent = studentMapping.remove(studentId);
                if(directiveStudent == null) {
                    AssertionFailedException.throwIf(!originalStudentIds.contains(studentId), "Unknown student for cancel directive");

                    directiveStudent = createDirectiveStudent(studentId, directive);
                    students.add(directiveStudent);
                }
            }
            // remove possible existing directive students not included in update command
            students.removeAll(studentMapping.values());
        } else {
            Map<Long, DirectiveStudent> studentMapping = StreamUtil.toMap(DirectiveStudent::getId, students);
            for(DirectiveFormStudent formStudent : StreamUtil.nullSafeList(form.getStudents())) {
                Long directiveStudentId = formStudent.getId();
                DirectiveStudent directiveStudent = directiveStudentId != null ? studentMapping.remove(directiveStudentId) : null;
                if(directiveStudent == null) {
                    Long studentId = formStudent.getStudent();
                    directiveStudent = createDirectiveStudent(studentId, directive);
                    setApplication(studentId, formStudent.getApplication(), directiveStudent);
                    if(KASKKIRI_IMMATV.equals(directiveType)) {
                        SaisApplication sais = em.getReference(SaisApplication.class, formStudent.getSaisApplication());
                        assertSameSchool(directive, sais.getSaisAdmission().getCurriculumVersion().getCurriculum().getSchool());
                        directiveStudent.setSaisApplication(sais);
                        setPerson(formStudent, directiveStudent);
                    }
                    students.add(directiveStudent);
                }

                if(KASKKIRI_IMMAT.equals(directiveType)) {
                    // directive type can add new persons (and later students) to the system
                    setPerson(formStudent, directiveStudent);
                }

                EntityUtil.bindToEntity(formStudent, directiveStudent, classifierRepository, "application", "directive", "person", "student");

                directiveStudent.setStudentGroup(EntityUtil.getOptionalOne(StudentGroup.class, formStudent.getStudentGroup(), em));
                directiveStudent.setCurriculumVersion(EntityUtil.getOptionalOne(CurriculumVersion.class, formStudent.getCurriculumVersion(), em));
                directiveStudent.setStudyPeriodStart(EntityUtil.getOptionalOne(StudyPeriod.class, formStudent.getStudyPeriodStart(), em));
                directiveStudent.setStudyPeriodEnd(EntityUtil.getOptionalOne(StudyPeriod.class, formStudent.getStudyPeriodEnd(), em));

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
        return EntityUtil.save(directive, em);
    }

    /**
     * Delete directive
     *
     * @param user
     * @param directive
     * @throws EntityRemoveException if there are references to directive
     */
    public void delete(HoisUserDetails user, Directive directive) {
        assertModifyable(directive);
        if(directive.getWdId() != null) {
            // sent to EKIS, delete there too
            Long directiveId = directive.getId();
            ekisService.deleteDirective(directiveId);
            /* realized in database using directive_id foreign key references directive(id) on delete set null
            em.createNativeQuery("update ws_ekis_log set directive_id=null where directive_id=?1")
                .setParameter(1, directiveId)
                .executeUpdate(); */
        }
        EntityUtil.setUsername(user.getUsername(), em);
        // update possible applications as free for directives
        directive.getStudents().forEach(this::studentRemovedFromDirective);
        EntityUtil.deleteEntity(directive, em);
    }

    /**
     * Fetch initial editing data for given directive type and for selected students
     *
     * @param user
     * @param cmd
     * @return
     */
    public DirectiveDto directivedata(HoisUserDetails user, DirectiveDataCommand cmd) {
        if(KASKKIRI_TYHIST.name().equals(cmd.getType())) {
            Directive canceled = em.getReference(Directive.class, cmd.getCanceledDirective());
            assertSameSchool(canceled, em.getReference(School.class, user.getSchoolId()));
            DirectiveDto.DirectiveCancelDto dto = directiveInitialValues(new DirectiveDto.DirectiveCancelDto(), user, cmd);
            dto.setCanceledDirectiveType(EntityUtil.getCode(canceled.getType()));
            dto.setCanceledStudents(StreamUtil.toMappedList(DirectiveViewStudentDto::of, canceled.getStudents()));
            dto.setChangedStudents(changedStudentsForCancel(canceled));
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
        dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(user.getUsername()));
        // directive type as default headline
        dto.setHeadline(em.getReference(Classifier.class, cmd.getType()).getNameEt());
        return dto;
    }

    private List<DirectiveStudentDto> loadStudents(Long schoolId, DirectiveDataCommand cmd) {
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

        ApplicationType applicationType = applicationType(directiveType);
        Map<Long, Application> applications = applicationType != null ? em.createQuery(
                "select a from Application a where a.student.school.id = ?1 and a.student.id in (?2) and a.type.code = ?3 and a.status.code in (?4)", Application.class)
            .setParameter(1, schoolId)
            .setParameter(2, studentIds)
            .setParameter(3, applicationType.name())
            .setParameter(4, APPLICATION_STATUS_FOR_DIRECTIVE)
            .getResultList()
            .stream().collect(Collectors.toMap(r -> EntityUtil.getId(r.getStudent()), r -> r, (o, n) -> o)) : Collections.emptyMap();

        List<Student> students = em.createQuery("select s from Student s where s.school.id = ?1 and s.id in (?2)", Student.class)
                .setParameter(1, schoolId).setParameter(2, studentIds).getResultList();

        // for each student, create DirectiveStudentDto either from application or from student
        return StreamUtil.toMappedList(student -> {
            Application application = applications.get(student.getId());
            if(application != null) {
                return DirectiveStudentDto.of(application, directiveType);
            }
            return DirectiveStudentDto.of(student, directiveType);
        }, students);
    }

    /**
     * Search students for adding into directive
     *
     * @param schoolId
     * @param criteria
     * @return
     */
    public List<DirectiveStudentSearchDto> searchStudents(Long schoolId, DirectiveStudentSearchCommand criteria) {
        if(isSais(criteria.getType())) {
            // if type is immat (vastuvõtt), then there is no student selection
            return Collections.emptyList();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student s inner join person person on s.person_id = person.id "+
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id inner join curriculum c on cv.curriculum_id = c.id "+
                "left outer join student_group sg on s.student_group_id = sg.id").sort("sg.code", "person.lastname", "person.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("person.firstname", "firstname", criteria.getFirstname());
        qb.optionalContains("person.lastname", "lastname", criteria.getLastname());
        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("s.id not in (select ds.student_id from directive_student ds where ds.directive_id = :directiveId)", "directiveId", criteria.getDirective());

        DirectiveType directiveType = DirectiveType.valueOf(criteria.getType());
        if(DirectiveType.ONLY_FROM_APPLICATION.contains(directiveType)) {
            criteria.setApplication(Boolean.TRUE);
        }
        ApplicationType applicationType = applicationType(directiveType);
        if(applicationType != null && criteria.getApplication() != null) {
            boolean isApplication = Boolean.TRUE.equals(criteria.getApplication());
            String applicationSql = "select a.id from application a where a.student_id = s.id and a.type_code = :applicationType and a.status_code in (:applicationStatus)";
            qb.requiredCriteria(String.format(isApplication ? "exists (%s)" : "not exists (%s)", applicationSql), "applicationType", applicationType);
            qb.parameter("applicationStatus", APPLICATION_STATUS_FOR_DIRECTIVE);
        }

        // student has no unconfirmed directive of same type
        qb.requiredCriteria("not exists(select ds2.id from directive_student ds2 inner join directive d2 on ds2.directive_id = d2.id where ds2.student_id = s.id and d2.type_code = :directiveType and d2.status_code in (:directiveStatus))", "directiveType", directiveType);
        qb.parameter("directiveStatus", EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL));

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

        List<?> data = qb.select("s.id, person.firstname, person.lastname, person.idcode, c.id as curriculum_id, cv.code, c.name_et, c.name_en, sg.code as student_group_code", em).setMaxResults(STUDENTS_MAX).getResultList();
        return StreamUtil.toMappedList(r -> {
            DirectiveStudentSearchDto dto = new DirectiveStudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            if(DirectiveType.KASKKIRI_LOPET.equals(directiveType)) {
                String curriculumVersionCode = resultAsString(r, 5);
                dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                        CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 6)),
                        CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 7))));
                dto.setStudentGroup(resultAsString(r, 8));
            }
            return dto;
        }, data);
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

        // suggest valid studentGroup, if possible
        List<StudentGroup> groups = findValidStudentGroups(schoolId);
        Map<Long, List<StudentGroup>> groupsByCurriculumVersion = groups.stream().filter(sg -> sg.getCurriculumVersion() != null).collect(Collectors.groupingBy(sg -> EntityUtil.getId(sg.getCurriculumVersion())));
        Map<Long, List<StudentGroup>> groupsByCurriculum = null;
        Map<Long, Integer> addedCount = new HashMap<>();

        for(DirectiveStudentDto s : students) {
            // first try to use student group with exact curriculum version
            StudentGroup sg = findStudentGroup(s, groupsByCurriculumVersion.get(s.getCurriculumVersion()), addedCount);
            if(sg == null) {
                // not found, try student groups with only curriculum defined
                if(groupsByCurriculum == null) {
                    // initialize lazily, because in vocational study there cannot be student groups without curriculum version
                    groupsByCurriculum = new HashMap<>();
                    for(StudentGroup g : groups) {
                        if(g.getCurriculumVersion() == null) {
                            for(CurriculumVersion cv : g.getCurriculum().getVersions()) {
                                groupsByCurriculum.computeIfAbsent(EntityUtil.getId(cv), key -> new ArrayList<>()).add(g);
                            }
                        }
                    }
                }
                sg = findStudentGroup(s, groupsByCurriculum.get(s.getCurriculumVersion()), addedCount);
            }
            if(sg != null) {
                s.setStudentGroup(sg.getId());
            }
        }
        return students;
    }

    /**
     * Search directive coordinators
     *
     * @param schoolId
     * @param pageable
     * @return
     */
    public Page<DirectiveCoordinatorDto> searchCoordinators(Long schoolId, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_coordinator dc").sort(pageable);
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

    /**
     * Create new directive coordinator
     *
     * @param user
     * @param form
     * @return
     */
    public DirectiveCoordinator create(HoisUserDetails user, DirectiveCoordinatorForm form) {
        DirectiveCoordinator coordinator = new DirectiveCoordinator();
        coordinator.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(coordinator, form);
    }

    /**
     * Store directive coordinator
     *
     * @param coordinator
     * @param form
     * @return
     */
    public DirectiveCoordinator save(DirectiveCoordinator coordinator, DirectiveCoordinatorForm form) {
        EntityUtil.bindToEntity(form, coordinator);
        return EntityUtil.save(coordinator, em);
    }

    /**
     * Delete directive coordinator
     *
     * @param user
     * @param coordinator
     * @throws EntityRemoveException if there are references to directive coordinator
     */
    public void delete(HoisUserDetails user, DirectiveCoordinator coordinator) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(coordinator, em);
    }

    List<Long> changedStudentsForCancel(Directive directive) {
        // fetch list of students which cannot canceled
        // skip history for direct student modifications (from student form)
        Query q = em.createNativeQuery("with recursive history_id(id) as (" +
            "select s.student_history_id from student s inner join directive_student ds on s.id = ds.student_id and ds.directive_id = :directiveId " +
            "union all " +
            "select sh.prev_student_history_id from student_history sh inner join history_id h on sh.id = h.id left outer join directive_student ds on ds.student_history_id = h.id where ds.id is null" +
            ") " +
            "select student_id from directive_student where directive_id = :directiveId and student_history_id not in (select id from history_id)");
        q.setParameter("directiveId", directive.getId());
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> Long.valueOf(((Number)r).longValue()), data);
    }

    private void setApplication(Long studentId, Long applicationId, DirectiveStudent directiveStudent) {
        if(applicationId != null) {
            // verify application to be linked
            String directiveType = EntityUtil.getCode(directiveStudent.getDirective().getType());
            Application app = em.getReference(Application.class, applicationId);
            ApplicationType applicationType = ApplicationType.valueOf(EntityUtil.getCode(app.getType()));
            // should be from same student and of matching type
            if(!EntityUtil.getId(app.getStudent()).equals(studentId) || !applicationType.directiveType().name().equals(directiveType)) {
                throw new AssertionFailedException("Student and/or directive type mismatch");
            }
            directiveStudent.setApplication(app);

            if(!ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_KINNITAM, app.getType())) {
                // student with application is included in directive
                // update application status to AVALDUS_STAATUS_KINNITAM
                app.setStatus(em.getReference(Classifier.class, ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name()));
                EntityUtil.save(app, em);
            }
        }
    }

    private void setPerson(DirectiveFormStudent formStudent, DirectiveStudent directiveStudent) {
        String idcode = formStudent.getIdcode();
        if(StringUtils.hasText(idcode)) {
            // add new person if person idcode is not known
            Person person = personRepository.findByIdcode(idcode);
            SaisApplication sais = directiveStudent.getSaisApplication();
            if(person == null) {
                person = new Person();
                person.setIdcode(idcode);
                if(sais != null) {
                    personFromSaisApplication(person, sais);
                } else {
                    person.setFirstname(formStudent.getFirstname());
                    person.setLastname(formStudent.getLastname());
                    person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
                    person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(idcode)));
                }
                person = EntityUtil.save(person, em);
            } else if(sais != null) {
                // update existing person from sais application
                personFromSaisApplication(person, sais);
            }
            directiveStudent.setPerson(person);
        }
    }

    private static void personFromSaisApplication(Person person, SaisApplication sais) {
        // copy fields from sais application
        person.setFirstname(sais.getFirstname());
        person.setLastname(sais.getLastname());
        person.setBirthdate(sais.getBirthdate());
        person.setSex(sais.getSex());
        person.setForeignIdcode(sais.getForeignIdcode());
        person.setAddress(sais.getAddress());
        person.setPhone(sais.getPhone());
        person.setEmail(sais.getEmail());
        person.setCitizenship(sais.getCitizenship());
        person.setResidenceCountry(sais.getResidenceCountry());
        person.setAddressAds(sais.getAddressAds());
    }

    private DirectiveStudent createDirectiveStudent(Long studentId, Directive directive) {
        DirectiveStudent directiveStudent = new DirectiveStudent();
        directiveStudent.setDirective(directive);
        directiveStudent.setCanceled(Boolean.FALSE);
        if(studentId != null) {
            Student student = em.getReference(Student.class, studentId);
            // verify student to be linked
            Long schoolId = EntityUtil.getId(directiveStudent.getDirective().getSchool());
            if(schoolId == null || !schoolId.equals(EntityUtil.getId(student.getSchool()))) {
                // not from same school
                throw new AssertionFailedException("School mismatch for directive student");
            }
            String directiveType = EntityUtil.getCode(directiveStudent.getDirective().getType());
            List<String> allowedStudentStatus = STUDENT_STATUS_FOR_DIRECTIVE_TYPE.get(DirectiveType.valueOf(directiveType));
            if(allowedStudentStatus != null && !allowedStudentStatus.contains(EntityUtil.getCode(student.getStatus()))) {
                // wrong status of student for given directive type
                throw new AssertionFailedException("Student status for given directive mismatch");
            }
            directiveStudent.setStudent(student);
            directiveStudent.setPerson(student.getPerson());
        }
        return directiveStudent;
    }

    private void studentRemovedFromDirective(DirectiveStudent directiveStudent) {
        Application app = directiveStudent.getApplication();
        if(app != null && !ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_YLEVAAT, app.getType())) {
            // student with application is removed from directive
            // update application status to AVALDUS_STAATUS_YLEVAAT
            app.setStatus(em.getReference(Classifier.class, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name()));
            EntityUtil.save(app, em);
        }
    }

    private List<StudentGroup> findValidStudentGroups(Long schoolId) {
        LocalDate now = LocalDate.now();
        return em.createQuery("select sg from StudentGroup sg where sg.school.id = ?1 and (sg.validFrom is null or sg.validFrom <= ?2) and (sg.validThru is null or sg.validThru >= ?2)", StudentGroup.class)
                .setParameter(1, schoolId).setParameter(2, now).getResultList();
    }

    private static StudentGroup findStudentGroup(DirectiveStudentDto directiveStudent, List<StudentGroup> groups, Map<Long, Integer> addedCount) {
        if(groups != null && !groups.isEmpty()) {
            // first try to use student group with limit of students
            for(StudentGroup sg : groups) {
                if(sg.getPlaces() != null && studentGroupMatches(directiveStudent, sg)) {
                    // check maximum number of students
                    int added = addedCount.getOrDefault(sg.getId(), Integer.valueOf(0)).intValue();
                    if(sg.getStudents().size() + added < sg.getPlaces().intValue()) {
                        addedCount.put(sg.getId(), Integer.valueOf(added + 1));
                        return sg;
                    }
                }
            }

            // try to use student group without limit
            for(StudentGroup sg : groups) {
                if(sg.getPlaces() == null && studentGroupMatches(directiveStudent, sg)) {
                    return sg;
                }
            }
        }
        return null;
    }

    private static boolean studentGroupMatches(DirectiveStudentDto student, StudentGroup group) {
        return Objects.equals(student.getStudyForm(), EntityUtil.getCode(group.getStudyForm())) &&
               Objects.equals(student.getLanguage(), EntityUtil.getCode(group.getLanguage()));
    }

    private static void adjustPeriod(DirectiveStudent directiveStudent) {
        if(Boolean.TRUE.equals(directiveStudent.getIsPeriod())) {
            directiveStudent.setStartDate(null);
            directiveStudent.setEndDate(null);
        } else {
            directiveStudent.setStudyPeriodStart(null);
            directiveStudent.setStudyPeriodEnd(null);
        }
    }

    private static boolean isSais(String directiveType) {
        return DirectiveType.KASKKIRI_IMMATV.name().equals(directiveType);
    }

    private static void assertModifyable(Directive directive) {
        // only directive state 'KOOSTAMISEL' allows modification
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, directive.getStatus()), "Directive status mismatch");
    }

    private static ApplicationType applicationType(DirectiveType type) {
        return Stream.of(ApplicationType.values()).filter(r -> type.equals(r.directiveType())).findFirst().orElse(null);
    }

    private static void assertSameSchool(Directive directive, School school) {
        if(school != null && !EntityUtil.getId(directive.getSchool()).equals(EntityUtil.getId(school))) {
            throw new AssertionFailedException("School mismatch");
        }
    }
}
