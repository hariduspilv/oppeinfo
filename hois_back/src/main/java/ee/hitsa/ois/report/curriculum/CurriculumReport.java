package ee.hitsa.ois.report.curriculum;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.enums.CurriculumConsecution;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumReport {
    
    public static final String VOCATIONAL_TEMPLATE_NAME = "curriculum.vocational.xhtml";
    private static final Set<String> SECONDARY_LEVELS = new HashSet<>(Arrays.asList("411", "413", "441"));

    private final String school;
    private final LocalDate approval;
    private final String approvalDokNr;
    
    private final String iscedClass;
    private final String nameEt;
    private final String nameEn;
    private final String nameRu;
    private final String merCode;
    
    private final String ekr21;
    private final String ekr31;
    private final String ekr41k;
    private final String ekr41;
    private final String ekr51;
    private final String ekr42;
    private final String ekr52;
    
    private final BigDecimal credits;
    private final String draftText;
    private final String outcomes;
    
    private final String admissionRequirements;
    private final String graduationRequirements;
    private final List<String> qualifications;
    private final List<String> partoccupations;
    private final String structure;
    private final String specialization;
    private final String contactPerson;
    private final String description;

    public CurriculumReport(Curriculum curriculum) {
        this(curriculum, Language.ET);
    }
    
    public CurriculumReport(Curriculum curriculum, Language lang) {
        school = name(curriculum.getSchool(), lang);
        approval = curriculum.getApproval();
        approvalDokNr = curriculum.getApprovalDokNr();
        
        iscedClass = name(curriculum.getIscedClass(), lang);
        nameEt = curriculum.getNameEt();
        nameEn = curriculum.getNameEn();
        nameRu = curriculum.getNameRu();
        merCode = curriculum.getMerCode();
        
        String studyLevel = curriculum.getOrigStudyLevel().getValue();
        Classifier consecution = curriculum.getConsecution();
        boolean isPrimary = ClassifierUtil.equals(CurriculumConsecution.OPPEKAVA_TYPE_E, consecution);
        boolean isSecondary = ClassifierUtil.equals(CurriculumConsecution.OPPEKAVA_TYPE_J, consecution);
        boolean isVocationalSecondary = SECONDARY_LEVELS.contains(studyLevel);
        ekr21 = isPrimary && studyLevel.startsWith("2") ? "X" : "";
        ekr31 = isPrimary && studyLevel.startsWith("3") ? "X" : "";
        ekr41k = isPrimary && isVocationalSecondary ? "X" : "";
        ekr41 = isPrimary && !isVocationalSecondary && studyLevel.startsWith("4") ? "X" : "";
        ekr51 = isPrimary && studyLevel.startsWith("5") ? "X" : "";
        ekr42 = isSecondary && studyLevel.startsWith("4") ? "X" : "";
        ekr52 = isSecondary && studyLevel.startsWith("5") ? "X" : "";
        
        credits = curriculum.getCredits();
        draftText = curriculum.getDraftText();
        outcomes = curriculum.getOutcomesEt();
        admissionRequirements = curriculum.getAdmissionRequirementsEt();
        graduationRequirements = curriculum.getGraduationRequirementsEt();
        qualifications = new ArrayList<>();
        partoccupations = new ArrayList<>();
        curriculum.getOccupations().stream()
            .map(CurriculumOccupation::getOccupation)
            .forEach(c -> {
                if (ClassifierUtil.mainClassCodeEquals(MainClassCode.KUTSE, c)) {
                    qualifications.add(name(c, lang));
                } else if (ClassifierUtil.mainClassCodeEquals(MainClassCode.OSAKUTSE, c)) {
                    partoccupations.add(name(c, lang));
                }
            });
        if (partoccupations.isEmpty()) {
            partoccupations.add(TranslateUtil.translate(ReportUtil.KEY_MISSING, lang));
        }
        structure = curriculum.getStructure();
        specialization = ReportUtil.valueOrMissing(curriculum.getSpecialization(), lang);
        contactPerson = curriculum.getContactPerson();
        description = curriculum.getDescription();
    }

    public String getSchool() {
        return school;
    }

    public LocalDate getApproval() {
        return approval;
    }

    public String getApprovalDokNr() {
        return approvalDokNr;
    }

    public String getIscedClass() {
        return iscedClass;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getMerCode() {
        return merCode;
    }

    public String getEkr21() {
        return ekr21;
    }

    public String getEkr31() {
        return ekr31;
    }

    public String getEkr41k() {
        return ekr41k;
    }

    public String getEkr41() {
        return ekr41;
    }

    public String getEkr51() {
        return ekr51;
    }

    public String getEkr42() {
        return ekr42;
    }

    public String getEkr52() {
        return ekr52;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getDraftText() {
        return draftText;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public String getAdmissionRequirements() {
        return admissionRequirements;
    }

    public String getGraduationRequirements() {
        return graduationRequirements;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    public List<String> getPartoccupations() {
        return partoccupations;
    }

    public String getStructure() {
        return structure;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getDescription() {
        return description;
    }

}
