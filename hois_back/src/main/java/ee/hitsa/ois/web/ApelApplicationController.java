package ee.hitsa.ois.web;

import java.io.IOException;

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
import ee.hitsa.ois.report.ApelApplicationReport;
import ee.hitsa.ois.service.ApelApplicationService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationCommentForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationRecordForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationSearchCommand;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationFileDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationSearchDto;

@RestController
@RequestMapping("/apelApplications")
public class ApelApplicationController {

    @Autowired
    private ApelApplicationService apelApplicationService;
    @Autowired
    private PdfService pdfService;

    
    @GetMapping
    public Page<ApelApplicationSearchDto> search(ApelApplicationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        return apelApplicationService.search(user, command, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public ApelApplicationDto get(HoisUserDetails user, @WithEntity ApelApplication application) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        return apelApplicationService.get(application);
    }
    
    @PostMapping
    public ApelApplicationDto create(HoisUserDetails user, @Valid @RequestBody ApelApplicationForm applicationForm) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        ApelApplication savedApplication = apelApplicationService.create(user, applicationForm);
        return get(user, savedApplication);
    }
    
    @PutMapping("/{id:\\d+}")
    public ApelApplicationDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) ApelApplication application,
            @Valid @RequestBody ApelApplicationForm applicationForm) {
        UserUtil.assertIsSchoolAdminOrStudent(user, application.getSchool());
        return get(user, apelApplicationService.save(user, application,  applicationForm));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") ApelApplication application,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdminOrStudent(user, application.getSchool());
        apelApplicationService.delete(user, application);
    }
    
    @PutMapping("/{id:\\d+}/submit")
    public ApelApplicationDto submit(HoisUserDetails user, @WithEntity ApelApplication application) {
        if (!UserUtil.canSubmitApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication submittedApplication = apelApplicationService.submit(application);
        return get(user, submittedApplication);
    }
    
    @PutMapping("/{id:\\d+}/sendToConfirm")
    public ApelApplicationDto sendToConfirm(HoisUserDetails user, @WithEntity ApelApplication application) {
        if (!UserUtil.canSendToConfirmApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication sentToConfirmApplication = apelApplicationService.sendToConfirm(application);
        return get(user, sentToConfirmApplication);
    }
    
    @PutMapping("/{id:\\d+}/sendBackToCreation")
    public ApelApplicationDto sendBackToCreation(HoisUserDetails user, @WithEntity ApelApplication application) {
        if (!UserUtil.canSendBackToCreation(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication sentBackToCreationApplication = apelApplicationService.sendBackToCreation(application);
        return get(user, sentBackToCreationApplication);
    }
    
    @PutMapping("/{id:\\d+}/confirm")
    public ApelApplicationDto confirm(HoisUserDetails user, @WithEntity ApelApplication application) {
        if (!UserUtil.canConfirmApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication confirmedApplication = apelApplicationService.confirm(user, application);
        return get(user, confirmedApplication);
    }
    
    @PutMapping("/{id:\\d+}/sendBack")
    public ApelApplicationDto sendBack(HoisUserDetails user, @WithEntity ApelApplication application) {
        if (!UserUtil.canSendBackApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication sentBackApplication = apelApplicationService.sendBack(application);
        return get(user, sentBackApplication);
    }
    
    @PutMapping("/{id:\\d+}/reject")
    public ApelApplicationDto reject(HoisUserDetails user, @WithEntity ApelApplication application,
            @Valid @RequestBody ApelApplicationCommentForm applicationCommentForm) {
        if (!UserUtil.canRejectApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication rejectedApplication = apelApplicationService.reject(application, applicationCommentForm);
        return get(user, rejectedApplication);
    }
    
    @PutMapping("/{id:\\d+}/removeConfirmation")
    public ApelApplicationDto removeConfirmation(HoisUserDetails user, @WithEntity ApelApplication application) {
        if (!UserUtil.canRemoveConfirmationApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        ApelApplication removedConfirmationApplication = apelApplicationService.removeConfirmation(application);
        return get(user, removedConfirmationApplication);
    }
  
    
    @PostMapping("/{applicationId:\\d+}/record")
    public ApelApplicationDto createRecord(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationRecordForm recordForm,
            @WithEntity("applicationId") ApelApplication application) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.createRecord(user, application, recordForm);
        return get(user, application);
    }
    
    @PutMapping("/{applicationId:\\d+}/record/{id:\\d+}")
    public ApelApplicationDto updateRecord(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationRecordForm recordForm,
            @WithEntity("applicationId") ApelApplication application,
            @WithEntity ApelApplicationRecord record) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.updateRecord(user, recordForm, record);
        return get(user, application);
    }
    
    @DeleteMapping("/{applicationId:\\d+}/record/{id:\\d+}")
    public ApelApplicationDto deleteRecord(HoisUserDetails user, @WithEntity("applicationId") ApelApplication application,
            @WithVersionedEntity(versionRequestParam = "version") ApelApplicationRecord record,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.deleteRecord(user, record);
        return get(user, application);
    }

    @PostMapping("/{applicationId:\\d+}/file")
    public ApelApplicationFileDto createFile(HoisUserDetails user,
            @Valid @RequestBody OisFileForm fileForm,
            @WithEntity("applicationId") ApelApplication application) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        return ApelApplicationFileDto.of(apelApplicationService.createFile(application, fileForm));
    }

    @DeleteMapping("/{applicationId:\\d+}/file/{fileId:\\d+}")
    public void deleteFile(HoisUserDetails user,
            @WithEntity("applicationId") ApelApplication application,
            @WithEntity("fileId") ApelApplicationFile file) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.deleteFile(user, file);
    }
    
    @PostMapping("/{applicationId:\\d+}/comment")
    public ApelApplicationDto createComment(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationCommentForm commentForm,
            @WithEntity("applicationId") ApelApplication application) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.createComment(application, commentForm);
        return get(user, application);
    }
    
    /* TODO: not used right now an might never be */
    @PutMapping("/{applicationId:\\d+}/comment/{id:\\d+}")
    public ApelApplicationDto updateComment(HoisUserDetails user,
            @Valid @RequestBody ApelApplicationCommentForm commentForm,
            @WithEntity("applicationId") ApelApplication application,
            @WithEntity ApelApplicationComment comment) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.updateComment(commentForm, comment);
        return get(user, application);
    }
    
    @DeleteMapping("/{applicationId:\\d+}/comment/{id:\\d+}")
    public ApelApplicationDto deleteComment(HoisUserDetails user, @WithEntity("applicationId") ApelApplication application,
            @WithVersionedEntity(versionRequestParam = "version") ApelApplicationComment comment,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        if (!UserUtil.canEditApelApplication(user, application)) {
            throw new ValidationFailedException("apel.error.nopermission");
        }
        apelApplicationService.deleteComment(user, comment);
        return get(user, application);
    }
    
    @GetMapping("/print/{id:\\d+}/application.pdf")
    public void print(HoisUserDetails user, @WithEntity ApelApplication application, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        HttpUtil.pdf(response, application.getId() + ".pdf",
                pdfService.generate(ApelApplicationReport.TEMPLATE_NAME, new ApelApplicationReport(application)));
    }
}