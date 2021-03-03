package ee.hitsa.ois.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleCompetence;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StateCurriculumModuleCompetenceDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    @ClassifierRestriction(MainClassCode.PADEVUS)
    private String competence;
    @Size(max=10000)
    private String description;
    
    public static StateCurriculumModuleCompetenceDto of(StateCurriculumModuleCompetence competence) {
        return EntityUtil.bindToDto(competence, new StateCurriculumModuleCompetenceDto(), "module");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
