package ee.hitsa.ois.web;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumForSearch;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

	@Autowired
	CurriculumService curriculumService;

	@Autowired
    ClassifierService classifierService;

	@Autowired
    SchoolService schoolService;

    @GetMapping(value = "/{id}")
    public Curriculum get(@WithEntity("id") Curriculum curriculum) {
        return curriculum;
    }

	/**
	 * TODO: if curriculumSearchCommand or pageable is null,
	 * there is going to be exception
	 */
    @GetMapping(value = "")
    public Page<CurriculumForSearch> search(CurriculumSearchCommand curriculumSearchCommand, Pageable pageable) {
        return curriculumService.search(curriculumSearchCommand, pageable);
    }

    @GetMapping(value = "/departments")
    public Iterable<CurriculumDepartment> getAllDepartments() {
        return curriculumService.findAllDepartments();
    }

    @PostMapping(value = "")
    public Curriculum create(HoisUserDetails user, @RequestBody CurriculumForm curriculumForm) {
        Curriculum curriculum = EntityUtil.bindToEntity(curriculumForm, new Curriculum());
        curriculum.setSchool(user.getSchool());
        return curriculumService.save(curriculum, curriculumForm);
    }

    @PutMapping(value = "/{id}")
    public Curriculum update(@NotNull @RequestBody CurriculumForm curriculumForm, @WithEntity("id") Curriculum curriculum) {
    	Curriculum editedCurriculum = EntityUtil.bindToEntity(curriculumForm, curriculum);
        return curriculumService.save(editedCurriculum, curriculumForm);
    }

    @GetMapping(value = "/unique")
    public boolean isUnique(HoisUserDetails user, UniqueCommand command) {
		return curriculumService.isUnique(user.getSchool(), command);
    }
    
    @DeleteMapping(value = "/{id}")
    public void delete(@WithEntity("id") Curriculum curriculum) {
    	curriculumService.delete(curriculum);
    }
    
    // TODO: find proper names for this and subsequent methods
    @GetMapping(value = "/areasOfStudyByGroupOfStudy/{code}")
    public List<Classifier> getAreasOfStudyByGroupOfStudy(@NotNull @PathVariable("code") String code) {
        return curriculumService.getAreasOfStudyByGroupOfStudy(code);
    }
}
