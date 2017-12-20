package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherContinuingEducation;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.service.TeacherOccupationService;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.ehis.EhisTeacherExportService;
import ee.hitsa.ois.service.rtip.RtipService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.HttpUtil.NoContentResponse;
import ee.hitsa.ois.util.TeacherUserRightsValidator;
import ee.hitsa.ois.util.TeacherUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.commandobject.ehis.EhisTeacherExportForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherContinuingEducationFormWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityFormWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationFromWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EhisTeacherExportResultDto;
import ee.hitsa.ois.web.dto.TeacherAbsenceDto;
import ee.hitsa.ois.web.dto.TeacherDto;
import ee.hitsa.ois.web.dto.TeacherSearchDto;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private RtipService rtipService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherOccupationService teacherOccupationService;
    @Autowired
    private EhisTeacherExportService ehisTeacherExportService;

    @GetMapping("/{id:\\d+}")
    public TeacherDto get(HoisUserDetails user, @WithEntity Teacher teacher) {
        return teacherService.get(user, teacher);
    }

    @GetMapping
    public Page<TeacherSearchDto> search(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        if (!user.isExternalExpert()) {
            command.setSchool(user.getSchoolId());
        }
        return teacherService.search(user, command, pageable);
    }

    /**
     * Options for search form
     */
    @GetMapping("/teacheroccupations")
    public List<AutocompleteResult> teacherOccupations(HoisUserDetails user) {
        TeacherOccupationSearchCommand command = new TeacherOccupationSearchCommand();
        command.setIsValid(Boolean.TRUE);
        return teacherOccupationService.search(user.getSchoolId(), command, new PageRequest(0, Integer.MAX_VALUE)).map(r -> {
            return new AutocompleteResult(r.getId(), r.getOccupationEt(), r.getOccupationEn());
        }).getContent();
    }
    
    @GetMapping("/{id:\\d+}/absences")
    public Page<TeacherAbsenceDto> teacherAbsences(HoisUserDetails user, @WithEntity Teacher teacher, Pageable pageable) {
        TeacherUserRightsValidator.assertCanView(user, teacher);        
        return teacherService.teacherAbsences(teacher, pageable);
    }

    @PostMapping
    public TeacherDto create(@Valid @RequestBody TeacherForm teacherForm, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return teacherService.create(user, teacherForm);
    }

    @PutMapping("/{id:\\d+}")
    public TeacherDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        return teacherService.save(user, teacher, teacherForm);
    }

    @PutMapping("/{id:\\d+}/sendToEhis")
    public TeacherDto sendToEhis(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        return teacherService.sendToEhis(user, teacher, teacherForm);
    }

    @PostMapping("/{id:\\d+}/rtip")
    public NoContentResponse rtip(HoisUserDetails user, @WithEntity Teacher teacher) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        rtipService.syncTeacher(teacher);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Teacher teacher,  @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        teacherService.delete(user, teacher);
    }

    @PutMapping("/{id:\\d+}/continuingEducations")
    public TeacherDto saveContinuingEducations(HoisUserDetails user, @WithEntity Teacher teacher, @Valid @RequestBody TeacherContinuingEducationFormWrapper teacherContinuingEducationForms) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        return teacherService.saveContinuingEducations(teacher, teacherContinuingEducationForms.getContinuingEducations());
    }

    @PutMapping("/{id:\\d+}/qualifications")
    public TeacherDto saveQualifications(HoisUserDetails user, @WithEntity Teacher teacher, @Valid @RequestBody TeacherQualificationFromWrapper teacherQualificationFroms) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        return teacherService.saveQualifications(teacher, teacherQualificationFroms.getQualifications());
    }

    @PutMapping("/{id:\\d+}/mobilities")
    public TeacherDto saveMobilities(HoisUserDetails user, @WithEntity Teacher teacher, @Valid @RequestBody TeacherMobilityFormWrapper mobilityForms) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        return teacherService.saveMobilities(teacher, mobilityForms.getMobilities());
    }

    @DeleteMapping("/{teacherId:\\d+}/continuingEducations/{id:\\d+}")
    public void deleteContinuingEducation(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherContinuingEducation continuingEducation) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        TeacherUtil.assertContinuingEducationBelongsToTeacher(continuingEducation, teacher);
        teacherService.delete(user, continuingEducation);
    }

    @DeleteMapping("/{teacherId:\\d+}/qualifications/{id:\\d+}")
    public void deleteQualification(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherQualification qualification) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        TeacherUtil.assertQualificationBelongsToTeacher(qualification, teacher);
        teacherService.delete(user, qualification);
    }

    @DeleteMapping("/{teacherId:\\d+}/mobilities/{id:\\d+}")
    public void deleteMobilities(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherMobility teacherMobility) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        TeacherUtil.assertMobilityBelongsToTeacher(teacherMobility, teacher);
        teacherService.delete(user, teacherMobility);
    }

    @DeleteMapping("/{teacherId:\\d+}/ehisPositions/{id:\\d+}")
    public void deleteEhisPosition(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherPositionEhis teacherPositionEhis) {
        UserUtil.assertIsSchoolAdmin(user, teacher.getSchool());
        TeacherUtil.assertEhisPositionBelongsToTeacher(teacherPositionEhis, teacher);
        teacherService.delete(user, teacherPositionEhis);
    }

    @PostMapping("/exportToEhis/higher")
    public List<EhisTeacherExportResultDto> exportToEhisHigher(@Valid @RequestBody EhisTeacherExportForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return ehisTeacherExportService.exportToEhis(user.getSchoolId(), true, form);
    }

    @PostMapping("/exportToEhis/vocational")
    public List<EhisTeacherExportResultDto> exportToEhisVocational(@Valid @RequestBody EhisTeacherExportForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return ehisTeacherExportService.exportToEhis(user.getSchoolId(), false, form);
    }
}
