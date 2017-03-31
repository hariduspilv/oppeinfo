package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.web.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.SaisApplicationImportedRowDto;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;

@Transactional
@Service
public class SaisApplicationService {
    private static final List<String> REVOKED_APPLICATION_STATUSES =
            Arrays.asList(SaisApplicationStatus.SAIS_AVALDUSESTAATUS_AL.name(), SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TL.name(), SaisApplicationStatus.SAIS_AVALDUSESTAATUS_TYH.name());

    private static final DateTimeFormatter CSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");

    @Autowired
    private SaisApplicationRepository saisApplicationRepository;

    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    public Page<SaisApplicationSearchDto> search(HoisUserDetails user, SaisApplicationSearchCommand criteria,
            Pageable pageable) {
        return saisApplicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if (!CollectionUtils.isEmpty(criteria.getCode())) {
                filters.add(root.get("saisAdmission").get("code").in(criteria.getCode()));
            }

            List<String> revokedStatuses = new ArrayList<>(REVOKED_APPLICATION_STATUSES);
            if (!CollectionUtils.isEmpty(criteria.getStatus())) {
                filters.add(root.get("status").get("code").in(criteria.getStatus()));
                revokedStatuses.removeAll(criteria.getStatus());
            }

            if (criteria.getShowRevoked() == null || Boolean.FALSE.equals(criteria.getShowRevoked())) {
                filters.add(cb.not(root.get("status").get("code").in(revokedStatuses)));
            }

            if(!StringUtils.isEmpty(criteria.getName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("firstname"), cb, criteria.getName(), name::add);
                propertyContains(() -> root.get("lastname"), cb, criteria.getName(), name::add);
                name.add(cb.like(cb.concat(cb.upper(root.get("firstname")), cb.concat(" ", cb.upper(root.get("lastname")))), SearchUtil.toContains(criteria.getName())));
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }

            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("idcode"), criteria.getIdcode()));
            }


            if (Boolean.TRUE.equals(criteria.getAddedToDirective())) {
                //TODO
            }

            filters.add(cb.equal(root.get("saisAdmission").get("curriculumVersion").get("curriculum").get("school").get("id"), user.getSchoolId()));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SaisApplicationSearchDto::of);
    }



    public SaisApplicationImportResultDto importCsv(byte[] fileData) {
        String csvFile = new String(fileData, StandardCharsets.UTF_8);
        SaisApplicationImportResultDto dto = new SaisApplicationImportResultDto();

        String[] lines = csvFile.split("\r\n|\r|\n");

        // TODO if there are no rows at all?
        // TODO optional utf-8 BOM at file start?
        List<String> headers = Arrays.asList(lines[0].split(";"));
        CsvFileReaderHelper helper = new CsvFileReaderHelper(headers, lines, ";");
        EstonianIdCodeValidator idCodeValidator = new EstonianIdCodeValidator();
        List<SaisApplicationImportedRowDto> failed = dto.getFailed();
        ClassifierCache classifiers = new ClassifierCache(classifierRepository);
        for (int rowNr = 1; rowNr < lines.length; rowNr++) {
            helper.initForRow(rowNr);
            try {
                proccessRow(helper, classifiers, idCodeValidator, failed, rowNr, dto);
            } catch (@SuppressWarnings("unused") Exception e) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, "Viga rea töötlemisel"));
                continue;
            }
        }
        return dto;
    }

    private void proccessRow(CsvFileReaderHelper helper, ClassifierCache classifiers, EstonianIdCodeValidator idCodeValidator, List<SaisApplicationImportedRowDto> failed, int rowNr,SaisApplicationImportResultDto dto) {
        String applicationNr = helper.get("AvalduseNr");
        if (!StringUtils.hasText(applicationNr)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, "Puudub avalduse number"));
            return;
        }
        SaisApplication existingSaisApplication = saisApplicationRepository.findByApplicationNr(applicationNr);
        SaisApplication saisApplication = existingSaisApplication == null ? new SaisApplication() : existingSaisApplication;

        String messageForMissing = String.format("Avaldus nr. %s-l puudub ", applicationNr);
        String messageForOther = String.format("Avaldus nr. %s-ga ", applicationNr);

        String firstname = helper.get("Eesnimi");
        if (!StringUtils.hasText(firstname)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"kandideerija eesnimi."));
            return;
        }

        String lastname = helper.get("Perekonnanimi");
        if (!StringUtils.hasText(lastname)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"kandideerija perekonnanimi."));
            return;
        }

        String idcode = helper.get("Isikukood");
        if (!idCodeValidator.isValid(idcode, null)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+" seotud isiku isikukood ei ole korrektne."));
            return;
        }

        LocalDate saisChanged = parseCsvDate(helper.get("AvalduseMuutmiseKp"));
        if (StringUtils.isEmpty(helper.get("AvalduseMuutmiseKp"))) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"muutmise kuupäev."));
            return;
        } else if (saisChanged == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+" seotud muutmise kuupäev on vigane."));
            return;
        }

        String statusValue = helper.get("AvalduseStaatus");
        Classifier status = classifiers.get(statusValue, MainClassCode.SAIS_AVALDUSESTAATUS);
        if (status == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"avalduse staatus."));
            return;
        }

        String citicenshipValue = helper.get("Kodakondsus");
        Classifier citicenship = classifiers.get(citicenshipValue, MainClassCode.RIIK);
        if (citicenship == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"kodakondsus."));
            return;
        }

        String residenceCountryValue = helper.get("Elukohariik");
        Classifier residenceCountry = classifiers.get(residenceCountryValue, MainClassCode.RIIK);
        if (residenceCountry == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"elukohariik."));
            return;
        }

        String finValue = helper.get("Finantseerimisallikas");
        Classifier fin = classifiers.get(finValue, MainClassCode.FINALLIKAS);
        if (fin == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"finantseerimisallikas."));
            return;
        }

        String code = helper.get("KonkursiKood");
        String curriculumVersionCode = helper.get("Oppekava/RakenduskavaKood");
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(curriculumVersionCode)) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+"seotud konkursil puudub konkursi kood."));
            return;
        }

        CurriculumVersion curriculumVersion = curriculumVersionRepository.findByCode(curriculumVersionCode);
        SaisAdmission existingSaisAdmission = null;

        if (StringUtils.isEmpty(code)) {
            if (curriculumVersion == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+" ei ole seotud õppekava/rakenduskava."));
                return;
            }
            existingSaisAdmission = saisAdmissionRepository.findByCurriculumVersionId(EntityUtil.getId(curriculumVersion));
        } else {
            existingSaisAdmission = saisAdmissionRepository.findByCode(code);
        }
        SaisAdmission saisAdmission = existingSaisAdmission == null ? new SaisAdmission() : existingSaisAdmission;
        if (existingSaisAdmission == null) {
            saisAdmission.setCurriculumVersion(curriculumVersion);
        }

        if (saisAdmission.getCurriculumVersion() == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+" seotud õppekava versioonile/rakenduskavale ei leitud vastet."));
            return;
        } else if (curriculumVersion == null){
            curriculumVersion = saisAdmission.getCurriculumVersion();
        }

        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(saisAdmission.getCode())) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+"seotud konkursil puudub konkursi kood."));
            return;
        }


        String studyLoadValue = helper.get("Oppekoormus");
        Classifier studyLoad = classifiers.get(studyLoadValue, MainClassCode.OPPEKOORMUS);
        if (studyLoad == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"õppekoormus."));
            return;
        }

        String studyFormValue = helper.get("Oppevorm");
        Classifier studyForm = classifiers.get(studyFormValue, MainClassCode.OPPEVORM);
        if (studyForm == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"õppevorm."));
            return;
        }

        String languageValue = helper.get("Oppekeel");
        Classifier language = classifiers.get(languageValue, MainClassCode.OPPEKEEL);
        if (language == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"õppekeel."));
            return;
        }

        String previousStudyLevelValue = helper.get("EelnevOppetase");
        Classifier previousStudyLevel = classifiers.get(previousStudyLevelValue, MainClassCode.OPPEASTE);
        if (previousStudyLevel == null) {
            failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"eelmine õppetase."));
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

        saisApplication.setApplicationNr(applicationNr);
        saisApplication.setFirstname(firstname);
        saisApplication.setLastname(lastname);
        saisApplication.setIdcode(idcode);
        saisApplication.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
        saisApplication.setCitizenship(citicenship);
        saisApplication.setResidenceCountry(residenceCountry);
        saisApplication.setFin(fin);
        saisApplication.setSaisChanged(saisChanged);
        saisApplication.setStatus(status);
        saisApplication.setStudyLoad(studyLoad);
        saisApplication.setStudyForm(studyForm);
        saisApplication.setLanguage(language);
        if (saisApplication.getSubmitted() == null) {
            saisApplication.setSubmitted(saisChanged);
        }


        if (saisAdmission.getCode() == null) {
            saisAdmission.setCode(code);
            saisAdmission.setName(code);
            saisAdmission.setSaisId(code);
        }
        if (saisAdmission.getFin() == null) {
            saisAdmission.setFin(fin);
        }
        if (saisAdmission.getCurriculumVersion() == null) {
            saisAdmission.setCurriculumVersion(curriculumVersion);
        }
        if (saisAdmission.getStudyLoad() == null) {
            saisAdmission.setStudyLoad(studyLoad);
        }
        if (saisAdmission.getStudyLevel() == null && curriculumVersion != null && curriculumVersion.getCurriculum() != null) {
            saisAdmission.setStudyLevel(curriculumVersion.getCurriculum().getOrigStudyLevel());
        }
        if (saisAdmission.getStudyForm() == null) {
            saisAdmission.setStudyForm(studyForm);
        }
        if(saisAdmission.getLanguage() == null) {
            saisAdmission.setLanguage(language);
        }
        if (saisAdmission.getPeriodStart() == null) {
            LocalDate periodStart = parseCsvDate(helper.get("KonkursiAlgusKp"));
            if (StringUtils.isEmpty(helper.get("KonkursiAlgusKp"))) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"konkursi alguse kuupäev."));
                return;
            } else if (periodStart == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+" seotud konkursi alguse kuupäev on vigane."));
                return;
            }
            saisAdmission.setPeriodStart(periodStart);
        }
        if(saisAdmission.getPeriodEnd() == null) {
            LocalDate periodEnd = parseCsvDate(helper.get("KonkursiLõppKp"));
            if (StringUtils.isEmpty(helper.get("KonkursiLõppKp"))) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForMissing+"konkursi lõpu kuupäev."));
                return;
            } else if (periodEnd == null) {
                failed.add(new SaisApplicationImportedRowDto(rowNr, messageForOther+" seotud konkursi lõpu kuupäev on vigane."));
                return;
            }
            saisAdmission.setPeriodEnd(periodEnd);
        }



        if(EntityUtil.getNullableId(saisApplication) == null) {
            saisAdmission.getApplications().add(saisApplication);
        }
        saisAdmissionRepository.save(saisAdmission);

        dto.getSuccessful().add(new SaisApplicationImportedRowDto(rowNr, applicationNr));

    }



    private static LocalDate parseCsvDate(String dateString) {
        try {
            return LocalDate.parse(dateString, CSV_DATE_FORMATTER);
        } catch (@SuppressWarnings("unused") Exception e) {
            return null;
        }
    }

    private static class ClassifierCache {
        private final ClassifierRepository repository;
        private final Map<MainClassCode, Map<String, Classifier>> classifiers = new HashMap<>();

        public ClassifierCache(ClassifierRepository repository) {
            this.repository = repository;
        }

        public Classifier get(String value, MainClassCode mainClassCode) {
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
}

class CsvFileReaderHelper {
    private String[] values;
    private String[] lines;
    private String delimiter;
    private List<String> headers;

    public CsvFileReaderHelper(List<String> headers, String[] lines, String delimiter) {
        this.lines = lines;
        this.headers = headers;
        this.delimiter = delimiter;
    }

    public void initForRow(int i) {
        values = lines[i].split(delimiter);
    }

    public String get(String name) {
        int pos = headers.indexOf(name);
        if(pos == -1) {
            // FIXME should throw exception?
            return null;
        }
        return values[pos];
    }
}
