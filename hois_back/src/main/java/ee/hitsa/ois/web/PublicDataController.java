package ee.hitsa.ois.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.PublicDataService;

@RestController
@RequestMapping("/public")
public class PublicDataController {

    @Autowired
    private PublicDataService publicDataService;

    @GetMapping("/curriculum/{id:\\d+}")
    public Map<String, ?> curriculum(@PathVariable("id") Long id) {
        return publicDataService.curriculum(id);
    }

    @GetMapping("/curriculum/{curriculum:\\d+}/{id:\\d+}")
    public Map<String, ?> curriculumVersion(@PathVariable("curriculum") Long curriculum, @PathVariable("id") Long id) {
        return publicDataService.curriculumVersion(curriculum, id);
    }
}
