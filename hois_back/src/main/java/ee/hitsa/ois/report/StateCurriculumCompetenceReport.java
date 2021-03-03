package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleCompetence;
import ee.hitsa.ois.util.EntityUtil;

public class StateCurriculumCompetenceReport {
    
    private String competence;
    
    private String description;

    public StateCurriculumCompetenceReport(StateCurriculumModuleCompetence competence) {
        this.competence = EntityUtil.getCode(competence.getCompetence());
        this.description = competence.getDescription();
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
