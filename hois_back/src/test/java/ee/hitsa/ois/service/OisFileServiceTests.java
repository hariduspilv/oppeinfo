package ee.hitsa.ois.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OisFileServiceTests {

    private static final String MISSING_FILE_ID = "0";

    @Autowired
    private OisFileService oisFileService;

    @Test(expected=EntityNotFoundException.class)
    public void getApelapplication() throws IOException {
        oisFileService.get("apelapplication", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getApplication() throws IOException {
        oisFileService.get("application", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getCurriculum() throws IOException {
        oisFileService.get("curriculum", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getPracticejournal() throws IOException {
        oisFileService.get("practicejournal", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getProtocol() throws IOException {
        oisFileService.get("protocol", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getScholarshipapplication() throws IOException {
        oisFileService.get("scholarshipapplication", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getSchool() throws IOException {
        oisFileService.get("school", MISSING_FILE_ID, null);
    }

    @Test(expected=EntityNotFoundException.class)
    public void getStudent() throws IOException {
        oisFileService.get("student", MISSING_FILE_ID, null);
    }
}
