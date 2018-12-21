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
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolStudentCreateForm;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.dto.ModuleProtocolDto;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ModuleProtocolControllerTests {

    private static final String ENDPOINT = "/moduleProtocols";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private ProtocolRepository protocolRepository;

    private School userSchool;
    private Protocol existingProtocol;
    private Long protocolId;

    @Before
    public void setUp() {
        if(existingProtocol == null) {
            existingProtocol = protocolRepository.findAll().stream()
                    .filter(it -> !it.getProtocolStudents().isEmpty() && it.getProtocolVdata() != null).findFirst().get();
            userSchool = existingProtocol.getSchool();
        }
        Role role = Role.ROLL_A;
        testConfigurationService.userToRoleInSchool(role, userSchool.getId(), restTemplate);

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
        ResponseEntity<ModuleProtocolSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT,
                ModuleProtocolSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("studyYear", Long.valueOf(1L));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1L));
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1L));
        uriBuilder.queryParam("module", Long.valueOf(1L), Long.valueOf(2L));
        uriBuilder.queryParam("status", "PROTOKOLL_STAATUS_S");
        uriBuilder.queryParam("protocolNr", "123");
        uriBuilder.queryParam("confirmDateFrom", "2017-05-26T00:00:00.000Z");
        uriBuilder.queryParam("confirmDateThru", "2017-05-26T01:00:00.000Z");
        uriBuilder.queryParam("insertedFrom", "2017-05-26T00:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-05-26T01:00:00.000Z");

        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), ModuleProtocolSearchDto.class);
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

        List<ModuleProtocolStudentCreateForm> protocolStudents = existingProtocol.getProtocolStudents().stream()
                .map(it -> {
                    ModuleProtocolStudentCreateForm form = new ModuleProtocolStudentCreateForm();
                    form.setStudentId(it.getStudent().getId());
                    return form;
                }).collect(Collectors.toList());

        ModuleProtocolCreateForm form = new ModuleProtocolCreateForm();
        form.setProtocolVdata(protocolVdata);
        form.setProtocolStudents(protocolStudents);

        ResponseEntity<ModuleProtocolDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form,
                ModuleProtocolDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        protocolId = responseEntity.getBody().getId();

        // get
        UriComponentsBuilder uri = uriBuilder.pathSegment(protocolId.toString());
        responseEntity = restTemplate.getForEntity(uri.toUriString(), ModuleProtocolDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // TODO: update - needs teacher login

        // delete
        delete(protocolId);
        protocolId = null;
    }

    @Test
    public void occupationModules() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("occupationModules")
                .pathSegment(existingProtocol.getProtocolVdata().getCurriculumVersion().getId().toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void occupationModule() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("occupationModule")
                .pathSegment(
                        existingProtocol.getProtocolVdata().getCurriculumVersionOccupationModule().getId().toString());

        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void otherStudents() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment(existingProtocol.getId().toString()).pathSegment("otherStudents");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addStudents() {
        // TODO
    }

    @Test
    public void confirm() {
        // TODO
    }

    private void delete(Long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);

        UriComponentsBuilder uri = uriBuilder.pathSegment(id.toString());
        ResponseEntity<ModuleProtocolDto> responseEntity = restTemplate.getForEntity(uri.toUriString(),
                ModuleProtocolDto.class);

        Long version = responseEntity.getBody().getVersion();
        uri = uriBuilder.pathSegment(id.toString());
        uri.queryParam("version", version);
        restTemplate.delete(uri.toUriString());
    }

}
