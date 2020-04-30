package ee.hitsa.ois.web.commandobject.report;

import java.util.List;

public class StudentDataStringList extends StudentData {
    
    private List<String> value;

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
    
}
