package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.LocalDateTimeXmlAdapter;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class InsertedChangedVersionDto extends VersionedCommand {

    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;

    public LocalDateTime getInserted() {
        return inserted;
    }
    @XmlJavaTypeAdapter(type=LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
    @XmlJavaTypeAdapter(type=LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
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
