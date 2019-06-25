package ee.hitsa.ois.message;

import java.time.LocalDate;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.DateUtils;

public class SupportServiceEnding extends StudentMessage {
    
    private LocalDate endDate;
    
    public SupportServiceEnding() {
        endDate = null;
    }

    public SupportServiceEnding(Student student, LocalDate endDate) {
        super(student);

        this.endDate = endDate;
    }

    public String getEndDate() {
        return DateUtils.date(endDate);
    }
    
    public String getLoppemiseKuupaev() {
        return DateUtils.date(endDate);
    }
}
