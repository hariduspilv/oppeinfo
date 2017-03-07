package ee.hitsa.ois.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.ClassifierWithCount;

@RestController
@RequestMapping("classifier")
public class ClassifierController {

    @Autowired
    private ClassifierService classifierService;

    /**
     * For creating new classifier
     */
    @PostMapping("")
    public Classifier create(@Valid @RequestBody Classifier classifier) {
        return classifierService.save(classifier);
    }

    /**
     * For updating existing classifier
     */
    @PutMapping("/{code}")
    public Classifier update(@WithEntity("code") Classifier classifier, @Valid @RequestBody Classifier newClassifier) {
        EntityUtil.bindToEntity(newClassifier, classifier);
        return classifierService.save(classifier);
    }

    /**
     * Getting single classifier by code
     */
    @GetMapping("/{code}")
    public Classifier get(@WithEntity("code") Classifier classifier) {
        return classifier;
    }

    /**
     * Getting classifiers as paginated results
     */
    @GetMapping("")
    public Page<ClassifierSelection> search(ClassifierSearchCommand classifierSearchCommand, Pageable pageable) {
        return classifierService.search(classifierSearchCommand, pageable);
    }

    @GetMapping("/all")
    public List<ClassifierSelection> searchAll(ClassifierSearchCommand classifierSearchCommand, Sort sort) {
        return classifierService.searchAll(classifierSearchCommand, sort)
                .stream().map(ClassifierSelection::of).collect(Collectors.toList());
    }

    @GetMapping("/heads")
    public Page<ClassifierWithCount> searchTables(ClassifierSearchCommand classifierSearchCommand, Pageable pageable) {
        return classifierService.searchTables(classifierSearchCommand, pageable);
    }

    @GetMapping("/getPossibleParentClassifiers")
    public List<Classifier> searchForAuto(ClassifierSearchCommand classifierSearchCommand) {
        return classifierService.searchForAutocomplete(classifierSearchCommand);
    }

    /**
     * For deleting classifier
     */
    @DeleteMapping("/{code}")
    public boolean delete(@PathVariable("code") String code) {
        classifierService.delete(code);
        return true;
    }

    @GetMapping("/connections/{code}")
    public List<Classifier> getPossibleConnections(@PathVariable("code") String code){
        return classifierService.getPossibleConnections(code);
    }

    @GetMapping("/parents/{code}")
    public List<Classifier> getParents(@PathVariable("code") String code) {
        return classifierService.getParents(code);
    }

    @GetMapping("/parents/{parentsMainClassifierCode}/{code}")
    public List<Classifier> getParentsByMainClassifier(@PathVariable("parentsMainClassifierCode") String parentsMainClassifierCode, @PathVariable("code") String code) {
        return classifierService.getParentsByMainClassifier(code, parentsMainClassifierCode);
    }

    @GetMapping("/children/{code}")
    public List<Classifier> getChildren(@PathVariable("code") String code) {
        return classifierService.findChildren(code);
    }
}
