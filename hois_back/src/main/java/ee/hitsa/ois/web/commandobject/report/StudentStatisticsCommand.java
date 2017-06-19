package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class StudentStatisticsCommand {

    private LocalDate date;
    private List<EntityConnectionCommand> curriculum;
    private String result;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    private static final Set<String> VALID_RESULT_VALUES = new HashSet<>(
            Arrays.asList(MainClassCode.FINALLIKAS.name(), MainClassCode.OPPEVORM.name(), MainClassCode.OPPURSTAATUS.name()));
}
