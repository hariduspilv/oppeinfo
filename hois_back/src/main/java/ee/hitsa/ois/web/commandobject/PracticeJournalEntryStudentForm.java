package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class PracticeJournalEntryStudentForm {

    private Long id;
    private LocalDate practiceDate;
    @Size(max=10000)
    private String description;
    private Double hours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(LocalDate practiceDate) {
        this.practiceDate = practiceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

}
