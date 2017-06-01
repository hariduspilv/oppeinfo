package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Sets;
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

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StateCurriculumControllerTest {

    private static final Long MISSING_ID = Long.valueOf(0);
    private static final String NAME = "StateCurriculumTest";

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test	
    public void testIsUniqueTrue() {
        Assert.assertEquals(testIsUnique(NAME.concat(NAME)), Boolean.TRUE);
    }
    
    @Test
    public void testIsUniqueFalse() {
        Assert.assertEquals(testIsUnique(NAME), Boolean.FALSE);
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
        uriBuilder.queryParam("statusCode", "OPPEKAVA_STAATUS_S", "OPPEKAVA_STAATUS_C");
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
        uriBuilder.queryParam("statusCode", "OPPEKAVA_STAATUS_S", "OPPEKAVA_STAATUS_C");
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
        
        ResponseEntity<StateCurriculumDto> responseEntity = this.restTemplate.postForEntity("/stateCurriculum", stateCurriculumForm,
                StateCurriculumDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        
        StateCurriculumDto stateCurriculumDto = responseEntity.getBody();
        Assert.assertTrue(stateCurriculumDto.getStatus().equals("OPPEKAVA_STAATUS_S"));
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
        stateCurriculumDto.setNameEt("StateCurriculumControllerTest2");
        stateCurriculumDto.getOccupations().add("KUTSE_10578607");
        stateCurriculumDto.getOccupations().remove("KUTSE_10512175");
        stateCurriculumDto.setStatus("OPPEKAVA_STAATUS_M");
        
        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();
        module.setOutcomesEt("StateCurriculumControllerTest2");
        module.setOutcomesEn("StateCurriculumControllerTest2");
        module.setNameEt("StateCurriculumControllerTest2");
        module.setModule("KUTSEMOODUL_P");
        
        module.getModuleOccupations().add("KUTSE_10578607");
        module.getModuleOccupations().remove("KUTSE_10512175");
        
        
        
        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        
        Assert.assertTrue(stateCurriculumDto.getNameEt().equals("StateCurriculumControllerTest2"));
        Assert.assertTrue(stateCurriculumDto.getStatus().equals("OPPEKAVA_STAATUS_M"));
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(1)));

        Assert.assertTrue(stateCurriculumDto.getOccupations().size() == 2);
        Assert.assertTrue(stateCurriculumDto.getOccupations().contains("KUTSE_10578607"));
        Assert.assertTrue(stateCurriculumDto.getOccupations().contains("KUTSE_10437390"));
        Assert.assertTrue(!stateCurriculumDto.getOccupations().contains("KUTSE_10512175"));
        
        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();

        Assert.assertTrue(module.getNameEt().equals("StateCurriculumControllerTest2"));
        Assert.assertTrue(module.getOutcomesEt().equals("StateCurriculumControllerTest2"));
        Assert.assertTrue(module.getOutcomesEn().equals("StateCurriculumControllerTest2"));
        Assert.assertTrue(module.getModule().equals("KUTSEMOODUL_P"));
        Assert.assertTrue(module.getVersion().equals(Long.valueOf(1)));
        
        Assert.assertTrue(module.getModuleOccupations().size() == 2);
        Assert.assertTrue(module.getModuleOccupations().contains("KUTSE_10578607"));
        Assert.assertTrue(module.getModuleOccupations().contains("KUTSE_10437390"));
        Assert.assertTrue(!module.getModuleOccupations().contains("KUTSE_10512175"));

        

        // update 2
        stateCurriculumDto.getOccupations().clear();
        stateCurriculumDto.setFinalExamDescription("StateCurriculumControllerTest");
        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();
        module.getModuleOccupations().clear();
        module.setCredits(Double.valueOf(2));
        
        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        
        Assert.assertTrue(stateCurriculumDto.getOccupations().isEmpty());
        Assert.assertTrue(stateCurriculumDto.getFinalExamDescription().equals("StateCurriculumControllerTest"));
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(2)));

        module = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn().equals("StateCurriculumControllerTestNameEn")).findFirst().get();
        Assert.assertTrue(module.getModuleOccupations().isEmpty());
        Assert.assertTrue(module.getCredits().equals(Double.valueOf(2)));
        Assert.assertTrue(module.getVersion().equals(Long.valueOf(2)));


        // update 3
        
        stateCurriculumDto.setGraduationRequirementsEt("StateCurriculumControllerTest");
        
        stateCurriculumDto.getModules().remove(module);
        stateCurriculumDto.getModules().add(getModuleDto());
        stateCurriculumDto.getModules().add(getModuleDto());

        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        
        Set<StateCurriculumModuleDto> deletedModules = stateCurriculumDto.getModules().stream().filter(m -> m.getNameEn() != null && m.getNameEn()
                .equals("StateCurriculumControllerTestNameEn")).collect(Collectors.toSet());
        Assert.assertTrue(deletedModules.isEmpty());
        Assert.assertTrue(stateCurriculumDto.getModules().size() == 3);
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(3)));
        
        // update 4
        stateCurriculumDto.getModules().clear();
        stateCurriculumDto.setObjectivesEn("StateCurriculumControllerTest");
        
        responseEntity = restTemplate.exchange("/stateCurriculum/{id}", HttpMethod.PUT, new HttpEntity<>(stateCurriculumDto), StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        stateCurriculumDto = responseEntity.getBody();
        Assert.assertTrue(stateCurriculumDto.getObjectivesEn().equals("StateCurriculumControllerTest"));
        Assert.assertTrue(stateCurriculumDto.getVersion().equals(Long.valueOf(4)));

        Assert.assertTrue(stateCurriculumDto.getModules().isEmpty());
        
        
        // get
        
        responseEntity = restTemplate.getForEntity("/stateCurriculum/{id}", StateCurriculumDto.class, stateCurriculumDto.getId());
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        
        // delete

        this.restTemplate.delete("/stateCurriculum/{id}", stateCurriculumDto.getId());

    }    
    
    private static StateCurriculumForm getForm() {
        StateCurriculumForm stateCurriculumForm = new StateCurriculumForm();
        stateCurriculumForm.setNameEt("StateCurriculumControllerTest");
        stateCurriculumForm.setOutcomesEt("StateCurriculumControllerTest");
        stateCurriculumForm.setObjectivesEt("StateCurriculumControllerTest");
        stateCurriculumForm.setAdmissionRequirementsEt("StateCurriculumControllerTest");
        stateCurriculumForm.setCredits(Long.valueOf(1));
        stateCurriculumForm.setOptionalStudyCredits(Double.valueOf(1));
        stateCurriculumForm.setValidFrom(LocalDate.now());
        stateCurriculumForm.setStatus("OPPEKAVA_STAATUS_S");
        stateCurriculumForm.setIscedClass("ISCED_RYHM_0522");
        stateCurriculumForm.setStateCurrClass("EHIS_ROK_15744");
        
        return stateCurriculumForm;
    }
    
    private static StateCurriculumModuleDto getModuleDto() {
        StateCurriculumModuleDto dto = new StateCurriculumModuleDto();
        dto.setNameEt("StateCurriculumControllerTest");
        dto.setObjectivesEt("StateCurriculumControllerTest");
        dto.setAssessmentsEt("StateCurriculumControllerTest");
        dto.setModule("KUTSEMOODUL_Y");
        dto.setCredits(Double.valueOf(1));
        dto.setOutcomesEt("StateCurriculumControllerTest");
        return dto;
    }
}
