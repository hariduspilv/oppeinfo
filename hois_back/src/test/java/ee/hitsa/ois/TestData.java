package ee.hitsa.ois;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

/**
 *
 * This is just proposal for creating instances of objects for testing (currently not used)
 * In most cases this approach does not work as inserted_by column, which is
 * mandatory in most tables and is handled by Spring, is left empty.
 *
 * Methods are placed in alphabetical order.
 *
 * Remember that not all objects have cascade delete option when cleaning test data!
 * 
 * Should it be totally database-independent?
 */
@Service
public class TestData {

    private static final String STRING = "ApplicationTest";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    /** Use getter go get the school even in this class! */
    private School school;

    public Curriculum getCurriculum() {
        Curriculum c = new Curriculum();
        c.setNameEt(STRING);
        c.setNameEn(STRING);
        c.setCode(STRING);

        c.setStudyPeriod(Integer.valueOf(1));
        c.setOptionalStudyCredits(BigDecimal.valueOf(1));

        c.setHigher(Boolean.FALSE);
        c.setJoint(Boolean.FALSE);
        c.setOccupation(Boolean.FALSE);
        c.setValidFrom(LocalDate.now());
        
        Classifier classifier = getClassifier();

        c.setConsecution(classifier);
        c.setStatus(classifier);
        c.setDraft(classifier);
        c.setOrigStudyLevel(classifier);
        c.setSchool(getSchool());

        return c;
    }
    
    public CurriculumVersion getHigherCurriculumVersion() {
        CurriculumVersion cv = new CurriculumVersion();
        cv.setId(Long.valueOf(-1));
        cv.setCode(STRING);
        cv.setStatus(getClassifier());
        return cv;
    }
    
    public Classifier getClassifier() {
        Classifier c = new Classifier();
        c.setCode(STRING);
        c.setNameEt(STRING);
        c.setNameEn(STRING);
        c.setValue(STRING);
        return c;
    }

    public School getSchool() {
//        if(school == null) {
//            school = schoolRepository.getOne(hoisUserDetailsService.
//                    loadUserByUsername(TestConfiguration.USER_ID).getSchoolId());
//        }
//        return school;
      if(school == null) {
          school = new School();
          school.setId(Long.valueOf(-1));
          school.setNameEn(STRING);
          school.setNameEt(STRING);
          school.setCode(STRING);
      }
      return school;
    }

    public StudentGroup getStudentGroup(Curriculum curriculum) {
        StudentGroup sg = new StudentGroup();
        sg.setCode(STRING);
        sg.setSchool(getSchool());
        sg.setCourse(Short.valueOf((short) 1));
        sg.setCurriculum(curriculum);
        sg.setStudyForm(classifierRepository.getOne("OPPEVORM_MS"));
        return sg;
    }

    public StudyPeriod getStudyPeriod(StudyYear studyYear) {
        StudyPeriod studyPeriod = new StudyPeriod();
        studyPeriod.setStudyYear(studyYear);
        studyPeriod.setNameEt(STRING);
        studyPeriod.setType(classifierRepository.getOne("OPPEPERIOOD_S"));
        studyPeriod.setStartDate(LocalDate.now());
        studyPeriod.setEndDate(LocalDate.now());
        return studyPeriod;
    }

    public StudyYear getStudyYear() {
        StudyYear studyYear = new StudyYear();
        studyYear.setStartDate(LocalDate.now().minusMonths(1));
        studyYear.setEndDate(LocalDate.now().plusMonths(1));
        studyYear.setSchool(getSchool());
        studyYear.setYear(classifierRepository.getOne("OPPEAASTA_2016_17"));
        return studyYear;
    }

    public StudyYearScheduleLegend getStudyYearScheduleLegend() {
        StudyYearScheduleLegend legend = new StudyYearScheduleLegend();
        legend.setSchool(getSchool());
        legend.setCode("A");
        legend.setColor("#FFFFFF");
        legend.setNameEt(STRING);
        legend.setInsertedBy(TestConfiguration.USER_ID);
        return legend;
    }
}
