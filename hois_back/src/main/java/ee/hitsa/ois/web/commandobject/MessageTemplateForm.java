package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;

public class MessageTemplateForm extends VersionedCommand {
    
    @NotBlank
    @Size(max=1000)
    private String headline;
    @NotBlank
    private String content;
    private LocalDate validFrom;
    private LocalDate validThru;
    @Required
    @ClassifierRestriction(MainClassCode.TEATE_LIIK)
    private String type;
    
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
}
