package ee.hitsa.ois.service;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.TestData;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.xml.curriculum.CurriculumVersionXml;
import ee.hitsa.ois.xml.curriculum.CurriculumXml;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class XmlServiceTests {
    
    @Autowired
    private XmlService xmlService;
    
    @Autowired
    private TestData testData;
    
    @Test
    public void curriculumXml() throws JAXBException {
        Curriculum curriculum = testData.getCurriculum();
        xmlService.generateFromObject(CurriculumXml.of(curriculum));
    }

    @Test
    public void curriculumVersionXml() throws JAXBException {
        Curriculum curriculum = testData.getCurriculum();
        CurriculumVersion cv = testData.getHigherCurriculumVersion();
        cv.setCurriculum(curriculum);
        curriculum.getVersions().add(cv);
        xmlService.generateFromObject(CurriculumVersionXml.get(cv));
    }
}
