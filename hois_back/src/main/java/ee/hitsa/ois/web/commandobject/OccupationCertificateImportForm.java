package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.NotEmpty;

@DateRange(from = "from", thru = "thru")
public class OccupationCertificateImportForm {

    @NotEmpty
    private List<Long> curriculumVersion;
    private List<Long> studentGroup;
    private String studentName;
    private LocalDate from;
    private LocalDate thru;

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<Long> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(List<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }
}