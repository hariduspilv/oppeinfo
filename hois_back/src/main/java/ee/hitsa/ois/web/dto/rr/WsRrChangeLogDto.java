package ee.hitsa.ois.web.dto.rr;

import java.time.LocalDateTime;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class WsRrChangeLogDto {

    private AutocompleteResult student;
    private AutocompleteResult group;
    private LocalDateTime changed;
    private String changedBy;
    private String newName;
    private String newAddress;
    private String oldName;
    private String oldAddress;
    private Boolean sameName;
    private Boolean sameAddress;
    
    public AutocompleteResult getStudent() {
        return student;
    }
    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }
    public AutocompleteResult getGroup() {
        return group;
    }
    public void setGroup(AutocompleteResult group) {
        this.group = group;
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
    public String getNewName() {
        return newName;
    }
    public void setNewName(String newName) {
        this.newName = newName;
    }
    public String getNewAddress() {
        return newAddress;
    }
    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
    public String getOldName() {
        return oldName;
    }
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
    public String getOldAddress() {
        return oldAddress;
    }
    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }
    public Boolean getSameName() {
        return sameName;
    }
    public void setSameName(Boolean sameName) {
        this.sameName = sameName;
    }
    public Boolean getSameAddress() {
        return sameAddress;
    }
    public void setSameAddress(Boolean sameAddress) {
        this.sameAddress = sameAddress;
    }
}
