package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.ArrayList;

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

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.CommitteeRepository;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.CommitteeMemberDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommitteeControllerTest {

    private static final String ENDPOINT = "/committees";
    private static final String TEXT = "CommitteeControllerTest";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private CommitteeRepository committeeRepository;

    private Long committeeId;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        if (committeeId != null) {
            delete(committeeId);
        }
        testConfigurationService.setSessionCookie(null);
    }

    private void delete(Long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);

        UriComponentsBuilder uri = uriBuilder.pathSegment(id.toString());
        ResponseEntity<CommitteeDto> responseEntity = restTemplate.getForEntity(uri.toUriString(),
                CommitteeDto.class);
        Long version = responseEntity.getBody().getVersion();
        this.restTemplate.delete(ENDPOINT + "/" + id + "?version=" + version);

    }
    
    @Test
    public void databaseNotPolluted() {
        Assert.assertFalse(committeeRepository.existsByAddInfo(TEXT));
    }

    @Test
    public void search() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("memberName", "random name");
        uriBuilder.queryParam("teacher", Long.valueOf(1));
        uriBuilder.queryParam("showInvalid", Boolean.FALSE);
        uriBuilder.queryParam("validFrom", "2016-10-31T22:00:00.000Z");
        uriBuilder.queryParam("validThru", "2016-12-31T22:00:00.000Z");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getMembers() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/members");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        CommitteeDto dto = getCommitteeDto();
         dto.getMembers().add(getExternalMemberDto());
        
        //create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ResponseEntity<CommitteeDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), dto,
                CommitteeDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        dto = responseEntity.getBody();
        committeeId = dto.getId();
        Assert.assertEquals(1, dto.getMembers().size());
        
        // update
        responseEntity = restTemplate.exchange(ENDPOINT + "/{id}", HttpMethod.PUT, new HttpEntity<>(dto), CommitteeDto.class, dto.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // get
        UriComponentsBuilder uri = uriBuilder.pathSegment(committeeId.toString());
        responseEntity = restTemplate.getForEntity(uri.toUriString(), CommitteeDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                
        delete(committeeId);
        committeeId = null;
    }

    public CommitteeDto getCommitteeDto() {
        CommitteeDto dto = new CommitteeDto();
        dto.setAddInfo(TEXT);
        dto.setValidFrom(LocalDate.now());
        dto.setValidThru(LocalDate.now().plusMonths(1));
        dto.setMembers(new ArrayList<>());
        return dto;
    }

    public CommitteeMemberDto getExternalMemberDto() {
        CommitteeMemberDto dto = new CommitteeMemberDto();
        dto.setIsChairman(Boolean.TRUE);
        dto.setIsExternal(Boolean.TRUE);
        dto.setMemberName(TEXT);
        return dto;
    }
}
