package ee.hitsa.ois.report.apelapplication;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class ApelApplicationReport {

    public static final String HIGHER_TEMPLATE_NAME = "apel.application.higher.xhtml";
    public static final String VOCATIONAL_TEMPLATE_NAME = "apel.application.vocational.xhtml";

    private final Boolean isHigherSchool;
    private final String name;
    private final String curriculumVersion;
    private final LocalDate inserted;
    private final LocalDate confirmed;
    private final String status;
    private final Boolean isNew;

    private final String nominalDurationDecision;
    private final LocalDate nominalStudyEnd;
    private BigDecimal abroadStudiesCredits = BigDecimal.ZERO;

    private final List<ApelApplicationRecordReport> records;
    private final List<ApelApplicationCommentReport> comments;
    private Long informalLearningRecords = Long.valueOf(0L);
    private Long formalLearningRecords = Long.valueOf(0L);

    public ApelApplicationReport(ApelApplication application, Boolean isHigherSchool, Boolean letterGrades, HoisUserDetails user) {
        this(application, isHigherSchool, letterGrades, Language.ET, user);
    }

    public ApelApplicationReport(ApelApplication application, Boolean isHigherSchool, Boolean letterGrades, Language lang, HoisUserDetails user) {
        Objects.requireNonNull(application);
        name = application.getStudent().getPerson().getFullname();
        curriculumVersion = application.getStudent().getCurriculumVersion().getCode();
        inserted = application.getInserted() != null ? application.getInserted().toLocalDate() : null;
        confirmed = application.getConfirmed() != null ? application.getConfirmed().toLocalDate() : null;
        status = name(application.getStatus(), lang);
        isNew = Boolean.TRUE.equals(application.getIsNew());

        nominalDurationDecision = TranslateUtil.name(application.getNominalType(), lang);
        nominalStudyEnd = application.getNewNominalStudyEnd();

        records = StreamUtil.toMappedList(r -> new ApelApplicationRecordReport(this, r, letterGrades, lang),
                application.getRecords());
        comments = StreamUtil.toMappedList(c -> new ApelApplicationCommentReport(c), 
        		user.isStudent() || user.isRepresentative() ? 
        				application.getComments().stream().filter(p -> Boolean.TRUE.equals(p.getIsStudent())).collect(Collectors.toList()) : 
        					application.getComments());
        comments.sort(Comparator.comparing(ApelApplicationCommentReport::getInserted));
        this.isHigherSchool = isHigherSchool;
    }

    public Boolean getIsHigherSchool() {
        return isHigherSchool;
    }

    public String getName() {
        return name;
    }

    public String getCurriculumVersion() {
        return curriculumVersion;
    }

    public LocalDate getInserted() {
        return inserted;
    }

    public LocalDate getConfirmed() {
        return confirmed;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public String getNominalDurationDecision() {
        return nominalDurationDecision;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public BigDecimal getAbroadStudiesCredits() {
        return abroadStudiesCredits;
    }

    public void setAbroadStudiesCredits(BigDecimal abroadStudiesCredits) {
        this.abroadStudiesCredits = abroadStudiesCredits;
    }

    public List<ApelApplicationRecordReport> getRecords() {
        return records;
    }

    public List<ApelApplicationCommentReport> getComments() {
        return comments;
    }

    public Long getInformalLearningRecords() {
        return informalLearningRecords;
    }

    public void setInformalLearningRecords(Long informalLearningRecords) {
        this.informalLearningRecords = informalLearningRecords;
    }

    public Long getFormalLearningRecords() {
        return formalLearningRecords;
    }

    public void setFormalLearningRecords(Long formalLearningRecords) {
        this.formalLearningRecords = formalLearningRecords;
    }

}
