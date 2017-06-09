package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModuleProtocolSearchDto {

    private Long id;
    private String protocolNr;
    private List<String> studentGroups = new ArrayList<>();
    private List<AutocompleteResult> curriculumVersions = new ArrayList<>();
    private List<AutocompleteResult> curriculumVersionOccupationModules = new ArrayList<>();
    private String status;
    private LocalDate inserted;
    private LocalDate confirmDate;
    private String confirmer;

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

    public List<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<String> studentGroups) {
        this.studentGroups = studentGroups;
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

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

}
