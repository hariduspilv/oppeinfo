package ee.hitsa.ois.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

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
    public void getMissing() {
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

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/directives/coordinators");
        String uri = uriBuilder.build().toUriString();
        DirectiveCoordinatorForm form = new DirectiveCoordinatorForm();
        form.setName("Käskkirjade kooskõlastaja");
        form.setIdcode("48908209998");
        ResponseEntity<DirectiveCoordinatorDto> responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long id = responseEntity.getBody().getId();
        Assert.assertNotNull(id);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString("/directives/coordinators").pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<DirectiveCoordinatorDto> response = restTemplate.getForEntity(uri, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        form = response.getBody();
        Assert.assertNotNull(form);
        form.setName("Kooskõlastaja Käskkirjade");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), DirectiveCoordinatorDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // read
        responseEntity = restTemplate.getForEntity(uri, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);

        // try to update with wrong version
        form.setName("Käskkirjade kooskõlastaja");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // search existing departments
        UriComponentsBuilder searchUriBuilder = UriComponentsBuilder.fromUriString("/directives/coordinators");
        ResponseEntity<Object> searchResponseEntity = restTemplate.getForEntity(searchUriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString("/directives/coordinators").pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
}
