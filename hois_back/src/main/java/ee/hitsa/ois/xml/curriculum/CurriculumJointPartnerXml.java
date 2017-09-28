package ee.hitsa.ois.xml.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumJointPartnerXml {
    
    private Long id;
    private Long version;
    private Boolean abroad;
    private String contractEt;
    private String contractEn;
    private String supervisor;
    private String nameEt;
    private String nameEn;
    private String ehisSchool;
     
    public static CurriculumJointPartnerXml of(CurriculumJointPartner partner) {
        CurriculumJointPartnerXml xml = EntityUtil.bindToDto(partner, new CurriculumJointPartnerXml(), "ehisSchool");
        xml.setEhisSchool(ClassifierUtil.getNullableNameEt(partner.getEhisSchool()));
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


    public Boolean getAbroad() {
        return abroad;
    }


    public void setAbroad(Boolean abroad) {
        this.abroad = abroad;
    }


    public String getContractEt() {
        return contractEt;
    }


    public void setContractEt(String contractEt) {
        this.contractEt = contractEt;
    }


    public String getContractEn() {
        return contractEn;
    }


    public void setContractEn(String contractEn) {
        this.contractEn = contractEn;
    }


    public String getSupervisor() {
        return supervisor;
    }


    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
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


    public String getEhisSchool() {
        return ehisSchool;
    }


    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }
}
