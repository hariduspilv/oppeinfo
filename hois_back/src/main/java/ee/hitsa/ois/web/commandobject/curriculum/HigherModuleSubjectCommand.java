package ee.hitsa.ois.web.commandobject.curriculum;

import java.util.Set;

import javax.validation.constraints.NotNull;

public class HigherModuleSubjectCommand {
    
    @NotNull
    private Long curriculumVersion;
    private Long module;
    /**
     * IDs of curriculum version specialties
     */
    private Set<Long> specialities;

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    public Long getModule() {
        return module;
    }
    public void setModule(Long module) {
        this.module = module;
    }
    public Set<Long> getSpecialities() {
        return specialities;
    }
    public void setSpecialities(Set<Long> specialities) {
        this.specialities = specialities;
    }

}
