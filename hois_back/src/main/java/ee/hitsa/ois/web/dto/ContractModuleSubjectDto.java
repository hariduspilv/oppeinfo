package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.ContractModuleSubject;
import ee.hitsa.ois.util.EntityUtil;

public class ContractModuleSubjectDto {
    
    private Long id;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private AutocompleteResult subject;
    private BigDecimal credits;
    private Short hours;
    
    public static ContractModuleSubjectDto of(ContractModuleSubject moduleSubject) {
        if (moduleSubject == null) {
            return null;
        }
        return EntityUtil.bindToDto(moduleSubject, new ContractModuleSubjectDto());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
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
