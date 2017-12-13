package ee.hitsa.ois.web.dto.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModuleOutcomes;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;

public class ApelApplicationInformalSubjectOrModuleOutcomesDto extends VersionedCommand{

    private Long id;
    private CurriculumModuleOutcomeDto curriculumModuleOutcomes;
    private Long apelApplicationInformalSubjectOrModuleId;
    
    public static ApelApplicationInformalSubjectOrModuleOutcomesDto of(
            ApelApplicationInformalSubjectOrModuleOutcomes informalSubjectOrModuleOutcomes) {
        ApelApplicationInformalSubjectOrModuleOutcomesDto dto = EntityUtil.bindToDto(informalSubjectOrModuleOutcomes,
                new ApelApplicationInformalSubjectOrModuleOutcomesDto());
        dto.setCurriculumModuleOutcomes(informalSubjectOrModuleOutcomes.getCurriculumModuleOutcomes() != null
                ? CurriculumModuleOutcomeDto.of(informalSubjectOrModuleOutcomes.getCurriculumModuleOutcomes())
                : null);
        return dto;
    }

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public CurriculumModuleOutcomeDto getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }
    public void setCurriculumModuleOutcomes(CurriculumModuleOutcomeDto curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }
    public Long getApelApplicationInformalSubjectOrModuleId() {
        return apelApplicationInformalSubjectOrModuleId;
    }
    public void setApelApplicationInformalSubjectOrModuleId(Long apelApplicationInformalSubjectOrModuleId) {
        this.apelApplicationInformalSubjectOrModuleId = apelApplicationInformalSubjectOrModuleId;
    }
    
    
}
