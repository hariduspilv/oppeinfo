package ee.hitsa.ois.web.dto.report;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentCountDto {
    
    /** StudyLevel, student group, age group, curricula or course (not used when result type is overall) */
    private AutocompleteResult object;
    /** Used only when per status is chosen (sum of male and female of that type) */
    private Long studying;
    private Long academic;
    private Long foreign;
    /** Used only when per sex is chosen (sum of male and female of that type) */
    private Long male;
    private Long female;
    /** Used only when per sex and per status is chosen */
    private Long studyingMale;
    private Long studyingFemale;
    private Long academicMale;
    private Long academicFemale;
    private Long foreignMale;
    private Long foreignFemale;
    /** Used always */
    private Long sum;

    public StudentCountDto() {}

    public AutocompleteResult getObject() {
        return object;
    }

    public void setObject(AutocompleteResult object) {
        this.object = object;
    }

    public Long getStudying() {
        return studying;
    }

    public void setStudying(Long studying) {
        this.studying = studying;
    }

    public Long getStudyingMale() {
        return studyingMale;
    }

    public void setStudyingMale(Long studyingMale) {
        this.studyingMale = studyingMale;
    }

    public Long getStudyingFemale() {
        return studyingFemale;
    }

    public void setStudyingFemale(Long studyingFemale) {
        this.studyingFemale = studyingFemale;
    }

    public Long getAcademic() {
        return academic;
    }

    public void setAcademic(Long academic) {
        this.academic = academic;
    }

    public Long getAcademicMale() {
        return academicMale;
    }

    public void setAcademicMale(Long academicMale) {
        this.academicMale = academicMale;
    }

    public Long getAcademicFemale() {
        return academicFemale;
    }

    public void setAcademicFemale(Long academicFemale) {
        this.academicFemale = academicFemale;
    }

    public Long getForeign() {
        return foreign;
    }

    public void setForeign(Long foreign) {
        this.foreign = foreign;
    }

    public Long getForeignMale() {
        return foreignMale;
    }

    public void setForeignMale(Long foreignMale) {
        this.foreignMale = foreignMale;
    }

    public Long getForeignFemale() {
        return foreignFemale;
    }

    public void setForeignFemale(Long foreignFemale) {
        this.foreignFemale = foreignFemale;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Long getMale() {
        return male;
    }

    public void setMale(Long male) {
        this.male = male;
    }

    public Long getFemale() {
        return female;
    }

    public void setFemale(Long female) {
        this.female = female;
    }

}
