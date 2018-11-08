package ee.hitsa.ois.web.dto.timetable;

import java.util.Set;

public class JournalEntryStudentAcceptedAbsenceDto {

    private Long journalStudent;
    private Boolean wholeDay;
    private Boolean practice;
    private Set<Long> lessons;

    public Long getJournalStudent() {
        return journalStudent;
    }

    public void setJournalStudent(Long journalStudent) {
        this.journalStudent = journalStudent;
    }

    public Boolean getWholeDay() {
        return wholeDay;
    }

    public void setWholeDay(Boolean wholeDay) {
        this.wholeDay = wholeDay;
    }

    public Boolean getPractice() {
        return practice;
    }

    public void setPractice(Boolean practice) {
        this.practice = practice;
    }

    public Set<Long> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Long> lessons) {
        this.lessons = lessons;
    }

}
