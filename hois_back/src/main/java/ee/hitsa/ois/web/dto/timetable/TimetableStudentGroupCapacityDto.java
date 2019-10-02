package ee.hitsa.ois.web.dto.timetable;

public class TimetableStudentGroupCapacityDto extends TimetableVocationalCapacityDto {
    private Long studentGroup;
    private Long journal;

    public TimetableStudentGroupCapacityDto(Long studentGroup, Long journal, String capacityType) {
        this.studentGroup = studentGroup;
        this.journal = journal;
        this.setCapacityType(capacityType);
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

}
