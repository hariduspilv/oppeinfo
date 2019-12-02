package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchool;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolIscedClass;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolLocation;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolPerson;
import ee.hitsa.ois.domain.enterprise.PracticeAdmission;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.PracticeEnterpriseService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.enterprise.ContractStatisticsCommand;
import ee.hitsa.ois.web.commandobject.enterprise.EnterpriseFileCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeAdmissionCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseForm;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseGradeCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseIscedClassCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseLocationCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterprisePersonCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseSearchCommand;
import ee.hitsa.ois.web.commandobject.enterprise.StudyYearStatisticsCommand;
import ee.hitsa.ois.web.commandobject.student.StudentPracticeStatisticsSearchCommand;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseRegCodeCheckDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseRegCodeResponseDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSchoolIscedClassDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSchoolLocationDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSchoolPersonDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContactDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.enterprise.ContractStatisticsDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseAdmissionDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseAdmissionWithStudentGroupsDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseGradeDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseImportResultDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSearchDto;
import ee.hitsa.ois.web.dto.enterprise.StudyYearStatisticsDto;
import ee.hitsa.ois.web.dto.student.StudentPracticeStatisticsDto;

@RestController
@RequestMapping("/practiceEnterprise")
public class PracticeEnterpriseController {

    @Autowired
    private PracticeEnterpriseService practiceEnterpriseService;

    @GetMapping
    public Page<EnterpriseSearchDto> search(HoisUserDetails user, PracticeEnterpriseSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.search(user, command, pageable);
    }

    @GetMapping("/sameCountryAndCode")
    public EnterpriseRegCodeResponseDto sameCountryAndCode(HoisUserDetails user,
            EnterpriseRegCodeCheckDto enterpriseForm) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.sameCountryAndCode(enterpriseForm);
    }

    @GetMapping("/regCodeCheck")
    public EnterpriseRegCodeResponseDto regCodeCheck(HoisUserDetails user, EnterpriseRegCodeCheckDto enterpriseForm) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.checkForCode(user, enterpriseForm);
    }

    @GetMapping("/regCodeWithoutCheck")
    public EnterpriseRegCodeResponseDto regCodeWithoutCheck(HoisUserDetails user,
            EnterpriseRegCodeCheckDto enterpriseForm) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.regCodeWithoutCheck(user, enterpriseForm);
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.delete(user, enterpriseSchool);
    }

    @GetMapping("/updateRegCode/{id:\\d+}")
    public PracticeEnterpriseForm updateRegCode(HoisUserDetails user, EnterpriseRegCodeCheckDto enterpriseForm,
            @WithEntity EnterpriseSchool enterprise) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.updateRegCode(user, enterpriseForm, enterprise);
    }

    @PostMapping
    public PracticeEnterpriseForm create(HoisUserDetails user, 
            @Valid @RequestBody PracticeEnterpriseForm practiceEnterpriseForm) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.create(user, practiceEnterpriseForm);
    }

    @GetMapping("/{id:\\d+}")
    public PracticeEnterpriseForm get(HoisUserDetails user, @WithEntity Enterprise enterprise) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.get(enterprise, user);
    }

    @PostMapping("/{id:\\d+}/persons")
    public void createPerson(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool,
            @RequestBody PracticeEnterprisePersonCommand practiceEnterprisePersonCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.createPerson(user, enterpriseSchool, practiceEnterprisePersonCommand);
    }

    @GetMapping("/{id:\\d+}/persons")
    public Page<EnterpriseSchoolPersonDto> getPersons(HoisUserDetails user,
            @WithEntity EnterpriseSchool enterpriseSchool, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getPersons(enterpriseSchool, pageable);
    }

    @PostMapping("/person/{id:\\d+}")
    public void updatePerson(HoisUserDetails user, @WithEntity EnterpriseSchoolPerson person,
            @RequestBody PracticeEnterprisePersonCommand practiceEnterprisePersonCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, person.getEnterpriseSchool().getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.updatePerson(user, person, practiceEnterprisePersonCommand);
    }

    @DeleteMapping("/person/{id:\\d+}")
    public void deletePerson(HoisUserDetails user, @WithEntity EnterpriseSchoolPerson person) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, person.getEnterpriseSchool().getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.deletePerson(user, person);
    }

    @GetMapping("/{id:\\d+}/locations")
    public Page<EnterpriseSchoolLocationDto> getLocations(HoisUserDetails user,
            @WithEntity EnterpriseSchool enterpriseSchool, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getLocations(enterpriseSchool, pageable);
    }

    @PostMapping("/{id:\\d+}/locations")
    public void createLocations(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool,
            @RequestBody PracticeEnterpriseLocationCommand locationCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.createLocation(user, enterpriseSchool, locationCommand);
    }

    @PostMapping("/location/{id:\\d+}")
    public void updateLocation(HoisUserDetails user, @WithEntity EnterpriseSchoolLocation location,
            @RequestBody PracticeEnterpriseLocationCommand practiceEnterpriseLocationCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, location.getEnterpriseSchool().getSchool(),
                Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.updateLocation(user, location, practiceEnterpriseLocationCommand);
    }

    @DeleteMapping("/location/{id:\\d+}")
    public void deleteLocation(HoisUserDetails user, @WithEntity EnterpriseSchoolLocation location) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, location.getEnterpriseSchool().getSchool(),
                Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.deleteLocation(user, location);
    }

    @GetMapping("/{id:\\d+}/studentGroups")
    public Page<EnterpriseSchoolIscedClassDto> getStudentGroups(HoisUserDetails user,
            @WithEntity EnterpriseSchool enterpriseSchool, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getStudentGroups(user, enterpriseSchool, pageable);
    }

    @PostMapping("/{id:\\d+}/studentGroups")
    public void createStudentGroups(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool,
            @RequestBody PracticeEnterpriseIscedClassCommand studentGroupCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.createStudentGroup(user, enterpriseSchool, studentGroupCommand);
    }

    @PostMapping("/studentGroup/{id:\\d+}")
    public void updateStudentGroup(HoisUserDetails user, @WithEntity EnterpriseSchoolIscedClass iscedClass,
            @RequestBody PracticeEnterpriseIscedClassCommand studentGroupCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, iscedClass.getEnterpriseSchool().getSchool(),
                Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.updateStudentGroup(user, iscedClass, studentGroupCommand);
    }

    @DeleteMapping("/studentGroup/{id:\\d+}")
    public void deleteStudentGroup(HoisUserDetails user, @WithEntity EnterpriseSchoolIscedClass iscedClass) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, iscedClass.getEnterpriseSchool().getSchool(),
                Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.deleteStudentGroup(user, iscedClass);
    }

    @GetMapping("/{id:\\d+}/contracts")
    public Page<ContractSearchDto> getContracts(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getContracts(enterpriseSchool, pageable);
    }

    @GetMapping("/{id:\\d+}/grades")
    public Page<EnterpriseGradeDto> getGrades(HoisUserDetails user, @WithEntity Enterprise enterprise,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getGrades(enterprise, pageable);
    }

    @GetMapping("/{id:\\d+}/grade")
    public EnterpriseGradeDto getGrade(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getGrade(enterpriseSchool);
    }

    @PostMapping("/{id:\\d+}/grades")
    public void setGrades(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool,
            @RequestBody PracticeEnterpriseGradeCommand grades) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.setGrades(user, enterpriseSchool, grades);
    }

    @DeleteMapping("/{id:\\d+}/grades")
    public void deleteGrade(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.deleteGrade(user, enterpriseSchool);
    }

    @GetMapping("/{id:\\d+}/admissions")
    public Page<EnterpriseAdmissionDto> getAdmissions(HoisUserDetails user,
            @WithEntity EnterpriseSchool enterpriseSchool, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.getAdmissions(enterpriseSchool, pageable);
    }

    @PostMapping("/{id:\\d+}/admissions")
    public void createAdmission(HoisUserDetails user, @WithEntity EnterpriseSchool enterpriseSchool,
            @RequestBody PracticeAdmissionCommand admissionCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, enterpriseSchool.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_ETTEVOTE);
        practiceEnterpriseService.createAdmission(user, enterpriseSchool, admissionCommand);
    }

    @GetMapping("/admission/{id:\\d+}")
    public EnterpriseAdmissionWithStudentGroupsDto getAdmission(HoisUserDetails user,
            @WithEntity PracticeAdmission admission) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, admission.getEnterpriseSchool().getSchool());
        UserUtil.throwAccessDeniedIf(!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE) &&
                !UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS));
        return practiceEnterpriseService.getAdmission(admission);
    }

    @PostMapping("/admission/{id:\\d+}")
    public void updateAdmission(HoisUserDetails user, @WithEntity PracticeAdmission admission,
            @RequestBody PracticeAdmissionCommand admissionCommand) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, admission.getEnterpriseSchool().getSchool());
        UserUtil.throwAccessDeniedIf(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE) &&
                !UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PRAKTIKAAVALDUS));
        practiceEnterpriseService.updateAdmission(user, admission, admissionCommand);
    }

    @DeleteMapping("/admission/{id:\\d+}")
    public AutocompleteResult deleteAdmission(HoisUserDetails user, @WithEntity PracticeAdmission admission) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, admission.getEnterpriseSchool().getSchool(),
                Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.deleteAdmission(user, admission);
    }

    @GetMapping("/studentStatistics")
    public Page<StudentPracticeStatisticsDto> getStudentStatistics(HoisUserDetails user,
            StudentPracticeStatisticsSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PRSTATISTIKA);
        return practiceEnterpriseService.getStudentStatistics(user, command, pageable);
    }

    @GetMapping("/contractStatistics")
    public Page<ContractStatisticsDto> getContractStatistics(HoisUserDetails user, ContractStatisticsCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PRSTATISTIKA);
        return practiceEnterpriseService.getContractStatistics(user, command, pageable);
    }

    @GetMapping("/studyYearStatistics")
    public Page<StudyYearStatisticsDto> getStudyYearStatistics(HoisUserDetails user, StudyYearStatisticsCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PRSTATISTIKA);
        return practiceEnterpriseService.getStudyYearStatistics(user, command, pageable);
    }

    @GetMapping("/practiceStudentStatistics.xls")
    public void searchExcel(HoisUserDetails user, StudentPracticeStatisticsSearchCommand criteria,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        HttpUtil.xls(response, "practiceStudentStatistics.xls", practiceEnterpriseService.searchExcel(user, criteria));
    }

    @GetMapping("/practiceContractStatistics.xls")
    public void searchExcel(HoisUserDetails user, ContractStatisticsCommand criteria, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        HttpUtil.xls(response, "practiceContractStatistics.xls", practiceEnterpriseService.searchExcel(user, criteria));
    }

    @GetMapping("/practiceStudyYearStatistics.xls")
    public void searchExcel(HoisUserDetails user, StudyYearStatisticsCommand criteria, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        HttpUtil.xls(response, "practiceStudyYearStatistics.xls",
                practiceEnterpriseService.searchExcel(user, criteria));
    }

    @PostMapping("importCsv")
    public EnterpriseImportResultDto importCsv(@RequestBody EnterpriseFileCommand command, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ETTEVOTE);
        return practiceEnterpriseService.importCsv(command.getFile().getFdata(), user);
    }

    @GetMapping("sample.csv")
    public void csvSampleFile(HttpServletResponse response) throws IOException {
        HttpUtil.csvUtf8WithBom(response, "sample.csv", practiceEnterpriseService.sampleCsvFile());
    }

    @GetMapping("/enterpriseContacts/{id:\\d+}")
    public List<ContactDto> enterpriseContacts(HoisUserDetails user, Enterprise enterprise) {
        return practiceEnterpriseService.enterpriseContacts(user, enterprise);
    }

}
