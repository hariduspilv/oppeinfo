package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EntityManager;
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
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.application.ApplicationForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class ApplicationServiceTests {

    @Autowired
    private ApplicationService service;
    @Autowired
    private EntityManager em;
    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private Validator validator;

    private HoisUserDetails testUser;
    private Student student;
    private Student occupationalStudent;

    @Before
    public void setUp() {
        if(student == null) {
            student = em.createQuery("select s from Student s where s.person.idcode = ?1"
                    + " and s.curriculumVersion.curriculum.origStudyLevel.value like ?2", Student.class)
                    .setParameter(1, TestConfiguration.USER_ID)
                    .setParameter(2, "5%")
                    .setMaxResults(1).getResultList().get(0);

            User user = testConfigurationService.userWithRoleInSchool(TestConfiguration.USER_ID, Role.ROLL_A, EntityUtil.getId(student.getSchool()));
            testUser = hoisUserDetailsService.getHoisUserDetails(user);
        }

        if(occupationalStudent == null) {
            occupationalStudent = em.createQuery("select s from Student s where s.person.idcode = ?1"
                    + " and s.curriculumVersion.curriculum.origStudyLevel.value like ?2", Student.class)
                    .setParameter(1, TestConfiguration.USER_ID)
                    .setParameter(2, "4%")
                    .setMaxResults(1).getResultList().get(0);
        }
    }


    @Test
    public void testAkadConstraintsMustBeHigher() {
        Assert.assertFalse(noStudents());
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
        Assert.assertFalse(noStudents());
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

    @Test
    public void testDaysUsed() {
        final Long studentId = Long.valueOf(427);
        Assert.assertEquals(27, service.daysUsed(studentId, AcademicLeaveReason.AKADPUHKUS_POHJUS_O));
        Assert.assertEquals(0, service.daysUsed(studentId, AcademicLeaveReason.AKADPUHKUS_POHJUS_T));
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

    private boolean noStudents() {
        return em.createNativeQuery("select 1 from student").setMaxResults(1).getResultList().isEmpty();
    }
}
