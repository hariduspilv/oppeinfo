package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Day;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.LessonTimeBuildingGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeGroupsDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LessonTimeControllerTests {

    private static String ENDPOINT = "/lessontimes";

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private TestConfigurationService testConfigurationService;

    List<AutocompleteResult> buildings;
    List<LocalDate> validFroms = new ArrayList<>();


    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
        buildings = autocompleteService.buildings(hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchoolId());
    }

    @After
    public void cleanUp() {
        if (!validFroms.isEmpty()) {
            for (LocalDate validFrom : validFroms) {
                LessonTimeGroupsDto form = new LessonTimeGroupsDto();
                form.setValidFrom(validFrom);
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
                restTemplate.exchange(uriBuilder.build().toUriString(), HttpMethod.PUT, new HttpEntity<>(form), LessonTimeGroupsDto.class);
            }
        }
    }

    @Test
    public void search() {
        ResponseEntity<LessonTimeSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, LessonTimeSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("from", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("thru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("day", Day.NADALAPAEV_E.name(), Day.NADALAPAEV_P.name());
        uriBuilder.queryParam("building", buildings.get(0).getId());

        String uri = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(uri, LessonTimeSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        validFroms.add(LocalDate.of(2011, 1, 1));

        //create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        String uri = uriBuilder.build().toUriString();
        LessonTimeGroupsDto form = new LessonTimeGroupsDto();
        ResponseEntity<LessonTimeGroupsDto> responseEntity = restTemplate.postForEntity(uri, form, LessonTimeGroupsDto.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setValidFrom(validFroms.get(0));
        LessonTimeBuildingGroupDto lessonTimeBuildingGroupDto = new LessonTimeBuildingGroupDto();
        lessonTimeBuildingGroupDto.setBuildings(new HashSet<>(Arrays.asList(buildings.get(0))));
        LessonTimeDto lessonTimeDto = new LessonTimeDto();
        lessonTimeDto.setDayMon(Boolean.TRUE);
        lessonTimeDto.setLessonNr(Short.valueOf((short) 1));
        lessonTimeDto.setStartTime(LocalTime.of(8, 0));
        lessonTimeDto.setEndTime(LocalTime.of(9, 0));
        lessonTimeBuildingGroupDto.setLessonTimes(new HashSet<>(Arrays.asList(lessonTimeDto)));

        form.setLessonTimeBuildingGroups(new HashSet<>(Arrays.asList(lessonTimeBuildingGroupDto)));

        responseEntity = restTemplate.postForEntity(uri, form, LessonTimeGroupsDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        LessonTimeDto savedLessonTime = responseEntity.getBody().getLessonTimeBuildingGroups().stream().findFirst().get().getLessonTimes().stream().findFirst().get();

        //read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(savedLessonTime.getId().toString());
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, LessonTimeGroupsDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getLessonTimeBuildingGroups().size());
        Assert.assertEquals(1, responseEntity.getBody().getLessonTimeBuildingGroups().stream().findFirst().get().getLessonTimes().size());
        Assert.assertEquals(form.getValidFrom(), responseEntity.getBody().getValidFrom());


        //update
        form = responseEntity.getBody();
        LessonTimeDto secondLessonTimeDto = new LessonTimeDto();
        secondLessonTimeDto.setDayMon(Boolean.TRUE);
        secondLessonTimeDto.setLessonNr(Short.valueOf((short) 2));
        secondLessonTimeDto.setStartTime(LocalTime.of(10, 0));
        secondLessonTimeDto.setEndTime(LocalTime.of(12, 0));
        form.getLessonTimeBuildingGroups().stream().findFirst().get().getLessonTimes().add(secondLessonTimeDto);
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), LessonTimeGroupsDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getLessonTimeBuildingGroups().size());
        Assert.assertEquals(2, responseEntity.getBody().getLessonTimeBuildingGroups().stream().findFirst().get().getLessonTimes().size());


        //delete
        form.getLessonTimeBuildingGroups().clear();
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.postForEntity(uri, form, LessonTimeGroupsDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void validThruIsSetToLatter() {

        //create first group
        validFroms.add(LocalDate.of(2011, 1, 1));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        String uri = uriBuilder.build().toUriString();
        LessonTimeGroupsDto form = new LessonTimeGroupsDto();
        form.setValidFrom(validFroms.get(0));
        LessonTimeBuildingGroupDto lessonTimeBuildingGroupDto = new LessonTimeBuildingGroupDto();
        lessonTimeBuildingGroupDto.setBuildings(new HashSet<>(Arrays.asList(buildings.get(0))));
        LessonTimeDto lessonTimeDto = new LessonTimeDto();
        lessonTimeDto.setDayMon(Boolean.TRUE);
        lessonTimeDto.setLessonNr(Short.valueOf((short) 1));
        lessonTimeDto.setStartTime(LocalTime.of(8, 0));
        lessonTimeDto.setEndTime(LocalTime.of(9, 0));
        lessonTimeBuildingGroupDto.setLessonTimes(new HashSet<>(Arrays.asList(lessonTimeDto)));

        form.setLessonTimeBuildingGroups(new HashSet<>(Arrays.asList(lessonTimeBuildingGroupDto)));

        ResponseEntity<LessonTimeGroupsDto>responseEntity = restTemplate.postForEntity(uri, form, LessonTimeGroupsDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LessonTimeDto savedLessonTime = responseEntity.getBody().getLessonTimeBuildingGroups().stream().findFirst().get().getLessonTimes().stream().findFirst().get();


        //create second group
        validFroms.add(LocalDate.of(2011, 1, 3));
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uri = uriBuilder.build().toUriString();
        form = new LessonTimeGroupsDto();
        form.setValidFrom(validFroms.get(1));
        lessonTimeBuildingGroupDto = new LessonTimeBuildingGroupDto();
        lessonTimeBuildingGroupDto.setBuildings(new HashSet<>(Arrays.asList(buildings.get(0))));
        lessonTimeDto = new LessonTimeDto();
        lessonTimeDto.setDayMon(Boolean.TRUE);
        lessonTimeDto.setLessonNr(Short.valueOf((short) 1));
        lessonTimeDto.setStartTime(LocalTime.of(8, 0));
        lessonTimeDto.setEndTime(LocalTime.of(9, 0));
        lessonTimeBuildingGroupDto.setLessonTimes(new HashSet<>(Arrays.asList(lessonTimeDto)));

        form.setLessonTimeBuildingGroups(new HashSet<>(Arrays.asList(lessonTimeBuildingGroupDto)));

        responseEntity = restTemplate.postForEntity(uri, form, LessonTimeGroupsDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


        //validate first group validThru
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(savedLessonTime.getId().toString());
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, LessonTimeGroupsDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(validFroms.get(1).minusDays(1), responseEntity.getBody().getLessonTimeBuildingGroups().stream().findFirst().get().getValidThru());
    }

    @Test
    public void currentPeriod() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("currentPeriod");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
