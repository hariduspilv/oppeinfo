package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.EntityUtil.propertyName;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.ClassifierConnectRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSearchDto;
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

    public Classifier save(Classifier classifier) {
        return classifierRepository.save(classifier);
    }

    public Page<ClassifierWithCount> searchTables(ClassifierSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from classifier c").sort(pageable);

        qb.filter("c.main_class_code is null");
        qb.optionalContains(Language.EN.equals(criteria.getLang()) ? "c.name_en" : "c.name_et", "name", criteria.getName());

        String select = "c.code, c.name_et, c.name_en, c.name_ru, (select count(*) from classifier c2 where c2.main_class_code = c.code)";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new ClassifierWithCount(resultAsString(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsLong(r, 4));
        });
    }

    public Page<ClassifierSearchDto> search(ClassifierSearchCommand cmd, Pageable pageable) {
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
        }, pageable).map(ClassifierSearchDto::of);
    }

    public void delete(String code) {
        EntityUtil.deleteEntity(classifierRepository, classifierRepository.getOne(code));
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
