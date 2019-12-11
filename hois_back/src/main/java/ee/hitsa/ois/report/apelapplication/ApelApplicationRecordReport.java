package ee.hitsa.ois.report.apelapplication;

import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;

public class ApelApplicationRecordReport {

    private final Long recordNr;
    private final Boolean isFormalLearning;
    private final List<ApelApplicationInformalSubjectOrModuleReport> informalSubjectsOrModules;
    private final List<ApelApplicationInformalExperienceReport> informalExperiences;
    private final List<ApelApplicationFormalSubjectOrModuleReport> formalSubjectsOrModules;
    private final List<ApelApplicationFormalReplacedSubjectOrModuleReport> formalReplacedSubjectsOrModules;
    
    public ApelApplicationRecordReport(ApelApplicationReport report, ApelApplicationRecord record,
            Boolean letterGrades, Language lang) {
        isFormalLearning = record.getIsFormalLearning();
        
        if (Boolean.TRUE.equals(isFormalLearning)) {
            int formalLearningRecords = report.getFormalLearningRecords().intValue();
            report.setFormalLearningRecords(Long.valueOf(++formalLearningRecords));
            recordNr = report.getFormalLearningRecords();
        } else {
            int informalLearningRecords = report.getInformalLearningRecords().intValue();
            report.setInformalLearningRecords(Long.valueOf(++informalLearningRecords));
            recordNr = report.getInformalLearningRecords();
        }
        
        informalSubjectsOrModules = StreamUtil.toMappedList(r -> new ApelApplicationInformalSubjectOrModuleReport(r, letterGrades, lang),
                record.getInformalSubjectsOrModules());
        informalSubjectsOrModules.sort(Comparator.comparing(ApelApplicationInformalSubjectOrModuleReport::getName));
        informalExperiences = StreamUtil.toMappedList(r -> new ApelApplicationInformalExperienceReport(r, lang),
                record.getInformalExperiences());
        informalExperiences.sort(Comparator.comparing(ApelApplicationInformalExperienceReport::getName));
        formalSubjectsOrModules = StreamUtil.toMappedList(r -> new ApelApplicationFormalSubjectOrModuleReport(r, letterGrades, lang),
                record.getFormalSubjectsOrModules());
        formalSubjectsOrModules.sort(Comparator.comparing(ApelApplicationFormalSubjectOrModuleReport::getName));
        formalReplacedSubjectsOrModules = StreamUtil.toMappedList(r -> new ApelApplicationFormalReplacedSubjectOrModuleReport(r, lang),
                record.getFormalReplacedSubjectsOrModules());
        formalReplacedSubjectsOrModules.sort(Comparator.comparing(ApelApplicationFormalReplacedSubjectOrModuleReport::getName));
    }
    
    public Long getRecordNr() {
        return recordNr;
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
