package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class TimetablePlanExcelCell {
    private Long id;
    private String name;
    private List<TimetablePlanExcelCell> displayValues = new ArrayList<>();
    
    @Override
    public String toString() {
        return name;
    }

    public TimetablePlanExcelCell(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimetablePlanExcelCell> getDisplayValues() {
        return displayValues;
    }

    public void setDisplayValues(List<TimetablePlanExcelCell> displayValues) {
        this.displayValues = displayValues;
    }

}
