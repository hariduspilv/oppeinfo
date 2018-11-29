package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class FinalProtocolReport {
    
    public static final String VOCATIONAL_TEMPLATE_NAME = "final.vocational.protocol.xhtml";
    public static final String HIGHER_TEMPLATE_NAME = "final.higher.protocol.xhtml";

    private final Boolean isVocational;
    private final String school;
    private final String protocolNr;
    private final String curriculumModule;
    private final String subject;
    private final String subjectCode;
    private final BigDecimal credits;
    private final LocalDate finalDate;
    private final String committeeMembers;
    private final Boolean hasOccupations;
    private final List<FinalProtocolStudentReport> protocolStudents;
    private final String confirmedBy;
    private final LocalDate confirmDate;
    
    public FinalProtocolReport(Protocol protocol, Boolean letterGrades) {
        this(protocol, letterGrades, Language.ET);
    }
    
    public FinalProtocolReport(Protocol protocol, Boolean letterGrades, Language lang) {
        Objects.requireNonNull(protocol);
        isVocational = protocol.getIsVocational();
        school = name(protocol.getSchool(), lang);
        protocolNr = protocol.getProtocolNr();
        
        if (Boolean.TRUE.equals(isVocational)) {
            CurriculumModule module = protocol.getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule();
            curriculumModule = name(module, lang);
            credits = module.getCredits();
            hasOccupations = Boolean.valueOf(!module.getOccupations().isEmpty());
            subject = null;
            subjectCode = null;
        } else {
            ProtocolHdata hData = protocol.getProtocolHdata();
            Subject subj = Boolean.TRUE.equals(protocol.getIsFinalThesis()) ? hData.getFinalSubject() : hData.getSubjectStudyPeriod().getSubject();
            subject = name(subj, lang);
            subjectCode = subj.getCode();
            credits = subj.getCredits();
            hasOccupations = Boolean.valueOf(hData.getCurriculum().getSpecialities().stream().anyMatch(s -> s.getOccupation() != null));
            curriculumModule = null;
        }
        
        finalDate = protocol.getFinalDate();
        committeeMembers = protocol.getProtocolCommitteeMembers().stream()
                .sorted(Comparator.comparing(pcm -> pcm.getCommitteeMember().getMemberFullname(),
                        String.CASE_INSENSITIVE_ORDER))
                .map(pcm -> pcm.getCommitteeMember().getMemberFullname())
                .collect(Collectors.joining(", "));

        protocolStudents = protocol.getProtocolStudents().stream()
                .sorted((o1, o2) -> PersonUtil.SORT.compare(o1.getStudent().getPerson(), o2.getStudent().getPerson()))
                .map(ps -> new FinalProtocolStudentReport(ps, isVocational, letterGrades, lang))
                .collect(Collectors.toList());
        confirmedBy = PersonUtil.stripIdcodeFromFullnameAndIdcode(protocol.getConfirmer());
        confirmDate = protocol.getConfirmDate();
    }

    public Boolean getIsVocational() {
        return isVocational;
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
    
    public String getSubject() {
        return subject;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public String getCommitteeMembers() {
        return committeeMembers;
    }
    
    public Boolean getHasOccupations() {
        return hasOccupations;
    }

    public List<FinalProtocolStudentReport> getProtocolStudents() {
        return protocolStudents;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }
    
}
