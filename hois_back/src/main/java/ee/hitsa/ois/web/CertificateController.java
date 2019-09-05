package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
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
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.report.certificate.CertificateRtfReport;
import ee.hitsa.ois.service.CertificateContentService;
import ee.hitsa.ois.service.CertificateService;
import ee.hitsa.ois.service.CertificateValidationService;
import ee.hitsa.ois.service.RtfService;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.PersonUtil;
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
    private CertificateValidationService certificateValidationService;
    @Autowired
    private RtfService rtfService;
    @Autowired
    private EntityManager em;

    @GetMapping
    public Page<CertificateSearchDto> search(HoisUserDetails user, @Valid CertificateSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return certificateService.search(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public CertificateDto get(HoisUserDetails user, @WithEntity Certificate certificate) {
        if(certificate.getStudent() != null) {
            if (!UserUtil.canViewStudentSpecificData(user, certificate.getStudent())) {
                throw new ValidationFailedException("main.messages.error.nopermission");
            }
        } else {
            UserUtil.assertIsSchoolAdmin(user, certificate.getSchool());
        }
        CertificateDto dto = CertificateDto.of(user, certificate);
        dto.setCanBeChanged(certificateValidationService.canBeChanged(user, certificate));
        return dto;
    }

    @GetMapping("/student/status/{id:\\d+}")
    public Map<String, String> getStudentStatus(HoisUserDetails user, @WithEntity Student student) {
        UserUtil.assertIsSchoolAdminOrStudent(user);
        return Collections.singletonMap("status", EntityUtil.getCode(student.getStatus()));
    }

    @GetMapping("/content")
    public Map<String, String> getContent(HoisUserDetails user, @Valid CertificateContentCommand command) {
      UserUtil.assertIsSchoolAdminOrStudent(user);
      if(user.isStudent()) {
        command.setStudent(user.getStudentId());
      }
      return Collections.singletonMap("content", 
                certificateContentService.generate(user.getSchoolId(), command));
    }

    @GetMapping("/signatories")
    public List<DirectiveCoordinatorDto> signatories(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return certificateService.signatories(user.getSchoolId());
    }

    /**
     * Create certificate endpoint for admin
     *
     * @param user
     * @param form
     * @return
     */
    @PostMapping
    public CertificateDto create(HoisUserDetails user, @Valid @RequestBody CertificateForm form) {
        UserUtil.assertIsSchoolAdmin(user);
        return get(user, certificateService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public CertificateDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) Certificate certificate,
            @Valid @RequestBody CertificateForm form) {
        certificateValidationService.assertCanChange(user, certificate);
        certificateValidationService.validate(user, form);
        return get(user, certificateService.save(user, certificate, form));
    }

    /**
     * Create certificate and send it to EKIS
     *
     * @param user
     * @param form
     * @return
     */
    @PostMapping("/order")
    public CertificateDto createAndOrder(HoisUserDetails user, @Valid @RequestBody CertificateForm form) {
        if(!CertificateType.isOther(form.getType())) {
            UserUtil.assertIsSchoolAdminOrStudent(user);
        } else {
            UserUtil.assertIsSchoolAdmin(user);
        }
        Certificate certificate = certificateService.create(user, form);
        if (Boolean.TRUE.equals(em.getReference(School.class, user.getSchoolId()).getIsWithoutEkis())) {
            return get(user, certificate);
        }
        return orderFromEkis(user, certificate);
    }

    /**
     * Update certificate and send it to EKIS
     *
     * @param user
     * @param certificate
     * @param form
     * @return
     */
    @PutMapping("/order/{id:\\d+}")
    public CertificateDto saveAndOrder(HoisUserDetails user, 
            @WithVersionedEntity(versionRequestBody = true) Certificate certificate,
            @Valid @RequestBody CertificateForm form) {
        certificateValidationService.assertCanChange(user, certificate);
        certificateValidationService.validate(user, form);
        certificate = certificateService.save(user, certificate, form);
        return orderFromEkis(user, certificate);
    }

    @PutMapping("/complete/{id:\\d+}")
    public CertificateDto saveAndComplete(HoisUserDetails user, 
            @WithVersionedEntity(versionRequestBody = true) Certificate certificate,
            @Valid @RequestBody CertificateForm form) {
        certificateValidationService.validate(user, form, certificate.getSchool());
        certificateValidationService.assertCanChange(user, certificate);
        certificate = certificateService.save(user, certificate, form);
        certificateValidationService.assertCanComplete(user, certificate);
        return get(user, certificateService.complete(certificate));
    }

    @PutMapping("/orderFromEkis/{id:\\d+}")
    public CertificateDto orderFromEkis(HoisUserDetails user, @WithEntity Certificate certificate) {
        certificateValidationService.assertCanSendToEkis(user, certificate);
        // send to EKIS
        Long certificateId = EntityUtil.getId(certificate);
        try {
            return get(user, ekisService.registerCertificate(certificateId));
        } catch(ValidationFailedException e) {
            // return certificate id to frontend
            if(user.isStudent()) {
                // student gets different message
                e = new ValidationFailedException("certificate.orderFailure");
            }
            e.getErrorInfo().setData(Collections.singletonMap("id", certificateId));
            throw e;
        }
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") Certificate certificate, @SuppressWarnings("unused") @RequestParam("version") Long version) {
        certificateValidationService.assertCanDelete(user, certificate);
        certificateService.delete(user, certificate);
    }

    @GetMapping("/otherStudent")
    public StudentSearchDto otherStudent(HoisUserDetails user, OtherStudentCommand command) {
        UserUtil.assertIsSchoolAdminOrStudentOrRepresentative(user);
        return certificateService.otherStudent(user, command.getId(), command.getIdcode());
    }
    
    @GetMapping("/print/{id:\\d+}/certificate.rtf")
    public void print(HoisUserDetails user, @WithEntity Certificate certificate, HttpServletResponse response, @RequestParam(required = false) Language lang) throws IOException {
        UserUtil.assertIsSchoolAdmin(user, certificate.getSchool(), Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TOEND);
        UserUtil.assertIsSchoolWithoutEkis(certificate.getSchool());
        HttpUtil.rtf(response, String.format("toend%s.rtf", certificate.getStudent() != null ? "_" + PersonUtil.fullname(certificate.getStudent().getPerson()) : ""),
                rtfService.generateFop(CertificateRtfReport.TEMPLATE_NAME, new CertificateRtfReport(certificate), lang));
    }
    
    @GetMapping("/hasorderedcertificates")
    public Map<String, Object> hasOrderedCertificates(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertIsSchoolWithoutEkis(em.getReference(School.class, user.getSchoolId()));
        Map<String, Object> result = new HashMap<>();
        result.put("has", Boolean.FALSE);
        if (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TOEND)) {
            result.put("has", certificateService.hasOrderedCertificates(user));
        }
        return result;
    }

    public static class OtherStudentCommand {

        private String idcode;
        private Long id;

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
