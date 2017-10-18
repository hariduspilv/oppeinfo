package ee.hitsa.ois.web.dto.timetable;

public class TimetableDifferenceExcelDto {
    private Long journalId;
    private String journalName;
    private Long studentGroupId;
    private String studentGroup;
    private String capacityType;
    private String teacherNames;
    private Long previousWeek;
    private Long currentWeek;
    private Long difference;

    public TimetableDifferenceExcelDto(Long journalId, String journalName, Long studentGroupId, String studentGroup, 
            String capacityType, Long currentWeek) {
        this.journalId = journalId;
        this.journalName = journalName;
        this.studentGroupId = studentGroupId;
        this.studentGroup = studentGroup;
        this.capacityType = capacityType;
        this.currentWeek = currentWeek;
        this.previousWeek = Long.valueOf(0);
        this.difference = currentWeek;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }

    public Long getPreviousWeek() {
        return previousWeek;
    }

    public void setPreviousWeek(Long previousWeek) {
        this.previousWeek = previousWeek;
    }

    public Long getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(Long currentWeek) {
        this.currentWeek = currentWeek;
    }

    public Long getDifference() {
        return difference;
    }

    public void setDifference(Long difference) {
        this.difference = difference;
    }

}
