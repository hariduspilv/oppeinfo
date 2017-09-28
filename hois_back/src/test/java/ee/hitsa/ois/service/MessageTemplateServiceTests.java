package ee.hitsa.ois.service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.enums.MessageType;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessageTemplateServiceTests {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Test
    public void findValidTemplate() {
        messageTemplateService.findValidTemplate(MessageType.TEATE_LIIK_AV_KINNIT, Long.valueOf(1));
    }
}
