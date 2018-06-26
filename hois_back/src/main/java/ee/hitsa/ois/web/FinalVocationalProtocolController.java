package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
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
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.report.FinalProtocolReport;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.FinalVocationalProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.MobileIdStatus;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSearchDto;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSignForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EntityMobileSignDto;
import ee.hitsa.ois.web.dto.EntitySignDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.finalprotocol.FinalProtocolCommitteeSelectDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalVocationalProtocolDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalVocationalProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalVocationalProtocolStudentDto;

@RestController
@RequestMapping("/finalVocationalProtocols")
public class FinalVocationalProtocolController {
    
    private static final String BDOC_TO_SIGN = "vocationalFinalProtocolBdocContainerToSign";
    private static final String MOBILE_SESSCODE = "vocationalFinalProtocolBdocMobileSesscode";
    
    @Autowired
    private FinalVocationalProtocolService finalProtocolService;
    @Autowired
    private BdocService bdocService;
    @Autowired
    private PdfService pdfService;
    
    @GetMapping
    public Page<FinalVocationalProtocolSearchDto> search(HoisUserDetails user, @Valid FinalVocationalProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.search(user, command, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public FinalVocationalProtocolDto get(HoisUserDetails user, @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        return finalProtocolService.finalVocationalProtocol(user, protocol);
    }
    
    @PostMapping
    public FinalVocationalProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody FinalVocationalProtocolCreateForm finalProtocolCreateForm) {
        FinalProtocolUtil.assertIsSchoolAdminOrTeacherResponsible(user, finalProtocolCreateForm.getProtocolVdata().getTeacher());
        return finalProtocolService.create(user, finalProtocolCreateForm);
    }
    
    @PutMapping("/{id:\\d+}")
    public FinalVocationalProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalVocationalProtocolSaveForm finalProtocolSaveForm) {
        FinalProtocolUtil.assertCanEdit(user, protocol);
        return get(user, finalProtocolService.save(protocol, finalProtocolSaveForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        FinalProtocolUtil.assertCanDelete(user, protocol);
        finalProtocolService.delete(user, protocol);
    }
    
    @GetMapping("/curriculumVersions")
    public List<CurriculumVersionResult> curriculumVersions(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.curriculumVersionsForSelection(user);
    }
    
    @GetMapping("/occupationModules/{curriculumVersionId:\\d+}")
    public List<AutocompleteResult> occupationModules(HoisUserDetails user, @PathVariable Long curriculumVersionId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.occupationModulesForSelection(user, curriculumVersionId);
    }
    
    @GetMapping("/occupationModule/exam/{studyYearId:\\d+}/{curriculumVersionOccupationModuleId:\\d+}")
    public FinalVocationalProtocolOccupationalModuleDto examOccupationModule(HoisUserDetails user, @PathVariable Long studyYearId,
            @PathVariable Long curriculumVersionOccupationModuleId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.occupationModule(user, studyYearId, curriculumVersionOccupationModuleId, Boolean.FALSE);
    }
    
    @GetMapping("/occupationModule/thesis/{studyYearId:\\d+}/{curriculumVersionOccupationModuleId:\\d+}")
    public FinalVocationalProtocolOccupationalModuleDto thesisOccupationModule(HoisUserDetails user, @PathVariable Long studyYearId,
            @PathVariable Long curriculumVersionOccupationModuleId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.occupationModule(user, studyYearId, curriculumVersionOccupationModuleId, Boolean.TRUE);
    }
    
    @GetMapping("/committees")
    public List<FinalProtocolCommitteeSelectDto> committees(HoisUserDetails user, 
            @RequestParam(value = "finalDate", required = false) LocalDate finalDate) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.committeesForSelection(user, finalDate);
    }
    
    @GetMapping("/{id:\\d+}/otherStudents")
    public Collection<FinalVocationalProtocolStudentDto> otherStudents(HoisUserDetails user,
            @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.otherStudents(user, protocol);
    }

    @DeleteMapping("/{id:\\d+}/removeStudent/{studentId:\\d+}")
    public FinalVocationalProtocolDto removeStudent(HoisUserDetails user, @WithEntity Protocol protocol,
            @WithEntity("studentId") ProtocolStudent student) {
        FinalProtocolUtil.assertCanEdit(user, protocol);
        finalProtocolService.removeStudent(user, student);
        return get(user, protocol);
    }
    
    @PostMapping("/{id:\\d+}/signToConfirm")
    public EntitySignDto signToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalVocationalProtocolSignForm protocolSignForm, HttpSession httpSession) {
        FinalProtocolUtil.assertCanConfirm(user, protocol);
        Protocol savedProtocol = finalProtocolService.save(protocol, protocolSignForm);

        UnsignedBdocContainer unsignedBdocContainer = bdocService.createUnsignedBdocContainer("lopueksami_protokoll.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfService.generate(FinalProtocolReport.VOCATIONAL_TEMPLATE_NAME, new FinalProtocolReport(savedProtocol)),
                protocolSignForm.getCertificate());

        httpSession.setAttribute(BDOC_TO_SIGN, unsignedBdocContainer);
        return EntitySignDto.of(savedProtocol, unsignedBdocContainer);
    }

    @PostMapping("/{id:\\d+}/signToConfirmFinalize")
    public FinalVocationalProtocolDto signToConfirmFinalize(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody SignatureCommand signatureCommand, HttpSession httpSession) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());

        UnsignedBdocContainer unsignedBdocContainer = (UnsignedBdocContainer) httpSession.getAttribute(BDOC_TO_SIGN);
        protocol.setOisFile(bdocService.getSignedBdoc(unsignedBdocContainer, signatureCommand.getSignature(), "protokoll"));
        httpSession.removeAttribute(BDOC_TO_SIGN);
        return get(user, finalProtocolService.confirm(user, protocol, null));
    }

    @PostMapping("/{id:\\d+}/mobileSignToConfirm")
    public EntityMobileSignDto mobileSignToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalVocationalProtocolSignForm protocolSaveForm, HttpSession httpSession) {
        FinalProtocolUtil.assertCanConfirm(user, protocol);
        Protocol savedProtocol = finalProtocolService.save(protocol, protocolSaveForm);
        
        byte[] pdfData = pdfService.generate(FinalProtocolReport.VOCATIONAL_TEMPLATE_NAME, new FinalProtocolReport(savedProtocol));
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
    public FinalVocationalProtocolDto mobileSignFinalize(HoisUserDetails user, 
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @SuppressWarnings("unused") @RequestBody VersionedCommand version, HttpSession httpSession) {
        Integer sesscode = (Integer) httpSession.getAttribute(MOBILE_SESSCODE);
        if (sesscode != null) {
            OisFile signedBdoc = bdocService.getMobileSignedBdoc(sesscode, "protokoll");
            if (signedBdoc != null) {
                protocol.setOisFile(signedBdoc);
                protocol = finalProtocolService.confirm(user, protocol, null);
            }
            httpSession.removeAttribute(MOBILE_SESSCODE);
        }
        return get(user, protocol);
    }
    
    @GetMapping("/{id:\\d+}/print/protocol.pdf")
    public void print(HoisUserDetails user, @WithEntity Protocol protocol, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf",
                pdfService.generate(FinalProtocolReport.VOCATIONAL_TEMPLATE_NAME, new FinalProtocolReport(protocol)));
    }
    
}