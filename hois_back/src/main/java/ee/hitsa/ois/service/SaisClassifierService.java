package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import ee.hitsa.ois.domain.sais.SaisClassifier;
import ee.hitsa.ois.repository.SaisClassifierRepository;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisClassifierSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;

@Transactional
@Service
public class SaisClassifierService {

    @Autowired
    private SaisClassifierRepository saisClassifierRepository;
    @Autowired
    private EntityManager em;
    //@Autowired
    //private SaisService saisService;
    //@Autowired
    //private SaisLogService saisLogService;

    public Page<SaisClassifierSearchDto> search(String parentCode, SaisClassifierSearchCommand criteria,
            Pageable pageable) {
        return saisClassifierRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("parentCode"), parentCode));

            if (!StringUtils.isEmpty(criteria.getValue())) {
                filters.add(cb.equal(root.get("value"), criteria.getValue()));
            }

            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("nameEt"), cb, criteria.getName(), name::add);
            propertyContains(() -> root.get("nameEn"), cb, criteria.getName(), name::add);
            if (!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SaisClassifierSearchDto::of);
    }

    public Page<SaisClassifierSearchDto> list(SaisClassifierSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from sais_classifier sc")
                .sort(pageable);

        qb.filter("sc.parent_code is null");
        qb.optionalContains("sc.name_en or sc.name_et", "name", criteria.getName());

        String select = "sc.name_et, (select count(*) from sais_classifier sc2 where sc2.parent_code = sc.code)";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new SaisClassifierSearchDto(resultAsString(r, 0), resultAsLong(r, 1));
        });
    }

    @RequestMapping("/importClassifiers")
    public Page<SaisClassifierSearchDto> importClassifiers(SaisClassifierSearchCommand criteria, Pageable pageable) {
        /*XRoadHeader xRoadHeader = new XRoadHeader();

        // siia tuleb teha confi lugemine
        xRoadHeader.setConsumer("10239452");
        xRoadHeader.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");
        xRoadHeader.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        xRoadHeader.setProducer("sais2");
        xRoadHeader.setService("sais2.ClassificationsExport.v1");
        xRoadHeader.setUserId("EE30101010007");

        SaisClassificationResponse classificationResponse = saisService.classificationsExport(xRoadHeader);
        classificationResponse.setQueryStart(LocalDateTime.now());
        
        for (ClassificationTypeItem cTItem : classificationResponse.getClassificationsExport().getClassificationTypes()
                .getClassificationTypeItem()) {
            SaisClassifier scRoot = new SaisClassifier();
            scRoot.setCode(cTItem.getId());
            scRoot.setValue(cTItem.getName());
            scRoot.setNameEt(cTItem.getName());
            em.persist(scRoot);
            for (ClassificationItem cItem : cTItem.getClassifications().getClassificationItem()) {
                SaisClassifier sc = new SaisClassifier();
                sc.setCode(cItem.getId());
                sc.setParentCode(scRoot.getCode());
                sc.setValue(cItem.getValue());
                for (Kvp kvp : cItem.getTranslation().getKvp()) {
                    String name = kvp.getValue();
                    switch(kvp.getKey().toUpperCase()) {
                    case "ENGLISH":
                        sc.setNameEn(name);
                        break;
                    case "ESTONIAN":
                        sc.setNameEt(name);
                        break;
                    }
                }
                em.persist(sc);
            }
        }
        classificationResponse.setQueryEnd(LocalDateTime.now());
        //saisLogService.insertLog(classificationResponse);*/

        return list(criteria, pageable);
    }

}
