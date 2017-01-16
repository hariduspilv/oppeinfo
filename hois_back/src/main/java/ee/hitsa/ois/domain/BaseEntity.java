package ee.hitsa.ois.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import ee.hitsa.ois.util.Versioned;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Annotation removed as it broke down all mapping of StateCurriculum and related objects while creating and updating.
 * Command objects will be added later as replacement.
 */
//@JsonIgnoreProperties(value = {"changed", "inserted", "version"}, allowGetters = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable, Versioned {
    private static final long serialVersionUID = -4748411594594102304L;

    @Version
    @Column(nullable = false)
    private Long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime inserted;
    @CreatedBy
    @Column(updatable = false)
    private String insertedBy;

    @LastModifiedDate
    private LocalDateTime changed;
    @LastModifiedBy
    private String changedBy;

    public LocalDateTime getInserted() {
        return inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public String getChangedBy() {
        return insertedBy;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
