package ee.hitsa.ois.report.curriculum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumCompetenceReport {
    
    private final String name;
    private final List<String> modules;
    
    public CurriculumCompetenceReport(Classifier competence, Collection<CurriculumModule> curriculumModules, 
            Set<CurriculumModule> moduleSet, Language lang) {
        name = TranslateUtil.name(competence, lang);
        modules = StreamUtil.toMappedList(m -> moduleSet.contains(m) ? "X" : "", curriculumModules);
    }

    public String getName() {
        return name;
    }

    public List<String> getModules() {
        return modules;
    }

}
