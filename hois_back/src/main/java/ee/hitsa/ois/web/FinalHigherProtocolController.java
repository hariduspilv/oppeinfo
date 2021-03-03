package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import ee.hitsa.ois.bdoc.MobileIdSigningSession;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.EntityMobileSignDto;
import org.digidoc4j.Container;
import org.digidoc4j.DataFile;
import org.digidoc4j.DataToSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.report.FinalProtocolReport;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.FinalHigherProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolSignForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EntitySignDto;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalHigherProtocolDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalHigherProtocolSubjectDto;

@RestController
@RequestMapping("/finalHigherProtocols")
public class FinalHigherProtocolController {

    private static final String BDOC_TO_SIGN = "higherFinalProtocolBdocContainerToSign";
    private static final String BDOC_CONT = "higherFinalProtocolBdocContainer";
    private static final String MOBILE_SESSIONID = "higherFinalProtocolBdocMobileSessionId";

    @Autowired
    private FinalHigherProtocolService finalProtocolService;
    @Autowired
    private BdocService bdocService;
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, @Valid FinalHigherProtocolSearchCommand command,
            Pageable pageable) {
        FinalProtocolUtil.assertCanSearchHigher(user);
        return finalProtocolService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public FinalHigherProtocolDto get(HoisUserDetails user, @WithEntity Protocol protocol) {
        FinalProtocolUtil.assertCanView(user, protocol);
        return finalProtocolService.finalHigherProtocol(user, protocol);
    }

    @PostMapping
    public FinalHigherProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody FinalHigherProtocolCreateForm finalProtocolCreateForm) {
        FinalProtocolUtil.assertCanCreateHigherProtocol(user);
        return get(user, finalProtocolService.create(user, finalProtocolCreateForm));
    }

    @PutMapping("/{id:\\d+}")
    public FinalHigherProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalHigherProtocolSaveForm finalProtocolSaveForm) {
        FinalProtocolUtil.assertCanEdit(user, protocol);
        return get(user, finalProtocolService.save(protocol, finalProtocolSaveForm, false));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        FinalProtocolUtil.assertCanDelete(user, protocol);
        finalProtocolService.delete(user, protocol);
    }

    @GetMapping("/curriculums/exam")
    public Page<AutocompleteResult> examCurriculums(HoisUserDetails user, SearchCommand lookup) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return new PageImpl<>(finalProtocolService.curriculumsForSelection(user.getSchoolId(), lookup, Boolean.FALSE));
    }

    @GetMapping("/curriculums/thesis")
    public Page<AutocompleteResult> thesisCurriculums(HoisUserDetails user, SearchCommand lookup) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return new PageImpl<>(finalProtocolService.curriculumsForSelection(user.getSchoolId(), lookup, Boolean.TRUE));
    }

    @GetMapping("/subjects/exam/{studyPeriodId:\\d+}/{curriculumVersionId:\\d+}")
    public List<AutocompleteResult> examSubjects(HoisUserDetails user, @PathVariable Long studyPeriodId,
            @PathVariable Long curriculumVersionId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.subjectsForSelection(user, studyPeriodId, curriculumVersionId, Boolean.FALSE);
    }

    @GetMapping("/subjects/thesis/{curriculumVersionId:\\d+}")
    public List<AutocompleteResult> thesisSubjects(HoisUserDetails user, @PathVariable Long curriculumVersionId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.subjectsForSelection(user, null, curriculumVersionId, Boolean.TRUE);
    }

    @GetMapping("/subject/exam/{curriculumVersionId:\\d+}/{studyPeriodId:\\d+}")
    public FinalHigherProtocolSubjectDto examSubject(HoisUserDetails user, @PathVariable Long curriculumVersionId,
            @PathVariable Long studyPeriodId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.subject(user, curriculumVersionId, studyPeriodId, Boolean.FALSE);
    }

    @GetMapping("/subject/thesis/{curriculumVersionId:\\d+}/{subjectId:\\d+}")
    public FinalHigherProtocolSubjectDto thesisSubject(HoisUserDetails user, @PathVariable Long curriculumVersionId,
            @PathVariable Long subjectId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.subject(user, curriculumVersionId, subjectId, Boolean.TRUE);
    }

    @GetMapping("/committees")
    public List<AutocompleteResult> committees(HoisUserDetails user,
            @RequestParam(value = "finalDate", required = false) LocalDate finalDate) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalProtocolService.committeesForSelection(user, finalDate);
    }

    @DeleteMapping("/{id:\\d+}/removeStudent/{studentId:\\d+}")
    public FinalHigherProtocolDto removeStudent(HoisUserDetails user, @WithEntity Protocol protocol,
            @WithEntity("studentId") ProtocolStudent student) {
        FinalProtocolUtil.assertCanEdit(user, protocol);
        finalProtocolService.removeStudent(user, student);
        return get(user, protocol);
    }

    @PostMapping("/{id:\\d+}/signToConfirm")
    public EntitySignDto signToConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalHigherProtocolSignForm protocolSignForm, HttpSession httpSession) {
        FinalProtocolUtil.assertCanConfirm(user, protocol);
        Protocol savedProtocol = finalProtocolService.save(protocol, protocolSignForm, true);

        Boolean isLetterGrades = protocol.getSchool().getIsLetterGrade();
        UnsignedBdocContainer unsignedBdocContainer = bdocService.createUnsignedBdocContainer("lopueksami_protokoll.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfService.generate(FinalProtocolReport.HIGHER_TEMPLATE_NAME, new FinalProtocolReport(savedProtocol, isLetterGrades)),
                protocolSignForm.getCertificate());

        httpSession.setAttribute(BDOC_TO_SIGN, unsignedBdocContainer.getDataToSign());
        httpSession.setAttribute(BDOC_CONT, unsignedBdocContainer.getContainer());
        return EntitySignDto.of(savedProtocol, unsignedBdocContainer);
    }

    @PostMapping("/{id:\\d+}/signToConfirmFinalize")
    public FinalHigherProtocolDto signToConfirmFinalize(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody SignatureCommand signatureCommand, HttpSession httpSession) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());

        DataToSign dataToSign = (DataToSign) httpSession.getAttribute(BDOC_TO_SIGN);
        Container container = (Container) httpSession.getAttribute(BDOC_CONT);

        protocol.setOisFile(bdocService.getSignedBdoc(container, dataToSign, signatureCommand.getSignature(), "protokoll"));

        httpSession.removeAttribute(BDOC_TO_SIGN);
        httpSession.removeAttribute(BDOC_CONT);
        return get(user, finalProtocolService.confirm(user, protocol));
    }

    @PostMapping("/{id:\\d+}/mobileIdSignatureRequest")
    public EntityMobileSignDto mobileIdSignatureRequest(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalHigherProtocolSignForm protocolSaveForm,
            HttpSession httpSession, Language lang) {
        FinalProtocolUtil.assertCanConfirm(user, protocol);
        Protocol savedProtocol = finalProtocolService.save(protocol, protocolSaveForm, true);

        Boolean isLetterGrades = protocol.getSchool().getIsLetterGrade();
        byte[] pdfData = pdfService.generate(FinalProtocolReport.VOCATIONAL_TEMPLATE_NAME,
                new FinalProtocolReport(savedProtocol, isLetterGrades));

        DataFile dataFile = new DataFile(pdfData, "lopueksami_protokoll.pdf", MediaType.APPLICATION_PDF_VALUE);
        String mobileNumber = UserUtil.isOAuthLoginType(user) ? protocolSaveForm.getSignerMobileNumber()
                : user.getMobileNumber();
        MobileIdSigningSession session = bdocService.mobileIdSignatureRequest(user.getIdcode(), mobileNumber,
                dataFile, lang);
        httpSession.setAttribute(MOBILE_SESSIONID, session.getSessionID());
        httpSession.setAttribute(BDOC_TO_SIGN, session.getDataToSign());
        httpSession.setAttribute(BDOC_CONT, session.getContainer());
        return EntityMobileSignDto.of(protocol, session.getVerificationCode());
    }

    @PostMapping("/{id:\\d+}/mobileIdSign")
    public FinalHigherProtocolDto mobileIdSign(HoisUserDetails user,
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
                protocol = finalProtocolService.confirm(user, protocol);
            }
            httpSession.removeAttribute(MOBILE_SESSIONID);
            httpSession.removeAttribute(BDOC_TO_SIGN);
            httpSession.removeAttribute(BDOC_CONT);
        }
        return get(user, protocol);
    }

    @GetMapping("/{id:\\d+}/print/protocol.pdf")
    public void print(HoisUserDetails user, @WithEntity Protocol protocol, HttpServletResponse response)
            throws IOException {
        FinalProtocolUtil.assertCanView(user, protocol);
        Boolean isLetterGrades = protocol.getSchool().getIsLetterGrade();
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf",
                pdfService.generate(FinalProtocolReport.HIGHER_TEMPLATE_NAME, new FinalProtocolReport(protocol, isLetterGrades)));
    }
}
