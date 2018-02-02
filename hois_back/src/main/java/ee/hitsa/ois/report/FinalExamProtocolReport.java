package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class FinalExamProtocolReport {
    
    public static final String TEMPLATE_NAME = "final.exam.protocol.xhtml";

    private final String school;
    private final String protocolNr;
    private final String curriculumModule;
    private final BigDecimal credits;
    private final LocalDate finalExamDate;
    private final String committeeMembers;
    private final List<FinalExamProtocolStudentReport> protocolStudents;
    private final String confirmedBy;
    private final LocalDate confirmDate;
    
    public FinalExamProtocolReport(Protocol protocol) {
        this(protocol, Language.ET);
    }
    
    public FinalExamProtocolReport(Protocol protocol, Language lang) {
        Objects.requireNonNull(protocol);
        school = name(protocol.getSchool(), lang);
        protocolNr = protocol.getProtocolNr();
        
        ProtocolVdata vData = protocol.getProtocolVdata();
        curriculumModule = name(vData.getCurriculumVersionOccupationModule().getCurriculumModule(), lang);
        credits = vData.getCurriculumVersionOccupationModule().getCurriculumModule().getCredits();
        
        //TODO: no final exam date field
        finalExamDate = null;
        committeeMembers = protocol.getProtocolCommitteeMembers().stream()
                .sorted(Comparator.comparing(pcm -> pcm.getCommitteeMember().getMemberFullname(),
                        String.CASE_INSENSITIVE_ORDER))
                .map(pcm -> pcm.getCommitteeMember().getMemberFullname())
                .collect(Collectors.joining(", "));
        protocolStudents = protocol.getProtocolStudents().stream()
                .sorted((o1, o2) -> PersonUtil.SORT.compare(o1.getStudent().getPerson(), o2.getStudent().getPerson()))
                .map(ps -> new FinalExamProtocolStudentReport(ps, lang))
                .collect(Collectors.toList());
        confirmedBy = PersonUtil.stripIdcodeFromFullnameAndIdcode(protocol.getConfirmer());
        confirmDate = protocol.getConfirmDate();
    }

    public String getSchool() {
        return school;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public String getCurriculumModule() {
        return curriculumModule;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public LocalDate getFinalExamDate() {
        return finalExamDate;
    }

    public String getCommitteeMembers() {
        return committeeMembers;
    }

    public List<FinalExamProtocolStudentReport> getProtocolStudents() {
        return protocolStudents;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }
    
}
