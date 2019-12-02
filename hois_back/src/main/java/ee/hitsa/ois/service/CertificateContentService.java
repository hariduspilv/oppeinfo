package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
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
import ee.hitsa.ois.report.certificate.CertificateReport;
import ee.hitsa.ois.report.certificate.CertificateReportSession;
import ee.hitsa.ois.report.certificate.CertificateReportStudent;
import ee.hitsa.ois.report.certificate.CertificateStudentResult;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateContentCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
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
        Boolean estonian = command.getEstonian();
        if(command.getStudent() != null) {
            Student student = em.getReference(Student.class, command.getStudent());
            if(!schoolId.equals(EntityUtil.getId(student.getSchool()))) {
                throw new ValidationFailedException("certificate.error.content");
            }
            return generate(student, type, Boolean.TRUE.equals(command.getAddOutcomes()), isHigherSchool, Boolean.TRUE.equals(command.getShowUncompleted()), estonian, isOnlyHigherSchool);
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
        if (estonian != null) {
            if (estonian.booleanValue()) {
                map.put("lang", Language.ET);
            } else {
                map.put("lang", Language.EN);
            }
        }
        return templateService.evaluateTemplate(getTemplateName(isHigherSchool, type, command.getEstonian()),
                map);
    }

    public String generate(Student student, CertificateType type, boolean addOutcomes, boolean isHigherSchool, 
            boolean showUncompleted, Boolean estonian, boolean isOnlyHigherSchool) {
        CertificateReport report = CertificateReport.of(student);
        report.setIsHigherSchool(Boolean.valueOf(isHigherSchool));
        StudyYear studyYear = studyYearService.getCurrentStudyYear(EntityUtil.getId(student.getSchool()));
        if(studyYear == null) {
            throw new ValidationFailedException("studyYear.missingCurrent");
        }
        report.setStudyYear(studyYear.getYear().getNameEt());
        setFinished(report, student, type);
        setStudentResults(report, student, type, addOutcomes, showUncompleted);
        if (type.equals(CertificateType.TOEND_LIIK_SOOR) && report.getStudent().isGuestStudent()) {
            setAbroadProgramme(report, student);
            if (report.getStudent() != null && report.getStudent().getResults() != null) {
                boolean hasGrade = report.getStudent().getResults().stream().anyMatch(p -> p != null && p.getGradeName() != null);
                if (hasGrade) setGradingSystem(report, student, isOnlyHigherSchool, isHigherSchool, showUncompleted, estonian);
            }
        }
        setSessions(report, studyYear, type);
        setLastSession(report, studyYear, type);
        Map<String, Object> map = new HashMap<>();
        map.put("content", report);
        if (estonian != null) {
            if (estonian.booleanValue()) {
                map.put("lang", Language.ET);
            } else {
                map.put("lang", Language.EN);
            }
        }
        Boolean higher;
        if (report.getStudent().isGuestStudent()) {
            higher = directiveStudyLevel(student);
        } else {
            higher = Boolean.valueOf(StudentUtil.isHigher(student));
        }
        return templateService.evaluateTemplate(getTemplateName((higher != null && higher.booleanValue()), type, estonian), map);
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

    private void setGradingSystem(CertificateReport report, Student student, boolean isOnlyHigherSchool, boolean isHigherSchool, boolean showUncompleted, Boolean estonian) {
        Boolean directiveStudyLevel = directiveStudyLevel(student);
        if (directiveStudyLevel != null) report.getStudent().setHigher((directiveStudyLevel.booleanValue() || isOnlyHigherSchool));
        TypedQuery<Classifier> query = em.createQuery("select c from Classifier c where c.code in (?1) order by c.value desc", Classifier.class);
        if (isHigherSchool) {
            query.setParameter(1, showUncompleted ? HigherAssessment.GRADE_SYSTEM : HigherAssessment.GRADE_POSITIVE);
        } else {
            query.setParameter(1, showUncompleted ? OccupationalGrade.OCCUPATIONAL_GRADE_SYSTEM : OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        }
        List<Classifier> vocationalGradeSystem = query.getResultList();
        Boolean isLetter = student.getSchool().getIsLetterGrade();
        report.setGradeSystem(vocationalGradeSystem.stream().map(p -> {
            return ((isHigherSchool && isLetter != null && isLetter.booleanValue()) ? p.getValue2() : p.getValue()) 
                    + " - " + (estonian != null && estonian.booleanValue() ? p.getNameEt() : p.getNameEn());
        }).collect(Collectors.joining("; ")));
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
                    + " and dip.status_code = ?4 and dip_f.status_code = ?5")
                    .setParameter(1, EntityUtil.getId(student))
                    .setParameter(2, DirectiveType.KASKKIRI_LOPET.name())
                    .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                    .setParameter(4, DocumentStatus.LOPUDOK_STAATUS_T.name())
                    .setParameter(5, FormStatus.LOPUBLANKETT_STAATUS_T.name())
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

    private void setStudentResults(CertificateReport report, Student student, CertificateType type, boolean addOutcomes, boolean showUncompleted) {
        if(CertificateType.TOEND_LIIK_SOOR.equals(type)) {
            Boolean higher;
            if (report.getStudent().isGuestStudent()) {
                higher = directiveStudyLevel(student);
            } else {
                higher = Boolean.valueOf(StudentUtil.isHigher(student));
            }
            report.setAddOutcomes(Boolean.valueOf(addOutcomes));
            report.getStudent().setResults((higher != null && higher.booleanValue()) ? 
                    getHigherResults(student, addOutcomes, showUncompleted) : getVocationalResults(student, addOutcomes, showUncompleted));
        }
    }

    private List<CertificateStudentResult> getHigherResults(Student student, boolean addOutcomes, boolean showUncompleted) {
        List<StudentHigherSubjectResultDto> list = studentResultHigherService.positiveHigherResults(student, showUncompleted);
        List<CertificateStudentResult> results = StreamUtil.toMappedList(CertificateStudentResult::of, list);
        if (addOutcomes) {
            addHigherOutcomes(list, results);
        }
        Collections.sort(results, StreamUtil.comparingWithNullsLast(CertificateStudentResult::getIsActive)
                .thenComparing(StreamUtil.comparingWithNullsLast(r -> DateUtils.parseDate(r.getDate()))));
        return results;
    }

    private List<CertificateStudentResult> getVocationalResults(Student student, boolean addOutcomes, boolean showUncompleted) {
        List<StudentVocationalResultModuleThemeDto> data = studentService.studentVocationalResults(student, showUncompleted);
        if (!showUncompleted) {
            data = StreamUtil.toFilteredList(
                    r -> OccupationalGrade.isPositive(r.getGrade()), data);
        }
        
        Map<String, Classifier> grades = getVocationalGrades();
        List<CertificateStudentResult> results = StreamUtil.toMappedList(r -> CertificateStudentResult.of(r, grades), data);
        if (addOutcomes) {
            addVocationalOutcomes(data, results);
        }
        Collections.sort(results, StreamUtil.comparingWithNullsLast(r -> DateUtils.parseDate(r.getDate())));
        return results;
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
                    result.setOutcomesEn(outcome.get().getNameEn());
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
                result.setOutcomesEn(outcomes.stream().map(p -> p.getNameEn()).collect(Collectors.joining(", ")));
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
        List<?> rows = em.createNativeQuery("select cvom.id as cvom_id, cmo.id as cmo_id"
                + " from curriculum_version_omodule cvom"
                + " join curriculum_module_outcomes cmo on cmo.curriculum_module_id = cvom.curriculum_module_id"
                + " where cvom.id in ?1"
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
                + " where cmo.curriculum_module_id in (select curriculum_module_id from curriculum_version_omodule where id in ?1)";
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

    private static String getTemplateName(boolean isHigher, CertificateType type, Boolean estonian) {
        String fileName = isHigher ? type.getHigherCertificate(estonian) : type.getVocationalCertificate(estonian);
        if (fileName == null) {
            throw new ValidationFailedException("wrong.type");
        }
        return PATH + fileName;
    }
    
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
