package ee.hitsa.ois.web.dto.poll;

public class QuestionAnswerDto implements Comparable<QuestionAnswerDto>{
    
    private Short answerNr;
    private String nameEt;
    private Short orderNr;
    private Long id;
    private Boolean chosen;
    
    public Short getAnswerNr() {
        return answerNr;
    }
    public void setAnswerNr(Short answerNr) {
        this.answerNr = answerNr;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public int compareTo(QuestionAnswerDto other) {
        if(this.getOrderNr().intValue() < other.getOrderNr().intValue()){
            return -1;
        } else if(this.getOrderNr().intValue() > other.getOrderNr().intValue()){
            return 1;
        } else {
            return 0;
        }
    }
    public Short getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }
    public Boolean getChosen() {
        return chosen;
    }
    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }

}
