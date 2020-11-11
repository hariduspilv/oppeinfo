package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleResult;

import java.util.ArrayList;
import java.util.List;

public class JournalCurriculumVersionDto {

    private Long id;
    private Long curriculumId;
    private String nameEt;
    private String nameEn;
    private List<CurriculumVersionOccupationModuleResult> modules;
    private List<AutocompleteResult> themes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
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

    public List<CurriculumVersionOccupationModuleResult> getModules() {
        return modules != null ? modules : (modules = new ArrayList<>());
    }

    public void setModules(List<CurriculumVersionOccupationModuleResult> modules) {
        this.modules = modules;
    }

    public List<AutocompleteResult> getThemes() {
        return themes != null ? themes : (themes = new ArrayList<>());
    }

    public void setThemes(List<AutocompleteResult> themes) {
        this.themes = themes;
    }
}
