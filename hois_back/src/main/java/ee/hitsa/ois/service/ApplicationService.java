package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.commandobject.ApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ApplicationDto;

@Transactional
@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;


    public Page<ApplicationDto> search(ApplicationSearchCommand criteria, Pageable pageable) {
        return applicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (criteria.getEhisSchool() != null) {
                filters.add(cb.equal(root.get("ehisSchool").get("code"), criteria.getEhisSchool()));
            }
            if(!CollectionUtils.isEmpty(criteria.getType())) {
                filters.add(root.get("type").get("code").in(criteria.getType()));
            }
            if(criteria.getInsertedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("insertedFrom"), criteria.getInsertedFrom()));
            }
            if(criteria.getInsertedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("insertedThru"), criteria.getInsertedThru()));
            }
            if(criteria.getSubmitedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("submitedFrom"), criteria.getSubmitedFrom()));
            }
            if(criteria.getSubmitedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("submitedThru"), criteria.getSubmitedThru()));
            }
            if(!StringUtils.isEmpty(criteria.getStatus())) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatus()));
            }
            if (criteria.getStudent() != null) {
                filters.add(cb.equal(root.get("student").get("id"), criteria.getStudent()));
            }
            if(!StringUtils.isEmpty(criteria.getStudentName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("student").get("person").get("firstname"), cb, criteria.getStudentName(), name::add);
                propertyContains(() -> root.get("student").get("person").get("lastname"), cb, criteria.getStudentName(), name::add);
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }
            if(!StringUtils.isEmpty(criteria.getStudentIdCode())) {
                filters.add(cb.equal(root.get("student").get("person").get("idcode"), criteria.getStudentIdCode()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(ApplicationDto::of);
    }

    public ApplicationDto save(HoisUserDetails user, Application application, ApplicationForm applicationForm) {
        EntityUtil.bindToEntity(applicationForm, application, classifierRepository, "student", "files", "studyPeriodStart", "studyPeriodStart");
        EntityUtil.setEntityFromRepository(applicationForm, application, studyPeriodRepository, "studyPeriodStart", "studyPeriodEnd");

        application.setEhisSchool(schoolRepository.getOne(user.getSchoolId()).getEhisSchool());
        application.setStudent(studentRepository.getOne(applicationForm.getStudent().getId()));
        updateFiles(application, applicationForm);
        return ApplicationDto.of(applicationRepository.save(application));
    }

    private void updateFiles(Application application, ApplicationForm applicationForm) {
        List<ApplicationFile> files = applicationForm.getFiles().stream().map(it -> {
            ApplicationFile file = new ApplicationFile();
            file.setOisFile(EntityUtil.bindToEntity(it.getOisFile(), new OisFile()));
            return file;
        }).collect(Collectors.toList());
        application.getFiles().clear();
        application.getFiles().addAll(files);
    }

    public void delete(Application application) {
        EntityUtil.deleteEntity(applicationRepository, application);
    }

}
