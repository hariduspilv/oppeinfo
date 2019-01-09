package ee.hitsa.ois.web.dto.student;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

public class StudentVocationalModuleDto {

    private Long id;
    private CurriculumModuleDto curriculumModule;
    private List<CurriculumVersionOccupationModuleThemeDto> themes = new ArrayList<>();
    // themes that belong to module that has the same curriculum module but not the same curriculum version module
    private List<CurriculumVersionOccupationModuleThemeDto> otherCurriculumVersionModuleThemes = new ArrayList<>();

    public static StudentVocationalModuleDto of(CurriculumVersionOccupationModule module) {
        StudentVocationalModuleDto dto = new StudentVocationalModuleDto();
        dto.setId(EntityUtil.getId(module));
        dto.setCurriculumModule(CurriculumModuleDto.forCurriculumFulfillment(module.getCurriculumModule()));
        dto.setThemes(StreamUtil.toMappedList(CurriculumVersionOccupationModuleThemeDto::forCurriculumFulfillment,
                module.getThemes()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CurriculumVersionOccupationModuleThemeDto> getThemes() {
        return themes;
    }

    public void setThemes(List<CurriculumVersionOccupationModuleThemeDto> themes) {
        this.themes = themes;
    }

    public CurriculumModuleDto getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(CurriculumModuleDto curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public List<CurriculumVersionOccupationModuleThemeDto> getOtherCurriculumVersionModuleThemes() {
        return otherCurriculumVersionModuleThemes;
    }

    public void setOtherCurriculumVersionModuleThemes(
            List<CurriculumVersionOccupationModuleThemeDto> otherCurriculumVersionModuleThemes) {
        this.otherCurriculumVersionModuleThemes = otherCurriculumVersionModuleThemes;
    }

}
