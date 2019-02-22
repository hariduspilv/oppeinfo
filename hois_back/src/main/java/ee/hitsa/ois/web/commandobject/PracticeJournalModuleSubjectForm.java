package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.PracticeJournalModuleSubject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.PracticeJournalValidation;

public class PracticeJournalModuleSubjectForm {
    
    private Long id;
    @NotNull(groups = PracticeJournalValidation.Vocational.class)
    private Long module;
    private Long theme;
    @NotNull(groups = PracticeJournalValidation.Higher.class)
    private Long subject;
    @NotNull
    @DecimalMin("0")
    @DecimalMax("999.9")
    private BigDecimal credits;
    @NotNull
    @Min(0)
    @Max(Short.MAX_VALUE)
    private Short hours;
    
    public static PracticeJournalModuleSubjectForm of(PracticeJournalModuleSubject moduleSubject) {
        if (moduleSubject == null) {
            return null;
        }
        return EntityUtil.bindToDto(moduleSubject, new PracticeJournalModuleSubjectForm());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public Long getTheme() {
        return theme;
    }

    public void setTheme(Long theme) {
        this.theme = theme;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }
    
}
