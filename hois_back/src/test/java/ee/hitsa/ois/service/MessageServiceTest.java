package ee.hitsa.ois.service;

import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.web.commandobject.UsersSeachCommand;
import ee.hitsa.ois.web.dto.UsersSearchDto;

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

    @Test
    public void searchAllUsers() {
        Page<UsersSearchDto> page = messageService.searchAllUsers(new UsersSeachCommand(), new PageRequest(0, 10));
        Assert.assertNotNull(page);
    }

    @Test
    public void getRepresentativePersonIds() {
        messageService.getRepresentativePersonIds(Collections.singleton(Long.valueOf(10)));
    }

    /**
     * TODO: set different users for different tests
     * In this case MessageController may be tested, not MessageService
     */
//    @Test
//    public void searchParentsTeachers() {
////        HoisUserDetails user = new HoisUserDetails(null, null);
//        User userr = userRepository.findOne(Long.valueOf(1));
//        Page<UsersSearchDto> page = messageService.searchParentsTeachers(null, new UsersSeachCommand(), new PageRequest(0, 10));
//        Assert.assertNotNull(page);
//    }
}
