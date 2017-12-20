package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.service.ScholarshipService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationListSubmitForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermStudentDto;

@RestController
@RequestMapping("/scholarships")
public class ScholarshipController {
    @Autowired
    ScholarshipService scholarshipService;

    @PostMapping
    public HttpUtil.CreatedResponse create(HoisUserDetails user, @Valid @RequestBody ScholarshipTermForm form) {
        // TODO: ADD VALIDATION BASED ON THE TYPE OF SCHOLARSHIP TERM -------
        // IMPORTANT!!!!!!!!!!!!!!!!
        UserUtil.assertIsSchoolAdmin(user);
        return HttpUtil.created(scholarshipService.create(user, form));
    }

    @GetMapping
    public Page<ScholarshipTermSearchDto> list(ScholarshipSearchCommand command, Pageable pageable,
            HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return scholarshipService.list(user, command, pageable);
    }
    
    @GetMapping("/applications")
    public List<ScholarshipApplicationSearchDto> applications(@Valid ScholarshipApplicationSearchCommand command, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return scholarshipService.applications(command, user);
    }

    @GetMapping("/{id:\\d+}")
    public ScholarshipTermDto get(HoisUserDetails user, @WithEntity ScholarshipTerm term) {
        UserUtil.assertIsSchoolAdmin(user, term.getSchool());
        return scholarshipService.get(term);
    }

    @GetMapping("/{id:\\d+}/application")
    public Map<String, Object> application(HoisUserDetails user, @WithEntity ScholarshipTerm term) {
        UserUtil.assertIsSchoolAdminOrStudent(user, term.getSchool());
        return scholarshipService.getStudentApplicationView(user, term);
    }

    @PutMapping("/apply/{id:\\d+}")
    public ScholarshipApplicationDto apply(HoisUserDetails user, @WithEntity ScholarshipApplication application) {
        UserUtil.assertIsSchoolAdminOrStudent(user, application.getScholarshipTerm().getSchool());
        return scholarshipService.getStudentApplicationDto(scholarshipService.apply(user, application));
    }
    
    @PutMapping("/acceptApplications")
    public HttpStatus acceptApplications(HoisUserDetails user, @Valid @RequestBody ScholarshipApplicationListSubmitForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return scholarshipService.acceptApplications(form);
    }
    
    @PutMapping("/annulApplications")
    public HttpStatus annulApplications(HoisUserDetails user, @Valid @RequestBody ScholarshipApplicationListSubmitForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return scholarshipService.annulApplications(form);
    }
    
    @GetMapping("/application/{id:\\d+}")
    public Map<String, Object> getStudentApplication(HoisUserDetails user, @WithEntity ScholarshipApplication application) {
        UserUtil.assertIsSchoolAdminOrStudent(user, application.getScholarshipTerm().getSchool());
        return scholarshipService.getApplicationView(application);
    }

    @PostMapping("/{id:\\d+}/application")
    public ScholarshipApplicationDto saveApplication(HoisUserDetails user, @WithEntity ScholarshipTerm term,
            @Valid @RequestBody ScholarshipStudentApplicationForm form) {
        UserUtil.assertIsSchoolAdminOrStudent(user, term.getSchool());
        return scholarshipService.getStudentApplicationDto(scholarshipService.saveApplication(user, term, form));
    }

    @PutMapping("/{id:\\d+}/application/{appId:\\d+}")
    public ScholarshipApplicationDto updateApplication(HoisUserDetails user, @WithEntity ScholarshipTerm term,
            @Valid @RequestBody ScholarshipStudentApplicationForm form,
            @WithEntity("appId") ScholarshipApplication application) {
        UserUtil.assertIsSchoolAdminOrStudent(user, term.getSchool());
        return scholarshipService
                .getStudentApplicationDto(scholarshipService.updateApplication(user, form, application));
    }

    @PutMapping("/{id:\\d+}")
    public ScholarshipTermDto save(HoisUserDetails user, @WithEntity ScholarshipTerm scholarshipTerm,
            @Valid @RequestBody ScholarshipTermForm form) {
        // TODO: ADD VALIDATION BASED ON THE TYPE OF SCHOLARSHIP TERM
        UserUtil.assertIsSchoolAdmin(user, scholarshipTerm.getSchool());
        return get(user, scholarshipService.save(scholarshipTerm, form));
    }

    @PutMapping("/{id:\\d+}/publish")
    public ScholarshipTermDto publish(HoisUserDetails user, @WithEntity ScholarshipTerm scholarshipTerm) {
        UserUtil.assertIsSchoolAdmin(user, scholarshipTerm.getSchool());
        return get(user, scholarshipService.publish(scholarshipTerm));
    }

    @GetMapping("/availableStipends")
    public List<ScholarshipTermStudentDto> availableStipends(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return scholarshipService.availableStipends(user.getStudentId());
    }

    @GetMapping("/studentStipends")
    public List<ScholarshipTermStudentDto> studentStipends(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return scholarshipService.studentStipends(user.getStudentId());
    }

    /*
     * public static boolean canEditScholarshipTerm(HoisUserDetails user,
     * ScholarshipTerm scholarshipTerm) { return
     * ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL,
     * scholarshipTerm.getStatus()) && UserUtil.assertIsSchoolAdmin(user,
     * scholarshipTerm.getSchool()); }
     */

}