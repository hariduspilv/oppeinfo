package ee.hitsa.ois.web.dto.basemodule;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.basemodule.BaseModuleOutcomes;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class BaseModuleOutcomesDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    @Size(max=1000)
    private String outcomeEt;
    @Size(max=1000)
    private String outcomeEn;
    @NotNull
    @Min(0)
    @Max(1000)
    private Integer orderNr;
    
    public static BaseModuleOutcomesDto of(BaseModuleOutcomes outcome) {
        return EntityUtil.bindToDto(outcome, new BaseModuleOutcomesDto());
    }
    
    public static BaseModuleOutcomesDto ofMin(BaseModuleOutcomes outcome) {
        BaseModuleOutcomesDto dto = new BaseModuleOutcomesDto();
        dto.setId(outcome.getId());
        dto.setOutcomeEt(outcome.getOutcomeEt());
        dto.setOutcomeEn(outcome.getOutcomeEn());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Integer orderNr) {
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
