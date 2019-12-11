package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.temporal.ChronoUnit;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ForeignStudentStatisticsDto extends GuestStudentStatisticsDto {
    
    private Long applicationId;
    private Long wantedEAP;
    private String foreignStudent;
    private AutocompleteResult foreignSchool;
    private String foreignCountry;
    
    public ForeignStudentStatisticsDto () {
        super();
    }
    
    public ForeignStudentStatisticsDto(Object r) {
        super();
        this.studentId = resultAsLong(r, 0);
        this.foreignStudent = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
        String curriculumCode = resultAsString(r, 3);
        if (curriculumCode != null) {
            this.curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(curriculumCode, resultAsString(r, 4)),
                    CurriculumUtil.versionName(curriculumCode, resultAsString(r, 5)));
        }
        this.studentGroup = resultAsString(r, 6);
        this.startDate = resultAsLocalDate(r, 7);
        this.endDate = resultAsLocalDate(r, 8);
        if (startDate != null && endDate != null) {
            this.duration = Long.valueOf(ChronoUnit.DAYS.between(startDate, endDate));
        }
        this.foreignSchool = new AutocompleteResult(null, resultAsString(r, 9), resultAsString(r, 10));
        this.foreignCountry = resultAsString(r, 11);
        this.programme = resultAsString(r, 12);
        this.applicationId = resultAsLong(r, 13);
        this.wantedEAP = resultAsLong(r, 14);
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getWantedEAP() {
        return wantedEAP;
    }

    public void setWantedEAP(Long wantedEAP) {
        this.wantedEAP = wantedEAP;
    }

    public String getForeignStudent() {
        return foreignStudent;
    }

    public void setForeignStudent(String foreignStudent) {
        this.foreignStudent = foreignStudent;
    }

    public String getForeignCountry() {
        return foreignCountry;
    }

    public void setForeignCountry(String foreignCountry) {
        this.foreignCountry = foreignCountry;
    }

    public AutocompleteResult getForeignSchool() {
        return foreignSchool;
    }

    public void setForeignSchool(AutocompleteResult foreignSchool) {
        this.foreignSchool = foreignSchool;
    }
}
