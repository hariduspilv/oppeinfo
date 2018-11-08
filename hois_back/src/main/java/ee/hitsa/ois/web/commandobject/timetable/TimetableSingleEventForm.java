package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.TimetableSingleEventTeacherForm;

public class TimetableSingleEventForm {
    // TODO: Validation is missing
    private Long id;
    private LocalDate date;
    @Required
    private LocalDateTime startTime;
    @Required
    private LocalDateTime endTime;
    @Required
    @Size(max = 255)
    private String name;
    private Boolean repeat;
    private String repeatCode;
    private List<AutocompleteResult> rooms;
    private List<TimetableSingleEventTeacherForm> teachers;
    private List<AutocompleteResult> studentGroups;
    private Long weekAmount;
    private String otherTeacher;
    private String otherRoom;
    private Boolean isSingleEvent;

    public static TimetableSingleEventForm of(TimetableEventTime event) {
        TimetableSingleEventForm form = new TimetableSingleEventForm();
        form.setId(EntityUtil.getId(event));
        form.setDate(event.getStart().toLocalDate());
        form.setStartTime(event.getStart());
        form.setEndTime(event.getEnd());
        form.setName(event.getTimetableEvent().getName());
        form.setRepeat(Boolean.FALSE);

        form.setRooms(StreamUtil.toMappedList(r -> {
            Room room = r.getRoom();
            Building building = room.getBuilding();
            
            String code = building.getCode() + "-" + room.getCode();
            Long seats = room.getSeats();
            String nameEt = seats != null ? code + " (kohti " + seats.toString() + ")" : code;
            String nameEn = seats != null ? code + " (seats " + seats.toString() + ")" : code;
            return new AutocompleteResult(EntityUtil.getId(room), nameEt, nameEn);
        }, event.getTimetableEventRooms()));
        form.setOtherRoom(event.getOtherRoom());

        form.setTeachers(
                StreamUtil.toMappedList(r -> TimetableSingleEventTeacherForm.of(r), event.getTimetableEventTeachers()));
        form.setOtherTeacher(event.getOtherTeacher());

        Set<AutocompleteResult> studentGroups = StreamUtil.toMappedSet(r -> AutocompleteResult.of(r.getStudentGroup()),
                event.getTimetableEventStudentGroups());
        
        TimetableObject tobj = event.getTimetableEvent().getTimetableObject();
        if (tobj != null) {
            List<AutocompleteResult> tobjStudentGroups = StreamUtil.toMappedList(
                    r -> AutocompleteResult.of(r.getStudentGroup()), tobj.getTimetableObjectStudentGroups());
            studentGroups.addAll(tobjStudentGroups);
        }
        
        List<AutocompleteResult> studentGroupsList = new ArrayList<>();
        studentGroupsList.addAll(studentGroups);
        form.setStudentGroups(studentGroupsList);

        LocalDateTime maxDate = event.getTimetableEvent().getTimetableEventTimes().stream()
                .map(TimetableEventTime::getStart).max(LocalDateTime::compareTo).get();
        form.setWeekAmount(Long.valueOf(event.getStart().until(maxDate, ChronoUnit.WEEKS)));
        form.setIsSingleEvent(Boolean.valueOf(event.getTimetableEvent().getTimetableObject() == null));
        return form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public String getRepeatCode() {
        return repeatCode;
    }

    public void setRepeatCode(String repeatCode) {
        this.repeatCode = repeatCode;
    }

    public List<AutocompleteResult> getRooms() {
        return rooms;
    }

    public void setRooms(List<AutocompleteResult> rooms) {
        this.rooms = rooms;
    }

    public List<TimetableSingleEventTeacherForm> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TimetableSingleEventTeacherForm> teachers) {
        this.teachers = teachers;
    }

    public List<AutocompleteResult> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<AutocompleteResult> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Long getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Long weekAmount) {
        this.weekAmount = weekAmount;
    }

    public static TimetableSingleEventForm of(Optional<TimetableEventTime> findFirst) {
        if (findFirst.isPresent()) {
            return of(findFirst.get());
        }
        return null;
    }

    public String getOtherTeacher() {
        return otherTeacher;
    }

    public void setOtherTeacher(String otherTeacher) {
        this.otherTeacher = otherTeacher;
    }

    public String getOtherRoom() {
        return otherRoom;
    }

    public void setOtherRoom(String otherRoom) {
        this.otherRoom = otherRoom;
    }

    public Boolean getIsSingleEvent() {
        return isSingleEvent;
    }

    public void setIsSingleEvent(Boolean isSingleEvent) {
        this.isSingleEvent = isSingleEvent;
    }

}
