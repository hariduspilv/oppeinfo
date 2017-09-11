package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.Validator;

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
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.CertificateContentService;
import ee.hitsa.ois.service.CertificateService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.CertificateValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateContentCommand;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateDto;
import ee.hitsa.ois.web.dto.CertificateSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateContentService certificateContentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private Validator validator;

    @GetMapping
    public Page<CertificateSearchDto> search(HoisUserDetails user, @Valid CertificateSearchCommand criteria, Pageable pageable) {
        return certificateService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public CertificateDto get(HoisUserDetails user, @WithEntity("id") Certificate certificate) {
        UserUtil.assertSameSchool(user, certificate.getSchool());
        return CertificateDto.of(certificate);
    }
    
    @GetMapping("/content")
    public Map<String, String> getContent(HoisUserDetails user, @Valid CertificateContentCommand command) {

        if(user.isStudent()) {
            command.setStudent(user.getStudentId());
        } else if(command.getStudent() == null) {
            throw new ValidationFailedException("no.student");
        }
        UserUtil.assertSameSchool(user, studentRepository.findOne(command.getStudent()).getSchool());  
        
        Map<String, String> response = new HashMap<>();
        response.put("content", certificateContentService.generate(command));
        return response;
    }
    
    @GetMapping("/content/{id:\\d+}")
    public Map<String, String> getContentOf(HoisUserDetails user, @WithEntity("id") Certificate certificate) {

        if(user.isStudent() && !user.getStudentId().equals(EntityUtil.getNullableId(certificate.getStudent()))) {
            throw new ValidationFailedException("no.permission");
        }
        UserUtil.assertSameSchool(user, certificate.getStudent().getSchool());  
        
        Map<String, String> response = new HashMap<>();
        response.put("content", certificateContentService.retrieve(certificate));
        return response;
    }

    @PostMapping
    public CertificateDto create(HoisUserDetails user, @Valid @RequestBody CertificateForm form) {
        // TODO: user right check
        if(user.isStudent()) {
            form.setStudent(user.getStudentId());
        }
        validate(form);
        return get(user, certificateService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public CertificateDto update(HoisUserDetails user, 
            @WithVersionedEntity(value = "id", versionRequestBody = true) Certificate certificate, 
            @Valid @RequestBody CertificateForm form) {
        UserUtil.assertSameSchool(user, certificate.getSchool());
        validate(form);
        return get(user, certificateService.save(certificate, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestParam = "version") Certificate certificate, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertSameSchool(user, certificate.getSchool());
        certificateService.delete(certificate);
    }

    @GetMapping("/otherStudent")
    public StudentSearchDto getOtherPerson(HoisUserDetails user, String idcode) {
        // TODO validation of idcode
        return certificateService.getOtherPerson(user.getSchoolId(), idcode);
    }
    
    public void validate(CertificateForm form) {
        if(!CertificateType.isOther(form.getType())) {
            ValidationFailedException.throwOnError(validator
                    .validate(form, CertificateValidator.StudentIsSet.class));
        } else {
            ValidationFailedException.throwOnError(validator.validate(form, 
                    CertificateValidator.OtherType.class));
            if(form.getStudent() != null) {
                ValidationFailedException.throwOnError(validator.validate(form, 
                        CertificateValidator.StudentIsSet.class));
            } else {
                ValidationFailedException.throwOnError(validator.validate(form, 
                        CertificateValidator.StudentIsNotSet.class));
            }
        }

    }
}
