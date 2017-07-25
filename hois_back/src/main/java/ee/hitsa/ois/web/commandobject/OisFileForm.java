package ee.hitsa.ois.web.commandobject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class OisFileForm {

    private Long id;

    @NotNull
    @Valid
    private OisFileCommand oisFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OisFileCommand getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileCommand oisFile) {
        this.oisFile = oisFile;
    }

}
