package ee.hitsa.ois.web;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import javax.persistence.EntityManager;

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
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.DirectiveService;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveDataCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveDto;
import ee.hitsa.ois.web.dto.directive.DirectiveViewDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DirectiveControllerTests {

    @Autowired
    private DirectiveService directiveService;
    @Autowired
    private EntityManager em;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    private Long coordinatorId;
    private Long directiveId;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
        if(coordinatorId != null) {
            directiveService.delete(em.getReference(DirectiveCoordinator.class, coordinatorId));
            coordinatorId = null;
        }
        if(directiveId != null) {
            directiveService.delete(em.getReference(Directive.class, directiveId));
            directiveId = null;
        }
    }

    @Test
    public void directivedata() {
        String url = "/directives/directivedata";
        Set<DirectiveType> excluded = EnumSet.of(DirectiveType.KASKKIRI_TYHIST);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, new DirectiveDataCommand(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        for(DirectiveType type : DirectiveType.values()) {
            if(excluded.contains(type)) {
                continue;
            }
            DirectiveDataCommand cmd = new DirectiveDataCommand();
            cmd.setType(type.name());
            cmd.setStudents(Arrays.asList(Long.valueOf(1)));
            responseEntity = restTemplate.postForEntity(url, cmd, Object.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    @Test
    public void search() {
        String url = "/directives";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/directives");
        uriBuilder.queryParam("type", "TYPE1", "TYPE2");
        uriBuilder.queryParam("headline", "headline");
        uriBuilder.queryParam("directiveNr", "directiveNr");
        uriBuilder.queryParam("confirmDateFrom", "2017-02-17T00:00:00.00Z");
        uriBuilder.queryParam("confirmDateThru", "2017-02-17T00:00:00.00Z");
        uriBuilder.queryParam("status", "OPPURSTATUS_AKAD", "OPPURSTATUS_OPIB");
        uriBuilder.queryParam("insertedFrom", "2017-02-16T00:00:00.00Z");
        uriBuilder.queryParam("insertedThru", "2017-02-16T00:00:00.00Z");
        uriBuilder.queryParam("studentGroup", "studentGroup");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchStudents() {
        String url = "/directives/findstudents";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        for(DirectiveType directiveType : DirectiveType.values()) {
            // student has application
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/directives/findstudents");
            uriBuilder.queryParam("type", directiveType.name());
            uriBuilder.queryParam("application", "true");
            responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            // student does not have application
            uriBuilder = UriComponentsBuilder.fromUriString("/directives/findstudents");
            uriBuilder.queryParam("type", directiveType.name());
            responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
            Assert.assertNotNull(responseEntity);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/directives/findstudents");
        uriBuilder.queryParam("firstname", "FIRSTNAME");
        uriBuilder.queryParam("lastname", "LASTNAME");
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("application", "true");
        uriBuilder.queryParam("type", DirectiveType.KASKKIRI_AKAD.name());
        uriBuilder.queryParam("directive", "1");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchCoordinators() {
        String url = "/directives/coordinators";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getMissing() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/directives/coordinators/0", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void validate() {
        // create
        String uri = "/directives/coordinators";
        DirectiveCoordinatorForm form = new DirectiveCoordinatorForm();
        ResponseEntity<DirectiveCoordinatorDto> responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setName("Kooskõlastaja Nimi");
        responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setIdcode("Wrong idcode");
        responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        String baseUrl = "/directives";
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        DirectiveForm form = new DirectiveForm();
        form.setType(DirectiveType.KASKKIRI_AKADK.name());
        form.setHeadline("Akad katkestamise käskkiri");
        ResponseEntity<DirectiveDto> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(), form, DirectiveDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        directiveId = responseEntity.getBody().getId();
        Assert.assertNotNull(directiveId);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(directiveId.toString());
        ResponseEntity<DirectiveDto> response = restTemplate.getForEntity(uriBuilder.build().toUriString(), DirectiveDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // read for view
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(directiveId.toString()).pathSegment("view");
        ResponseEntity<DirectiveViewDto> viewResponse = restTemplate.getForEntity(uriBuilder.build().toUriString(), DirectiveViewDto.class);
        Assert.assertNotNull(viewResponse);
        Assert.assertEquals(HttpStatus.OK, viewResponse.getStatusCode());

        // update
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(directiveId.toString());
        form = response.getBody();
        Assert.assertNotNull(form);
        form.setHeadline("Akad katkestamise käskkiri (muudetud)");
        responseEntity = restTemplate.exchange(uriBuilder.build().toUriString(), HttpMethod.PUT, new HttpEntity<>(form), DirectiveDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // read
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(directiveId.toString());
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), DirectiveDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(directiveId.toString());
        uriBuilder.queryParam("version", version);
        restTemplate.delete(uriBuilder.build().toUriString());
        directiveId = null;
    }

    @Test
    public void crudCoordinator() {
        String baseUrl = "/directives/coordinators";
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        String uri = uriBuilder.build().toUriString();
        DirectiveCoordinatorForm form = new DirectiveCoordinatorForm();
        form.setName("Käskkirjade kooskõlastaja");
        form.setIdcode("46904014003");
        ResponseEntity<DirectiveCoordinatorDto> responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        coordinatorId = responseEntity.getBody().getId();
        Assert.assertNotNull(coordinatorId);

        // duplicate entry
        responseEntity = restTemplate.postForEntity(uri, form, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        // read
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(coordinatorId.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<DirectiveCoordinatorDto> response = restTemplate.getForEntity(uri, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        form = response.getBody();
        Assert.assertNotNull(form);
        form.setName("Kooskõlastaja Käskkirjade");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), DirectiveCoordinatorDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // read
        responseEntity = restTemplate.getForEntity(uri, DirectiveCoordinatorDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);

        // try to update with wrong version
        form.setName("Käskkirjade kooskõlastaja");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), DirectiveCoordinatorDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // search existing departments
        UriComponentsBuilder searchUriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        ResponseEntity<Object> searchResponseEntity = restTemplate.getForEntity(searchUriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(coordinatorId.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
        coordinatorId = null;
    }
}
