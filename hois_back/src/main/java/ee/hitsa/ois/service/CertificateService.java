package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateStatus;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class CertificateService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CertificateContentService certificateContentService;
    
    private static final String CERTIFICATE_FROM = "from certificate c "
            + "left outer join student s on s.id = c.student_id "
            + "left outer join person p on p.id = s.person_id "
            + "inner join classifier type on c.type_code = type.code ";

    private static final String CERTIFICATE_SELECT = "c.id, c.type_code, c.certificate_nr, c.headline, c.whom, c.inserted, "
            + "case when c.student_id is not null then p.firstname || ' ' || p.lastname "
            + "else c.other_name end as name, c.student_id, "
            + "case when c.student_id is not null then p.lastname || ' ' || p.firstname "
            + "else split_part(c.other_name, ' ', 2) || ' ' || split_part(c.other_name, ' ', 1) "
            + "end as sortablename ";

    public Page<CertificateSearchDto> search(HoisUserDetails user, CertificateSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(CERTIFICATE_FROM).sort(pageable);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id = :studentId", "studentId", user.getStudentId());
        qb.optionalContains("c.headline", "headline", criteria.getHeadline());
        qb.optionalContains("c.certificate_nr", "certificate_nr", criteria.getCertificateNr());
        qb.optionalCriteria("c.type_code in (:type)", "type", criteria.getType());
        qb.optionalCriteria("(p.idcode = :idcode OR c.other_idcode = :idcode)", "idcode", criteria.getIdcode());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname", "c.other_name"), "name", criteria.getName());
        qb.optionalCriteria("c.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("c.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);
        Page<Object[]> result = JpaQueryUtil.pagingResult(qb, CERTIFICATE_SELECT, em, pageable);

        return result.map(r -> {
            CertificateSearchDto dto = new CertificateSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setCertificateNr(resultAsString(r, 2));
            dto.setHeadline(resultAsString(r, 3));
            dto.setWhom(resultAsString(r, 4));
            dto.setInserted(JpaQueryUtil.resultAsLocalDateTime(r, 5));  //TODO: make as LocalDate
            dto.setStudentFullname(resultAsString(r, 6));
            dto.setStudentId(resultAsLong(r, 7));
            return dto;
        });
    }

    public Certificate create(HoisUserDetails user, CertificateForm form) {
        Certificate certificate = new Certificate();
        certificate.setSchool(em.getReference(School.class, user.getSchoolId()));
        certificate.setStatus(classifierRepository.getOne(CertificateStatus.TOEND_STAATUS_T.name()));
        return save(certificate, form);
    }

    public Certificate save(Certificate certificate, CertificateForm form) {
        EntityUtil.bindToEntity(form, certificate, classifierRepository, "student", "status");
        certificate.setStudent(EntityUtil.getOptionalOne(Student.class, form.getStudent(), em));
        if(!CertificateType.isOther(EntityUtil.getCode(certificate.getType()))) {
            certificate.setContent(certificateContentService.generateFor(certificate));
        }
        return EntityUtil.save(certificate, em);
    }

    public void delete(Certificate certificate) {
        EntityUtil.deleteEntity(certificate, em);
    }

    public StudentSearchDto getOtherPerson(Long schoolId, String idcode) {
        Person person = personRepository.findByIdcode(idcode);
        if(person == null) {
            return null;
        }

        // TODO refactor as only first result is used
        List<Student> students = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            filters.add(cb.equal(root.get("person").get("id"), person.getId()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        if(!students.isEmpty()) {
            return StudentSearchDto.of(students.get(0));
        }

        StudentSearchDto dto = new StudentSearchDto();
        dto.setIdcode(idcode);
        dto.setFullname(person.getFullname());
        return dto;
    }
}
