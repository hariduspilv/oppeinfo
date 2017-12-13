package ee.hitsa.ois.web.dto.apelapplication;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolForm;

public class ApelSchoolDto extends ApelSchoolForm {

    private Long id;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    
    public static ApelSchoolDto of(ApelSchool school) {
        ApelSchoolDto dto = EntityUtil.bindToDto(school, new ApelSchoolDto());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getInserted() {
        return inserted;
    }
    
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    
    public String getInsertedBy() {
        return insertedBy;
    }
    
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
    
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
    
}
