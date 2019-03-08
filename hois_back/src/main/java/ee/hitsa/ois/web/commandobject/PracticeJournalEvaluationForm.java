package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

public class PracticeJournalEvaluationForm {
    
    private Long id;
    @Size(max=4000)
    private String valueTxt;
    private Long valueNr;
    private String valueClf;
    private Long criteriaId;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValueTxt() {
        return valueTxt;
    }
    public void setValueTxt(String valueTxt) {
        this.valueTxt = valueTxt;
    }
    public Long getValueNr() {
        return valueNr;
    }
    public void setValueNr(Long valueNr) {
        this.valueNr = valueNr;
    }
    public String getValueClf() {
        return valueClf;
    }
    public void setValueClf(String valueClf) {
        this.valueClf = valueClf;
    }
    public Long getCriteriaId() {
        return criteriaId;
    }
    public void setCriteriaId(Long criteriaId) {
        this.criteriaId = criteriaId;
    }
    

}
