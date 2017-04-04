package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.AutocompleteCommand;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.student.StudentGroupResult;

@Transactional
@Service
public class AutocompleteService {

    private static final int MAX_ITEM_COUNT = 20;

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

    public List<AutocompleteResult> buildings(Long schoolId) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from building b");

        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("b.id, b.name", em).getResultList();
        return data.stream().map(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }).collect(Collectors.toList());
    }

    public List<Classifier> classifierForAutocomplete(ClassifierSearchCommand classifierSearchCommand) {
        String nameField = Language.EN.equals(classifierSearchCommand.getLang()) ? "nameEn" : "nameEt";
        return classifierRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("mainClassCode"), classifierSearchCommand.getMainClassCode()));
            propertyContains(() -> root.get(nameField), cb, classifierSearchCommand.getName(), filters::add);
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, sortAndLimit(nameField)).getContent();
    }

    public List<ClassifierSelection> classifiers(List<String> mainClassCodes) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from classifier c");

        qb.requiredCriteria("c.main_class_code in :mainClassCodes", "mainClassCodes", mainClassCodes);

        List<?> data = qb.select("c.code, c.name_et, c.name_en, c.name_ru, c.valid, c.is_higher, c.is_vocational, c.main_class_code, c.value", em).getResultList();
        return data.stream().map(r -> new ClassifierSelection(resultAsString(r, 0),
                    resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3),
                    resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6),
                    resultAsString(r, 7), resultAsString(r, 8))).collect(Collectors.toList());
    }

    public List<AutocompleteResult> curriculums(Long schoolId, AutocompleteCommand term) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from curriculum c");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Language.EN.equals(term.getLang()) ? "c.name_en" : "c.name_et", "name", term.getName());

        List<?> data = qb.select("c.id, c.name_et, c.name_en", em).getResultList();
        return data.stream().map(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2))).collect(Collectors.toList());
    }

    public List<CurriculumVersionResult> curriculumVersions(Long schoolId, Boolean valid) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from curriculum_version cv inner join curriculum c on cv.curriculum_id = c.id "+
                "left outer join curriculum_study_form sf on cv.curriculum_study_form_id = sf.id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        if(Boolean.TRUE.equals(valid)) {
            // only valid ones
            qb.requiredCriteria("cv.status_code = :statusCode", "statusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name());
            qb.requiredCriteria("c.valid_from <= :currentDate and (c.valid_thru is null or c.valid_thru >= :currentDate)", "currentDate", LocalDate.now());
        }

        List<?> data = qb.select("cv.id, cv.code, c.name_et, c.name_en, sf.study_form_code", em).getResultList();
        return data.stream().map(r -> {
            String code = resultAsString(r, 1);
            return new CurriculumVersionResult(resultAsLong(r, 0), CurriculumUtil.versionName(code, resultAsString(r, 2)),
                    CurriculumUtil.versionName(code, resultAsString(r, 3)), resultAsString(r, 4));
        }).collect(Collectors.toList());
    }

    public List<AutocompleteResult> directiveCoordinators(Long schoolId) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_coordinator dc");

        qb.requiredCriteria("dc.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("dc.id, dc.name", em).getResultList();
        return data.stream().map(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }).collect(Collectors.toList());
    }

    public Person person(PersonLookupCommand lookup) {
        if("student".equals(lookup.getRole())) {
            // FIXME multiple students with same idcode?
            // FIXME should filter by school?
            return personRepository.findByIdcodeStudent(lookup.getIdcode());
        }
        return personRepository.findByIdcode(lookup.getIdcode());
    }

    public List<SchoolWithoutLogo> schools() {
        return schoolRepository.findAllSchools();
    }

    /**
     * Values for selecting department. All departments of given school are returned ordered by name(Et|En) field
     * @param schoolId
     * @param criteria
     * @return
     */
    public List<AutocompleteResult> schoolDepartments(Long schoolId, SchoolDepartmentAutocompleteCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from school_department sd");

        qb.requiredCriteria("sd.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("sd.id != :excludedId", "excludedId", criteria.getExcludedId());

        List<?> data = qb.select("sd.id, sd.name_et, sd.name_en", em).getResultList();
        return data.stream().map(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2))).collect(Collectors.toList());
    }

    public List<StudentGroupResult> studentGroups(Long schoolId) {
        // StudentGroupResult includes attributes for filtering
        return studentGroupRepository.findAllBySchool_id(schoolId).stream().map(StudentGroupResult::of).collect(Collectors.toList());
    }

    public List<AutocompleteResult> students(Long schoolId, AutocompleteCommand lookup) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from student s inner join person p on s.person_id = p.id", new Sort("p.lastname", "p.firstname"));

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname"), "name", lookup.getName());

        List<?> data = qb.select("s.id, p.firstname, p.lastname, p.idcode", em).setMaxResults(MAX_ITEM_COUNT).getResultList();
        return data.stream().map(r -> {
            String name = PersonUtil.fullnameAndIdcode(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }).collect(Collectors.toList());
    }

    public Page<SubjectSearchDto> subjects(Long schoolId, AutocompleteCommand command) {
        SubjectSearchCommand subjectSearchCommand = new SubjectSearchCommand();
        subjectSearchCommand.setName(command.getName());
        subjectSearchCommand.setStatus(Collections.singletonList(SubjectStatus.AINESTAATUS_K.name()));
        return subjectService.search(schoolId, subjectSearchCommand, new PageRequest(0, MAX_ITEM_COUNT));
    }

    public Page<AutocompleteResult> teachers(Long schoolId, AutocompleteCommand lookup) {
        return teacherRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("person").get("firstname"), cb, lookup.getName(), name::add);
            propertyContains(() -> root.get("person").get("lastname"), cb, lookup.getName(), name::add);
            if(!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, sortAndLimit("person.lastname", "person.firstname")).map(AutocompleteResult::of);
    }

    public List<AutocompleteResult> studyPeriods(Long schoolId) {
        return studyPeriodRepository.findAll((root, query, cb) -> {
            return cb.equal(root.get("studyYear").get("school").get("id"), schoolId);
        }).stream().map(AutocompleteResult::of).collect(Collectors.toList());
    }

    public List<AutocompleteResult> saisAdmissionCodes(Long schoolId) {
        return saisAdmissionRepository.findAllDistinctCodeByCurriculumVersionCurriculumSchoolId(schoolId)
                .stream().map(AutocompleteResult::of).collect(Collectors.toList());
    }

    private static PageRequest sortAndLimit(String... sortFields) {
        return new PageRequest(0, MAX_ITEM_COUNT, new Sort(sortFields));
    }
}
