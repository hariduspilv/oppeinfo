package ee.hitsa.ois.message;

import java.time.LocalDate;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.DateUtils;

public class StudentRepresentativeEnding extends StudentMessage {
    
    private final LocalDate birthdate;

    public StudentRepresentativeEnding() {
        birthdate = null;
    }

    public StudentRepresentativeEnding(Student student) {
        super(student);
        
        birthdate = student.getPerson().getBirthdate();
    }
    
    public String getOppuriSunnipaev() {
        return DateUtils.date(birthdate);
    }
}
