package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumModuleOutcomeDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    private String outcomeEt;
    private String outcomeEn;
    
    public static CurriculumModuleOutcomeDto of(CurriculumModuleOutcome outcome) {
        return EntityUtil.bindToDto(outcome, new CurriculumModuleOutcomeDto());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOutcomeEt() {
        return outcomeEt;
    }
    public void setOutcomeEt(String outcomeEt) {
        this.outcomeEt = outcomeEt;
    }
    public String getOutcomeEn() {
        return outcomeEn;
    }
    public void setOutcomeEn(String outcomeEn) {
        this.outcomeEn = outcomeEn;
    }
    
    
}
