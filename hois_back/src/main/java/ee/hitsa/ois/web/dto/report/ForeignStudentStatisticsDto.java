package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.temporal.ChronoUnit;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ForeignStudentStatisticsDto extends GuestStudentStatisticsDto {

    private String educationLevel;
    private AutocompleteResult schoolDepartment;
    private Long applicationId;
    private Long wantedEAP;
    private String foreignStudent;
    private AutocompleteResult foreignSchool;
    private String foreignCountry;
    private String extention;
    
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
        this.educationLevel = resultAsString(r, 7);
        this.schoolDepartment = new AutocompleteResult(null, resultAsString(r, 8), resultAsString(r, 9));
        this.startDate = resultAsLocalDate(r, 10);
        this.endDate = resultAsLocalDate(r, 11);
        if (startDate != null && endDate != null) {
            this.duration = Long.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1);
        }
        this.foreignSchool = new AutocompleteResult(null, resultAsString(r, 12), resultAsString(r, 13));
        this.foreignCountry = resultAsString(r, 14);
        this.programme = resultAsString(r, 15);
        this.applicationId = resultAsLong(r, 16);
        this.wantedEAP = resultAsLong(r, 17);
        this.eap = resultAsLong(r, 18);
        String nominalValue = resultAsString(r, 19);
        if (nominalValue != null) {
            this.extention = "NOM_PIKEND_" + nominalValue;
        }
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public AutocompleteResult getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(AutocompleteResult schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
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

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }
}
