package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.repository.CertificateRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.commandobject.CertificateSearchCommand;
import ee.hitsa.ois.web.dto.CertificateSearchDto;

@Transactional
@Service
public class CertificateService {
    
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudentRepository studentRepository;
    
    public Page<CertificateSearchDto> search(Long schoolId, CertificateSearchCommand criteria, Pageable pageable) {
        return certificateRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            
            if (schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            LocalDateTime insertedFrom = criteria.getInsertedFrom();
            if(insertedFrom != null) {
              filters.add(cb.greaterThanOrEqualTo(root.get("inserted"), insertedFrom));
            }
            LocalDateTime insertedThru = criteria.getInsertedThru();
            if(insertedThru != null) {
              insertedThru = LocalDateTime.of(insertedThru.toLocalDate(), LocalTime.of(0, 0, 0)).plusDays(1).minusNanos(1);
              filters.add(cb.lessThanOrEqualTo(root.get("inserted"), insertedThru));
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

    public Certificate save(Certificate certificate, CertificateForm form) {
        EntityUtil.bindToEntity(form, certificate, classifierRepository, "student");
        if(form.getStudent() != null) {
            certificate.setStudent(studentRepository.findOne(form.getStudent()));
        }
        return certificateRepository.save(certificate);
    }

    public void delete(Certificate certificate) {
        EntityUtil.deleteEntity(certificateRepository, certificate);        
    }
}
