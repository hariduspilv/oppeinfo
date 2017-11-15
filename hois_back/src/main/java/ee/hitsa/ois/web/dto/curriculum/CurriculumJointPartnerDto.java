package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumJointPartnerDto extends VersionedCommand {
    
    private Long id;
    
    @NotNull
    private Boolean abroad;

    @Size(max=1000)
    private String contractEt;

    @Size(max=1000)
    private String contractEn;

    @Size(max=255)
    private String supervisor;

    @Size(max=255)
    private String nameEt;

    @Size(max=255)
    private String nameEn;
    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String ehisSchool;
    
    /**
     * User cannot delete joint partner, if subjects from this school have been added to any curriculum version
     */
    private Boolean hasSubjects;    

    public static CurriculumJointPartnerDto of(CurriculumJointPartner parnter) {
        return EntityUtil.bindToDto(parnter, new CurriculumJointPartnerDto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getHasSubjects() {
        return hasSubjects;
    }

    public void setHasSubjects(Boolean hasSubjects) {
        this.hasSubjects = hasSubjects;
    }
}
