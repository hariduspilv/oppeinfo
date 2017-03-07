package ee.hitsa.ois.web;

import java.util.Arrays;

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

import ee.hitsa.ois.web.commandobject.CertificateForm;
import ee.hitsa.ois.web.dto.CertificateDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CertificateControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private final String BASE_URL = "/certificate";
    private final String TEXT = "CertificateControllerTest";
    private final String ID_CODE = "37810017107";
    private final String TYPE = "TOEND_LIIK_OPI";
    private final String STATUS = "TOEND_STAATUS_V";
    private final Long STUDENT_ID = Long.valueOf(2);
    
    @Test
    public void search() {
        String url = BASE_URL;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        uriBuilder.queryParam("student.person.name", "test1");
        uriBuilder.queryParam("certificateNr", "123");
        uriBuilder.queryParam("idcode", "39008114578");
        uriBuilder.queryParam("name", "Alice");
        uriBuilder.queryParam("headline", "3211212");
        uriBuilder.queryParam("type", Arrays.asList("TOEND_LIIK_OPI", "TOEND_LIIK_LOPET"));
        uriBuilder.queryParam("insertedFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "student.person.lastname,student.person.firstname,asc");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void crud() {
        CertificateForm form = getForm();
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<CertificateDto> responseEntity = restTemplate.postForEntity(uri, form, CertificateDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CertificateDto dto = responseEntity.getBody();
        Assert.assertNotNull(dto);
        Long id = dto.getId();
        Assert.assertNotNull(id);
        Long version = dto.getVersion();
        Assert.assertNotNull(version);
        Assert.assertEquals(Long.valueOf(0), version);
        
        //read
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<CertificateDto> response = restTemplate.getForEntity(uri, CertificateDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getId());

        //update 
        form = response.getBody();
        Assert.assertNotNull(form);
        String newContent = TEXT.concat("2");
        form.setContent(newContent);
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), CertificateDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        dto = responseEntity.getBody();
        version = dto.getVersion();
        Assert.assertEquals(Long.valueOf(1), version);
        Assert.assertEquals(newContent, dto.getContent());
        
        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
    
    public CertificateForm getForm() {
        CertificateForm form = new CertificateForm();
        form.setHeadline(TEXT);
        form.setContent(TEXT);
        form.setWhom(TEXT);
        form.setSignatoryIdcode(ID_CODE);
        form.setSignatoryName(TEXT);
        form.setWdUrl(TEXT);
        form.setType(TYPE);
        form.setStatus(STATUS);
        form.setStudent(STUDENT_ID);
        return form;
    }
}
