package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.time.LocalDate;
import java.util.Objects;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.enums.Language;

public class ApelApplicationReport {
    
    public static final String TEMPLATE_NAME = "apel.application.xhtml";

    private final String name;
    private final String curriculumVersion;
    private final LocalDate inserted;
    private final LocalDate confirmed;
    private final String status;
    
    public ApelApplicationReport(ApelApplication application) {
        this(application, Language.ET);
    }
    
    public ApelApplicationReport(ApelApplication application , Language lang) {
        Objects.requireNonNull(application);
        this.name = application.getStudent().getPerson().getFullname();
        this.curriculumVersion = application.getStudent().getCurriculumVersion().getCode();
        this.inserted = application.getInserted() != null ? application.getInserted().toLocalDate() : null;
        this.confirmed = application.getConfirmed() != null ? application.getConfirmed().toLocalDate() : null;
        this.status = name(application.getStatus(), lang);
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
    
}
