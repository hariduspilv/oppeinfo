package ee.hitsa.ois.web.dto.timetable;

public class TimetableDifferenceExcelDto {
    private Long journalId;
    private String journalName;
    private String studentGroups;
    private String themes;
    private String capacityType;
    private String teacherNames;
    private Long previousWeek = Long.valueOf(0);
    private Long currentWeek = Long.valueOf(0);
    private Long difference = Long.valueOf(0);

    public TimetableDifferenceExcelDto(Long journalId, String journalName, String studentGroups,
            String themes, String capacityType) {
        this.journalId = journalId;
        this.journalName = journalName;
        this.studentGroups = studentGroups;
        this.themes = themes;
        this.capacityType = capacityType;
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

    public String getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
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
