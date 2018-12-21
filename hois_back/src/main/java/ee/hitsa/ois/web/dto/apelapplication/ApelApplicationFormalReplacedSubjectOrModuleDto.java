package ee.hitsa.ois.web.dto.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalReplacedSubjectOrModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

public class ApelApplicationFormalReplacedSubjectOrModuleDto {

    private Long id;
    private SubjectDto subject;
    private CurriculumVersionOccupationModuleDto curriculumVersionOmodule;
    private CurriculumVersionOccupationModuleThemeDto curriculumVersionOmoduleTheme;
    
    public static ApelApplicationFormalReplacedSubjectOrModuleDto of(
            ApelApplicationFormalReplacedSubjectOrModule replacedSubjectOrModule) {
        ApelApplicationFormalReplacedSubjectOrModuleDto dto = EntityUtil.bindToDto(replacedSubjectOrModule,
                new ApelApplicationFormalReplacedSubjectOrModuleDto());
        dto.setSubject(replacedSubjectOrModule.getSubject() != null
                ? SubjectDto.forApelApplication(replacedSubjectOrModule.getSubject())
                : null);
        dto.setCurriculumVersionOmodule(
                replacedSubjectOrModule.getCurriculumVersionOmodule() != null ? CurriculumVersionOccupationModuleDto
                        .forApelApplicationForm(replacedSubjectOrModule.getCurriculumVersionOmodule()) : null);
        dto.setCurriculumVersionOmoduleTheme(
                replacedSubjectOrModule.getCurriculumVersionOmoduleTheme() != null
                        ? CurriculumVersionOccupationModuleThemeDto
                                .forApelApplicationForm(replacedSubjectOrModule.getCurriculumVersionOmoduleTheme())
                        : null);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public CurriculumVersionOccupationModuleDto getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModuleDto curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public CurriculumVersionOccupationModuleThemeDto getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(CurriculumVersionOccupationModuleThemeDto curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }

}
