package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.GradeDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentCurriculumModuleOutcomesResultDto extends StudentCurriculumModuleOutcomesResultForm {

    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    private List<StudentCurriculumModuleOutcomesResultHistoryDto> history;
    private Boolean canEdit;

    public static StudentCurriculumModuleOutcomesResultDto of(StudentCurriculumModuleOutcomesResult result) {
        StudentCurriculumModuleOutcomesResultDto dto = new StudentCurriculumModuleOutcomesResultDto();
        dto.setId(result.getId());
        dto.setStudentId(EntityUtil.getId(result.getStudent()));
        dto.setGrade(GradeDto.of(result));
        dto.setGradeDate(result.getGradeDate());
        dto.setGradeInserted(result.getGradeInserted());
        dto.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(result.getGradeInsertedBy()));
        dto.setAddInfo(result.getAddInfo());
        dto.setVersion(result.getVersion());
        return dto;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public List<StudentCurriculumModuleOutcomesResultHistoryDto> getHistory() {
        return history != null ? history : new ArrayList<>();
    }

    public void setHistory(List<StudentCurriculumModuleOutcomesResultHistoryDto> history) {
        this.history = history;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
