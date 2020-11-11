package ee.hitsa.ois.web.subjectStudyPeriod;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodStudentGroupSearchService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodStudentGroupService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.CurriculumProgramDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodStudentGroupSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

@RestController
@RequestMapping("/subjectStudyPeriods/studentGroups")
public class SubjectStudyPeriodStudentGroupController {

    @Autowired
    private SubjectStudyPeriodStudentGroupSearchService subjectStudyPeriodStudentGroupSearchService;

    @Autowired
    private SubjectStudyPeriodStudentGroupService subjectStudyPeriodStudentGroupService;

    @Autowired
    private SubjectStudyPeriodCapacitiesService subjectStudyPeriodCapacitiesService;

    @GetMapping
    public Page<SubjectStudyPeriodStudentGroupSearchDto> searchByStudentGroup(HoisUserDetails user,
            SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOORM);
        return subjectStudyPeriodStudentGroupSearchService.searchByStudentGroup(user.getSchoolId(), criteria, pageable);
    }
    
    @GetMapping("/workloadsummary.xls")
    public void summaryLessonplanAsExcelVocational(HoisUserDetails user, @Valid SubjectStudyPeriodSearchCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOORM);
        HttpUtil.xls(response, "workloadsummary.xls", subjectStudyPeriodStudentGroupSearchService.teacherCapacitiesAsExcel(criteria, user));
    }

    @GetMapping("/container")
    public SubjectStudyPeriodDtoContainer getStudentGroupsSspContainer(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container) {
        AssertionFailedException.throwIf(container.getStudentGroup() == null,
                "StudentGroup must be specified");
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOORM);
        subjectStudyPeriodStudentGroupService.setSubjectStudyPeriodsToStudentGroupsContainer(user.getSchoolId(), container);
        subjectStudyPeriodCapacitiesService.setSubjects(container);
        subjectStudyPeriodStudentGroupService.setSubjectStudyPeriodPlansToStudentGroupContainer(container);
        subjectStudyPeriodCapacitiesService.setCapacityTypes(user.getSchoolId(), container);
        return container;
    }

    @PutMapping("/container")
    public SubjectStudyPeriodDtoContainer updateStudentGroupsSspCapacities(HoisUserDetails user, @Valid @RequestBody SubjectStudyPeriodDtoContainer container) {
        UserUtil.assertIsSchoolAdmin(user);
        AssertionFailedException.throwIf(container.getStudentGroup() == null,
                "StudentGroup must be specified");
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KOORM);
        subjectStudyPeriodCapacitiesService.updateSspCapacities(user.getSchoolId(), container);
        return getStudentGroupsSspContainer(user, container);
    }

    @GetMapping("/list")
    public List<StudentGroupSearchDto> getStudentGroupsList(HoisUserDetails user) {
        return subjectStudyPeriodStudentGroupService.getStudentGroupsList(user.getSchoolId(), null);
    }

    @GetMapping("/list/limited/{studyPeriodId:\\d+}")
    public List<StudentGroupSearchDto> getStudentGroupsFilteredList(HoisUserDetails user, @PathVariable("studyPeriodId") Long studyPeriodId) {
        return subjectStudyPeriodStudentGroupService.getStudentGroupsList(user.getSchoolId(), studyPeriodId);
    }

    @GetMapping("/curriculum/{id:\\d+}")
    public CurriculumDto getCurriculum(HoisUserDetails user, @WithEntity Curriculum curriculum) {
        UserUtil.assertSameSchool(user, curriculum.getSchool());
        CurriculumDto dto = new CurriculumDto();
        dto.setId(EntityUtil.getId(curriculum));
        dto.setNameEt(curriculum.getNameEt());
        dto.setNameEn(curriculum.getNameEn());
        dto.setStudyPeriod(curriculum.getStudyPeriod());
        dto.setCode(curriculum.getCode());
        return dto;
    }

    @GetMapping("/curricula")
    public List<CurriculumSearchDto> getCurricula(HoisUserDetails user) {
        return subjectStudyPeriodStudentGroupService.getCurricula(user.getSchoolId());
    }
    
    @GetMapping("/curriculumProgram/{studyPeriod:\\d+}/{studentGroup:\\d+}")
    public Map<Short, List<CurriculumProgramDto>> getCurriculumProgram(HoisUserDetails user, @WithEntity("studentGroup") StudentGroup group,
            @WithEntity("studyPeriod") StudyPeriod period) {
        UserUtil.assertIsSchoolAdmin(user, group.getSchool());
        UserUtil.assertSameSchool(user, period.getStudyYear().getSchool());
        return subjectStudyPeriodStudentGroupService.getCurriculumProgram(group, period);
    }
    
    @GetMapping("/connect/{subjectStudyPeriod:\\d+}/{studentGroup:\\d+}")
    public void connectStudentGroupWithPair(HoisUserDetails user, @WithEntity("subjectStudyPeriod") SubjectStudyPeriod ssp,
            @WithEntity("studentGroup") StudentGroup group) {
        UserUtil.assertIsSchoolAdmin(user, group.getSchool());
        // If has already group then it should not make another one
        UserUtil.throwAccessDeniedIf(ssp.getStudentGroups().stream().map(sspg -> sspg.getStudentGroup()).filter(sg -> sg.equals(group)).findAny().isPresent());
        subjectStudyPeriodStudentGroupService.connect(ssp, group);
    }

    @GetMapping("/searchByStudentGroup.xls")
    public void searchByStudentGroupAsExcel(HoisUserDetails user, @Valid SubjectStudyPeriodSearchCommand criteria, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOORM);
        HttpUtil.xls(response, "searchByStudentGroup.xls", subjectStudyPeriodStudentGroupSearchService.searchByStudentGroupAsExcel(user.getSchoolId(), criteria));
    }

    @GetMapping("/subjectstudyperiodstudentgroup.xls")
    public void subjectStudyPeriodAsExcel(HoisUserDetails user, @Valid SubjectStudyPeriodDtoContainer container, HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_KOORM);
        HttpUtil.xls(response, "subjectstudyperiodstudentgroup.xls", subjectStudyPeriodStudentGroupService.subjectStudyPeriodStudentGroupAsExcel(user.getSchoolId(), container));
    }
}
