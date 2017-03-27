package ee.hitsa.ois.web;

import java.nio.charset.StandardCharsets;

import javax.transaction.Transactional;

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
import ee.hitsa.ois.web.dto.SaisApplicationImportResultDto;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SaisApplicationControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

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
    public void importCsv() {
        SaisApplicationImportCsvCommand form = new SaisApplicationImportCsvCommand();
        OisFileCommand file = new OisFileCommand();
        file.setFdata(getCsvFile());
        form.setFile(file);
        ResponseEntity<SaisApplicationImportResultDto> responseEntity =
                restTemplate.postForEntity("/saisApplications/importCsv", form, SaisApplicationImportResultDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(0, responseEntity.getBody().getFailed().size());
        Assert.assertEquals(3, responseEntity.getBody().getSuccessful().size());
        
        SaisAdmission saisAdmission = saisAdmissionRepository.findByCode("FIL12/12");
        if (saisAdmission != null) {
            delete(saisAdmission);
        }
        
        saisAdmission = saisAdmissionRepository.findByCode("MAT15/16");
        if (saisAdmission != null) {
            delete(saisAdmission);
        }
    }

    private static byte[] getCsvFile() {
        return new String("KonkursiKood;AvalduseNr;Eesnimi;Perekonnanimi;Isikukood;Kodakondsus;Finantseerimisallikas;AvalduseMuutmiseKp;AvalduseStaatus;Oppekava/RakenduskavaKood;Oppekoormus;Oppevorm;Oppekeel;EelnevOppetase;KonkursiAlgusKp;KonkursiLõppKp\n"+
"FIL12/12;Nr123;Mari;Maasikas;47810010009;EST;RE;1.01.2012;T;first;TAIS;P;E;411;1.12.2011;1.02.2012\n"+
"MAT15/16;Nr456;Tõnu;Kuut;37810010008;FIN;REV;3.03.2012;T;first;OSA;P;I;411;1.01.2012;3.04.2012\n"+
"MAT15/16;Nr321;Tiiu;Kask;37810019886;EST;RE;12.02.2012;T;first;OSA;K;E;411;1.01.2012;3.04.2012").getBytes(StandardCharsets.UTF_8);
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
