package ee.hitsa.ois.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.service.ContractService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractSearchCommand;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping
    public Page<ContractSearchDto> search(HoisUserDetails user, ContractSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ContractDto get(HoisUserDetails user, @WithEntity("id") Contract contract) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.get(contract);
    }

    @PostMapping
    public ContractDto create(@Valid @RequestBody ContractForm contractForm, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, contractService.create(contractForm));
    }

    @PutMapping("/{id:\\d+}")
    public ContractDto update(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Contract contract,
            @Valid @RequestBody ContractForm contractForm) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, contractService.save(contract, contractForm));
    }

    @GetMapping("studentPracticeModules/{studentId:\\d+}")
    public Collection<ContractStudentModuleDto> studentPracticeModules(HoisUserDetails user, @PathVariable Long studentId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.studentPracticeModules(user, studentId);
    }

}
