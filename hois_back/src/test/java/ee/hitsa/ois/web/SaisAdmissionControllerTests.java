package ee.hitsa.ois.web;

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
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.dto.SaisAdmissionDto;
import ee.hitsa.ois.web.dto.SaisAdmissionSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SaisAdmissionControllerTests {

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

    @Test
    public void search() {
        ResponseEntity<SaisAdmissionSearchDto> responseEntity = restTemplate.getForEntity("/saisAdmissions", SaisAdmissionSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisAdmissions");
        uriBuilder.queryParam("type", "test");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studyForm", "OPPEVORM_M");
        uriBuilder.queryParam("fin", "FINALLIKAS_RE");
        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, SaisAdmissionSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        SaisAdmission saisAdmission = saisAdmissionRepository.findAll().stream().findFirst().get();

        //read
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisAdmissions").pathSegment(saisAdmission.getId().toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<SaisAdmissionDto> response = restTemplate.getForEntity(uri, SaisAdmissionDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
