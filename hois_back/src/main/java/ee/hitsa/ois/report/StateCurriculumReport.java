package ee.hitsa.ois.report;

import java.util.List;
import java.util.Objects;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.util.StreamUtil;

public class StateCurriculumReport {
    
    public static final String TEMPLATE_NAME = "statecurriculum.xhtml";

    private final String nameEt;
    private final String nameEn;
    private final String iscedClass;
    private final Long credits;
    private final String objectives;
    private final String outcomes;
    private final String admissionRequirements;
    private final String graduationRequirements;
    private final String description;
    private final List<StateCurriculumModuleReport> modules;
    private final List<StateCurriculumOccupationReport> occupations;

    public StateCurriculumReport(StateCurriculum stateCurriculum) {
        Objects.requireNonNull(stateCurriculum);

        nameEt = stateCurriculum.getNameEt();
        nameEn = stateCurriculum.getNameEn();
        iscedClass = stateCurriculum.getIscedClass().getNameEt();
        credits = stateCurriculum.getCredits();
        objectives = stateCurriculum.getObjectivesEt();
        outcomes = stateCurriculum.getOutcomesEt();
        admissionRequirements = stateCurriculum.getAdmissionRequirementsEt();
        graduationRequirements = stateCurriculum.getGraduationRequirementsEt();
        description = stateCurriculum.getDescription();
        modules = StreamUtil.toMappedList(m -> new StateCurriculumModuleReport(m), stateCurriculum.getModules());
        occupations = StreamUtil.toMappedList(m -> new StateCurriculumOccupationReport(m), stateCurriculum.getOccupations());
    }
    
    public List<StateCurriculumOccupationReport> getOccupations() {
        return occupations;
    }

    public List<StateCurriculumModuleReport> getModules() {
        return modules;
    }

    public String getIscedClass() {
        return iscedClass;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public static String getTemplateName() {
        return TEMPLATE_NAME;
    }

    public Long getCredits() {
        return credits;
    }

    public String getObjectives() {
        return objectives;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public String getAdmissionRequirements() {
        return admissionRequirements;
    }

    public String getGraduationRequirements() {
        return graduationRequirements;
    }

    public String getDescription() {
        return description;
    }
}
