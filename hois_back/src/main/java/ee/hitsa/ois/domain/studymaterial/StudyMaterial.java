package ee.hitsa.ois.domain.studymaterial;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class StudyMaterial extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Teacher teacher;
    private String nameEt;
    @Column(nullable = false, updatable = false)
    private String typeCode;
    private String descriptionEt;
    private Boolean isPublic;
    private Boolean isVisibleToStudents;
    private String url;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OisFile oisFile;
    @Column(nullable = false, updatable = false)
    private Boolean isVocational;
    
    @OneToMany(mappedBy = "studyMaterial")
    private List<StudyMaterialConnect> studyMaterialConnect;
    
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getDescriptionEt() {
        return descriptionEt;
    }
    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }
    
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
    public Boolean getIsPublic() {
        return isPublic;
    }
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    public Boolean getIsVisibleToStudents() {
        return isVisibleToStudents;
    }
    public void setIsVisibleToStudents(Boolean isVisibleToStudents) {
        this.isVisibleToStudents = isVisibleToStudents;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public OisFile getOisFile() {
        return oisFile;
    }
    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }
    
    public Boolean getIsVocational() {
        return isVocational;
    }
    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }
    public List<StudyMaterialConnect> getStudyMaterialConnect() {
        return studyMaterialConnect == null ? (studyMaterialConnect = new LinkedList<>()) : studyMaterialConnect;
    }
    public void setStudyMaterialConnect(List<StudyMaterialConnect> studyMaterialConnect) {
        getStudyMaterialConnect().clear();
        getStudyMaterialConnect().addAll(studyMaterialConnect);
    }
    
}
