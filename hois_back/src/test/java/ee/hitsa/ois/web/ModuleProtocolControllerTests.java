package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCommand;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ProtocolDto;

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
    private UserRepository userRepository;

    private School userSchool;

    @Before
    public void setUp() {
        Role role = Role.ROLL_A;
        userSchool = userRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.isNotNull(root.get("school")));
            filters.add(cb.equal(root.get("role").get("code"), role.name()));
            filters.add(cb.equal(root.get("person").get("idcode"), TestConfiguration.USER_ID));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().findFirst().get().getSchool();
        testConfigurationService.userToRoleInSchool(role, userSchool.getId(), restTemplate);
    }

    @After
    public void cleanUp() {
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
    public void createInitial() {
        //TODO: remove hard coded values
        ModuleProtocolCommand cmd = new ModuleProtocolCommand();
        cmd.setCurriculumVersion(Long.valueOf(1840L));
        cmd.setCurriculumVersionOccupationModule(Long.valueOf(366L));
        cmd.setStudents(Arrays.asList(Long.valueOf(20L)));
        cmd.setStudyYear(Long.valueOf(11L));
        cmd.setTeacher(Long.valueOf(1L));

        ResponseEntity<ProtocolDto> responseEntity = restTemplate.postForEntity(ENDPOINT+"/create", cmd, ProtocolDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
