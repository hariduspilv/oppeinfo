package ee.hitsa.ois.web.dto.timetable;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;

public class TimetableImportDto extends TimetableEditForm {

    private Boolean isIndividual;
    @NotNull
    private Boolean isHigher;
    @NotNull
    private OisFile oisFile;

    public Boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public OisFile getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }

}
