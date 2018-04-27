package ee.hitsa.ois.service;

import static org.junit.Assert.*;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DirectiveConfirmServiceTests {

    @Autowired
    private EntityManager em;
    @Autowired
    private DirectiveConfirmService directiveConfirmService;
    
    @Test
    public void findAcademicLeaves() {
        Directive directive = em.getReference(Directive.class, Long.valueOf(2056));
        Map<Long, DirectiveStudent> academicLeaves = directiveConfirmService.findAcademicLeaves(directive);
        assertNotNull(academicLeaves.get(Long.valueOf(427)));
    }

}
