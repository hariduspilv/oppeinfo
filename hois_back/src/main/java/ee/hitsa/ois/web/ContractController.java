package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
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
import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.service.ContractService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ContractAllCommand;
import ee.hitsa.ois.web.commandobject.ContractCancelForm;
import ee.hitsa.ois.web.commandobject.ContractEkisForm;
import ee.hitsa.ois.web.commandobject.ContractForEkisSearchCommand;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractEkisDto;
import ee.hitsa.ois.web.dto.ContractForEkisDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentSubjectDto;
import ee.hitsa.ois.web.dto.ContractToEkisMessageDto;
import ee.hitsa.ois.web.dto.StudentGroupContractSearchCommand;
import ee.hitsa.ois.web.dto.StudentGroupContractSearchDto;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;
    
    @Autowired
    private EntityManager em;

    @GetMapping
    public Page<ContractSearchDto> search(HoisUserDetails user, ContractSearchCommand command, Pageable pageable) {
        if (user.isStudent()) {
            command.setStudent(user.getStudentId());
        }
        return contractService.search(user, command, pageable);
    }
    
    @GetMapping("/all")
    public Page<ContractSearchDto> search(HoisUserDetails user, ContractAllCommand command, Pageable pageable) {
        return contractService.searchAll(user, command, pageable);
    }
    
    @GetMapping("/studentGroup")
    public Page<StudentGroupContractSearchDto> searchStudentGroupContract(HoisUserDetails user, @Valid StudentGroupContractSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return contractService.searchStudentGroupContract(user, command, pageable);
    }
    
    @GetMapping("/ekis")
    public Page<ContractForEkisDto> searchContractForEkis(HoisUserDetails user, @Valid ContractForEkisSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return contractService.searchContractForEkis(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ContractDto get(HoisUserDetails user, @WithEntity Contract contract) {
        UserUtil.assertSameSchool(user, contract.getStudent().getSchool());
        if (user.isTeacher()) {
            UserUtil.throwAccessDeniedIf(!EntityUtil.getId(contract.getTeacher()).equals(user.getTeacherId()));
        } else if (user.isStudent() || user.isRepresentative()) {
            UserUtil.throwAccessDeniedIf(!EntityUtil.getId(contract.getStudent()).equals(user.getStudentId()));
        } else {
            UserUtil.assertIsSchoolAdmin(user);
        }
        return contractService.get(user, contract);
    }

    @PostMapping
    public ContractDto create(HoisUserDetails user, @Valid @RequestBody ContractForm contractForm) {
        UserUtil.assertIsSchoolAdmin(user);
        Contract savedContract = null;
        if (contractForm.getStudents() != null && !contractForm.getStudents().isEmpty()) {
            for (AutocompleteResult student : contractForm.getStudents()) {
               ContractForm copyOfContracForm = contractForm;
               copyOfContracForm.setStudent(student);
               savedContract = contractService.create(user, contractForm);
            }
            if (savedContract != null) {
                return get(user, savedContract);
            }
        } else {
            savedContract = contractService.create(user, contractForm);
        }
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
        return get(user, contractService.save(user, contract, contractForm));
    }
    
    @PutMapping("/cancel/{id:\\d+}")
    public ContractDto cancel(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Contract contract,
            @Valid @RequestBody ContractCancelForm contractForm) {
        UserUtil.assertIsSchoolAdmin(user);
        if (!ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_K, contract.getStatus()) && !ClassifierUtil.equals(ContractStatus.LEPING_STAATUS_Y, contract.getStatus())) {
            throw new ValidationFailedException("contract.messages.updatingOnlyAllowedForStatusKandY");
        }
        return get(user, contractService.cancel(user, contract, contractForm));
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

    @GetMapping("/checkForEkis/{id:\\d+}")
    public Map<String, ?> checkForEkis(HoisUserDetails user, @PathVariable("id") Long contractId) {
        UserUtil.assertIsSchoolAdmin(user);
        return contractService.checkForEkis(user, contractId);
    }
    
    @GetMapping("/checkForEkis")
    public Map<String, ?> checkForEkis(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return contractService.checkForEkis(user, null);
    }
    
    @PutMapping("/sendEmail/{id:\\d+}")
    public void sendEmail(HoisUserDetails user, @WithEntity ContractSupervisor supervisor) {
        UserUtil.assertIsSchoolAdmin(user);
        contractService.sendUniqueUrlEmailToEnterpriseSupervisor(user, supervisor);
    }

    @PostMapping("/sendToEkis/{id:\\d+}")
    public ContractDto sendToEkis(HoisUserDetails user, @WithEntity Contract contract) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, contractService.sendToEkis(user, contract));
    }
    
    @PostMapping("/sendToEkis")
    public ContractEkisDto sendToEkis(HoisUserDetails user, @RequestBody ContractEkisForm contractEkisForm) {
        UserUtil.assertIsSchoolAdmin(user);
        List<ContractToEkisMessageDto> passed = new ArrayList<>();
        List<ContractToEkisMessageDto> failed = new ArrayList<>();
        for (Long id : contractEkisForm.getContracts()) {
            Contract contract = em.getReference(Contract.class, id);
            try {
                contractService.sendToEkis(user, contract);
                passed.add(ContractService.createContractToEkisDto(contract, "contract.messages.sendToEkis.success"));
            } catch(Exception e) {
                failed.add(ContractService.createContractToEkisDto(contract, e.getMessage()));
            }
        }
        ContractEkisDto dto = new ContractEkisDto();
        dto.setFailed(failed);
        dto.setSuccessful(passed);
        return dto;
    }
}
