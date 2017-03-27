package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.repository.CertificateRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class CertificateService {
    
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public Page<CertificateSearchDto> search(Long schoolId, CertificateSearchCommand criteria, Pageable pageable) {
        return certificateRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            
            if (schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            LocalDate insertedFrom = criteria.getInsertedFrom();
            if(insertedFrom != null) {
              filters.add(cb.greaterThanOrEqualTo(root.get("inserted"), DateUtils.firstMomentOfDay(insertedFrom)));
            }
            LocalDate insertedThru = criteria.getInsertedThru();
            if(insertedThru != null) {
              filters.add(cb.lessThanOrEqualTo(root.get("inserted"), DateUtils.lastMomentOfDay(insertedThru)));
            }
            if(!CollectionUtils.isEmpty(criteria.getType())) {
                filters.add(root.get("type").get("code").in(criteria.getType()));
            }
            propertyContains(() -> root.get("headline"), cb, criteria.getHeadline(), filters::add);
            propertyContains(() -> root.get("certificateNr"), cb, criteria.getCertificateNr(), filters::add);
            
            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("student").get("person").get("idcode"), criteria.getIdcode()));
            }
            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("student").get("person").get("firstname"), cb, criteria.getName(), name::add);
            propertyContains(() -> root.get("student").get("person").get("lastname"), cb, criteria.getName(), name::add);
            if(!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(CertificateSearchDto::of);
    }

    public Certificate create(HoisUserDetails user, CertificateForm form) {
        Certificate certificate = new Certificate();
        certificate.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return save(certificate, form);
    }

    public Certificate save(Certificate certificate, CertificateForm form) {
        EntityUtil.bindToEntity(form, certificate, classifierRepository, "student");
        certificate.setStudent(EntityUtil.getOptionalOne(Student.class, form.getStudent(), em));
        return certificateRepository.save(certificate);
    }

    public void delete(Certificate certificate) {
        EntityUtil.deleteEntity(certificateRepository, certificate);        
    }
    
    public StudentSearchDto getOtherPerson(Long schoolId, String idcode) {
        Person person = personRepository.findByIdcode(idcode);
        List<Student> students = new ArrayList<>();
        if(person != null) {
            students = studentRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                
                if (schoolId != null) {
                    filters.add(cb.equal(root.get("school").get("id"), schoolId));
                }
                filters.add(cb.equal(root.get("person").get("id"), person.getId()));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            });
        }
        
        StudentSearchDto dto;
        
        if(!students.isEmpty()) {
            dto = StudentSearchDto.of(students.get(0));
        } else {
            if(person == null) {
                return null;
            }
            dto = new StudentSearchDto();
            dto.setIdcode(idcode);
            dto.setFullname(person.getFullname());
        }
        return dto;
    }
}
