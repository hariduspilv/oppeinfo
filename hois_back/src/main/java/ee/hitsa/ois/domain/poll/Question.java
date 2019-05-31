package ee.hitsa.ois.domain.poll;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Question extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    private String nameEt;
    private String nameEn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    private String addInfoEt;
    private String addInfoEn;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id", nullable = false, updatable = false, insertable = false)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id", nullable = false, updatable = false, insertable = false)
    private List<PollThemeQuestion> pollThemeQuestions = new ArrayList<>();
    
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public Classifier getType() {
        return type;
    }
    public void setType(Classifier type) {
        this.type = type;
    }
    public String getAddInfoEt() {
        return addInfoEt;
    }
    public void setAddInfoEt(String addInfoEt) {
        this.addInfoEt = addInfoEt;
    }
    public String getAddInfoEn() {
        return addInfoEn;
    }
    public void setAddInfoEn(String addInfoEn) {
        this.addInfoEn = addInfoEn;
    }
    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }
    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
    public List<PollThemeQuestion> getPollThemeQuestions() {
        return pollThemeQuestions;
    }
    public void setPollThemeQuestions(List<PollThemeQuestion> pollThemeQuestions) {
        this.pollThemeQuestions = pollThemeQuestions;
    }
    
    
}
