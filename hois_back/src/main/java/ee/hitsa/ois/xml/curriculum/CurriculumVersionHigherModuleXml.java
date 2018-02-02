package ee.hitsa.ois.xml.curriculum;

import java.math.BigDecimal;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class CurriculumVersionHigherModuleXml {

    private Long id;
    private Long version;
    private String nameEt;
    private String nameEn;
    private String objectivesEt;
    private String objectivesEn;
    private String outcomesEt;
    private String outcomesEn;
    private String typeNameEt;
    private String typeNameEn;
    private BigDecimal totalCredits;
    private BigDecimal optionalStudyCredits;
    private BigDecimal compulsoryStudyCredits;
    private Short electiveModulesNumber;
    private Boolean minorSpeciality;
    private String type;
    private Set<CurriculumVersionHigherModuleSubjectXml> subjects;
    private Set<CurriculumVersionElectiveModuleXml> electiveModules;
    private Set<Long> specialities;

    public static CurriculumVersionHigherModuleXml of(CurriculumVersionHigherModule module) {
        CurriculumVersionHigherModuleXml xml = EntityUtil.bindToDto(module, new CurriculumVersionHigherModuleXml(),
                "electiveModules", "specialities", "subjects", "type");
        xml.setType(ClassifierUtil.getNullableNameEt(module.getType()));
        xml.setSpecialities(StreamUtil.toMappedSet(s -> EntityUtil.getId(s.getSpeciality().getCurriculumSpeciality()), module.getSpecialities()));
        xml.setSubjects(StreamUtil.toMappedSet(CurriculumVersionHigherModuleSubjectXml::of, module.getSubjects()));
        xml.setElectiveModules(StreamUtil.toMappedSet(CurriculumVersionElectiveModuleXml::of, module.getElectiveModules()));
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

    public String getObjectivesEt() {
        return objectivesEt;
    }

    public void setObjectivesEt(String objectivesEt) {
        this.objectivesEt = objectivesEt;
    }

    public String getObjectivesEn() {
        return objectivesEn;
    }

    public void setObjectivesEn(String objectivesEn) {
        this.objectivesEn = objectivesEn;
    }

    public String getOutcomesEt() {
        return outcomesEt;
    }

    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }

    public String getOutcomesEn() {
        return outcomesEn;
    }

    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }

    public String getTypeNameEt() {
        return typeNameEt;
    }

    public void setTypeNameEt(String typeNameEt) {
        this.typeNameEt = typeNameEt;
    }

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    public BigDecimal getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(BigDecimal optionalStudyCredits) {
        this.optionalStudyCredits = optionalStudyCredits;
    }

    public BigDecimal getCompulsoryStudyCredits() {
        return compulsoryStudyCredits;
    }

    public void setCompulsoryStudyCredits(BigDecimal compulsoryStudyCredits) {
        this.compulsoryStudyCredits = compulsoryStudyCredits;
    }

    public Short getElectiveModulesNumber() {
        return electiveModulesNumber;
    }

    public void setElectiveModulesNumber(Short electiveModulesNumber) {
        this.electiveModulesNumber = electiveModulesNumber;
    }

    public Boolean getMinorSpeciality() {
        return minorSpeciality;
    }

    public void setMinorSpeciality(Boolean minorSpeciality) {
        this.minorSpeciality = minorSpeciality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElementWrapper(name = "subjects")
    @XmlElement(name="subject")
    public Set<CurriculumVersionHigherModuleSubjectXml> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<CurriculumVersionHigherModuleSubjectXml> subjects) {
        this.subjects = subjects;
    }

    @XmlElementWrapper(name = "electiveModules")
    @XmlElement(name="electiveModule")
    public Set<CurriculumVersionElectiveModuleXml> getElectiveModules() {
        return electiveModules;
    }

    public void setElectiveModules(Set<CurriculumVersionElectiveModuleXml> electiveModules) {
        this.electiveModules = electiveModules;
    }

    @XmlElementWrapper(name = "specialities")
    @XmlElement(name="id")
    public Set<Long> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Long> specialities) {
        this.specialities = specialities;
    }
}
