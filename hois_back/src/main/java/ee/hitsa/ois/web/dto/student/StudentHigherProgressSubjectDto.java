package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;

public class StudentHigherProgressSubjectDto extends CurriculumVersionHigherModuleSubjectDto {

    private CurriculumVersionHigherModuleResult module;
    private CurriculumVersionHigherModuleResult replacedModule;
    private Boolean replacedModuleOptional;
    private Long resultId;
    private String grade;
    private Long protocolId;
    private Boolean isFinalProtocol;
    private Long apelApplicationId;
    private Boolean prerequisitesCompleted;
    private Boolean taughtThisSemester;
    private Boolean declared;
    private Long replacedApelApplicationId;

    public CurriculumVersionHigherModuleResult getModule() {
        return module;
    }

    public void setModule(CurriculumVersionHigherModuleResult module) {
        this.module = module;
    }

    public CurriculumVersionHigherModuleResult getReplacedModule() {
        return replacedModule;
    }

    public void setReplacedModule(CurriculumVersionHigherModuleResult replacedModule) {
        this.replacedModule = replacedModule;
    }

    public Boolean getReplacedModuleOptional() {
        return replacedModuleOptional;
    }

    public void setReplacedModuleOptional(Boolean replacedModuleOptional) {
        this.replacedModuleOptional = replacedModuleOptional;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getPrerequisitesCompleted() {
        return prerequisitesCompleted;
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    public Boolean getIsFinalProtocol() {
        return isFinalProtocol;
    }

    public void setIsFinalProtocol(Boolean isFinalProtocol) {
        this.isFinalProtocol = isFinalProtocol;
    }

    public Long getApelApplicationId() {
        return apelApplicationId;
    }

    public void setApelApplicationId(Long apelApplicationId) {
        this.apelApplicationId = apelApplicationId;
    }

    public void setPrerequisitesCompleted(Boolean prerequisitesCompleted) {
        this.prerequisitesCompleted = prerequisitesCompleted;
    }

    public Boolean getTaughtThisSemester() {
        return taughtThisSemester;
    }

    public void setTaughtThisSemester(Boolean taughtThisSemester) {
        this.taughtThisSemester = taughtThisSemester;
    }

    public Boolean getDeclared() {
        return declared;
    }

    public void setDeclared(Boolean declared) {
        this.declared = declared;
    }

    public Long getReplacedApelApplicationId() {
        return replacedApelApplicationId;
    }

    public void setReplacedApelApplicationId(Long replacedApelApplicationId) {
        this.replacedApelApplicationId = replacedApelApplicationId;
    }
}
