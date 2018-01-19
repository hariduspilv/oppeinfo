package ee.hitsa.ois.service;

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
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.repository.StudyPeriodRepository;
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

@Service
public class StudentResultHigherService {

    @Autowired
    private EntityManager em;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    
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
        StudentHigherResultDto dto = new StudentHigherResultDto();

        List<CurriculumVersionHigherModule> modules = StreamUtil.toFilteredList(
                m -> Boolean.FALSE.equals(m.getMinorSpeciality()), student.getCurriculumVersion().getModules());
        List<StudentHigherSubjectResultDto> moduleSubjects = getModuleSubjects(modules);
        List<StudentHigherSubjectResultDto> studentResults = getStudentResults(student);
        List<StudentHigherSubjectResultDto> mergedList = mergeModuleSubjectsAndResults(moduleSubjects, studentResults);
        calculateIsOk(mergedList);
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

    private static List<StudentHigherSubjectResultDto> getModuleSubjects(List<CurriculumVersionHigherModule> modules) {
        List<CurriculumVersionHigherModuleSubject> moduleSubjects = new ArrayList<>();
        for(CurriculumVersionHigherModule module : modules) {
            moduleSubjects.addAll(module.getSubjects());
        }
        List<StudentHigherSubjectResultDto> results = StreamUtil
                .toMappedList(StudentHigherSubjectResultDto::ofFromHigherModuleSubject, moduleSubjects);
        return results;
    }

    private List<StudentHigherSubjectResultDto> getStudentResults(Student student) {
        List<StudentHigherSubjectResultDto> studentResults = new ArrayList<>();
        
        String from = "from student_higher_result shr";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.sort("shr.grade_date");
        
        List<?> rows = qb.select(
                "distinct shr.subject_id, shr.grade_code, shr.grade, shr.grade_date, shr.teachers, shr.study_period_id, shr.grade_mark",
                em).getResultList();

        for (Object r : rows) {
            StudentHigherSubjectResultDto dto = new StudentHigherSubjectResultDto();
            dto.setSubject(SubjectSearchDto.of(EntityUtil.getOptionalOne(Subject.class, resultAsLong(r, 0), em)));
            dto.setIsExtraCurriculum(Boolean.TRUE);
            dto.setIsOk(Boolean.FALSE);
            dto.setIsOptional(Boolean.TRUE);
            
            StudentHigherSubjectResultGradeDto grade = new StudentHigherSubjectResultGradeDto();
            grade.setGrade(resultAsString(r, 1));
            grade.setGradeValue(resultAsString(r, 2));
            grade.setGradeDate(resultAsLocalDate(r, 3));
            grade.getTeachers().add(resultAsString(r, 4));
            grade.setStudyPeriod(resultAsLong(r, 5));
            grade.setGradeMark(resultAsShort(r, 6));
            grade.setGradeNameEt(ClassifierUtil.getNullableNameEt(em.getReference(Classifier.class, resultAsString(r, 1))));
            
            dto.getGrades().add(grade);
            studentResults.add(dto);
        }
        return studentResults;
    }

    private static List<StudentHigherSubjectResultDto> mergeModuleSubjectsAndResults(
            List<StudentHigherSubjectResultDto> moduleSubjects, List<StudentHigherSubjectResultDto> studentResults) {
        for(StudentHigherSubjectResultDto moduleSubject : moduleSubjects) {
            List<StudentHigherSubjectResultDto> subjectsResults = getStudentResultsForSubject(moduleSubject.getSubject().getId(), studentResults);
            addGrades(moduleSubject, subjectsResults);
        }
        addResultsForExtraCurriculumSubjects(moduleSubjects, studentResults);
        return moduleSubjects;
    }

    private static List<StudentHigherSubjectResultDto> getStudentResultsForSubject(Long subjectId, List<StudentHigherSubjectResultDto> studentResults) {
        return StreamUtil.toFilteredList(sr -> sr.getSubject().getId().equals(subjectId), studentResults);
    }

    private static void addGrades(StudentHigherSubjectResultDto moduleSubject,
            List<StudentHigherSubjectResultDto> subjectsResults) {
        for(StudentHigherSubjectResultDto subjectResult : subjectsResults) {
            moduleSubject.getGrades().addAll(subjectResult.getGrades());
            subjectResult.setIsExtraCurriculum(Boolean.FALSE);
        }
    }

    private static void addResultsForExtraCurriculumSubjects(List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        List<StudentHigherSubjectResultDto> extraCurriculumResults =
                StreamUtil.toFilteredList(sr -> Boolean.TRUE.equals(sr.getIsExtraCurriculum()), studentResults);
        mergeEstraCurriculumResutls(extraCurriculumResults);
        moduleSubjects.addAll(extraCurriculumResults);
    }

    private static void mergeEstraCurriculumResutls(List<StudentHigherSubjectResultDto> extraCurriculumResults) {
        Map<Long, StudentHigherSubjectResultDto> map = new HashMap<>();
        Iterator<StudentHigherSubjectResultDto> iterator = extraCurriculumResults.iterator();
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

                if(subjectResult.isDistinctiveAssessment()) {
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
        Set<Long> studyPeriodIds = StreamUtil.toMappedSet(r -> r.getLastGrade().getStudyPeriod(), 
                dto.getSubjectResults().stream()
                .filter(r -> r.getLastGrade() != null && 
                r.getLastGrade().getGradeValue() != null)
                .collect(Collectors.toSet()));
        if(!studyPeriodIds.isEmpty()) {
            List<StudyPeriod> studyPeriods = studyPeriodRepository.findAll(studyPeriodIds);
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

                if(subjectResult.isDistinctiveAssessment()) {
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
