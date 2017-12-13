package ee.hitsa.ois.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.service.kutseregister.KutseregisterService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class KutseregisterServiceTests {

    @Autowired
    private KutseregisterService kutseregisterService;

    @Ignore("for development")
    @Test
    public void muutunudKutsestandardid() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        kutseregisterService.muutunudKutsestandardid(lastMonth);
    }
}
