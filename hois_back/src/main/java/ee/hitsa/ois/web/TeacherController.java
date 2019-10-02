package ee.hitsa.ois.web;

import static ee.hitsa.ois.util.UserUtil.assertIsSchoolAdmin;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.UUID;

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
import ee.hitsa.ois.domain.teacher.TeacherContinuingEducation;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.TeacherOccupationService;
import ee.hitsa.ois.service.TeacherService;
import ee.hitsa.ois.service.ehis.EhisTeacherExportService;
import ee.hitsa.ois.service.ehis.EhisTeacherExportService.ExportTeacherRequest;
import ee.hitsa.ois.service.rtip.RtipService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.HttpUtil.NoContentResponse;
import ee.hitsa.ois.util.TeacherUserRights;
import ee.hitsa.ois.util.TeacherUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.commandobject.ehis.EhisTeacherExportForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherContinuingEducationFormWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityFormWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationFromWrapper;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EhisTeacherRequestDto;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
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
        TeacherUserRights.assertCanView(user, teacher);
        return teacherService.get(user, teacher);
    }

    @GetMapping
    public Page<TeacherSearchDto> search(TeacherSearchCommand command, Pageable pageable, HoisUserDetails user) {
        TeacherUserRights.assertCanSearch(user);
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
        TeacherUserRights.assertCanView(user, teacher);
        return teacherService.teacherAbsences(teacher, pageable);
    }

    @PostMapping
    public TeacherDto create(@Valid @RequestBody TeacherForm teacherForm, HoisUserDetails user) {
        TeacherUserRights.assertCanCreate(user);
        return teacherService.create(user, teacherForm);
    }

    @PutMapping("/{id:\\d+}")
    public TeacherDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        if(user.isTeacher()) {
            TeacherUserRights.assertCanEditAsTeacher(user, teacher);
            return teacherService.saveAsTeacher(user, teacher, teacherForm);
        }
        TeacherUserRights.assertCanEdit(user, teacher);
        return teacherService.save(user, teacher, teacherForm);
    }

    @PutMapping("/{id:\\d+}/sendToEhis")
    public TeacherDto sendToEhis(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) Teacher teacher, @Valid @RequestBody TeacherForm teacherForm) {
        TeacherUserRights.assertCanEdit(user, teacher);
        return teacherService.sendToEhis(user, teacher, teacherForm);
    }

    @PostMapping("/{id:\\d+}/rtip")
    public NoContentResponse rtip(HoisUserDetails user, @WithEntity Teacher teacher) {
        TeacherUserRights.assertCanEdit(user, teacher);
        rtipService.syncTeacher(teacher);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Teacher teacher,  @SuppressWarnings("unused") @RequestParam("version") Long version) {
        TeacherUserRights.assertCanEdit(user, teacher);
        teacherService.delete(user, teacher);
    }

    @PutMapping("/{id:\\d+}/continuingEducations")
    public TeacherDto saveContinuingEducations(HoisUserDetails user, @WithEntity Teacher teacher, @Valid @RequestBody TeacherContinuingEducationFormWrapper teacherContinuingEducationForms) {
        TeacherUserRights.assertCanTeacherAndAdminEdit(user, teacher);
        return teacherService.saveContinuingEducations(user, teacher, teacherContinuingEducationForms.getContinuingEducations());
    }

    @PutMapping("/{id:\\d+}/qualifications")
    public TeacherDto saveQualifications(HoisUserDetails user, @WithEntity Teacher teacher, @Valid @RequestBody TeacherQualificationFromWrapper teacherQualificationFroms) {
        TeacherUserRights.assertCanEdit(user, teacher);
        return teacherService.saveQualifications(user, teacher, teacherQualificationFroms.getQualifications());
    }

    @PutMapping("/{id:\\d+}/mobilities")
    public TeacherDto saveMobilities(HoisUserDetails user, @WithEntity Teacher teacher, @Valid @RequestBody TeacherMobilityFormWrapper mobilityForms) {
        TeacherUserRights.assertCanEdit(user, teacher);
        return teacherService.saveMobilities(user, teacher, mobilityForms.getMobilities());
    }

    @DeleteMapping("/{teacherId:\\d+}/continuingEducations/{id:\\d+}")
    public void deleteContinuingEducation(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherContinuingEducation continuingEducation) {
        TeacherUserRights.assertCanTeacherAndAdminEdit(user, teacher);
        TeacherUtil.assertContinuingEducationBelongsToTeacher(continuingEducation, teacher);
        teacherService.delete(user, continuingEducation);
    }

    @DeleteMapping("/{teacherId:\\d+}/qualifications/{id:\\d+}")
    public void deleteQualification(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherQualification qualification) {
        UserUtil.assertSameSchool(user, teacher.getSchool());
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPETAJA)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        TeacherUtil.assertQualificationBelongsToTeacher(qualification, teacher);
        teacherService.delete(user, qualification);
    }

    @DeleteMapping("/{teacherId:\\d+}/mobilities/{id:\\d+}")
    public void deleteMobilities(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherMobility teacherMobility) {
        TeacherUserRights.assertCanEdit(user, teacher);
        TeacherUtil.assertMobilityBelongsToTeacher(teacherMobility, teacher);
        teacherService.delete(user, teacherMobility);
    }

    @DeleteMapping("/{teacherId:\\d+}/ehisPositions/{id:\\d+}")
    public void deleteEhisPosition(HoisUserDetails user, @WithEntity("teacherId") Teacher teacher, @WithEntity TeacherPositionEhis teacherPositionEhis) {
        TeacherUserRights.assertCanEdit(user, teacher);
        TeacherUtil.assertEhisPositionBelongsToTeacher(teacherPositionEhis, teacher);
        teacherService.delete(user, teacherPositionEhis);
    }

    @PostMapping("/exportToEhis/higher")
    public SimpleEntry<String, String> exportToEhisHigher(@Valid @RequestBody EhisTeacherExportForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_EHIS);
        String key = UUID.randomUUID().toString();
        ehisTeacherExportService.exportTeachers(user, true, form, key);
        return new SimpleEntry<>("key", key);
    }

    @PostMapping("/exportToEhis/vocational")
    public SimpleEntry<String, String> exportToEhisVocational(@Valid @RequestBody EhisTeacherExportForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_EHIS);
        String key = UUID.randomUUID().toString();
        ehisTeacherExportService.exportTeachers(user, false, form, key);
        return new SimpleEntry<>("key", key);
    }
    
    @GetMapping("/ehisTeacherExportCheck")
    public EhisTeacherRequestDto ehisStudentExportCheck(HoisUserDetails user, @Valid EhisTeacherExportForm form) {
        assertIsSchoolAdmin(user);
        ExportTeacherRequest overlappedRequest = ehisTeacherExportService.findOverlappedActiveExportTeacherRequest(user, form);
        return overlappedRequest != null && !overlappedRequest.isDone() ? EhisTeacherRequestDto.of(overlappedRequest) : null;
    }

    @GetMapping("/ehisTeacherExportStatus")
    public FutureStatusResponse ehisStudentExportStatus(HoisUserDetails user, @RequestParam(required = true) String key) {
        assertIsSchoolAdmin(user);
        return ehisTeacherExportService.exporTeacherStatus(user, key);
    }
}
