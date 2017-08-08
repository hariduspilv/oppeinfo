package ee.hitsa.ois.report;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;

public class HigherProtocolReport {
    
    public static final String TEMPLATE_NAME = "higher.protocol.xhtml";
    
    private final String protocolNr;
    private final LocalDate inserted;
    private final String status;
    private final String type;
    private final String subject;
    private final String studyPeriod;
    private final  String confirmer;
    private final LocalDate confirmDate;
    private final Set<String> teachers;
    private final Set<HigherProtocolStudentReport> protocolStudents;

    public HigherProtocolReport(Protocol protocol) {
        this(protocol, Language.ET);
    }

    public HigherProtocolReport(Protocol protocol, Language lang) {
        Objects.requireNonNull(protocol);
        protocolNr = protocol.getProtocolNr();
        inserted = protocol.getInserted().toLocalDate();
        status = protocol.getStatus().getNameEt();
        type = protocol.getProtocolHdata().getType().getNameEt();
        SubjectStudyPeriod ssp = protocol.getProtocolHdata().getSubjectStudyPeriod();
        Subject s = ssp.getSubject();
        subject = SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits());
        studyPeriod = ssp.getStudyPeriod().getNameEt();
        confirmer = protocol.getConfirmer();
        confirmDate = protocol.getConfirmDate();
        teachers = StreamUtil.toMappedSet(t -> PersonUtil.fullname(t.getTeacher().getPerson()), 
                ssp.getTeachers());
        protocolStudents = StreamUtil.toMappedSet(ps -> new HigherProtocolStudentReport(ps), 
                protocol.getProtocolStudents());
    }
    
    public Set<HigherProtocolStudentReport> getProtocolStudents() {
        return protocolStudents;
    }

    public Set<String> getTeachers() {
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
