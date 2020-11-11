package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class JournalStudentIndividualCurriculumDto {

    private Long studentId;
    private String fullname;
    private List<JournalStudentDistinctionDto> distinctions;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<JournalStudentDistinctionDto> getDistinctions() {
        return distinctions;
    }

    public void setDistinctions(List<JournalStudentDistinctionDto> distinctions) {
        this.distinctions = distinctions;
    }

}
