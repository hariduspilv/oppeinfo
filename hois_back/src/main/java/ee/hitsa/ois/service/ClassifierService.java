package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.EntityUtil.propertyName;
import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.ClassifierConnectRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.ClassifierWithCount;

@Transactional
@Service
public class ClassifierService {

    @Autowired
    private EntityManager em;
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

    @SuppressWarnings("unchecked")
    public Page<ClassifierWithCount> searchTables(ClassifierSearchCommand criteria, Pageable pageable) {
        return JpaQueryUtil.query(ClassifierWithCount.class, Classifier.class, (root, query, cb) -> {
            ((CriteriaQuery<ClassifierWithCount>)query).select(cb.construct(ClassifierWithCount.class, root.get("code"), root.get("nameEt"), root.get("nameEn"), root.get("mainClassCode"), root.get("nameRu"), cb.count(root.join("children").get("code")), root.get("value")));
            query.groupBy(root.get("code"), root.get("nameEt"), root.get("nameEn"), root.get("nameRu"), root.get("mainClassCode"));

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.isNull(root.get("mainClassCode")));
            String nameField = Language.EN.equals(criteria.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, criteria.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);
    }

    public Page<ClassifierSelection> search(ClassifierSearchCommand cmd, Pageable pageable) {
        return classifierRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if(StringUtils.hasText(cmd.getCode())) {
                filters.add(cb.equal(root.get("code"), cmd.getCode()));
            }

            propertyContains(() -> root.get("value"), cb, cmd.getValue(), filters::add);

            if(cmd.isHigher() != null) {
                filters.add(cb.equal(root.get("higher"), cmd.isHigher()));
            }

            if(cmd.isVocational() != null) {
                filters.add(cb.equal(root.get("vocational"), cmd.isVocational()));
            }

            //This must be exact equal for dropdown's
            if(cmd.getMainClassCode() != null) {
                filters.add(cb.equal(root.get("mainClassCode"), cmd.getMainClassCode()));
            }

            if(cmd.getMainClassCodes() != null) {
                filters.add(root.get("mainClassCode").in(cmd.getMainClassCodes()));
            }

            propertyContains(() -> root.get(propertyName("name", cmd.getLang())), cb, cmd.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(ClassifierSelection::of);
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
        return StreamUtil.toMappedList(classifierRepository::findOne, requiredCodes);
    }
}
