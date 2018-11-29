package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.diploma.Diploma;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementForm;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.sais.SaisApplicationGraduatedSchool;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveCancelType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.exception.SingleMessageWithParamsException;
import ee.hitsa.ois.message.AcademicLeaveEnding;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.message.StudentScholarshipEnding;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.PeriodRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.ErrorForField;
import ee.hitsa.ois.web.commandobject.directive.DirectiveConfirmForm;
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

    public Directive sendToConfirm(Directive directive, boolean ekis) {
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
        Map<Long, List<DirectiveStudent>> scholarships = findScholarships(directive);
        Set<Long> changedStudents = DirectiveType.KASKKIRI_TYHIST.equals(directiveType) ? new HashSet<>(directiveService.changedStudentsForCancel(directive.getCanceledDirective())) : Collections.emptySet();
        List<DirectiveViewStudentDto> invalidStudents = new ArrayList<>();
        // validate each student's data for given directive
        long rowNum = 0;
        directive.getStudents().sort(Comparator.comparing(DirectiveStudent::getId));
        for(DirectiveStudent ds : directive.getStudents()) {
            if(directiveType.validationGroup() != null) {
                Set<ConstraintViolation<DirectiveStudent>> errors = validator.validate(ds, directiveType.validationGroup());
                if(!errors.isEmpty()) {
                    for(ConstraintViolation<DirectiveStudent> e : errors) {
                        String message = e.getMessage();
                        String propertyPath = e.getPropertyPath().toString();
                        // map MissingPeriodRange to startDate/studyPeriodStart/endDate/studyPeriodEnd
                        if(PeriodRange.MESSAGE.equals(message)) {
                            message = "required";
                            boolean startMissing = DateUtils.periodStart(ds) == null, endMissing = DateUtils.periodEnd(ds) == null;
                            propertyPath = Boolean.TRUE.equals(ds.getIsPeriod()) ? "studyPeriodEnd" : "endDate";
                            if(startMissing) {
                                if(endMissing) {
                                    // both missing, add one more error for start field
                                    allErrors.add(new ErrorForField(message, propertyPath(rowNum, Boolean.TRUE.equals(ds.getIsPeriod()) ? "studyPeriodStart" : "startDate")));
                                } else {
                                    // just start missing, change property path
                                    propertyPath = Boolean.TRUE.equals(ds.getIsPeriod()) ? "studyPeriodStart" : "startDate";
                                }
                            }
                        }
                        allErrors.add(new ErrorForField(message, propertyPath(rowNum, propertyPath)));
                    }
                }
            }
            // FIXME check for valid studentGroup for ennist, immat, okava, ovorm?
            // FIXME check for valid curriculumVersion for ennist (student's current), immat, okava?
            if(DirectiveType.KASKKIRI_AKAD.equals(directiveType)) {
                List<DirectiveStudent> studentScholarships = scholarships.get(EntityUtil.getId(ds.getStudent()));
                if(studentScholarships != null) {
                    LocalDate startDate = DateUtils.periodStart(ds);
                    LocalDate endDate = DateUtils.periodEnd(ds);
                    if(studentScholarships.stream().anyMatch(p -> !p.getStartDate().isAfter(endDate) && !p.getEndDate().isBefore(startDate)
                            && !Boolean.TRUE.equals(p.getScholarshipApplication().getScholarshipTerm().getIsAcademicLeave()))) {
                        invalidStudents.add(createInvalidStudent(ds, "directive.scholarshipExists"));
                    }
                }
            } else if(DirectiveType.KASKKIRI_AKADK.equals(directiveType)) {
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
            } else if(DirectiveType.KASKKIRI_EKSMAT.equals(directiveType)) {
                // check for existing scholarship
                if(scholarships.containsKey(EntityUtil.getId(ds.getStudent()))) {
                    invalidStudents.add(createInvalidStudent(ds, "directive.scholarshipExists"));
                }
            } else if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType)) {
                // check by hand because for immatv it's filled automatically after directive confirmation
                if(ds.getNominalStudyEnd() == null) {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "nominalStudyEnd")));
                }
                CurriculumVersion curriculumVersion = ds.getCurriculumVersion();
                if (curriculumVersion != null) {
                    Curriculum curriculum = curriculumVersion.getCurriculum();
                    // check by hand because it is required only in higher study
                    if(ds.getStudyLoad() == null && CurriculumUtil.isHigher(curriculum)) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "studyLoad")));
                    }
                    Set<Long> uniqueRows = new LinkedHashSet<>();
                    if (directiveService.studentExists(EntityUtil.getId(directive.getSchool()), EntityUtil.getId(ds.getPerson()), 
                            EntityUtil.getId(curriculum))) {
                        uniqueRows.add(Long.valueOf(rowNum));
                    }
                    for (DirectiveStudent ds2 : directive.getStudents()) {
                        if (!ds2.equals(ds) && ds2.getCurriculumVersion() != null) {
                            if (ds.getPerson().equals(ds2.getPerson()) 
                                    && curriculum.equals(ds2.getCurriculumVersion().getCurriculum())) {
                                uniqueRows.add(Long.valueOf(rowNum));
                            }
                        }
                    }
                    for (Long existRow : uniqueRows) {
                        allErrors.add(createStudentExistsError(existRow.longValue()));
                    }
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
                    invalidStudents.add(createInvalidStudent(ds, reason));
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

        if(!allErrors.isEmpty()) {
            throw new ValidationFailedException(allErrors);
        }

        if(!invalidStudents.isEmpty()) {
            // show students which are not permitted for directive
            throw new SingleMessageWithParamsException(null, Collections.singletonMap("invalidStudents", invalidStudents));
        }

        if(!ekis) {
            setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL);
        }
        directive.getStudents().sort(Comparator.comparing(ds -> ds.getPerson().getLastname()));
        directive = EntityUtil.save(directive, em);
        if(ekis) {
            ekisService.registerDirective(EntityUtil.getId(directive));
        }
        return directive;
    }

    public static ErrorForField createStudentExistsError(long rowNum) {
        return new ErrorForField("studentExists", propertyPath(rowNum, "idcode"));
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

    public Directive confirmedByUser(String confirmer, Directive directive, LocalDate confirmDate, DirectiveConfirmForm form) {
        directive.setDirectiveNr(form.getDirectiveNr());
        directive.setPreamble(form.getPreamble());
        return confirm(confirmer, directive, confirmDate);
    }

    /**
     * Check if preconditions are ok for confirming directive
     *
     * @param user
     * @param directiveId
     * @return
     * @throws ValidationFailedException if something is wrong
     */
    public Map<String, ?> checkForConfirm(HoisUserDetails user, Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);
        School school = directive.getSchool();
        UserUtil.assertSameSchool(user, school);
        List<Error> errors = new ArrayList<>();

        if(!ClassifierUtil.equals(DirectiveType.KASKKIRI_TYHIST, directive.getType())) {
            // for all other than KASKKIRI_TYHIST TEATE_LIIK_UUS_KK
            messageTemplateService.requireValidTemplate(MessageType.TEATE_LIIK_UUS_KK, school, errors);
            if(ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_AKAD, DirectiveType.KASKKIRI_AKADK)) {
                // for akad and akadk also send TEATE_LIIK_AP_LOPP
                messageTemplateService.requireValidTemplate(MessageType.TEATE_LIIK_AP_LOPP, school, errors);
            }
        }

        Map<String, Object> status = new HashMap<>();
        status.put("templateExists", Boolean.valueOf(errors.isEmpty()));
        status.put("templateName", StreamUtil.toMappedList(r -> r.getParams().values().iterator().next(), errors));
        return status;
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
            cancelScholarships(directiveStudent);
            break;
        case KASKKIRI_AKADK:
            duration = ChronoUnit.DAYS.between(DateUtils.periodStart(academicLeave), directiveStudent.getStartDate());
            student.setNominalStudyEnd(student.getNominalStudyEnd().minusDays(duration));
            break;
        case KASKKIRI_EKSMAT:
            // FIXME correct field for Õppuri eksmatrikuleerimise kuupäev?
            student.setStudyEnd(confirmDate);
            userService.disableUser(student, LocalDate.now().minusDays(1));
            cancelScholarships(directiveStudent);
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
            userService.disableUser(student, LocalDate.now().minusDays(1));
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
        if (DirectiveType.KASKKIRI_STIPTOETL.equals(directiveType)) {
            ScholarshipApplication scholarshipApplication = directiveStudent.getScholarshipApplication();
            if (scholarshipApplication != null) {
                StudentScholarshipEnding scholarshipData = new StudentScholarshipEnding(scholarshipApplication);
                automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_TOET_KATK, student, scholarshipData);
            }
        }
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
                        cancelFormsAndDocuments(directive.getConfirmer(), ds);
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
    
    private void cancelFormsAndDocuments(String username, DirectiveStudent directiveStudent) {
        Directive directive = directiveStudent.getDirective();
        Student student = directiveStudent.getStudent();
        List<Form> forms = em.createQuery("select d.form from Diploma d"
                + " where d.student = ?1 and d.form.status.code = ?2", Form.class)
                .setParameter(1, student)
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList();
        forms.addAll(em.createQuery("select dsf.form from DiplomaSupplementForm dsf"
                + " where dsf.diplomaSupplement.diploma.student = ?1"
                + " and dsf.form.status.code = ?2", Form.class)
                .setParameter(1, student)
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList());
        Classifier formStatus = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name());
        String reason = "Käskkiri nr " + (directive.getDirectiveNr() != null ? directive.getDirectiveNr() : " ")
                + " (" + DateUtils.date(directive.getConfirmDate()) + ") tühistatud";
        LocalDate now = LocalDate.now();
        for (Form form : forms) {
            form.setStatus(formStatus);
            form.setDefectReason(reason);
            form.setDefected(now);
            form.setDefectedBy(username);
            EntityUtil.save(form, em);
        }
        
        List<DiplomaSupplementForm> supplementForms = em.createQuery("select dsf from DiplomaSupplementForm dsf"
                + " where dsf.diplomaSupplement.diploma.student = ?1", DiplomaSupplementForm.class)
                .setParameter(1, student)
                .getResultList();
        for (DiplomaSupplementForm supplementForm : supplementForms) {
            EntityUtil.deleteEntity(supplementForm, em);
        }
        List<DiplomaSupplement> supplements = em.createQuery("select ds from DiplomaSupplement ds"
                + " where ds.diploma.student = ?1", DiplomaSupplement.class)
                .setParameter(1, student)
                .getResultList();
        for (DiplomaSupplement supplement : supplements) {
            EntityUtil.deleteEntity(supplement, em);
        }
        List<Diploma> diplomas = em.createQuery("select d from Diploma d"
                + " where d.student = ?1", Diploma.class)
                .setParameter(1, student)
                .getResultList();
        for (Diploma diploma : diplomas) {
            EntityUtil.deleteEntity(diploma, em);
        }
    }

    private void cancelScholarships(DirectiveStudent directiveStudent) {
        List<ScholarshipApplication> scholarships = em.createQuery("select sa from ScholarshipApplication sa where sa.student.id = ?1 and sa.status.code = ?2 " +
                "and not exists(select ds from DirectiveStudent ds where ds.scholarshipApplication = sa)", ScholarshipApplication.class)
            .setParameter(1, EntityUtil.getId(directiveStudent.getStudent()))
            .setParameter(2, ScholarshipStatus.STIPTOETUS_STAATUS_A.name())
            .getResultList();

        if(!scholarships.isEmpty()) {
            // reject all matching scholarship applications which are not on directive
            Classifier rejected = em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_L.name());
            LocalDate startDate = DateUtils.periodStart(directiveStudent);
            LocalDate endDate = DateUtils.periodEnd(directiveStudent);

            for(ScholarshipApplication sa : scholarships) {
                if(ClassifierUtil.equals(DirectiveType.KASKKIRI_AKAD, directiveStudent.getDirective().getType())) {
                    // only those matching period and not allowed during academic leave
                    if(startDate.isAfter(DateUtils.endDate(sa)) || endDate.isBefore(DateUtils.startDate(sa)) ||
                            Boolean.TRUE.equals(sa.getScholarshipTerm().getIsAcademicLeave())) {
                        continue;
                    }
                }
                sa.setStatus(rejected);
            }
        }
    }

    private void updateApplicationStatus(DirectiveStudent directiveStudent, Classifier applicationStatus) {
        Application application = directiveStudent.getApplication();
        if(application != null) {
            application.setStatus(applicationStatus);
            EntityUtil.save(application, em);
        }
    }

    Map<Long, DirectiveStudent> findAcademicLeaves(Directive directive) {
        if(!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_AKADK, DirectiveType.KASKKIRI_STIPTOET) || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "+
                "join directive d on ds.directive_id = d.id and ds.canceled = false "+
                "left join study_period sps on ds.study_period_start_id = sps.id "+
                "left join study_period spe on ds.study_period_end_id = spe.id").sort(new Sort(Direction.DESC, "d.confirm_date"));

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        qb.requiredCriteria("ds.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_AKAD);
        if(ClassifierUtil.equals(DirectiveType.KASKKIRI_AKADK, directive.getType())) {
            qb.requiredCriteria("ds.directive_id in (select a.directive_id"
                    + " from directive_student ds2"
                    + " join application a on ds2.application_id = a.id"
                    + " where ds2.directive_id = :directiveId)", "directiveId", directive.getId());
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

    Map<Long, List<DirectiveStudent>> findScholarships(Directive directive) {
        if(!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_AKAD, DirectiveType.KASKKIRI_EKSMAT) || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }
        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        // scholarships which have not ended or which have no directive to end payment
        List<DirectiveStudent> data = em.createQuery("select ds from DirectiveStudent ds " +
                "where ds.canceled = false and ds.student.id in (?1) and ds.directive.type.code = ?2 and ds.directive.status.code = ?3 and ds.endDate >= CURRENT_DATE " +
                "and not exists(select ds2 from DirectiveStudent ds2 where ds2.canceled = false " +
                    "and ds2.scholarshipApplication = ds.scholarshipApplication and ds2.directive.type.code = ?4 and ds2.directive.status.code = ?5)", DirectiveStudent.class)
            .setParameter(1, studentIds)
            .setParameter(2, DirectiveType.KASKKIRI_STIPTOET.name())
            .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(4, DirectiveType.KASKKIRI_STIPTOETL.name())
            .setParameter(5, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> EntityUtil.getId(r.getStudent())));
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

        if(directiveStudent.getSaisApplication() != null) {
            Iterator<SaisApplicationGraduatedSchool> i = directiveStudent.getSaisApplication().getGraduatedSchools().iterator();
            if(i.hasNext()) {
                SaisApplicationGraduatedSchool s = i.next();
                student.setPreviousSchoolName(s.getName());
                student.setPreviousSchoolEndDate(s.getEndDate());
            }
        }

        // new role for student
        userService.enableUser(student, directiveStudent.getDirective().getConfirmDate());
        return student;
    }

    private static DirectiveViewStudentDto createInvalidStudent(DirectiveStudent ds, String reason) {
        DirectiveViewStudentDto dto = new DirectiveViewStudentDto();
        dto.setStudent(EntityUtil.getNullableId(ds.getStudent()));
        Person p = ds.getPerson();
        dto.setFullname(p.getFullname());
        dto.setIdcode(p.getIdcode());
        dto.setReason(reason);
        return dto;
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

    static String propertyPath(long rowNum, String property) {
        String pathPrefix = String.format("students[%s]", Long.valueOf(rowNum));
        return property.isEmpty() ? pathPrefix : pathPrefix + "." + property;
    }
}
