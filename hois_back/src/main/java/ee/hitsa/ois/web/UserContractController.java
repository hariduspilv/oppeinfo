package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.UserContractService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.UserContractForm;
import ee.hitsa.ois.web.commandobject.UserContractSearchCommand;
import ee.hitsa.ois.web.dto.UserContractSearchDto;

@RestController
@RequestMapping("/userContract")
public class UserContractController {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private UserContractService service;

    @GetMapping("/admin")
    public UserContractForm getShoolContractData(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TINGIMUS);
        return service.getContractData(em.getReference(School.class, user.getSchoolId()));
    }
    
    @PutMapping("/admin")
    public UserContractForm saveSchoolContractData(HoisUserDetails user, @Valid @RequestBody UserContractForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TINGIMUS);
        service.saveContractData(form, em.getReference(School.class, user.getSchoolId()));
        return getShoolContractData(user);
    }
    
    @GetMapping
    public Map<String, String> get(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        Map<String, String> result = new HashMap<>();
        result.put("data", service.getContract(em.getReference(School.class, user.getSchoolId())));
        return result;
    }
    
    @PostMapping
    public void confirm(HoisUserDetails user) {
        UserUtil.assertIsStudent(user);
        service.confirmContract(em.getReference(Student.class, user.getStudentId()), em.getReference(School.class, user.getSchoolId()));
    }
    
    @GetMapping("/users")
    public Page<UserContractSearchDto> search(HoisUserDetails user, @Valid UserContractSearchCommand cmd, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TINGIMUS);
        return service.search(user, cmd, pageable);
    }
    
    @GetMapping("/users/{id:\\d+}")
    public Map<String, String> getStudentContract(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdmin(user, student.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TINGIMUS);
        Map<String, String> result = new HashMap<>();
        result.put("data", student.getContractText());
        return result;
    }
}
