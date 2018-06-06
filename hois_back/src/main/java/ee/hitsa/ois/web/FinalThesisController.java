package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.FinalThesis;
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
import ee.hitsa.ois.web.dto.FinalThesisDto;
import ee.hitsa.ois.web.dto.FinalThesisSearchDto;
import ee.hitsa.ois.web.dto.TeacherDto;

@RestController
@RequestMapping("/finalThesis")
public class FinalThesisController {
   
    @Autowired
    private FinalThesisService finalThesisService;
    @Autowired
    private PdfService pdfService;
    
    @GetMapping
    public Page<FinalThesisSearchDto> search(HoisUserDetails user, @Valid FinalThesisSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalThesisService.search(user, command, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public FinalThesisDto get(HoisUserDetails user, @WithEntity FinalThesis finalThesis) {
        FinalThesisUtil.assertCanView(user, finalThesis);
        return finalThesisService.get(user, finalThesis);
    }
    
    @GetMapping("/studentFinalThesis")
    public Map<String, Object> studentFinalThesis(HoisUserDetails user) {
        return finalThesisService.studentFinalThesis(user);
    }
    
    @PostMapping
    public FinalThesisDto create(HoisUserDetails user, @Valid @RequestBody FinalThesisForm form) {
        FinalThesisUtil.assertCanCreate(user);
        return get(user, finalThesisService.create(form));
    }

    @PutMapping("/{id:\\d+}")
    public FinalThesisDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) FinalThesis finalThesis,
            @Valid @RequestBody FinalThesisForm form) {
        FinalThesisUtil.assertCanEdit(user, finalThesis);
        return get(user, finalThesisService.save(finalThesis, form));
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
    public FinalThesisDto confirm(HoisUserDetails user, @WithEntity FinalThesis finalThesis, @Valid @RequestBody FinalThesisForm form) {
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
