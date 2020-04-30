package ee.hitsa.ois.web.dto.finalthesis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalThesisDto extends VersionedCommand {
    
    private Long id;
    private AutocompleteResult student;
    private String status;
    private String themeEt;
    private String themeEn;
    private String language;
    private AutocompleteResult curriculumGrade;
    private Boolean hasDraft;
    private String draft;
    private String addInfo;
    private List<FinalThesisSupervisorDto> supervisors = new ArrayList<>();
    private Set<FinalThesisCercsDto> cercses;
    private LocalDateTime confirmed;
    
    private Boolean canBeEdited;
    private Boolean canBeConfirmed;
    
    public static FinalThesisDto of(FinalThesis finalThesis) {
        if (finalThesis == null) {
            return null;
        }
        FinalThesisDto dto = EntityUtil.bindToDto(finalThesis, new FinalThesisDto(), "supervisorsForms", "cercs");
        dto.setSupervisors(StreamUtil.toMappedList(s -> FinalThesisSupervisorDto.of(s), finalThesis.getSupervisors()));
        Collections.sort(dto.getSupervisors(), StreamUtil.comparingWithNullsLast(FinalThesisSupervisorDto::getId));

        dto.setCercses(finalThesis.getCercses().stream().map(FinalThesisCercsDto::of).collect(Collectors.toCollection(LinkedHashSet::new)));
        return dto;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThemeEt() {
        return themeEt;
    }

    public void setThemeEt(String themeEt) {
        this.themeEt = themeEt;
    }

    public String getThemeEn() {
        return themeEn;
    }

    public void setThemeEn(String themeEn) {
        this.themeEn = themeEn;
    }

    public Boolean getHasDraft() {
        return hasDraft;
    }

    public void setHasDraft(Boolean hasDraft) {
        this.hasDraft = hasDraft;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public List<FinalThesisSupervisorDto> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<FinalThesisSupervisorDto> supervisors) {
        this.supervisors = supervisors;
    }

    public LocalDateTime getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public Boolean getCanBeConfirmed() {
        return canBeConfirmed;
    }

    public void setCanBeConfirmed(Boolean canBeConfirmed) {
        this.canBeConfirmed = canBeConfirmed;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public AutocompleteResult getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(AutocompleteResult curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

    public Set<FinalThesisCercsDto> getCercses() {
        return cercses;
    }

    public void setCercses(Set<FinalThesisCercsDto> cercses) {
        this.cercses = cercses;
    }
    
}
