package ee.hitsa.ois.web;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamProtocolStudentCreateForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamVocationalProtocolDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FinalExamVocationalProtocolControllerTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private ProtocolRepository protocolRepository;
    
    private static final String ENDPOINT = "/finalExamVocationalProtocols";
    
    private static final String DATE = "2017-01-01T00:00:00.000Z";
    
    private Protocol existingProtocol;
    private Long protocolId;
    
    @Before
    public void setUp() {
        if(existingProtocol == null) {
            existingProtocol = protocolRepository.findAll().stream()
                    .filter(it -> !it.getProtocolStudents().isEmpty() && it.getProtocolVdata() != null).findFirst().get();
        }
        
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        if (protocolId != null) {
            delete(protocolId);
        }
        testConfigurationService.setSessionCookie(null);
    }
    
    @Test
    public void search() {
        ResponseEntity<FinalExamVocationalProtocolSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, FinalExamVocationalProtocolSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("status", ProtocolStatus.PROTOKOLL_STAATUS_S.name());
        uriBuilder.queryParam("insertedFrom", DATE);
        uriBuilder.queryParam("insertedThru", DATE);
        uriBuilder.queryParam("confirmDateFrom", DATE);
        uriBuilder.queryParam("confirmDateThru", DATE);
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        uriBuilder.queryParam("studyYear", Long.valueOf(1L));
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1L));
        uriBuilder.queryParam("protocolNr", "180001");
        uriBuilder.queryParam("module", Long.valueOf(1L), Long.valueOf(2L));

        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), FinalExamVocationalProtocolSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ProtocolVdataForm protocolVdata = new ProtocolVdataForm();
        protocolVdata.setStudyYear(existingProtocol.getProtocolVdata().getStudyYear().getId());
        protocolVdata.setTeacher(existingProtocol.getProtocolVdata().getTeacher().getId());
        protocolVdata.setCurriculumVersion(existingProtocol.getProtocolVdata().getCurriculumVersion().getId());
        protocolVdata.setCurriculumVersionOccupationModule(
                existingProtocol.getProtocolVdata().getCurriculumVersionOccupationModule().getId());

        List<FinalExamProtocolStudentCreateForm> protocolStudents = existingProtocol.getProtocolStudents().stream()
                .map(it -> {
                    FinalExamProtocolStudentCreateForm form = new FinalExamProtocolStudentCreateForm();
                    form.setStudentId(it.getStudent().getId());
                    return form;
                }).collect(Collectors.toList());

        FinalExamVocationalProtocolCreateForm createForm = new FinalExamVocationalProtocolCreateForm();
        createForm.setProtocolVdata(protocolVdata);
        createForm.setProtocolStudents(protocolStudents);

        ResponseEntity<FinalExamVocationalProtocolDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), createForm,
                FinalExamVocationalProtocolDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        protocolId = responseEntity.getBody().getId();

        // get
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(protocolId.toString());
        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), FinalExamVocationalProtocolDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        // update
        // needs permissions

        // delete
        delete(protocolId);
        protocolId = null;
    }

    private void delete(Long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);

        UriComponentsBuilder uri = uriBuilder.pathSegment(id.toString());
        ResponseEntity<FinalExamVocationalProtocolDto> responseEntity = restTemplate.getForEntity(uri.toUriString(),
                FinalExamVocationalProtocolDto.class);

        Long version = responseEntity.getBody().getVersion();
        uri = uriBuilder.pathSegment(id.toString());
        uri.queryParam("version", version);
        restTemplate.delete(uri.toUriString());
    }
    
    @Test
    public void curriculumVersions() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("curriculumVersions");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void occupationModules() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("occupationModules")
                .pathSegment(Long.valueOf(1L).toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void occupationModule() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("occupationModule")
                .pathSegment(Long.valueOf(1L).toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void committees() {
        // Without date
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("committees");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        // With date
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("committees");
        uriBuilder.queryParam("finalExamDate", DATE);
        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
