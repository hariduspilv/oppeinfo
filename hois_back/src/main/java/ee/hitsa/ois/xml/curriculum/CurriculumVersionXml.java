package ee.hitsa.ois.xml.curriculum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.LocalDateTimeXmlAdapter;
import ee.hitsa.ois.LocalDateXmlAdapter;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class CurriculumVersionXml {
    
    private Long id;
    private Long version;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    private String code;
    private Short admissionYear;
    private String targetGroup;
    private Boolean individual;
    private String teachers;
    private String description;
    private String type;
    private String status;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Set<Long> specialities;
    private Set<CurriculumVersionHigherModuleXml> modules;
    
    public static CurriculumXml get(CurriculumVersion version) {
        CurriculumXml xml = CurriculumXml.ofWithoutVersions(version.getCurriculum());
        xml.setVersions(new HashSet<>());
        xml.getVersions().add(CurriculumVersionXml.of(version));
        return xml;
    }
    
    public static CurriculumVersionXml of (CurriculumVersion version) {
        CurriculumVersionXml xml = EntityUtil.bindToDto(version, new CurriculumVersionXml(), "modules", "specialities", "type", "status");
        xml.setType(ClassifierUtil.getNullableNameEt(version.getType()));
        xml.setStatus(ClassifierUtil.getNullableNameEt(version.getStatus()));
        xml.setSpecialities(StreamUtil.toMappedSet(s -> EntityUtil.getId(s.getCurriculumSpeciality()), version.getSpecialities()));
        xml.setModules(version.getModules().stream().sorted(Comparator
                .comparing(CurriculumVersionHigherModule::getOrderNr, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(
                        Comparator.comparing(CurriculumVersionHigherModule::getNameEt, String.CASE_INSENSITIVE_ORDER)))
                .map(CurriculumVersionHigherModuleXml::of).collect(Collectors.toCollection(LinkedHashSet::new)));
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

    public LocalDateTime getInserted() {
        return inserted;
    }

    @XmlJavaTypeAdapter(type=LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    @XmlJavaTypeAdapter(type=LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Short admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public Boolean getIndividual() {
        return individual;
    }

    public void setIndividual(Boolean individual) {
        this.individual = individual;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    @XmlElementWrapper(name = "specialities")
    @XmlElement(name="id")
    public Set<Long> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Long> specialities) {
        this.specialities = specialities;
    }

    @XmlElementWrapper(name = "modules")
    @XmlElement(name="module")
    public Set<CurriculumVersionHigherModuleXml> getModules() {
        return modules;
    }

    public void setModules(Set<CurriculumVersionHigherModuleXml> modules) {
        this.modules = modules;
    }
}
