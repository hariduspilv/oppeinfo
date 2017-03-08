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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.SchoolDepartmentService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.TeacherOccupationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolForm;
import ee.hitsa.ois.web.commandobject.SchoolSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyLevelsCommand;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.dto.SchoolDepartmentDto;
import ee.hitsa.ois.web.dto.SchoolDto;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.StudyYearsSearchDto;
import ee.hitsa.ois.web.dto.TeacherOccupationDto;


@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolDepartmentService schoolDepartmentService;
    @Autowired
    private TeacherOccupationService teacherOccupationService;
    @Autowired
    private StudyYearService studyYearService;

    @GetMapping("")
    public Page<SchoolDto> search(@Valid SchoolSearchCommand schoolSearchCommand, Pageable pageable) {
        return schoolService.search(schoolSearchCommand, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SchoolDto get(@WithEntity("id") School school) {
        return SchoolDto.ofWithLogo(school);
    }

    @PostMapping("")
    public SchoolDto create(@Valid @RequestBody SchoolForm schoolForm) {
        return get(schoolService.save(new School(), schoolForm));
    }

    @PutMapping("/{id:\\d+}")
    public SchoolDto update(@WithVersionedEntity(value = "id", versionRequestBody = true) School school, @Valid @RequestBody SchoolForm schoolForm) {
        return get(schoolService.save(school, schoolForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@WithVersionedEntity(value = "id", versionRequestParam = "version") School school, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        schoolService.delete(school);
    }

    @GetMapping("/{id:\\d+}/logo")
    public byte[] getLogo(@PathVariable("id") String schoolId) {
        OisFile logo = schoolRepository.findSchoolLogo(Long.valueOf(schoolId));
        if (logo == null) {
            throw new EntityNotFoundException();
        }
        return logo.getFdata();
    }

    @GetMapping("/studyLevels")
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

    @PutMapping("/studyLevels")
    public Map<String, ?> updateStudyLevels(HoisUserDetails user, @Valid @RequestBody SchoolUpdateStudyLevelsCommand studyLevelsCmd) {
        School school = getSchool(user);
        EntityUtil.assertEntityVersion(school, studyLevelsCmd.getVersion());
        schoolService.updateStudyLevels(school, studyLevelsCmd.getStudyLevels());
        return studyLevels(user);
    }

    @GetMapping("/departments")
    public Page<SchoolDepartmentDto> searchSchoolDepartment(HoisUserDetails user, @Valid SchoolDepartmentSearchCommand criteria, Pageable pageable) {
        return schoolDepartmentService.findAll(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/departments/{id:\\d+}")
    public SchoolDepartmentDto getSchoolDepartment(HoisUserDetails user, @WithEntity("id") SchoolDepartment schoolDepartment) {
        UserUtil.assertSameSchool(user, schoolDepartment.getSchool());
        return SchoolDepartmentDto.of(schoolDepartment);
    }

    @PostMapping("/departments")
    public SchoolDepartmentDto createSchoolDepartment(HoisUserDetails user, @Valid @RequestBody SchoolDepartmentForm request) {
        SchoolDepartment schoolDepartment = EntityUtil.bindToEntity(request, new SchoolDepartment());
        schoolDepartment.setSchool(getSchool(user));
        return getSchoolDepartment(user, schoolDepartmentService.save(schoolDepartment, request.getParentSchoolDepartment()));
    }

    @PutMapping("/departments/{id:\\d+}")
    public SchoolDepartmentDto updateSchoolDepartment(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) SchoolDepartment schoolDepartment, @Valid @RequestBody SchoolDepartmentForm request) {
        UserUtil.assertSameSchool(user, schoolDepartment.getSchool());
        EntityUtil.bindToEntity(request, schoolDepartment);
        return getSchoolDepartment(user, schoolDepartmentService.save(schoolDepartment, request.getParentSchoolDepartment()));
    }

    @DeleteMapping("/departments/{id:\\d+}")
    public void deleteSchoolDepartment(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") SchoolDepartment schoolDepartment, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, schoolDepartment.getSchool());
        schoolDepartmentService.delete(schoolDepartment);
    }

    @GetMapping("/teacheroccupations")
    public Page<TeacherOccupationDto> searchTeacherOccupation(HoisUserDetails user, @Valid TeacherOccupationSearchCommand criteria, Pageable pageable) {
        return teacherOccupationService.findAll(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/teacheroccupations/{id:\\d+}")
    public TeacherOccupationDto getTeacherOccupation(HoisUserDetails user, @WithEntity("id") TeacherOccupation teacherOccupation) {
        UserUtil.assertSameSchool(user, teacherOccupation.getSchool());
        return TeacherOccupationDto.of(teacherOccupation);
    }

    @GetMapping("/teacheroccupations/all")
    public List<TeacherOccupationDto> getAllTeacherOccupations(HoisUserDetails user) {
        return teacherOccupationService.listAll(user.getSchoolId());
    }

    @PostMapping("/teacheroccupations")
    public TeacherOccupationDto createTeacherOccupation(HoisUserDetails user, @Valid @RequestBody TeacherOccupationForm request) {
        TeacherOccupation teacherOccupation = EntityUtil.bindToEntity(request, new TeacherOccupation());
        teacherOccupation.setSchool(getSchool(user));
        return getTeacherOccupation(user, teacherOccupationService.save(teacherOccupation));
    }

    @PutMapping("/teacheroccupations/{id:\\d+}")
    public TeacherOccupationDto updateTeacherOccupation(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) TeacherOccupation teacherOccupation, @Valid @RequestBody TeacherOccupationForm request) {
        UserUtil.assertSameSchool(user, teacherOccupation.getSchool());
        return getTeacherOccupation(user, teacherOccupationService.save(EntityUtil.bindToEntity(request, teacherOccupation)));
    }

    @DeleteMapping("/teacheroccupations/{id:\\d+}")
    public void deleteTeacherOccupation(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") TeacherOccupation teacherOccupation, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, teacherOccupation.getSchool());
        teacherOccupationService.delete(teacherOccupation);
    }

    @GetMapping("/studyYears")
    public List<StudyYearsSearchDto> getAllStudyYears(HoisUserDetails user) {
        return studyYearService.getStudyYears(user.getSchoolId());
    }

    @GetMapping("/studyYears/{id:\\d+}")
    public StudyYearDto getStudyYear(HoisUserDetails user, @WithEntity("id") StudyYear studyYear) {
        return StudyYearDto.of(studyYear);
    }

    @PostMapping("/studyYears")
    public StudyYearDto createStudyYear(HoisUserDetails user, @Valid @RequestBody StudyYearForm studyYearForm) {
        return studyYearService.save(getSchool(user), new StudyYear(), studyYearForm);
    }

    @PutMapping("/studyYears/{id:\\d+}")
    public StudyYearDto updateStudyYear(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") StudyYear studyYear, @Valid @RequestBody StudyYearForm request) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return studyYearService.save(getSchool(user), studyYear, request);
    }

    private School getSchool(HoisUserDetails user) {
        return schoolService.getOne(user.getSchoolId());
    }
}
