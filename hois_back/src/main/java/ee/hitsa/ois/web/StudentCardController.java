package ee.hitsa.ois.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.StudentCardService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.student.StudentCardSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentCardSearchDto;

@RestController
@RequestMapping("/studentCards")
public class StudentCardController {

    @Autowired
    private StudentCardService studentCardService;

    @GetMapping
    public Page<StudentCardSearchDto> search(HoisUserDetails user, @NotNull StudentCardSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PILET);
        return studentCardService.search(user, criteria, pageable);
    }

    @PostMapping
    public void updateCards(HoisUserDetails user, @RequestBody ArrayList<StudentCardSearchDto> studentCardDtos) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PILET);
        studentCardService.updateStudentCards(studentCardDtos);
    }

    @GetMapping("/order.zip")
    public void order(HoisUserDetails user, @NotNull StudentCardSearchCommand cmd,
            HttpServletResponse response) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PILET);
        studentCardService.order(response, user, cmd);
    }

    @GetMapping("/orderAgain.zip")
    public void orderAgain(HoisUserDetails user, @NotNull StudentCardSearchCommand cmd,
            HttpServletResponse response) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PILET);
        studentCardService.orderAgain(response, user, cmd);
    }

    @GetMapping("/orderExtension.zip")
    public void orderExtension(HoisUserDetails user, @NotNull StudentCardSearchCommand cmd,
            HttpServletResponse response) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PILET);
        studentCardService.orderExtension(response, user, cmd);
    }

    @GetMapping("/excel.xlsx")
    public void excel(HoisUserDetails user, @NotNull StudentCardSearchCommand cmd,
            HttpServletResponse response) throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PILET);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYY");
        HttpUtil.xls(response, String.format("opilaspilet_tryki_%s.xlsx", formatter.format(LocalDate.now())), studentCardService.excel(user, cmd));
    }

    @PostMapping("/orderRepetition/{id:\\d+}")
    public StudentCardSearchDto orderRepetition(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PILET);
        UserUtil.assertIsNotGuestStudent(student);
        return studentCardService.orderRepetition(student);
    }
}
