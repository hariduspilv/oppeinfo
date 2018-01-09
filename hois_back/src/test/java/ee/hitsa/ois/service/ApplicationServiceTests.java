package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.AcademicLeaveReason;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class ApplicationServiceTests {

    @Autowired
    private ApplicationService service;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private Validator validator;
    @Autowired
    private StudentRepository studentRepository;

    private HoisUserDetails testUser;
    private Student student;
    private Student occupationalStudent;

    @Before
    public void setUp() {
        if(student == null) {
            student = studentRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.like(root.get("curriculumVersion").get("curriculum").get("origStudyLevel").get("value"), "5%"));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }).get(0);

            User user = testConfigurationService.userWithRoleInSchool(TestConfiguration.USER_ID, Role.ROLL_A, EntityUtil.getId(student.getSchool()));
            testUser = hoisUserDetailsService.getHoisUserDetails(user);
        }

        if(occupationalStudent == null) {
            occupationalStudent = studentRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.like(root.get("curriculumVersion").get("curriculum").get("origStudyLevel").get("value"), "4%"));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }).get(0);
        }
    }


    @Test
    public void testAkadConstraintsMustBeHigher() {
        Assert.assertFalse(studentRepository.findAll().isEmpty());
        ApplicationForm applicationForm = getAkadApplicationForm(occupationalStudent);
        applicationForm.setStartDate(LocalDate.now());
        applicationForm.setEndDate(LocalDate.now().plusYears(1));

        Set<ConstraintViolation<ApplicationForm>> errors = validator.validate(applicationForm);
        Assert.assertTrue(errors.isEmpty());

        ValidationFailedException result = null;
        try {
            service.save(testUser, new Application(), applicationForm);
        } catch (ValidationFailedException e) {
            result = e;
        }

        Assert.assertNotNull(result);
        Assert.assertEquals("application.messages.studentIsNotHigher", result.getErrorInfo().getErrors().get(0).getCode());
    }

    @Test
    public void testAkadConstraintsPeriodIsTooLong() {
        Assert.assertFalse(studentRepository.findAll().isEmpty());
        ApplicationForm applicationForm = getAkadApplicationForm(student);
        applicationForm.setStartDate(LocalDate.now());
        applicationForm.setEndDate(LocalDate.now().plusYears(2));

        Set<ConstraintViolation<ApplicationForm>> errors = validator.validate(applicationForm);
        Assert.assertTrue(errors.isEmpty());

        ValidationFailedException result = null;
        try {
            service.save(testUser, new Application(), applicationForm);
        } catch (ValidationFailedException e) {
            result = e;
        }

        Assert.assertNotNull(result);
        Assert.assertEquals("application.messages.periodIsTooLong", result.getErrorInfo().getErrors().get(0).getCode());
    }

    private static ApplicationForm getAkadApplicationForm(Student student) {
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setStudent(AutocompleteResult.of(student));
        applicationForm.setStatus(ApplicationStatus.AVALDUS_STAATUS_KOOST.name());
        applicationForm.setType(ApplicationType.AVALDUS_LIIK_AKAD.name());
        applicationForm.setReason(AcademicLeaveReason.AKADPUHKUS_POHJUS_O.name());
        applicationForm.setIsPeriod(Boolean.FALSE);
        return applicationForm;
    }

}
