package ee.hitsa.ois.web.dto.student;

public class StudentMatchedResultMinDto {

    private Long id;
    private Long moduleId;
    private Long oldModuleId;
    private Long themeId;
    private Long oldThemeId;

    public StudentMatchedResultMinDto(Long id, Long moduleId, Long oldModuleId, Long themeId, Long oldThemeId) {
        this.id = id;
        this.moduleId = moduleId;
        this.oldModuleId = oldModuleId;
        this.themeId = themeId;
        this.oldThemeId = oldThemeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getOldModuleId() {
        return oldModuleId;
    }

    public void setOldModuleId(Long oldModuleId) {
        this.oldModuleId = oldModuleId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getOldThemeId() {
        return oldThemeId;
    }

    public void setOldThemeId(Long oldThemeId) {
        this.oldThemeId = oldThemeId;
    }
}
