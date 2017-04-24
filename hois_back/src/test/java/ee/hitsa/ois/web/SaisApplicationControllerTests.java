package ee.hitsa.ois.web;

import java.nio.charset.StandardCharsets;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.SaisAdmission;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.SaisApplicationImportCsvCommand;
import ee.hitsa.ois.web.dto.SaisApplicationDto;
import ee.hitsa.ois.web.dto.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SaisApplicationControllerTests {

    private final String CSV_HEADER = "KonkursiKood;AvalduseNr;Eesnimi;Perekonnanimi;Isikukood;Kodakondsus;Elukohariik;Finantseerimisallikas;AvalduseMuutmiseKp;AvalduseStaatus;Oppekava/RakenduskavaKood;Oppekoormus;Oppevorm;Oppekeel;EelnevOppetase;KonkursiAlgusKp;KonkursiLõppKp";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;

    String admissionCode = "SaisApplicationControllerTests/1";

    @After
    public void cleanUp() {
        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(admissionCode);
        if (saisAdmission != null) {
            delete(saisAdmission);
        }
        testConfigurationService.setSessionCookie(null);
    }

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @Test
    public void search() {
        ResponseEntity<SaisApplicationSearchDto> responseEntity = restTemplate.getForEntity("/saisApplications", SaisApplicationSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisApplications");
        uriBuilder.queryParam("code", "test1", "test2");
        uriBuilder.queryParam("status", "SAIS_AVALDUSESTAATUS_TYH", "SAIS_AVALDUSESTAATUS_ML");
        uriBuilder.queryParam("name", "test");
        uriBuilder.queryParam("idcode", "123456789");
        uriBuilder.queryParam("showRevoked", "false");
        uriBuilder.queryParam("addedToDirective", "false");

        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, SaisApplicationSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        //create
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(
                admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(admissionCode);
        Long saisApplicationId = saisAdmission.getApplications().stream().findFirst().get().getId();

        //read
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisApplications").pathSegment(saisApplicationId.toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void importCsvValid() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(
                admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().stream().findFirst().get().getRowNr());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisApplications");
        uriBuilder.queryParam("code", admissionCode);
        uriBuilder.queryParam("idcode", "47810010009");

        String url = uriBuilder.build().toUriString();
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> searchResponseEntity = restTemplate.getForEntity(url, Map.class);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());
        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<Map> applications = (List<Map>) searchResponseEntity.getBody().get("content");

        Assert.assertEquals(1, applications.size());

        uriBuilder = UriComponentsBuilder.fromUriString("/saisApplications").pathSegment(applications.get(0).get("id").toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<SaisApplicationDto> response = restTemplate.getForEntity(uri, SaisApplicationDto.class);
        Assert.assertNotNull(response.getBody().getSex());
    }

    @Test
    public void importCsvWrongIdCode() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().stream().findFirst().get().getRowNr());

        cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;37810012580;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");
        responseEntity = restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 on süsteemis juba seotud teise isikuga (47810010009).", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvApplicationNrMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Puudub avalduse number", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvApplicationNrEmpty() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + "; ;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Puudub avalduse number", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvFirstnameMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kandideerija eesnimi.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvSaisChangedMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 seotud muutmise kuupäev on puudu või on vigane.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvSaisChangedWrongFormat() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1/1/2001;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 seotud muutmise kuupäev on puudu või on vigane.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCodeAndCurriculumVersionCodeMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusega nr Nr123456789 seotud konkursil puudub konkursi kood.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCitizenshipEmpty() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009; ;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kodakondsus.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCitizenshipMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kodakondsus.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCitizenshipWrongClassifierValue() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EESTI;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldusel nr Nr123456789 puudub kodakondsus.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }


    @Test
    public void importCsvAdmissionCodeMissing() {

        SaisApplicationImportCsvCommand cmd = csvCmdForRows(";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
    }


    @Test
    public void importCsvAdmissionCodeMissingForSecondRow() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRows(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012",
                ";Nr223456789;Juku;Juurikas;37810010030;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(2, responseEntity.getBody().getSuccessful().size());

    }


    private SaisApplicationImportCsvCommand csvCmdForRows(String...rows) {
        SaisApplicationImportCsvCommand form = new SaisApplicationImportCsvCommand();
        OisFileCommand file = new OisFileCommand();
        String csvFileContent = CSV_HEADER + "\n";
        for (String row : rows) {
            csvFileContent += (row + "\n");
        }
        file.setFdata(csvFileContent.getBytes(StandardCharsets.UTF_8));
        form.setFile(file);
        return form;
    }

    private void delete(SaisAdmission saisAdmission) {
        Long id = saisAdmission.getId();
        Long version = saisAdmission.getVersion();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisAdmissions").pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        String uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

}
