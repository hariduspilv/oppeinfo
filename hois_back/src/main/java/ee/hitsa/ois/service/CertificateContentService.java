package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.report.certificate.CertificateReport;
import ee.hitsa.ois.report.certificate.CertificateReportSession;
import ee.hitsa.ois.report.certificate.CertificateStudentResult;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.student.StudentHigherSubjectResultDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;

@Service
public class CertificateContentService {

    private static final String PATH = "certificates/";

    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    @Autowired 
    private ProtocolService protocolService;
    @Autowired 
    private ClassifierService classifierService;

    public String generate(Student student, CertificateType type) {
        CertificateReport report = CertificateReport.of(student);
        StudyYear studyYear = studyYearService.getCurrentStudyYear(EntityUtil.getId(student.getSchool()));
        report.setStudyYear(studyYear.getYear().getNameEt());

        setStudentResults(report, student, type);
        setSessions(report, studyYear, type);
        setLastSession(report, studyYear, type);

        return templateService.evaluateTemplate(getTemplateName(StudentUtil.isHigher(student), type), 
                Collections.singletonMap("content", report));
    }

    private void setStudentResults(CertificateReport report, Student student, CertificateType type) {
        if(CertificateType.TOEND_LIIK_SOOR.equals(type)) {
            report.getStudent().setResults(StudentUtil.isHigher(student) ? getHigherResults(student) : getVocationalResults(student));
        }
    }

    private List<CertificateStudentResult> getHigherResults(Student student) {
        List<StudentHigherSubjectResultDto> list = studentResultHigherService.positiveHigherResults(student);
        List<CertificateStudentResult> results = StreamUtil.toMappedList(CertificateStudentResult::of, list);
        Collections.sort(results, Comparator.comparing(CertificateStudentResult::getDate));
        return results;
    }

    private List<CertificateStudentResult> getVocationalResults(Student student) {
        Stream<StudentVocationalResultModuleThemeDto> data = protocolService.vocationalResults(student)
                .stream().filter(r -> OccupationalGrade.isPositive(r.getGrade()));
        Map<String, Classifier> grades = getVocationalGrades();
        List<CertificateStudentResult> results = StreamUtil.toMappedList(r -> CertificateStudentResult.of(r, grades), data);
        Collections.sort(results, Comparator.comparing(CertificateStudentResult::getDate));
        return results;
    }

    private static void setSessions(CertificateReport report, StudyYear studyYear, CertificateType type) {
        if(CertificateType.TOEND_LIIK_SESS.equals(type)) {
            report.setSessions(StreamUtil.toMappedList(CertificateReportSession::of, 
                    currentStudyYearsSessions(studyYear)));
        }
    }

    private static void setLastSession(CertificateReport report, StudyYear studyYear, CertificateType type) {
        if(CertificateType.TOEND_LIIK_KONTAKT.equals(type)) {
            List<StudyPeriodEvent> finishedSessions = 
                    currentStudyYearsSessions(studyYear).stream()
                    .filter(e -> hasFinished(e)).collect(Collectors.toList());
            
            if(!finishedSessions.isEmpty()) {
                StudyPeriodEvent lastSession = finishedSessions.get(finishedSessions.size() - 1);
                report.setLastSession(CertificateReportSession.of(lastSession));
            }
        }
    }
    
    private static List<StudyPeriodEvent> currentStudyYearsSessions(StudyYear studyYear) {
        List<StudyPeriodEvent> sessions = studyYear.getStudyPeriodEvents()
                .stream()
                .filter(e -> isSession(e))
                .collect(Collectors.toList());
        Collections.sort(sessions, Comparator.comparing(StudyPeriodEvent::getEnd));
        return sessions;
    }

    private static boolean isSession(StudyPeriodEvent event) {
        return ClassifierUtil.equals(StudyPeriodEventType.SYNDMUS_SESS, event.getEventType());
    }

    private static boolean hasFinished(StudyPeriodEvent event) {
        return event.getEnd().toLocalDate().isBefore(LocalDate.now());
    }

    private Map<String, Classifier> getVocationalGrades() {
        List<Classifier> grades = classifierService.findAllByMainClassCode(MainClassCode.KUTSEHINDAMINE);
        return StreamUtil.toMap(Classifier::getCode, grades);
    }

    private static String getTemplateName(boolean isHigher, CertificateType type) {
        String fileName = isHigher ? type.getHigherCertificate() : type.getVocationalCertificate();
        if(fileName == null) {
            throw new ValidationFailedException("wrong.type");
        }
        return PATH + fileName;
    }
}
