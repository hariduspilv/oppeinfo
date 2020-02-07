package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_AKAD;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_AKADK;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_EKSMAT;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_ENNIST;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_FINM;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_IMMAT;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_IMMATV;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_INDOK;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_INDOKLOP;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_KIITUS;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_KYLALIS;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_LOPET;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_NOOMI;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OKAVA;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OKOORM;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OTEGEVUS;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_OVORM;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_PRAKTIK;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_STIPTOET;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_STIPTOETL;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_TUGI;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_TUGILOPP;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_TYHIST;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_VALIS;
import static ee.hitsa.ois.enums.DirectiveType.KASKKIRI_VALISKATK;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_A;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_K;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_O;
import static ee.hitsa.ois.enums.StudentStatus.OPPURSTAATUS_V;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.directive.DirectiveStudentModule;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.SaisApplicationStatus;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.enums.ScholarshipType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.EntityRemoveException;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.message.SupportServiceEnding;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApplicationUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.DirectiveUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.ErrorForField;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveDataCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudent;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudentModule;
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
import ee.hitsa.ois.web.dto.directive.ExistingDirectiveStudentDto;
import ee.hitsa.ois.web.dto.directive.ExistingIndividualCurriculumModuleDto;
import ee.hitsa.ois.web.dto.directive.IndividualCurriculumModuleDto;
import ee.hitsa.ois.web.dto.directive.ScholarshipApplicationSelectDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalStudyProgramme;

/**
 * What you need to know:
 * 
 * - Enum.valueOf:
 * It throws an error if there is no such element in Enum. So try to use EnumUtil.valueOf which returns null if not found.
 * 
 * - Edit and View form:
 * For edit form used DirectiveDto while for view form DirectiveViewDto (same about DirectiveStudent...)
 * 
 * - About TUGI directive:
 * There is 3 important controlling points.
 * 1) During searchStudents where it tries to find suitable students
 * 2) During mapping of searchStudents results
 * 3) (ApplicationService) During checking if it is possible to remove confirmation for application. 
 *
 */
@Transactional
@Service
public class DirectiveService {
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private static final String DIRECTIVE_LIST_SELECT =
            "d.id, d.headline, d.directive_nr, d.type_code, d.status_code, d.inserted, d.confirm_date";
    private static final String DIRECTIVE_LIST_FROM =
            "from directive d inner join classifier type on d.type_code=type.code inner join classifier status on d.status_code=status.code";

    // maximum number of students returned by search for one directive
    private static final int STUDENTS_MAX = 100;

    // corresponding application type for directive type
    private static final EnumMap<DirectiveType, ApplicationType> APPLICATION_TYPE = new EnumMap<>(DirectiveType.class);

    // required student status for given directive type
    private static final Map<DirectiveType, List<String>> STUDENT_STATUS_FOR_DIRECTIVE_TYPE = new HashMap<>();
    static {
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_AKAD, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_AKADK, EnumUtil.toNameList(OPPURSTAATUS_A));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_EKSMAT, EnumUtil.toNameList(OPPURSTAATUS_O, OPPURSTAATUS_V));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_ENNIST, EnumUtil.toNameList(OPPURSTAATUS_K));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_FINM, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_INDOK, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_INDOKLOP, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_LOPET, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKAVA, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OKOORM, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OVORM, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_STIPTOET, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_VALIS, EnumUtil.toNameList(OPPURSTAATUS_O));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_VALISKATK, EnumUtil.toNameList(OPPURSTAATUS_O, OPPURSTAATUS_V));
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_STIPTOETL, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_KIITUS, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_NOOMI, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_OTEGEVUS, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_PRAKTIK, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_TUGI, StudentStatus.STUDENT_STATUS_ACTIVE);
        STUDENT_STATUS_FOR_DIRECTIVE_TYPE.put(KASKKIRI_TUGILOPP, StudentStatus.STUDENT_STATUS_ACTIVE);

        for(ApplicationType appType : ApplicationType.values()) {
            if(appType.directiveType() != null) {
                APPLICATION_TYPE.put(appType.directiveType(), appType);
            }
        }
    }

    private static final List<DirectiveType> REVOCATION_DIRECTIVE_TYPES = Arrays.asList(KASKKIRI_INDOKLOP,
            KASKKIRI_STIPTOETL, KASKKIRI_TUGILOPP, KASKKIRI_VALISKATK);

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
    private Validator validator;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private StudentService studentService;

    /**
     * get directive record for editing
     *
     * @param directive
     * @return
     */
    public DirectiveDto get(HoisUserDetails user, Directive directive) {
        DirectiveDto dto;
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));

        if (KASKKIRI_TYHIST.equals(directiveType)) {
            dto = DirectiveCancelDto.of(directive, changedStudentsForCancel(directive.getCanceledDirective()));
        } else {
            dto = DirectiveDto.of(directive);
            if (KASKKIRI_STIPTOET.equals(directiveType)) {
                setScholarshipApplications(dto);
            } else if (KASKKIRI_LOPET.equals(directiveType)) {
                setOccupations(dto);
            } else if (REVOCATION_DIRECTIVE_TYPES.contains(directiveType)) {
                setExistingDirectiveStudents(dto, directiveType);
            }
        }
        dto.setCanEditDirective(Boolean.valueOf(UserUtil.canEditDirective(user, directive)));
        return dto;
    }

    private void setExistingDirectiveStudents(DirectiveDto dto, DirectiveType directiveType) {
        List<? extends DirectiveFormStudent> studentDtos = dto.getStudents();
        List<Long> studentIds = StreamUtil.toMappedList(s -> s.getStudent(), studentDtos);

        if (!studentIds.isEmpty()) {
            Map<Long, List<ExistingDirectiveStudentDto>> existingDirectiveStudents =
                    existingDirectiveStudents(dto.getId(), directiveType, studentIds);
            studentDtos.forEach(studentDto -> {
                studentDto.setExistingDirectiveStudents(existingDirectiveStudents.get(studentDto.getStudent()));
            });
        }
    }

    private void setScholarshipApplications(DirectiveDto dto) {
        List<? extends DirectiveFormStudent> studentDtos = dto.getStudents();
        List<Long> studentIds = StreamUtil.toMappedList(s -> s.getStudent(), studentDtos);

        if (!studentIds.isEmpty()) {
            Map<Long, List<ScholarshipApplicationSelectDto>> scholarshipApplications = studentScholarshipApplications(
                    dto.getId(), dto.getScholarshipType(), studentIds);
            studentDtos.forEach(studentDto -> {
                studentDto.setScholarshipApplications(scholarshipApplications.get(studentDto.getStudent()));
            });
        }
    }

    private void setOccupations(DirectiveDto dto) {
        List<? extends DirectiveFormStudent> studentDtos = dto.getStudents();
        Set<Long> studentIds = StreamUtil.toMappedSet(s -> s.getStudent(), studentDtos);

        if (!studentIds.isEmpty()) {
            setStudentOccupations(studentDtos, studentIds, dto.getIsHigher().booleanValue());
        }
    }

    private void setStudentOccupations(List<? extends DirectiveFormStudent> studentDtos, Set<Long> studentIds, boolean isHigher) {
        Map<Long, List<String>> occupations = occupations(studentIds, isHigher);
        Map<Long, List<String>> partOccupations = partOccupations(studentIds, isHigher);
        Map<Long, Map<String, List<String>>> specialities = specialities(studentIds, isHigher);

        for (DirectiveFormStudent dto : studentDtos) {
            Long studentId = dto.getStudent();
            if (occupations.containsKey(studentId)) {
                dto.setOccupations(occupations.get(studentId));
            }
            if (partOccupations.containsKey(studentId)) {
                if (dto.getOccupations() == null) {
                    dto.setOccupations(new ArrayList<>());
                }
                dto.getOccupations().addAll(partOccupations.get(studentId));
            }
            if (specialities.containsKey(studentId)) {
                dto.setSpecialities(specialities.get(studentId));
            }
        }
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

        if (ClassifierUtil.equals(DirectiveType.KASKKIRI_LOPET, directive.getType())) {
            setViewOccupations(dto);
        }

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
        dto.setUserCanConfirm(Boolean.valueOf(userCanConfirm(user, directive)));
        dto.setUserCanEdit(Boolean.valueOf(UserUtil.canEditDirective(user, directive)));
        return dto;
    }

    private void setViewOccupations(DirectiveViewDto dto) {
        List<DirectiveViewStudentDto> studentDtos = dto.getStudents();
        Set<Long> studentIds = StreamUtil.toMappedSet(s -> s.getStudent(), studentDtos);

        if (!studentIds.isEmpty()) {
            setViewStudentOccupations(studentDtos, studentIds, dto.getIsHigher().booleanValue());
        }
    }

    private void setViewStudentOccupations(List<DirectiveViewStudentDto> studentDtos, Set<Long> studentIds,
            boolean isHigher) {
        Map<Long, List<String>> occupations = occupations(studentIds, isHigher);
        Map<Long, List<String>> partOccupations = partOccupations(studentIds, isHigher);
        Map<Long, Map<String, List<String>>> specialities = specialities(studentIds, isHigher);

        for (DirectiveViewStudentDto dto : studentDtos) {
            Long studentId = dto.getStudent();
            if (occupations.containsKey(studentId)) {
                dto.setOccupations(occupations.get(studentId));
            }
            if (partOccupations.containsKey(studentId)) {
                if (dto.getOccupations() == null) {
                    dto.setOccupations(new ArrayList<>());
                }
                dto.getOccupations().addAll(partOccupations.get(studentId));
            }
            if (specialities.containsKey(studentId)) {
                dto.setSpecialities(specialities.get(studentId));
            }
        }
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
        return save(user, directive, form);
    }

    /**
     * Store directive
     *
     * @param directive
     * @param form
     * @return
     */
    public Directive save(HoisUserDetails user, Directive directive, DirectiveForm form) {
        assertModifyable(directive);

        EntityUtil.bindToEntity(form, directive, classifierRepository, "students");

        DirectiveCoordinator coordinator = EntityUtil.getOptionalOne(DirectiveCoordinator.class, form.getDirectiveCoordinator(), em);
        assertSameSchool(directive, coordinator != null ? coordinator.getSchool() : null);
        directive.setDirectiveCoordinator(coordinator);

        List<ErrorForField> errors = new ArrayList<>();
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(directiveType.validationGroup() != null) {
            // second validation of input: specific for given directive type
            for(ConstraintViolation<DirectiveForm> e : validator.validate(form, directiveType.validationGroup())) {
                errors.add(new ErrorForField(e.getMessage(), e.getPropertyPath().toString()));
            }
        }

        if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_KYLALIS.equals(directiveType)) {
            long rowNum = 0;
            for(DirectiveFormStudent dfs : StreamUtil.nullSafeList(form.getStudents())) {
                if(!StringUtils.hasText(dfs.getSex())) {
                    errors.add(new ErrorForField(Required.MESSAGE, DirectiveConfirmService.propertyPath(rowNum, "sex")));
                }
                if(!StringUtils.hasText(dfs.getCitizenship())) {
                    errors.add(new ErrorForField(Required.MESSAGE, DirectiveConfirmService.propertyPath(rowNum, "citizenship")));
                }
                rowNum++;
            }
        }
        if (DirectiveType.KASKKIRI_IMMAT.equals(directiveType) || DirectiveType.KASKKIRI_IMMATV.equals(directiveType) || DirectiveType.KASKKIRI_KYLALIS.equals(directiveType)) {
            long rowNum = 0;
            Map<Long, Long> curriculumVersionCurriculum = new HashMap<>();
            Set<Long> uniqueRows = new LinkedHashSet<>();
            for (DirectiveFormStudent dfs : StreamUtil.nullSafeList(form.getStudents())) {
                String idcode = dfs.getIdcode();
                String foreignIdcode = dfs.getForeignIdcode();
                if (!StringUtils.hasText(idcode) && ClassifierUtil.COUNTRY_ESTONIA.equals(dfs.getCitizenship())) {
                    errors.add(new ErrorForField(Required.MESSAGE, DirectiveConfirmService.propertyPath(rowNum, "idcode")));
                }
                String citizenshipCode = dfs.getCitizenship();
                Long curriculumVersionId = dfs.getCurriculumVersion();
                if ((StringUtils.hasText(idcode) || (StringUtils.hasText(foreignIdcode) && citizenshipCode != null) || 
                        (!StringUtils.hasText(dfs.getIdcode()) && !StringUtils.hasText(dfs.getForeignIdcode()) &&
                                StringUtils.hasText(dfs.getFirstname()) && StringUtils.hasText(dfs.getLastname()) &&
                                dfs.getBirthdate() != null && StringUtils.hasText(dfs.getCitizenship()))) && curriculumVersionId != null) {
                    Long curriculumId = em.createQuery("select cv.curriculum.id from CurriculumVersion cv"
                            + " where cv.id = ?1", Long.class)
                            .setParameter(1, curriculumVersionId)
                            .getSingleResult();
                    curriculumVersionCurriculum.put(curriculumVersionId, curriculumId);
                    Person person = null;
                    if (StringUtils.hasText(idcode)) {
                        person = personRepository.findByIdcode(idcode);
                    } else {
                        person = findForeignPerson(dfs);
                    }
                    if (person != null) {
                        if (studentExists(EntityUtil.getId(directive.getSchool()), EntityUtil.getId(person), curriculumId)) {
                            uniqueRows.add(Long.valueOf(rowNum));
                        }
                    }
                }
                rowNum++;
            }
            rowNum = 0;
            // Check student uniqueness
            for (DirectiveFormStudent dfs : StreamUtil.nullSafeList(form.getStudents())) {
                for (DirectiveFormStudent dfs2 : form.getStudents()) {
                    if (!dfs2.equals(dfs) && 
                            // id codes match -> check currriculum versions
                            ((StringUtils.hasText(dfs.getIdcode()) && dfs.getIdcode().equals(dfs2.getIdcode())) || 
                            // foreign id codes and citizenship match -> check currriculum versions
                            (StringUtils.hasText(dfs.getForeignIdcode()) &&
                                    dfs.getCitizenship() != null &&
                                    dfs.getCitizenship().equals(dfs2.getCitizenship()) &&
                                    dfs.getForeignIdcode().equals(dfs2.getForeignIdcode())) ||
                            // when id codes are missing, compare names, citizenship and birthdate -> check curriculum version
                            (!StringUtils.hasText(dfs.getIdcode()) && !StringUtils.hasText(dfs2.getIdcode()) &&
                                    !StringUtils.hasText(dfs.getForeignIdcode()) && !StringUtils.hasText(dfs2.getForeignIdcode()) &&
                                    StringUtils.hasText(dfs.getFirstname()) && StringUtils.hasText(dfs.getLastname()) &&
                                    dfs.getBirthdate() != null && StringUtils.hasText(dfs.getCitizenship()) &&
                                    dfs.getFirstname().equalsIgnoreCase(dfs2.getFirstname()) &&
                                    dfs.getLastname().equalsIgnoreCase(dfs2.getLastname()) &&
                                    dfs.getBirthdate().equals(dfs2.getBirthdate()) &&
                                    dfs.getCitizenship().equals(dfs2.getCitizenship())
                                    ))) {
                        if (dfs.getCurriculumVersion() != null && dfs2.getCurriculumVersion() != null
                                && curriculumVersionCurriculum.get(dfs.getCurriculumVersion()).equals(curriculumVersionCurriculum.get(dfs2.getCurriculumVersion()))) {
                            uniqueRows.add(Long.valueOf(rowNum));
                        }
                    }
                }
                rowNum++;
            }
            for (Long existRow : uniqueRows) {
                errors.add(DirectiveConfirmService.createStudentExistsError(existRow.longValue()));
            }
        }
        if(!errors.isEmpty()) {
            throw new ValidationFailedException(errors);
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
            boolean isHigher = directive.getIsHigher().booleanValue();
            Map<Long, DirectiveStudent> studentMapping = StreamUtil.toMap(DirectiveStudent::getId, students);
            Set<Long> fetchedStudentIds = StreamUtil.toMappedSet(DirectiveFormStudent::getStudent, form.getStudents());
            Set<Long> cumLaude = !fetchedStudentIds.isEmpty() && KASKKIRI_LOPET.equals(directiveType) ?
                    cumLaudes(fetchedStudentIds, isHigher) : Collections.emptySet();
            Set<Long> studentsWithCertificate = !fetchedStudentIds.isEmpty() && KASKKIRI_LOPET.equals(directiveType) ?
                    occupationCertificates(fetchedStudentIds) : Collections.emptySet();
            List<DirectiveStudent> messagesToStudents = new ArrayList<>();
            for(DirectiveFormStudent formStudent : StreamUtil.nullSafeList(form.getStudents())) {
                Long directiveStudentId = formStudent.getId();
                DirectiveStudent directiveStudent = directiveStudentId != null ? studentMapping.remove(directiveStudentId) : null;
                if(directiveStudent == null) {
                    // new student on directive
                    Long studentId = formStudent.getStudent();
                    directiveStudent = createDirectiveStudent(studentId, directive);
                    if(KASKKIRI_IMMATV.equals(directiveType)) {
                        SaisApplication sais = em.getReference(SaisApplication.class, formStudent.getSaisApplication());
                        assertSameSchool(directive, sais.getSaisAdmission().getCurriculumVersion().getCurriculum().getSchool());
                        directiveStudent.setSaisApplication(sais);
                        setPerson(formStudent, directiveStudent);
                    } else {
                        setApplication(studentId, formStudent.getApplication(), directiveStudent);
                    }
                    students.add(directiveStudent);
                    messagesToStudents.add(directiveStudent);
                }

                if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_KYLALIS.equals(directiveType)) {
                    // directive type can add new persons (and later students) to the system
                    setPerson(formStudent, directiveStudent);
                }

                EntityUtil.bindToEntity(formStudent, directiveStudent, classifierRepository, "application", "directive",
                        "person", "student", "occupations", "modules");

                directiveStudent.setStudentGroup(EntityUtil.getOptionalOne(StudentGroup.class, formStudent.getStudentGroup(), em));
                directiveStudent.setCurriculumVersion(EntityUtil.getOptionalOne(CurriculumVersion.class, formStudent.getCurriculumVersion(), em));
                directiveStudent.setStudyPeriodStart(EntityUtil.getOptionalOne(StudyPeriod.class, formStudent.getStudyPeriodStart(), em));
                directiveStudent.setStudyPeriodEnd(EntityUtil.getOptionalOne(StudyPeriod.class, formStudent.getStudyPeriodEnd(), em));

                Student student = directiveStudent.getStudent();
                Long studentId = EntityUtil.getNullableId(student);
                switch (directiveType) {
                case KASKKIRI_KYLALIS:
                    directiveStudent.setApelSchool(EntityUtil.getOptionalOne(ApelSchool.class, formStudent.getApelSchoolId(), em));
                    break;
                case KASKKIRI_AKAD:
                    adjustPeriod(directiveStudent);
                    break;
                case KASKKIRI_INDOK:
                    bindDirectiveStudentModules(directiveStudent, formStudent);
                    break;
                case KASKKIRI_INDOKLOP:
                case KASKKIRI_VALISKATK:
                    directiveStudent.setDirectiveStudent(EntityUtil.getOptionalOne(DirectiveStudent.class,
                            formStudent.getDirectiveStudent(), em));
                    break;
                case KASKKIRI_LOPET:
                    directiveStudent.setCurriculumVersion(student.getCurriculumVersion());
                    directiveStudent.setIsCumLaude(Boolean.valueOf(cumLaude.contains(studentId)));
                    directiveStudent.setCurriculumGrade(getCurriculumGrade(studentId));
                    directiveStudent.setIsOccupationExamPassed(Boolean.valueOf(studentsWithCertificate.contains(studentId)));
                    break;
                case KASKKIRI_STIPTOET:
                    ScholarshipApplication sa = EntityUtil.getOptionalOne(ScholarshipApplication.class,
                            formStudent.getScholarshipApplication(), em);
                    if (sa != null) {
                        assertSameSchool(directiveStudent.getDirective(), sa.getStudent().getSchool());
                        directiveStudent.setScholarshipApplication(sa);
                        directiveStudent.setBankAccount(sa.getBankAccount());
                    }
                    directiveStudent.setStartDate(formStudent.getStartDate());
                    directiveStudent.setEndDate(formStudent.getEndDate());
                    directiveStudent.setAmountPaid(formStudent.getAmountPaid());
                    break;
                case KASKKIRI_STIPTOETL:
                    DirectiveStudent ds = EntityUtil.getOptionalOne(DirectiveStudent.class, formStudent.getDirectiveStudent(), em);
                    directiveStudent.setDirectiveStudent(ds);
                    directiveStudent.setScholarshipApplication(ds != null ? ds.getScholarshipApplication() : null);
                    directiveStudent.setStartDate(ds != null ? ds.getStartDate() : null);
                    directiveStudent.setEndDate(ds != null ? ds.getEndDate() : null);
                    break;
                case KASKKIRI_TUGILOPP:
                    directiveStudent.setDirectiveStudent(
                            EntityUtil.getOptionalOne(DirectiveStudent.class, formStudent.getDirectiveStudent(), em));
                case KASKKIRI_VALIS:
                    directiveStudent.setApelSchool(EntityUtil.getOptionalOne(ApelSchool.class, formStudent.getApelSchoolId(), em));
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
            if (!KASKKIRI_TUGI.equals(directiveType)) {
                studentMapping.values().forEach(ds -> studentRemovedFromDirective(user, ds));
            }
            if(!DirectiveType.KASKKIRI_IMMAT.equals(directiveType) && !DirectiveType.KASKKIRI_IMMATV.equals(directiveType) && !DirectiveType.KASKKIRI_KYLALIS.equals(directiveType)) {
                for (DirectiveStudent directiveStudent : messagesToStudents) {
                    StudentDirectiveCreated data = new StudentDirectiveCreated(directiveStudent);
                    automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_KASKKIRI, directiveStudent.getStudent(), data);
                }
            }
        }
        return EntityUtil.save(directive, em);
    }

    public boolean studentExists(Long schoolId, Long personId, Long curriculumId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.person_id = :personId", "personId", personId);
        qb.requiredCriteria("s.status_code in :status", "status", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("cv.curriculum_id = :curriculumId", "curriculumId", curriculumId);
        List<?> result = qb.select("s.id", em).getResultList();
        return !result.isEmpty();
    }

    public void bindDirectiveStudentModules(DirectiveStudent directiveStudent, DirectiveFormStudent formStudent) {
        EntityUtil.bindEntityCollection(directiveStudent.getModules(), DirectiveStudentModule::getId,
                formStudent.getModules(), DirectiveFormStudentModule::getId, moduleForm -> {
                    DirectiveStudentModule module = EntityUtil.bindToEntity(moduleForm, new DirectiveStudentModule(),
                            "curriculumVersionOmodule");
                    module.setDirectiveStudent(directiveStudent);
                    module.setCurriculumVersionOmodule(em.getReference(CurriculumVersionOccupationModule.class,
                            moduleForm.getCurriculumVersionOmodule()));
                    return module;
                }, (moduleForm, module) -> {
                    EntityUtil.bindToEntity(moduleForm, module);
                });
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
        if (!ClassifierUtil.equals(KASKKIRI_TUGI, directive.getType())) {
            directive.getStudents().forEach(ds -> studentRemovedFromDirective(user, ds));   
        }
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
        String headline = em.getReference(Classifier.class, cmd.getType()).getNameEt();
        String directiveType = dto.getType();
        if(DirectiveType.KASKKIRI_STIPTOET.name().equals(directiveType) || DirectiveType.KASKKIRI_STIPTOETL.name().equals(directiveType)) {
            if(dto.getScholarshipType() != null) {
                String scholarshipType = em.getReference(Classifier.class, dto.getScholarshipType()).getNameEt();
                headline = String.format("%s (%s)", headline, scholarshipType.toLowerCase());
            }
        }
        dto.setHeadline(headline);
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
        
        if (KASKKIRI_TUGI.equals(directiveType)) {
            return loadStudentsTugi(schoolId, cmd);
        }
        List<DirectiveStudentDto> result = new ArrayList<>();
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
        result = StreamUtil.toMappedList(student -> {
            Application application = applications.get(student.getId());
            if(application != null) {
                return DirectiveStudentDto.of(application, directiveType);
            }
            return DirectiveStudentDto.of(student, directiveType);
        }, students);
        if(KASKKIRI_LOPET.equals(directiveType)) {
            Set<Long> fetchedStudentIds = StreamUtil.toMappedSet(DirectiveStudentDto::getStudent, result);
            if (!fetchedStudentIds.isEmpty()) {
                boolean isHigher = cmd.getIsHigher().booleanValue();
                Set<Long> cumLaude = cumLaudes(fetchedStudentIds, isHigher);
                Set<Long> studentsWithCertificate = occupationCertificates(fetchedStudentIds);
                for (DirectiveStudentDto dto : result) {
                    Long studentId = dto.getStudent();
                    dto.setIsCumLaude(Boolean.valueOf(cumLaude.contains(studentId)));
                    CurriculumGrade curriculumGrade = getCurriculumGrade(studentId);
                    if (curriculumGrade != null) {
                        dto.setCurriculumGrade(AutocompleteResult.of(curriculumGrade));
                    }
                    dto.setIsOccupationExamPassed(Boolean.valueOf(studentsWithCertificate.contains(studentId)));
                }
                setStudentOccupations(result, fetchedStudentIds, isHigher);
            }
        } else if (KASKKIRI_STIPTOET.equals(directiveType)) {
            Map<Long, List<ScholarshipApplicationSelectDto>> scholarshipApplications = studentScholarshipApplications(
                    cmd.getDirective(), cmd.getScholarshipType(), studentIds);
            for (DirectiveStudentDto dto : result) {
                List<ScholarshipApplicationSelectDto> studentApplications = scholarshipApplications
                        .get(dto.getStudent());
                if (studentApplications != null) {
                    dto.setScholarshipApplications(studentApplications);
                }
            }
        } else if (REVOCATION_DIRECTIVE_TYPES.contains(directiveType)) {
            Map<Long, List<ExistingDirectiveStudentDto>> existingDirectiveStudents =
                    existingDirectiveStudents(cmd.getDirective(), directiveType, studentIds);
            result.forEach(studentDto -> {
                studentDto.setExistingDirectiveStudents(existingDirectiveStudents.get(studentDto.getStudent()));
            });
        }
        return result;
    }

    private Map<Long, List<ExistingDirectiveStudentDto>> existingDirectiveStudents(Long directiveId,
            DirectiveType directiveType, List<Long> studentIds) {
        Map<Long, List<ExistingDirectiveStudentDto>> existingDirectiveStudents;
        switch (directiveType) {
            case KASKKIRI_INDOKLOP:
                existingDirectiveStudents = studentExistingIndividualCurriculums(directiveId, studentIds);
                break;
            case KASKKIRI_STIPTOETL:
                existingDirectiveStudents = studentExistingScholarshipDirectives(directiveId, studentIds);
                break;
            case KASKKIRI_TUGILOPP:
                existingDirectiveStudents = studentExistingSupportServiceDirectives(directiveId, studentIds);
                break;
            case KASKKIRI_VALISKATK:
                existingDirectiveStudents = studentExistingExternalStudyDirectives(directiveId, studentIds);
                break;
            default:
                existingDirectiveStudents = new HashMap<>();
                break;
        }
        return existingDirectiveStudents;
    }

    /**
     * Load students for TUGI directive.
     * 
     * @param schoolId
     * @param cmd
     * @return
     */
    private List<DirectiveStudentDto> loadStudentsTugi(Long schoolId, DirectiveDataCommand cmd) {
        if (cmd.getStudentApplication() == null || cmd.getStudentApplication().isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, Application> applications = cmd.getStudentApplication().keySet().stream()
                .collect(Collectors.toMap(id -> id, id -> em.getReference(Application.class, cmd.getStudentApplication().get(id)), (n, o) -> n));

        List<Student> students = em.createQuery("select s from Student s where s.school.id = ?1 and s.id in (?2)", Student.class)
                .setParameter(1, schoolId).setParameter(2, cmd.getStudentApplication().keySet()).getResultList();

        // for each student, create DirectiveStudentDto from application
        List<DirectiveStudentDto> result = StreamUtil.toMappedList(student -> {
            return DirectiveStudentDto.of(applications.get(student.getId()), KASKKIRI_TUGI);
        }, students);
        return result;
    }

    private Map<Long, List<ExistingDirectiveStudentDto>> studentExistingIndividualCurriculums(Long directiveId,
            List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d "
                + "join directive_student ds on ds.directive_id = d.id");
        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_INDOK);

        qb.filter("((ds.canceled = false and d.status_code = :directiveStatus and ds.end_date >= :today)"
                + (directiveId != null ? " or ds.id in (select ds2.directive_student_id from directive d2 "
                + "join directive_student ds2 on ds2.directive_id = d2.id where d2.id = :directiveId))" : ")"));
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()));

        qb.filter("(not exists (select 1 from directive d3 join directive_student ds3 on ds3.directive_id = d3.id "
                + "where ds3.canceled = false and d3.type_code = :lopDirectiveType and ds3.student_id = ds.student_id "
                + "and ds3.directive_student_id = ds.id)"
                + (directiveId != null ? " or ds.id in (select ds4.directive_student_id from directive d4 "
                + "join directive_student ds4 on ds4.directive_id = d4.id where d4.id = :directiveId))" : ")"));
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_INDOKLOP.name());

        if (directiveId != null) {
            qb.parameter("directiveId", directiveId);
        }

        List<?> data = qb.select("ds.student_id, ds.id, d.directive_nr, ds.start_date, ds.end_date", em)
                .getResultList();
        if (data.isEmpty()) {
            return new HashMap<>();
        }

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            ExistingDirectiveStudentDto eds = new ExistingDirectiveStudentDto();
            eds.setId(resultAsLong(r, 1));
            eds.setDirectiveNr(resultAsString(r, 2));
            eds.setStartDate(resultAsLocalDate(r, 3));
            eds.setEndDate(resultAsLocalDate(r, 4));
            return eds;
        }, Collectors.toList())));
    }
    
    private Map<Long, List<ExistingDirectiveStudentDto>> studentExistingExternalStudyDirectives(Long directiveId,
            List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d "
                + "join directive_student ds on ds.directive_id = d.id "
                + "left join study_period spEnd on spEnd.id = ds.study_period_end_id "
                + "left join study_period spStart on spStart.id = ds.study_period_start_id");

        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS.name());

        qb.filter("((ds.canceled = false and d.status_code = :directiveStatus " +
                "and coalesce(spStart.start_date, ds.start_date) <= :today and coalesce(spEnd.end_date, ds.end_date) >= :today)"
                + (directiveId != null ? " or ds.id in (select ds2.directive_student_id from directive d2 "
                + "join directive_student ds2 on ds2.directive_id = d2.id where d2.id = :directiveId))" : ")"));
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()));

        qb.filter("(not exists (select 1 from directive d3 join directive_student ds3 on ds3.directive_id = d3.id "
                + "where ds3.canceled = false and d3.type_code = :lopDirectiveType and ds3.student_id = ds.student_id "
                + "and ds3.directive_student_id = ds.id)"
                + (directiveId != null ? " or ds.id in (select ds4.directive_student_id from directive d4 "
                    + "join directive_student ds4 on ds4.directive_id = d4.id where d4.id = :directiveId))" : ")"));
        qb.parameter("lopDirectiveType", KASKKIRI_VALISKATK.name());

        if (directiveId != null) {
            qb.parameter("directiveId", directiveId);
        }

        List<?> data = qb.select("ds.student_id, ds.id, d.directive_nr, coalesce(ds.start_date, spStart.start_date) start_date, "
                + "coalesce(spEnd.end_date, ds.end_date) end_date", em).getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            ExistingDirectiveStudentDto eds = new ExistingDirectiveStudentDto();
            eds.setId(resultAsLong(r, 1));
            eds.setDirectiveNr(resultAsString(r, 2));
            eds.setStartDate(resultAsLocalDate(r, 3));
            eds.setEndDate(resultAsLocalDate(r, 4));
            return eds;
        }, Collectors.toList())));
    }
    
    private Map<Long, List<ExistingDirectiveStudentDto>> studentExistingSupportServiceDirectives(Long directiveId, List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d "
                + "join directive_student ds on ds.directive_id = d.id");
        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_TUGI);

        qb.filter("((ds.canceled = false and d.status_code = :directiveStatus and ds.end_date >= :today)"
                + (directiveId != null ? " or ds.id in (select ds2.directive_student_id from directive d2 "
                + "join directive_student ds2 on ds2.directive_id = d2.id where d2.id = :directiveId))" : ")"));
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()));

        qb.filter("(not exists (select 1 from directive d3 join directive_student ds3 on ds3.directive_id = d3.id "
                + "where ds3.canceled = false and d3.type_code = :lopDirectiveType and ds3.student_id = ds.student_id "
                + "and ds3.directive_student_id = ds.id)"
                + (directiveId != null ? " or ds.id in (select ds4.directive_student_id from directive d4 "
                + "join directive_student ds4 on ds4.directive_id = d4.id where d4.id = :directiveId))" : ")"));
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_TUGILOPP.name());

        if (directiveId != null) {
            qb.parameter("directiveId", directiveId);
        }

        List<?> data = qb.select("ds.student_id, ds.id, d.directive_nr, ds.start_date, ds.end_date", em)
                .getResultList();
        if (data.isEmpty()) {
            return new HashMap<>();
        }

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            ExistingDirectiveStudentDto eds = new ExistingDirectiveStudentDto();
            eds.setId(resultAsLong(r, 1));
            eds.setDirectiveNr(resultAsString(r, 2));
            eds.setStartDate(resultAsLocalDate(r, 3));
            eds.setEndDate(resultAsLocalDate(r, 4));
            return eds;
        }, Collectors.toList())));
    }

    private Map<Long, List<ScholarshipApplicationSelectDto>> studentScholarshipApplications(Long directiveId,
            String scholarshipType, List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from scholarship_application sa "
                + "join scholarship_term st on st.id = sa.scholarship_term_id "
                + "join student s on s.id = sa.student_id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("st.type_code = :scholarshipType", "scholarshipType", scholarshipType);

        qb.filter("(sa.status_code = :scholarshipStatus and not exists "
                + "(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                + "where d.type_code = 'KASKKIRI_STIPTOET' and ds.scholarship_application_id = sa.id and ds.canceled = false)"
                + (directiveId != null
                        ? " or sa.id in (select ds2.scholarship_application_id from directive_student ds2 "
                                + "join directive d2 on d2.id = ds2.directive_id "
                                + "where ds2.directive_id = :directiveId and d2.type_code = :directiveType))"
                        : ")"));

        qb.parameter("scholarshipStatus", ScholarshipStatus.STIPTOETUS_STAATUS_A.name());
        if (directiveId != null) {
            qb.parameter("directiveId", directiveId);
            qb.parameter("directiveType", DirectiveType.KASKKIRI_STIPTOET.name());
        }

        List<?> data = qb.select("s.id student_id, sa.id scholarship_application_id, st.type_code, st.name_et, "
                + "sa.scholarship_from, sa.scholarship_thru, st.payment_start, st.payment_end, "
                + "sa.bank_account, st.amount_paid", em).getResultList();
        if (data.isEmpty()) {
            return new HashMap<>();
        }

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            String type = resultAsString(r, 2);
            ScholarshipApplicationSelectDto dto = new ScholarshipApplicationSelectDto();
            dto.setId(resultAsLong(r, 1));
            dto.setNameEt(resultAsString(r, 3));
            dto.setStartDate(type.equals(ScholarshipType.STIPTOETUS_ERI.name()) ? resultAsLocalDate(r, 4)
                    : resultAsLocalDate(r, 6));
            dto.setEndDate(type.equals(ScholarshipType.STIPTOETUS_ERI.name()) ? resultAsLocalDate(r, 5)
                    : resultAsLocalDate(r, 7));
            dto.setBankAccount(resultAsString(r, 8));
            dto.setAmountPaid(resultAsDecimal(r, 9));
            return dto;
        }, Collectors.toList())));
    }

    private Map<Long, List<ExistingDirectiveStudentDto>> studentExistingScholarshipDirectives(Long directiveId,
            List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d "
                + "join directive_student ds on ds.directive_id = d.id "
                + "join scholarship_application sa on sa.id = ds.scholarship_application_id "
                + "join scholarship_term st on st.id = sa.scholarship_term_id");
        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_STIPTOET);

        qb.filter("((ds.canceled = false and d.status_code = :directiveStatus and ds.end_date >= :today)"
                + (directiveId != null
                        ? " or ds.id in (select ds2.directive_student_id from directive d2 "
                                + "join directive_student ds2 on ds2.directive_id = d2.id where d2.id = :directiveId))"
                        : ")"));
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()));

        qb.filter("(not exists (select 1 from directive d3 join directive_student ds3 on ds3.directive_id = d3.id "
                + "where ds3.canceled = false and d3.type_code = :lopDirectiveType and ds3.student_id = ds.student_id "
                + "and ds3.directive_student_id = ds.id)"
                + (directiveId != null
                        ? " or ds.id in (select ds4.directive_student_id from directive d4 "
                                + "join directive_student ds4 on ds4.directive_id = d4.id where d4.id = :directiveId))"
                        : ")"));
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_STIPTOETL.name());

        if (directiveId != null) {
            qb.parameter("directiveId", directiveId);
        }

        List<?> data = qb.select("ds.student_id, ds.id, d.directive_nr, sa.id scholarship_application_id, "
                + "st.name_et, ds.start_date, ds.end_date, d.confirm_date", em).getResultList();
        if (data.isEmpty()) {
            return new HashMap<>();
        }

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            ExistingDirectiveStudentDto eds = new ExistingDirectiveStudentDto();
            eds.setId(resultAsLong(r, 1));
            eds.setDirectiveNr(resultAsString(r, 2));
            eds.setScholarshipApplicationId(resultAsLong(r, 3));
            eds.setScholarshipTermNameEt(resultAsString(r, 4));
            eds.setStartDate(resultAsLocalDate(r, 5));
            eds.setEndDate(resultAsLocalDate(r, 6));
            eds.setConfirmDate(resultAsLocalDate(r, 7));
            return eds;
        }, Collectors.toList())));
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
        DirectiveType directiveType = DirectiveType.valueOf(criteria.getType());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student s inner join person person on s.person_id = person.id " +
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id inner join curriculum c on cv.curriculum_id = c.id " +
                (DirectiveType.KASKKIRI_LOPET == directiveType ? "join student_curriculum_completion scc on scc.student_id = s.id " : "") +
                "left outer join student_group sg on s.student_group_id = sg.id").sort("sg.code", "person.lastname", "person.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("person.firstname || ' ' || person.lastname", "name", criteria.getName());
        qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("s.id not in (select ds.student_id from directive_student ds where ds.directive_id = :directiveId)", "directiveId", criteria.getDirective());
        qb.optionalCriteria("c.is_higher = :isHigher", "isHigher", criteria.getIsHigher());
        qb.optionalCriteria("s.student_group_id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        if (DirectiveType.KASKKIRI_TYHIST != directiveType) qb.requiredCriteria("s.type_code != :typeCode", "typeCode", StudentType.OPPUR_K.name());
        
        boolean scholarship = isScholarship(criteria.getType());
        if(scholarship) {
            // exists "accepted" scholarship application which is not on another directive
            qb.requiredCriteria("exists(select a.id from scholarship_application a join scholarship_term t on a.scholarship_term_id = t.id "+
                    "where a.student_id = s.id and t.type_code = :scholarshipTermType and a.status_code = :scholarshipApplicationAccepted "+
                    "and not exists(select 1 from directive_student dsa join directive dsad on dsa.directive_id = dsad.id where dsa.scholarship_application_id = a.id and dsa.canceled = false and dsad.type_code = :scholarshipDirectiveType))",
                    "scholarshipTermType", criteria.getScholarshipType());
            qb.parameter("scholarshipApplicationAccepted", ScholarshipStatus.STIPTOETUS_STAATUS_A.name());
            qb.parameter("scholarshipDirectiveType", criteria.getType());
        } else {
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
        }

        // student has no unconfirmed directive of same type
        // check scholarshipType too, if it's STIPTOET || STIPTOETL directive
        String scholarshipTypeSql = scholarship ? " and d2.scholarship_type_code = :scholarshipType" : "";
        // For TUGI and TUGILOPP there is no limit for directives.
        if (!Arrays.asList(KASKKIRI_TUGI, KASKKIRI_TUGILOPP).contains(directiveType)) {
            String existingDirectiveSql = String.format("not exists(select ds2.id from directive_student ds2 inner join directive d2 on ds2.directive_id = d2.id where ds2.student_id = s.id and d2.type_code = :directiveType and d2.status_code in (:directiveStatus)%s)", scholarshipTypeSql);
            qb.requiredCriteria(existingDirectiveSql, "directiveType", directiveType);
            qb.parameter("directiveStatus", EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL));
        }
        if(scholarship) {
            qb.parameter("scholarshipType", criteria.getScholarshipType());
        }

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
        case KASKKIRI_EKSMAT:
            // no confirmed scholarship
            qb.requiredCriteria("not exists(select a.id from directive_student ds join directive d on ds.directive_id = d.id join scholarship_application a on ds.scholarship_application_id = a.id join scholarship_term t on a.scholarship_term_id = t.id " +
                    "where ds.canceled = false and d.status_code = :scholarshipDirectiveStatus and t.is_academic_leave = false and a.student_id = s.id and coalesce(ds.end_date, to_Date(to_char(now(),'dd.mm.yyyy'),'dd.mm.yyyy') - 1) >= to_Date(to_char(now(),'dd.mm.yyyy'),'dd.mm.yyyy') " +
                    "and not exists(select 1 from directive_student ds2 join directive d2 on ds2.directive_id = d2.id and ds2.canceled = false and ds2.scholarship_application_id = ds.scholarship_application_id and d2.status_code = :scholarshipDirectiveStatus and d2.type_code = :scholarshipEndDirectiveType))",
                    "scholarshipDirectiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
            qb.parameter("scholarshipEndDirectiveType", DirectiveType.KASKKIRI_STIPTOETL.name());
            break;
        case KASKKIRI_INDOKLOP:
            // should exists confirmed and not canceled KASKKIRI_INDOK that
            // doesn't have already connected KASKKIRI_INDOKLOP
            qb.filter("exists (select d_lop.id from directive d_lop "
                    + "join directive_student ds_lop on ds_lop.directive_id = d_lop.id and ds_lop.canceled = false and ds_lop.student_id = s.id "
                    + "where d_lop.type_code = :lopDirectiveType and d_lop.status_code = :lopDirectiveStatus and ds_lop.end_date > :today "
                    + "and not exists (select 1 from directive d_lop2 join directive_student ds_lop2 on ds_lop2.directive_id = d_lop2.id "
                    + "where ds_lop2.canceled = false and d_lop2.type_code = :existingLopDirective and ds_lop2.student_id = s.id "
                    + "and ds_lop2.directive_student_id = ds_lop.id))");
            qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_INDOK.name());
            qb.parameter("lopDirectiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
            qb.parameter("today", LocalDate.now());
            qb.parameter("existingLopDirective", DirectiveType.KASKKIRI_INDOKLOP.name());
            break;
        case KASKKIRI_LOPET:
            qb.filter("scc.study_backlog = 0");
            break;
        case KASKKIRI_OKOORM:
            qb.filter("c.is_higher = true");
            break;
        case KASKKIRI_STIPTOETL:
            // should exists confirmed and not canceled KASKKIRI_STIPTOET that
            // doesn't have already connected KASKKIRI_INDOKLOP
            qb.filter("exists (select d_lop.id from directive d_lop "
                    + "join directive_student ds_lop on ds_lop.directive_id = d_lop.id and ds_lop.canceled = false and ds_lop.student_id = s.id "
                    + "where d_lop.type_code = :lopDirectiveType and d_lop.status_code = :lopDirectiveStatus and ds_lop.end_date > :today "
                    + "and d_lop.scholarship_type_code = :scholarshipTypeCode and not exists "
                    + "(select 1 from directive d_lop2 join directive_student ds_lop2 on ds_lop2.directive_id = d_lop2.id "
                    + "where ds_lop2.canceled = false and d_lop2.type_code = :existingLopDirective and ds_lop2.student_id = s.id "
                    + "and ds_lop2.directive_student_id = ds_lop.id))");
            qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_STIPTOET.name());
            qb.parameter("lopDirectiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
            qb.parameter("today", LocalDate.now());
            qb.parameter("scholarshipTypeCode", criteria.getScholarshipType());
            qb.parameter("existingLopDirective", DirectiveType.KASKKIRI_STIPTOETL.name());
            break;
        case KASKKIRI_KIITUS:
        case KASKKIRI_INDOK:
        case KASKKIRI_NOOMI:
        case KASKKIRI_OTEGEVUS:
            qb.filter("c.is_higher = false");
            break;
        case KASKKIRI_TUGI:
            // For TUGI directive there is different control about applications.
            // We need that there was no application which is used in a confirmed TUGI directive.
            // Only then we can show this student for this type directive.
            qb.filter("exists (select a.id from application a "
                    + "where not exists (select 1 from directive_student ds_app join directive d_app on d_app.id = ds_app.directive_id "
                    + "where d_app.type_code = :directiveTypeApp and d_app.status_code in (:directiveStatusApp) and ds_app.application_id = a.id and ds_app.canceled is not true) and "
                    + "a.student_id = s.id and a.type_code = :applicationType and a.status_code in (:applicationStatus) and a.is_decided = true)");
            qb.parameter("directiveTypeApp", DirectiveType.KASKKIRI_TUGI.name());
            qb.parameter("directiveStatusApp", EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL,
                    DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL));
            qb.parameter("applicationType", ApplicationType.AVALDUS_LIIK_TUGI.name());
            qb.parameter("applicationStatus", ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
            qb.filter("c.is_higher = false");
            break;
        case KASKKIRI_TUGILOPP:
            qb.filter("exists (select 1 from directive_student ds_lopp join directive d_lopp on d_lopp.id = ds_lopp.directive_id "
                    + "where ds_lopp.student_id = s.id and d_lopp.type_code = :directiveTypeApp and d_lopp.status_code = :directiveStatusApp "
                    + "and ds_lopp.end_date > :today and ds_lopp.canceled = false and not exists "
                    + "(select 1 from directive_student ds_lopp2 join directive d_lopp2 on d_lopp2.id = ds_lopp2.directive_id "
                    + "where ds_lopp2.canceled = false and d_lopp2.type_code = :existingLopDirectiveType and ds_lopp2.student_id = s.id "
                    + "and ds_lopp2.directive_student_id = ds_lopp.id))");
            qb.parameter("directiveTypeApp", KASKKIRI_TUGI.name());
            qb.parameter("directiveStatusApp", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
            qb.parameter("today", LocalDate.now());
            qb.parameter("existingLopDirectiveType", KASKKIRI_TUGILOPP.name());
            qb.filter("c.is_higher = false");
            break;
        case KASKKIRI_VALISKATK:
            // should exists confirmed and not canceled KASKKIRI_VALIS directive
            qb.filter("exists (select d_valis.id from directive d_valis "
                    + "join directive_student ds_valis on (ds_valis.directive_id = d_valis.id and ds_valis.canceled = false and ds_valis.student_id = s.id) "
                    + "left join study_period spEnd on ds_valis.study_period_end_id = spEnd.id "
                    + "left join study_period spStart on ds_valis.study_period_start_id = spStart.id "
                    + "where d_valis.type_code = :valisDirectiveType "
                    + "and d_valis.status_code = :valisDirectiveStatus "
                    + "and coalesce(ds_valis.end_date, spEnd.end_date) >= :today "
                    + "and coalesce(ds_valis.start_date, spStart.start_date) <= :today "
                    + "and not exists(select d_katk from directive d_katk "
                        + "join directive_student ds_katk "
                            + "on (d_katk.id = ds_katk.directive_id and ds_valis.id = ds_katk.directive_student_id) "
                        + "and d_katk.type_code = :katkDirectiveType "
                        + "and d_katk.status_code = :valisDirectiveStatus))");
            qb.requiredCriteria("s.status_code = :studentStatus", "studentStatus", StudentStatus.OPPURSTAATUS_V.name());
            qb.parameter("valisDirectiveType", DirectiveType.KASKKIRI_VALIS.name());
            qb.parameter("katkDirectiveType", DirectiveType.KASKKIRI_VALISKATK.name());
            qb.parameter("valisDirectiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
            qb.parameter("today", LocalDate.now());
            break;
        default:
            break;
        }

        List<?> data = qb.select("s.id, person.firstname, person.lastname, person.idcode, c.id as curriculum_id, cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.type_code as studentType", em).setMaxResults(STUDENTS_MAX).getResultList();
        return StreamUtil.toMappedList(r -> {
            DirectiveStudentSearchDto dto = new DirectiveStudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullnameOptionalGuest(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 9)));
            dto.setIdcode(resultAsString(r, 3));
            if(!DirectiveType.KASKKIRI_IMMAT.equals(directiveType) || !DirectiveType.KASKKIRI_IMMATV.equals(directiveType)) {
                String curriculumVersionCode = resultAsString(r, 5);
                dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                        CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 6)),
                        CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 7))));
                dto.setStudentGroup(resultAsString(r, 8));
            }
            if (KASKKIRI_TUGI == directiveType) {
                JpaNativeQueryBuilder applicationQB = new JpaNativeQueryBuilder("from application a").sort("a.representative_confirmed asc").groupBy("a.id");
                // Remove rows where we have connection with directive already (excluding "tyhistatud" directives)
                applicationQB.requiredCriteria("not exists (select 1 from directive_student ds_app join directive d_app on d_app.id = ds_app.directive_id " + 
                        " where d_app.status_code in (:directiveStatusApp) and ds_app.application_id = a.id and ds_app.canceled is not true )", "directiveStatusApp",
                        EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL));
                applicationQB.requiredCriteria("a.student_id = :studentId", "studentId", dto.getId());
                applicationQB.requiredCriteria("a.type_code = :applicationType", "applicationType", ApplicationType.AVALDUS_LIIK_TUGI.name());
                applicationQB.requiredCriteria("a.status_code = :applicationStatus", "applicationStatus", ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
                applicationQB.filter("a.is_decided = true");
                
                List<?> applications = applicationQB.select("a.id, case when length(a.other_text) > 40 then left(a.other_text, 37) || '...' else a.other_text end as other_text, a.representative_confirmed", em).getResultList();
                dto.setApplications(StreamUtil.toMappedList(a -> {
                    DirectiveStudentSearchDto.ApplicationMinDto applicationDto = new DirectiveStudentSearchDto.ApplicationMinDto();
                    applicationDto.setId(resultAsLong(a, 0));
                    String description = resultAsString(a, 1);
                    applicationDto.setDescription(description != null ? description.replaceAll("\n", " ") : null);
                    applicationDto.setConfirmed(resultAsLocalDateTime(a, 2));
                    return applicationDto;
                }, applications));
            } else if (KASKKIRI_VALIS == directiveType) {
                JpaNativeQueryBuilder applicationQB = new JpaNativeQueryBuilder("from application a").groupBy("a.id");
                // Remove rows where we have connection with directive already (excluding "tyhistatud" directives)
                applicationQB.requiredCriteria("not exists (select 1 from directive_student ds_app join directive d_app on d_app.id = ds_app.directive_id " + 
                        " where d_app.status_code in (:directiveStatusApp) and ds_app.application_id = a.id and ds_app.canceled is not true )", "directiveStatusApp",
                        EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL));
                applicationQB.requiredCriteria("a.student_id = :studentId", "studentId", dto.getId());
                applicationQB.requiredCriteria("a.type_code = :applicationType", "applicationType", ApplicationType.AVALDUS_LIIK_VALIS.name());
                applicationQB.requiredCriteria("a.status_code = :applicationStatus", "applicationStatus", ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
                
                List<?> applications = applicationQB.select("a.id, a.representative_confirmed", em).getResultList();
                dto.setApplications(StreamUtil.toMappedList(a -> {
                    DirectiveStudentSearchDto.ApplicationMinDto applicationDto = new DirectiveStudentSearchDto.ApplicationMinDto();
                    applicationDto.setId(resultAsLong(a, 0));
                    applicationDto.setConfirmed(resultAsLocalDateTime(a, 1));
                    return applicationDto;
                }, applications));
            }

            return dto;
        }, data);
    }

    private List<DirectiveStudentDto> saisLoadStudents(Long schoolId, DirectiveDataCommand cmd) {
        JpaQueryBuilder<SaisApplication> qb = new JpaQueryBuilder<>(SaisApplication.class, "sa").sort(new Sort("lastname", "firstname"));
        qb.requiredCriteria("sa.saisAdmission.curriculumVersion.curriculum.school.id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sa.status.code = :statusCode", "statusCode", SaisApplicationStatus.SAIS_AVALDUSESTAATUS_T);
        qb.optionalCriteria("sa.saisAdmission.curriculumVersion.id in (:curriculumVersion)", "curriculumVersion", cmd.getCurriculumVersion());
        qb.optionalCriteria("sa.saisAdmission.studyLevel.code in (:studyLevel)", "studyLevel", cmd.getStudyLevel());
        qb.filter("not exists(select ds2 from DirectiveStudent ds2 where ds2.canceled = false and ds2.saisApplication.id = sa.id)");
        qb.filter("(sa.saisAdmission.is_archived is null or sa.saisAdmission.is_archived = false)");

        List<DirectiveStudentDto> students = StreamUtil.toMappedList(DirectiveStudentDto::of, qb.select(em).setMaxResults(STUDENTS_MAX).getResultList());

        // suggest valid studentGroup, if possible
        List<StudentGroup> groups = findValidStudentGroups(schoolId, true);
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
                        if(g.getCurriculumVersion() == null && g.getCurriculum() != null) {
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
        if (Boolean.TRUE.equals(coordinator.getIsCertificateDefault())) {
            StringBuilder sql = new StringBuilder("select dc.id from directive_coordinator dc where dc.school_id = ?1 and dc.is_certificate_default");
            if (coordinator.getId() != null) {
                sql.append(" and dc.id != ?2");
            }
            Query query = em.createNativeQuery(sql.toString()).setParameter(1, coordinator.getSchool().getId());
            if (coordinator.getId() != null) {
                query.setParameter(2, coordinator.getId());
            }
            if (query.getResultList().size() > 0) {
                throw new ValidationFailedException("directive.coordinator.alreadyhascertificatedefaultcoordinator");
            }
        
        }
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

            // For TUGI it should be ignored!
            if(!ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_KINNITAM, app.getType()) && !ClassifierUtil.equals(KASKKIRI_TUGI, directiveStudent.getDirective().getType())) {
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
                    person.setCitizenship(EntityUtil.validateClassifier(EntityUtil.getOptionalOne(formStudent.getCitizenship(), em), MainClassCode.RIIK));
                }
                person = EntityUtil.save(person, em);
            } else if(sais != null) {
                // update existing person from sais application
                personFromSaisApplication(person, sais);
            } else {
                updatePersonData(person, formStudent);
            }
            directiveStudent.setPerson(person);
        } else if(StringUtils.hasText(formStudent.getForeignIdcode()) || (formStudent.getBirthdate() != null && StringUtils.hasText(formStudent.getSex()))) {
            // add new person if person foreign idcode is not known or not set
            Person person = findForeignPerson(formStudent);
            SaisApplication sais = directiveStudent.getSaisApplication();
            if(person == null) {
                person = new Person();
                if(sais != null) {
                    personFromSaisApplication(person, sais);
                } else {
                    person.setForeignIdcode(formStudent.getForeignIdcode());
                    person.setFirstname(formStudent.getFirstname());
                    person.setLastname(formStudent.getLastname());
                    person.setBirthdate(formStudent.getBirthdate());
                    person.setSex(EntityUtil.validateClassifier(EntityUtil.getOptionalOne(formStudent.getSex(), em), MainClassCode.SUGU));
                    person.setCitizenship(EntityUtil.validateClassifier(EntityUtil.getOptionalOne(formStudent.getCitizenship(), em), MainClassCode.RIIK));
                }
                person = EntityUtil.save(person, em);
            } else if(sais != null) {
                // update existing person from sais application
                personFromSaisApplication(person, sais);
            } else {
                updatePersonData(person, formStudent);
            }
            directiveStudent.setPerson(person);
        }
    }

    private void updatePersonData(Person person, DirectiveFormStudent formStudent) {
        person.setCitizenship(EntityUtil.validateClassifier(EntityUtil.getOptionalOne(formStudent.getCitizenship(), em), MainClassCode.RIIK));
        String idcode = person.getIdcode();
        if(StringUtils.hasText(idcode)) {
            if(person.getBirthdate() == null) {
                person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
            }
            if(person.getSex() == null) {
                person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(idcode)));
            }
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
        person.setAddressAdsOid(sais.getAddressAdsOid());
        person.setPostcode(sais.getPostcode());
    }

    private Person findForeignPerson(DirectiveFormStudent formStudent) {
        TypedQuery<Person> q;
        if(StringUtils.hasText(formStudent.getForeignIdcode())) {
            q = em.createQuery("select p from Person p where p.foreignIdcode = ?1", Person.class)
                    .setParameter(1, formStudent.getForeignIdcode());
        } else {
            // XXX similar code in TeacherService.create
            q = em.createQuery("select p from Person p where p.idcode is null and p.foreignIdcode is null " +
                    "and upper(p.firstname) = ?1 and upper(p.lastname) = ?2 and p.birthdate = ?3 and p.citizenship.code = ?4", Person.class)
                    .setParameter(1, formStudent.getFirstname().toUpperCase())
                    .setParameter(2, formStudent.getLastname().toUpperCase())
                    .setParameter(3, formStudent.getBirthdate())
                    .setParameter(4, formStudent.getCitizenship());
        }
        List<Person> data = q.getResultList();
        return data.isEmpty() ? null : data.get(0);
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

    private void studentRemovedFromDirective(HoisUserDetails user, DirectiveStudent directiveStudent) {
        Application app = directiveStudent.getApplication();
        if(app != null && !ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_YLEVAAT, app.getType())) {
            // student with application is removed from directive
            // update application status to AVALDUS_STAATUS_YLEVAAT
            app.setStatus(em.getReference(Classifier.class, ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name()));
            EntityUtil.save(app, em);
        }
        DirectiveUtil.cancelFormsAndDocuments(user.getUsername(), directiveStudent, em);
    }

    private List<StudentGroup> findValidStudentGroups(Long schoolId, boolean withoutGuestGroups) {
        LocalDate now = LocalDate.now();
        return em.createQuery("select sg from StudentGroup sg where sg.school.id = ?1 "
                + "and (sg.validFrom is null or sg.validFrom <= ?2) and (sg.validThru is null or sg.validThru >= ?2)"
                + (withoutGuestGroups ? "and sg.isGuest is not true" : ""), StudentGroup.class)
                .setParameter(1, schoolId).setParameter(2, now).getResultList();
    }

    private Set<Long> cumLaudes(Set<Long> studentIds, boolean isHigher) {
        if (isHigher) {
            return higherCumLaudes(studentIds);
        }
        return vocationalCumLaudes(studentIds);
    }

    private Set<Long> higherCumLaudes(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select scc.student_id from student_curriculum_completion scc"
                + " join protocol_student ps on ps.student_id = scc.student_id"
                + " join protocol p on p.id = ps.protocol_id"
                + " where scc.student_id in ?1 and scc.average_mark >= 4.6 and p.status_code = ?2"
                + " and ((p.is_final = true and p.is_final_thesis = false and ps.grade_mark = 5)"
                + " or exists(select 1 from protocol_student_occupation pso where pso.protocol_student_id = ps.id))"
                + " and exists(select 1 from student s join curriculum_version cv on s.curriculum_version_id = cv.id"
                + " join curriculum c on cv.curriculum_id = c.id and c.is_higher = true"
                + " where s.id = scc.student_id)")
                .setParameter(1, studentIds)
                .setParameter(2, ProtocolStatus.PROTOKOLL_STAATUS_K.name())
                .getResultList();
        return data.stream().map(r -> resultAsLong(r, 0)).collect(Collectors.toSet());
    }

    private Set<Long> vocationalCumLaudes(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select scc.student_id from student_curriculum_completion scc"
                + " join protocol_student ps on ps.student_id = scc.student_id"
                + " join protocol p on p.id = ps.protocol_id"
                + " where scc.student_id in ?1 and scc.average_mark >= 4.6 and p.status_code = ?2"
                + " and ((p.is_final = true and p.is_final_thesis = false and ps.grade_mark = 5)"
                + " or exists(select 1 from protocol_student_occupation pso where pso.protocol_student_id = ps.id))"
                + " and exists(select 1 from student s join curriculum_version cv on s.curriculum_version_id = cv.id"
                + " join curriculum c on cv.curriculum_id = c.id"
                + " join classifier_connect cc on c.orig_study_level_code = cc.classifier_code and cc.connect_classifier_code = ?3"
                + " where s.id = scc.student_id)"
                + " and not exists(select 1 from student_vocational_result svr3 where svr3.student_id = scc.student_id"
                    + " and svr3.grade_code = ?4)")
                .setParameter(1, studentIds)
                .setParameter(2, ProtocolStatus.PROTOKOLL_STAATUS_K.name())
                .setParameter(3, FormType.LOPUBLANKETT_KK.name())
                .setParameter(4, OccupationalGrade.KUTSEHINDAMINE_3.name())
                .getResultList();

        Set<Long> studentsMeetingCriteria = data.stream().map(r -> resultAsLong(r, 0)).collect(Collectors.toSet());
        if (!studentsMeetingCriteria.isEmpty()) {
            Set<Long> filteredStudentIds = studentsMeetingCriteria;
            Map<Long, StudentVocationalStudyProgramme> studyProgrammes = studentService
                    .studentStudyProgrammes(filteredStudentIds);
            Map<Long, BigDecimal> generalStudiesCredits = studentModuleTypeCredits(filteredStudentIds,
                    EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_Y), true);
            Map<Long, BigDecimal> coreStudiesCredits = studentModuleTypeCredits(filteredStudentIds,
                    EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_P), true);
            Map<Long, BigDecimal> freeChoiceCredits = studentModuleTypeCredits(filteredStudentIds,
                    EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_Y, CurriculumModuleType.KUTSEMOODUL_P,
                            CurriculumModuleType.KUTSEMOODUL_L), false);

            for (Long studentId : filteredStudentIds) {
                StudentVocationalStudyProgramme studentStudyProgramme = studyProgrammes.get(studentId);
                if (studentStudyProgramme == null) {
                    studentsMeetingCriteria.remove(studentId);
                    continue;
                }
                BigDecimal studentGeneralCredits = generalStudiesCredits.containsKey(studentId)
                        ? generalStudiesCredits.get(studentId) : BigDecimal.ZERO;
                BigDecimal studentCoreCredits = coreStudiesCredits.containsKey(studentId)
                        ? coreStudiesCredits.get(studentId) : BigDecimal.ZERO;
                BigDecimal studentFreeChoiceCredits = freeChoiceCredits.containsKey(studentId)
                        ? freeChoiceCredits.get(studentId) : BigDecimal.ZERO;
                BigDecimal distinctiveGradesCriteria = BigDecimal.valueOf(0.5);

                BigDecimal programmeGeneralCredits = studentStudyProgramme.getGeneralStudies();
                if (BigDecimal.ZERO.compareTo(programmeGeneralCredits) != 0
                        && studentGeneralCredits.divide(programmeGeneralCredits, 3, RoundingMode.DOWN)
                                .compareTo(distinctiveGradesCriteria) == -1) {
                    studentsMeetingCriteria.remove(studentId);
                    continue;
                }

                BigDecimal programmeCoreAndFreeCredits = studentStudyProgramme.getCoreStudies()
                        .add(studentStudyProgramme.getFreeChoice());
                BigDecimal studentCoreAndFreeCredits = studentCoreCredits.add(studentFreeChoiceCredits);
                if (BigDecimal.ZERO.compareTo(programmeCoreAndFreeCredits) != 0
                        && studentCoreAndFreeCredits.divide(programmeCoreAndFreeCredits, 3, RoundingMode.DOWN)
                                .compareTo(distinctiveGradesCriteria) == -1) {
                    studentsMeetingCriteria.remove(studentId);
                }
            }
        }
        return studentsMeetingCriteria;
    }

    private CurriculumGrade getCurriculumGrade(Long studentId) {
        List<CurriculumGrade> result = em.createQuery("select ps.curriculumGrade"
                + " from ProtocolStudent ps"
                + " where ps.student.id = ?1"
                + " and ps.protocol.status.code = ?2 and ps.protocol.isFinal = true", 
                CurriculumGrade.class)
                .setParameter(1, studentId)
                .setParameter(2, ProtocolStatus.PROTOKOLL_STAATUS_K.name())
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    private Map<Long, BigDecimal> studentModuleTypeCredits(Set<Long> studentIds, List<String> moduleTypes, boolean ofModuleType) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr"
                + " join curriculum_version_omodule cmm on (svr.arr_modules is null"
                + " and svr.curriculum_version_omodule_id = cmm.id or cmm.id=any(svr.arr_modules))");
        qb.requiredCriteria("svr.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("grade in (:positiveDistinctiveGrades)", "positiveDistinctiveGrades",
                Arrays.asList("3", "4", "5"));
        qb.filter("cmm.curriculum_module_id in (select cm.id as m_id from curriculum_version cv"
                + " join curriculum_version_omodule cvo on cv.id=cvo.curriculum_version_id"
                + " join curriculum_module cm on cvo.curriculum_module_id=cm.id and cv.curriculum_id=cm.curriculum_id"
                    + " and coalesce(cm.is_additional,false)=false"
                    + " and cm.module_code" + (ofModuleType ? " " : " not ") + "in (:moduleTypes)"
                    + " and coalesce(cm.is_additional,false)=false"
                + " join student ss on cv.id=ss.curriculum_version_id"
                + " left join student_group sg on ss.student_group_id=sg.id"
                + " where ss.id in (:studentIds) and (sg.id is null or coalesce(sg.speciality_code,'x')='x' or"
                + " coalesce(sg.speciality_code,'x')!='x' and exists("
                + " select 1 from curriculum_module_occupation cmo"
                + " left join classifier_connect ccc on cmo.occupation_code=ccc.connect_classifier_code"
                + " where cmo.curriculum_module_id=cm.id and (cmo.occupation_code=sg.speciality_code or ccc.classifier_code=sg.speciality_code))))"
                + " group by svr.student_id");
        qb.parameter("moduleTypes", moduleTypes);

        List<?> data = qb.select("svr.student_id, sum(svr.credits)", em).getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsDecimal(r, 1), data);
    }

    private Set<Long> occupationCertificates(Set<Long> studentIds) {
        // most part of query checks if occupation_certificate has same speciality/(part)occupation as student's current curriculum
        List<?> data = em.createNativeQuery("select soc.student_id from student_occupation_certificate soc join student s on soc.student_id = s.id " +
                "join curriculum_version cv on s.curriculum_version_id = cv.id where soc.student_id in ?1 and soc.valid_thru >= s.nominal_study_end " +
                "and ((soc.speciality_code is not null and exists(select 1 from curriculum_occupation_speciality cos join curriculum_occupation co on co.id = cos.curriculum_occupation_id where cv.curriculum_id = co.curriculum_id and cos.speciality_code = soc.speciality_code)) or " +
                "(soc.speciality_code is null and exists(select 1 from curriculum_occupation co where co.curriculum_id = cv.curriculum_id and co.occupation_code = coalesce(soc.part_occupation_code, soc.occupation_code))))")
                .setParameter(1, studentIds)
                .getResultList();
        return data.stream().map(r -> resultAsLong(r, 0)).collect(Collectors.toSet());
    }

    private static JpaNativeQueryBuilder occupationBaseQuery(Set<Long> studentIds, boolean isHigher) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol_student ps"
                + " join protocol_student_occupation pso on pso.protocol_student_id = ps.id"
                + " join protocol p on p.id = ps.protocol_id "
                + " left join student_occupation_certificate soc on soc.id = pso.student_occupation_certificate_id");
        qb.requiredCriteria("ps.student_id in :students", "students", studentIds);
        qb.filter("p.is_final = true");
        qb.requiredCriteria("p.status_code = :pstatus", "pstatus", ProtocolStatus.PROTOKOLL_STAATUS_K.name());
        qb.requiredCriteria("ps.grade_code in :grades", "grades", isHigher ?
                Stream.of(HigherAssessment.values()).filter(HigherAssessment::getIsPositive)
                    .map(HigherAssessment::name).collect(Collectors.toList())
                : OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        return qb;
    }

    private Map<Long, List<String>> occupations(Set<Long> studentIds, boolean isHigher) {
        List<?> data = occupationBaseQuery(studentIds, isHigher)
                .select("distinct ps.student_id, pso.occupation_code", em)
                .getResultList();
        Map<Long, List<String>> result = new HashMap<>();
        for (Object r : data) {
            Long studentId = resultAsLong(r, 0);
            result.computeIfAbsent(studentId, k -> new ArrayList<>())
                    .add(resultAsString(r, 1));
        }
        return result;
    }

    private Map<Long, Map<String, List<String>>> specialities(Set<Long> studentIds, boolean isHigher) {
        JpaNativeQueryBuilder qb = occupationBaseQuery(studentIds, isHigher);
        qb.filter("soc.speciality_code is not null");
        List<?> data = qb.select("distinct ps.student_id, pso.occupation_code, soc.speciality_code", em)
                .getResultList();
        Map<Long, Map<String, List<String>>> result = new HashMap<>();
        for (Object r : data) {
            Long studentId = resultAsLong(r, 0);
            result.computeIfAbsent(studentId, k -> new HashMap<>());
            String occupation = resultAsString(r, 1);
            result.get(studentId).computeIfAbsent(occupation, k -> new ArrayList<>()).add(resultAsString(r, 2));
        }
        return result;
    }

    private Map<Long, List<String>> partOccupations(Set<Long> studentIds, boolean isHigher) {
        JpaNativeQueryBuilder qb = occupationBaseQuery(studentIds, isHigher);
        qb.filter("pso.part_occupation_code is not null");
        List<?> data = qb
                .select("distinct ps.student_id, pso.part_occupation_code", em)
                .getResultList();
        Map<Long, List<String>> result = new HashMap<>();
        for (Object r : data) {
            Long studentId = resultAsLong(r, 0);
            result.computeIfAbsent(studentId, k -> new ArrayList<>())
                .add(resultAsString(r, 1));
        }
        return result;
    }
    
    private static boolean userCanConfirm(HoisUserDetails user, Directive directive) {
        return UserUtil.isSchoolAdmin(user, directive.getSchool()) &&
               UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_KASKKIRI_EKISETA);
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

    private static boolean isScholarship(String directiveType) {
        return DirectiveType.KASKKIRI_STIPTOET.name().equals(directiveType);
    }

    private static void assertModifyable(Directive directive) {
        // only directive state 'KOOSTAMISEL' allows modification
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, directive.getStatus()), "Directive status mismatch");
    }

    private static ApplicationType applicationType(DirectiveType type) {
        return APPLICATION_TYPE.get(type);
    }

    private static void assertSameSchool(Directive directive, School school) {
        if(school != null && !EntityUtil.getId(directive.getSchool()).equals(EntityUtil.getId(school))) {
            throw new AssertionFailedException("School mismatch");
        }
    }

    public List<IndividualCurriculumModuleDto> individualCurriculumModules(Student student, Long directiveId) {
        Long studentId = EntityUtil.getId(student);
        Map<Long, Map<Long, List<ExistingIndividualCurriculumModuleDto>>> existingModules = existingIndividualCurriculumModules(
                Arrays.asList(studentId), directiveId);
        Map<Long, List<ExistingIndividualCurriculumModuleDto>> studentExistingModules = existingModules.get(studentId);

        JpaNativeQueryBuilder qb = individualCurriculumModulesQb(Arrays.asList(studentId), directiveId);
        List<?> data = qb.select("cvo.id, cm.name_et, mcl.name_et mcl_name_et, cm.name_en, mcl.name_en mcl_name_en", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> new IndividualCurriculumModuleDto(resultAsLong(r, 0),
                CurriculumUtil.moduleName(resultAsString(r, 1), resultAsString(r, 2)),
                CurriculumUtil.moduleName(resultAsString(r, 3) != null ? resultAsString(r, 3) : resultAsString(r, 1), resultAsString(r, 4)),
                studentExistingModules != null ? studentExistingModules.get(resultAsLong(r, 0)) : null), data);
    }

    public JpaNativeQueryBuilder individualCurriculumModulesQb(List<Long> studentIds, Long directiveId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s"
                + " join curriculum_version cv on s.curriculum_version_id = cv.id"
                + " join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " join classifier mcl on cm.module_code = mcl.code");

        qb.optionalCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("cm.module_code in (:moduleCodes)", "moduleCodes",
                EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_P, CurriculumModuleType.KUTSEMOODUL_Y));

        qb.filter("(not exists (select 1 from student_vocational_result svr"
                + " where svr.curriculum_version_omodule_id = cvo.id and svr.student_id = s.id"
                + " and svr.grade_code in (:positiveGradeCodes))"
                + (directiveId != null
                        ? " or exists (select 1 from directive_student_module dsm"
                                + " join directive_student ds on dsm.directive_student_id = ds.id"
                                + " and s.id = ds.student_id and ds.directive_id = :directiveId"
                                + " where dsm.curriculum_version_omodule_id = cvo.id))"
                        : ")"));

        if (directiveId != null) {
            qb.parameter("directiveId", directiveId);
        }
        qb.parameter("positiveGradeCodes", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        return qb;
    }

    public Map<Long, Map<Long, List<ExistingIndividualCurriculumModuleDto>>> existingIndividualCurriculumModules(
            List<Long> studentIds, Long directiveId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " join directive_student_module dsm on dsm.directive_student_id = ds.id"
                + " left join (directive_student ds_lop join directive d_lop on d_lop.id = ds_lop.directive_id "
                + " and d_lop.type_code = :lopTypeCode and d_lop.status_code = :statusCode)"
                + " on ds_lop.directive_student_id = ds.id and ds_lop.canceled = false");

        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.type_code = :typeCode", "typeCode", DirectiveType.KASKKIRI_INDOK);
        qb.requiredCriteria("d.status_code = :statusCode", "statusCode", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.optionalCriteria("d.id != :directiveId", "directiveId", directiveId);
        qb.parameter("lopTypeCode", DirectiveType.KASKKIRI_INDOKLOP.name());
        qb.filter("ds.canceled = false");

        String indokQuery = qb.querySql("ds.student_id, dsm.curriculum_version_omodule_id, ds.start_date, "
                + "coalesce(ds_lop.start_date, ds.end_date) end_date", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from directive_student ds2"
                + " join directive d2 on d2.id = ds2.directive_id"
                + " join application a on a.id = ds2.application_id"
                + " join application_support_service ass on ass.application_id = a.id"
                + " join application_support_service_module assm on assm.application_support_service_id = ass.id"
                + " left join (directive_student ds_lop2 join directive d_lop2 on d_lop2.id = ds_lop2.directive_id "
                + " and d_lop2.type_code = :lopTypeCode2 and d_lop2.status_code = :statusCode) "
                + " on ds_lop2.directive_student_id = ds2.id and ds_lop2.canceled = false");

        qb.requiredCriteria("ds2.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d2.type_code = :typeCode2", "typeCode2", DirectiveType.KASKKIRI_TUGI);
        qb.requiredCriteria("d2.status_code = :statusCode", "statusCode", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("ass.support_service_code = :supportServiceCode", "supportServiceCode",
                SupportServiceType.TUGITEENUS_1);
        qb.optionalCriteria("d2.id != :directiveId", "directiveId", directiveId);
        qb.parameter("lopTypeCode2", DirectiveType.KASKKIRI_TUGILOPP.name());
        qb.filter("ds2.canceled = false");

        String tugiQuery = qb.querySql("ds2.student_id, assm.curriculum_version_omodule_id, ds2.start_date, "
                + "coalesce(ds_lop2.start_date, ds2.end_date) end_date", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + indokQuery + " union all " + tugiQuery + ") as icm");

        Map<Long, Map<Long, List<ExistingIndividualCurriculumModuleDto>>> existingModules = new HashMap<>();
        List<?> data = qb.select("student_id, curriculum_version_omodule_id, start_date, end_date", em, parameters)
                .getResultList();
        if (!data.isEmpty()) {
            existingModules = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.groupingBy(r -> resultAsLong(r, 1), Collectors.mapping(r -> {
                        ExistingIndividualCurriculumModuleDto dto = new ExistingIndividualCurriculumModuleDto();
                        dto.setId(resultAsLong(r, 1));
                        dto.setStartDate(resultAsLocalDate(r, 2));
                        dto.setEndDate(resultAsLocalDate(r, 3));
                        return dto;
                    }, Collectors.toList()))));
        }
        return existingModules;
    }

    /**
     * 
     * @param supportServiceMessageDays Days before sending notification about ending service. 
     */
    public void sendSupportServiceMessages(Integer supportServiceMessageDays) {
        // TUGI
        // School admins should get a notification message at the moment when service is going to end after 30 days.
        // One time notification.
        // Also should check TUGILOPP
        LOG.info("Starting to send messages about ending support services");
        List<?> data = em.createNativeQuery("select ds.student_id, coalesce(ds2.start_date, ds.end_date) "
                + "from directive_student ds "
                + "join directive d on d.id = ds.directive_id "
                + "left join directive_student ds2 on ds.id = ds2.directive_student_id and ds2.canceled = false "
                + "left join directive d2 on d2.id = ds2.directive_id and d2.type_code = ?1 and d2.status_code = ?2 "
                + "where d.type_code = ?3 and d.status_code = ?4 "
                + "and ds.canceled = false and coalesce(ds2.start_date, ds.end_date) = ?5")
            .setParameter(1, KASKKIRI_TUGILOPP.name())
            .setParameter(2, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(3, KASKKIRI_TUGI.name())
            .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(5, JpaQueryUtil.parameterAsTimestamp(LocalDate.now().plusDays(supportServiceMessageDays.intValue())))
            .getResultList();
        LOG.info(String.format("Found %d directive(s) to be sent.", Integer.valueOf(data != null ? data.size() : 0)));
        StreamUtil.nullSafeList(data).stream().forEach(r -> {
            Student student = em.getReference(Student.class, resultAsLong(r, 0));
            LOG.info(String.format("Sending message about student [%d]%s", student.getId(), student.getPerson().getFullname()));
            SupportServiceEnding message = new SupportServiceEnding(student, resultAsLocalDate(r, 1));
            automaticMessageService.sendMessageToSchoolAdmins(MessageType.TEATE_LIIK_TUGI_LOPP, student.getSchool(), message);
        });
    }
}
