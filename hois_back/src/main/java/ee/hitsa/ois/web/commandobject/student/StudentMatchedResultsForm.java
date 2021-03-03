package ee.hitsa.ois.web.commandobject.student;

import javax.validation.Valid;
import java.util.List;

public class StudentMatchedResultsForm {

    @Valid
    private List<StudentMatchedResultForm> rows;

    public List<StudentMatchedResultForm> getRows() {
        return rows;
    }

    public void setRows(List<StudentMatchedResultForm> rows) {
        this.rows = rows;
    }
}
