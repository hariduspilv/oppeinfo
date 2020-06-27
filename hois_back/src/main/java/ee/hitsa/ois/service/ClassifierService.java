package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.EntityUtil.propertyName;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
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

    public Classifier save(Classifier classifier) {
        return classifierRepository.save(classifier);
    }

    public Page<ClassifierWithCount> searchTables(ClassifierSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c").sort(pageable);

        qb.filter("c.main_class_code is null");
        qb.optionalContains(Language.EN.equals(criteria.getLang()) ? "c.name_en" : "c.name_et", "name", criteria.getName());

        String select = "c.code, c.name_et, c.name_en, c.name_ru, (select count(*) from classifier c2 where c2.main_class_code = c.code)";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new ClassifierWithCount(resultAsString(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsLong(r, 4));
        });
    }

    public Page<ClassifierSearchDto> search(ClassifierSearchCommand cmd, Pageable pageable) {
        JpaQueryBuilder<Classifier> qb = new JpaQueryBuilder<>(Classifier.class, "c").sort(pageable);

        // FIXME clarify which ones are used in frontend
        qb.optionalCriteria("c.code = :code", "code", cmd.getCode());
        qb.optionalContains("c.value", "value", cmd.getValue());
        qb.optionalCriteria("c.higher = :higher", "higher", cmd.isHigher());
        qb.optionalCriteria("c.vocational = :vocational", "vocational", cmd.isVocational());

        //This must be exact equal for dropdown's
        qb.optionalCriteria("c.mainClassCode = :mainClassCode", "mainClassCode", cmd.getMainClassCode());
        qb.optionalCriteria("c.mainClassCode in (:mainClassCodes)", "mainClassCodes", cmd.getMainClassCodes());
        qb.optionalContains("c." + propertyName("name", cmd.getLang()), "name", cmd.getName());

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(ClassifierSearchDto::of);
    }

    public void delete(String code) {
        EntityUtil.deleteEntity(em.getReference(Classifier.class, code), em);
    }

    public List<Classifier> findAllByMainClassCode(MainClassCode mainClassCode) {
        return em.createQuery("select c from Classifier c where c.mainClassCode = ?1", Classifier.class)
                .setParameter(1, mainClassCode.name()).getResultList();
    }

    public List<Classifier> findParents(String code) {
        return classifierRepository.findParents(code);
    }

    public List<Classifier> findParentsByMainClassifier(String code, String parentsMainClassifierCode) {
        return classifierRepository.findParentsByMainClassifier(code, parentsMainClassifierCode);
    }

    public List<Classifier> findChildren(String code) {
        return classifierRepository.findChildren(code);
    }

    public List<Classifier> getPossibleConnections(String code) {
        List<String> requiredCodes = POSSIBLE_CONNECTIONS.get(code);
        return StreamUtil.toMappedList(classifierRepository::getOne, requiredCodes);
    }

    private static final Map<String, List<String>> POSSIBLE_CONNECTIONS = new HashMap<>();
    static {
        POSSIBLE_CONNECTIONS.put("OPPEASTE", Arrays.asList("HARIDUSTASE", "EKR"));
        POSSIBLE_CONNECTIONS.put("KUTSE", Arrays.asList("EKR"));
        POSSIBLE_CONNECTIONS.put("OSAKUTSE", Arrays.asList("KUTSE"));
        POSSIBLE_CONNECTIONS.put("SPETSKUTSE", Arrays.asList("KUTSE"));
        POSSIBLE_CONNECTIONS.put("ISCED_RYHM", Arrays.asList("ISCED_SUUN","OPPEKAVAGRUPP"));
        POSSIBLE_CONNECTIONS.put("ISCED_SUUN", Arrays.asList("ISCED_VALD"));
        POSSIBLE_CONNECTIONS.put("KOMPETENTS", Arrays.asList("OSAKUTSE", "SPETSKUTSE", "KUTSE"));
        POSSIBLE_CONNECTIONS.put("EHAK", Arrays.asList("EHAK"));
    }
}
