package ee.hitsa.ois.web.commandobject.curriculum;

import javax.validation.constraints.NotNull;

public class CurriculumModuleTypesCommand {
    
    @NotNull
    private Long curriculum;
    private Long module;

    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
    public Long getModule() {
        return module;
    }
    public void setModule(Long module) {
        this.module = module;
    }
}
