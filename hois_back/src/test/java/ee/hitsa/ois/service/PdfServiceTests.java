package ee.hitsa.ois.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.TestData;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.report.CurriculumReport;
import ee.hitsa.ois.report.StateCurriculumReport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class PdfServiceTests {

    @Autowired
    private PdfService pdfService;
    @Autowired
    private TestData testData;

    @Test
    public void higherCurriculumPdf() {
        CurriculumVersion curriculumVersion = new CurriculumVersion();
        Curriculum curriculum = new Curriculum();
        curriculumVersion.setCurriculum(curriculum);
        curriculum.setStudyPeriod(Integer.valueOf(15));
        /* byte[] data = */ pdfService.generate(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculumVersion));
        curriculum.setMerRegDate(LocalDate.now());
        /* data = */ pdfService.generate(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculumVersion));
        // toFile("highercurriculum.pdf", data);
    }
    
    @Test
    public void stateCurriculumPdf() {
        StateCurriculum sc = testData.getStateCurriculum();
        sc.getModules().add(testData.getStateCurriculumModule());
        pdfService.generate(StateCurriculumReport.TEMPLATE_NAME, new StateCurriculumReport(sc));
    }

    private static void toFile(String name, byte[] data) {
        try(FileOutputStream os = new FileOutputStream(Objects.requireNonNull(name))) {
            os.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
