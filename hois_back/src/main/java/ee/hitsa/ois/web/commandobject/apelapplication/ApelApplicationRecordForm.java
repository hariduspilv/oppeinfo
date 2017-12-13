package ee.hitsa.ois.web.commandobject.apelapplication;

import java.util.List;

import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationRecordForm extends InsertedChangedVersionDto {

    private Long id;
    private Boolean isFormalLearning;
    private List<ApelApplicationInformalExperienceForm> informalExperiences;
    private List<ApelApplicationInformalSubjectOrModuleForm> informalSubjectsOrModules;
    private List<ApelApplicationFormalSubjectOrModuleForm> formalSubjectsOrModules;
    private List<ApelApplicationFormalReplacedSubjectOrModuleForm> formalReplacedSubjectsOrModules;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

    public List<ApelApplicationInformalExperienceForm> getInformalExperiences() {
        return informalExperiences;
    }
    
    public void setInformalExperiences(List<ApelApplicationInformalExperienceForm> informalExperiences) {
        this.informalExperiences = informalExperiences;
    }
    
    public List<ApelApplicationInformalSubjectOrModuleForm> getInformalSubjectsOrModules() {
        return informalSubjectsOrModules;
    }
    
    public void setInformalSubjectsOrModules(List<ApelApplicationInformalSubjectOrModuleForm> informalSubjectsOrModules) {
        this.informalSubjectsOrModules = informalSubjectsOrModules;
    }

    public List<ApelApplicationFormalSubjectOrModuleForm> getFormalSubjectsOrModules() {
        return formalSubjectsOrModules;
    }

    public void setFormalSubjectsOrModules(List<ApelApplicationFormalSubjectOrModuleForm> formalSubjectsOrModules) {
        this.formalSubjectsOrModules = formalSubjectsOrModules;
    }

    public List<ApelApplicationFormalReplacedSubjectOrModuleForm> getFormalReplacedSubjectsOrModules() {
        return formalReplacedSubjectsOrModules;
    }

    public void setFormalReplacedSubjectsOrModules(
            List<ApelApplicationFormalReplacedSubjectOrModuleForm> formalReplacedSubjectsOrModules) {
        this.formalReplacedSubjectsOrModules = formalReplacedSubjectsOrModules;
    }
    
}
