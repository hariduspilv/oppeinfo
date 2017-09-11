package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.*;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveCancelType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;

@Transactional
@Service
public class DirectiveConfirmService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private EntityManager em;
    @Autowired
    private EmailGeneratorService emailGeneratorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private Validator validator;

    public void sendToConfirm(Directive directive) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, directive.getStatus()), "Invalid directive status");

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        List<Map.Entry<String, String>> allErrors = new ArrayList<>();
        if(directive.getDirectiveCoordinator() == null) {
            allErrors.add(new AbstractMap.SimpleImmutableEntry<>("directiveCoordinator", "NotNull"));
        }
        if(directive.getStudents().isEmpty()) {
            allErrors.add(new AbstractMap.SimpleImmutableEntry<>(null, "directive.missingstudents"));
        }
        Map<Long, DirectiveStudent> academicLeaves = findAcademicLeaves(directive);
        Set<Long> changedStudents = DirectiveType.KASKKIRI_TYHIST.equals(directiveType) ? new HashSet<>(directiveService.changedStudentsForCancel(directive.getCanceledDirective())) : Collections.emptySet();
        // validate each student's data for given directive
        long rowNum = 0;
        for(DirectiveStudent ds : directive.getStudents()) {
            if(directiveType.validationGroup() != null) {
                Set<ConstraintViolation<DirectiveStudent>> errors = validator.validate(ds, directiveType.validationGroup());
                if(!errors.isEmpty()) {
                    for(ConstraintViolation<DirectiveStudent> e : errors) {
                        allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, e.getPropertyPath().toString()), e.getMessage()));
                    }
                }
            }
            // TODO check for valid studentGroup for ennist, immat, okava, ovorm
            // TODO check for valid curriculumVersion for ennist (student's current), immat, okava
            if(DirectiveType.KASKKIRI_AKADK.equals(directiveType)) {
                // check that cancel date is inside academic leave period
                DirectiveStudent academicLeave = academicLeaves.get(EntityUtil.getId(ds.getStudent()));
                LocalDate leaveCancel = ds.getStartDate();
                if(academicLeave == null || leaveCancel.isBefore(DateUtils.periodStart(academicLeave)) || leaveCancel.isAfter(DateUtils.periodEnd(academicLeave))) {
                    allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, "startDate"), "InvalidValue"));
                }
            } else if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType)) {
                // check by hand because for immatv it's filled automatically after directive confirmation
                if(ds.getNominalStudyEnd() == null) {
                    allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, "nominalStudyEnd"), "NotNull"));
                }
            } else if(DirectiveType.KASKKIRI_TYHIST.equals(directiveType)) {
                // check that it's last modification of student
                if(changedStudents.contains(EntityUtil.getId(ds.getStudent()))) {
                    allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, "fullname"), "StudentChanged"));
                }
            } else if(DirectiveType.KASKKIRI_VALIS.equals(directiveType)) {
                boolean isAbroad = Boolean.TRUE.equals(ds.getIsAbroad());
                if(isAbroad ? !StringUtils.hasText(ds.getAbroadSchool()) : ds.getEhisSchool() == null) {
                    allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, isAbroad ? "abroadSchool" : "ehisSchool"), "NotNull"));
                }
            }
            rowNum++;
        }

        if(!allErrors.isEmpty()) {
            throw new ValidationFailedException(allErrors);
        }

        // TODO send to sais, if service returns no errors then update wdId with service return value and set status to kinnitamisel
        setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL);
        EntityUtil.save(directive, em);
    }

    public Directive rejectByEkis(long directiveId, String rejectComment, String preamble, long wdId) {
        //TODO veahaldus
        // • ÕISi käskkiri ei leitud – ÕISist ei leita vastava OIS_ID ja WD_ID-ga käskkirja
        // • Vale staatus – tagasi lükata saab ainult „kinnitamisel“ staatusega käskkirja. „Koostamisel“ ja „Kinnitatud“ käskkirju tagasi lükata ei saa.
        // • Üldine veateade – üldine viga salvestamisel, nt liiga lühike andmeväli, vale andmetüüp vms.
        Directive directive = findDirective(directiveId);
        log.info("directive {} rejected by ekis with reason {}", EntityUtil.getId(directive), rejectComment);
        setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL);
        directive.setPreamble(preamble);
        directive.setAddInfo(directive.getAddInfo() + " " + rejectComment);
        directive.setWdId(Long.valueOf(wdId));
        return EntityUtil.save(directive, em);
    }

    public Directive confirmedByEkis(long directiveId, String directiveNr, LocalDate confirmDate, String preamble, long wdId,
            String signerIdCode, String signerName) {
        //TODO veahaldus
        //• ÕISi käskkiri ei leitud – ÕISist ei leita vastava vastava OIS_ID ja WD_ID-ga käskkirja
        //• Vale staatus – kinnitada saab ainult „kinnitamisel“ staatusega käskkirja. „Koostamisel“ ja „Kinnitatud“ käskkirju kinnitada ei saa.
        //• Üldine veateade – üldine viga salvestamisel, nt liiga lühike andmeväli, vale andmetüüp vms
        Directive directive = findDirective(directiveId);
        directive.setDirectiveNr(directiveNr);
        directive.setPreamble(preamble);
        directive.setWdId(Long.valueOf(wdId));
        return confirm(PersonUtil.fullnameAndIdcode(signerName, signerIdCode), directive, confirmDate);
    }

    private Directive findDirective(long directiveId) {
        try {
            Directive directive = em.getReference(Directive.class, Long.valueOf(directiveId));
            if(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, directive.getStatus())) {
                throw new HoisException("Käskkiri vale staatusega");
            }
            return directive;
        } catch(@SuppressWarnings("unused") EntityNotFoundException e) {
            throw new HoisException("Käskkirja ei leitud");
        }
    }

    public Directive confirm(String confirmer, Directive directive, LocalDate confirmDate) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, directive.getStatus()), "Invalid directive status");

        // update directive fields
        setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        directive.setConfirmDate(confirmDate);
        directive.setConfirmer(confirmer);

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(KASKKIRI_TYHIST.equals(directiveType)) {
            cancelDirective(directive);
        } else {
            Classifier studentStatus = directiveType.studentStatus() != null ? em.getReference(Classifier.class, directiveType.studentStatus().name()) : null;
            Classifier applicationStatus = em.getReference(Classifier.class, ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
            Map<Long, DirectiveStudent> academicLeaves = findAcademicLeaves(directive);
            for(DirectiveStudent ds : directive.getStudents()) {
                Student student = ds.getStudent();
                // store student version for undo
                ds.setStudentHistory(student != null ? student.getStudentHistory() : null);
                updateApplicationStatus(ds, applicationStatus);
                updateStudentData(directiveType, ds, studentStatus, student != null ? academicLeaves.get(student.getId()) : null);
            }
        }
        return EntityUtil.save(directive, em);
    }

    public void updateStudentStatus(Job job) {
        JobType type = JobType.valueOf(EntityUtil.getCode(job.getType()));
        StudentStatus newStatus = null;
        switch(type) {
        case JOB_AKAD_MINEK:
            newStatus = StudentStatus.OPPURSTAATUS_A;
            break;
        case JOB_AKAD_TULEK:
        case JOB_AKAD_KATK:
        case JOB_VALIS_TULEK:
            newStatus = StudentStatus.OPPURSTAATUS_O;
            break;
        case JOB_VALIS_MINEK:
            newStatus = StudentStatus.OPPURSTAATUS_V;
            break;
        default:
            // do nothing
        }
        if(newStatus != null) {
            // check that directive is not already canceled for given student
            Long studentId = EntityUtil.getId(job.getStudent());
            Query q = em.createNativeQuery("select 1 from directive_student ds where ds.student_id = ?1 and ds.directive_id = ?2 and ds.canceled = true");
            q.setParameter(1, studentId);
            q.setParameter(2, EntityUtil.getId(job.getDirective()));
            if(q.setMaxResults(1).getResultList().isEmpty()) {
                // no canceled, update status
                Student student = em.getReference(Student.class, studentId);
                student.setStatus(em.getReference(Classifier.class, newStatus.name()));
                studentService.saveWithHistory(student);
            }
        }
    }

    private void updateStudentData(DirectiveType directiveType, DirectiveStudent directiveStudent, Classifier studentStatus, DirectiveStudent academicLeave) {
        Student student = directiveStudent.getStudent();
        if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_IMMATV.equals(directiveType)) {
            student = createStudent(directiveStudent);
        }

        // copy entered data from directive
        copyDirectiveProperties(directiveType, directiveStudent, student);

        // directive type specific calculated data and additional actions
        LocalDate confirmDate = directiveStudent.getDirective().getConfirmDate();
        User user;
        long duration;

        switch(directiveType) {
        case KASKKIRI_AKAD:
            duration = ChronoUnit.DAYS.between(DateUtils.periodStart(directiveStudent), DateUtils.periodEnd(directiveStudent).plusDays(1));
            student.setNominalStudyEnd(student.getNominalStudyEnd().plusDays(duration));
            break;
        case KASKKIRI_AKADK:
            duration = ChronoUnit.DAYS.between(DateUtils.periodStart(academicLeave), directiveStudent.getStartDate());
            student.setNominalStudyEnd(student.getNominalStudyEnd().minusDays(duration));
            break;
        case KASKKIRI_EKSMAT:
            // FIXME correct field for Õppuri eksmatrikuleerimise kuupäev?
            student.setStudyEnd(confirmDate);
            user = userForStudent(student);
            if(user != null) {
                user.setValidThru(confirmDate);
            }
            break;
        case KASKKIRI_ENNIST:
            student.setStudyStart(confirmDate);
            student.setStudyEnd(null);
            user = userForStudent(student);
            // FIXME can user be null?
            if(user == null) {
                createUser(student, confirmDate);
            } else {
                user.setValidFrom(confirmDate);
                user.setValidThru(null);
            }
            break;
        case KASKKIRI_IMMATV:
            Integer months = directiveStudent.getCurriculumVersion().getCurriculum().getStudyPeriod();
            student.setNominalStudyEnd(confirmDate.plusMonths(months.longValue()));
            // set nominal study end also on directive
            directiveStudent.setNominalStudyEnd(student.getNominalStudyEnd());
            // fall thru
        case KASKKIRI_IMMAT:
            student.setStudyStart(confirmDate);
            break;
        case KASKKIRI_LOPET:
            student.setStudyEnd(confirmDate);
            user = userForStudent(student);
            if(user != null) {
                user.setValidThru(confirmDate);
            }
            break;
        default:
            break;
        }

        // optional new status
        if(studentStatus != null) {
            student.setStatus(studentStatus);
        }

        student = studentService.saveWithHistory(student);
        if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_IMMATV.equals(directiveType)) {
            // store reference to created student also into directive_student
            directiveStudent.setStudent(student);
            directiveStudent.setStudentHistory(student.getStudentHistory());
        }

        // inform student about new directive
        StudentDirectiveCreated data = new StudentDirectiveCreated(directiveStudent);
        automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_UUS_KK, student, data);
    }

    private void cancelDirective(Directive directive) {
        // cancellation may include only some students
        Map<Long, DirectiveStudent> includedStudents = StreamUtil.toMap(ds -> EntityUtil.getId(ds.getStudent()), ds -> ds, directive.getStudents());
        Directive canceledDirective = directive.getCanceledDirective();
        DirectiveType canceledDirectiveType = DirectiveType.valueOf(EntityUtil.getCode(canceledDirective.getType()));
        for(DirectiveStudent ds : canceledDirective.getStudents()) {
            Student student = ds.getStudent();
            DirectiveStudent cancelds = includedStudents.get(student.getId());
            if(cancelds != null) {
                if(KASKKIRI_IMMAT.equals(canceledDirectiveType) || KASKKIRI_IMMATV.equals(canceledDirectiveType)) {
                    // undo create student. Logic similar to EKSMAT
                    LocalDate confirmDate = directive.getConfirmDate();
                    student.setStudyEnd(confirmDate);
                    student.setStatus(em.getReference(Classifier.class, StudentStatus.OPPURSTAATUS_K.name()));
                    User user = userForStudent(student);
                    if(user != null) {
                        user.setValidThru(confirmDate);
                    }
                } else {
                    StudentHistory original = ds.getStudentHistory();
                    copyDirectiveProperties(canceledDirectiveType, original, student);
                    switch(canceledDirectiveType) {
                    case KASKKIRI_AKAD:
                    case KASKKIRI_AKADK:
                        student.setNominalStudyEnd(original.getNominalStudyEnd());
                        student.setStatus(original.getStatus());
                        break;
                    case KASKKIRI_EKSMAT:
                        student.setStudyEnd(null);
                        student.setStatus(original.getStatus());
                        User user = userForStudent(student);
                        if(user != null) {
                            user.setValidThru(null);
                        }
                        break;
                    case KASKKIRI_VALIS:
                        student.setStatus(original.getStatus());
                        break;
                    default:
                        if(canceledDirectiveType.studentStatus() != null) {
                            student.setStatus(original.getStatus());
                        }
                        break;
                    }
                }
                cancelds.setCanceled(Boolean.TRUE);
                cancelds.setStudentHistory(student.getStudentHistory());
                studentService.saveWithHistory(student);
            }
        }
        // FIXME what happens when student is changed between send-to-confirm and confirm
        if(ClassifierUtil.equals(DirectiveCancelType.KASKKIRI_TYHISTAMISE_VIIS_T, directive.getCancelType())) {
            setDirectiveStatus(directive.getCanceledDirective(), DirectiveStatus.KASKKIRI_STAATUS_TYHISTATUD);
        }
    }

    private void updateApplicationStatus(DirectiveStudent directive, Classifier applicationStatus) {
        Application application = directive.getApplication();
        if(application != null) {
            application.setStatus(applicationStatus);
            EntityUtil.save(application, em);
        }
    }

    private Map<Long, DirectiveStudent> findAcademicLeaves(Directive directive) {
        if(!ClassifierUtil.equals(DirectiveType.KASKKIRI_AKADK, directive.getType())) {
            return Collections.emptyMap();
        }

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_student ds "+
                "inner join directive d on ds.directive_id = d.id and ds.canceled = false "+
                "left outer join study_period sps on ds.study_period_start_id = sps.id "+
                "left outer join study_period spe on ds.study_period_end_id = spe.id").sort(new Sort(Direction.DESC, "d.id"));

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_AKAD);

        List<?> data = qb.select("ds.student_id, case when ds.is_period then sps.start_date else ds.start_date end, "+
                  "case when ds.is_period then spe.end_date else ds.end_date end", em).getResultList();

        return data.stream().map(r -> {
            DirectiveStudent ds = new DirectiveStudent();
            ds.setStudent(em.getReference(Student.class, resultAsLong(r, 0)));
            ds.setIsPeriod(Boolean.FALSE);
            ds.setStartDate(resultAsLocalDate(r, 1));
            ds.setEndDate(resultAsLocalDate(r, 2));
            return ds;
        }).collect(Collectors.toMap(ds -> EntityUtil.getId(ds.getStudent()), ds -> ds, (o, n) -> o));
    }

    private void setDirectiveStatus(Directive directive, DirectiveStatus status) {
        directive.setStatus(em.getReference(Classifier.class, status.name()));
    }

    private static User userForStudent(Student student) {
        Long studentId = student.getId();
        return student.getPerson().getUsers().stream().filter(u -> studentId.equals(EntityUtil.getNullableId(u.getStudent()))).findFirst().orElse(null);        
    }

    private Student createStudent(DirectiveStudent directiveStudent) {
        School school = directiveStudent.getDirective().getSchool();

        Student student = new Student();
        student.setPerson(directiveStudent.getPerson());
        student.setSchool(school);
        student.setStudyStart(directiveStudent.getStartDate());
        student.setIsRepresentativeMandatory(Boolean.FALSE);
        student.setIsSpecialNeed(Boolean.FALSE);

        // student's email
        Person person = student.getPerson();
        String email = emailGeneratorService.lookupSchoolEmail(school, person);
        if(email == null && Boolean.TRUE.equals(school.getGenerateUserEmail())) {
            email = emailGeneratorService.generateEmail(school, person.getFirstname(), person.getLastname());
        }
        if(email == null) {
            // if student's email is not generated, copy from person
            email = directiveStudent.getPerson().getEmail();
        }
        student.setEmail(email);

        // new role for student
        createUser(student, directiveStudent.getDirective().getConfirmDate());
        return student;
    }

    private User createUser(Student student, LocalDate validFrom) {
        User user = new User();
        user.setPerson(student.getPerson());
        user.setSchool(student.getSchool());
        user.setRole(em.getReference(Classifier.class, Role.ROLL_T.name()));
        user.setStudent(student);
        user.setValidFrom(validFrom);
        // rights for logging in
        // TODO wrong rights, replace!
        UserRights userRights = new UserRights();
        userRights.setUser(user);
        userRights.setPermission(em.getReference(Classifier.class, Permission.OIGUS_V.name()));
        userRights.setObject(em.getReference(Classifier.class, "TEEMAOIGUS_A"));
        user.setUserRights(Collections.singleton(userRights));
        em.persist(user);
        return user;
    }

    private static void copyDirectiveProperties(DirectiveType directiveType, Object source, Student student) {
        String[] copiedProperties = directiveType.updatedFields();
        if(copiedProperties.length > 0) {
            PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(source);
            PropertyAccessor writer = PropertyAccessorFactory.forBeanPropertyAccess(student);
            for(String propertyName : copiedProperties) {
                if(reader.isReadableProperty(propertyName)) {
                    Object value = reader.getPropertyValue(propertyName);
                    writer.setPropertyValue(propertyName, value);
                }
            }
        }
    }

    private static String propertyPath(long rowNum, String property) {
        String pathPrefix = String.format("students[%s]", Long.valueOf(rowNum));
        return property.isEmpty() ? pathPrefix : pathPrefix + "." + property;
    }
}
