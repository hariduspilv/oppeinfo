package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleOutcome;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionOccupationModuleOutcomeDto extends VersionedCommand {

    private Long id;
    private Long outcome;

    public static CurriculumVersionOccupationModuleOutcomeDto of(CurriculumVersionOccupationModuleOutcome outcome) {
        CurriculumVersionOccupationModuleOutcomeDto dto =
                EntityUtil.bindToDto(outcome, new CurriculumVersionOccupationModuleOutcomeDto());
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOutcome() {
        return outcome;
    }
    public void setOutcome(Long outcome) {
        this.outcome = outcome;
    }

}
