package ee.hitsa.ois.web.dto.application;

import ee.hitsa.ois.domain.application.ApplicationPlannedSubjectEquivalent;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ApplicationPlannedSubjectEquivalentDto extends VersionedCommand {

    private Long id;
    private String nameEt;
    private String nameEn;
    private Long subject;
    private Long moduleId;
    private Long themeId;

    public static ApplicationPlannedSubjectEquivalentDto of(ApplicationPlannedSubjectEquivalent equivalent) {
        ApplicationPlannedSubjectEquivalentDto dto = EntityUtil.bindToDto(equivalent, new ApplicationPlannedSubjectEquivalentDto(), "subject");
        Subject subject = equivalent.getSubject();
        if (subject != null) {
            dto.setSubject(EntityUtil.getId(subject));
            dto.setNameEt(subject.getNameEt());
            dto.setNameEn(subject.getNameEn());
        }
        dto.setModuleId(EntityUtil.getNullableId(equivalent.getCurriculumVersionOmodule()));
        dto.setThemeId(EntityUtil.getNullableId(equivalent.getCurriculumVersionOmoduleTheme()));
        if (equivalent.getCurriculumVersionOmodule() != null && equivalent.getCurriculumVersionOmodule().getCurriculumModule() != null) {
            CurriculumModule module = equivalent.getCurriculumVersionOmodule().getCurriculumModule();
            CurriculumVersionOccupationModuleTheme theme = equivalent.getCurriculumVersionOmoduleTheme();
            if (theme != null && theme.getNameEt() != null) {
                dto.setNameEt(module.getNameEt() + '/' + theme.getNameEt());
                dto.setNameEt(module.getNameEn() + '/' + theme.getNameEt());
            } else {
                dto.setNameEt(module.getNameEt());
                dto.setNameEt(module.getNameEn());
            }
        }
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

}
