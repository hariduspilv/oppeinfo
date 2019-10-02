package ee.hitsa.ois.web.dto.timetable;

public class TimetableJournalTeacherCapacityDto extends TimetableVocationalCapacityDto {
    private Long journalTeacher;

    public TimetableJournalTeacherCapacityDto(Long journalTeacher, String capacityType) {
        this.journalTeacher = journalTeacher;
        this.setCapacityType(capacityType);
    }

    public Long getJournalTeacher() {
        return journalTeacher;
    }

    public void setJournalTeacher(Long journalTeacher) {
        this.journalTeacher = journalTeacher;
    }

}
