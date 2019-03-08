package ee.hitsa.ois.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluationCriteria;

@Entity
public class PracticeJournalEvaluation extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private PracticeJournal practiceJournal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private PracticeEvaluation practiceEvaluation;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private PracticeEvaluationCriteria practiceEvaluationCriteria;
    private String valueTxt;
    private Long valueNr;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Classifier valueClf;
    
    public PracticeJournal getPracticeJournal() {
        return practiceJournal;
    }
    public void setPracticeJournal(PracticeJournal practiceJournal) {
        this.practiceJournal = practiceJournal;
    }
    public PracticeEvaluation getPracticeEvaluation() {
        return practiceEvaluation;
    }
    public void setPracticeEvaluation(PracticeEvaluation practiceEvaluation) {
        this.practiceEvaluation = practiceEvaluation;
    }
    public PracticeEvaluationCriteria getPracticeEvaluationCriteria() {
        return practiceEvaluationCriteria;
    }
    public void setPracticeEvaluationCriteria(PracticeEvaluationCriteria practiceEvaluationCriteria) {
        this.practiceEvaluationCriteria = practiceEvaluationCriteria;
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
    public Classifier getValueClf() {
        return valueClf;
    }
    public void setValueClf(Classifier valueClf) {
        this.valueClf = valueClf;
    }
}
