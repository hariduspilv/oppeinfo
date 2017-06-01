package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

public class JournalStudentsCommand {

    private List<Long> students;

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }

}
