package ee.hitsa.ois.service;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Certificate;
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

    private static final Set<String> ONLY_ACTIVE = EnumUtil.toNameSet(CertificateType.TOEND_LIIK_OPI, CertificateType.TOEND_LIIK_KONTAKT);

    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;

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
        Student student = em.getReference(Student.class, studentId);
        if(ONLY_ACTIVE.contains(type) && !StudentUtil.isActive(student)) {
            throw new ValidationFailedException("certificate.error.studentNotActive");
        }
        if(CertificateType.TOEND_LIIK_LOPET.name().equals(type) && !StudentUtil.hasFinished(student)) {
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
            UserUtil.assertIsSchoolAdminOrStudent(user, em.getReference(Student.class, form.getStudent()).getSchool());
        } else {
            UserUtil.assertIsSchoolAdmin(user);
        }
    }

    public void assertCanChange(HoisUserDetails user, Certificate certificate) {
        if(!canBeChanged(user, certificate)) {
            throw new ValidationFailedException("no.rights");
        }
    }

    public void assertCanDelete(HoisUserDetails user, Certificate certificate) {
        UserUtil.assertIsSchoolAdminOrStudent(user, certificate.getSchool());
        if((user.isStudent() && !UserUtil.isSame(user, certificate.getStudent())) || !entering(certificate)) {
            throw new ValidationFailedException("no.rights");
        }
    }

    public void assertCanSendToEkis(HoisUserDetails user, Certificate certificate) {
        UserUtil.assertIsSchoolAdminOrStudent(user, certificate.getSchool());
        if((user.isStudent() && !UserUtil.isSame(user, certificate.getStudent())) || !entering(certificate)) {
            throw new ValidationFailedException("no.rights");
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
