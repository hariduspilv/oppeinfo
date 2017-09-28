package ee.hitsa.ois.report;

import java.math.BigDecimal;
import java.util.List;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.util.StreamUtil;

public class StateCurriculumModuleReport {
    private final String name;
    private final BigDecimal credits;
    private final List<String> occupations;
    private final String objectives; 
    private final String outcomes;
    private final String assessments;
    private final String module;

    StateCurriculumModuleReport(StateCurriculumModule stateCurriculumModule) {
        name = stateCurriculumModule.getNameEt();
        module = stateCurriculumModule.getModule().getNameEt();
        credits = stateCurriculumModule.getCredits();
        occupations = StreamUtil.toMappedList(o -> o.getOccupation().getNameEt(), 
                stateCurriculumModule.getModuleOccupations());
        objectives = stateCurriculumModule.getObjectivesEt();
        outcomes = stateCurriculumModule.getOutcome().getOutcomesEt();
        assessments = stateCurriculumModule.getAssessmentsEt();
    }
    
    public String getModule() {
        return module;
    }
    public String getObjectives() {
        return objectives;
    }
    public String getOutcomes() {
        return outcomes;
    }
    public String getAssessments() {
        return assessments;
    }
    public String getName() {
        return name;
    }
    public BigDecimal getCredits() {
        return credits;
    }
    public List<String> getOccupations() {
        return occupations;
    }
}
