package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.StudyYearSchedule;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import ee.hitsa.ois.web.dto.timetable.SchoolPublicDataSettingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.service.curriculum.CurriculumSearchService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.dto.AcademicCalendarDto;
import ee.hitsa.ois.web.dto.PublicDataMapper;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;
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
    private SchoolService schoolService;
    @Autowired
    private StudyMaterialService studyMaterialService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudyYearScheduleService studyYearScheduleService;

    public SchoolPublicDataSettingsDto schoolPublicSettings(Long schoolId) {
        School school = em.getReference(School.class, schoolId);
        SchoolService.SchoolType schoolType = schoolService.schoolType(schoolId);

        SchoolPublicDataSettingsDto dto = new SchoolPublicDataSettingsDto();
        dto.setIsAcademicCalendarNotPublic(Boolean.TRUE.equals(school.getIsNotPublic()));
        dto.setIsTimetableNotPublic(Boolean.TRUE.equals(school.getIsNotPublicTimetable()));
        dto.setIsCurriculumNotPublic(Boolean.TRUE.equals(school.getIsNotPublicCurriculum()));
        dto.setIsSubjectNotPublic(Boolean.TRUE.equals(school.getIsNotPublicSubject()));
        dto.setHigher(Boolean.valueOf(schoolType.isHigher()));
        return dto;
    }

    public AcademicCalendarDto academicCalendar(Long schoolId) {
        School school = EntityUtil.getOptionalOne(School.class, schoolId, em);
        if (school != null && Boolean.TRUE.equals(school.getIsNotPublic())) {
            return null;
        }
        return academicCalendarService.academicCalendar(schoolId);
    }
    
    public AcademicCalendarDto academicCalendar(School school, StudyYear studyYear) {
        return academicCalendarService.academicCalendar(EntityUtil.getId(school), studyYear);
    }

    public Object curriculum(Long curriculumId) {
        Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
        assertVisibleToPublic(curriculum);
        return new PublicDataMapper(Language.ET).map(curriculum);
    }

    public Object curriculumVersion(Long curriculumId, Long curriculumVersionId) {
        CurriculumVersion curriculumVersion = em.getReference(CurriculumVersion.class, curriculumVersionId);
        if(curriculumId == null || !curriculumId.equals(EntityUtil.getId(curriculumVersion.getCurriculum()))) {
            throw new ValidationFailedException("main.messages.error.dataNotFound");
        }
        if(!CurriculumUtil.isCurriculumVersionConfirmed(curriculumVersion)) {
            throw new ValidationFailedException("main.messages.error.dataNotFound");
        }
        return new PublicDataMapper(Language.ET).map(curriculumVersion);
    }

    public Object subject(Long subjectId) {
        Subject s = em.getReference(Subject.class, subjectId);
        assertVisibleToPublic(s);
        return new PublicDataMapper(Language.ET).map(s);
    }

    private static void assertVisibleToPublic(Curriculum curriculum) {
        if(Boolean.TRUE.equals(curriculum.getSchool().getIsNotPublicCurriculum())
                || !ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, curriculum.getStatus())) {
            throw new ValidationFailedException("main.messages.error.dataNotFound");
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
            filters.add(cb.notEqual(root.get("school").get("isNotPublicCurriculum"), Boolean.TRUE));

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

    private static void assertVisibleToPublic(StateCurriculum stateCurriculum) {
        if(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, stateCurriculum.getStatus())) {
            throw new ValidationFailedException("main.messages.error.dataNotFound");
        }
    }

    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId) {
        if (!schoolSubjectsPublic(schoolId)) {
            return Collections.emptyList();
        }
        return autocompleteService.schoolDepartments(schoolId);
    }

    public List<CurriculumVersionResult> curriculumVersions(Long schoolId) {
        if (!schoolSubjectsPublic(schoolId)) {
            return Collections.emptyList();
        }
        CurriculumVersionAutocompleteCommand lookup = new CurriculumVersionAutocompleteCommand();
        lookup.setHigher(Boolean.TRUE);
        lookup.setValid(Boolean.TRUE);
        return autocompleteService.curriculumVersions(schoolId, lookup);
    }

    public Page<SubjectSearchDto> searchSubjects(SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        if (subjectSearchCommand.getSchoolId() != null && !schoolSubjectsPublic(subjectSearchCommand.getSchoolId())) {
            return new PageImpl<>(Collections.emptyList());
        }
        return subjectService.search(null, subjectSearchCommand, pageable);
    }

    public SubjectDto subjectView(Subject subject) {
        assertVisibleToPublic(subject);
        return SubjectDto.forPublic(subject, em.createQuery("select cvms.module.curriculumVersion from CurriculumVersionHigherModuleSubject cvms"
                + " where cvms.subject.id = ?1 and cvms.module.curriculumVersion.status.code = ?2", CurriculumVersion.class)
                .setParameter(1, subject.getId())
                .setParameter(2, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name())
                .getResultList());
    }

    public SubjectStudyPeriodDto subjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        assertVisibleToPublic(subjectStudyPeriod.getSubject());
        return studyMaterialService.getSubjectStudyPeriod(null, subjectStudyPeriod);
    }

    public List<StudyMaterialSearchDto> subjectStudyPeriodMaterials(SubjectStudyPeriod subjectStudyPeriod) {
        assertVisibleToPublic(subjectStudyPeriod.getSubject());
        return studyMaterialService.materials(null, null, subjectStudyPeriod);
    }

    private boolean schoolSubjectsPublic(Long schoolId) {
        School school = EntityUtil.getOptionalOne(School.class, schoolId, em);
        return school != null && !Boolean.TRUE.equals(school.getIsNotPublicSubject());
    }

    private void assertVisibleToPublic(Subject subject) {
        if (Boolean.TRUE.equals(subject.getSchool().getIsNotPublicSubject()) || !SubjectUtil.isActive(subject)) {
            throw new ValidationFailedException("main.messages.error.dataNotFound");
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

    private static void assertVisibleToPublic(Journal journal) {
        if (Boolean.TRUE.equals(journal.getSchool().getIsNotPublicTimetable())) {
            throw new ValidationFailedException("main.messages.error.dataNotFound");
        }
    }

    public Object subjectProgram(SubjectProgram program) {
        return new PublicDataMapper(Language.ET).map(program);
    }

    public Map<String, ?> getStudyYearScheduleLegends(Long schoolId) {
        School school = em.getReference(School.class, schoolId);
        Map<String, Object> response = new HashMap<>();
        response.put("legends", StreamUtil.toMappedList(StudyYearScheduleLegendDto::of, school.getStudyYearScheduleLegends()));
        return response;
    }

    public List<StudentGroupSearchDto> getStudyYearScheduleStudentGroups(Long schoolId) {
        List<StudentGroup> data;
        JpaQueryBuilder<StudentGroup> qb = new JpaQueryBuilder<>(StudentGroup.class, "sg").sort("code");
        qb.requiredCriteria("sg.school.id = :schoolId", "schoolId", schoolId);
        data = qb.select(em).getResultList();

        return StreamUtil.toMappedList(sg -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(sg.getId());
            dto.setCode(sg.getCode());
            dto.setValidFrom(sg.getValidFrom());
            dto.setValidThru(sg.getValidThru());
            dto.setSchoolDepartments(StreamUtil.toMappedList(d -> EntityUtil.getId(d.getSchoolDepartment()),
                    sg.getCurriculum().getDepartments()));
            dto.setCanEdit(Boolean.FALSE);
            return dto;
        }, data.stream().filter(sg -> sg.getCurriculum() != null && !sg.getCurriculum().getDepartments().isEmpty()));
    }

    public List<StudyYearDto> getStudyYearsWithStudyPeriods(Long schoolId) {
        return studyYearScheduleService.getStudyYearsWithStudyPeriods(schoolId);
    }

    public Set<StudyYearScheduleDto> getStudyYearSchedule(Long schoolId, StudyYearScheduleDtoContainer schedulesCmd) {
        JpaQueryBuilder<StudyYearSchedule> qb = new JpaQueryBuilder<>(StudyYearSchedule.class, "sys");

        qb.requiredCriteria("sys.school.id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sys.studyPeriod.id in (:studyPeriodIds)", "studyPeriodIds", schedulesCmd.getStudyPeriods());
        qb.optionalCriteria("sys.studentGroup.id in (:studentGroupIds)", "studentGroupIds", schedulesCmd.getStudentGroups());

        return StreamUtil.toMappedSet(StudyYearScheduleDto::of, qb.select(em).getResultList());
    }

    public List<StudyPeriodWithWeeksDto> getStudyYearPeriods(StudyYear studyYear) {
        return studyYearScheduleService.getStudyYearPeriods(studyYear);
    }

    public List<SchoolDepartmentResult> studyYearScheduleSchoolDepartments(Long schoolId) {
        return autocompleteService.schoolDepartments(schoolId);
    }

    public byte[] studyYearScheduleAsExcel(Long schoolId, StudyYearScheduleForm schedulesCmd) {
        return studyYearScheduleService.studyYearScheduleAsExcel(null, schoolId, schedulesCmd);
    }

    public byte[] studyYearScheduleAsPdf(Long schoolId, StudyYearScheduleForm schedulesCmd) {
        return studyYearScheduleService.studyYearScheduleAsPdf(null, schoolId, schedulesCmd);
    }
    
    
}
