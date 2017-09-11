package ee.hitsa.ois.web;

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
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.BuildingForm;
import ee.hitsa.ois.web.commandobject.RoomForm;
import ee.hitsa.ois.web.dto.BuildingDto;
import ee.hitsa.ois.web.dto.RoomDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BuildingControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void allBuildings() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/autocomplete/buildings", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchRooms() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/rooms", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/rooms");
        uriBuilder.queryParam("name", "NIMI");
        uriBuilder.queryParam("code", "3211212");
        uriBuilder.queryParam("buildingName", "HOONE");
        uriBuilder.queryParam("buildingCode", "OPPEVORM_P");
        String url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/buildings");
        BuildingForm form = new BuildingForm();
        form.setName("Hoone nimi (test)");
        form.setCode("Hoone kood (test)");
        form.setAddress("Hoone aadress (test)");
        ResponseEntity<BuildingDto> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(), form, BuildingDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long buildingId = responseEntity.getBody().getId();
        Assert.assertNotNull(buildingId);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString("/buildings").pathSegment(buildingId.toString());
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<BuildingDto> response = restTemplate.getForEntity(uri, BuildingDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        form = response.getBody();
        Assert.assertNotNull(form);
        form.setAddress("Hoone uus aadress (test)");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), BuildingDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // read
        responseEntity = restTemplate.getForEntity(uri, BuildingDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);

        // try to update with wrong version
        form.setAddress("Hoone eriti uus aadress (test)");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), BuildingDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // create room
        uriBuilder = UriComponentsBuilder.fromUriString("/rooms");
        RoomForm room = new RoomForm();
        room.setBuilding(buildingId);
        room.setCode("Ruumi kood");
        ResponseEntity<RoomDto> roomResponse = restTemplate.postForEntity(uriBuilder.build().toUriString(), room, RoomDto.class);
        Assert.assertNotNull(roomResponse);
        Assert.assertEquals(HttpStatus.CREATED, roomResponse.getStatusCode());
        Assert.assertNotNull(roomResponse.getBody());
        Long roomId = roomResponse.getBody().getId();
        Assert.assertNotNull(roomId);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString("/rooms").pathSegment(roomId.toString());
        String roomUri = uriBuilder.build().toUriString();
        roomResponse = restTemplate.getForEntity(roomUri, RoomDto.class);
        Assert.assertNotNull(roomResponse);
        Assert.assertEquals(HttpStatus.OK, roomResponse.getStatusCode());

        // update room
        room = roomResponse.getBody();
        room.setName("Ruumi nimi (test)");
        roomResponse = restTemplate.exchange(roomUri, HttpMethod.PUT, new HttpEntity<>(room), RoomDto.class);
        Assert.assertNotNull(roomResponse);
        Assert.assertEquals(HttpStatus.OK, roomResponse.getStatusCode());

        // delete room
        uriBuilder = UriComponentsBuilder.fromUriString("/rooms").pathSegment(roomId.toString());
        uriBuilder.queryParam("version", roomResponse.getBody().getVersion());
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString("/buildings").pathSegment(buildingId.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
}
