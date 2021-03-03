package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumStudyField;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumStudyFieldDto {
    
    private Long id;
    private Long tempId;
    private String nameEt;
    private String nameEn;
    
    public static CurriculumStudyFieldDto of(CurriculumStudyField studyField) {
        CurriculumStudyFieldDto dto = new CurriculumStudyFieldDto();
        EntityUtil.bindToDto(studyField, dto);
        dto.setTempId(EntityUtil.getId(studyField));
        return dto;
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

}
