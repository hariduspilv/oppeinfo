package ee.hitsa.ois.web.dto.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalReplacedSubjectOrModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;

public class ApelApplicationFormalReplacedSubjectOrModuleDto {

    private Long id;
    private SubjectDto subject;
    private CurriculumVersionOccupationModuleDto curriculumVersionOmodule;
    
    public static ApelApplicationFormalReplacedSubjectOrModuleDto of(
            ApelApplicationFormalReplacedSubjectOrModule replacedSubjectOrModule) {
        ApelApplicationFormalReplacedSubjectOrModuleDto dto = EntityUtil.bindToDto(replacedSubjectOrModule,
                new ApelApplicationFormalReplacedSubjectOrModuleDto());
        dto.setSubject(replacedSubjectOrModule.getSubject() != null ? SubjectDto.of(replacedSubjectOrModule.getSubject(), null) : null);
        dto.setCurriculumVersionOmodule(replacedSubjectOrModule.getCurriculumVersionOmodule() != null 
                ? CurriculumVersionOccupationModuleDto.of(replacedSubjectOrModule.getCurriculumVersionOmodule()) : null);
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
    
}
