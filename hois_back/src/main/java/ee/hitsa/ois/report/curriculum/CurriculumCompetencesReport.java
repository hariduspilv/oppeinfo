package ee.hitsa.ois.report.curriculum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleCompetence;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumCompetencesReport {
    
    public static final String TEMPLATE_NAME = "curriculum.competences.fo";
    
    private static final int TABLE_SIZE = 10;
    
    private final List<CurriculumCompetenceReportTable> tables;

    public CurriculumCompetencesReport(Curriculum curriculum) {
        this(curriculum, Language.ET);
    }
    
    public CurriculumCompetencesReport(Curriculum curriculum, Language lang) {
        List<CurriculumModule> curriculumModules = curriculum.getModules().stream().sorted(Comparator
                .comparing(CurriculumModule::getOrderNr, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Comparator.comparing(CurriculumModule::getNameEt, String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());
        Map<Classifier, Set<CurriculumModule>> competenceMap = new HashMap<>();
        for (CurriculumModule module : curriculumModules) {
            for (CurriculumModuleCompetence moduleCompetence : module.getCompetences()) {
                Classifier competence = moduleCompetence.getCompetence();
                Set<CurriculumModule> moduleSet = competenceMap.get(competence);
                if (moduleSet == null) {
                    moduleSet = new LinkedHashSet<>();
                    competenceMap.put(competence, moduleSet);
                }
                moduleSet.add(module);
            }
        }
        int modulesCount = curriculumModules.size();
        int tableCount = modulesCount / TABLE_SIZE;
        if (modulesCount % TABLE_SIZE != 0) {
            tableCount++;
        }
        tables = new ArrayList<>();
        for (int i = 0; i < tableCount; i++) {
            List<CurriculumModule> modules = curriculumModules.subList(i * TABLE_SIZE, Math.min((i + 1) * TABLE_SIZE, modulesCount));
            tables.add(new CurriculumCompetenceReportTable(
                    StreamUtil.toMappedList(m -> TranslateUtil.name(m, lang), modules), 
                    StreamUtil.toMappedList(competence -> new CurriculumCompetenceReport(
                            competence, modules, competenceMap.get(competence), lang), 
                            competenceMap.keySet())));
        }
    }

    public List<CurriculumCompetenceReportTable> getTables() {
        return tables;
    }

}
