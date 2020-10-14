package ee.hitsa.ois.web.dto.gradingSchema;

import ee.hitsa.ois.domain.gradingschema.GradingSchema;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.gradingschema.GradingSchemaForm;

import java.time.LocalDate;

public class GradingSchemaDto extends GradingSchemaForm {

    private Long id;
    private LocalDate inserted;

    public static GradingSchemaDto of(GradingSchema gradingSchema) {
        GradingSchemaDto dto = EntityUtil.bindToDto(gradingSchema, new GradingSchemaDto(),
                "studyYears", "gradingSchemaRows");
        dto.setInserted(gradingSchema.getInserted().toLocalDate());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInserted() {
        return inserted;
    }

    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
    }
}
