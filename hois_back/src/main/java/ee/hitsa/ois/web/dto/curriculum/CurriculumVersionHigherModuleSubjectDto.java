package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionHigherModuleSubjectDto extends VersionedCommand {
    
    private Long id;
    private Long electiveModule;
    
    @NotNull
    private Boolean optional;
    
    @NotNull
    private Long subjectId;
    private String nameEt;
    private String nameEn;
    private String schoolCode;
    private String ehisSchoolCode;
    private BigDecimal credits;
    private String code;
    
    public static CurriculumVersionHigherModuleSubjectDto of (Subject subject) {
        CurriculumVersionHigherModuleSubjectDto dto = new CurriculumVersionHigherModuleSubjectDto();
        dto.setNameEt(subject.getNameEt());
        dto.setNameEn(subject.getNameEn());
        dto.setCredits(subject.getCredits());
        dto.setCode(subject.getCode());
        dto.setSubjectId(subject.getId());
        dto.setSchoolCode(subject.getSchool().getCode());
        dto.setEhisSchoolCode(EntityUtil.getCode(subject.getSchool().getEhisSchool()));

        return dto;
    }
    
    public static CurriculumVersionHigherModuleSubjectDto of(CurriculumVersionHigherModuleSubject subject) {
        CurriculumVersionHigherModuleSubjectDto dto = 
                EntityUtil.bindToDto(subject, new CurriculumVersionHigherModuleSubjectDto(), "subject", "electiveModule");
        dto.setSubjectId(subject.getSubject().getId());
        dto.setNameEt(subject.getSubject().getNameEt());
        dto.setNameEn(subject.getSubject().getNameEn());
        dto.setCredits(subject.getSubject().getCredits());
        dto.setCode(subject.getSubject().getCode());
        dto.setSchoolCode(subject.getSubject().getSchool().getCode());
        dto.setEhisSchoolCode(EntityUtil.getCode(subject.getSubject().getSchool().getEhisSchool()));
        dto.setElectiveModule(EntityUtil.getNullableId(subject.getElectiveModule()));
        return dto;
    }
    @XmlTransient
    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    @XmlTransient
    public String getEhisSchoolCode() {
        return ehisSchoolCode;
    }

    public void setEhisSchoolCode(String ehisSchoolCode) {
        this.ehisSchoolCode = ehisSchoolCode;
    }

    public Long getElectiveModule() {
        return electiveModule;
    }

    public void setElectiveModule(Long electiveModule) {
        this.electiveModule = electiveModule;
    }
    @XmlElement(name="subjectCode")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @XmlElement(name="optional")
    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
    
    @XmlElement(name="subjectId")
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    @XmlTransient
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    @XmlTransient
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    @XmlTransient
    public BigDecimal getCredits() {
        return credits;
    }
    
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
}
