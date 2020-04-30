package ee.hitsa.ois.web.commandobject.report;

import java.util.List;

public class StudentDataLongList extends StudentData {
    
    private List<Long> value;

    public List<Long> getValue() {
        return value;
    }

    public void setValue(List<Long> value) {
        this.value = value;
    }

}
