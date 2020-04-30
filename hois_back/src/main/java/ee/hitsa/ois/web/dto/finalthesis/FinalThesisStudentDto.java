package ee.hitsa.ois.web.dto.finalthesis;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalThesisStudentDto {

    private AutocompleteResult person;
    private AutocompleteResult curriculumVersion;
    private AutocompleteResult studentGroup;
    private Boolean isVocational;
    private Boolean isMagisterStudy;
    private Boolean isDoctoralStudy;
    private Boolean isIntegratedStudy;
    private List<AutocompleteResult> curriculumGrades;

    public FinalThesisStudentDto(Student student) {
        this.person = AutocompleteResult.of(student.getPerson());
        if (student.getCurriculumVersion() != null) {
            this.curriculumVersion = AutocompleteResult.of(student.getCurriculumVersion().getCurriculum());
            this.curriculumGrades = student.getCurriculumVersion().getCurriculum().getGrades().stream()
                    .map(AutocompleteResult::of).collect(Collectors.toList());
            this.isMagisterStudy = Boolean
                    .valueOf(CurriculumUtil.isMagisterStudy(student.getCurriculumVersion().getCurriculum()));
            this.isDoctoralStudy = Boolean
                    .valueOf(CurriculumUtil.isDoctoralStudy(student.getCurriculumVersion().getCurriculum()));
            this.isIntegratedStudy = Boolean
                    .valueOf(CurriculumUtil.isIntegratedStudy(student.getCurriculumVersion().getCurriculum()));
        }
        if (student.getStudentGroup() != null) {
            this.studentGroup = AutocompleteResult.of(student.getStudentGroup());
        }
        this.isVocational = Boolean.valueOf(StudentUtil.isVocational(student));
    }

    public AutocompleteResult getPerson() {
        return person;
    }

    public void setPerson(AutocompleteResult person) {
        this.person = person;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public Boolean getIsMagisterStudy() {
        return isMagisterStudy;
    }

    public void setIsMagisterStudy(Boolean isMagisterStudy) {
        this.isMagisterStudy = isMagisterStudy;
    }

    public Boolean getIsDoctoralStudy() {
        return isDoctoralStudy;
    }

    public void setIsDoctoralStudy(Boolean isDoctoralStudy) {
        this.isDoctoralStudy = isDoctoralStudy;
    }

    public Boolean getIsIntegratedStudy() {
        return isIntegratedStudy;
    }

    public void setIsIntegratedStudy(Boolean isIntegratedStudy) {
        this.isIntegratedStudy = isIntegratedStudy;
    }

    public List<AutocompleteResult> getCurriculumGrades() {
        return curriculumGrades;
    }

    public void setCurriculumGrades(List<AutocompleteResult> curriculumGrades) {
        this.curriculumGrades = curriculumGrades;
    }

}
