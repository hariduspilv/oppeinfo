package ee.hitsa.ois.web;

import java.math.BigDecimal;
import java.util.Arrays;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.MidtermTaskUpdateForm;
import ee.hitsa.ois.web.dto.MidtermTaskDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MidtermTaskControllerTests {

    private static String ENDPOINT = "/midtermTasks";
    private static String TEXT = "MidtermTaskControllerTest";
    private static Long SUBJECT_STUDY_PERIOD_ID = Long.valueOf(374);

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
    public void searchSubjectStudyPeriods() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/subjectStudyPeriods/1");
        uriBuilder.queryParam("studyPeriod", Long.valueOf(1L));
        uriBuilder.queryParam("subject", Long.valueOf(1L));
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getMidtermTasks() {
        ResponseEntity<MidtermTaskUpdateForm> responseEntity = restTemplate.getForEntity(String.format(ENDPOINT + "/%d", SUBJECT_STUDY_PERIOD_ID), MidtermTaskUpdateForm.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void updateMidtermTasks() {
        final String URL = ENDPOINT + "/" + SUBJECT_STUDY_PERIOD_ID;
        MidtermTaskUpdateForm form = new MidtermTaskUpdateForm();
        form.setMidtermTasks(new HashSet<>(Arrays.asList(getMidtermTaskDto(), getMidtermTaskDto())));

        // create midtermTasks
        ResponseEntity<MidtermTaskUpdateForm> responseEntity = restTemplate.exchange
                (URL, HttpMethod.PUT, new HttpEntity<>(form), MidtermTaskUpdateForm.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //update one midtermTask and remove other
        form = responseEntity.getBody();
        MidtermTaskDto dto = form.getMidtermTasks().stream().findAny().get();
        final String NEW_NAME = TEXT + 2;
        dto.setNameEt(NEW_NAME);

        form.setMidtermTasks(new HashSet<>(Arrays.asList(dto)));
        responseEntity = restTemplate.exchange
                (URL, HttpMethod.PUT, new HttpEntity<>(form), MidtermTaskUpdateForm.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        form = responseEntity.getBody();
        Assert.assertEquals(1, responseEntity.getBody().getMidtermTasks().size());
        Assert.assertEquals(NEW_NAME, form.getMidtermTasks().stream().findAny().get().getNameEt());

        // delete midtermTasks
        form.setMidtermTasks(null);
        responseEntity = restTemplate.exchange
                (URL, HttpMethod.PUT, new HttpEntity<>(form), MidtermTaskUpdateForm.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    public MidtermTaskDto getMidtermTaskDto() {
        MidtermTaskDto dto = new MidtermTaskDto();
        dto.setNameEt(TEXT);
        dto.setDescriptionEt(TEXT);
        dto.setMaxPoints(BigDecimal.ONE);
        dto.setPercentage(Short.valueOf((short) 10));
        return dto;
    }

    @Test
    public void getStudentsResults() {
        ResponseEntity<MidtermTaskUpdateForm> responseEntity = restTemplate.getForEntity(String.format(ENDPOINT + "/studentResults/%d", SUBJECT_STUDY_PERIOD_ID), MidtermTaskUpdateForm.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
    }
}
