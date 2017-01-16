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
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.service.SchoolDepartmentService;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutocompleteControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SchoolDepartmentService schoolDepartmentService;

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    private SchoolDepartment schoolDepartment;

    @Before
    public void setUp() {
        School school = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchool();
        schoolDepartment = new SchoolDepartment();
        schoolDepartment.setNameEt("Struktuuriüksus");
        schoolDepartment.setValidFrom(LocalDate.now());
        schoolDepartment.setSchool(school);
        schoolDepartment = schoolDepartmentService.save(schoolDepartment, null);
    }

    @After
    public void cleanUp() {
        schoolDepartmentService.delete(schoolDepartment);
    }

    @Test
    public void schoolDepartments() {
        String uri = "/autocomplete/schooldepartments?lang=EN&excludedId=1";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
