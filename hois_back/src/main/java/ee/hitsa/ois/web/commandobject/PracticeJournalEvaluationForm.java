package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.web.dto.GradeDto;

import javax.validation.constraints.Size;

public class PracticeJournalEvaluationForm {

    private Long id;
    @Size(max=4000)
    private String valueTxt;
    private Long valueNr;
    private GradeDto grade;
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

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public Long getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Long criteriaId) {
        this.criteriaId = criteriaId;
    }

}
