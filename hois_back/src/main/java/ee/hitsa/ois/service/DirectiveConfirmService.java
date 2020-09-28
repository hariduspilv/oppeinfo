package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationSupportService;
import ee.hitsa.ois.domain.application.ApplicationSupportServiceModule;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.directive.DirectiveStudentDuplicateForm;
import ee.hitsa.ois.domain.directive.DirectiveStudentModule;
import ee.hitsa.ois.domain.sais.SaisApplicationGraduatedSchool;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.DirectiveCancelType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.exception.SingleMessageWithParamsException;
import ee.hitsa.ois.message.AcademicLeaveEnding;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.message.StudentScholarshipEnding;
import ee.hitsa.ois.service.SchoolService.SchoolType;
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
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.PeriodRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.ErrorForField;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.ErrorForIcpField;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.directive.DirectiveViewStudentDto;
import ee.hitsa.ois.web.dto.directive.ExistingDirectiveStudentDto;
import ee.hitsa.ois.web.dto.directive.ExistingIndividualCurriculumModuleDto;
import ee.hitsa.ois.web.dto.directive.IndividualCurriculumModuleDto;

@Transactional
@Service
public class DirectiveConfirmService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private ContractService contractService;
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
    private StudentAbsenceService studentAbsenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;
    @Autowired
    private PersonService personService;
    @Autowired
    private SchoolService schoolService;

    private static final String ABSENCE_REJECT_DIRECTIVE_CANCELED = "Käskkiri tühistatud";

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
        Map<Long, List<ExistingDirectiveStudentDto>> woApplicationScholarships = directiveService
                .getExistingStudentStipendsWOApplicationDirectives(directive,
                        directive.getStudents().stream().filter(ds -> ds.getStudent() != null)
                                .map(ds -> EntityUtil.getId(ds.getStudent())).collect(Collectors.toSet()));
        Map<Long, List<IndividualCurriculumModuleDto>> individualCurriculumSuitableModules = individualCurriculumSuitableModules(directive);
        Map<Long, List<DirectiveStudent>> individualCurriculums = findIndividualCurriculums(directive);
        Map<Long, List<ScholarshipApplication>> scholarshipApplications = findScholarshipApplications(directive);
        Map<Long, List<DirectiveStudent>> terminationScholarships = findScholarshipsAvailableForTermination(directive);
        Map<Long, List<DirectiveStudent>> abroadStudies = findAbroadStudies(directive);
        Set<Long> changedStudents = DirectiveType.KASKKIRI_TYHIST.equals(directiveType) ? new HashSet<>(directiveService.changedStudentsForCancel(directive.getCanceledDirective())) : Collections.emptySet();
        List<DirectiveViewStudentDto> invalidStudents = new ArrayList<>();
        // validate each student's data for given directive
        long rowNum = 0;
        directive.getStudents().sort(DirectiveUtil.getStudentDtoComparator(directiveType));
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
            } else if(DirectiveType.KASKKIRI_KYLALIS.equals(directiveType)) {
                SchoolType schoolType = schoolService.schoolType(EntityUtil.getId(directive.getSchool()));
                boolean isOnlyHigher = schoolType.isHigher() && !schoolType.isVocational();
                
                CurriculumVersion curriculumVersion = ds.getCurriculumVersion();
                if (curriculumVersion != null) {
                    Curriculum curriculum = curriculumVersion.getCurriculum();
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
                // manually checked fields for higher
                // school that is both higher and vocational checks directive school type
                if (isOnlyHigher || (directive.getIsHigher() != null && directive.getIsHigher().booleanValue())) {
                    if (ds.getPreviousStudyLevel() == null) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "previousStudyLevel")));
                    }
                    if (ds.getAbroadProgramme() == null) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "abroadProgramme")));
                    }
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
                    // check by hand because it is required only in vocational study
                    if(ds.getDormitory() == null && CurriculumUtil.isVocational(curriculum)) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "dormitory")));
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
            } else if(DirectiveType.KASKKIRI_IMMATV.equals(directiveType)) {
                CurriculumVersion curriculumVersion = ds.getCurriculumVersion();
                if (curriculumVersion != null) {
                    Curriculum curriculum = curriculumVersion.getCurriculum();
                    // check by hand because it is required only in vocational study
                    if(ds.getDormitory() == null && CurriculumUtil.isVocational(curriculum)) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "dormitory")));
                    }
                }
            } else if (DirectiveType.KASKKIRI_INDOK.equals(directiveType)) {
                if (ds.getModules() == null || ds.getModules().isEmpty()) {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "modules")));
                } else {
                    Map<Long, IndividualCurriculumModuleDto> studentSuitableModules = StreamUtil.toMap(m -> m.getId(),
                            individualCurriculumSuitableModules.get(EntityUtil.getId(ds.getStudent())));
                    for (DirectiveStudentModule module : ds.getModules()) {
                        IndividualCurriculumModuleDto suitableModule = studentSuitableModules
                                .get(EntityUtil.getId(module.getCurriculumVersionOmodule()));
                        if (suitableModule == null) {
                            allErrors.add(new ErrorForField("directive.moduleAlreadyGraded", propertyPath(rowNum, "modules"),
                                    AutocompleteResult.of(module.getCurriculumVersionOmodule(), false)));
                        } else if (ds.getStartDate() != null && ds.getEndDate() != null
                                && suitableModule.getExistingModules() != null) {
                            for (ExistingIndividualCurriculumModuleDto existingModule : suitableModule
                                    .getExistingModules()) {
                                if (DateUtils.periodsOverlap(ds.getStartDate(), ds.getEndDate(),
                                        existingModule.getStartDate(), existingModule.getEndDate())) {
                                    allErrors.add(new ErrorForIcpField("directive.moduleAlreadyInIndividualCurriculum",
                                            propertyPath(rowNum, "modules"), AutocompleteResult.of(module.getCurriculumVersionOmodule(), false),
                                            existingModule.getStartDate(), existingModule.getEndDate()));
                                }
                            }
                        }
                    }
                }
            } else if (DirectiveType.KASKKIRI_INDOKLOP.equals(directiveType)) {
                DirectiveStudent formIndividualCurriculum = ds.getDirectiveStudent();
                if (formIndividualCurriculum != null) {
                    List<DirectiveStudent> studentIndividualCurriculums = individualCurriculums
                            .get(EntityUtil.getId(ds.getStudent()));
                    boolean formIndividualCurriculumAllowed = StreamUtil.nullSafeList(studentIndividualCurriculums).stream()
                            .anyMatch(s -> s.equals(formIndividualCurriculum));

                    if (formIndividualCurriculumAllowed) {
                        if (ds.getStartDate() != null) {
                            if (ds.getStartDate().isBefore(formIndividualCurriculum.getStartDate())
                                    || ds.getStartDate().isAfter(formIndividualCurriculum.getEndDate())) {
                                allErrors.add(new ErrorForField("directive.notInIndividualCurriculumDateRange",
                                        propertyPath(rowNum, "startDate")));
                            }
                        } else {
                            allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "startDate")));
                        }
                    } else {
                        allErrors.add(new ErrorForField("directive.notValid", propertyPath(rowNum, "directiveStudent")));
                    }
                }
            } else if (DirectiveType.KASKKIRI_EKSTERN.equals(directiveType)) {
                if (ds.getPreviousStudyLevel() == null) {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "previousStudyLevel")));
                }
            } else if (DirectiveType.KASKKIRI_STIPTOET.equals(directiveType)) {
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

                ScholarshipApplication formApplication = ds.getScholarshipApplication();
                if (formApplication != null) {
                    List<ScholarshipApplication> studentApplications = scholarshipApplications
                            .get(EntityUtil.getId(ds.getStudent()));
                    boolean formScholarshipAllowed = StreamUtil.nullSafeList(studentApplications).stream()
                            .anyMatch(s -> s.equals(formApplication));
                    if (!formScholarshipAllowed) {
                        allErrors.add(new ErrorForField("directive.scholarshipNotAccepted",
                                propertyPath(rowNum, "scholarshipApplication")));
                    }
                } else if (directive.getScholarshipEhis() != null) {
                    if (ds.getBankAccount() == null) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, DirectiveConfirmService.propertyPath(rowNum, "bankAccount")));
                    }
                    List<ExistingDirectiveStudentDto> studentExistingDirectives = woApplicationScholarships
                            .get(EntityUtil.getId(ds.getStudent()));
                    if (studentExistingDirectives != null) {
                        for (ExistingDirectiveStudentDto existingDirective : studentExistingDirectives) {
                            if (DateUtils.periodsOverlap(ds.getStartDate(), ds.getEndDate(),
                                    existingDirective.getStartDate(), existingDirective.getEndDate())) {
                                allErrors.add(new ErrorForIcpField("stipendExists", propertyPath(rowNum, "student"), null,
                                        existingDirective.getStartDate(), existingDirective.getEndDate()));
                            }
                        }
                    }
                } else {
                    allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "scholarshipApplication")));
                }
            } else if (DirectiveType.KASKKIRI_STIPTOETL.equals(directiveType)) {
                // status should be active
                String reason = null;
                if(!StudentUtil.isActive(ds.getStudent())) {
                    reason = "directive.scholarshipStudentNotActive";
                }
                if(reason != null) {
                    invalidStudents.add(createInvalidStudent(ds, reason));
                }

                DirectiveStudent formScholarshipDirective = ds.getDirectiveStudent();
                List<DirectiveStudent> studentTerminationScholarships = terminationScholarships
                        .get(EntityUtil.getId(ds.getStudent()));
                boolean formDirectiveAllowed = StreamUtil.nullSafeList(studentTerminationScholarships).stream()
                        .anyMatch(s -> s.equals(formScholarshipDirective));
                if (!formDirectiveAllowed) {
                    allErrors.add(new ErrorForField("directive.notValid", propertyPath(rowNum, "directiveStudent")));
                }
            } else if (DirectiveType.KASKKIRI_TUGI.equals(directiveType)) {
                if (!StudentUtil.hasSpecialNeeds(ds.getStudent())) {
                    Map<Object, Object> obj = new HashMap<>();
                    obj.put("student", AutocompleteResult.of(ds.getStudent(), false));
                    allErrors.add(new Error("directive.studentHasNoSpecialNeedWithName", obj));
                }
                
                // NominalStudyEnd should be after the current one
                if (ds.getStudent().getNominalStudyEnd() != null && ds.getNominalStudyEnd() != null && ds.getStudent().getNominalStudyEnd().isAfter(ds.getNominalStudyEnd())) {
                    allErrors.add(new ErrorForField("directive.studentHasWrongNominalStudyEnd", propertyPath(rowNum, "nominalStudyEnd")));
                }

                if (ds.getEndDate() != null && ds.getEndDate().isBefore(LocalDate.now())) {
                    allErrors.add(new ErrorForField("directive.endDateInPast", propertyPath(rowNum, "endDate")));
                }
                
                Optional<ApplicationSupportService> assOpt = StreamUtil.nullSafeSet(ds.getApplication().getSupportServices()).stream()
                        .filter(s -> ClassifierUtil.equals(SupportServiceType.TUGITEENUS_1, s.getSupportService()))
                        .findFirst();
                if (assOpt.isPresent()) {
                    ApplicationSupportService ass = assOpt.get();
                    if (ds.getStartDate() != null && ds.getEndDate() != null) {
                        Map<Long, IndividualCurriculumModuleDto> studentSuitableModules = StreamUtil.toMap(m -> m.getId(),
                                individualCurriculumSuitableModules.get(EntityUtil.getId(ds.getStudent())));

                        for (ApplicationSupportServiceModule module : ass.getModules()) {
                            IndividualCurriculumModuleDto suitableModule = studentSuitableModules
                                    .get(EntityUtil.getId(module.getModule()));

                            if (suitableModule == null) {
                                allErrors.add(new ErrorForField("directive.moduleHasPositiveGrade", propertyPath(rowNum, "modules"),
                                        AutocompleteResult.of(module.getModule(), false)));
                            } else if (suitableModule.getExistingModules() != null) {
                                for (ExistingIndividualCurriculumModuleDto existingModule : suitableModule
                                        .getExistingModules()) {
                                    if (DateUtils.periodsOverlap(ds.getStartDate(), ds.getEndDate(),
                                            existingModule.getStartDate(), existingModule.getEndDate())) {
                                        allErrors.add(new ErrorForIcpField("directive.moduleAlreadyInIndividualCurriculum",
                                                propertyPath(rowNum, "modules"), AutocompleteResult.of(module.getModule(), false),
                                                existingModule.getStartDate(), existingModule.getEndDate()));
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (DirectiveType.KASKKIRI_TUGILOPP.equals(directiveType)) {
                DirectiveStudent otherDirectiveStudent = ds.getDirectiveStudent();
                if (otherDirectiveStudent != null) {
                    if (ds.getStartDate() != null) {
                        if (ds.getStartDate().isBefore(otherDirectiveStudent.getStartDate())
                                || ds.getStartDate().isAfter(otherDirectiveStudent.getEndDate())) {
                            allErrors.add(new ErrorForField("directive.notInSupportServiceDateRange",
                                    propertyPath(rowNum, "startDate")));
                        } else if (ds.getStartDate().isBefore(LocalDate.now())) {
                            allErrors.add(new ErrorForField("directive.endDateInPast", propertyPath(rowNum, "startDate")));
                        }
                    } else {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "startDate")));
                    }
                }
            } else if(DirectiveType.KASKKIRI_TYHIST.equals(directiveType)) {
                // check that it's last modification of student
                if(changedStudents.contains(EntityUtil.getId(ds.getStudent()))) {
                    allErrors.add(new ErrorForField("StudentChanged", propertyPath(rowNum, "fullname")));
                }
            } else if(DirectiveType.KASKKIRI_VALIS.equals(directiveType)) {
                SchoolType schoolType = schoolService.schoolType(EntityUtil.getId(directive.getSchool()));
                boolean isOnlyHigher = schoolType.isHigher() && !schoolType.isVocational();
                // manually checked fields for higher
                // school that is both higher and vocational checks directive school type
                if (isOnlyHigher || (directive.getIsHigher() != null && directive.getIsHigher().booleanValue())) {
                    if (ds.getAbroadProgramme() == null) {
                        allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "abroadProgramme")));
                    }
                }
                ApplicationUtil.assertValisDirectiveConstraints(ds, em);
            } else if(DirectiveType.KASKKIRI_VALISKATK.equals(directiveType)) {
                DirectiveStudent formAbroadStudies = ds.getDirectiveStudent();
                if (formAbroadStudies != null) {
                    List<DirectiveStudent> studentAbroadStudies = abroadStudies.get(EntityUtil.getId(ds.getStudent()));
                    boolean formIndividualCurriculumAllowed = StreamUtil.nullSafeList(studentAbroadStudies).stream()
                            .anyMatch(s -> s.equals(formAbroadStudies));

                    if (formIndividualCurriculumAllowed) {
                        if (ds.getStartDate() != null) {
                            if (ds.getStartDate().isBefore(DateUtils.periodStart(formAbroadStudies))
                                    || ds.getStartDate().isAfter(DateUtils.periodEnd(formAbroadStudies))) {
                                allErrors.add(new ErrorForField("directive.notInAbroadStudiesDateRange",
                                        propertyPath(rowNum, "startDate")));
                            } else if (!ds.getStartDate().isAfter(DateUtils.periodStart(formAbroadStudies))) {
                                allErrors.add(new ErrorForField("directive.disruptedAtAbroadStudiesStart",
                                        propertyPath(rowNum, "startDate")));
                            }
                        } else {
                            allErrors.add(new ErrorForField(Required.MESSAGE, propertyPath(rowNum, "startDate")));
                        }
                    } else {
                        allErrors.add(new ErrorForField("directive.notValid", propertyPath(rowNum, "directiveStudent")));
                    }
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
        directive.getStudents().sort(DirectiveUtil.getStudentEkisComparator(directiveType));
        directive = EntityUtil.save(directive, em);
        if(ekis) {
            ekisService.registerDirective(EntityUtil.getId(directive));
        }
        return directive;
    }

    public static ErrorForField createStudentExistsError(long rowNum) {
        return new ErrorForField("studentExists", propertyPath(rowNum, "idcode"));
    }

    public Directive rejectByEkis(long directiveId, String rejectComment, String preamble, long wdId, long schoolId) {
        //TODO veahaldus
        // • ÕISi käskkiri ei leitud – ÕISist ei leita vastava OIS_ID ja WD_ID-ga käskkirja
        // • Vale staatus – tagasi lükata saab ainult „kinnitamisel“ staatusega käskkirja. „Koostamisel“ ja „Kinnitatud“ käskkirju tagasi lükata ei saa.
        // • Üldine veateade – üldine viga salvestamisel, nt liiga lühike andmeväli, vale andmetüüp vms.
        Directive directive = findDirective(directiveId, wdId, schoolId);
        LOG.info("directive {} rejected by ekis with reason {}", EntityUtil.getId(directive), rejectComment);
        setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL);
        directive.setPreamble(preamble);
        directive.setAddInfo(directive.getAddInfo() != null ? (directive.getAddInfo() + " " + rejectComment) : rejectComment);
        return EntityUtil.save(directive, em);
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
        
        List<Error> validationErrors = new ArrayList<>();
        switch (EnumUtil.valueOf(DirectiveType.class, directive.getType())) {
            case KASKKIRI_TUGI:
                Optional<DirectiveStudent> dsOpt = directive.getStudents().stream().filter(ds -> !StudentUtil.hasSpecialNeeds(ds.getStudent())).findAny();
                if (dsOpt.isPresent()) {
                    Map<Object, Object> obj = new HashMap<>();
                    obj.put("student", AutocompleteResult.of(dsOpt.get().getStudent(), false));
                    validationErrors.add(new Error("directive.studentHasNoSpecialNeedWithName", obj));
                }
                break;
            default:
                break;
        }

        Map<String, Object> status = new HashMap<>();
        status.put("templateExists", Boolean.valueOf(errors.isEmpty()));
        status.put("templateName", StreamUtil.toMappedList(r -> r.getParams().values().iterator().next(), errors));
        status.put("validationError", validationErrors);
        return status;
    }

    private Directive findDirective(long directiveId, long wdId, long schoolId) {
        try {
            Directive directive = em.getReference(Directive.class, Long.valueOf(directiveId));

            School school = directive.getSchool();
            // ekis true, school 0 - throw
            // ekis false, school 0 - OK
            // ekis true, school !0 - OK, if same school
            // ekis false, school !0 - throw
            if (
                    (school.getEkisUrl() != null && (Long.valueOf(0).equals(schoolId) || !school.getId().equals(schoolId)))
                ||
                    (school.getEkisUrl() == null && !Long.valueOf(0).equals(schoolId))
            ) {
                throw new EntityNotFoundException();
            }

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
        return confirmDirectiveChanges(confirmer, directive, confirmDate);
    }

    @Transactional(TxType.REQUIRES_NEW)
    public Directive confirmDirectiveChanges(String confirmer, Directive directive, LocalDate confirmDate) {
        AssertionFailedException.throwIf(!ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, directive.getStatus()), "Invalid directive status");
        AssertionFailedException.throwIf(ClassifierUtil.equals(DirectiveType.KASKKIRI_TUGI, directive.getType())
                && directive.getStudents().stream().filter(ds -> !StudentUtil.hasSpecialNeeds(ds.getStudent())).findAny().isPresent(), "Student without special needs");

        // update directive fields
        setDirectiveStatus(directive, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        directive.setConfirmDate(confirmDate);
        directive.setConfirmer(confirmer);

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(DirectiveType.KASKKIRI_TYHIST.equals(directiveType)) {
            cancelDirective(confirmer, directive);
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
        directive = EntityUtil.save(directive, em);
        em.flush();
        return directive;
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
        boolean newStudent = false;
        if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType) || DirectiveType.KASKKIRI_IMMATV.equals(directiveType) 
                || DirectiveType.KASKKIRI_KYLALIS.equals(directiveType)
                || (EntityUtil.getNullableId(student) == null && DirectiveType.KASKKIRI_EKSTERN.equals(directiveType))) {
            student = createStudent(directiveStudent);
            newStudent = true;
        }

        // copy entered data from directive
        copyDirectiveProperties(directiveType, directiveStudent, student);

        // directive type specific calculated data and additional actions
        LocalDate confirmDate = directiveStudent.getDirective().getConfirmDate();
        long duration;

        switch(directiveType) {
        case KASKKIRI_KYLALIS:
            student.setType(EntityUtil.getOptionalOne(StudentType.OPPUR_K.name(), em));
            student.setStudyStart(directiveStudent.getStartDate());
            student.setStudyEnd(directiveStudent.getEndDate());
            student.setPreviousStudyLevel(null);
            break;
        case KASKKIRI_AKAD:
            duration = ChronoUnit.DAYS.between(DateUtils.periodStart(directiveStudent), DateUtils.periodEnd(directiveStudent).plusDays(1));
            student.setNominalStudyEnd(student.getNominalStudyEnd().plusDays(duration));
            cancelScholarships(directiveStudent);
            break;
        case KASKKIRI_AKADK:
            duration = ChronoUnit.DAYS.between(DateUtils.periodStart(academicLeave), directiveStudent.getStartDate());
            student.setNominalStudyEnd(student.getNominalStudyEnd().minusDays(duration));
            break;
        case KASKKIRI_DUPLIKAAT:
            if (directiveStudent.getDiploma() != null) {
                directiveStudent.getDiploma().setStatus(em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_C.name()));
                
                // Remove supplement which is not printed yet
                List<DiplomaSupplement> supplements = em.createQuery("select ds from DiplomaSupplement ds"
                        + " where ds.diploma.id = ?1 and (ds.status.code = ?2 or ds.statusEn.code = ?2)", DiplomaSupplement.class)
                        .setParameter(1, directiveStudent.getDiploma().getId())
                        .setParameter(2, DocumentStatus.LOPUDOK_STAATUS_K.name())
                        .getResultList();
                for (DiplomaSupplement supplement : supplements) {
                    if (ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatus())) {
                        supplement.setStatus(null);
                        supplement.setDuplicate(null);
                    }
                    if (ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, supplement.getStatusEn())) {
                        supplement.setStatusEn(null);
                        supplement.setDuplicateEn(null);
                    }
                    // delete if there is no need in this
                    if (supplement.getStatus() == null && supplement.getStatusEn() == null) {
                        EntityUtil.deleteEntity(supplement, em);
                    }
                }
            }
            if (directiveStudent.getDiplomaForm() != null) {
                directiveStudent.getDiplomaForm().setStatus(em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name()));
                directiveStudent.getDiplomaForm().setDefected(confirmDate);
                directiveStudent.getDiplomaForm().setDefectReason(directiveStudent.getAddInfo());
                directiveStudent.getDiplomaForm().setDefectedBy(directiveStudent.getDirective().getConfirmer());
            }

            List<DirectiveStudentDuplicateForm> forms = new ArrayList<>();
            List<DirectiveStudentDuplicateForm> formsEn = new ArrayList<>();
            
            directiveStudent.getForms().forEach(f -> (Boolean.TRUE.equals(f.getEn()) ? formsEn : forms).add(f));
            if (directiveStudent.getDiplomaSupplement() != null) {
                directiveStudent.getDiplomaSupplement().setStatus(em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_C.name()));
                forms.forEach(dsForm -> {
                    dsForm.getForm().setStatus(em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name()));
                    dsForm.getForm().setDefected(confirmDate);
                    dsForm.getForm().setDefectReason(directiveStudent.getAddInfo());
                    dsForm.getForm().setDefectedBy(directiveStudent.getDirective().getConfirmer());
                });
                if (ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, directiveStudent.getDiplomaSupplement().getStatusEn())) {
                    directiveStudent.getDiplomaSupplement().setStatusEn(null);
                    directiveStudent.getDiplomaSupplement().setDuplicateEn(null);
                }
            }
            if (directiveStudent.getDiplomaSupplementEn() != null) {
                directiveStudent.getDiplomaSupplementEn().setStatusEn(em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_C.name()));
                formsEn.forEach(dsForm -> {
                    dsForm.getForm().setStatus(em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name()));
                    dsForm.getForm().setDefected(confirmDate);
                    dsForm.getForm().setDefectReason(directiveStudent.getAddInfo());
                    dsForm.getForm().setDefectedBy(directiveStudent.getDirective().getConfirmer());
                });
                if (ClassifierUtil.equals(DocumentStatus.LOPUDOK_STAATUS_K, directiveStudent.getDiplomaSupplementEn().getStatus())) {
                    directiveStudent.getDiplomaSupplementEn().setStatus(null);
                    directiveStudent.getDiplomaSupplementEn().setDuplicate(null);
                }
            }
            break;
        case KASKKIRI_EKSMAT:
            // FIXME correct field for Õppuri eksmatrikuleerimise kuupäev?
            student.setStudyEnd(confirmDate);
            userService.disableUser(student, LocalDate.now().minusDays(1));
            cancelScholarships(directiveStudent);
            endContracts(student);
            break;
        case KASKKIRI_EKSTERN:
            student.setType(EntityUtil.getOptionalOne(StudentType.OPPUR_E.name(), em));
            student.setStudyStart(confirmDate);
            student.setStudyEnd(null);
            if (!newStudent) {
                // already existing user
                // new students are enabled during creation
                userService.enableUser(student, confirmDate);
            }
            break;
        case KASKKIRI_EKSTERNKATK:
            student.setStudyEnd(confirmDate);
            userService.disableUser(student, LocalDate.now().minusDays(1));
            cancelScholarships(directiveStudent);
            endContracts(student);
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
        case KASKKIRI_OKAVA:
            student.setCurriculumSpeciality(null);
            break;
        case KASKKIRI_OTEGEVUS:
            if (Boolean.TRUE.equals(directiveStudent.getIsAbsence())) {
                studentAbsenceService.createDirectiveAbsence(directiveStudent);
            }
            break;
        case KASKKIRI_TUGI:
            student.setNominalStudyEnd(directiveStudent.getNominalStudyEnd());
            break;
        default:
            break;
        }

        // optional new status
        if(studentStatus != null) {
            student.setStatus(studentStatus);
        }

        student = studentService.saveWithHistory(student);
        if(DirectiveType.KASKKIRI_IMMAT.equals(directiveType) || DirectiveType.KASKKIRI_IMMATV.equals(directiveType) 
                || DirectiveType.KASKKIRI_KYLALIS.equals(directiveType) || (newStudent && DirectiveType.KASKKIRI_EKSTERN.equals(directiveType))) {
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

    private void cancelDirective(String confirmer, Directive directive) {
        // cancellation may include only some students
        Map<Long, DirectiveStudent> includedStudents = StreamUtil.toMap(ds -> EntityUtil.getId(ds.getStudent()), ds -> ds, directive.getStudents());
        Directive canceledDirective = directive.getCanceledDirective();
        DirectiveType canceledDirectiveType = DirectiveType.valueOf(EntityUtil.getCode(canceledDirective.getType()));
        LocalDate confirmDate = directive.getConfirmDate();

        for(DirectiveStudent ds : canceledDirective.getStudents()) {
            Student student = ds.getStudent();
            DirectiveStudent cancelds = includedStudents.get(student.getId());
            if(cancelds != null) {
                if(DirectiveType.KASKKIRI_IMMAT.equals(canceledDirectiveType) || DirectiveType.KASKKIRI_IMMATV.equals(canceledDirectiveType) 
                        || DirectiveType.KASKKIRI_KYLALIS.equals(canceledDirectiveType) || DirectiveType.KASKKIRI_EKSTERN.equals(canceledDirectiveType)) {
                    student.setStudyEnd(confirmDate);
                    student.setStatus(em.getReference(Classifier.class, StudentStatus.OPPURSTAATUS_K.name()));
                    personService.deleteUser(confirmer, UserService.userFor(student.getPerson(), student.getId(), Role.ROLL_T));
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
                    case KASKKIRI_EKSTERNKATK:
                        student.setStudyEnd(null);
                        student.setStatus(original.getStatus());
                        userService.enableUser(student, confirmDate);
                        break;
                    case KASKKIRI_LOPET:
                        student.setStudyEnd(null);
                        student.setStatus(original.getStatus());
                        userService.enableUser(student, confirmDate);
                        DirectiveUtil.cancelFormsAndDocuments(directive.getConfirmer(), ds, em);
                        break;
                    case KASKKIRI_VALIS:
                        student.setStatus(original.getStatus());
                        break;
                    case KASKKIRI_OKAVA:
                        student.setCurriculumSpeciality(original.getCurriculumSpeciality());
                        break;
                    case KASKKIRI_OTEGEVUS:
                        StudentAbsence absence = ds.getStudentAbsence();
                        if (absence != null) {
                            studentAbsenceService.reject(confirmer, absence, ABSENCE_REJECT_DIRECTIVE_CANCELED);
                        }
                        break;
                    case KASKKIRI_TUGI:
                        student.setNominalStudyEnd(original.getNominalStudyEnd());
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
                    LocalDate scholarshipEndDate = DateUtils.endDate(sa);
                    LocalDate scholarshipStartDate = DateUtils.startDate(sa);
                    if((scholarshipEndDate != null && startDate.isAfter(scholarshipEndDate)) || 
                            (scholarshipStartDate != null && endDate.isBefore(scholarshipStartDate)) ||
                            Boolean.TRUE.equals(sa.getScholarshipTerm().getIsAcademicLeave())) {
                        continue;
                    }
                }
                sa.setStatus(rejected);
            }
        }
    }

    private void endContracts(Student student) {
        List<Contract> contracts = em
                .createQuery("select c from Contract c where c.student.id = ?1 and c.status.code in (?2)", Contract.class)
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, EnumUtil.toNameList(ContractStatus.LEPING_STAATUS_S, ContractStatus.LEPING_STAATUS_Y,
                        ContractStatus.LEPING_STAATUS_K))
                .getResultList();

        if (!contracts.isEmpty()) {
            em.createNativeQuery("update contract set status_code = ?1 where id in (?2)")
                    .setParameter(1, ContractStatus.LEPING_STAATUS_L.name())
                    .setParameter(2, StreamUtil.toMappedList(c -> c.getId(), contracts))
                    .executeUpdate();
            for (Contract contract : contracts) {
                if (contract.getStudentAbsence() != null) {
                    contractService.rejectEndedContractAbsence(contract);
                }
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
            qb.requiredCriteria("exists(select 1 from directive_student ds2 where ds2.directive_id = :directiveId "
                    + "and ds2.start_date <= case when ds.is_period then spe.end_date else ds.end_date end "
                    + "and ds2.end_date >= case when ds.is_period then sps.start_date else ds.start_date end "
                    + "and ds2.student_id = ds.student_id)", "directiveId", directive.getId());
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

    Map<Long, List<IndividualCurriculumModuleDto>> individualCurriculumSuitableModules(Directive directive) {
        if (!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_INDOK, DirectiveType.KASKKIRI_TUGI)
                || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        if (ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_TUGI)
                && !supportServiceIncludesIndividualCurriculum(directive)) {
            return Collections.emptyMap();
        }

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        JpaNativeQueryBuilder qb = directiveService.individualCurriculumModulesQb(studentIds, null);
        List<?> data = qb.select("s.id student_id, cvo.id module_id", em).getResultList();

        Map<Long, Map<Long, List<ExistingIndividualCurriculumModuleDto>>> existingModules = directiveService
                .existingIndividualCurriculumModules(studentIds, null);

        return StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    IndividualCurriculumModuleDto dto = new IndividualCurriculumModuleDto();
                    dto.setId(resultAsLong(r, 1));
                    dto.setExistingModules(existingModules.containsKey(resultAsLong(r, 0))
                            ? existingModules.get(resultAsLong(r, 0)).get(resultAsLong(r, 1))
                            : null);
                    return dto;
                }, Collectors.toList())));
    }

    private boolean supportServiceIncludesIndividualCurriculum(Directive directive) {
        List<?> data = em.createNativeQuery("select ds.id from directive d "
                + "join directive_student ds on ds.directive_id = d.id "
                + "join application a on a.id = ds.application_id "
                + "join application_support_service ass on ass.application_id = a.id "
                + "where d.id = ?1 and ass.support_service_code = ?2")
                .setParameter(1, EntityUtil.getId(directive))
                .setParameter(2, SupportServiceType.TUGITEENUS_1.name())
                .setMaxResults(1).getResultList();
        return !data.isEmpty();
    }

    Map<Long, List<DirectiveStudent>> findIndividualCurriculums(Directive directive) {
        if(!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_INDOKLOP) || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        List<DirectiveStudent> data = em.createQuery("select ds from DirectiveStudent ds " +
                "where ds.canceled = false and ds.student.id in (?1) and ds.directive.type.code = ?2 and ds.directive.status.code = ?3 and ds.endDate >= CURRENT_DATE " +
                "and (not exists (select ds2 from DirectiveStudent ds2 where ds2.canceled = false and ds2.directive.type.code = ?4 and ds2.id = ds.id) " +
                "or ds.id in (select ds3.id from DirectiveStudent ds3 where ds3.directive.id = ?5))", DirectiveStudent.class)
            .setParameter(1, studentIds)
            .setParameter(2, DirectiveType.KASKKIRI_INDOK.name())
            .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(4, DirectiveType.KASKKIRI_INDOKLOP.name())
            .setParameter(5, EntityUtil.getId(directive))
            .getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> EntityUtil.getId(r.getStudent())));
    }

    Map<Long, List<ScholarshipApplication>> findScholarshipApplications(Directive directive) {
        if (!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_STIPTOET)
                || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        List<ScholarshipApplication> data = em.createQuery("select sa from ScholarshipApplication sa " +
                "where sa.student.id in (?1) and sa.scholarshipTerm.type.code = ?2 and sa.status.code = ?3 " +
                "and not exists (select ds from DirectiveStudent ds where ds.canceled = false and ds.directive.type.code = ?4 " +
                "and ds.scholarshipApplication.id = sa.id and ds.directive.id != ?5)", ScholarshipApplication.class)
            .setParameter(1, studentIds)
            .setParameter(2, EntityUtil.getNullableCode(directive.getScholarshipType()))
            .setParameter(3, ScholarshipStatus.STIPTOETUS_STAATUS_A.name())
            .setParameter(4, DirectiveType.KASKKIRI_STIPTOET.name())
            .setParameter(5, EntityUtil.getId(directive))
            .getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> EntityUtil.getId(r.getStudent())));
    }

    Map<Long, List<DirectiveStudent>> findScholarshipsAvailableForTermination(Directive directive) {
        if (!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_STIPTOETL)
                || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        boolean isEhisType = directive.getScholarshipEhis() != null;
        List<DirectiveStudent> data = em.createQuery("select ds from DirectiveStudent ds " +
                "where ds.canceled = false and ds.student.id in (?1) and ds.directive.type.code = ?2 " +
                "and ds.directive." + (isEhisType ? "scholarshipEhis" : "scholarshipType") + ".code = ?3 and ds.directive.status.code = ?4 and ds.endDate >= CURRENT_DATE " +
                "and (not exists (select ds2 from DirectiveStudent ds2 where ds2.canceled = false and ds2.directive.type.code = ?5 and ds2.id = ds.id) " +
                "or ds.id in (select ds3.id from DirectiveStudent ds3 where ds3.directive.id = ?6))", DirectiveStudent.class)
            .setParameter(1, studentIds)
            .setParameter(2, DirectiveType.KASKKIRI_STIPTOET.name())
            .setParameter(3, EntityUtil.getNullableCode(isEhisType ? directive.getScholarshipEhis() : directive.getScholarshipType()))
            .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
            .setParameter(5, DirectiveType.KASKKIRI_STIPTOETL.name())
            .setParameter(6, EntityUtil.getId(directive))
            .getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> EntityUtil.getId(r.getStudent())));
    }

    Map<Long, List<DirectiveStudent>> findAbroadStudies(Directive directive) {
        if(!ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_VALISKATK) || directive.getStudents().isEmpty()) {
            return Collections.emptyMap();
        }

        List<Long> studentIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
        List<DirectiveStudent> data = em.createQuery("select ds from DirectiveStudent ds " +
                "left join ds.studyPeriodStart sp_start left join ds.studyPeriodEnd sp_end " +
                "where ds.canceled = false and ds.student.id in (?1) and ds.directive.type.code = ?2 and ds.directive.status.code = ?3 " +
                "and coalesce(sp_start.startDate, ds.startDate) <= CURRENT_DATE and coalesce(sp_end.endDate, ds.endDate) >= CURRENT_DATE " +
                "and (not exists (select ds2 from DirectiveStudent ds2 where ds2.canceled = false and ds2.directive.type.code = ?4 and ds2.id = ds.id) " +
                "or ds.id in (select ds3.id from DirectiveStudent ds3 where ds3.directive.id = ?5))", DirectiveStudent.class)
                .setParameter(1, studentIds)
                .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
                .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(4, DirectiveType.KASKKIRI_VALISKATK.name())
                .setParameter(5, EntityUtil.getId(directive))
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
        student.setIsContractAgreed(Boolean.FALSE);
        student.setType(EntityUtil.getOptionalOne(StudentType.OPPUR_T.name(), em));
        
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
