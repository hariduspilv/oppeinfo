package ee.hitsa.ois.web;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.MessageService;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.dto.MessageDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessageControllerTests {

    private static final String BASE_URL = "/message";
    private static final String TEXT = "MessageControllerTest";

    @Autowired
    private EntityManager em;
    @Autowired
    private MessageService messageService;
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
    public void searchSent() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/sent");
        uriBuilder.queryParam("subject", Boolean.TRUE);
        uriBuilder.queryParam("sender", "3211212");
        uriBuilder.queryParam("sentFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("sentThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "subject,asc");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchSentAutomatic() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/sent/automatic");
        uriBuilder.queryParam("subject", Boolean.TRUE);
        uriBuilder.queryParam("sender", "3211212");
        uriBuilder.queryParam("sentFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("sentThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "subject,asc");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchReceived() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/received");
        uriBuilder.queryParam("subject", Boolean.TRUE);
        uriBuilder.queryParam("sender", "3211212");
        uriBuilder.queryParam("sentFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("sentThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "p.lastname,p.firstname,asc");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchReceivedForMainPage() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/received/mainPage");
        uriBuilder.queryParam("sort", "inserted,desc");
        uriBuilder.queryParam("size", "5");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void unreadReceivedCount() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/received/new");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        // create
        MessageForm form = getForm();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<MessageDto> responseEntity = restTemplate.postForEntity(uri, form, MessageDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MessageDto dto = responseEntity.getBody();
        Assert.assertNotNull(dto);
        Long id = dto.getId();
        Assert.assertNotNull(id);
        Long version = dto.getVersion();
        Assert.assertNotNull(version);
        Assert.assertEquals(Long.valueOf(0), version);
        
        // read
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<MessageDto> response = restTemplate.getForEntity(uri, MessageDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getId());
        
        // TODO: Set read
        
        // There is no need to test update as we do not edit sent messages
        
        // delete
        messageService.delete(testConfigurationService.getHoisUserDetails(), em.getReference(Message.class, id));
    }

    @Test
    public void getParents() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/parents");
        uriBuilder.queryParam("studentGroupId", Long.valueOf(39));
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchPersons() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/persons");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchPersonsAsTeacher() {
        testConfigurationService.userToRole(Role.ROLL_O, restTemplate);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/persons").queryParam("role", Role.ROLL_T.name());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/persons").queryParam("role", Role.ROLL_L.name());
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchPersonsAsRepresentative() {
        testConfigurationService.userToRole(Role.ROLL_L, restTemplate);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/persons");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchPersonsAsStudent() {
        testConfigurationService.userToRole(Role.ROLL_O, restTemplate);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/persons");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void students() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(BASE_URL + "/students", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private static MessageForm getForm() {
        MessageForm form = new MessageForm();
        form.setSubject(TEXT);
        form.setContent(TEXT);
        form.setReceivers(Stream.of(Long.valueOf(2)).collect(Collectors.toSet()));
        return form;
    }
}
