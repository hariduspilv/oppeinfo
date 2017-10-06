package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.enums.Language;

public class ModuleProtocolReport {

    public static final String TEMPLATE_NAME = "module.protocol.xhtml";
    
    private final String protocolNr;
    private final LocalDate inserted;
    private final String status;
    private final String confirmer;
    private final LocalDate confirmDate;
    private final String teacher;
    private final String studyYear;
    private final String curriculumVersion;
    private final String curriculumModule;
    private final BigDecimal credits;
    private final String assessmentType;
    private final List<ProtocolStudentReport> protocolStudents;

    public ModuleProtocolReport(Protocol protocol) {
        this(protocol, Language.ET);
    }

    public ModuleProtocolReport(Protocol protocol, Language lang) {
        this.protocolNr = protocol.getProtocolNr();
        this.inserted = protocol.getInserted().toLocalDate();
        this.status = name(protocol.getStatus(), lang);
        this.confirmDate = protocol.getConfirmDate();
        this.confirmer = protocol.getConfirmer();
        
        ProtocolVdata vData = protocol.getProtocolVdata();
        this.teacher = vData.getTeacher().getPerson().getFullname();
        this.studyYear = name(vData.getStudyYear().getYear(), lang);
        this.curriculumVersion = vData.getCurriculumVersion().getCode(); // TODO: curriculum name?
        this.curriculumModule = name(vData.getCurriculumVersionOccupationModule().getCurriculumModule(), lang);
        this.credits = vData.getCurriculumVersionOccupationModule().getCurriculumModule().getCredits();
        this.assessmentType = name(vData.getCurriculumVersionOccupationModule().getAssessment(), lang);

        this.protocolStudents = protocol.getProtocolStudents().stream()
                .sorted(Comparator.comparing(ps -> ps.getStudent().getPerson().getFirstname(), 
                        String.CASE_INSENSITIVE_ORDER))
                .map(ps -> new ProtocolStudentReport(ps))
                .collect(Collectors.toList());
    }

    public static String getTemplateName() {
        return TEMPLATE_NAME;
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

    public String getConfirmer() {
        return confirmer;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public String getCurriculumVersion() {
        return curriculumVersion;
    }

    public String getCurriculumModule() {
        return curriculumModule;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public List<ProtocolStudentReport> getProtocolStudents() {
        return protocolStudents;
    }
}
