package ee.hitsa.ois.service.sais;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.config.SaisProperties;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.FinSource;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.exception.BadConfigurationException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionSearchDto;
import ee.hois.soap.LogContext;
import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.sais2.generated.Admission;
import ee.hois.xroad.sais2.generated.AdmissionExportResponse;
import ee.hois.xroad.sais2.generated.AdmissionTuition;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportRequest;
import ee.hois.xroad.sais2.generated.ArrayOfInt;
import ee.hois.xroad.sais2.generated.Kvp;
import ee.hois.xroad.sais2.generated.SAISClassification;
import ee.hois.xroad.sais2.service.SaisClient;

@Transactional
@Service
public class SaisAdmissionService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DEFAULT_OPPEKEEL = "18";
    private static final String DEFAULT_OPPEVORM = "M";

    private DatatypeFactory datatypeFactory;
    @Autowired
    private EntityManager em;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;
    @Autowired
    private SaisProperties sp;
    @Autowired
    private SaisClient saisClient;
    @Autowired
    private SaisLogService saisLogService;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private ClassifierRepository classifierRepository;

    @PostConstruct
    public void postConstruct() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new BadConfigurationException("Unable to create data type factory", e);
        }
    }

    public Page<SaisAdmissionSearchDto> search(HoisUserDetails user, SaisAdmissionSearchCommand criteria,
            Pageable pageable) {
        return saisAdmissionRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (!StringUtils.isEmpty(criteria.getCode())) {
                filters.add(cb.equal(root.get("code"), criteria.getCode()));
            }

            if (criteria.getCurriculumVersion() != null) {
                filters.add(cb.equal(root.get("curriculumVersion").get("id"), criteria.getCurriculumVersion()));
            }

            if (!StringUtils.isEmpty(criteria.getStudyForm())) {
                filters.add(cb.equal(root.get("studyForm").get("code"), criteria.getStudyForm()));
            }

            if (!StringUtils.isEmpty(criteria.getFin())) {
                filters.add(cb.equal(root.get("fin").get("code"), criteria.getFin()));
            }

            filters.add(cb.equal(root.get("curriculumVersion").get("curriculum").get("school").get("id"), user.getSchoolId()));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SaisAdmissionSearchDto::of);
    }

    public void delete(SaisAdmission saisAdmission) {
        EntityUtil.deleteEntity(saisAdmission, em);
    }

    public Page<SaisAdmissionSearchDto> saisImport(SaisAdmissionImportForm form, HoisUserDetails user) {
        List<SaisAdmissionSearchDto> importResult = new ArrayList<>();
        XRoadHeader xRoadHeader = getXroadHeader(user);

        Long schoolId = user.getSchoolId();
        AllAdmissionsExportRequest request = null;
        try {
            request = getRequest(form, user);
        } catch(NumberFormatException e) {
            LogContext logResult = xRoadHeader.logContext();
            logResult.setError(e);
            saisLogService.insertLog(logResult, schoolId, "Kooli reg. nr. lugemisel tekkis viga", true);
        }

        if(request != null) {
            saisLogService.withResponse(saisClient.admissionsExport(xRoadHeader, request), schoolId, (result, logResult) -> {
                processResponse(result, importResult, schoolId);
                return null;
            });
        }
        return new PageImpl<>(importResult);
    }

    private void processResponse(AdmissionExportResponse response, List<SaisAdmissionSearchDto> result, Long schoolId) {
        Map<String, Classifier> oppekeelMap = StreamUtil.toMap(Classifier::getExtraval1, classifierRepository.findAllByMainClassCode(MainClassCode.OPPEKEEL.name()));
        // XXX maybe ignore values with getExtraval1 missing and when multiple items with same values, use first one
        Map<String, Classifier> oppevormMap = StreamUtil.toMap(Classifier::getValue, classifierRepository.findAllByMainClassCode(MainClassCode.OPPEVORM.name()));

        Classifier finallikasRe = classifierRepository.getOne(FinSource.FINALLIKAS_RE.name());
        Classifier finallikasRev = classifierRepository.getOne(FinSource.FINALLIKAS_REV.name());
        Classifier oppekoormusOsa = classifierRepository.getOne(StudyLoad.OPPEKOORMUS_OSA.name());
        Classifier oppekoormusTais = classifierRepository.getOne(StudyLoad.OPPEKOORMUS_TAIS.name());

        List<Admission> addmissions = response.getAdmissions() != null ? response.getAdmissions().getAdmission() : null;
        for(Admission admission : StreamUtil.nullSafeList(addmissions)) {
            SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(admission.getCode());
            CurriculumVersion curriculumVersion = curriculumVersionRepository.findByCodeAndCurriculumSchoolId(admission.getCode(), schoolId);
            if(saisAdmission == null) {
                saisAdmission = new SaisAdmission();
            }

            if(curriculumVersion == null || admission.getAdmissionPeriodStart() == null || admission.getAdmissionPeriodEnd() == null) {
                SaisAdmissionSearchDto admissionSearch = new SaisAdmissionSearchDto();
                admissionSearch.setFailed(Boolean.TRUE);
                admissionSearch.setCode(admission.getCode());
                if(curriculumVersion == null) {
                    admissionSearch.setError("reception.admission.missingAdmissionVersion");
                } else if(admission.getAdmissionPeriodStart() == null) {
                    admissionSearch.setError("reception.admission.missinPeriodStart");
                } else if(admission.getAdmissionPeriodEnd() == null) {
                    admissionSearch.setError("reception.admission.missingPeriodEnd");
                }
                result.add(admissionSearch);
                continue;
            }

            saisAdmission.setSaisId(admission.getId());
            saisAdmission.setCurriculumVersion(curriculumVersion);
            saisAdmission.setCode(admission.getCode());
            for(Kvp kvp : admission.getName().getKvp()) {
                if("ESTONIAN".equalsIgnoreCase(kvp.getKey())) {
                    saisAdmission.setName(kvp.getValue());
                }
            }
            if(admission.isIsFullLoad()) {
                for(AdmissionTuition tuition : admission.getAdmissionTuitions().getAdmissionTuition()) {
                    if(tuition.isIsFullLoad() && tuition.isIsFree()) {
                        saisAdmission.setFin(finallikasRe);
                    }
                }
            }
            if(saisAdmission.getFin() == null) {
                saisAdmission.setFin(finallikasRev);
            }
            Date startDate = admission.getAdmissionPeriodStart().toGregorianCalendar().getTime();
            Date endDate = admission.getAdmissionPeriodEnd().toGregorianCalendar().getTime();
            saisAdmission.setPeriodStart(LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()).toLocalDate());
            saisAdmission.setPeriodEnd(LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()).toLocalDate());

            if(admission.isIsPartialLoad()) {
                saisAdmission.setStudyLoad(oppekoormusOsa);
            } else {
                saisAdmission.setStudyLoad(oppekoormusTais);
            }

            saisAdmission.setStudyLevel(saisAdmission.getCurriculumVersion().getCurriculum().getOrigStudyLevel());

            for(SAISClassification clf : admission.getCurriculumLanguages().getSAISClassification()) {
                saisAdmission.setLanguage(oppekeelMap.get(clf.getValue()));
            }
            if(saisAdmission.getLanguage() == null) {
                log.info("couldn't map language for admission with code {}, using default value {}", saisAdmission.getCode(), DEFAULT_OPPEKEEL);
                saisAdmission.setLanguage(oppekeelMap.get(DEFAULT_OPPEKEEL));
            }

            for(SAISClassification clf : admission.getStudyForms().getSAISClassification()) {
                saisAdmission.setStudyForm(oppevormMap.get(clf.getValue()));
            }
            if(saisAdmission.getStudyForm() == null) {
                log.info("couldn't map studyform for admission with code {}, using default value {}", saisAdmission.getCode(), DEFAULT_OPPEVORM);
                saisAdmission.setStudyForm(oppevormMap.get(DEFAULT_OPPEVORM));
            }
            result.add(SaisAdmissionSearchDto.of(saisAdmissionRepository.save(saisAdmission)));
        }
    }

    private XRoadHeader getXroadHeader(HoisUserDetails user) {
        XRoadHeader xRoadHeader = new XRoadHeader();

        xRoadHeader.setConsumer(sp.getConsumer());
        xRoadHeader.setEndpoint(sp.getEndpoint());
        xRoadHeader.setProducer(sp.getProducer());
        xRoadHeader.setUserId(sp.getUseridprefix() + em.getReference(Person.class, user.getPersonId()).getIdcode());
        xRoadHeader.setId(UUID.randomUUID().toString());
        // TODO configurable
        xRoadHeader.setService("sais2.AllAdmissionsExport.v1");
        return xRoadHeader;
    }

    private AllAdmissionsExportRequest getRequest(SaisAdmissionImportForm form, HoisUserDetails user) {
        AllAdmissionsExportRequest request = new AllAdmissionsExportRequest();
        GregorianCalendar gcal = GregorianCalendar.from(form.getCreateDateFrom().atStartOfDay(ZoneId.systemDefault()));
        request.setCreateDateFrom(datatypeFactory.newXMLGregorianCalendar(gcal));
        request.setModifyDateFrom(datatypeFactory.newXMLGregorianCalendar(gcal));

        gcal = GregorianCalendar.from(form.getCreateDateTo().atStartOfDay(ZoneId.systemDefault()));
        request.setCreateDateTo(datatypeFactory.newXMLGregorianCalendar(gcal));
        request.setModifyDateTo(datatypeFactory.newXMLGregorianCalendar(gcal));
        Classifier ehisSchool = em.getReference(School.class, user.getSchoolId()).getEhisSchool();
        Integer koolRegNr = null;
        if(ehisSchool.getValue2() != null) {
            koolRegNr = Integer.valueOf(ehisSchool.getValue2());
        }
        ArrayOfInt aoi = new ArrayOfInt();
        aoi.getInt().add(koolRegNr);
        request.setInstitutionRegCodes(aoi);

        return request;
    }
}
