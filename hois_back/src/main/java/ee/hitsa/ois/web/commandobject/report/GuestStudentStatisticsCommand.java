package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;

public class GuestStudentStatisticsCommand {
    
    private Long studyYear;
    private String student;
    private LocalDate startFrom;
    private LocalDate startThru;
    private LocalDate endFrom;
    private LocalDate endThru;
    private Long curriculum;
    private Long curriculumVersion;
    private Long department;
    private String educationLevel;
    private String homeSchool;
    private String homeCountry;
    private String programme;
    
    public LocalDate getStartFrom() {
        return startFrom;
    }
    public void setStartFrom(LocalDate startFrom) {
        this.startFrom = startFrom;
    }
    public LocalDate getStartThru() {
        return startThru;
    }
    public void setStartThru(LocalDate startThru) {
        this.startThru = startThru;
    }
    public LocalDate getEndFrom() {
        return endFrom;
    }
    public void setEndFrom(LocalDate endFrom) {
        this.endFrom = endFrom;
    }
    public LocalDate getEndThru() {
        return endThru;
    }
    public void setEndThru(LocalDate endThru) {
        this.endThru = endThru;
    }
    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    public Long getDepartment() {
        return department;
    }
    public void setDepartment(Long department) {
        this.department = department;
    }
    public String getEducationLevel() {
        return educationLevel;
    }
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
    public String getHomeSchool() {
        return homeSchool;
    }
    public void setHomeSchool(String homeSchool) {
        this.homeSchool = homeSchool;
    }
    public String getHomeCountry() {
        return homeCountry;
    }
    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }
    public String getProgramme() {
        return programme;
    }
    public void setProgramme(String programme) {
        this.programme = programme;
    }
    public Long getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

}
