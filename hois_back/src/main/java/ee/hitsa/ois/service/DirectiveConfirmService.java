package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.exception.SingleMessageWithParamsException;
import ee.hitsa.ois.message.AcademicLeaveEnding;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.ErrorForField;
import ee.hitsa.ois.web.dto.directive.DirectiveViewStudentDto;

@Transactional
@Service
public class DirectiveConfirmService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private EkisService ekisService;
    @Autowired
    private EntityManager em;
    @Autowired
    private EmailGeneratorService emailGeneratorService;
    @Autowired
    private MessageTemplateService messageTemplateService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;

    public Directive sendToConfirm(Directive directive) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, directive.getStatus()), "Invalid directive status");

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        List<Error> allErrors = new ArrayList<>();
        if(directive.getDirectiveCoordinator() == null) {
            allErrors.add(new ErrorForField(Required.MESSAGE, "directiveCoordinator"));
        }
        if(directive.getStudents().isEmpty()) {
            allErrors.add(new Error("directive.missingstudents"));
        }
        Map<Long, DirectiveStudent> academicLeaves = findAcademicLeaves(directive);
        Set<Long> changedStudents = DirectiveType.KASKKIRI_TYHIST.equals(directiveType) ? new HashSet<>(directiveService.changedStudentsForCancel(directive.getCanceledDirective())) : Collections.emptySet();
        List<DirectiveViewStudentDto> invalidStudents = new ArrayList<>();
        // validate each student's data for given directive
        long rowNum = 0;
        for(DirectiveStudent ds : directive.getStudents()) {
            if(directiveType.validationGroup() != null) {
                Set<ConstraintViolation<DirectiveStudent>> errors = validator.validate(ds, directiveType.validationGroup());
                if(!errors.isEmpty()) {
                    for(ConstraintViolation<DirectiveStudent> e : errors) {
                        allErrors.add(new ErrorForField(e.getMessage(), propertyPath(rowNum, e.getPropertyPath().toString())));
                    }
                }
            }
            // TODO check for valid studentGroup for ennist, immat, okava, ovorm
            // TODO check for valid curriculumVersion for ennist (student's current), immat, okava
            if(DirectiveType.KASKKIRI_AKADK.equals(directiveType)) {
                // check that cancel date is inside academic leave period
                DirectiveStudent academicLeave = academicLeaves.get(EntityUtil.getId(ds.getStudent()));
                LocalDate leaveCancel = ds.getStartDate();
                // missing value is catched above, here we match only range from academic leave directive
                if(leaveCancel != null) {
                    String msg = null;
                    if(academicLeave == null) {
                        msg = "directive.academicLeaveApplicationNotfound";
                    } else if(leaveCancel.isBefore(DateUtils.periodStart(academicLeave))) {
                        msg = "directive.revocationStartDateBeforeAcademicLeaveStartDate";
                    } else if(leaveCancel.isAfter(DateUtils.periodEnd(academicLeave))) {
                        msg = "directive.revocationStartDateAfterAcademicLeaveEndDate";
                    }
                    if(msg != null) {
                        allErrors.add(new ErrorForField(msg, propertyPath(rowNum, "startDate")));
                    }
                }
            } else if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType)) {
                // check by hand because for immatv it's filled automatically after directive confirmation
                if(ds.getNominalStudyEnd() == null) {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "nominalStudyEnd")));
                }
                // check by hand because it is required only in higher study
                if(ds.getStudyLoad() == null && ds.getCurriculumVersion() != null && CurriculumUtil.isHigher(ds.getCurriculumVersion().getCurriculum())) {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "studyLoad")));
                }
            } else if(DirectiveType.KASKKIRI_STIPTOET.equals(directiveType)) {
                // check students which do not qualify for directive
                // status should be active and no overlapping academic leave
                String reason = null;
                if(academicLeaves.containsKey(EntityUtil.getId(ds.getStudent()))) {
                    reason = "directive.scholarshipOverlapsAcademicLeave";
                } else if(!StudentUtil.isActive(ds.getStudent())) {
                    reason = "directive.scholarshipStudentNotActive";
                }
                if(reason != null) {
                    DirectiveViewStudentDto dto = new DirectiveViewStudentDto();
                    Person p = ds.getPerson();
                    dto.setFullname(p.getFullname());
                    dto.setIdcode(p.getIdcode());
                    dto.setReason(reason);
                    invalidStudents.add(dto);
                }
            } else if(DirectiveType.KASKKIRI_TYHIST.equals(directiveType)) {
                // check that it's last modification of student
                if(changedStudents.contains(EntityUtil.getId(ds.getStudent()))) {
                    allErrors.add(new ErrorForField("StudentChanged", propertyPath(rowNum, "fullname")));
                }
            } else if(DirectiveType.KASKKIRI_VALIS.equals(directiveType)) {
                boolean isAbroad = Boolean.TRUE.equals(ds.getIsAbroad());
                if(isAbroad ? !StringUtils.hasText(ds.getAbroadSchool()) : ds.getEhisSchool() == null) {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, isAbroad ? "abroadSchool" : "ehisSchool")));
                }
            }
            rowNum++;
        }

        checkAutomaticMessageTemplates(directiveType, directive.getSchool(), allErrors);

        if(!allErrors.isEmpty()) {
            throw new ValidationFailedException(allErrors);
        }

        if(!invalidStudents.isEmpty()) {
            // show students which are not permitted for directive
            throw new SingleMessageWithParamsException(null, Collections.singletonMap("invalidStudents", invalidStudents));
        }

        directive = EntityUtil.save(directive, em);
        ekisService.registerDirective(EntityUtil.getId(directive));
        return directive;
    }

    public Directive rejectByEkis(long directiveId, String rejectComment, String preamble, long wdId) {
        //TODO veahaldus
        // • ÕISi käskkiri ei leitud – ÕISist ei leita vastava OIS_ID ja WD_ID-ga käskkirja
        // • Vale staatus – tagasi lükata saab ainult „kinnitamisel“ staatusega käskkirja. „Koostamisel“ ja „Kinnitatud“ käskkirju tagasi lükata ei saa.
        // • Üldine veateade – üldine viga salvestamisel, nt liiga lühike andmeväli, vale andmetüüp vms.
        Directive directive = findDirective(directiveId, wdId);
        LOG.info("directive {} rejected by ekis with reason {}", EntityUtil.getId(directive), rejectComment);
        setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL);
        directive.setPreamble(preamble);
        directive.setAddInfo(directive.getAddInfo() != null ? (directive.getAddInfo() + " " + rejectComment) : rejectComment);
        return EntityUtil.save(directive, em);
    }

    public Directive confirmedByEkis(long directiveId, String directiveNr, LocalDate confirmDate, String preamble, long wdId,
            String signerIdCode, String signerName) {
        //TODO veahaldus
        //• ÕISi käskkiri ei leitud – ÕISist ei leita vastava vastava OIS_ID ja WD_ID-ga käskkirja
        //• Vale staatus – kinnitada saab ainult „kinnitamisel“ staatusega käskkirja. „Koostamisel“ ja „Kinnitatud“ käskkirju kinnitada ei saa.
        //• Üldine veateade – üldine viga salvestamisel, nt liiga lühike andmeväli, vale andmetüüp vms
        Directive directive = findDirective(directiveId, wdId);
        directive.setDirectiveNr(directiveNr);
        directive.setPreamble(preamble);
        return confirm(PersonUtil.fullnameAndIdcode(signerName, signerIdCode), directive, confirmDate);
    }

    private Directive findDirective(long directiveId, long wdId) {
        try {
            Directive directive = em.getReference(Directive.class, Long.valueOf(directiveId));
            if(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, directive.getStatus())) {
                throw new HoisException("Käskkiri vale staatusega");
            }
            if(directive.getWdId() == null || directive.getWdId().longValue() != wdId) {
                throw new HoisException("Käskkiri vale ekise id-ga");
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
        if(DirectiveType.KASKKIRI_TYHIST.equals(directiveType)) {
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
                if(!ClassifierUtil.equals(newStatus, student.getStatus())) {
                    student.setStatus(em.getReference(Classifier.class, newStatus.name()));
                    studentService.saveWithHistory(student);
                }
            }
        }
    }

    public void sendAcademicLeaveEndingMessage(Job job) {
        Long studentId = EntityUtil.getId(job.getStudent());
        Long directiveId = EntityUtil.getId(job.getDirective());

        List<DirectiveStudent> data = em.createQuery("select ds from DirectiveStudent ds where ds.student.id = ?1 and ds.directive.id = ?2 and ds.canceled = false", DirectiveStudent.class)
            .setParameter(1, studentId)
            .setParameter(2, directiveId)
            .setMaxResults(1).getResultList();
        if(!data.isEmpty()) {
            DirectiveStudent directiveStudent = data.get(0);
            AcademicLeaveEnding message = new AcademicLeaveEnding(directiveStudent);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_AP_LOPP, directiveStudent.getStudent(), message);
        }
    }

    private void updateStudentData(DirectiveType directiveType, DirectiveStudent directiveStudent, Classifier studentStatus, DirectiveStudent academicLeave) {
        Student student = directiveStudent.getStudent();
        if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType) || DirectiveType.KASKKIRI_IMMATV.equals(directiveType)) {
            student = createStudent(directiveStudent);
        }

        // copy entered data from directive
        copyDirectiveProperties(directiveType, directiveStudent, student);

        // directive type specific calculated data and additional actions
        LocalDate confirmDate = directiveStudent.getDirective().getConfirmDate();
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
            userService.disableUser(student, confirmDate);
            break;
        case KASKKIRI_ENNIST:
            student.setStudyStart(confirmDate);
            student.setStudyEnd(null);
            userService.enableUser(student, confirmDate);
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
            userService.disableUser(student, confirmDate);
            break;
        default:
            break;
        }

        // optional new status
        if(studentStatus != null) {
            student.setStatus(studentStatus);
        }

        student = studentService.saveWithHistory(student);
        if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType) || DirectiveType.KASKKIRI_IMMATV.equals(directiveType)) {
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
        LocalDate confirmDate = directive.getConfirmDate();

        for(DirectiveStudent ds : canceledDirective.getStudents()) {
            Student student = ds.getStudent();
            DirectiveStudent cancelds = includedStudents.get(student.getId());
            if(cancelds != null) {
                if(DirectiveType.KASKKIRI_IMMAT.equals(canceledDirectiveType) || DirectiveType.KASKKIRI_IMMATV.equals(canceledDirectiveType)) {
                    // undo create student. Logic similar to EKSMAT
                    student.setStudyEnd(confirmDate);
                    student.setStatus(em.getReference(Classifier.class, StudentStatus.OPPURSTAATUS_K.name()));
                    userService.disableUser(student, confirmDate);
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
                        userService.enableUser(student, confirmDate);
                        break;
                    case KASKKIRI_ENNIST:
                        student.setStudyStart(original.getStudyStart());
                        student.setStudyEnd(original.getStudyEnd());
                        student.setStatus(original.getStatus());
                        userService.disableUser(student, confirmDate);
                        break;
                    case KASKKIRI_LOPET:
                        student.setStudyEnd(null);
                        student.setStatus(original.getStatus());
                        userService.enableUser(student, confirmDate);
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
                ds.setCanceled(Boolean.TRUE);
                cancelds.setStudentHistory(student.getStudentHistory());
                studentService.saveWithHistory(student);
            }
        }
        // FIXME what happens when student is changed between send-to-confirm and confirm
        if(ClassifierUtil.equals(DirectiveCancelType.KASKKIRI_TYHISTAMISE_VIIS_T, directive.getCancelType())) {
            setDirectiveStatus(directive.getCanceledDirective(), DirectiveStatus.KASKKIRI_STAATUS_TYHISTATUD);
        }
    }

    private void updateApplicationStatus(DirectiveStudent directiveStudent, Classifier applicationStatus) {
        Application application = directiveStudent.getApplication();
        if(application != null) {
            application.setStatus(applicationStatus);
            EntityUtil.save(application, em);
        }
    }

    private Map<Long, DirectiveStudent> findAcademicLeaves(Directive directive) {
        if(!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_AKADK, DirectiveType.KASKKIRI_STIPTOET) || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "+
                "inner join directive d on ds.directive_id = d.id and ds.canceled = false "+
                "left join study_period sps on ds.study_period_start_id = sps.id "+
                "left join study_period spe on ds.study_period_end_id = spe.id").sort(new Sort(Direction.DESC, "d.confirm_date"));

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_AKAD);
        if(ClassifierUtil.equals(DirectiveType.KASKKIRI_AKADK, directive.getType())) {
            qb.requiredCriteria("ds.application_id in (select a.academic_application_id from directive_student ds2 inner join application a on ds2.application_id = a.id where ds2.directive_id = :directiveId)", "directiveId", directive.getId());
        } else {
            // match academic leave period
            qb.requiredCriteria("exists(select 1 from directive_student ds2 where ds2.directive_id = :directiveId and ds2.start_date <= case when ds.is_period then spe.end_date else ds.end_date end and ds2.end_date >= case when ds.is_period then sps.start_date else ds.start_date end)", "directiveId", directive.getId());
        }

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

    private Student createStudent(DirectiveStudent directiveStudent) {
        School school = directiveStudent.getDirective().getSchool();

        Student student = new Student();
        student.setPerson(directiveStudent.getPerson());
        student.setSchool(school);
        student.setStudyStart(directiveStudent.getStartDate());
        student.setIsRepresentativeMandatory(Boolean.FALSE);
        student.setIsSpecialNeed(Boolean.FALSE);

        // fill student's email
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
        userService.enableUser(student, directiveStudent.getDirective().getConfirmDate());
        return student;
    }

    /**
     * Check for automatic message templates existence
     *
     * @param directiveType
     * @param school
     * @param allErrors
     */
    private void checkAutomaticMessageTemplates(DirectiveType directiveType, School school, List<Error> allErrors) {
        if(DirectiveType.KASKKIRI_TYHIST.equals(directiveType)) {
            return;
        }
        // for all other than KASKKIRI_TYHIST TEATE_LIIK_UUS_KK
        messageTemplateService.requireValidTemplate(MessageType.TEATE_LIIK_UUS_KK, school, allErrors);
        if(DirectiveType.KASKKIRI_AKAD.equals(directiveType) || DirectiveType.KASKKIRI_AKADK.equals(directiveType)) {
            // for akad and akadk also send TEATE_LIIK_AP_LOPP
            messageTemplateService.requireValidTemplate(MessageType.TEATE_LIIK_AP_LOPP, school, allErrors);
        }
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
