package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class ModuleProtocolJournalResultDto {

    private Long journalId;
    private String nameEt;
    private Integer capacity;
    private Boolean journalHasOutcomes;

    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;

    public ModuleProtocolJournalResultDto() {

    }

    public ModuleProtocolJournalResultDto(Long journalId, String nameEt, Integer capacity, String grade, Boolean journalHasOutcomes) {
        this.journalId = journalId;
        this.nameEt = nameEt;
        this.capacity = capacity;
        this.grade = grade;
        this.journalHasOutcomes = journalHasOutcomes;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getJournalHasOutcomes() {
        return journalHasOutcomes;
    }

    public void setJournalHasOutcomes(Boolean journalHasOutcomes) {
        this.journalHasOutcomes = journalHasOutcomes;
    }
    

}
