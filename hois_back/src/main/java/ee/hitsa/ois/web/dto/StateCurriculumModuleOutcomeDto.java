package ee.hitsa.ois.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOutcome;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StateCurriculumModuleOutcomeDto extends VersionedCommand {

    private Long id;
    @NotNull
    @Size(max=4000)
    private String outcomesEt;
    @Size(max=4000)
    private String outcomesEn;
    
    public static StateCurriculumModuleOutcomeDto of(StateCurriculumModuleOutcome outcome) {
        return EntityUtil.bindToDto(outcome, new StateCurriculumModuleOutcomeDto(), "module");
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOutcomesEt() {
        return outcomesEt;
    }
    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }
    public String getOutcomesEn() {
        return outcomesEn;
    }
    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }
    
    
    
}
