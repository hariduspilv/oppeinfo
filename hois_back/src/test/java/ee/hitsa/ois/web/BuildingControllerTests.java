package ee.hitsa.ois.web;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BuildingControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void all() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/buildings", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchRooms() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/buildings/searchrooms", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/buildings/searchrooms");
        uriBuilder.queryParam("name", "NIMI");
        uriBuilder.queryParam("code", "3211212");
        uriBuilder.queryParam("buildingName", "HOONE");
        uriBuilder.queryParam("buildingCode", "OPPEVORM_P");
        String url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
