package ee.hitsa.ois.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.StudentGroupYearTransferService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.studentgroupyeartransfer.CalculateCommand;
import ee.hitsa.ois.web.commandobject.studentgroupyeartransfer.TransferCommand;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.studentgroupyeartransfer.CalculatedStudentGroupDto;
import ee.hitsa.ois.web.dto.studentgroupyeartransfer.StudentDto;
import ee.hitsa.ois.web.dto.studentgroupyeartransfer.StudentGroupDto;

@RestController
@RequestMapping("/studentGroupYearTransfer")
public class StudentGroupYearTransferController {

    @Autowired
    private StudentGroupYearTransferService transferService;

    @GetMapping("/studyYears")
    public List<StudyYearSearchDto> studyYears(HoisUserDetails user) {
        assertCanView(user);
        return transferService.studyYears(user.getSchoolId());
    }

    @GetMapping
    public List<StudentGroupDto> search(HoisUserDetails user, SearchCommand criteria) {
        assertCanView(user);
        return transferService.search(user, criteria);
    }

    @PostMapping("/calculate")
    public Map<Long, CalculatedStudentGroupDto> calculate(HoisUserDetails user, @RequestBody @Valid CalculateCommand command) {
        assertCanEdit(user);
        return transferService.calculate(user, command);
    }
    
    @PostMapping("/transfer")
    public HttpUtil.NoContentResponse transfer(HoisUserDetails user, @RequestBody @Valid TransferCommand command) {
        assertCanEdit(user);
        transferService.transfer(user, command);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @GetMapping("/matching/{id:\\d+}")
    public List<String> matching(HoisUserDetails user, @PathVariable("id") Long logId) {
        assertCanView(user);
        return transferService.matching(user, logId);
    }

    @GetMapping("/mismatching/{id:\\d+}")
    public List<StudentDto> mismatching(HoisUserDetails user, @PathVariable("id") Long logId) {
        assertCanView(user);
        return transferService.mismatching(user, logId);
    }

    private static void assertCanView(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_UUDE_AASTASSE);
    }
    
    private static void assertCanEdit(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_UUDE_AASTASSE);
    }
}
