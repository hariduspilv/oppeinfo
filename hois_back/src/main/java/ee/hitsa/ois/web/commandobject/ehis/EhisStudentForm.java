package ee.hitsa.ois.web.commandobject.ehis;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.EhisStudentDataType;
import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "from", thru = "thru")
public class EhisStudentForm {

    @NotNull
    private EhisStudentDataType dataType;
    private LocalDate from;
    private LocalDate thru;

    public EhisStudentDataType getDataType() {
        return dataType;
    }

    public void setDataType(EhisStudentDataType dataType) {
        this.dataType = dataType;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }
}
