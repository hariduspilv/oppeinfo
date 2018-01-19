package ee.hitsa.ois.service.sais;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.config.SaisProperties;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.sais.SaisClassifier;
import ee.hitsa.ois.repository.SaisClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisClassifierSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.sais2.generated.ClassificationItem;
import ee.hois.xroad.sais2.generated.ClassificationTypeItem;
import ee.hois.xroad.sais2.generated.Kvp;
import ee.hois.xroad.sais2.service.SaisClient;

@Transactional
@Service
public class SaisClassifierService {

    static final String ESTONIAN = "ESTONIAN";

    @Autowired
    private SaisClassifierRepository saisClassifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SaisClient saisClient;
    @Autowired
    private SaisProperties sp;
    @Autowired
    private SaisLogService saisLogService;

    public Page<SaisClassifierSearchDto> search(String parentCode, SaisClassifierSearchCommand criteria,
            Pageable pageable) {
        JpaQueryBuilder<SaisClassifier> qb = new JpaQueryBuilder<>(SaisClassifier.class, "sc").sort(pageable);
        qb.requiredCriteria("sc.parentCode = :parentCode", "parentCode", parentCode);
        qb.optionalCriteria("sc.value = :value", "value", criteria.getValue());
        qb.optionalContains(Arrays.asList("sc.nameEt", "sc.nameEn"), "name", criteria.getName());

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(SaisClassifierSearchDto::of);
    }

    public Page<SaisClassifierSearchDto> list(SaisClassifierSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from sais_classifier sc").sort(pageable);

        qb.filter("sc.parent_code is null");
        qb.optionalContains("sc.name_et", "name", criteria.getName());

        String select = "sc.code, sc.name_et, (select count(*) from sais_classifier sc2 where sc2.parent_code = sc.code)";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new SaisClassifierSearchDto(resultAsString(r, 0), resultAsString(r, 1), resultAsLong(r, 2));
        });
    }

    public Page<SaisClassifierSearchDto> importFromSais(SaisClassifierSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        XRoadHeaderV4 xRoadHeader = getXroadHeader(user);

        saisLogService.withResponse(saisClient.classificationsExport(xRoadHeader), null, (result, logResult) -> {
            saisClassifierRepository.deleteAllInBatch();
            List<ClassificationTypeItem> ctItems = result.getClassificationTypes() != null ? result.getClassificationTypes().getClassificationTypeItem() : null;
            for (ClassificationTypeItem cTItem : StreamUtil.nullSafeList(ctItems)) {
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
                        case ESTONIAN:
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
            logResult.setRecordCount(Long.valueOf(saisClassifierRepository.count()));
            return null;
        });
        return list(criteria, pageable);
    }

    private XRoadHeaderV4 getXroadHeader(HoisUserDetails user) {
        return sp.xroadHeader("ClassificationsExport", em.getReference(Person.class, user.getPersonId()).getIdcode());
    }
}
