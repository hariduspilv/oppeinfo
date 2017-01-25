package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SubjectConnect;
import ee.hitsa.ois.domain.SubjectLanguage;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SubjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;

@Transactional
@Service
public class SubjectService {

    @Autowired
    private SubjectRepository repository;

    @Autowired
    private ClassifierService classifierService;

    public Subject save(Subject subject, SubjectForm newSubject) {
        bindLanguages(subject, newSubject.getLanguages());
        bindConnections(subject, newSubject);
        return repository.save(subject);
    }

    private void bindConnections(Subject target, SubjectForm source) {
        Set<SubjectConnect> connections = target.getSubjectConnections();
        Set<SubjectConnect> newConnections = new HashSet<>();

        bindSubjectConnect(target, classifierService.findOne(SubjectConnection.AINESEOS_EK.name()), connections, newConnections, source.getMandatoryPrerequisiteSubjects());
        bindSubjectConnect(target, classifierService.findOne(SubjectConnection.AINESEOS_EV.name()), connections, newConnections, source.getRecommendedPrerequisiteSubjects());
        bindSubjectConnect(target, classifierService.findOne(SubjectConnection.AINESEOS_A.name()), connections, newConnections, source.getSubstituteSubjects());


        List<Long> ids = new ArrayList<>();
        ids.add(target.getId());
        for (SubjectConnect subjectConnect : newConnections) {
            Long id = subjectConnect.getConnectSubject().getId();
            if (ids.contains(id)) {
                throw new ValidationFailedException("subjectConnections", "same-subject-multipile");
            }
            ids.add(id);
        }

        target.setSubjectConnections(newConnections);
    }

    private void bindSubjectConnect(Subject primarySubject, Classifier connectionType, Set<SubjectConnect> connections, Set<SubjectConnect> newConnections, Set<Subject> connectSubjects) {
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

    private void bindLanguages(Subject target, Set<Classifier> languages) {
        Map<String, SubjectLanguage> codes = target.getSubjectLanguages().stream().collect(Collectors.toMap(
                e -> e.getLanguage().getCode(), e -> e
        ));
        Set<SubjectLanguage> newSet = new HashSet<>();
        for (Classifier language : languages) {
            if (codes.keySet().contains(language.getCode())) {
                newSet.add(codes.get(language.getCode()));
            } else {
                newSet.add(new SubjectLanguage(language, target));
            }
        }
        target.setSubjectLanguages(newSet);
    }

    public Subject findOne(Long id) {
        return repository.findOne(id);
    }

    // todo private Collection<Long> curricula;
    public Page<Subject> search(SubjectSearchCommand subjectSearchCommand, Long schoolId, Pageable pageable) {
        return repository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }

            if (subjectSearchCommand.getDepartments() != null) {
                filters.add(root.get("schoolDepartment").get("id").in(subjectSearchCommand.getDepartments()));
            }

            if (subjectSearchCommand.getLanguages() != null) {
                filters.add(root.join("subjectLanguages").get("language").get("code").in(subjectSearchCommand.getLanguages()));
            }

            if (subjectSearchCommand.getAssessments() != null) {
                filters.add(root.get("assessment").get("code").in(subjectSearchCommand.getAssessments()));
            }

            if (subjectSearchCommand.getStatus() != null) {
                filters.add(root.get("status").get("code").in(subjectSearchCommand.getStatus()));
            }

            if (subjectSearchCommand.getFrom() != null && !Objects.equals(subjectSearchCommand.getFrom(), BigDecimal.ZERO)) {
                filters.add(cb.ge(root.get("credits"), subjectSearchCommand.getFrom()));
            }

            if (subjectSearchCommand.getThru() != null && !Objects.equals(subjectSearchCommand.getThru(), BigDecimal.ZERO)) {
                filters.add(cb.le(root.get("credits"), subjectSearchCommand.getThru()));
            }

            if (StringUtils.hasText(subjectSearchCommand.getCode())) {
                filters.add(cb.equal(root.get("code"), subjectSearchCommand.getCode()));
            }

            String nameField = Language.EN.equals(subjectSearchCommand.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, subjectSearchCommand.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable);
    }

    public void delete(Subject subject) {
        repository.delete(subject);
    }
}
