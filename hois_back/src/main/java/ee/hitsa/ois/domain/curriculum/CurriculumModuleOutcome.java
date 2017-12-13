package ee.hitsa.ois.domain.curriculum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModuleOutcomes;

@Entity
@Table(name = "curriculum_module_outcomes")
public class CurriculumModuleOutcome extends BaseEntityWithId {

    private String outcomeEt;
    private String outcomeEn;
    private Long orderNr;

    @OneToMany(mappedBy="curriculumModuleOutcomes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes = new ArrayList<>();

    public Long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }

    public String getOutcomeEt() {
        return outcomeEt;
    }

    public void setOutcomeEt(String outcomeEt) {
        this.outcomeEt = outcomeEt;
    }

    public String getOutcomeEn() {
        return outcomeEn;
    }

    public void setOutcomeEn(String outcomeEn) {
        this.outcomeEn = outcomeEn;
    }

    public List<ApelApplicationInformalSubjectOrModuleOutcomes> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes) {
        this.outcomes = outcomes;
    }
    
}
