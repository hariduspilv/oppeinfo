package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.config.SaisProperties;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.sais.SaisClassifier;
import ee.hitsa.ois.repository.SaisClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisClassifierSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.helpers.sais.SaisClassificationResponse;
import ee.hois.xroad.sais2.generated.ClassificationTypeItem;
import ee.hois.xroad.sais2.generated.ClassificationItem;
import ee.hois.xroad.sais2.generated.Kvp;
import ee.hois.xroad.sais2.service.SaisService;

@Transactional
@Service
public class SaisClassifierService {

    @Autowired
    private SaisClassifierRepository saisClassifierRepository;
    @Autowired
    private EntityManager em;
    private SaisService saisService = new SaisService();
    @Autowired
    private SaisProperties sp;
    @Autowired
    private SaisLogService saisLogService;

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
        qb.optionalContains("sc.name_et", "name", criteria.getName());

        String select = "sc.code, sc.name_et, (select count(*) from sais_classifier sc2 where sc2.parent_code = sc.code)";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new SaisClassifierSearchDto(resultAsString(r, 0), resultAsString(r, 1), resultAsLong(r, 2));
        });
    }

    public Page<SaisClassifierSearchDto> importClassifiers(SaisClassifierSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        XRoadHeader xRoadHeader = new XRoadHeader();

        xRoadHeader.setConsumer(sp.getConsumer());
        xRoadHeader.setEndpoint(sp.getEndpoint());
        xRoadHeader.setProducer(sp.getProducer());
        xRoadHeader.setUserId(sp.getUseridprefix() + em.getReference(Person.class, user.getPersonId()).getIdcode());
        xRoadHeader.setId(UUID.randomUUID().toString());
        xRoadHeader.setService("sais2.ClassificationsExport.v1");
        SaisClassificationResponse classificationResponse = null;

        try {
            classificationResponse = saisService.classificationsExport(xRoadHeader);
            saisClassifierRepository.deleteAllInBatch();
            classificationResponse.setQueryStart(LocalDateTime.now());
            try {
            for (ClassificationTypeItem cTItem : classificationResponse.getClassificationsExport()
                    .getClassificationTypes().getClassificationTypeItem()) {
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
                        switch (kvp.getKey().toUpperCase()) {
                        case "ENGLISH":
                            sc.setNameEn(name);
                            break;
                        case "ESTONIAN":
                            sc.setNameEt(name);
                            break;
                        default:
                            sc.setNameEt("");
                        }
                    }
                    em.persist(sc);
                }
            }
            // TODO calculate count from classificationResponse
            classificationResponse.setRecordCount(Long.valueOf(saisClassifierRepository.count()));
            } catch (Exception e) {
                classificationResponse.setError(ExceptionUtil.exceptionToStackTraceString(e));
                classificationResponse.setProcessingErrors(Boolean.TRUE);
            }
            classificationResponse.setQueryEnd(LocalDateTime.now());
        } catch (Exception e) {
            if(classificationResponse == null) {
                classificationResponse = new SaisClassificationResponse();
            }
            classificationResponse.setError(ExceptionUtil.exceptionToStackTraceString(e));
            classificationResponse.setxRoadErrors(Boolean.TRUE);
        }
        saisLogService.insertLog(classificationResponse, user, null);
        return list(criteria, pageable);
    }
}
