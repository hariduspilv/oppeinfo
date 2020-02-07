package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.service.curriculum.CurriculumSearchService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.PublicDataMapper;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodDto;

@Transactional
@Service
public class PublicDataService {

    @Autowired
    private EntityManager em;
    @Autowired
    private AcademicCalendarService academicCalendarService;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private CurriculumSearchService curriculumSearchService;
    @Autowired
    private StudyMaterialService studyMaterialService;
    @Autowired
    private SubjectService subjectService;

    // TODO: forbidden school public data
    private static final Long FORBIDDEN_SCHOOL_ID = Long.valueOf(26);

    public AcademicCalendarDto academicCalendar(Long schoolId) {
        if (isForbiddenSchool(schoolId)) {
            return null;
        }
        return academicCalendarService.academicCalendar(schoolId);
    }

    public Object curriculum(Long curriculumId) {
        Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
        assertVisibleToPublic(curriculum);
        return new PublicDataMapper(Language.ET).map(curriculum);
    }

    public Object curriculumVersion(Long curriculumId, Long curriculumVersionId) {
        CurriculumVersion curriculumVersion = em.getReference(CurriculumVersion.class, curriculumVersionId);
        if(curriculumId == null || !curriculumId.equals(EntityUtil.getId(curriculumVersion.getCurriculum()))) {
            throw new EntityNotFoundException();
        }
        if(!CurriculumUtil.isCurriculumVersionConfirmed(curriculumVersion)) {
            throw new EntityNotFoundException();
        }
        return new PublicDataMapper(Language.ET).map(curriculumVersion);
    }

    public Object subject(Long subjectId) {
        Subject s = em.getReference(Subject.class, subjectId);
        assertVisibleToPublic(s);
        return new PublicDataMapper(Language.ET).map(s);
    }

    private static void assertVisibleToPublic(Curriculum curriculum) {
        if(isForbiddenSchool(EntityUtil.getId(curriculum.getSchool()))
                || !ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, curriculum.getStatus())) {
            throw new EntityNotFoundException();
        }
    }

    @SuppressWarnings("unchecked")
    public Page<CurriculumSearchDto> curriculumSearch(CurriculumSearchCommand criteria, Pageable pageable) {
        return JpaQueryUtil.query(CurriculumSearchDto.class, Curriculum.class, (root, query, cb) -> {
            ((CriteriaQuery<CurriculumSearchDto>)query).select(cb.construct(CurriculumSearchDto.class,
                root.get("id"), root.get("nameEt"), root.get("nameEn"),
                root.get("credits"), root.get("validFrom"), root.get("validThru"), root.get("higher"),
                root.get("status").get("code"), root.get("origStudyLevel").get("code"),
                root.get("school").get("id"), root.get("school").get("nameEt"), root.get("school").get("nameEn"), 
                root.get("ehisStatus").get("code"), root.get("code"), root.get("merCode")));

            List<Predicate> filters = new ArrayList<>();

            // only confirmed
            filters.add(cb.equal(root.get("status").get("code"), CurriculumStatus.OPPEKAVA_STAATUS_K.name()));
            // TODO: forbidden school public data
            filters.add(cb.notEqual(root.get("school").get("id"), FORBIDDEN_SCHOOL_ID));

            String nameField = Language.EN.equals(criteria.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, criteria.getName(), filters::add);
            propertyContains(() -> root.get("code"), cb, criteria.getCode(), filters::add);

            if(!CollectionUtils.isEmpty(criteria.getStudyLevel())) {
                filters.add(root.get("origStudyLevel").get("code").in(criteria.getStudyLevel()));
            }

            List<Long> curriculumSchools = curriculumSearchService.getSchools(null, criteria.getSchool());
            if(!curriculumSchools.isEmpty()) {
                filters.add(curriculumSearchService.filterBySchools(root, query, cb, curriculumSchools, criteria.getIsPartnerSchool()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);
    }
    
    public StateCurriculumDto stateCurriculum(StateCurriculum stateCurriculum) {
        assertVisibleToPublic(stateCurriculum);
        StateCurriculumDto dto = StateCurriculumDto.of(stateCurriculum);
        dto.setCurricula(StreamUtil.toMappedSet(CurriculumSearchDto::forStateCurriculumForm, 
                stateCurriculum.getCurricula().stream()
                    .filter(c -> ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, c.getStatus()))));
        return dto;
    }

    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId) {
        if (isForbiddenSchool(schoolId)) {
            return Collections.emptyList();
        }
        return autocompleteService.schoolDepartments(schoolId);
    }

    public List<CurriculumVersionResult> curriculumVersions(Long schoolId) {
        if (isForbiddenSchool(schoolId)) {
            return Collections.emptyList();
        }
        CurriculumVersionAutocompleteCommand lookup = new CurriculumVersionAutocompleteCommand();
        lookup.setHigher(Boolean.TRUE);
        lookup.setValid(Boolean.TRUE);
        return autocompleteService.curriculumVersions(schoolId, lookup);
    }

    public Page<SubjectSearchDto> searchSubjects(SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        if (isForbiddenSchool(subjectSearchCommand.getSchoolId())) {
            return new PageImpl<>(Collections.emptyList());
        }
        return subjectService.search(null, subjectSearchCommand, pageable);
    }

    private static void assertVisibleToPublic(StateCurriculum stateCurriculum) {
        if(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, stateCurriculum.getStatus())) {
            throw new EntityNotFoundException();
        }
    }

    public SubjectDto subjectView(Subject subject) {
        assertVisibleToPublic(subject);
        return SubjectDto.forPublic(subject, em.createQuery("select cvms.module.curriculumVersion from CurriculumVersionHigherModuleSubject cvms"
                + " where cvms.subject.id = ?1 and cvms.module.curriculumVersion.status.code = ?2", CurriculumVersion.class)
                .setParameter(1, subject.getId())
                .setParameter(2, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name())
                .getResultList());
    }

    private static void assertVisibleToPublic(Subject subject) {
        if (isForbiddenSchool(EntityUtil.getId(subject.getSchool())) || !SubjectUtil.isActive(subject)) {
            throw new EntityNotFoundException();
        }
    }

    public JournalDto journal(Journal journal) {
        assertVisibleToPublic(journal);
        return studyMaterialService.getJournal(null, journal);
    }

    public List<StudyMaterialSearchDto> journalMaterials(Journal journal) {
        assertVisibleToPublic(journal);
        return studyMaterialService.materials(null, journal, null);
    }

    public SubjectStudyPeriodDto subjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        assertVisibleToPublic(subjectStudyPeriod.getSubject());
        return studyMaterialService.getSubjectStudyPeriod(null, subjectStudyPeriod);
    }

    public List<StudyMaterialSearchDto> subjectStudyPeriodMaterials(SubjectStudyPeriod subjectStudyPeriod) {
        assertVisibleToPublic(subjectStudyPeriod.getSubject());
        return studyMaterialService.materials(null, null, subjectStudyPeriod);
    }

    private static void assertVisibleToPublic(Journal journal) {
        if (isForbiddenSchool(journal.getSchool())) {
            throw new EntityNotFoundException();
        }
    }

    public Object subjectProgram(SubjectProgram program) {
        return new PublicDataMapper(Language.ET).map(program);
    }

    // TODO: forbidden school public data
    private static boolean isForbiddenSchool(Long schoolId) {
        return FORBIDDEN_SCHOOL_ID.equals(schoolId);
    }

    private static boolean isForbiddenSchool(School school) {
        return isForbiddenSchool(EntityUtil.getId(school));
    }

}
