package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.StudentUtil;
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
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.CertificateController.OtherStudentCommand;
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
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left outer join person p on p.id = s.person_id "
            + "inner join classifier type on c.type_code = type.code "
            + "join classifier status on status.code = c.status_code ";

    private static final String CERTIFICATE_SELECT = "c.id, c.type_code, c.certificate_nr, c.headline, c.whom, c.inserted, "
            + "case when c.student_id is not null then p.firstname || ' ' || p.lastname "
            + "else c.other_name end as name, c.student_id, "
            + "case when c.student_id is not null then p.lastname || ' ' || p.firstname "
            + "else split_part(c.other_name, ' ', 2) || ' ' || split_part(c.other_name, ' ', 1) "
            + "end as sortablename, c.status_code, sg.code, case when c.student_id is not null then s.type_code else null end as studentType";

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
        qb.optionalCriteria("c.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("sg.id in (:studentGroups)", "studentGroups", criteria.getStudentGroups());

        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cur.id from curriculum_version cv join curriculum cur on cur.id = cv.curriculum_id"
                            + " where s.curriculum_version_id = cv.id and cur.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

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
            dto.setStudentFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 6), resultAsString(r, 11)));
            dto.setStudentId(resultAsLong(r, 7));
            dto.setStatus(resultAsString(r, 9));
            dto.setStudentGroup(resultAsString(r, 10));
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
            SchoolType schoolType = schoolService.schoolType(user.getSchoolId());
            boolean isHigherSchool = schoolType.isHigher();
            Language lang = Boolean.FALSE.equals(form.getEstonian()) ? Language.EN : Language.ET;
            certificate.setContent(certificateContentService.generate(certificate.getStudent(),
                    CertificateType.valueOf(form.getType()), Boolean.TRUE.equals(form.getShowModules()),
                    Boolean.TRUE.equals(form.getAddOutcomes()), Boolean.TRUE.equals(form.getShowUncompleted()),
                    Boolean.TRUE.equals(form.getStateGradingSchema()), isHigherSchool, lang));
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
        if (Boolean.TRUE.equals(certificate.getSchool().getIsWithoutEkis())) {
            certificate.setCertificateNr(form.getCertificateNr());
            if (user.isSchoolAdmin()) {
                certificate.setWhom(form.getWhom());
            }
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
     * @param command
     * @return null if idcode is null or person with given idcode is not found
     * @throws EntityNotFoundException when id is not null and student is not found
     */
    public StudentSearchDto otherStudent(HoisUserDetails user, OtherStudentCommand command) throws HoisException {
        JpaQueryBuilder<Student> qb = new JpaQueryBuilder<>(Student.class, "s", "join s.person p");
        qb.requiredCriteria("s.school.id = :schoolId", "schoolId", user.getSchoolId());

        if (user.isRepresentative()) {
            // check it has rights to see given student
            qb.requiredCriteria("s.id in (select sr.student.id from StudentRepresentative sr"
                    + " where sr.person.id = :personId and sr.isStudentVisible = true)",
                    "personId", user.getPersonId());
        }
        if (command.getId() != null) {
            qb.requiredCriteria("s.id = :id", "id", command.getId());
        } else if (StringUtils.hasText(command.getIdcode())) {
            qb.requiredCriteria("s.person.idcode = :idcode", "idcode", command.getIdcode());
        } else {
            return null;
        }

        List<Student> students = qb.select(em).getResultList();
        // if there is a guest student and regular student with the same idcode,
        // filter out the guest student
        // if after filtering the list is empty, all students were guest students
        if (Boolean.TRUE.equals(command.getHideGuestStudents()) && !students.isEmpty()) {
            students = students.stream().filter(p -> !ClassifierUtil.equals(StudentType.OPPUR_K, p.getType())).collect(Collectors.toList());
            if (students.isEmpty()) {
                throw new HoisException("student.error.cannotBeGuestStudent");
            }
        }
        if (!students.isEmpty()) {
            Student student = students.get(0);
            Person person = student.getPerson();
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(student.getId());
            dto.setIdcode(person.getIdcode());
            dto.setFullname(PersonUtil.fullname(person));
            dto.setStatus(EntityUtil.getCode(student.getStatus()));
            dto.setType(EntityUtil.getCode(student.getType()));
            dto.setHigher(Boolean.valueOf(StudentUtil.isHigher(student)));
            return dto;
        } else if (command.getId() != null) {
            throw new EntityNotFoundException();
        }

        Person person = personRepository.findByIdcode(command.getIdcode());
        if(person == null) {
            return null;
        }

        StudentSearchDto dto = new StudentSearchDto();
        dto.setIdcode(command.getIdcode());
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

    public Certificate complete(Certificate certificate) {
        setCertificateStatus(certificate, CertificateStatus.TOEND_STAATUS_V);
        return certificate;
    }

    public Boolean hasOrderedCertificates(HoisUserDetails user) {
        return Boolean.valueOf(!em.createNativeQuery("select c.id from certificate c where c.school_id = ?1 and c.status_code = ?2")
            .setParameter(1, user.getSchoolId())
            .setParameter(2, CertificateStatus.TOEND_STAATUS_T.name())
            .setMaxResults(1).getResultList().isEmpty());
    }
}
