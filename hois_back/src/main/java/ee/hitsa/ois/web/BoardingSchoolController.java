package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

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

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.BoardingSchoolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolManagementForm;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolManagementSearchCommand;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolRoomCommand;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolSearchCommand;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolManagementCheckDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolManagementDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolRoomDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolSearchDto;

@RestController
@RequestMapping("/boardingSchools")
public class BoardingSchoolController {

    @Autowired
    private BoardingSchoolService boardingSchoolService;

    @GetMapping
    public Page<BoardingSchoolSearchDto> search(HoisUserDetails user, @NotNull BoardingSchoolSearchCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPILASKODU);
        return boardingSchoolService.search(user, criteria, pageable);
    }

    @GetMapping("/search.xlsx")
    public void excel(HoisUserDetails user, @NotNull BoardingSchoolSearchCommand criteria, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPILASKODU);
        HttpUtil.xls(response, "boarding.schools.xlsx",
                boardingSchoolService.boardingSchoolResidetnsAsExcel(user, criteria));
    }

    @GetMapping("/management")
    public Page<BoardingSchoolManagementDto> managementSearch(HoisUserDetails user,
            @NotNull BoardingSchoolManagementSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPILASKODU);
        return boardingSchoolService.managementSearch(user, criteria, pageable);
    }

    @PostMapping("/management")
    private void saveBoardingSchoolResidents(HoisUserDetails user,
            @RequestBody List<BoardingSchoolManagementForm> form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPILASKODU);
        boardingSchoolService.saveBoardingSchoolResidents(form);
    }

    @PostMapping("/management/check")
    private List<BoardingSchoolManagementCheckDto> checkBoardingSchoolResidents(HoisUserDetails user,
            @RequestBody List<BoardingSchoolManagementForm> form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPILASKODU);
        return boardingSchoolService.checkBoardingSchoolResidents(form);
    }

    @GetMapping("/rooms")
    public Page<BoardingSchoolRoomDto> rooms(HoisUserDetails user, @NotNull BoardingSchoolRoomCommand criteria,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPILASKODU);
        return boardingSchoolService.rooms(user, criteria, pageable);
    }
}
