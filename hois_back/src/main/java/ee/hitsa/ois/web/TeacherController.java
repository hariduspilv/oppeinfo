package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.service.TeacherOccupationService;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.ehis.EhisTeacherExportService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.TeacherUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.EhisTeacherExportForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityFormWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationFromWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EhisTeacherExportResultDto;
import ee.hitsa.ois.web.dto.TeacherDto;
import ee.hitsa.ois.web.dto.TeacherSearchDto;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherOccupationService teacherOccupationService;
    @Autowired
    private EhisTeacherExportService ehisTeacherExportService;

    @GetMapping("/{id:\\d+}")
    public TeacherDto get(@WithEntity("id") Teacher teacher) {
        return TeacherDto.of(teacher);
    }

    @GetMapping
    public Page<TeacherSearchDto> search(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        if (!user.isExternalExpert()) {
            command.setSchool(user.getSchoolId());
        }
        return teacherService.search(command, pageable);
    }
    
    /**
     * Options for search form
     */
    @GetMapping("/teacheroccupations")
    public List<AutocompleteResult> teacherOccupations(HoisUserDetails user) {
        TeacherOccupationSearchCommand command = new TeacherOccupationSearchCommand();
        command.setIsValid(Boolean.TRUE);
        return teacherOccupationService.findAll(user.getSchoolId(), command, new PageRequest(0, Integer.MAX_VALUE)).map(r -> {
            return new AutocompleteResult(r.getId(), r.getOccupationEt(), r.getOccupationEn());
        }).getContent();
    }

    @PostMapping
    public TeacherDto create(@Valid @RequestBody TeacherForm teacherForm, HoisUserDetails user) {
        return teacherService.create(user, teacherForm);
    }

    @PutMapping("/{id:\\d+}")
    public TeacherDto save(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        return teacherService.save(user, teacher, teacherForm);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Teacher teacher,  @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        teacherService.delete(teacher);
    }

    @PutMapping("/{id:\\d+}/qualifications")
    public TeacherDto saveQualifications(HoisUserDetails user, @WithEntity(value = "id") Teacher teacher, @Valid @RequestBody TeacherQualificationFromWrapper teacherQualificationFroms) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        return teacherService.saveQualifications(teacher, teacherQualificationFroms.getQualifications());
    }

    @PutMapping("/{id:\\d+}/mobilities")
    public TeacherDto saveMobilities(HoisUserDetails user, @WithEntity(value = "id") Teacher teacher, @Valid @RequestBody TeacherMobilityFormWrapper mobilityForms) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        return teacherService.saveMobilities(teacher, mobilityForms.getMobilities());
    }

    @DeleteMapping("/{teacherId:\\d+}/qualifications/{id:\\d+}")
    public void deleteQualification(HoisUserDetails user, @WithEntity(value = "teacherId") Teacher teacher, @WithEntity(value = "id") TeacherQualification qualification) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        TeacherUtil.assertQualificationBelongsToTeacher(qualification, teacher);
        teacherService.delete(qualification);
    }

    @DeleteMapping("/{teacherId:\\d+}/mobilities/{id:\\d+}")
    public void deleteMobilities(HoisUserDetails user, @WithEntity(value = "teacherId") Teacher teacher, @WithEntity(value = "id")TeacherMobility teacherMobility) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        TeacherUtil.assertMobilityBelongsToTeacher(teacherMobility, teacher);
        teacherService.delete(teacherMobility);
    }

    @DeleteMapping("/{teacherId:\\d+}/ehisPositions/{id:\\d+}")
    public void deleteEhisPosition(HoisUserDetails user, @WithEntity(value = "teacherId") Teacher teacher, @WithEntity(value = "id")TeacherPositionEhis teacherPositionEhis) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        TeacherUtil.assertEhisPositionBelongsToTeacher(teacherPositionEhis, teacher);
        teacherService.delete(teacherPositionEhis);
    }
    
    @PostMapping("/ehisTeacherExport")
    public List<EhisTeacherExportResultDto> ehisTeacherExport(@Valid @RequestBody EhisTeacherExportForm form, HoisUserDetails user) {
        return ehisTeacherExportService.exportToEhis(form, user);
    }
}
