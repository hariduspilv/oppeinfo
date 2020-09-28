package ee.hitsa.ois.web.dto.gradingSchema;

import ee.hitsa.ois.domain.gradingschema.GradingSchema;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.gradingschema.GradingSchemaForm;

public class GradingSchemaDto extends GradingSchemaForm {

    private Long id;
    private Boolean isValid;

    public static GradingSchemaDto of(GradingSchema gradingSchema) {
        return EntityUtil.bindToDto(gradingSchema, new GradingSchemaDto(),
                "studyYears", "gradingSchemaRows");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
