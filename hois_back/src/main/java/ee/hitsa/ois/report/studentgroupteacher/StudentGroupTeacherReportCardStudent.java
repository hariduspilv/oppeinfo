package ee.hitsa.ois.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentGroupTeacherReportCardStudent {

    private String fullname;
    private List<StudentGroupTeacherReportCardTableRow> tableRows;
    private Map<String, Long> absenceTypeTotals = new HashMap<>();

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<StudentGroupTeacherReportCardTableRow> getTableRows() {
        return tableRows != null ? tableRows : (tableRows = new ArrayList<>());
    }

    public void setTableRows(List<StudentGroupTeacherReportCardTableRow> tableRows) {
        this.tableRows = tableRows;
    }

    public Map<String, Long> getAbsenceTypeTotals() {
        return absenceTypeTotals;
    }

    public void setAbsenceTypeTotals(Map<String, Long> absenceTypeTotals) {
        this.absenceTypeTotals = absenceTypeTotals;
    }
}
