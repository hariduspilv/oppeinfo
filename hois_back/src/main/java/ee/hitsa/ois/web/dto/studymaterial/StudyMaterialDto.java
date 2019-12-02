package ee.hitsa.ois.web.dto.studymaterial;

import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class StudyMaterialDto extends InsertedChangedVersionDto {

    private Long id;
    private Long teacher;
    private String nameEt;
    private String descriptionEt;
    private String typeCode;
    private Boolean isPublic;
    private Boolean isVisibleToStudents;
    private String url;
    private Boolean isVocational;
    private Boolean canEdit;

    public static StudyMaterialDto of(StudyMaterial material) {
        if (material == null) {
            return null;
        }
        return EntityUtil.bindToDto(material, new StudyMaterialDto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
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

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

}
