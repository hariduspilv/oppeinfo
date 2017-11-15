package ee.hitsa.ois.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.school.School;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class EmailGeneratorServiceTests {

    @Autowired
    private EmailGeneratorService emailGeneratorService;

    @Test
    public void generateEmail() {
        School school = new School();
        school.setGenerateUserEmail(Boolean.TRUE);
        school.setEmailDomain("school.domain.eu");
        emailGeneratorService.generateEmail(school, "First Name", "Lastname");
    }
}
