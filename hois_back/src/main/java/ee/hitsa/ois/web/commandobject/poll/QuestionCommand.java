package ee.hitsa.ois.web.commandobject.poll;

import java.util.ArrayList;
import java.util.List;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.dto.poll.QuestionAnswerDto;

public class QuestionCommand implements Comparable<QuestionCommand> {
    
    private Long theme;
    private Short orderNr;
    private String nameEt;
    private String addInfoEt;
    private Boolean isRequired;
    private String type;
    private Boolean isInRow;
    private Long question;
    private List<QuestionAnswerDto> answers = new ArrayList<>();
    private List<OisFileForm> files = new ArrayList<>();
    public Long getTheme() {
        return theme;
    }
    public void setTheme(Long theme) {
        this.theme = theme;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getAddInfoEt() {
        return addInfoEt;
    }
    public void setAddInfoEt(String addInfoEt) {
        this.addInfoEt = addInfoEt;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Boolean getIsInRow() {
        return isInRow;
    }
    public void setIsInRow(Boolean isInRow) {
        this.isInRow = isInRow;
    }
    public List<QuestionAnswerDto> getAnswers() {
        return answers;
    }
    public void setAnswers(List<QuestionAnswerDto> answers) {
        this.answers = answers;
    }
    public Short getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }
    public Boolean getIsRequired() {
        return isRequired;
    }
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
    public List<OisFileForm> getFiles() {
        return files;
    }
    public void setFiles(List<OisFileForm> files) {
        this.files = files;
    }
    @Override
    public int compareTo(QuestionCommand other) {
        if(this.getOrderNr().intValue() < other.getOrderNr().intValue()){
            return -1;
        } else if(this.getOrderNr().intValue() > other.getOrderNr().intValue()){
            return 1;
        } else {
            return 0;
        }
    }
    public Long getQuestion() {
        return question;
    }
    public void setQuestion(Long question) {
        this.question = question;
    }
}
