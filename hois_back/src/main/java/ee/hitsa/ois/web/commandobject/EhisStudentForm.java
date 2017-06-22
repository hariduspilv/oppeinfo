package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.EhisStudentDataType;
import ee.hitsa.ois.validation.DateRange;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@DateRange(from = "from", thru = "thru")
public class EhisStudentForm {
    @NotNull
    private EhisStudentDataType dataType;
    private Long schoolID;

    private LocalDate from;

    private LocalDate thru;

    public EhisStudentDataType getDataType() {
        return dataType;
    }

    public void setDataType(EhisStudentDataType dataType) {
        this.dataType = dataType;
    }

    public Long getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Long schoolID) {
        this.schoolID = schoolID;
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
