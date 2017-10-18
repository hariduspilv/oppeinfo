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
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudyLoad;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReportControllerTests {

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
    public void students() {
        String url = "/reports/students";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("name", "nimi");
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("birthdateFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("birthdateThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("studyStartFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("studyStartThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("studyLevel", "OPPEASTE_442");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1));
        uriBuilder.queryParam("studyLoad", "OPPEKOORMUS_TAIS");
        uriBuilder.queryParam("studyForm", "OPPEVORM_P");
        uriBuilder.queryParam("status", "OPPURSTAATUS_A");
        uriBuilder.queryParam("fin", "FINALLIKAS_RE");
        uriBuilder.queryParam("finSpecific", "FINTAPSUSTUS_Y");
        uriBuilder.queryParam("language", "OPPEKEEL_E");

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentsAsExcel() {
        String url = "/reports/students/students.xls";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);

        ResponseEntity<?> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Void.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentStatistics() {
        String url = "/reports/students/statistics";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", MainClassCode.FINALLIKAS.name());
        uriBuilder.queryParam("date", "2017-01-01T00:00:00.000Z");

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", MainClassCode.OPPEVORM.name());

        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", MainClassCode.OPPURSTAATUS.name());

        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentStatisticsAsExcel() {
        String url = "/reports/students/statistics/studentstatistics.xls";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", MainClassCode.FINALLIKAS.name());
        uriBuilder.queryParam("date", "2017-01-01T00:00:00.000Z");

        ResponseEntity<?> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Void.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentStatisticsByPeriod() {
        String url = "/reports/students/statistics/byperiod";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", StudentStatus.OPPURSTAATUS_A.name());
        uriBuilder.queryParam("from", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("thru", "2017-01-01T00:00:00.000Z");

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", StudentStatus.OPPURSTAATUS_K.name());

        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", StudentStatus.OPPURSTAATUS_L.name());

        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentStatisticsByPeriodAsExcel() {
        String url = "/reports/students/statistics/studentstatisticsbyperiod.xls";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("result", StudentStatus.OPPURSTAATUS_A.name());
        uriBuilder.queryParam("from", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("thru", "2017-01-01T00:00:00.000Z");

        ResponseEntity<?> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Void.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void curriculumCompletion() {
        String url = "/reports/curriculums/completion";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("name", "nimi");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1));
        uriBuilder.queryParam("studyLoad", StudyLoad.OPPEKOORMUS_TAIS.name());
        uriBuilder.queryParam("studyForm", "OPPEVORM_P");

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void teacherLoadHigher() {
        String url = "/reports/teachers/load/higher";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void teacherLoadVocational() {
        String url = "/reports/teachers/load/vocational";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
