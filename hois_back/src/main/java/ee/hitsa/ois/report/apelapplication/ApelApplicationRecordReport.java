package ee.hitsa.ois.report.apelapplication;

import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;

public class ApelApplicationRecordReport {

    private final Boolean isFormalLearning;
    private final List<ApelApplicationInformalSubjectOrModuleReport> informalSubjectsOrModules;
    private final List<ApelApplicationInformalExperienceReport> informalExperiences;
    private final List<ApelApplicationFormalSubjectOrModuleReport> formalSubjectsOrModules;
    private final List<ApelApplicationFormalReplacedSubjectOrModuleReport> formalReplacedSubjectsOrModules;
    
    public ApelApplicationRecordReport(ApelApplicationRecord record, Language lang) {
        isFormalLearning = record.getIsFormalLearning();
        
        informalSubjectsOrModules = StreamUtil.toMappedList(r -> new ApelApplicationInformalSubjectOrModuleReport(r, lang), record.getInformalSubjectsOrModules());
        informalExperiences = StreamUtil.toMappedList(r -> new ApelApplicationInformalExperienceReport(r, lang), record.getInformalExperiences());
        formalSubjectsOrModules = StreamUtil.toMappedList(r -> new ApelApplicationFormalSubjectOrModuleReport(r, lang), record.getFormalSubjectsOrModules());
        formalReplacedSubjectsOrModules = StreamUtil.toMappedList(r -> new ApelApplicationFormalReplacedSubjectOrModuleReport(r, lang), record.getFormalReplacedSubjectsOrModules());
    }
    
    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }
    
    public List<ApelApplicationInformalSubjectOrModuleReport> getInformalSubjectsOrModules() {
        return informalSubjectsOrModules;
    }
    
    public List<ApelApplicationInformalExperienceReport> getInformalExperiences() {
        return informalExperiences;
    }
    
    public List<ApelApplicationFormalSubjectOrModuleReport> getFormalSubjectsOrModules() {
        return formalSubjectsOrModules;
    }
    
    public List<ApelApplicationFormalReplacedSubjectOrModuleReport> getFormalReplacedSubjectsOrModules() {
        return formalReplacedSubjectsOrModules;
    }
}
