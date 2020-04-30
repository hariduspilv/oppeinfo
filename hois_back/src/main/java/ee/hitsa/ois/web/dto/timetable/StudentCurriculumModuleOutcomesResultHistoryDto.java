package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResultHistory;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentCurriculumModuleOutcomesResultHistoryDto {

    private Long resultId;
    private String grade;
    private LocalDate gradeDate;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    private String addInfo;

    public static StudentCurriculumModuleOutcomesResultHistoryDto of(StudentCurriculumModuleOutcomesResultHistory history) {
        StudentCurriculumModuleOutcomesResultHistoryDto dto = new StudentCurriculumModuleOutcomesResultHistoryDto();
        dto.setResultId(EntityUtil.getId(history.getStudentCurriculumModuleOutcomesResult()));
        dto.setGrade(EntityUtil.getCode(history.getGrade()));
        dto.setGradeDate(history.getGradeDate());
        dto.setGradeInserted(history.getGradeInserted());
        dto.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(history.getGradeInsertedBy()));
        dto.setAddInfo(history.getAddInfo());
        return dto;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
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

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
