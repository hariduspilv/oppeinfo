package ee.hitsa.ois.web.commandobject.practice;

public class PracticeEvaluationSearchCommand {

    private String nameEt;
    private String target;
    private Boolean isActive;
    
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
}
