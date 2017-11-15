package ee.hitsa.ois.web;

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
import ee.hitsa.ois.enums.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimetableEventControllerTests {

    private static final Long timetableId = Long.valueOf(80L);
    private static final Long studentGroups = Long.valueOf(140L);
    private static final Long teachers = Long.valueOf(198L);
    private static final Long studentId = Long.valueOf(189L);
    private static final Long roomId = Long.valueOf(1480L);
    private static final String lang = "et";
    
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
    public void searchFormData() {
        String uri = UriComponentsBuilder.fromUriString("/timetableevents/searchFormData").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void search() {
        String uri = UriComponentsBuilder.fromUriString("/timetableevents").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchTimetable() {
        String uri = UriComponentsBuilder.fromUriString("/timetableevents/timetableSearch").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void searchTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByGroup/calendar");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("studentGroups", studentGroups);
        uriBuilder.queryParam("lang", lang);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        System.out.println(uriBuilder.toUriString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void groupTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByGroup");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("studentGroups", studentGroups);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        System.out.println(uriBuilder.toUriString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void groupTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByGroup/calendar");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("studentGroups", studentGroups);
        uriBuilder.queryParam("lang", lang);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        System.out.println(uriBuilder.toUriString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void teacherTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByTeacher");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("teachers", teachers);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void teacherTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByTeacher/calendar");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("teachers", teachers);
        uriBuilder.queryParam("lang", lang);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByStudent");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("student", studentId);
        uriBuilder.queryParam("vocational", Boolean.TRUE);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByStudent/calendar");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("student", studentId);
        uriBuilder.queryParam("vocational", Boolean.TRUE);
        uriBuilder.queryParam("lang", lang);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void roomTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByRoom");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("room", roomId);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void roomTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByRoom/calendar");
        uriBuilder.queryParam("timetable", timetableId);
        uriBuilder.queryParam("room", roomId);
        uriBuilder.queryParam("lang", lang);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
