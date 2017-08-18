package ee.hitsa.ois.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.repository.DirectiveRepository;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DirectiveServiceTests {

    @Autowired
    private DirectiveRepository directiveRepository;

    @Test
    public void cancelDirectiveExists() {
        directiveRepository.existsByCanceledDirectiveIdAndStatusCodeEquals(Long.valueOf(1), DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL.name());
    }
}
