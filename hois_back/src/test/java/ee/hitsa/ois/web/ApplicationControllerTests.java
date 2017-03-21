package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

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

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTests {

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @Before
    public void setUp() {
        Long schoolId = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchoolId();
        student = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().findFirst().get();
    }

    @Test
    public void search() {
        ResponseEntity<ApplicationSearchDto> responseEntity = restTemplate.getForEntity("/applications", ApplicationSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/applications");
        uriBuilder.queryParam("type", "AVALDUS_LIIK_AKAD","AVALDUS_LIIK_AKADK");
        uriBuilder.queryParam("insertedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("submittedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("submittedThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("status", "AVALDUS_STAATUS_KINNITAM","AVALDUS_STAATUS_KINNITATUD");
        uriBuilder.queryParam("student", student.getId());
        uriBuilder.queryParam("studentName", student.getPerson().getFirstname());
        uriBuilder.queryParam("studentIdCode", student.getPerson().getIdcode());
        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, ApplicationSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        //create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/applications");
        ApplicationForm form = new ApplicationForm();
        form.setType(ApplicationType.AVALDUS_LIIK_EKSMAT.name());
        form.setStatus(ApplicationStatus.AVALDUS_STAATUS_ESIT.name());

        ResponseEntity<ApplicationDto> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(), form, ApplicationDto.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        AutocompleteResult studentAutocomplete = new AutocompleteResult(student.getId(), "nameEt", "nameEn");
        form.setStudent(studentAutocomplete);

        responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(), form, ApplicationDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Long applicationId = responseEntity.getBody().getId();

        //read
        uriBuilder = UriComponentsBuilder.fromUriString("/applications").pathSegment(applicationId.toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<ApplicationDto> response = restTemplate.getForEntity(uri, ApplicationDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        //update
        form = responseEntity.getBody();
        form.setStatus(ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
        uriBuilder = UriComponentsBuilder.fromUriString("/applications").pathSegment(applicationId.toString());
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), ApplicationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //delete
        Long version = responseEntity.getBody().getVersion();
        uriBuilder = UriComponentsBuilder.fromUriString("/applications").pathSegment(applicationId.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

}
