package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.OisFileCommand;

public class StudentSupportServiceForm {
    
    private Long id;
    @Required
    private LocalDate entryDate;
    @Required
    @Size(max=255)
    private String nameEt;
    @Required
    @Size(max=4000)
    private String content;
    @Required
    @ClassifierRestriction(MainClassCode.TUGIKEHTIV)
    private String validity;
    private Boolean isPublic;
    private OisFileCommand file;
    private String entrySubmitter;
    private Boolean ehis = Boolean.FALSE;
    private Boolean visible = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public OisFileCommand getFile() {
        return file;
    }

    public void setFile(OisFileCommand file) {
        this.file = file;
    }

    public String getEntrySubmitter() {
        return entrySubmitter;
    }

    public void setEntrySubmitter(String entrySubmitter) {
        this.entrySubmitter = entrySubmitter;
    }

    public Boolean getEhis() {
        return ehis;
    }

    public void setEhis(Boolean ehis) {
        this.ehis = ehis;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
