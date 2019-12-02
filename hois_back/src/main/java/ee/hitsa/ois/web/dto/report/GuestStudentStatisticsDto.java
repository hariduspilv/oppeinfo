package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GuestStudentStatisticsDto {
    
    private Long studentId;
    private String guestStudent;
    private AutocompleteResult curriculumVersion;
    private String studentGroup;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long duration;
    private AutocompleteResult homeSchool;
    private String homeCountry;
    private String programme;
    private Long eap;
    
    public GuestStudentStatisticsDto(Object r) {
        this.studentId = resultAsLong(r, 0);
        this.guestStudent = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
        String curriculumCode = resultAsString(r, 3);
        if (curriculumCode != null) {
            this.curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(curriculumCode, resultAsString(r, 4)),
                    CurriculumUtil.versionName(curriculumCode, resultAsString(r, 5)));
        }
        this.studentGroup = resultAsString(r, 6);
        this.startDate = resultAsLocalDate(r, 7);
        this.endDate = resultAsLocalDate(r, 8);
        if (startDate != null && endDate != null) {
            this.duration = Long.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1);
        }
        Long apelSchoolId = resultAsLong(r, 9);
        if (apelSchoolId != null) {
            this.setHomeSchool(new AutocompleteResult(resultAsLong(r, 9), resultAsString(r, 10), resultAsString(r, 11)));
        }
        this.homeCountry = resultAsString(r, 12);
        this.programme = resultAsString(r, 13);
        this.eap = resultAsLong(r, 14);
    }
    public String getGuestStudent() {
        return guestStudent;
    }
    public void setGuestStudent(String guestStudent) {
        this.guestStudent = guestStudent;
    }
    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    public String getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
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
    public Long getEap() {
        return eap;
    }
    public void setEap(Long eap) {
        this.eap = eap;
    }
    public AutocompleteResult getHomeSchool() {
        return homeSchool;
    }
    public void setHomeSchool(AutocompleteResult homeSchool) {
        this.homeSchool = homeSchool;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

}
