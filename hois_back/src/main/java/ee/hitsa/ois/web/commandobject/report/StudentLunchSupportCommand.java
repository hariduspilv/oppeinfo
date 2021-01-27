package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;

public class StudentLunchSupportCommand {
    
    private String name;
    private String idcode;
    private Long studentGroup;
    private Long curriculum;
    private LocalDate startFrom;
    private LocalDate startThru;
    private Long searchType;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
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
    public Long getSearchType() {
        return searchType;
    }
    public void setSearchType(Long searchType) {
        this.searchType = searchType;
    }

}
