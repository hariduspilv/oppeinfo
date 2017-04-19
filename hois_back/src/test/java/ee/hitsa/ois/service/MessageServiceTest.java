package ee.hitsa.ois.service;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.MessageReceiverDto;

/**
 * Different queries are run depending on user's role.
 * The goal of these tests is to check whether these queries work at all
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    @Test
    public void searchAllUsers() {
        List<MessageReceiverDto> page = messageService.searchAllUsers(hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID), new UsersSearchCommand());
        Assert.assertNotNull(page);
    }

    /**
     * TODO: set different users for different tests
     * In this case MessageController may be tested, not MessageService
     */
//    @Test
//    public void searchParentsTeachers() {
////        HoisUserDetails user = new HoisUserDetails(null, null);
//        User userr = userRepository.findOne(Long.valueOf(1));
//        Page<UsersSearchDto> page = messageService.searchParentsTeachers(null, new UsersSearchCommand(), new PageRequest(0, 10));
//        Assert.assertNotNull(page);
//    }
}
