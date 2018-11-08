package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StudentAbsenceLessonsForm {

    private Map<LocalDate, Map<Long, Boolean>> lessonsByDate = new HashMap<>();
    
    public Map<LocalDate, Map<Long, Boolean>> getLessonsByDate() {
        return lessonsByDate;
    }
    
    public void setLessonsByDate(Map<LocalDate, Map<Long, Boolean>> lessonsByDate) {
        this.lessonsByDate = lessonsByDate;
    }
    
}
