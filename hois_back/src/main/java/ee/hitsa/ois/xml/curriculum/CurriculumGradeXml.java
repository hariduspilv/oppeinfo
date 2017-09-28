package ee.hitsa.ois.xml.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumGradeXml {
    
    private Long id;
    private Long version;
    private String nameEt;
    private String nameEn;
    private String nameGenitiveEt;
    private String ehisGrade;
    
    public static CurriculumGradeXml of(CurriculumGrade grade) {
        CurriculumGradeXml xml = EntityUtil.bindToDto(grade, new CurriculumGradeXml(), "ehisGrade");
        xml.setEhisGrade(ClassifierUtil.getNullableNameEt(grade.getEhisGrade()));
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

    public String getNameGenitiveEt() {
        return nameGenitiveEt;
    }

    public void setNameGenitiveEt(String nameGenitiveEt) {
        this.nameGenitiveEt = nameGenitiveEt;
    }

    public String getEhisGrade() {
        return ehisGrade;
    }

    public void setEhisGrade(String ehisGrade) {
        this.ehisGrade = ehisGrade;
    }
}
