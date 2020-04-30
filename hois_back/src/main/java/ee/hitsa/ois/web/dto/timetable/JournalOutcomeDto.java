package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class JournalOutcomeDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private List<StudentCurriculumModuleOutcomesResultDto> outcomeStudents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public List<StudentCurriculumModuleOutcomesResultDto> getOutcomeStudents() {
        return outcomeStudents != null ? outcomeStudents : new ArrayList<>();
    }

    public void setOutcomeStudents(List<StudentCurriculumModuleOutcomesResultDto> outcomeStudents) {
        this.outcomeStudents = outcomeStudents;
    }

}
