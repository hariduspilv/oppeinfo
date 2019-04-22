package ee.hitsa.ois.service.sais;

import static ee.hitsa.ois.service.sais.SaisClassifierService.ESTONIAN;

import java.lang.invoke.MethodHandles;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
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
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.ArchiveForm;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionDto;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionSearchDto;
import ee.hois.soap.LogContext;
import ee.hois.xroad.helpers.XRoadHeaderV4;
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
    private ClassifierService classifierService;

    @PostConstruct
    public void postConstruct() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new BadConfigurationException("Unable to create data type factory", e);
        }
    }
    
    public void updateArchive(HoisUserDetails user, ArchiveForm archiveForm) {
    	Long schoolId = user.getSchoolId();
    	List<SaisAdmission> admissions = em.createQuery("select sa from SaisAdmission sa where sa.curriculumVersion.curriculum.school.id=?1"
    	 		+ " and (sa.is_archived is null OR sa.is_archived is false)"
    	 		+ " and sa.periodEnd <= ?2", SaisAdmission.class)
                 .setParameter(1, schoolId)
                 .setParameter(2, archiveForm.getEndDate())
                 .getResultList();
    	if (!admissions.isEmpty()) {
    		EntityUtil.setUsername(user.getUsername(), em);
        	admissions.forEach(p->save(p, Boolean.TRUE));
    	}
    }
    
    public SaisAdmissionDto updateArchive(HoisUserDetails user, SaisAdmission saisAdmission) {
		EntityUtil.setUsername(user.getUsername(), em);
		Long schoolId = user.getSchoolId();
    	List<SaisAdmission> admissions = em.createQuery("select sa from SaisAdmission sa where sa.curriculumVersion.curriculum.school.id=?1"
    	 		+ " and (sa.is_archived is null OR sa.is_archived is false)", SaisAdmission.class)
                 .setParameter(1, schoolId)
                 .getResultList();
    	List<String> admissionCodes = admissions.stream().map(p->p.getCode()).collect(Collectors.toList());
    	if (!admissionCodes.contains(saisAdmission.getCode())) {
    		save(saisAdmission, Boolean.FALSE);
    	}
    	return SaisAdmissionDto.of(saisAdmission);
	}
    
    public SaisAdmission save(SaisAdmission admission, Boolean archived) {
    	admission.setArchived(archived);
    	admission = EntityUtil.save(admission, em);
    	return admission;
    }
    
    public Page<SaisAdmissionSearchDto> search(Long schoolId, SaisAdmissionSearchCommand criteria,
            Pageable pageable) {
        JpaQueryBuilder<SaisAdmission> qb = new JpaQueryBuilder<>(SaisAdmission.class, "sa").sort(pageable);
        qb.optionalCriteria("sa.code = :code", "code", criteria.getCode());
        qb.optionalCriteria("sa.curriculumVersion.id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVersion());
        qb.optionalCriteria("sa.studyForm.code = :studyForm", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("sa.fin.code = :fin", "fin", criteria.getFin());
        qb.requiredCriteria("sa.curriculumVersion.curriculum.school.id = :schoolId", "schoolId", schoolId);
        if (criteria.getArchived() == null || Boolean.FALSE.equals(criteria.getArchived())) {
        	qb.filter("(sa.is_archived is null OR sa.is_archived is false)");
        }
        return JpaQueryUtil.pagingResult(qb, em, pageable).map(SaisAdmissionSearchDto::of);
    }
    
    public Page<SaisAdmissionSearchDto> saisImport(SaisAdmissionImportForm form, HoisUserDetails user) {
        List<SaisAdmissionSearchDto> importResult = new ArrayList<>();
        XRoadHeaderV4 xRoadHeader = getXroadHeader(user);

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
        Map<String, Classifier> oppekeelMap = classifierService.findAllByMainClassCode(MainClassCode.OPPEKEEL).stream().collect(
                Collectors.toMap(Classifier::getExtraval1, it -> it, ClassifierUtil::validOne));
        Map<String, Classifier> oppevormMap = classifierService.findAllByMainClassCode(MainClassCode.OPPEVORM).stream().collect(
                Collectors.toMap(Classifier::getValue, it -> it, ClassifierUtil::validOne));

        Classifier finallikasRe = em.getReference(Classifier.class, FinSource.FINALLIKAS_RE.name());
        Classifier finallikasRev = em.getReference(Classifier.class, FinSource.FINALLIKAS_REV.name());
        Classifier oppekoormusOsa = em.getReference(Classifier.class, StudyLoad.OPPEKOORMUS_OSA.name());
        Classifier oppekoormusTais = em.getReference(Classifier.class, StudyLoad.OPPEKOORMUS_TAIS.name());
        Classifier oppekoormusMta = em.getReference(Classifier.class, StudyLoad.OPPEKOORMUS_MTA.name());

        List<Admission> addmissions = response.getAdmissions() != null ? response.getAdmissions().getAdmission() : null;
        for(Admission admission : StreamUtil.nullSafeList(addmissions)) {
            CurriculumVersion curriculumVersion = curriculumVersionRepository.findByCodeAndCurriculumSchoolId(admission.getCode(), schoolId);
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

            List<SaisAdmission> saisAdmissions = saisAdmissionRepository.findByCode(admission.getCode());
            Optional<SaisAdmission> saisAdmissionNotArchived = saisAdmissions.stream().filter(p-> Boolean.FALSE.equals(p.getArchived())).findFirst();
            SaisAdmission saisAdmission = null;
            if (saisAdmissionNotArchived.isPresent()) {
            	saisAdmission = saisAdmissionNotArchived.get();
            }
            if(saisAdmission == null) {
                saisAdmission = new SaisAdmission();
                saisAdmission.setArchived(Boolean.FALSE);
            }
            saisAdmission.setSaisId(admission.getId());
            saisAdmission.setCurriculumVersion(curriculumVersion);
            saisAdmission.setCode(admission.getCode());
            for(Kvp kvp : admission.getName().getKvp()) {
                if(ESTONIAN.equalsIgnoreCase(kvp.getKey())) {
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
            saisAdmission.setPeriodStart(DateUtils.toLocalDate(admission.getAdmissionPeriodStart()));
            saisAdmission.setPeriodEnd(DateUtils.toLocalDate(admission.getAdmissionPeriodEnd()));

            saisAdmission.setIsFullLoad(Boolean.valueOf(admission.isIsFullLoad()));
            saisAdmission.setIsPartialLoad(Boolean.valueOf(admission.isIsPartialLoad()));
            saisAdmission.setIsUndefinedLoad(Boolean.valueOf(admission.isIsUndefinedLoad()));

            if (admission.isIsFullLoad()) {
                saisAdmission.setStudyLoad(oppekoormusTais);
            } else if (admission.isIsPartialLoad()) {
                saisAdmission.setStudyLoad(oppekoormusOsa);
            } else {
                saisAdmission.setStudyLoad(oppekoormusMta);
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
            saisAdmission.setPlaces(admission.getAdmissionCount());
            
            result.add(SaisAdmissionSearchDto.of(EntityUtil.save(saisAdmission, em)));
        }
    }

    private XRoadHeaderV4 getXroadHeader(HoisUserDetails user) {
        return sp.xroadHeader("AllAdmissionsExport", em.getReference(Person.class, user.getPersonId()).getIdcode());
    }

    private AllAdmissionsExportRequest getRequest(SaisAdmissionImportForm form, HoisUserDetails user) {
        AllAdmissionsExportRequest request = new AllAdmissionsExportRequest();
        GregorianCalendar gcal = GregorianCalendar.from(form.getCreateDateFrom().atStartOfDay(ZoneId.systemDefault()));
        //request.setCreateDateFrom(datatypeFactory.newXMLGregorianCalendar(gcal));
        request.setModifyDateFrom(datatypeFactory.newXMLGregorianCalendar(gcal));

        gcal = GregorianCalendar.from(form.getCreateDateTo().plusDays(1).atStartOfDay(ZoneId.systemDefault()));
        //request.setCreateDateTo(datatypeFactory.newXMLGregorianCalendar(gcal));
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
