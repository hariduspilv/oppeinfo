package ee.hitsa.ois.web.dto.api;

import java.util.ArrayList;
import java.util.List;

public class ExmatStudentsDto {

    private List<StudentBaseDto> students = new ArrayList<>();

    public List<StudentBaseDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentBaseDto> students) {
        this.students = students;
    }
}
