package ee.hitsa.ois.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.report.certificate.CertificateReport;
import ee.hitsa.ois.report.certificate.CertificateReportSession;
import ee.hitsa.ois.report.certificate.CertificateStudentResult;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateContentCommand;
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

    public String generate(Long schoolId, CertificateContentCommand command) {
        CertificateType type = CertificateType.valueOf(command.getType());
        if(command.getStudent() != null) {
            Student student = em.getReference(Student.class, command.getStudent());
            if(!schoolId.equals(EntityUtil.getId(student.getSchool()))) {
                throw new ValidationFailedException("certificate.error.content");
            }
            return generate(student, type);
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
        return templateService.evaluateTemplate(getTemplateName(false, type),
                Collections.singletonMap("content", report));
    }

    public String generate(Student student, CertificateType type) {
        CertificateReport report = CertificateReport.of(student);
        StudyYear studyYear = studyYearService.getCurrentStudyYear(EntityUtil.getId(student.getSchool()));
        if(studyYear == null) {
            throw new ValidationFailedException("studyYear.missingCurrent");
        }
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
        Collections.sort(results, StreamUtil.comparingWithNullsLast(CertificateStudentResult::getDate));
        return results;
    }

    private List<CertificateStudentResult> getVocationalResults(Student student) {
        Stream<StudentVocationalResultModuleThemeDto> data = studentService.studentVocationalResults(student)
                .stream().filter(r -> OccupationalGrade.isPositive(r.getGrade()));
        Map<String, Classifier> grades = getVocationalGrades();
        List<CertificateStudentResult> results = StreamUtil.toMappedList(r -> CertificateStudentResult.of(r, grades), data);
        Collections.sort(results, StreamUtil.comparingWithNullsLast(CertificateStudentResult::getDate));
        return results;
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

    private static String getTemplateName(boolean isHigher, CertificateType type) {
        String fileName = isHigher ? type.getHigherCertificate() : type.getVocationalCertificate();
        if(fileName == null) {
            throw new ValidationFailedException("wrong.type");
        }
        return PATH + fileName;
    }
}
