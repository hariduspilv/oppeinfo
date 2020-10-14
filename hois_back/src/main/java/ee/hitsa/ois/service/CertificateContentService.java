package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.web.dto.student.StudentHigherModuleResultDto;
import ee.hitsa.ois.web.dto.student.StudentHigherResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Streams;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.report.certificate.CertificateEvent;
import ee.hitsa.ois.report.certificate.CertificateEventAcademicLeave;
import ee.hitsa.ois.report.certificate.CertificateEventForeignStudy;
import ee.hitsa.ois.report.certificate.CertificateReport;
import ee.hitsa.ois.report.certificate.CertificateReportGrade;
import ee.hitsa.ois.report.certificate.CertificateReportSession;
import ee.hitsa.ois.report.certificate.CertificateReportStudent;
import ee.hitsa.ois.report.certificate.CertificateStudentResult;
import ee.hitsa.ois.report.certificate.CertificateStudentResultHeader;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateContentCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;

@Service
public class CertificateContentService {

    private static final String PATH = "certificates/";

    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    @Autowired 
    private StudentService studentService;
    @Autowired 
    private ClassifierService classifierService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolService schoolService;

    public String generate(Long schoolId, CertificateContentCommand command) {
        CertificateType type = CertificateType.valueOf(command.getType());
        SchoolType schoolType = schoolService.schoolType(schoolId);
        boolean isHigherSchool = schoolType.isHigher();
        boolean isOnlyHigherSchool = schoolType.isHigher() && !schoolType.isVocational();
        Language lang = Boolean.FALSE.equals(command.getEstonian()) ? Language.EN : Language.ET;
        if(command.getStudent() != null) {
            Student student = em.getReference(Student.class, command.getStudent());
            if(!schoolId.equals(EntityUtil.getId(student.getSchool()))) {
                throw new ValidationFailedException("certificate.error.content");
            }
            return generate(student, type, Boolean.TRUE.equals(command.getShowModules()), Boolean.TRUE.equals(command.getAddOutcomes()),
                    Boolean.TRUE.equals(command.getShowUncompleted()), isHigherSchool, isOnlyHigherSchool, lang);
        }

        if(command.getOtherIdcode() == null) {
            throw new ValidationFailedException("certificate.error.content");
        }

        Person person = personRepository.findByIdcode(command.getOtherIdcode());
        School school = em.getReference(School.class, schoolId);
        CertificateReport report = null;
        if(person != null) {
            report = CertificateReport.of(person, school);
        } else {
            report = CertificateReport.of(school, command.getOtherName(), command.getOtherIdcode());
        }
        report.setIsHigherSchool(Boolean.valueOf(isHigherSchool));
        Map<String, Object> map = new HashMap<>();
        map.put("content", report);
        map.put("lang", lang);
        return templateService.evaluateTemplate(getTemplateName(isHigherSchool, type, lang),
                map);
    }

    public String generate(Student student, CertificateType type, boolean showModules, boolean addOutcomes,
            boolean showUncompleted, boolean isHigherSchool, boolean isOnlyHigherSchool, Language lang) {
        CertificateReport report = CertificateReport.of(student);
        report.setIsHigherSchool(Boolean.valueOf(isHigherSchool));
        StudyYear studyYear = studyYearService.getCurrentStudyYear(EntityUtil.getId(student.getSchool()));
        if(studyYear == null) {
            throw new ValidationFailedException("studyYear.missingCurrent");
        }

        Boolean higher;
        if (report.getStudent().isGuestStudent()) {
            higher = directiveStudyLevel(student);
        } else {
            higher = Boolean.valueOf(StudentUtil.isHigher(student));
        }
        
        report.setStudyYear(studyYear.getYear().getNameEt());
        setFinished(report, student, type);
        setStudentResults(report, student, type, showModules, addOutcomes, showUncompleted, lang);
        if (type.equals(CertificateType.TOEND_LIIK_SOOR)) {
            if (report.getStudent().isGuestStudent()) {
                setAbroadProgramme(report, student);
                if (report.getStudent() != null && (Boolean.TRUE.equals(higher) ? report.getStudent().getResults() != null : report.getStudent().getMappedResults() != null)) {
                    boolean hasGrade = Boolean.TRUE.equals(higher) 
                            ? report.getStudent().getResults().stream().anyMatch(p -> p != null && p.getGradeName() != null)
                            : Streams.concat(report.getStudent().getMappedResults().values().stream().flatMap(e -> e.keySet().stream()),
                                    report.getStudent().getMappedResults().values().stream().flatMap(e -> e.values().stream()).flatMap(e -> e.stream()))
                            .anyMatch(p -> p != null && p.getGradeName() != null);
                    if (hasGrade) {
                        setGradingSystem(report, student, isHigherSchool, isOnlyHigherSchool, showUncompleted, lang);
                    }
                }
            }
            if (Boolean.TRUE.equals(higher)) {
                StudentCurriculumCompletion completion = studentService.getStudentCurriculumCompletion(student);
                report.getStudent().setAverageMark(completion != null ? completion.getAverageMark().setScale(2, RoundingMode.DOWN) : null);
                report.getStudent().setCreditsAll(higherCreditsAll(report.getStudent()));
                setGrades(report, student, lang);
            }
            setEvents(report, student, lang);
        }
        setSessions(report, studyYear, type);
        setLastSession(report, studyYear, type);
        Map<String, Object> map = new HashMap<>();
        map.put("content", report);
        map.put("lang", lang);
        return templateService.evaluateTemplate(getTemplateName(Boolean.TRUE.equals(higher), type, lang), map);
    }

    private BigDecimal higherCreditsAll(CertificateReportStudent reportStudent) {
        if (reportStudent.getMappedResults() != null) {
            BigDecimal credits = BigDecimal.ZERO;
            Map<CertificateStudentResult, List<CertificateStudentResult>> mappedResults = reportStudent
                    .getMappedResults().get(null);
            for (Entry<CertificateStudentResult, List<CertificateStudentResult>> module : mappedResults.entrySet()) {
                if (module.getKey().getGradeValue() != null) {
                    credits = credits.add(module.getKey().getModuleCredits());
                } else {
                    credits = credits.add(StreamUtil.sumBigDecimals(CertificateStudentResult::getHours, module.getValue()));
                }
            }
            return credits;
        }
        return StreamUtil.sumBigDecimals(CertificateStudentResult::getHours, reportStudent.getResults());
    }

    private void setGrades(CertificateReport report, Student student, Language lang) {
        Boolean isLetterGrade = student.getSchool().getIsLetterGrade();
        TypedQuery<Classifier> query = em.createQuery("select c from Classifier c where c.code in (?1) order by c.value desc", Classifier.class);
        query.setParameter(1, HigherAssessment.GRADE_SYSTEM);
        Map<String, Classifier> grades = query.getResultList().stream().collect(Collectors.toMap(g -> g.getCode(), g -> g, (o, n) -> o, LinkedHashMap::new));
        grades.values().forEach(grade -> {
            if (!ClassifierUtil.oneOf(grade, HigherAssessment.KORGHINDAMINE_0, HigherAssessment.KORGHINDAMINE_1,
                    HigherAssessment.KORGHINDAMINE_2, HigherAssessment.KORGHINDAMINE_3,
                    HigherAssessment.KORGHINDAMINE_4, HigherAssessment.KORGHINDAMINE_5)) {
                return;
            }
            if (report.getGrades() == null) {
                report.setGrades(new ArrayList<>());
            }
            CertificateReportGrade gradeReport = new CertificateReportGrade();
            gradeReport.setValue(ReportUtil.gradeValue(grade, isLetterGrade, lang));
            gradeReport.setShortDescription(Language.EN.equals(lang) ? TranslateUtil.getNonNullableNameEn(grade) : grade.getNameEt());
            gradeReport.setDescription(TranslateUtil.optionalTranslate("report.certificate.grades." + grade.getCode(), lang));
            report.getGrades().add(gradeReport);
        });

        try {
            report.setGradesDescription(String.format(
                    TranslateUtil.optionalTranslate("report.certificate.gradesDescription", lang),
                    ReportUtil.gradeValue(grades.get(HigherAssessment.KORGHINDAMINE_1.name()), isLetterGrade, lang),
                    ReportUtil.gradeValue(grades.get(HigherAssessment.KORGHINDAMINE_A.name()), isLetterGrade, lang)));
        } catch (@SuppressWarnings("unused") MissingFormatArgumentException e) {}
    }

    private void setEvents(CertificateReport report, Student student, Language lang) {
        List<CertificateEvent> events = new ArrayList<>();
        events.addAll(findAcademicLeaves(student, lang));
        events.addAll(findForeignStudy(student, lang));
        events.sort(Comparator.comparing(CertificateEvent::getStart).thenComparing(CertificateEvent::getEnd, Comparator.nullsLast(Comparator.naturalOrder())));
        report.getStudent().setEvents(events);
    }
    
    private List<CertificateEventAcademicLeave> findAcademicLeaves(Student student, Language lang) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "
                + "join directive d on ds.directive_id = d.id "
                + "left join study_period sps on ds.study_period_start_id = sps.id "
                + "left join study_period spe on ds.study_period_end_id = spe.id "
                + "left join (select ds2.directive_student_id, ds2.start_date "
                    + "from directive_student ds2 "
                    + "left join directive d2 on d2.id = ds2.directive_id "
                    + "where d2.type_code = :directiveAkadk and d2.status_code = :directiveConfirmed and ds2.canceled = false ) "
                + "AKADK on AKADK.directive_student_id = ds.id ");
        
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("ds.canceled = false");
        qb.requiredCriteria("d.status_code = :directiveConfirmed", "directiveConfirmed", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("d.type_code = :directiveAkad", "directiveAkad", DirectiveType.KASKKIRI_AKAD);
        qb.parameter("directiveAkadk", DirectiveType.KASKKIRI_AKADK.name());
        qb.requiredCriteria(":now between coalesce(sps.start_date, ds.start_date) and "
                + "coalesce(case when AKADK.start_date is null then null else cast(AKADK.start_date - interval '1 day' as date) end, spe.end_date, ds.end_date)", "now", LocalDate.now());
        
        List<?> results = qb.select("coalesce(sps.start_date, ds.start_date) e_start, "
                + "coalesce(case when AKADK.start_date is null then null else cast(AKADK.start_date - interval '1 day' as date) end, spe.end_date, ds.end_date) e_end", em)
                .getResultList();
        
        String name = Language.EN.equals(lang) ? "academic leave" : "akadeemilisel puhkusel";
        return results.stream().map(r -> {
            return new CertificateEventAcademicLeave(name, resultAsLocalDate(r, 0), resultAsLocalDate(r, 1));
        }).collect(Collectors.toList());
    }
    
    private List<CertificateEventForeignStudy> findForeignStudy(Student student, Language lang) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "
                + "join directive d on ds.directive_id = d.id "
                + "left join study_period sps on ds.study_period_start_id = sps.id "
                + "left join study_period spe on ds.study_period_end_id = spe.id "
                + "left join (select ds2.directive_student_id, ds2.start_date "
                    + "from directive_student ds2 "
                    + "left join directive d2 on d2.id = ds2.directive_id "
                    + "where d2.type_code = :directiveValiskatk and d2.status_code = :directiveConfirmed and ds2.canceled = false ) "
                + "VALISKATK on VALISKATK.directive_student_id = ds.id ");
        
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("ds.canceled = false");
        qb.requiredCriteria("d.status_code = :directiveConfirmed", "directiveConfirmed", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("d.type_code = :directiveValis", "directiveValis", DirectiveType.KASKKIRI_VALIS);
        qb.parameter("directiveValiskatk", DirectiveType.KASKKIRI_VALISKATK.name());
        qb.requiredCriteria(":now between coalesce(sps.start_date, ds.start_date) and "
                + "coalesce(case when VALISKATK.start_date is null then null else cast(VALISKATK.start_date - interval '1 day' as date) end, spe.end_date, ds.end_date)", "now", LocalDate.now());
        
        List<?> results = qb.select("coalesce(sps.start_date, ds.start_date) e_start, "
                + "coalesce(case when VALISKATK.start_date is null then null else cast(VALISKATK.start_date - interval '1 day' as date) end, spe.end_date, ds.end_date) e_end", em)
                .getResultList();
        
        String name = Language.EN.equals(lang) ? "study abroad" : "välisõppes";
        return results.stream().map(r -> {
            return new CertificateEventForeignStudy(name, resultAsLocalDate(r, 0), resultAsLocalDate(r, 1));
        }).collect(Collectors.toList());
    }

    private static void setAbroadProgramme(CertificateReport report, Student student) {
        // should only be one KYLALIS type directive per student
        Optional<Classifier> optionalAbroadProgramme = student.getDirectiveStudents().stream()
                .filter(p -> p.getDirective() != null && p.getDirective().getType() != null && DirectiveType.KASKKIRI_KYLALIS.name().equals(EntityUtil.getNullableCode(p.getDirective().getType())))
                .filter(p -> p.getAbroadProgramme() != null)
                .map(p -> p.getAbroadProgramme()).findFirst();
        if (optionalAbroadProgramme.isPresent()) {
            Classifier abroadProgramme = optionalAbroadProgramme.get();
            if (abroadProgramme != null) {
                report.setAbroadProgramme(new AutocompleteResult(null, abroadProgramme.getNameEt(), abroadProgramme.getNameEn()));
            }
        }
    }

    private void setGradingSystem(CertificateReport report, Student student, boolean isHigherSchool,
            boolean isOnlyHigherSchool, boolean showUncompleted, Language lang) {
        Boolean directiveStudyLevel = directiveStudyLevel(student);
        if (directiveStudyLevel != null) {
            report.getStudent().setHigher(Boolean.TRUE.equals(directiveStudyLevel) || isOnlyHigherSchool);
        }
        TypedQuery<Classifier> query = em.createQuery("select c from Classifier c where c.code in (?1) order by c.value desc", Classifier.class);
        if (isHigherSchool) {
            query.setParameter(1, showUncompleted ? HigherAssessment.GRADE_SYSTEM : HigherAssessment.GRADE_POSITIVE);
        } else {
            query.setParameter(1, showUncompleted ? OccupationalGrade.OCCUPATIONAL_GRADE_SYSTEM
                    : OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        }
        List<Classifier> grades = query.getResultList();
        Boolean isLetterGrade = student.getSchool().getIsLetterGrade();
        report.setGradeSystem(grades.stream().map(p ->
                (isHigherSchool && Boolean.TRUE.equals(isLetterGrade) ? p.getValue2() : p.getValue())
                        + " - " + TranslateUtil.name(p, lang)).collect(Collectors.joining("; ")));
    }

    private void setFinished(CertificateReport report, Student student, CertificateType type) {
        if(CertificateType.TOEND_LIIK_LOPET.equals(type)) {
            List<?> result = em.createNativeQuery("select dip_f.full_code, dip.occupation_text, dip.partoccupation_text, dip.curriculum_grade_name_et"
                    + " from directive_student ds"
                    + " join directive d on d.id = ds.directive_id"
                    + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                    + " join form dip_f on dip_f.id = dip.form_id"
                    + " where ds.canceled = false and ds.student_id = ?1"
                    + " and d.type_code = ?2 and d.status_code = ?3"
                    + " and dip.status_code in ?4 and case when dip.status_code = ?6 then dip_f.status_code = ?5 else dip_f.status_code in ?7 end")
                    .setParameter(1, EntityUtil.getId(student))
                    .setParameter(2, DirectiveType.KASKKIRI_LOPET.name())
                    .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                    .setParameter(4, EnumUtil.toNameList(DocumentStatus.LOPUDOK_STAATUS_T, DocumentStatus.LOPUDOK_STAATUS_C))
                    .setParameter(5, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                    .setParameter(6, DocumentStatus.LOPUDOK_STAATUS_T.name())
                    .setParameter(7, EnumUtil.toNameList(FormStatus.LOPUBLANKETT_STAATUS_T, FormStatus.LOPUBLANKETT_STAATUS_R))
                    .getResultList();
            if (!result.isEmpty()) {
                Object r = result.get(0);
                CertificateReportStudent reportStudent = report.getStudent();
                reportStudent.setDiplomaNr(resultAsString(r, 0));
                reportStudent.setOccupationText(resultAsString(r, 1));
                reportStudent.setPartoccupationText(resultAsString(r, 2));
                reportStudent.setCurriculumGradeNameEt(resultAsString(r, 3));
            }
        }
    }

    private void setStudentResults(CertificateReport report, Student student, CertificateType type,
            boolean showModules, boolean addOutcomes, boolean showUncompleted, Language lang) {
        if(CertificateType.TOEND_LIIK_SOOR.equals(type)) {
            boolean higher = report.getStudent().isGuestStudent() ? Boolean.TRUE.equals(directiveStudyLevel(student))
                    : StudentUtil.isHigher(student);
            report.setAddOutcomes(Boolean.valueOf(addOutcomes));
            if (higher) {
                setHigherResults(report, student, showModules, showUncompleted, addOutcomes, lang);
            } else {
                report.getStudent().setMappedResults(getVocationalResults(student, addOutcomes, showUncompleted));
            }
        }
    }

    private void setHigherResults(CertificateReport report, Student student, boolean showModules,
            boolean showUncompleted, boolean addOutcomes, Language lang) {
        StudentHigherResultDto higherResults = studentResultHigherService.positiveHigherResults(student,
                showModules, showUncompleted);
        List<StudentHigherModuleResultDto> modules = higherResults.getModules().stream()
                .sorted(CurriculumUtil.higherModuleComparator(lang)).collect(Collectors.toList());
        List<StudentHigherSubjectResultDto> subjectResults = higherResults.getSubjectResults();

        Map<Long, AutocompleteResult> schoolsById = new LinkedHashMap<>();
        Map<CertificateStudentResult, Long> resultBySchoolId = new HashMap<>();
        List<CertificateStudentResult> results = new ArrayList<>();

        subjectResults.forEach(r -> {
            CertificateStudentResult result = CertificateStudentResult.of(r);
            results.add(result);
            if (r.getSubject().getSchool() != null) {
                AutocompleteResult school = r.getSubject().getSchool();
                if (!schoolsById.containsKey(school.getId())) {
                    schoolsById.put(school.getId(), new AutocompleteResult(null, school.getNameEt(), school.getNameEn()));
                }
                resultBySchoolId.put(result, school.getId());
            }
        });
        results.sort(StreamUtil.comparingWithNullsLast(r -> DateUtils.parseDate(r.getDate())));

        if (addOutcomes) {
            addHigherOutcomes(subjectResults, results);
        }

        List<AutocompleteResult> apelSchools = new ArrayList<>();
        results.forEach(r -> {
            if (resultBySchoolId.containsKey(r)) {
                AutocompleteResult apelSchool = schoolsById.get(resultBySchoolId.get(r));
                if (apelSchool.getId() == null) {
                    apelSchool.setId(Long.valueOf(apelSchools.size() + 1));
                    apelSchools.add(apelSchool);
                }
                r.setSubject(r.getSubject() + " *" + apelSchool.getId());
                r.setSubjectEn(r.getSubjectEn() + " *" + apelSchool.getId());
            }
        });
        report.getStudent().setApelSchools(apelSchools);

        if (showModules) {
            Map<Long, List<CertificateStudentResult>> resultsByModules = new HashMap<>();
            results.forEach(r -> {
                Long moduleId = r.getModule().getId();
                if (!resultsByModules.containsKey(moduleId)) {
                    List<CertificateStudentResult> moduleResults = new ArrayList<>();
                    moduleResults.add(r);
                    resultsByModules.put(moduleId, moduleResults);
                } else {
                    resultsByModules.get(moduleId).add(r);
                }
            });

            LinkedHashMap<CertificateStudentResult, List<CertificateStudentResult>> mappedResults = new LinkedHashMap<>();
            for (StudentHigherModuleResultDto module : modules) {
                CertificateStudentResult moduleResult = CertificateStudentResult.of(module);
                List<CertificateStudentResult> moduleSubjectResults = resultsByModules.containsKey(module.getId()) ?
                        resultsByModules.get(module.getId()) : new ArrayList<>();
                if (moduleResult.getGradeValue() != null || !moduleSubjectResults.isEmpty()) {
                    mappedResults.put(moduleResult, moduleSubjectResults);
                }
            }
            // vocational results are mapped by module type, higher results are not
            report.getStudent().setMappedResults(Collections.singletonMap(null, mappedResults));
            // subject results are need for calculating credits total and outcome rows
            // subjects should be in the same order as they appear in modules
            report.getStudent().setResults(mappedResults.entrySet().stream().flatMap(m -> m.getValue().stream())
                    .collect(Collectors.toList()));
        } else {
            report.getStudent().setResults(results);
        }
    }

    private Map<CertificateStudentResultHeader, Map<CertificateStudentResult, List<CertificateStudentResult>>> getVocationalResults(Student student, boolean addOutcomes, boolean showUncompleted) {
        List<StudentVocationalResultModuleThemeDto> data = studentService.studentVocationalResults(student,
                showUncompleted, false);
        if (!showUncompleted) {
            data = StreamUtil.toFilteredList(r -> r.getGrade() != null && OccupationalGrade.isPositive(r.getGrade().getCode()), data);
        }
        
        Map<String, Classifier> grades = getVocationalGrades();
        Long curriculumId = student.getCurriculumVersion() != null ? student.getCurriculumVersion().getCurriculum().getId() : null;
        String cvCode = student.getCurriculumVersion() != null ? student.getCurriculumVersion().getCode() : null;
        List<CertificateStudentResult> results = StreamUtil.toMappedList(r -> CertificateStudentResult.of(r, grades, curriculumId), data);
        if (addOutcomes) {
            addVocationalOutcomes(data, results);
        }
        
        // For apel it has a negative ID as it takes student_vocational_result.id * -1
        LinkedHashMap<String, List<CertificateStudentResult>> mappedResultsById = results.stream()
                .collect(
                        Collectors.groupingBy(
                                r -> String.format("%d-%d", r.getModule().getId(),
                                        r.getIsSameCurriculum().booleanValue() ? Long.valueOf(0)
                                                : r.getOccupationModuleId()),
                                LinkedHashMap::new, Collectors.mapping(r -> r, Collectors.toList())));
        
        // Sort theme/outcome inside of module
        mappedResultsById.values().forEach(v -> v.sort(Comparator.comparing(
                    (CertificateStudentResult r) -> Boolean.valueOf(!(r.getOutcome() == null && r.getTheme() == null)))
                    .thenComparing(StreamUtil.comparingWithNullsLast(r -> DateUtils.parseDate(r.getDate())))));

        LinkedHashMap<CertificateStudentResult, List<CertificateStudentResult>> mappedResults = new LinkedHashMap<>();
        ClassifierCache cache = new ClassifierCache(classifierService);
        mappedResultsById.forEach((id, values) -> {
            CertificateStudentResult header = null;
            for (int i = 0; i < values.size(); i++) {
                CertificateStudentResult v = values.get(i);
                if (v.getOutcome() == null && v.getTheme() == null) {
                    header = v;
                    mappedResults.put(v, new ArrayList<>());
                } else if (header != null) {
                    mappedResults.get(header).add(v);
                }
                // If it is from the same curriculum then it should be considered from the same version.
                if (v.getIsSameCurriculum().booleanValue()) {
                    v.setVersionCode(cvCode);
                }
                if (v.getModuleCode() != null && v.getModuleCode().getCode() != null) {
                    Classifier moduleCode = cache.getByCode(v.getModuleCode().getCode(), MainClassCode.KUTSEMOODUL);
                    v.getModuleCode().setNameEt(moduleCode.getNameEt());
                    v.getModuleCode().setNameEn(TranslateUtil.getNonNullableNameEn(moduleCode));
                }
            }
            if (header == null) {
                header = values.get(0);
                header.setIsHeader(Boolean.TRUE);
                mappedResults.put(header, values);
            }
        });
        
        // Sort modules
        Comparator<Entry<CertificateStudentResult, List<CertificateStudentResult>>> comparator = Comparator
                .comparing((Entry<CertificateStudentResult, List<CertificateStudentResult>> entry) -> entry.getKey().getIsSameCurriculum(), Comparator.reverseOrder())
                .thenComparingInt(entry -> CurriculumUtil.vocationalModuleOrderNr(entry.getKey().getModuleCode().getCode()))
                .thenComparing(StreamUtil.comparingWithNullsLast(entry -> entry.getKey().getOrderNr()))
                .thenComparing(StreamUtil.comparingWithNullsLast(
                        entry -> {
                            return DateUtils.parseDate(entry.getKey().getDate() != null ? entry.getKey().getDate() : entry.getValue().get(0).getDate());
                        }))
                ;
        
        ArrayList<Entry<CertificateStudentResult, List<CertificateStudentResult>>> listOfEntries = new ArrayList<>(mappedResults.entrySet());
        Collections.sort(listOfEntries, comparator);
        
        // "Extra curricular performances" header
        CertificateStudentResultHeader extraCurricula = new CertificateStudentResultHeader("EXTRA", "Õppekavavälised sooritused", "Extra curricular performances");
        LinkedHashMap<String, CertificateStudentResultHeader> categoriesByCode = new LinkedHashMap<>();
        LinkedHashMap<CertificateStudentResultHeader, Map<CertificateStudentResult, List<CertificateStudentResult>>> resultsByCategories = new LinkedHashMap<>();
        listOfEntries.forEach(entry -> {
            mappedResults.put(entry.getKey(), entry.getValue());
            if (entry.getKey().getIsSameCurriculum().booleanValue()) {
                ClassifierDto moduleCode = entry.getKey().getModuleCode();
                if (!categoriesByCode.containsKey(moduleCode.getCode())) {
                    CertificateStudentResultHeader header = new CertificateStudentResultHeader(moduleCode.getCode(), moduleCode.getNameEt(), moduleCode.getNameEn());
                    categoriesByCode.put(moduleCode.getCode(), header);
                }
                if (resultsByCategories.get(categoriesByCode.get(moduleCode.getCode())) == null) {
                    resultsByCategories.put(categoriesByCode.get(moduleCode.getCode()), new LinkedHashMap<>());
                }
                resultsByCategories.get(categoriesByCode.get(moduleCode.getCode())).put(entry.getKey(), entry.getValue());
            } else {
                if (resultsByCategories.get(extraCurricula) == null) {
                    resultsByCategories.put(extraCurricula, new LinkedHashMap<>());
                }
                resultsByCategories.get(extraCurricula).put(entry.getKey(), entry.getValue());
            }
        });
        return resultsByCategories;
    }

    private void addHigherOutcomes(List<StudentHigherSubjectResultDto> data, List<CertificateStudentResult> results) {
        Set<Long> subjectIds = new HashSet<>();
        for (StudentHigherSubjectResultDto dto : data) {
            SubjectSearchDto subject = dto.getSubject();
            if (subject != null && subject.getId() != null) {
                subjectIds.add(subject.getId());
            }
        }
        
        List<AutocompleteResult> subjectOutcomes = getSubjectOutcomes(subjectIds);
        
        Iterator<CertificateStudentResult> resultsIterator = results.iterator();
        for (StudentHigherSubjectResultDto dto : data) {
            CertificateStudentResult result = resultsIterator.next();
            SubjectSearchDto subject = dto.getSubject();
            if (subject != null && subject.getId() != null) {
                Optional<AutocompleteResult> outcome = subjectOutcomes.stream().filter(p -> subject.getId().equals(p.getId())).findFirst();
                if (outcome.isPresent()) {
                    result.setOutcomes(outcome.get().getNameEt());
                    result.setOutcomesEn(TranslateUtil.getNonNullableNameEn(outcome.get()));
                }
            }
        }
    }

    private List<AutocompleteResult> getSubjectOutcomes(Set<Long> subjectIds) {
        if (subjectIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<?> rows = em.createNativeQuery("select id, outcomes_et, outcomes_en"
                + " from subject"
                + " where id in ?1")
            .setParameter(1, subjectIds)
            .getResultList();
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), rows);
    }

    private void addVocationalOutcomes(List<StudentVocationalResultModuleThemeDto> data, List<CertificateStudentResult> results) {
        Set<Long> moduleIds = new HashSet<>();
        Set<Long> themeIds = new HashSet<>();
        for (StudentVocationalResultModuleThemeDto dto : data) {
            AutocompleteResult theme = dto.getTheme();
            if (theme != null && theme.getId() != null) {
                themeIds.add(theme.getId());
            } else {
                AutocompleteResult module = dto.getModule();
                if (module != null && module.getId() != null) {
                    moduleIds.add(module.getId());
                }
            }
        }
        
        List<AutocompleteResult> curriculumModuleOutcomes = getCurriculumModuleOutcomes(moduleIds, themeIds);
        Map<Long, List<Long>> moduleOutcomes = getModuleOutcomes(moduleIds);
        Map<Long, List<Long>> themeOutcomes = getThemeOutcomes(themeIds);
        
        Iterator<CertificateStudentResult> resultsIterator = results.iterator();
        for (StudentVocationalResultModuleThemeDto dto : data) {
            CertificateStudentResult result = resultsIterator.next();
            List<Long> outcomeIds = null;
            AutocompleteResult theme = dto.getTheme();
            if (theme != null && theme.getId() != null) {
                outcomeIds = themeOutcomes.get(theme.getId());
            } else {
                AutocompleteResult module = dto.getModule();
                if (module != null && module.getId() != null) {
                    outcomeIds = moduleOutcomes.get(module.getId());
                }
            }
            if (outcomeIds != null) {
                List<AutocompleteResult> outcomes = outcomeIds.stream()
                        .filter(p -> curriculumModuleOutcomes.stream().filter(s -> p.equals(s.getId())).findFirst().isPresent())
                        .map(p -> curriculumModuleOutcomes.stream().filter(s -> p.equals(s.getId())).findFirst().get())
                        .collect(Collectors.toList());
                result.setOutcomes(outcomes.stream().map(p -> p.getNameEt()).collect(Collectors.joining(", ")));
                result.setOutcomesEn(outcomes.stream().map(p -> TranslateUtil.getNonNullableNameEn(p)).collect(Collectors.joining(", ")));
            }
        }
    }

    private Map<Long, List<Long>> getThemeOutcomes(Set<Long> themeIds) {
        Map<Long, List<Long>> result = new HashMap<>();
        if (themeIds.isEmpty()) {
            return result;
        }
        List<?> rows = em.createNativeQuery("select cvomo.curriculum_version_omodule_theme_id, cvomo.curriculum_module_outcomes_id"
                + " from curriculum_version_omodule_outcomes cvomo"
                + " join curriculum_module_outcomes cmo on cmo.id = cvomo.curriculum_module_outcomes_id"
                + " where cvomo.curriculum_version_omodule_theme_id in ?1"
                + " order by cmo.order_nr")
            .setParameter(1, themeIds)
            .getResultList();
        for (Object r : rows) {
            result.computeIfAbsent(resultAsLong(r, 0), k -> new ArrayList<>()).add(resultAsLong(r, 1));
        }
        return result;
    }

    private Map<Long, List<Long>> getModuleOutcomes(Set<Long> moduleIds) {
        Map<Long, List<Long>> result = new HashMap<>();
        if (moduleIds.isEmpty()) {
            return result;
        }
        List<?> rows = em.createNativeQuery("select cmo.curriculum_module_id, cmo.id as cmo_id"
                + " from curriculum_module_outcomes cmo"
                + " where cmo.curriculum_module_id in ?1"
                + " order by cmo.order_nr")
            .setParameter(1, moduleIds)
            .getResultList();
        for (Object r : rows) {
            result.computeIfAbsent(resultAsLong(r, 0), k -> new ArrayList<>()).add(resultAsLong(r, 1));
        }
        return result;
    }

    private List<AutocompleteResult> getCurriculumModuleOutcomes(Set<Long> moduleIds, Set<Long> themeIds) {
        if (moduleIds.isEmpty() && themeIds.isEmpty()) {
            return new ArrayList<>();
        }
        String sql = "select cmo.id, cmo.outcome_et, cmo.outcome_en"
                + " from curriculum_module_outcomes cmo"
                + " where cmo.id in (";
        if (!moduleIds.isEmpty()) {
            sql += "select cmo.id"
                + " from curriculum_module_outcomes cmo"
                + " where cmo.curriculum_module_id in (?1)";
            if (!themeIds.isEmpty()) {
                sql += " union";
            }
        }
        if (!themeIds.isEmpty()) {
            sql += " select cvomo.curriculum_module_outcomes_id"
                + " from curriculum_version_omodule_outcomes cvomo"
                + " where cvomo.curriculum_version_omodule_theme_id in ?2";
        }
        sql += ")";
        Query query = em.createNativeQuery(sql);
        if (!moduleIds.isEmpty()) {
            query.setParameter(1, moduleIds);
        }
        if (!themeIds.isEmpty()) {
            query.setParameter(2, themeIds);
        }
        List<?> rows = query.getResultList();
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)), rows);
    }

    private static void setSessions(CertificateReport report, StudyYear studyYear, CertificateType type) {
        if(CertificateType.TOEND_LIIK_SESS.equals(type)) {
            report.setSessions(StreamUtil.toMappedList(CertificateReportSession::new,
                    currentStudyYearsSessions(studyYear)));
        }
    }

    private static void setLastSession(CertificateReport report, StudyYear studyYear, CertificateType type) {
        if(CertificateType.TOEND_LIIK_KONTAKT.equals(type)) {
            LocalDateTime now = LocalDateTime.now();
            List<StudyPeriodEvent> sessions =
                    StreamUtil.toFilteredList(e -> !e.getStart().isAfter(now), currentStudyYearsSessions(studyYear));

            if(!sessions.isEmpty()) {
                StudyPeriodEvent lastSession = sessions.get(sessions.size() - 1);
                report.setLastSession(new CertificateReportSession(lastSession));
            }
        }
    }

    private static List<StudyPeriodEvent> currentStudyYearsSessions(StudyYear studyYear) {
        List<StudyPeriodEvent> sessions = StreamUtil.toFilteredList(e -> isSession(e), studyYear.getStudyPeriodEvents());
        Collections.sort(sessions, StreamUtil.comparingWithNullsLast(StudyPeriodEvent::getStart));
        return sessions;
    }

    private static boolean isSession(StudyPeriodEvent event) {
        return ClassifierUtil.equals(StudyPeriodEventType.SYNDMUS_SESS, event.getEventType());
    }

    private Map<String, Classifier> getVocationalGrades() {
        List<Classifier> grades = classifierService.findAllByMainClassCode(MainClassCode.KUTSEHINDAMINE);
        return StreamUtil.toMap(Classifier::getCode, grades);
    }

    private static String getTemplateName(boolean isHigher, CertificateType type, Language lang) {
        String fileName = isHigher ? type.getHigherCertificate(lang) : type.getVocationalCertificate(lang);
        return PATH + fileName;
    }

    //TODO: rename- directive study level = true/false ?
    public Boolean directiveStudyLevel(Student student) {
        List<?> directiveStudyLevels = em.createNativeQuery("select d.is_higher from directive d "
                + "join directive_student ds on ds.directive_id = d.id and ds.student_id = ?1 "
                + "where d.type_code = ?2")
            .setParameter(1, EntityUtil.getId(student))
            .setParameter(2, DirectiveType.KASKKIRI_KYLALIS.name())
            .setMaxResults(1).getResultList();
        return JpaQueryUtil.resultAsBoolean(directiveStudyLevels.get(0), 0);
    }
}
