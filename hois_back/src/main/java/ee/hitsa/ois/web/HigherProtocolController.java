package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.report.HigherProtocolReport;
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
import ee.hitsa.ois.web.commandobject.HigherProtocolStudentSearchCommand;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolDto;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RestController
@RequestMapping("/higherProtocols")
public class HigherProtocolController {

    @Autowired
    private HigherProtocolService higherProtocolService;
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
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf",
                pdfService.generate(HigherProtocolReport.TEMPLATE_NAME, new HigherProtocolReport(protocol)));
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

    /**
     * HigherProtocolSaveForm is used only for version control
     */
    @PutMapping("/confirm/{id:\\d+}")
    public HigherProtocolDto confirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @SuppressWarnings("unused") @NotNull @RequestBody HigherProtocolSaveForm form) {
        HigherProtocolUtil.assertCanConfirm(user, protocol);
        return get(user, higherProtocolService.confirm(user, protocol));
    }

    @PutMapping("/saveAndConfirm/{id:\\d+}")
    public HigherProtocolDto saveAndConfirm(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Protocol protocol,
            @NotNull @Valid @RequestBody HigherProtocolSaveForm form) {
        HigherProtocolUtil.assertCanChange(user, protocol);
        HigherProtocolUtil.assertCanConfirm(user, protocol);
        HigherProtocolUtil.validate(form, protocol);
        return get(user, higherProtocolService.saveAndConfirm(user, protocol, form));
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

    @GetMapping("/{id:\\d+}/calculate")
    public List<ProtocolStudentResultDto> calculateGrades(HoisUserDetails user,
            @NotNull @Valid ProtocolCalculateCommand command, @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        return higherProtocolService.calculateGrades(command);
    }
}
