package ee.hitsa.ois.web.dto.apelapplication;

import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

public class ApelApplicationInformalSubjectOrModuleDto extends VersionedCommand{

    private Long id;
    private SubjectDto subject;
    private CurriculumVersionHigherModuleDto curriculumVersionHmodule;
    private CurriculumVersionOccupationModuleDto curriculumVersionOmodule;
    private CurriculumVersionOccupationModuleThemeDto curriculumVersionOmoduleTheme;
    private Boolean isOptional;
    private String skills;
    private String grade;
    private Boolean transfer;
    private List<ApelApplicationInformalSubjectOrModuleOutcomesDto> outcomes;
    
    public static ApelApplicationInformalSubjectOrModuleDto of(
            ApelApplicationInformalSubjectOrModule subjectOrModule) {
        ApelApplicationInformalSubjectOrModuleDto dto = EntityUtil.bindToDto(subjectOrModule,
                new ApelApplicationInformalSubjectOrModuleDto(), "outcomes");
        dto.setSubject(subjectOrModule.getSubject() != null ? SubjectDto.of(subjectOrModule.getSubject(), null) : null);
        dto.setCurriculumVersionHmodule(subjectOrModule.getCurriculumVersionHmodule() != null
                ? CurriculumVersionHigherModuleDto.of(subjectOrModule.getCurriculumVersionHmodule()) : null);
        dto.setCurriculumVersionOmodule(subjectOrModule.getCurriculumVersionOmodule() != null 
                ? CurriculumVersionOccupationModuleDto.of(subjectOrModule.getCurriculumVersionOmodule()) : null);
        dto.setCurriculumVersionOmoduleTheme(subjectOrModule.getCurriculumVersionOmoduleTheme() != null 
                ? CurriculumVersionOccupationModuleThemeDto.of(subjectOrModule.getCurriculumVersionOmoduleTheme()) : null);
        dto.setOutcomes(StreamUtil.toMappedList(ApelApplicationInformalSubjectOrModuleOutcomesDto::of, subjectOrModule.getOutcomes()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public List<ApelApplicationInformalSubjectOrModuleOutcomesDto> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(
            List<ApelApplicationInformalSubjectOrModuleOutcomesDto> outcomes) {
        this.outcomes = outcomes;
    }
    
}
