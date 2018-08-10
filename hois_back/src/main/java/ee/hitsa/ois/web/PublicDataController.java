package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.PublicDataService;
import ee.hitsa.ois.service.StateCurriculumService;
import ee.hitsa.ois.service.StudyMaterialService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.dto.SchoolDepartmentResult;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodDto;

@RestController
@RequestMapping("/public")
public class PublicDataController {

    @Autowired
    private PublicDataService publicDataService;
    @Autowired
    private StudyMaterialService studyMaterialService;
    @Autowired
    private StateCurriculumService stateCurriculumService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/curriculum/{id:\\d+}")
    public Object curriculum(@PathVariable("id") Long id) {
        return publicDataService.curriculum(id);
    }

    @GetMapping("/curriculum/{curriculum:\\d+}/{id:\\d+}")
    public Object curriculumVersion(@PathVariable("curriculum") Long curriculum, @PathVariable("id") Long id) {
        return publicDataService.curriculumVersion(curriculum, id);
    }

    @GetMapping("/subject/{id:\\d+}")
    public Object subject(@PathVariable("id") Long id) {
        return publicDataService.subject(id);
    }

    @GetMapping("/curriculumsearch")
    public Page<CurriculumSearchDto> curriculumSearch(CurriculumSearchCommand curriculumSearchCommand,
            Pageable pageable) {
        return publicDataService.curriculumSearch(curriculumSearchCommand, pageable);
    }

    @GetMapping("/statecurriculumsearch")
    public Page<StateCurriculumSearchDto> stateCurriculumSearch(StateCurriculumSearchCommand curriculumSearchCommand,
            Pageable pageable) {
        return stateCurriculumService.search(null, curriculumSearchCommand, pageable);
    }

    @GetMapping("/statecurriculum/{id:\\d+}")
    public StateCurriculumDto stateCurriculum(@WithEntity StateCurriculum stateCurriculum) {
        return publicDataService.stateCurriculum(stateCurriculum);
    }

    @GetMapping("/schooldepartments")
    public List<SchoolDepartmentResult> schoolDepartments(Long schoolId) {
        return autocompleteService.schoolDepartments(schoolId);
    }

    @GetMapping("/curriculumversions")
    public List<CurriculumVersionResult> curriculumVersions(Long schoolId) {
        CurriculumVersionAutocompleteCommand lookup = new CurriculumVersionAutocompleteCommand();
        lookup.setHigher(Boolean.TRUE);
        lookup.setValid(Boolean.TRUE);
        return autocompleteService.curriculumVersions(schoolId, lookup);
    }
    
    @GetMapping("/subjectsearch")
    public Page<SubjectSearchDto> search(@Valid SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        return subjectService.search(null, subjectSearchCommand, pageable);
    }

    @GetMapping("/subject/view/{id:\\d+}")
    public SubjectDto subjectView(@WithEntity Subject subject) {
        return publicDataService.subjectView(subject);
    }
    
    @GetMapping("/studyMaterial/journal/{id:\\d+}")
    public JournalDto journal(@WithEntity Journal journal) {
        return studyMaterialService.getJournal(journal);
    }

    @GetMapping("/studyMaterial/journal/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> journalMaterials(@WithEntity Journal journal) {
        return studyMaterialService.materials(null, EntityUtil.getId(journal), Boolean.TRUE, null);
    }

    @GetMapping("/studyMaterial/subjectStudyPeriod/{id:\\d+}")
    public SubjectStudyPeriodDto subjectStudyPeriod(@WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        return studyMaterialService.getSubjectStudyPeriod(subjectStudyPeriod);
    }

    @GetMapping("/studyMaterial/subjectStudyPeriod/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> subjectStudyPeriodMaterials(@WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        return studyMaterialService.materials(EntityUtil.getId(subjectStudyPeriod), null, Boolean.TRUE, null);
    }

}
