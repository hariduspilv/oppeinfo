package ee.hitsa.ois.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.repository.ProtocolStudentRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentHigherElectiveModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherStudyPeriodResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;

@Service
public class StudentResultHigherService {

    @Autowired
    private ProtocolStudentRepository protocolStudentRepository;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    
    public List<StudentHigherSubjectResultDto> positiveHigherResults(Student student) {
        List<CurriculumVersionHigherModule> modules = student.getCurriculumVersion().getModules()
                .stream().filter(m -> Boolean.FALSE.equals(m.getMinorSpeciality())).collect(Collectors.toList());

        List<StudentHigherSubjectResultDto> moduleSubjects = getModuleSubjects(modules);
        List<StudentHigherSubjectResultDto> studentResults = getStudentResults(student);
        List<StudentHigherSubjectResultDto> mergedList = mergeModuleSubjectsAndResults(moduleSubjects, studentResults);
        return filterNegativeResults(mergedList);
    }
    
    public List<StudentHigherSubjectResultDto> filterNegativeResults(List<StudentHigherSubjectResultDto> list) {
        calculateIsOk(list);
        return list.stream().filter(r -> Boolean.TRUE.equals(r.getIsOk())).collect(Collectors.toList());
    }
    
    public StudentHigherResultDto higherResults(Student student) {
        StudentHigherResultDto dto = new StudentHigherResultDto();

        List<CurriculumVersionHigherModule> modules = student.getCurriculumVersion().getModules()
                .stream().filter(m -> Boolean.FALSE.equals(m.getMinorSpeciality())).collect(Collectors.toList());

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
        List<StudentHigherSubjectResultDto> extraCurriculumSubjects = dto.getSubjectResults().stream()
                .filter(s -> Boolean.TRUE.equals(s.getIsExtraCurriculum())).collect(Collectors.toList());
        if(!extraCurriculumSubjects.isEmpty()) {
            List<StudentHigherModuleResultDto> freeModules = dto.getModules().stream()
                    .filter(m -> HigherModuleType.KORGMOODUL_V.name().equals(m.getType())).collect(Collectors.toList());
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
        List<ProtocolStudent> studentResults = protocolStudentRepository.findByStudent(EntityUtil.getId(student)).stream().filter(ps -> ps.getGrade() != null).collect(Collectors.toList());
        return StreamUtil.toMappedList(StudentHigherSubjectResultDto::ofFromProtocolStudent, studentResults);
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
        return studentResults.stream().filter(sr -> sr.getSubject().getId().equals(subjectId)).collect(Collectors.toList());
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
                studentResults.stream().filter(sr ->
                Boolean.TRUE.equals(sr.getIsExtraCurriculum())).collect(Collectors.toList());
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
            List<StudentHigherSubjectResultDto> subjects = subjectResults.stream()
                    .filter(s -> s.getElectiveModule() != null && electiveModule.getId()
                    .equals(s.getElectiveModule())).collect(Collectors.toList());
            electiveModule.setIsOk(Boolean.valueOf(subjects.stream().allMatch(s -> Boolean.TRUE.equals(s.getIsOk()))));
        }
    }

    private static List<StudentHigherSubjectResultDto> filterModulesPositiveResults(
            Long module, List<StudentHigherSubjectResultDto> subjectResults) {
        return subjectResults.stream().filter(r -> r.getHigherModule() != null &&
                module.equals(r.getHigherModule().getId())).filter(r -> Boolean.TRUE.equals(r.getIsOk())).collect(Collectors.toList());
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
        return studyPeriodResults.stream().filter(s -> s.getLastGrade() != null && 
                Boolean.TRUE.equals(s.getIsOk()) && 
                s.getLastGrade().getStudyPeriod()
                .equals(result.getStudyPeriod().getId()))
                .collect(Collectors.toList());
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
