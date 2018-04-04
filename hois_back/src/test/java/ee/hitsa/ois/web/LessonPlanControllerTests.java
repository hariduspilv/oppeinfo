package ee.hitsa.ois.web;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
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

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.GroupProportion;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.VocationalGradeType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LessonPlanControllerTests {

    private static final String ENDPOINT = "/lessonplans";
    private static final Role ROLE = Role.ROLL_A;
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private EntityManager em;

    private Long schoolId;
    private Long lessonPlanId;
    private Long occupationModuleId;
    private Long moduleThemeId;
    private Long lessonPlanModuleId;

    private void setUp() {
        testConfigurationService.userToRole(ROLE, restTemplate);
    }

    private void setUpNewModule() {
        List<School> userSchools = testConfigurationService.personSchools(ROLE);
        Assert.assertFalse(userSchools.isEmpty());
        
        Object result = em.createNativeQuery("select lp.id as lesson_plan_id, cvom.id as module_id, cvomt.id as theme_id, c.school_id"
                + " from curriculum c"
                + " inner join curriculum_version cv on cv.curriculum_id = c.id"
                + " inner join curriculum_version_omodule cvom on cvom.curriculum_version_id = cv.id"
                + " inner join curriculum_version_omodule_theme cvomt on cvomt.curriculum_version_omodule_id = cvom.id"
                + " inner join curriculum_version_omodule_theme_capacity cvomtc on cvomtc.curriculum_version_omodule_theme_id = cvomt.id"
                + " inner join lesson_plan lp on lp.school_id = c.school_id"
                + " left join lesson_plan_module lpm on lpm.curriculum_version_omodule_id = cvom.id and lpm.lesson_plan_id = lp.id"
                + " where lpm.id is null and lp.school_id in (?1)")
            .setParameter(1, userSchools)
            .setMaxResults(1).getResultList().get(0);
        lessonPlanId = resultAsLong(result, 0);
        occupationModuleId = resultAsLong(result, 1);
        moduleThemeId = resultAsLong(result, 2);
        schoolId = resultAsLong(result, 3);
        
        testConfigurationService.userToRoleInSchool(ROLE, schoolId, restTemplate);
    }

    private void setUpOldModule() {
        List<School> userSchools = testConfigurationService.personSchools(ROLE);
        Assert.assertFalse(userSchools.isEmpty());
        
        Object result = em.createNativeQuery("select lp.id as lesson_plan_id, cvom.id as module_id, cvomt.id as theme_id"
                + ", lpm.id as lesson_plan_module_id, lp.school_id"
                + " from lesson_plan lp"
                + " inner join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                + " inner join curriculum_version_omodule cvom on cvom.id = lpm.curriculum_version_omodule_id"
                + " inner join curriculum_version_omodule_theme cvomt on cvomt.curriculum_version_omodule_id = cvom.id"
                + " inner join curriculum_version_omodule_theme_capacity cvomtc on cvomtc.curriculum_version_omodule_theme_id = cvomt.id"
                + " where lp.school_id in (?1)")
            .setParameter(1, userSchools)
            .setMaxResults(1).getResultList().get(0);
        lessonPlanId = resultAsLong(result, 0);
        occupationModuleId = resultAsLong(result, 1);
        moduleThemeId = resultAsLong(result, 2);
        lessonPlanModuleId = resultAsLong(result, 3);
        schoolId = resultAsLong(result, 4);
        
        testConfigurationService.userToRoleInSchool(ROLE, schoolId, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void searchformData() {
        setUp();
        String url = ENDPOINT + "/searchFormData";
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void search() {
        setUp();
        String url = ENDPOINT;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));
        uriBuilder.queryParam("schoolDepartment", Long.valueOf(1));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1));
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));

        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchByTeacher() {
        setUp();
        String url = ENDPOINT + "/byteacher";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));
        uriBuilder.queryParam("teacher", Long.valueOf(1));

        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void newJournalNewModule() {
        setUpNewModule();
        String url = UriComponentsBuilder.fromUriString(ENDPOINT + "/journals/new")
                .queryParam("lessonPlan", lessonPlanId)
                .queryParam("occupationModule", occupationModuleId)
                .build().toUriString();
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void newJournalOldModule() {
        setUpOldModule();
        String url = UriComponentsBuilder.fromUriString(ENDPOINT + "/journals/new")
                .queryParam("lessonPlan", lessonPlanId)
                .queryParam("occupationModule", occupationModuleId)
                .queryParam("lessonPlanModule", lessonPlanModuleId)
                .build().toUriString();
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void journalCrudNewModule() {
        setUpNewModule();
        Assert.assertNull(lessonPlanModuleId);
        String uri = ENDPOINT + "/journals";
        LessonPlanJournalForm form = createJournalForm();

        // create
        ResponseEntity<Response> responseEntity = restTemplate.postForEntity(uri, form, Response.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Response dto = responseEntity.getBody();
        Assert.assertNotNull(dto);
        Long id = dto.getId();
        Assert.assertNotNull(id);
        lessonPlanModuleId = dto.getLessonPlanModuleId();
        Assert.assertNotNull(lessonPlanModuleId);

        journalCrud(uri, form, id);
    }

    @Test
    public void journalCrudOldModule() {
        setUpOldModule();
        Assert.assertNotNull(lessonPlanModuleId);
        String uri = ENDPOINT + "/journals";
        LessonPlanJournalForm form = createJournalForm();

        // create
        ResponseEntity<Response> responseEntity = restTemplate.postForEntity(uri, form, Response.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Response dto = responseEntity.getBody();
        Assert.assertNotNull(dto);
        Long id = dto.getId();
        Assert.assertNotNull(id);
        Assert.assertEquals(lessonPlanModuleId, dto.getLessonPlanModuleId());

        journalCrud(uri, form, id);
    }

    private void journalCrud(String uri, LessonPlanJournalForm form, Long id) {
        // read
        ResponseEntity<Response> response = restTemplate.getForEntity(UriComponentsBuilder.fromUriString(uri)
                .pathSegment(id.toString())
                .queryParam("lessonPlanModule", lessonPlanModuleId)
                .build().toUriString(), Response.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Response dto = response.getBody();
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
        Assert.assertNotNull(dto.getVersion());

        // update
        form.setLessonPlanModuleId(lessonPlanModuleId);
        form.setNameEt("changed_name");
        response = restTemplate.exchange(UriComponentsBuilder.fromUriString(uri)
                .pathSegment(id.toString())
                .build().toUriString(), HttpMethod.PUT, new HttpEntity<>(form), Response.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        dto = response.getBody();
        Long version = dto.getVersion();
        Assert.assertNotNull(version);

        // delete
        response = restTemplate.exchange(UriComponentsBuilder.fromUriString(uri)
                .pathSegment(id.toString())
                .queryParam("version", version)
                .build().toUriString(), HttpMethod.DELETE, null, Response.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private LessonPlanJournalForm createJournalForm() {
        CurriculumVersionOccupationModuleTheme theme = em.find(CurriculumVersionOccupationModuleTheme.class, moduleThemeId);
        
        LessonPlanJournalForm form = new LessonPlanJournalForm();
        
        form.setLessonPlan(lessonPlanId);
        form.setOccupationModuleId(occupationModuleId);
        form.setLessonPlanModuleId(lessonPlanModuleId);
        form.setJournalOccupationModuleThemes(Collections.singletonList(moduleThemeId));
        form.setAssessment(theme.getAssessment() != null ? 
                EntityUtil.getCode(theme.getAssessment()) : 
                VocationalGradeType.KUTSEHINDAMISVIIS_M.name());
        form.setNameEt(theme.getNameEt());
        form.setJournalCapacityTypes(getCapacityTypes());
        form.setGroupProportion(GroupProportion.PAEVIK_GRUPI_JAOTUS_1.name());
        
        return form;
    }
    
    private List<String> getCapacityTypes() {
        List<?> result = em.createNativeQuery("select capacity_type_code"
                + " from curriculum_version_omodule_theme_capacity"
                + " where curriculum_version_omodule_theme_id = ?1")
            .setParameter(1, moduleThemeId)
            .getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), result);
    }
    
    static class Response {
        private Long id;
        private Long lessonPlanModuleId;
        private Long version;
        
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        
        public Long getLessonPlanModuleId() {
            return lessonPlanModuleId;
        }
        public void setLessonPlanModuleId(Long lessonPlanModuleId) {
            this.lessonPlanModuleId = lessonPlanModuleId;
        }
        
        public Long getVersion() {
            return version;
        }
        public void setVersion(Long version) {
            this.version = version;
        }
        
    }
    
}
