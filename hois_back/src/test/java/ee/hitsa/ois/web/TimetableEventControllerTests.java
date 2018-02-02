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

    private static final Long SCHOOL_ID = Long.valueOf(970L);
    private static final Long TIMETABLE_ID = Long.valueOf(80L);
    private static final Long STUDENT_GROUPS = Long.valueOf(140L);
    private static final Long TEACHERS = Long.valueOf(198L);
    private static final Long STUDENT_ID = Long.valueOf(189L);
    private static final Long ROOM_ID = Long.valueOf(1480L);
    private static final String LANG = "et";
    
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
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("studentGroups", STUDENT_GROUPS);
        uriBuilder.queryParam("lang", LANG);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        System.out.println(uriBuilder.toUriString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void groupTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByGroup");
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("studentGroups", STUDENT_GROUPS);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        System.out.println(uriBuilder.toUriString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void groupTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByGroup/calendar");
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("studentGroups", STUDENT_GROUPS);
        uriBuilder.queryParam("lang", LANG);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        System.out.println(uriBuilder.toUriString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void teacherTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByTeacher");
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("teachers", TEACHERS);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void teacherTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByTeacher/calendar");
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("teachers", TEACHERS);
        uriBuilder.queryParam("lang", LANG);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByStudent");
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("student", STUDENT_ID);
        uriBuilder.queryParam("vocational", Boolean.TRUE);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByStudent/calendar");
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("student", STUDENT_ID);
        uriBuilder.queryParam("vocational", Boolean.TRUE);
        uriBuilder.queryParam("lang", LANG);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void roomTimetableForWeek() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByRoom");
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("room", ROOM_ID);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void roomTimetableIcs() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/timetableevents/timetableByRoom/calendar");
        uriBuilder.pathSegment(SCHOOL_ID.toString());
        uriBuilder.queryParam("timetable", TIMETABLE_ID);
        uriBuilder.queryParam("room", ROOM_ID);
        uriBuilder.queryParam("lang", LANG);
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
