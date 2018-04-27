package ee.hitsa.ois.web;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.ExmatriculationReason;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.web.commandobject.application.ApplicationForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.application.ApplicationDto;
import ee.hitsa.ois.web.dto.application.ApplicationSearchDto;
import ee.hitsa.ois.web.dto.application.ValidAcademicLeaveDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationControllerTests {

    private static final String ENDPOINT = "/applications";
    private static final Role ROLE = Role.ROLL_A;
    private static final Long SCHOOL_ID = Long.valueOf(10);
    /** student has academic leave directive, but directiveStudent.application is null */
    private static final Long WITHOUT_APP_STUDENT_ID = Long.valueOf(427);
    /** application has academicApplication, but does not have directive */
    private static final Long WITH_APP_APP_ID = Long.valueOf(2205);
    /** application has directive, but does not have academicApplication */
    private static final Long WITHOUT_APP_APP_ID = Long.valueOf(2271);

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;

    private Student student;

    @Before
    public void setUp() {
        if(student == null) {
            student = studentRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));
                filters.add(cb.equal(root.get("school"), SCHOOL_ID));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }).get(0);
        }
        testConfigurationService.userToRoleInSchool(ROLE, SCHOOL_ID, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        ResponseEntity<ApplicationSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, ApplicationSearchDto.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("type", "AVALDUS_LIIK_AKAD","AVALDUS_LIIK_AKADK");
        uriBuilder.queryParam("insertedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("submittedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("submittedThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("status", ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name(), ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());
        uriBuilder.queryParam("student", student.getId());
        uriBuilder.queryParam("studentName", student.getPerson().getFirstname());
        uriBuilder.queryParam("studentIdCode", student.getPerson().getIdcode());

        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), ApplicationSearchDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() throws IllegalAccessException, InvocationTargetException {
        //create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ApplicationForm form = new ApplicationForm();
        form.setType(ApplicationType.AVALDUS_LIIK_EKSMAT.name());
        form.setStatus(ApplicationStatus.AVALDUS_STAATUS_KOOST.name());

        ResponseEntity<ApplicationDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ApplicationDto.class);
        assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        AutocompleteResult studentAutocomplete = new AutocompleteResult(student.getId(), "nameEt", "nameEn");
        form.setStudent(studentAutocomplete);
        form.setReason(ExmatriculationReason.EKSMAT_POHJUS_A.name());

        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ApplicationDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Long applicationId = responseEntity.getBody().getId();

        //read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applicationId.toString());
        ResponseEntity<ApplicationDto> response = restTemplate.getForEntity(uriBuilder.toUriString(), ApplicationDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //update
        BeanUtils.copyProperties(form, response.getBody());
        form.setAddInfo("additional info update");
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applicationId.toString());
        responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, new HttpEntity<>(form), ApplicationDto.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //delete
        Long version = responseEntity.getBody().getVersion();
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applicationId.toString());
        uriBuilder.queryParam("version", version);
        restTemplate.delete(uriBuilder.toUriString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void applicableApplicationTypes() {
        List<Student> allNotStudyingStudents = studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school"), SCHOOL_ID));
            filters.add(cb.not(root.get("status").get("code").in(StudentStatus.STUDENT_STATUS_ACTIVE)));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        assertTrue(!allNotStudyingStudents.isEmpty());

        List<Student> notStudyingStudents = allNotStudyingStudents.stream()
                .filter(s -> s.getSchool().getId() == SCHOOL_ID).collect(Collectors.toList());

        for (Student notStudyingStudent : notStudyingStudents) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/student/"+notStudyingStudent.getId()+"/applicable");
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
            assertNotNull(responseEntity);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Map<String, Map<?, ?>> result = (Map<String, Map<?, ?>>) responseEntity.getBody();
            for (String key : result.keySet()) {
                assertFalse(Boolean.TRUE.equals(result.get(key).get("isAllowed")));
            }
        }
    }
    
    @Test
    public void validAcademicLeave() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/student/"+student.getId()+"/validAcademicLeave");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void applicableWithoutApplication() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).path("/student/" + WITHOUT_APP_STUDENT_ID + "/applicable")
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        Map<?, ?> response = (Map<?, ?>) responseEntity.getBody();
        assertNotNull(response);
        Map<?, ?> akadk = (Map<?, ?>) response.get(ApplicationType.AVALDUS_LIIK_AKADK.name());
        assertNotNull(akadk);
        assertNotEquals(Boolean.TRUE, akadk.get("isAllowed"));
    }
    
    @Test
    public void validAcademicLeaveWithoutApplication() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).path("/student/" + WITHOUT_APP_STUDENT_ID + "/validAcademicLeave")
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void readWithAcademicApplication() {
        ResponseEntity<ApplicationDto> response = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(WITH_APP_APP_ID.toString())
                .toUriString(), ApplicationDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        ApplicationDto applicationDto = response.getBody();
        assertNotNull(applicationDto);
        ValidAcademicLeaveDto validAcademicLeave = applicationDto.getValidAcademicLeave();
        assertNotNull(validAcademicLeave);
        assertEquals(Long.valueOf(1974), validAcademicLeave.getId());
    }

    @Test
    public void readWithoutAcademicApplication() {
        ResponseEntity<ApplicationDto> response = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(WITHOUT_APP_APP_ID.toString())
                .toUriString(), ApplicationDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        ApplicationDto applicationDto = response.getBody();
        assertNotNull(applicationDto);
        ValidAcademicLeaveDto validAcademicLeave = applicationDto.getValidAcademicLeave();
        assertNotNull(validAcademicLeave);
        assertEquals(Long.valueOf(2026), validAcademicLeave.getId());
    }

}
