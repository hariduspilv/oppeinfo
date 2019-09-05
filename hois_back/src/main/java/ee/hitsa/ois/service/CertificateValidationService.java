package ee.hitsa.ois.service;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateStatus;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.CertificateValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateForm;

@Transactional
@Service
public class CertificateValidationService {

    private static final Set<String> ONLY_ACTIVE = EnumUtil.toNameSet(CertificateType.TOEND_LIIK_OPI, 
            CertificateType.TOEND_LIIK_SESS, CertificateType.TOEND_LIIK_KONTAKT);

    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;

    public void validate(HoisUserDetails user, CertificateForm form) {
        validate(user, form, null);
    }
    
    public void validate(HoisUserDetails user, CertificateForm form, School school) {
        if(canEditContent(user, form.getType())) {
            ValidationFailedException.throwOnError(validator
                    .validate(form, CertificateValidator.ContentIsEditable.class));
        }
        ValidationFailedException.throwOnError(validator
                .validate(form, CertificateValidator.ValidateLater.class));
        
        if (school != null) {
            if (Boolean.TRUE.equals(school.getIsWithoutEkis())) {
                ValidationFailedException.throwOnError(validator
                        .validate(form, CertificateValidator.RequiredIfWithoutEkis.class));
            }
        }
        
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
        Student student = em.getReference(Student.class, studentId);
        if(ONLY_ACTIVE.contains(type) && !StudentUtil.isActive(student)) {
            throw new ValidationFailedException("certificate.error.studentNotActive");
        }
        if(CertificateType.TOEND_LIIK_LOPET.name().equals(type) && !StudentUtil.hasFinished(student)) {
            throw new ValidationFailedException("certificate.error.studentNotFinished");
        }
    }

    public void assertCanChange(HoisUserDetails user, Certificate certificate) {
        if(!canBeChanged(user, certificate)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void assertCanDelete(HoisUserDetails user, Certificate certificate) {
        UserUtil.assertIsSchoolAdminOrStudent(user, certificate.getSchool());
        Student student = certificate.getStudent();
        if((user.isStudent() && (student == null || !UserUtil.isStudent(user, student))) || !entering(certificate)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void assertCanSendToEkis(HoisUserDetails user, Certificate certificate) {
        UserUtil.assertIsSchoolAdminOrStudent(user, certificate.getSchool());
        Student student = certificate.getStudent();
        if ((user.isStudent() && (student == null || !UserUtil.isStudent(user, student))) || !entering(certificate) || Boolean.TRUE.equals(certificate.getSchool().getIsWithoutEkis())) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public void assertCanComplete(HoisUserDetails user, Certificate certificate) {
        UserUtil.assertIsSchoolAdmin(user, certificate.getSchool());
        if (!entering(certificate) || !Boolean.TRUE.equals(certificate.getSchool().getIsWithoutEkis())) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public boolean canEditContent(HoisUserDetails user, String typeCode) {
        return user.isSchoolAdmin() && CertificateType.schoolAdminCanEdit(typeCode);
    }

    public boolean canBeChanged(HoisUserDetails user, Certificate certificate) {
        return UserUtil.isSchoolAdmin(user, certificate.getSchool()) && entering(certificate);
    }

    public boolean canBeChanged(HoisUserDetails user, String status) {
        return user.isSchoolAdmin() && CertificateStatus.TOEND_STAATUS_T.name().equals(status);
    }

    private static boolean entering(Certificate certificate) {
        return ClassifierUtil.equals(CertificateStatus.TOEND_STAATUS_T, certificate.getStatus());
    }
}
