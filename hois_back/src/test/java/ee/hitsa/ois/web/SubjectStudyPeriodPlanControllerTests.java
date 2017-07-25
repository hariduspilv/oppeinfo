package ee.hitsa.ois.web;

import java.util.HashSet;

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
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SubjectStudyPeriodPlanControllerTests {
    
    private static final String BASE_URL = "/subjectStudyPeriodPlans";
    
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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        uriBuilder.queryParam("studyPeriod", "27");
        uriBuilder.queryParam("curriculum", "3");
        uriBuilder.queryParam("subject", "41");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void get() {
        Long id = Long.valueOf(3);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<SubjectStudyPeriodPlanDto> response = restTemplate.getForEntity(uri, SubjectStudyPeriodPlanDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        SubjectStudyPeriodPlanDto dto = response.getBody();
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
    }
    
    @Test
    public void createDelete() {
        SubjectStudyPeriodPlanDto form = getForm();
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<SubjectStudyPeriodPlanDto> responseEntity = restTemplate.postForEntity(uri, form, SubjectStudyPeriodPlanDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SubjectStudyPeriodPlanDto dto = responseEntity.getBody();
        Assert.assertNotNull(dto);
        Long id = dto.getId();
        Assert.assertNotNull(id);
        Long version = dto.getVersion();
        Assert.assertNotNull(version);
        Assert.assertEquals(Long.valueOf(0), version);

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

    public SubjectStudyPeriodPlanDto getForm() { 
        final Long subjectId = Long.valueOf(41);
        final Long studyPeriodId = Long.valueOf(27);
        
        SubjectStudyPeriodPlanDto form = new SubjectStudyPeriodPlanDto();
        form.setSubject(subjectId);
        form.setStudyPeriod(studyPeriodId);
        
        form.setCapacities(new HashSet<>());
        form.getCapacities().add(getCapacity());
        
        form.setStudyForms(new HashSet<>());
        form.getStudyForms().add("OPPEVORM_P");
        
        form.setCurriculums(new HashSet<>());
        form.getCurriculums().add(Long.valueOf(3));
        return form;
    }

    public SubjectStudyPeriodPlanCapacityDto getCapacity() {
        
        SubjectStudyPeriodPlanCapacityDto dto = new SubjectStudyPeriodPlanCapacityDto();
        dto.setHours(Long.valueOf(1));
        dto.setIsContact(Boolean.FALSE);
        dto.setCapacityType(CapacityType.MAHT_a.name());
        return dto;
    }

}
