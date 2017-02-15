package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.domain.SubjectConnect;
import ee.hitsa.ois.domain.SubjectLanguage;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectForm;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.util.EntityUtil;

@Transactional
@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private SchoolDepartmentRepository schoolDepartmentRepository;

    public Subject save(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        // TODO remove comment when status change logic is resolved
        EntityUtil.bindToEntity(newSubject, subject, classifierRepository /*, "status"*/);
        subject.setSchool(user.getSchool());
        SchoolDepartment schoolDepartment = null;
        if (newSubject.getSchoolDepartment() != null && newSubject.getSchoolDepartment() > 0) {
            schoolDepartment = schoolDepartmentRepository.findOne(newSubject.getSchoolDepartment());
        }
        subject.setSchoolDepartment(schoolDepartment);
        EntityUtil.bindClassifierCollection(subject.getSubjectLanguages(), language -> EntityUtil.getCode(language.getLanguage()), newSubject.getLanguages(), code -> {
            Classifier language = classifierRepository.getOne(code);
            if (!MainClassCode.OPPEKEEL.name().equals(language.getMainClassCode())) {
                throw new IllegalArgumentException("Wrong classifier code: " + language.getMainClassCode());
            }
            SubjectLanguage subjectLanguage = new SubjectLanguage();
            subjectLanguage.setSubject(subject);
            subjectLanguage.setLanguage(language);
            return subjectLanguage;
        });
        bindConnections(subject, newSubject);
        return subjectRepository.save(subject);
    }

    private void bindConnections(Subject target, SubjectForm source) {
        Set<SubjectConnect> connections = target.getSubjectConnections();
        Set<SubjectConnect> newConnections = new HashSet<>();

        bindSubjectConnect(target, classifierRepository.getOne(SubjectConnection.AINESEOS_EK.name()), connections, newConnections, source.getMandatoryPrerequisiteSubjects());
        bindSubjectConnect(target, classifierRepository.getOne(SubjectConnection.AINESEOS_EV.name()), connections, newConnections, source.getRecommendedPrerequisiteSubjects());
        bindSubjectConnect(target, classifierRepository.getOne(SubjectConnection.AINESEOS_A.name()), connections, newConnections, source.getSubstituteSubjects());

        List<Long> ids = new ArrayList<>();
        ids.add(target.getId());
        for (SubjectConnect subjectConnect : newConnections) {
            Long id = subjectConnect.getConnectSubject().getId();
            if (ids.contains(id)) {
                throw new ValidationFailedException(subjectConnect.getConnection().getCode(), "same-subject-multipile");
            }
            ids.add(id);
        }

        target.setSubjectConnections(newConnections);
    }

    private static void bindSubjectConnect(Subject primarySubject, Classifier connectionType, Set<SubjectConnect> connections, Set<SubjectConnect> newConnections, Set<Subject> connectSubjects) {
        Map<Long, SubjectConnect> m = connections.stream()
                .filter(it -> Objects.equals(it.getConnection().getCode(), connectionType.getCode()))
                .collect(Collectors.toMap(k -> k.getConnectSubject().getId(), v -> v));
        for (Subject connected : connectSubjects) {
            if (m.keySet().contains(connected.getId())) {
                newConnections.add(m.get(connected.getId()));
            } else {
                newConnections.add(new SubjectConnect(primarySubject, connected, connectionType));
            }
        }
    }

    // todo private Collection<Long> curricula;
    public Page<SubjectSearchDto> search(Long schoolId, SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        return subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            
            Collection<String> ehisSchools = subjectSearchCommand.getEhisSchools();

            if (schoolId != null && CollectionUtils.isEmpty(ehisSchools)) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            } else if(!CollectionUtils.isEmpty(ehisSchools)) {
                filters.add(root.get("school").get("ehisSchool").get("code").in(ehisSchools));
            }

            if (!CollectionUtils.isEmpty(subjectSearchCommand.getDepartments())) {
                filters.add(root.get("schoolDepartment").get("id").in(subjectSearchCommand.getDepartments()));
            }

            Collection<String> languages = subjectSearchCommand.getLanguages();
            if (!CollectionUtils.isEmpty(languages)) {
                Subquery<Long> languageQuery = query.subquery(Long.class);
                Root<SubjectLanguage> languageRoot = languageQuery.from(SubjectLanguage.class);
                languageQuery = languageQuery.select(languageRoot.get("subject").get("id")).where(languageRoot.get("language").get("code").in(languages));
                filters.add(root.get("id").in(languageQuery));
            }

            if (!CollectionUtils.isEmpty(subjectSearchCommand.getAssessments())) {
                filters.add(root.get("assessment").get("code").in(subjectSearchCommand.getAssessments()));
            }

            if (!CollectionUtils.isEmpty(subjectSearchCommand.getStatus())) {
                filters.add(root.get("status").get("code").in(subjectSearchCommand.getStatus()));
            }

            if (subjectSearchCommand.getFrom() != null && !Objects.equals(subjectSearchCommand.getFrom(), BigDecimal.ZERO)) {
                filters.add(cb.ge(root.get("credits"), subjectSearchCommand.getFrom()));
            }

            if (subjectSearchCommand.getThru() != null && !Objects.equals(subjectSearchCommand.getThru(), BigDecimal.ZERO)) {
                filters.add(cb.le(root.get("credits"), subjectSearchCommand.getThru()));
            }

            propertyContains(() -> root.get("code"), cb, subjectSearchCommand.getCode(), filters::add);

            String nameField = Language.EN.equals(subjectSearchCommand.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, subjectSearchCommand.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SubjectSearchDto::of);
    }

    public void delete(Subject subject) {
        EntityUtil.deleteEntity(subjectRepository, subject);
    }
}
