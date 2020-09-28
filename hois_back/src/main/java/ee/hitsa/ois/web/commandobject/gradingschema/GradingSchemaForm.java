package ee.hitsa.ois.web.commandobject.gradingschema;

import ee.hitsa.ois.web.commandobject.VersionedCommand;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class GradingSchemaForm extends VersionedCommand {

    private Boolean isVocational;
    private Boolean isHigher;
    private Boolean isBasic;
    private Boolean isSecondary;
    private Boolean isVerbal;
    private Boolean isGrade;

    @NotEmpty
    private List<Long> studyYears;
    private List<GradingSchemaRowForm> gradingSchemaRows;

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Boolean getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(Boolean isBasic) {
        this.isBasic = isBasic;
    }

    public Boolean getIsSecondary() {
        return isSecondary;
    }

    public void setIsSecondary(Boolean isSecondary) {
        this.isSecondary = isSecondary;
    }

    public Boolean getIsVerbal() {
        return isVerbal;
    }

    public void setIsVerbal(Boolean isVerbal) {
        this.isVerbal = isVerbal;
    }

    public Boolean getIsGrade() {
        return isGrade;
    }

    public void setIsGrade(Boolean isGrade) {
        this.isGrade = isGrade;
    }

    public List<Long> getStudyYears() {
        return studyYears != null ? studyYears : (studyYears = new ArrayList<>());
    }

    public void setStudyYears(List<Long> studyYears) {
        this.studyYears = studyYears;
    }

    public List<GradingSchemaRowForm> getGradingSchemaRows() {
        return gradingSchemaRows != null ? gradingSchemaRows : (gradingSchemaRows = new ArrayList<>());
    }

    public void setGradingSchemaRows(List<GradingSchemaRowForm> gradingSchemaRows) {
        this.gradingSchemaRows = gradingSchemaRows;
    }
}
