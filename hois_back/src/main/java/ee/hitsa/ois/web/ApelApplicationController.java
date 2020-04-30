package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

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

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationComment;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFile;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.report.apelapplication.ApelApplicationReport;
import ee.hitsa.ois.service.ApelApplicationService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ApelApplicationUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationCommentForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationRecordForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationFileDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleResult;

@RestController
@RequestMapping("/apelApplications")
public class ApelApplicationController {

    @Autowired
    private ApelApplicationService apelApplicationService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public Page<ApelApplicationSearchDto> search(@Valid ApelApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canSearch(user));
        return apelApplicationService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ApelApplicationDto get(HoisUserDetails user, @WithEntity ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canView(user, application));
        return apelApplicationService.get(user, application);
    }

    @PostMapping
    public ApelApplicationDto create(HoisUserDetails user, @Valid @RequestBody ApelApplicationForm applicationForm) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canCreate(user));
        ApelApplication savedApplication = apelApplicationService.create(user, applicationForm);
        return get(user, savedApplication);
    }

    @PutMapping("/{id:\\d+}")
    public ApelApplicationDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) ApelApplication application,
            @RequestBody ApelApplicationForm applicationForm) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canCanChangeTransferStatus(user, application));
        return get(user, apelApplicationService.save(user, application, applicationForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") ApelApplication application,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.delete(user, application);
    }

    @PutMapping("/{id:\\d+}/submit")
    public ApelApplicationDto submit(HoisUserDetails user, @WithEntity ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canSubmit(user, application));
        ApelApplication submittedApplication = apelApplicationService.submit(application);
        return get(user, submittedApplication);
    }

    @PutMapping("/{id:\\d+}/sendToConfirm")
    public ApelApplicationDto sendToConfirm(HoisUserDetails user, @WithEntity ApelApplication application,
            @RequestBody ApelApplicationForm applicationForm) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canSendToConfirm(user, application));
        return get(user, apelApplicationService.sendToConfirm(user, application, applicationForm));
    }

    @PutMapping("/{id:\\d+}/sendToCommittee")
    public ApelApplicationDto sendToCommittee(HoisUserDetails user, @WithEntity ApelApplication application,
            @RequestBody ApelApplicationForm applicationForm) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canSendToCommittee(user, application));
        return get(user, apelApplicationService.sendToCommittee(user, application, applicationForm));
    }

    @PutMapping("/{id:\\d+}/sendBackToCreation")
    public ApelApplicationDto sendBackToCreation(HoisUserDetails user, @WithEntity ApelApplication application,
            @Valid @RequestBody ApelApplicationCommentForm applicationCommentForm) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canSendBackToCreation(user, application));
        ApelApplication sentBackToCreationApplication = apelApplicationService.sendBackToCreation(application,
                applicationCommentForm);
        return get(user, sentBackToCreationApplication);
    }

    @PutMapping("/{id:\\d+}/confirm")
    public ApelApplicationDto confirm(HoisUserDetails user, @WithEntity ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canConfirm(user, application));
        ApelApplication confirmedApplication = apelApplicationService.confirm(user, application);
        return get(user, confirmedApplication);
    }

    @PutMapping("/{id:\\d+}/sendBack")
    public ApelApplicationDto sendBack(HoisUserDetails user, @WithEntity ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canSendBack(user, application));
        ApelApplication sentBackApplication = apelApplicationService.sendBack(application);
        return get(user, sentBackApplication);
    }

    @PutMapping("/{id:\\d+}/reject")
    public ApelApplicationDto reject(HoisUserDetails user, @WithEntity ApelApplication application,
            @Valid @RequestBody ApelApplicationCommentForm applicationCommentForm) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canReject(user, application));
        ApelApplication rejectedApplication = apelApplicationService.reject(application, applicationCommentForm);
        return get(user, rejectedApplication);
    }

    @PutMapping("/{id:\\d+}/removeConfirmation")
    public ApelApplicationDto removeConfirmation(HoisUserDetails user, @WithEntity ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canRemoveConfirmation(user, application));
        ApelApplication removedConfirmationApplication = apelApplicationService.removeConfirmation(user, application);
        return get(user, removedConfirmationApplication);
    }

    @PostMapping("/{applicationId:\\d+}/record")
    public ApelApplicationDto createRecord(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationRecordForm recordForm,
            @WithEntity("applicationId") ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.createRecord(user, application, recordForm);
        return get(user, application);
    }

    @PutMapping("/{applicationId:\\d+}/record/{id:\\d+}")
    public ApelApplicationDto updateRecord(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationRecordForm recordForm,
            @WithEntity("applicationId") ApelApplication application,
            @WithEntity ApelApplicationRecord record) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.updateRecord(user, recordForm, record, application.getIsVocational());
        return get(user, application);
    }

    @DeleteMapping("/{applicationId:\\d+}/record/{id:\\d+}")
    public ApelApplicationDto deleteRecord(HoisUserDetails user, @WithEntity("applicationId") ApelApplication application,
            @WithVersionedEntity(versionRequestParam = "version") ApelApplicationRecord record,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.deleteRecord(user, record);
        return get(user, application);
    }

    @PostMapping("/{applicationId:\\d+}/file")
    public ApelApplicationFileDto createFile(HoisUserDetails user,
            @Valid @RequestBody OisFileForm fileForm,
            @WithEntity("applicationId") ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        return ApelApplicationFileDto.of(apelApplicationService.createFile(application, fileForm));
    }

    @DeleteMapping("/{applicationId:\\d+}/file/{fileId:\\d+}")
    public void deleteFile(HoisUserDetails user,
            @WithEntity("applicationId") ApelApplication application,
            @WithEntity("fileId") ApelApplicationFile file) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.deleteFile(user, file);
    }

    @PostMapping("/{applicationId:\\d+}/comment")
    public ApelApplicationDto createComment(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationCommentForm commentForm,
            @WithEntity("applicationId") ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.createComment(application, commentForm);
        return get(user, application);
    }

    @DeleteMapping("/{applicationId:\\d+}/comment/{id:\\d+}")
    public ApelApplicationDto deleteComment(HoisUserDetails user, @WithEntity("applicationId") ApelApplication application,
            @WithVersionedEntity(versionRequestParam = "version") ApelApplicationComment comment,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.deleteComment(user, comment);
        return get(user, application);
    }

    @GetMapping("/print/{id:\\d+}/application.pdf")
    public void print(HoisUserDetails user, @WithEntity ApelApplication application, HttpServletResponse response)
            throws IOException {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canView(user, application));
        Boolean isHigherSchool = Boolean.valueOf(schoolService.schoolType(EntityUtil.getId(application.getSchool())).isHigher());
        Boolean letterGrades = application.getSchool().getIsLetterGrade();
        ApelApplicationReport report = new ApelApplicationReport(application, isHigherSchool, letterGrades);
        String templateName = Boolean.TRUE.equals(application.getIsVocational()) ?
                ApelApplicationReport.VOCATIONAL_TEMPLATE_NAME : ApelApplicationReport.HIGHER_TEMPLATE_NAME;
        HttpUtil.pdf(response, application.getId() + ".pdf", pdfService.generate(templateName, report));
    }

    @GetMapping("/subjectModule")
    public CurriculumVersionHigherModuleDto subjectModule(HoisUserDetails user, @RequestParam Long curriculumVersionId,
            @RequestParam Long subjectId) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        return apelApplicationService.subjectModule(curriculumVersionId, subjectId);
    }

    @GetMapping("/studentModules/{id:\\d+}")
    public List<CurriculumVersionHigherModuleResult> studentModules(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        return apelApplicationService.studentModules(student);
    }

    @GetMapping("/freeChoiceModule/{id:\\d+}")
    public AutocompleteResult studentFreeChoiceModule(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        return apelApplicationService.studentFreeChoiceModule(student);
    }

    @GetMapping("/{applicationId:\\d+}/committees")
    public List<AutocompleteResult> committees(HoisUserDetails user,
            @WithEntity("applicationId") ApelApplication application) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user);
        return apelApplicationService.committeesForSelection(user, application);
    }

    @GetMapping("/{applicationId:\\d+}/transferFromApplication")
    public ApelApplicationDto transferFromApplication(HoisUserDetails user,
            @WithEntity("applicationId") ApelApplication application) {
        UserUtil.throwAccessDeniedIf(!ApelApplicationUtil.canEdit(user, application));
        apelApplicationService.addAbroadStudiesApplicationSubjects(application);
        return get(user, application);
    }
}
