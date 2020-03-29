package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationSearchCommand;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.UnappliedScholarshipApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.ScholarshipService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.ScholarshipUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationListSubmitForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationRankingSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipCommitteeSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipDecisionForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ScholarshipTermApplicationRankingSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationStudentDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipDecisionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentRejectionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermComplianceDto;
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
        // IKE: needs to be reviewed when editing higher school scholarships 
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return HttpUtil.created(scholarshipService.create(user, form));
    }

    @GetMapping
    public Page<ScholarshipTermSearchDto> list(ScholarshipSearchCommand command, Pageable pageable,
            HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.list(user, command, pageable);
    }

    @GetMapping("/applications")
    public Page<ScholarshipApplicationSearchDto> applications(HoisUserDetails user,
            @Valid ScholarshipApplicationSearchCommand command, Pageable pageable) {
        ScholarshipUtil.assertCanSearchApplications(user);
        return scholarshipService.applications(user, command, pageable);
    }

    @GetMapping("/applications/ranking")
    public ScholarshipTermApplicationRankingSearchDto applicationsRanking(HoisUserDetails user,
            @Valid ScholarshipApplicationRankingSearchCommand command) {
        ScholarshipUtil.assertCanSearchApplications(user);
        return scholarshipService.applicationsRanking(user, command);
    }

    @GetMapping("/{id:\\d+}")
    public ScholarshipTermDto get(HoisUserDetails user, @WithEntity ScholarshipTerm term) {
        UserUtil.assertIsSchoolAdmin(user, term.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.get(term);
    }

    @GetMapping("/committees")
    public List<AutocompleteResult> committees(HoisUserDetails user, ScholarshipCommitteeSearchCommand command) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.committeesForSelection(user, command);
    }

    @GetMapping("/decision/canCreate")
    public Map<String, Boolean> canCreateDecision(HoisUserDetails user,
            @RequestParam("ids") List<Long> applicationIds) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return Collections.singletonMap("canCreate", Boolean.valueOf(scholarshipService.canCreateDecision(applicationIds)));
    }

    @GetMapping("/decision")
    public ScholarshipDecisionDto decision(HoisUserDetails user,
            @RequestParam("ids") List<Long> applicationIds) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.decision(user, applicationIds);
    }

    @GetMapping("/decision/{id:\\d+}")
    public ScholarshipDecisionDto decision(HoisUserDetails user, @PathVariable("id") Long decisionId) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        // student group teacher doesn't need to have STIPTOETUS user right
        if (!user.isTeacher() ) {
            UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        }
        return scholarshipService.decision(user, decisionId);
    }

    @DeleteMapping("/decision/{id:\\d+}")
    public void deleteDecision(HoisUserDetails user, @PathVariable("id") Long decisionId) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        scholarshipService.deleteDecision(user, decisionId);
    }

    @PostMapping("/decide")
    public void decide(HoisUserDetails user, @RequestBody ScholarshipDecisionForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        scholarshipService.decide(user, form);
    }

    @GetMapping("/studentProfilesRejection")
    public List<ScholarshipStudentRejectionDto> studentProfilesRejection(HoisUserDetails user,
            @RequestParam("id") List<Long> applicationIds) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        // student group teacher doesn't need to have STIPTOETUS user right
        if (!user.isTeacher() ) {
            UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        }
        return scholarshipService.getStudentProfilesForRejection(applicationIds);
    }

    @GetMapping("/studentTermCompliance/{studentId:\\d+}/{termId:\\d+}")
    public ScholarshipTermComplianceDto studentTermCompliance(HoisUserDetails user,
            @WithEntity("studentId") Student student, @WithEntity("termId") ScholarshipTerm term) {
        UserUtil.throwAccessDeniedIf(!ScholarshipUtil.canViewStudentTermCompliance(user, student, term));
        return scholarshipService.studentCompliesTerm(student, term);
    }

    @PutMapping("/apply/{id:\\d+}")
    public ScholarshipApplicationDto apply(HoisUserDetails user, @WithEntity ScholarshipApplication application) {
        ScholarshipUtil.assertCanEditApplication(user, application);
        return scholarshipService.getApplicationDto(scholarshipService.apply(application));
    }

    @PutMapping("/acceptApplications")
    public HttpStatus acceptApplications(HoisUserDetails user, @RequestBody List<Long> applicationIds) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.acceptApplications(user, applicationIds);
    }

    @PutMapping("/teacherConfirmApplications/yes")
    public HttpStatus teacherConfirmApplicationsYes(HoisUserDetails user, @RequestBody List<Long> applicationIds) {
        UserUtil.assertIsTeacher(user);
        return scholarshipService.teacherConfirmApplications(user, applicationIds, Boolean.TRUE);
    }

    @PutMapping("/teacherConfirmApplications/no")
    public HttpStatus teacherConfirmApplicationsNo(HoisUserDetails user, @RequestBody List<Long> applicationIds) {
        UserUtil.assertIsTeacher(user);
        return scholarshipService.teacherConfirmApplications(user, applicationIds, Boolean.FALSE);
    }

    @PutMapping("/annulApplications")
    public HttpStatus annulApplications(HoisUserDetails user,
            @Valid @RequestBody ScholarshipApplicationListSubmitForm form) {
        ScholarshipUtil.assertCanAnnulApplication(user);
        return scholarshipService.annulApplications(form, user);
    }

    @PutMapping("/rejectApplications")
    public HttpStatus rejectApplications(HoisUserDetails user,
            @Valid @RequestBody ScholarshipApplicationListSubmitForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.rejectApplications(form, user);
    }

    @PutMapping("/refreshResults")
    public HttpStatus refreshResults(HoisUserDetails user, @RequestBody List<Long> applicationIds) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.refreshResults(user, applicationIds);
    }

    @PostMapping("/checkComplies")
    public Map<Long, ScholarshipTermComplianceDto> checkComplies(HoisUserDetails user, @RequestBody List<Long> applicationIds) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        return scholarshipService.checkComplies(user, applicationIds);
    }

    @GetMapping("/application/{id:\\d+}")
    public Map<String, Object> getStudentApplication(HoisUserDetails user,
            @WithEntity ScholarshipApplication application) {
        ScholarshipUtil.assertCanViewApplication(user, application);
        return scholarshipService.getApplicationView(application);
    }

    @GetMapping("/{id:\\d+}/application")
    public Map<String, Object> application(HoisUserDetails user, @WithEntity ScholarshipTerm term,
            @RequestParam(required = false) @WithEntity("student") Student student) {
        // user rights are checked in getStudentApplicationView method
        return scholarshipService.getStudentApplicationView(user, term, student);
    }

    @PostMapping("/{id:\\d+}/application")
    public ScholarshipApplicationDto saveApplication(HoisUserDetails user, @WithEntity ScholarshipTerm term,
            @Valid @RequestBody ScholarshipStudentApplicationForm form) {
        // user rights are checked in saveApplication method
        ScholarshipApplication application = scholarshipService.saveApplication(user, term, form);
        return scholarshipService.getStudentApplicationDto(application.getStudent(), term);
    }

    @PutMapping("/{id:\\d+}/application/{appId:\\d+}")
    public ScholarshipApplicationDto updateApplication(HoisUserDetails user, @WithEntity ScholarshipTerm term,
            @Valid @RequestBody ScholarshipStudentApplicationForm form,
            @WithEntity("appId") ScholarshipApplication application) {
        ScholarshipUtil.assertCanEditApplication(user, application);
        scholarshipService.updateApplication(user, form, application);
        return scholarshipService.getStudentApplicationDto(application.getStudent(), term);
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

    @DeleteMapping("/{id:\\d+}/deleteTerm")
    public void deleteTerm(HoisUserDetails user, @WithEntity ScholarshipTerm term) {
        UserUtil.assertIsSchoolAdmin(user, term.getSchool());
        scholarshipService.delete(term);
    }


    @GetMapping("/availableStipends")
    public List<ScholarshipTermStudentDto> availableStipends(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return scholarshipService.availableStipends(user.getStudentId());
    }

    @GetMapping("/availableDrGrants")
    public List<ScholarshipTermStudentDto> availableDrGrants(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return scholarshipService.availableDrGrants(user.getStudentId());
    }

    @GetMapping("/studentStipends")
    public List<ScholarshipApplicationStudentDto> studentStipends(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return scholarshipService.studentStipends(user.getStudentId());
    }

    @GetMapping("/studentDrGrants")
    public List<ScholarshipApplicationStudentDto> studentDrGrants(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        return scholarshipService.studentDrGrants(user.getStudentId());
    }

    @GetMapping("/schoolAvailableStipends")
    public List<AutocompleteResult> schoolAvailableStipends(HoisUserDetails user,
            @RequestParam("scholarshipType") String scholarshipType) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return scholarshipService.schoolAvailableStipends(user.getSchoolId(), scholarshipType);
    }

    @GetMapping("/stipendAvailableStudents")
    public Page<AutocompleteResult> stipendAvailableStudents(HoisUserDetails user,
            @RequestParam("termId") Long termId, SearchCommand lookup) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return scholarshipService.stipendAvailableStudents(user, termId, lookup);
    }

    @GetMapping("/studentUnappliedScholarships")
    public List<UnappliedScholarshipApplicationDto> unappliedScholarships(HoisUserDetails user) {
        return scholarshipService.unappliedScholarships(user);
    }

}