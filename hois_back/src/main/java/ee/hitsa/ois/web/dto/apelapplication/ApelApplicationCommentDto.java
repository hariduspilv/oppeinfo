package ee.hitsa.ois.web.dto.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationComment;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationCommentDto extends InsertedChangedVersionDto {

    private Long id;
    
    private String addInfo;
    private Boolean isStudent;
    
    public static ApelApplicationCommentDto of(ApelApplicationComment applicationComment) {
        ApelApplicationCommentDto dto = EntityUtil.bindToDto(applicationComment, new ApelApplicationCommentDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }
    
}
