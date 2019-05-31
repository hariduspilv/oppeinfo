package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class JournalStudentIndividualCurriculumDto {

    private Long studentId;
    private String fullname;
    private List<JournalModuleDescriptionDto> distinctions;

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

    public List<JournalModuleDescriptionDto> getDistinctions() {
        return distinctions;
    }

    public void setDistinctions(List<JournalModuleDescriptionDto> distinctions) {
        this.distinctions = distinctions;
    }

}
