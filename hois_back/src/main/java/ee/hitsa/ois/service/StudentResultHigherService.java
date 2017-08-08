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

import org.apache.commons.collections.CollectionUtils;
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
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.student.StudentHigherElectiveModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;

@Service
public class StudentResultHigherService {

    @Autowired
    private ProtocolStudentRepository protocolStudentRepository;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    
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
        setStudyPeriods(dto);
        return dto;
    }

    private void setExtraCurriculumSubjects(StudentHigherResultDto dto) {
        List<StudentHigherSubjectResultDto> extraCurriculumSubjects = dto.getSubjectResults().stream()
                .filter(s -> Boolean.TRUE.equals(s.getIsExtraCurriculum())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(extraCurriculumSubjects)) {
            List<StudentHigherModuleResultDto> freeModules = dto.getModules().stream()
                    .filter(m -> HigherModuleType.KORGMOODUL_V.name().equals(m.getType())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(freeModules)) {
                StudentHigherModuleResultDto freeModule = StudentHigherModuleResultDto.createFreeModule();
                setSubjectsToModule(freeModule, extraCurriculumSubjects);
                dto.getModules().add(freeModule);
            } else if (freeModules.size() == 1) {
                setSubjectsToModule(freeModules.get(0), extraCurriculumSubjects);
            }
        }
    }

    private void setSubjectsToModule(StudentHigherModuleResultDto module, List<StudentHigherSubjectResultDto> subjects) {
        for(StudentHigherSubjectResultDto subject : subjects) {
            subject.setHigherModule(new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
        }
    }

    private List<StudentHigherSubjectResultDto> getModuleSubjects(List<CurriculumVersionHigherModule> modules) {
        List<CurriculumVersionHigherModuleSubject> moduleSubjects = new ArrayList<>();
        for(CurriculumVersionHigherModule module : modules) {
            moduleSubjects.addAll(module.getSubjects());
        }
        List<StudentHigherSubjectResultDto> results = StreamUtil
                .toMappedList(StudentHigherSubjectResultDto::ofFromHigherModuleSubject, moduleSubjects);
        return results;
    }

    private List<StudentHigherSubjectResultDto> getStudentResults(Student student) {
        List<ProtocolStudent> studentResults = protocolStudentRepository.findByStudent(EntityUtil.getId(student));
        return StreamUtil.toMappedList(StudentHigherSubjectResultDto::ofFromProtocolStudent, studentResults);
    }

    private List<StudentHigherSubjectResultDto> mergeModuleSubjectsAndResults(
            List<StudentHigherSubjectResultDto> moduleSubjects, List<StudentHigherSubjectResultDto> studentResults) {
        for(StudentHigherSubjectResultDto moduleSubject : moduleSubjects) {
            List<StudentHigherSubjectResultDto> subjectsResults = getStudentResultsForSubject(moduleSubject.getSubject().getId(), studentResults);
            addGrades(moduleSubject, subjectsResults);
        }
        addResultsForExtraCurriculumSubjects(moduleSubjects, studentResults);
        return moduleSubjects;
    }

    private List<StudentHigherSubjectResultDto> getStudentResultsForSubject(Long subjectId, List<StudentHigherSubjectResultDto> studentResults) {
        return studentResults.stream().filter(sr -> sr.getSubject().getId().equals(subjectId)).collect(Collectors.toList());
    }

    private void addGrades(StudentHigherSubjectResultDto moduleSubject,
            List<StudentHigherSubjectResultDto> subjectsResults) {
        for(StudentHigherSubjectResultDto subjectResult : subjectsResults) {
            moduleSubject.getGrades().addAll(subjectResult.getGrades());
            subjectResult.setIsExtraCurriculum(Boolean.FALSE);
        }
    }

    private void addResultsForExtraCurriculumSubjects(List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        List<StudentHigherSubjectResultDto> extraCurriculumResults =
                studentResults.stream().filter(sr ->
                Boolean.TRUE.equals(sr.getIsExtraCurriculum())).collect(Collectors.toList());
        mergeEstraCurriculumResutls(extraCurriculumResults);
        moduleSubjects.addAll(extraCurriculumResults);
    }

    private void mergeEstraCurriculumResutls(List<StudentHigherSubjectResultDto> extraCurriculumResults) {
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

    private void calculateIsOk(List<StudentHigherSubjectResultDto> mergedList) {
        for(StudentHigherSubjectResultDto studentResult : mergedList) {
            studentResult.calculateIsOk();
        }
    }

    private void calculateModulesCompletion(StudentHigherResultDto dto) {
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


    private void calculateElectiveModulesCompletion(List<StudentHigherSubjectResultDto> subjectResults,
            StudentHigherModuleResultDto module) {
        for(StudentHigherElectiveModuleResultDto electiveModule : module.getElectiveModulesResults()) {
            List<StudentHigherSubjectResultDto> subjects = subjectResults.stream()
                    .filter(s -> s.getElectiveModule() != null && electiveModule.getId()
                    .equals(s.getElectiveModule())).collect(Collectors.toList());
            electiveModule.setIsOk(subjects.stream().allMatch(s -> Boolean.TRUE.equals(s.getIsOk())));
        }
    }

    private List<StudentHigherSubjectResultDto> filterModulesPositiveResults(
            Long module, List<StudentHigherSubjectResultDto> subjectResults) {
        return subjectResults.stream().filter(r -> r.getHigherModule() != null &&
                module.equals(r.getHigherModule().getId())).filter(r -> Boolean.TRUE.equals(r.getIsOk())).collect(Collectors.toList());
    }

    private BigDecimal calculateCredits(List<StudentHigherSubjectResultDto> modulesPositiveResults, Boolean isOptional) {
        Optional<BigDecimal> credits = modulesPositiveResults.stream().filter(r -> isOptional.equals(r.getIsOptional()))
                .map(r -> r.getSubject().getCredits()).reduce((c, sum) -> c.add(sum));
        return credits.isPresent() ? credits.get() : BigDecimal.ZERO;
    }

    private BigDecimal calculateTotalDifference(StudentHigherModuleResultDto module) {

        if (optionalCreditsDebt(module) && !mandatoryCreditsDebt(module)) {
            return module.getOptionalDifference();
        } else if(!optionalCreditsDebt(module) && mandatoryCreditsDebt(module)) {
            return module.getMandatoryDifference();
        }
        return module.getOptionalDifference().add(module.getMandatoryDifference());
    }

    private boolean optionalCreditsDebt(StudentHigherModuleResultDto module) {
        return BigDecimal.ZERO.compareTo(module.getOptionalDifference()) == 1;
    }

    private boolean mandatoryCreditsDebt(StudentHigherModuleResultDto module) {
        return BigDecimal.ZERO.compareTo(module.getMandatoryDifference()) == 1;
    }

    private void calculateAverageGrade(StudentHigherResultDto dto) {
        BigDecimal numerator = BigDecimal.ZERO;
        BigDecimal denominator = BigDecimal.ZERO;

        BigDecimal creditsSubmittedConsidered = BigDecimal.ZERO;
        BigDecimal creditsSubmitted = BigDecimal.ZERO;

        for(StudentHigherSubjectResultDto subjectResult : dto.getSubjectResults()) {

            if(Boolean.TRUE.equals(subjectResult.getIsOk())) {
                BigDecimal credits = subjectResult.getSubject().getCredits();

                if(subjectResult.isDistinctiveAssessment()) {
                    BigDecimal gradeMark = BigDecimal.valueOf(subjectResult.getLastGrade().getGradeMark());
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

    private void calculateCurriculumCompletion(StudentHigherResultDto dto) {
        boolean isOk = !CollectionUtils.isEmpty(dto.getModules()) && dto.getModules().stream().allMatch(m -> Boolean.TRUE.equals(m.getIsOk()));
        dto.setIsCurriculumFulfilled(Boolean.valueOf(isOk));
    }
    

    private void setStudyPeriods(StudentHigherResultDto dto) {
        Set<Long> studyPeriodIds = StreamUtil.toMappedSet(r -> r.getLastGrade().getStudyPeriod(), 
                dto.getSubjectResults().stream()
                .filter(r -> r.getLastGrade() != null && 
                r.getLastGrade().getGradeValue() != null)
                .collect(Collectors.toSet()));
        if(!CollectionUtils.isEmpty(studyPeriodIds)) {
            List<StudyPeriod> studyPeriods = studyPeriodRepository.findAll((root, query, cb) -> {
                return root.get("id").in(studyPeriodIds);
            });
            dto.setStudyPeriods(StreamUtil.toMappedList(StudyPeriodDto::of, studyPeriods));
        }
    }
}
