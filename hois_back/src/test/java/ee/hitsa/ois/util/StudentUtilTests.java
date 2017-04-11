package ee.hitsa.ois.util;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.Student;

public class StudentUtilTests {

    Student student;

    @Before
    public void setUp() {
        student = new Student();
        student.setStatus(new Classifier());
    }

    @Test
    public void isActive() {
        student.getStatus().setCode("OPPURSTAATUS_O");
        Assert.assertTrue(StudentUtil.isActive(student));
    }

    @Test
    public void isNominalStudy() {
        student.setNominalStudyEnd(LocalDate.now().plusDays(1));
        Assert.assertTrue(StudentUtil.isNominalStudy(student));
    }

    @Test
    public void isOnAcademicLeave() {
        student.getStatus().setCode("OPPURSTAATUS_A");
        Assert.assertTrue(StudentUtil.isOnAcademicLeave(student));
    }

    @Test
    public void isStudying() {
        student.getStatus().setCode("OPPURSTAATUS_O");
        Assert.assertTrue(StudentUtil.isStudying(student));
    }

}
