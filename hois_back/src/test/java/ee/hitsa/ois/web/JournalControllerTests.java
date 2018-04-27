package ee.hitsa.ois.web;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JournalControllerTests {

    private static final String ENDPOINT = "/journals";
    private static final Long STUDENT_ID = Long.valueOf(189L);
    private static final Long STUDY_YEAR_ID = Long.valueOf(36L);
    private static final Role ROLE = Role.ROLL_A;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper objectMapper;

    private Long schoolId;
    private Long journalId;
    private LocalDate endDate;
    private Long journalEntryId;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);
        if (schoolId == null) {
            List<School> userSchools = testConfigurationService.personSchools(ROLE);
            Assert.assertFalse(userSchools.isEmpty());
            Object result = em.createNativeQuery("select j.id journal_id, j.end_date, je.id as journal_entry_id, j.school_id"
                    + " from journal j"
                    + " inner join journal_entry je on je.journal_id = j.id"
                    + " where j.end_date is not null and j.school_id in (?1)")
                .setParameter(1, userSchools)
                .setMaxResults(1).getResultList().get(0);
            journalId = resultAsLong(result, 0);
            endDate = resultAsLocalDate(result, 1);
            journalEntryId = resultAsLong(result, 2);
            schoolId = resultAsLong(result, 3);
        }
        testConfigurationService.userToRoleInSchool(ROLE, schoolId, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        ResponseEntity<JournalSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, JournalSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        
        responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT)
                    .queryParam("studyYear", Long.valueOf(1L))
                    .toUriString(), 
                JournalSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("studyYear", Long.valueOf(1L));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1L));
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        uriBuilder.queryParam("module", Long.valueOf(1L), Long.valueOf(2L));
        uriBuilder.queryParam("journal", Long.valueOf(1L));
        uriBuilder.queryParam("status", "PAEVIK_STAATUS_T");

        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), JournalSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void get() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void saveEndDate() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "saveEndDate");
        Map<String, LocalDate> postData = Collections.singletonMap("endDate", endDate);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), postData, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getJournalEntries() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "journalEntry");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getJournalEntry() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "journalEntry", journalEntryId.toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getOtherStudents() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "otherStudents");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getSuitedStudents() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "suitedStudents");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getJournalStudents() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "journalStudents");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getJournalStudentsByDate() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "journalEntriesByDate");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getJournalLessonInfo() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(journalId.toString(), "journalEntry", "lessonInfo");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentJournals() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment("studentJournals");
        uriBuilder.queryParam("studentId", STUDENT_ID);
        uriBuilder.queryParam("studyYearId", STUDY_YEAR_ID);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentJournalTasks() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("studentJournalTasks");
        uriBuilder.queryParam("studentId", STUDENT_ID);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentJournalStudy() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("studentJournalStudy");
        uriBuilder.queryParam("studentId", STUDENT_ID);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentAbsences() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("studentJournalAbsences");
        uriBuilder.queryParam("studentId", STUDENT_ID);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void studentLastResults() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("studentJournalLastResults");
        uriBuilder.queryParam("studentId", STUDENT_ID);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void excel() {
        String url = ENDPOINT + "/" + journalId + "/journal.xls";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Void.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void unconfirmedJournals() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/unconfirmedJournalsInfo");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    //TODO: test save endpoints
}
