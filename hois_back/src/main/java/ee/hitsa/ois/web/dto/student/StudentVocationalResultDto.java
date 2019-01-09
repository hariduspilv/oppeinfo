package ee.hitsa.ois.web.dto.student;

import java.util.ArrayList;
import java.util.List;

public class StudentVocationalResultDto {

    private List<StudentVocationalResultModuleThemeDto> results = new ArrayList<>();
    private List<StudentVocationalModuleDto> curriculumModules = new ArrayList<>();
    private List<StudentVocationalModuleDto> extraCurriculaModules = new ArrayList<>();

    public List<StudentVocationalResultModuleThemeDto> getResults() {
        return results;
    }

    public void setResults(List<StudentVocationalResultModuleThemeDto> results) {
        this.results = results;
    }

    public List<StudentVocationalModuleDto> getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(List<StudentVocationalModuleDto> curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public List<StudentVocationalModuleDto> getExtraCurriculaModules() {
        return extraCurriculaModules;
    }

    public void setExtraCurriculaModules(List<StudentVocationalModuleDto> extraCurriculaModules) {
        this.extraCurriculaModules = extraCurriculaModules;
    }
}
