package ee.hitsa.ois.web;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.assertj.core.util.Sets;
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
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class StateCurriculumControllerTests {

    private static final Long MISSING_ID = Long.valueOf(0);
    private static final String NAME = "StateCurriculumControllerTest";

    private StateCurriculum testStateCurriculum;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StateCurriculumRepository stateCurriculumRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_P, restTemplate);
    }

    @After
    public void cleanUp() {
        if (testStateCurriculum != null && testStateCurriculum.getId() != null) {
            this.restTemplate.delete("/stateCurriculum/{id}", testStateCurriculum.getId());
        }
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void testIsUniqueTrue() {
        Assert.assertEquals(testIsUnique(NAME.concat(NAME)), Boolean.TRUE);
    }

    private Boolean testIsUnique(String name) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum/unique");
        uriBuilder.queryParam("paramName", "nameEt");
        uriBuilder.queryParam("paramValue", name);

        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, Boolean.class);

    	Assert.assertNotNull(responseEntity.getBody());
    	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    	return responseEntity.getBody();
    }

    @Test
    public void testGetMissing() {
        ResponseEntity<StateCurriculum> responseEntity = restTemplate.getForEntity("/stateCurriculum/{id}", StateCurriculum.class, MISSING_ID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void testSearchWithoutEkrLevel() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum");
        uriBuilder.queryParam("name", "test");
        uriBuilder.queryParam("lang", "ET");
        uriBuilder.queryParam("validFromMillis", Integer.valueOf(123456));
        uriBuilder.queryParam("statusCode", CurriculumStatus.OPPEKAVA_STAATUS_S.name(), CurriculumStatus.OPPEKAVA_STAATUS_C.name());
        uriBuilder.queryParam("sort", "id,asc");

        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testSearchWithEkrLevel() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/stateCurriculum");
        uriBuilder.queryParam("name", "test");
        uriBuilder.queryParam("lang", "ET");
        uriBuilder.queryParam("statusCode", CurriculumStatus.OPPEKAVA_STAATUS_S.name(), CurriculumStatus.OPPEKAVA_STAATUS_C.name());
        uriBuilder.queryParam("ekrLevels", "Eesti kvalifikatsiooniraamistiku 6. tase", "Eesti kvalifikatsiooniraamistiku 4. tase");
        uriBuilder.queryParam("sort", "ekrLevel,asc");

        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {

        StateCurriculumForm stateCurriculumForm = getForm();
        stateCurriculumForm.setOccupations(Sets.newLinkedHashSet("KUTSE_10512175", "KUTSE_10437390"));


        StateCurriculumModuleDto module = getModuleDto();
        module.setNameEn("StateCurriculumControllerTestNameEn");
        module.setModuleOccupations(new HashSet<>());
        module.getModuleOccupations().add("KUTSE_10512175");
        module.getModuleOccupations().add("KUTSE_10437390");

        stateCurriculumForm.setModules(new HashSet<>());
        stateCurriculumForm.getModules().add(module);
        stateCurriculumForm.getModules().add(getModuleDto());

        ResponseEntity<StateCurriculumDto> responseEntity = restTemplate.postForEntity("/stateCurriculum", stateCurriculumForm,
                StateCurriculumDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());

        StateCurriculumDto stateCurriculumDto = responseEntity.getBody();

        testStateCurriculum = stateCurriculumRepository.getOne(stateCurriculumDto.getId());

        Assert.assertTrue(stateCurriculumDto.getStatus().equals(CurriculumStatus.OPPEKAVA_STAATUS_S.name()));
        Assert.assertTrue(stateCurriculumDto.getOccupations().size() == 2);
        Assert.assertTrue(stateCurriculumDto.getOccupations().contains("KUTSE_10512175"));
        Assert.assertTrue(stateCurriculumDto.getOccupations().contains("KUTSE_10437390"));

        Assert.assertTrue(stateCurriculumDto.getModules().size() == 2);

        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();
        Assert.assertTrue(module.getModuleOccupations().size() == 2);
        Assert.assertTrue(module.getModuleOccupations().contains("KUTSE_10512175"));
        Assert.assertTrue(module.getModuleOccupations().contains("KUTSE_10437390"));
        Assert.assertTrue(module.getModule().equals("KUTSEMOODUL_Y"));


        //update 1
        stateCurriculumDto.setObjectivesEt(NAME + "2");
        stateCurriculumDto.getOccupations().add("KUTSE_10578607");
        stateCurriculumDto.getOccupations().remove("KUTSE_10512175");
        stateCurriculumDto.setStatus(CurriculumStatus.OPPEKAVA_STAATUS_S.name());

        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();
        module.setNameEt(NAME + "2");
        module.setModule("KUTSEMOODUL_P");

        module.getModuleOccupations().add("KUTSE_10578607");
        module.getModuleOccupations().remove("KUTSE_10512175");



        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        testStateCurriculum = stateCurriculumRepository.getOne(stateCurriculumDto.getId());


        Assert.assertTrue(stateCurriculumDto.getObjectivesEt().equals(NAME + "2"));
        Assert.assertTrue(stateCurriculumDto.getStatus().equals(CurriculumStatus.OPPEKAVA_STAATUS_S.name()));
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(1)));

        Assert.assertTrue(stateCurriculumDto.getOccupations().size() == 2);
        Assert.assertTrue(stateCurriculumDto.getOccupations().contains("KUTSE_10578607"));
        Assert.assertTrue(stateCurriculumDto.getOccupations().contains("KUTSE_10437390"));
        Assert.assertTrue(!stateCurriculumDto.getOccupations().contains("KUTSE_10512175"));

        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();

        Assert.assertTrue(module.getNameEt().equals(NAME + "2"));
        Assert.assertTrue(module.getModule().equals("KUTSEMOODUL_P"));
        Assert.assertTrue(module.getVersion().equals(Long.valueOf(1)));

        Assert.assertTrue(module.getModuleOccupations().size() == 2);
        Assert.assertTrue(module.getModuleOccupations().contains("KUTSE_10578607"));
        Assert.assertTrue(module.getModuleOccupations().contains("KUTSE_10437390"));
        Assert.assertTrue(!module.getModuleOccupations().contains("KUTSE_10512175"));

        // update 2
        stateCurriculumDto.getOccupations().clear();
        stateCurriculumDto.setFinalExamDescription(NAME);
        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();
        module.setCredits(BigDecimal.valueOf(2));

        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        testStateCurriculum = stateCurriculumRepository.getOne(stateCurriculumDto.getId());

        Assert.assertTrue(stateCurriculumDto.getOccupations().isEmpty());
        Assert.assertTrue(NAME.equals(stateCurriculumDto.getFinalExamDescription()));
        Assert.assertTrue(Long.valueOf(2).equals(stateCurriculumDto.getVersion()));

        module = stateCurriculumDto.getModules().stream().filter(m -> "StateCurriculumControllerTestNameEn".equals(m.getNameEn())).findFirst().get();
        Assert.assertTrue(module.getModuleOccupations().size() == 2);
        Assert.assertTrue(BigDecimal.valueOf(2).equals(module.getCredits()));
        Assert.assertTrue(Long.valueOf(2).equals(module.getVersion()));

        // update 3
        stateCurriculumDto.setGraduationRequirementsEt(NAME);

        stateCurriculumDto.getModules().remove(module);
        stateCurriculumDto.getModules().add(getModuleDto());
        stateCurriculumDto.getModules().add(getModuleDto());

        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        testStateCurriculum = stateCurriculumRepository.getOne(stateCurriculumDto.getId());

        Set<StateCurriculumModuleDto> deletedModules = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn()
                .equals("StateCurriculumControllerTestNameEn")).collect(Collectors.toSet());
        Assert.assertTrue(deletedModules.isEmpty());
        Assert.assertTrue(stateCurriculumDto.getModules().size() == 3);
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(3)));

        // update 4
        stateCurriculumDto.getModules().clear();
        stateCurriculumDto.setObjectivesEn(NAME);

        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        testStateCurriculum = stateCurriculumRepository.getOne(stateCurriculumDto.getId());


        Assert.assertTrue(stateCurriculumDto.getObjectivesEn().equals(NAME));
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(4)));

        Assert.assertTrue(stateCurriculumDto.getModules().isEmpty());

        // get
        responseEntity = restTemplate.getForEntity("/stateCurriculum/{id}", StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
    }

    private static StateCurriculumForm getForm() {
        StateCurriculumForm stateCurriculumForm = new StateCurriculumForm();
        stateCurriculumForm.setNameEt(NAME);
        stateCurriculumForm.setOutcomesEt(NAME);
        stateCurriculumForm.setObjectivesEt(NAME);
        stateCurriculumForm.setAdmissionRequirementsEt(NAME);
        stateCurriculumForm.setCredits(Long.valueOf(1));
        stateCurriculumForm.setOptionalStudyCredits(BigDecimal.valueOf(1));
        stateCurriculumForm.setIscedClass("ISCED_RYHM_0522");
        stateCurriculumForm.setStateCurrClass("EHIS_ROK_15744");

        return stateCurriculumForm;
    }

    private static StateCurriculumModuleDto getModuleDto() {
        StateCurriculumModuleDto dto = new StateCurriculumModuleDto();
        dto.setNameEt(NAME);
        dto.setObjectivesEt(NAME);
        dto.setAssessmentsEt(NAME);
        dto.setModule("KUTSEMOODUL_Y");
        dto.setCredits(BigDecimal.valueOf(1));
        Set<String> moduleOccupations = new HashSet<>();
        moduleOccupations.add("KUTSE_10578607");
        dto.setModuleOccupations(moduleOccupations);
        return dto;
    }
}
