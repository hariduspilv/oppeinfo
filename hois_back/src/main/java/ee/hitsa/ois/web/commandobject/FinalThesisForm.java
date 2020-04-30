package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.finalthesis.FinalThesisCercsForm;

public class FinalThesisForm extends VersionedCommand {
    
    private Long id;
    @NotNull
    private AutocompleteResult student;
    @NotNull
    private String themeEt;
    private String themeEn;
    @NotNull
    private Boolean hasDraft;
    @Size(max=20000)
    private String draft;
    @Size(max=4000)
    private String addInfo;
    @ClassifierRestriction(MainClassCode.LOPUTOO_KEEL)
    private String language;
    private EntityConnectionCommand curriculumGrade;
    
    private List<FinalThesisSupervisorForm> supervisors;
    @Valid
    private List<FinalThesisCercsForm> cercses;
    
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

    public List<FinalThesisSupervisorForm> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<FinalThesisSupervisorForm> supervisors) {
        this.supervisors = supervisors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<FinalThesisCercsForm> getCercses() {
        return cercses;
    }

    public void setCercses(List<FinalThesisCercsForm> cercses) {
        this.cercses = cercses;
    }

    public EntityConnectionCommand getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(EntityConnectionCommand curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

}
