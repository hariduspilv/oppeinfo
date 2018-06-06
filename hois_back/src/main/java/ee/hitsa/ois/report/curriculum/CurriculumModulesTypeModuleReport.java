package ee.hitsa.ois.report.curriculum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;

public class CurriculumModulesTypeModuleReport {

    private final String name;
    private final BigDecimal credits;
    private final List<String> outcomes;
    
    public CurriculumModulesTypeModuleReport(CurriculumModule module, Language lang) {
        name = TranslateUtil.name(module, lang);
        credits = module.getCredits();
        
        List<CurriculumModuleOutcome> moduleOutcomes = new ArrayList<>();
        moduleOutcomes.addAll(module.getOutcomes());
        //moduleOutcomes.sort(Comparator.comparing(CurriculumModuleOutcome::getOrderNr));
        Collections.sort(moduleOutcomes, StreamUtil.comparingWithNullsLast(CurriculumModuleOutcome::getOrderNr));
        outcomes = StreamUtil.toMappedList(o -> Language.EN.equals(lang) ? o.getOutcomeEn() : o.getOutcomeEt(), moduleOutcomes);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public List<String> getOutcomes() {
        return outcomes;
    }

}