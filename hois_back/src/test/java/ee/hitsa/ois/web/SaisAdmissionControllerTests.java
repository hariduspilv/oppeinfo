package ee.hitsa.ois.web;

import java.time.LocalDate;

import javax.transaction.Transactional;

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

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.FinSource;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionImportForm;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SaisAdmissionControllerTests {

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
        ResponseEntity<SaisAdmissionSearchDto> responseEntity = restTemplate.getForEntity("/saisAdmissions", SaisAdmissionSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisAdmissions");
        uriBuilder.queryParam("type", "test");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studyForm", "OPPEVORM_M");
        uriBuilder.queryParam("fin", FinSource.FINALLIKAS_RE.name());
        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, SaisAdmissionSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
//        TODO: switch user
//        Long schoolId = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchoolId();
//        SaisAdmission saisAdmission = saisAdmissionRepository.findAll().stream().filter(it -> it.getCurriculumVersion().getCurriculum().getSchool().getId() == schoolId).findFirst().get();
//
//        //read
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/saisAdmissions").pathSegment(saisAdmission.getId().toString());
//        String uri = uriBuilder.build().toUriString();
//        ResponseEntity<SaisAdmissionDto> response = restTemplate.getForEntity(uri, SaisAdmissionDto.class);
//        Assert.assertNotNull(response);
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void saisImport() {
        String uri = UriComponentsBuilder.fromUriString("/saisAdmissions/saisImport").build().toUriString();
        SaisAdmissionImportForm form = new SaisAdmissionImportForm();
        form.setCreateDateFrom(LocalDate.parse("2016-10-31"));
        form.setCreateDateTo(LocalDate.parse("2016-12-31"));
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri, form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
