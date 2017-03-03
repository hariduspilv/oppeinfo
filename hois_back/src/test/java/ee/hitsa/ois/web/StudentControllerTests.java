package ee.hitsa.ois.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.service.StudentService;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.commandobject.StudentSearchCommand;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TestRestTemplate restTemplate;

    private Long schoolId;

    @Before
    public void setUp() {
        schoolId = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchoolId();
    }

    @Test
    public void search() {
        String url = "/students";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students");
        uriBuilder.queryParam("idcode", "3211212");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString("/students");
        uriBuilder.queryParam("name", "NIMI");
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studyForm", "OPPEVORM_P", "OPPEVORM_K");
        uriBuilder.queryParam("status", "OPPURSTATUS_AKAD", "OPPURSTATUS_OPIB");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void documents() {
        Assert.assertNotNull(schoolId);
        Page<StudentSearchDto> students = studentService.search(schoolId, new StudentSearchCommand(), new PageRequest(0, 1));
        Assert.assertNotNull(students);

        if(students.hasContent()) {
            Long id = students.getContent().get(0).getId();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students").pathSegment(id.toString(), "documents");
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toString(), Object.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }
}
