package ee.hitsa.ois.web;

import java.util.Collection;
import java.util.stream.Collectors;

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

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.service.ContractService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractSearchCommand;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentSubjectDto;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping
    public Page<ContractSearchDto> search(HoisUserDetails user, ContractSearchCommand command, Pageable pageable) {
        if (user.isStudent()) {
            command.setStudent(user.getStudentId());
        }
        return contractService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ContractDto get(HoisUserDetails user, @WithEntity Contract contract) {
        UserUtil.assertSameSchool(user, contract.getStudent().getSchool());
        return contractService.get(contract);
    }

    @PostMapping
    public ContractDto create(HoisUserDetails user, @Valid @RequestBody ContractForm contractForm) {
        UserUtil.assertIsSchoolAdmin(user);
        Contract savedContract = contractService.create(contractForm);
        contractService.sendUniqueUrlEmailToEnterpriseSupervisor(user, savedContract);
        return get(user, savedContract);
    }

    @PutMapping("/{id:\\d+}")
    public ContractDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Contract contract,
            @Valid @RequestBody ContractForm contractForm) {
        UserUtil.assertIsSchoolAdmin(user);
        if (!ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_S, contract.getStatus())) {
            throw new ValidationFailedException("contract.messages.updatingOnlyAllowedForStatusS");
        }
        return get(user, contractService.save(contract, contractForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") Contract contract,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdmin(user);
        if (!ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_Y, contract.getStatus())
                && !ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_S, contract.getStatus())) {
            throw new ValidationFailedException("contract.messages.deletionOnlyAllowedForStatusSAndY");
        }
        contractService.delete(user, contract);
    }

    @GetMapping("studentPracticeModules/{studentId:\\d+}")
    public Collection<ContractStudentModuleDto> studentPracticeModules(HoisUserDetails user,
            @PathVariable Long studentId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.studentPracticeModules(user, studentId);
    }

    @GetMapping("studentPracticeSubjects/{studentId:\\d+}")
    public Collection<ContractStudentSubjectDto> studentSubjects(HoisUserDetails user, @PathVariable Long studentId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.studentPracticeHigherModules(user, studentId)
                .stream().flatMap(it -> it.getSubjects().stream()).collect(Collectors.toList());
    }

    @PostMapping("/sendToEkis/{id:\\d+}")
    public ContractDto sendToEkis(HoisUserDetails user, @WithEntity Contract contract) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, contractService.sendToEkis(user, contract));
    }

}