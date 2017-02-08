package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.SchoolDto;

public class CurriculumVersionHigherModuleSubjectDto extends VersionedCommand {
    
    private Long id;
    private Long electiveModule;
    
    @NotNull
    private Boolean optional;
    
    /*
     * SubjectDto is not used as it has Classifier object fields(not classifier codes),
     * which can not be serialized
     */
    @NotNull
    private Long subjectId;
    private String nameEt;
    private String nameEn;
    private SchoolDto school;
    private BigDecimal credits;
    private String code;
    
    
    public static CurriculumVersionHigherModuleSubjectDto of(CurriculumVersionHigherModuleSubject subject, Set<CurriculumVersionElectiveModuleDto> electiveModules) {
        CurriculumVersionHigherModuleSubjectDto dto = 
                EntityUtil.bindToDto(subject, new CurriculumVersionHigherModuleSubjectDto(), "subject");
        dto.setSubjectId(subject.getSubject().getId());
        dto.setNameEt(subject.getSubject().getNameEt());
        dto.setNameEn(subject.getSubject().getNameEn());
        dto.setCredits(subject.getSubject().getCredits());
        dto.setCode(subject.getSubject().getCode());
        dto.setSchool(SchoolDto.of(subject.getSubject().getSchool()));
        
        List<CurriculumVersionElectiveModuleDto> list = electiveModules.stream().filter(em -> em.getSubjects().contains(dto.getSubjectId())).collect(Collectors.toList());  
        if(list.size() == 1) {
            dto.setElectiveModule(list.get(0).getReferenceNumber());
        }
        return dto;
    }
    
    public Long getElectiveModule() {
        return electiveModule;
    }

    public void setElectiveModule(Long electiveModule) {
        this.electiveModule = electiveModule;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public SchoolDto getSchool() {
        return school;
    }

    public void setSchool(SchoolDto school) {
        this.school = school;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
}
