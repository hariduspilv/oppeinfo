package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionModuleOrThemeResult;

import java.util.List;

public class StudentMatchedResultsFormDto {

    private List<CurriculumVersionModuleOrThemeResult> modulesAndThemes;
    private List<CurriculumVersionModuleOrThemeResult> extraCurriculumResults;
    private List<StudentMatchedResultDto> rows;

    public List<CurriculumVersionModuleOrThemeResult> getModulesAndThemes() {
        return modulesAndThemes;
    }

    public void setModulesAndThemes(List<CurriculumVersionModuleOrThemeResult> modulesAndThemes) {
        this.modulesAndThemes = modulesAndThemes;
    }

    public List<CurriculumVersionModuleOrThemeResult> getExtraCurriculumResults() {
        return extraCurriculumResults;
    }

    public void setExtraCurriculumResults(List<CurriculumVersionModuleOrThemeResult> extraCurriculumResults) {
        this.extraCurriculumResults = extraCurriculumResults;
    }

    public List<StudentMatchedResultDto> getRows() {
        return rows;
    }

    public void setRows(List<StudentMatchedResultDto> rows) {
        this.rows = rows;
    }
}
