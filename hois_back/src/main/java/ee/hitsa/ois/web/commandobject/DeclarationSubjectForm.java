package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

public class DeclarationSubjectForm {
    @NotNull
    private Long subjectStudyPeriod;
    @NotNull
    private Long declaration;
    private EntityConnectionCommand subgroup;

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

    public EntityConnectionCommand getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(EntityConnectionCommand subgroup) {
        this.subgroup = subgroup;
    }
}
