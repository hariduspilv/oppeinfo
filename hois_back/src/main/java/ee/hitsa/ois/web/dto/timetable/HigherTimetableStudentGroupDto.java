package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class HigherTimetableStudentGroupDto extends TimetableStudentGroupDto {
    private List<TimetableEventDto> lessons = new ArrayList<>();
    
    public HigherTimetableStudentGroupDto(Long id, String code, Long curriculumId) {
        super(id, code, curriculumId);
    }

    public List<TimetableEventDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<TimetableEventDto> lessons) {
        this.lessons = lessons;
    }

}
