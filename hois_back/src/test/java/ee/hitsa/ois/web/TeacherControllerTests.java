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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.ehis.EhisTeacherExportForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TeacherControllerTests {

    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private TestRestTemplate restTemplate;

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
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/teachers");
        uri.queryParam("idcode", TestConfiguration.USER_ID);
        uri.queryParam("name", "Name");
        uri.queryParam("schoolDepartment", Long.valueOf(1));
        uri.queryParam("teacherOccupation", Long.valueOf(1));
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void teacheroccupations() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/teachers/teacheroccupations");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void exportToEhisHigher() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/teachers/exportToEhis/higher");
        EhisTeacherExportForm form = new EhisTeacherExportForm();
        form.setAllDates(true);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form = new EhisTeacherExportForm();
        form.setAllDates(false);
        form.setChangeDateFrom(LocalDate.parse("2016-10-31"));
        form.setChangeDateTo(LocalDate.parse("2016-12-31"));
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form = new EhisTeacherExportForm();
        form.setAllDates(true);
        form.setSubjectData(true);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form = new EhisTeacherExportForm();
        form.setAllDates(false);
        form.setChangeDateFrom(LocalDate.parse("2016-10-31"));
        form.setChangeDateTo(LocalDate.parse("2016-12-31"));
        form.setSubjectData(true);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void exportToEhisVocational() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/teachers/exportToEhis/vocational");
        EhisTeacherExportForm form = new EhisTeacherExportForm();
        form.setAllDates(true);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form = new EhisTeacherExportForm();
        form.setAllDates(false);
        form.setChangeDateFrom(LocalDate.parse("2016-10-31"));
        form.setChangeDateTo(LocalDate.parse("2016-12-31"));
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form = new EhisTeacherExportForm();
        form.setAllDates(true);
        form.setSubjectData(true);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form = new EhisTeacherExportForm();
        form.setAllDates(false);
        form.setChangeDateFrom(LocalDate.parse("2016-10-31"));
        form.setChangeDateTo(LocalDate.parse("2016-12-31"));
        form.setSubjectData(true);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
