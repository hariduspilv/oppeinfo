package ee.hitsa.ois.service;

import java.util.Arrays;
import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateStatus;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.CertificateValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateForm;

@Service
public class CertificateValidationService {
    
    @Autowired
    private Validator validator;
    @Autowired
    private StudentRepository studentRepository;

    
    public void validate(HoisUserDetails user, CertificateForm form) {
        
        if(canEditContent(user, form.getType())) {
            ValidationFailedException.throwOnError(validator
                    .validate(form, CertificateValidator.ContentIsEditable.class));
        }
        ValidationFailedException.throwOnError(validator
                .validate(form, CertificateValidator.ValidateLater.class));
        
        if(!CertificateType.isOther(form.getType())) {
            ValidationFailedException.throwOnError(validator
                    .validate(form, CertificateValidator.StudentIsSet.class));
            validateStudentAndTypeMatch(form.getStudent(), form.getType());
        } else {
            if(form.getStudent() != null) {
                ValidationFailedException.throwOnError(validator.validate(form, 
                        CertificateValidator.StudentIsSet.class));
            } else {
                ValidationFailedException.throwOnError(validator.validate(form, 
                        CertificateValidator.StudentIsNotSet.class));
            }
        }
    }

    private void validateStudentAndTypeMatch(Long studentId, String type) {
        Student student = studentRepository.getOne(studentId);
        List<String> onlyActive = 
                Arrays.asList(CertificateType.TOEND_LIIK_OPI.name(), 
                        CertificateType.TOEND_LIIK_KONTAKT.name());
        List<String> onlyFinished = Arrays.asList(CertificateType.TOEND_LIIK_LOPET.name());
        
        if(onlyActive.contains(type) && !StudentUtil.isActive(student)) {
            throw new ValidationFailedException("certificate.error.studentNotActive");
        }
        if(onlyFinished.contains(type) && !StudentUtil.hasFinished(student)) {
            throw new ValidationFailedException("certificate.error.studentNotFinished");
        }
    }

    public void assertCanView(HoisUserDetails user, Certificate certificate) {
        if(certificate.getStudent() != null) {
            if(!UserUtil.canViewStudent(user, certificate.getStudent())) {
                throw new ValidationFailedException("no.permission");
            }
        } else {
            UserUtil.assertIsSchoolAdmin(user, certificate.getSchool());
        }

    }
    
    public void assertCanCreate(HoisUserDetails user, CertificateForm form) {
        if(!CertificateType.isOther(form.getType()) || form.getStudent() != null) {
            UserUtil.assertIsSchoolAdminOrStudent(user, studentRepository.getOne(form.getStudent()).getSchool());
        } else {
            UserUtil.assertIsSchoolAdmin(user);
        }
    }
    
    public void assertCanChange(HoisUserDetails user, Certificate certificate) {
        if(!canBeChanged(user, certificate)) {
            throw new ValidationFailedException("no.rights");
        }
    }
    
    public boolean canEditContent(HoisUserDetails user, String typeCode) {
        return user.isSchoolAdmin() && CertificateType.schoolAdminCanEdit(typeCode);
    }
    
    public boolean canBeChanged(HoisUserDetails user, Certificate certificate) {
        return UserUtil.isSchoolAdmin(user, certificate.getSchool()) && 
                ClassifierUtil.equals(CertificateStatus.TOEND_STAATUS_T, certificate.getStatus());
    }
    
    public boolean canBeChanged(HoisUserDetails user, String status) {
        return user.isSchoolAdmin() && CertificateStatus.TOEND_STAATUS_T.name().equals(status);
    }
}
