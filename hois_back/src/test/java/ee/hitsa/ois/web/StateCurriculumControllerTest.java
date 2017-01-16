package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StateCurriculum;
import ee.hitsa.ois.domain.StateCurriculumModule;
import ee.hitsa.ois.domain.StateCurriculumModuleOccupation;
import ee.hitsa.ois.domain.StateCurriculumModuleOutcome;
import ee.hitsa.ois.domain.StateCurriculumOccupation;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.StateCurriculumService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StateCurriculumControllerTest {

    private static final Long MISSING_ID = Long.valueOf(0);
    private static final String NAME = "StateCurriculumTest";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    StateCurriculumRepository repository;
    
    @Autowired
    ClassifierService classifierService;
    
    @Autowired
    StateCurriculumService stateCurriculumService;
    
    private StateCurriculum stateCurriculum;
    
    @Before
    public void setUp() {
        stateCurriculum = getNew(NAME, Integer.valueOf(1));
        stateCurriculum = stateCurriculumService.create(stateCurriculum);
    }
    
    @After
    public void cleanUp() {
        stateCurriculumService.delete(stateCurriculum);
    }
    
    @Test	
    public void testIsUniqueTrue() {
    	Assert.assertTrue(testIsUnique(NAME.concat(NAME)));
    }
    
    @Test
    public void testIsUniqueFalse() {
    	Assert.assertFalse(testIsUnique(NAME));
    }

    private Boolean testIsUnique(String name) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum/unique");
        uriBuilder.queryParam("paramName", "nameEt");
        uriBuilder.queryParam("paramValue", name);
        
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, Boolean.class);
    	        
    	Assert.assertNotNull(responseEntity.getBody());
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	return responseEntity.getBody();
    }

    @Test
    public void testGetMissing() {
        ResponseEntity<StateCurriculum> responseEntity = restTemplate.getForEntity("/stateCurriculum/{id}", StateCurriculum.class, MISSING_ID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void testGet() {
        ResponseEntity<StateCurriculum> responseEntity = restTemplate.getForEntity("/stateCurriculum/{id}", StateCurriculum.class, stateCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
    }
    
    @Test
    public void testSearchWithoutEkrLevel() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum");
        uriBuilder.queryParam("name", "test");
        uriBuilder.queryParam("lang", "ET");
        uriBuilder.queryParam("validFromMillis", Integer.valueOf(123456));
        uriBuilder.queryParam("statusCode", "OPPEKAVA_STAATUS_S", "OPPEKAVA_STAATUS_C");
        uriBuilder.queryParam("sort", "id,asc");
        
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void testSearchWithEkrLevel() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum");
        uriBuilder.queryParam("name", "test");
        uriBuilder.queryParam("lang", "ET");
        uriBuilder.queryParam("statusCode", "OPPEKAVA_STAATUS_S", "OPPEKAVA_STAATUS_C");
        uriBuilder.queryParam("ekrLevels", "Eesti kvalifikatsiooniraamistiku 6. tase", "Eesti kvalifikatsiooniraamistiku 4. tase");
        uriBuilder.queryParam("sort", "ekrLevel,asc");
        
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateAndDelete() {
        //create
        StateCurriculum newStateCurriculum = getNew("StateCurriculumTest", Integer.valueOf(1));
        ResponseEntity<StateCurriculum> responseEntity = this.restTemplate.postForEntity("/stateCurriculum", newStateCurriculum, StateCurriculum.class, new HashMap<>());

        Assert.assertNotNull(responseEntity);
	    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	    StateCurriculum createdStateCurriculum = responseEntity.getBody();
	    Assert.assertNotNull(createdStateCurriculum);
	    Assert.assertEquals(Long.valueOf(0), createdStateCurriculum.getVersion());
	    Assert.assertNotNull(createdStateCurriculum.getInserted());

	    //delete
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum/" + createdStateCurriculum.getId());
        uriBuilder.queryParam("version", Long.valueOf(0));
        String uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

    @Test
    public void testUpdate() {
    	String newNameEt = "newTestName";
    	stateCurriculum.setNameEt(newNameEt);
    	ResponseEntity<StateCurriculum> responseEntity =
		        this.restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculum), StateCurriculum.class, stateCurriculum.getId());

    	Assert.assertNotNull(responseEntity);
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	StateCurriculum updatedStateCurriculum = responseEntity.getBody();
		Assert.assertNotNull(updatedStateCurriculum);
		stateCurriculum = updatedStateCurriculum;
		Assert.assertEquals(Long.valueOf(1), updatedStateCurriculum.getVersion());
		
		Assert.assertNotNull(updatedStateCurriculum.getInserted());
		Assert.assertNotNull(updatedStateCurriculum.getChanged());
		Assert.assertNotEquals(updatedStateCurriculum.getInserted(), updatedStateCurriculum.getChanged());
		Assert.assertTrue(updatedStateCurriculum.getNameEt().equals(newNameEt));
    }

    private StateCurriculum getNew(String name, Integer credits) {
    	StateCurriculum newStateCurriculum = new StateCurriculum();
    	newStateCurriculum.setNameEt(name);
    	newStateCurriculum.setObjectivesEt(name);
    	newStateCurriculum.setOutcomesEt(name);
    	newStateCurriculum.setCredits(credits);
    	newStateCurriculum.setAdmissionRequirementsEt(name);
    	newStateCurriculum.setOptionalStudyCredits(credits);
    	newStateCurriculum.setValidFrom(LocalDate.now());
    	newStateCurriculum.setStatus(classifierService.findOne("OPPEKAVA_STAATUS_S"));
    	newStateCurriculum.setIscedClass(classifierService.findOne("ISCED_RYHM_0522"));
    	newStateCurriculum.setStateCurrClass(classifierService.findOne("EHIS_ROK_15744"));
    	
    	Classifier occupation = classifierService.findOne("KUTSE_10437390");
    	
    	StateCurriculumOccupation stateCurriculumOccupation = new StateCurriculumOccupation();
    	stateCurriculumOccupation.setOccupation(occupation);
    	Set<StateCurriculumOccupation> occupations = new HashSet<>();
    	occupations.add(stateCurriculumOccupation);
    	newStateCurriculum.setOccupations(occupations);
    	
    	StateCurriculumModule module = new StateCurriculumModule();
    	module.setNameEt(name);
    	module.setCredits(credits);
    	module.setObjectivesEt(name);
    	module.setAssessmentsEt(name);
    	module.setModuleType(classifierService.findOne("KUTSEMOODUL_Y"));
    	
    	StateCurriculumModuleOutcome outcome = new StateCurriculumModuleOutcome();
    	outcome.setOutcomesEt(name);
    	module.setOutcome(outcome);
    	
    	StateCurriculumModuleOccupation moduleOccupation = new StateCurriculumModuleOccupation();
    	moduleOccupation.setType('O');
    	moduleOccupation.setOccupation(occupation);
    	Set<StateCurriculumModuleOccupation> moduleOccupations = new HashSet<>();
    	moduleOccupations.add(moduleOccupation);
    	module.setModuleOccupations(moduleOccupations);
    	
    	Set<StateCurriculumModule> modules = new HashSet<>();
    	modules.add(module);
    	newStateCurriculum.setModules(modules);

    	return newStateCurriculum;
    }
}
