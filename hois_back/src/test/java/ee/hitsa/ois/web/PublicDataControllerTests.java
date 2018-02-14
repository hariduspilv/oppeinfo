package ee.hitsa.ois.web;

import java.util.List;

import javax.persistence.EntityManager;

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

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.util.EntityUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PublicDataControllerTests {

    private static final String CURRICULUM_ENDPOINT = "/public/curriculum/";
    private static final String SUBJECT_ENDPOINT = "/public/subject";

    @Autowired
    private EntityManager em;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void curriculum() {
        Curriculum curriculum = confirmedCurriculum();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(CURRICULUM_ENDPOINT);
        String uri = uriBuilder.pathSegment(curriculum.getId().toString()).toUriString();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void curriculumVersion() {
        CurriculumVersion cv = confirmedCurriculumVersion();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(CURRICULUM_ENDPOINT);
        String uri = uriBuilder.pathSegment(EntityUtil.getId(cv.getCurriculum()).toString()).pathSegment(cv.getId().toString()).toUriString();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void subject() {
        Subject s = activeSubject();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(SUBJECT_ENDPOINT);
        String uri = uriBuilder.pathSegment(EntityUtil.getId(s).toString()).toUriString();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Curriculum confirmedCurriculum() {
        List<Curriculum> curriculums = em.createQuery("select c from Curriculum c where c.status.code = ?1", Curriculum.class)
            .setParameter(1, CurriculumStatus.OPPEKAVA_STAATUS_K.name())
            .setMaxResults(1).getResultList();
        return curriculums.isEmpty() ? null : curriculums.get(0);
    }

    private CurriculumVersion confirmedCurriculumVersion() {
        List<CurriculumVersion> cvs = em.createQuery("select cv from CurriculumVersion cv where cv.status.code = ?1", CurriculumVersion.class)
            .setParameter(1, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name())
            .setMaxResults(1).getResultList();
        return cvs.isEmpty() ? null : cvs.get(0);
    }

    private Subject activeSubject() {
        List<Subject> subjects = em.createQuery("select s from Subject s where s.status.code = ?1", Subject.class)
            .setParameter(1, SubjectStatus.AINESTAATUS_K.name())
            .setMaxResults(1).getResultList();
        return subjects.isEmpty() ? null : subjects.get(0);
    }
}
