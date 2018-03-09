package ee.hitsa.ois.service.sais;

import static ee.hitsa.ois.service.sais.SaisClassifierService.ESTONIAN;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import ee.hitsa.ois.config.SaisProperties;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.domain.sais.SaisApplicationGrade;
import ee.hitsa.ois.domain.sais.SaisApplicationGraduatedSchool;
import ee.hitsa.ois.domain.sais.SaisApplicationOtherData;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.FinSource;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SaisApplicationStatus;
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.exception.BadConfigurationException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.SaisAdmissionUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationClassifiersCsv;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationCsvRow;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportedRowDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;
import ee.hois.soap.LogContext;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.AppExportResponse;
import ee.hois.xroad.sais2.generated.Application;
import ee.hois.xroad.sais2.generated.ApplicationFormData;
import ee.hois.xroad.sais2.generated.ArrayOfCandidateAddress;
import ee.hois.xroad.sais2.generated.ArrayOfInt;
import ee.hois.xroad.sais2.generated.ArrayOfKvp;
import ee.hois.xroad.sais2.generated.ArrayOfString;
import ee.hois.xroad.sais2.generated.CandidateAddress;
import ee.hois.xroad.sais2.generated.CandidateEducation;
import ee.hois.xroad.sais2.generated.CandidateGrade;
import ee.hois.xroad.sais2.generated.CandidateStateExam;
import ee.hois.xroad.sais2.generated.FormFieldOption;
import ee.hois.xroad.sais2.generated.Kvp;
import ee.hois.xroad.sais2.generated.ObjectFactory;
import ee.hois.xroad.sais2.generated.SAISClassification;
import ee.hois.xroad.sais2.service.SaisApplicationResponse;
import ee.hois.xroad.sais2.service.SaisClient;

@Transactional
@Service
public class SaisApplicationService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String CONTACT_ADDR_VALUE = "1";

    private static final List<String> REVOKED_APPLICATION_STATUSES = EnumUtil.toNameList(SaisApplicationStatus.SAIS_AVALDUSESTAATUS_AL,
            SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TL, SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TYH);

    private static final List<String> CLASSIFIERS_LIST = EnumUtil.toNameList(MainClassCode.FINALLIKAS, MainClassCode.RIIK,
            MainClassCode.SAIS_AVALDUSESTAATUS, MainClassCode.OPPEASTE, MainClassCode.OPPEKEEL, MainClassCode.OPPEKOORMUS, MainClassCode.OPPEVORM);

    private static final String SAIS_APPLICATION_FROM = "from (select a.id, a.application_nr, a.idcode, a.firstname, a.lastname, a.status_code,"+
            "sais_admission.code as sais_admission_code, (exists (select id from directive_student where directive_student.sais_application_id = a.id and canceled = false)) as added_to_directive, curriculum.school_id as school_id from sais_application a "+
            "inner join sais_admission on sais_admission.id = a.sais_admission_id "+
            "inner join classifier status on a.status_code = status.code "+
            "left join curriculum_version on curriculum_version.id = sais_admission.curriculum_version_id "+
            "left join curriculum on curriculum.id = curriculum_version.curriculum_id) as sais_application_dto";

    private DatatypeFactory datatypeFactory;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;
    @Autowired
    private SaisApplicationRepository saisApplicationRepository;
    @Autowired
    private SaisLogService saisLogService;
    @Autowired
    private SaisClient saisClient;
    @Autowired
    private Validator validator;
    @Autowired
    private SaisProperties sp;

    private final CsvMapper csvMapper = new CsvMapper();
    private final ObjectFactory objectFactory = new ObjectFactory();

    @PostConstruct
    public void postConstruct() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new BadConfigurationException("Unable to create data type factory", e);
        }
    }

    public Page<SaisApplicationSearchDto> search(HoisUserDetails user, SaisApplicationSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SAIS_APPLICATION_FROM).sort(pageable);

        qb.requiredCriteria("school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("sais_admission_code in (:code)", "code", criteria.getCode());
        if (Boolean.TRUE.equals(criteria.getAddedToDirective())) {
            qb.filter("added_to_directive = true");
        }

        qb.optionalContains(Arrays.asList("firstname", "lastname", "firstname || ' ' || lastname"), "name", criteria.getName());
        qb.optionalCriteria("idcode = :idcode", "idcode", criteria.getIdcode());

        qb.optionalCriteria("status_code in (:status)", "status", criteria.getStatus());

        List<String> revokedStatuses = new ArrayList<>(REVOKED_APPLICATION_STATUSES);
        if (!CollectionUtils.isEmpty(criteria.getStatus())) {
            revokedStatuses.removeAll(criteria.getStatus());
        }
        if (!Boolean.TRUE.equals(criteria.getShowRevoked())) {
          qb.optionalCriteria("status_code not in (:revokedStatus)", "revokedStatus", revokedStatuses);
        }

        return JpaQueryUtil.pagingResult(qb, "id, application_nr, idcode, firstname, lastname, status_code, sais_admission_code, added_to_directive, school_id", em, pageable).map(r -> {
            SaisApplicationSearchDto dto = new SaisApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setApplicationNr(resultAsString(r, 1));
            dto.setIdcode(resultAsString(r, 2));
            dto.setFirstname(resultAsString(r, 3));
            dto.setLastname(resultAsString(r, 4));
            dto.setStatus(resultAsString(r, 5));
            dto.setSaisAdmissionCode(resultAsString(r, 6));
            dto.setAddedToDirective(resultAsBoolean(r, 7));
            return dto;
        });
    }

    public SaisApplicationImportResultDto importCsv(byte[] fileData, HoisUserDetails user) {
        CsvSchema schema = csvMapper.schemaFor(SaisApplicationCsvRow.class).withHeader().withColumnSeparator(';');

        SaisApplicationImportResultDto dto = new SaisApplicationImportResultDto();
        EstonianIdCodeValidator idCodeValidator = new EstonianIdCodeValidator();
        List<SaisApplicationImportedRowDto> failed = dto.getFailed();
        ClassifierCache classifiers = new ClassifierCache(classifierService);

        EntityUtil.setUsername(user.getUsername(), em);

        String fileContent = getContent(fileData);

        int rowNr = 1;
        Map<String, Object> processedByNr = new HashMap<>();
        try (MappingIterator<SaisApplicationCsvRow> csvValues = csvMapper.readerFor(SaisApplicationCsvRow.class).with(schema).readValues(fileContent)) {
            while (csvValues.hasNext()) {
                SaisApplicationCsvRow row = csvValues.next();
                try {
                      proccessRow(row, rowNr, failed, dto, classifiers, processedByNr, idCodeValidator, user.getSchoolId());
                  } catch (Exception e) {
                      failed.add(new SaisApplicationImportedRowDto(rowNr, "Viga rea töötlemisel"));
                      log.error(e.getMessage(), e);
                  }
                rowNr++;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            failed.add(new SaisApplicationImportedRowDto(rowNr, "tundmatu viga."));
        }

        return dto;
    }

    private static String getContent(byte[] fileData) {
        //ISO-8859-1 vs Windows-1252: ISO-8859-1 (also called Latin-1) is identical to Windows-1252 (also called CP1252) except for the code points 128-159 (0x80-0x9F)
        //ISO-8859-1 vs ISO-8859-15: These 2 encodings are identical except for 8 code points, which causes confusion between the two of them as well as with Windows-1252.
        //ISO-8859-1 vs us ASCII the first 127 characters are the same
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        try {
            decoder.decode(ByteBuffer.wrap(fileData));
        } catch (@SuppressWarnings("unused") CharacterCodingException e) {
            return new String(fileData, StandardCharsets.ISO_8859_1);
        }
        return new String(fileData, StandardCharsets.UTF_8);
    }

    /**
     * TODO: this method may be slow - it has many N+1 query problems.
     */
    private void proccessRow(SaisApplicationCsvRow row, int rowNr, List<SaisApplicationImportedRowDto> failed,
            SaisApplicationImportResultDto dto, ClassifierCache classifiers, Map<String, Object> processedByNr,
            EstonianIdCodeValidator idCodeValidator, Long schoolId) {

        String applicationNr = row.getApplicationNr();
        if (!StringUtils.hasText(applicationNr)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, "Puudub avalduse number"));
            return;
        } else if (processedByNr.containsKey(applicationNr)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, String.format("Failis on rohkem kui üks avalduse numbriga %s vastuvõtu avaldust.", applicationNr)));
            return;
        }
        processedByNr.put(applicationNr, null);

        String messageForMissing = String.format("Avaldusel nr %s puudub ", applicationNr);
        String messageForOther = String.format("Avaldusega nr %s ", applicationNr);

        String admissionCode = row.getCode();
        String curriculumVersionCode = row.getCurriculumVersionCode();
        if (StringUtils.isEmpty(admissionCode) && StringUtils.isEmpty(curriculumVersionCode)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud konkursil puudub konkursi kood."));
            return;
        }

        CurriculumVersion curriculumVersion = curriculumVersionRepository.findByCodeAndCurriculumSchoolId(curriculumVersionCode, schoolId);
        SaisAdmission existingSaisAdmission = null;

        if (StringUtils.isEmpty(admissionCode)) {
            if (curriculumVersion == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "ei ole seotud õppekava/rakenduskava."));
                return;
            }
            existingSaisAdmission = saisAdmissionRepository.findFirstByCurriculumVersionIdOrderByIdDesc(EntityUtil.getId(curriculumVersion));
        } else {
            existingSaisAdmission = saisAdmissionRepository.findByCodeAndCurriculumVersionCurriculumSchoolId(admissionCode, schoolId);
        }

        SaisAdmission saisAdmission = existingSaisAdmission == null ? new SaisAdmission() : existingSaisAdmission;
        if (existingSaisAdmission == null) {
            saisAdmission.setCurriculumVersion(curriculumVersion);
        }

        if (saisAdmission.getCurriculumVersion() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud õppekava versioonile/rakenduskavale ei leitud vastet."));
            return;
        } else if (curriculumVersion == null) {
            curriculumVersion = saisAdmission.getCurriculumVersion();
        }

        SaisApplication existingSaisApplication = saisApplicationRepository.findByApplicationNrAndSaisAdmissionCode(applicationNr, saisAdmission.getCode());
        if (existingSaisApplication != null && StringUtils.hasText(existingSaisApplication.getIdcode()) &&
                StringUtils.hasText(row.getIdcode()) && !existingSaisApplication.getIdcode().equals(row.getIdcode())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, String.format("%son süsteemis juba seotud teise isikuga (%s).", messageForOther, existingSaisApplication.getIdcode())));
            return;
        }

        if (existingSaisApplication != null && !directiveStudentsWithSaisApplication(Arrays.asList(existingSaisApplication.getId())).isEmpty()) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, String.format("%son seotud käskkiri - seda ei uuendata.", messageForOther)));
            return;
        }

        SaisApplication saisApplication = new SaisApplication();
        EntityUtil.bindToEntity(row, saisApplication, classifierRepository, "curriculumVersionCode", "submitted");

        if (!StringUtils.hasText(saisApplication.getFirstname())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "kandideerija eesnimi."));
            return;
        }

        if (!StringUtils.hasText(saisApplication.getLastname())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "kandideerija perekonnanimi."));
            return;
        }

        if (!StringUtils.hasText(saisApplication.getIdcode())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "kandideerija isikukood."));
            return;
        }

        if (!idCodeValidator.isValid(saisApplication.getIdcode(), null)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud isiku isikukood ei ole korrektne."));
            return;
        }
        String sexCode = EstonianIdCodeValidator.sexFromIdcode(saisApplication.getIdcode());
        if (StringUtils.hasText(sexCode)) {
            saisApplication.setSex(classifiers.getByCode(sexCode, MainClassCode.SUGU));
        }

        if (saisApplication.getSaisChanged() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud muutmise kuupäev on puudu või on vigane."));
            return;
        }

        if (saisApplication.getStatus() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "avalduse staatus."));
            return;
        }

        if (saisApplication.getCitizenship() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "kodakondsus."));
            return;
        }

        if (saisApplication.getResidenceCountry() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "elukohariik."));
            return;
        }

        if (saisApplication.getFin() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "finantseerimisallikas."));
            return;
        }

        if (saisAdmission.getStudyLevel() == null && curriculumVersion != null && curriculumVersion.getCurriculum() != null) {
            saisAdmission.setStudyLevel(curriculumVersion.getCurriculum().getOrigStudyLevel());
        }

        //study load is only mandatory for higher education
        if (saisApplication.getStudyLoad() == null && SaisAdmissionUtil.isHigher(saisAdmission)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "õppekoormus."));
            return;
        }

        if (saisApplication.getStudyForm() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "õppevorm."));
            return;
        }

        if (saisApplication.getLanguage() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "õppekeel."));
            return;
        }

        String previousStudyLevelValue = row.getStudyLevel();
        Classifier previousStudyLevel = classifiers.getByValue(previousStudyLevelValue, MainClassCode.OPPEASTE);
        if (previousStudyLevel == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "eelmine õppetase."));
            return;
        }

        String previousStudyLevelCode = EntityUtil.getCode(previousStudyLevel);
        SaisApplicationGraduatedSchool existing = saisApplication.getGraduatedSchools().stream()
                .filter(it -> previousStudyLevelCode.equals(EntityUtil.getNullableCode(it.getStudyLevel()))).findFirst().orElse(null);

        if(existing == null) {
            SaisApplicationGraduatedSchool sags = new SaisApplicationGraduatedSchool();
            sags.setStudyLevel(previousStudyLevel);
            saisApplication.getGraduatedSchools().add(sags);
        } else {
            existing.setStudyLevel(previousStudyLevel);
        }

        saisApplication.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(row.getIdcode()));
        if (saisApplication.getSubmitted() == null) {
            saisApplication.setSubmitted(row.getSaisChanged());
        }

        if (saisAdmission.getCode() == null) {
            saisAdmission.setCode(admissionCode);
            saisAdmission.setName(admissionCode);
            saisAdmission.setSaisId(admissionCode);
        }
        if (saisAdmission.getFin() == null) {
            saisAdmission.setFin(saisApplication.getFin());
        }
        if (saisAdmission.getCurriculumVersion() == null) {
            saisAdmission.setCurriculumVersion(curriculumVersion);
        }
        if (saisAdmission.getStudyLoad() == null) {
            saisAdmission.setStudyLoad(saisApplication.getStudyLoad());
        }
        if (saisAdmission.getStudyForm() == null) {
            saisAdmission.setStudyForm(saisApplication.getStudyForm());
        }
        if(saisAdmission.getLanguage() == null) {
            saisAdmission.setLanguage(saisApplication.getLanguage());
        }
        if (saisAdmission.getPeriodStart() == null) {
            LocalDate periodStart = row.getStartDate();
            if (periodStart == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud konkursi alguse kuupäev on puudu või on vigane."));
                return;
            }
            saisAdmission.setPeriodStart(periodStart);
        }
        if(saisAdmission.getPeriodEnd() == null) {
            LocalDate periodEnd = row.getEndDate();
            if (periodEnd == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud konkursi lõpu kuupäev on puudu või on vigane."));
                return;
            }
            saisAdmission.setPeriodEnd(periodEnd);
        }

        Long admissionSchool = EntityUtil.getId(saisAdmission.getCurriculumVersion().getCurriculum().getSchool());
        if (!admissionSchool.equals(schoolId)) {
            log.error("{}seotud õppekava/rakenduskava kool {} ei kuulu kasutaja koolile {}.", messageForOther, admissionSchool, schoolId);
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud õppekava/rakenduskava kool ei ühti kasutaja kooliga."));
            return;
        }

        if (existingSaisApplication == null) {
            Set<ConstraintViolation<SaisApplication>> saisApplicationErrors = validator.validate(saisApplication, Default.class);
            if (!saisApplicationErrors.isEmpty()) {
                if(log.isErrorEnabled()) {
                    log.error("sais application validation failed: [{}]", saisApplicationErrors.stream().map(error -> error.getPropertyPath() + ": " + error.getMessage()).collect(Collectors.joining("; ")));
                }
                failed.add(new SaisApplicationImportedRowDto(rowNr, "avalduse andmed on puudulikud salvestamiseks."));
                return;
            }
        }

        if (existingSaisAdmission == null) {
            Set<ConstraintViolation<SaisAdmission>> saisAdmissionErrors = validator.validate(saisAdmission, Default.class);
            if (!saisAdmissionErrors.isEmpty()) {
                if(log.isErrorEnabled()) {
                    log.error("sais admission validation failed: [{}]", saisAdmissionErrors.stream().map(error -> error.getPropertyPath() + ": " + error.getMessage()).collect(Collectors.joining("; ")));
                }
                failed.add(new SaisApplicationImportedRowDto(rowNr, "konkursi andmed on puudulikud salvestamiseks."));
                return;
            }
        }

        if(existingSaisApplication == null) {
            saisAdmission.getApplications().add(saisApplication);
        } else {
            EntityUtil.bindToEntity(saisApplication, existingSaisApplication);
        }
        saisAdmission = EntityUtil.save(saisAdmission, em);

        dto.getSuccessful().add(new SaisApplicationImportedRowDto(rowNr, saisApplication.getApplicationNr()));
    }

    public String sampleCsvFile() {
        return "KonkursiKood;AvalduseNr;Eesnimi;Perekonnanimi;Isikukood;Kodakondsus;Elukohariik;Finantseerimisallikas;AvalduseMuutmiseKp;AvalduseStaatus;OppekavaVersioon/RakenduskavaKood;Oppekoormus;Oppevorm;Oppekeel;OppuriEelnevOppetase;KonkursiAlgusKp;KonkursiLõppKp\r\n"+
               "FIL12/12;Nr123;Mari;Maasikas;49011112345;EST;EST;RE;1.01.2012;T;FIL12/12;TAIS;P;E;411;1.12.2011;1.02.2012\r\n"+
               "MAT15/16;Nr456;Tõnu;Kuut;39311112312;FIN;EST;REV;3.03.2012;T;MAT15/16;OSA;P;I;411;1.01.2012;3.04.2012\r\n"+
               "MAT15/16;Nr321;Tiiu;Kask;49302052312;EST;EST;RE;12.02.2012;T;MAT15/16;OSA;K;E;411;1.01.2012;3.04.2012\r\n"+
               "MAT15/16;Nr321;El;Mariažši;48703083963;EST;EST;RE;22.02.2012;T;MAT15/16;OSA;K;E;411;1.01.2012;3.04.2012";
    }

    public String classifiersFile() {
        List<ClassifierSelection> classifiers = autocompleteService.classifiers(CLASSIFIERS_LIST);
        classifiers.sort(Comparator.comparing(ClassifierSelection::getMainClassCode).thenComparing(Comparator.comparing(ClassifierSelection::getValue)));

        CsvSchema schema = csvMapper.schemaFor(SaisApplicationClassifiersCsv.class).withHeader().withColumnSeparator(';');
        try {
            return csvMapper.writer().with(schema).writeValueAsString(StreamUtil.toMappedList(SaisApplicationClassifiersCsv::of, classifiers));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public SaisApplicationImportResultDto importFromSais(SaisApplicationImportForm form, HoisUserDetails user) {
        ClassifierCache classifiers = new ClassifierCache(classifierService);
        XRoadHeaderV4 xRoadHeader = getXroadHeader(user);

        Long schoolId = user.getSchoolId();
        AllAppsExportRequest request = null;
        try {
            request = getRequest(form, schoolId, classifiers);
        } catch(NumberFormatException e) {
            LogContext logContext = xRoadHeader.logContext();
            logContext.setError(e);
            saisLogService.insertLog(logContext, schoolId, "Kooli reg. nr. lugemisel tekkis viga", true);
        }

        EntityUtil.setUsername(user.getUsername(), em);
        return importPages(xRoadHeader, schoolId, request, classifiers);
    }

    private SaisApplicationImportResultDto importPages(XRoadHeaderV4 xRoadHeader, Long schoolId, AllAppsExportRequest request, ClassifierCache classifiers) {
        SaisApplicationImportResultDto dto = new SaisApplicationImportResultDto();
        EstonianIdCodeValidator idCodeValidator = new EstonianIdCodeValidator();
        Map<String, SaisAdmission> admissionMap = new HashMap<>();
        long fetchedCount = 0;

        while(request != null && request.getPage() != null) {
            SaisApplicationResponse response = saisClient.applicationsExport(xRoadHeader, request);
            if(response.getResult() != null) {
                fetchedCount += applyPaging(request, response.getResult(), fetchedCount);
            }

            saisLogService.withResponse(response, schoolId, (result, logContext) -> {

                //look for all the application numbers to find previously imported applications with one query
                List<Application> applications = result.getApplications() != null ? result.getApplications().getApplication() : null;
                List<String> previousApplicationNrs = StreamUtil.toMappedList(Application::getApplicationNumber, applications);
                Map<String, SaisApplication> previousApplications = previousApplicationNrs.isEmpty() ?
                        Collections.emptyMap() : StreamUtil.toMap(SaisApplication::getApplicationNr,
                                em.createQuery("select sa from SaisApplication sa where sa.applicationNr in ?1", SaisApplication.class)
                                    .setParameter(1, previousApplicationNrs).getResultList());
                Map<Long, Long> prevDirectives = !previousApplications.isEmpty() ? directiveStudentsWithSaisApplication(StreamUtil.toMappedList(SaisApplication::getId, previousApplications.values())) : null;

                List<SaisApplicationImportedRowDto> failed = new ArrayList<>();
                List<SaisApplicationImportedRowDto> successful = new ArrayList<>();

                for(Application application : StreamUtil.nullSafeList(applications)) {
                    SaisApplication prevApp = previousApplications.get(application.getApplicationNumber());
                    if(prevApp != null && prevDirectives != null && prevDirectives.containsKey(prevApp.getId())) {
                        String error = String.format("Avaldusega nr %s on seotud käskkiri - seda ei uuendata.", application.getApplicationNumber());
                        failed.add(new SaisApplicationImportedRowDto(application.getApplicationNumber(), error));
                    } else {
                        SaisApplicationImportedRowDto importedRow = processApplication(application, prevApp, classifiers, admissionMap, idCodeValidator);
                        if(importedRow.getMessage() == null || importedRow.getMessage().isEmpty()) {
                            successful.add(importedRow);
                        } else {
                            failed.add(importedRow);
                        }
                    }
                }

                dto.getFailed().addAll(failed);
                dto.getSuccessful().addAll(successful);

                List<SaisApplicationImportedRowDto> importResult = new ArrayList<>(failed);
                importResult.addAll(successful);
                return importResult.stream().collect(Collectors
                        .toMap(SaisApplicationImportedRowDto::getApplicationNr, SaisApplicationImportedRowDto::toString)).toString();
            });
            if(response.hasError()) {
                break;
            }
        }
        return dto;
    }

    /**
     * Update request for fetching next page. If there are no more pages left, set page element to null
     *
     * @param request
     * @param response
     * @param fetchedCount
     * @return number of application fetched including this result
     */
    private long applyPaging(AllAppsExportRequest request, AppExportResponse response, long fetchedCount) {
        List<Application> applications = response.getApplications() != null ? response.getApplications().getApplication() : null;
        JAXBElement<Integer> page = null;
        if(applications != null && !applications.isEmpty()) {
            fetchedCount += applications.size();
            if(response.getTotalCount() != null && fetchedCount < response.getTotalCount().longValue()) {
                // read less than totalCount number of applications, try next page
                page = objectFactory.createAllAppsExportRequestPage(Integer.valueOf(request.getPage().getValue().intValue() + 1));
            }
        }
        request.setPage(page);
        return fetchedCount;
    }

    private SaisApplicationImportedRowDto processApplication(Application application, SaisApplication prevApp, ClassifierCache classifiers,
            Map<String, SaisAdmission> admissionMap,
            EstonianIdCodeValidator idCodeValidator) {
        SaisApplication saisApplication;
        SaisAdmission saisAdmission = null;
        if(prevApp != null && applicationForSamePerson(application, prevApp)) {
            saisApplication = prevApp;
        } else {
            saisApplication = new SaisApplication();
        }

        saisApplication.setApplicationNr(application.getApplicationNumber());
        if(admissionMap.get(application.getAdmissionCode()) == null) {
            SaisAdmission admission = saisAdmissionRepository.findByCode(application.getAdmissionCode());
            if(admission != null) {
                admissionMap.put(admission.getCode(), admission);
            }
        }
        saisApplication.setSaisAdmission(admissionMap.get(application.getAdmissionCode()));
        saisAdmission = admissionMap.get(application.getAdmissionCode());
        saisApplication.setStatus(classifiers.getByEhisValue(classifierValue(application.getApplicationStatus()), MainClassCode.SAIS_AVALDUSESTAATUS));
        saisApplication.setFirstname(application.getFirstName());
        saisApplication.setLastname(application.getLastName());
        if(application.getDateModified() != null) {
            saisApplication.setChanged(DateUtils.toLocalDateTime(application.getDateModified()));
            saisApplication.setSubmitted(DateUtils.toLocalDate(application.getDateModified()));
        }
        saisApplication.setIdcode(application.getIdCode());
        saisApplication.setSaisId(application.getId());
        if(application.getBirthday() != null) {
            saisApplication.setBirthdate(DateUtils.toLocalDate(application.getBirthday()));
        }
        if(application.getOtherIdNumber() != null) {
            saisApplication.setForeignIdcode(application.getOtherIdNumber());
        }

        //siia validate et ei tootleks mottetult avaldusi
        String error = "";
        error = validate(saisApplication, saisAdmission, prevApp, idCodeValidator);
        if(!error.isEmpty()) {
            return new SaisApplicationImportedRowDto(saisApplication.getApplicationNr(), saisApplication.getSubmitted(), error);
        }

        ArrayOfCandidateAddress addrArray = application.getCandidateAddresses();
        if(addrArray != null && !addrArray.getCandidateAddress().isEmpty()) {
            StringBuilder addrString = new StringBuilder();
            // take contact address, if possible
            CandidateAddress address = addrArray.getCandidateAddress().stream().filter(r -> CONTACT_ADDR_VALUE.equals(classifierValue(r.getAddressType())))
                    .findFirst().orElse(addrArray.getCandidateAddress().get(0));
            if(StringUtils.hasText(address.getAddress())) {
                addrString.append(address.getAddress());
            }
            if(StringUtils.hasText(address.getStreet()) && StringUtils.hasText(address.getHouse())) {
                if(addrString.length() > 0) {
                    addrString.append(", ");
                }
                addrString.append(address.getStreet());
                addrString.append(' ');
                addrString.append(address.getHouse());
                if(StringUtils.hasText(address.getApartment())) {
                    addrString.append('-');
                    addrString.append(address.getApartment());
                }
            }
            if(StringUtils.hasText(address.getPlaceOrCityPart())) {
                if(addrString.length() > 0) {
                    addrString.append(", ");
                }
                addrString.append(address.getPlaceOrCityPart());
            }
            if(StringUtils.hasText(address.getCity())) {
                if(addrString.length() > 0) {
                    addrString.append(", ");
                }
                addrString.append(address.getCity());
            }
            if(StringUtils.hasText(address.getCounty())) {
                if(addrString.length() > 0) {
                    addrString.append(", ");
                }
                addrString.append(address.getCounty());
            }
            if(StringUtils.hasText(address.getPostalcode())) {
                if(addrString.length() > 0) {
                    addrString.append(", ");
                }
                addrString.append(address.getPostalcode());
            }
            saisApplication.setAddress(addrString.toString());
            saisApplication.setAddressAds(address.getAdsAddressCode());
            saisApplication.setAddressAdsOid(address.getAdsOid());
        }

        String sexCode = classifierValue(application.getSexClassification());
        if (StringUtils.hasText(sexCode)) {
            saisApplication.setSex(classifiers.getByValue(sexCode, MainClassCode.SUGU));
        }

        saisApplication.setPhone(application.getPhone());
        saisApplication.setEmail(application.getEmail());
        saisApplication.setFin(classifiers.getByCode((application.isIsTuitionFeeRequired() ? FinSource.FINALLIKAS_REV : FinSource.FINALLIKAS_RE).name(), MainClassCode.FINALLIKAS));
        saisApplication.setPoints(application.getApplicationTotalPoints());
        saisApplication.setCitizenship(classifiers.getByValue(classifierValue(application.getCitizenshipCountry()), MainClassCode.RIIK));
        saisApplication.setStudyLoad(classifiers.getByCode((Boolean.TRUE.equals(application.isIsFullLoad()) ? StudyLoad.OPPEKOORMUS_TAIS : StudyLoad.OPPEKOORMUS_OSA).name(), MainClassCode.OPPEKOORMUS));
        // XXX is this correct
        saisApplication.setResidenceCountry(classifiers.getByCode(ClassifierUtil.COUNTRY_ESTONIA, MainClassCode.RIIK));
        saisApplication.setStudyForm(classifiers.getByValue(classifierValue(application.getStudyForm()), MainClassCode.OPPEVORM));
        saisApplication.setLanguage(saisAdmission.getLanguage());

        saisApplication.getGrades().clear();
        saisApplication.getGraduatedSchools().clear();
        saisApplication.getOtherData().clear();

        for(CandidateEducation education : application.getCandidateEducations().getCandidateEducation()) {
            processEducation(education, saisApplication, classifiers);
        }
        for(CandidateStateExam exam : application.getCandidateStateExams().getCandidateStateExam()) {
            processExam(exam, saisApplication);
        }
        for(ApplicationFormData data : application.getApplicationFormData().getApplicationFormData()) {
            processData(data, saisApplication);
        }

        if(application.getDateModified() != null) {
            saisApplication.setSaisChanged(DateUtils.toLocalDate(application.getDateModified()));
        }

        saisAdmission.getApplications().add(saisApplication);

        return new SaisApplicationImportedRowDto(EntityUtil.save(saisApplication, em), null);
    }

    private Map<Long, Long> directiveStudentsWithSaisApplication(List<Long> saisApplicationIds) {
        List<?> data = em.createNativeQuery("select ds.sais_application_id, ds.id from directive_student ds where "+
                "ds.sais_application_id in (?1) and ds.canceled = false")
            .setParameter(1, saisApplicationIds)
            .getResultList();
        return data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
    }

    //if returned string is empty then there is no error, otherwise returns a string containing the error message
    private static String validate(SaisApplication application, SaisAdmission saisAdmission,
            SaisApplication previousApplication, EstonianIdCodeValidator idCodeValidator) {
        String messageForMissing = String.format("Avaldusel nr %s puudub ", application.getApplicationNr());
        String messageForOther = String.format("Avaldusega nr %s ", application.getApplicationNr());

        if(saisAdmission == null) {
            return String.format("Avaldusele nr %s ei leitud konkurssi.", application.getApplicationNr());
        }
        if(application.getStatus() == null) {
            return messageForMissing + "avalduse staatus";
        }
        if(application.getFirstname() == null) {
            return messageForMissing + "kandideerija eesnimi";
        }
        if(application.getLastname() == null) {
            return messageForMissing + "kandideerija perekonnanimi";
        }
        if(application.getBirthdate() == null) {
            return messageForMissing + "kandideerija sünnikuupäev";
        }
        if(application.getSubmitted() == null) {
            return messageForMissing + "esitamise kuupäev";
        }
        if(!idCodeValidator.isValid(application.getIdcode(), null)) {
            return messageForOther + "seotud isiku isikukood ei ole korrektne";
        }
        if(previousApplication != null && !applicationForSamePerson(application, previousApplication)) {
            return String.format("Avaldus nr %s on süsteemis juba seotud teise isikuga (%s)", application.getApplicationNr(), previousApplication.getIdcode());
        }

        return "";
    }

    private static void processEducation(CandidateEducation education, SaisApplication application, ClassifierCache classifiers) {
        SaisApplicationGraduatedSchool graduated = new SaisApplicationGraduatedSchool();
        graduated.setName(education.getInstitutionName());
        if(education.getStudyBeginDate() != null) {
            graduated.setStartDate(DateUtils.toLocalDate(education.getStudyBeginDate()));
        }
        if(education.getStudyEndDate() != null) {
            graduated.setEndDate(DateUtils.toLocalDate(education.getStudyEndDate()));
        }
        graduated.setRegCode(education.getInstitutionRegNr() != null ? education.getInstitutionRegNr().toString() : "");
        graduated.setIsAbroad(education.getInstitutionCountry() == null || "EST".equals(classifierValue(education.getInstitutionCountry())) ? Boolean.FALSE : Boolean.TRUE);
        graduated.setStudyLevel(classifiers.getByValue(classifierValue(education.getEhisLevel()), MainClassCode.OPPEASTE));
        if(education.getStudyFormClassification() != null) {
            graduated.setStudyForm(classifiers.getByValue(classifierValue(education.getStudyFormClassification()), MainClassCode.OPPEVORM));
        }
        for(CandidateGrade grade : education.getCandidateGrades().getCandidateGrade()) {
            processGrade(grade, application);
        }
        application.getGraduatedSchools().add(graduated);
    }

    private static void processGrade(CandidateGrade grade, SaisApplication application) {
        if(grade.getReplacedResult() != null || grade.getGrade() != null) {
            SaisApplicationGrade applicationGrade = new SaisApplicationGrade();
            applicationGrade.setSubjectName(kvpHandler(grade.getCurriculumClassification().getTranslations(), ESTONIAN));
            applicationGrade.setSubjectType(grade.getCurriculumClassification().getClassificationTypeName());
            Integer result = grade.getReplacedResult();
            if(result == null) {
                result = grade.getGrade();
            }
            applicationGrade.setGrade(result.toString());
            application.getGrades().add(applicationGrade);
        }
    }

    private static void processExam(CandidateStateExam exam, SaisApplication application) {
        SaisApplicationGrade applicationGrade = new SaisApplicationGrade();
        applicationGrade.setSubjectName(kvpHandler(exam.getStateExamClassification().getTranslations(), ESTONIAN));
        applicationGrade.setSubjectType(exam.getStateExamClassification().getClassificationTypeName());
        Integer result = exam.getReplacedResult();
        if(result == null) {
            result = Integer.valueOf(exam.getResult());
        }
        applicationGrade.setGrade(result.toString());
        application.getGrades().add(applicationGrade);
    }

    private static void processData(ApplicationFormData data, SaisApplication application) {
        String name = kvpHandler(data.getFieldName(), ESTONIAN);
        if(name != null && (data.getSelectedOptions() != null || data.getValue() != null)) {
            // both otherDataName and otherDataValue are required fields
            SaisApplicationOtherData otherData = new SaisApplicationOtherData();
            otherData.setOtherDataName(name);
            if(data.getSelectedOptions() != null) {
                for(FormFieldOption ffo : data.getSelectedOptions().getFormFieldOption()) {
                    otherData.setOtherDataValue(kvpHandler(ffo.getName(), ESTONIAN));
                }
            } else {
                otherData.setOtherDataValue(data.getValue());
            }
            if(otherData.getOtherDataValue() != null) {
                application.getOtherData().add(otherData);
            }
        }
    }

    private static String kvpHandler(ArrayOfKvp kvpArray, String targetLanguage) {
        if(kvpArray != null) {
            for(Kvp kvp : kvpArray.getKvp()) {
                if(targetLanguage.equalsIgnoreCase(kvp.getKey())) {
                    return kvp.getValue();
                }
            }
        }
        return null;
    }

    private XRoadHeaderV4 getXroadHeader(HoisUserDetails user) {
        return sp.xroadHeader("AllApplicationsExport", em.getReference(Person.class, user.getPersonId()).getIdcode());
    }

    private AllAppsExportRequest getRequest(SaisApplicationImportForm form, Long schoolId, ClassifierCache classifiers) {
        AllAppsExportRequest request = new AllAppsExportRequest();
        if (form.getApplicationDateFrom() != null && form.getApplicationDateTo() != null) {
            GregorianCalendar gcal = GregorianCalendar.from(form.getApplicationDateFrom().atStartOfDay(ZoneId.systemDefault()));
            request.setStatusChangeDateFrom(datatypeFactory.newXMLGregorianCalendar(gcal));
            gcal = GregorianCalendar.from(form.getApplicationDateTo().atStartOfDay(ZoneId.systemDefault()));
            request.setStatusChangeDateTo(datatypeFactory.newXMLGregorianCalendar(gcal));
        }
        if(StringUtils.hasText(form.getIdCode())) {
            request.setIdCode(form.getIdCode());
        }
        if(form.getStatus() != null) {
            ArrayOfString aos = new ArrayOfString();
            for(String value : form.getStatus()) {
                aos.getString().add(classifiers.getByCode(value, MainClassCode.SAIS_AVALDUSESTAATUS).getEhisValue());
            }
            request.setApplicationStatusValues(aos);
        }
        if(form.getAdmissionCode() != null) {
            request.setAdmissionId(saisAdmissionRepository.findByCode(form.getAdmissionCode()).getSaisId());
        }
        ArrayOfInt aoi = new ArrayOfInt();
        Classifier ehisSchool = em.getReference(School.class, schoolId).getEhisSchool();
        Integer koolRegNr = Integer.valueOf(ehisSchool.getValue2());
        aoi.getInt().add(koolRegNr);
        request.setInstitutionRegCodes(aoi);
        request.setPage(objectFactory.createAllAppsExportRequestPage(Integer.valueOf(0)));
        return request;
    }

    private static String classifierValue(SAISClassification c) {
        return c != null ? c.getValue() : null;
    }

    private static boolean applicationForSamePerson(Application application, SaisApplication prevApp) {
        return Objects.equals(prevApp.getIdcode(), application.getIdCode()) && Objects.equals(prevApp.getForeignIdcode(), application.getOtherIdNumber());
    }

    private static boolean applicationForSamePerson(SaisApplication application, SaisApplication prevApp) {
        return Objects.equals(prevApp.getIdcode(), application.getIdcode()) && Objects.equals(prevApp.getForeignIdcode(), application.getForeignIdcode());
    }
}
