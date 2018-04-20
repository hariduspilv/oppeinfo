package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class JournalEntryTableDto {

    private Long id;
    private String entryType;
    private LocalDate entryDate;
    private String nameEt;
    private String content;
    private String homework;
    private LocalDate homeworkDuedate;
    private Long moodleGradeItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public LocalDate getHomeworkDuedate() {
        return homeworkDuedate;
    }

    public void setHomeworkDuedate(LocalDate homeworkDuedate) {
        this.homeworkDuedate = homeworkDuedate;
    }

    public Long getMoodleGradeItemId() {
        return moodleGradeItemId;
    }

    public void setMoodleGradeItemId(Long moodleGradeItemId) {
        this.moodleGradeItemId = moodleGradeItemId;
    }

}
