package ee.hitsa.ois.xml.curriculum;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumSpecialityXml {

    private Long id;
    private Long version;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private String description;
    private String occupationEt;
    private String occupationEn;
    private String occupation;

    public static CurriculumSpecialityXml of(CurriculumSpeciality speciality) {
        CurriculumSpecialityXml xml = EntityUtil.bindToDto(speciality, new CurriculumSpecialityXml(), "occupation");
        xml.setOccupation(ClassifierUtil.getNullableNameEt(speciality.getOccupation()));
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupationEt() {
        return occupationEt;
    }

    public void setOccupationEt(String occupationEt) {
        this.occupationEt = occupationEt;
    }

    public String getOccupationEn() {
        return occupationEn;
    }

    public void setOccupationEn(String occupationEn) {
        this.occupationEn = occupationEn;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
