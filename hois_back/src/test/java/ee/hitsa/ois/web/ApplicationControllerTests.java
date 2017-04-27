package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.ExmatriculationReason;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.dto.ApplicationDto;
import ee.hitsa.ois.web.dto.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTests {

    private static final Long SCHOOL_ID = Long.valueOf(9L);

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;

    private Student student;

    @Before
    public void setUp() {
        testConfigurationService.userToRoleInSchool(Role.ROLL_A, SCHOOL_ID, restTemplate);
        student = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), SCHOOL_ID));
            filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().findFirst().get();
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
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
        form.setStatus(ApplicationStatus.AVALDUS_STAATUS_KOOST.name());

        ResponseEntity<ApplicationDto> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(), form, ApplicationDto.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        AutocompleteResult studentAutocomplete = new AutocompleteResult(student.getId(), "nameEt", "nameEn");
        form.setStudent(studentAutocomplete);
        form.setReason(ExmatriculationReason.EKSMAT_POHJUS_A.name());

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
        form.setAddInfo("additional info update");
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void applicable() {
        List<Student> students = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), SCHOOL_ID));
            filters.add(cb.not(root.get("status").get("code").in(Arrays.asList(StudentStatus.OPPURSTAATUS_O.name(), StudentStatus.OPPURSTAATUS_A.name(), StudentStatus.OPPURSTAATUS_V.name()))));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        Assert.assertTrue(!CollectionUtils.isEmpty(students));

        for (Student notStudyingStudent : students) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/applications/student/"+notStudyingStudent.getId()+"/applicable");
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Map<String, Map> result = (Map<String, Map>) responseEntity.getBody();
            for (String key : result.keySet()) {
                Assert.assertFalse(Boolean.TRUE.equals(result.get(key).get("isAllowed")));
            }
        }

    }

}
