package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "from", thru = "thru")
public class StudentStatisticsByPeriodCommand {

    private LocalDate from;
    private LocalDate thru;
    private List<Long> curriculum;
    private String result;

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

    public List<Long> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<Long> curriculum) {
        this.curriculum = curriculum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @AssertTrue
    public boolean validResult() {
        return result == null || VALID_RESULT_VALUES.contains(result);
    }

    private static final Set<String> VALID_RESULT_VALUES = new HashSet<>(
            Arrays.asList(StudentStatus.OPPURSTAATUS_K.name(), StudentStatus.OPPURSTAATUS_L.name(), StudentStatus.OPPURSTAATUS_A.name()));
}
