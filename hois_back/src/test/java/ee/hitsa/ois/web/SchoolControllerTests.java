package ee.hitsa.ois.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolStudyLevel;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyLevelsCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class SchoolControllerTests {

    private static final Long MISSING_SCHOOL_ID = Long.valueOf(0);

    private School school;

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        school = schoolRepository.getOne(hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchoolId());
    }

    @Test
    public void get() {
        ResponseEntity<Object> responseEntity = restTemplate
                .getForEntity(String.format("/school/%d", MISSING_SCHOOL_ID), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        Assert.assertNotNull(school);
        responseEntity = restTemplate.getForEntity(String.format("/school/%d", school.getId()), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void search() {
        String uri = UriComponentsBuilder.fromUriString("/school").queryParam("lang", "ET").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = UriComponentsBuilder.fromUriString("/school").queryParam("name", "Nimetus").queryParam("lang", "ET")
                .build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = UriComponentsBuilder.fromUriString("/school").queryParam("name", "Nimetus").queryParam("lang", "EN")
                .build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studyLevels() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/school/studyLevels", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateStudyLevels() {
        SchoolUpdateStudyLevelsCommand request = new SchoolUpdateStudyLevelsCommand();
        Assert.assertNotNull(school);
        Long initialVersion = school.getVersion();
        request.setVersion(Long.valueOf(initialVersion.longValue() + 1));

        ResponseEntity<Object> responseEntity = restTemplate.exchange("/school/studyLevels", HttpMethod.PUT,
                new HttpEntity<>(request), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        List<String> studyLevels = getStudyLevels();
        request.setVersion(initialVersion);
        request.setStudyLevels(studyLevels);
        responseEntity = restTemplate.exchange("/school/studyLevels", HttpMethod.PUT, new HttpEntity<>(request),
                Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        school = schoolRepository.getOne(school.getId());
        Assert.assertTrue(school.getVersion().longValue() >= initialVersion.longValue());
        Set<String> studyLevelCodes = school.getStudyLevels().stream().map(SchoolStudyLevel::getStudyLevel)
                .map(Classifier::getCode).collect(Collectors.toSet());
        // verify that values are changed
        Assert.assertTrue(studyLevelCodes.equals(studyLevels.stream().collect(Collectors.toSet())));
    }

    private List<String> getStudyLevels() {
        return classifierRepository.findAllByMainClassCode(MainClassCode.OPPEASTE.name()).stream().map(ClassifierSelection::getCode)
                .collect(Collectors.toList());
    }
}
