package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.service.ehis.EhisDirectiveStudentService;
import ee.hitsa.ois.util.EnumUtil;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EhisDirectiveStudentServiceTests {

    private static final int NUMBER_OF_ITEMS = 10;

    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;
    @Autowired
    private EntityManager em;

    @Test
    public void updateStudents() {
        for(Directive directive : findDirectives()) {
            ehisDirectiveStudentService.updateStudents(directive.getId());
        }
    }

    private List<Directive> findDirectives() {
        List<Directive> directives = new ArrayList<>();
        // one directive of each type, excluding cancel directive
        for(DirectiveType type : DirectiveType.values()) {
            if(DirectiveType.KASKKIRI_TYHIST.equals(type)) {
                continue;
            }
            directives.addAll(em.createQuery("select d from Directive d where d.status.code in ?1", Directive.class)
                    .setParameter(1, EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD))
                    .setMaxResults(NUMBER_OF_ITEMS).getResultList());
        }
        return directives;
    }

}
