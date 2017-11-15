package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

public class TimetableClashForm {
    Long timetableEventTimeId;
    List<Long> teachers;
    List<Long> rooms;
    Boolean higher;

    public Long getTimetableEventTimeId() {
        return timetableEventTimeId;
    }

    public void setTimetableEventTimeId(Long timetableEventTimeId) {
        this.timetableEventTimeId = timetableEventTimeId;
    }

    public List<Long> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Long> teachers) {
        this.teachers = teachers;
    }

    public List<Long> getRooms() {
        return rooms;
    }

    public void setRooms(List<Long> rooms) {
        this.rooms = rooms;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }
    

}
