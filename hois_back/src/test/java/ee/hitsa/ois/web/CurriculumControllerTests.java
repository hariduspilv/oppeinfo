package ee.hitsa.ois.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumForm;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionElectiveModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleOutcomeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class CurriculumControllerTests {

    private static final String NAME = "CurriculumControllerTest2";
    private static final String CODE = "testCode2";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestConfigurationService testConfigurationService;

    private JacksonTester<CurriculumForm> curriculumFormJson;

    private Curriculum testCurriculum;

    private static long referenceNumber = -1;

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        if (testCurriculum != null && testCurriculum.getId() != null) {
            this.restTemplate.delete("/curriculum/{id}", testCurriculum.getId());
        }
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/curriculum");
        uriBuilder.queryParam("sort", "id");
        uriBuilder.queryParam("isJoint", Boolean.FALSE);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        uriBuilder.queryParam("nameEt", "nimi");
        uriBuilder.queryParam("code", "code");
        uriBuilder.queryParam("merCode", "code");
        uriBuilder.queryParam("validFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("validThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("creditsMin", "0");
        uriBuilder.queryParam("creditsMax", "100");
        uriBuilder.queryParam("isJoint", Boolean.TRUE);
        uriBuilder.queryParam("school", "1", "2", "3");
        uriBuilder.queryParam("status", "S1", "S2");
        uriBuilder.queryParam("ehisStatus", "S1", "S2");
        uriBuilder.queryParam("iscedClassCode", "S1", "S2");
        uriBuilder.queryParam("studyLevel", "L1", "L2");
        uriBuilder.queryParam("ekrLevel", "EKR1", "EKR2");
        uriBuilder.queryParam("studyLanguage", "SL1", "SL2");
        uriBuilder.queryParam("department", "1", "2");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        uriBuilder.queryParam("isPartnerSchool", Boolean.FALSE);
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void deserialize() throws IOException {
        Path p = Paths.get("src/test/java/data/curriculum.json");
        String json = new String(Files.readAllBytes(p));
        CurriculumForm curriculumForm = curriculumFormJson.parseObject(json);
        Assert.assertNotNull(curriculumForm);
        Assert.assertNotNull(curriculumForm.getCode());
        Assert.assertNotNull(curriculumForm.getDraft());
        Assert.assertFalse(curriculumForm.getStudyLanguages().isEmpty());
        Assert.assertFalse(curriculumForm.getStudyForms().isEmpty());
        Assert.assertFalse(curriculumForm.getSchoolDepartments().isEmpty());
        Assert.assertFalse(curriculumForm.getFiles().isEmpty());
        Assert.assertFalse(curriculumForm.getJointPartners().isEmpty());
        Assert.assertFalse(curriculumForm.getModules().isEmpty());
        Assert.assertNotNull(curriculumForm.getStudyForms().stream().findFirst().get());
        Assert.assertNotNull(curriculumForm.getJointPartners().stream().findFirst().get().getEhisSchool());
    }

    @Test
    public void createAndGetHigher() {
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(LocalDate.now());
        setCollections(curriculumForm);

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());

        Assert.assertNotNull(testCurriculum);
        Assert.assertEquals(CODE, testCurriculum.getCode());
        Assert.assertEquals(CurriculumStatus.OPPEKAVA_STAATUS_S.name(), testCurriculum.getStatus().getCode());
        Assert.assertTrue(testCurriculum.getHigher().booleanValue());
        Assert.assertTrue(testCurriculum.getValidFrom().isEqual(validFrom));
        Assert.assertTrue(testCurriculum.getIscedClass().getCode().equals("ISCED_RYHM_0812"));

        Assert.assertTrue(testCurriculum.getStudyLanguages().size() == 2);
        Assert.assertNotNull(testCurriculum.getStudyLanguages());
        Assert.assertNotNull(testCurriculum.getStudyLanguages().stream().findFirst().get().getId());
        Assert.assertNotNull(testCurriculum.getStudyLanguages().stream().findFirst().get().getCurriculum().getId());

        Assert.assertTrue(testCurriculum.getDepartments().size() == 2);
        Assert.assertTrue(testCurriculum.getFiles().size() == 2);
        Assert.assertTrue(testCurriculum.getGrades().size() == 2);
        Assert.assertTrue(testCurriculum.getSpecialities().size() == 2);
        Assert.assertTrue(testCurriculum.getJointPartners().size() == 2);
        //test get

        responseEntity = restTemplate.getForEntity(String.format("/curriculum/%d", testCurriculum.getId()), CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody().getId().equals(testCurriculum.getId()));
    }

    @Test
    public void createAndGetVocational() {
        CurriculumForm curriculumForm = getForm(LocalDate.now());
        curriculumForm.setHigher(Boolean.FALSE);
        setCollections(curriculumForm);

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());

        Assert.assertNotNull(testCurriculum);
        Assert.assertEquals(CODE, testCurriculum.getCode());
        Assert.assertEquals(CurriculumStatus.OPPEKAVA_STAATUS_S.name(), testCurriculum.getStatus().getCode());

        Assert.assertTrue(testCurriculum.getStudyForms().size() == 2);

        Assert.assertTrue(testCurriculum.getOccupations().size() == 2);
        Assert.assertTrue(testCurriculum.getOccupations().stream().findFirst().get().getSpecialities().size() == 2);
        Assert.assertNotNull(testCurriculum.getOccupations().stream().findFirst().get().getSpecialities().stream().findFirst().get().getId());

        Assert.assertTrue(testCurriculum.getModules().size() == 2);
        Assert.assertTrue(testCurriculum.getModules().stream().findFirst().get().getCompetences().size() == 2);
        Assert.assertTrue(testCurriculum.getModules().stream().findFirst().get().getOutcomes().size() == 2);
        Assert.assertNotNull(testCurriculum.getModules().stream().findFirst().get().getOutcomes().stream().findFirst().get().getId());

        //test get

        responseEntity = restTemplate.getForEntity(String.format("/curriculum/%d", testCurriculum.getId()), CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody().getId().equals(testCurriculum.getId()));
    }

    @Test
    public void crudVersion() {
        // create curriculum
        CurriculumForm curriculumForm = getForm(LocalDate.now());

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());

        // create version with collections
        CurriculumVersionDto version1 = getCurriculumVersionDto();
        setCurriculumVersionsCollections(version1);

        ResponseEntity<CurriculumVersionDto> curriculumVersionResponseEntity =
                this.restTemplate.postForEntity("/curriculum/" + testCurriculum.getId() + "/versions", version1,
                CurriculumVersionDto.class);
        Assert.assertEquals(HttpStatus.OK, curriculumVersionResponseEntity.getStatusCode());
        Assert.assertNotNull(curriculumVersionResponseEntity);
        // validate
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());
        Assert.assertTrue(testCurriculum.getVersions().size() == 1);
        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().size() == 1);
        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().stream().findFirst().get().getElectiveModules().size() == 1);

        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().stream().findFirst().get().getSubjects().size() == 2);
        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().stream()
                .findFirst().get().getElectiveModules().stream().findFirst().get().getSubjects().size() == 1);
    }

    @Test
    public void testGetAreasOfStudyByGroupOfStudy() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity
                (String.format("/curriculum/areasOfStudyByGroupOfStudy/OPPEKAVAGRUPP_1"), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testConnectionsWithSpecialities() {
        CurriculumForm curriculumForm = getForm(LocalDate.now());

        Set<CurriculumSpecialityDto> specialities = new HashSet<>();
        CurriculumSpecialityDto spec1 = getCurriculumSpecialityDto();
        specialities.add(spec1);
        curriculumForm.setSpecialities(specialities);



        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        // for deleting
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());



        CurriculumVersionDto version1 = getCurriculumVersionDto();

        Long specRefNum = testCurriculum.getSpecialities().stream().findFirst().get().getReferenceNumber();

        version1.getSpecialitiesReferenceNumbers().add(specRefNum);
        CurriculumVersionHigherModuleDto module = getCurriculumVersionHigherModuleDto();
        module.getSpecialitiesReferenceNumbers().add(specRefNum);
        version1.getModules().add(module);

        ResponseEntity<CurriculumVersionDto> versionResponse = this.restTemplate.postForEntity("/curriculum/" + testCurriculum.getId() + "/versions", version1,
                CurriculumVersionDto.class);
        Assert.assertEquals(HttpStatus.OK, versionResponse.getStatusCode());
        Assert.assertNotNull(versionResponse.getBody().getId());

        CurriculumVersionDto versionDto = versionResponse.getBody();

        CurriculumDto curriculumDto = responseEntity.getBody();
        CurriculumSpecialityDto someSpec = curriculumDto.getSpecialities().stream().findFirst().get();
        Assert.assertEquals(someSpec.getId(), someSpec.getReferenceNumber());
        Set<Long> referenceNumbers = curriculumDto.getSpecialities().stream()
                .map(s -> s.getReferenceNumber()).collect(Collectors.toSet());
        Long versionRefNum = versionDto.getSpecialitiesReferenceNumbers().stream().findFirst().get();
        Long moduleRefNum = versionDto
                .getModules().stream().findFirst().get().getSpecialitiesReferenceNumbers().stream().findFirst().get();

        Assert.assertTrue(referenceNumbers.contains(versionRefNum));
        Assert.assertTrue(referenceNumbers.contains(moduleRefNum));
    }

    @Test
    public void updateElectiveModuleSubjects() {

        CurriculumForm curriculumForm = getForm(LocalDate.now());

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CurriculumDto curriculumDto = responseEntity.getBody();
        Assert.assertNotNull(curriculumDto);
        testCurriculum = curriculumRepository.getOne(curriculumDto.getId());

        CurriculumVersionDto version1 = getCurriculumVersionDto();

        CurriculumVersionHigherModuleDto versionModule = getCurriculumVersionHigherModuleDto();
        CurriculumVersionHigherModuleSubjectDto subject1 = getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(33), Boolean.TRUE);
        CurriculumVersionHigherModuleSubjectDto subject2 = getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(34), Boolean.TRUE);

        CurriculumVersionElectiveModuleDto electiveModule = getCurriculumVersionElectiveModuleDto();

        subject1.setElectiveModule(electiveModule.getReferenceNumber());
        subject2.setElectiveModule(electiveModule.getReferenceNumber());

        versionModule.setSubjects(Sets.newLinkedHashSet(subject1, subject2));

        versionModule.setElectiveModules(Sets.newLinkedHashSet(electiveModule));
        version1.setModules(Sets.newLinkedHashSet(versionModule));

        ResponseEntity<CurriculumVersionDto> versionResponse = this.restTemplate.postForEntity("/curriculum/" + testCurriculum.getId() + "/versions", version1,
                CurriculumVersionDto.class);
        Assert.assertEquals(HttpStatus.OK, versionResponse.getStatusCode());
        Assert.assertNotNull(versionResponse.getBody().getId());

        CurriculumVersionDto versionDto = versionResponse.getBody();

        versionModule = versionDto.getModules().stream().findFirst().get();
        electiveModule = versionModule.getElectiveModules().stream().findFirst().get();
        Long electiveModuleReferenceNumber = electiveModule.getReferenceNumber();
        List<CurriculumVersionHigherModuleSubjectDto> subjects = new ArrayList<>(versionModule.getSubjects());

        Assert.assertTrue(subjects.size() == 2);
        Assert.assertTrue(subjects.get(0).getElectiveModule().equals(electiveModuleReferenceNumber));
        Assert.assertTrue(subjects.get(1).getElectiveModule().equals(electiveModuleReferenceNumber));

        this.restTemplate.delete("/curriculum/{id}", curriculumDto.getId());
    }


    @Test
    public void update() {
        // create
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(validFrom);
        setCollections(curriculumForm);   // fails with collections!

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());
        Assert.assertFalse(testCurriculum.getNameEt().equals("newName"));

        // update curriculum text field and classifier field
        curriculumForm = responseEntity.getBody();
        curriculumForm.setNameEt("newName");
        curriculumForm.setVersion(testCurriculum.getVersion());

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(curriculumForm), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CurriculumDto updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        // check

        Assert.assertTrue(updatedCurriculum.getNameEt().equals("newName"));
        Assert.assertTrue(updatedCurriculum.getVersion().equals(Long.valueOf(1)));

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        // remove one item from collection

        Set<Long> schooleDepartments = updatedCurriculum.getSchoolDepartments();
        schooleDepartments.remove(schooleDepartments.iterator().next());

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        Assert.assertTrue(updatedCurriculum.getSchoolDepartments().size() == 1);

        // check that collection items do not change their id when not changed
        // one from join table and other item that is created on form
        Long studyLanguageId = testCurriculum.getStudyLanguages().stream().findAny().get().getId();

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        testCurriculum = curriculumRepository.getOne(updatedCurriculum.getId());
        CurriculumStudyLanguage lang = testCurriculum.getStudyLanguages().stream()
                .filter(l -> l.getId().equals(studyLanguageId)).findFirst().get();
        Assert.assertNotNull(lang);

        // add new item to collection
        updatedCurriculum.getStudyLanguages().add("OPPEKEEL_E");

        updatedCurriculum.getSpecialities().add(getCurriculumSpecialityDto());

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);
        Assert.assertTrue(updatedCurriculum.getStudyLanguages().size() == 3);
    }


    @Test
    public void updateVocational() {
        // create
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(validFrom);
        curriculumForm.setHigher(Boolean.FALSE);
        setCollections(curriculumForm);   // fails with collections!

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());
        Assert.assertFalse(testCurriculum.getNameEt().equals("newName"));

        // update curriculum text field and classifier field
        curriculumForm = responseEntity.getBody();
        curriculumForm.setNameEt("newName");
        curriculumForm.setVersion(testCurriculum.getVersion());

        Assert.assertFalse(testCurriculum.getStudyForms().isEmpty());

    }

    @Test
    public void saveCurriculumVersionOccupationModule() {
        CurriculumForm curriculumForm = getForm(LocalDate.now());
        curriculumForm.setHigher(Boolean.FALSE);
        curriculumForm.setModules(Sets.newLinkedHashSet(getCurriculumModuleDto()));

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumRepository.getOne(responseEntity.getBody().getId());
        Assert.assertTrue(testCurriculum.getVersions().isEmpty());
        Assert.assertFalse(testCurriculum.getModules().stream().findFirst().get().getOutcomes().isEmpty());


        CurriculumVersionDto curriculumVersionDto = getCurriculumVersionDto();
        CurriculumVersionOccupationModuleDto cvomDto = getCurriculumVersionOccupationModuleDto(responseEntity.getBody().getModules().stream().findFirst().get());
        curriculumVersionDto.getOccupationModules().add(cvomDto);

        //create
        ResponseEntity<CurriculumVersionDto> versionResponseEntity =
                this.restTemplate.postForEntity("/curriculum/{curriculumId}/versions", curriculumVersionDto, CurriculumVersionDto.class, testCurriculum.getId());

        Assert.assertEquals(HttpStatus.OK, versionResponseEntity.getStatusCode());
        Assert.assertNotNull(versionResponseEntity);

        curriculumVersionDto = versionResponseEntity.getBody();
        Assert.assertNotNull(curriculumVersionDto.getId());
        Assert.assertFalse(curriculumVersionDto.getOccupationModules().isEmpty());
        Assert.assertNotNull(curriculumVersionDto.getOccupationModules().stream().findFirst().get().getCurriculumModule());

        curriculumVersionDto.setCode("change");

        CurriculumVersionOccupationModuleDto savedOccupationModule = curriculumVersionDto.getOccupationModules().stream().findFirst().get();
        savedOccupationModule.setGrade3Description("grade3");

        CurriculumVersionOccupationModuleCapacityDto capacity = new CurriculumVersionOccupationModuleCapacityDto();
        capacity.setCapacityType(CapacityType.MAHT_a.name());
        capacity.setContact(Boolean.TRUE);
        capacity.setHours(Short.valueOf((short) 2));
        savedOccupationModule.getCapacities().add(capacity);

        CurriculumVersionOccupationModuleThemeDto theme = new CurriculumVersionOccupationModuleThemeDto();
        theme.setNameEt("themeNameEt");
        theme.setCredits(new BigDecimal("1.0"));
        theme.setHours(Short.valueOf((short) 1));

        CurriculumVersionOccupationModuleThemeCapacityDto themeCapacity = new CurriculumVersionOccupationModuleThemeCapacityDto();
        themeCapacity.setCapacityType(CapacityType.MAHT_a.name());
        themeCapacity.setContact(Boolean.TRUE);
        themeCapacity.setHours(Short.valueOf((short) 2));
        theme.getCapacities().add(themeCapacity);

        CurriculumVersionOccupationModuleOutcomeDto themeOutcome = new CurriculumVersionOccupationModuleOutcomeDto();
        Long curriculumModuleOutcomeId = testCurriculum.getModules().stream().findFirst().get()
                .getOutcomes().stream().findFirst().get().getId();
        themeOutcome.setOutcome(curriculumModuleOutcomeId);
//        theme.getOutcomes().add(themeOutcome);

        savedOccupationModule.getThemes().add(theme);

        //update
        versionResponseEntity = restTemplate.exchange("/curriculum/{curriculumId}/versions/{id}", HttpMethod.PUT,
                new HttpEntity<>(curriculumVersionDto), CurriculumVersionDto.class, testCurriculum.getId(), curriculumVersionDto.getId());

        Assert.assertEquals(HttpStatus.OK, versionResponseEntity.getStatusCode());
        CurriculumVersionDto updatedCurriculumVersionDto = versionResponseEntity.getBody();

        Assert.assertEquals("change", updatedCurriculumVersionDto.getCode());
        CurriculumVersionOccupationModuleDto updatedOccupationModule = updatedCurriculumVersionDto.getOccupationModules().stream().findFirst().get();
        Assert.assertEquals("grade3", updatedOccupationModule.getGrade3Description());

        Assert.assertNotNull(updatedOccupationModule.getCapacities().stream().findFirst().get().getId());
        Assert.assertEquals(Boolean.TRUE, updatedOccupationModule.getCapacities().stream().findFirst().get().getContact());

        Assert.assertEquals("themeNameEt", updatedOccupationModule.getThemes().stream().findFirst().get().getNameEt());
        Assert.assertNotNull(updatedOccupationModule.getThemes().stream().findFirst().get().getId());
    }

    /**
     * New requirement: versions must be handled on their own form.
     * Managing list of CurriculumVersionSpecialities now causes an exception.
     */
    @Test
    public void saveVersion() {
        CurriculumForm curriculumForm = getForm(LocalDate.now());

        curriculumForm.setSpecialities(new HashSet<>());
        CurriculumSpecialityDto spec1 = getCurriculumSpecialityDto();
        curriculumForm.getSpecialities().add(spec1);

        /*
         * Curriculum must be saved before
         */
        ResponseEntity<CurriculumDto> curriculumResponse = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, curriculumResponse.getStatusCode());
        Assert.assertNotNull(curriculumResponse);
        testCurriculum = curriculumRepository.getOne(curriculumResponse.getBody().getId());
        CurriculumDto curriculumDto = curriculumResponse.getBody();
        CurriculumSpecialityDto specDto = curriculumDto.getSpecialities().stream().findFirst().get();

        CurriculumVersionDto versionDto = getCurriculumVersionDto();
        versionDto.setSpecialitiesReferenceNumbers(new HashSet<>());
        versionDto.getSpecialitiesReferenceNumbers().add(specDto.getReferenceNumber());

        /*
         * Save Curriculum version with speciality
         */
        ResponseEntity<CurriculumVersionDto> versionResponse = this.restTemplate.postForEntity("/curriculum/" + testCurriculum.getId() + "/versions", versionDto,
                CurriculumVersionDto.class);
        Assert.assertEquals(HttpStatus.OK, versionResponse.getStatusCode());
        Assert.assertNotNull(versionResponse.getBody().getId());
    }

    private static CurriculumVersionOccupationModuleDto getCurriculumVersionOccupationModuleDto(CurriculumModuleDto curriculumModuleDto) {
        CurriculumVersionOccupationModuleDto dto = new CurriculumVersionOccupationModuleDto();
        dto.setRequirementsEt("requirementsEt");
        dto.setAssessmentsEt("assessmentsEt");
        dto.setAssessment("KUTSEHINDAMISVIIS_E");
        dto.setTotalGradeDescription("totalGradeDescription");
        dto.setSupervisor("supervisor");
        dto.setCurriculumModule(curriculumModuleDto.getId());
        return dto;
    }
    // TODO: write according tests
    private boolean testIsUnique(String code) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/curriculum/unique");
        uriBuilder.queryParam("paramName", "code");
        uriBuilder.queryParam("paramValue", code);

        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(uri, Boolean.class);

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        return responseEntity.getBody().booleanValue();
    }

    private static CurriculumForm getForm(LocalDate validFrom) {
        CurriculumForm curriculumForm = new CurriculumForm();
        curriculumForm.setCode(CODE);
        curriculumForm.setOptionalStudyCredits(BigDecimal.valueOf(1));
        curriculumForm.setStudyPeriod(Integer.valueOf(1));
        curriculumForm.setNameEn("nameEn");
        curriculumForm.setNameEt("nameEt");
        curriculumForm.setConsecution("OPPEKAVA_TYPE_E");
        curriculumForm.setOrigStudyLevel("OPPEASTE_503");
        curriculumForm.setDraft(CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_PUUDUB.name());
        curriculumForm.setJointMentor("EHIS_KOOL_1");
        curriculumForm.setHigher(Boolean.TRUE);
        curriculumForm.setValidFrom(validFrom);
        curriculumForm.setStudyLanguages(Sets.newLinkedHashSet("OPPEKEEL_et_en", "OPPEKEEL_et_ru"));
        curriculumForm.setIscedClass("ISCED_RYHM_0812");
//        curriculumForm.setVersions(new HashSet<>());
        curriculumForm.setJoint(Boolean.FALSE);
        return curriculumForm;
    }

    private static void setCollections(CurriculumForm curriculumForm) {
        curriculumForm.setSchoolDepartments(Sets.newLinkedHashSet(Long.valueOf(1), Long.valueOf(3)));
        curriculumForm.setStudyForms(Sets.newLinkedHashSet("OPPEVORM_K", "OPPEVORM_MS"));

        Set<CurriculumGradeDto> grades = new HashSet<>();
        grades.add(getCurriculumGradeDto());
        grades.add(getCurriculumGradeDto());
        curriculumForm.setGrades(grades);

        Set<CurriculumSpecialityDto> specialities = new HashSet<>();
        specialities.add(getCurriculumSpecialityDto());
        specialities.add(getCurriculumSpecialityDto());
        curriculumForm.setSpecialities(specialities);

        Set<CurriculumJointPartnerDto> partners = new HashSet<>();
        partners.add(getCurriculumJointPartnerDto());
        partners.add(getCurriculumJointPartnerDto());
        curriculumForm.setJointPartners(partners);

//        Set<CurriculumOccupationDto> occupations = new HashSet<>();
//        occupations.add(getCurriculumOccupationDto("KUTSE_10431606"));
//        occupations.add(getCurriculumOccupationDto("KUTSE_10411912"));
//        curriculumForm.setOccupations(occupations);

        curriculumForm.setModules(Sets.newLinkedHashSet(getCurriculumModuleDto(), getCurriculumModuleDto()));
    }

    private static CurriculumSpecialityDto getCurriculumSpecialityDto() {
        CurriculumSpecialityDto dto = new CurriculumSpecialityDto();
        dto.setNameEt(NAME);
        dto.setNameEn(NAME);
        dto.setCredits(BigDecimal.valueOf(1));
        dto.setOccupation("KUTSE_10491530");
        dto.setReferenceNumber(Long.valueOf(referenceNumber--));
        return dto;
    }

    private static CurriculumGradeDto getCurriculumGradeDto() {
        CurriculumGradeDto dto = new CurriculumGradeDto();
        dto.setNameEt(NAME);
        dto.setNameEn(NAME);
        dto.setNameGenitiveEt(NAME);
        dto.setEhisGrade("AKAD_KRAAD_AJM");
        return dto;
    }

    private static CurriculumJointPartnerDto getCurriculumJointPartnerDto() {
        CurriculumJointPartnerDto dto = new CurriculumJointPartnerDto();
        dto.setAbroad(Boolean.FALSE);
        dto.setContractEt(NAME);
        dto.setContractEn(NAME);
        dto.setSupervisor(NAME);
        dto.setEhisSchool("EHIS_KOOL_113");
        return dto;
    }

    private static CurriculumOccupationDto getCurriculumOccupationDto(String occupation) {
        CurriculumOccupationDto dto = new CurriculumOccupationDto();
        dto.setOccupationGrant(Boolean.FALSE);
        dto.setOccupation(occupation);
        Set<String> specialities = new HashSet<>();
        specialities.add("SPETSKUTSE_10601291");
        specialities.add("SPETSKUTSE_10601294");
        dto.setSpecialities(specialities);
        return dto;
    }

    private static CurriculumModuleDto getCurriculumModuleDto(){
        CurriculumModuleDto dto = new CurriculumModuleDto();
        dto.setNameEt(NAME);
        dto.setObjectivesEt(NAME);
        dto.setModule("KUTSEMOODUL_P");
        dto.setPractice(Boolean.FALSE);
        dto.setCredits(BigDecimal.ONE);
        dto.setOccupations(Sets.newLinkedHashSet("OSAKUTSE_10498104", "KUTSE_10463859"));
        dto.setCompetences(Sets.newLinkedHashSet("KOMPETENTS_4", "KOMPETENTS_13"));
        dto.setOutcomes(Sets.newLinkedHashSet(getCurriculumModuleOutcomeDto(), getCurriculumModuleOutcomeDto()));
        return dto;
    }

    private static CurriculumModuleOutcomeDto getCurriculumModuleOutcomeDto() {
        CurriculumModuleOutcomeDto dto = new CurriculumModuleOutcomeDto();
        dto.setOutcomeEt(NAME);
        dto.setOrderNr(Long.valueOf(1));
        return dto;
    }

    private static CurriculumVersionDto getCurriculumVersionDto() {
        CurriculumVersionDto dto = new CurriculumVersionDto();
        dto.setCode(NAME);
        dto.setType("OPPEKAVA_VERSIOON_LIIK_O");
        dto.setStatus(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_S.name());
        dto.setAdmissionYear(Short.valueOf((short) 2017));
        return dto;
    }

    private static void setCurriculumVersionsCollections(CurriculumVersionDto dto) {
        CurriculumVersionHigherModuleDto versionModule = getCurriculumVersionHigherModuleDto();

        CurriculumVersionHigherModuleSubjectDto subject1 = getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(33), Boolean.TRUE);
        CurriculumVersionHigherModuleSubjectDto subject2 = getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(34), Boolean.TRUE);
        CurriculumVersionElectiveModuleDto electiveModule = getCurriculumVersionElectiveModuleDto();
        subject1.setElectiveModule(electiveModule.getReferenceNumber());
        versionModule.setSubjects(Sets.newLinkedHashSet(subject1, subject2));
        versionModule.setElectiveModules(Sets.newLinkedHashSet(electiveModule));

        dto.setModules(Sets.newLinkedHashSet(versionModule));
    }

    private static CurriculumVersionHigherModuleDto getCurriculumVersionHigherModuleDto() {
        CurriculumVersionHigherModuleDto dto = new CurriculumVersionHigherModuleDto();
        dto.setTotalCredits(BigDecimal.valueOf(1));
        dto.setOptionalStudyCredits(BigDecimal.valueOf(1));
        dto.setType(HigherModuleType.KORGMOODUL_F.name());
        dto.setNameEn(NAME);
        dto.setNameEt(NAME);
        dto.setElectiveModulesNumber(Short.valueOf((short) 1));
        dto.setCompulsoryStudyCredits(BigDecimal.valueOf(1));
        return dto;
    }

    private static CurriculumVersionElectiveModuleDto getCurriculumVersionElectiveModuleDto() {
        CurriculumVersionElectiveModuleDto dto = new CurriculumVersionElectiveModuleDto();
        dto.setNameEt(NAME);
        dto.setNameEn(NAME);
        dto.setReferenceNumber(Long.valueOf(referenceNumber--));
        return dto;
    }

    private static CurriculumVersionHigherModuleSubjectDto getCurriculumVersionHigherModuleSubjectDto(Long subjectId, Boolean isOptional){
        CurriculumVersionHigherModuleSubjectDto dto = new CurriculumVersionHigherModuleSubjectDto();
        dto.setSubjectId(subjectId);
        dto.setOptional(isOptional);
        return dto;
    }
}
