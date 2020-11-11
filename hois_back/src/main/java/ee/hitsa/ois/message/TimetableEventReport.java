package ee.hitsa.ois.message;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TimetableEventReport {

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private String teacher;
    private Week week1;
    private Week week2;
    private String url;

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setWeek1(Week week1) {
        this.week1 = week1;
    }

    public void setWeek2(Week week2) {
        this.week2 = week2;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpetajaNimi() {
        return teacher;
    }

    public List<Event> getNadal1Sundmused() {
        return week1 != null ? week1.getEvents() : Collections.emptyList();
    }

    public String getNadalNr1() {
        return Optional.ofNullable(week1).map(Week::getNadalNr).orElse(null);
    }

    public String getNadalAlgusLopp1() {
        return Optional.ofNullable(week1)
                .map(Week::getNadalAlgusLopp)
                .orElse(null);
    }

    public List<Event>  getNadal2Sundmused() {
        return week2 != null ? week2.getEvents() : Collections.emptyList();
    }

    public String getNadalNr2() {
        return Optional.ofNullable(week2).map(Week::getNadalNr).orElse(null);
    }

    public String getNadalAlgusLopp2() {
        return Optional.ofNullable(week2)
                .map(Week::getNadalAlgusLopp)
                .orElse(null);
    }

    public String getTunniplaanUrl() {
        return url;
    }

    public static class Week {

        private Short nr;
        private LocalDate startDt;
        private LocalDate endDt;
        private List<Event> events;

        public void setNr(Short nr) {
            this.nr = nr;
        }

        public void setStartDt(LocalDate startDt) {
            this.startDt = startDt;
        }

        public void setEndDt(LocalDate endDt) {
            this.endDt = endDt;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }

        public String getNadalNr() {
            return nr != null ? nr.toString() : null;
        }

        public List<Event> getEvents() {
            return events;
        }

        public String getNadalAlgusLopp() {
            if (startDt != null && endDt != null) {
                return String.format("%s - %s", DATE_FORMATTER.format(startDt), DATE_FORMATTER.format(endDt));
            }
            return null;
        }

    }

    public static class Event {

        private Short nr;
        private String name;
        private List<String> groups;
        private String weekDay;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<String> rooms;

        public void setNr(Short nr) {
            this.nr = nr;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public void setRooms(List<String> rooms) {
            this.rooms = rooms;
        }

        public String getJrk() {
            return nr != null ? nr.toString() : null;
        }

        public String getSundmuseNimetus() {
            return name;
        }

        public String getOpperyhm() {
            return groups != null && !groups.isEmpty() ? String.join(", ", groups) : "-";
        }

        public String getNadalapaev() {
            return weekDay;
        }

        public String getSundmuseAeg() {
            if (startTime != null && endTime != null) {
                return String.format("%s - %s", TIME_FORMATTER.format(startTime), TIME_FORMATTER.format(endTime));
            }
            return null;
        }

        public String getSundmuseRuum() {
            return rooms != null && !rooms.isEmpty() ? String.join(", ", rooms) : "-";
        }
    }
}
