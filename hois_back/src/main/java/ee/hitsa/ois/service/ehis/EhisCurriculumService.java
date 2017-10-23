package ee.hitsa.ois.service.ehis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.soap.LogContext;
import ee.hois.xroad.ehis.generated.OisFail;
import ee.hois.xroad.ehis.generated.OisFailid;
import ee.hois.xroad.ehis.generated.OisKutsestandard;
import ee.hois.xroad.ehis.generated.OisKutsestandardid;
import ee.hois.xroad.ehis.generated.OisOppekava;
import ee.hois.xroad.ehis.generated.OisOppekavaStaatus;
import ee.hois.xroad.ehis.generated.OisOppekavad;
import ee.hois.xroad.ehis.generated.OisOppekavadStaatus;
import ee.hois.xroad.ehis.generated.OisOppekeeled;
import ee.hois.xroad.ehis.generated.OisOsakutse;
import ee.hois.xroad.ehis.generated.OisValisOAS;
import ee.hois.xroad.ehis.generated.OisYhisOppekava;
import ee.hois.xroad.ehis.generated.OiskutseSpetsialiseerumine;
import ee.hois.xroad.ehis.generated.OppekavaOis;
import ee.hois.xroad.ehis.generated.OppekavaStaatusOis;
import ee.hois.xroad.ehis.service.EhisOisOppekavaResponse;
import ee.hois.xroad.ehis.service.EhisOisOppekavaStaatusResponse;

@Transactional
@Service
public class EhisCurriculumService extends EhisService {

    private static final Pattern OCCUPATION_ID_PREFIX = Pattern.compile("^KUTSE_(\\d+)$");
    private static final Pattern PARTOCCUPATION_ID_PREFIX = Pattern.compile("^OSAKUTSE_(\\d+)$");
    private static final String CURRICULUM_FILE_ID = "EHIS_FAIL_15773";

    static final String OIS_OPPEKAVA_SERVICE_CODE = "oisOppekava";
    public static final String OIS_OPPEKAVA_SERVICE = "ehis."+ OIS_OPPEKAVA_SERVICE_CODE + ".v1";

    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;

    public void sendToEhis(HoisUserDetails userDetails, Curriculum curriculum) {
        OisOppekava oisOppekava = new OisOppekava();
        oisOppekava.setRegNumber(value2(curriculum.getSchool().getEhisSchool()));
        Person person = em.getReference(Person.class, userDetails.getPersonId());
        oisOppekava.setKasutajaIk(person.getIdcode());
        OisOppekavad oppekavad = new OisOppekavad();
        oisOppekava.setOppekavad(oppekavad);

        boolean higher = Boolean.TRUE.equals(curriculum.getHigher());
        OppekavaOis oppekavaOis = new OppekavaOis();
        oppekavaOis.setOppekavaKood(curriculumCode(curriculum));
        oppekavaOis.setOppekavaLiik(higher ? "OK_LIIK_KORG" : "OK_LIIK_KUTSE");
        oppekavaOis.setOppekavaNimetus(curriculum.getNameEt());
        oppekavaOis.setOppekavaNimetusEng(curriculum.getNameEn());
        oppekavaOis.setTase(value(curriculum.getOrigStudyLevel()));
        oppekavaOis.setOasKinnitDoc(curriculum.getApprovalDokNr());
        oppekavaOis.setOasKinnitKp(date(curriculum.getApproval()));
        for(CurriculumStudyLanguage csl : curriculum.getStudyLanguages()) {
            OisOppekeeled studyLang = new OisOppekeeled();
            studyLang.setOppekeeleKood(value(csl.getStudyLang()));
            oppekavaOis.getOppekeeled().add(studyLang);
        }
        oppekavaOis.setNomKestusAasta(curriculum.getStudyPeriod() != null ? BigInteger.valueOf(curriculum.getStudyPeriod().longValue() / 12) : null);
        oppekavaOis.setNomKestusKuud(curriculum.getStudyPeriod() != null ? Integer.valueOf(curriculum.getStudyPeriod().intValue() % 12) : null);
        oppekavaOis.setOppekavaMaht(curriculum.getCredits() != null ? BigInteger.valueOf(curriculum.getCredits().longValue()) : null);
        oppekavaOis.setOppekavaRyhm(value(curriculum.getIscedClass()));
        Classifier group;
        if(higher) {
            group = curriculum.getGroup();
        } else if(curriculum.getIscedClass() != null) {
            ClassifierConnect c = curriculum.getIscedClass().getClassifierConnects().stream().filter(r -> MainClassCode.OPPEKAVAGRUPP.name().equals(r.getMainClassifierCode())).findFirst().orElse(null);
            group = c != null ? c.getConnectClassifier() : null;
        } else {
            group = null;
        }
        oppekavaOis.setOppekavaGrupp(ehisValue(group));
        oppekavaOis.setAkadKraad(higher && curriculum.getGrades() != null && !curriculum.getGrades().isEmpty() ? ehisValue(curriculum.getGrades().stream().findFirst().get().getEhisGrade()) : null);
        BigInteger practice;
        if(higher) {
            CurriculumVersion cv = curriculum.getVersions().stream().findFirst().orElse(null);
            if(cv != null) {
                practice = cv.getModules().stream().flatMap(r -> r.getSubjects().stream()).map(r -> r.getSubject()).filter(r -> Boolean.TRUE.equals(r.getIsPractice()) && r.getCredits() != null).map(r -> r.getCredits()).reduce((x, y) -> x.add(y)).orElse(BigDecimal.ZERO).toBigInteger();
            } else {
                practice = BigInteger.ZERO;
            }
        } else {
            practice = curriculum.getModules().stream().filter(r -> Boolean.TRUE.equals(r.getPractice()) && r.getCredits() != null).map(r -> r.getCredits()).reduce((x, y) -> x.add(y)).orElse(BigDecimal.ZERO).toBigInteger();
        }
        oppekavaOis.setPraktikaMaht(practice);
        oppekavaOis.setVastavusRiikOppekava(!higher && curriculum.getStateCurriculum() != null ? value(curriculum.getStateCurriculum().getStateCurrClass()) : null);
        if(Boolean.TRUE.equals(curriculum.getJoint())) {
            for(CurriculumJointPartner cjp : curriculum.getJointPartners()) {
                OisYhisOppekava oisYhisOppekava = new OisYhisOppekava();
                if(cjp.isAbroad()) {
                    OisValisOAS oisValisOAS = new OisValisOAS();
                    // TODO valisOASKood
                    oisValisOAS.setValisOASNimetus(cjp.getNameEt());
                    oisYhisOppekava.setValisOAS(oisValisOAS);
                } else {
                    oisYhisOppekava.setRegNumberEestiOAS(value2(cjp.getEhisSchool()));
                }
                oppekavaOis.getYhisOppekavaOas().add(oisYhisOppekava);
            }
        }
        OisKutsestandardid occupations = new OisKutsestandardid();
        if(curriculum.getOccupations().isEmpty()) {
            occupations.setPuudubKehtivKutsestandard(Integer.valueOf(1));
        } else {
            for(CurriculumOccupation co : curriculum.getOccupations()) {
                String occupationId = EntityUtil.getNullableCode(co.getOccupation());
                if(occupationId != null) {
                    Matcher m = OCCUPATION_ID_PREFIX.matcher(occupationId);
                    if(m.matches()) {
                        occupationId = value(co.getOccupation());
                        OisKutsestandard oisKutsestandard = new OisKutsestandard();
                        oisKutsestandard.setStandardReaId(new BigInteger(occupationId));
                        for(CurriculumOccupationSpeciality cos : co.getSpecialities()) {
                            OiskutseSpetsialiseerumine oiskutseSpetsialiseerumine = new OiskutseSpetsialiseerumine();
                            // FIXME validity check for classifier.value as number
                            oiskutseSpetsialiseerumine.setKutseSpetsialiseerumineReaId(new BigInteger(value(cos.getSpeciality())));
                            oisKutsestandard.getKutseSpetsialiseerumised().add(oiskutseSpetsialiseerumine);
                        }
                        if(!higher) {
                            Set<String> partOccupationCodes = co.getOccupation().getChildConnects().stream().map(r -> EntityUtil.getCode(r.getClassifier())).collect(Collectors.toSet());
                            for(CurriculumOccupation cpo : curriculum.getOccupations()) {
                                String partOccupationId = EntityUtil.getNullableCode(cpo.getOccupation());
                                if(partOccupationId != null) {
                                    Matcher pm = PARTOCCUPATION_ID_PREFIX.matcher(partOccupationId);
                                    if(pm.matches() && partOccupationCodes.contains(partOccupationId)) {
                                        partOccupationId = value(cpo.getOccupation());
                                        OisOsakutse oisOsakutse = new OisOsakutse();
                                        oisOsakutse.setOsakutseReaId(new BigInteger(partOccupationId));
                                        oisKutsestandard.getOsakutsed().add(oisOsakutse);
                                    }
                                }
                            }
                        }
                        occupations.getKutsestandard().add(oisKutsestandard);
                    }
                }
            }
        }
        oppekavaOis.setKutsestandardid(occupations);
        OisFailid oisFailid = new OisFailid();
        for(CurriculumFile cf : curriculum.getFiles()) {
            if(!cf.isSendEhis() || !CURRICULUM_FILE_ID.equals(EntityUtil.getCode(cf.getEhisFile()))) {
                continue;
            }
            OisFail oisFail = new OisFail();
            oisFail.setTyyp(value(cf.getEhisFile()));
            // FIXME no fdescription
            // oisFail.setKommentaar(cf.getOisFile().getFtype());
            oisFail.setContentID(fileUrl(cf.getOisFile()));
            oisFailid.getOkFail().add(oisFail);
        }
        oppekavaOis.setFailid(oisFailid);
        oppekavaOis.setKommentaar(curriculum.getDescription());
        oppekavad.getOppekava().add(oppekavaOis);

        EhisOisOppekavaResponse response = ehisClient.oisOppekava(getXroadHeader(), oisOppekava);
        LogContext queryLog = response.getLog();
        // TODO logging
        /*
        wsEhisStudentLog.setHasOtherErrors(Boolean.FALSE);
        wsEhisStudentLog.setHasXteeErrors(Boolean.valueOf(queryLog.getError() != null));

        if(!response.hasError()) {
            wsEhisStudentLog.setLogTxt(String.join(";", StreamUtil.nullSafeList(response.getResult())));
        }
        return ehisLogService.insert(queryLog, wsEhisStudentLog);
        */
    }

    public void updateFromEhis(HoisUserDetails userDetails, Curriculum curriculum) {
        OisOppekavaStaatus oisOppekavaStaatus = new OisOppekavaStaatus();
        oisOppekavaStaatus.setRegNumber(value2(curriculum.getSchool().getEhisSchool()));
        Person person = em.getReference(Person.class, userDetails.getPersonId());
        oisOppekavaStaatus.setKasutajaIk(person.getIdcode());
        OisOppekavadStaatus oisOppekavadStaatus = new OisOppekavadStaatus();
        oisOppekavaStaatus.setOppekavad(oisOppekavadStaatus);

        OppekavaStaatusOis oppekavaStaatusOis = new OppekavaStaatusOis();
        // FIXME why here string? Other places have as BigInteger
        oppekavaStaatusOis.setOppekavaKood(curriculum.getMerCode());
        // XXX create enum
        oppekavaStaatusOis.setOperatsioon("kontrollimine");
        oisOppekavadStaatus.getOisOppekava().add(oppekavaStaatusOis);

        EhisOisOppekavaStaatusResponse response = ehisClient.oisOppekavaStaatus(getXroadHeader(), oisOppekavaStaatus);
        LogContext queryLog = response.getLog();
        // TODO logging
    }

    private String fileUrl(OisFile file) {
        return frontendBaseUrl + "oisfile/get/" + file.getId();
    }

    @Override
    protected String getServiceCode() {
        return OIS_OPPEKAVA_SERVICE_CODE;
    }
}
