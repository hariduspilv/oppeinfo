package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

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
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.ClassifierSearchDto;
import ee.hitsa.ois.web.dto.ClassifierWithCount;

@RestController
@RequestMapping("classifier")
public class ClassifierController {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private ClassifierService classifierService;

    /**
     * For creating new classifier
     */
    @PostMapping
    public ClassifierDto create(HoisUserDetails user, @Valid @RequestBody Classifier classifier) {
        UserUtil.assertIsMainAdmin(user);
        return get(classifierService.save(classifier));
    }

    /**
     * For updating existing classifier
     */
    @PutMapping("/{code}")
    public ClassifierDto update(HoisUserDetails user, @WithEntity("code") Classifier classifier, @Valid @RequestBody Classifier newClassifier) {
        UserUtil.assertIsMainAdmin(user);
        EntityUtil.bindToEntity(newClassifier, classifier);
        return get(classifierService.save(classifier));
    }

    /**
     * Getting single classifier by code
     */
    @GetMapping("/{code}")
    public ClassifierDto get(@WithEntity("code") Classifier classifier) {
        return ClassifierDto.of(classifier);
    }

    /**
     * Getting classifiers as paginated results
     */
    @GetMapping
    public Page<ClassifierSearchDto> search(ClassifierSearchCommand classifierSearchCommand, Pageable pageable) {
        // FIXME: add security constraints
        return classifierService.search(classifierSearchCommand, pageable);
    }

    @GetMapping("/heads")
    public Page<ClassifierWithCount> searchTables(ClassifierSearchCommand classifierSearchCommand, Pageable pageable) {
        return classifierService.searchTables(classifierSearchCommand, pageable);
    }

    // TODO move into AutocompleteController
    @GetMapping("/getPossibleParentClassifiers")
    public List<ClassifierDto> searchForAuto(ClassifierSearchCommand classifierSearchCommand) {
        return StreamUtil.toMappedList(ClassifierDto::of, autocompleteService.classifierForAutocomplete(classifierSearchCommand));
    }

    /**
     * For deleting classifier
     */
    @DeleteMapping("/{code}")
    public boolean delete(HoisUserDetails user, @PathVariable("code") String code) {
        UserUtil.assertIsMainAdmin(user);
        classifierService.delete(code);
        return true;
    }

    @GetMapping("/connections/{code}")
    public List<ClassifierDto> getPossibleConnections(@PathVariable("code") String code) {
        return StreamUtil.toMappedList(ClassifierDto::of, classifierService.getPossibleConnections(code));
    }

    @GetMapping("/parents/{code}")
    public List<ClassifierDto> getParents(@PathVariable("code") String code) {
        return StreamUtil.toMappedList(ClassifierDto::of, classifierService.getParents(code));
    }

    @GetMapping("/parents/{parentsMainClassifierCode}/{code}")
    public List<ClassifierDto> getParentsByMainClassifier(
            @PathVariable("parentsMainClassifierCode") String parentsMainClassifierCode,
            @PathVariable("code") String code) {
        return StreamUtil.toMappedList(ClassifierDto::of, classifierService.getParentsByMainClassifier(code, parentsMainClassifierCode));
    }

    @GetMapping("/children/{code}")
    public List<ClassifierDto> getChildren(@PathVariable("code") String code) {
        return StreamUtil.toMappedList(ClassifierDto::of, classifierService.findChildren(code));
    }
}
