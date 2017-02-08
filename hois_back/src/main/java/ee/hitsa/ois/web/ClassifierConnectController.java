package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.service.ClassifierConnectService;
import ee.hitsa.ois.web.commandobject.ClassifierConnectSearchCommand;

@RestController
@RequestMapping("classifierConnect")
public class ClassifierConnectController {

    @Autowired ClassifierConnectService service;

    @GetMapping(value = "")
    public Page<ClassifierConnect> search(ClassifierConnectSearchCommand classifierConnectSearchCommand, Pageable pageable) {
        return service.search(classifierConnectSearchCommand, pageable);
    }

    /*
     * TODO: change ArrayList to Set and implement equals and hashCode at ClassifierConnect (or ClassifierConnectPK?)
     * Or @Id for both variables of composite primary key is enough?
     */
    @PostMapping(value = "/changeParents/{code}")
    public boolean changeListOfParents(@PathVariable("code") String code, @Valid @RequestBody List<Classifier> parents) {
        service.updateParents(code, parents);
        return true;
    }

    @GetMapping(value = "/all")
    @JsonView(JsonViews.Basic.class)
    public List<ClassifierConnect> searchAll(ClassifierConnectSearchCommand classifierConnectSearchCommand, Sort sort) {
        return service.searchAll(classifierConnectSearchCommand, sort);
    }
}
