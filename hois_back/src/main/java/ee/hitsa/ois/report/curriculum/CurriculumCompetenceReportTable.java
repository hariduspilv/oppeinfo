package ee.hitsa.ois.report.curriculum;

import java.util.List;

public class CurriculumCompetenceReportTable {

    private final List<String> modules;
    private final List<CurriculumCompetenceReport> competences;
    
    public CurriculumCompetenceReportTable(List<String> modules, List<CurriculumCompetenceReport> competences) {
        this.modules = modules;
        this.competences = competences;
    }

    public List<String> getModules() {
        return modules;
    }

    public List<CurriculumCompetenceReport> getCompetences() {
        return competences;
    }

}
