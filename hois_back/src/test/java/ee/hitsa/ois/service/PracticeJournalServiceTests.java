package ee.hitsa.ois.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PracticeJournalServiceTests {

    @Autowired
    private PracticeJournalService practiceJournalService;

    @Test
    public void getFromSupervisorUrl() {
        String uuid = UUID.randomUUID().toString();
        Assert.assertNull(practiceJournalService.getFromSupervisorUrl(uuid));
        // Assert.assertNull(practiceJournalService.getFromSupervisorUrl("83a10b32-5094-425c-bebc-83cdb2a8fb50"));
        // Assert.assertNotNull(practiceJournalService.getFromSupervisorUrl("593bc8fb-afad-4ac7-ad34-8d5a55e7e883"));
    }
}
