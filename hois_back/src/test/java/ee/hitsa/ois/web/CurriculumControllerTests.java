package ee.hitsa.ois.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Curriculum;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.web.commandobject.CurriculumForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class CurriculumControllerTests {

	private final static String CODE = "code";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private ClassifierService classifierService;

    @Autowired
    ObjectMapper objectMapper;

    private JacksonTester<CurriculumForm> curriculumFormJson;

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @After
    public void cleanUp() {
        if(testCurriculum != null && testCurriculum.getId() != null) {
            curriculumService.delete(testCurriculum);
        }
    }

    private Curriculum testCurriculum;


    @Test
    public void testIsUniqueTrue() {
    	Assert.assertTrue(testIsUnique(CODE.concat(CODE)));
    }

    @Test
    public void testIsUniqueFalse() {
    	Assert.assertFalse(testIsUnique(CODE));
    }

    @Test
    public void search() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/curriculum");
        uriBuilder.queryParam("sort", "id");
        uriBuilder.queryParam("isJoint", Boolean.FALSE);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void deserialization() throws IOException {
        Path p = Paths.get("src/test/java/data/curriculum.json");
        String json = new String(Files.readAllBytes(p));
        CurriculumForm curriculumForm = curriculumFormJson.parseObject(json);
        Assert.assertNotNull(curriculumForm);
        Assert.assertNotNull(curriculumForm.getCode());
        Assert.assertNotNull(curriculumForm.getDraft().getCode());
        Assert.assertFalse(curriculumForm.getStudyLanguageClassifiers().isEmpty());
        Assert.assertFalse(curriculumForm.getStudyFormClassifiers().isEmpty());
        Assert.assertFalse(curriculumForm.getSchoolDepartments().isEmpty());
        Assert.assertFalse(curriculumForm.getFiles().isEmpty());
        Assert.assertFalse(curriculumForm.getJointPartners().isEmpty());
        Assert.assertFalse(curriculumForm.getModules().isEmpty());
        Assert.assertNotNull(curriculumForm.getStudyFormClassifiers().stream().findFirst().get().getCode());
        Assert.assertNotNull(curriculumForm.getJointPartners().stream().findFirst().get().getEhisSchool().getCode());


        ResponseEntity<Curriculum> responseEntity = this.restTemplate
                .postForEntity("/curriculum", curriculumForm, Curriculum.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());
        Assert.assertNotNull(testCurriculum.getModules().stream().findFirst().get());
    }

    @Test
    public void create() {
        Classifier classifier = classifierService.findOne("OPPEKAVA_STAATUS_S");
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(classifier, validFrom);

        ResponseEntity<Curriculum> responseEntity = this.restTemplate
                .postForEntity("/curriculum", curriculumForm, Curriculum.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());

        Assert.assertNotNull(testCurriculum);
        Assert.assertEquals("testCode", testCurriculum.getCode());
        Assert.assertEquals(classifier, testCurriculum.getStatus());
        Assert.assertTrue(testCurriculum.isHigher());
        Assert.assertTrue(testCurriculum.getValidFrom().isEqual(validFrom));
        Assert.assertNotNull(testCurriculum.getStudyLanguages());
        Assert.assertNotNull(testCurriculum.getStudyLanguages().stream().findFirst().get().getId());

    }

    @Test
    public void update() {
        Classifier classifier = classifierService.findOne("OPPEKAVA_STAATUS_S");
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(classifier, validFrom);

        ResponseEntity<Curriculum> responseEntity = this.restTemplate
                .postForEntity("/curriculum", curriculumForm, Curriculum.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());
        //TODO: change some fields

    }

    @Test
    public void badUpdate() {
        Classifier classifier = classifierService.findOne("OPPEKAVA_STAATUS_S");
        Assert.assertNotEquals("badChange", classifier.getNameEt());
        classifier.setNameEt("badChange");
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(classifier, validFrom);


        ResponseEntity<Curriculum> responseEntity = this.restTemplate
                .postForEntity("/curriculum", curriculumForm, Curriculum.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());

        Assert.assertNotNull(testCurriculum);
        Assert.assertEquals("testCode", testCurriculum.getCode());
        Assert.assertEquals(classifier, testCurriculum.getStatus());
        Assert.assertNotEquals(classifier.getNameEt(), testCurriculum.getStatus().getNameEt());
        Assert.assertTrue(testCurriculum.isHigher());
        Assert.assertTrue(testCurriculum.getValidFrom().isEqual(validFrom));
        Assert.assertNotNull(testCurriculum.getStudyLanguages());
        Assert.assertNotNull(testCurriculum.getStudyLanguages().stream().findFirst().get().getId());
        Assert.assertNotEquals(classifier.getNameEt(), testCurriculum.getStudyLanguages().stream().findFirst().get().getStudyLang().getNameEt());

    }

    private boolean testIsUnique(String code) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/curriculum/unique");
        uriBuilder.queryParam("paramName", "code");
        uriBuilder.queryParam("paramValue", code);

        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, Boolean.class);

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity.getBody().booleanValue();
    }

    private CurriculumForm getForm(Classifier classifier, LocalDate validFrom) {
        CurriculumForm curriculumForm = new CurriculumForm();
        curriculumForm.setCode("testCode");
        curriculumForm.setOptionalStudyCredits(Integer.valueOf(1));
        curriculumForm.setStudyPeriod(Integer.valueOf(1));
        curriculumForm.setNameEn("nameEn");
        curriculumForm.setNameEt("nameEt");
        curriculumForm.setConsecution(classifier);
        curriculumForm.setOrigStudyLevel(classifier);
        curriculumForm.setDraft(classifier);
        curriculumForm.setStatus(classifier);
        curriculumForm.setHigher(true);
        curriculumForm.setValidFrom(validFrom);
        curriculumForm.setStudyLanguageClassifiers(new HashSet<>(Arrays.asList(classifier)));
        return curriculumForm;
    }


}
