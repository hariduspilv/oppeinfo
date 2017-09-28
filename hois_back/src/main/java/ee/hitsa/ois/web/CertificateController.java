package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.CertificateContentService;
import ee.hitsa.ois.service.CertificateService;
import ee.hitsa.ois.service.CertificateValidationService;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateContentCommand;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateDto;
import ee.hitsa.ois.web.dto.CertificateSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateContentService certificateContentService;
    @Autowired
    private EkisService ekisService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CertificateValidationService certificateValidationService;

    @GetMapping
    public Page<CertificateSearchDto> search(HoisUserDetails user, @Valid CertificateSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return certificateService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public CertificateDto get(HoisUserDetails user, @WithEntity("id") Certificate certificate) {
        certificateValidationService.assertCanView(user, certificate);
        CertificateDto dto = CertificateDto.of(certificate);
        dto.setCanBeChanged(certificateValidationService.canBeChanged(user, certificate));
        return dto;
    }

    @GetMapping("/content")
    public Map<String, String> getContent(HoisUserDetails user, @Valid CertificateContentCommand command) {
        if(user.isStudent()) {
            command.setStudent(user.getStudentId());
        } else if(command.getStudent() == null) {
            throw new ValidationFailedException("no.student");
        }
        Student student = studentRepository.findOne(command.getStudent());
        UserUtil.assertIsSchoolAdminOrStudent(user, student.getSchool());

        Map<String, String> response = new HashMap<>();
        response.put("content", certificateContentService.generate(student, CertificateType.valueOf(command.getType())));
        return response;
    }

    @GetMapping("/signatories")
    public List<DirectiveCoordinatorDto> signatories(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return certificateService.signatories(user.getSchoolId());
    }

    @PostMapping
    public CertificateDto create(HoisUserDetails user, @Valid @RequestBody CertificateForm form) {
        if(user.isStudent()) {
            form.setStudent(user.getStudentId());
            certificateService.setSignatory(form, user.getSchoolId());
        }
        certificateValidationService.assertCanCreate(user, form);
        certificateValidationService.validate(user, form);
        return get(user, certificateService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public CertificateDto update(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestBody = true) Certificate certificate, 
            @Valid @RequestBody CertificateForm form) {
        certificateValidationService.assertCanChange(user, certificate);
        certificateValidationService.validate(user, form);
        return get(user, certificateService.save(user, certificate, form));
    }

    @PostMapping("/order")
    public CertificateDto createAndOrder(HoisUserDetails user, @Valid @RequestBody CertificateForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        certificateValidationService.validate(user, form);
        Certificate certificate = certificateService.create(user, form);
        // send to EKIS
        return get(user, ekisService.registerCertificate(certificate));
    }

    @PutMapping("/order/{id:\\d+}")
    public CertificateDto saveAndOrder(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestBody = true) Certificate certificate, 
            @Valid @RequestBody CertificateForm form) {
        certificateValidationService.assertCanChange(user, certificate);
        certificateValidationService.validate(user, form);
        // TODO: send to EKIS
        return get(user, certificateService.save(user, certificate, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Certificate certificate, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        certificateValidationService.assertCanChange(user, certificate);
        certificateService.delete(certificate);
    }

    @GetMapping("/otherStudent")
    public StudentSearchDto getOtherPerson(HoisUserDetails user, String idcode) {
        UserUtil.assertIsSchoolAdmin(user);
        return certificateService.getOtherPerson(user.getSchoolId(), idcode);
    }
}
