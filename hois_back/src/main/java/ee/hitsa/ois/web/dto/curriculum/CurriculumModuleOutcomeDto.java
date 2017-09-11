package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumModuleOutcomeDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    @Size(max=1000)
    private String outcomeEt;
    @Size(max=1000)
    private String outcomeEn;
    @NotNull
    @Min(0)
    @Max(1000)
    private Long orderNr;
    
    public static CurriculumModuleOutcomeDto of(CurriculumModuleOutcome outcome) {
        return EntityUtil.bindToDto(outcome, new CurriculumModuleOutcomeDto());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
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
