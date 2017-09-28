package ee.hitsa.ois.domain.teacher;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class TeacherContinuingEducation extends BaseEntityWithId{

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier field;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier diploma;
    private String nameEt;
    private LocalDate  diplomaDate;
    private String diplomaNr;
    private Short capacity;
    private String otherSchool;
    private Boolean isAbroad;
    private String abroadDesc;
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public Classifier getField() {
        return field;
    }
    
    public void setField(Classifier field) {
        this.field = field;
    }
    
    public Classifier getSchool() {
        return school;
    }
    
    public void setSchool(Classifier school) {
        this.school = school;
    }
    
    public Classifier getDiploma() {
        return diploma;
    }
    
    public void setDiploma(Classifier diploma) {
        this.diploma = diploma;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public LocalDate getDiplomaDate() {
        return diplomaDate;
    }
    
    public void setDiplomaDate(LocalDate diplomaDate) {
        this.diplomaDate = diplomaDate;
    }
    
    public String getDiplomaNr() {
        return diplomaNr;
    }
    
    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }
    
    public Short getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }
    
    public String getOtherSchool() {
        return otherSchool;
    }
    
    public void setOtherSchool(String otherSchool) {
        this.otherSchool = otherSchool;
    }
    
    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    public String getAbroadDesc() {
        return abroadDesc;
    }
    
    public void setAbroadDesc(String abroadDesc) {
        this.abroadDesc = abroadDesc;
    }

}
