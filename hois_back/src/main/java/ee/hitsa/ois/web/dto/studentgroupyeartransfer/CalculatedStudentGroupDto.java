package ee.hitsa.ois.web.dto.studentgroupyeartransfer;

import java.util.ArrayList;
import java.util.List;

public class CalculatedStudentGroupDto {

    private final Long logId;
    private Long suitableStudents = Long.valueOf(0);
    private Long unsuitableStudents = Long.valueOf(0);
    private final List<StudentDto> mismatchingStudents = new ArrayList<>();
    
    public CalculatedStudentGroupDto(Long logId) {
        this.logId = logId;
    }

    public Long getLogId() {
        return logId;
    }
    
    public Long getSuitableStudents() {
        return suitableStudents;
    }
    public void setSuitableStudents(Long suitableStudents) {
        this.suitableStudents = suitableStudents;
    }

    public Long getUnsuitableStudents() {
        return unsuitableStudents;
    }
    public void setUnsuitableStudents(Long unsuitableStudents) {
        this.unsuitableStudents = unsuitableStudents;
    }

    public List<StudentDto> getMismatchingStudents() {
        return mismatchingStudents;
    }
    
}
