package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.bdoc.MobileIdSession;
import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.report.FinalExamProtocolReport;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.FinalExamVocationalProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.MobileIdStatus;
import ee.hitsa.ois.util.FinalExamProtocolValidationUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol.FinalExamVocationProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol.FinalExamVocationalProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol.FinalExamVocationalProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol.FinalExamVocationalProtocolSearchDto;
import ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol.FinalExamVocationalProtocolSignForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EntityMobileSignDto;
import ee.hitsa.ois.web.dto.EntitySignDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.finalExamVocationalProtocol.FinalExamVocationalProtocolCommitteeSelectDto;
import ee.hitsa.ois.web.dto.finalExamVocationalProtocol.FinalExamVocationalProtocolDto;
import ee.hitsa.ois.web.dto.finalExamVocationalProtocol.FinalExamVocationalProtocolOccupationalModuleDto;

@RestController
@RequestMapping("/finalExamVocationalProtocols")
public class FinalExamVocationalProtocolController {
    
    private static final String BDOC_TO_SIGN = "finalExamProtocolBdocContainerToSign";
    private static final String MOBILE_SESSCODE = "finalExamProtocolBdocMobileSesscode";
    
    @Autowired
    private FinalExamVocationalProtocolService finalExamProtocolService;
    @Autowired
    private BdocService bdocService;
    @Autowired
    private PdfService pdfService;
    
    @GetMapping
    public Page<FinalExamVocationalProtocolSearchDto> search(HoisUserDetails user, @Valid FinalExamVocationProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.search(user, command, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public FinalExamVocationalProtocolDto get(HoisUserDetails user, @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        return finalExamProtocolService.finalExamVocationalProtocol(user, protocol);
    }
    
    @PostMapping
    public FinalExamVocationalProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody FinalExamVocationalProtocolCreateForm finalExamProtocolCreateForm) {
        FinalExamProtocolValidationUtil.assertIsSchoolAdminOrTeacherResponsible(user, finalExamProtocolCreateForm.getProtocolVdata().getTeacher());
        return FinalExamVocationalProtocolDto.of(finalExamProtocolService.create(user, finalExamProtocolCreateForm));
    }
    
    @PutMapping("/{id:\\d+}")
    public FinalExamVocationalProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalExamVocationalProtocolSaveForm finalExamProtocolSaveForm) {
        FinalExamProtocolValidationUtil.assertCanEdit(user, protocol);
        return get(user, finalExamProtocolService.save(protocol, finalExamProtocolSaveForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        FinalExamProtocolValidationUtil.assertCanDelete(user, protocol);
        finalExamProtocolService.delete(user, protocol);
    }
    
    @GetMapping("/curriculumVersions")
    public List<CurriculumVersionResult> curriculumVersions(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.curriculumVersionsForSelection(user);
    }
    
    @GetMapping("/occupationModules/{curriculumVersionId:\\d+}")
    public List<AutocompleteResult> occupationModules(HoisUserDetails user, @PathVariable Long curriculumVersionId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.occupationModulesForSelection(user, curriculumVersionId);
    }
    
    @GetMapping("occupationModule/{curriculumVersionOccupationModuleId:\\d+}")
    public FinalExamVocationalProtocolOccupationalModuleDto occupationModule(HoisUserDetails user,
            @PathVariable Long curriculumVersionOccupationModuleId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.occupationModule(user, curriculumVersionOccupationModuleId);
    }
    
    @GetMapping("/committees")
    public List<FinalExamVocationalProtocolCommitteeSelectDto> committees(HoisUserDetails user, 
            @RequestParam(value = "finalExamDate", required = false) LocalDate finalExamDate) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.committeesForSelection(user, finalExamDate);
    }
    
    @PostMapping("/{id:\\d+}/signToConfirm")
    public EntitySignDto signToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalExamVocationalProtocolSignForm protocolSignForm, HttpSession httpSession) {

        FinalExamProtocolValidationUtil.assertCanEdit(user, protocol);

        Protocol savedProtocol = finalExamProtocolService.save(protocol, protocolSignForm);

        UnsignedBdocContainer unsignedBdocContainer = bdocService.createUnsignedBdocContainer("lopueksami_protokoll.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfService.generate(FinalExamProtocolReport.TEMPLATE_NAME, new FinalExamProtocolReport(savedProtocol)),
                protocolSignForm.getCertificate());

        httpSession.setAttribute(BDOC_TO_SIGN, unsignedBdocContainer);
        return EntitySignDto.of(savedProtocol, unsignedBdocContainer);
    }

    @PostMapping("/{id:\\d+}/signToConfirmFinalize")
    public FinalExamVocationalProtocolDto signToConfirmFinalize(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody SignatureCommand signatureCommand, HttpSession httpSession) {

        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());

        UnsignedBdocContainer unsignedBdocContainer = (UnsignedBdocContainer) httpSession.getAttribute(BDOC_TO_SIGN);
        protocol.setOisFile(bdocService.getSignedBdoc(unsignedBdocContainer, signatureCommand.getSignature(), "protokoll"));
        httpSession.removeAttribute(BDOC_TO_SIGN);
        return get(user, finalExamProtocolService.confirm(user, protocol, null));
    }

    @PostMapping("/{id:\\d+}/mobileSignToConfirm")
    public EntityMobileSignDto mobileSignToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalExamVocationalProtocolSignForm protocolSaveForm, HttpSession httpSession) {
        FinalExamProtocolValidationUtil.assertCanEdit(user, protocol);

        Protocol savedProtocol = finalExamProtocolService.save(protocol, protocolSaveForm);
        byte[] pdfData = pdfService.generate(FinalExamProtocolReport.TEMPLATE_NAME, new FinalExamProtocolReport(savedProtocol));
        
        MobileIdSession session = bdocService.mobileSign("lopueksami_protokoll.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfData,
                user.getMobileNumber());

        httpSession.setAttribute(MOBILE_SESSCODE, session.getSesscode());
        return EntityMobileSignDto.of(savedProtocol, session.getChallengeID());
    }

    @RequestMapping("/{id:\\d+}/mobileSignStatus")
    public MobileIdStatus mobileSignStatus(HttpSession httpSession) {
        MobileIdStatus response = new MobileIdStatus();
        Integer sesscode = (Integer) httpSession.getAttribute(MOBILE_SESSCODE);
        if (sesscode != null) {
            String statusCode = bdocService.mobileSignStatus(sesscode);
            response.setStatus(statusCode);
            if (BdocService.closeMobileSession(statusCode)) {
                httpSession.removeAttribute(MOBILE_SESSCODE);
            }
        }
        return response;
    }

    @PostMapping("/{id:\\d+}/mobileSignFinalize")
    public FinalExamVocationalProtocolDto mobileSignFinalize(HoisUserDetails user, 
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @SuppressWarnings("unused") @RequestBody VersionedCommand version, HttpSession httpSession) {
        Integer sesscode = (Integer) httpSession.getAttribute(MOBILE_SESSCODE);
        if (sesscode != null) {
            OisFile signedBdoc = bdocService.getMobileSignedBdoc(sesscode, "protokoll");
            if (signedBdoc != null) {
                protocol.setOisFile(signedBdoc);
                protocol = finalExamProtocolService.confirm(user, protocol, null);
            }
            httpSession.removeAttribute(MOBILE_SESSCODE);
        }
        return get(user, protocol);
    }

    @PostMapping("/{id:\\d+}/confirm")
    public FinalExamVocationalProtocolDto confirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalExamVocationalProtocolSaveForm protocolSaveForm) {
        FinalExamProtocolValidationUtil.assertCanEdit(user, protocol);
        return get(user, finalExamProtocolService.confirm(user, protocol, protocolSaveForm));
    }

    @GetMapping("/{id:\\d+}/print/protocol.pdf")
    public void print(HoisUserDetails user, @WithEntity Protocol protocol, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf",
                pdfService.generate(FinalExamProtocolReport.TEMPLATE_NAME, new FinalExamProtocolReport(protocol)));
    }
    
}
