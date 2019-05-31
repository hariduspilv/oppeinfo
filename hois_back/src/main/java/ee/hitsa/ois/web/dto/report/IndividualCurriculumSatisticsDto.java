package ee.hitsa.ois.web.dto.report;

import java.time.LocalDate;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class IndividualCurriculumSatisticsDto {

    private Long studentId;
    private String fullname;
    private AutocompleteResult studentGroup;
    private AutocompleteResult curriculumModule;
    private String addInfo;
    private LocalDate startDate;
    private LocalDate endDate;

    public IndividualCurriculumSatisticsDto(Object record) {
        studentId = resultAsLong(record, 0);
        fullname = PersonUtil.fullname(resultAsString(record, 1), resultAsString(record, 2));
        studentGroup = new AutocompleteResult(resultAsLong(record, 3), resultAsString(record, 4),
                resultAsString(record, 4));
        curriculumModule = new AutocompleteResult(resultAsLong(record, 5), resultAsString(record, 6),
                resultAsString(record, 7));
        addInfo = resultAsString(record, 8);
        startDate = resultAsLocalDate(record, 9);
        endDate = resultAsLocalDate(record, 10);
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public AutocompleteResult getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(AutocompleteResult curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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

}
