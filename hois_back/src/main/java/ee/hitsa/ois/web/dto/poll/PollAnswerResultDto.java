package ee.hitsa.ois.web.dto.poll;

public class PollAnswerResultDto extends AutocompleteWithOrder {
    
    public PollAnswerResultDto(Long id, String nameEt, String nameEn, Long order) {
        super(id, nameEt, nameEn, order);
    }
    
    public PollAnswerResultDto() {
       super(); 
    }
    
    private Double percentage;
    private Long answers = Long.valueOf(0L);
    private Long weight;
    // for textfield
    private String answerTxt;
    
    public Double getPercentage() {
        return percentage;
    }
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
    public Long getAnswers() {
        return answers;
    }
    public void setAnswers(Long answers) {
        this.answers = answers;
    }
    public String getAnswerTxt() {
        return answerTxt;
    }
    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

}
