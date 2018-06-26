package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ModuleDto implements Translatable {

    private Long id;
    private String nameEt;
    private String nameEn;
    private List<AutocompleteResult> journals = new ArrayList<>();
    private Boolean isPracticeModule;
    private List<AutocompleteResult> practiceModuleThemes = new ArrayList<>();
    private Long colspan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public List<AutocompleteResult> getJournals() {
        return journals;
    }

    public void setJournals(List<AutocompleteResult> journals) {
        this.journals = journals;
    }

    public Boolean getIsPracticeModule() {
        return isPracticeModule;
    }

    public void setIsPracticeModule(Boolean isPracticeModule) {
        this.isPracticeModule = isPracticeModule;
    }

    public List<AutocompleteResult> getPracticeModuleThemes() {
        return practiceModuleThemes;
    }

    public void setPracticeModuleThemes(List<AutocompleteResult> practiceModuleThemes) {
        this.practiceModuleThemes = practiceModuleThemes;
    }

    public Long getColspan() {
        return colspan;
    }

    public void setColspan(Long colspan) {
        this.colspan = colspan;
    }
    
    public List<Long> getExcelCopsanColums() {
        List<Long> columns = new ArrayList<>();
        for (int i = 0; i < this.colspan.longValue() - 1; i++) {
            columns.add(Long.valueOf(i));
        }
        return columns;
    }

}
