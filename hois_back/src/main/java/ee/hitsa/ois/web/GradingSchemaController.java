package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.gradingschema.GradingSchema;
import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.service.GradingSchemaService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.GradingSchemaUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.gradingschema.GradingSchemaForm;
import ee.hitsa.ois.web.dto.gradingSchema.GradingSchemaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gradingSchema")
public class GradingSchemaController {

    @Autowired
    private GradingSchemaService gradingSchemaService;

    @GetMapping("/{id:\\d+}")
    public GradingSchemaDto get(HoisUserDetails user, @WithEntity GradingSchema gradingSchema) {
        UserUtil.throwAccessDeniedIf(!GradingSchemaUtil.canView(user, gradingSchema));
        return gradingSchemaService.get(gradingSchema);
    }

    @GetMapping("/typeSchemas")
    public List<GradingSchemaDto> typeSchemas(HoisUserDetails user, @RequestParam String type) {
        // used for grade selections and therefore can't have and won't need user rights check
        return gradingSchemaService.typeSchemas(user, type);
    }

    @PostMapping
    public GradingSchemaDto create(HoisUserDetails user, @Valid @RequestBody GradingSchemaForm form) {
        UserUtil.throwAccessDeniedIf(!GradingSchemaUtil.canCreate(user));
        return get(user, gradingSchemaService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public GradingSchemaDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) GradingSchema gradingSchema,
            @Valid @RequestBody GradingSchemaForm form) {
        UserUtil.throwAccessDeniedIf(!GradingSchemaUtil.canEdit(user, gradingSchema));
        return get(user, gradingSchemaService.save(gradingSchema, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") GradingSchema gradingSchema,
           @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.throwAccessDeniedIf(!GradingSchemaUtil.canEdit(user, gradingSchema));
        gradingSchemaService.delete(user, gradingSchema);
    }

    @DeleteMapping("/{schemaId:\\d+}/row/{id:\\d+}")
    public void deleteSchemaRow(HoisUserDetails user, @WithEntity("schemaId") GradingSchema gradingSchema,
            @WithEntity GradingSchemaRow row) {
        UserUtil.throwAccessDeniedIf(!GradingSchemaUtil.canEdit(user, gradingSchema));
        gradingSchemaService.deleteSchemaRow(user, row);
    }
}
