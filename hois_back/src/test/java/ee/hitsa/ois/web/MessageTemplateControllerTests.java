package ee.hitsa.ois.web;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.message.MessageTemplateForm;
import ee.hitsa.ois.web.dto.message.MessageTemplateDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessageTemplateControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        String url = "/messageTemplate";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/messageTemplate");
        uriBuilder.queryParam("valid", Boolean.TRUE);
        uriBuilder.queryParam("headline", "3211212");
        uriBuilder.queryParam("type", "TEATE_LIIK_AP_LOPP", "TEATE_LIIK_AV_KINNIT");
        uriBuilder.queryParam("validFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("validThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "type.nameEt,asc");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getMissing() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/messageTemplate/0", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        MessageTemplateForm form = getForm();
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/messageTemplate");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<MessageTemplateDto> responseEntity = restTemplate.postForEntity(uri, form, MessageTemplateDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long id = responseEntity.getBody().getId();
        Assert.assertNotNull(id);
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);
        Assert.assertEquals(Long.valueOf(0), version);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString("/messageTemplate").pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<MessageTemplateDto> response = restTemplate.getForEntity(uri, MessageTemplateDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        String newContent = "MessageTemplateControllerTest2";

        form = response.getBody();
        Assert.assertNotNull(form);
        form.setContent(newContent);
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), MessageTemplateDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        version = responseEntity.getBody().getVersion();
        Assert.assertEquals(Long.valueOf(1), version);
        Assert.assertEquals(newContent, responseEntity.getBody().getContent());

        // try to update with wrong version
        form.setContent("MessageTemplateControllerTest3");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), MessageTemplateDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString("/messageTemplate").pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

    @Test
    public void getUsedTypeCodes() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/messageTemplate/usedTypeCodes?code=TEATE_LIIK_AP_LOPP", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private static MessageTemplateForm getForm() {
        MessageTemplateForm form = new MessageTemplateForm();
        form.setHeadline("MessageTemplateControllerTest");
        form.setContent("MessageTemplateControllerTest");
        form.setType("TEATE_LIIK_AP_LOPP");
        form.setValidFrom(LocalDate.now());
        return form;
    }
}
