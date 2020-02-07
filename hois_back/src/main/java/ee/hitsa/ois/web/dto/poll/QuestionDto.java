package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.commandobject.OisFileEditDto;

public class QuestionDto implements Comparable<QuestionDto> {
    
    private Long id;
    private Short orderNr;
    private String nameEt;
    private String nameEn;
    private String addInfoEt;
    private Boolean isRequired;
    private String type;
    private Boolean isInRow;
    private Long question;
    private Boolean disabled;
    private String answerTxt;
    private Long responseSubjectId;
    private Long teacherId;
    private List<QuestionAnswerDto> answers = new ArrayList<>();
    private List<OisFileEditDto> files = new ArrayList<>();
    
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
    public List<OisFileEditDto> getFiles() {
        return files;
    }
    public void setFiles(List<OisFileEditDto> files) {
        this.files = files;
    }
    @Override
    public int compareTo(QuestionDto other) {
        if(this.getOrderNr().intValue() < other.getOrderNr().intValue()){
            return -1;
        } else if(this.getOrderNr().intValue() > other.getOrderNr().intValue()){
            return 1;
        } else {
            return 0;
        }
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getQuestion() {
        return question;
    }
    public void setQuestion(Long question) {
        this.question = question;
    }
    public Boolean getDisabled() {
        return disabled;
    }
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
    public String getAnswerTxt() {
        return answerTxt;
    }
    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }
    public Long getResponseSubjectId() {
        return responseSubjectId;
    }
    public void setResponseSubjectId(Long responseSubjectId) {
        this.responseSubjectId = responseSubjectId;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public Long getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

}
