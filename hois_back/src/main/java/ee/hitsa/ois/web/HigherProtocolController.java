package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.bdoc.MobileIdSigningSession;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.EntityMobileSignDto;
import org.digidoc4j.Container;
import org.digidoc4j.DataFile;
import org.digidoc4j.DataToSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.report.HigherProtocolReport;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.HigherProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
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
import ee.hitsa.ois.web.commandobject.higherprotocol.SubjectStudyPeriodCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
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
    private static final String MOBILE_SESSIONID = "higherProtocolBdocMobileSessionID";

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
        HigherProtocolUtil.assertCanCreate(user, form.getSubjectStudyPeriod());
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

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        HigherProtocolUtil.assertCanDelete(user, protocol);
        higherProtocolService.delete(user, protocol);
    }

    @GetMapping("/subjectStudyPeriods")
    public List<AutocompleteResult> getSubjectStudyPeriods(HoisUserDetails user, SubjectStudyPeriodCommand lookup) {
        HigherProtocolUtil.assertCanCreate(user, lookup.getStudyPeriodId());
        return higherProtocolService.getSubjectStudyPeriods(user, lookup);
    }

    @GetMapping("/curriculumVersions")
    public Page<AutocompleteResult> getModuleProtocolCurriculumVersions(HoisUserDetails user, SearchCommand lookup,
            Pageable pageable) {
        return higherProtocolService.getModuleProtocolCurriculumVersions(user, lookup, pageable);
    }

    @GetMapping("/students")
    public List<StudentSearchDto> getStudents(HoisUserDetails user,
            @Valid HigherProtocolStudentSearchCommand criteria) {
        HigherProtocolUtil.assertCanCreate(user, criteria.getSubjectStudyPeriod());
        return higherProtocolService.getStudents(user.getSchoolId(), criteria);
    }

    @GetMapping("/moduleProtocol/students")
    public List<StudentSearchDto> getModuleProtocolStudents(HoisUserDetails user, @RequestParam Long curriculumVersionHmodule) {
        HigherProtocolUtil.assertCanCreate(user, null);
        return higherProtocolService.getModuleProtocolStudents(user.getSchoolId(), curriculumVersionHmodule);
    }

    @DeleteMapping("/{id:\\d+}/removeStudent/{studentId:\\d+}")
    public HigherProtocolDto removeStudent(HoisUserDetails user, @WithEntity Protocol protocol,
            @WithEntity("studentId") ProtocolStudent student) {
        HigherProtocolUtil.canChange(user,protocol);
        higherProtocolService.removeStudent(user, student);
        return get(user, protocol);
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

    @PostMapping("/{id:\\d+}/mobileIdSignatureRequest")
    public EntityMobileSignDto mobileIdSignatureRequest(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody HigherProtocolSaveForm higherProtocolSaveForm,
            HttpSession httpSession, Language lang) {
        HigherProtocolUtil.assertCanChange(user, protocol);
        HigherProtocolUtil.assertCanConfirm(user, protocol);
        HigherProtocolUtil.validate(higherProtocolSaveForm, protocol);

        Protocol savedProtocol = higherProtocolService.save(protocol, higherProtocolSaveForm);
        byte[] pdfData = pdfService.generate(HigherProtocolReport.TEMPLATE_NAME,
                higherProtocolService.higherProtocolReport(savedProtocol));

        DataFile dataFile = new DataFile(pdfData, "protokoll.pdf", MediaType.APPLICATION_PDF_VALUE);
        String mobileNumber = UserUtil.isOAuthLoginType(user) ? higherProtocolSaveForm.getSignerMobileNumber()
                : user.getMobileNumber();
        MobileIdSigningSession session = bdocService.mobileIdSignatureRequest(user.getIdcode(), mobileNumber,
                dataFile, lang);
        httpSession.setAttribute(MOBILE_SESSIONID, session.getSessionID());
        httpSession.setAttribute(BDOC_TO_SIGN, session.getDataToSign());
        httpSession.setAttribute(BDOC_CONT, session.getContainer());
        return EntityMobileSignDto.of(protocol, session.getVerificationCode());
    }

    @PostMapping("/{id:\\d+}/mobileIdSign")
    public HigherProtocolDto mobileIdSign(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @SuppressWarnings("unused") @RequestBody VersionedCommand version, HttpSession httpSession) {
        String sessionID = (String) httpSession.getAttribute(MOBILE_SESSIONID);
        DataToSign dataToSign = (DataToSign) httpSession.getAttribute(BDOC_TO_SIGN);
        Container container = (Container) httpSession.getAttribute(BDOC_CONT);

        if (sessionID != null) {
            MobileIdSigningSession session = new MobileIdSigningSession(sessionID, dataToSign, container);
            OisFile signedBdoc = bdocService.mobileIdSign(session, "protokoll");
            if (signedBdoc != null) {
                protocol.setOisFile(signedBdoc);
                protocol = higherProtocolService.confirm(user, protocol, null);
            }
            httpSession.removeAttribute(MOBILE_SESSIONID);
            httpSession.removeAttribute(BDOC_TO_SIGN);
            httpSession.removeAttribute(BDOC_CONT);
        }
        return get(user, protocol);
    }

    @GetMapping("/{id:\\d+}/calculate")
    public List<ProtocolStudentResultDto> calculateGrades(HoisUserDetails user, @WithEntity Protocol protocol,
            @NotNull @Valid ProtocolCalculateCommand command) {
        HigherProtocolUtil.assertCanChange(user, protocol);
        return higherProtocolService.calculateGrades(protocol, command);
    }
}
