package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

@DateRange(from = "from", thru = "thru")
public class StudentStatisticsByPeriodCommand {

    private LocalDate from;
    private LocalDate thru;
    private List<EntityConnectionCommand> curriculum;
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

    public List<EntityConnectionCommand> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<EntityConnectionCommand> curriculum) {
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

    private static final Set<String> VALID_RESULT_VALUES = EnumUtil.toNameSet(
            StudentStatus.OPPURSTAATUS_K, StudentStatus.OPPURSTAATUS_L, StudentStatus.OPPURSTAATUS_A);
}
