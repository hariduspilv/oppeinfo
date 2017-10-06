package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.SubjectUtil;

public class HigherProtocolReport {

    public static final String TEMPLATE_NAME = "higher.protocol.xhtml";

    private final String protocolNr;
    private final LocalDate inserted;
    private final String status;
    private final String type;
    private final String subject;
    private final String studyPeriod;
    private final String confirmer;
    private final LocalDate confirmDate;
    private final List<String> teachers;
    private final List<ProtocolStudentReport> protocolStudents;

    public HigherProtocolReport(Protocol protocol) {
        this(protocol, Language.ET);
    }

    public HigherProtocolReport(Protocol protocol, Language lang) {
        Objects.requireNonNull(protocol);
        protocolNr = protocol.getProtocolNr();
        inserted = protocol.getInserted().toLocalDate();
        status = name(protocol.getStatus(), lang);
        type = name(protocol.getProtocolHdata().getType(), lang);
        SubjectStudyPeriod ssp = protocol.getProtocolHdata().getSubjectStudyPeriod();
        Subject s = ssp.getSubject();
        subject = SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits());
        studyPeriod = name(ssp.getStudyPeriod(), lang);
        confirmer = protocol.getConfirmer();
        confirmDate = protocol.getConfirmDate();
        teachers = PersonUtil.sorted(ssp.getTeachers().stream().map(t -> t.getTeacher().getPerson()));
        this.protocolStudents = protocol.getProtocolStudents().stream()
                .sorted(Comparator.comparing(ps -> ps.getStudent().getPerson().getFirstname(), 
                        String.CASE_INSENSITIVE_ORDER))
                .map(ps -> new ProtocolStudentReport(ps))
                .collect(Collectors.toList());
    }

    public List<ProtocolStudentReport> getProtocolStudents() {
        return protocolStudents;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public LocalDate getInserted() {
        return inserted;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public String getStudyPeriod() {
        return studyPeriod;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public String getProtocolnr() {
        return protocolNr;
    }
}
