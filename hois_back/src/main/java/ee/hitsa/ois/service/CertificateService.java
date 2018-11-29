package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateStatus;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class CertificateService {

    private static final String CERTIFICATE_FROM = "from certificate c "
            + "left outer join student s on s.id = c.student_id "
            + "left outer join person p on p.id = s.person_id "
            + "inner join classifier type on c.type_code = type.code "
            + "join classifier status on status.code = c.status_code ";

    private static final String CERTIFICATE_SELECT = "c.id, c.type_code, c.certificate_nr, c.headline, c.whom, c.inserted, "
            + "case when c.student_id is not null then p.firstname || ' ' || p.lastname "
            + "else c.other_name end as name, c.student_id, "
            + "case when c.student_id is not null then p.lastname || ' ' || p.firstname "
            + "else split_part(c.other_name, ' ', 2) || ' ' || split_part(c.other_name, ' ', 1) "
            + "end as sortablename, c.status_code ";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CertificateValidationService certificateValidationService;
    @Autowired
    private CertificateContentService certificateContentService;
    @Autowired
    private SchoolService schoolService;

    /**
     * Search certificates
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<CertificateSearchDto> search(HoisUserDetails user, CertificateSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(CERTIFICATE_FROM).sort(pageable);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id = :studentId_1", "studentId_1", user.getStudentId());
        qb.optionalCriteria("s.id = :studentId_2", "studentId_2", criteria.getStudent());
        qb.optionalContains("c.headline", "headline", criteria.getHeadline());
        qb.optionalContains("c.certificate_nr", "certificate_nr", criteria.getCertificateNr());
        qb.optionalCriteria("c.type_code in (:type)", "type", criteria.getType());
        qb.optionalCriteria("(p.idcode = :idcode OR c.other_idcode = :idcode)", "idcode", criteria.getIdcode());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname", "c.other_name"), "name", criteria.getName());
        qb.optionalCriteria("c.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("c.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);

        if(user.isRepresentative()) {
            qb.requiredCriteria("exists("
                    + "select * from student_representative sr "
                    + "where sr.student_id = s.id "
                    + "and sr.is_student_visible = true "
                    + "and sr.person_id = :representativePersonId)", "representativePersonId", user.getPersonId());
        }

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb, CERTIFICATE_SELECT, em, pageable);
        return result.map(r -> {
            CertificateSearchDto dto = new CertificateSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setCertificateNr(resultAsString(r, 2));
            dto.setHeadline(resultAsString(r, 3));
            dto.setWhom(resultAsString(r, 4));
            dto.setInserted(JpaQueryUtil.resultAsLocalDateTime(r, 5).toLocalDate());
            dto.setStudentFullname(resultAsString(r, 6));
            dto.setStudentId(resultAsLong(r, 7));
            dto.setStatus(resultAsString(r, 9));
            dto.setCanBeChanged(certificateValidationService.canBeChanged(user, dto.getStatus()));
            return dto;
        });
    }

    /**
     * Create new certificate
     *
     * @param user
     * @param form
     * @return
     */
    public Certificate create(HoisUserDetails user, CertificateForm form) {
        if(user.isStudent()) {
            form.setStudent(user.getStudentId());
            setSignatory(form, user.getSchoolId());
        }
        certificateValidationService.validate(user, form);

        Certificate certificate = EntityUtil.bindToEntity(form, new Certificate(), classifierRepository,
                "student", "otherName", "otherIdcode");
        certificate.setSchool(em.getReference(School.class, user.getSchoolId()));
        setCertificateStatus(certificate, CertificateStatus.TOEND_STAATUS_T);

        certificate.setStudent(EntityUtil.getOptionalOne(Student.class, form.getStudent(), em));
        if(CertificateType.isOther(form.getType()) && form.getStudent() == null) {
            certificate.setOtherName(form.getOtherName());
            certificate.setOtherIdcode(form.getOtherIdcode());
        }
        if(!certificateValidationService.canEditContent(user, EntityUtil.getCode(certificate.getType()))) {
            boolean isHigherSchool = schoolService.schoolType(user.getSchoolId()).isHigher();
            certificate.setContent(certificateContentService.generate(certificate.getStudent(),
                    CertificateType.valueOf(form.getType()), Boolean.TRUE.equals(form.getAddOutcomes()),
                    isHigherSchool));
        }
        return save(user, certificate, form);
    }

    /**
     * Update certificate
     *
     * @param user
     * @param certificate
     * @param form
     * @return
     */
    public Certificate save(HoisUserDetails user, Certificate certificate, CertificateForm form) {
        certificate.setHeadline(form.getHeadline());
        certificate.setSignatoryName(form.getSignatoryName());
        certificate.setSignatoryIdcode(form.getSignatoryIdcode());
        if(certificateValidationService.canEditContent(user, EntityUtil.getCode(certificate.getType()))) {
            certificate.setContent(form.getContent());
        } 
        return EntityUtil.save(certificate, em);
    }

    /**
     * Delete certificate
     *
     * @param user
     * @param certificate
     */
    public void delete(HoisUserDetails user, Certificate certificate) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(certificate, em);
    }

    /**
     * Lookup student for certificate
     *
     * @param user
     * @param id
     * @param idcode
     * @return null if idcode is null or person with given idcode is not found
     * @throws EntityNotFoundException when id is not null and student is not found
     */
    public StudentSearchDto otherStudent(HoisUserDetails user, Long id, String idcode) {
        if(user.isStudent()) {
            id = user.getStudentId();
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s join person p on s.person_id = p.id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if(user.isRepresentative()) {
            // check it has rights to see given student
            qb.requiredCriteria("s.id in (select sr.student_id from student_representative sr where sr.person_id = :personId and sr.is_student_visible = true)", "personId", user.getPersonId());
        }
        if(id != null) {
            qb.requiredCriteria("s.id = :id", "id", id);
        } else {
            if(!StringUtils.hasText(idcode)) {
                return null;
            }

            qb.requiredCriteria("p.idcode = :idcode", "idcode", idcode);
        }

        List<?> students = qb.select("s.id, p.idcode, p.firstname, p.lastname, s.status_code", em).setMaxResults(1).getResultList();
        if(!students.isEmpty()) {
            Object student = students.get(0);
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(student, 0));
            dto.setIdcode(resultAsString(student, 1));
            dto.setFullname(PersonUtil.fullname(resultAsString(student, 2), resultAsString(student, 3)));
            dto.setStatus(resultAsString(student, 4));
            return dto;
        } else if(id != null) {
            throw new EntityNotFoundException();
        }

        Person person = personRepository.findByIdcode(idcode);
        if(person == null) {
            return null;
        }

        StudentSearchDto dto = new StudentSearchDto();
        dto.setIdcode(idcode);
        dto.setFullname(person.getFullname());
        return dto;
    }

    private void setSignatory(CertificateForm form, Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_coordinator").sort("id");
        qb.requiredCriteria("school_id = :schoolId", "schoolId", schoolId);
        qb.filter("is_certificate_default = true");

        List<?> data = qb.select("name, idcode", em).setMaxResults(1).getResultList();
        if(data.isEmpty()) {
            throw new ValidationFailedException("certificate.signatoriesMissing");
        }
        form.setSignatoryName(resultAsString(data.get(0), 0));
        form.setSignatoryIdcode(resultAsString(data.get(0), 1));
    }

    public List<DirectiveCoordinatorDto> signatories(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_coordinator").sort("name");
        qb.requiredCriteria("school_id = :schoolId", "schoolId", schoolId);
        qb.filter("is_certificate = true");

        List<?> data = qb.select("name, idcode", em).getResultList();
        if(data.isEmpty()) {
            throw new ValidationFailedException("certificate.signatoriesMissing");
        }
        return StreamUtil.toMappedList(d -> {
            DirectiveCoordinatorDto dto = new DirectiveCoordinatorDto();
            dto.setName(resultAsString(d, 0));
            dto.setIdcode(resultAsString(d, 1));
            return dto;
        }, data);
    }

    private void setCertificateStatus(Certificate certificate, CertificateStatus status) {
        certificate.setStatus(em.getReference(Classifier.class, status.name()));
    }
}
