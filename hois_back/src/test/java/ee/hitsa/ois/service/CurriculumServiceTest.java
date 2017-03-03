package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.school.School;

/**
 * Created in order to test Curriculum mapping with dependent objects 
 * as controller test does not 
 * 
 * NB! getValidInstance() methods made static in order to enabling 
 * creation of test instances in another test classes,
 * thus making test independent from values in database.
 * 
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurriculumServiceTest {

    private static final String NAME = "CurriculumTestName";
    private static final Integer NUMBER = Integer.valueOf(1);
    private static final Long SCHOOL_ID = Long.valueOf(1);
    private static final List<String> LANGUAGES = Arrays.asList("OPPEKEEL_E", "OPPEKEEL_I", "OPPEKEEL_V");

    @Autowired
    private ClassifierService classifierService;
    
    @Autowired
    private CurriculumService curriculumService;
    
    @Autowired
    private SchoolService schoolService;

    @BeforeClass
    public static void setUp() {
    }
    
    @AfterClass
    public static void cleanUp() {	
    }
    
    @Test
    public void crud() {
        //create
        Classifier testClassifier = classifierService.findOne("OPPEKAVA_TYPE_E");
        Curriculum c = getValidCurriculum(NAME, NUMBER, schoolService.getOne(SCHOOL_ID), testClassifier);
        Set<CurriculumStudyLanguage> studyLanguages = new HashSet<>();
        studyLanguages.add(getValidCurriculumStudyLang(classifierService.findOne(LANGUAGES.get(0))));
        studyLanguages.add(getValidCurriculumStudyLang(classifierService.findOne(LANGUAGES.get(1))));
        c.setStudyLanguages(studyLanguages);

        c = curriculumService.save(c);
        Assert.assertNotNull(c);
        Assert.assertNotNull(c.getId());
        Assert.assertNotNull(c.getInserted());

        // update field
        String newName = NAME.concat(NAME);
        c.setNameEt(newName);
        c = curriculumService.save(c);
        Assert.assertEquals(newName, c.getNameEt());

        // ....update list of languages
        Assert.assertEquals(2, c.getStudyLanguages().size());

        // remove one
        String removedLangCode = LANGUAGES.get(0);
        Iterator<CurriculumStudyLanguage> i = c.getStudyLanguages().iterator();
        while(i.hasNext()) {
            CurriculumStudyLanguage lang = i.next();
            if(lang.getStudyLang().getCode().equals(removedLangCode)) {
                i.remove();
            }
        }
        // add new one
        studyLanguages = new HashSet<>();
        studyLanguages.add(c.getStudyLanguages().iterator().next());
        studyLanguages.add(c.getStudyLanguages().iterator().next());
        studyLanguages.add(getValidCurriculumStudyLang(classifierService.findOne(LANGUAGES.get(2))));
        c.setStudyLanguages(studyLanguages);
        
        
        // check
        c = curriculumService.save(c);
        Assert.assertEquals(2, c.getStudyLanguages().size());

        i = c.getStudyLanguages().iterator();
        while(i.hasNext()) {
            CurriculumStudyLanguage lang = i.next();
            String langCode = lang.getStudyLang().getCode();
            Assert.assertTrue(langCode.equals(LANGUAGES.get(1)) || langCode.equals(LANGUAGES.get(2)));
            Assert.assertFalse(langCode.equals(removedLangCode));
        }

        // read
        Long id = c.getId();
        Curriculum c2 = curriculumService.getOne(id);
        Assert.assertEquals(id, c2.getId());

        // delete
        curriculumService.delete(c);
    }

    public static Curriculum getValidCurriculum(String name, Integer number, School school, Classifier classifier) {
        Curriculum curriculum = new Curriculum();
        curriculum.setHigher(Boolean.TRUE);
        curriculum.setNameEt(name);
        curriculum.setNameEn(name);
        curriculum.setCode(name);
        curriculum.setStudyPeriod(number);
        curriculum.setJoint(Boolean.TRUE);
        curriculum.setOptionalStudyCredits(number);
        curriculum.setOccupation(Boolean.TRUE);
        curriculum.setValidFrom(LocalDate.now());
        curriculum.setConsecution(classifier);
        curriculum.setDraft(classifier);
        curriculum.setStatus(classifier);
        curriculum.setOrigStudyLevel(classifier);
        curriculum.setSchool(school);

        return curriculum;
    }
     
    public static CurriculumStudyLanguage getValidCurriculumStudyLang(Classifier lang) {
        CurriculumStudyLanguage studyLang = new CurriculumStudyLanguage();
        studyLang.setStudyLang(lang);
        return studyLang;
    }
        
    /*
     * TODO: test managing list of CurriculumFiles, 
     * using two methods below
     */
    public static CurriculumFile getValidCurriculumFile(String name) {
        CurriculumFile file = new CurriculumFile();
        file.setEhis(true);
        file.setSendEhis(true);
        file.setOisFile(getValidOisFile(name));
        return file;
    }

    public static OisFile getValidOisFile(String name) {
        OisFile oisFile = new OisFile();
        oisFile.setFname(name);
        oisFile.setFtype(name);
        oisFile.setFdata(new byte[]{1, 1});
        return oisFile;
    }
}
