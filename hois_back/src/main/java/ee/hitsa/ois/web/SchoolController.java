package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.EmailGeneratorService;
import ee.hitsa.ois.service.SchoolDepartmentService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.TeacherOccupationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.GenerateEmailCommand;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolForm;
import ee.hitsa.ois.web.commandobject.SchoolSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyLevelsCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyYearScheduleLegendsCommand;
import ee.hitsa.ois.web.commandobject.StudyPeriodEventForm;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.dto.SchoolDepartmentDto;
import ee.hitsa.ois.web.dto.SchoolDto;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.StudyPeriodEventDto;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.TeacherOccupationDto;


@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private EmailGeneratorService emailGeneratorService;
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

    @GetMapping
    public Page<SchoolDto> search(@Valid SchoolSearchCommand schoolSearchCommand, Pageable pageable) {
        return schoolService.search(schoolSearchCommand, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public SchoolDto get(@WithEntity("id") School school) {
        return SchoolDto.ofWithLogo(school);
    }

    @PostMapping
    public SchoolDto create(@Valid @RequestBody SchoolForm schoolForm) {
        return get(schoolService.create(schoolForm));
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
        response.put("studyLevels", StreamUtil.toMappedList(sl -> EntityUtil.getCode(sl.getStudyLevel()), school.getStudyLevels()));
        return response;
    }

    @PutMapping("/studyLevels")
    public Map<String, ?> updateStudyLevels(HoisUserDetails user, @Valid @RequestBody SchoolUpdateStudyLevelsCommand studyLevelsCmd) {
        School school = getSchool(user);
        EntityUtil.assertEntityVersion(school, studyLevelsCmd.getVersion());
        schoolService.updateStudyLevels(school, studyLevelsCmd);
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
    public HttpUtil.CreatedResponse createSchoolDepartment(HoisUserDetails user, @Valid @RequestBody SchoolDepartmentForm form) {
        return HttpUtil.created(schoolDepartmentService.create(user, form));
    }

    @PutMapping("/departments/{id:\\d+}")
    public SchoolDepartmentDto updateSchoolDepartment(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) SchoolDepartment schoolDepartment, @Valid @RequestBody SchoolDepartmentForm form) {
        UserUtil.assertSameSchool(user, schoolDepartment.getSchool());
        return getSchoolDepartment(user, schoolDepartmentService.save(schoolDepartment, form));
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
    public HttpUtil.CreatedResponse createTeacherOccupation(HoisUserDetails user, @Valid @RequestBody TeacherOccupationForm form) {
        return HttpUtil.created(teacherOccupationService.create(user, form));
    }

    @PutMapping("/teacheroccupations/{id:\\d+}")
    public TeacherOccupationDto updateTeacherOccupation(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) TeacherOccupation teacherOccupation, @Valid @RequestBody TeacherOccupationForm form) {
        UserUtil.assertSameSchool(user, teacherOccupation.getSchool());
        return getTeacherOccupation(user, teacherOccupationService.save(teacherOccupation, form));
    }

    @DeleteMapping("/teacheroccupations/{id:\\d+}")
    public void deleteTeacherOccupation(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") TeacherOccupation teacherOccupation, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, teacherOccupation.getSchool());
        teacherOccupationService.delete(teacherOccupation);
    }

    @GetMapping("/studyYears")
    public List<StudyYearSearchDto> getAllStudyYears(HoisUserDetails user) {
        return studyYearService.getStudyYears(user.getSchoolId());
    }

    @GetMapping("/studyYears/{id:\\d+}")
    public StudyYearDto getStudyYear(@WithEntity("id") StudyYear studyYear) {
        return StudyYearDto.of(studyYear);
    }

    @PostMapping("/studyYears")
    public StudyYearDto createStudyYear(HoisUserDetails user, @Valid @RequestBody StudyYearForm studyYearForm) {
        return getStudyYear(studyYearService.create(user, studyYearForm));
    }

    @PutMapping("/studyYears/{id:\\d+}")
    public StudyYearDto updateStudyYear(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) StudyYear studyYear, @Valid @RequestBody StudyYearForm request) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return getStudyYear(studyYearService.save(studyYear, request));
    }

    @PostMapping("/studyYears/{id:\\d+}/studyPeriods")
    public StudyPeriodDto createStudyPeriod(HoisUserDetails user, @WithEntity("id") StudyYear studyYear, @Valid @RequestBody StudyPeriodForm request) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return get(studyYearService.createStudyPeriod(studyYear, request));
    }

    @PutMapping("/studyYears/{year:\\d+}/studyPeriods/{id:\\d+}")
    public StudyPeriodDto updateStudyPeriod(HoisUserDetails user, @WithEntity("year") StudyYear studyYear, @WithVersionedEntity(value = "id", versionRequestBody = true) StudyPeriod studyPeriod, @Valid @RequestBody StudyPeriodForm request) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return get(studyYearService.saveStudyPeriod(studyYear, studyPeriod, request));
    }

    @DeleteMapping("/studyYears/{year:\\d+}/studyPeriods/{id:\\d+}")
    public void deleteStudyPeriod(HoisUserDetails user, @WithEntity("year") StudyYear studyYear, @WithEntity("id") StudyPeriod studyPeriod) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        if (!EntityUtil.getId(studyYear).equals(EntityUtil.getId(studyPeriod.getStudyYear()))) {
            throw new AssertionFailedException("Study year mismatch");
        }
        studyYearService.delete(studyPeriod);
    }

    @PostMapping("/studyYears/{id:\\d+}/studyPeriodEvents")
    public StudyPeriodEventDto createStudyPeriodEvent(HoisUserDetails user, @WithEntity("id") StudyYear studyYear, @Valid @RequestBody StudyPeriodEventForm request) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return get(studyYearService.create(studyYear, request));
    }

    @PutMapping("/studyYears/{year:\\d+}/studyPeriodEvents/{id:\\d+}")
    public StudyPeriodEventDto updateStudyPeriodEvent(HoisUserDetails user, @WithEntity("year") StudyYear studyYear, @WithVersionedEntity(value = "id", versionRequestBody = true) StudyPeriodEvent studyPeriodEvent, @Valid @RequestBody StudyPeriodEventForm request) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return get(studyYearService.save(studyYear, studyPeriodEvent, request));
    }

    @DeleteMapping("/studyYears/{year:\\d+}/studyPeriodEvents/{id:\\d+}")
    public void deleteStudyPeriodEvent(HoisUserDetails user, @WithEntity("year") StudyYear studyYear, @WithEntity("id") StudyPeriodEvent studyPeriodEvent) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        if (!EntityUtil.getId(studyYear).equals(EntityUtil.getId(studyPeriodEvent.getStudyYear()))) {
            throw new AssertionFailedException("Study year mismatch");
        }
        studyYearService.delete(studyPeriodEvent);
    }

    @GetMapping("/studyYearScheduleLegends")
    public Map<String, ?> studyYearScheduleLegends(HoisUserDetails user) {
        School school = getSchool(user);
        Map<String, Object> response = new HashMap<>();
        response.put("legends", StreamUtil.toMappedList(StudyYearScheduleLegendDto::of, school.getStudyYearScheduleLegends()));
        return response;
    }

    @PutMapping("/studyYearScheduleLegends")
    public Map<String, ?> updateStudyYearScheduleLegends(HoisUserDetails user, @Valid @RequestBody SchoolUpdateStudyYearScheduleLegendsCommand legendsCmd) {
        School school = getSchool(user);
        schoolService.updateLegends(school, legendsCmd);
        return studyYearScheduleLegends(user);
    }

    @GetMapping("/studyPeriod/current")
    public Map<String, ?> getCurrentStudyPeriod(HoisUserDetails user) {
        Long studyPeriod = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
        Map<String, Object> response = new HashMap<>();
        response.put("currentStudyPeriod", studyPeriod);
        return response;
    }

    @PostMapping("/generateEmail")
    public Map<String, ?> generateEmail(HoisUserDetails user, @Valid @RequestBody GenerateEmailCommand name) {
        UserUtil.assertIsSchoolAdmin(user);
        return Collections.singletonMap("email", emailGeneratorService.generateEmail(getSchool(user), name.getFirstname(), name.getLastname()));
    }

    private School getSchool(HoisUserDetails user) {
        return schoolRepository.getOne(user.getSchoolId());
    }

    private static StudyPeriodDto get(StudyPeriod studyPeriod) {
        return StudyPeriodDto.of(studyPeriod);
    }

    private static StudyPeriodEventDto get(StudyPeriodEvent studyPeriodEvent) {
        return StudyPeriodEventDto.of(studyPeriodEvent);
    }
}
