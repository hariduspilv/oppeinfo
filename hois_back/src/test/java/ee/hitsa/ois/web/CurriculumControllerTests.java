package ee.hitsa.ois.web;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.service.CurriculumService;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileDto;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class CurriculumControllerTests {

    private final static String CODE = "code";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private ObjectMapper objectMapper;

    private JacksonTester<CurriculumForm> curriculumFormJson;

    private Curriculum testCurriculum;
    
    private Long referenceNumber = Long.valueOf(1);

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @After
    public void cleanUp() {
        if (testCurriculum != null && testCurriculum.getId() != null) {
            this.restTemplate.delete("/curriculum/{id}", testCurriculum.getId());
        }
    }

    @Test
    public void testIsUniqueTrue() {
        Assert.assertTrue(testIsUnique(CODE.concat(CODE)));
    }

    @Test
    public void testIsUniqueFalse() {
        Assert.assertFalse(testIsUnique(CODE));
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
        uriBuilder.queryParam("creditsMin", "0.0");
        uriBuilder.queryParam("creditsMax", "100.0");
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
    public void createAndGet() {
        LocalDate validFrom = LocalDate.now();
        CurriculumForm curriculumForm = getForm(LocalDate.now());
        setCollections(curriculumForm);

        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());

        Assert.assertNotNull(testCurriculum);
        Assert.assertEquals("testCode", testCurriculum.getCode());
        Assert.assertEquals("OPPEKAVA_STAATUS_S", testCurriculum.getStatus().getCode());
        Assert.assertTrue(testCurriculum.getHigher().booleanValue());
        Assert.assertTrue(testCurriculum.getValidFrom().isEqual(validFrom));
        Assert.assertTrue(testCurriculum.getIscedClass().getCode().equals("ISCED_RYHM_0812"));

        Assert.assertTrue(testCurriculum.getStudyLanguages().size() == 2);
        Assert.assertNotNull(testCurriculum.getStudyLanguages());
        Assert.assertNotNull(testCurriculum.getStudyLanguages().stream().findFirst().get().getId());
        Assert.assertNotNull(testCurriculum.getStudyLanguages().stream().findFirst().get().getCurriculum().getId());

        Assert.assertTrue(testCurriculum.getDepartments().size() == 2);
        Assert.assertTrue(testCurriculum.getStudyForms().size() == 2);
        Assert.assertTrue(testCurriculum.getFiles().size() == 2);
        Assert.assertTrue(testCurriculum.getGrades().size() == 2);
        Assert.assertTrue(testCurriculum.getSpecialities().size() == 2);
        Assert.assertTrue(testCurriculum.getJointPartners().size() == 2);

        Assert.assertTrue(testCurriculum.getOccupations().size() == 2);
        Assert.assertTrue(testCurriculum.getOccupations().stream().findFirst().get().getSpecialities().size() == 2);
        Assert.assertNotNull(testCurriculum.getOccupations().stream().findFirst().get().getSpecialities().stream().findFirst().get().getId());

        Assert.assertTrue(testCurriculum.getModules().size() == 2);
        Assert.assertTrue(testCurriculum.getModules().stream().findFirst().get().getCompetences().size() == 2);
        Assert.assertTrue(testCurriculum.getModules().stream().findFirst().get().getOutcomes().size() == 2);
        Assert.assertNotNull(testCurriculum.getModules().stream().findFirst().get().getOutcomes().stream().findFirst().get().getId());

        Assert.assertTrue(testCurriculum.getVersions().size() == 1);
        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().size() == 1);
        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().stream().findFirst().get().getElectiveModules().size() == 1);

        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().stream().findFirst().get().getSubjects().size() == 2);
        Assert.assertTrue(testCurriculum.getVersions().stream().findFirst().get().getModules().stream()
                .findFirst().get().getElectiveModules().stream().findFirst().get().getSubjects().size() == 1);
        
        
        //test get
        
        responseEntity = restTemplate.getForEntity(String.format("/curriculum/%d", testCurriculum.getId()), CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.getBody().getId().equals(testCurriculum.getId()));
    }

    /*
     * TODO: test connections between module and specialty (not yet implemented)
     */
    @Test
    public void testConnectionsWithSpecialities() {
        CurriculumForm curriculumForm = getForm(LocalDate.now());
        
        Set<CurriculumSpecialityDto> specialities = new HashSet<>();
        CurriculumSpecialityDto spec1 = getCurriculumSpecialityDto();
        CurriculumSpecialityDto spec2 = getCurriculumSpecialityDto();
        specialities.add(spec1);
        specialities.add(spec2);
        curriculumForm.setSpecialities(specialities);
        
        CurriculumVersionDto version1 = getCurriculumVersionDto();
        version1.getSpecialitiesReferenceNumbers().add(spec1.getReferenceNumber());
        
        CurriculumVersionHigherModuleDto module = getCurriculumVersionHigherModuleDto();
        module.getSpecialitiesReferenceNumbers().add(spec1.getReferenceNumber());
        version1.getModules().add(module);
        Set<CurriculumVersionDto> versions = new HashSet<>();
        versions.add(version1);
        curriculumForm.setVersions(versions);
        
        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
        
        CurriculumDto curriculumDto = responseEntity.getBody();
        CurriculumSpecialityDto someSpec = curriculumDto.getSpecialities().stream().findFirst().get();
        Assert.assertEquals(someSpec.getId(), someSpec.getReferenceNumber());
        Set<Long> referenceNumbers = curriculumDto.getSpecialities().stream()
                .map(s -> s.getReferenceNumber()).collect(Collectors.toSet());
        Long versionRefNum = curriculumDto.getVersions().stream().findFirst().get().getSpecialitiesReferenceNumbers().stream().findFirst().get();
        Long moduleRefNum = curriculumDto.getVersions().stream().findFirst().get()
                .getModules().stream().findFirst().get().getSpecialitiesReferenceNumbers().stream().findFirst().get();
        
        Assert.assertTrue(referenceNumbers.contains(versionRefNum));
        Assert.assertTrue(referenceNumbers.contains(moduleRefNum));
        
        // for deleting
        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());

    }
    
    @Test
    public void updateElectiveModuleSubjects() {
        
        CurriculumForm curriculumForm = getForm(LocalDate.now());
        CurriculumVersionDto version1 = getCurriculumVersionDto();
        
        CurriculumVersionHigherModuleDto versionModule = getCurriculumVersionHigherModuleDto();
        versionModule.setSubjects(Sets.newLinkedHashSet
                (getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(33), Boolean.TRUE),
                        getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(34), Boolean.FALSE)));
        
        CurriculumVersionElectiveModuleDto electiveModule = getCurriculumVersionElectiveModuleDto();
        electiveModule.setSubjects(Sets.newLinkedHashSet(Long.valueOf(33)));

        versionModule.setElectiveModules(Sets.newLinkedHashSet(electiveModule));
        version1.setModules(Sets.newLinkedHashSet(versionModule));
        Set<CurriculumVersionDto> versions = new HashSet<>();
        versions.add(version1);
        curriculumForm.setVersions(versions);
        
        // save subjects to elective module and check that response is appropriate
        
        ResponseEntity<CurriculumDto> responseEntity = this.restTemplate.postForEntity("/curriculum", curriculumForm,
                CurriculumDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CurriculumDto curriculumDto = responseEntity.getBody();
        Assert.assertNotNull(curriculumDto);
        
        
        versionModule = curriculumDto.getVersions().stream().findFirst().get()
                .getModules().stream().findFirst().get();
        electiveModule = versionModule.getElectiveModules().stream().findFirst().get();
        Long electiveModuleReferenceNumber = electiveModule.getReferenceNumber();
        Set<Long> referenceNumbers = versionModule.getSubjects().stream()
                .map(s -> s.getElectiveModule()).collect(Collectors.toSet());
        Assert.assertTrue(referenceNumbers.contains(electiveModuleReferenceNumber));
        Assert.assertTrue(electiveModule.getSubjects().stream().findFirst().get().equals(Long.valueOf(33)));
        Assert.assertTrue(electiveModule.getSubjects().size() == 1);

        
        // add one subject and remove other
        
        electiveModule.getSubjects().add(Long.valueOf(34));
        electiveModule.getSubjects().remove(Long.valueOf(33));
        
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(curriculumDto), CurriculumDto.class, curriculumDto.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        curriculumDto = responseEntity.getBody();
        Assert.assertNotNull(curriculumDto);
        
        versionModule = curriculumDto.getVersions().stream().findFirst().get()
                .getModules().stream().findFirst().get();
        electiveModule = versionModule.getElectiveModules().stream().findFirst().get();
        electiveModuleReferenceNumber = electiveModule.getReferenceNumber();
        referenceNumbers = versionModule.getSubjects().stream()
                .map(s -> s.getElectiveModule()).collect(Collectors.toSet());
        Assert.assertTrue(referenceNumbers.contains(electiveModuleReferenceNumber));
        Assert.assertTrue(electiveModule.getSubjects().stream().findFirst().get().equals(Long.valueOf(34)));
        Assert.assertTrue(electiveModule.getSubjects().size() == 1);        
        
        // remove all subjects from elective module
        electiveModule.getSubjects().clear();
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(curriculumDto), CurriculumDto.class, curriculumDto.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        curriculumDto = responseEntity.getBody();
        Assert.assertNotNull(curriculumDto);
        versionModule = curriculumDto.getVersions().stream().findFirst().get()
                .getModules().stream().findFirst().get();
        electiveModule = versionModule.getElectiveModules().stream().findFirst().get();
        Assert.assertTrue(electiveModule.getSubjects().isEmpty());
        
        // remove elective module with subjects
        electiveModule.setSubjects(Sets.newLinkedHashSet(Long.valueOf(33)));
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(curriculumDto), CurriculumDto.class, curriculumDto.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        curriculumDto = responseEntity.getBody();
        Assert.assertNotNull(curriculumDto);
        
        versionModule = curriculumDto.getVersions().stream().findFirst().get()
                .getModules().stream().findFirst().get();
        versionModule.setElectiveModules(null);
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(curriculumDto), CurriculumDto.class, curriculumDto.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        curriculumDto = responseEntity.getBody();
        Assert.assertNotNull(curriculumDto);
        versionModule = curriculumDto.getVersions().stream().findFirst().get()
                .getModules().stream().findFirst().get();
        Assert.assertTrue(versionModule.getElectiveModules().isEmpty());
        Assert.assertTrue(versionModule.getSubjects().size() == 2);
        
        this.restTemplate.delete("/curriculum/{id}", curriculumDto.getId());
    }
    
    /*
     * Test version of each instance!!
     *
     * TODO: check that ids of items, that are not changed, are not changed as well
     */
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
        testCurriculum = curriculumService.getOne(responseEntity.getBody().getId());
        Assert.assertFalse(testCurriculum.getNameEt().equals("newName"));

        // update curriculum text field and classifier field
        curriculumForm = responseEntity.getBody();
        curriculumForm.setStatus("OPPEKAVA_STAATUS_M");
        curriculumForm.setNameEt("newName");
        curriculumForm.setVersion(testCurriculum.getVersion());

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(curriculumForm), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CurriculumDto updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        // check

        Assert.assertTrue(updatedCurriculum.getStatus().equals("OPPEKAVA_STAATUS_M"));
        Assert.assertTrue(updatedCurriculum.getNameEt().equals("newName"));
        Assert.assertTrue(updatedCurriculum.getVersion().equals(Long.valueOf(1)));

        // remove collection completely
        updatedCurriculum.setGrades(null);
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);
        Assert.assertTrue(updatedCurriculum.getGrades().isEmpty());

        // remove one item from collection

        Set<Long> schooleDepartments = updatedCurriculum.getSchoolDepartments();
        schooleDepartments.remove(schooleDepartments.iterator().next());

        Set<CurriculumModuleDto> curriculumModules = updatedCurriculum.getModules();
        curriculumModules.remove(curriculumModules.iterator().next());

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        Assert.assertTrue(updatedCurriculum.getSchoolDepartments().size() == 1);
        Assert.assertTrue(updatedCurriculum.getModules().size() == 1);

        // change one collection item twice
        updatedCurriculum.getSpecialities().stream().findAny().get().setNameEt("CurriculumControllerTest changed 1");
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);
        CurriculumSpecialityDto updatedSpeciality = updatedCurriculum.getSpecialities().stream().filter(s -> s.getNameEt()
                .equals("CurriculumControllerTest changed 1")).findFirst().get();
        Assert.assertEquals(Long.valueOf(1), updatedSpeciality.getVersion());

        updatedSpeciality.setNameEt("CurriculumControllerTest changed 2");
        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);
        updatedSpeciality = updatedCurriculum.getSpecialities().stream().filter(s -> s.getNameEt()
                .equals("CurriculumControllerTest changed 2")).findFirst().get();
        Assert.assertEquals(Long.valueOf(2), updatedSpeciality.getVersion());

        // check that collection items do not change their id when not changed
        // one from join table and other item that is created on form
        Long specialityId = updatedCurriculum.getSpecialities().stream().findFirst().get().getId();
        testCurriculum = curriculumService.getOne(updatedCurriculum.getId());
        Long studyLanguageId = testCurriculum.getStudyLanguages().stream().findAny().get().getId();

        responseEntity = restTemplate.exchange("/curriculum/{id}", HttpMethod.PUT, new HttpEntity<>(updatedCurriculum), CurriculumDto.class, testCurriculum.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        updatedCurriculum = responseEntity.getBody();
        Assert.assertNotNull(updatedCurriculum);

        updatedSpeciality = updatedCurriculum.getSpecialities().stream().filter(s -> s.getId()
                .equals(specialityId)).findFirst().get();
        Assert.assertNotNull(updatedSpeciality);

        testCurriculum = curriculumService.getOne(updatedCurriculum.getId());
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
        Assert.assertTrue(updatedCurriculum.getSpecialities().size() == 3);


    }

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

    private CurriculumForm getForm(LocalDate validFrom) {
        CurriculumForm curriculumForm = new CurriculumForm();
        curriculumForm.setCode("testCode");
        curriculumForm.setOptionalStudyCredits(Integer.valueOf(1));
        curriculumForm.setStudyPeriod(Integer.valueOf(1));
        curriculumForm.setNameEn("nameEn");
        curriculumForm.setNameEt("nameEt");
        curriculumForm.setConsecution("OPPEKAVA_TYPE_E");
        curriculumForm.setOrigStudyLevel("EKR_2");
        curriculumForm.setDraft("OPPEKAVA_LOOMISE_VIIS_PUUDUB");
        curriculumForm.setStatus("OPPEKAVA_STAATUS_S");
        curriculumForm.setJointMentor("EHIS_KOOL_1");
        curriculumForm.setHigher(Boolean.TRUE);
        curriculumForm.setValidFrom(validFrom);
        curriculumForm.setStudyLanguages(new HashSet<>());
        curriculumForm.setIscedClass("ISCED_RYHM_0812");
        return curriculumForm;
    }

    private void setCollections(CurriculumForm curriculumForm) {
        curriculumForm.setSchoolDepartments(Sets.newLinkedHashSet(Long.valueOf(1), Long.valueOf(3)));
        curriculumForm.setStudyLanguages(Sets.newLinkedHashSet("OPPEKEEL_et_en", "OPPEKEEL_et_ru"));
        curriculumForm.setStudyForms(Sets.newLinkedHashSet("OPPEVORM_K", "OPPEVORM_MS"));

        Set<CurriculumFileDto> files = new HashSet<>();
        files.add(getCurriculumFileDto());
        files.add(getCurriculumFileDto());
        curriculumForm.setFiles(files);

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

        Set<CurriculumOccupationDto> occupations = new HashSet<>();
        occupations.add(getCurriculumOccupationDto("KUTSE_10431606"));
        occupations.add(getCurriculumOccupationDto("KUTSE_10411912"));
        curriculumForm.setOccupations(occupations);

        curriculumForm.setModules(Sets.newLinkedHashSet(getCurriculumModuleDto(), getCurriculumModuleDto()));

        CurriculumVersionDto version1 = getCurriculumVersionDto();
        CurriculumVersionHigherModuleDto versionModule = getCurriculumVersionHigherModuleDto();
        versionModule.setSubjects(Sets.newLinkedHashSet
                (getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(33), Boolean.TRUE),
                        getCurriculumVersionHigherModuleSubjectDto(Long.valueOf(34), Boolean.FALSE)));
        CurriculumVersionElectiveModuleDto electiveModule = getCurriculumVersionElectiveModuleDto();
        electiveModule.setSubjects(Sets.newLinkedHashSet(Long.valueOf(33)));
        versionModule.setElectiveModules(Sets.newLinkedHashSet(electiveModule));
        version1.setModules(Sets.newLinkedHashSet(versionModule));
        Set<CurriculumVersionDto> versions = new HashSet<>();
        versions.add(version1);
        curriculumForm.setVersions(versions);
    }

    private CurriculumFileDto getCurriculumFileDto() {
        CurriculumFileDto dto = new CurriculumFileDto();
        dto.setEhis(Boolean.FALSE);
        dto.setSendEhis(Boolean.FALSE);
        dto.setEhisFile("EHIS_FAIL_17884");

        OisFileCommand oisFile = new OisFileCommand();
        oisFile.setFname("CurriculumControllerTest");
        oisFile.setFtype("CurriculumControllerTest");
        oisFile.setFdata(new byte[]{1, 2, 3});

        dto.setOisFile(oisFile);
        return dto;
    }

    private CurriculumSpecialityDto getCurriculumSpecialityDto() {
        CurriculumSpecialityDto dto = new CurriculumSpecialityDto();
        dto.setNameEt("CurriculumControllerTest");
        dto.setNameEn("CurriculumControllerTest");
        dto.setCredits(Double.valueOf(1));
        dto.setOccupation("KUTSE_10491530");
        dto.setReferenceNumber(referenceNumber++);
        return dto;
    }

    private CurriculumGradeDto getCurriculumGradeDto() {
        CurriculumGradeDto dto = new CurriculumGradeDto();
        dto.setNameEt("CurriculumControllerTest");
        dto.setNameEn("CurriculumControllerTest");
        dto.setNameGenitiveEt("CurriculumControllerTest");
        dto.setEhisGrade("EKR_8");
        return dto;
    }

    private CurriculumJointPartnerDto getCurriculumJointPartnerDto() {
        CurriculumJointPartnerDto dto = new CurriculumJointPartnerDto();
        dto.setAbroad(Boolean.FALSE);
        dto.setContractEt("CurriculumControllerTest");
        dto.setContractEn("CurriculumControllerTest");
        dto.setSupervisor("CurriculumControllerTest");
        dto.setEhisSchool("EHIS_KOOL_113");
        return dto;
    }

    private CurriculumOccupationDto getCurriculumOccupationDto(String occupation) {
        CurriculumOccupationDto dto = new CurriculumOccupationDto();
        dto.setOccupationGrant(Boolean.FALSE);
        dto.setOccupation(occupation);
        Set<String> specialities = new HashSet<>();
        specialities.add("SPETSKUTSE_10601291");
        specialities.add("SPETSKUTSE_10601294");
        dto.setSpecialities(specialities);
        return dto;
    }

    private CurriculumModuleDto getCurriculumModuleDto(){
        CurriculumModuleDto dto = new CurriculumModuleDto();
        dto.setNameEt("CurriculumControllerTest");
        dto.setObjectivesEt("CurriculumControllerTest");
        dto.setModule("KUTSEMOODUL_P");
        dto.setPractice(Boolean.FALSE);
        dto.setCredits(1);
        dto.setOccupations(Sets.newLinkedHashSet("OSAKUTSE_10498104", "KUTSE_10463859"));
        dto.setCompetences(Sets.newLinkedHashSet("KOMPETENTS_4", "KOMPETENTS_13"));
        dto.setOutcomes(Sets.newLinkedHashSet(getCurriculumModuleOutcomeDto(), getCurriculumModuleOutcomeDto()));
        return dto;
    }

    private CurriculumModuleOutcomeDto getCurriculumModuleOutcomeDto() {
        CurriculumModuleOutcomeDto dto = new CurriculumModuleOutcomeDto();
        dto.setOutcomeEt("CurriculumControllerTest");
        return dto;
    }

    private CurriculumVersionDto getCurriculumVersionDto() {
        CurriculumVersionDto dto = new CurriculumVersionDto();
        dto.setCode("CurriculumControllerTest");
        dto.setType("OPPEKAVA_VERSIOON_LIIK_O");
        dto.setStatus("OPPEKAVA_VERSIOON_STAATUS_K");
        dto.setAdmissionYear(2017);
        return dto;
    }

    private CurriculumVersionHigherModuleDto getCurriculumVersionHigherModuleDto() {
        CurriculumVersionHigherModuleDto dto = new CurriculumVersionHigherModuleDto();
        dto.setTotalCredits(1);
        dto.setOptionalStudyCredits(1);
        dto.setType("KORGMOODUL_F");
        dto.setNameEn("CurriculumControllerTest");
        dto.setNameEt("CurriculumControllerTest");
        dto.setElectiveModulesNumber(1);
        dto.setCompulsoryStudyCredits(1);
        return dto;
    }

    private CurriculumVersionElectiveModuleDto getCurriculumVersionElectiveModuleDto() {
        CurriculumVersionElectiveModuleDto dto = new CurriculumVersionElectiveModuleDto();
        dto.setNameEt("CurriculumControllerTest");
        dto.setNameEn("CurriculumControllerTest");
        return dto;
    }

    private CurriculumVersionHigherModuleSubjectDto getCurriculumVersionHigherModuleSubjectDto(Long subjectId, Boolean isOptional){
        CurriculumVersionHigherModuleSubjectDto dto = new CurriculumVersionHigherModuleSubjectDto();
        dto.setSubjectId(subjectId);
        dto.setOptional(isOptional);
        return dto;
    }
}
