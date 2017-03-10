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

import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TeacherOccupationControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void search() {
        // no parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/school/teacheroccupations");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // occupationEt
        uriBuilder.queryParam("occupationEt", "Amet eesti keeles");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // occupationEn
        uriBuilder.queryParam("occupationEn", "Occupation name in english");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // isValid
        uriBuilder.queryParam("isValid", "true");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void get() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/school/").pathSegment("teacheroccupations", "0");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/school/teacheroccupations");
        String uri = uriBuilder.build().toUriString();
        TeacherOccupationForm teacherOccupationForm = new TeacherOccupationForm();
        teacherOccupationForm.setOccupationEt("Amet eesti keeles");
        teacherOccupationForm.setOccupationEn("Occupation in english");
        teacherOccupationForm.setIsValid(Boolean.TRUE);
        ResponseEntity<TeacherOccupation> responseEntity = restTemplate.postForEntity(uri, teacherOccupationForm, TeacherOccupation.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long occupationId = responseEntity.getBody().getId();
        Assert.assertNotNull(occupationId);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString("/school").pathSegment("teacheroccupations", occupationId.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<TeacherOccupationForm> response = restTemplate.getForEntity(uri, TeacherOccupationForm.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        teacherOccupationForm = response.getBody();
        Assert.assertNotNull(teacherOccupationForm);
        teacherOccupationForm.setOccupationEn("Occupation in english (renamed)");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(teacherOccupationForm), TeacherOccupation.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // try to update with bad version
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(teacherOccupationForm), TeacherOccupation.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // read
        responseEntity = restTemplate.getForEntity(uri, TeacherOccupation.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);
        Assert.assertEquals(teacherOccupationForm.getOccupationEn(), responseEntity.getBody().getOccupationEn());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString("/school").pathSegment("teacheroccupations", occupationId.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
}
