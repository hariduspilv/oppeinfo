package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.SubjectUtil;

public class HigherProtocolReport {

    public static final String TEMPLATE_NAME = "higher.protocol.xhtml";

    private final String school;
    private final String protocolNr;
    private final LocalDate protocolDate;
    private final String status;
    private final String type;
    private final String subject;
    private final String studyPeriod;
    private final String module;
    private final BigDecimal credits;
    private final String confirmer;
    private final LocalDate confirmDate;
    private final List<String> teachers;
    private final List<ProtocolStudentReport> protocolStudents;

    public HigherProtocolReport(Protocol protocol, Boolean letterGrades) {
        this(protocol, letterGrades, Language.ET);
    }

    public HigherProtocolReport(Protocol protocol,Boolean letterGrades, Language lang) {
        Objects.requireNonNull(protocol);
        school = name(protocol.getSchool(), lang);
        protocolNr = protocol.getProtocolNr();
        protocolDate = protocol.getFinalDate() != null ? protocol.getFinalDate() : protocol.getInserted().toLocalDate();
        status = name(protocol.getStatus(), lang);

        SubjectStudyPeriod ssp = protocol.getProtocolHdata().getSubjectStudyPeriod();
        if (ssp != null) {
            type = name(protocol.getProtocolHdata().getType(), lang);
            Subject s = ssp.getSubject();
            subject = SubjectUtil.subjectName(s.getCode(), name(s, lang), s.getCredits());
            studyPeriod = name(ssp.getStudyPeriod(), lang);
            module = null;
            credits = s.getCredits();
            teachers = PersonUtil.sorted(ssp.getTeachers().stream().map(t -> t.getTeacher().getPerson()));
        } else {
            type = null;
            subject = null;
            studyPeriod = null;
            CurriculumVersionHigherModule m = protocol.getProtocolHdata().getCurriculumVersionHmodule();
            module = name(m, lang);
            credits = m.getTotalCredits();
            teachers = Collections.singletonList(PersonUtil.fullname(protocol.getProtocolHdata().getTeacher().getPerson()));
        }

        confirmer = PersonUtil.stripIdcodeFromFullnameAndIdcode(protocol.getConfirmer());
        confirmDate = protocol.getConfirmDate();
        this.protocolStudents = protocol.getProtocolStudents().stream()
                .sorted((o1, o2) -> PersonUtil.SORT.compare(o1.getStudent().getPerson(), o2.getStudent().getPerson()))
                .map(ps -> new ProtocolStudentReport(ps, letterGrades, lang))
                .collect(Collectors.toList());
    }

    public String getSchool() {
        return school;
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

    public LocalDate getProtocolDate() {
        return protocolDate;
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

    public String getModule() {
        return module;
    }

    public BigDecimal getCredits() {
        return credits;
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
