package ee.hitsa.ois.report.teacherdetailload;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherResultRowDto {
    
    private AutocompleteResult teacher;
    
    private List<ResultRowDto> rows;

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public List<ResultRowDto> getRows() {
        return rows;
    }

    public void setRows(List<ResultRowDto> rows) {
        this.rows = rows;
    }

}
