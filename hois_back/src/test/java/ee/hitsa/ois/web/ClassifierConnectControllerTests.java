package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.web.dto.ClassifierConnectSelection;
import ee.hitsa.ois.web.dto.ClassifierSelection;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClassifierConnectControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_P, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void testChangeListOfParents() throws RestClientException {

        Classifier child = classifierRepository.findOne("OPPEKAVA_STAATUS_S");
        Classifier parent1 = classifierRepository.findOne("OPPEKAVA_STAATUS_M");
        Classifier parent2 = classifierRepository.findOne("OPPEKAVA_STAATUS_K");
        Classifier parent3 = classifierRepository.findOne("OPPEKAVA_STAATUS_C");

        Assert.assertNotNull(child);
        Assert.assertNotNull(parent1);
        Assert.assertNotNull(parent2);
        Assert.assertNotNull(parent3);

        // save initial list of parents
        ResponseEntity<Boolean> responseEntity = this.restTemplate.postForEntity(
                "/classifierConnect/changeParents/" + child.getCode(), asList(parent1, parent2), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // check that only parent1 and parent2 are in list of parents
         UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/classifierConnect/all").queryParam("classifierCode", child.getCode());
         String uri = uriBuilder.toUriString();
         ResponseEntity<Object> searchResponseEntity = restTemplate.getForEntity(uri, Object.class);
         Assert.assertNotNull(searchResponseEntity);
         Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());

         List<ClassifierConnectSelection> searchResponse = getResponseEntity(searchResponseEntity.getBody());
         Assert.assertEquals(2, searchResponse.size());

         ClassifierConnectSelection cc1 = searchResponse.get(0);
         Assert.assertEquals(child.getCode(), cc1.getClassifier().getCode());
         Assert.assertTrue(cc1.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                 cc1.getConnectClassifier().getCode().equals(parent2.getCode()));

         ClassifierConnectSelection cc2 = searchResponse.get(1);
         Assert.assertEquals(child.getCode(), cc2.getClassifier().getCode());
         Assert.assertTrue(cc2.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                 cc2.getConnectClassifier().getCode().equals(parent2.getCode()) &&
                 !cc2.getConnectClassifier().getCode().equals(cc1.getConnectClassifier().getCode()));

        // change list of parents: remove one and add a new one

        responseEntity = this.restTemplate.postForEntity("/classifierConnect/changeParents/" + child.getCode(),
                asList(parent1, parent3), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


        // check that only parent1 and parent3 are in list of parents

        searchResponseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());

        searchResponse = getResponseEntity(searchResponseEntity.getBody());
        Assert.assertEquals(2, searchResponse.size());

        cc1 = searchResponse.get(0);
        Assert.assertEquals(child.getCode(), cc1.getClassifier().getCode());
        Assert.assertTrue(cc1.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                cc1.getConnectClassifier().getCode().equals(parent3.getCode()));

        cc2 = searchResponse.get(1);
        Assert.assertEquals(child.getCode(), cc2.getClassifier().getCode());
        Assert.assertTrue(cc2.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                cc2.getConnectClassifier().getCode().equals(parent3.getCode()) &&
                !cc2.getConnectClassifier().getCode().equals(cc1.getConnectClassifier().getCode()));

        // remove connections with parents

        responseEntity = this.restTemplate.postForEntity("/classifierConnect/changeParents/" + child.getCode(),
                Arrays.asList(), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // check that list of parents is empty

        searchResponseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());

        searchResponse = getResponseEntity(searchResponseEntity.getBody());
        Assert.assertTrue(searchResponse.isEmpty());
    }

    @SuppressWarnings("unchecked")
    private static List<ClassifierConnectSelection> getResponseEntity(Object body) {
        List<ClassifierConnectSelection> response = new ArrayList<>();
        for (LinkedHashMap<String, Object> classifierConnectSelection : (List<LinkedHashMap<String, Object>>)body) {
            response.add(new ClassifierConnectSelection(
                    getClassifierSelection(classifierConnectSelection.get("classifier")),
                    getClassifierSelection(classifierConnectSelection.get("connectClassifier")),
                    (String)classifierConnectSelection.get("mainClassifierCode")));
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    private static ClassifierSelection getClassifierSelection(Object object) {
        LinkedHashMap<String, Object> classifierSelectionObject = (LinkedHashMap<String, Object>) object;
        ClassifierSelection classifierSelection = new ClassifierSelection((String)classifierSelectionObject.get("code"),
                null, null, null, null, null, null, null, null, null);
        return classifierSelection;
    }

    private static List<Classifier> asList(Classifier...classifiers) {
        List<Classifier> list = new ArrayList<>();
        for (Classifier classifier : classifiers) {
            list.add(classifier);
        }
        return list;
    }

    @Test
    public void testSearchAll() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/classifierConnect/all");
        uriBuilder.queryParam("classifierCode", "");
        uriBuilder.queryParam("connectClassifierCode", "");
        uriBuilder.queryParam("mainClassifierCode", "");
        uriBuilder.queryParam("connectClassifierCode", Arrays.asList("", ""));
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testSearchAllWithNoParametersFail() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/classifierConnect");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
    }
}
