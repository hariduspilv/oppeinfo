package ee.hitsa.ois.web;

import java.util.Arrays;
import java.util.HashSet;
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

import ee.hitsa.ois.TestData;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.repository.StudyYearScheduleLegendRepository;
import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;
import ee.hitsa.ois.web.dto.StudyYearScheduleDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class StudyYearScheduleControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private StudyYearRepository studyYearRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private StudyYearScheduleLegendRepository studyYearScheduleLegendRepository;

    @Autowired 
    private TestData testData;

    private final String BASE_URL = "/studyYearSchedule";

    private Curriculum curriculum;
    private StudentGroup studentGroup;
    private StudyYear studyYear;
    private StudyPeriod studyPeriod;
    private StudyYearScheduleLegend legend;
    
    @Before
    public void generateTestData() {
//        curriculum = curriculumRepository.save(testData.getCurriculum());
//        studentGroup = studentGroupRepository.save(testData.getStudentGroup(curriculum)); 
        
        //TODO: temporary solution
        studentGroup = studentGroupRepository.findOne(Long.valueOf(38));
        studyYear = studyYearRepository.findOne(Long.valueOf(15));
        studyPeriod = studyPeriodRepository.findOne(Long.valueOf(27));
        legend = studyYearScheduleLegendRepository.findAll().stream().findFirst().get();
        /*
         * Not able to generate studPeriod and legend because insertedBy is obligatory.
         * It cannot be set via setter
         */
//        studyYear = studyYearRepository.save(testData.getStudyYear());
//        studPeriod = studyPeriodRepository.save(testData.getStudyPeriod(studyYear));
        
//        legend = studyYearScheduleLegendRepository.save(testData.getStudyYearScheduleLegend());
//        System.out.println("Hola!");
    }
    
    @After
    public void deleteTestData() {
//        if(studentGroup != null) {
//            studentGroupRepository.delete(studentGroup);
//        }
//        if(curriculum != null) {
//            curriculumRepository.delete(curriculum);
//        }
//        if(studyPeriod != null) {
//            studyPeriodRepository.delete(studyPeriod);
//        }
//        if(studyYear != null) {
//            studyYearRepository.delete(studyYear);
//        }
//        if(legend != null) {
//            studyYearScheduleLegendRepository.delete(legend);
//        }
    }

    @Test
    public void getStudyYearSchedules() {
//        String URL = BASE_URL + "/" + studyYear.getId();
        String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("studyPeriods", "1", "2", "3").build().toUriString();
        ResponseEntity<StudyYearScheduleDtoContainer> responseEntity = 
                restTemplate.getForEntity(uri, StudyYearScheduleDtoContainer.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
    }

//    @Ignore
    @Test
    public void crud() {
        Long weekNr = Long.valueOf(1);
//        String URL = BASE_URL + "/" + studyYear.getId();

        // create
        StudyYearScheduleDtoContainer container = new StudyYearScheduleDtoContainer();
//        container.setStudyYear(studyYear.getId());
        container.setStudyYearSchedules(Arrays.asList(getDto(weekNr++), getDto(weekNr++))
                .stream().collect(Collectors.toSet()));
        
        Set<Long> studyPeriods = new HashSet<>();
        studyPeriods.add(studyPeriod.getId());
        container.setStudyPeriods(studyPeriods);
        
        ResponseEntity<StudyYearScheduleDtoContainer> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
                new HttpEntity<>(container), StudyYearScheduleDtoContainer.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());

        // add one and remove other
        container = responseEntity.getBody();
//        Assert.assertTrue(container.getStudyYearSchedules().size() == 2);
//        Set<StudyYearScheduleDto> dtoSet = dtos.getStudyYearSchedules();
//        
//        System.out.println("<--- Hola update! " + dtoSet.size());
        
        // delete all
        container = responseEntity.getBody();
        container.getStudyYearSchedules().clear();
        responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
                new HttpEntity<>(container), StudyYearScheduleDtoContainer.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
    }

    public StudyYearScheduleDto getDto(Long weekNr) {
        StudyYearScheduleDto dto = new StudyYearScheduleDto();
        dto.setStudentGroup(studentGroup.getId());
        dto.setStudyPeriod(studyPeriod.getId());
        dto.setStudyYearScheduleLegend(legend.getId());
        dto.setWeekNr(weekNr);
        return dto;
    }
}
