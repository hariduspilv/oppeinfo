package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.SubjectForm;

import java.time.LocalDateTime;
import java.util.Set;

public class SubjectDto extends SubjectForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Set<AutocompleteResult<Long>> primarySubjects;

    private LocalDateTime inserted;

    private String insertedBy;

    private LocalDateTime changed;

    private String changedBy;

    public static SubjectDto of(Subject subject) {
        return EntityUtil.bindToDto(subject, new SubjectDto());
    }

    public Set<AutocompleteResult<Long>> getPrimarySubjects() {
        return primarySubjects;
    }

    public void setPrimarySubjects(Set<AutocompleteResult<Long>> primarySubjects) {
        this.primarySubjects = primarySubjects;
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
