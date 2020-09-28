package ee.hitsa.ois.web.dto.gradingSchema;

import ee.hitsa.ois.web.commandobject.gradingschema.GradingSchemaRowForm;

public class GradingSchemaRowDto extends GradingSchemaRowForm {

    private Boolean inUse;

    public GradingSchemaRowDto(Long id, String grade, String gradeEn, String gradeReal, Boolean isValid, Boolean inUse) {
        super(id, grade, gradeEn, gradeReal, isValid);
        this.inUse = inUse;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }
}
