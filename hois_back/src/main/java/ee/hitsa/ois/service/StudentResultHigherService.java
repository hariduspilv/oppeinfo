package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletionHigherModule;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleResult;
import ee.hitsa.ois.web.dto.student.StudentHigherProgressDto;
import ee.hitsa.ois.web.dto.student.StudentHigherProgressPeriodDto;
import ee.hitsa.ois.web.dto.student.StudentHigherProgressSubjectDto;
import ee.hitsa.ois.web.dto.student.StudentHigherProgressYearDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHigherResult;
import ee.hitsa.ois.domain.student.StudentHigherResultModule;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
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
import ee.hitsa.ois.web.dto.student.StudentHigherResultGradeDto;
import ee.hitsa.ois.web.dto.student.StudentModuleResultDto;
import ee.hitsa.ois.web.dto.student.SubjectResultReplacedSubjectDto;

@Transactional
@Service
public class StudentResultHigherService {

    private static final String REPLACED_SUBJECTS_FROM = "from student_higher_result shr "
            + "join apel_application_record aar on shr.apel_application_record_id = aar.id "
            + "join apel_application_formal_replaced_subject_or_module aafrs on aafrs.apel_application_record_id = aar.id "
            + "join subject s on s.id = aafrs.subject_id";

    @Autowired
    private EntityManager em;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyYearService studyYearService;

    public StudentHigherResultDto positiveHigherResults(Student student, boolean showModules,
            boolean showUncompleted) {
        List<CurriculumVersionHigherModule> modules = new ArrayList<>();
        List<StudentHigherSubjectResultDto> moduleSubjects = new ArrayList<>();
        if (student.getCurriculumVersion() != null) {
            modules = getStudentModules(student);
            moduleSubjects = getModuleSubjects(modules);
        }
        List<StudentHigherSubjectResultDto> studentResults = getStudentSubjectResults(student, true, false);
        List<StudentHigherSubjectResultDto> mergedSubjects = mergeModuleSubjectsAndResults(student, modules,
                moduleSubjects, studentResults, showUncompleted);
        calculateIsOk(mergedSubjects, showUncompleted);

        Map<Long, List<StudentHigherResultGradeDto>> moduleResults = new HashMap<>();
        if (showModules) {
            moduleResults = getStudentModuleResults(student, true);
        }
        List<StudentHigherModuleResultDto> mergedModules = mergeModuleAndResults(modules, moduleResults, true);

        StudentHigherResultDto dto = new StudentHigherResultDto();
        dto.setModules(mergedModules);
        dto.setSubjectResults(filterNegativeResults(mergedSubjects));
        if (showModules) {
            setExtraCurriculumSubjects(dto);
        }
        return dto;
    }

    public List<StudentHigherSubjectResultDto> filterNegativeResults(List<StudentHigherSubjectResultDto> list) {
        return StreamUtil.toFilteredList(r -> Boolean.TRUE.equals(r.getIsOk()), list);
    }

    public StudentHigherResultDto higherResults(Student student) {
        StudentCurriculumCompletion completion = studentService.getStudentCurriculumCompletion(student);
        BigDecimal curriculumCredits = BigDecimal.ZERO;
        List<CurriculumVersionHigherModule> modules = new ArrayList<>();
        List<StudentHigherSubjectResultDto> moduleSubjects = new ArrayList<>();
        if (student.getCurriculumVersion() != null) {
            modules = getStudentModules(student);
            moduleSubjects = getModuleSubjects(modules);
            curriculumCredits = student.getCurriculumVersion().getCurriculum().getCredits();
        }
        List<StudentHigherSubjectResultDto> subjectResults = getStudentSubjectResults(student, false, true);
        List<StudentHigherSubjectResultDto> mergedSubjects = mergeModuleSubjectsAndResults(student, modules,
                moduleSubjects, subjectResults, false);
        calculateIsOk(mergedSubjects, false);

        Map<Long, List<StudentHigherResultGradeDto>> moduleResults = getStudentModuleResults(student, false);
        List<StudentHigherModuleResultDto> mergedModules = mergeModuleAndResults(modules, moduleResults, false);

        StudentHigherResultDto dto = new StudentHigherResultDto();
        dto.setModules(mergedModules);
        setExtraCurriculumModuleResults(dto, moduleResults);
        dto.setSubjectResults(mergedSubjects);
        setExtraCurriculumSubjects(dto);
        calculateModulesCompletion(dto, completion, modulesMarkedComplete(student));
        dto.setCreditsSubmitted(completion.getCredits());
        dto.setCreditsSubmittedConsidered(curriculumCredits.add(completion.getStudyBacklog()));
        dto.setConsideredFulfillmentPercentage(getConsideredCurriculumCompletion(student,
                dto.getCreditsSubmittedConsidered()));
        dto.setAverageGrade(completion.getAverageMark());
        dto.setIsCurriculumFulfilled(completion.isCurriculumFulfilled());
        dto.setFulfillmentPercentage(getCurriculumFulfillmentPercentage(curriculumCredits,
                dto.getCreditsSubmittedConsidered()));
        setStudyPeriodResults(dto);
        return dto;
    }

    public List<CurriculumVersionHigherModule> getStudentModules(Student student) {
        JpaNativeQueryBuilder qb = studentModulesQueryBuilder(student);
        List<?> data = qb.select("cvhm.id", em).getResultList();

        List<CurriculumVersionHigherModule> modules = new ArrayList<>();
        if (!data.isEmpty()) {
            modules = curriculumVersionHigherModules(StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data));
        }
        return modules;
    }

    private List<CurriculumVersionHigherModule> curriculumVersionHigherModules(Set<Long> moduleIds) {
        return em.createQuery("select cvhm from CurriculumVersionHigherModule cvhm "
                        + "join fetch cvhm.curriculumVersion cv "
                        + "join fetch cv.curriculum c "
                        + "where cvhm.id in (?1)", CurriculumVersionHigherModule.class)
                .setParameter(1, moduleIds)
                .getResultList();
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

    public static BigDecimal getCurriculumFulfillmentPercentage(BigDecimal curriculumCredits, BigDecimal submittedCredits) {
        if (curriculumCredits != null && BigDecimal.ZERO.compareTo(curriculumCredits) != 0) {
            return submittedCredits.multiply(BigDecimal.valueOf(100)).divide(curriculumCredits, 1, RoundingMode.DOWN);
        }
        return null;
    }

    // algorithm in HITSAOIS-748
    public BigDecimal getConsideredCurriculumCompletion(Student student, BigDecimal collectedCredits) {
        List<?> data = em.createNativeQuery("select case when jaak_sem > nom_sem then 0 else nom_sem - jaak_sem end * 30 from "
                + "(select nom_sem, coalesce(round(extract (day from nominal_study_end - now()) / 30.5 / 6), 0) jaak_sem from "
                + "(select round(c.study_period/6.0) nom_sem, coalesce(s.nominal_study_end, "
                + "s.study_start + interval '1 month' * c.study_period - interval '1 day') nominal_study_end "
                + "from student s "
                + "join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                + "where s.id = :studentId) x) y")
                .setParameter("studentId", EntityUtil.getId(student))
                .setMaxResults(1).getResultList();
        BigDecimal expectedCredits = !data.isEmpty() ? resultAsDecimal(data.get(0), 0) : BigDecimal.ZERO;
        if (BigDecimal.ZERO.compareTo(expectedCredits) != 0) {
            return collectedCredits.multiply(BigDecimal.valueOf(100)).divide(expectedCredits, 1, RoundingMode.DOWN);
        }
        return BigDecimal.valueOf(100);
    }

    public Long getTotalPositiveGradeCredits(Student student) {
        String query = "from student_higher_result shr ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(query);
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("shr.grade_code in (:positiveGrades)", "positiveGrades", HigherAssessment.GRADE_POSITIVE);
        qb.filter("shr.is_active = true and shr.is_module = false");

        List<?> rows = qb.select("sum(shr.credits)", em).getResultList();
        return resultAsLong(rows.get(0), 0);
    }

    private void setExtraCurriculumModuleResults(StudentHigherResultDto dto,
            Map<Long, List<StudentHigherResultGradeDto>> moduleResults) {
        List<StudentHigherModuleResultDto> extraCurriculumModuleResults = new ArrayList<>();
        if (!moduleResults.isEmpty()) {
            Set<Long> moduleIds = moduleResults.keySet();
            List<CurriculumVersionHigherModule> extraCurriculumModules = curriculumVersionHigherModules(moduleIds);
            extraCurriculumModuleResults = mergeModuleAndResults(extraCurriculumModules, moduleResults, false);
        }
        dto.setExtraCurriculumModuleResults(extraCurriculumModuleResults);
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

    private Map<Long, List<StudentHigherResultGradeDto>> getStudentModuleResults(Student student,
            boolean onlyActiveResults) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_higher_result shr "
                + "left join curriculum_version_hmodule cvh on cvh.id = shr.curriculum_version_hmodule_id "
                + "left join classifier cl on shr.grade_code = cl.code")
                .sort("shr.is_active desc, shr.grade_date");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("shr.is_module = true");

        if (onlyActiveResults) {
            qb.filter("shr.is_active = true");
        }

        List<?> rows = qb.select("cvh.id curriculum_version_hmodule_id, shr.id shr_id, shr.grade_code, shr.grade, "
                + "shr.grade_date, shr.teachers, shr.study_period_id, cl.name_et grade_name_et, cl.name_en grade_name_en, "
                + "shr.is_active", em).getResultList();

        return rows.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            StudentHigherResultGradeDto grade = new StudentHigherResultGradeDto();
            grade.setId(resultAsLong(r, 1));
            grade.setGrade(resultAsString(r, 2));
            grade.setGradeValue(resultAsString(r, 3));
            grade.setGradeDate(resultAsLocalDate(r, 4));
            grade.getTeachers().add(resultAsString(r, 5));
            grade.setStudyPeriod(resultAsLong(r, 6));
            grade.setGradeNameEt(resultAsString(r, 7));
            grade.setGradeNameEn(resultAsString(r, 8));
            grade.setIsActive(resultAsBoolean(r, 9));
            return grade;
        }, Collectors.toList())));
    }

    private List<StudentHigherSubjectResultDto> getStudentSubjectResults(Student student, boolean onlyActiveResults,
            boolean generateNameBeforehand) {
        String query = "from student_higher_result shr "
                + "left join student_higher_result_module shrm on shr.id = shrm.student_higher_result_id "
                + "left join curriculum_version_hmodule cvh on cvh.id = coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) "
                + "left join classifier cl on shr.grade_code = cl.code "
                + "left join apel_application_record aar on shr.apel_application_record_id = aar.id "
                + "left join apel_application aa on aar.apel_application_id = aa.id "
                + "left join apel_school a_s on shr.apel_school_id = a_s.id "
                + "left join classifier country on country.code = a_s.country_code ";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(query).sort("shr.grade_date");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("shr.is_module = false");

        if (onlyActiveResults) {
            qb.filter("shr.is_active = true");
        }

        List<?> rows = qb.select(
                "distinct shr.id, shr.subject_id, shr.subject_name_et, shr.subject_name_en, shr.subject_code, shr.credits, "
                + "cvh.id curriculum_version_hmodule_id, cvh.name_et cvh_name_et, cvh.name_en cvh_name_en, "
                + "coalesce(shrm.is_optional, shr.is_optional) is_optional, "
                + "shr.apel_application_record_id, aar.is_formal_learning, a_s.id as school_id, a_s.name_et, a_s.name_en, "
                + "shr.grade_code, shr.grade, shr.grade_date, shr.teachers, shr.study_period_id, "
                + "cl.name_et grade_name_et, cl.name_en grade_name_en, shr.is_active, "
                + "country.name_et c_name_et, coalesce(country.name_en, country.name_et) c_name_en",
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
            dto.setIsOptional(resultAsBoolean(r, 9));

            Long apelApplicationRecordId = resultAsLong(r, 10);
            Boolean isApelTransfer = apelApplicationRecordId != null ? Boolean.TRUE : Boolean.FALSE;
            dto.setIsApelTransfer(isApelTransfer);
            if (Boolean.TRUE.equals(isApelTransfer)) {
                dto.setIsFormalLearning(resultAsBoolean(r, 11));
                if (Boolean.TRUE.equals(dto.getIsFormalLearning()) && resultAsLong(r, 12) != null) {
                    if (generateNameBeforehand) {
                        dto.getSubject().setNameEt(dto.getSubject().getNameEt() + " - " + resultAsString(r, 13));
                        dto.getSubject().setNameEn(dto.getSubject().getNameEn() + " - " + resultAsString(r, 14));
                    }
                    String schoolNameEt = resultAsString(r, 13);
                    String schoolNameEn = resultAsString(r, 14);
                    schoolNameEn = schoolNameEn != null ? schoolNameEn + " (" + resultAsString(r, 24) + ")"
                            : schoolNameEt + " (" + resultAsString(r, 24) + ")";
                    schoolNameEt = schoolNameEt + " (" + resultAsString(r, 23) + ")";
                    subject.setSchool(new AutocompleteResult(resultAsLong(r, 12), schoolNameEt, schoolNameEn));
                }
            }

            StudentHigherResultGradeDto grade = new StudentHigherResultGradeDto();
            grade.setId(resultAsLong(r, 0));
            grade.setGrade(resultAsString(r, 15));
            grade.setGradeValue(resultAsString(r, 16));
            grade.setGradeDate(resultAsLocalDate(r, 17));
            grade.getTeachers().add(resultAsString(r, 18));
            grade.setStudyPeriod(resultAsLong(r, 19));
            grade.setGradeNameEt(resultAsString(r, 20));
            grade.setGradeNameEn(resultAsString(r, 21));
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
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(REPLACED_SUBJECTS_FROM);
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

    private List<StudentHigherSubjectResultDto> mergeModuleSubjectsAndResults(Student student,
            List<CurriculumVersionHigherModule> modules, List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults, boolean showUncompleted) {
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
        if (showUncompleted) addDeclarationSubjects(moduleSubjects, student);
        return moduleSubjects;
    }

    private void addDeclarationSubjects(List<StudentHigherSubjectResultDto> moduleSubjects, Student student) {
        calculateIsOk(moduleSubjects, true);
        List<Long> addedSubjectIds = moduleSubjects.stream().filter(p -> p.getSubject() != null && p.getIsOk().booleanValue()).map(p -> p.getSubject().getId()).collect(Collectors.toList());
        // Should have only one declaration
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject s on ssp.subject_id = s.id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "join teacher t on sspt.teacher_id = t.id "
                + "join person p on t.person_id = p.id");
        qb.optionalCriteria("s.id not in (:subjectIds)", "subjectIds", addedSubjectIds);
        qb.requiredCriteria("d.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("d.status_code = :declarationStatus", "declarationStatus", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        qb.groupBy("s.id, s.name_et, s.name_en, s.code, s.credits, s.assessment_code");
        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits, s.assessment_code, string_agg(p.firstname||' '||p.lastname,', ') as teacher", em).getResultList();
        for (Object r : data) {
            StudentHigherSubjectResultDto dto = new StudentHigherSubjectResultDto();
            SubjectSearchDto subject = new SubjectSearchDto();
            subject.setId(resultAsLong(r, 0));
            subject.setNameEt(resultAsString(r, 1));
            subject.setNameEn(resultAsString(r, 2));
            subject.setCode(resultAsString(r, 3));
            subject.setCredits(JpaQueryUtil.resultAsDecimal(r, 4));
            subject.setAssessment(resultAsString(r, 5));
            dto.setSubject(subject);
            dto.setAllTeachers(resultAsString(r, 6));
            dto.setIsAddedFromDirective(Boolean.TRUE);
            dto.setIsExtraCurriculum(Boolean.TRUE);
            moduleSubjects.add(dto);
        }
    }

    private static void addTransferedSubjects(Set<Long> moduleIds, List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        Set<Long> subjectIds = StreamUtil.toMappedSet(ms -> ms.getSubject().getId(), moduleSubjects);

        List<StudentHigherSubjectResultDto> transferredSubjects = StreamUtil.toFilteredList(
            sr -> Boolean.TRUE.equals(sr.getGrades().get(0).getIsActive()) &&
                // subjects that don't belong to curriculum module but are added there
                // through apel application or subject module change
                (((sr.getHigherModule() != null && moduleIds.contains(sr.getHigherModule().getId()))
                    && !subjectIds.contains(sr.getSubject().getId()))
                    // apel formal learning external transfer subjects (DATA_TRANSFER_PROCESS - can be without module)
                    || sr.getSubject().getId() == null),
            studentResults);

        for (StudentHigherSubjectResultDto sr : transferredSubjects) {
            boolean isExtraCurriculum = sr.getHigherModule() == null || !moduleIds.contains(sr.getHigherModule().getId());
            sr.setIsExtraCurriculum(Boolean.valueOf(isExtraCurriculum));
            moduleSubjects.add(sr);
        }
    }

    private static List<StudentHigherSubjectResultDto> getStudentResultsForSubject(Long subjectId,
            List<StudentHigherSubjectResultDto> studentResults) {
        // active result and inactive results are filtered separately because the active result needs
        // to exist and needs to have higher module
        List<StudentHigherSubjectResultDto> subjectResults = new ArrayList<>();
        StudentHigherSubjectResultDto activeResult = StreamUtil.nullSafeList(studentResults).stream()
                .filter(sr -> sr.getHigherModule() != null && sr.getSubject() != null 
                        && sr.getSubject().getId() != null && sr.getSubject().getId().equals(subjectId)
                        && Boolean.TRUE.equals(sr.getGrades().get(0).getIsActive()))
                .findFirst().orElse(null);

        if (activeResult != null) {
            List<StudentHigherSubjectResultDto> inactiveResults = StreamUtil.nullSafeList(studentResults).stream()
                    .filter(sr -> sr.getSubject() != null && sr.getSubject().getId() != null
                            && sr.getSubject().getId().equals(subjectId)
                            && Boolean.FALSE.equals(sr.getGrades().get(0).getIsActive()))
                    .collect(Collectors.toList());

            subjectResults.add(activeResult);
            subjectResults.addAll(inactiveResults);
        }
        return subjectResults;
    }

    private static void addGrades(StudentHigherSubjectResultDto moduleSubject,
            List<StudentHigherSubjectResultDto> subjectsResults) {
        for (StudentHigherSubjectResultDto subjectResult : subjectsResults) {
            List<Long> addedGrades = StreamUtil.toMappedList(g -> g.getId(), moduleSubject.getGrades());
            List<StudentHigherResultGradeDto> notAddedGrades = StreamUtil
                    .toFilteredList(g -> !addedGrades.contains(g.getId()), subjectResult.getGrades());
            moduleSubject.getGrades().addAll(notAddedGrades);
            subjectResult.setIsExtraCurriculum(Boolean.FALSE);
        }
    }

    private static void addResultsForExtraCurriculumSubjects(List<StudentHigherSubjectResultDto> moduleSubjects,
            List<StudentHigherSubjectResultDto> studentResults) {
        // subjects without id are already added
        List<StudentHigherSubjectResultDto> extraCurriculumResults = StreamUtil.toFilteredList(
                sr -> Boolean.TRUE.equals(sr.getIsExtraCurriculum()) && sr.getSubject().getId() != null, studentResults);
        mergeExtraCurriculumResults(extraCurriculumResults);
        moduleSubjects.addAll(extraCurriculumResults);
    }

    private static void mergeExtraCurriculumResults(List<StudentHigherSubjectResultDto> extraCurriculumResults) {
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

    private static void calculateIsOk(List<StudentHigherSubjectResultDto> mergedList, boolean showUncompleted) {
        for(StudentHigherSubjectResultDto studentResult : mergedList) {
            studentResult.calculateIsOk(showUncompleted);
        }
    }

    private List<StudentHigherModuleResultDto> mergeModuleAndResults(List<CurriculumVersionHigherModule> modules,
            Map<Long, List<StudentHigherResultGradeDto>> studentResults, boolean onlyPositiveResults) {
        List<StudentHigherModuleResultDto> moduleDtos = StreamUtil.toMappedList(StudentHigherModuleResultDto::of, modules);

        for (StudentHigherModuleResultDto module : moduleDtos) {
            List<StudentHigherResultGradeDto> moduleResults = studentResults.remove(module.getId());
            if (moduleResults != null) {
                module.setGrades(moduleResults);
                StudentHigherResultGradeDto lastGrade = moduleResults.get(0);
                if (lastGrade != null && (!onlyPositiveResults || HigherAssessment.GRADE_POSITIVE.contains(lastGrade.getGrade()))) {
                    module.setLastGrade(lastGrade);
                }
            }
        }
        return moduleDtos;
    }

    private static void calculateModulesCompletion(StudentHigherResultDto dto, StudentCurriculumCompletion completion,
            Map<Long, StudentCurriculumCompletionHigherModule> modulesMarkedComplete) {
        for(StudentHigherModuleResultDto module : dto.getModules()) {
            Long moduleId = module.getId();
            List<StudentHigherSubjectResultDto> modulesPositiveResults = filterModulesPositiveResults(moduleId, dto.getSubjectResults());
            module.setMandatoryCreditsSubmitted(calculateCredits(modulesPositiveResults, Boolean.FALSE));
            module.setOptionalCreditsSubmitted(calculateCredits(modulesPositiveResults, Boolean.TRUE));
            module.setMandatoryDifference(module.getMandatoryCreditsSubmitted().subtract(module.getCompulsoryStudyCredits()));
            module.setOptionalDifference(module.getOptionalCreditsSubmitted().subtract(module.getOptionalStudyCredits()));
            module.setTotalDifference(calculateTotalDifference(module));
            calculateElectiveModulesCompletion(dto.getSubjectResults(), module);
            if (module.getLastGrade() != null) {
                // already graded modules 'marked as complete' can't be changed
                module.setCanMarkComplete(Boolean.FALSE);
            } else if (modulesMarkedComplete.containsKey(moduleId)) {
                module.setStudentCurriculumCompletionHigherModule(modulesMarkedComplete.get(moduleId).getId());
                module.setIsOk(Boolean.TRUE);
            } else {
                module.calculateIsOk();
                if (Boolean.FALSE.equals(module.getIsOk()) && HigherModuleType.KORGMOODUL_V.name().equals(module.getType())) {
                    module.setIsOk(Boolean.valueOf(BigDecimal.ZERO.setScale(1).equals(completion.getStudyOptionalBacklog())));
                }
                // if module is OK without marking it complete then there is no need for marking it complete
                if (Boolean.TRUE.equals(module.getIsOk())) {
                    module.setCanMarkComplete(Boolean.FALSE);
                }
            }
        }
    }

    private Map<Long, StudentCurriculumCompletionHigherModule> modulesMarkedComplete(Student student) {
        List<StudentCurriculumCompletionHigherModule> markedComplete = em.createQuery(
                "select scchm from StudentCurriculumCompletionHigherModule scchm " +
                        "where scchm.student.id = ?1", StudentCurriculumCompletionHigherModule.class)
                .setParameter(1, EntityUtil.getId(student))
                .getResultList();
        return StreamUtil.toMap(m -> EntityUtil.getId(m.getModule()), m -> m, markedComplete);
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

    private void setStudyPeriodResults(StudentHigherResultDto dto) {
        Set<Long> studyPeriodIds = StreamUtil.toMappedSet(g -> g.getStudyPeriod(), 
                dto.getSubjectResults().stream()
                    .filter(r -> r.getLastGrade() != null)
                    .map(StudentHigherSubjectResultDto::getLastGrade)
                    .filter(g -> g.getGradeValue() != null && g.getStudyPeriod() != null));
        studyPeriodIds.addAll(StreamUtil.toMappedSet(g -> g.getStudyPeriod(),
                dto.getModules().stream()
                    .filter(r -> r.getLastGrade() != null)
                    .map(StudentHigherModuleResultDto::getLastGrade)
                    .filter(g -> g.getGradeValue() != null && g.getStudyPeriod() != null)));
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

                HigherAssessment grade = HigherAssessment.valueOf(subjectResult.getLastGrade().getGrade());
                if (grade.getIsDistinctive().booleanValue()) {
                    BigDecimal gradeMark = BigDecimal.valueOf(grade.getMark().longValue());
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
                + "shr.subject_code, shr.credits, shr.grade_code, shr.grade, shr.grade_date, coalesce(shrm.is_optional, shr.is_optional) is_optional "
                + "from student_higher_result shr "
                + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id "
                + "left join curriculum_version_hmodule cvh on cvh.id = coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) "
                + "where student_id = ?1 and (cvh.id is null or cvh.type_code not in (?2)) and shr.is_active = true and shr.is_module = false");
        q.setParameter(1, EntityUtil.getId(student));
        q.setParameter(2, HigherModuleType.FINAL_MODULES);
        List<?> data = q.getResultList();

        return StreamUtil.toMappedList(r -> new StudentModuleResultDto(resultAsLong(r, 0), resultAsLong(r, 1),
                resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsDecimal(r, 5),
                resultAsString(r, 6), resultAsString(r, 7), resultAsLocalDate(r, 8), resultAsBoolean(r, 9)), data);
    }

    public List<AutocompleteResult> higherCurriculumModulesForSelection(Student student) {
        JpaNativeQueryBuilder qb = studentModulesQueryBuilder(student);
        qb.optionalCriteria("cvhm.type_code not in (:finalTypes)", "finalTypes", HigherModuleType.FINAL_MODULES);
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

    public void markModuleComplete(Student student, CurriculumVersionHigherModule module) {
        Map<Long, StudentCurriculumCompletionHigherModule> markedComplete = modulesMarkedComplete(student);
        Long moduleId = EntityUtil.getId(module);
        if (!markedComplete.containsKey(moduleId)) {
            StudentCurriculumCompletionHigherModule higherModuleCompletion = new StudentCurriculumCompletionHigherModule();
            higherModuleCompletion.setStudent(student);
            higherModuleCompletion.setModule(module);
            EntityUtil.save(higherModuleCompletion, em);
        }
    }

    public void removeModuleCompletion(HoisUserDetails user, StudentCurriculumCompletionHigherModule moduleCompletion) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(moduleCompletion, em);
    }

    public StudentHigherProgressDto progress(Student student) {
        List<CurriculumVersionHigherModule> modules = getStudentModules(student);
        List<StudentHigherProgressSubjectDto> subjects = progressSubjects(student, modules);

        StudentHigherProgressDto progress = new StudentHigherProgressDto();
        progress.setYears(progressYearDtos(subjects));
        progress.setExtraCurriculumResults(progressPeriodDto(null, null, progressExtraCurriculumResults(student, subjects)));
        return progress;
    }

    private List<StudentHigherProgressYearDto> progressYearDtos(List<StudentHigherProgressSubjectDto> subjects) {
        List<StudentHigherProgressYearDto> years = new ArrayList<>();
        if (!subjects.isEmpty()) {
            Map<Short, Map<Boolean, List<StudentHigherProgressSubjectDto>>> mappedYears = new LinkedHashMap<>();
            for (StudentHigherProgressSubjectDto subject : subjects) {
                Short yearNr = subject.getStudyYearNumber();
                if (!mappedYears.containsKey(yearNr)) {
                    Map<Boolean, List<StudentHigherProgressSubjectDto>> mappedPeriods = new LinkedHashMap<>();
                    mappedPeriods.put(Boolean.TRUE, new ArrayList<>());
                    mappedPeriods.put(Boolean.FALSE, new ArrayList<>());
                    mappedPeriods.put(null, new ArrayList<>());
                    mappedYears.put(yearNr, mappedPeriods);
                }
                if (Boolean.TRUE.equals(subject.getAutumn())) {
                    mappedYears.get(yearNr).get(Boolean.TRUE).add(subject);
                }
                if (Boolean.TRUE.equals(subject.getSpring())) {
                    mappedYears.get(yearNr).get(Boolean.FALSE).add(subject);
                }
                if (!Boolean.TRUE.equals(subject.getAutumn()) && !Boolean.TRUE.equals(subject.getSpring())) {
                    mappedYears.get(yearNr).get(null).add(subject);
                }
            }

            for (Map.Entry<Short, Map<Boolean, List<StudentHigherProgressSubjectDto>>> year : mappedYears.entrySet()) {
                Map<Boolean, List<StudentHigherProgressSubjectDto>> yearPeriods = year.getValue();
                StudentHigherProgressYearDto yearDto = new StudentHigherProgressYearDto();
                yearDto.setStudyYearNumber(year.getKey());
                for (Map.Entry<Boolean, List<StudentHigherProgressSubjectDto>> period : yearPeriods.entrySet()) {
                    if (!period.getValue().isEmpty()) {
                        yearDto.getPeriods().add(progressPeriodDto(year.getKey(), period.getKey(), period.getValue()));
                    }
                }
                years.add(yearDto);
            }
        }
        return years;
    }

    private StudentHigherProgressPeriodDto progressPeriodDto(Short studyYearNumber, Boolean isAutumn,
            List<StudentHigherProgressSubjectDto> subjects) {
        StudentHigherProgressPeriodDto dto = new StudentHigherProgressPeriodDto();
        dto.setStudyYearNumber(studyYearNumber);
        dto.setAutumn(Boolean.TRUE.equals(isAutumn));
        dto.setSpring(Boolean.FALSE.equals(isAutumn));
        dto.setSubjects(subjects);
        setPeriodStatistics(dto, subjects);
        return dto;
    }

    private static void setPeriodStatistics(StudentHigherProgressPeriodDto dto,
            List<StudentHigherProgressSubjectDto> subjects) {
        BigDecimal totalCredits = BigDecimal.ZERO;
        BigDecimal avgNumerator = BigDecimal.ZERO;
        BigDecimal avgDenominator = BigDecimal.ZERO;

        for (StudentHigherProgressSubjectDto subject : subjects) {
            if (subject.getGrade() != null) {
                HigherAssessment grade = HigherAssessment.valueOf(subject.getGrade());
                if (grade.getIsPositive()) {
                    BigDecimal credits = subject.getCredits();
                    totalCredits = totalCredits.add(subject.getCredits());

                    if (Boolean.TRUE.equals(grade.getIsDistinctive())) {
                        BigDecimal gradeMark = BigDecimal.valueOf(grade.getMark().longValue());
                        avgNumerator = avgNumerator.add(gradeMark.multiply(credits));
                        avgDenominator = avgDenominator.add(credits);
                    }
                }
            }
        }
        dto.setTotal(totalCredits);
        if (BigDecimal.ZERO.compareTo(avgDenominator) != 0) {
            dto.setAverageGrade(avgNumerator.divide(avgDenominator, 3, BigDecimal.ROUND_HALF_UP));
        }
    }

    private List<StudentHigherProgressSubjectDto> progressSubjects(Student student, List<CurriculumVersionHigherModule> modules) {
        if (modules.isEmpty()) {
            return new ArrayList<>();
        }
        Long currentStudyPeriod = studyYearService.getCurrentStudyPeriod(EntityUtil.getId(student.getSchool()));
        Map<String, Object> parameters = new HashMap<>();

        // 'prerequisites' subquery
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject subj2 "
                + "join subject_connect subjc on subjc.primary_subject_id = subj2.id "
                + "left join student_higher_result shr2 on shr2.student_id = s.id "
                    + "and shr2.subject_id = subjc.connect_subject_id and shr2.is_active = true");
        qb.requiredCriteria("subjc.connection_code in (:prerequisites)", "prerequisites",
                EnumUtil.toNameList(SubjectConnection.AINESEOS_EK, SubjectConnection.AINESEOS_EV));
        qb.requiredCriteria("(shr2.id is null or shr2.grade_code not in (:positiveGrades))", "positiveGrades",
                HigherAssessment.GRADE_POSITIVE);
        qb.filter("subj2.id = subj.id");

        parameters.putAll(qb.queryParameters());
        String prerequisites = qb.querySql("1", false);

        String taughtThisSemester = null;
        String declared = null;
        if (currentStudyPeriod != null) {
            // 'is subject taught in current semester' subquery
            qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                    + "join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id");
            qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", currentStudyPeriod);
            qb.filter("ssp.subject_id = subj.id and sspsg.student_group_id = s.student_group_id");

            parameters.putAll(qb.queryParameters());
            taughtThisSemester = qb.querySql("1", false);

            // 'is subject declared' subquery
            qb = new JpaNativeQueryBuilder("from declaration d "
                    + "join declaration_subject ds on ds.declaration_id = d.id "
                    + "join subject_study_period ssp2 on ssp2.id = ds.subject_study_period_id");
            qb.requiredCriteria("d.status_code = :declarationStatus", "declarationStatus",
                    DeclarationStatus.OPINGUKAVA_STAATUS_K);
            qb.requiredCriteria("d.study_period_id = :studyPeriodId", "studyPeriodId", currentStudyPeriod);
            qb.filter("d.student_id = s.id and ssp2.subject_id = subj.id");

            parameters.putAll(qb.queryParameters());
            declared = qb.querySql("1", false);
        }


        qb =  new JpaNativeQueryBuilder("from curriculum_version_hmodule_subject cvhs "
                + "join subject subj on subj.id = cvhs.subject_id "
                + "join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id "
                + "join curriculum_version cv on cv.id = cvh.curriculum_version_id "
                + "join student s on s.id = :studentId "
                + "left join student_higher_result shr on shr.student_id = s.id "
                    + "and shr.subject_id = subj.id and shr.is_active = true "
                + "left join protocol_student ps on ps.id = shr.protocol_student_id "
                + "left join protocol p on p.id = ps.protocol_id "
                + "left join apel_application_record aar on aar.id = shr.apel_application_record_id "
                + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id "
                + "left join curriculum_version_hmodule shrm_cvh on shrm_cvh.id = shrm.curriculum_version_hmodule_id "
                + "left join curriculum_version shrm_cv on shrm_cv.id = shrm_cvh.curriculum_version_id ");
        qb.requiredCriteria("cvh.id in (:moduleIds)", "moduleIds", StreamUtil.toMappedList(EntityUtil::getId, modules));
        qb.parameter("studentId", student.getId());

        qb.sort("cvhs.study_year_number, subj.code");
        String select = "subj.id subj_id, subj.code, subj.name_et subj_name_et, subj.name_en subj_name_en, "
                + "subj.credits, cvhs.is_optional, cvhs.study_year_number, cvhs.is_autumn, cvhs.is_spring, "
                + "cvh.id cvh_id, cvh.name_et cvh_name_et, cvh.name_en cvh_name_en, cvh.type_code, cv.id cv_id, cv.curriculum_id, "
                + "shrm_cvh.id shrm_cvh_id, shrm_cvh.name_et shrm_cvh_name_et, shrm_cvh.name_en shrm_cvh_name_en, "
                + "shrm_cvh.type_code shrm_cvh_type_code, shrm_cv.id shrm_cvh_cv_id, shrm_cv.curriculum_id shrm_cv_curriculum_id, "
                + "shrm.is_optional shrm_is_optional, shr.id shr_id, shr.grade_code, p.id protocol_id, p.is_final, aar.apel_application_id, "
                + "not exists (" + prerequisites + ") prerequisites, "
                + (taughtThisSemester != null ? "exists (" + taughtThisSemester + ")" : "false") + " taught_this_semester, "
                + (declared != null ? "exists (" + declared + ")" : "false") + " declared";
        List<?> data = qb.select(select, em, parameters).getResultList();

        Map<Long, Long> replacedSubjectApplications = replacedSubjectApplications(student);
        return StreamUtil.toMappedList(r -> {
            StudentHigherProgressSubjectDto dto = new StudentHigherProgressSubjectDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setNameEt(resultAsString(r, 2));
            dto.setNameEn(resultAsString(r, 3));
            dto.setCredits(resultAsDecimal(r, 4));
            dto.setOptional(resultAsBoolean(r, 5));
            dto.setStudyYearNumber(resultAsShort(r, 6));
            dto.setAutumn(resultAsBoolean(r, 7));
            dto.setSpring(resultAsBoolean(r, 8));
            dto.setModule(new CurriculumVersionHigherModuleResult(resultAsLong(r, 9), resultAsString(r, 10),
                    resultAsString(r, 11), resultAsString(r, 12), resultAsLong(r, 13), resultAsLong(r, 14)));
            if (resultAsLong(r, 15) != null) {
                dto.setReplacedModule(new CurriculumVersionHigherModuleResult(resultAsLong(r, 15), resultAsString(r, 16),
                        resultAsString(r, 17), resultAsString(r, 18), resultAsLong(r, 19), resultAsLong(r, 20)));
                dto.setReplacedModuleOptional(resultAsBoolean(r, 21));
            }
            dto.setResultId(resultAsLong(r, 22));
            dto.setGrade(resultAsString(r, 23));
            dto.setProtocolId(resultAsLong(r, 24));
            dto.setIsFinalProtocol(resultAsBoolean(r, 25));
            dto.setApelApplicationId(resultAsLong(r, 26));
            dto.setPrerequisitesCompleted(resultAsBoolean(r, 27));
            dto.setTaughtThisSemester(resultAsBoolean(r, 28));
            dto.setDeclared(resultAsBoolean(r, 29));
            dto.setReplacedApelApplicationId(replacedSubjectApplications.get(dto.getId()));
            return dto;
        }, data);
    }

    private Map<Long, Long> replacedSubjectApplications(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(REPLACED_SUBJECTS_FROM);
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", student.getId());
        qb.filter("shr.is_active = true");

        List<?> data = qb.select("s.id, aar.apel_application_id", em).getResultList();
        return data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
    }

    private List<StudentHigherProgressSubjectDto> progressExtraCurriculumResults(Student student,
            List<StudentHigherProgressSubjectDto> curriculumSubjects) {
        List<Long> curriculumResultIds = curriculumSubjects.stream().filter(s -> s.getResultId() != null)
                .map(StudentHigherProgressSubjectDto::getResultId).collect(Collectors.toList());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_higher_result shr "
                + "left join curriculum_version_hmodule cvh on cvh.id = shr.curriculum_version_hmodule_id "
                + "left join curriculum_version cv on cv.id = cvh.curriculum_version_id "
                + "left join protocol_student ps on ps.id = shr.protocol_student_id "
                + "left join protocol p on p.id = ps.protocol_id "
                + "left join apel_application_record aar on aar.id = shr.apel_application_record_id "
                + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id "
                + "left join curriculum_version_hmodule shrm_cvh on shrm_cvh.id = shrm.curriculum_version_hmodule_id "
                + "left join curriculum_version shrm_cv on shrm_cv.id = shrm_cvh.curriculum_version_id");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", student.getId());
        qb.optionalCriteria("shr.id not in (:resultIds)", "resultIds", curriculumResultIds);
        qb.filter("shr.is_active = true and shr.is_module = false");

        String select = "shr.subject_id, shr.subject_code, shr.subject_name_et, shr.subject_name_en, shr.credits, shr.is_optional, "
                + "cvh.id cvh_id, cvh.name_et cvh_name_et, cvh.name_en cvh_name_en, cvh.type_code, cv.id cv_id, cv.curriculum_id, "
                + "shrm_cvh.id shrm_cvh_id, shrm_cvh.name_et shrm_cvh_name_et, shrm_cvh.name_en shrm_cvh_name_en, "
                + "shrm_cvh.type_code shrm_cvh_type_code, shrm_cv.id shrm_cv_id, shrm_cv.curriculum_id shrm_cv_curriculum_id, "
                + "shrm.is_optional shrm_is_optional, shr.id shr_id, shr.grade_code, ps.protocol_id, p.is_final, "
                + "aar.apel_application_id";
        List<?> data = qb.select(select, em).getResultList();

        return StreamUtil.toMappedList(r -> {
            StudentHigherProgressSubjectDto dto = new StudentHigherProgressSubjectDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setNameEt(resultAsString(r, 2));
            dto.setNameEn(resultAsString(r, 3));
            dto.setCredits(resultAsDecimal(r, 4));
            dto.setOptional(resultAsBoolean(r, 5));
            dto.setModule(new CurriculumVersionHigherModuleResult(resultAsLong(r, 6), resultAsString(r, 7),
                    resultAsString(r, 8), resultAsString(r, 9), resultAsLong(r, 10), resultAsLong(r, 11)));
            if (resultAsLong(r, 12) != null) {
                dto.setReplacedModule(new CurriculumVersionHigherModuleResult(resultAsLong(r, 12), resultAsString(r, 13),
                        resultAsString(r, 14), resultAsString(r, 15), resultAsLong(r, 16), resultAsLong(r, 17)));
                dto.setReplacedModuleOptional(resultAsBoolean(r, 18));
            }
            dto.setResultId(resultAsLong(r, 19));
            dto.setGrade(resultAsString(r, 20));
            dto.setProtocolId(resultAsLong(r, 21));
            dto.setIsFinalProtocol(resultAsBoolean(r, 22));
            dto.setApelApplicationId(resultAsLong(r, 23));
            return dto;
        }, data);
    }
}
