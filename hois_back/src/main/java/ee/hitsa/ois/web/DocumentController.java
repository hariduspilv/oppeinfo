package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.DocumentService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.document.DiplomaConfirmForm;
import ee.hitsa.ois.web.commandobject.document.DiplomaForm;
import ee.hitsa.ois.web.commandobject.document.SupplementForm;
import ee.hitsa.ois.web.commandobject.document.SupplementSearchForm;
import ee.hitsa.ois.web.dto.FinalDocSignerDto;
import ee.hitsa.ois.web.dto.document.DirectiveDto;
import ee.hitsa.ois.web.dto.document.FormDto;
import ee.hitsa.ois.web.dto.document.StudentDto;
import ee.hitsa.ois.web.dto.document.SupplementDto;
import ee.hitsa.ois.web.dto.document.SupplementStudentDto;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    
    @GetMapping("/diploma/directives")
    public List<DirectiveDto> diplomaDirectives(HoisUserDetails user, Boolean isHigher) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        return documentService.diplomaDirectives(user, isHigher);
    }

    @GetMapping("/supplement/directives")
    public List<DirectiveDto> supplementDirectives(HoisUserDetails user, Boolean isHigher) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        return documentService.supplementDirectives(user, isHigher);
    }

    @GetMapping("/formtypes")
    public List<String> formTypes(HoisUserDetails user, DiplomaForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        return documentService.formTypes(user, form);
    }

    @GetMapping("/diploma/students")
    public List<StudentDto> diplomaStudents(HoisUserDetails user, DiplomaForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        return documentService.diplomaStudents(user, form);
    }

    @GetMapping("/supplement/students")
    public Page<SupplementStudentDto> supplementStudents(HoisUserDetails user, Boolean isHigher, 
            SupplementSearchForm form, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        return documentService.supplementStudents(user, isHigher, form, pageable);
    }

    @GetMapping("/supplement/{id:\\d+}")
    public SupplementDto supplement(HoisUserDetails user, @PathVariable("id") Long directiveStudentId) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        return documentService.supplement(user, directiveStudentId);
    }
    
    @GetMapping("/signers")
    public List<FinalDocSignerDto> signers(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        return documentService.signers(user);
    }
    
    @GetMapping("/diploma/print/view.pdf")
    public void diplomaPrintView(HoisUserDetails user, DiplomaForm form, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        documentService.createUpdateDiplomas(user, form);
        HttpUtil.pdf(response, "diplomas_view.pdf", documentService.diplomaPrintView(user, form));
    }

    @GetMapping("/diploma/forms")
    public List<FormDto> diplomaForms(HoisUserDetails user, String formType) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        return documentService.diplomaForms(user, formType);
    }
    
    @GetMapping("/diploma/calculate")
    public List<FormDto> calculate(HoisUserDetails user, DiplomaForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        documentService.createUpdateDiplomas(user, form);
        return documentService.calculate(user, form);
    }
    
    @GetMapping("/diploma/print.pdf")
    public void diplomaPrint(HoisUserDetails user, DiplomaForm form, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        documentService.createUpdateDiplomas(user, form);
        HttpUtil.pdf(response, "diplomas.pdf", documentService.diplomaPrint(user, form));
    }
    
    @PostMapping("/diploma/confirm")
    public void diplomaPrintConfirm(HoisUserDetails user, @RequestBody DiplomaConfirmForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_LOPTUNNISTUS_TRUKKIMINE);
        documentService.diplomaPrintConfirm(user, form);
    }
    
    @GetMapping("/supplement/{id:\\d+}/print/view.pdf")
    public void supplementPrintView(HoisUserDetails user, @PathVariable("id") Long directiveStudentId, 
            Long signer1Id, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        documentService.createUpdateSupplement(user, directiveStudentId, signer1Id);
        HttpUtil.pdf(response, "diploma_supplement_view.pdf", documentService.supplementPrintView(user, directiveStudentId));
    }

    @GetMapping("/supplement/{id:\\d+}/calculate")
    public List<FormDto> calculate(HoisUserDetails user, @PathVariable("id") Long directiveStudentId, 
            SupplementForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        documentService.createUpdateSupplement(user, directiveStudentId, form.getSigner1Id());
        return documentService.calculate(user, directiveStudentId, form);
    }
    
    @GetMapping("/supplement/{id:\\d+}/print.pdf")
    public void supplementPrint(HoisUserDetails user, @PathVariable("id") Long directiveStudentId, 
            SupplementForm form, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        documentService.createUpdateSupplement(user, directiveStudentId, form.getSigner1Id());
        HttpUtil.pdf(response, "diploma_supplement.pdf", documentService.supplementPrint(user, directiveStudentId, form));
    }
    
    @PostMapping("/supplement/{id:\\d+}/confirm")
    public void supplementPrintConfirm(HoisUserDetails user, @PathVariable("id") Long directiveStudentId, 
            @RequestBody List<Long> formIds) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_HINNETELEHT_TRUKKIMINE);
        documentService.supplementPrintConfirm(user, directiveStudentId, formIds);
    }
}
