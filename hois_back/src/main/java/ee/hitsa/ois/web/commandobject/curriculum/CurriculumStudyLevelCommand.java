package ee.hitsa.ois.web.commandobject.curriculum;

import javax.validation.constraints.NotNull;

public class CurriculumStudyLevelCommand {
    
    private Long curriculum;
    @NotNull
    private Boolean isHigher;

    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
    public Boolean getIsHigher() {
        return isHigher;
    }
    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
}
