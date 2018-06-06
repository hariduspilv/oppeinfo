package ee.hitsa.ois.web;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.TestUtil;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentGroupYearTransferControllerTests {

    private static final String ENDPOINT = "/studentGroupYearTransfer";
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;

    @Before
    public void setUp() {
        testConfigurationService.userToRoleInSchool(Role.ROLL_A, Long.valueOf(10), restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    private List<StudyYearSearchDto> getStudyYears() {
        ResponseEntity<List<StudyYearSearchDto>> responseEntity = TestUtil.getParameterizedEntity(restTemplate, 
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("studyYears")
                .toUriString(), StudentGroupYearTransferController.class, "studyYears", HoisUserDetails.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    @Test
    public void studyYears() {
        List<StudyYearSearchDto> studyYears = getStudyYears();
        assertTrue(studyYears.size() >= 2);
        assertTrue(studyYears.get(0).getEndDate().isBefore(studyYears.get(1).getStartDate()));
    }

    @Test
    public void searchLogs() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT)
                .queryParam("id", Long.valueOf(30))
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchCurrent() {
        List<StudyYearSearchDto> studyYears = getStudyYears();
        Long studyYearId = getCurrentStudyYearId(studyYears);
        if (studyYearId == null) {
            return;
        }
        
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT)
                .queryParam("id", studyYearId)
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private static Long getCurrentStudyYearId(List<StudyYearSearchDto> studyYears) {
        LocalDate now = LocalDate.now();
        Optional<StudyYearSearchDto> result = studyYears.stream()
            .filter(sy -> now.isAfter(sy.getStartDate()) && now.isBefore(sy.getEndDate()))
            .findAny();
        return result.isPresent() ? result.get().getId() : null;
    }

}
