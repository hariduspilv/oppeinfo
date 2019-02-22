package ee.hitsa.ois.web.commandobject.practice;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationCriteriaDto;

public class PracticeEvaluationForm extends VersionedCommand {
    
    @Required
    private String nameEt;
    private String addInfo;
    @Required
    private Boolean isActive;
    @Required
    private String target;
    @NotEmpty
    private List<PracticeEvaluationCriteriaDto> criteria = new ArrayList<>();
    
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public List<PracticeEvaluationCriteriaDto> getCriteria() {
        return criteria;
    }
    public void setCriteria(List<PracticeEvaluationCriteriaDto> criteria) {
        this.criteria = criteria;
    }
}
