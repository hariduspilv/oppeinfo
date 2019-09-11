package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHigherResult;
import ee.hitsa.ois.domain.student.StudentHigherResultModule;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.student.StudentModuleListChangeForm;
import ee.hitsa.ois.web.commandobject.student.StudentResultModuleChangeForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentHigherElectiveModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherStudyPeriodResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultGradeDto;
import ee.hitsa.ois.web.dto.student.StudentModuleResultDto;
import ee.hitsa.ois.web.dto.student.SubjectResultReplacedSubjectDto;

@Transactional
@Service
public class StudentResultHigherService {

    @Autowired
    private EntityManager em;

    public List<StudentHigherSubjectResultDto> positiveHigherResults(Student student) {
        List<CurriculumVersionHigherModule> modules = getStudentModules(student);
        List<StudentHigherSubjectResultDto> moduleSubjects = getModuleSubjects(modules);
        List<StudentHigherSubjectResultDto> studentResults = getStudentResults(student, true);
        List<StudentHigherSubjectResultDto> mergedList = mergeModuleSubjectsAndResults(modules, moduleSubjects,
                studentResults);
        return filterNegativeResults(mergedList);
    }

    public List<StudentHigherSubjectResultDto> filterNegativeResults(List<StudentHigherSubjectResultDto> list) {
        calculateIsOk(list);
        return StreamUtil.toFilteredList(r -> Boolean.TRUE.equals(r.getIsOk()), list);
    }

    public StudentHigherResultDto higherResults(Student student) {
        List<CurriculumVersionHigherModule> modules = getStudentModules(student);
        List<StudentHigherSubjectResultDto> moduleSubjects = getModuleSubjects(modules);
        List<StudentHigherSubjectResultDto> studentResults = getStudentResults(student, false);
        List<StudentHigherSubjectResultDto> mergedList = mergeModuleSubjectsAndResults(modules, moduleSubjects,
                studentResults);
        calculateIsOk(mergedList);

        StudentHigherResultDto dto = new StudentHigherResultDto();
        dto.setModules(StreamUtil.toMappedList(StudentHigherModuleResultDto::of, modules));
        dto.setSubjectResults(mergedList);
        setExtraCurriculumSubjects(dto);
        calculateModulesCompletion(dto);
        calculateAverageGrade(dto);
        calculateCurriculumCompletion(dto);
        setStudyPeriodResults(dto);
        return dto;
    }

    private List<CurriculumVersionHigherModule> getStudentModules(Student student) {
        JpaNativeQueryBuilder qb = studentModulesQueryBuilder(student);
        List<?> data = qb.select("cvhm.id", em).getResultList();

        List<CurriculumVersionHigherModule> modules = new ArrayList<>();
        if (!data.isEmpty()) {
            modules = em.createQuery("select cvhm from CurriculumVersionHigherModule cvhm where cvhm.id in (?1)",
                            CurriculumVersionHigherModule.class)
                    .setParameter(1, StreamUtil.toMappedList(r -> resultAsLong(r, 0), data))
                    .getResultList();
        }
        return modules;
    }

    public JpaNativeQueryBuilder studentModulesQueryBuilder(Student student) {
        Long curriculumVersionId = EntityUtil.getId(student.getCurriculumVersion());
        Long curriculumSpecialityId = EntityUtil.getNullableId(student.getCurriculumSpeciality());

        String from = "from curriculum_version_hmodule cvhm";
        if (curriculumSpecialityId != null) {
            from += " left join curriculum_version_hmodule_speciality cvhms on cvhms.curriculum_version_hmodule_id = cvhm.id"
                    + " left join curriculum_version_speciality cvs on cvs.id = cvhms.curriculum_version_speciality_id"
                    + " left join curriculum_speciality cs on cs.id = cvs.curriculum_speciality_id";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("cvhm.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersionId);
        qb.optionalCriteria("cs.id = :curriculumSpecialityId", "curriculumSpecialityId", curriculumSpecialityId);
        qb.filter("cvhm.is_minor_speciality = false");

        return qb;
    }

    private static void setExtraCurriculumSubjects(StudentHigherResultDto dto) {
        List<StudentHigherSubjectResultDto> extraCurriculumSubjects = StreamUtil.toFilteredList(
                s -> Boolean.TRUE.equals(s.getIsExtraCurriculum()), dto.getSubjectResults());
        if(!extraCurriculumSubjects.isEmpty()) {
            List<StudentHigherModuleResultDto> freeModules = StreamUtil.toFilteredList(
                    m -> HigherModuleType.KORGMOODUL_V.name().equals(m.getType()), dto.getModules());
            if(freeModules.isEmpty()) {
                StudentHigherModuleResultDto freeModule = StudentHigherModuleResultDto.createFreeModule();
                setSubjectsToModule(freeModule, extraCurriculumSubjects);
                dto.getModules().add(freeModule);
            } else {
                setSubjectsToModule(freeModules.get(0), extraCurriculumSubjects);
            }
        }
    }

    private static void setSubjectsToModule(StudentHigherModuleResultDto module, List<StudentHigherSubjectResultDto> subjects) {
        for(StudentHigherSubjectResultDto subject : subjects) {
            subject.setHigherModule(new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
        }
    }

    private List<StudentHigherSubjectResultDto> getModuleSubjects(List<CurriculumVersionHigherModule> modules) {
        List<CurriculumVersionHigherModuleSubject> moduleSubjects = modules.isEmpty() ? null :
                em.createQuery("select cvhms from CurriculumVersionHigherModuleSubject cvhms where cvhms.module in (?1)", CurriculumVersionHigherModuleSubject.class)
                .setParameter(1, modules)
                .getResultList();
        List<StudentHigherSubjectResultDto> results = StreamUtil
                .toMappedList(StudentHigherSubjectResultDto::ofHigherModuleSubject, moduleSubjects);
        return results;
    }

    private List<StudentHigherSubjectResultDto> getStudentResults(Student student, boolean onlyActiveResults) {
        String query = "from student_higher_result shr "
                + "left join student_higher_result_module shrm on shr.id = shrm.student_higher_result_id "
                + "left join curriculum_version_hmodule cvh on cvh.id = coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) "
                + "left join classifier cl on shr.grade_code = cl.code "
                + "left join apel_application_record aar on shr.apel_application_record_id = aar.id "
                + "left join apel_application aa on aar.apel_application_id = aa.id "
                + "left join apel_school a_s on shr.apel_school_id = a_s.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(query).sort("shr.grade_date");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));

        if (onlyActiveResults) {
            qb.filter("shr.is_active = true");
        }

        List<?> rows = qb.select(
                "distinct shr.id, shr.subject_id, shr.subject_name_et, shr.subject_name_en, shr.subject_code, shr.credits, "
                + "cvh.id curriculum_version_hmodule_id, cvh.name_et cvh_name_et, cvh.name_en cvh_name_en, "
                + "shr.apel_application_record_id, aar.is_formal_learning, a_s.id as school_id, a_s.name_et, a_s.name_en, "
                + "shr.grade_code, shr.grade, shr.grade_date, shr.teachers, "
                + "coalesce(shr.study_period_id, get_study_period(cast(shr.grade_date as date), cast(aa.school_id as int))) as study_period_id, "
                + "shr.grade_mark, cl.name_et grade_name_et, coalesce(shrm.is_optional, shr.is_optional) is_optional, shr.is_active",
                em).getResultList();

        List<StudentHigherSubjectResultDto> studentResults = new ArrayList<>();
        for (Object r : rows) {
            StudentHigherSubjectResultDto dto = new StudentHigherSubjectResultDto();
            dto.setId(resultAsLong(r, 0));

            SubjectSearchDto subject = new SubjectSearchDto();
            subject.setId(resultAsLong(r, 1));
            subject.setNameEt(resultAsString(r, 2));
            subject.setNameEn(resultAsString(r, 3));
            subject.setCode(resultAsString(r, 4));
            subject.setCredits(resultAsDecimal(r, 5));
            dto.setSubject(subject);

            Long moduleId = resultAsLong(r, 6);
            dto.setHigherModule(moduleId != null ? new AutocompleteResult(moduleId, resultAsString(r, 7), resultAsString(r, 8)) : null);
            dto.setIsExtraCurriculum(Boolean.TRUE);
            dto.setIsOk(Boolean.FALSE);
            dto.setIsOptional(resultAsBoolean(r, 21));

            Long apelApplicationRecordId = resultAsLong(r, 9);
            dto.setIsApelTransfer(apelApplicationRecordId != null ? Boolean.TRUE : Boolean.FALSE);
            if (dto.getIsApelTransfer().booleanValue()) {
                dto.setIsFormalLearning(resultAsBoolean(r, 10));
                if (dto.getIsFormalLearning().booleanValue() && resultAsLong(r, 11) != null) {
                    dto.getSubject().setNameEt(dto.getSubject().getNameEt() + " - " + resultAsString(r, 12));
                    dto.getSubject().setNameEn(dto.getSubject().getNameEn() + " - " + resultAsString(r, 13));
                }
            }

            StudentHigherSubjectResultGradeDto grade = new StudentHigherSubjectResultGradeDto();
            grade.setId(resultAsLong(r, 0));
            grade.setGrade(resultAsString(r, 14));
            grade.setGradeValue(resultAsString(r, 15));
            grade.setGradeDate(resultAsLocalDate(r, 16));
            grade.getTeachers().add(resultAsString(r, 17));
            grade.setStudyPeriod(resultAsLong(r, 18));
            grade.setGradeMark(resultAsShort(r, 19));
            grade.setGradeNameEt(resultAsString(r, 20));
            grade.setIsActive(resultAsBoolean(r, 22));

            dto.getGrades().add(grade);
            studentResults.add(dto);
        }
        setResultReplacedSubjects(studentResults);
        return studentResults;
    }

    private void setResultReplacedSubjects(List<StudentHigherSubjectResultDto> studentResults) {
        Map<Long, StudentHigherSubjectResultDto> resultMap = StreamUtil.toMap(sr -> sr.getId(), studentResults);
        if (!resultMap.isEmpty()) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_higher_result shr "
                    + "join apel_application_record aar on shr.apel_application_record_id = aar.id "
                    + "join apel_application_formal_replaced_subject_or_module aafrs on aafrs.apel_application_record_id = aar.id "
                    + "join subject s on s.id = aafrs.subject_id");
            qb.requiredCriteria("shr.id in (:resultIds)", "resultIds", resultMap.keySet());

            List<?> data = qb.select("shr.id, s.id s_id, s.name_et s_name_et, s.name_en s_name_en, "
                    + "s.code, s.credits", em).getResultList();
            Map<Long, List<SubjectResultReplacedSubjectDto>> replacedSubjects = StreamUtil.nullSafeList(data).stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                            r -> new SubjectResultReplacedSubjectDto(resultAsLong(r, 1), resultAsString(r, 2),
                                    resultAsString(r, 3), resultAsString(r, 4), resultAsDecimal(r, 5)),
                            Collectors.toList())));

            for (Long resultId : replacedSubjects.keySet()) {
                List<SubjectResultReplacedSubjectDto> subjectReplacedSubjects = replacedSubjects.get(resultId);
                resultMap.get(resultId).getReplacedSubjects().addAll(subjectReplacedSubjects);
            }
        }
    }

    private static List<StudentHigherSubjectResultDto> mergeModuleSubjectsAndResults(
            List<CurriculumVersionHigherModule> modules, List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        Set<Long> moduleIds = StreamUtil.toMappedSet(m -> m.getId(), modules);
        addTransferedSubjects(moduleIds, moduleSubjects, studentResults);

        for(StudentHigherSubjectResultDto moduleSubject : moduleSubjects) {
            List<StudentHigherSubjectResultDto> subjectsResults = getStudentResultsForSubject(
                    moduleSubject.getSubject().getId(), studentResults);
            addGrades(moduleSubject, subjectsResults);

            if (!subjectsResults.isEmpty()) {
                // active student_higher_result row decides if subject is
                // transferred, is optional and the module all subject grades belong to
                StudentHigherSubjectResultDto activeResult = subjectsResults.get(0);
                moduleSubject.setIsApelTransfer(activeResult.getIsApelTransfer());
                moduleSubject.setIsFormalLearning(activeResult.getIsFormalLearning());
                moduleSubject.setIsOptional(activeResult.getIsOptional());
                moduleSubject.setHigherModule(activeResult.getHigherModule());
                moduleSubject.setReplacedSubjects(activeResult.getReplacedSubjects());

                // if set higher module is extra curriculum module then result
                // should be considerd as extra curriculum subject
                if (!moduleIds.contains(activeResult.getHigherModule().getId())) {
                    moduleSubject.setIsExtraCurriculum(Boolean.TRUE);
                }
            }
        }
        addResultsForExtraCurriculumSubjects(moduleSubjects, studentResults);
        return moduleSubjects;
    }

    // subjects that don't belong to curriculum module subjects but are added there
    // through apel application or subject module change
    private static void addTransferedSubjects(Set<Long> moduleIds, List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        Set<Long> subjectIds = StreamUtil.toMappedSet(ms -> ms.getSubject().getId(), moduleSubjects);
        List<StudentHigherSubjectResultDto> transferredSubjects = StreamUtil.toFilteredList(
                sr -> Boolean.TRUE.equals(sr.getGrades().get(0).getIsActive())
                        && (sr.getHigherModule() != null && moduleIds.contains(sr.getHigherModule().getId()))
                        && (sr.getSubject() == null || !subjectIds.contains(sr.getSubject().getId())),
                studentResults);

        for (StudentHigherSubjectResultDto subjectResult : transferredSubjects) {
            subjectResult.setIsExtraCurriculum(Boolean.FALSE);
            moduleSubjects.add(subjectResult);
        }
    }

    private static List<StudentHigherSubjectResultDto> getStudentResultsForSubject(Long subjectId,
            List<StudentHigherSubjectResultDto> studentResults) {
        // sorts active result to front
        return StreamUtil.nullSafeList(studentResults).stream()
                .filter(sr -> sr.getHigherModule() != null && sr.getSubject() != null && sr.getSubject().getId() != null
                        && sr.getSubject().getId().equals(subjectId))
                .sorted(Comparator.comparing(sr -> sr.getGrades().get(0).getIsActive(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private static void addGrades(StudentHigherSubjectResultDto moduleSubject,
            List<StudentHigherSubjectResultDto> subjectsResults) {
        for (StudentHigherSubjectResultDto subjectResult : subjectsResults) {
            List<Long> addedGrades = StreamUtil.toMappedList(g -> g.getId(), moduleSubject.getGrades());
            List<StudentHigherSubjectResultGradeDto> notAddedGrades = StreamUtil
                    .toFilteredList(g -> !addedGrades.contains(g.getId()), subjectResult.getGrades());
            moduleSubject.getGrades().addAll(notAddedGrades);
            subjectResult.setIsExtraCurriculum(Boolean.FALSE);
        }
    }

    private static void addResultsForExtraCurriculumSubjects(List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        List<StudentHigherSubjectResultDto> extraCurriculumResults =
                StreamUtil.toFilteredList(sr -> Boolean.TRUE.equals(sr.getIsExtraCurriculum()), studentResults);
        mergeExtraCurriculumResults(extraCurriculumResults);
        moduleSubjects.addAll(extraCurriculumResults);
    }

    private static void mergeExtraCurriculumResults(List<StudentHigherSubjectResultDto> extraCurriculumResults) {
        Map<Long, StudentHigherSubjectResultDto> map = new HashMap<>();
        Iterator<StudentHigherSubjectResultDto> iterator = extraCurriculumResults.iterator();
        while(iterator.hasNext()) {
            StudentHigherSubjectResultDto studentResult = iterator.next();
            if (studentResult.getSubject() != null) {
                Long subjectId = studentResult.getSubject().getId();
                if(map.containsKey(subjectId)) {
                    map.get(subjectId).getGrades().addAll(studentResult.getGrades());
                    iterator.remove();
                } else {
                    map.put(subjectId, studentResult);
                }
            }
        }
    }

    private static void calculateIsOk(List<StudentHigherSubjectResultDto> mergedList) {
        for(StudentHigherSubjectResultDto studentResult : mergedList) {
            studentResult.calculateIsOk();
        }
    }

    private static void calculateModulesCompletion(StudentHigherResultDto dto) {
        for(StudentHigherModuleResultDto module : dto.getModules()) {
            List<StudentHigherSubjectResultDto> modulesPositiveResults = filterModulesPositiveResults(module.getId(), dto.getSubjectResults());
            module.setMandatoryCreditsSubmitted(calculateCredits(modulesPositiveResults, Boolean.FALSE));
            module.setOptionalCreditsSubmitted(calculateCredits(modulesPositiveResults, Boolean.TRUE));
            module.setMandatoryDifference(module.getMandatoryCreditsSubmitted().subtract(module.getCompulsoryStudyCredits()));
            module.setOptionalDifference(module.getOptionalCreditsSubmitted().subtract(module.getOptionalStudyCredits()));
            module.setTotalDifference(calculateTotalDifference(module));
            calculateElectiveModulesCompletion(dto.getSubjectResults(), module);
            module.calculateIsOk();
        }
    }


    private static void calculateElectiveModulesCompletion(List<StudentHigherSubjectResultDto> subjectResults,
            StudentHigherModuleResultDto module) {
        for(StudentHigherElectiveModuleResultDto electiveModule : module.getElectiveModulesResults()) {
            List<StudentHigherSubjectResultDto> subjects = StreamUtil.toFilteredList(
                    s -> s.getElectiveModule() != null && electiveModule.getId().equals(s.getElectiveModule()), subjectResults);
            electiveModule.setIsOk(Boolean.valueOf(subjects.stream().allMatch(s -> Boolean.TRUE.equals(s.getIsOk()))));
        }
    }

    private static List<StudentHigherSubjectResultDto> filterModulesPositiveResults(Long module, List<StudentHigherSubjectResultDto> subjectResults) {
        return StreamUtil.toFilteredList(r -> r.getHigherModule() != null && module.equals(r.getHigherModule().getId()) && Boolean.TRUE.equals(r.getIsOk()), subjectResults);
    }

    private static BigDecimal calculateCredits(List<StudentHigherSubjectResultDto> modulesPositiveResults, Boolean isOptional) {
        Optional<BigDecimal> credits = modulesPositiveResults.stream().filter(r -> isOptional.equals(r.getIsOptional()))
                .map(r -> r.getSubject().getCredits()).reduce((c, sum) -> c.add(sum));
        return credits.orElse(BigDecimal.ZERO);
    }

    private static BigDecimal calculateTotalDifference(StudentHigherModuleResultDto module) {

        if (optionalCreditsDebt(module) && !mandatoryCreditsDebt(module)) {
            return module.getOptionalDifference();
        } else if(!optionalCreditsDebt(module) && mandatoryCreditsDebt(module)) {
            return module.getMandatoryDifference();
        }
        return module.getOptionalDifference().add(module.getMandatoryDifference());
    }

    private static boolean optionalCreditsDebt(StudentHigherModuleResultDto module) {
        return BigDecimal.ZERO.compareTo(module.getOptionalDifference()) > 0;
    }

    private static boolean mandatoryCreditsDebt(StudentHigherModuleResultDto module) {
        return BigDecimal.ZERO.compareTo(module.getMandatoryDifference()) > 0;
    }

    private static void calculateAverageGrade(StudentHigherResultDto dto) {
        BigDecimal numerator = BigDecimal.ZERO;
        BigDecimal denominator = BigDecimal.ZERO;

        BigDecimal creditsSubmittedConsidered = BigDecimal.ZERO;
        BigDecimal creditsSubmitted = BigDecimal.ZERO;

        for(StudentHigherSubjectResultDto subjectResult : dto.getSubjectResults()) {

            if(Boolean.TRUE.equals(subjectResult.getIsOk())) {
                BigDecimal credits = subjectResult.getSubject().getCredits();

                if(HigherAssessment.valueOf(subjectResult.getLastGrade().getGrade()).getIsDistinctive().booleanValue()) {
                    BigDecimal gradeMark = BigDecimal.valueOf(subjectResult.getLastGrade().getGradeMark().longValue());
                    numerator = numerator.add(gradeMark.multiply(credits));
                    denominator = denominator.add(credits);
                }
                if(Boolean.FALSE.equals(subjectResult.getIsExtraCurriculum())) {
                    creditsSubmittedConsidered = creditsSubmittedConsidered.add(credits);
                }
                creditsSubmitted = creditsSubmitted.add(credits);
            }
        }
        if(BigDecimal.ZERO.compareTo(denominator) != 0) {
            dto.setAverageGrade(numerator.divide(denominator, 3, BigDecimal.ROUND_HALF_UP));
        }
        dto.setCreditsSubmittedConsidered(creditsSubmittedConsidered);
        dto.setCreditsSubmitted(creditsSubmitted);
    }

    private static void calculateCurriculumCompletion(StudentHigherResultDto dto) {
        List<StudentHigherModuleResultDto> modules = dto.getModules();
        boolean isOk = modules != null && !modules.isEmpty() && modules.stream().allMatch(m -> Boolean.TRUE.equals(m.getIsOk()));
        dto.setIsCurriculumFulfilled(Boolean.valueOf(isOk));
    }

    private void setStudyPeriodResults(StudentHigherResultDto dto) {
        Set<Long> studyPeriodIds = StreamUtil.toMappedSet(g -> g.getStudyPeriod(), 
                dto.getSubjectResults().stream()
                    .filter(r -> r.getLastGrade() != null)
                    .map(StudentHigherSubjectResultDto::getLastGrade)
                    .filter(g -> g.getGradeValue() != null && g.getStudyPeriod() != null));
        if(!studyPeriodIds.isEmpty()) {
            List<StudyPeriod> studyPeriods = em.createQuery("select sp from StudyPeriod sp where sp.id in (?1)", StudyPeriod.class)
                    .setParameter(1, studyPeriodIds)
                    .getResultList();
            List<StudentHigherStudyPeriodResultDto> results = StreamUtil.toMappedList(StudentHigherStudyPeriodResultDto::of, studyPeriods);
            for(StudentHigherStudyPeriodResultDto result : results) {
                List<StudentHigherSubjectResultDto> subjects = filterSubjectsByStudyPeriod(result, dto.getSubjectResults());
                result.setAverageGrade(calculateStudyPeriodAverageGrade(subjects));
                result.setTotal(calculateTotalCredits(subjects));
            }
            dto.setStudyPeriodResults(results);
        }
    }
    
    private static List<StudentHigherSubjectResultDto> filterSubjectsByStudyPeriod(
            StudentHigherStudyPeriodResultDto result, List<StudentHigherSubjectResultDto> studyPeriodResults) {
        return StreamUtil.toFilteredList(s -> s.getLastGrade() != null && Boolean.TRUE.equals(s.getIsOk())
                && s.getLastGrade().getStudyPeriod() != null
                && s.getLastGrade().getStudyPeriod().equals(result.getStudyPeriod().getId()), studyPeriodResults);
    }

    private static BigDecimal calculateTotalCredits(List<StudentHigherSubjectResultDto> subjects) {
        BigDecimal sum = BigDecimal.ZERO;
        for(StudentHigherSubjectResultDto subject : subjects) {
            sum = sum.add(subject.getSubject().getCredits());
        }
        return sum;
    }

    private static BigDecimal calculateStudyPeriodAverageGrade(List<StudentHigherSubjectResultDto> subjects) {
        BigDecimal numerator = BigDecimal.ZERO;
        BigDecimal denominator = BigDecimal.ZERO;

        for(StudentHigherSubjectResultDto subjectResult : subjects) {

            if(Boolean.TRUE.equals(subjectResult.getIsOk())) {
                BigDecimal credits = subjectResult.getSubject().getCredits();

                if(HigherAssessment.valueOf(subjectResult.getLastGrade().getGrade()).getIsDistinctive().booleanValue()) {
                    BigDecimal gradeMark = BigDecimal.valueOf(subjectResult.getLastGrade().getGradeMark().longValue());
                    numerator = numerator.add(gradeMark.multiply(credits));
                    denominator = denominator.add(credits);
                }
            }
        }
        if(BigDecimal.ZERO.compareTo(denominator) != 0) {
            return numerator.divide(denominator, 3, BigDecimal.ROUND_HALF_UP);
        } 
        return null;
    }

    public List<StudentModuleResultDto> higherChangeableModules(Student student) {
        Query q = em.createNativeQuery("select shr.id, cvh.id curriculum_version_hmodule_id, shr.subject_name_et, shr.subject_name_en, "
                + "shr.subject_code, shr.credits, shr.grade, shr.grade_date, coalesce(shrm.is_optional, shr.is_optional) is_optional "
                + "from student_higher_result shr "
                + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id "
                + "left join curriculum_version_hmodule cvh on cvh.id = coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) "
                + "where student_id = ?1 and (cvh.id is null or cvh.type_code not in (?2)) and shr.is_active = true");
        q.setParameter(1, EntityUtil.getId(student));
        q.setParameter(2, EnumUtil.toNameList(HigherModuleType.KORGMOODUL_F, HigherModuleType.KORGMOODUL_L));
        List<?> data = q.getResultList();

        return StreamUtil.toMappedList(r -> new StudentModuleResultDto(resultAsLong(r, 0), resultAsLong(r, 1),
                resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsDecimal(r, 5),
                resultAsString(r, 6), resultAsLocalDate(r, 7), resultAsBoolean(r, 8)), data);
    }

    public List<AutocompleteResult> higherCurriculumModulesForSelection(Student student) {
        JpaNativeQueryBuilder qb = studentModulesQueryBuilder(student);
        qb.optionalCriteria("cvhm.type_code not in (:finalTypes)", "finalTypes",
                EnumUtil.toNameList(HigherModuleType.KORGMOODUL_F, HigherModuleType.KORGMOODUL_L));
        List<?> data = qb.select("cvhm.id, cvhm.name_et, cvhm.name_en", em).getResultList();

        return StreamUtil.toMappedList(
                r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), data);
    }

    public void changeHigherCurriculumVersionModules(Student student, StudentModuleListChangeForm form) {
        StudentHigherResult result = em.getReference(StudentHigherResult.class, form.getModules().get(0).getId());
        if (!student.equals(result.getStudent())) {
            throw new AssertionFailedException("Student mismatch");
        }

        List<Long> higherResultIds = form.getModules().stream().map(m -> m.getId()).collect(Collectors.toList());
        List<StudentHigherResultModule> data = em.createQuery(
                "select shrm from StudentHigherResultModule shrm where shrm.studentHigherResult.id in (?1)",
                StudentHigherResultModule.class).setParameter(1, higherResultIds).getResultList();

        Map<Long, StudentHigherResultModule> higherResultModules = StreamUtil
                .toMap(d -> d.getStudentHigherResult().getId(), data);

        for (StudentResultModuleChangeForm higherModuleForm : form.getModules()) {
            if (higherModuleForm.getCurriculumVersionModuleId() != null && (!higherModuleForm
                    .getCurriculumVersionModuleId().equals(higherModuleForm.getOldCurriculumVersionModuleId())
                    || !higherModuleForm.getIsOptional().equals(higherModuleForm.getOldIsOptional()))) {
                StudentHigherResultModule module = higherResultModules.get(higherModuleForm.getId());
                if (module != null) {
                    module.setCurriculumVersionHmodule(em.getReference(CurriculumVersionHigherModule.class,
                            higherModuleForm.getCurriculumVersionModuleId()));
                    module.setIsOptional(higherModuleForm.getIsOptional());
                    em.merge(module);
                } else {
                    module = EntityUtil.bindToEntity(form, new StudentHigherResultModule());
                    module.setStudentHigherResult(em.getReference(StudentHigherResult.class, higherModuleForm.getId()));
                    module.setCurriculumVersionHmodule(em.getReference(CurriculumVersionHigherModule.class,
                            higherModuleForm.getCurriculumVersionModuleId()));
                    module.setIsOptional(higherModuleForm.getIsOptional());
                    em.persist(module);
                }
            }
        }
    }
}
