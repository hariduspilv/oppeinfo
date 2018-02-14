package ee.hitsa.ois.web.commandobject.studymaterial;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class StudyMaterialForm extends InsertedChangedVersionDto {

    private Long teacher;
    private Long journal;
    private Long subjectStudyPeriod;
    @NotNull
    private String nameEt;
    private String descriptionEt;
    @NotNull
    private String typeCode;
    @NotNull
    private Boolean isPublic;
    @NotNull
    private Boolean isVisibleToStudents;
    private String url;
    @Valid
    private OisFileCommand oisFile;
    @NotNull
    private Boolean isVocational;
    
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    
    public Long getJournal() {
        return journal;
    }
    public void setJournal(Long journal) {
        this.journal = journal;
    }
    
    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
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
    
    public OisFileCommand getOisFile() {
        return oisFile;
    }
    public void setOisFile(OisFileCommand oisFile) {
        this.oisFile = oisFile;
    }
    
    public Boolean getIsVocational() {
        return isVocational;
    }
    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

}
