package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationSupportService;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.report.ApplicationTugiReport;
import ee.hitsa.ois.service.ApplicationService;
import ee.hitsa.ois.service.AutomaticMessageService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApplicationUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.application.ApplicationConfirmConfirmationForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.application.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.application.ApplicationApplicableDto;
import ee.hitsa.ois.web.dto.application.ApplicationDto;
import ee.hitsa.ois.web.dto.application.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.application.ApplicationSupportServiceModuleDto;
import ee.hitsa.ois.web.dto.application.ApplicationThemeReplacementDto;
import ee.hitsa.ois.web.dto.application.ValidAcademicLeaveDto;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private EntityManager em;

    @GetMapping("/{id:\\d+}")
    public ApplicationDto get(HoisUserDetails user, @WithEntity Application application) {
        if (UserUtil.canViewStudentSpecificData(user, application.getStudent())) {
            return applicationService.get(user, application, true);
        }
        if (UserUtil.isTeacher(user, application.getStudent().getSchool()) || UserUtil.isSchoolAdmin(user, application.getStudent().getSchool())) {

            Query isCommitteeMemberQuery = em.createNativeQuery("select c.id from application a "
                    + "join committee c on a.committee_id = c.id "
                    + "join committee_member cm on cm.committee_id = c.id "
                    + "where a.id = ?0 and c.school_id = ?1 and (cm.person_id = ?2 "
                    + (user.getTeacherId() != null ? "or cm.teacher_id = ?3)" : ")")
                    + " limit 1")
                    .setParameter(0, application.getId()).setParameter(1, user.getSchoolId()).setParameter(2, user.getPersonId());
            if (user.getTeacherId() != null) {
                isCommitteeMemberQuery.setParameter(3, user.getTeacherId());
            }
            
            if (!isCommitteeMemberQuery.getResultList().isEmpty()) {
                return applicationService.get(user, application, false);
            }
        }
        throw new ValidationFailedException("main.messages.error.nopermission");
    }

    @GetMapping
    public Page<ApplicationSearchDto> search(@Valid ApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if (user.getTeacherId() != null || !UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_AVALDUS)) {
            command.setConnectedByCommittee(Boolean.TRUE);
        }
        return applicationService.search(user, command, pageable);
    }

    @PostMapping
    public ApplicationDto create(@Valid @RequestBody ApplicationForm applicationForm, HoisUserDetails user) {
        return get(user, applicationService.create(user, applicationForm));
    }

    @PutMapping("/{id:\\d+}")
    public ApplicationDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Application application, @Valid @RequestBody ApplicationForm applicationForm) {
        if (!(UserUtil.isSchoolAdmin(user, application.getStudent().getSchool()) || UserUtil.isStudent(user, application.getStudent())) || !StudentUtil.canBeEdited(application.getStudent())) {
            if (!ApplicationType.AVALDUS_LIIK_TUGI.name().equals(applicationForm.getType())
                    || !(UserUtil.isStudentRepresentative(user, application.getStudent()) || UserUtil.isStudentGroupTeacher(user, application.getStudent()))) {
                throw new ValidationFailedException("main.messages.error.nopermission");
            }
            
        }
        checkUpdateBusinessRules(user, application, applicationForm);
        return get(user, applicationService.save(user, application, applicationForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Application application, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        Student student = application.getStudent();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        if((!(UserUtil.isStudent(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool())) || !ApplicationStatus.AVALDUS_STAATUS_KOOST.equals(status)) && !StudentUtil.canBeEdited(student)) {
            throw new ValidationFailedException(String.format("user %s is not allowed to delete application %d with status %s", user.getUsername(), application.getId(), status.name()));
        }
        applicationService.delete(user, application);
    }

    @GetMapping("/student/{id:\\d+}/validAcademicLeave")
    public ValidAcademicLeaveDto academicLeave(HoisUserDetails user, @WithEntity Student student) {
        if(!(UserUtil.isStudent(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        DirectiveStudent academicLeave = applicationService.findLastValidAcademicLeaveWithoutRevocation(EntityUtil.getId(student));
        return academicLeave == null ? null : ValidAcademicLeaveDto.of(academicLeave);
    }

    @GetMapping("student/{id:\\d+}/applicable")
    public Map<String, ApplicationApplicableDto> applicableApplicationTypes(HoisUserDetails user, @WithEntity Student student) {
        if(!(UserUtil.isStudent(user, student) || UserUtil.isStudentRepresentative(user, student) || UserUtil.isStudentGroupTeacher(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool()))) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }

        return applicationService.applicableApplicationTypes(student);
    }

    @PutMapping("/{id:\\d+}/submit")
    public ApplicationDto submit(HoisUserDetails user, @WithEntity Application application) {
        if(!UserUtil.canSubmitApplication(user, application)) {
            String status = EntityUtil.getCode(application.getStatus());
            throw new ValidationFailedException(String.format("User %s is not allowed to submit application %d with status %s", user.getUsername(), application.getId(), status));
        }
        
        ApplicationType type = EnumUtil.valueOf(ApplicationType.class, application.getType());

        Application submitedApplication = applicationService.submit(user, application);
        Student student = application.getStudent();
        if (ApplicationUtil.SEND_MESSAGE_CALL.containsKey(type)) {
            ApplicationUtil.SEND_MESSAGE_CALL.get(type).get(ApplicationUtil.OperationType.SUBMIT).accept(application, automaticMessageService);
        } else if (UserUtil.isStudent(user, student) && !UserUtil.isAdultStudent(user, student)) {
            applicationService.sendConfirmNeededNotificationMessage(submitedApplication);
        }

        return get(user, submitedApplication);
    }

    @PutMapping("/{id:\\d+}/reject")
    public ApplicationDto reject(HoisUserDetails user, @WithEntity Application application,
            @Valid @RequestBody ApplicationRejectForm applicationRejectForm) {
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (!UserUtil.canRejectApplication(user, application)) {
            throw new ValidationFailedException(String.format("user %s is not allowed to reject application %d with status %s", user.getUsername(), application.getId(), status));
        }
        Application rejectedApplication = applicationService.reject(application, applicationRejectForm);
        applicationService.sendRejectionNotificationMessage(rejectedApplication, user);

        return get(user, rejectedApplication);
    }

    @PutMapping("/{id:\\d+}/confirm")
    public ApplicationDto confirm(HoisUserDetails user, @WithEntity Application application) {
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (!UserUtil.canConfirmApplication(user, application)) {
            throw new ValidationFailedException(String.format("user %s is not allowed to confirm application %d with status %s", user.getUsername(), application.getId(), status));
        }
        checkConfirmBusinessRules(user, application);
        Application confirmedApplication = applicationService.confirm(application);
        applicationService.sendConfirmationNotificationMessage(confirmedApplication, user);

        return get(user, confirmedApplication);
    }
    
    @PutMapping("/{id:\\d+}/confirmConfirmation")
    public ApplicationDto confirmConfirmation(HoisUserDetails user, @WithEntity Application application, @Valid @RequestBody ApplicationConfirmConfirmationForm form) {
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (!UserUtil.canConfirmApplicationConfirmation(user, application)) {
            throw new ValidationFailedException(String.format("user %s is not allowed to confirm application %d with status %s", user.getUsername(), application.getId(), status));
        }

        checkConfirmBusinessRules(user, application);
        applicationService.confirmConfirmation(user, application, form);
        
        return get(user, application);
    }
    
    @PutMapping("/{id:\\d+}/removeConfirmation")
    public ApplicationDto removeConfirmation(HoisUserDetails user, @WithEntity Application application) {
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));

        if (!applicationService.canRemoveApplicationConfirmation(user, application)) {
            throw new ValidationFailedException(String.format("user %s is not allowed to remove application confirmation %d with status %s", user.getUsername(), application.getId(), status));
        }

        applicationService.removeConfirmation(application);
        
        return get(user, application);
    }
    
    @GetMapping("/print/{id:\\d+}/application.pdf")
    public void print(HoisUserDetails user, @WithEntity Application application, HttpServletResponse response, @RequestParam(required = false) Language lang) throws IOException {
        
        boolean notAllowed = !UserUtil.canViewStudentSpecificData(user, application.getStudent());
        
        if (notAllowed && (UserUtil.isTeacher(user, application.getStudent().getSchool()) || UserUtil.isSchoolAdmin(user, application.getStudent().getSchool()))) {
            Query isCommitteeMemberQuery = em.createNativeQuery("select c.id from application a "
                    + "join committee c on a.committee_id = c.id "
                    + "join committee_member cm on cm.committee_id = c.id "
                    + "where a.id = ?0 and c.school_id = ?1 and (cm.person_id = ?2 "
                    + (user.getTeacherId() != null ? "or cm.teacher_id = ?3)" : ")")
                    + " limit 1")
                    .setParameter(0, application.getId()).setParameter(1, user.getSchoolId()).setParameter(2, user.getPersonId());
            if (user.getTeacherId() != null) {
                isCommitteeMemberQuery.setParameter(3, user.getTeacherId());
            }
            
            notAllowed = isCommitteeMemberQuery.getResultList().isEmpty();
        }
        
        if (notAllowed) {
            throw new ValidationFailedException(String.format("user %s is not allowed to view application %d",
                    user.getUsername(), application.getId()));
        }
        ApplicationType type = EnumUtil.valueOf(ApplicationType.class, application.getType());
        if (ApplicationType.AVALDUS_LIIK_TUGI.equals(type)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYY");
            String fileName = String.format("%s_%s_%s.pdf", "tugimeede", application.getStudent().getPerson().getFullname(),
                    formatter.format(application.getRepresentativeConfirmed())).replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            HttpUtil.pdf(response, fileName, pdfService.generate(ApplicationTugiReport.TEMPLATE_NAME, new ApplicationTugiReport(application, lang == null ? Language.ET : lang)));
        }
    }

    @GetMapping("/studentIndividualCurriculumModules/{id:\\d+}")
    public List<ApplicationSupportServiceModuleDto> individualCurriculumModules(HoisUserDetails user, @WithEntity Student student,
            @RequestParam(value = "applicationId", required = false) Long applicationId) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_AVALDUS);
        return applicationService.individualCurriculumModules(student, applicationId);
    }

    @GetMapping("/curriculumVersionThemeReplacements/{id:\\d+}")
    public ApplicationThemeReplacementDto curriculumVersionThemeReplacements(HoisUserDetails user, @WithEntity Student student,
            @RequestParam(value = "applicationId", required = false) Long applicationId,
            @RequestParam(value = "curriculumVersionId", required = true) Long curriculumVersionId) {
        UserUtil.canViewStudentSpecificData(user, student);
        return applicationService.curriculumVersionThemeReplacements(student, applicationId, curriculumVersionId);
    }

    @SuppressWarnings("fallthrough")
    private static void checkUpdateBusinessRules(HoisUserDetails user, Application application, ApplicationForm applicationForm) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());

        Student student = application.getStudent();
        ApplicationStatus status = ApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        ApplicationType type = ApplicationType.valueOf(EntityUtil.getCode(application.getType()));

        switch (status) {
        case AVALDUS_STAATUS_KOOST:
            if ((UserUtil.isAdultStudent(user, student) || UserUtil.isSchoolAdmin(user, student.getSchool())) && (ApplicationStatus.AVALDUS_STAATUS_KOOST.name().equals(applicationForm.getStatus())
                    || ApplicationStatus.AVALDUS_STAATUS_ESIT.name().equals(applicationForm.getStatus()))) {
                break;
            } else if (!UserUtil.isAdultStudent(user, student) && ApplicationStatus.AVALDUS_STAATUS_KOOST.name().equals(applicationForm.getStatus())) {
                break;
            } else if (UserUtil.isStudentRepresentative(user, student) && (ApplicationStatus.AVALDUS_STAATUS_ESIT.name().equals(applicationForm.getStatus()) ||
                    ApplicationStatus.AVALDUS_STAATUS_TAGASI.name().equals(applicationForm.getStatus()))) {
                break;
            }
        case AVALDUS_STAATUS_ESIT:
            //fallthrough
        case AVALDUS_STAATUS_YLEVAAT:
            if(UserUtil.isSchoolAdmin(user, student.getSchool())
                    && (ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name().equals(applicationForm.getStatus())) || ApplicationStatus.AVALDUS_STAATUS_TAGASI.name().equals(applicationForm.getStatus())) {
                break;
            }
            //fallthrough
        case AVALDUS_STAATUS_KINNITAM:
            //fallthrough
        case AVALDUS_STAATUS_KINNITATUD:
            if (ApplicationType.AVALDUS_LIIK_RAKKAVA.equals(type) && UserUtil.isSchoolAdmin(user, student.getSchool())) {
                Long studentCurriculumVersion = EntityUtil.getId(student.getCurriculumVersion());
                Long applicationCurriculumVersion = EntityUtil.getId(application.getNewCurriculumVersion());
                if (UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_AVALDUS)
                        && studentCurriculumVersion.equals(applicationCurriculumVersion)) {
                    break;
                }
            }
            //fallthrough
        case AVALDUS_STAATUS_TAGASI:
            throw new ValidationFailedException(String.format("user %s is not allowed to update application %d with status %s", user.getUsername(), application.getId(), status.name()));
        default:
            break;
        }
    }

    private static void checkConfirmBusinessRules(HoisUserDetails user, Application application) {
        UserUtil.assertSameSchool(user, application.getStudent().getSchool());

        ApplicationStatus status = EnumUtil.valueOf(ApplicationStatus.class, EntityUtil.getCode(application.getStatus()));
        ApplicationType type = EnumUtil.valueOf(ApplicationType.class, EntityUtil.getCode(application.getType()));

        switch (status) {
        case AVALDUS_STAATUS_ESIT:
        case AVALDUS_STAATUS_YLEVAAT:
        case AVALDUS_STAATUS_KINNITAM:
            if (ApplicationType.AVALDUS_LIIK_TUGI.equals(type)) {
                if (application.getIsDecided() == null || application.getDecision() == null
                        || (Boolean.TRUE.equals(application.getIsDecided())
                                && (application.getSupportServices().isEmpty() || application.getImplementationPlan() == null))) {
                    throw new ValidationFailedException("application.messages.emptyNecessaryFields");
                } else if (Boolean.TRUE.equals(application.getIsDecided()) && !application.getSupportServices().isEmpty()) {
                    ApplicationSupportService service1 = application.getSupportServices().stream()
                            .filter(service -> ClassifierUtil.equals(SupportServiceType.TUGITEENUS_1, service.getSupportService()))
                            .findFirst().orElse(null);
                    if (service1 != null && service1.getModules().isEmpty()) {
                        throw new ValidationFailedException("application.messages.emptyNecessaryFields");
                    }
                }
            }
            break;
        case AVALDUS_STAATUS_KOOST:
        case AVALDUS_STAATUS_KINNITATUD:
        case AVALDUS_STAATUS_TAGASI:
            throw new ValidationFailedException(String.format("User %s is not allowed to confirm application %d", user.getUsername(), application.getId(), status.name()));
        default:
            break;
        }
    }
}
