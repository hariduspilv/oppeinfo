package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.ClassifierConnectRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.specification.ClassifierSpecification;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierWithCount;

@Transactional
@Service
public class ClassifierService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private ClassifierConnectRepository classifierConnectRepository;

    @CacheEvict(cacheNames = "classifier", allEntries = true)
    public void evictCache() {
    }

    public Classifier save(Classifier classifier) {
        return classifierRepository.save(classifier);
    }

    public List<Classifier> findByCode(String code) {
        return classifierRepository.findByCode(code);
    }

    public Classifier findOne(String code) {
        return classifierRepository.findOne(code);
    }

    public Page<ClassifierWithCount> searchTables(ClassifierSearchCommand classifierSearchCommand, Pageable pageable) {
        String name = classifierSearchCommand.getName();
        if(name == null) {
            name = "";
        }

        Page<ClassifierWithCount> result = null;
        if(Language.EN.equals(classifierSearchCommand.getLang())) {
            result = classifierRepository.findAllByMainClassCodeIsNullAndNameEnContainingIgnoreCase(name, pageable);
        } else {
            result = classifierRepository.findAllByMainClassCodeIsNullAndNameEtContainingIgnoreCase(name, pageable);
        }
        return result;
    }

    public Page<Classifier> search(ClassifierSearchCommand classifierSearchCommand, Pageable pageable) {
        return classifierRepository.findAll(new ClassifierSpecification(classifierSearchCommand), pageable);
    }

    public List<Classifier> searchAll(ClassifierSearchCommand classifierSearchCommand) {
        return classifierRepository.findAll(new ClassifierSpecification(classifierSearchCommand));
    }

    public List<Classifier> searchAll(ClassifierSearchCommand classifierSearchCommand, Sort sort) {
        return classifierRepository.findAll(new ClassifierSpecification(classifierSearchCommand), sort);
    }

    // TODO move into AutocompleteService
    public List<Classifier> searchForAutocomplete(ClassifierSearchCommand classifierSearchCommand) {
        List<Classifier> result = null;

        String name = classifierSearchCommand.getName();
        String mainClassCode = classifierSearchCommand.getMainClassCode();
        if(Language.EN.equals(classifierSearchCommand.getLang())) {
            result = classifierRepository.findTop20ByNameEnStartingWithIgnoreCaseAndMainClassCodeOrderByNameEnAsc(name, mainClassCode);
        } else {
            result = classifierRepository.findTop20ByNameEtStartingWithIgnoreCaseAndMainClassCodeOrderByNameEtAsc(name, mainClassCode);
        }

        return result;
    }

    //TODO: for some reason cannot order by nameEt, error in front end occurs
    public List<Classifier> searchForDropdown(ClassifierSearchCommand classifierSearchCommand) {
        return classifierRepository.findAllByMainClassCodeOrderByCode(classifierSearchCommand.getMainClassCode());
    }

    public void delete(String code) {
        deleteConnections(code);
        classifierRepository.removeByCode(code);
    }

    public void deleteConnections(String code) {
        classifierConnectRepository.removeAllByClassifierCodeOrConnectClassifierCode(code, code);
    }

    public List<Classifier> getParents(String code) {
        return classifierRepository.findParents(code);
    }

    public List<Classifier> getParentsByMainClassifier(String code, String parentsMainClassifierCode) {
        return classifierRepository.findParentsByMainClassifier(code, parentsMainClassifierCode);
    }

    public List<Classifier> findChildren(String code) {
        return classifierRepository.findChildren(code);
    }

    public List<Classifier> getPossibleConnections(String code) {
        Map<String, List<String>> possibleConnections = new HashMap<>();
        possibleConnections.put("OPPEASTE", Arrays.asList("HARIDUSTASE", "EKR"));
        possibleConnections.put("KUTSE", Arrays.asList("EKR"));
        possibleConnections.put("OSAKUTSE", Arrays.asList("KUTSE"));
        possibleConnections.put("SPETSKUTSE", Arrays.asList("KUTSE"));
        possibleConnections.put("ISCED_RYHM", Arrays.asList("ISCED_SUUN","OPPEKAVAGRUPP"));
        possibleConnections.put("ISCED_SUUN", Arrays.asList("ISCED_VALD"));
        possibleConnections.put("KOMPETENTS", Arrays.asList("OSAKUTSE", "SPETSKUTSE", "KUTSE"));

        List<String> requiredCodes = possibleConnections.get(code);

        if(requiredCodes == null) {
            return new ArrayList<>();
        }

        return requiredCodes.stream().map(this::findOne).collect(Collectors.toList());
    }
}
