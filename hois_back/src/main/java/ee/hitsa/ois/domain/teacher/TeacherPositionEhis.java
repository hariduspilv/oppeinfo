package ee.hitsa.ois.domain.teacher;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.SchoolDepartment;

@Entity
public class TeacherPositionEhis extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier position;

    private Boolean isVocational;

    private LocalDate contractStart;
    private LocalDate contractEnd;
    private Boolean isContractEnded;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier contractType;
    private BigDecimal load;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;

    private Boolean meetsQualification;
    private Boolean isChildCare;
    private Boolean isClassTeacher;

    private String positionSpecificationEt;
    private String positionSpecificationEn;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier employmentType;
    private String employmentTypeSpecification;
    private Boolean isTeacher;
    private String employmentCode;
    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolDepartment schoolDepartment;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Boolean getVocational() {
        return isVocational;
    }

    public void setVocational(Boolean vocational) {
        isVocational = vocational;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public Classifier getPosition() {
        return position;
    }

    public void setPosition(Classifier position) {
        this.position = position;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean vocational) {
        isVocational = vocational;
    }

    public LocalDate getContractStart() {
        return contractStart;
    }

    public void setContractStart(LocalDate contractStart) {
        this.contractStart = contractStart;
    }

    public Boolean getIsContractEnded() {
        return isContractEnded;
    }

    public void setIsContractEnded(Boolean contractEnded) {
        isContractEnded = contractEnded;
    }

    public Classifier getContractType() {
        return contractType;
    }

    public void setContractType(Classifier contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getLoad() {
        return load;
    }

    public void setLoad(BigDecimal load) {
        this.load = load;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public Boolean getMeetsQualification() {
        return meetsQualification;
    }

    public void setMeetsQualification(Boolean meetsQualification) {
        this.meetsQualification = meetsQualification;
    }

    public Boolean getIsChildCare() {
        return isChildCare;
    }

    public void setIsChildCare(Boolean childCare) {
        isChildCare = childCare;
    }

    public Boolean getIsClassTeacher() {
        return isClassTeacher;
    }

    public void setIsClassTeacher(Boolean classTeacher) {
        isClassTeacher = classTeacher;
    }

    public String getPositionSpecificationEt() {
        return positionSpecificationEt;
    }

    public void setPositionSpecificationEt(String positionSpecificationEt) {
        this.positionSpecificationEt = positionSpecificationEt;
    }

    public String getPositionSpecificationEn() {
        return positionSpecificationEn;
    }

    public void setPositionSpecificationEn(String positionSpecificationEn) {
        this.positionSpecificationEn = positionSpecificationEn;
    }

    public Classifier getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Classifier employmentType) {
        this.employmentType = employmentType;
    }

    public String getEmploymentTypeSpecification() {
        return employmentTypeSpecification;
    }

    public void setEmploymentTypeSpecification(String employmentTypeSpecification) {
        this.employmentTypeSpecification = employmentTypeSpecification;
    }

    public LocalDate getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(LocalDate contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getEmploymentCode() {
        return employmentCode;
    }

    public void setEmploymentCode(String employmentCode) {
        this.employmentCode = employmentCode;
    }

    public SchoolDepartment getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(SchoolDepartment schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }
}
