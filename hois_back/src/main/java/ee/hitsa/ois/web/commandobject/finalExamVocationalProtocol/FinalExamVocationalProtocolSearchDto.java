package ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalExamVocationalProtocolSearchDto {

    private Long id;
    private String protocolNr;
    private String teacher;
    private List<AutocompleteResult> curriculumVersions = new ArrayList<>();
    private List<AutocompleteResult> curriculumVersionOccupationModules = new ArrayList<>();
    private String status;
    private LocalDate inserted;
    private LocalDate confirmed;
    private String confirmer;
    private Boolean canEdit;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProtocolNr() {
        return protocolNr;
    }
    
    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    
    public String getTeacher() {
        return teacher;
    }
    
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    
    public List<AutocompleteResult> getCurriculumVersions() {
        return curriculumVersions;
    }
    
    public void setCurriculumVersions(List<AutocompleteResult> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }
    
    public List<AutocompleteResult> getCurriculumVersionOccupationModules() {
        return curriculumVersionOccupationModules;
    }
    
    public void setCurriculumVersionOccupationModules(List<AutocompleteResult> curriculumVersionOccupationModules) {
        this.curriculumVersionOccupationModules = curriculumVersionOccupationModules;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDate getInserted() {
        return inserted;
    }
    
    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
    }
    
    public LocalDate getConfirmed() {
        return confirmed;
    }
    
    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }
    
    public String getConfirmer() {
        return confirmer;
    }
    
    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }
    
    public Boolean getCanEdit() {
        return canEdit;
    }
    
    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
    
}
