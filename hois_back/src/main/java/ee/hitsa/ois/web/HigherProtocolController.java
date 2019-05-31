package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.digidoc4j.Container;
import org.digidoc4j.DataToSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.bdoc.MobileIdSession;
import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.report.HigherProtocolReport;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.HigherProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.MobileIdStatus;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.HigherProtocolSignForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolStudentSearchCommand;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EntityMobileSignDto;
import ee.hitsa.ois.web.dto.EntitySignDto;
import ee.hitsa.ois.web.dto.HigherProtocolDto;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RestController
@RequestMapping("/higherProtocols")
public class HigherProtocolController {
    
    private static final String BDOC_TO_SIGN = "higherProtocolBdocContainerToSign";
    private static final String BDOC_CONT = "higherProtocolBdocContainer";
    private static final String MOBILE_SESSCODE = "higherProtocolBdocMobileSesscode";

    @Autowired
    private HigherProtocolService higherProtocolService;
    @Autowired
    private BdocService bdocService;
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user,
            @NotNull @Valid HigherProtocolSearchCommand criteria, Pageable pageable) {
        HigherProtocolUtil.assertCanSearch(user);
        return higherProtocolService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public HigherProtocolDto get(HoisUserDetails user, @WithEntity Protocol protocol) {
        HigherProtocolUtil.assertCanView(user, protocol);
        return higherProtocolService.get(user, protocol);
    }

    @GetMapping("/print/{id:\\d+}/protocol.pdf")
    public void print(HoisUserDetails user, @WithEntity Protocol protocol, HttpServletResponse response)
            throws IOException {
        HigherProtocolUtil.assertCanView(user, protocol);
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf", pdfService
                .generate(HigherProtocolReport.TEMPLATE_NAME, higherProtocolService.higherProtocolReport(protocol)));
    }

    @PostMapping
    public HigherProtocolDto create(HoisUserDetails user, @NotNull @Valid @RequestBody HigherProtocolCreateForm form) {
        HigherProtocolUtil.assertCanCreate(user);
        HigherProtocolUtil.assertStudentsAdded(form);
        return higherProtocolService.create(user, form);
    }

    @PutMapping("/{id:\\d+}")
    public HigherProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @NotNull @Valid @RequestBody HigherProtocolSaveForm form) {
        HigherProtocolUtil.assertCanChange(user, protocol);
        HigherProtocolUtil.validate(form, protocol);
        return get(user, higherProtocolService.save(protocol, form));
    }

    @GetMapping("/subjectStudyPeriods")
    public List<AutocompleteResult> getSubjectStudyPeriods(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return higherProtocolService.getSubjectStudyPeriods(user.getSchoolId());
    }

    @GetMapping("/students")
    public List<StudentSearchDto> getStudents(HoisUserDetails user,
            @Valid HigherProtocolStudentSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return higherProtocolService.getStudents(user.getSchoolId(), criteria);
    }
    
    @PostMapping("/{id:\\d+}/signToConfirm")
    public EntitySignDto signToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody HigherProtocolSignForm higherProtocolSignForm, HttpSession httpSession) {
        HigherProtocolUtil.assertCanChange(user, protocol);
        HigherProtocolUtil.assertCanConfirm(user, protocol);
        HigherProtocolUtil.validate(higherProtocolSignForm, protocol);

        Protocol savedProtocol = higherProtocolService.save(protocol, higherProtocolSignForm);

        UnsignedBdocContainer unsignedBdocContainer = bdocService.createUnsignedBdocContainer("protokoll.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfService.generate(HigherProtocolReport.TEMPLATE_NAME,
                        higherProtocolService.higherProtocolReport(savedProtocol)),
                higherProtocolSignForm.getCertificate());

        httpSession.setAttribute(BDOC_TO_SIGN, unsignedBdocContainer.getDataToSign());
        httpSession.setAttribute(BDOC_CONT, unsignedBdocContainer.getContainer());
        return EntitySignDto.of(savedProtocol, unsignedBdocContainer);
    }

    @PostMapping("/{id:\\d+}/signToConfirmFinalize")
    public HigherProtocolDto signToConfirmFinalize(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody SignatureCommand signatureCommand, HttpSession httpSession) {

        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());

        DataToSign dataToSign = (DataToSign) httpSession.getAttribute(BDOC_TO_SIGN);
        Container container = (Container) httpSession.getAttribute(BDOC_CONT);
        
        protocol.setOisFile(bdocService.getSignedBdoc(container, dataToSign, signatureCommand.getSignature(), "protokoll"));
        
        httpSession.removeAttribute(BDOC_TO_SIGN);
        httpSession.removeAttribute(BDOC_CONT);
        return get(user, higherProtocolService.confirm(user, protocol, null));
    }

    @PostMapping("/{id:\\d+}/mobileSignToConfirm")
    public EntityMobileSignDto mobileSignToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody HigherProtocolSaveForm higherProtocolSaveForm, HttpSession httpSession) {
        HigherProtocolUtil.assertCanChange(user, protocol);
        HigherProtocolUtil.assertCanConfirm(user, protocol);
        HigherProtocolUtil.validate(higherProtocolSaveForm, protocol);

        Protocol savedProtocol = higherProtocolService.save(protocol, higherProtocolSaveForm);
        byte[] pdfData = pdfService.generate(HigherProtocolReport.TEMPLATE_NAME,
                higherProtocolService.higherProtocolReport(protocol));
        
        MobileIdSession session = bdocService.mobileSign("protokoll.pdf", MediaType.APPLICATION_PDF_VALUE, pdfData,
                user.getPersonId());

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
    public HigherProtocolDto mobileSignFinalize(HoisUserDetails user, 
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @SuppressWarnings("unused") @RequestBody VersionedCommand version, HttpSession httpSession) {
        Integer sesscode = (Integer) httpSession.getAttribute(MOBILE_SESSCODE);
        if (sesscode != null) {
            OisFile signedBdoc = bdocService.getMobileSignedBdoc(sesscode, "protokoll");
            if (signedBdoc != null) {
                protocol.setOisFile(signedBdoc);
                protocol = higherProtocolService.confirm(user, protocol, null);
            }
            httpSession.removeAttribute(MOBILE_SESSCODE);
        }
        return get(user, protocol);
    }

    @GetMapping("/{id:\\d+}/calculate")
    public List<ProtocolStudentResultDto> calculateGrades(HoisUserDetails user,
            @NotNull @Valid ProtocolCalculateCommand command, @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        return higherProtocolService.calculateGrades(command);
    }
}
