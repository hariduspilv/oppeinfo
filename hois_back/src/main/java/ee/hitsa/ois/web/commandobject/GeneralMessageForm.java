package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GeneralMessageForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 1000)
    private String title;
    @NotNull
    @Size(min = 1, max = 4000)
    private String content;
    private LocalDate validFrom;
    private LocalDate validThru;
    @NotNull
    @Size(min = 1)
    private List<String> targets;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }
}
