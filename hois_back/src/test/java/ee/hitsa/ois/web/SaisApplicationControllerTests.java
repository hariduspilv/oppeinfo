package ee.hitsa.ois.web;

import java.nio.charset.StandardCharsets;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
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

import ee.hitsa.ois.domain.SaisAdmission;
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

    String admissionCode = "SaisApplicationControllerTests/1";

    @After
    public void cleanUp() {
        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(admissionCode);
        if (saisAdmission != null) {
            delete(saisAdmission);
        }
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
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode(admissionCode);
        Long saisApplicationId = saisAdmission.getApplications().stream().findFirst().get().getId();

        //read
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisApplications").pathSegment(saisApplicationId.toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<SaisApplicationDto> response = restTemplate.getForEntity(uri, SaisApplicationDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void importCsvValid() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().stream().findFirst().get().getRowNr());
    }

    @Test
    public void importCsvWrongIdCode() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().size());
        Assert.assertEquals(1, responseEntity.getBody().getSuccessful().stream().findFirst().get().getRowNr());

        cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;37810012580;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");
        responseEntity = restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-ga on süsteemis juba seotud teise isikuga (47810010009).", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvApplicationNrMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Puudub avalduse number", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvApplicationNrEmpty() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + "; ;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Puudub avalduse number", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvFirstnameMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-l puudub kandideerija eesnimi.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvSaisChangedMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-ga seotud muutmise kuupäev on puudu või on vigane.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvSaisChangedWrongFormat() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1/1/2001;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-ga seotud muutmise kuupäev on puudu või on vigane.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCodeAndCurriculumVersionCodeMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(";Nr123456789;Mari;Maasikas;47810010009;EST;EST;RE;1.01.2012;T;;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-ga seotud konkursil puudub konkursi kood.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCitizenshipEmpty() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009; ;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-l puudub kodakondsus.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCitizenshipMissing() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-l puudub kodakondsus.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    @Test
    public void importCsvCitizenshipWrongClassifierValue() {
        SaisApplicationImportCsvCommand cmd = csvCmdForRow(admissionCode + ";Nr123456789;Mari;Maasikas;47810010009;EESTI;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012");

        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", cmd, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getFailed().size());
        Assert.assertEquals("Avaldus nr. Nr123456789-l puudub kodakondsus.", responseEntity.getBody().getFailed().stream().findFirst().get().getMessage());
    }

    private SaisApplicationImportCsvCommand csvCmdForRow(String row) {
        SaisApplicationImportCsvCommand form = new SaisApplicationImportCsvCommand();
        OisFileCommand file = new OisFileCommand();
        file.setFdata((CSV_HEADER + "\n" + row ).getBytes(StandardCharsets.UTF_8));
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
