package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.report.FinalExamProtocolReport;
import ee.hitsa.ois.service.FinalExamHigherProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.FinalExamProtocolUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolSaveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolSubjectDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamProtocolCommitteeSelectDto;

@RestController
@RequestMapping("/finalExamHigherProtocols")
public class FinalExamHigherProtocolController {

    @Autowired
    private FinalExamHigherProtocolService finalExamProtocolService;
    @Autowired
    private PdfService pdfService;
    
    @GetMapping
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, @Valid HigherProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.search(user, command, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public FinalExamHigherProtocolDto get(HoisUserDetails user, @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        return finalExamProtocolService.finalExamHigherProtocol(user, protocol);
    }
    
    @PostMapping
    public FinalExamHigherProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody FinalExamHigherProtocolCreateForm finalExamProtocolCreateForm) {
        FinalExamProtocolUtil.assertCanCreateHigherProtocol(user);
        return FinalExamHigherProtocolDto.of(finalExamProtocolService.create(user, finalExamProtocolCreateForm));
    }
    
    @PutMapping("/{id:\\d+}")
    public FinalExamHigherProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody FinalExamHigherProtocolSaveForm finalExamProtocolSaveForm) {
        FinalExamProtocolUtil.assertCanEdit(user, protocol);
        return get(user, finalExamProtocolService.save(protocol, finalExamProtocolSaveForm));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        FinalExamProtocolUtil.assertCanDelete(user, protocol);
        finalExamProtocolService.delete(user, protocol);
    }
    
    @GetMapping("/curriculums")
    public List<AutocompleteResult> curriculums(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.curriculumsForSelection(user.getSchoolId());
    }
    
    @GetMapping("/subjects/{studyPeriodId:\\d+}/{curriculumId:\\d+}")
    public List<AutocompleteResult> subjects(HoisUserDetails user, @PathVariable Long studyPeriodId,
            @PathVariable Long curriculumId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.subjectsForSelection(user, studyPeriodId, curriculumId);
    }
    
    @GetMapping("/subject/{studyPeriodId:\\d+}")
    public FinalExamHigherProtocolSubjectDto subject(HoisUserDetails user, @PathVariable Long studyPeriodId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.subject(user, studyPeriodId);
    }
    
    @GetMapping("/committees")
    public List<FinalExamProtocolCommitteeSelectDto> committees(HoisUserDetails user, 
            @RequestParam(value = "finalDate", required = false) LocalDate finalDate) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.committeesForSelection(user, finalDate);
    }
    
    @DeleteMapping("/{id:\\d+}/removeStudent/{studentId:\\d+}")
    public FinalExamHigherProtocolDto removeStudent(HoisUserDetails user, @WithEntity Protocol protocol,
            @WithEntity("studentId") ProtocolStudent student) {
        FinalExamProtocolUtil.assertCanEdit(user, protocol);
        finalExamProtocolService.removeStudent(user, student);
        return get(user, protocol);
    }
    
    @GetMapping("/{id:\\d+}/print/protocol.pdf")
    public void print(HoisUserDetails user, @WithEntity Protocol protocol, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf",
                pdfService.generate(FinalExamProtocolReport.HIGHER_TEMPLATE_NAME, new FinalExamProtocolReport(protocol)));
    }
}
