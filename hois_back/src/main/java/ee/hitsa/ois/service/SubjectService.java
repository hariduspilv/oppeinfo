package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUserRights;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectForm;
import ee.hitsa.ois.web.commandobject.subject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectDto;
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
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    /**
     * Create new subject
     *
     * @param user
     * @param newSubject
     * @return
     */
    public Subject create(HoisUserDetails user, SubjectForm newSubject) {
        Subject subject = new Subject();
        setSubjectStatus(subject, SubjectStatus.AINESTAATUS_S);
        return save(user, subject, newSubject);
    }

    /**
     * Update subject
     *
     * @param user
     * @param subject
     * @param newSubject
     * @return
     */
    public Subject save(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        EntityUtil.setUsername(user.getUsername(), em);
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
            subjectLanguage.setLanguage(EntityUtil.validateClassifier(em.getReference(Classifier.class, code), MainClassCode.OPPEKEEL));
            return subjectLanguage;
        });
        bindConnections(subject, newSubject);
        return EntityUtil.save(subject, em);
    }

    /**
     * Search subjects
     *
     * @param user
     * @param subjectSearchCommand
     * @param pageable
     * @return
     */
    public Page<SubjectSearchDto> search(HoisUserDetails user, SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        Boolean canEdit = Boolean.valueOf(user != null && SubjectUserRights.hasPermissionToEdit(user));
        return subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (subjectSearchCommand.getSchoolId() != null) {
                filters.add(cb.equal(root.get("school").get("id"), subjectSearchCommand.getSchoolId()));
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
            if(user == null || !SubjectUserRights.canViewAllSubjects(user)) {
                subjectSearchCommand.setStatus(Collections.singletonList(SubjectStatus.AINESTAATUS_K.name()));
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
        }, pageable).map(subject -> {
            SubjectSearchDto dto = SubjectSearchDto.of(subject);
            dto.setCanEdit(canEdit);
            return dto;
        });
    }

    /**
     * Delete subject
     *
     * @param user
     * @param subject
     */
    public void delete(HoisUserDetails user, Subject subject) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(subject, em);
    }

    /**
     * Uniqueness check for subject
     *
     * @param schoolId
     * @param command
     * @return
     */
    public boolean isCodeUnique(Long schoolId, UniqueCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        String code = command.getParamValue();
        if(code != null) {
            qb.requiredCriteria("s.code = :code", "code", code);
        } else {
            qb.filter("s.code is null");
        }
        qb.optionalCriteria("s.id <> :id", "id", command.getId());
        return qb.select("s.id", em).setMaxResults(1).getResultList().isEmpty();
    }

    /**
     * Update subjct and set status to active
     *
     * @param user
     * @param subject
     * @param newSubject
     * @return
     */
    public Subject saveAndConfirm(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        setSubjectStatus(subject, SubjectStatus.AINESTAATUS_K);
        return save(user, subject, newSubject);
    }

    /**
     * Update subject and set status to passive
     *
     * @param user
     * @param subject
     * @param newSubject
     * @return
     */
    public Subject saveAndUnconfirm(HoisUserDetails user, Subject subject, SubjectForm newSubject) {
        setSubjectStatus(subject, SubjectStatus.AINESTAATUS_P);
        return save(user, subject, newSubject);
    }

    /**
     * Get subject data
     *
     * @param user
     * @param subject
     * @return
     */
    public SubjectDto get(HoisUserDetails user, Subject subject) {
        SubjectDto dto;
        if (user.isSchoolAdmin()) {
            dto = SubjectDto.of(subject, curriculumVersionRepository.findAllDistinctByModules_Subjects_Subject_id(subject.getId()));
        } else {
            dto = SubjectDto.forPublic(subject, curriculumVersionRepository.findAllDistinctByModules_Subjects_Subject_id(subject.getId()));
        }
        dto.setCanEdit(Boolean.valueOf(SubjectUserRights.canEdit(user, subject)));
        dto.setCanDelete(Boolean.valueOf(SubjectUserRights.canDelete(user, subject)));
        dto.setCanSetActive(Boolean.valueOf(SubjectUserRights.canSetActive(user, subject)));
        dto.setCanSetPassive(Boolean.valueOf(SubjectUserRights.canSetPassive(user, subject)));
        return dto;
    }

    /**
     * Find all subject by given ids
     *
     * @param subjectIds may be empty
     * @return
     */
    public List<Subject> findAllById(Collection<Long> subjectIds) {
        if(subjectIds.isEmpty()) {
            return Collections.emptyList();
        }

        return em.createQuery("select s from Subject s where s.id in (?1)", Subject.class)
                .setParameter(1, subjectIds)
                .getResultList();
    }

    private void bindConnections(Subject target, SubjectForm source) {
        Set<Long> subjectIds = new HashSet<>();
        Collection<Long> mandatory = StreamUtil.toMappedSet(EntityConnectionCommand::getId, source.getMandatoryPrerequisiteSubjects());
        Collection<Long> recommended = StreamUtil.toMappedSet(EntityConnectionCommand::getId, source.getRecommendedPrerequisiteSubjects());
        Collection<Long> substitute = StreamUtil.toMappedSet(EntityConnectionCommand::getId, source.getSubstituteSubjects());

        subjectIds.addAll(mandatory);
        subjectIds.addAll(recommended);
        subjectIds.addAll(substitute);
        List<Subject> subjects = findAllById(subjectIds);

        Set<SubjectConnect> connections = target.getSubjectConnections();
        Set<SubjectConnect> newConnections = new HashSet<>();

        bindSubjectConnect(target, SubjectConnection.AINESEOS_EK, connections, newConnections, subjects.stream().filter(it -> mandatory.contains(it.getId())).collect(Collectors.toSet()));
        bindSubjectConnect(target, SubjectConnection.AINESEOS_EV, connections, newConnections, subjects.stream().filter(it -> recommended.contains(it.getId())).collect(Collectors.toSet()));
        bindSubjectConnect(target, SubjectConnection.AINESEOS_A, connections, newConnections, subjects.stream().filter(it -> substitute.contains(it.getId())).collect(Collectors.toSet()));

        Set<Long> ids = new HashSet<>();
        ids.add(target.getId());
        for (SubjectConnect subjectConnect : newConnections) {
            Long id = EntityUtil.getId(subjectConnect.getConnectSubject());
            if (ids.contains(id)) {
                Map<Object, Object> params = new HashMap<>();
                params.put("type", new AutocompleteResult(null, subjectConnect.getConnection()));
                params.put("subject", AutocompleteResult.of(subjectConnect.getConnectSubject()));
                throw new ValidationFailedException("subject.alreadyConnectedOrSameSubject", params);
                //throw new ValidationFailedException(EntityUtil.getCode(subjectConnect.getConnection()), "same-subject-multipile");
            }
            ids.add(id);
        }

        target.setSubjectConnections(newConnections);
    }

    private void bindSubjectConnect(Subject primarySubject, SubjectConnection subjectConnection, Set<SubjectConnect> connections, Set<SubjectConnect> newConnections, Collection<Subject> connectSubjects) {
        Classifier connectionType = em.getReference(Classifier.class, subjectConnection.name());

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

    private void setSubjectStatus(Subject subject, SubjectStatus status) {
        subject.setStatus(em.getReference(Classifier.class, status.name()));
    }
}
