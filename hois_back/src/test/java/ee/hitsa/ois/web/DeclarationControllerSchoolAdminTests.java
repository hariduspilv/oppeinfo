package ee.hitsa.ois.web;

import javax.persistence.EntityManager;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.DeclarationService;
import ee.hitsa.ois.web.dto.DeclarationDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeclarationControllerSchoolAdminTests {
    
    private static final String ENDPOINT = "/declarations";
    private static final Long STUDENT_ID = Long.valueOf(107L); 

    @Autowired
    private DeclarationService declarationService;
    @Autowired
    private EntityManager em;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;

    
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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("studyPeriod", Long.valueOf(1L));
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1L));
        uriBuilder.queryParam("studentGroups", Long.valueOf(1L));
        uriBuilder.queryParam("studentsName", "Mari Tudeng");
        uriBuilder.queryParam("status", "OPINGUKAVA_STAATUS_S");
        uriBuilder.queryParam("repeatingDeclaration", "true");
        uriBuilder.queryParam("insertedFrom", "2017-05-26T00:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-05-26T01:00:00.000Z");
        uriBuilder.queryParam("confirmedFrom", "2017-05-26T00:00:00.000Z");
        uriBuilder.queryParam("confirmedThru", "2017-05-26T01:00:00.000Z");
        ResponseEntity<DeclarationDto> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), DeclarationDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private void basicTest(String url) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void canCreate() {
        basicTest(ENDPOINT + "/canCreate");
    }

    @Test
    public void getStudentsWithoutDeclaration() {
        basicTest(ENDPOINT + "/students");
    }

    @Test
    public void searchStudentsWithoutDeclaration() {
        basicTest(ENDPOINT + "/withoutDeclaration");
    }

    @Test
    public void getCurrentStudyPeriod() {
        basicTest(ENDPOINT + "/currentStudyPeriod");
    }

    /*
     * TODO: adding/removing subjects
     * 
     * FIXME: test fails because it pollutes the database
     */
    @Test
    public void crud() {
        //create declaration
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/create/" + STUDENT_ID);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<DeclarationDto> responseEntity = restTemplate.postForEntity(uri, null, DeclarationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long id = responseEntity.getBody().getId();
        Assert.assertNotNull(id);
        
        //confirm
        responseEntity = restTemplate.exchange(ENDPOINT + "/confirm/" + id, HttpMethod.PUT, null, DeclarationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(DeclarationStatus.OPINGUKAVA_STAATUS_K.name(), responseEntity.getBody().getStatus());
        
        // remove confirmation
        responseEntity = restTemplate.exchange(ENDPOINT + "/removeConfirm/" + id, HttpMethod.PUT, null, DeclarationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(DeclarationStatus.OPINGUKAVA_STAATUS_S.name(), responseEntity.getBody().getStatus());

        // get
        responseEntity = restTemplate.getForEntity(String.format(ENDPOINT + "/%d", id), DeclarationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        // get modules 
        basicTest(ENDPOINT + "/modules/" + id);
        
        // get curriculum subjects
        basicTest(ENDPOINT + "/subjects/" + id);
        
        // get extra curriculum subjects
        basicTest(ENDPOINT + "/subjects/extracurriculum/" + id);

        // delete
        declarationService.delete(testConfigurationService.getHoisUserDetails(), em.getReference(Declaration.class, id));
    }
}
