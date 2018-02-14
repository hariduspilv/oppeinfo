package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.PublicDataService;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;

@RestController
@RequestMapping("/public")
public class PublicDataController {

    @Autowired
    private PublicDataService publicDataService;

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

}
