package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ee.hitsa.ois.web.dto.finalthesis.FinalThesisDtoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.report.FinalThesisReport;
import ee.hitsa.ois.service.FinalThesisService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.FinalThesisUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.FinalThesisForm;
import ee.hitsa.ois.web.commandobject.FinalThesisSearchCommand;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.TeacherDto;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisSearchDto;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisStudentDto;

@RestController
@RequestMapping("/finalThesis")
public class FinalThesisController {

    @Autowired
    private FinalThesisService finalThesisService;
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public Page<FinalThesisSearchDto> search(HoisUserDetails user, @Valid FinalThesisSearchCommand command,
            Pageable pageable) {
        FinalThesisUtil.assertCanSearch(user);
        return finalThesisService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public FinalThesisDtoContainer get(HoisUserDetails user, @WithEntity FinalThesis finalThesis) {
        FinalThesisUtil.assertCanView(user, finalThesis);
        return finalThesisService.get(user, finalThesis);
    }

    @GetMapping("/studentFinalThesis")
    public FinalThesisDtoContainer studentFinalThesis(HoisUserDetails user,
            @RequestParam(value = "studentId", required = false) Long studentId) {
        return finalThesisService.studentFinalThesis(user, studentId != null ? studentId : user.getStudentId());
    }

    @PostMapping
    public FinalThesisDtoContainer create(HoisUserDetails user, @Valid @RequestBody FinalThesisForm form) {
        FinalThesisUtil.assertCanCreate(user, form);
        return get(user, finalThesisService.create(user, form));
    }

    @GetMapping("/{id:\\d+}/isStudentActive")
    public Map<String, Boolean> isFinalThesisStudentActive(@WithEntity FinalThesis finalThesis) {
        return finalThesisService.isFinalThesisStudentActive(finalThesis);
    }

    @PutMapping("/{id:\\d+}")
    public FinalThesisDtoContainer save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) FinalThesis finalThesis,
            @Valid @RequestBody FinalThesisForm form) {
        FinalThesisUtil.assertCanEdit(user, finalThesis);
        return get(user, finalThesisService.save(finalThesis, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") FinalThesis finalThesis,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        FinalThesisUtil.assertCanDelete(user, finalThesis);
        finalThesisService.delete(user, finalThesis);
    }

    @GetMapping("/students")
    public Page<AutocompleteResult> students(HoisUserDetails user, SearchCommand lookup) {
        return new PageImpl<>(finalThesisService.students(user, lookup));
    }

    @GetMapping("/teacher")
    public TeacherDto teacher(@RequestParam(value = "teacherId") Long teacherId) {
        return finalThesisService.teacher(teacherId);
    }

    @PutMapping("/{id:\\d+}/confirm")
    public FinalThesisDtoContainer confirm(HoisUserDetails user, @WithEntity FinalThesis finalThesis,
            @Valid @RequestBody FinalThesisForm form) {
        FinalThesisUtil.assertCanConfirm(user, finalThesis);
        return get(user, finalThesisService.confirm(user, finalThesis, form));
    }

    @GetMapping("/print/{id:\\d+}/finalThesis.pdf")
    public void print(HoisUserDetails user, @WithEntity FinalThesis finalThesis, HttpServletResponse response)
            throws IOException {
        FinalThesisUtil.assertCanView(user, finalThesis);

        HttpUtil.pdf(response, "final.thesis.pdf",
                pdfService.generate(FinalThesisReport.TEMPLATE_NAME, new FinalThesisReport(finalThesis)));
    }

}
