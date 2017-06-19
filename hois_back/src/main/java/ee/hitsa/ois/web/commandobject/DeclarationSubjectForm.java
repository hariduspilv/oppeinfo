package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

public class DeclarationSubjectForm {
    @NotNull
    private Long subjectStudyPeriod;
    @NotNull
    private Long declaration;
    @NotNull
    private Long curriculumVersionHigherModule;
    @NotNull
    private Boolean isOptional;

    public Boolean getIsOptional() {
        return isOptional;
    }
    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }
    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    public Long getDeclaration() {
        return declaration;
    }
    public void setDeclaration(Long declaration) {
        this.declaration = declaration;
    }
    public Long getCurriculumVersionHigherModule() {
        return curriculumVersionHigherModule;
    }
    public void setCurriculumVersionHigherModule(Long curriculumVersionHigherModule) {
        this.curriculumVersionHigherModule = curriculumVersionHigherModule;
    }
}
