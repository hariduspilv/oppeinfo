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

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.report.CurriculumReport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class PdfServiceTests {

    @Autowired
    private PdfService pdfService;

    @Test
    public void higherCurriculumPdf() {
        Curriculum curriculum = new Curriculum();
        curriculum.setStudyPeriod(Integer.valueOf(15));
        byte[] data = pdfService.generatePdf(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculum, Language.ET));
        curriculum.setMerRegDate(LocalDate.now());
        data = pdfService.generatePdf(CurriculumReport.TEMPLATE_NAME, new CurriculumReport(curriculum, Language.ET));
        // toFile("highercurriculum.pdf", data);
    }

    private static void toFile(String name, byte[] data) {
        try(FileOutputStream os = new FileOutputStream(Objects.requireNonNull(name))) {
            os.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
