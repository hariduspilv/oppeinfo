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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.ValidationFailedException;

@Transactional
@Service
public class DirectiveConfirmService {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudentService studentService;
    @Autowired
    private Validator validator;

    public void sendToConfirm(Directive directive) {
        if(!DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name().equals(EntityUtil.getCode(directive.getStatus()))) {
            throw new AssertionFailedException("Inalid directive status");
        }
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        List<Map.Entry<String, String>> allErrors = new ArrayList<>();
        if(directive.getDirectiveCoordinator() == null) {
            allErrors.add(new AbstractMap.SimpleImmutableEntry<>("directiveCoordinator", "NotNull"));
        }
        Map<Long, DirectiveStudent> academicLeaves = findAcademicLeaves(directive);
        // validate each student's data for given directive
        long rowNum = 0;
        for(DirectiveStudent ds : directive.getStudents()) {
            Set<ConstraintViolation<DirectiveStudent>> errors = validator.validate(ds, directiveType.validationGroup());
            if(!errors.isEmpty()) {
                for(ConstraintViolation<DirectiveStudent> e : errors) {
                    allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, e.getPropertyPath().toString()), e.getMessage()));
                }
            }
            if(DirectiveType.KASKKIRI_AKADK.equals(directiveType)) {
                // check that cancel date is inside academic leave period
                DirectiveStudent academicLeave = academicLeaves.get(EntityUtil.getId(ds.getStudent()));
                LocalDate leaveCancel = ds.getStartDate();
                if(academicLeave == null || leaveCancel.isBefore(periodStart(academicLeave)) || leaveCancel.isAfter(periodEnd(academicLeave))) {
                    allErrors.add(new AbstractMap.SimpleImmutableEntry<>(propertyPath(rowNum, "startDate"), "InvalidValue"));
                }
            }
            rowNum++;
        }

        if(!allErrors.isEmpty()) {
            throw new ValidationFailedException(allErrors);
        }

        // TODO send to sais, if service returns no errors then update wdId with service return value and set status to kinnitamisel
        directive.setStatus(classifierRepository.getOne(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL.name()));
        directiveRepository.save(directive);
    }

    public void confirm(HoisUserDetails user, Directive directive, LocalDate confirmDate) {
        if(!DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL.name().equals(EntityUtil.getCode(directive.getStatus()))) {
            throw new AssertionFailedException("Inalid directive status");
        }
        // update directive fields
        directive.setStatus(classifierRepository.getOne(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name()));
        directive.setConfirmDate(confirmDate);
        directive.setConfirmer(user.getUsername());

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(KASKKIRI_TYHIST.equals(directiveType)) {
            cancelDirective(directive);
        } else {
            Classifier studentStatus = directiveType.studentStatus() != null ? classifierRepository.getOne(directiveType.studentStatus().name()) : null;
            Classifier applicationStatus = classifierRepository.getOne(ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
            Map<Long, DirectiveStudent> academicLeaves = findAcademicLeaves(directive);
            for(DirectiveStudent ds : directive.getStudents()) {
                // TODO put changes which should occur in future, into task queue
                // store student version for undo
                Student student = ds.getStudent();
                ds.setStudentHistory(student != null ? student.getStudentHistory() : null);
                updateApplicationStatus(ds, applicationStatus);
                updateStudentData(directiveType, ds, studentStatus, student != null ? academicLeaves.get(EntityUtil.getId(student)) : null);
            }
        }
        directiveRepository.save(directive);
    }

    private void updateStudentData(DirectiveType directiveType, DirectiveStudent directiveStudent, Classifier studentStatus, DirectiveStudent academicLeave) {
        Student student = directiveStudent.getStudent();
        LocalDate confirmDate = directiveStudent.getDirective().getConfirmDate();
        User user;
        long duration;

        switch(directiveType) {
        case KASKKIRI_AKAD:
            duration = ChronoUnit.DAYS.between(periodStart(directiveStudent), periodEnd(directiveStudent).plusDays(1));
            student.setNominalStudyEnd(student.getNominalStudyEnd().plusDays(duration));
            break;
        case KASKKIRI_AKADK:
            duration = ChronoUnit.DAYS.between(periodStart(academicLeave), directiveStudent.getStartDate());
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
            user = userForStudent(student);
            // FIXME can user be null?
            if(user == null) {
                createUser(student, confirmDate);
            } else {
                user.setValidFrom(confirmDate);
                user.setValidThru(null);
            }
            break;
        case KASKKIRI_IMMAT:
        case KASKKIRI_IMMATV:
            student = createStudent(directiveStudent);
            student.setStudyStart(confirmDate);
            break;
        default:
            break;
        }

        copyDirectiveProperties(directiveType, directiveStudent, student, false);

        // optional new status
        if(studentStatus != null) {
            student.setStatus(studentStatus);
        }
        studentService.saveWithHistory(student);
    }

    private static void cancelDirective(Directive directive) {
        // cancellation may include only some students
        Set<Long> includedStudentIds = directive.getStudents().stream().map(ds -> EntityUtil.getId(ds.getStudent())).collect(Collectors.toSet());
        Directive canceledDirective = directive.getCanceledDirective();
        DirectiveType canceledDirectiveType = DirectiveType.valueOf(EntityUtil.getCode(canceledDirective.getType()));
        for(DirectiveStudent ds : canceledDirective.getStudents()) {
            Student student = ds.getStudent();
            if(includedStudentIds.contains(student.getId())) {
                if(!KASKKIRI_IMMAT.equals(canceledDirectiveType) && !KASKKIRI_IMMATV.equals(canceledDirectiveType)) {
                    copyDirectiveProperties(canceledDirectiveType, ds.getStudentHistory(), student, true);
                } else {
                    // TODO undo create student
                }
                // TODO cancel task from task queue, if there is one for given student and directive
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
        if(!DirectiveType.KASKKIRI_AKADK.name().equals(EntityUtil.getCode(directive.getType()))) {
            return Collections.emptyMap();
        }

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_student ds "+
                "inner join student_history dsh on ds.student_history_id = dsh.prev_student_history_id and ds.student_id = dsh.student_id "+
                "left outer join study_period sps on ds.study_period_start_id = sps.id "+
                "left outer join study_period spe on ds.study_period_end_id = spe.id");

        List<Long> studentIds = directive.getStudents().stream().map(r -> EntityUtil.getId(r.getStudent())).collect(Collectors.toList());
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

    private static LocalDate periodStart(DirectiveStudent directive) {
        return Boolean.TRUE.equals(directive.getIsPeriod()) ? directive.getStudyPeriodStart().getStartDate() : directive.getStartDate();
    }

    private static LocalDate periodEnd(DirectiveStudent directive) {
        return Boolean.TRUE.equals(directive.getIsPeriod()) ? directive.getStudyPeriodEnd().getEndDate() : directive.getEndDate();
    }

    private Student createStudent(DirectiveStudent directiveStudent) {
        Student student = new Student();
        student.setPerson(directiveStudent.getPerson());
        student.setSchool(directiveStudent.getDirective().getSchool());
        student.setStudyStart(directiveStudent.getStartDate());
        student.setIsRepresentativeMandatory(Boolean.FALSE);
        student.setIsSpecialNeed(Boolean.FALSE);
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
