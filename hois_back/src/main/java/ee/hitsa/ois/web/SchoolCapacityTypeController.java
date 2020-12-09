package ee.hitsa.ois.web;

import java.util.List;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.SchoolCapacityTypeService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeForms;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeLoadForms;
import ee.hitsa.ois.web.dto.schoolcapacity.SchoolCapacityTypeDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/schoolCapacityType")
public class SchoolCapacityTypeController {

    @Autowired
    private SchoolCapacityTypeService schoolCapacityTypeService;
    @Autowired
    private StudyYearService studyYearService;

    @GetMapping
    public List<SchoolCapacityTypeDto> get(HoisUserDetails user, Boolean isHigher) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPPETOOLIIK);
        StudyYear currentStudyYear = studyYearService.getCurrentStudyYear(user.getSchoolId());
        StudyYear previousStudyYear = currentStudyYear != null
                ? studyYearService.getPreviousStudyYear(currentStudyYear) : null;
        StudyYear nextStudyYear = studyYearService.getNextStudyYear(user.getSchoolId());
        return schoolCapacityTypeService.get(user, isHigher, EntityUtil.getNullableId(previousStudyYear),
                EntityUtil.getNullableId(currentStudyYear), EntityUtil.getNullableId(nextStudyYear));
    }
    
    @PostMapping
    public void save(HoisUserDetails user, @RequestBody SchoolCapacityTypeForms forms) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPETOOLIIK);
        schoolCapacityTypeService.save(user, forms);
    }
    
    @PostMapping("/loads")
    public void saveLoads(HoisUserDetails user, @RequestBody @Valid SchoolCapacityTypeLoadForms forms) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPETOOLIIK);
        schoolCapacityTypeService.saveLoads(user, forms);
    }

}
