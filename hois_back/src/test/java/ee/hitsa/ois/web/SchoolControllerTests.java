package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolStudyLevel;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyLevelsCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyYearScheduleLegendsCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class SchoolControllerTests {

    //TODO use pattern UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(...)
    private static final String ENDPOINT = "/school";

    private static final Long MISSING_SCHOOL_ID = Long.valueOf(0);

    private School school;

    @Autowired
    private AutocompleteService autocompleteService;

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestConfigurationService testConfigurationService;


    @Before
    public void setUp() {
        if(school == null) {
            school = schoolRepository.getOne(hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID).getSchoolId());
        }
        testConfigurationService.userToRoleInSchool(Role.ROLL_A, school.getId(), restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void get() {
        ResponseEntity<Object> responseEntity = restTemplate
                .getForEntity(String.format("/school/%d", MISSING_SCHOOL_ID), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        Assert.assertNotNull(school);
        responseEntity = restTemplate.getForEntity(String.format("/school/%d", school.getId()), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void search() {
        String uri = UriComponentsBuilder.fromUriString("/school").queryParam("lang", "ET").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = UriComponentsBuilder.fromUriString("/school").queryParam("name", "Nimetus").queryParam("lang", "ET")
                .build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = UriComponentsBuilder.fromUriString("/school").queryParam("name", "Nimetus").queryParam("lang", "EN")
                .build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studyLevels() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/school/studyLevels", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateStudyLevels() {
        SchoolUpdateStudyLevelsCommand request = new SchoolUpdateStudyLevelsCommand();
        Assert.assertNotNull(school);
        Long initialVersion = school.getVersion();
        request.setVersion(Long.valueOf(initialVersion.longValue() + 1));

        ResponseEntity<Object> responseEntity = restTemplate.exchange("/school/studyLevels", HttpMethod.PUT,
                new HttpEntity<>(request), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        List<String> studyLevels = getStudyLevels();
        request.setVersion(initialVersion);
        request.setStudyLevels(studyLevels);
        responseEntity = restTemplate.exchange("/school/studyLevels", HttpMethod.PUT, new HttpEntity<>(request),
                Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        school = schoolRepository.getOne(school.getId());
        Assert.assertTrue(school.getVersion().longValue() >= initialVersion.longValue());
        Set<String> studyLevelCodes = school.getStudyLevels().stream().map(SchoolStudyLevel::getStudyLevel)
                .map(Classifier::getCode).collect(Collectors.toSet());
        // verify that values are changed
        Assert.assertTrue(studyLevelCodes.equals(studyLevels.stream().collect(Collectors.toSet())));
    }

    private List<String> getStudyLevels() {
        return StreamUtil.toMappedList(ClassifierSelection::getCode, autocompleteService.classifiers(Collections.singletonList(MainClassCode.OPPEASTE.name())));
    }

    @Test
    public void studyYearScheduleLegends() {
        String uri = UriComponentsBuilder.fromUriString("/school/studyYearScheduleLegends").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateStudyYearScheduleLegends() {

        String uri = UriComponentsBuilder.fromUriString("/school/studyYearScheduleLegends").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<StudyYearScheduleLegendDto> legends = getUpdateLegendsResult(responseEntity.getBody());
        int initialDtos = legends.size();

        final String URL = "/school/studyYearScheduleLegends";
        final String CODE_1 = "A";
        final String CODE_2 = "B";
        final String CODE_3 = "C";
        SchoolUpdateStudyYearScheduleLegendsCommand request = new SchoolUpdateStudyYearScheduleLegendsCommand();
        legends.add(getlegendDto(CODE_1));
        legends.add(getlegendDto(CODE_2));
        request.setLegends(legends);

        responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,
                new HttpEntity<>(request), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<StudyYearScheduleLegendDto> responseDtos = getUpdateLegendsResult(responseEntity.getBody());
        Assert.assertEquals(initialDtos + 2, responseDtos.size());

        //remove one
        responseDtos = responseDtos.stream().filter(l -> !l.getCode().equals(CODE_1)).collect(Collectors.toList());
        request.setLegends(responseDtos);
        responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,
                new HttpEntity<>(request), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseDtos = getUpdateLegendsResult(responseEntity.getBody());
        Assert.assertEquals(initialDtos + 1, responseDtos.size());

        // update one
        StudyYearScheduleLegendDto item = responseDtos.stream().
                filter(l -> l.getCode().equals(CODE_2)).findFirst().get();
        item.setCode(CODE_3);
        request.setLegends(responseDtos);
        responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,
                new HttpEntity<>(request), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseDtos = getUpdateLegendsResult(responseEntity.getBody());

        Assert.assertEquals(initialDtos + 1, responseDtos.size());
        item = responseDtos.get(0);
        Assert.assertTrue(item.getCode().equals(CODE_3));
        
        // remove all
        request.setLegends(new ArrayList<>());
        responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,
                new HttpEntity<>(request), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studyYears() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/school/studyYears", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void teacherOccupations() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/school/teacheroccupations/all", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void generateEmail() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("generateEmail");
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri.toUriString(), Collections.singletonMap("lastname", "PEREKONNANIMI"), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(HttpStatus.OK.equals(responseEntity.getStatusCode()) || HttpStatus.BAD_REQUEST.equals(responseEntity.getStatusCode()));
    }

    @SuppressWarnings("unchecked")
    private static List<StudyYearScheduleLegendDto> getUpdateLegendsResult(Object body) {
        Map<String, ?> map = (Map<String, ?>) body;
        List<Object> legends = (List<Object>) map.get("legends");
        if(legends == null || legends.isEmpty()) {
            return new ArrayList<>();
        }
        List<StudyYearScheduleLegendDto> output = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Object o : legends) {
           StudyYearScheduleLegendDto item = mapper.convertValue(o, StudyYearScheduleLegendDto.class);
           output.add(item);
        }
        return output;
    }

    private static StudyYearScheduleLegendDto getlegendDto(String code) {
        StudyYearScheduleLegendDto dto = new StudyYearScheduleLegendDto();
        dto.setCode(code);
        dto.setNameEt("StudyYearScheduleLegendsTest");
        dto.setColor("#FFFFFF");
        return dto;
    }
}
