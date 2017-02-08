package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.domain.TeacherOccupation;
import ee.hitsa.ois.service.SchoolDepartmentService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.TeacherOccupationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolForm;
import ee.hitsa.ois.web.commandobject.SchoolSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyLevelsCommand;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.dto.SchoolDepartmentDto;
import ee.hitsa.ois.web.dto.SchoolDto;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.TeacherOccupationDto;


@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    SchoolService schoolService;
    @Autowired
    SchoolDepartmentService schoolDepartmentService;
    @Autowired
    TeacherOccupationService teacherOccupationService;

    @GetMapping(value = "")
    public Page<SchoolDto> search(@Valid SchoolSearchCommand schoolSearchCommand, Pageable pageable) {
        return schoolService.search(schoolSearchCommand, pageable);
    }

    @GetMapping(value = "/{id}")
    public SchoolDto get(@WithEntity("id") School school) {
        return SchoolDto.ofWithLogo(school);
    }

    @PostMapping(value = "")
    public SchoolDto create(@Valid @RequestBody SchoolForm schoolForm) {
        return get(schoolService.save(new School(), schoolForm));
    }

    @PutMapping(value = "/{id}")
    public SchoolDto update(@WithVersionedEntity(value = "id", versionRequestBody = true) School school, @Valid @RequestBody SchoolForm schoolForm) {
        return get(schoolService.save(school, schoolForm));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@WithVersionedEntity(value = "id", versionRequestParam = "version") School school, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        schoolService.delete(school);
    }

    @GetMapping(value = "/{id}/logo")
    public byte[] getLogo(@WithEntity("id") School school) {
        OisFile logo = school.getLogo();
        if (logo == null) {
            throw new EntityNotFoundException();
        }
        return logo.getFdata();
    }

    @GetMapping(value = "/all")
    public List<SchoolWithoutLogo> getAll() {
        return schoolService.findAll();
    }

    @GetMapping(value = "/studyLevels")
    public Map<String, ?> studyLevels(HoisUserDetails user) {
        School school = getSchool(user);
        Map<String, Object> response = new HashMap<>();
        response.put("id", school.getId());
        response.put("version", school.getVersion());
        response.put("nameEt", school.getNameEt());
        response.put("nameEn", school.getNameEn());
        response.put("studyLevels", school.getStudyLevels().stream().map(sl -> EntityUtil.getCode(sl.getStudyLevel())).collect(Collectors.toList()));
        return response;
    }

    @PutMapping(value = "/studyLevels")
    public Map<String, ?> updateStudyLevels(HoisUserDetails user, @Valid @RequestBody SchoolUpdateStudyLevelsCommand studyLevelsCmd) {
        School school = getSchool(user);
        EntityUtil.assertEntityVersion(school, studyLevelsCmd.getVersion());
        schoolService.updateStudyLevels(school, studyLevelsCmd.getStudyLevels());
        return studyLevels(user);
    }

    @GetMapping(value = "/departments")
    public Page<SchoolDepartmentDto> searchSchoolDepartment(HoisUserDetails user, @Valid SchoolDepartmentSearchCommand criteria, Pageable pageable) {
        return schoolDepartmentService.findAll(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping(value = "/departments/{id}")
    public SchoolDepartmentDto getSchoolDepartment(HoisUserDetails user, @WithEntity("id") SchoolDepartment schoolDepartment) {
        assertSameSchool(user, schoolDepartment.getSchool());
        return SchoolDepartmentDto.of(schoolDepartment);
    }

    @PostMapping(value = "/departments")
    public SchoolDepartmentDto createSchoolDepartment(HoisUserDetails user, @Valid @RequestBody SchoolDepartmentForm request) {
        SchoolDepartment schoolDepartment = EntityUtil.bindToEntity(request, new SchoolDepartment());
        schoolDepartment.setSchool(getSchool(user));
        return getSchoolDepartment(user, schoolDepartmentService.save(schoolDepartment, request.getParentSchoolDepartment()));
    }

    @PutMapping(value = "/departments/{id}")
    public SchoolDepartmentDto updateSchoolDepartment(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) SchoolDepartment schoolDepartment, @Valid @RequestBody SchoolDepartmentForm request) {
        assertSameSchool(user, schoolDepartment.getSchool());
        EntityUtil.bindToEntity(request, schoolDepartment);
        return getSchoolDepartment(user, schoolDepartmentService.save(schoolDepartment, request.getParentSchoolDepartment()));
    }

    @DeleteMapping(value = "/departments/{id}")
    public void deleteSchoolDepartment(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") SchoolDepartment schoolDepartment, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, schoolDepartment.getSchool());
        schoolDepartmentService.delete(schoolDepartment);
    }

    @GetMapping(value = "/teacheroccupations")
    public Page<TeacherOccupationDto> searchTeacherOccupation(HoisUserDetails user, @Valid TeacherOccupationSearchCommand criteria, Pageable pageable) {
        return teacherOccupationService.findAll(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping(value = "/teacheroccupations/{id}")
    public TeacherOccupationDto getTeacherOccupation(HoisUserDetails user, @WithEntity("id") TeacherOccupation teacherOccupation) {
        assertSameSchool(user, teacherOccupation.getSchool());
        return TeacherOccupationDto.of(teacherOccupation);
    }

    @GetMapping(value = "/teacheroccupations/all")
    public List<TeacherOccupationDto> getAllTeacherOccupations(HoisUserDetails user) {
        return teacherOccupationService.listAll(user.getSchoolId());
    }

    @PostMapping(value = "/teacheroccupations")
    public TeacherOccupationDto createTeacherOccupation(HoisUserDetails user, @Valid @RequestBody TeacherOccupationForm request) {
        TeacherOccupation teacherOccupation = EntityUtil.bindToEntity(request, new TeacherOccupation());
        teacherOccupation.setSchool(getSchool(user));
        return getTeacherOccupation(user, teacherOccupationService.save(teacherOccupation));
    }

    @PutMapping(value = "/teacheroccupations/{id}")
    public TeacherOccupationDto updateTeacherOccupation(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) TeacherOccupation teacherOccupation, @Valid @RequestBody TeacherOccupationForm request) {
        assertSameSchool(user, teacherOccupation.getSchool());
        return getTeacherOccupation(user, teacherOccupationService.save(EntityUtil.bindToEntity(request, teacherOccupation)));
    }

    @DeleteMapping(value = "/teacheroccupations/{id}")
    public void deleteTeacherOccupation(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") TeacherOccupation teacherOccupation, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertSameSchool(user, teacherOccupation.getSchool());
        teacherOccupationService.delete(teacherOccupation);
    }

    private School getSchool(HoisUserDetails user) {
        return schoolService.getOne(user.getSchoolId());
    }

    private static void assertSameSchool(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        if(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(school))) {
            throw new IllegalArgumentException();
        }
    }
}
