package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

public class StudentJournalResultDto {
    private final Long entryId;
    private final String entryCode;
    private final String nameEt;
    private final String nameEn;
    private final String content;
    private final String grade;
    private final LocalDateTime gradeInserted;
    private final String addInfo;

    public StudentJournalResultDto(Long entryId, String entryCode, String nameEt, String nameEn, String content,
            String grade, LocalDateTime gradeInserted, String addInfo) {
        this.entryId = entryId;
        this.entryCode = entryCode;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.content = content;
        this.grade = grade;
        this.gradeInserted = gradeInserted;
        this.addInfo = addInfo;
    }

    public Long getEntryId() {
        return entryId;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getContent() {
        return content;
    }

    public String getGrade() {
        return grade;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public String getAddInfo() {
        return addInfo;
    }

}
