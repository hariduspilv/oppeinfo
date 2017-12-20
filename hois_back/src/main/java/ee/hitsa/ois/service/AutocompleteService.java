package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EnterpriseUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.AutocompleteCommand;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAndSubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.JournalAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.PersonLookupCommand;
import ee.hitsa.ois.web.commandobject.RoomsAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionModuleAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionOccupationModuleThemeAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.EnterpriseResult;
import ee.hitsa.ois.web.dto.PersonDto;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.SchoolWithoutLogo;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOModulesAndThemesResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
import ee.hitsa.ois.web.dto.student.StudentGroupResult;

@Transactional
@Service
public class AutocompleteService {

    private static final int MAX_ITEM_COUNT = 20;
    private static final List<String> POSITIVE_HIGHER_GRADES = EnumUtil.toNameList(
            HigherAssessment.KORGHINDAMINE_A, HigherAssessment.KORGHINDAMINE_1, HigherAssessment.KORGHINDAMINE_2,
            HigherAssessment.KORGHINDAMINE_3, HigherAssessment.KORGHINDAMINE_4, HigherAssessment.KORGHINDAMINE_5);
    private static final List<String> POSITIVE_VOCATIONAL_GRADES = EnumUtil.toNameList(
            OccupationalGrade.KUTSEHINDAMINE_A, OccupationalGrade.KUTSEHINDAMINE_3, OccupationalGrade.KUTSEHINDAMINE_4,
            OccupationalGrade.KUTSEHINDAMINE_5);

    @Autowired
    private EntityManager em;
    @Autowired
    private EmailGeneratorService emailGeneratorService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

    public List<AutocompleteResult> buildings(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from building b");

        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("b.id, b.name", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    public List<AutocompleteResult> rooms(Long schoolId, RoomsAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from room r inner join building b on b.id = r.building_id");

        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("r.code",  "code", lookup.getName());
        qb.optionalCriteria("b.id in (:buildingIds)", "buildingIds", lookup.getBuildingIds());
        qb.sort("r.code");

        List<?> data = qb.select("r.id, r.code, r.seats", em).getResultList();
        /*Comparator.comparing(LessonPlanByTeacherSubjectDto::getNameEt, String.CASE_INSENSITIVE_ORDER)*/
        return StreamUtil.toMappedList(r -> {
            Long seats = resultAsLong(r, 2);
            String code = resultAsString(r, 1);
            String nameEt = seats != null ? code + " (kohti " + seats.toString() + ")" : code;
            String nameEn = seats != null ? code + " (seats " + seats.toString() + ")" : code;
            return new AutocompleteResult(resultAsLong(r, 0), nameEt, nameEn);
        }, data);
    }

    public List<Classifier> classifierForAutocomplete(ClassifierSearchCommand classifierSearchCommand) {
        String nameField = Language.EN.equals(classifierSearchCommand.getLang()) ? "nameEn" : "nameEt";
        JpaQueryBuilder<Classifier> qb = new JpaQueryBuilder<>(Classifier.class, "c").sort(nameField);
        qb.requiredCriteria("c.mainClassCode = :mainClassCode", "mainClassCode", classifierSearchCommand.getMainClassCode());
        qb.optionalContains("c." + nameField, "name", classifierSearchCommand.getName());

        return qb.select(em).setMaxResults(MAX_ITEM_COUNT).getResultList();
     }

    public List<ClassifierSelection> classifiers(List<String> mainClassCodes) {
        // ClassifierSelection includes attributes for filtering in frontend
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c");

        qb.requiredCriteria("c.main_class_code in (:mainClassCodes)", "mainClassCodes", mainClassCodes);

        List<?> data = qb.select("c.code, c.name_et, c.name_en, c.name_ru, c.valid, c.is_higher, c.is_vocational, c.main_class_code, c.value, c.value2", em).getResultList();
        List<ClassifierSelection> result = StreamUtil.toMappedList(r -> new ClassifierSelection(resultAsString(r, 0),
                    resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3),
                    resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6),
                    resultAsString(r, 7), resultAsString(r, 8), resultAsString(r, 9)), data);

        return ClassifierUtil.sort(mainClassCodes, result);
    }

    /**
     * Get list of classifiers with parents (bound via ClassifierConnect) for filtering in front-end
     */
    public List<ClassifierSelection> classifiersWithParents(List<String> mainClassCodes) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c left join (select array_agg(cc.connect_classifier_code) as parent, cc.classifier_code from classifier_connect cc group by cc.classifier_code) parents on c.code = parents.classifier_code");

        qb.requiredCriteria("c.main_class_code in (:mainClassCodes)", "mainClassCodes", mainClassCodes);

        List<?> data = qb.select("c.code, c.name_et, c.name_en, c.name_ru, c.valid, c.is_higher, c.is_vocational, c.main_class_code, c.value, array_to_string(parents.parent, ', ')", em).getResultList();
        List<ClassifierSelection> result = StreamUtil.toMappedList(r -> {
            ClassifierSelection c = new ClassifierSelection(resultAsString(r, 0),
                    resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3),
                    resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6),
                    resultAsString(r, 7), resultAsString(r, 8), resultAsString(r, 9));
            String parents = resultAsString(r, 9);
            if(parents != null) {
                c.setParents(Arrays.asList(parents.split(", ")));
            }
            return c;
        }, data);

        return ClassifierUtil.sort(mainClassCodes, result);
    }

    public List<AutocompleteResult> curriculums(Long schoolId, AutocompleteCommand term) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum c");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Language.EN.equals(term.getLang()) ? "c.name_en" : "c.name_et", "name", term.getName());

        List<?> data = qb.select("c.id, c.name_et, c.name_en", em).setMaxResults(MAX_ITEM_COUNT).getResultList();
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), data);
    }

    public List<CurriculumVersionResult> curriculumVersions(Long schoolId, CurriculumVersionAutocompleteCommand lookup) {
        String from = "from curriculum_version cv inner join curriculum c on cv.curriculum_id = c.id "+
            "left outer join curriculum_study_form sf on cv.curriculum_study_form_id = sf.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        if(Boolean.TRUE.equals(lookup.getValid())) {
            // only valid ones
            qb.requiredCriteria("cv.status_code = :statusCode", "statusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K);
            qb.requiredCriteria("c.valid_from <= :currentDate and (c.valid_thru is null or c.valid_thru >= :currentDate)", "currentDate", LocalDate.now());
        }
        if(Boolean.TRUE.equals(lookup.getSais())) {
            qb.filter("exists(select sa.id from sais_admission sa where sa.curriculum_version_id = cv.id)");
        }
        qb.optionalCriteria("c.is_higher = :higher", "higher", lookup.getHigher());
        qb.optionalContains(Arrays.asList("c.name_et", "cv.code"), "name", lookup.getName());

        List<?> data = qb.select("cv.id, cv.code, c.name_et, c.name_en, c.id as curriculum_id, cv.school_department_id, sf.study_form_code, c.is_higher", em).getResultList();
        List<CurriculumVersionResult> result = StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            return new CurriculumVersionResult(resultAsLong(r, 0), CurriculumUtil.versionName(code, resultAsString(r, 2)),
                    CurriculumUtil.versionName(code, resultAsString(r, 3)), resultAsLong(r, 4), resultAsLong(r, 5), resultAsString(r, 6), Boolean.valueOf(!Boolean.TRUE.equals(resultAsBoolean(r, 7))));
        }, data);

        if(Boolean.TRUE.equals(lookup.getLanguages())) {
            // attach study languages
            Set<Long> curriculumIds = result.stream().map(CurriculumVersionResult::getCurriculum).collect(Collectors.toSet());
            if(!curriculumIds.isEmpty()) {
                data = em.createNativeQuery("select csl.curriculum_id, csl.study_lang_code from curriculum_study_lang csl where csl.curriculum_id in ?1")
                    .setParameter(1, curriculumIds).getResultList();
                Map<Long, List<String>> curriculumLanguages = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
                for(CurriculumVersionResult r : result) {
                    r.setStudyLang(curriculumLanguages.get(r.getCurriculum()));
                }
            }
        }
        return result;
    }
    
    public List<AutocompleteResult> curriculumVersionHigherModules(CurriculumVersionModuleAutocompleteCommand lookup) {
        String from = "from curriculum_version_hmodule cvh"
                + " inner join curriculum_version cv on cvh.curriculum_version_id = cv.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("cvh.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                lookup.getCurriculumVersion());
        qb.optionalContains("cv.status_code",  "statusCode", lookup.getCurriculumVersionStatusCode());

        List<?> data = qb.select("cvh.id, cvh.name_et, cvh.name_en", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2));
        }, data);
    }
  
    public List<CurriculumVersionOccupationModuleResult> curriculumVersionOccupationModules(CurriculumVersionModuleAutocompleteCommand lookup) {
        String from = "from curriculum_version_omodule cvo"
                + " inner join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " inner join curriculum_version cv on cvo.curriculum_version_id = cv.id"
                + " inner join curriculum c on cv.curriculum_id = c.id";

        if (lookup.getStudent() != null) {
            from += " left join protocol_vdata pvd on cvo.id = pvd.curriculum_version_omodule_id"
                    + " left join protocol p on pvd.protocol_id = p.id"
                    + " left join protocol_student ps on p.id = ps.protocol_id";
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.optionalCriteria("cvo.id = :id", "id", lookup.getId());
        
        if (Boolean.FALSE.equals(lookup.getCurriculumModules())) {
            qb.requiredCriteria("cvo.curriculum_version_id != :curriculumVersionId", "curriculumVersionId",
                    lookup.getCurriculumVersion());
        } else if (lookup.getCurriculumVersion() != null){
            qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                    lookup.getCurriculumVersion());
        }
        qb.optionalCriteria("c.school_id = :schoolId",  "schoolId", lookup.getSchool());

        qb.optionalContains("cv.status_code",  "statusCode", lookup.getCurriculumVersionStatusCode());
        qb.optionalContains(Language.EN.equals(lookup.getLang()) ? "cm.name_en" : "cm.name_et", "name", lookup.getName());
        
        if (lookup.getStudent() != null) {
            qb.optionalCriteria("(ps.grade_code is null or ps.grade_code not in (:postiveGrades))", "postiveGrades", POSITIVE_VOCATIONAL_GRADES);
            
            if (Boolean.TRUE.equals(lookup.getOtherStudents())) {
                qb.optionalCriteria(
                        "ps.student_id in (select s2.id from student s join student s2 on s.person_id=s2.person_id and s.id!=s2.id where s2.id!=:studentId and s.id = :studentId)",
                        "studentId", lookup.getStudent());
            } else {
                qb.optionalCriteria("(ps.grade_code is null or ps.student_id = :studentId)", "studentId", lookup.getStudent());
                qb.optionalCriteria("cvo.id not in (select svr.curriculum_version_omodule_id from student_vocational_result svr where svr.student_id = :studentId)", "studentId", lookup.getStudent());                
            }
        }
        
        if (Language.EN.equals(lookup.getLang())) {
            qb.sort("cm.name_en");
        } else {
            qb.sort("cm.name_et");
        }

        List<?> data = qb.select("distinct cvo.id, cm.name_et, cm.name_en, cvo.assessment_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new CurriculumVersionOccupationModuleResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3));
        }, data);
    }

    public List<CurriculumVersionOccupationModuleThemeResult> curriculumVersionOccupationModuleThemes(
            CurriculumVersionOccupationModuleThemeAutocompleteCommand lookup) {
        String from = "from curriculum_version_omodule_theme cvot"
                + " inner join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id"
                + " inner join curriculum_version cv on cvo.curriculum_version_id = cv.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("cvot.curriculum_version_omodule_id = :curriculum_version_omodule_id",
                "curriculum_version_omodule_id", lookup.getCurriculumVersionOmoduleId());
        qb.optionalContains("cv.status_code",  "statusCode", lookup.getCurriculumVersionStatusCode());

        List<?> data = qb.select("cvot.id, cvot.name_et, cvot.credits", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new CurriculumVersionOccupationModuleThemeResult(resultAsLong(r, 0), name, name, resultAsDecimal(r, 2));
        }, data);
    }
    
    public List<CurriculumVersionOModulesAndThemesResult> curriculumVersionOccupationModulesAndThemes(CurriculumVersionModuleAutocompleteCommand lookup) {
        List<CurriculumVersionOModulesAndThemesResult> modulesAndThemes = new ArrayList<>();
        List<CurriculumVersionOccupationModuleResult> modules = curriculumVersionOccupationModules(lookup);
        
        for (CurriculumVersionOccupationModuleResult module : modules) {
            CurriculumVersionOccupationModuleThemeAutocompleteCommand themeLookup = new CurriculumVersionOccupationModuleThemeAutocompleteCommand();
            themeLookup.setCurriculumVersionOmoduleId(module.getId());
            themeLookup.setCurriculumVersionStatusCode(lookup.getCurriculumVersionStatusCode());
            List<CurriculumVersionOccupationModuleThemeResult> themes = curriculumVersionOccupationModuleThemes(themeLookup);
            modulesAndThemes.add(new CurriculumVersionOModulesAndThemesResult(module.getId(), module.getNameEt(), module.getNameEn(), module.getAssessment(), themes));
        }
        return modulesAndThemes;
    }
    

    public List<AutocompleteResult> directiveCoordinators(Long schoolId, DirectiveCoordinatorAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_coordinator dc");

        qb.requiredCriteria("dc.school_id = :schoolId", "schoolId", schoolId);
        if(Boolean.TRUE.equals(lookup.getIsDirective())) {
            qb.filter("dc.is_directive = true");
        }
        if(Boolean.TRUE.equals(lookup.getIsCertificate())) {
            qb.filter("dc.is_certificate = true");
        }

        List<?> data = qb.select("dc.id, dc.name", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    public PersonDto person(HoisUserDetails user, PersonLookupCommand lookup) {
        Person person;
        String idcode = lookup.getIdcode().trim();
        if("student".equals(lookup.getRole())) {
            // FIXME multiple students with same idcode?
            // FIXME should filter by school?
            List<?> data = em.createQuery("select s.person from Student s where s.person.idcode = ?1", Person.class)
                    .setParameter(1, idcode).setMaxResults(1).getResultList();
            person = data.isEmpty() ? null : (Person)data.get(0);
        } else if("activestudent".equals(lookup.getRole())) {
            List<?> data = em.createQuery("select s.person from Student s where s.person.idcode = ?1 and s.status.code in ?2", Person.class)
                    .setParameter(1, idcode).setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE).setMaxResults(1).getResultList();
            person = data.isEmpty() ? null : (Person)data.get(0);
        } else {
           person = personRepository.findByIdcode(idcode);
        }
        PersonDto dto = null;
        if(person != null) {
            dto = PersonDto.of(person);
            if("forteacher".equals(lookup.getRole())) {
                if(user.isSchoolAdmin()) {
                    List<?> teacher = em.createNativeQuery("select id from teacher where school_id = ?1 and person_id = ?2")
                            .setParameter(1, user.getSchoolId()).setParameter(2, person.getId()).getResultList();
                    dto.setTeacherId(teacher.isEmpty() ? null : resultAsLong(teacher.get(0), 0));
                    dto.setSchoolEmail(emailGeneratorService.lookupSchoolEmail(em.getReference(School.class, user.getSchoolId()), person));
                }
            }
        }
        return dto;
    }

    public List<SchoolWithoutLogo> schools(AutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school s");

        qb.optionalCriteria("s.id = :schoolId", "schoolId", lookup.getId());

        List<?> data = qb.select("s.id, s.code, s.name_et, s.name_en, s.email", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new SchoolWithoutLogo(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4));
        }, data);
    }

    public List<SchoolWithoutLogo> ldapSchools() {
        List<?> data = em.createNativeQuery("select s.id, s.code, s.name_et, s.name_en, s.email" +
                " from school s where s.ad_domain is not null and s.ad_base is not null" + 
                " and s.ad_port is not null and s.ad_url is not null and s.ad_idcode_field is not null")
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            return new SchoolWithoutLogo(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4));
        }, data);
    }
    
    public List<ApelSchoolResult> apelSchools(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_school s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        
        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.country_code, s.ehis_school_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new ApelSchoolResult(
                    resultAsLong(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3),
                    resultAsString(r, 4));
        }, data);
    }

    /**
     * Values for selecting department.
     * @param schoolId
     * @return
     */
    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school_department sd inner join school s on s.id = sd.school_id");
        // optional for teacher's view form, when user is external expert
        qb.optionalCriteria("sd.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("sd.id, sd.name_et, sd.name_en, sd.school_id, s.code", em).getResultList();
        return StreamUtil.toMappedList(r -> new SchoolDepartmentResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsLong(r, 3), resultAsString(r, 4)), data);
    }

    public List<StudentGroupResult> studentGroups(Long schoolId, StudentGroupAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student_group sg inner join curriculum c on sg.curriculum_id = c.id " +
                "left outer join curriculum_version cv on sg.curriculum_version_id = cv.id");

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);

        if(Boolean.TRUE.equals(lookup.getValid())) {
            qb.requiredCriteria("(sg.valid_from is null or sg.valid_from <= :now) and (sg.valid_thru is null or sg.valid_thru >= :now)", "now", LocalDate.now());
        }
        qb.optionalCriteria("c.is_higher = :higher", "higher", lookup.getHigher());
        qb.optionalContains("sg.code",  "code", lookup.getName());
        qb.sort("sg.code");

        List<?> data = qb.select("sg.id, sg.code, c.id as c_id, cv.id as cv_id, sg.study_form_code, sg.language_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupResult dto = new StudentGroupResult(resultAsLong(r, 0), resultAsString(r, 1));
            dto.setCurriculum(resultAsLong(r, 2));
            dto.setCurriculumVersion(resultAsLong(r, 3));
            dto.setStudyForm(resultAsString(r, 4));
            dto.setLanguage(resultAsString(r, 5));
            return dto;
        }, data);
    }

    public List<AutocompleteResult> students(Long schoolId, StudentAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student s inner join person p on s.person_id = p.id").sort("p.lastname", "p.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", lookup.getName());
        qb.optionalCriteria("s.id = :studentId", "studentId", lookup.getId());

        if (Boolean.TRUE.equals(lookup.getActive())) {
            qb.requiredCriteria("s.status_code in :statusCodes", "statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        }

        StudentStatus status = null;
        if (Boolean.TRUE.equals(lookup.getFinished())) {
            status = StudentStatus.OPPURSTAATUS_L;
        } else if (Boolean.TRUE.equals(lookup.getStudying())) {
            status = StudentStatus.OPPURSTAATUS_O;
        } else if (Boolean.TRUE.equals(lookup.getAcademicLeave())) {
            status = StudentStatus.OPPURSTAATUS_A;
        }
        qb.optionalCriteria("s.status_code = :statusCode", "statusCode", status);

        if (Boolean.TRUE.equals(lookup.getNominalStudy())) {
            qb.requiredCriteria("s.nominal_study_end > :currentDate", "currentDate", LocalDate.now());
        }
        if (Boolean.TRUE.equals(lookup.getHigher())) {
            qb.filter("exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "where cv.id = s.curriculum_version_id "
                    + "and c.is_higher = true )");
        }
        if (Boolean.FALSE.equals(lookup.getHigher())) {
            qb.filter("exists (select c.id from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "where cv.id = s.curriculum_version_id "
                    + "and c.is_higher = false )");
        }
        
        List<?> data = qb.select("s.id, p.firstname, p.lastname, p.idcode", em).setMaxResults(MAX_ITEM_COUNT).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcode(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    /**
     * SubjectService.search() is not used as it does not enable to search by both code and name using autocomplete
     */
    public List<AutocompleteResult> subjects(Long schoolId, SubjectAutocompleteCommand lookup) {
        String from ="from subject s";
        
        if (Boolean.TRUE.equals(lookup.getCurriculumSubjects()) || Boolean.FALSE.equals(lookup.getCurriculumSubjects())) {
            from += " inner join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id"
                    + " inner join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id"
                    + " inner join curriculum_version cv on cv.id = cvh.curriculum_version_id";
        }
        
        if (lookup.getStudent() != null) {
            from += " left join subject_study_period ssp on s.id = ssp.subject_id" 
                    + " left join protocol_hdata ph on ssp.id = ph.subject_study_period_id" 
                    + " left join protocol pro on ph.protocol_id = pro.id"
                    + " left join protocol_student ps on pro.id = ps.protocol_id";
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code = :statusCode", "statusCode", SubjectStatus.AINESTAATUS_K);
        qb.optionalContains(Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et", "name", lookup.getName());
        qb.optionalContains(Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et", "code", lookup.getCode());
        qb.optionalCriteria("is_practice = :isPractice", "isPractice", lookup.getPractice());
        qb.optionalCriteria(Boolean.TRUE.equals(lookup.getCurriculumSubjects()) ? "cv.id = :curriculumVersionId"
                : "cv.id != :curriculumVersionId", "curriculumVersionId", lookup.getCurriculumVersion());
        
        if (lookup.getStudent() != null) {
            qb.optionalCriteria("(ps.grade_code is null or ps.grade_code not in (:postiveGrades))", "postiveGrades", POSITIVE_HIGHER_GRADES);
            
            if (Boolean.TRUE.equals(lookup.getOtherStudents())) {
                qb.optionalCriteria(
                        "ps.student_id in (select s2.id from student s join student s2 on s.person_id=s2.person_id and s.id!=s2.id where s2.id!=:studentId and s.id = :studentId)",
                        "studentId", lookup.getStudent());
            } else {
                qb.optionalCriteria("(ps.grade_code is null or ps.student_id = :studentId)", "studentId", lookup.getStudent());
                qb.optionalCriteria("s.id not in (select shr.subject_id from student_higher_result shr where shr.student_id = :studentId)", "studentId", lookup.getStudent());                
            }
        }
        
        if (Language.EN.equals(lookup.getLang())) {
            qb.sort("s.name_en");
        } else {
            qb.sort("s.name_et");
        }

        List<?> data = qb.select("distinct s.id, s.name_et, s.name_en, s.code, s.credits", em).getResultList();
        
        if (!lookup.getWithCredits().booleanValue()) {
            return StreamUtil.toMappedList(r -> {
                String code = resultAsString(r, 3);
                String nameEt = SubjectUtil.subjectName(code, resultAsString(r, 1));
                String nameEn = SubjectUtil.subjectName(code, resultAsString(r, 2));
                return new AutocompleteResult(resultAsLong(r, 0), nameEt, nameEn);
            }, data);
        }
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 3);
            BigDecimal credits = resultAsDecimal(r, 4);
            String nameEt = SubjectUtil.subjectName(code, resultAsString(r, 1), credits);
            String nameEn = SubjectUtil.subjectName(code, resultAsString(r, 2), credits);
            return new AutocompleteResult(resultAsLong(r, 0), nameEt, nameEn);
        }, data);
    }

    public List<AutocompleteResult> teachers(Long schoolId, TeacherAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from teacher t inner join person p on t.person_id = p.id").sort("p.lastname", "p.firstname");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"),  "name", lookup.getName());
        if(Boolean.TRUE.equals(lookup.getValid())) {
            qb.filter("t.is_active = true");
        }

        List<?> data = qb.select("t.id, p.firstname, p.lastname", em).setMaxResults(MAX_ITEM_COUNT).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    /**
     * startDate and endDate required to get current studyPeriod in front end
     */
    public List<StudyPeriodWithYearDto> studyPeriods(Long schoolId) {
        List<StudyPeriod> data = em.createQuery("select sp from StudyPeriod sp where sp.studyYear.school.id = ?1", StudyPeriod.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(StudyPeriodWithYearDto::of, data);
    }

    public List<StudyYearSearchDto> studyYears(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from study_year sy inner join classifier c on sy.year_code = c.code").sort("c.code desc");
        qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", schoolId);
        List<?> data = qb.select("c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count", em).getResultList();
        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }

    public List<AutocompleteResult> saisAdmissionCodes(Long schoolId) {
        return StreamUtil.toMappedList(AutocompleteResult::of,
                saisAdmissionRepository.findAllDistinctCodeByCurriculumVersionCurriculumSchoolId(schoolId));
    }

    public List<SaisClassifierSearchDto> saisClassifiers(String parentCode) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from sais_classifier c");
        qb.requiredCriteria("c.parent_code = :parentCode", "parentCode", parentCode);

        List<?> data = qb.select("c.code, c.name_et, c.name_en", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            SaisClassifierSearchDto c = new SaisClassifierSearchDto();
            c.setCode(resultAsString(r, 0));
            c.setNameEt(resultAsString(r, 1));
            c.setNameEn(resultAsString(r, 2));
            return c;
        }, data);
    }

    public Page<AutocompleteResult> vocationalModules(Long schoolId, AutocompleteCommand lookup) {
        String nameField = Language.EN.equals(lookup.getLang()) ? "nameEn" : "nameEt";
        PageRequest pageable = sortAndLimit(nameField);
        JpaQueryBuilder<CurriculumModule> qb = new JpaQueryBuilder<>(CurriculumModule.class, "cm").sort(pageable);
        qb.requiredCriteria("cm.curriculum.school.id = :schoolId", "schoolId", schoolId);
        qb.filter("cm.curriculum.higher = false");
        qb.optionalContains("cm." + nameField, "name", lookup.getName());

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(AutocompleteResult::of);
    }

    public List<AutocompleteResult> journals(HoisUserDetails user, JournalAutocompleteCommand lookup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j");
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isTeacher()) {
            qb.requiredCriteria("j.id in (select jt.journal_id from journal_teacher jt where jt.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        }
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", lookup.getStudyYear());
        qb.optionalContains("j.name_et",  "name_et", lookup.getName());

        List<?> data = qb.select("j.id, j.name_et", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

    private static PageRequest sortAndLimit(String... sortFields) {
        return new PageRequest(0, MAX_ITEM_COUNT, new Sort(sortFields));
    }

    public List<EnterpriseResult> enterprises() {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e");

        List<?> data = qb.select("e.id, e.name, e.contact_person_name, e.contact_person_email, e.contact_person_phone, e.reg_code", em)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            String regCode = resultAsString(r, 5);
            String enterpriseName = EnterpriseUtil.getName(name, regCode);
            EnterpriseResult enterpriseResult = new EnterpriseResult(resultAsLong(r, 0), enterpriseName, enterpriseName);
            enterpriseResult.setContactPersonName(resultAsString(r, 2));
            enterpriseResult.setContactPersonEmail(resultAsString(r, 3));
            enterpriseResult.setContactPersonPhone(resultAsString(r, 4));
            return enterpriseResult;
        }, data);
    }
    
    public List<AutocompleteResult> journalsAndSubjects(HoisUserDetails user, JournalAndSubjectAutocompleteCommand lookup) {        
        JournalAutocompleteCommand journalLookup = new JournalAutocompleteCommand();
        journalLookup.setStudyYear(lookup.getStudyYear());
        if (lookup.getName() != null) {
            journalLookup.setLang(lookup.getLang());
            journalLookup.setName(lookup.getName());
        }
        List<AutocompleteResult> journalsList = journals(user, journalLookup);
        // Change journal ids to negative to differentiate between journal and subject ids
        for (AutocompleteResult journal : journalsList) {
            journal.setId(Long.valueOf(-journal.getId().longValue()));
        }

        SubjectAutocompleteCommand subjectLookup = new SubjectAutocompleteCommand();
        if (lookup.getName() != null) {
            journalLookup.setLang(lookup.getLang());
            subjectLookup.setName(lookup.getName());
        }
        subjectLookup.setPractice(lookup.getPractice());
        List<AutocompleteResult> subjectsList = subjects(user.getSchoolId(), subjectLookup);
        
        List<AutocompleteResult> journalsAndSubjects = new ArrayList<>();
        journalsAndSubjects.addAll(journalsList);
        journalsAndSubjects.addAll(subjectsList);

        journalsAndSubjects.sort(Comparator.comparing(
                Language.EN.equals(lookup.getLang()) ? AutocompleteResult::getNameEn : AutocompleteResult::getNameEt,
                String.CASE_INSENSITIVE_ORDER));
        
        return journalsAndSubjects;
    }
}
