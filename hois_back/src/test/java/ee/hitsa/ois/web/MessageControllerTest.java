package ee.hitsa.ois.web;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.dto.MessageDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private final String BASE_URL = "/message";
    private final String TEXT = "MessageControllerTest";
    
    @Test
    public void searchSent() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL + "/sent");
        uriBuilder.queryParam("subject", Boolean.TRUE);
        uriBuilder.queryParam("sender", "3211212");
        uriBuilder.queryParam("sentFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("sentThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "sender.lastname,sender.firstname,asc");
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
        
        // There is no need to test update as we do not edit sent messages
        
        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

    private MessageForm getForm() {
        MessageForm form = new MessageForm();
        form.setSubject(TEXT);
        form.setContent(TEXT);
        form.setReceivers(Stream.of(Long.valueOf(2)).collect(Collectors.toSet()));
        return form;
    }
}
