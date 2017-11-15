package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectConnect;
import ee.hitsa.ois.domain.subject.SubjectLanguage;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.SubjectForm;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.SubjectSearchDto;

@Transactional
@Service
public class SubjectService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ClassifierRepository classifierRepository;

    public Subject create(HoisUserDetails user, SubjectForm newSubject) {
        Subject subject = new Subject();
        subject.setStatus(em.getReference(Classifier.class, SubjectStatus.AINESTAATUS_S.name()));
        return save(user, subject, newSubject);
    }

    public Subject save(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        EntityUtil.bindToEntity(newSubject, subject, classifierRepository, "status");

        subject.setSchool(em.getReference(School.class, user.getSchoolId()));
        SchoolDepartment schoolDepartment = null;
        if (newSubject.getSchoolDepartment() != null && newSubject.getSchoolDepartment().getId() != null && newSubject.getSchoolDepartment().getId().longValue() > 0) {
            schoolDepartment = em.getReference(SchoolDepartment.class, newSubject.getSchoolDepartment().getId());
        }
        subject.setSchoolDepartment(schoolDepartment);
        EntityUtil.bindEntityCollection(subject.getSubjectLanguages(), language -> EntityUtil.getCode(language.getLanguage()), newSubject.getLanguages(), code -> {
            SubjectLanguage subjectLanguage = new SubjectLanguage();
            subjectLanguage.setSubject(subject);
            subjectLanguage.setLanguage(EntityUtil.validateClassifier(classifierRepository.getOne(code), MainClassCode.OPPEKEEL));
            return subjectLanguage;
        });
        bindConnections(subject, newSubject);
        return EntityUtil.save(subject, em);
    }

    private void bindConnections(Subject target, SubjectForm source) {
        Set<Long> subjectIds = new HashSet<>();
        Collection<Long> mandatory = StreamUtil.toMappedSet(EntityConnectionCommand::getId, source.getMandatoryPrerequisiteSubjects());
        Collection<Long> recommended = StreamUtil.toMappedSet(EntityConnectionCommand::getId, source.getRecommendedPrerequisiteSubjects());
        Collection<Long> substitute = StreamUtil.toMappedSet(EntityConnectionCommand::getId, source.getSubstituteSubjects());

        subjectIds.addAll(mandatory);
        subjectIds.addAll(recommended);
        subjectIds.addAll(substitute);
        List<Subject> subjects = subjectRepository.findAll(subjectIds);

        Set<SubjectConnect> connections = target.getSubjectConnections();
        Set<SubjectConnect> newConnections = new HashSet<>();

        bindSubjectConnect(target, classifierRepository.getOne(SubjectConnection.AINESEOS_EK.name()), connections, newConnections, subjects.stream().filter(it -> mandatory.contains(it.getId())).collect(Collectors.toSet()));
        bindSubjectConnect(target, classifierRepository.getOne(SubjectConnection.AINESEOS_EV.name()), connections, newConnections, subjects.stream().filter(it -> recommended.contains(it.getId())).collect(Collectors.toSet()));
        bindSubjectConnect(target, classifierRepository.getOne(SubjectConnection.AINESEOS_A.name()), connections, newConnections, subjects.stream().filter(it -> substitute.contains(it.getId())).collect(Collectors.toSet()));

        List<Long> ids = new ArrayList<>();
        ids.add(target.getId());
        for (SubjectConnect subjectConnect : newConnections) {
            Long id = EntityUtil.getId(subjectConnect.getConnectSubject());
            if (ids.contains(id)) {
                throw new ValidationFailedException(EntityUtil.getCode(subjectConnect.getConnection()), "same-subject-multipile");
            }
            ids.add(id);
        }

        target.setSubjectConnections(newConnections);
    }

    private static void bindSubjectConnect(Subject primarySubject, Classifier connectionType, Set<SubjectConnect> connections, Set<SubjectConnect> newConnections, Collection<Subject> connectSubjects) {
        // TODO use EntityUtil.bindEntityCollection
        Map<Long, SubjectConnect> m = connections.stream()
                .filter(it -> Objects.equals(EntityUtil.getCode(it.getConnection()), EntityUtil.getCode(connectionType)))
                .collect(Collectors.toMap(k -> EntityUtil.getId(k.getConnectSubject()), v -> v));
        for (Subject connected : connectSubjects) {
            SubjectConnect sc = m.get(connected.getId());
            if (sc != null) {
                newConnections.add(sc);
            } else {
                newConnections.add(new SubjectConnect(primarySubject, connected, connectionType));
            }
        }
    }

    /**
     *  Curricula OR CurriculaVersion
     */
    public Page<SubjectSearchDto> search(Long schoolId, SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        return subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
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

            Collection<Long> curricula = subjectSearchCommand.getCurricula();
            Collection<Long> curriculaVersion = subjectSearchCommand.getCurriculaVersion();
            if (!CollectionUtils.isEmpty(curricula) || !CollectionUtils.isEmpty(curriculaVersion)) {
                Subquery<Long> curriculaQuery = query.subquery(Long.class);
                Root<CurriculumVersion> curriculumVersionRoot = curriculaQuery.from(CurriculumVersion.class);
                curriculaQuery = curriculaQuery
                        .select(curriculumVersionRoot.join("modules").join("subjects").get("subject").get("id"));

                if (!CollectionUtils.isEmpty(curriculaVersion)) {
                    curriculaQuery = curriculaQuery.where(curriculumVersionRoot.get("id").in(curriculaVersion));
                } else {
                    curriculaQuery = curriculaQuery.where(curriculumVersionRoot.get("curriculum").get("id").in(curricula));
                }
                filters.add(root.get("id").in(curriculaQuery));
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

    public void delete(HoisUserDetails user, Subject subject) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(subject, em);
    }

    public boolean isCodeUnique(Long schoolId, UniqueCommand command) {
        if(command.getId() == null) {
            return !subjectRepository.existsBySchoolIdAndCode(schoolId, command.getParamValue());
        }
        return !subjectRepository.existsBySchoolIdAndCodeAndIdNot(schoolId, command.getParamValue(), command.getId());
    }

    public Subject saveAndConfirm(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        subject.setStatus(classifierRepository.getOne(SubjectStatus.AINESTAATUS_K.name()));
        return save(user, subject, newSubject);
    }

    public Subject saveAndUnconfirm(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        subject.setStatus(classifierRepository.getOne(SubjectStatus.AINESTAATUS_P.name()));
        return save(user, subject, newSubject);
    }
}
