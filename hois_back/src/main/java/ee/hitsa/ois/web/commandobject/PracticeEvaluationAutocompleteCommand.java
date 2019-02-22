package ee.hitsa.ois.web.commandobject;

public class PracticeEvaluationAutocompleteCommand extends SearchCommand{
    
    private String targetCode;
    private Boolean active;

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
