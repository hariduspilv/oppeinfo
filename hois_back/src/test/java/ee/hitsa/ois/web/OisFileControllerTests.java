package ee.hitsa.ois.web;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.repository.OisFileRepository;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OisFileControllerTests {

    private static final String ENDPOINT = "/oisfile";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OisFileRepository oisFileRepository;

    @Test
    public void get() {
        OisFile oisFile = oisFileRepository.findAll(new PageRequest(1, 1)).getContent().get(0);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        String uri = uriBuilder.pathSegment("get").pathSegment(oisFile.getId().toString()).toUriString();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().length > 0);
    }

}
