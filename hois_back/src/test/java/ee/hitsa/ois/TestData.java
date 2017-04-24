package ee.hitsa.ois;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
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
 */
@Service
public class TestData {
    
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    
    private final String STRING = "ApplicationTest";
    /** Use getter go get the school even in this class! */
    private School school;

    public Curriculum getCurriculum() {
        Curriculum c = new Curriculum();
        c.setNameEt(STRING);
        c.setNameEn(STRING);
        c.setCode(STRING);
        
        c.setStudyPeriod(1);
        c.setOptionalStudyCredits(1);
        
        c.setHigher(Boolean.FALSE);
        c.setJoint(Boolean.FALSE);
        c.setOccupation(Boolean.FALSE);
        c.setValidFrom(LocalDate.now());
        
        c.setConsecution(classifierRepository.findOne("OPPEKAVA_TYPE_E"));
        c.setStatus(classifierRepository.findOne("OPPEKAVA_STAATUS_S"));
        c.setDraft(classifierRepository.findOne("OPPEKAVA_LOOMISE_VIIS_PUUDUB"));
        c.setOrigStudyLevel(classifierRepository.findOne("OPPEASTE_511"));
        c.setSchool(getSchool());

        return c;
    }
    
    public School getSchool() {
        if(school == null) {
            school = schoolRepository.getOne(hoisUserDetailsService.
                    loadUserByUsername(TestConfiguration.USER_ID).getSchoolId());
        }
        return school;
    }
    
    public StudentGroup getStudentGroup(Curriculum curriculum) {
        StudentGroup sg = new StudentGroup();
        sg.setCode(STRING);
        sg.setSchool(getSchool());
        sg.setCourse(1);
        sg.setCurriculum(curriculum);
        sg.setStudyForm(classifierRepository.findOne("OPPEVORM_MS"));
        return sg;
    }
    
    public StudyPeriod getStudyPeriod(StudyYear studyYear) {
        StudyPeriod studyPeriod = new StudyPeriod();
        studyPeriod.setStudyYear(studyYear);
        studyPeriod.setNameEt(STRING);
        studyPeriod.setType(classifierRepository.findOne("OPPEPERIOOD_S"));
        studyPeriod.setStartDate(LocalDate.now());
        studyPeriod.setEndDate(LocalDate.now());
        return studyPeriod;
    }
    
    public StudyYear getStudyYear() {
        StudyYear studyYear = new StudyYear();
        studyYear.setStartDate(LocalDate.now());
        studyYear.setEndDate(LocalDate.now());
        studyYear.setSchool(getSchool());
        studyYear.setYear(classifierRepository.findOne("OPPEAASTA_2016_17"));
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
