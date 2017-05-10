package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.domain.sais.SaisApplicationGraduatedSchool;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SaisApplicationStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationClassifiersCsv;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationCsvRow;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportedRowDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;

@Transactional
@Service
public class SaisApplicationService {
    private static final Logger log = LoggerFactory.getLogger(SaisApplicationService.class);

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
    private EntityManager em;

    @Autowired
    private SaisApplicationRepository saisApplicationRepository;

    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private AutocompleteService autocompleteService;

    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    @Autowired
    private Validator validator;

    private CsvMapper csvMapper = new CsvMapper();

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
        SaisApplication saisApplication = existingSaisApplication == null ? new SaisApplication() : existingSaisApplication;

        if (existingSaisApplication != null && StringUtils.hasText(existingSaisApplication.getIdcode()) &&
                StringUtils.hasText(row.getIdcode()) && !existingSaisApplication.getIdcode().equals(row.getIdcode())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, String.format(messageForOther + "on süsteemis juba seotud teise isikuga (%s).", existingSaisApplication.getIdcode())));
            return;
        }

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
            log.error(String.format(messageForOther + "seotud õppekava/rakenduskava kool %d ei kuulu kasutaja koolile %d.", admissionSchool, schoolId));
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud õppekava/rakenduskava kool ei ühti kasutaja kooliga."));
            return;
        }

        if (existingSaisApplication == null) {
            Set<ConstraintViolation<SaisApplication>> saisApplicationErrors = validator.validate(saisApplication, Default.class);
            if (!saisApplicationErrors.isEmpty()) {
                log.error("sais application validation failed: ["+ String.join("; ", StreamUtil.toMappedList(error -> error.getPropertyPath() + ": " + error.getMessage(), saisApplicationErrors)) + "]");
                failed.add(new SaisApplicationImportedRowDto(rowNr, "avalduse andmed on puudulikud salvestamiseks."));
                return;
            }
        }

        if (existingSaisAdmission == null) {
            Set<ConstraintViolation<SaisAdmission>> saisAdmissionErrors = validator.validate(saisAdmission, Default.class);
            if (!saisAdmissionErrors.isEmpty()) {
                log.error("sais admission validation failed: ["+ String.join("; ", StreamUtil.toMappedList(error -> error.getPropertyPath() + ": " + error.getMessage(), saisAdmissionErrors)) + "]");
                failed.add(new SaisApplicationImportedRowDto(rowNr, "konkursi andmed on puudulikud salvestamiseks."));
                return;
            }
        }


        if(EntityUtil.getNullableId(saisApplication) == null) {
            saisAdmission.getApplications().add(saisApplication);
        }
        saisAdmissionRepository.save(saisAdmission);

        dto.getSuccessful().add(new SaisApplicationImportedRowDto(rowNr, saisApplication.getApplicationNr()));

    }

    public String getSampleCsvFile() {
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

}

class ClassifierCache {
    private final ClassifierRepository repository;
    private final Map<MainClassCode, Map<String, Classifier>> classifiers = new HashMap<>();

    public ClassifierCache(ClassifierRepository repository) {
        this.repository = repository;
    }

    public Classifier get(String value, MainClassCode mainClassCode, Boolean isCode) {
        // FIXME should fetch all values by mainClassCode with single query?
        Map<String, Classifier> cache = classifiers.computeIfAbsent(mainClassCode, key -> new HashMap<>());
        Classifier c = cache.get(value);
        if(c == null) {
            if(cache.containsKey(value)) {
                return null;
            }
            c = StringUtils.hasText(value) ? getClassifier(value, mainClassCode, isCode) : null;
            cache.put(value, c);
        }
        return c;
    }

    private Classifier getClassifier(String valueOrCode, MainClassCode mainClassCode, Boolean isCode) {
        if (Boolean.TRUE.equals(isCode)) {
            return repository.getOne(valueOrCode);
        }
        return repository.findByValueAndMainClassCode(valueOrCode, mainClassCode.name());
    }

    public Classifier get(String value, MainClassCode mainClassCode) {
        return get(value, mainClassCode, Boolean.FALSE);
    }

    public Classifier getByCode(String value, MainClassCode mainClassCode) {
        return get(value, mainClassCode, Boolean.TRUE);
    }
}
