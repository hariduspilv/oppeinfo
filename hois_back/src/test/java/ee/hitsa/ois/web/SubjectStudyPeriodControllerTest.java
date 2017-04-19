package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
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

import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodTeacherForm;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SubjectStudyPeriodControllerTest {
    
    private static final String BASE_URL = "/subjectStudyPeriods";
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void search() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        uriBuilder.queryParam("subjectNameAndCode", "3211212");
        uriBuilder.queryParam("teachersFullname", "Name");
        uriBuilder.queryParam("studyPeriods", "1,2,3");
        uriBuilder.queryParam("sort", "s.nameEt,s.code,asc");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    /**
     * TODO: 
     * For now test method does not generate it's own data for testing
     * (teachers, persons, study periods, study years, subjects).
     */
    @Test
    public void crud() {
        SubjectStudyPeriodForm form = getForm();

        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<SubjectStudyPeriodDto> responseEntity = restTemplate.postForEntity(uri, form, SubjectStudyPeriodDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SubjectStudyPeriodDto dto = responseEntity.getBody();
        Assert.assertNotNull(dto);
        Long id = dto.getId();
        Assert.assertNotNull(id);
        Long version = dto.getVersion();
        Assert.assertNotNull(version);
        Assert.assertEquals(Long.valueOf(0), version);
        dto.getTeachers().forEach(t -> {
            Assert.assertEquals(Long.valueOf(0), t.getVersion());
        });

        //read
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<SubjectStudyPeriodDto> response = restTemplate.getForEntity(uri, SubjectStudyPeriodDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        dto = response.getBody();
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
        Assert.assertNotNull(dto.getVersion());
        dto.getTeachers().forEach(t -> {
            Assert.assertNotNull(t.getVersion());
        });

        //update 
        SubjectStudyPeriodTeacherDto t1 = new SubjectStudyPeriodTeacherDto();
        t1.setTeacherId(Long.valueOf(22));
        t1.setIsSignatory(Boolean.TRUE);
        dto.getTeachers().add(t1);

        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(dto), SubjectStudyPeriodDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        dto = responseEntity.getBody();
        Assert.assertNotNull(dto.getVersion());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL).pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
    
    private static SubjectStudyPeriodForm getForm() {
        SubjectStudyPeriodForm form = new SubjectStudyPeriodForm();
        form.setStudyPeriod(Long.valueOf(4));
        form.setSubject(Long.valueOf(51));
        
        SubjectStudyPeriodTeacherForm t1 = new SubjectStudyPeriodTeacherForm();
        t1.setTeacherId(Long.valueOf(8));
        t1.setIsSignatory(Boolean.FALSE);
        
        SubjectStudyPeriodTeacherForm t2 = new SubjectStudyPeriodTeacherForm();
        t2.setTeacherId(Long.valueOf(10));
        t2.setIsSignatory(Boolean.TRUE);
        
        List<SubjectStudyPeriodTeacherForm> teachers = new ArrayList<>();
        teachers.add(t1);
        teachers.add(t2);
        
        form.setTeachers(teachers);
        
        return form;
    }
}
