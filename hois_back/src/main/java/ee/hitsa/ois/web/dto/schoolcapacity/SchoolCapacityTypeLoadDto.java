package ee.hitsa.ois.web.dto.schoolcapacity;

import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeLoadForm;

import java.time.LocalDate;

public class SchoolCapacityTypeLoadDto extends SchoolCapacityTypeLoadForm {

    private Type type;
    private LocalDate startDate;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public enum Type {
        PREVIOUS,
        CURRENT,
        NEXT
    }
}
