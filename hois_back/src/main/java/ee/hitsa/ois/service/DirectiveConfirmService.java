package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.*;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.UserRights;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;

@Transactional
@Service
public class DirectiveConfirmService {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private EntityManager em;
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
        Map<Long, DirectiveStudent> academicLeaves = findAcademicLeaves(directive);
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
                // TODO check that it's last modification of student
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
        directive.setStatus(em.getReference(Classifier.class, DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL.name()));
        directiveRepository.save(directive);
    }

    public Directive confirm(HoisUserDetails user, Directive directive, LocalDate confirmDate) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, directive.getStatus()), "Invalid directive status");

        // update directive fields
        directive.setStatus(em.getReference(Classifier.class, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name()));
        directive.setConfirmDate(confirmDate);
        directive.setConfirmer(user.getUsername());

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
        return directiveRepository.save(directive);
    }

    private void updateStudentData(DirectiveType directiveType, DirectiveStudent directiveStudent, Classifier studentStatus, DirectiveStudent academicLeave) {
        Student student = directiveStudent.getStudent();
        if(KASKKIRI_IMMAT.equals(directiveType) || KASKKIRI_IMMATV.equals(directiveType)) {
            student = createStudent(directiveStudent);
        }

        // copy entered data from directive
        copyDirectiveProperties(directiveType, directiveStudent, student, false);

        // directive type specific calculated data and additional actions
        LocalDate confirmDate = directiveStudent.getDirective().getConfirmDate();
        User user;
        long duration;

        // TODO put changes which should occur in future, into task queue
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
        }

        // inform student about new directive
        StudentDirectiveCreated data = new StudentDirectiveCreated(directiveStudent);
        automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_UUS_KK, student, data);
    }

    private void cancelDirective(Directive directive) {
        // cancellation may include only some students
        Set<Long> includedStudentIds = StreamUtil.toMappedSet(ds -> EntityUtil.getId(ds.getStudent()), directive.getStudents());
        Directive canceledDirective = directive.getCanceledDirective();
        DirectiveType canceledDirectiveType = DirectiveType.valueOf(EntityUtil.getCode(canceledDirective.getType()));
        for(DirectiveStudent ds : canceledDirective.getStudents()) {
            Student student = ds.getStudent();
            if(includedStudentIds.contains(student.getId())) {
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
                    copyDirectiveProperties(canceledDirectiveType, ds.getStudentHistory(), student, true);
                    if(KASKKIRI_AKAD.equals(canceledDirectiveType) || KASKKIRI_AKADK.equals(canceledDirectiveType)) {
                        student.setNominalStudyEnd(ds.getStudentHistory().getNominalStudyEnd());
                    }
                }
                // TODO cancel task from task queue, if there is one for given student and directive
                studentService.saveWithHistory(student);
            }
        }
    }

    private void updateApplicationStatus(DirectiveStudent directive, Classifier applicationStatus) {
        Application application = directive.getApplication();
        if(application != null) {
            application.setStatus(applicationStatus);
            applicationRepository.save(application);
        }
    }

    private Map<Long, DirectiveStudent> findAcademicLeaves(Directive directive) {
        if(!ClassifierUtil.equals(DirectiveType.KASKKIRI_AKADK, directive.getType())) {
            return Collections.emptyMap();
        }

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_student ds "+
                "inner join student_history dsh on ds.student_history_id = dsh.prev_student_history_id and ds.student_id = dsh.student_id "+
                "left outer join study_period sps on ds.study_period_start_id = sps.id "+
                "left outer join study_period spe on ds.study_period_end_id = spe.id");

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        qb.requiredCriteria("dsh.id in "+
                "(select max(sh.id) from student_history sh where sh.student_id in (:studentIds) and sh.status_code = 'OPPURSTAATUS_A' group by sh.student_id)", "studentIds", studentIds);

        List<?> data = qb.select("ds.student_id, case when ds.is_period then sps.start_date else ds.start_date end, "+
                  "case when ds.is_period then spe.end_date else ds.end_date end", em).getResultList();

        return data.stream().map(r -> {
            DirectiveStudent ds = new DirectiveStudent();
            ds.setStudent(em.getReference(Student.class, resultAsLong(r, 0)));
            ds.setIsPeriod(Boolean.FALSE);
            ds.setStartDate(resultAsLocalDate(r, 1));
            ds.setEndDate(resultAsLocalDate(r, 2));
            return ds;
        }).collect(Collectors.toMap(ds -> EntityUtil.getId(ds.getStudent()), ds -> ds));
    }

    private static User userForStudent(Student student) {
        Long studentId = student.getId();
        return student.getPerson().getUsers().stream().filter(u -> studentId.equals(EntityUtil.getNullableId(u.getStudent()))).findFirst().orElse(null);        
    }

    private Student createStudent(DirectiveStudent directiveStudent) {
        Student student = new Student();
        student.setPerson(directiveStudent.getPerson());
        student.setSchool(directiveStudent.getDirective().getSchool());
        student.setStudyStart(directiveStudent.getStartDate());
        student.setIsRepresentativeMandatory(Boolean.FALSE);
        student.setIsSpecialNeed(Boolean.FALSE);
        // if student's email is not generated, copy from person
        student.setEmail(directiveStudent.getPerson().getEmail());
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

    private static void copyDirectiveProperties(DirectiveType directiveType, Object source, Student student, boolean cancellation) {
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
            if(cancellation && directiveType.studentStatus() != null) {
                Object status = reader.getPropertyValue("status");
                writer.setPropertyValue("status", status);
            }
        }
    }

    private static String propertyPath(long rowNum, String property) {
        String pathPrefix = String.format("students[%s]", Long.valueOf(rowNum));
        return property.isEmpty() ? pathPrefix : pathPrefix + "." + property;
    }
}
