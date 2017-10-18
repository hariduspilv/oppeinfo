package ee.hitsa.ois.web;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.sais.SaisApplicationImportCsvCommand;
import ee.hitsa.ois.web.dto.sais.SaisApplicationDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.sais.SaisApplicationSearchDto;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SaisApplicationControllerTests {

    private static final String ENDPOINT = "/saisApplications";
    private static final String ENDPOINT_IMPORT_CSV = "/saisApplications/importCsv";

    private static final String CSV_HEADER = "KonkursiKood;AvalduseNr;Eesnimi;Perekonnanimi;Isikukood;Kodakondsus;Elukohariik;Finantseerimisallikas;AvalduseMuutmiseKp;AvalduseStaatus;Oppekava/RakenduskavaKood;Oppekoormus;Oppevorm;Oppekeel;EelnevOppetase;KonkursiAlgusKp;KonkursiLõppKp";
    private static final String ADMISSION_CODE = "SaisApplicationControllerTests/1";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;
    @Autowired
    private SaisApplicationRepository saisApplicationRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private PlatformTransactionManager txManager;

    private Directive directive;

    @After
    public void cleanUp() {
        if (directive != null) {
            TransactionTemplate txTemplate = new TransactionTemplate(txManager);
            txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            directive = txTemplate.execute((TransactionStatus status) -> {
                directiveRepository.delete(directive);
                return null;
            });
        }
        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(ADMISSION_CODE);
        if (saisAdmission != null) {
            saisAdmissionRepository.delete(saisAdmission);
        }
        testConfigurationService.setSessionCookie(null);
    }

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @Test
    public void search() {
        ResponseEntity<SaisApplicationSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, SaisApplicationSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("code", "test1", "test2");
        uriBuilder.queryParam("status", "SAIS_AVALDUSESTAATUS_TYH", "SAIS_AVALDUSESTAATUS_ML");
        uriBuilder.queryParam("name", "test");
        uriBuilder.queryParam("idcode", "123456789");
        uriBuilder.queryParam("showRevoked", "false");
        uriBuilder.queryParam("addedToDirective", "false");

        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), SaisApplicationSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        //create
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(
                ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());

        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(ADMISSION_CODE);
        Long saisApplicationId = saisAdmission.getApplications().stream().findFirst().get().getId();

        //read
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(saisApplicationId.toString());
        ResponseEntity<Object> response = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void importCsvValid() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(
                ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().get(0).getRowNr());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("code", ADMISSION_CODE);
        uriBuilder.queryParam("idcode", "47810010009");

        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> searchResponseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Map.class);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());
        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<Map> applications = (List<Map>) searchResponseEntity.getBody().get("content");

        Assert.assertEquals(1, applications.size());

        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applications.get(0).get("id").toString());
        ResponseEntity<SaisApplicationDto> response = restTemplate.getForEntity(uriBuilder.toUriString(), SaisApplicationDto.class);
        Assert.assertNotNull(response.getBody().getSex());
    }

    @Test
    public void importCsvWrongIdCode() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().get(0).getRowNr());

        cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;37810012580;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");
        responseEntity = restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 on süsteemis juba seotud teise isikuga (47810010009).", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvApplicationNrMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Puudub avalduse number", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvApplicationNrEmpty() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + "; ;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Puudub avalduse number", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvApplicationNrDublicate() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012",
                ADMISSION_CODE + ";Nr123456789;Toni;Kuut;37810010008;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());

        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Failis on rohkem kui üks avalduse numbriga Nr123456789 vastuvõtu avaldust.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvFirstnameMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kandideerija eesnimi.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvSaisChangedMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 seotud muutmise kuupäev on puudu või on vigane.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvSaisChangedWrongFormat() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1/1/2001;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 seotud muutmise kuupäev on puudu või on vigane.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvCodeAndCurriculumVersionCodeMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 seotud konkursil puudub konkursi kood.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvCitizenshipEmpty() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009; ;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kodakondsus.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvCitizenshipMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kodakondsus.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvCitizenshipWrongClassifierValue() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EESTI;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kodakondsus.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void importCsvAdmissionCodeMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
    }

    @Test
    public void importCsvAdmissionCodeMissingForSecondRow() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(ADMISSION_CODE + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012",
                ";Nr223456789;Juku;Juurikas;37810010030;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(2, responseEntity.getBody().getSuccessful().size());
    }

    @Test
    @WithUserDetails(TestConfiguration.USER_ID)
    public void importCsvApplicationTryUpdateWhenAddedToDirective() {
        String lastname = "Maasikas";
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(
                ADMISSION_CODE + ";Nr123456789;Mari;" + lastname + ";47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        //create
        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());

        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(ADMISSION_CODE);
        SaisApplication saisApplication = saisAdmission.getApplications().stream().findFirst().get();

        //get
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(saisApplication.getId().toString());
        ResponseEntity<SaisApplicationDto> response = restTemplate.getForEntity(uriBuilder.toUriString(), SaisApplicationDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(lastname, response.getBody().getLastname());

        //update
        lastname = "Mustikas";
        cmd = csvCmdForRows(
                ADMISSION_CODE + ";Nr123456789;Mari;" + lastname + ";47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");
        responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());

        saisAdmission = saisAdmissionRepository.findByCode(ADMISSION_CODE);
        saisApplication = saisAdmission.getApplications().stream().findFirst().get();

        //get
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(saisApplication.getId().toString());
        response = restTemplate.getForEntity(uriBuilder.toUriString(), SaisApplicationDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(lastname, response.getBody().getLastname());

        //add to directive
        Long saisApplicationId = saisApplication.getId();
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        directive = txTemplate.execute((TransactionStatus status) -> {
            DirectiveStudent directiveStudent = new DirectiveStudent();
            directiveStudent.setCanceled(Boolean.FALSE);
            directiveStudent.setSaisApplication(saisApplicationRepository.findOne(saisApplicationId));

            Directive newDirective = new Directive();
            newDirective.setSchool(testConfigurationService.getCurrentUser().getSchool());
            newDirective.setType(classifierRepository.getOne(DirectiveType.KASKKIRI_IMMAT.name()));
            newDirective.setStatus(classifierRepository.getOne(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name()));
            newDirective.setHeadline("test - importCsvApplicationTryUpdateWhenAddedToDirective");
            List<DirectiveStudent> students = new ArrayList<>();
            students.add(directiveStudent);
            directiveStudent.setDirective(newDirective);
            newDirective.setStudents(students);
            newDirective = directiveRepository.save(newDirective);
            return newDirective;
        });


        //try update
        lastname = "Mustikas2";
        cmd = csvCmdForRows(
                ADMISSION_CODE + ";Nr123456789;Mari;" + lastname + ";47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");
        responseEntity =
                restTemplate.postForEntity(ENDPOINT_IMPORT_CSV, cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 on seotud käskkiri - seda ei uuendata.", responseEntity.getBody().getFailed().get(0).getMessage());
    }

    @Test
    public void csvSampleFile() {
        ResponseEntity<?> response = restTemplate.getForEntity(ENDPOINT + "/sample.csv", Void.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void classifiersFile() {
        ResponseEntity<?> response = restTemplate.getForEntity(ENDPOINT + "/classifiers.csv", Void.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void importSais() {
        String uri = UriComponentsBuilder.fromUriString(ENDPOINT + "/importSais").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri, Collections.emptyMap(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private static SaisApplicationImportCsvCommand csvCmdForRows(String...rows) {
        SaisApplicationImportCsvCommand form = new SaisApplicationImportCsvCommand();
        OisFileCommand file = new OisFileCommand();
        String csvFileContent = CSV_HEADER + "\n" + String.join("\n", rows);
        file.setFdata(csvFileContent.getBytes(StandardCharsets.UTF_8));
        form.setFile(file);
        return form;
    }
}
