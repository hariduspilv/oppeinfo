package ee.hitsa.ois.report.studentgroupteacher;

import java.util.List;
import java.util.Map;

public class StudentGroupTeacherReportTable {

    private List<String> resultColumns;
    private List<Map<String, Object>> students;
    
    public List<String> getResultColumns() {
        return resultColumns;
    }
    
    public void setResultColumns(List<String> resultColumns) {
        this.resultColumns = resultColumns;
    }
    
    public List<Map<String, Object>> getStudents() {
        return students;
    }
    
    public void setStudents(List<Map<String, Object>> students) {
        this.students = students;
    }
    
}
