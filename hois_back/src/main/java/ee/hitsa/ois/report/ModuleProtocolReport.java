package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class ModuleProtocolReport {

    public static final String TEMPLATE_NAME = "module.protocol.xhtml";
    
    private final String school;
    private final Boolean isHigherSchool;
    private final String protocolNr;
    private final String confirmer;
    private final LocalDate confirmDate;
    private final String teacher;
    private final String studyYear;
    private final String curriculumVersion;
    private final String curriculumModule;
    private final BigDecimal credits;
    private final String assessmentType;
    private final List<ProtocolStudentReport> protocolStudents;

    public ModuleProtocolReport(Protocol protocol, Boolean higherSchool) {
        this(protocol, higherSchool, Language.ET);
    }

    public ModuleProtocolReport(Protocol protocol, Boolean higherSchool, Language lang) {
        Objects.requireNonNull(protocol);
        isHigherSchool = higherSchool;
        school = name(protocol.getSchool(), lang);
        protocolNr = protocol.getProtocolNr();
        confirmDate = protocol.getConfirmDate();
        confirmer = PersonUtil.stripIdcodeFromFullnameAndIdcode(protocol.getConfirmer());
        
        ProtocolVdata vData = protocol.getProtocolVdata();
        teacher = vData.getTeacher().getPerson().getFullname();
        studyYear = name(vData.getStudyYear().getYear(), lang);
        curriculumVersion = vData.getCurriculumVersion().getCode(); // TODO: curriculum name?
        curriculumModule = name(vData.getCurriculumVersionOccupationModule().getCurriculumModule(), lang);
        credits = vData.getCurriculumVersionOccupationModule().getCurriculumModule().getCredits();
        assessmentType = name(vData.getCurriculumVersionOccupationModule().getAssessment(), lang);

        protocolStudents = protocol.getProtocolStudents().stream()
                .sorted((o1, o2) -> PersonUtil.SORT.compare(o1.getStudent().getPerson(), o2.getStudent().getPerson()))
                .map(ps -> new ProtocolStudentReport(ps, higherSchool, Boolean.FALSE, lang))
                .collect(Collectors.toList());
    }

    public static String getTemplateName() {
        return TEMPLATE_NAME;
    }
    
    public String getSchool() {
        return school;
    }

    public Boolean getIsHigherSchool() {
        return isHigherSchool;
    }

    public String getProtocolNr() {
        return protocolNr;
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
