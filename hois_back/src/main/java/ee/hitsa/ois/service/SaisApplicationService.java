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

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SaisAdmission;
import ee.hitsa.ois.domain.SaisApplication;
import ee.hitsa.ois.domain.SaisApplicationGraduatedSchool;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SaisApplicationStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.web.commandobject.SaisApplicationCsvRow;
import ee.hitsa.ois.web.commandobject.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.SaisApplicationImportedRowDto;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;

@Transactional
@Service
public class SaisApplicationService {
    private static final Logger log = LoggerFactory.getLogger(SaisApplicationService.class);

    private static final List<String> REVOKED_APPLICATION_STATUSES = Arrays.asList(SaisApplicationStatus.SAIS_AVALDUSESTAATUS_AL.name(),
            SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TL.name(), SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TYH.name());

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
    private CurriculumVersionRepository curriculumVersionRepository;

    @Autowired
    private Validator validator;

    private CsvMapper csvMapper = new CsvMapper();

    public Page<SaisApplicationSearchDto> search(HoisUserDetails user, SaisApplicationSearchCommand criteria,
            Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SAIS_APPLICATION_FROM, pageable);

        qb.requiredCriteria("school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("sais_admission_code in (:code)", "code", criteria.getCode());
        if (Boolean.TRUE.equals(criteria.getAddedToDirective())) {
            qb.optionalCriteria("added_to_directive = :added_to_directive", "added_to_directive", criteria.getAddedToDirective());
        }

        qb.optionalContains(Arrays.asList("firstname", "lastname", "firstname || ' ' || lastname"), "name", criteria.getName());
        qb.optionalCriteria("idcode = :idcode", "idcode", criteria.getIdcode());

        qb.optionalCriteria("status_code in (:status)", "status", criteria.getStatus());

        List<String> revokedStatuses = new ArrayList<>(REVOKED_APPLICATION_STATUSES);
        if (!CollectionUtils.isEmpty(criteria.getStatus())) {
            revokedStatuses.removeAll(criteria.getStatus());
        }
        if (criteria.getShowRevoked() == null || Boolean.FALSE.equals(criteria.getShowRevoked())) {
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

        String messageForMissing = String.format("Avaldus nr. %s-l puudub ", applicationNr);
        String messageForOther = String.format("Avaldus nr. %s-ga ", applicationNr);

        String code = row.getCode();
        String curriculumVersionCode = row.getCurriculumVersionCode();
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(curriculumVersionCode)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "seotud konkursil puudub konkursi kood."));
            return;
        }

        // FIXME should add school as search parameter?
        CurriculumVersion curriculumVersion = curriculumVersionRepository.findByCode(curriculumVersionCode);
        SaisAdmission existingSaisAdmission = null;

        if (StringUtils.isEmpty(code)) {
            if (curriculumVersion == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther + "ei ole seotud õppekava/rakenduskava."));
                return;
            }
            // FIXME which one gets used from possible multiple admissions linked to given curriculum version?
            existingSaisAdmission = saisAdmissionRepository.findByCurriculumVersionId(EntityUtil.getId(curriculumVersion));
        } else {
            // FIXME should add also school to search parameters?
            existingSaisAdmission = saisAdmissionRepository.findByCode(code);
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

        // FIXME should use also school as search parameter?
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

        if (saisApplication.getStudyLoad() == null) {
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
            saisAdmission.setCode(code);
            saisAdmission.setName(code);
            saisAdmission.setSaisId(code);
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
        if (saisAdmission.getStudyLevel() == null && curriculumVersion != null && curriculumVersion.getCurriculum() != null) {
            saisAdmission.setStudyLevel(curriculumVersion.getCurriculum().getOrigStudyLevel());
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
                log.error("sais application validation failed: ["+ String.join("; ", saisApplicationErrors.stream().map(error -> error.getPropertyPath() + ": " + error.getMessage()).collect(Collectors.toList())) + "]");
                failed.add(new SaisApplicationImportedRowDto(rowNr, "avalduse andmed on puudulikud salvestamiseks."));
                return;
            }
        }

        if (existingSaisAdmission == null) {
            Set<ConstraintViolation<SaisAdmission>> saisAdmissionErrors = validator.validate(saisAdmission, Default.class);
            if (!saisAdmissionErrors.isEmpty()) {
                log.error("sais admission validation failed: ["+ String.join("; ", saisAdmissionErrors.stream().map(error -> error.getPropertyPath() + ": " + error.getMessage()).collect(Collectors.toList())) + "]");
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

}

class ClassifierCache {
    private final ClassifierRepository repository;
    private final Map<MainClassCode, Map<String, Classifier>> classifiers = new HashMap<>();

    public ClassifierCache(ClassifierRepository repository) {
        this.repository = repository;
    }

    public Classifier get(String value, MainClassCode mainClassCode) {
        // FIXME should fetch all values by mainClassCode with single query?
        Map<String, Classifier> cache = classifiers.computeIfAbsent(mainClassCode, key -> new HashMap<>());
        Classifier c = cache.get(value);
        if(c == null) {
            if(cache.containsKey(value)) {
                return null;
            }
            c = StringUtils.hasText(value) ? repository.findByValueAndMainClassCode(value, mainClassCode.name()) : null;
            cache.put(value, c);
        }
        return c;
    }
}

