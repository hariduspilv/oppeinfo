package ee.hitsa.ois.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.PracticeEvaluationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.practice.PracticeEvaluationForm;
import ee.hitsa.ois.web.commandobject.practice.PracticeEvaluationSearchCommand;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationDto;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationSearchDto;

@RestController
@RequestMapping("/practiceEvaluation")
public class PracticeEvaluationController {

    @Autowired
    private PracticeEvaluationService practiceEvaluationService;

    @GetMapping("/{id:\\d+}")
    public PracticeEvaluationDto get(HoisUserDetails user, @WithEntity PracticeEvaluation practiceEvaluation) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, practiceEvaluation.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_PRHINDAMISVORM);
        return practiceEvaluationService.get(practiceEvaluation);
    }

    @GetMapping
    public Page<PracticeEvaluationSearchDto> search(@Valid PracticeEvaluationSearchCommand command, Pageable pageable, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PRHINDAMISVORM);
        return practiceEvaluationService.search(user, command, pageable);
    }

    @PostMapping
    public PracticeEvaluationDto create(@Valid @RequestBody PracticeEvaluationForm form, HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PRHINDAMISVORM);
        return get(user, practiceEvaluationService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public PracticeEvaluationDto save(HoisUserDetails user, @WithVersionedEntity(versionRequestBody = true) PracticeEvaluation practiceEvaluation, 
            @Valid @RequestBody PracticeEvaluationForm form) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, practiceEvaluation.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_PRHINDAMISVORM);
        return get(user, practiceEvaluationService.save(user, practiceEvaluation, form));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithVersionedEntity(versionRequestParam = "version") PracticeEvaluation practiceEvaluation, 
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, practiceEvaluation.getSchool(), Permission.OIGUS_M,
                PermissionObject.TEEMAOIGUS_PRHINDAMISVORM);
        practiceEvaluationService.delete(user, practiceEvaluation);
    }

}
