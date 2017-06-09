package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
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
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SaisApplicationImportForm;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationClassifiersCsv;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationCsvRow;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportedRowDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;
import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.helpers.sais.SaisApplicationResponse;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.Application;
import ee.hois.xroad.sais2.generated.ApplicationFormData;
import ee.hois.xroad.sais2.generated.ArrayOfInt;
import ee.hois.xroad.sais2.generated.ArrayOfString;
import ee.hois.xroad.sais2.generated.CandidateAddress;
import ee.hois.xroad.sais2.generated.CandidateEducation;
import ee.hois.xroad.sais2.generated.CandidateGrade;
import ee.hois.xroad.sais2.generated.CandidateStateExam;
import ee.hois.xroad.sais2.generated.FormFieldOption;
import ee.hois.xroad.sais2.generated.Kvp;
import ee.hois.xroad.sais2.service.SaisService;

@Transactional
@Service
public class SaisApplicationService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String ESTONIAN = "ESTONIAN";

    private static final List<String> REVOKED_APPLICATION_STATUSES = Arrays.asList(SaisApplicationStatus.SAIS_AVALDUSESTAATUS_AL.name(),
            SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TL.name(), SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TYH.name());

    private static final List<String> CLASSIFIERS_LIST = Arrays.asList(MainClassCode.FINALLIKAS.name(), MainClassCode.RIIK.name(),
            MainClassCode.SAIS_AVALDUSESTAATUS.name(), MainClassCode.OPPEASTE.name(), MainClassCode.OPPEKEEL.name(), MainClassCode.OPPEKOORMUS.name(), MainClassCode.OPPEVORM.name());

    private static final String SAIS_APPLICATION_FROM = "from (select a.id, a.application_nr, a.idcode, a.firstname, a.lastname, a.status_code,"+
            "sais_admission.code as sais_admission_code, (exists (select id from directive_student where directive_student.sais_application_id = a.id)) as added_to_directive, curriculum.school_id as school_id from sais_application a "+
            "inner join sais_admission on sais_admission.id = a.sais_admission_id "+
            "inner join classifier status on a.status_code = status.code "+
            "left join curriculum_version on curriculum_version.id = sais_admission.curriculum_version_id "+
            "left join curriculum on curriculum.id = curriculum_version.curriculum_id) as sais_application_dto";

    private static final String SAIS_APPLICATION_SELECT = "id, application_nr, idcode, firstname, lastname, status_code, sais_admission_code, added_to_directive, school_id";

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private ClassifierRepository classifierRepository;
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
    private Validator validator;
    @Autowired
    private SaisProperties sp;
    @Autowired
    private DirectiveService directiveService;

    private final SaisService saisService = new SaisService();
    private final CsvMapper csvMapper = new CsvMapper();

    public Page<SaisApplicationSearchDto> search(HoisUserDetails user, SaisApplicationSearchCommand criteria,
            Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SAIS_APPLICATION_FROM).sort(pageable);

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

        return JpaQueryUtil.pagingResult(qb, SAIS_APPLICATION_SELECT, em, pageable).map(r -> {
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
        ClassifierCache classifiers = new ClassifierCache(classifierRepository);

        String fileContent = getContent(fileData);

        int rowNr = 1;
        try (MappingIterator<SaisApplicationCsvRow> csvValues = csvMapper.readerFor(SaisApplicationCsvRow.class).with(schema).readValues(fileContent)) {
            while (csvValues.hasNext()) {
                SaisApplicationCsvRow row = csvValues.next();
                try {
                      proccessRow(row, rowNr, failed, dto, classifiers, idCodeValidator, user.getSchoolId());
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
     * TODO: Avalduse andmeid ei muudeta siis, kui avaldus on juba lisatud immatrikuleerimise käskkirjale.
     */
    private void proccessRow(SaisApplicationCsvRow row, int rowNr, List<SaisApplicationImportedRowDto> failed,
            SaisApplicationImportResultDto dto, ClassifierCache classifiers, EstonianIdCodeValidator idCodeValidator,
            Long schoolId) {

        String applicationNr = row.getApplicationNr();
        if (!StringUtils.hasText(applicationNr)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, "Puudub avalduse number"));
            return;
        }

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
        } else if (curriculumVersion == null){
            curriculumVersion = saisAdmission.getCurriculumVersion();
        }

        SaisApplication existingSaisApplication = saisApplicationRepository.findByApplicationNrAndSaisAdmissionCode(applicationNr, saisAdmission.getCode());

        if (existingSaisApplication != null && StringUtils.hasText(existingSaisApplication.getIdcode()) &&
                StringUtils.hasText(row.getIdcode()) && !existingSaisApplication.getIdcode().equals(row.getIdcode())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, String.format(messageForOther + "on süsteemis juba seotud teise isikuga (%s).", existingSaisApplication.getIdcode())));
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
        if (saisApplication.getStudyLoad() == null && CurriculumUtil.isHigher(saisAdmission.getStudyLevel())) {
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
        Classifier previousStudyLevel = classifiers.get(previousStudyLevelValue, MainClassCode.OPPEASTE);
        if (previousStudyLevel == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing + "eelmine õppetase."));
            return;
        }

        List<SaisApplicationGraduatedSchool> existing = saisApplication.getGraduatedSchools().stream()
                .filter(it -> EntityUtil.getCode(previousStudyLevel).equals(EntityUtil.getNullableCode(it.getStudyLevel()))).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(existing)) {
            SaisApplicationGraduatedSchool sags = new SaisApplicationGraduatedSchool();
            sags.setStudyLevel(previousStudyLevel);
            saisApplication.getGraduatedSchools().add(sags);
        } else {
            existing.get(0).setStudyLevel(previousStudyLevel);
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
            log.error(messageForOther + "seotud õppekava/rakenduskava kool {} ei kuulu kasutaja koolile {}.", admissionSchool, schoolId);
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
        saisAdmissionRepository.save(saisAdmission);

        dto.getSuccessful().add(new SaisApplicationImportedRowDto(rowNr, saisApplication.getApplicationNr()));

    }

    public String getSampleCsvFile() {
        // TODO use letters šŠžŽ too
        return "KonkursiKood;AvalduseNr;Eesnimi;Perekonnanimi;Isikukood;Kodakondsus;Elukohariik;Finantseerimisallikas;AvalduseMuutmiseKp;AvalduseStaatus;OppekavaVersioon/RakenduskavaKood;Oppekoormus;Oppevorm;Oppekeel;OppuriEelnevOppetase;KonkursiAlgusKp;KonkursiLõppKp\n"+
               "FIL12/12;Nr123;Mari;Maasikas;49011112345;EST;EST;RE;1.01.2012;T;FIL12/12;TAIS;P;E;411;1.12.2011;1.02.2012\n"+
               "MAT15/16;Nr456;Tõnu;Kuut;39311112312;FIN;EST;REV;3.03.2012;T;MAT15/16;OSA;P;I;411;1.01.2012;3.04.2012\n"+
               "MAT15/16;Nr321;Tiiu;Kask;49302052312;EST;EST;RE;12.02.2012;T;MAT15/16;OSA;K;E;411;1.01.2012;3.04.2012";
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
        SaisApplicationResponse applicationResponse = null;
        SaisApplicationImportResultDto dto = new SaisApplicationImportResultDto();
        ClassifierCache classifiers = new ClassifierCache(classifierRepository);

        try {
            XRoadHeader xRoadHeader = getHeader(user);
            AllAppsExportRequest request = getRequest(form, user, classifiers);
            applicationResponse = saisService.applicationsExport(xRoadHeader, request);
            applicationResponse.setQueryStart(LocalDateTime.now());
            if(!Boolean.FALSE.equals(applicationResponse.getxRoadErrors())) {
                EstonianIdCodeValidator idCodeValidator = new EstonianIdCodeValidator();
                List<String> previousApplicationNrs = new ArrayList<>();
                Map<String, SaisAdmission> admissionMap = new HashMap<>();
                //look for all the application numbers to find previously imported applications with one query
                for(Application application : applicationResponse.getAppExportResponse().getApplications().getApplication()) {
                    previousApplicationNrs.add(application.getApplicationNumber());
                }
                Map<String, SaisApplication> previousApplications= saisApplicationRepository.findAllByApplicationNrIn(previousApplicationNrs)
                        .stream().collect(Collectors.toMap(SaisApplication::getApplicationNr, a -> a));
                Map<Long, Long> prevDirectives = null;
                if(!previousApplications.isEmpty()) {
                    prevDirectives = directiveService.directiveStudentsWithSaisApplication(previousApplications.values().stream().map(SaisApplication::getId).collect(Collectors.toList()));
                }
                for(Application application : applicationResponse.getAppExportResponse().getApplications().getApplication()) {
                    SaisApplication prevApp = previousApplications.get(application.getApplicationNumber());
                    if(prevApp != null && prevDirectives != null && prevDirectives.containsKey(prevApp.getId())) {
                        String error = String.format("Avaldusega nr %s on seotud käskkiri - seda ei uuendata.", application.getApplicationNumber());
                        dto.getFailed().add(new SaisApplicationImportedRowDto(application.getApplicationNumber(), error));
                    } else {
                        SaisApplicationImportedRowDto importedRow = processApplication(application, prevApp, classifiers, admissionMap, idCodeValidator);
                        if(importedRow.getMessage() == null || importedRow.getMessage().isEmpty()) {
                            dto.getSuccessful().add(importedRow);
                        } else {
                            dto.getFailed().add(importedRow);
                        }
                    }
                }
                applicationResponse.setQueryEnd(LocalDateTime.now());
            } else {
                throw new ValidationFailedException("reception.saisApplication.xRoadCommunication");
            }
        } catch (Exception e) {
            if(applicationResponse == null) {
                applicationResponse = new SaisApplicationResponse();
            }
            applicationResponse.setError("Avalduste importimisel tekkis exception: " + ExceptionUtil.exceptionToStackTraceString(e));
            applicationResponse.setxRoadErrors(Boolean.TRUE);
            applicationResponse.setQueryName("sais2.AllApplicationsExport.v1");
            log.error("Exception in SaisApplicationService.importFromSais: ", e);
        }
        List<SaisApplicationImportedRowDto> result = Stream.concat(dto.getFailed().stream(), dto.getSuccessful().stream())
                .collect(Collectors.toList());
        saisLogService.insertLog(applicationResponse, user, result.stream().collect(Collectors
                .toMap(SaisApplicationImportedRowDto::getApplicationNr, SaisApplicationImportedRowDto::toString)).toString());

        return dto;
    }

    private SaisApplicationImportedRowDto processApplication(Application application, SaisApplication prevApp, ClassifierCache classifiers,
            Map<String, SaisAdmission> admissionMap,
            EstonianIdCodeValidator idCodeValidator) {
        SaisApplication saisApplication;
        SaisAdmission saisAdmission = null;
        if(prevApp != null && prevApp.getIdcode().equals(application.getIdCode())) {
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
        saisApplication.setStatus(classifiers.getByEhisValue(application.getApplicationStatus().getValue(), MainClassCode.SAIS_AVALDUSESTAATUS));
        saisApplication.setFirstname(application.getFirstName());
        saisApplication.setLastname(application.getLastName());
        if(application.getDateModified() != null) {
            Date modifiedDate = application.getDateModified().toGregorianCalendar().getTime();
            saisApplication.setChanged(LocalDateTime.ofInstant(modifiedDate.toInstant(), ZoneId.systemDefault()));
            saisApplication.setSubmitted(LocalDateTime.ofInstant(modifiedDate.toInstant(), ZoneId.systemDefault()).toLocalDate());
        }
        saisApplication.setIdcode(application.getIdCode());

        //siia validate et ei tootleks mottetult avaldusi
        String error = "";
        error = validate(saisApplication, saisAdmission, prevApp, idCodeValidator);
        if(!error.isEmpty()) {
            return new SaisApplicationImportedRowDto(saisApplication.getApplicationNr(), saisApplication.getSubmitted(), error);
        }

        saisApplication.setSaisId(application.getId());
        if(application.getBirthday() != null) {
            Date startDate = application.getBirthday().toGregorianCalendar().getTime();
            saisApplication.setBirthdate(LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()).toLocalDate());
        }

        if(application.getOtherIdNumber() != null) {
            saisApplication.setForeignIdcode(application.getOtherIdNumber());
        }

        String address = "";
        for(CandidateAddress currAddress : application.getCandidateAddresses().getCandidateAddress()) {
            if(currAddress != null) {
                address += currAddress.getAddress();
                address += currAddress.getPlaceOrCityPart().isEmpty() ?  "" : ", " + currAddress.getPlaceOrCityPart();
                address += currAddress.getCity().isEmpty() ? "" : ", " + currAddress.getCity();
                address += currAddress.getCounty().isEmpty() ? "" : ", " + currAddress.getCounty();
            }
        }
        saisApplication.setAddress(address);

        String sexCode = EstonianIdCodeValidator.sexFromIdcode(application.getSexClassification().getValue());
        if (StringUtils.hasText(sexCode)) {
            saisApplication.setSex(classifiers.getByCode(sexCode, MainClassCode.SUGU));
        }

        saisApplication.setPhone(application.getPhone());
        saisApplication.setEmail(application.getEmail());
        saisApplication.setFin(classifiers.getByCode((application.isIsTuitionFeeRequired() ? FinSource.FINALLIKAS_REV : FinSource.FINALLIKAS_RE).name(), MainClassCode.FINALLIKAS));
        saisApplication.setPoints(application.getApplicationTotalPoints());
        saisApplication.setCitizenship(classifiers.get(application.getCitizenshipCountry().getValue(), MainClassCode.RIIK));
        saisApplication.setStudyLoad(classifiers.getByCode((Boolean.TRUE.equals(application.isIsFullLoad()) ? StudyLoad.OPPEKOORMUS_TAIS : StudyLoad.OPPEKOORMUS_OSA).name(), MainClassCode.OPPEKOORMUS));
        if(application.getResidenceCountry() != null) {
            saisApplication.setResidenceCountry(classifiers.get(application.getResidenceCountry().getValue(), MainClassCode.RIIK));
        } else {
            saisApplication.setResidenceCountry(classifiers.get("EST", MainClassCode.RIIK));
        }
        saisApplication.setStudyForm(classifiers.get(application.getStudyForm().getValue(), MainClassCode.OPPEVORM));
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

        saisAdmission.getApplications().add(saisApplication);
        saisAdmissionRepository.save(saisAdmission);

        return new SaisApplicationImportedRowDto(saisApplication, null);
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
        if(application.getSubmitted() == null) {
            return messageForMissing + "esitamise kuupäev";
        }
        if(!idCodeValidator.isValid(application.getIdcode(), null)) {
            return messageForOther + "seotud isiku isikukood ei ole korrektne";
        }
        if(previousApplication != null && !application.getIdcode().equals(previousApplication.getIdcode())) {
            return String.format("Avaldus nr %s on süsteemis juba seotud teise isikuga (%s)", application.getApplicationNr(), previousApplication.getIdcode());
        }

        return "";
    }

    private static void processEducation(CandidateEducation education, SaisApplication application, ClassifierCache classifiers) {
        SaisApplicationGraduatedSchool graduated = new SaisApplicationGraduatedSchool();
        graduated.setName(education.getInstitutionName());
        if(education.getStudyBeginDate() != null) {
            Date startDate = education.getStudyBeginDate().toGregorianCalendar().getTime();
            graduated.setStartDate(LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()));
        }
        if(education.getStudyEndDate() != null) {
            Date endDate = education.getStudyEndDate().toGregorianCalendar().getTime();
            graduated.setEndDate(LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()));
        }
        graduated.setRegCode(education.getInstitutionRegNr() != null ? education.getInstitutionRegNr().toString() : "");
        graduated.setIsAbroad(education.getInstitutionCountry() == null || "EST".equals(education.getInstitutionCountry().getValue()) ? Boolean.FALSE : Boolean.TRUE);
        graduated.setStudyLevel(classifiers.get(education.getEhisLevel().getValue(), MainClassCode.OPPEASTE));
        if(education.getStudyFormClassification() != null) {
            graduated.setStudyForm(classifiers.get(education.getStudyFormClassification().getValue(), MainClassCode.OPPEVORM));
        }
        for(CandidateGrade grade : education.getCandidateGrades().getCandidateGrade()) {
            processGrade(grade, application);
        }
        application.getGraduatedSchools().add(graduated);
    }

    private static void processGrade(CandidateGrade grade, SaisApplication application) {
        SaisApplicationGrade applicationGrade = new SaisApplicationGrade();
        applicationGrade.setSubjectName(kvpHandler(grade.getCurriculumClassification().getTranslations().getKvp(), ESTONIAN));
        applicationGrade.setSubjectName(grade.getCurriculumClassification().getClassificationTypeName());
        applicationGrade.setGrade(grade.getGrade() != null ? grade.getGrade().toString() : "");
        application.getGrades().add(applicationGrade);
    }

    private static void processExam(CandidateStateExam exam, SaisApplication application) {
        SaisApplicationGrade applicationGrade = new SaisApplicationGrade();
        applicationGrade.setSubjectName(kvpHandler(exam.getStateExamClassification().getTranslations().getKvp(), ESTONIAN));
        applicationGrade.setSubjectName(exam.getStateExamClassification().getClassificationTypeName());
        applicationGrade.setGrade(Integer.toString(exam.getResult()));
        application.getGrades().add(applicationGrade);
    }

    private static void processData(ApplicationFormData data, SaisApplication application) {
        SaisApplicationOtherData otherData = new SaisApplicationOtherData();
        otherData.setOtherDataName(kvpHandler(data.getFieldName().getKvp(), ESTONIAN));
        for(FormFieldOption ffo : data.getSelectedOptions().getFormFieldOption()) {
            otherData.setOtherDataValue(kvpHandler(ffo.getName().getKvp(), ESTONIAN));
        }
        application.getOtherData().add(otherData);
    }

    private static String kvpHandler(List<Kvp> kvpList, String targetLanguage) {
        for(Kvp kvp : kvpList) {
            if(kvp.getKey().toUpperCase().equals(targetLanguage)) {
                return kvp.getValue();
            }
        }
        return null;
    }

    private XRoadHeader getHeader(HoisUserDetails user) {
        XRoadHeader xRoadHeader = new XRoadHeader();

        xRoadHeader.setConsumer(sp.getConsumer());
        xRoadHeader.setEndpoint(sp.getEndpoint());
        xRoadHeader.setProducer(sp.getProducer());
        xRoadHeader.setUserId(sp.getUseridprefix() + em.getReference(Person.class, user.getPersonId()).getIdcode());
        xRoadHeader.setId(UUID.randomUUID().toString());
        xRoadHeader.setService("sais2.AllApplicationsExport.v1");
        return xRoadHeader;
    }

    private AllAppsExportRequest getRequest(SaisApplicationImportForm form, HoisUserDetails user, ClassifierCache classifiers) {
        AllAppsExportRequest request = new AllAppsExportRequest();
        try {
            if (form.getApplicationDateFrom() != null && form.getApplicationDateTo() != null) {
                GregorianCalendar gcal = GregorianCalendar.from(form.getApplicationDateFrom().atStartOfDay(ZoneId.systemDefault()));
                request.setStatusChangeDateFrom(DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal));
                gcal = GregorianCalendar.from(form.getApplicationDateTo().atStartOfDay(ZoneId.systemDefault()));
                request.setStatusChangeDateTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal));
            }
            if(form.getIdCode() != null && !form.getIdCode().equals("")) {
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
            Classifier ehisSchool = em.getReference(School.class, user.getSchoolId()).getEhisSchool();
            Integer koolRegNr = Integer.valueOf(ehisSchool.getValue2());
            aoi.getInt().add(koolRegNr);
            request.setInstitutionRegCodes(aoi);
        } catch (DatatypeConfigurationException e) {
            log.error(e.getMessage(), e);
        }
        return request;
    }
}
