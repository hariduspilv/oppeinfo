package ee.hitsa.ois.xml.curriculum;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumVersionHigherModuleSubjectXml {
    
    private Long id;
    private Long version;
    private Long electiveModule;
    private Boolean optional;
    private Long subjectId;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private String code;
    
    public static CurriculumVersionHigherModuleSubjectXml of(CurriculumVersionHigherModuleSubject subject) {
        CurriculumVersionHigherModuleSubjectXml xml = 
                EntityUtil.bindToDto(subject, new CurriculumVersionHigherModuleSubjectXml(), "subject", "electiveModule");
        xml.setSubjectId(subject.getSubject().getId());
        xml.setNameEt(subject.getSubject().getNameEt());
        xml.setNameEn(subject.getSubject().getNameEn());
        xml.setCredits(subject.getSubject().getCredits());
        xml.setCode(subject.getSubject().getCode());
        xml.setElectiveModule(EntityUtil.getNullableId(subject.getElectiveModule()));
        return xml;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getElectiveModule() {
        return electiveModule;
    }

    public void setElectiveModule(Long electiveModule) {
        this.electiveModule = electiveModule;
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
