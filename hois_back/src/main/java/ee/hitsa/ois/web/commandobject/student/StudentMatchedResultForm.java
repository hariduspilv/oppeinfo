package ee.hitsa.ois.web.commandobject.student;

import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionModuleOrThemeResult;

public class StudentMatchedResultForm extends VersionedCommand {

    private Long id;
    private CurriculumVersionModuleOrThemeResult curriculumModuleOrTheme;
    private CurriculumVersionModuleOrThemeResult extraCurriculumResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurriculumVersionModuleOrThemeResult getCurriculumModuleOrTheme() {
        return curriculumModuleOrTheme;
    }

    public void setCurriculumModuleOrTheme(CurriculumVersionModuleOrThemeResult curriculumModuleOrTheme) {
        this.curriculumModuleOrTheme = curriculumModuleOrTheme;
    }

    public CurriculumVersionModuleOrThemeResult getExtraCurriculumResult() {
        return extraCurriculumResult;
    }

    public void setExtraCurriculumResult(CurriculumVersionModuleOrThemeResult extraCurriculumResult) {
        this.extraCurriculumResult = extraCurriculumResult;
    }
}
