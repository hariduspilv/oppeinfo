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

import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SaisApplicationControllerTests {

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private TestRestTemplate restTemplate;

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

}
