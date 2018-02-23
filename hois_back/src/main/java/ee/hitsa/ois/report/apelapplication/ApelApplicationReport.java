package ee.hitsa.ois.report.apelapplication;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;

public class ApelApplicationReport {
    
    public static final String HIGHER_TEMPLATE_NAME = "apel.application.higher.xhtml";
    public static final String VOCATIONAL_TEMPLATE_NAME = "apel.application.vocational.xhtml";

    private final String name;
    private final String curriculumVersion;
    private final LocalDate inserted;
    private final LocalDate confirmed;
    private final String status;
    private final List<ApelApplicationRecordReport> records;
    private final List<ApelApplicationCommentReport> comments;
    private Long informalLearningRecords = Long.valueOf(0L);
    private Long formalLearningRecords = Long.valueOf(0L);
    
    public ApelApplicationReport(ApelApplication application) {
        this(application, Language.ET);
    }
    
    public ApelApplicationReport(ApelApplication application, Language lang) {
        Objects.requireNonNull(application);
        name = application.getStudent().getPerson().getFullname();
        curriculumVersion = application.getStudent().getCurriculumVersion().getCode();
        inserted = application.getInserted() != null ? application.getInserted().toLocalDate() : null;
        confirmed = application.getConfirmed() != null ? application.getConfirmed().toLocalDate() : null;
        status = name(application.getStatus(), lang);
        records = StreamUtil.toMappedList(r -> new ApelApplicationRecordReport(this, r, lang), application.getRecords());
        comments = StreamUtil.toMappedList(c -> new ApelApplicationCommentReport(c), application.getComments());
        comments.sort(Comparator.comparing(ApelApplicationCommentReport::getInserted));
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
