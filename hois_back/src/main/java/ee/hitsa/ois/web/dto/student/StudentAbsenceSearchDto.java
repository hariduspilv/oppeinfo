package ee.hitsa.ois.web.dto.student;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class StudentAbsenceSearchDto extends PageImpl<StudentAbsenceDto> {

    private final String studentName;
    private final String studentGroup;
    private final boolean canAddAbsence;

    public StudentAbsenceSearchDto(List<StudentAbsenceDto> data, Pageable pageable, long total, String studentName, String studentGroup, boolean canAddAbsence) {
        super(data, pageable, total);

        this.studentName = studentName;
        this.studentGroup = studentGroup;
        this.canAddAbsence = canAddAbsence;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public boolean isCanAddAbsence() {
        return canAddAbsence;
    }
}
