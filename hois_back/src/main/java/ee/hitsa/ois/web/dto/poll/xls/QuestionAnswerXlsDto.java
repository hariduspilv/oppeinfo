package ee.hitsa.ois.web.dto.poll.xls;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class QuestionAnswerXlsDto {
    
    private AutocompleteResult name;
    private Long orderNr;
    private Long weight;
    
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public Long getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }
    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }

}
