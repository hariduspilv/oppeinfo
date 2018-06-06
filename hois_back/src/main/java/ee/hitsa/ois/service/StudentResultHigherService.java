package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentHigherElectiveModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherStudyPeriodResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultGradeDto;

@Transactional
@Service
public class StudentResultHigherService {

    @Autowired
    private EntityManager em;

    public List<StudentHigherSubjectResultDto> positiveHigherResults(Student student) {
        List<CurriculumVersionHigherModule> modules = StreamUtil.toFilteredList(
                m -> Boolean.FALSE.equals(m.getMinorSpeciality()), student.getCurriculumVersion().getModules());

        List<StudentHigherSubjectResultDto> moduleSubjects = getModuleSubjects(modules);
        List<StudentHigherSubjectResultDto> studentResults = getStudentResults(student);
        List<StudentHigherSubjectResultDto> mergedList = mergeModuleSubjectsAndResults(moduleSubjects, studentResults);
        return filterNegativeResults(mergedList);
    }

    public List<StudentHigherSubjectResultDto> filterNegativeResults(List<StudentHigherSubjectResultDto> list) {
        calculateIsOk(list);
        return StreamUtil.toFilteredList(r -> Boolean.TRUE.equals(r.getIsOk()), list);
    }

    public StudentHigherResultDto higherResults(Student student) {
        List<CurriculumVersionHigherModule> modules = StreamUtil.toFilteredList(
                m -> Boolean.FALSE.equals(m.getMinorSpeciality()), student.getCurriculumVersion().getModules());
        List<StudentHigherSubjectResultDto> moduleSubjects = getModuleSubjects(modules);
        List<StudentHigherSubjectResultDto> studentResults = getStudentResults(student);
        List<StudentHigherSubjectResultDto> mergedList = mergeModuleSubjectsAndResults(moduleSubjects, studentResults);
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

    private List<StudentHigherSubjectResultDto> getStudentResults(Student student) {
        String query = "from student_higher_result shr "
                + "left join student_higher_result_module shrm on shr.id = shrm.student_higher_result_id "
                + "left join apel_application_record aar on shr.apel_application_record_id = aar.id "
                + "left join apel_application aa on aar.apel_application_id = aa.id "
                + "left join apel_school a_s on shr.apel_school_id = a_s.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(query).sort("shr.grade_date");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        
        List<?> rows = qb.select(
                "distinct shr.id, shr.subject_id, shr.subject_name_et, shr.subject_name_en, shr.subject_code, shr.credits, "
                + "coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id), "
                + "shr.apel_application_record_id, aar.is_formal_learning, a_s.id as school_id, a_s.name_et, a_s.name_en, "
                + "shr.grade_code, shr.grade, shr.grade_date, shr.teachers, "
                + "coalesce(shr.study_period_id, get_study_period(cast(shr.grade_date as date), cast(aa.school_id as int))) as study_period_id, shr.grade_mark",
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
            dto.setHigherModule(moduleId != null ? StudentHigherSubjectResultDto.getHigherModuleDto(em.getReference(CurriculumVersionHigherModule.class, moduleId)): null);
            dto.setIsExtraCurriculum(Boolean.TRUE);
            dto.setIsOk(Boolean.FALSE);
            dto.setIsOptional(Boolean.TRUE);
            
            Long apelApplicationRecordId = resultAsLong(r, 7);
            dto.setIsApelTransfer(apelApplicationRecordId != null ? Boolean.TRUE : Boolean.FALSE);
            if (dto.getIsApelTransfer().booleanValue()) {
                dto.setIsFormalLearning(resultAsBoolean(r, 8));
                if (dto.getIsFormalLearning().booleanValue() && resultAsLong(r, 9) != null) {
                    dto.getSubject().setNameEt(dto.getSubject().getNameEt() + " - " + resultAsString(r, 10));
                    dto.getSubject().setNameEn(dto.getSubject().getNameEn() + " - " + resultAsString(r, 11));
                }
            }
            
            StudentHigherSubjectResultGradeDto grade = new StudentHigherSubjectResultGradeDto();
            grade.setGrade(resultAsString(r, 12));
            grade.setGradeValue(resultAsString(r, 13));
            grade.setGradeDate(resultAsLocalDate(r, 14));
            grade.getTeachers().add(resultAsString(r, 15));
            grade.setStudyPeriod(resultAsLong(r, 16));
            grade.setGradeMark(resultAsShort(r, 17));
            grade.setGradeNameEt(ClassifierUtil.getNullableNameEt(em.getReference(Classifier.class, resultAsString(r, 12))));
            
            dto.getGrades().add(grade);
            studentResults.add(dto);
        }
        return studentResults;
    }

    private static List<StudentHigherSubjectResultDto> mergeModuleSubjectsAndResults(
            List<StudentHigherSubjectResultDto> moduleSubjects, List<StudentHigherSubjectResultDto> studentResults) {
        List<Long> addedGrades = new ArrayList<>();
        for(StudentHigherSubjectResultDto moduleSubject : moduleSubjects) {
            List<StudentHigherSubjectResultDto> subjectsResults = getStudentResultsForSubject(
                    moduleSubject.getHigherModule().getId(), moduleSubject.getSubject().getId(), studentResults);
            boolean isApelTransfer = subjectsResults.stream().anyMatch(sr -> Boolean.TRUE.equals(sr.getIsApelTransfer()));
            moduleSubject.setIsApelTransfer(Boolean.valueOf(isApelTransfer));
            
            addGrades(moduleSubject, subjectsResults);
            // collect added grades so that they won't be added again
            addedGrades.addAll(StreamUtil.toMappedList(sr -> sr.getId(), subjectsResults));
        }
        addTransferredSubjectsToModules(moduleSubjects, studentResults, addedGrades);
        addResultsForExtraCurriculumSubjects(moduleSubjects, studentResults);
        return moduleSubjects;
    }

    private static List<StudentHigherSubjectResultDto> getStudentResultsForSubject(Long moduleId, Long subjectId,
            List<StudentHigherSubjectResultDto> studentResults) {
        return StreamUtil.toFilteredList(
                sr -> (sr.getHigherModule() == null || sr.getHigherModule().getId().equals(moduleId))
                        && sr.getSubject().getId().equals(subjectId),
                StreamUtil.toFilteredList(sr -> sr.getSubject().getId() != null, studentResults));
    }

    private static void addGrades(StudentHigherSubjectResultDto moduleSubject,
            List<StudentHigherSubjectResultDto> subjectsResults) {
        for(StudentHigherSubjectResultDto subjectResult : subjectsResults) {
            moduleSubject.getGrades().addAll(subjectResult.getGrades());
            subjectResult.setIsExtraCurriculum(Boolean.FALSE);
        }
    }
    
    private static void addTransferredSubjectsToModules(List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults, List<Long> addedGrades) {
        List<StudentHigherSubjectResultDto> transferredSubjects = StreamUtil.toFilteredList(
                sr -> sr.getHigherModule() != null && !addedGrades.contains(sr.getId()), studentResults);
        
        for (StudentHigherSubjectResultDto subject : transferredSubjects) {
            subject.setIsExtraCurriculum(Boolean.FALSE);
            subject.setIsOptional(Boolean.FALSE);
            moduleSubjects.add(subject);
        }
    }

    private static void addResultsForExtraCurriculumSubjects(List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        List<StudentHigherSubjectResultDto> extraCurriculumResults =
                StreamUtil.toFilteredList(sr -> Boolean.TRUE.equals(sr.getIsExtraCurriculum()), studentResults);
        extraCurriculumResults = mergeExtraCurriculumResults(extraCurriculumResults);
        moduleSubjects.addAll(extraCurriculumResults);
    }

    private static List<StudentHigherSubjectResultDto> mergeExtraCurriculumResults(List<StudentHigherSubjectResultDto> extraCurriculumResults) {
        Map<Long, StudentHigherSubjectResultDto> map = new HashMap<>();
        List<StudentHigherSubjectResultDto> extraCurriculumSubjectResults = StreamUtil.toFilteredList(r -> r.getSubject().getId() != null, 
                extraCurriculumResults);
        Iterator<StudentHigherSubjectResultDto> iterator = extraCurriculumSubjectResults.iterator();
        while(iterator.hasNext()) {
            StudentHigherSubjectResultDto studentResult = iterator.next();
            Long subjectId = studentResult.getSubject().getId();
            if(map.containsKey(subjectId)) {
                map.get(subjectId).getGrades().addAll(studentResult.getGrades());
                iterator.remove();
            } else {
                map.put(subjectId, studentResult);
            }
        }
        return extraCurriculumSubjectResults;
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
}
