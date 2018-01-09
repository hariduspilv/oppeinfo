package ee.hitsa.ois.web.commandobject.apelapplication;

import java.util.List;

import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

public class ApelApplicationInformalSubjectOrModuleForm extends InsertedChangedVersionDto {

    private Long id;
    private AutocompleteResult subject;
    private CurriculumVersionHigherModuleDto curriculumVersionHmodule;
    private CurriculumVersionOccupationModuleDto curriculumVersionOmodule;
    private CurriculumVersionOccupationModuleThemeDto curriculumVersionOmoduleTheme;
    private Boolean isOptional;
    private Boolean transfer;
    
    @Required
    @ClassifierRestriction({MainClassCode.KORGHINDAMINE, MainClassCode.KUTSEHINDAMINE})
    private String grade;
    
    private List<ApelApplicationInformalSubjectOrModuleOutcomesForm> outcomes;
    
    @Required
    @Size(max = 4000)
    private String skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }
    
    public CurriculumVersionHigherModuleDto getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModuleDto curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public CurriculumVersionOccupationModuleDto getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModuleDto curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public CurriculumVersionOccupationModuleThemeDto getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(CurriculumVersionOccupationModuleThemeDto curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<ApelApplicationInformalSubjectOrModuleOutcomesForm> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<ApelApplicationInformalSubjectOrModuleOutcomesForm> outcomes) {
        this.outcomes = outcomes;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
