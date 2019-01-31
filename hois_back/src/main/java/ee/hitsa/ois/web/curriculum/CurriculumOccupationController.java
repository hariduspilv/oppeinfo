package ee.hitsa.ois.web.curriculum;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.curriculum.CurriculumOccupationService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.dto.ExpiringOccupationStandardDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationViewDto;

@RestController
@RequestMapping("curriculumOccupation")
public class CurriculumOccupationController {

    @Autowired 
    private CurriculumOccupationService curriculumOccupationService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private EntityManager em;

    @GetMapping("/expiringOccupationStandards")
    public List<ExpiringOccupationStandardDto> expiringOccupationStandards(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        return curriculumOccupationService.expiringOccupationStandards(user.getSchoolId());
    }

    @GetMapping("/{id:\\d+}")
    public CurriculumOccupationViewDto get(HoisUserDetails user, @WithEntity("id") CurriculumOccupation curriculumOccupation) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculumOccupation.getCurriculum());
        return CurriculumOccupationViewDto.of(curriculumOccupation);
    }

    @PostMapping
    public CurriculumOccupationViewDto create(HoisUserDetails user, 
            @NotNull @Valid @RequestBody CurriculumOccupationDto dto) {
        Curriculum curriculum = em.getReference(Curriculum.class, dto.getCurriculum());
        validate(user, curriculum);
        //CurriculumUtil.assertOccupationCanBeChanged(curriculum.getDraft());
        return get(user, curriculumOccupationService.create(user, dto));
    }

    @PutMapping("/{id:\\d+}")
    public CurriculumOccupationViewDto update(HoisUserDetails user, 
            @WithEntity("id") CurriculumOccupation curriculumOccupation, 
            @NotNull @Valid @RequestBody CurriculumOccupationDto dto) {
        validate(user, curriculumOccupation.getCurriculum());
        return get(user, curriculumOccupationService.update(user, dto, curriculumOccupation));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user, @WithEntity("id") CurriculumOccupation curriculumOccupation) {
        validate(user, curriculumOccupation.getCurriculum());
        //CurriculumUtil.assertOccupationCanBeDeleted(curriculumOccupation);
        curriculumOccupationService.delete(user, curriculumOccupation);
    }

    @GetMapping("/curriculum/{id:\\d+}")
    public CurriculumDto get(HoisUserDetails user, @WithEntity("id") Curriculum curriculum) {
        CurriculumUtil.assertCanView(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        return CurriculumDto.afterDeletingOccupation(curriculum);
    }

    private void validate(HoisUserDetails user, Curriculum curriculum) {
        AssertionFailedException.throwIf(!CurriculumUtil.isVocational(curriculum), 
                "only vocational curricula have occupations");
        CurriculumUtil.assertCanChange(user, schoolService.getEhisSchool(user.getSchoolId()), curriculum);
        CurriculumUtil.assertBasicDataCanBeEdited(curriculum);
    }
}
