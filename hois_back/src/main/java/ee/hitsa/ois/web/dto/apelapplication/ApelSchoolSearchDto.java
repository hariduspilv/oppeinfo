package ee.hitsa.ois.web.dto.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.util.EntityUtil;

public class ApelSchoolSearchDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private String country;
    private String ehisSchool;
    
    public static ApelSchoolSearchDto of(ApelSchool school) {
        ApelSchoolSearchDto dto = EntityUtil.bindToDto(school, new ApelSchoolSearchDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }
    
}
