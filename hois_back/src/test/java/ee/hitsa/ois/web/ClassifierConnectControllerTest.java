package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.repository.ClassifierRepository;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClassifierConnectControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClassifierRepository classifierRepository;

    @Test
    public void testChangeListOfParents() {

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

         UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/classifierConnect/all");
         uriBuilder.queryParam("classifierCode", child.getCode());
         String uri = uriBuilder.build().toUriString();
         ResponseEntity<Object[]> searchResponseEntity = restTemplate.getForEntity(uri, Object[].class);
         Assert.assertNotNull(searchResponseEntity);
         Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());
         Assert.assertTrue(searchResponseEntity.getBody().length == 2);
         ObjectMapper mapper = new ObjectMapper();

         ClassifierConnect cc1 = mapper.convertValue(searchResponseEntity.getBody()[0], ClassifierConnect.class);
         Assert.assertTrue(cc1.getClassifier().getCode().equals(child.getCode()));
         Assert.assertTrue(cc1.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                 cc1.getConnectClassifier().getCode().equals(parent2.getCode()));

         ClassifierConnect cc2 = mapper.convertValue(searchResponseEntity.getBody()[1], ClassifierConnect.class);
         Assert.assertTrue(cc2.getClassifier().getCode().equals(child.getCode()));
         Assert.assertTrue(cc2.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                 cc2.getConnectClassifier().getCode().equals(parent2.getCode()) &&
                 !cc2.getConnectClassifier().getCode().equals(cc1.getConnectClassifier().getCode()));

        // change list of parents: remove one and add a new one

        responseEntity = this.restTemplate.postForEntity("/classifierConnect/changeParents/" + child.getCode(),
                asList(parent1, parent3), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


        // check that only parent1 and parent3 are in list of parents

        searchResponseEntity = restTemplate.getForEntity(uri, Object[].class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());
        Assert.assertTrue(searchResponseEntity.getBody().length == 2);

        cc1 = mapper.convertValue(searchResponseEntity.getBody()[0], ClassifierConnect.class);
        Assert.assertTrue(cc1.getClassifier().getCode().equals(child.getCode()));
        Assert.assertTrue(cc1.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                cc1.getConnectClassifier().getCode().equals(parent3.getCode()));

        cc2 = mapper.convertValue(searchResponseEntity.getBody()[1], ClassifierConnect.class);
        Assert.assertTrue(cc2.getClassifier().getCode().equals(child.getCode()));
        Assert.assertTrue(cc2.getConnectClassifier().getCode().equals(parent1.getCode()) ||
                cc2.getConnectClassifier().getCode().equals(parent3.getCode()) &&
                !cc2.getConnectClassifier().getCode().equals(cc1.getConnectClassifier().getCode()));

        // remove connections with parents

        responseEntity = this.restTemplate.postForEntity("/classifierConnect/changeParents/" + child.getCode(),
                Arrays.asList(), Boolean.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // check that list of parents is empty

        searchResponseEntity = restTemplate.getForEntity(uri, Object[].class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());
        Assert.assertTrue(searchResponseEntity.getBody().length == 0);
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
