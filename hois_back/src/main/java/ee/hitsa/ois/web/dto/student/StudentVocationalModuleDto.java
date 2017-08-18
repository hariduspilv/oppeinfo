package ee.hitsa.ois.web.dto.student;

import java.util.List;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

public class StudentVocationalModuleDto {

    private Long id;
    private CurriculumModuleDto curriculumModule;
    private List<CurriculumVersionOccupationModuleThemeDto> themes;

    public static StudentVocationalModuleDto of(CurriculumVersionOccupationModule module) {
        StudentVocationalModuleDto dto = EntityUtil.bindToDto(module, new StudentVocationalModuleDto(), "curriculumModule", "themes");

        dto.setCurriculumModule(CurriculumModuleDto.of(module.getCurriculumModule()));
        dto.setThemes(StreamUtil.toMappedList(CurriculumVersionOccupationModuleThemeDto::of, module.getThemes()));
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

}
