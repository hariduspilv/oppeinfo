package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ThemeDto implements Comparable<ThemeDto> {
    
    private Long id;
    private String nameEt;
    private Short orderNr;
    private Boolean isRepetitive;
    private AutocompleteResult journal;
    private AutocompleteResult subject;
    private List<QuestionDto> questions = new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public Short getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }
    
    @Override
    public int compareTo(ThemeDto other) {
        
        if(this.getOrderNr().intValue() < other.getOrderNr().intValue()){
            return -1;
        } else if(this.getOrderNr().intValue() > other.getOrderNr().intValue()){
            return 1;
        } else {
            return 0;
        }
        
    }
    public List<QuestionDto> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
    public Boolean getIsRepetitive() {
        return isRepetitive;
    }
    public void setIsRepetitive(Boolean isRepetitive) {
        this.isRepetitive = isRepetitive;
    }
    public AutocompleteResult getJournal() {
        return journal;
    }
    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }
    public AutocompleteResult getSubject() {
        return subject;
    }
    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

}
