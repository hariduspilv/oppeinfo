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

import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.dto.DirectiveCoordinatorDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DirectiveControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void search() {
        String url = "/directives/coordinators";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void get() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/directives/coordinators/0", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void validate() {
        // create
        String uri = "/directives/coordinators";
        DirectiveCoordinatorForm form = new DirectiveCoordinatorForm();
        ResponseEntity<DirectiveCoordinatorDto> responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setName("Kooskõlastaja Nimi");
        responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setIdcode("Wrong idcode");
        responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
    }
}
